package ru.rtire.lightchat.utils;

import java.util.Map;
import java.util.HashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToMillis {

    public static Long Parse(String s) {
        Long time = 0L;

        @SuppressWarnings("serial")
        Map<String, Long> kindByMs = new HashMap<String, Long>() {{
            put("Y", 31536000000L); // не високосный год
            put("M", 2592000000L); // 30 дней
            put("w", 604800000L); // 7 дней
            put("d", 86400000L); // 1 день
            put("h", 3600000L); // 1 час
            put("m", 60000L);  // 1 минута
            put("s", 1000L);  // 1 секунда
            put("S", 1L);    // 1 миллисекунда
        }};

        Matcher m = Pattern.compile("(\\d+)(Y|M|w|d|h|m|s|S)").matcher(s);

        while (m.find()) {
            int number = Integer.parseInt(m.group(1));
            String kind = m.group(2);
            Long ms = kindByMs.get(kind);

            time = time + number * ms;
        }

        return time;
    }

}
