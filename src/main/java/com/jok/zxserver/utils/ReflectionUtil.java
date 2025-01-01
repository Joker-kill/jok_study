package com.jok.zxserver.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author JOKER
 * create time 2024/12/21 17:25
 */
public class ReflectionUtil {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Test test = new Test();
        Method name = invokeSet(test.getClass(), "like");
        name.invoke(test,"张三");
        System.out.println(test);
    }
    public static Method invokeSet(Class<?> clazz,String filedName){
        try{
            Field field = clazz.getDeclaredField(filedName);
            if(field != null){
                String setMethodName = "set"+filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
                System.out.println(setMethodName);
                System.out.println(setMethodName);
                Method method = clazz.getDeclaredMethod(setMethodName, String.class);
                return method;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }



}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Test{
    private String name;
    private String like;
}