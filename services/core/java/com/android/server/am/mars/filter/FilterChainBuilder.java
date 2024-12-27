package com.android.server.am.mars.filter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FilterChainBuilder {
    public FilterChain filterChain = null;

    public final void add(IFilter iFilter) {
        FilterChain filterChain = this.filterChain;
        FilterChain filterChain2 = new FilterChain();
        filterChain2.mFilter = iFilter;
        filterChain2.nextFilterChain = filterChain;
        this.filterChain = filterChain2;
    }
}
