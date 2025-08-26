package com.android.systemui.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Repeatable(Container.class)
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes2.dex */
public @interface GeneratedImport {

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.CLASS)
    public @interface Container {
        GeneratedImport[] value();
    }

    String extraImport();
}
