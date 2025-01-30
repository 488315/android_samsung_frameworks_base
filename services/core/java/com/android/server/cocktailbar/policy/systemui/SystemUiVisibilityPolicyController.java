package com.android.server.cocktailbar.policy.systemui;

import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Slog;
import com.samsung.android.cocktailbar.ISystemUiVisibilityCallback;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SystemUiVisibilityPolicyController {
    public static final String TAG = "SystemUiVisibilityPolicyController";
    public Handler mSystemUiVisibilityHandler;
    public HandlerThread mSystemUiVisibilityThread;
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static SystemUiVisibilityPolicyController mInstance = null;
    public int mSystemUiVisibility = 0;
    public final Object mLock = new Object();
    public ArrayList mStateListeners = new ArrayList();

    public static synchronized SystemUiVisibilityPolicyController getInstance(Context context) {
        SystemUiVisibilityPolicyController systemUiVisibilityPolicyController;
        synchronized (SystemUiVisibilityPolicyController.class) {
            if (mInstance == null) {
                mInstance = new SystemUiVisibilityPolicyController(context);
            }
            systemUiVisibilityPolicyController = mInstance;
        }
        return systemUiVisibilityPolicyController;
    }

    public SystemUiVisibilityPolicyController(Context context) {
    }

    public void registerSystemUiVisibilityListenerCallback(IBinder iBinder, ComponentName componentName) {
        synchronized (this.mStateListeners) {
            Iterator it = this.mStateListeners.iterator();
            while (it.hasNext()) {
                SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo = (SystemUiVisibilityListenerInfo) it.next();
                if (systemUiVisibilityListenerInfo != null && iBinder.equals(systemUiVisibilityListenerInfo.token)) {
                    Slog.e(TAG, "registerListenerCallback : already registered");
                    return;
                }
            }
            SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo2 = new SystemUiVisibilityListenerInfo(iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid());
            try {
                iBinder.linkToDeath(systemUiVisibilityListenerInfo2, 0);
            } catch (RemoteException e) {
                Slog.e(TAG, "registerListenerCallback : exception in linkToDeath " + e);
            }
            this.mStateListeners.add(systemUiVisibilityListenerInfo2);
            notifyStateToBinder(systemUiVisibilityListenerInfo2.token);
        }
    }

    public void unregisterSystemUiVisibilityListenerCallback(IBinder iBinder) {
        synchronized (this.mStateListeners) {
            Iterator it = this.mStateListeners.iterator();
            SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo = null;
            while (it.hasNext()) {
                SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo2 = (SystemUiVisibilityListenerInfo) it.next();
                if (systemUiVisibilityListenerInfo2 != null && iBinder.equals(systemUiVisibilityListenerInfo2.token)) {
                    systemUiVisibilityListenerInfo = systemUiVisibilityListenerInfo2;
                }
            }
            if (systemUiVisibilityListenerInfo == null) {
                Slog.e(TAG, "registerListenerCallback : cannot find the matched listener");
                return;
            }
            if (!this.mStateListeners.isEmpty()) {
                this.mStateListeners.remove(systemUiVisibilityListenerInfo);
            }
            iBinder.unlinkToDeath(systemUiVisibilityListenerInfo, 0);
            this.mStateListeners.notify();
        }
    }

    public void topAppWindowChanged(int i, boolean z, boolean z2) {
        if (i != 0) {
            return;
        }
        int i2 = this.mSystemUiVisibility;
        setState(1, 1, z);
        setState(2, 2, z2);
        int i3 = this.mSystemUiVisibility;
        if ((i3 & 1) == 0 || (2 & i3) == 0) {
            setState(4, 4, false);
        }
        if (i2 != this.mSystemUiVisibility) {
            systemUiVisibilityChanged();
        }
    }

    public void transientChanged(boolean z) {
        int i = this.mSystemUiVisibility;
        setState(4, 4, z);
        if (i != this.mSystemUiVisibility) {
            systemUiVisibilityChanged();
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
            enqueueMessageLocked(obtain, 0L, true);
        }
    }

    public final void handleUpdateVisibility(int i) {
        if (DEBUG) {
            Slog.i(TAG, "handleUpdateVisibility: visibility = " + i);
        }
        synchronized (this.mStateListeners) {
            Iterator it = this.mStateListeners.iterator();
            while (it.hasNext()) {
                ((SystemUiVisibilityListenerInfo) it.next()).onSystemUiVisibilityChanged(i);
            }
        }
    }

    public final void notifyStateToBinder(IBinder iBinder) {
        if (DEBUG) {
            Slog.i(TAG, "handleNotifySystemUiVisibilityToBinder");
        }
        synchronized (this.mLock) {
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.obj = iBinder;
            obtain.arg1 = this.mSystemUiVisibility;
            enqueueMessageLocked(obtain, 0L, false);
        }
    }

    public final boolean notifySystemUiVisibilityToBinder(IBinder iBinder, int i) {
        if (DEBUG) {
            Slog.i(TAG, "notifySystemUiVisibilityToBinder: visibility = " + i);
        }
        synchronized (this.mStateListeners) {
            Iterator it = this.mStateListeners.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SystemUiVisibilityListenerInfo systemUiVisibilityListenerInfo = (SystemUiVisibilityListenerInfo) it.next();
                if (iBinder.equals(systemUiVisibilityListenerInfo.token)) {
                    systemUiVisibilityListenerInfo.onSystemUiVisibilityChanged(i);
                    break;
                }
            }
        }
        return true;
    }

    public final void createHandlerThreadLocked() {
        if (this.mSystemUiVisibilityThread == null) {
            HandlerThread handlerThread = new HandlerThread("SystemUiVisibility");
            this.mSystemUiVisibilityThread = handlerThread;
            handlerThread.start();
            synchronized (this.mLock) {
                SystemUiVisibilityHandler systemUiVisibilityHandler = new SystemUiVisibilityHandler(this.mSystemUiVisibilityThread.getLooper());
                this.mSystemUiVisibilityHandler = systemUiVisibilityHandler;
                systemUiVisibilityHandler.post(new Runnable() { // from class: com.android.server.cocktailbar.policy.systemui.SystemUiVisibilityPolicyController.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Process.setThreadPriority(-4);
                        Process.setCanSelfBackground(false);
                    }
                });
            }
        }
    }

    public final void quitHandlerThread() {
        synchronized (this.mLock) {
            HandlerThread handlerThread = this.mSystemUiVisibilityThread;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                this.mSystemUiVisibilityThread = null;
                this.mSystemUiVisibilityHandler = null;
            }
        }
    }

    public final void enqueueMessageLocked(Message message, long j, boolean z) {
        createHandlerThreadLocked();
        if (z) {
            removeQueuedMessageLocked(message.what);
        }
        Handler handler = this.mSystemUiVisibilityHandler;
        if (handler != null) {
            handler.sendMessageDelayed(message, j);
            updateThreadExpireTimeLocked(j);
        }
    }

    public final void removeQueuedMessageLocked(int i) {
        Handler handler = this.mSystemUiVisibilityHandler;
        if (handler != null) {
            handler.removeMessages(i);
        }
    }

    public final void updateThreadExpireTimeLocked(long j) {
        this.mSystemUiVisibilityHandler.removeMessages(101);
        this.mSystemUiVisibilityHandler.sendEmptyMessageDelayed(101, 5000L);
    }

    public final class SystemUiVisibilityHandler extends Handler {
        public SystemUiVisibilityHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Slog.i(SystemUiVisibilityPolicyController.TAG, "handleMessage: entry what = " + message.what);
            int i = message.what;
            if (i == 1) {
                SystemUiVisibilityPolicyController.this.handleUpdateVisibility(message.arg1);
            } else if (i == 2) {
                SystemUiVisibilityPolicyController.this.notifySystemUiVisibilityToBinder((IBinder) message.obj, message.arg1);
            } else {
                if (i != 101) {
                    return;
                }
                SystemUiVisibilityPolicyController.this.quitHandlerThread();
            }
        }
    }

    public class SystemUiVisibilityListenerInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final int pid;
        public final IBinder token;
        public final int uid;

        public SystemUiVisibilityListenerInfo(IBinder iBinder, ComponentName componentName, int i, int i2) {
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Slog.v(SystemUiVisibilityPolicyController.TAG, "binderDied : binder = " + this.token);
            synchronized (SystemUiVisibilityPolicyController.this.mStateListeners) {
                SystemUiVisibilityPolicyController.this.notifyStateToBinder(this.token);
                SystemUiVisibilityPolicyController.this.mStateListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public void onSystemUiVisibilityChanged(int i) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Slog.w(SystemUiVisibilityPolicyController.TAG, "onSystemUiVisibilityChanged : token is null");
                return;
            }
            try {
                ISystemUiVisibilityCallback asInterface = ISystemUiVisibilityCallback.Stub.asInterface(iBinder);
                if (asInterface != null) {
                    asInterface.onSystemUiVisibilityChanged(i);
                }
            } catch (RemoteException e) {
                Slog.e(SystemUiVisibilityPolicyController.TAG, "onSystemUiVisibilityChanged : RemoteException : ", e);
            }
        }
    }
}
