package com.android.server.am.mars.filter;

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
