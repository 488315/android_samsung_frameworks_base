package com.samsung.android.wifi.stdp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

public class StandardPlusManager {
    private final Context mContext;
    private Looper mLooper;
    private final IStandardPlusManager mService;

    public interface EventListener {
        void onEvent(int i);
    }

    public StandardPlusManager(Context context, IStandardPlusManager service) {
        this.mContext = context;
        this.mService = service;
        this.mLooper = this.mContext.getMainLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class StandardPlusCallbackProxy extends IStandardPlusCallback.Stub {
        private final Object mCallback;
        private final Handler mHandler;

        StandardPlusCallbackProxy(Looper looper, Object callback) {
            this.mHandler = new Handler(looper);
            this.mCallback = callback;
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusCallback
        public void onEvent(final int event) {
            if (this.mCallback != null) {
                this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.wifi.stdp.StandardPlusManager$StandardPlusCallbackProxy$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                StandardPlusManager.StandardPlusCallbackProxy.this.lambda$onEvent$0(
                                        event);
                            }
                        });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEvent$0(int event) {
            ((EventListener) this.mCallback).onEvent(event);
        }
    }

    public void startBleScan() {
        try {
            this.mService.startBleScan();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopBleScan() {
        try {
            this.mService.stopBleScan();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableUsdNearby(int duration) {
        try {
            this.mService.enableUsdNearby(duration);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableUsdNearby(int duration) {
        try {
            this.mService.disableUsdNearby(duration);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void stopBleAdvertising() {
        try {
            this.mService.stopBleAdvertising();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean registerCallback(EventListener listener) {
        StandardPlusCallbackProxy callbackProxy =
                new StandardPlusCallbackProxy(this.mLooper, listener);
        try {
            return this.mService.registerCallback(listener.hashCode(), callbackProxy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean unregisterCallback(EventListener listener) {
        try {
            return this.mService.unregisterCallback(listener.hashCode());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
