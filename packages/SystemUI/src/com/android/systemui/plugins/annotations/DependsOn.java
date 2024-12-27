package com.android.systemui.plugins.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@Repeatable(Dependencies.class)
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes2.dex */
public @interface DependsOn {
    Class<?> target();
}
