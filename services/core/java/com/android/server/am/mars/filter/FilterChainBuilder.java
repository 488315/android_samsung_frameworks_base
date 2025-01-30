package com.android.server.am.mars.filter;

/* loaded from: classes.dex */
public class FilterChainBuilder {
  public FilterChain filterChain = null;

  public FilterChainBuilder add(IFilter iFilter) {
    this.filterChain = new FilterChain(iFilter, this.filterChain);
    return this;
  }

  public FilterChain build() {
    return this.filterChain;
  }
}
