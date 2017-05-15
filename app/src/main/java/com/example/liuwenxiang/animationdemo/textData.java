package com.example.liuwenxiang.animationdemo;

import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuwenxiang on 17/5/12.
 */

public class textData {


    /**
     * @param args
     * @Title : main
     * @Type : DateType
     * @date : 2014年3月8日 下午10:54:50
     * @Description :
     */
    public static void main(String[] args) {
        {
            String checkValue = "2017.04.31";


            boolean b =dataLenient(checkValue);
            if (b) {

                System.out.println("格式正确");
            } else {
                System.out.println("格式错误");
            }

        }
    }


    public static boolean dataLenient(String dataStr) {
        boolean flag = true;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        try {
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(dataStr);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}