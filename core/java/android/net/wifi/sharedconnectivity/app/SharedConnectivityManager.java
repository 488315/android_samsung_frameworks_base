package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
import android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback;
import android.net.wifi.sharedconnectivity.service.ISharedConnectivityService;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.C4337R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public class SharedConnectivityManager {
    private static final boolean DEBUG = true;
    private static final String TAG = SharedConnectivityManager.class.getSimpleName();
    private final Context mContext;
    private final String mIntentAction;
    private ISharedConnectivityService mService;
    private ServiceConnection mServiceConnection;
    private final String mServicePackageName;
    private final Map<SharedConnectivityClientCallback, SharedConnectivityCallbackProxy> mProxyMap = new HashMap();
    private final Map<SharedConnectivityClientCallback, SharedConnectivityCallbackProxy> mCallbackProxyCache = new HashMap();
    private final Object mProxyDataLock = new Object();

    /* JADX INFO: Access modifiers changed from: private */
    static final class SharedConnectivityCallbackProxy extends ISharedConnectivityCallback.Stub {
        private final SharedConnectivityClientCallback mCallback;
        private final Executor mExecutor;

        SharedConnectivityCallbackProxy(Executor executor, SharedConnectivityClientCallback callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworksUpdated(final List<HotspotNetwork> networks) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onHotspotNetworksUpdated$0(networks);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotspotNetworksUpdated$0(List networks) {
            this.mCallback.onHotspotNetworksUpdated(networks);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworksUpdated(final List<KnownNetwork> networks) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onKnownNetworksUpdated$1(networks);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKnownNetworksUpdated$1(List networks) {
            this.mCallback.onKnownNetworksUpdated(networks);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onSharedConnectivitySettingsChanged(final SharedConnectivitySettingsState state) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onSharedConnectivitySettingsChanged$2(state);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSharedConnectivitySettingsChanged$2(SharedConnectivitySettingsState state) {
            this.mCallback.onSharedConnectivitySettingsChanged(state);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworkConnectionStatusChanged(final HotspotNetworkConnectionStatus status) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onHotspotNetworkConnectionStatusChanged$3(status);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotspotNetworkConnectionStatusChanged$3(HotspotNetworkConnectionStatus status) {
            this.mCallback.onHotspotNetworkConnectionStatusChanged(status);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworkConnectionStatusChanged(final KnownNetworkConnectionStatus status) {
            if (this.mCallback != null) {
                long token = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onKnownNetworkConnectionStatusChanged$4(status);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKnownNetworkConnectionStatusChanged$4(KnownNetworkConnectionStatus status) {
            this.mCallback.onKnownNetworkConnectionStatusChanged(status);
        }
    }

    public static SharedConnectivityManager create(Context context) {
        Resources resources = context.getResources();
        try {
            String servicePackageName = resources.getString(C4337R.string.config_sharedConnectivityServicePackage);
            String serviceIntentAction = resources.getString(C4337R.string.config_sharedConnectivityServiceIntentAction);
            if (!TextUtils.isEmpty(servicePackageName) && !TextUtils.isEmpty(serviceIntentAction)) {
                return new SharedConnectivityManager(context, servicePackageName, serviceIntentAction);
            }
            Log.m96e(TAG, "To support shared connectivity service on this device, the service's package name and intent action strings must not be empty");
            return null;
        } catch (Resources.NotFoundException e) {
            Log.m96e(TAG, "To support shared connectivity service on this device, the service's package name and intent action strings must be defined");
            return null;
        }
    }

    public static SharedConnectivityManager create(Context context, String servicePackageName, String serviceIntentAction) {
        return new SharedConnectivityManager(context, servicePackageName, serviceIntentAction);
    }

    private SharedConnectivityManager(Context context, String servicePackageName, String serviceIntentAction) {
        this.mContext = context;
        this.mServicePackageName = servicePackageName;
        this.mIntentAction = serviceIntentAction;
    }

    /* renamed from: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1 */
    class ServiceConnectionC22691 implements ServiceConnection {
        ServiceConnectionC22691() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            SharedConnectivityManager.this.mService = ISharedConnectivityService.Stub.asInterface(service);
            synchronized (SharedConnectivityManager.this.mProxyDataLock) {
                if (!SharedConnectivityManager.this.mCallbackProxyCache.isEmpty()) {
                    SharedConnectivityManager.this.mCallbackProxyCache.keySet().forEach(new Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SharedConnectivityManager.ServiceConnectionC22691.this.lambda$onServiceConnected$0((SharedConnectivityClientCallback) obj);
                        }
                    });
                    SharedConnectivityManager.this.mCallbackProxyCache.clear();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$0(SharedConnectivityClientCallback callback) {
            SharedConnectivityManager sharedConnectivityManager = SharedConnectivityManager.this;
            sharedConnectivityManager.registerCallbackInternal(callback, (SharedConnectivityCallbackProxy) sharedConnectivityManager.mCallbackProxyCache.get(callback));
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            Log.m98i(SharedConnectivityManager.TAG, "onServiceDisconnected");
            SharedConnectivityManager.this.mService = null;
            synchronized (SharedConnectivityManager.this.mProxyDataLock) {
                if (!SharedConnectivityManager.this.mCallbackProxyCache.isEmpty()) {
                    SharedConnectivityManager.this.mCallbackProxyCache.keySet().forEach(new Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SharedConnectivityClientCallback) obj).onServiceDisconnected();
                        }
                    });
                    SharedConnectivityManager.this.mCallbackProxyCache.clear();
                }
                if (!SharedConnectivityManager.this.mProxyMap.isEmpty()) {
                    SharedConnectivityManager.this.mProxyMap.keySet().forEach(new Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SharedConnectivityClientCallback) obj).onServiceDisconnected();
                        }
                    });
                    SharedConnectivityManager.this.mProxyMap.clear();
                }
            }
        }
    }

    private void bind() {
        this.mServiceConnection = new ServiceConnectionC22691();
        this.mContext.bindService(new Intent().setPackage(this.mServicePackageName).setAction(this.mIntentAction), this.mServiceConnection, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerCallbackInternal(SharedConnectivityClientCallback callback, SharedConnectivityCallbackProxy proxy) {
        try {
            this.mService.registerCallback(proxy);
            synchronized (this.mProxyDataLock) {
                this.mProxyMap.put(callback, proxy);
            }
            callback.onServiceConnected();
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in registerCallback", e);
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
        ServiceConnection serviceConnection = this.mServiceConnection;
        if (serviceConnection != null) {
            this.mContext.unbindService(serviceConnection);
            this.mServiceConnection = null;
        }
    }

    public void registerCallback(Executor executor, SharedConnectivityClientCallback callback) {
        boolean shouldBind;
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(callback, "callback cannot be null");
        if (this.mProxyMap.containsKey(callback) || this.mCallbackProxyCache.containsKey(callback)) {
            Log.m96e(TAG, "Callback already registered");
            callback.onRegisterCallbackFailed(new IllegalStateException("Callback already registered"));
            return;
        }
        SharedConnectivityCallbackProxy proxy = new SharedConnectivityCallbackProxy(executor, callback);
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
        if (!this.mProxyMap.containsKey(callback) && !this.mCallbackProxyCache.containsKey(callback)) {
            Log.m96e(TAG, "Callback not found, cannot unregister");
            return false;
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
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in unregisterCallback", e);
            return false;
        }
    }

    public boolean connectHotspotNetwork(HotspotNetwork network) {
        Objects.requireNonNull(network, "Hotspot network cannot be null");
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return false;
        }
        try {
            iSharedConnectivityService.connectHotspotNetwork(network);
            return true;
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in connectHotspotNetwork", e);
            return false;
        }
    }

    public boolean disconnectHotspotNetwork(HotspotNetwork network) {
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return false;
        }
        try {
            iSharedConnectivityService.disconnectHotspotNetwork(network);
            return true;
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in disconnectHotspotNetwork", e);
            return false;
        }
    }

    public boolean connectKnownNetwork(KnownNetwork network) {
        Objects.requireNonNull(network, "Known network cannot be null");
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return false;
        }
        try {
            iSharedConnectivityService.connectKnownNetwork(network);
            return true;
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in connectKnownNetwork", e);
            return false;
        }
    }

    public boolean forgetKnownNetwork(KnownNetwork network) {
        Objects.requireNonNull(network, "Known network cannot be null");
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return false;
        }
        try {
            iSharedConnectivityService.forgetKnownNetwork(network);
            return true;
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in forgetKnownNetwork", e);
            return false;
        }
    }

    public List<HotspotNetwork> getHotspotNetworks() {
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return null;
        }
        try {
            return iSharedConnectivityService.getHotspotNetworks();
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in getHotspotNetworks", e);
            return null;
        }
    }

    public List<KnownNetwork> getKnownNetworks() {
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return null;
        }
        try {
            return iSharedConnectivityService.getKnownNetworks();
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in getKnownNetworks", e);
            return null;
        }
    }

    public SharedConnectivitySettingsState getSettingsState() {
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return null;
        }
        try {
            return iSharedConnectivityService.getSettingsState();
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in getSettingsState", e);
            return null;
        }
    }

    public HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() {
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return null;
        }
        try {
            return iSharedConnectivityService.getHotspotNetworkConnectionStatus();
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in getHotspotNetworkConnectionStatus", e);
            return null;
        }
    }

    public KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() {
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return null;
        }
        try {
            return iSharedConnectivityService.getKnownNetworkConnectionStatus();
        } catch (RemoteException e) {
            Log.m97e(TAG, "Exception in getKnownNetworkConnectionStatus", e);
            return null;
        }
    }
}
