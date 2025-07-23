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
import android.net.wifi.sharedconnectivity.service.ISharedConnectivityService;
import android.net.wifi.sharedconnectivity.service.SharedConnectivityService;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.R;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@SystemApi
/* loaded from: classes3.dex */
public abstract class SharedConnectivityService extends Service {
    private static final boolean DEBUG = true;
    private static final String TAG = "SharedConnectivityService";
    private CountDownLatch mCountDownLatch;
    private Handler mHandler;
    private final RemoteCallbackList<ISharedConnectivityCallback> mRemoteCallbackList = new RemoteCallbackList<>();
    private List<HotspotNetwork> mHotspotNetworks = Collections.EMPTY_LIST;
    private List<KnownNetwork> mKnownNetworks = Collections.EMPTY_LIST;
    private SharedConnectivitySettingsState mSettingsState = null;
    private HotspotNetworkConnectionStatus mHotspotNetworkConnectionStatus = null;
    private KnownNetworkConnectionStatus mKnownNetworkConnectionStatus = null;

    public void onBind() {
    }

    public abstract void onConnectHotspotNetwork(HotspotNetwork hotspotNetwork);

    public abstract void onConnectKnownNetwork(KnownNetwork knownNetwork);

    public abstract void onDisconnectHotspotNetwork(HotspotNetwork hotspotNetwork);

