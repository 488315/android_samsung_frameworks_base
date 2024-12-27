package com.android.server.am.mars.filter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
