package net.syxsoft.ldyhapplication.utils;

/**
 * Created by 谷永庆 on 2018/2/17.
 */

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期是该月的第几天
     *
     * @return
     */
    public static int getCurrentDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日期是该周的第几天
     *
     * @return
     */
    public static int getCurrentDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前是几点
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//二十四小时制
    }

    /**
     * 获取当前是几分
     *
     * @return
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 获取当前秒
     *
     * @return
     */
    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    /**
     * 根据传入的年份和月份，获取当前月份的日历分布
     *
     * @param year
     * @param month
     * @return
     */
    public static int[][] getDayOfMonthFormat(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);//设置时间为每月的第一天
        //设置日历格式数组,6行7列
        int days[][] = new int[6][7];
        //设置该月的第一天是周几
        int daysOfFirstWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //设置本月有多少天
        int daysOfMonth = getDaysOfMonth(year, month);
        //设置上个月有多少天
        int daysOfLastMonth = getLastDaysOfMonth(year, month);
        int dayNum = 1;
        int nextDayNum = 1;
        //将日期格式填充数组
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                if (i == 0 && j < daysOfFirstWeek - 1) {
                    days[i][j] = daysOfLastMonth - daysOfFirstWeek + 2 + j;
                } else if (dayNum <= daysOfMonth) {
                    days[i][j] = dayNum++;
                } else {
                    days[i][j] = nextDayNum++;
                }
            }
        }
        return days;
    }

    /**
     * 根据传入的年份和月份，判断上一个有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getLastDaysOfMonth(int year, int month) {
        int lastDaysOfMonth = 0;
        if (month == 1) {
            lastDaysOfMonth = getDaysOfMonth(year - 1, 12);
        } else {
            lastDaysOfMonth = getDaysOfMonth(year, month - 1);
        }
        return lastDaysOfMonth;
    }

    /**
     * 根据传入的年份和月份，判断当前月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (isLeap(year)) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return -1;
    }

    /**
     * 判断是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date date = null;
        try {
            date = f.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getSimpleDateFormat(Date date,String formatString) {
        if (formatString == null || formatString.length() == 0) {
            formatString = "yyyy-MM-dd";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(date.getTime());
    }

    public static Date strToDate(String str,String formatString) {

        if (formatString == null || formatString.length() == 0) {
            formatString = "yyyy-MM-dd";
        }

        SimpleDateFormat format = new SimpleDateFormat(formatString);

        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateGST(Object obj, String pattern) throws ParseException{
        String str = obj.toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if(str.equals(""))
            return null;
        else
            return sdf.parse(str);
    }
}