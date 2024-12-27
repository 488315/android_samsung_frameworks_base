package com.android.server.wm;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MWCommandInfo {
    String cmd() default "";

    boolean supportsReleaseBuild() default false;
}
