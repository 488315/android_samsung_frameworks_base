package com.android.server.am.mars.filter;

/* loaded from: classes.dex */
public class FilterChain {
  public IFilter mFilter;
  public FilterChain nextFilterChain;

  public FilterChain(IFilter iFilter, FilterChain filterChain) {
    this.mFilter = iFilter;
    this.nextFilterChain = filterChain;
  }

  public int filter(String str, int i, int i2, int i3) {
    int filter = this.mFilter.filter(str, i, i2, i3);
    if (filter > 0) {
      return filter;
    }
    if (getNextFilterChain() != null) {
      return getNextFilterChain().filter(str, i, i2, i3);
    }
    return 0;
  }

  public final FilterChain getNextFilterChain() {
    return this.nextFilterChain;
  }
}
