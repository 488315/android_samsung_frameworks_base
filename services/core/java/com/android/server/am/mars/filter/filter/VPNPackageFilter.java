package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.util.ArrayMap;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class VPNPackageFilter implements IFilter {
  public ArrayMap mVpnPkgs;

  public abstract class VPNPackageFilterHolder {
    public static final VPNPackageFilter INSTANCE = new VPNPackageFilter();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {}

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {}

  public VPNPackageFilter() {
    this.mVpnPkgs = new ArrayMap();
  }

  public static VPNPackageFilter getInstance() {
    return VPNPackageFilterHolder.INSTANCE;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    synchronized (this.mVpnPkgs) {
      ArrayList arrayList = (ArrayList) this.mVpnPkgs.get(Integer.valueOf(i));
      return (arrayList == null || !arrayList.contains(str)) ? 0 : 6;
    }
  }

  public void onVpnPkgBinded(String str, Integer num) {
    synchronized (this.mVpnPkgs) {
      ArrayList arrayList = (ArrayList) this.mVpnPkgs.get(num);
      if (arrayList == null) {
        arrayList = new ArrayList();
        this.mVpnPkgs.put(num, arrayList);
      }
      arrayList.add(str);
    }
  }

  public void onVpnPkgUnBinded(String str, Integer num) {
    synchronized (this.mVpnPkgs) {
      ArrayList arrayList = (ArrayList) this.mVpnPkgs.get(num);
      if (arrayList != null && arrayList.contains(str)) {
        arrayList.remove(str);
        this.mVpnPkgs.put(num, arrayList);
      }
    }
  }
}
