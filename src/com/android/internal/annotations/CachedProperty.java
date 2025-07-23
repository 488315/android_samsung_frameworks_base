package com.android.internal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes5.dex */
public @interface CachedProperty {
    String api() default "";

    int max() default -1;

    CacheModifier[] mods() default {CacheModifier.STATIC};

    String module() default "";
}
