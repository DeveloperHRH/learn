package com.learn.management.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: herui
 * @Description: 日期工具类
 * @Date: Create in 14:38 2018/8/6
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public final static String YMD_HMS_DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 计算日期date和当前日期相差多少,date在现在日期后
     *
     * @param date 和当前时间比较的日期
     * @return
     */
    public static String subtraction(String date) {

        String restTime = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = new Date();
        try {
            d1 = format.parse(date);
            //毫秒ms
            long diff = d1.getTime() - d2.getTime();
            if (diff < 0) {
                //diff = d2.getTime() - d1.getTime();
                //return "0天";
                return "0";
            }
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays > 0) {
                return diffDays + "";
            } else {
                return "0";
               /* if(diffHours > 0){
                    return diffHours+"小时";
                }else{
                    return diffMinutes+"分"+diffSeconds+"秒";
                }*/
            }

        } catch (Exception e) {
            logger.error("日期工具类，日期减法" + e);
        }
        return restTime;
    }


    public static String subtractionS(String date) {

        String restTime = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = new Date();
        try {
            d1 = format.parse(date);
            //毫秒ms
            long diff = d1.getTime() - d2.getTime();
            if (diff < 0) {
                //diff = d2.getTime() - d1.getTime();
                return "0天";

            }
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            return diffDays + "天" + diffHours + "小时" + diffMinutes + "分" + diffSeconds + "秒";

        } catch (Exception e) {
            logger.error("日期工具类，日期减法" + e);
        }
        return restTime;
    }


    public static int subReturnInt(String date) {

        String restTime = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = new Date();
        try {
            d1 = format.parse(date);
            //毫秒ms
            long diff = d1.getTime() - d2.getTime();
            if (diff < 0) {
                return 0;
            }
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays > 0) {
                //一天多返回二天
                if (diff - (diffDays * (24 * 60 * 60 * 1000)) > 0) {
                    return (int) diffDays + 1;
                }
                return (int) diffDays;
            } else {
                if ((long) 24 * 60 * 60 * 1000 - diff > 0) {
                    return 1;
                }
                return 0;
            }

        } catch (Exception e) {
            logger.error("日期工具类，日期减法" + e);
            return -1;
        }

    }


/*    public static int sub(String date){

        int restDays = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = new Date();
        try {
            d1 = format.parse(date);

            long diff = d1.getTime() - d2.getTime();
            if(diff < 0){
                return 0;
            }

            long diffDays = diff / (24 * 60 * 60 * 1000);
            return (int)diff;

        } catch (Exception e) {
            logger.error("日期工具类，日期减法"+e);
        }
        return restDays;
    }*/

    /**
     * 计算指定日期加上一个毫秒数后得到的日期
     *
     * @param dateTime   指定的日期
     * @param timeMillis 毫秒数
     * @return 返回计算后的日期
     */
    public static String addition(String dateTime, long timeMillis) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date d1 = format.parse(dateTime);

            Date date = new Date(d1.getTime() + timeMillis);
            return format.format(date);
        } catch (Exception e) {
            logger.error("日期工具类，日期加法" + e);
        }
        return "";
    }

    /**
     * 返回一天的时刻
     *
     * @return
     */
    public static String getPartOfDay() {

        Date date = new Date();
        int hour = date.getHours();
        if (0 <= hour && hour < 5) {
            return "凌晨";
        }
        if (5 <= hour && hour < 12) {
            return "上午";
        }
        if (12 <= hour && hour < 14) {
            return "中午";
        }
        if (14 <= hour && hour < 19) {
            return "下午";
        }
        if (20 <= hour && hour < 0) {
            return "晚上";
        }
        return "";
    }


    /**
     * @param format 需要时间的格式(自定义,例如:yyyy-MM-dd)
     */
    public static String getCustomTime(String format,Date date) {
        SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
        return df.format(date);
    }



    /**
     * 根据当前日期获取后某个月的日期
     *
     * @param month 月 整型
     * @return
     */
    public static String addMonth(int month) {
        if (month <= 0) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, month);
        return f.format(calendar.getTime());
    }


    /**
     * 根据当前日期获取后某个月的日期
     *
     * @param day 日 整型
     * @return
     */
    public static String addDay(int day) {
        if (day <= 0) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_WEEK, day);
        return f.format(calendar.getTime());
    }

    /**
     * 根据当前日期获取前某个月的时间日期
     *
     * @param month 月 整型
     * @return
     */
    public static String rollMonth(int month) {
        if (month <= 0) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.roll(Calendar.MONTH, -month);
        return f.format(calendar.getTime());
    }

    /**
     * mysql 存在时间戳溢出，定义默认最大上限时间2038-01-01 00:00:00 的 时间戳值
     */
    public static final Long maxTimeMillis = Long.valueOf("2145888000000");
    public static final String maxDay = "2038-01-01";

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long nowTimes() {
        return (Calendar.getInstance().getTimeInMillis());
    }

    public static long nowTime() {
        return (Calendar.getInstance().getTimeInMillis()/1000);
    }

    /**
     * 时间转换为时间戳
     *
     * @param time
     * @return
     */
    public static long dateToTimestamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(time);
            long ts = date.getTime() / 1000;
            return ts;
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 时间戳转换为时间
     *
     * @param time
     * @return
     */
    public static String timestampToDate(long time) {
        String dateTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YMD_HMS_DATEFORMAT);
        long timeLong = Long.valueOf(time);
        dateTime = simpleDateFormat.format(new Date(timeLong * 1000L));
        return dateTime;
    }

    /**
     * 时间戳转换为时间
     *
     * @param time
     * @return
     */
    public static String timestampToDate(long time, String  format) {
        String dateTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long timeLong = Long.valueOf(time);
        dateTime = simpleDateFormat.format(new Date(timeLong * 1000L));
        return dateTime;
    }
    
    public static void main(String[] args) {
        String time = timestampToDate(1561458385);
        logger.info("time:"+time);
    }
}
