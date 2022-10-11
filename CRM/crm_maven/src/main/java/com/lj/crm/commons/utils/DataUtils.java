package com.lj.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
    public static String formatDateTime(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr= simpleDateFormat.format(date);
        return dateStr;
    }
}
