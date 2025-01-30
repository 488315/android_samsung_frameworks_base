package com.android.server;

import android.content.Context;
import android.os.IBinder;
import android.os.ISpqrService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

/* loaded from: classes.dex */
public class SpqrService extends ProfileService {
  public volatile ISpqrService mService;

  public SpqrService(Context context) {
    super(context, "SpqrService", "sqpr_service");
    this.packageBlockList = initPackageBlockList("/system/etc/spqr-package-blocklist.conf");
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    lambda$selectSuitableProfileService$0();
  }

  @Override // com.android.server.ProfileService
  public IBinder getBinderOfService() {
    return ServiceManager.getService("spqr_service");
  }

  @Override // com.android.server.ProfileService
  public void setInterfaceOfService(IBinder iBinder) {
    if (iBinder != null) {
      this.mService = ISpqrService.Stub.asInterface(iBinder);
    }
  }

  @Override // com.android.server.ProfileService
  public void initializeInterfaceOfService() {
    this.mService = null;
  }

  @Override // com.android.server.ProfileService
  public boolean isServiceRunning() {
    if (this.mService != null) {
      return true;
    }
    Slog.w(this.TAG, "spqr_service is not running");
    return false;
  }

  @Override // com.android.server.ProfileService
  public boolean checkAppId(int i) {
    if (i >= 0) {
      return true;
    }
    Slog.w(this.TAG, "Invalid app id: " + i);
    return false;
  }

  public boolean createInvariantProfileLI(
      String str, String str2, int i, int i2, String str3, String str4, String str5, String str6) {
    if (!checkUserAndService(i, i2)) {
      return false;
    }
    try {
    } catch (RemoteException | NullPointerException e) {
      Slog.e(this.TAG, "Failed to create invariant profile: " + e.getMessage());
    }
    if (this.mService.createInvariantProfile(str, str2, i, i2, str3, str4, str5, str6)) {
      return true;
    }
    Slog.e(this.TAG, "Failed to prepare new profile for " + str3);
    return false;
  }
}
