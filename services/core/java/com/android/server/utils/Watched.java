package com.android.server.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface Watched {
  boolean manual() default false;
}
