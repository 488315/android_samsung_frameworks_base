package com.android.server.am.mars.filter.filter;

import android.content.Context;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;

/* loaded from: classes.dex */
public class NoAppIconFilter implements IFilter {
  public static String TAG = "MARs:" + NoAppIconFilter.class.getSimpleName();
  public Context context;

  public abstract class NoAppIconFilterHolder {
    public static final NoAppIconFilter INSTANCE = new NoAppIconFilter();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {}

  public NoAppIconFilter() {
    this.context = null;
  }

  public static NoAppIconFilter getInstance() {
    return NoAppIconFilterHolder.INSTANCE;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    this.context = context;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    if (MARsPolicyManager.getInstance().getHasAppIcon(str, i)) {
      return 0;
    }
    return (!MARsPolicyManager.getInstance().isFirstTimeTriggerAutorun()
            || str.startsWith("com.sec.")
            || str.startsWith("com.samsung.")
            || str.startsWith("com.sds."))
        ? 5
        : 0;
  }
}
