package com.wkt.entrance.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

public class ZmjUtil {

    /**
     * 判断对象是否为空
     * @param o
     * @return
     */
    public static boolean isNullOrEmpty(Object o){
        if (o == null) {
            return true;
        }

        if (o instanceof CharSequence) {
            return ((CharSequence) o).length() == 0;
        }

        if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        }

        if (o instanceof Map) {
            return ((Map) o).isEmpty();
        }

        if (o instanceof Object[]) {
            Object[] object = (Object[]) o;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    /**
     * 复制对象的非空属性
     * @param newObj    源对象
     * @param oldObj    结果对象
     */
    public static void copyPropertiesIgnoreNull(Object newObj, Object oldObj) {
        BeanUtils.copyProperties(newObj, oldObj, getNullPropertyNames(newObj));
    }

    /**
     * 获取空属性名
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {

        final BeanWrapper src = new BeanWrapperImpl(source);

        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    //置空开始-------------------------


    public static String processNull(String var0) {
        return var0 == null ? "" : var0;
    }

    public static String processNull(Date var0) {
        return var0 == null ? "" : var0.toString();
    }

    public static String processNull(Long var0) {
        return var0 == null ? "" : var0.toString();
    }

    public static String processNull(Object var0) {
        return var0 == null ? "" : var0.toString();
    }

    public static String processNull(float var0) {
        return (double)var0 == 0.0D ? "" : String.valueOf(var0);
    }


    public static String processSpace(String var0) {
        return var0 == null ? "&nbsp;" : var0;
    }


    //置空结束-------------------------------

    public static String getUUID19(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static  String getNum19(){
        String numStr = "" ;
        String trandStr = String.valueOf((Math.random() * 9 + 1) * 1000000);
        String dataStr = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        numStr = trandStr.toString().substring(0, 4);
        numStr = dataStr+numStr;
        return numStr ;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

    public static void main(String[] args) {
        String s = "";
        System.out.println(ZmjUtil.isNullOrEmpty(s));
        System.out.println(getNum19());
    }
}
