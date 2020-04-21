package com.scsse.workflow.entity;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.List;

public class HandleCommon {


    public  String codeString(String fileName) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        bin.close();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }

        return code;
    }
    //拟合直线求系数

    public double xishu(List<Data> sample){
        final WeightedObservedPoints obs = new WeightedObservedPoints();
        for(int i=0;i<5;i++){
            double m,n;
            m=Double.parseDouble(sample.get(i).getId());
            n=sample.get(i).getMeanMrea();
            obs.add(m,n);
        }

        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(1);

        // Retrieve fitted parameters (coefficients of the polynomial function).
        final double[] coeff = fitter.fit(obs.toList()); //第二个数字为系数
        for (double c : coeff) {
            System.out.println(c);
        }

        double x=(double) Math.round(coeff[1]*1000) / 1000;
        return x;
    }


    public  String appentStr4Length(String str , int length){
        if(str == null){
            str = "";
        }
        try {
            int strLen = 0;//计算原字符串所占长度,规定中文占两个,其他占一个
            for(int i = 0 ; i<str.length(); i++){
                if(isChinese(str.charAt(i))){
                    strLen = strLen + 2;
                }else{
                    strLen = strLen + 1;
                }
            }
            if(strLen>=length){
                return str;
            }
            int remain = length - strLen;//计算所需补充空格长度
            for(int i =0 ; i< remain ;i++){
                str = str + " ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

}
