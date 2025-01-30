package com.samsung.android.sepunion;

import android.content.Context;
import android.content.p002pm.PackageManager;

/* loaded from: classes5.dex */
public class UnionUtils {
  private static final String FEATURE_NFC_AUTHENTICATION = "com.sec.feature.nfc_authentication";
  private static final String FEATURE_USB_AUTHENTICATION = "com.sec.feature.usb_authentication";
  private static UnionUtils sInstance = null;
  public static boolean FEATURE_ENABLED = true;
  private boolean mIsNfcAuthEnabled = false;
  private boolean mIsUsbAuthEnabled = false;
  private boolean mIsCoverSupported = false;

  public static UnionUtils getInstance(Context context) {
    if (sInstance == null) {
      sInstance = new UnionUtils(context);
    }
    return sInstance;
  }

  private UnionUtils(Context context) {
    updateSystemFeature(context);
  }

  private void updateSystemFeature(Context context) {
    PackageManager pm = context.getPackageManager();
    if (pm != null) {
      this.mIsNfcAuthEnabled = pm.hasSystemFeature(FEATURE_NFC_AUTHENTICATION);
      this.mIsUsbAuthEnabled = pm.hasSystemFeature(FEATURE_USB_AUTHENTICATION);
      this.mIsCoverSupported = pm.hasSystemFeature(PackageManager.FEATURE_COVER);
    }
  }

  public boolean isNfcAuthEnabled() {
    return this.mIsNfcAuthEnabled;
  }

  public boolean isUsbAuthEnabled() {
    return this.mIsUsbAuthEnabled;
  }

  public boolean isCoverSupported() {
    return this.mIsCoverSupported;
  }
}
