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
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class GenericCoverServiceController {
    public static final String TAG = "CoverManager_" + GenericCoverServiceController.class.getSimpleName();
    public Context mContext;
    public PowerManager.WakeLock mDisableLcdOffWakeLock;
    public GenericPressServiceControllerHandler mHandler;
    public ArrayList mListeners = new ArrayList();
    public PowerManager mPowerManager;
    public PowerManager.WakeLock mSendPowerKeyWakeLock;

    public final class ListenerTypes {
    }

    public final class SystemEvents {
    }

    public GenericCoverServiceController(Looper looper, Context context) {
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        this.mHandler = new GenericPressServiceControllerHandler(looper);
        PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(1, "send power key");
        this.mSendPowerKeyWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 = this.mPowerManager.newWakeLock(1, "disable LCD Off");
        this.mDisableLcdOffWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
    }

    public final class GenericPressServiceControllerHandler extends Handler {
        public GenericPressServiceControllerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                GenericCoverServiceController.this.handleSendPowerKeyToCover((Bundle) message.obj);
            } else {
                if (i != 1) {
                    return;
                }
                GenericCoverServiceController.this.handleLcdOffDisabledByCover((Bundle) message.obj);
            }
        }
    }

    public void sendPowerKeyToCover() {
        Log.d(TAG, "sendPowerKeyToCover");
        acquireWakeLockWithPermission(this.mSendPowerKeyWakeLock);
        this.mHandler.obtainMessage(0).sendToTarget();
    }

    public void setLcdOffDisabledByCover(boolean z) {
        Log.d(TAG, "setLcdOffDisabledByCover disabled:" + z);
        acquireWakeLockWithPermission(this.mDisableLcdOffWakeLock);
        Bundle bundle = new Bundle();
        bundle.putBoolean("lcd_off_disabled_by_cover", z);
        Message obtainMessage = this.mHandler.obtainMessage(1);
        obtainMessage.obj = bundle;
        obtainMessage.sendToTarget();
    }

    public final void handleSendPowerKeyToCover(Bundle bundle) {
        synchronized (this.mListeners) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                GenericPressEventListenerInfo genericPressEventListenerInfo = (GenericPressEventListenerInfo) it.next();
                if (genericPressEventListenerInfo.type == 10) {
                    genericPressEventListenerInfo.onSystemCoverEvent(1, bundle);
                }
            }
        }
        releaseWakeLockWithPermission(this.mSendPowerKeyWakeLock);
    }

    public final void handleLcdOffDisabledByCover(Bundle bundle) {
        synchronized (this.mListeners) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                GenericPressEventListenerInfo genericPressEventListenerInfo = (GenericPressEventListenerInfo) it.next();
                if (genericPressEventListenerInfo.type == 4) {
                    genericPressEventListenerInfo.onSystemCoverEvent(4, bundle);
                }
            }
        }
        releaseWakeLockWithPermission(this.mDisableLcdOffWakeLock);
    }

    public final void acquireWakeLockWithPermission(PowerManager.WakeLock wakeLock) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!wakeLock.isHeld()) {
                wakeLock.acquire();
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, "Shouldn't happen", e);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void releaseWakeLockWithPermission(PowerManager.WakeLock wakeLock) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (wakeLock.isHeld()) {
                wakeLock.release();
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, "Shouldn't happen", e);
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void registerNfcTouchListenerCallback(int i, IBinder iBinder, ComponentName componentName) {
        String str = TAG;
        Log.d(str, "registerNfcTouchListenerCallback: binder = " + iBinder + ", pid : " + Binder.getCallingPid() + ", uid : " + Binder.getCallingUid() + ", type : " + i);
        if (i != 10 && i != 4) {
            Log.e(str, "Unsupported touch listener type: " + i);
            return;
        }
        synchronized (this.mListeners) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                GenericPressEventListenerInfo genericPressEventListenerInfo = (GenericPressEventListenerInfo) it.next();
                if (genericPressEventListenerInfo != null && iBinder.equals(genericPressEventListenerInfo.token)) {
                    Log.e(TAG, "registerNfcTouchListenerCallback : duplicated listener handle");
                    return;
                }
            }
            GenericPressEventListenerInfo genericPressEventListenerInfo2 = new GenericPressEventListenerInfo(iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid(), i);
            iBinder.linkToDeath(genericPressEventListenerInfo2, 0);
            this.mListeners.add(genericPressEventListenerInfo2);
        }
    }

    public boolean unRegisterNfcTouchListenerCallback(IBinder iBinder) {
        Log.d(TAG, "unRegisterNfcTouchListenerCallback: binder = " + iBinder + ", pid : " + Binder.getCallingPid() + ", uid : " + Binder.getCallingUid());
        synchronized (this.mListeners) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                GenericPressEventListenerInfo genericPressEventListenerInfo = (GenericPressEventListenerInfo) it.next();
                if (genericPressEventListenerInfo != null && iBinder.equals(genericPressEventListenerInfo.token)) {
                    Log.e(TAG, "remove listener: " + genericPressEventListenerInfo.pid);
                    this.mListeners.remove(genericPressEventListenerInfo);
                    iBinder.unlinkToDeath(genericPressEventListenerInfo, 0);
                    return true;
                }
            }
            Log.e(TAG, "UnregisterNfcTouchListener: listener does not exist");
            return false;
        }
    }

    public class GenericPressEventListenerInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final int pid;
        public final IBinder token;
        public final int type;
        public final int uid;

        public GenericPressEventListenerInfo(IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
            this.type = i3;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.v(GenericCoverServiceController.TAG, "binderDied : binder = " + this.token);
            synchronized (GenericCoverServiceController.this.mListeners) {
                GenericCoverServiceController.this.mListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public void onSystemCoverEvent(int i, Bundle bundle) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w(GenericCoverServiceController.TAG, "null listener received onSystemCoverEvent!");
                return;
            }
            try {
                INfcLedCoverTouchListenerCallback asInterface = INfcLedCoverTouchListenerCallback.Stub.asInterface(iBinder);
                if (asInterface != null) {
                    asInterface.onSystemCoverEvent(i, bundle);
                }
            } catch (RemoteException e) {
                Log.e(GenericCoverServiceController.TAG, "Failed onSystemCoverEvent callback", e);
            }
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current Generic Cover Callback state:");
        synchronized (this.mListeners) {
            printWriter.println("  Live callbacks (" + this.mListeners.size() + "):");
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                GenericPressEventListenerInfo genericPressEventListenerInfo = (GenericPressEventListenerInfo) it.next();
                if (genericPressEventListenerInfo != null) {
                    printWriter.println("    " + genericPressEventListenerInfo.component + " (pid=" + genericPressEventListenerInfo.pid + " uid=" + genericPressEventListenerInfo.uid + " type=" + genericPressEventListenerInfo.type + ")");
                }
            }
            printWriter.println("  ");
        }
    }
}
