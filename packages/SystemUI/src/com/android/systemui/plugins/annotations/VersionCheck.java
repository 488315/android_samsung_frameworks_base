package com.android.systemui.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes2.dex */
public @interface VersionCheck {
    boolean defBoolean() default false;

    float defFloat() default 0.0f;

    int defInt() default 0;

    long defLong() default 0;

    String defString() default "";

    int version();
}
