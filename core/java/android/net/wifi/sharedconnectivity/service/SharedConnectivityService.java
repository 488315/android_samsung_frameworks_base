package android.net.wifi.sharedconnectivity.service;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;
import android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.RemoteCallbackList;
import android.p009os.RemoteException;
import android.util.Log;
import com.android.internal.C4337R;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@SystemApi
/* loaded from: classes3.dex */
public abstract class SharedConnectivityService extends Service {
  private static final boolean DEBUG = true;
  private static final String TAG = SharedConnectivityService.class.getSimpleName();
  private CountDownLatch mCountDownLatch;
  private Handler mHandler;
  private final RemoteCallbackList<ISharedConnectivityCallback> mRemoteCallbackList =
      new RemoteCallbackList<>();
  private List<HotspotNetwork> mHotspotNetworks = Collections.emptyList();
  private List<KnownNetwork> mKnownNetworks = Collections.emptyList();
  private SharedConnectivitySettingsState mSettingsState = null;
  private HotspotNetworkConnectionStatus mHotspotNetworkConnectionStatus =
      new HotspotNetworkConnectionStatus.Builder().setStatus(0).setExtras(Bundle.EMPTY).build();
  private KnownNetworkConnectionStatus mKnownNetworkConnectionStatus =
      new KnownNetworkConnectionStatus.Builder().setStatus(0).setExtras(Bundle.EMPTY).build();

  public abstract void onConnectHotspotNetwork(HotspotNetwork hotspotNetwork);

  public abstract void onConnectKnownNetwork(KnownNetwork knownNetwork);

  public abstract void onDisconnectHotspotNetwork(HotspotNetwork hotspotNetwork);

  public abstract void onForgetKnownNetwork(KnownNetwork knownNetwork);

  @Override // android.app.Service
  public final IBinder onBind(Intent intent) {
    Log.m98i(TAG, "onBind intent=" + intent);
    this.mHandler = new Handler(getMainLooper());
    IBinder serviceStub = new BinderC22711();
    onBind();
    return serviceStub;
  }

