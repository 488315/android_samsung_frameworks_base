package com.android.wm.shell.windowdecor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface CaptionLoggerInfo {
    int behavior() default 0;

    int interaction() default 0;

    boolean isDexMode() default false;

    int windowingMode() default 0;
}
