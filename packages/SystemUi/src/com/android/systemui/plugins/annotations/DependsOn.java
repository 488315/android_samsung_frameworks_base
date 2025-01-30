package com.android.systemui.plugins.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Repeatable(Dependencies.class)
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes2.dex */
public @interface DependsOn {
    Class<?> target();
}
