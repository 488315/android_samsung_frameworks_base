package android.telephony;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.INetworkService;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public abstract class NetworkService extends Service {
    private static final int NETWORK_SERVICE_CREATE_NETWORK_SERVICE_PROVIDER = 1;
    private static final int NETWORK_SERVICE_GET_REGISTRATION_INFO = 4;
    private static final int NETWORK_SERVICE_INDICATION_NETWORK_INFO_CHANGED = 7;
    private static final int NETWORK_SERVICE_REGISTER_FOR_INFO_CHANGE = 5;
    private static final int NETWORK_SERVICE_REMOVE_ALL_NETWORK_SERVICE_PROVIDERS = 3;
    private static final int NETWORK_SERVICE_REMOVE_NETWORK_SERVICE_PROVIDER = 2;
    private static final int NETWORK_SERVICE_UNREGISTER_FOR_INFO_CHANGE = 6;
    public static final String SERVICE_INTERFACE = "android.telephony.NetworkService";
    private final NetworkServiceHandler mHandler;
    private final HandlerThread mHandlerThread;
    private final String TAG = "NetworkService";
    private final SparseArray<NetworkServiceProvider> mServiceMap = new SparseArray<>();
    public final INetworkServiceWrapper mBinder = new INetworkServiceWrapper();

    public abstract NetworkServiceProvider onCreateNetworkServiceProvider(int i);

    public abstract class NetworkServiceProvider implements AutoCloseable {
        private final List<INetworkServiceCallback> mNetworkRegistrationInfoChangedCallbacks = new ArrayList();
        private final int mSlotIndex;

        @Override // java.lang.AutoCloseable
        public abstract void close();

        public NetworkServiceProvider(int i) {
            this.mSlotIndex = i;
        }

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        public void requestNetworkRegistrationInfo(int i, NetworkServiceCallback networkServiceCallback) {
            networkServiceCallback.onRequestNetworkRegistrationInfoComplete(1, null);
        }

        public final void notifyNetworkRegistrationInfoChanged() {
            NetworkService.this.mHandler.obtainMessage(7, this.mSlotIndex, 0, null).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerForInfoChanged(INetworkServiceCallback iNetworkServiceCallback) {
            synchronized (this.mNetworkRegistrationInfoChangedCallbacks) {
                this.mNetworkRegistrationInfoChangedCallbacks.add(iNetworkServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregisterForInfoChanged(INetworkServiceCallback iNetworkServiceCallback) {
            synchronized (this.mNetworkRegistrationInfoChangedCallbacks) {
                this.mNetworkRegistrationInfoChangedCallbacks.remove(iNetworkServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyInfoChangedToCallbacks() {
            Iterator<INetworkServiceCallback> it = this.mNetworkRegistrationInfoChangedCallbacks.iterator();
            while (it.hasNext()) {
                try {
                    it.next().onNetworkStateChanged();
                } catch (RemoteException unused) {
                }
            }
        }
    }

    private class NetworkServiceHandler extends Handler {
        NetworkServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.arg1;
            INetworkServiceCallback iNetworkServiceCallback = (INetworkServiceCallback) message.obj;
            NetworkServiceProvider networkServiceProvider = (NetworkServiceProvider) NetworkService.this.mServiceMap.get(i);
            switch (message.what) {
                case 1:
                    if (networkServiceProvider == null) {
                        NetworkService.this.mServiceMap.put(i, NetworkService.this.onCreateNetworkServiceProvider(i));
                        break;
                    }
                    break;
                case 2:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.close();
                        NetworkService.this.mServiceMap.remove(i);
                        break;
                    }
                    break;
                case 3:
                    for (int i2 = 0; i2 < NetworkService.this.mServiceMap.size(); i2++) {
                        NetworkServiceProvider networkServiceProvider2 = (NetworkServiceProvider) NetworkService.this.mServiceMap.get(i2);
                        if (networkServiceProvider2 != null) {
                            networkServiceProvider2.close();
                        }
                    }
                    NetworkService.this.mServiceMap.clear();
                    break;
                case 4:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.requestNetworkRegistrationInfo(message.arg2, new NetworkServiceCallback(iNetworkServiceCallback));
                        break;
                    }
                    break;
                case 5:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.registerForInfoChanged(iNetworkServiceCallback);
                        break;
                    }
                    break;
                case 6:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.unregisterForInfoChanged(iNetworkServiceCallback);
                        break;
                    }
                    break;
                case 7:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.notifyInfoChangedToCallbacks();
                        break;
                    }
                    break;
            }
        }
    }

    public NetworkService() {
        HandlerThread handlerThread = new HandlerThread("NetworkService");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new NetworkServiceHandler(handlerThread.getLooper());
        log("network service created");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (intent == null || !SERVICE_INTERFACE.equals(intent.getAction())) {
            loge("Unexpected intent " + intent);
            return null;
        }
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        this.mHandler.obtainMessage(3, 0, 0, null).sendToTarget();
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandlerThread.quitSafely();
        super.onDestroy();
    }

    private class INetworkServiceWrapper extends INetworkService.Stub {
        private INetworkServiceWrapper() {
        }

        @Override // android.telephony.INetworkService
        public void createNetworkServiceProvider(int i) {
            NetworkService.this.mHandler.obtainMessage(1, i, 0, null).sendToTarget();
        }

        @Override // android.telephony.INetworkService
        public void removeNetworkServiceProvider(int i) {
            NetworkService.this.mHandler.obtainMessage(2, i, 0, null).sendToTarget();
        }

        @Override // android.telephony.INetworkService
        public void requestNetworkRegistrationInfo(int i, int i2, INetworkServiceCallback iNetworkServiceCallback) {
            NetworkService.this.mHandler.obtainMessage(4, i, i2, iNetworkServiceCallback).sendToTarget();
        }

        @Override // android.telephony.INetworkService
        public void registerForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) {
            NetworkService.this.mHandler.obtainMessage(5, i, 0, iNetworkServiceCallback).sendToTarget();
        }

        @Override // android.telephony.INetworkService
        public void unregisterForNetworkRegistrationInfoChanged(int i, INetworkServiceCallback iNetworkServiceCallback) {
            NetworkService.this.mHandler.obtainMessage(6, i, 0, iNetworkServiceCallback).sendToTarget();
        }
    }

    private final void log(String str) {
        com.android.telephony.Rlog.d(this.TAG, str);
    }

    private final void loge(String str) {
        com.android.telephony.Rlog.e(this.TAG, str);
    }
}
