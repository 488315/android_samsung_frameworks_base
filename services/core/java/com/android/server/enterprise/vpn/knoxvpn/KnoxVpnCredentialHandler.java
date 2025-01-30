package com.android.server.enterprise.vpn.knoxvpn;

import android.content.Context;
import android.os.Binder;
import android.security.LegacyVpnProfileStore;
import android.util.Log;

/* loaded from: classes2.dex */
public class KnoxVpnCredentialHandler {
  public static KnoxVpnCredentialHandler mInstance;
  public Context mContext;

  public KnoxVpnCredentialHandler(Context context) {
    this.mContext = context;
  }

  public static synchronized KnoxVpnCredentialHandler getInstance(Context context) {
    KnoxVpnCredentialHandler knoxVpnCredentialHandler;
    synchronized (KnoxVpnCredentialHandler.class) {
      if (mInstance == null) {
        mInstance = new KnoxVpnCredentialHandler(context);
      }
      knoxVpnCredentialHandler = mInstance;
    }
    return knoxVpnCredentialHandler;
  }

  public boolean storeCredentialsInKeystore(String str, String str2) {
    boolean z;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      byte[] bytes = str2.getBytes();
      if (bytes != null) {
        z = storeCredentials(str, bytes);
        Log.d(
            "KnoxVpnCredentialHandler",
            "storeCredentialsInKeystore status key is "
                + z
                + " for alias "
                + str
                + " for key "
                + str2);
      } else {
        z = false;
      }
      return z;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean storeCredentials(String str, byte[] bArr) {
    if (bArr == null) {
      return false;
    }
    try {
      return LegacyVpnProfileStore.put(str, bArr);
    } catch (Exception e) {
      Log.e(
          "KnoxVpnCredentialHandler",
          "Exception occured while trying to store the info inside keystore");
      e.printStackTrace();
      return false;
    }
  }

  public String retrieveCredentialsFromKeystore(String str) {
    Log.d("KnoxVpnCredentialHandler", "retrieveCredentialsFromKeystore alias retrieved is " + str);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    String str2 = null;
    try {
      try {
        byte[] bArr = LegacyVpnProfileStore.get(str);
        if (bArr != null) {
          str2 = new String(bArr);
        }
      } catch (Exception e) {
        Log.d(
            "KnoxVpnCredentialHandler",
            "Exception occured while trying to retrieve the info from keystore");
        e.printStackTrace();
      }
      Log.d("KnoxVpnCredentialHandler", "retrieveCredentialsFromKeystore key retrieved is " + str2);
      return str2;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void deleteCredentialsFromKeystore(String str) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        LegacyVpnProfileStore.remove(str);
      } catch (Exception e) {
        e.printStackTrace();
        Log.d(
            "KnoxVpnCredentialHandler",
            "Exception occured while trying to delete the info from keystore");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }
}
