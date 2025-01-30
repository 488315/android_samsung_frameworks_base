package com.samsung.android.sume.core.filter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface ContentFilterRegister {
    public static final int FILTER_DATA_TYPE = 2;
    public static final int FILTER_DIMENSION = 1;
    public static final int FILTER_MEDIA_TYPE = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FilterType {
    }

    void registerFilter(ContentFilterRegistry contentFilterRegistry);
}
