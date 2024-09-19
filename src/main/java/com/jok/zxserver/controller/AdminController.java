package com.jok.zxserver.controller;

;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jok.zxserver.domain.DO.UserDO;
import com.jok.zxserver.domain.DO.WxLoginDO;
import com.jok.zxserver.domain.R;
import com.jok.zxserver.domain.VO.LoginVo;
import com.jok.zxserver.domain.entity.User;
import com.jok.zxserver.service.UserService;
import com.jok.zxserver.utils.CommonUtil;
import com.jok.zxserver.utils.DecodeUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Map;

/**
 * @Author JOKER
 * create time 2024/8/3 20:01
 */

@RestController
@RequestMapping("/user")
public class AdminController {

    @Autowired
    UserService userService;
    @PostMapping("/login")
    public R<LoginVo> Login(@RequestBody String body, @PathParam("model") String model, HttpServletRequest request) throws IOException {

        WxLoginDO data ;
        System.out.println("请求数据"+body);
        System.out.println(model);
        if(model!=null && model !=""){
            if(model.equals("sq")){
                data =  JSON.parseObject(body, WxLoginDO.class);
                System.out.println("封装之后"+data);
                String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+
                        CommonUtil.getAppInfo().get("appId") +
                        "&secret="+CommonUtil.getAppInfo().get("appSecret")+
                        "&js_code=" +data.getCode()+"&grant_type=authorization_code ";

                RestTemplate restTemplate = new RestTemplate();
                String result = restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();

                System.out.println("返回用户数据"+result);

                JSONObject jsonObject = JSON.parseObject(result);
                String sessionKey = jsonObject.getString("session_key");
                String openId = jsonObject.getString("openid");
                String decode = DecodeUserInfo.decode(data.getEncryptedData(), data.getIv(), data.getEncryptedData(), sessionKey);

                UserDO userDO = new UserDO();
                JSONObject userInfo = JSON.parseObject(decode);
                userDO.setAvatarUrl(userInfo.getString("avatarUrl"));
                userDO.setGender(userInfo.getInteger("gender"));
                userDO.setNickName(userInfo.getString("nickName"));
                userDO.setOpenId(openId);

                LoginVo login = userService.login(userDO);

                return R.ok(login);
            }
            if(model.equals("at")){
                Map<String,String> parse = (Map<String,String>)JSON.parse(body);
                String userName = parse.get("userName");
                String password = parse.get("password");
                LoginVo loginVo = userService.login(userName,password);
                if (loginVo == null){
                    return R.fail(466,"用户名或密码错误");
                }
                return R.ok(loginVo);
            }
        }
        return R.fail(479,"未知错误");
    }

    @GetMapping("/info")
    public R<User> getUser(@PathParam("uid") String uid){
        User user = userService.getById(uid);
        return R.ok(user);
    }
}
