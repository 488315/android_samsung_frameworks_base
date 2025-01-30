package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.util.ArrayMap;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class TopPackageFilter implements IFilter {
  public ArrayMap mTopPkg;

  public abstract class TopPackageFilterHolder {
    public static final TopPackageFilter INSTANCE = new TopPackageFilter();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {}

  public final void setContext(Context context) {}

  public TopPackageFilter() {
    this.mTopPkg = new ArrayMap();
  }

  public static TopPackageFilter getInstance() {
    return TopPackageFilterHolder.INSTANCE;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    setContext(context);
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    if (MARsPolicyManager.getInstance().isChinaPolicyEnabled()
        && !MARsPolicyManager.getInstance().getScreenOnState()
        && MARsVersionManager.getInstance().isAdjustRestrictionMatch(5, str, null, null)) {
      return 0;
    }
    if ((i3 == 4 || i3 == 15) && !MARsPolicyManager.getInstance().getScreenOnState()) {
      return 0;
    }
    synchronized (this.mTopPkg) {
      ArrayList arrayList = (ArrayList) this.mTopPkg.get(Integer.valueOf(i));
      return (arrayList == null || !arrayList.contains(str)) ? 0 : 12;
    }
  }

  public void updateTopPkgList(ArrayList arrayList, int i) {
    if (arrayList != null) {
      synchronized (this.mTopPkg) {
        this.mTopPkg.clear();
        this.mTopPkg.put(Integer.valueOf(i), arrayList);
      }
    }
  }

  public boolean isInTopPkgList(String str, int i) {
    synchronized (this.mTopPkg) {
      ArrayList arrayList = (ArrayList) this.mTopPkg.get(Integer.valueOf(i));
      return arrayList != null && arrayList.contains(str);
    }
  }
}
