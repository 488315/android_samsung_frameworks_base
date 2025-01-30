package com.android.server.chimera;

import android.util.ArrayMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class ChimeraDataCache {
    public static final ThreadLocal DATE_FORMAT_MAP = new ThreadLocal();

    public static DateFormat getDateFormat(String str) {
        ThreadLocal threadLocal = DATE_FORMAT_MAP;
        Map map = (Map) threadLocal.get();
        if (map == null) {
            ArrayMap arrayMap = new ArrayMap();
            threadLocal.set(arrayMap);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
            arrayMap.put(str, simpleDateFormat);
            return simpleDateFormat;
        }
        DateFormat dateFormat = (DateFormat) map.get(str);
        if (dateFormat != null) {
            return dateFormat;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str);
        map.put(str, simpleDateFormat2);
        return simpleDateFormat2;
    }
}
