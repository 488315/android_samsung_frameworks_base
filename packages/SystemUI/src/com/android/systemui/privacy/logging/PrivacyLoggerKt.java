package com.android.systemui.privacy.logging;

import android.icu.text.SimpleDateFormat;
import java.util.Locale;

public abstract class PrivacyLoggerKt {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
}
