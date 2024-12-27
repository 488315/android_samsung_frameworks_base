package com.samsung.android.sume.core.filter;

public interface ContentFilterRegistry {
    void addFilter(int i, Object obj);

    <R> R getFilter(int i);
}
