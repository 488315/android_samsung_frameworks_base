package com.android.systemui.log.table;

import android.icu.text.SimpleDateFormat;
import java.util.Locale;

public abstract class TableLogBufferKt {
    public static final SimpleDateFormat TABLE_LOG_DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
}
