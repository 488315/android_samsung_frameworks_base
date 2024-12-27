package com.android.server.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes2.dex */
public @interface Watched {
    boolean manual() default false;
}
