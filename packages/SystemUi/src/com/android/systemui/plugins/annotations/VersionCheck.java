package com.android.systemui.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
