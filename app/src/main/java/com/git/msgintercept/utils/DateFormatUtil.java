/*
 * 文件名: DateFormatUtil
 * 版    权：  Copyright  LiJinHua  All Rights Reserved.
 * 描    述: [常量类]
 * 创建人: LiJinHua
 * 创建时间:  2014-3-21
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.git.msgintercept.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * DateFormatUtil时间日期格式
 */
public class DateFormatUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYYdMMdDDsHHmMM = "yyyy.MM.dd HH:mm";

    public static final String MM_DD = "MM-dd";

    public static final String MM_DD2 = "MM月dd日";
    public static final String MM_DD2_HH_MM = "MM月dd日HH:mm";
    public static final String YYYY_MM_DD2 = "yyyy年MM月dd日";
    public static final String YYYY_MM_DD2_HH_MM = "yyyy年MM月dd日HH:mm";
    public static final String YYYY_MM_DD2_HH_MM_SS = "yyyy年MM月dd日 HH:mm:ss";

    public static final String HH_MM_12 = "KK:mm";
    public static final String HH_MM_24 = "HH:mm";

    enum TimeName {
        today,
        yesterday,
        the_day_before_yesterday,
        the_day_before_yesterday_more,
        tomorrow,
        the_day_after_tomorrow,
        the_day_after_tomorrow_more,
        last_month,
        last_month_more,
        next_month,
        next_month_more,
        last_year,
        last_year_more,
        next_year,
        next_year_more
    }

    /**
     * 时段
     */
    enum TimeQuantum {
        /**
         * 凌晨:24点-6点
         **/
        wee_hours,
        /**
         * 上午:6点-12点
         **/
        forenoon,
        /**
         * 中午:12点-14点
         **/
        nooning,
        /**
         * 下午:14点-18点
         **/
        afternoon,
        /**
         * 晚上:18点-24点
         **/
        night
    }

    /**
     * 对比时间
     *
     * @param milliseconds
     * @return
     */
    public static TimeName compareTime(long milliseconds) {
        return compareTime(milliseconds, System.currentTimeMillis());
    }

    /**
     * 对比时间
     *
     * @param milliseconds
     * @param current
     * @return
     */
    public static TimeName compareTime(long milliseconds, long current) {
        Calendar calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTimeInMillis(current);

        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds);

        int y = calendarUser.get(Calendar.YEAR) - calendarCurrent.get(Calendar.YEAR);

        if (y == 0) {
            return compareMonth(calendarUser, calendarCurrent);
        } else if (y == -1) {
            //跨年份，
            int m = calendarUser.get(Calendar.MONTH) - (calendarCurrent.get(Calendar.MONTH) + 12);
            if (m == -1) {
                //上个月是12月时
                int d = calendarUser.get(Calendar.DAY_OF_MONTH) - (calendarCurrent.get(Calendar.DAY_OF_MONTH) + 31);
                if (d >= -3) {
                    //前两天，则算名称按昨天前天
                    return compareDay(d);
                } else {
                    return TimeName.last_month;
                }
            } else {
                return TimeName.last_year;
            }
        } else if (y <= -2) {
            return TimeName.last_year_more;
        } else if (y == 1) {
            //跨年份
            int m = calendarUser.get(Calendar.MONTH) + 12 - calendarCurrent.get(Calendar.MONTH);
            if (m == 1) {
                //下个月是1月
                int d = calendarUser.get(Calendar.DAY_OF_MONTH) + 31 - calendarCurrent.get(Calendar.DAY_OF_MONTH);
                if (d <= 3) {
                    //前两天，则算名称按昨天前天
                    return compareDay(d);
                } else {
                    return TimeName.next_month;
                }
            } else {
                return TimeName.next_year;
            }
        } else {
            return TimeName.next_year_more;
        }
    }

    private static TimeName compareMonth(Calendar calendarUser, Calendar calendarCurrent) {
        int m = calendarUser.get(Calendar.MONTH) - calendarCurrent.get(Calendar.MONTH);
        int d = calendarUser.get(Calendar.DAY_OF_YEAR) - calendarCurrent.get(Calendar.DAY_OF_YEAR);
        if (m == 0) {
            //当月
            return compareDay(d);
        } else if (m == -1) {
            //上个月
            if (d >= -3) {
                //前两天，则算名称按昨天前天
                return compareDay(d);
            } else {
                return TimeName.last_month;
            }
        } else if (m <= -2) {
            return TimeName.last_month_more;
        } else if (m == 1) {
            //下个月
            if (d <= 3) {
                //后两天，则算名称按明天后天
                return compareDay(d);
            } else {
                return TimeName.next_month;
            }
        } else {
            //下下个月
            return TimeName.next_month_more;
        }
    }

    private static TimeName compareDay(int d) {
        if (d == 0) {
            return TimeName.today;
        } else if (d == -1) {
            return TimeName.yesterday;
        } else if (d == -2) {
            return TimeName.the_day_before_yesterday;
        } else if (d < -2) {
            return TimeName.the_day_before_yesterday_more;
        } else if (d == 1) {
            return TimeName.tomorrow;
        } else if (d == 2) {
            return TimeName.the_day_after_tomorrow;
        } else {
            return TimeName.the_day_after_tomorrow_more;
        }
    }

    public static TimeQuantum getTimeQuantum(long milliseconds) {
        Calendar calendarUser = Calendar.getInstance();
        calendarUser.setTimeInMillis(milliseconds);
        int h = calendarUser.get(Calendar.HOUR_OF_DAY);
        if (h >= 0 && h < 6) {// 凌晨:24点-6点
            return TimeQuantum.wee_hours;
        } else if (h >= 6 && h < 12) {// 上午:6点-12点
            return TimeQuantum.forenoon;
        } else if (h >= 12 && h < 14) {// 中午:12点-14点
            return TimeQuantum.nooning;
        } else if (h >= 14 && h < 18) {// 下午:14点-18点
            return TimeQuantum.afternoon;
        } else {// 晚上:18点-24点
            return TimeQuantum.night;
        }
    }

    /**
     * 格式化日期时间
     *
     * @param milliseconds
     * @param format
     * @return
     */
    public static String format(long milliseconds, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(milliseconds));
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime() {
        return getDataTime("HH:mm");
    }

    /**
     * 格式化日期时间
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String format(String dateStr, String format) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.parseLong(dateStr) * 1000));
    }

    /**
     * 解析日期格式
     *
     * @param date
     * @param format
     * @return
     */
    public static Date parseDate(String date, String format) {
        Date tempDate = null;
        try {
            SimpleDateFormat asf = new SimpleDateFormat(format);
            tempDate = asf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (tempDate == null) {
            tempDate = new Date();
        }
        return tempDate;
    }

    private static TimeName compareTime(String time) {
        String current = null;
        current = "2014-01-01 13:35:38";
        return compareTime(time, current);
    }

    private static TimeName compareTime(String time, String current) {
        Date uDate = parseDate(time, YYYY_MM_DD_HH_MM_SS);
        if (current == null) {
            return compareTime(uDate.getTime(), System.currentTimeMillis());
        } else {
            Date cDate = parseDate(current, YYYY_MM_DD_HH_MM_SS);
            return compareTime(uDate.getTime(), cDate.getTime());
        }
    }

    private static TimeQuantum getTimeQuantum(String time) {
        Date uDate = parseDate(time, YYYY_MM_DD_HH_MM_SS);
        return getTimeQuantum(uDate.getTime());
    }

    public static String  timeFormat(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(time);
        return  hms;
    }

    private static String[] times = {
            "2004-03-22 00:35:38",
            "2010-03-22 05:35:38",
            "2012-03-22 06:35:38",
            "2013-03-22 08:35:38",
            "2014-03-22 11:35:38",
            "2015-03-22 12:35:38",
            "2016-03-22 13:35:38",
            "2017-03-22 15:35:38",
            "2018-03-22 16:35:38",
            "2019-03-22 18:35:38",
            "2014-01-22 19:35:38",
            "2014-02-22 21:35:38",
            "2014-03-22 23:35:38",
            "2014-04-22 13:35:38",
            "2014-05-22 13:35:38",
            "2014-06-22 13:35:38",
            "2014-07-22 13:35:38",
            "2014-08-22 13:35:38",
            "2014-03-12 13:35:38",
            "2014-03-17 13:35:38",
            "2014-03-18 13:35:38",
            "2014-03-20 13:35:38",
            "2014-03-21 13:35:38",
            "2014-03-22 13:35:38",
            "2014-03-23 13:35:38",
            "2014-03-24 13:35:38",
            "2014-03-25 13:35:38",
            "2014-03-26 13:35:38",

            "2014-01-01 13:35:38",
            "2014-01-02 13:35:38",
            "2014-01-03 13:35:38",
            "2014-01-04 13:35:38",

            "2013-12-28 13:35:38",
            "2013-12-29 13:35:38",
            "2013-12-30 13:35:38",
            "2013-12-31 13:35:38",
            "2013-11-22 13:35:38",
            "2013-10-11 13:35:38"
    };

    public static void main(String[] args) {
        for (String tstr : times) {
//            System.out.println(tstr+"  ==  "+compareTime(tstr) + "  ==  " +getTimeQuantum(tstr));
        }
    }
}
