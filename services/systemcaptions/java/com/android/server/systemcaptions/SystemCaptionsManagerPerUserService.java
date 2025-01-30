package com.android.server.systemcaptions;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.infra.AbstractPerUserSystemService;

/* loaded from: classes3.dex */
public final class SystemCaptionsManagerPerUserService extends AbstractPerUserSystemService {
  public static final String TAG = "SystemCaptionsManagerPerUserService";
  public RemoteSystemCaptionsManagerService mRemoteService;

  public SystemCaptionsManagerPerUserService(
      SystemCaptionsManagerService systemCaptionsManagerService, Object obj, boolean z, int i) {
    super(systemCaptionsManagerService, obj, i);
  }

  @Override // com.android.server.infra.AbstractPerUserSystemService
  public ServiceInfo newServiceInfoLocked(ComponentName componentName) {
    try {
      return AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
    } catch (RemoteException unused) {
      throw new PackageManager.NameNotFoundException("Could not get service for " + componentName);
    }
  }

  public void initializeLocked() {
    if (((SystemCaptionsManagerService) this.mMaster).verbose) {
      Slog.v(TAG, "initialize()");
    }
    if (getRemoteServiceLocked() == null && ((SystemCaptionsManagerService) this.mMaster).verbose) {
      Slog.v(TAG, "initialize(): Failed to init remote server");
    }
  }

  public void destroyLocked() {
    if (((SystemCaptionsManagerService) this.mMaster).verbose) {
      Slog.v(TAG, "destroyLocked()");
    }
    RemoteSystemCaptionsManagerService remoteSystemCaptionsManagerService = this.mRemoteService;
    if (remoteSystemCaptionsManagerService != null) {
      remoteSystemCaptionsManagerService.destroy();
      this.mRemoteService = null;
    }
  }

  public final RemoteSystemCaptionsManagerService getRemoteServiceLocked() {
    if (this.mRemoteService == null) {
      String componentNameLocked = getComponentNameLocked();
      if (componentNameLocked == null) {
        if (!((SystemCaptionsManagerService) this.mMaster).verbose) {
          return null;
        }
        Slog.v(TAG, "getRemoteServiceLocked(): Not set");
        return null;
      }
      this.mRemoteService =
          new RemoteSystemCaptionsManagerService(
              getContext(),
              ComponentName.unflattenFromString(componentNameLocked),
              this.mUserId,
              ((SystemCaptionsManagerService) this.mMaster).verbose);
      if (((SystemCaptionsManagerService) this.mMaster).verbose) {
        Slog.v(TAG, "getRemoteServiceLocked(): initialize for user " + this.mUserId);
      }
      this.mRemoteService.initialize();
    }
    return this.mRemoteService;
  }
}
