package com.android.systemui.dump;

import android.icu.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class LogBufferEulogizerKt {
    public static final long MIN_WRITE_GAP = TimeUnit.MINUTES.toMillis(5);
    public static final long MAX_AGE_TO_DUMP = TimeUnit.HOURS.toMillis(48);
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
}
