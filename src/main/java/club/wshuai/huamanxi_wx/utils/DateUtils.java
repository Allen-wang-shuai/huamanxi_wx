package club.wshuai.huamanxi_wx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    //日期转换成字符串
    public static String dateToString(Date date,String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String formatDate = format.format(date);
         return formatDate;
    }

    //字符串转换成日期
    public static Date stringToDate(String str,String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date parseDate = format.parse(str);
        return parseDate;
    }
}
