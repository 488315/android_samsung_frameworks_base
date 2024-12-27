package com.android.systemui.dump;

import android.icu.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public abstract class LogBufferEulogizerKt {
    public static final long MIN_WRITE_GAP = TimeUnit.MINUTES.toMillis(5);
    public static final long MAX_AGE_TO_DUMP = TimeUnit.HOURS.toMillis(48);
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
}
