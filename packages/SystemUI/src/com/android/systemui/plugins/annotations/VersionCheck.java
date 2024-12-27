package com.android.systemui.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionCheck {
    boolean defBoolean() default false;

    float defFloat() default 0.0f;

    int defInt() default 0;

    long defLong() default 0;

    String defString() default "";

    int version();
}
