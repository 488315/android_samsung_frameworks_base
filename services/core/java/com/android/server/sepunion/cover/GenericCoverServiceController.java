package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;

import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;
import com.samsung.android.sepunion.Log;

import java.util.ArrayList;
import java.util.Iterator;

public final class GenericCoverServiceController {
    public final PowerManager.WakeLock mDisableLcdOffWakeLock;
    public final GenericPressServiceControllerHandler mHandler;
    public final ArrayList mListeners;
    public final PowerManager.WakeLock mSendPowerKeyWakeLock;

    public final class GenericPressEventListenerInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final int pid;
        public final IBinder token;
        public final int type;
        public final int uid;

        public GenericPressEventListenerInfo(
                IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
            this.type = i3;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.v(
                    "CoverManager_GenericCoverServiceController",
                    "binderDied : binder = " + this.token);
            synchronized (GenericCoverServiceController.this.mListeners) {
                GenericCoverServiceController.this.mListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final void onSystemCoverEvent(int i, Bundle bundle) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w(
                        "CoverManager_GenericCoverServiceController",
                        "null listener received onSystemCoverEvent!");
                return;
            }
            try {
                INfcLedCoverTouchListenerCallback asInterface =
                        INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
                if (asInterface != null) {
                    asInterface.onSystemCoverEvent(i, bundle);
                }
            } catch (RemoteException e) {
                Log.e(
                        "CoverManager_GenericCoverServiceController",
                        "Failed onSystemCoverEvent callback",
                        e);
            }
        }
    }

    public final class GenericPressServiceControllerHandler extends Handler {
        public GenericPressServiceControllerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                GenericCoverServiceController genericCoverServiceController =
                        GenericCoverServiceController.this;
                Bundle bundle = (Bundle) message.obj;
                synchronized (genericCoverServiceController.mListeners) {
                    try {
                        Iterator it = genericCoverServiceController.mListeners.iterator();
                        while (it.hasNext()) {
                            GenericPressEventListenerInfo genericPressEventListenerInfo =
                                    (GenericPressEventListenerInfo) it.next();
                            if (genericPressEventListenerInfo.type == 10) {
                                genericPressEventListenerInfo.onSystemCoverEvent(1, bundle);
                            }
                        }
                    } finally {
                    }
                }
                PowerManager.WakeLock wakeLock =
                        genericCoverServiceController.mSendPowerKeyWakeLock;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (wakeLock.isHeld()) {
                        wakeLock.release();
                    }
                } catch (IllegalStateException e) {
                    Log.e("CoverManager_GenericCoverServiceController", "Shouldn't happen", e);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            if (i != 1) {
                return;
            }
            GenericCoverServiceController genericCoverServiceController2 =
                    GenericCoverServiceController.this;
            Bundle bundle2 = (Bundle) message.obj;
            synchronized (genericCoverServiceController2.mListeners) {
                try {
                    Iterator it2 = genericCoverServiceController2.mListeners.iterator();
                    while (it2.hasNext()) {
                        GenericPressEventListenerInfo genericPressEventListenerInfo2 =
                                (GenericPressEventListenerInfo) it2.next();
                        if (genericPressEventListenerInfo2.type == 4) {
                            genericPressEventListenerInfo2.onSystemCoverEvent(4, bundle2);
                        }
                    }
                } finally {
                }
            }
            PowerManager.WakeLock wakeLock2 = genericCoverServiceController2.mDisableLcdOffWakeLock;
            long clearCallingIdentity2 = Binder.clearCallingIdentity();
            try {
                if (wakeLock2.isHeld()) {
                    wakeLock2.release();
                }
            } catch (IllegalStateException e2) {
                Log.e("CoverManager_GenericCoverServiceController", "Shouldn't happen", e2);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity2);
        }
    }

    public final class ListenerTypes {}

    public final class SystemEvents {}

    public GenericCoverServiceController(Looper looper, Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mListeners = new ArrayList();
        this.mHandler = new GenericPressServiceControllerHandler(looper);
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "send power key");
        this.mSendPowerKeyWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 = powerManager.newWakeLock(1, "disable LCD Off");
        this.mDisableLcdOffWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
    }

    public final void setLcdOffDisabledByCover(boolean z) {
        Log.d(
                "CoverManager_GenericCoverServiceController",
                "setLcdOffDisabledByCover disabled:" + z);
        PowerManager.WakeLock wakeLock = this.mDisableLcdOffWakeLock;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!wakeLock.isHeld()) {
                wakeLock.acquire();
            }
        } catch (IllegalStateException e) {
            Log.e("CoverManager_GenericCoverServiceController", "Shouldn't happen", e);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        Bundle bundle = new Bundle();
        bundle.putBoolean("lcd_off_disabled_by_cover", z);
        Message obtainMessage = this.mHandler.obtainMessage(1);
        obtainMessage.obj = bundle;
        obtainMessage.sendToTarget();
    }
}
