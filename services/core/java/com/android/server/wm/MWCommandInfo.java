package com.android.server.wm;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface MWCommandInfo {
  String cmd() default "";

  boolean supportsReleaseBuild() default false;
}
