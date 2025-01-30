package com.android.server.smartspace;

import android.app.AppGlobals;
import android.app.smartspace.ISmartspaceCallback;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceSessionId;
import android.app.smartspace.SmartspaceTargetEvent;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.service.smartspace.ISmartspaceService;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.infra.AbstractRemoteService;
import com.android.server.infra.AbstractPerUserSystemService;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class SmartspacePerUserService extends AbstractPerUserSystemService
    implements RemoteSmartspaceService.RemoteSmartspaceServiceCallbacks {
  public static final String TAG = SmartspacePerUserService.class.getSimpleName();
  public RemoteSmartspaceService mRemoteService;
  public final ArrayMap mSessionInfos;
  public boolean mZombie;

  public SmartspacePerUserService(
      SmartspaceManagerService smartspaceManagerService, Object obj, int i) {
    super(smartspaceManagerService, obj, i);
    this.mSessionInfos = new ArrayMap();
  }

  @Override // com.android.server.infra.AbstractPerUserSystemService
  public ServiceInfo newServiceInfoLocked(ComponentName componentName) {
    try {
      return AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
    } catch (RemoteException unused) {
      throw new PackageManager.NameNotFoundException("Could not get service for " + componentName);
    }
  }

  @Override // com.android.server.infra.AbstractPerUserSystemService
  public boolean updateLocked(boolean z) {
    boolean updateLocked = super.updateLocked(z);
    if (updateLocked) {
      if (isEnabledLocked()) {
        resurrectSessionsLocked();
      } else {
        updateRemoteServiceLocked();
      }
    }
    return updateLocked;
  }

  public void onCreateSmartspaceSessionLocked(
      final SmartspaceConfig smartspaceConfig,
      final SmartspaceSessionId smartspaceSessionId,
      IBinder iBinder) {
    if (!resolveService(
            smartspaceSessionId,
            new AbstractRemoteService
                .AsyncRequest() { // from class:
                                  // com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda1
              public final void run(IInterface iInterface) {
                ((ISmartspaceService) iInterface)
                    .onCreateSmartspaceSession(smartspaceConfig, smartspaceSessionId);
              }
            })
        || this.mSessionInfos.containsKey(smartspaceSessionId)) {
      return;
    }
    SmartspaceSessionInfo smartspaceSessionInfo =
        new SmartspaceSessionInfo(
            smartspaceSessionId,
            smartspaceConfig,
            iBinder,
            new IBinder
                .DeathRecipient() { // from class:
                                    // com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda2
              @Override // android.os.IBinder.DeathRecipient
              public final void binderDied() {
                SmartspacePerUserService.this.lambda$onCreateSmartspaceSessionLocked$1(
                    smartspaceSessionId);
              }
            });
    if (smartspaceSessionInfo.linkToDeath()) {
      this.mSessionInfos.put(smartspaceSessionId, smartspaceSessionInfo);
    } else {
      onDestroyLocked(smartspaceSessionId);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onCreateSmartspaceSessionLocked$1(
      SmartspaceSessionId smartspaceSessionId) {
    synchronized (this.mLock) {
      onDestroyLocked(smartspaceSessionId);
    }
  }

  public void notifySmartspaceEventLocked(
      final SmartspaceSessionId smartspaceSessionId,
      final SmartspaceTargetEvent smartspaceTargetEvent) {
    if (((SmartspaceSessionInfo) this.mSessionInfos.get(smartspaceSessionId)) == null) {
      return;
    }
    resolveService(
        smartspaceSessionId,
        new AbstractRemoteService
            .AsyncRequest() { // from class:
                              // com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda4
          public final void run(IInterface iInterface) {
            ((ISmartspaceService) iInterface)
                .notifySmartspaceEvent(smartspaceSessionId, smartspaceTargetEvent);
          }
        });
  }

  public void requestSmartspaceUpdateLocked(final SmartspaceSessionId smartspaceSessionId) {
    if (((SmartspaceSessionInfo) this.mSessionInfos.get(smartspaceSessionId)) == null) {
      return;
    }
    resolveService(
        smartspaceSessionId,
        new AbstractRemoteService
            .AsyncRequest() { // from class:
                              // com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda0
          public final void run(IInterface iInterface) {
            ((ISmartspaceService) iInterface).requestSmartspaceUpdate(smartspaceSessionId);
          }
        });
  }

  public void registerSmartspaceUpdatesLocked(
      final SmartspaceSessionId smartspaceSessionId,
      final ISmartspaceCallback iSmartspaceCallback) {
    SmartspaceSessionInfo smartspaceSessionInfo =
        (SmartspaceSessionInfo) this.mSessionInfos.get(smartspaceSessionId);
    if (smartspaceSessionInfo != null
        && resolveService(
            smartspaceSessionId,
            new AbstractRemoteService
                .AsyncRequest() { // from class:
                                  // com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda6
              public final void run(IInterface iInterface) {
                ((ISmartspaceService) iInterface)
                    .registerSmartspaceUpdates(smartspaceSessionId, iSmartspaceCallback);
              }
            })) {
      smartspaceSessionInfo.addCallbackLocked(iSmartspaceCallback);
    }
  }

  public void unregisterSmartspaceUpdatesLocked(
      final SmartspaceSessionId smartspaceSessionId,
      final ISmartspaceCallback iSmartspaceCallback) {
    SmartspaceSessionInfo smartspaceSessionInfo =
        (SmartspaceSessionInfo) this.mSessionInfos.get(smartspaceSessionId);
    if (smartspaceSessionInfo != null
        && resolveService(
            smartspaceSessionId,
            new AbstractRemoteService
                .AsyncRequest() { // from class:
                                  // com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda5
              public final void run(IInterface iInterface) {
                ((ISmartspaceService) iInterface)
                    .unregisterSmartspaceUpdates(smartspaceSessionId, iSmartspaceCallback);
              }
            })) {
      smartspaceSessionInfo.removeCallbackLocked(iSmartspaceCallback);
    }
  }

  public void onDestroyLocked(final SmartspaceSessionId smartspaceSessionId) {
    if (isDebug()) {
      Slog.d(TAG, "onDestroyLocked(): sessionId=" + smartspaceSessionId);
    }
    SmartspaceSessionInfo smartspaceSessionInfo =
        (SmartspaceSessionInfo) this.mSessionInfos.remove(smartspaceSessionId);
    if (smartspaceSessionInfo == null) {
      return;
    }
    resolveService(
        smartspaceSessionId,
        new AbstractRemoteService
            .AsyncRequest() { // from class:
                              // com.android.server.smartspace.SmartspacePerUserService$$ExternalSyntheticLambda3
          public final void run(IInterface iInterface) {
            ((ISmartspaceService) iInterface).onDestroySmartspaceSession(smartspaceSessionId);
          }
        });
    smartspaceSessionInfo.destroy();
  }

  @Override // com.android.server.smartspace.RemoteSmartspaceService.RemoteSmartspaceServiceCallbacks
  public void onConnectedStateChanged(boolean z) {
    if (isDebug()) {
      Slog.d(TAG, "onConnectedStateChanged(): connected=" + z);
    }
    if (z) {
      synchronized (this.mLock) {
        if (this.mZombie) {
          if (this.mRemoteService == null) {
            Slog.w(TAG, "Cannot resurrect sessions because remote service is null");
          } else {
            this.mZombie = false;
            resurrectSessionsLocked();
          }
        }
      }
    }
  }

  public void onServiceDied(RemoteSmartspaceService remoteSmartspaceService) {
    if (isDebug()) {
      Slog.w(TAG, "onServiceDied(): service=" + remoteSmartspaceService);
    }
    synchronized (this.mLock) {
      this.mZombie = true;
    }
    updateRemoteServiceLocked();
  }

  public final void updateRemoteServiceLocked() {
    RemoteSmartspaceService remoteSmartspaceService = this.mRemoteService;
    if (remoteSmartspaceService != null) {
      remoteSmartspaceService.destroy();
      this.mRemoteService = null;
    }
  }

  public void onPackageUpdatedLocked() {
    if (isDebug()) {
      Slog.v(TAG, "onPackageUpdatedLocked()");
    }
    destroyAndRebindRemoteService();
  }

  public void onPackageRestartedLocked() {
    if (isDebug()) {
      Slog.v(TAG, "onPackageRestartedLocked()");
    }
    destroyAndRebindRemoteService();
  }

  public final void destroyAndRebindRemoteService() {
    if (this.mRemoteService == null) {
      return;
    }
    if (isDebug()) {
      Slog.d(TAG, "Destroying the old remote service.");
    }
    this.mRemoteService.destroy();
    this.mRemoteService = null;
    synchronized (this.mLock) {
      this.mZombie = true;
    }
    RemoteSmartspaceService remoteServiceLocked = getRemoteServiceLocked();
    this.mRemoteService = remoteServiceLocked;
    if (remoteServiceLocked != null) {
      if (isDebug()) {
        Slog.d(TAG, "Rebinding to the new remote service.");
      }
      this.mRemoteService.reconnect();
    }
  }

  public final void resurrectSessionsLocked() {
    int size = this.mSessionInfos.size();
    if (isDebug()) {
      Slog.d(
          TAG,
          "Resurrecting remote service (" + this.mRemoteService + ") on " + size + " sessions.");
    }
    for (SmartspaceSessionInfo smartspaceSessionInfo : this.mSessionInfos.values()) {
      smartspaceSessionInfo.resurrectSessionLocked(this, smartspaceSessionInfo.mToken);
    }
  }

  public boolean resolveService(
      SmartspaceSessionId smartspaceSessionId, AbstractRemoteService.AsyncRequest asyncRequest) {
    RemoteSmartspaceService remoteServiceLocked = getRemoteServiceLocked();
    if (remoteServiceLocked != null) {
      remoteServiceLocked.executeOnResolvedService(asyncRequest);
    }
    return remoteServiceLocked != null;
  }

  public final RemoteSmartspaceService getRemoteServiceLocked() {
    if (this.mRemoteService == null) {
      String componentNameLocked = getComponentNameLocked();
      if (componentNameLocked == null) {
        if (!((SmartspaceManagerService) this.mMaster).verbose) {
          return null;
        }
        Slog.v(TAG, "getRemoteServiceLocked(): not set");
        return null;
      }
      this.mRemoteService =
          new RemoteSmartspaceService(
              getContext(),
              "android.service.smartspace.SmartspaceService",
              ComponentName.unflattenFromString(componentNameLocked),
              this.mUserId,
              this,
              ((SmartspaceManagerService) this.mMaster).isBindInstantServiceAllowed(),
              ((SmartspaceManagerService) this.mMaster).verbose);
    }
    return this.mRemoteService;
  }

  public final class SmartspaceSessionInfo {
    public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
    public final IBinder.DeathRecipient mDeathRecipient;
    public final SmartspaceSessionId mSessionId;
    public final SmartspaceConfig mSmartspaceConfig;
    public final IBinder mToken;

    public SmartspaceSessionInfo(
        SmartspaceSessionId smartspaceSessionId,
        SmartspaceConfig smartspaceConfig,
        IBinder iBinder,
        IBinder.DeathRecipient deathRecipient) {
      this.mSessionId = smartspaceSessionId;
      this.mSmartspaceConfig = smartspaceConfig;
      this.mToken = iBinder;
      this.mDeathRecipient = deathRecipient;
    }

    public void addCallbackLocked(ISmartspaceCallback iSmartspaceCallback) {
      this.mCallbacks.register(iSmartspaceCallback);
    }

    public void removeCallbackLocked(ISmartspaceCallback iSmartspaceCallback) {
      this.mCallbacks.unregister(iSmartspaceCallback);
    }

    public boolean linkToDeath() {
      try {
        this.mToken.linkToDeath(this.mDeathRecipient, 0);
        return true;
      } catch (RemoteException unused) {
        return false;
      }
    }

    public void destroy() {
      IBinder iBinder = this.mToken;
      if (iBinder != null) {
        iBinder.unlinkToDeath(this.mDeathRecipient, 0);
      }
      this.mCallbacks.kill();
    }

    public void resurrectSessionLocked(
        final SmartspacePerUserService smartspacePerUserService, IBinder iBinder) {
      this.mCallbacks.getRegisteredCallbackCount();
      smartspacePerUserService.onCreateSmartspaceSessionLocked(
          this.mSmartspaceConfig, this.mSessionId, iBinder);
      this.mCallbacks.broadcast(
          new Consumer() { // from class:
                           // com.android.server.smartspace.SmartspacePerUserService$SmartspaceSessionInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              SmartspacePerUserService.SmartspaceSessionInfo.this.lambda$resurrectSessionLocked$0(
                  smartspacePerUserService, (ISmartspaceCallback) obj);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resurrectSessionLocked$0(
        SmartspacePerUserService smartspacePerUserService,
        ISmartspaceCallback iSmartspaceCallback) {
      smartspacePerUserService.registerSmartspaceUpdatesLocked(
          this.mSessionId, iSmartspaceCallback);
    }
  }
}
