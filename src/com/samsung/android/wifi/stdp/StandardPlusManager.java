package com.samsung.android.wifi.stdp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.samsung.android.wifi.stdp.IStandardPlusCallback;

/* loaded from: classes6.dex */
public class StandardPlusManager {
    private final Context mContext;
    private Looper mLooper;
    private final IStandardPlusManager mService;

    public interface EventListener {
        void onEvent(int i);
    }

    public StandardPlusManager(Context context, IStandardPlusManager iStandardPlusManager) {
        this.mContext = context;
        this.mService = iStandardPlusManager;
        this.mLooper = context.getMainLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class StandardPlusCallbackProxy extends IStandardPlusCallback.Stub {
        private final Object mCallback;
        private final Handler mHandler;

        StandardPlusCallbackProxy(StandardPlusManager standardPlusManager, Looper looper, Object obj) {
            this.mHandler = new Handler(looper);
            this.mCallback = obj;
        }

        @Override // com.samsung.android.wifi.stdp.IStandardPlusCallback
        public void onEvent(final int i) {
            if (this.mCallback != null) {
                this.mHandler.post(new Runnable() { // from class: com.samsung.android.wifi.stdp.StandardPlusManager$StandardPlusCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onEvent$0(i);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEvent$0(int i) {
            ((EventListener) this.mCallback).onEvent(i);
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

    public void enableUsdNearby(int i) {
        try {
            this.mService.enableUsdNearby(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableUsdNearby(int i) {
        try {
            this.mService.disableUsdNearby(i);
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

    public boolean registerCallback(EventListener eventListener) {
        try {
            return this.mService.registerCallback(eventListener.hashCode(), new StandardPlusCallbackProxy(this, this.mLooper, eventListener));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean unregisterCallback(EventListener eventListener) {
        try {
            return this.mService.unregisterCallback(eventListener.hashCode());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
