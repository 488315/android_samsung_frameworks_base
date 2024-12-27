package com.android.internal.inputmethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface DirectBootAwareness {
    public static final int ANY = 1;
    public static final int AUTO = 0;
}
