package com.android.server.pm;

import android.R;
import android.content.Context;
import android.util.ArraySet;
import android.util.SparseArray;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public class ProtectedPackages {
  public final Context mContext;
  public String mDeviceOwnerPackage;
  public int mDeviceOwnerUserId;
  public final String mDeviceProvisioningPackage;
  public final SparseArray mOwnerProtectedPackages = new SparseArray();
  public SparseArray mProfileOwnerPackages;

  public ProtectedPackages(Context context) {
    this.mContext = context;
    this.mDeviceProvisioningPackage =
        context.getResources().getString(R.string.default_notification_channel_label);
  }

  public synchronized void setDeviceAndProfileOwnerPackages(
      int i, String str, SparseArray sparseArray) {
    this.mDeviceOwnerUserId = i;
    SparseArray sparseArray2 = null;
    if (i == -10000) {
      str = null;
    }
    this.mDeviceOwnerPackage = str;
    if (sparseArray != null) {
      sparseArray2 = sparseArray.clone();
    }
    this.mProfileOwnerPackages = sparseArray2;
  }

  public synchronized void setOwnerProtectedPackages(int i, List list) {
    if (list == null) {
      this.mOwnerProtectedPackages.remove(i);
    } else {
      this.mOwnerProtectedPackages.put(i, new ArraySet(list));
    }
  }

  public final synchronized boolean hasDeviceOwnerOrProfileOwner(int i, String str) {
    if (str == null) {
      return false;
    }
    String str2 = this.mDeviceOwnerPackage;
    if (str2 != null && this.mDeviceOwnerUserId == i && str.equals(str2)) {
      return true;
    }
    SparseArray sparseArray = this.mProfileOwnerPackages;
    if (sparseArray != null) {
      if (str.equals(sparseArray.get(i))) {
        return true;
      }
    }
    return false;
  }

  public synchronized String getDeviceOwnerOrProfileOwnerPackage(int i) {
    if (this.mDeviceOwnerUserId == i) {
      return this.mDeviceOwnerPackage;
    }
    SparseArray sparseArray = this.mProfileOwnerPackages;
    if (sparseArray == null) {
      return null;
    }
    return (String) sparseArray.get(i);
  }

  /* JADX WARN: Code restructure failed: missing block: B:11:0x000f, code lost:

     if (isOwnerProtectedPackage(r2, r3) != false) goto L8;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final synchronized boolean isProtectedPackage(int i, String str) {
    boolean z;
    if (str != null) {
      if (!str.equals(this.mDeviceProvisioningPackage)) {}
      z = true;
    }
    z = false;
    return z;
  }

  public final synchronized boolean isOwnerProtectedPackage(int i, String str) {
    boolean isPackageProtectedForUser;
    if (hasProtectedPackages(i)) {
      isPackageProtectedForUser = isPackageProtectedForUser(i, str);
    } else {
      isPackageProtectedForUser = isPackageProtectedForUser(-1, str);
    }
    return isPackageProtectedForUser;
  }

  public final synchronized boolean isPackageProtectedForUser(int i, String str) {
    boolean z;
    int indexOfKey = this.mOwnerProtectedPackages.indexOfKey(i);
    if (indexOfKey >= 0) {
      z = ((Set) this.mOwnerProtectedPackages.valueAt(indexOfKey)).contains(str);
    }
    return z;
  }

  public final synchronized boolean hasProtectedPackages(int i) {
    return this.mOwnerProtectedPackages.indexOfKey(i) >= 0;
  }

  public boolean isPackageStateProtected(int i, String str) {
    return hasDeviceOwnerOrProfileOwner(i, str) || isProtectedPackage(i, str);
  }

  public boolean isPackageDataProtected(int i, String str) {
    return hasDeviceOwnerOrProfileOwner(i, str) || isProtectedPackage(i, str);
  }
}
