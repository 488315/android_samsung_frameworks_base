package com.android.wm.shell.windowdecor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface CaptionLoggerInfo {
    int behavior() default 0;

    int interaction() default 0;

    boolean isDexMode() default false;

    int windowingMode() default 0;
}
