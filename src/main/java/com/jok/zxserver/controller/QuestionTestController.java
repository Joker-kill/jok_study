package com.jok.zxserver.controller;

import com.jok.zxserver.domain.R;
import com.jok.zxserver.utils.QuestionExportModule;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author JOKER
 * create time 2024/11/24 15:25
 */

@RestController
@RequestMapping("/question")
public class QuestionTestController {

    @GetMapping("/getSubjects")
    public R<String> getSubjects(){
        // todo 获取问卷列表
        return R.ok("成功");
    }

    /**
     * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
     *
     * @since 2.1.1
     */
    @GetMapping("/getQuestionModule")
    public ResponseEntity<Resource> downloadExcel() throws IOException {
        Workbook workbook = QuestionExportModule.questionImportModule();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        // 将字节输出流转换为Resource
        ByteArrayResource resource = new ByteArrayResource(bos.toByteArray()) {
            @Override
            public String getFilename() {
                return "example.xlsx";
            }
        };
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        // 返回ResponseEntity，包含文件和HTTP头信息
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("/uploadQuestionModule")
    public ResponseEntity<String> handleUploadModule(@RequestParam("file") MultipartFile file){

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("请上传文件");
        }
        try {
            InputStream inputStream = file.getInputStream();
            QuestionExportModule.readExcel(inputStream);
            return ResponseEntity.ok("文件上传成功: " + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败");
        }
    }
}
