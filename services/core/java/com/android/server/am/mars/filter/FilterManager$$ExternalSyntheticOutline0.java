package com.android.server.am.mars.filter;

public abstract /* synthetic */ class FilterManager$$ExternalSyntheticOutline0 {
    public static void m(
            FilterFactory filterFactory,
            int i,
            FilterChainBuilder filterChainBuilder,
            int i2,
            int i3) {
        filterChainBuilder.add(filterFactory.getFilter(i));
        filterChainBuilder.add(filterFactory.getFilter(i2));
        filterChainBuilder.add(filterFactory.getFilter(i3));
    }
}
