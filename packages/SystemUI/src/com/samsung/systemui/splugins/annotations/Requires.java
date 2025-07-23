package com.samsung.systemui.splugins.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Repeatable(Requirements.class)
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes4.dex */
public @interface Requires {
    Class<?> target();

    int version();
}
