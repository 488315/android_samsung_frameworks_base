package com.samsung.android.knox.zt.config.securelog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ConfidenceScore {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ConfigSignalType {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PhishingConfig {
    }
}
