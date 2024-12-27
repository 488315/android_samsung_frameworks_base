package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback;
import android.net.wifi.sharedconnectivity.service.ISharedConnectivityService;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
public class SharedConnectivityManager {
    private static final boolean DEBUG = false;
    private static final String TAG = SharedConnectivityManager.class.getSimpleName();
    private final Context mContext;
    private final String mIntentAction;
    private ISharedConnectivityService mService;
    private ServiceConnection mServiceConnection;
    private final String mServicePackageName;
    private UserManager mUserManager;
    private final Map<SharedConnectivityClientCallback, SharedConnectivityCallbackProxy> mProxyMap =
            new HashMap();
    private final Map<SharedConnectivityClientCallback, SharedConnectivityCallbackProxy>
            mCallbackProxyCache = new HashMap();
    private final Object mProxyDataLock = new Object();
    private final BroadcastReceiver mBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    context.unregisterReceiver(SharedConnectivityManager.this.mBroadcastReceiver);
                    SharedConnectivityManager.this.bind();
                }
            };

    static final class SharedConnectivityCallbackProxy extends ISharedConnectivityCallback.Stub {
        private final SharedConnectivityClientCallback mCallback;
        private final Executor mExecutor;

        SharedConnectivityCallbackProxy(
                Executor executor, SharedConnectivityClientCallback callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onServiceConnected() {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                             // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SharedConnectivityManager.SharedConnectivityCallbackProxy.this
                                            .lambda$onServiceConnected$0();
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        public /* synthetic */ void lambda$onServiceConnected$0() {
            this.mCallback.onServiceConnected();
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworksUpdated(final List<HotspotNetwork> networks) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                             // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SharedConnectivityManager.SharedConnectivityCallbackProxy.this
                                            .lambda$onHotspotNetworksUpdated$1(networks);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        public /* synthetic */ void lambda$onHotspotNetworksUpdated$1(List networks) {
            this.mCallback.onHotspotNetworksUpdated(networks);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworksUpdated(final List<KnownNetwork> networks) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                             // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SharedConnectivityManager.SharedConnectivityCallbackProxy.this
                                            .lambda$onKnownNetworksUpdated$2(networks);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        public /* synthetic */ void lambda$onKnownNetworksUpdated$2(List networks) {
            this.mCallback.onKnownNetworksUpdated(networks);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onSharedConnectivitySettingsChanged(
                final SharedConnectivitySettingsState state) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                             // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SharedConnectivityManager.SharedConnectivityCallbackProxy.this
                                            .lambda$onSharedConnectivitySettingsChanged$3(state);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        public /* synthetic */ void lambda$onSharedConnectivitySettingsChanged$3(
                SharedConnectivitySettingsState state) {
            this.mCallback.onSharedConnectivitySettingsChanged(state);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworkConnectionStatusChanged(
                final HotspotNetworkConnectionStatus status) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                             // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SharedConnectivityManager.SharedConnectivityCallbackProxy.this
                                            .lambda$onHotspotNetworkConnectionStatusChanged$4(
                                                    status);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        public /* synthetic */ void lambda$onHotspotNetworkConnectionStatusChanged$4(
                HotspotNetworkConnectionStatus status) {
            this.mCallback.onHotspotNetworkConnectionStatusChanged(status);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworkConnectionStatusChanged(
                final KnownNetworkConnectionStatus status) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                             // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SharedConnectivityManager.SharedConnectivityCallbackProxy.this
                                            .lambda$onKnownNetworkConnectionStatusChanged$5(status);
                                }
                            });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        public /* synthetic */ void lambda$onKnownNetworkConnectionStatusChanged$5(
                KnownNetworkConnectionStatus status) {
            this.mCallback.onKnownNetworkConnectionStatusChanged(status);
        }
    }

    public static SharedConnectivityManager create(Context context) {
        Resources resources = context.getResources();
        try {
            String servicePackageName =
                    resources.getString(R.string.config_sharedConnectivityServicePackage);
            String serviceIntentAction =
                    resources.getString(R.string.config_sharedConnectivityServiceIntentAction);
            if (!TextUtils.isEmpty(servicePackageName) && !TextUtils.isEmpty(serviceIntentAction)) {
                return new SharedConnectivityManager(
                        context, servicePackageName, serviceIntentAction);
            }
            Log.e(
                    TAG,
                    "To support shared connectivity service on this device, the service's package"
                        + " name and intent action strings must not be empty");
            return null;
        } catch (Resources.NotFoundException e) {
            Log.e(
                    TAG,
                    "To support shared connectivity service on this device, the service's package"
                        + " name and intent action strings must be defined");
            return null;
        }
    }

    public static SharedConnectivityManager create(
            Context context, String servicePackageName, String serviceIntentAction) {
        return new SharedConnectivityManager(context, servicePackageName, serviceIntentAction);
    }

    private SharedConnectivityManager(
            Context context, String servicePackageName, String serviceIntentAction) {
        this.mContext = context;
        this.mServicePackageName = servicePackageName;
        this.mIntentAction = serviceIntentAction;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    /* renamed from: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1, reason: invalid class name */
    class AnonymousClass1 implements ServiceConnection {
        AnonymousClass1() {}

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            SharedConnectivityManager.this.mService =
                    ISharedConnectivityService.Stub.asInterface(service);
            synchronized (SharedConnectivityManager.this.mProxyDataLock) {
                if (!SharedConnectivityManager.this.mCallbackProxyCache.isEmpty()) {
                    SharedConnectivityManager.this
                            .mCallbackProxyCache
                            .keySet()
                            .forEach(
                                    new Consumer() { // from class:
                                                     // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            SharedConnectivityManager.AnonymousClass1.this
                                                    .lambda$onServiceConnected$0(
                                                            (SharedConnectivityClientCallback) obj);
                                        }
                                    });
                    SharedConnectivityManager.this.mCallbackProxyCache.clear();
                }
            }
        }

        public /* synthetic */ void lambda$onServiceConnected$0(
                SharedConnectivityClientCallback callback) {
            SharedConnectivityManager.this.registerCallbackInternal(
                    callback,
                    (SharedConnectivityCallbackProxy)
                            SharedConnectivityManager.this.mCallbackProxyCache.get(callback));
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            SharedConnectivityManager.this.mService = null;
            synchronized (SharedConnectivityManager.this.mProxyDataLock) {
                if (!SharedConnectivityManager.this.mCallbackProxyCache.isEmpty()) {
                    SharedConnectivityManager.this
                            .mCallbackProxyCache
                            .keySet()
                            .forEach(
                                    new Consumer() { // from class:
                                                     // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            ((SharedConnectivityClientCallback) obj)
                                                    .onServiceDisconnected();
                                        }
                                    });
                    SharedConnectivityManager.this.mCallbackProxyCache.clear();
                }
                if (!SharedConnectivityManager.this.mProxyMap.isEmpty()) {
                    SharedConnectivityManager.this
                            .mProxyMap
                            .keySet()
                            .forEach(
                                    new Consumer() { // from class:
                                                     // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            ((SharedConnectivityClientCallback) obj)
                                                    .onServiceDisconnected();
                                        }
                                    });
                    SharedConnectivityManager.this.mProxyMap.clear();
                }
            }
        }
    }

    public void bind() {
        this.mServiceConnection = new AnonymousClass1();
        boolean result =
                this.mContext.bindService(
                        new Intent()
                                .setPackage(this.mServicePackageName)
                                .setAction(this.mIntentAction),
                        this.mServiceConnection,
                        1);
        if (!result) {
            this.mServiceConnection = null;
            if (this.mUserManager != null && !this.mUserManager.isUserUnlocked()) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(Intent.ACTION_USER_UNLOCKED);
                this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
            } else {
                synchronized (this.mProxyDataLock) {
                    if (!this.mCallbackProxyCache.isEmpty()) {
                        this.mCallbackProxyCache
                                .keySet()
                                .forEach(
                                        new Consumer() { // from class:
                                            // android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                ((SharedConnectivityClientCallback) obj)
                                                        .onRegisterCallbackFailed(
                                                                new IllegalStateException(
                                                                        "Failed to bind after user"
                                                                            + " unlock"));
                                            }
                                        });
                        this.mCallbackProxyCache.clear();
                    }
                }
            }
        }
    }

    public BroadcastReceiver getBroadcastReceiver() {
        return this.mBroadcastReceiver;
    }

    public void registerCallbackInternal(
            SharedConnectivityClientCallback callback, SharedConnectivityCallbackProxy proxy) {
        try {
            this.mService.registerCallback(proxy);
            synchronized (this.mProxyDataLock) {
                this.mProxyMap.put(callback, proxy);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in registerCallback", e);
            callback.onRegisterCallbackFailed(e);
        }
    }

    public void setService(IInterface service) {
        this.mService = (ISharedConnectivityService) service;
    }

    public ServiceConnection getServiceConnection() {
        return this.mServiceConnection;
    }

    private void unbind() {
        if (this.mServiceConnection != null) {
            this.mContext.unbindService(this.mServiceConnection);
            this.mServiceConnection = null;
            this.mService = null;
        }
    }

    public void registerCallback(Executor executor, SharedConnectivityClientCallback callback) {
        boolean shouldBind;
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(callback, "callback cannot be null");
        if (this.mProxyMap.containsKey(callback)
                || this.mCallbackProxyCache.containsKey(callback)) {
            Log.e(TAG, "Callback already registered");
            callback.onRegisterCallbackFailed(
                    new IllegalStateException("Callback already registered"));
            return;
        }
        SharedConnectivityCallbackProxy proxy =
                new SharedConnectivityCallbackProxy(executor, callback);
        if (this.mService == null) {
            synchronized (this.mProxyDataLock) {
                shouldBind = this.mCallbackProxyCache.size() == 0;
                this.mCallbackProxyCache.put(callback, proxy);
            }
            if (shouldBind) {
                bind();
                return;
            }
            return;
        }
        registerCallbackInternal(callback, proxy);
    }

    public boolean unregisterCallback(SharedConnectivityClientCallback callback) {
        boolean shouldUnbind;
        boolean shouldUnbind2;
        Objects.requireNonNull(callback, "callback cannot be null");
        if (!this.mProxyMap.containsKey(callback)
                && !this.mCallbackProxyCache.containsKey(callback)) {
            Log.e(TAG, "Callback not found, cannot unregister");
            return false;
        }
        try {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        } catch (IllegalArgumentException e) {
        }
        if (this.mService == null) {
            synchronized (this.mProxyDataLock) {
                this.mCallbackProxyCache.remove(callback);
                shouldUnbind2 = this.mCallbackProxyCache.isEmpty();
            }
            if (shouldUnbind2) {
                unbind();
            }
            return true;
        }
        try {
            synchronized (this.mProxyDataLock) {
                this.mService.unregisterCallback(this.mProxyMap.get(callback));
                this.mProxyMap.remove(callback);
                shouldUnbind = this.mProxyMap.isEmpty();
            }
            if (shouldUnbind) {
                unbind();
            }
            return true;
        } catch (RemoteException e2) {
            Log.e(TAG, "Exception in unregisterCallback", e2);
            return false;
        }
    }

    public boolean connectHotspotNetwork(HotspotNetwork network) {
        Objects.requireNonNull(network, "Hotspot network cannot be null");
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.connectHotspotNetwork(network);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in connectHotspotNetwork", e);
            return false;
        }
    }

    public boolean disconnectHotspotNetwork(HotspotNetwork network) {
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.disconnectHotspotNetwork(network);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in disconnectHotspotNetwork", e);
            return false;
        }
    }

    public boolean connectKnownNetwork(KnownNetwork network) {
        Objects.requireNonNull(network, "Known network cannot be null");
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.connectKnownNetwork(network);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in connectKnownNetwork", e);
            return false;
        }
    }

    public boolean forgetKnownNetwork(KnownNetwork network) {
        Objects.requireNonNull(network, "Known network cannot be null");
        if (this.mService == null) {
            return false;
        }
        try {
            this.mService.forgetKnownNetwork(network);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in forgetKnownNetwork", e);
            return false;
        }
    }

    public List<HotspotNetwork> getHotspotNetworks() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getHotspotNetworks();
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in getHotspotNetworks", e);
            return null;
        }
    }

    public List<KnownNetwork> getKnownNetworks() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getKnownNetworks();
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in getKnownNetworks", e);
            return null;
        }
    }

    public SharedConnectivitySettingsState getSettingsState() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getSettingsState();
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in getSettingsState", e);
            return null;
        }
    }

    public HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getHotspotNetworkConnectionStatus();
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in getHotspotNetworkConnectionStatus", e);
            return null;
        }
    }

    public KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() {
        if (this.mService == null) {
            return null;
        }
        try {
            return this.mService.getKnownNetworkConnectionStatus();
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in getKnownNetworkConnectionStatus", e);
            return null;
        }
    }
}
