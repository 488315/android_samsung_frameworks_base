package com.android.server.am.mars.filter.filter;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.ArrayMap;
import com.android.server.am.mars.filter.IFilter;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class DeviceAdminPackageFilter implements IFilter {
  public ArrayMap mActiveAdmins;
  public Context mContext;

  public abstract class DeviceAdminPackageFilterHolder {
    public static final DeviceAdminPackageFilter INSTANCE = new DeviceAdminPackageFilter();
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void deInit() {}

  public DeviceAdminPackageFilter() {
    this.mContext = null;
    this.mActiveAdmins = new ArrayMap();
  }

  public static DeviceAdminPackageFilter getInstance() {
    return DeviceAdminPackageFilterHolder.INSTANCE;
  }

  public final void setContext(Context context) {
    this.mContext = context;
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public void init(Context context) {
    setContext(context);
    getActiveAdminsPackages(context.getUserId());
    SemPersonaManager semPersonaManager =
        (SemPersonaManager) this.mContext.getSystemService("persona");
    if (semPersonaManager != null) {
      int knoxId = semPersonaManager.getKnoxId(2, true);
      if (this.mContext.getUserId() != 0 || knoxId < 150 || knoxId > 160) {
        return;
      }
      getActiveAdminsPackages(knoxId);
    }
  }

  @Override // com.android.server.am.mars.filter.IFilter
  public int filter(String str, int i, int i2, int i3) {
    synchronized (this.mActiveAdmins) {
      ArrayList arrayList = (ArrayList) this.mActiveAdmins.get(Integer.valueOf(i));
      return (arrayList == null || !arrayList.contains(str)) ? 0 : 9;
    }
  }

  public final void getActiveAdminsPackages(int i) {
    synchronized (this.mActiveAdmins) {
      ArrayList arrayList = (ArrayList) this.mActiveAdmins.get(Integer.valueOf(i));
      List<ComponentName> activeAdmins =
          ((DevicePolicyManager) this.mContext.getSystemService("device_policy")).getActiveAdmins();
      if (arrayList == null) {
        arrayList = new ArrayList();
      }
      if (activeAdmins != null) {
        Iterator<ComponentName> it = activeAdmins.iterator();
        while (it.hasNext()) {
          String packageName = it.next().getPackageName();
          if (!arrayList.contains(packageName)) {
            arrayList.add(packageName);
            this.mActiveAdmins.put(Integer.valueOf(i), arrayList);
          }
        }
      }
    }
  }

  public void onDeviceAdminEnabled(String str, int i) {
    synchronized (this.mActiveAdmins) {
      ArrayList arrayList = (ArrayList) this.mActiveAdmins.get(Integer.valueOf(i));
      if (arrayList == null) {
        arrayList = new ArrayList();
      }
      if (!arrayList.contains(str)) {
        arrayList.add(str);
        this.mActiveAdmins.put(Integer.valueOf(i), arrayList);
      }
    }
  }

  public void onDeviceAdminDisabled(String str, int i) {
    synchronized (this.mActiveAdmins) {
      ArrayList arrayList = (ArrayList) this.mActiveAdmins.get(Integer.valueOf(i));
      if (arrayList != null && arrayList.contains(str)) {
        arrayList.remove(str);
        this.mActiveAdmins.put(Integer.valueOf(i), arrayList);
      }
    }
  }
}
