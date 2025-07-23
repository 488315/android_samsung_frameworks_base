package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.net.wifi.sharedconnectivity.app.SharedConnectivityManager;
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
/* loaded from: classes3.dex */
public class SharedConnectivityManager {
    private static final boolean DEBUG = false;
    private static final String TAG = "SharedConnectivityManager";
    private final Context mContext;
    private final String mIntentAction;
    private ISharedConnectivityService mService;
    private ServiceConnection mServiceConnection;
    private final String mServicePackageName;
    private UserManager mUserManager;
    private final Map<SharedConnectivityClientCallback, SharedConnectivityCallbackProxy> mProxyMap = new HashMap();
    private final Map<SharedConnectivityClientCallback, SharedConnectivityCallbackProxy> mCallbackProxyCache = new HashMap();
    private final Object mProxyDataLock = new Object();
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(SharedConnectivityManager.this.mBroadcastReceiver);
            SharedConnectivityManager.this.bind();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    static final class SharedConnectivityCallbackProxy extends ISharedConnectivityCallback.Stub {
        private final SharedConnectivityClientCallback mCallback;
        private final Executor mExecutor;

        SharedConnectivityCallbackProxy(Executor executor, SharedConnectivityClientCallback sharedConnectivityClientCallback) {
            this.mExecutor = executor;
            this.mCallback = sharedConnectivityClientCallback;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onServiceConnected() {
            if (this.mCallback != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onServiceConnected$0();
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$0() {
            this.mCallback.onServiceConnected();
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onServiceDisconnected() {
            if (this.mCallback != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onServiceDisconnected$1();
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceDisconnected$1() {
            this.mCallback.onServiceDisconnected();
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworksUpdated(final List<HotspotNetwork> list) {
            if (this.mCallback != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onHotspotNetworksUpdated$2(list);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotspotNetworksUpdated$2(List list) {
            this.mCallback.onHotspotNetworksUpdated(list);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworksUpdated(final List<KnownNetwork> list) {
            if (this.mCallback != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onKnownNetworksUpdated$3(list);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKnownNetworksUpdated$3(List list) {
            this.mCallback.onKnownNetworksUpdated(list);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onSharedConnectivitySettingsChanged(final SharedConnectivitySettingsState sharedConnectivitySettingsState) {
            if (this.mCallback != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onSharedConnectivitySettingsChanged$4(sharedConnectivitySettingsState);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSharedConnectivitySettingsChanged$4(SharedConnectivitySettingsState sharedConnectivitySettingsState) {
            this.mCallback.onSharedConnectivitySettingsChanged(sharedConnectivitySettingsState);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworkConnectionStatusChanged(final HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
            if (this.mCallback != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onHotspotNetworkConnectionStatusChanged$5(hotspotNetworkConnectionStatus);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onHotspotNetworkConnectionStatusChanged$5(HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) {
            this.mCallback.onHotspotNetworkConnectionStatusChanged(hotspotNetworkConnectionStatus);
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworkConnectionStatusChanged(final KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
            if (this.mCallback != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$SharedConnectivityCallbackProxy$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SharedConnectivityManager.SharedConnectivityCallbackProxy.this.lambda$onKnownNetworkConnectionStatusChanged$6(knownNetworkConnectionStatus);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onKnownNetworkConnectionStatusChanged$6(KnownNetworkConnectionStatus knownNetworkConnectionStatus) {
            this.mCallback.onKnownNetworkConnectionStatusChanged(knownNetworkConnectionStatus);
        }
    }

    public static SharedConnectivityManager create(Context context) {
        Resources resources = context.getResources();
        try {
            String string = resources.getString(R.string.config_sharedConnectivityServicePackage);
            String string2 = resources.getString(R.string.config_sharedConnectivityServiceIntentAction);
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                return new SharedConnectivityManager(context, string, string2);
            }
            Log.e(TAG, "To support shared connectivity service on this device, the service's package name and intent action strings must not be empty");
            return null;
        } catch (Resources.NotFoundException unused) {
            Log.e(TAG, "To support shared connectivity service on this device, the service's package name and intent action strings must be defined");
            return null;
        }
    }

    public static SharedConnectivityManager create(Context context, String str, String str2) {
        return new SharedConnectivityManager(context, str, str2);
    }

    private SharedConnectivityManager(Context context, String str, String str2) {
        this.mContext = context;
        this.mServicePackageName = str;
        this.mIntentAction = str2;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    /* renamed from: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1, reason: invalid class name */
    class AnonymousClass1 implements ServiceConnection {
        AnonymousClass1() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SharedConnectivityManager.this.mService = ISharedConnectivityService.Stub.asInterface(iBinder);
            synchronized (SharedConnectivityManager.this.mProxyDataLock) {
                if (!SharedConnectivityManager.this.mCallbackProxyCache.isEmpty()) {
                    SharedConnectivityManager.this.mCallbackProxyCache.keySet().forEach(new Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            SharedConnectivityManager.AnonymousClass1.this.lambda$onServiceConnected$0((SharedConnectivityClientCallback) obj);
                        }
                    });
                    SharedConnectivityManager.this.mCallbackProxyCache.clear();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$0(SharedConnectivityClientCallback sharedConnectivityClientCallback) {
            SharedConnectivityManager sharedConnectivityManager = SharedConnectivityManager.this;
            sharedConnectivityManager.registerCallbackInternal(sharedConnectivityClientCallback, (SharedConnectivityCallbackProxy) sharedConnectivityManager.mCallbackProxyCache.get(sharedConnectivityClientCallback));
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            SharedConnectivityManager.this.mService = null;
            synchronized (SharedConnectivityManager.this.mProxyDataLock) {
                if (!SharedConnectivityManager.this.mCallbackProxyCache.isEmpty()) {
                    SharedConnectivityManager.this.mCallbackProxyCache.values().forEach(new Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SharedConnectivityManager.SharedConnectivityCallbackProxy) obj).onServiceDisconnected();
                        }
                    });
                    SharedConnectivityManager.this.mCallbackProxyCache.clear();
                }
                if (!SharedConnectivityManager.this.mProxyMap.isEmpty()) {
                    SharedConnectivityManager.this.mProxyMap.values().forEach(new Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$1$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SharedConnectivityManager.SharedConnectivityCallbackProxy) obj).onServiceDisconnected();
                        }
                    });
                    SharedConnectivityManager.this.mProxyMap.clear();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bind() {
        this.mServiceConnection = new AnonymousClass1();
        if (this.mContext.bindService(new Intent().setPackage(this.mServicePackageName).setAction(this.mIntentAction), this.mServiceConnection, 1)) {
            return;
        }
        this.mServiceConnection = null;
        UserManager userManager = this.mUserManager;
        if (userManager != null && !userManager.isUserUnlocked()) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_USER_UNLOCKED);
            this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
        } else {
            synchronized (this.mProxyDataLock) {
                if (!this.mCallbackProxyCache.isEmpty()) {
                    this.mCallbackProxyCache.keySet().forEach(new Consumer() { // from class: android.net.wifi.sharedconnectivity.app.SharedConnectivityManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SharedConnectivityClientCallback) obj).onRegisterCallbackFailed(new IllegalStateException("Failed to bind after user unlock"));
                        }
                    });
                    this.mCallbackProxyCache.clear();
                }
            }
        }
    }

    public BroadcastReceiver getBroadcastReceiver() {
        return this.mBroadcastReceiver;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerCallbackInternal(SharedConnectivityClientCallback sharedConnectivityClientCallback, SharedConnectivityCallbackProxy sharedConnectivityCallbackProxy) {
        try {
            this.mService.registerCallback(sharedConnectivityCallbackProxy);
            synchronized (this.mProxyDataLock) {
                this.mProxyMap.put(sharedConnectivityClientCallback, sharedConnectivityCallbackProxy);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in registerCallback", e);
            sharedConnectivityClientCallback.onRegisterCallbackFailed(e);
        }
    }

    public void setService(IInterface iInterface) {
        this.mService = (ISharedConnectivityService) iInterface;
    }

    public ServiceConnection getServiceConnection() {
        return this.mServiceConnection;
    }

    private void unbind() {
        ServiceConnection serviceConnection = this.mServiceConnection;
        if (serviceConnection != null) {
            this.mContext.unbindService(serviceConnection);
            this.mServiceConnection = null;
            this.mService = null;
        }
    }

    public void registerCallback(Executor executor, SharedConnectivityClientCallback sharedConnectivityClientCallback) {
        boolean z;
        Objects.requireNonNull(executor, "executor cannot be null");
        Objects.requireNonNull(sharedConnectivityClientCallback, "callback cannot be null");
        if (this.mProxyMap.containsKey(sharedConnectivityClientCallback) || this.mCallbackProxyCache.containsKey(sharedConnectivityClientCallback)) {
            Log.e(TAG, "Callback already registered");
            sharedConnectivityClientCallback.onRegisterCallbackFailed(new IllegalStateException("Callback already registered"));
            return;
        }
        SharedConnectivityCallbackProxy sharedConnectivityCallbackProxy = new SharedConnectivityCallbackProxy(executor, sharedConnectivityClientCallback);
        if (this.mService == null) {
            synchronized (this.mProxyDataLock) {
                z = this.mCallbackProxyCache.size() == 0;
                this.mCallbackProxyCache.put(sharedConnectivityClientCallback, sharedConnectivityCallbackProxy);
            }
            if (z) {
                bind();
                return;
            }
            return;
        }
        registerCallbackInternal(sharedConnectivityClientCallback, sharedConnectivityCallbackProxy);
    }

    public boolean unregisterCallback(SharedConnectivityClientCallback sharedConnectivityClientCallback) {
        boolean isEmpty;
        boolean isEmpty2;
        Objects.requireNonNull(sharedConnectivityClientCallback, "callback cannot be null");
        if (!this.mProxyMap.containsKey(sharedConnectivityClientCallback) && !this.mCallbackProxyCache.containsKey(sharedConnectivityClientCallback)) {
            Log.e(TAG, "Callback not found, cannot unregister");
            return false;
        }
        try {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        } catch (IllegalArgumentException unused) {
        }
        if (this.mService == null) {
            synchronized (this.mProxyDataLock) {
                this.mCallbackProxyCache.remove(sharedConnectivityClientCallback);
                isEmpty2 = this.mCallbackProxyCache.isEmpty();
            }
            if (isEmpty2) {
                unbind();
            }
            return true;
        }
        try {
            synchronized (this.mProxyDataLock) {
                this.mService.unregisterCallback(this.mProxyMap.get(sharedConnectivityClientCallback));
                this.mProxyMap.remove(sharedConnectivityClientCallback);
                isEmpty = this.mProxyMap.isEmpty();
            }
            if (isEmpty) {
                unbind();
            }
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in unregisterCallback", e);
            return false;
        }
    }

    public boolean connectHotspotNetwork(HotspotNetwork hotspotNetwork) {
        Objects.requireNonNull(hotspotNetwork, "Hotspot network cannot be null");
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return false;
        }
        try {
            iSharedConnectivityService.connectHotspotNetwork(hotspotNetwork);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in connectHotspotNetwork", e);
            return false;
        }
    }

    public boolean disconnectHotspotNetwork(HotspotNetwork hotspotNetwork) {
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return false;
        }
        try {
            iSharedConnectivityService.disconnectHotspotNetwork(hotspotNetwork);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in disconnectHotspotNetwork", e);
            return false;
        }
    }

    public boolean connectKnownNetwork(KnownNetwork knownNetwork) {
        Objects.requireNonNull(knownNetwork, "Known network cannot be null");
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return false;
        }
        try {
            iSharedConnectivityService.connectKnownNetwork(knownNetwork);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in connectKnownNetwork", e);
            return false;
        }
    }

    public boolean forgetKnownNetwork(KnownNetwork knownNetwork) {
        Objects.requireNonNull(knownNetwork, "Known network cannot be null");
        ISharedConnectivityService iSharedConnectivityService = this.mService;
        if (iSharedConnectivityService == null) {
            return false;
        }
        try {
            iSharedConnectivityService.forgetKnownNetwork(knownNetwork);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Exception in forgetKnownNetwork", e);
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
            Log.e(TAG, "Exception in getHotspotNetworks", e);
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
            Log.e(TAG, "Exception in getKnownNetworks", e);
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
            Log.e(TAG, "Exception in getSettingsState", e);
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
            Log.e(TAG, "Exception in getHotspotNetworkConnectionStatus", e);
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
            Log.e(TAG, "Exception in getKnownNetworkConnectionStatus", e);
            return null;
        }
    }
}
