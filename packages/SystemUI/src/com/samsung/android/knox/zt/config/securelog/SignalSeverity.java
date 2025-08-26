package com.samsung.android.knox.zt.config.securelog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class SignalSeverity {
    public static final String HIGH = "high";
    public static final String LOW = "low";
    public static final String MED = "med";
    public static final String NONE = "none";

    @Retention(RetentionPolicy.RUNTIME)
    public @interface SeverityLevel {
    }
}