  /* renamed from: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1 */
  class BinderC22711 extends ISharedConnectivityService.Stub {
    BinderC22711() {}

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public void registerCallback(final ISharedConnectivityCallback callback) {
      checkPermissions();
      SharedConnectivityService.this.mHandler.post(
          new Runnable() { // from class:
                           // android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              SharedConnectivityService.BinderC22711.this.lambda$registerCallback$0(callback);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerCallback$0(ISharedConnectivityCallback callback) {
      SharedConnectivityService.this.onRegisterCallback(callback);
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public void unregisterCallback(final ISharedConnectivityCallback callback) {
      checkPermissions();
      SharedConnectivityService.this.mHandler.post(
          new Runnable() { // from class:
                           // android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              SharedConnectivityService.BinderC22711.this.lambda$unregisterCallback$1(callback);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterCallback$1(ISharedConnectivityCallback callback) {
      SharedConnectivityService.this.onUnregisterCallback(callback);
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public void connectHotspotNetwork(final HotspotNetwork network) {
      checkPermissions();
      SharedConnectivityService.this.mHandler.post(
          new Runnable() { // from class:
                           // android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              SharedConnectivityService.BinderC22711.this.lambda$connectHotspotNetwork$2(network);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectHotspotNetwork$2(HotspotNetwork network) {
      SharedConnectivityService.this.onConnectHotspotNetwork(network);
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public void disconnectHotspotNetwork(final HotspotNetwork network) {
      checkPermissions();
      SharedConnectivityService.this.mHandler.post(
          new Runnable() { // from class:
                           // android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              SharedConnectivityService.BinderC22711.this.lambda$disconnectHotspotNetwork$3(
                  network);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnectHotspotNetwork$3(HotspotNetwork network) {
      SharedConnectivityService.this.onDisconnectHotspotNetwork(network);
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public void connectKnownNetwork(final KnownNetwork network) {
      checkPermissions();
      SharedConnectivityService.this.mHandler.post(
          new Runnable() { // from class:
                           // android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              SharedConnectivityService.BinderC22711.this.lambda$connectKnownNetwork$4(network);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectKnownNetwork$4(KnownNetwork network) {
      SharedConnectivityService.this.onConnectKnownNetwork(network);
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public void forgetKnownNetwork(final KnownNetwork network) {
      checkPermissions();
      SharedConnectivityService.this.mHandler.post(
          new Runnable() { // from class:
                           // android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              SharedConnectivityService.BinderC22711.this.lambda$forgetKnownNetwork$5(network);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forgetKnownNetwork$5(KnownNetwork network) {
      SharedConnectivityService.this.onForgetKnownNetwork(network);
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public List<HotspotNetwork> getHotspotNetworks() {
      checkPermissions();
      return SharedConnectivityService.this.mHotspotNetworks;
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public List<KnownNetwork> getKnownNetworks() {
      checkPermissions();
      return SharedConnectivityService.this.mKnownNetworks;
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public SharedConnectivitySettingsState getSettingsState() {
      checkPermissions();
      if (SharedConnectivityService.this.mSettingsState == null) {
        SharedConnectivityService.this.mSettingsState =
            new SharedConnectivitySettingsState.Builder()
                .setInstantTetherEnabled(false)
                .setExtras(Bundle.EMPTY)
                .build();
      }
      return SharedConnectivityService.this.mSettingsState;
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() {
      checkPermissions();
      return SharedConnectivityService.this.mHotspotNetworkConnectionStatus;
    }

    @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
    public KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() {
      checkPermissions();
      return SharedConnectivityService.this.mKnownNetworkConnectionStatus;
    }

    private void checkPermissions() {
      if (SharedConnectivityService.this.checkCallingOrSelfPermission(
                  Manifest.permission.NETWORK_SETTINGS)
              != 0
          && SharedConnectivityService.this.checkCallingOrSelfPermission(
                  Manifest.permission.NETWORK_SETUP_WIZARD)
              != 0) {
        throw new SecurityException(
            "Calling process must have NETWORK_SETTINGS or NETWORK_SETUP_WIZARD permission");
      }
    }
  }

  public void onBind() {}

  public final void setCountdownLatch(CountDownLatch latch) {
    this.mCountDownLatch = latch;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void onRegisterCallback(ISharedConnectivityCallback callback) {
    this.mRemoteCallbackList.register(callback);
    CountDownLatch countDownLatch = this.mCountDownLatch;
    if (countDownLatch != null) {
      countDownLatch.countDown();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void onUnregisterCallback(ISharedConnectivityCallback callback) {
    this.mRemoteCallbackList.unregister(callback);
    CountDownLatch countDownLatch = this.mCountDownLatch;
    if (countDownLatch != null) {
      countDownLatch.countDown();
    }
  }

  public final void setHotspotNetworks(List<HotspotNetwork> networks) {
    this.mHotspotNetworks = networks;
    int count = this.mRemoteCallbackList.beginBroadcast();
    for (int i = 0; i < count; i++) {
      try {
        this.mRemoteCallbackList
            .getBroadcastItem(i)
            .onHotspotNetworksUpdated(this.mHotspotNetworks);
      } catch (RemoteException e) {
        Log.m103w(TAG, "Exception in setHotspotNetworks", e);
      }
    }
    this.mRemoteCallbackList.finishBroadcast();
  }

  public final void setKnownNetworks(List<KnownNetwork> networks) {
    this.mKnownNetworks = networks;
    int count = this.mRemoteCallbackList.beginBroadcast();
    for (int i = 0; i < count; i++) {
      try {
        this.mRemoteCallbackList.getBroadcastItem(i).onKnownNetworksUpdated(this.mKnownNetworks);
      } catch (RemoteException e) {
        Log.m103w(TAG, "Exception in setKnownNetworks", e);
      }
    }
    this.mRemoteCallbackList.finishBroadcast();
  }

  public final void setSettingsState(SharedConnectivitySettingsState settingsState) {
    this.mSettingsState = settingsState;
    int count = this.mRemoteCallbackList.beginBroadcast();
    for (int i = 0; i < count; i++) {
      try {
        this.mRemoteCallbackList
            .getBroadcastItem(i)
            .onSharedConnectivitySettingsChanged(this.mSettingsState);
      } catch (RemoteException e) {
        Log.m103w(TAG, "Exception in setSettingsState", e);
      }
    }
    this.mRemoteCallbackList.finishBroadcast();
  }

  public final void updateHotspotNetworkConnectionStatus(HotspotNetworkConnectionStatus status) {
    this.mHotspotNetworkConnectionStatus = status;
    int count = this.mRemoteCallbackList.beginBroadcast();
    for (int i = 0; i < count; i++) {
      try {
        this.mRemoteCallbackList
            .getBroadcastItem(i)
            .onHotspotNetworkConnectionStatusChanged(this.mHotspotNetworkConnectionStatus);
      } catch (RemoteException e) {
        Log.m103w(TAG, "Exception in updateHotspotNetworkConnectionStatus", e);
      }
    }
    this.mRemoteCallbackList.finishBroadcast();
  }

  public final void updateKnownNetworkConnectionStatus(KnownNetworkConnectionStatus status) {
    this.mKnownNetworkConnectionStatus = status;
    int count = this.mRemoteCallbackList.beginBroadcast();
    for (int i = 0; i < count; i++) {
      try {
        this.mRemoteCallbackList
            .getBroadcastItem(i)
            .onKnownNetworkConnectionStatusChanged(this.mKnownNetworkConnectionStatus);
      } catch (RemoteException e) {
        Log.m103w(TAG, "Exception in updateKnownNetworkConnectionStatus", e);
      }
    }
    this.mRemoteCallbackList.finishBroadcast();
  }

  public static boolean areHotspotNetworksEnabledForService(Context context) {
    String servicePackage =
        context.getResources().getString(C4337R.string.config_sharedConnectivityServicePackage);
    return Objects.equals(context.getPackageName(), servicePackage)
        && context.getResources().getBoolean(C4337R.bool.config_hotspotNetworksEnabledForService);
  }

  public static boolean areKnownNetworksEnabledForService(Context context) {
    String servicePackage =
        context.getResources().getString(C4337R.string.config_sharedConnectivityServicePackage);
    return Objects.equals(context.getPackageName(), servicePackage)
        && context.getResources().getBoolean(C4337R.bool.config_knownNetworksEnabledForService);
  }
}