    public abstract void onForgetKnownNetwork(KnownNetwork knownNetwork);

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind intent=" + intent);
        this.mHandler = new Handler(getMainLooper());
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        onBind();
        return anonymousClass1;
    }

    /* renamed from: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1, reason: invalid class name */
    class AnonymousClass1 extends ISharedConnectivityService.Stub {
        AnonymousClass1() {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void registerCallback(final ISharedConnectivityCallback iSharedConnectivityCallback) {
            checkPermissions();
            SharedConnectivityService.this.mHandler.post(new Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    SharedConnectivityService.AnonymousClass1.this.lambda$registerCallback$0(iSharedConnectivityCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registerCallback$0(ISharedConnectivityCallback iSharedConnectivityCallback) {
            SharedConnectivityService.this.onRegisterCallback(iSharedConnectivityCallback);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void unregisterCallback(final ISharedConnectivityCallback iSharedConnectivityCallback) {
            checkPermissions();
            SharedConnectivityService.this.mHandler.post(new Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SharedConnectivityService.AnonymousClass1.this.lambda$unregisterCallback$1(iSharedConnectivityCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$unregisterCallback$1(ISharedConnectivityCallback iSharedConnectivityCallback) {
            SharedConnectivityService.this.onUnregisterCallback(iSharedConnectivityCallback);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void connectHotspotNetwork(final HotspotNetwork hotspotNetwork) {
            checkPermissions();
            SharedConnectivityService.this.mHandler.post(new Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SharedConnectivityService.AnonymousClass1.this.lambda$connectHotspotNetwork$2(hotspotNetwork);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$connectHotspotNetwork$2(HotspotNetwork hotspotNetwork) {
            SharedConnectivityService.this.onConnectHotspotNetwork(hotspotNetwork);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void disconnectHotspotNetwork(final HotspotNetwork hotspotNetwork) {
            checkPermissions();
            SharedConnectivityService.this.mHandler.post(new Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SharedConnectivityService.AnonymousClass1.this.lambda$disconnectHotspotNetwork$3(hotspotNetwork);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$disconnectHotspotNetwork$3(HotspotNetwork hotspotNetwork) {
            SharedConnectivityService.this.onDisconnectHotspotNetwork(hotspotNetwork);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void connectKnownNetwork(final KnownNetwork knownNetwork) {
            checkPermissions();
            SharedConnectivityService.this.mHandler.post(new Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    SharedConnectivityService.AnonymousClass1.this.lambda$connectKnownNetwork$4(knownNetwork);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$connectKnownNetwork$4(KnownNetwork knownNetwork) {
            SharedConnectivityService.this.onConnectKnownNetwork(knownNetwork);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void forgetKnownNetwork(final KnownNetwork knownNetwork) {
            checkPermissions();
            SharedConnectivityService.this.mHandler.post(new Runnable() { // from class: android.net.wifi.sharedconnectivity.service.SharedConnectivityService$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    SharedConnectivityService.AnonymousClass1.this.lambda$forgetKnownNetwork$5(knownNetwork);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$forgetKnownNetwork$5(KnownNetwork knownNetwork) {
            SharedConnectivityService.this.onForgetKnownNetwork(knownNetwork);
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
                SharedConnectivityService.this.mSettingsState = new SharedConnectivitySettingsState.Builder().setInstantTetherEnabled(false).setExtras(Bundle.EMPTY).build();
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
            if (SharedConnectivityService.this.checkCallingOrSelfPermission(Manifest.permission.NETWORK_SETTINGS) != 0 && SharedConnectivityService.this.checkCallingOrSelfPermission(Manifest.permission.NETWORK_SETUP_WIZARD) != 0) {
                throw new SecurityException("Calling process must have NETWORK_SETTINGS or NETWORK_SETUP_WIZARD permission");
            }
        }
    }

    public final void setCountdownLatch(CountDownLatch countDownLatch) {
        this.mCountDownLatch = countDownLatch;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRegisterCallback(ISharedConnectivityCallback iSharedConnectivityCallback) {
        this.mRemoteCallbackList.register(iSharedConnectivityCallback);
        try {
            iSharedConnectivityCallback.onServiceConnected();
        } catch (RemoteException e) {
            Log.w(TAG, "Exception in onRegisterCallback", e);
        }
        CountDownLatch countDownLatch = this.mCountDownLatch;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUnregisterCallback(ISharedConnectivityCallback iSharedConnectivityCallback) {
        this.mRemoteCallbackList.unregister(iSharedConnectivityCallback);
        CountDownLatch countDownLatch = this.mCountDownLatch;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public final void setHotspotNetworks(List<HotspotNetwork> list) {
        this.mHotspotNetworks = list;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onHotspotNetworksUpdated(this.mHotspotNetworks);
            } catch (RemoteException e) {
                Log.w(TAG, "Exception in setHotspotNetworks", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public final void setKnownNetworks(List<KnownNetwork> list) {
        this.mKnownNetworks = list;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onKnownNetworksUpdated(this.mKnownNetworks);
            } catch (RemoteException e) {
                Log.w(TAG, "Exception in setKnownNetworks", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public final void setSettingsState(SharedConnectivitySettingsState sharedConnectivitySettingsState) {
        this.mSettingsState = sharedConnectivitySettingsState;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onSharedConnectivitySettingsChanged(this.mSettingsState);
            } catch (RemoteException e) {
                Log.w(TAG, "Exception in setSettingsState", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public final void updateHotspotNetworkConnectionStatus(HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
        this.mHotspotNetworkConnectionStatus = hotspotNetworkConnectionStatus;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onHotspotNetworkConnectionStatusChanged(this.mHotspotNetworkConnectionStatus);
            } catch (RemoteException e) {
                Log.w(TAG, "Exception in updateHotspotNetworkConnectionStatus", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public final void updateKnownNetworkConnectionStatus(KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
        this.mKnownNetworkConnectionStatus = knownNetworkConnectionStatus;
        int beginBroadcast = this.mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mRemoteCallbackList.getBroadcastItem(i).onKnownNetworkConnectionStatusChanged(this.mKnownNetworkConnectionStatus);
            } catch (RemoteException e) {
                Log.w(TAG, "Exception in updateKnownNetworkConnectionStatus", e);
            }
        }
        this.mRemoteCallbackList.finishBroadcast();
    }

    public static boolean areHotspotNetworksEnabledForService(Context context) {
        return Objects.equals(context.getPackageName(), context.getResources().getString(R.string.config_sharedConnectivityServicePackage)) && context.getResources().getBoolean(R.bool.config_hotspotNetworksEnabledForService);
    }

    public static boolean areKnownNetworksEnabledForService(Context context) {
        return Objects.equals(context.getPackageName(), context.getResources().getString(R.string.config_sharedConnectivityServicePackage)) && context.getResources().getBoolean(R.bool.config_knownNetworksEnabledForService);
    }
}
