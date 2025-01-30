package com.android.server.enterprise.adapterlayer;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.ServiceManager;
import com.samsung.android.security.IDirEncryptService;
import com.samsung.android.security.SemSdCardEncryption;

/* loaded from: classes2.dex */
public class EncryptionManagerAdapter {
  public static Context mContext;
  public static EncryptionManagerAdapter mInstance;

  public static synchronized EncryptionManagerAdapter getInstance(Context context) {
    EncryptionManagerAdapter encryptionManagerAdapter;
    synchronized (EncryptionManagerAdapter.class) {
      if (mInstance == null) {
        mContext = context;
        mInstance = new EncryptionManagerAdapter();
      }
      encryptionManagerAdapter = mInstance;
    }
    return encryptionManagerAdapter;
  }

  public boolean isEncryptionFeatureEnabled() {
    return SemSdCardEncryption.isEncryptionFeatureEnabled();
  }

  public boolean getRequireStorageCardEncryption() {
    return ((DevicePolicyManager) mContext.getSystemService("device_policy"))
        .semGetRequireStorageCardEncryption(null);
  }

  public int enableStorageCardEncryptionPolicy() {
    return new SemSdCardEncryption(mContext).setSdCardEncryptionPolicy(1, -1, (String) null);
  }

  public int disableStorageCardEncryptionPolicy() {
    return new SemSdCardEncryption(mContext).setSdCardEncryptionPolicy(0, -1, (String) null);
  }

  public boolean isStorageCardEncrypted() {
    if (!SemSdCardEncryption.isEncryptionFeatureEnabled()) {
      return false;
    }
    try {
      IDirEncryptService asInterface =
          IDirEncryptService.Stub.asInterface(ServiceManager.getService("DirEncryptService"));
      if (asInterface != null) {
        return asInterface.isSdCardEncryped();
      }
      return false;
    } catch (Exception unused) {
      return false;
    }
  }
}
