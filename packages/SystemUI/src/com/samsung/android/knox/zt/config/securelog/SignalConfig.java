package com.samsung.android.knox.zt.config.securelog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class SignalConfig {
    public static final String AGGRESSIVE = "Aggressive";
    public static final String ALLOW_LIST = "allowList";
    public static final String BLOCK_LIST = "blockList";
    public static final String CONFIDENCE_SCORE = "confidenceScore";
    public static final String MORE_AGGRESSIVE = "MoreAggressive";
    public static final String MOST_AGGRESSIVE = "MostAggressive";
    public static final String PHISHING = "phishing";
    public static final String STANDARD = "Standard";

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ConfidenceScore {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ConfigSignalType {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface PhishingConfig {
    }
}
