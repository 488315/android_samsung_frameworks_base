package com.android.server.am.mars.filter;

import android.content.Context;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.database.MARsVersionManager;

/* loaded from: classes.dex */
public class FilterManager {
  public Context mContext;
  public final FilterChainFactory mFCF;
  public final FilterFactory mFF;

  public abstract class FilterManagerHolder {
    public static final FilterManager INSTANCE = new FilterManager();
  }

  public FilterManager() {
    this.mFF = FilterFactory.getInstance();
    this.mFCF = FilterChainFactory.getInstance();
  }

  public static FilterManager getInstance() {
    return FilterManagerHolder.INSTANCE;
  }

  public void setContext(Context context) {
    this.mContext = context;
  }

  public void init(Context context) {
    setContext(context);
    this.mFF.init(this.mContext);
    this.mFCF.init(this.mContext);
  }

  public void deInit() {
    this.mFF.deInit();
    this.mFCF.deInit();
  }

  public final int filter(int i, String str, int i2, int i3) {
    FilterChain filterChain;
    if ((MARsPolicyManager.getInstance().isChinaPolicyEnabled()
            && i == 1
            && MARsVersionManager.getInstance().isAdjustRestrictionMatch(10, str, null, null))
        || (filterChain = FilterChainFactory.getInstance().getFilterChain(i)) == null) {
      return 0;
    }
    return filterChain.filter(str, i2, i3, i);
  }

  public int filterForSpecificPolicy(int i, String str, int i2, int i3) {
    return filter(i, str, i2, i3);
  }

  public int filterForChimera(String str, int i, int i2) {
    IFilter filter;
    int i3 = 0;
    for (int i4 = 2; i4 < 32; i4++) {
      if (i4 != 8
          && (filter = FilterFactory.getInstance().getFilter(i4)) != null
          && filter.filter(str, i, i2, 17) != 0) {
        i3 |= 1 << (i4 - 1);
      }
    }
    return i3;
  }
}
