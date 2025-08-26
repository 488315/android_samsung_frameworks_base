package com.samsung.android.knox.zt.config.securelog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class SecureLogConfig {
    public static final String CONFIG_SIGNAL = "configSignal";
    public static final String ENABLE_SIGNAL = "enableSignal";
    public static final String PUSH_LEVEL = "pushLevel";

    @Retention(RetentionPolicy.RUNTIME)
    public @interface SecureLogConfigType {
    }
}
