package com.intertek.demo.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.intertek.demo.common.entity.ItsConstant;
import com.intertek.demo.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * ITS内部工具类
 *
 * @author jacksy.qin
 * @date 2019/9/23 14:34
 */
@Slf4j
public class ItsUtil {

    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
        if (arr.length == 0) {
            return value;
        }
        StringBuilder result = new StringBuilder();
        IntStream.range(0, arr.length).forEach(i -> {
            if (i != arr.length - 1) {
                result.append(arr[i]).append(StringPool.UNDERSCORE);
            }else {
                result.append(arr[i]);
            }
        });
        return StringUtils.lowerCase(result.toString());
    }

    /**
     * 下划线转驼峰
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String underscoreToCamel(String value) {
        StringBuilder result = new StringBuilder();
        String[] arr = value.split("_");
        for (String s : arr) {
            result.append((String.valueOf(s.charAt(0))).toUpperCase()).append(s.substring(1));
        }
        return result.toString();
    }

    /**
     * 判断是否为 ajax请求
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }

    /**
     * 正则校验
     *
     * @param regex 正则表达式字符串
     * @param value 要匹配的字符串
     * @return 正则校验结果
     */
    public static boolean match(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 获取当前登录用户
     *
     * @return User
     */
    public static User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登录用户UserId
     *
     * @return User
     */
    public static Integer getCurrentUserId() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user == null ? null : user.getUserId().intValue();

    }

    /**
     * 判断是否包含中文
     *
     * @param value 内容
     * @return 结果
     */
    public static boolean containChinese(String value) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(value);
        return m.find();
    }

    public static String view(String viewName) {
        return ItsConstant.VIEW_PREFIX + viewName;
    }

    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }

    public static double formatObjectAsDouble(Object value, int decimalPoint){
        if(isNumeric(value.toString())){
            BigDecimal b = new BigDecimal(value.toString());
            return b.setScale(decimalPoint, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return 0d;
    }

    public static double intDivisionFormat(double first, double second, int decimalPoint){
        try{
            double result = new BigDecimal((float)first / second).setScale(decimalPoint, BigDecimal.ROUND_HALF_UP).doubleValue();
            return result;
        }catch(Exception e){
            return 0d;
        }
    }

    /**
     * 求俩数组的交集
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] intersect(String[] arr1, String[] arr2){
        List<String> l = new LinkedList<String>();
        Set<String> common = new HashSet<String>();
        for(String str:arr1){
            if(!l.contains(str)){
                l.add(str);
            }
        }
        for(String str:arr2){
            if(l.contains(str)){
                common.add(str);
            }
        }
        String[] result={};
        return common.toArray(result);
    }

    /**
     * 求俩数组的并集
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] union (String[] arr1, String[] arr2){
        Set<String> hs = new HashSet<String>();
        for(String str:arr1){
            hs.add(str);
        }
        for(String str:arr2){
            hs.add(str);
        }
        String[] result={};
        return hs.toArray(result);
    }

    /**
     * 求俩数组的差集
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] substract(String[] arr1, String[] arr2) {
        LinkedList<String> list = new LinkedList<String>();
        for (String str : arr1) {
            if(!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : arr2) {
            if (list.contains(str)) {
                list.remove(str);
            }
        }
        String[] result = {};
        return list.toArray(result);
    }
}
