package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AllowListFilter implements IFilter {
  public List mCarrierAllowList;
  public Context mContext;
  public ArrayList mDefaultAllowList;

  public abstract class AllowListFilterHolder {
    public static final AllowListFilter INSTANCE = new AllowListFilter();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    return 0;
  }

  public AllowListFilter() {
    this.mContext = null;
    this.mDefaultAllowList = new ArrayList();
    this.mCarrierAllowList = new ArrayList();
  }

  public static AllowListFilter getInstance() {
    return AllowListFilterHolder.INSTANCE;
  }

  public final void setContext(Context context) {
    this.mContext = context;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    setContext(context);
    setCarrierAllowList();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {
    synchronized (this.mDefaultAllowList) {
      this.mDefaultAllowList.clear();
    }
    synchronized (this.mCarrierAllowList) {
      this.mCarrierAllowList.clear();
    }
  }

  public boolean isInDefaultAllowList(String str) {
    synchronized (this.mCarrierAllowList) {
      if (this.mCarrierAllowList.contains(str)) {
        return true;
      }
      synchronized (this.mDefaultAllowList) {
        return this.mDefaultAllowList.contains(str);
      }
    }
  }

  public void setDefaultAllowList(ArrayList arrayList) {
    synchronized (this.mDefaultAllowList) {
      this.mDefaultAllowList.clear();
      this.mDefaultAllowList.addAll(arrayList);
    }
  }

  public void setCarrierAllowList() {
    TelephonyManager telephonyManager =
        (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
    if (telephonyManager != null) {
      List carrierPrivilegedPackagesForAllActiveSubscriptions =
          telephonyManager.getCarrierPrivilegedPackagesForAllActiveSubscriptions();
      synchronized (this.mCarrierAllowList) {
        this.mCarrierAllowList.clear();
        this.mCarrierAllowList.addAll(carrierPrivilegedPackagesForAllActiveSubscriptions);
      }
    }
  }
}
