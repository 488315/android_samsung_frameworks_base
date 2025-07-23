package com.android.systemui.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Target({ElementType.TYPE})
@Repeatable(Container.class)
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes2.dex */
public @interface GeneratedImport {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.CLASS)
    public @interface Container {
        GeneratedImport[] value();
    }

    String extraImport();
}
