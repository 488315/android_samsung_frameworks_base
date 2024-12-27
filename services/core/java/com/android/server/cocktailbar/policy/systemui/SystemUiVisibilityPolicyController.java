package com.android.server.cocktailbar.policy.systemui;

import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;

import com.samsung.android.cocktailbar.ISystemUiVisibilityCallback;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemUiVisibilityPolicyController {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static SystemUiVisibilityPolicyController mInstance = null;
    public SystemUiVisibilityHandler mSystemUiVisibilityHandler;
    public HandlerThread mSystemUiVisibilityThread;
    public int mSystemUiVisibility = 0;
    public final Object mLock = new Object();
    public final ArrayList mStateListeners = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.cocktailbar.policy.systemui.SystemUiVisibilityPolicyController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(-4);
            Process.setCanSelfBackground(false);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemUiVisibilityHandler extends Handler {
        public SystemUiVisibilityHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z = SystemUiVisibilityPolicyController.DEBUG;
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    new StringBuilder("handleMessage: entry what = "),
                    message.what,
                    "SystemUiVisibilityPolicyController");
            int i = message.what;
            if (i == 1) {
                SystemUiVisibilityPolicyController systemUiVisibilityPolicyController =
                        SystemUiVisibilityPolicyController.this;
                int i2 = message.arg1;
                if (SystemUiVisibilityPolicyController.DEBUG) {
                    systemUiVisibilityPolicyController.getClass();
                    Slog.i(
                            "SystemUiVisibilityPolicyController",
                            "handleUpdateVisibility: visibility = " + i2);
                }
                synchronized (systemUiVisibilityPolicyController.mStateListeners) {
                    try {
                        Iterator it = systemUiVisibilityPolicyController.mStateListeners.iterator();
                        while (it.hasNext()) {
                            ((SystemUiVisibilityListenerInfo) it.next())
                                    .onSystemUiVisibilityChanged(i2);
                        }
                    } finally {
                    }
                }
                return;
            }
            if (i != 2) {
                if (i != 101) {
                    return;
                }
                SystemUiVisibilityPolicyController systemUiVisibilityPolicyController2 =
                        SystemUiVisibilityPolicyController.this;
                synchronized (systemUiVisibilityPolicyController2.mLock) {
                    try {
                        HandlerThread handlerThread =
                                systemUiVisibilityPolicyController2.mSystemUiVisibilityThread;
                        if (handlerThread != null) {
                            handlerThread.quitSafely();
                            systemUiVisibilityPolicyController2.mSystemUiVisibilityThread = null;
                            systemUiVisibilityPolicyController2.mSystemUiVisibilityHandler = null;
                        }
                    } finally {
                    }
                }
                return;
            }
            SystemUiVisibilityPolicyController systemUiVisibilityPolicyController3 =
                    SystemUiVisibilityPolicyController.this;
            IBinder iBinder = (IBinder) message.obj;
            int i3 = message.arg1;
            if (SystemUiVisibilityPolicyController.DEBUG) {
                systemUiVisibilityPolicyController3.getClass();
                Slog.i(
                        "SystemUiVisibilityPolicyController",
                        "notifySystemUiVisibilityToBinder: visibility = " + i3);
            }
            synchronized (systemUiVisibilityPolicyController3.mStateListeners) {
                try {
                    Iterator it2 = systemUiVisibilityPolicyController3.mStateListeners.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo =
                                (SystemUiVisibilityListenerInfo) it2.next();
                        if (iBinder.equals(systemUiVisibilityListenerInfo.token)) {
                            systemUiVisibilityListenerInfo.onSystemUiVisibilityChanged(i3);
                            break;
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemUiVisibilityListenerInfo implements IBinder.DeathRecipient {
        public final IBinder token;

        public SystemUiVisibilityListenerInfo(IBinder iBinder) {
            this.token = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean z = SystemUiVisibilityPolicyController.DEBUG;
            Slog.v("SystemUiVisibilityPolicyController", "binderDied : binder = " + this.token);
            synchronized (SystemUiVisibilityPolicyController.this.mStateListeners) {
                SystemUiVisibilityPolicyController.this.notifyStateToBinder(this.token);
                SystemUiVisibilityPolicyController.this.mStateListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final void onSystemUiVisibilityChanged(int i) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                boolean z = SystemUiVisibilityPolicyController.DEBUG;
                Slog.w(
                        "SystemUiVisibilityPolicyController",
                        "onSystemUiVisibilityChanged : token is null");
                return;
            }
            try {
                ISystemUiVisibilityCallback asInterface =
                        ISystemUiVisibilityCallback.Stub.asInterface(iBinder);
                if (asInterface != null) {
                    asInterface.onSystemUiVisibilityChanged(i);
                }
            } catch (RemoteException e) {
                boolean z2 = SystemUiVisibilityPolicyController.DEBUG;
                Slog.e(
                        "SystemUiVisibilityPolicyController",
                        "onSystemUiVisibilityChanged : RemoteException : ",
                        e);
            }
        }
    }

    public final void enqueueMessageLocked(Message message, boolean z) {
        if (this.mSystemUiVisibilityThread == null) {
            HandlerThread handlerThread = new HandlerThread("SystemUiVisibility");
            this.mSystemUiVisibilityThread = handlerThread;
            handlerThread.start();
            synchronized (this.mLock) {
                SystemUiVisibilityHandler systemUiVisibilityHandler =
                        new SystemUiVisibilityHandler(this.mSystemUiVisibilityThread.getLooper());
                this.mSystemUiVisibilityHandler = systemUiVisibilityHandler;
                systemUiVisibilityHandler.post(new AnonymousClass1());
            }
        }
        if (z) {
            int i = message.what;
            SystemUiVisibilityHandler systemUiVisibilityHandler2 = this.mSystemUiVisibilityHandler;
            if (systemUiVisibilityHandler2 != null) {
                systemUiVisibilityHandler2.removeMessages(i);
            }
        }
        SystemUiVisibilityHandler systemUiVisibilityHandler3 = this.mSystemUiVisibilityHandler;
        if (systemUiVisibilityHandler3 != null) {
            systemUiVisibilityHandler3.sendMessageDelayed(message, 0L);
            this.mSystemUiVisibilityHandler.removeMessages(101);
            this.mSystemUiVisibilityHandler.sendEmptyMessageDelayed(101, 5000L);
        }
    }

    public final void notifyStateToBinder(IBinder iBinder) {
        if (DEBUG) {
            Slog.i("SystemUiVisibilityPolicyController", "handleNotifySystemUiVisibilityToBinder");
        }
        synchronized (this.mLock) {
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.obj = iBinder;
            obtain.arg1 = this.mSystemUiVisibility;
            enqueueMessageLocked(obtain, false);
        }
    }

    public final void setState(int i, int i2, boolean z) {
        if (z) {
            this.mSystemUiVisibility = i | ((~i2) & this.mSystemUiVisibility);
        } else {
            this.mSystemUiVisibility = (~i) & this.mSystemUiVisibility;
        }
    }

    public final void systemUiVisibilityChanged() {
        synchronized (this.mLock) {
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.arg1 = this.mSystemUiVisibility;
            enqueueMessageLocked(obtain, true);
        }
    }
}
