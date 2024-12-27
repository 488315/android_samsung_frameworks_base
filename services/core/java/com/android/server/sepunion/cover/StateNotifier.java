package com.android.server.sepunion.cover;

import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;

import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManagerCallback;
import com.samsung.android.cover.ICoverStateListenerCallback;
import com.samsung.android.sepunion.Log;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class StateNotifier {
    public final Context mContext;
    public final H mHandler;
    public boolean mLcdOffDisabledByApp;
    public LcdOffDisabledByAppListener mLcdOffDisabledByAppListener;
    public final PowerManager mPowerManager;
    public final PowerManager.WakeLock mSendCoverAttachStateWakeLock;
    public final PowerManager.WakeLock mSendCoverSwitchStateWakeLock;
    public final ArrayList mHighPriorityListeners = new ArrayList();
    public final ArrayList mListeners = new ArrayList();
    public final ArrayList mLcdOffDisableListeners = new ArrayList();
    public int mCoverType = 2;
    public boolean mCoverOpen = false;
    public boolean mBootComplete = false;
    public StateNotifier$$ExternalSyntheticLambda0 mGoToSleepRunnable = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CoverStateListenerInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final int pid;
        public final IBinder token;
        public final int type;
        public final int uid;

        public CoverStateListenerInfo(
                IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
            this.type = i3;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.v("CoverManager_StateNotifier", "binderDied : binder = " + this.token);
            synchronized (StateNotifier.this.mListeners) {
                StateNotifier.this.mListeners.remove(this);
            }
            synchronized (StateNotifier.this.mHighPriorityListeners) {
                StateNotifier.this.mHighPriorityListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final void onCoverAttachStateChanged(boolean z) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w("CoverManager_StateNotifier", "onCoverAttachStateChanged : token is null");
                return;
            }
            if ((this.type & 2) != 0) {
                try {
                    ICoverStateListenerCallback asInterface =
                            ICoverStateListenerCallback.Stub.asInterface(iBinder);
                    if (asInterface != null) {
                        asInterface.onCoverAttachStateChanged(z);
                    }
                } catch (RemoteException e) {
                    Log.e(
                            "CoverManager_StateNotifier",
                            "Failed onCoverAttachStateChanged callback",
                            e);
                }
            }
        }

        public final void onCoverSwitchStateChanged(CoverState coverState) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w("CoverManager_StateNotifier", "onCoverSwitchStateChanged : token is null");
                return;
            }
            if (this.type == 1) {
                try {
                    ICoverManagerCallback asInterface =
                            ICoverManagerCallback.Stub.asInterface(iBinder);
                    if (asInterface != null) {
                        asInterface.coverCallback(coverState);
                    }
                } catch (RemoteException e) {
                    Log.e(
                            "CoverManager_StateNotifier",
                            "Failed onCoverStateChanged coverCallback",
                            e);
                }
            }
        }

        public final void onCoverSwitchStateChanged(boolean z) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                Log.w("CoverManager_StateNotifier", "onCoverSwitchStateChanged : token is null");
                return;
            }
            if ((this.type & 2) != 0) {
                try {
                    ICoverStateListenerCallback asInterface =
                            ICoverStateListenerCallback.Stub.asInterface(iBinder);
                    if (asInterface != null) {
                        asInterface.onCoverSwitchStateChanged(z);
                    }
                } catch (RemoteException e) {
                    Log.e(
                            "CoverManager_StateNotifier",
                            "Failed onCoverSwitchStateChanged callback",
                            e);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            int i = message.what;
            if (i == 0) {
                StateNotifier stateNotifier = StateNotifier.this;
                CoverState coverState = (CoverState) message.obj;
                z = message.arg1 == 1;
                synchronized (stateNotifier.mListeners) {
                    try {
                        Iterator it = stateNotifier.mListeners.iterator();
                        while (it.hasNext()) {
                            CoverStateListenerInfo coverStateListenerInfo =
                                    (CoverStateListenerInfo) it.next();
                            coverStateListenerInfo.onCoverSwitchStateChanged(coverState);
                            coverStateListenerInfo.onCoverSwitchStateChanged(z);
                        }
                    } finally {
                    }
                }
                Log.addLogString("CoverManager_", "send cover switch : " + z);
                if (stateNotifier.mSendCoverSwitchStateWakeLock.isHeld()) {
                    stateNotifier.mSendCoverSwitchStateWakeLock.release();
                    return;
                }
                return;
            }
            if (i != 1) {
                return;
            }
            StateNotifier stateNotifier2 = StateNotifier.this;
            z = message.arg1 == 1;
            synchronized (stateNotifier2.mHighPriorityListeners) {
                try {
                    Iterator it2 = stateNotifier2.mHighPriorityListeners.iterator();
                    while (it2.hasNext()) {
                        ((CoverStateListenerInfo) it2.next()).onCoverAttachStateChanged(z);
                    }
                } finally {
                }
            }
            synchronized (stateNotifier2.mListeners) {
                try {
                    Iterator it3 = stateNotifier2.mListeners.iterator();
                    while (it3.hasNext()) {
                        ((CoverStateListenerInfo) it3.next()).onCoverAttachStateChanged(z);
                    }
                } finally {
                }
            }
            Log.addLogString("CoverManager_", "send cover attach : " + z);
            if (stateNotifier2.mSendCoverAttachStateWakeLock.isHeld()) {
                stateNotifier2.mSendCoverAttachStateWakeLock.release();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LcdOffDisableListenerInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final int pid;
        public final IBinder token;
        public final int uid;

        public LcdOffDisableListenerInfo(
                IBinder iBinder, ComponentName componentName, int i, int i2) {
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.v("CoverManager_StateNotifier", "binderDied : binder = " + this.token);
            synchronized (StateNotifier.this.mLcdOffDisableListeners) {
                StateNotifier.this.mLcdOffDisableListeners.remove(this);
                StateNotifier.this.enableLcdOffByCoverIfPossibleLocked();
            }
            this.token.unlinkToDeath(this, 0);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface LcdOffDisabledByAppListener {
        void onLcdOffByCoverEnabled();
    }

    public StateNotifier(Looper looper, Context context) {
        this.mContext = context;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        this.mHandler = new H(looper);
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "CoverSwitchState");
        this.mSendCoverSwitchStateWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock newWakeLock2 = powerManager.newWakeLock(1, "CoverAttachState");
        this.mSendCoverAttachStateWakeLock = newWakeLock2;
        newWakeLock2.setReferenceCounted(false);
    }

    public final void addListenerToListLocked(
            IBinder iBinder, ComponentName componentName, int i, List list) {
        ArrayList arrayList = (ArrayList) list;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            CoverStateListenerInfo coverStateListenerInfo = (CoverStateListenerInfo) it.next();
            if (coverStateListenerInfo != null && iBinder.equals(coverStateListenerInfo.token)) {
                Log.e(
                        "CoverManager_StateNotifier",
                        "addListenerToListLocked : duplicated listener handle");
                return;
            }
        }
        CoverStateListenerInfo coverStateListenerInfo2 =
                new CoverStateListenerInfo(
                        iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid(), i);
        try {
            iBinder.linkToDeath(coverStateListenerInfo2, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        arrayList.add(coverStateListenerInfo2);
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println(" Current StateNotifier state:");
        synchronized (this.mListeners) {
            try {
                printWriter.println("  Live listeners (" + this.mListeners.size() + "):");
                Iterator it = this.mListeners.iterator();
                while (it.hasNext()) {
                    CoverStateListenerInfo coverStateListenerInfo =
                            (CoverStateListenerInfo) it.next();
                    if (coverStateListenerInfo != null) {
                        printWriter.println(
                                "    "
                                        + coverStateListenerInfo.component
                                        + " (pid="
                                        + coverStateListenerInfo.pid
                                        + " uid="
                                        + coverStateListenerInfo.uid
                                        + ")");
                    }
                }
                printWriter.println("  ");
            } finally {
            }
        }
        synchronized (this.mHighPriorityListeners) {
            try {
                printWriter.println(
                        "  Live high priority listeners ("
                                + this.mHighPriorityListeners.size()
                                + "):");
                Iterator it2 = this.mHighPriorityListeners.iterator();
                while (it2.hasNext()) {
                    CoverStateListenerInfo coverStateListenerInfo2 =
                            (CoverStateListenerInfo) it2.next();
                    if (coverStateListenerInfo2 != null) {
                        printWriter.println(
                                "    "
                                        + coverStateListenerInfo2.component
                                        + " (pid="
                                        + coverStateListenerInfo2.pid
                                        + " uid="
                                        + coverStateListenerInfo2.uid
                                        + ")");
                    }
                }
                printWriter.println("  ");
            } finally {
            }
        }
        synchronized (this.mLcdOffDisableListeners) {
            try {
                printWriter.println(" LCD Off disabled by app: " + this.mLcdOffDisabledByApp);
                printWriter.println(
                        " LCD Off listeners (" + this.mLcdOffDisableListeners.size() + "):");
                Iterator it3 = this.mLcdOffDisableListeners.iterator();
                while (it3.hasNext()) {
                    LcdOffDisableListenerInfo lcdOffDisableListenerInfo =
                            (LcdOffDisableListenerInfo) it3.next();
                    if (lcdOffDisableListenerInfo != null) {
                        printWriter.println(
                                "    "
                                        + lcdOffDisableListenerInfo.component
                                        + " (pid="
                                        + lcdOffDisableListenerInfo.pid
                                        + " uid="
                                        + lcdOffDisableListenerInfo.uid
                                        + ")");
                    }
                }
                printWriter.println("  ");
            } finally {
            }
        }
    }

    public final void enableLcdOffByCoverIfPossibleLocked() {
        if (this.mLcdOffDisableListeners.isEmpty()) {
            this.mLcdOffDisabledByApp = false;
            if (!this.mCoverOpen && this.mCoverType == 0) {
                Log.addLogString("CoverManager_", "goToSleep by cover close : enableLcdOff");
                this.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 20, 0);
            }
            LcdOffDisabledByAppListener lcdOffDisabledByAppListener =
                    this.mLcdOffDisabledByAppListener;
            if (lcdOffDisabledByAppListener != null) {
                int i = this.mCoverType;
                if (i == 7 || i == 11 || i == 14) {
                    lcdOffDisabledByAppListener.onLcdOffByCoverEnabled();
                }
            }
        }
    }

    public final void updateCoverAttachState(int i, boolean z, boolean z2) {
        boolean z3;
        this.mCoverType = i;
        this.mCoverOpen = z2;
        Feature.getInstance(this.mContext).getClass();
        if (!Feature.sIsSupportSecureCover) {
            if (i == 0) {
                Feature.getInstance(this.mContext).getClass();
                z3 = Feature.sIsSupportFlipCover;
            } else if (i == 11) {
                Feature.getInstance(this.mContext).getClass();
                z3 = Feature.sIsSupportNeonCover;
            } else if (i == 7) {
                Feature.getInstance(this.mContext).getClass();
                if (Feature.sIsSupportNfcLedCover && Feature.sIsNfcAuthSystemFeatureEnabled) {
                    z3 = true;
                }
                z3 = false;
            } else if (i != 8) {
                switch (i) {
                    case 14:
                        Feature.getInstance(this.mContext).getClass();
                        z3 = Feature.sIsSupportLEDBackCover;
                        break;
                    case 15:
                        Feature.getInstance(this.mContext).getClass();
                        z3 = Feature.sIsSupportClearSideViewCover;
                        break;
                    case 16:
                        Feature.getInstance(this.mContext).getClass();
                        z3 = Feature.sIsSupportMiniSviewWalletCover;
                        break;
                    case 17:
                        Feature.getInstance(this.mContext).getClass();
                        z3 = Feature.sIsSupportClearCameraViewCover;
                        break;
                    default:
                        z3 = false;
                        break;
                }
            } else {
                Feature.getInstance(this.mContext).getClass();
                z3 = Feature.sIsSupportClearCover;
            }
            if (!z3) {
                return;
            }
        }
        if (!this.mSendCoverAttachStateWakeLock.isHeld()) {
            this.mSendCoverAttachStateWakeLock.acquire();
        }
        if (CoverManagerUtils.isSupportWirelessCharge) {
            int i2 = this.mCoverType;
            if ((i2 == 7 || i2 == 8 || i2 == 14 || i2 == 15 || i2 == 16 || i2 == 0 || i2 == 17)
                    && z) {
                CoverManagerUtils.fileWriteInt(1);
            } else {
                CoverManagerUtils.fileWriteInt(0);
            }
        }
        this.mHandler.obtainMessage(1, z ? 1 : 0, 0).sendToTarget();
    }

    public final void updateCoverSwitchState(CoverState coverState) {
        this.mCoverOpen = coverState.getSwitchState();
        synchronized (this.mHighPriorityListeners) {
            try {
                Iterator it = this.mHighPriorityListeners.iterator();
                while (it.hasNext()) {
                    CoverStateListenerInfo coverStateListenerInfo =
                            (CoverStateListenerInfo) it.next();
                    coverStateListenerInfo.onCoverSwitchStateChanged(coverState);
                    coverStateListenerInfo.onCoverSwitchStateChanged(coverState.getSwitchState());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!this.mSendCoverSwitchStateWakeLock.isHeld()) {
            this.mSendCoverSwitchStateWakeLock.acquire();
        }
        this.mHandler
                .obtainMessage(0, coverState.getSwitchState() ? 1 : 0, 0, coverState)
                .sendToTarget();
    }

    public final void updatePowerState(int i, boolean z) {
        Log.d("CoverManager_StateNotifier", "updatePowerState : isOpen=" + z + ", type=" + i);
        this.mPowerManager.updateCoverState(z ^ true);
        if (i != 0) {
            if (i == 11) {
                Feature.getInstance(this.mContext).getClass();
                if (Feature.sIsSupportNeonCover && this.mPowerManager.isInteractive()) {
                    this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                    return;
                }
                return;
            }
            if (i != 100) {
                if (i != 7) {
                    if (i != 8) {
                        return;
                    }
                    Feature.getInstance(this.mContext).getClass();
                    if (Feature.sIsSupportClearCover && this.mPowerManager.isInteractive()) {
                        this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                        return;
                    }
                    return;
                }
                Feature.getInstance(this.mContext).getClass();
                if (Feature.sIsSupportNfcLedCover && Feature.sIsNfcAuthSystemFeatureEnabled) {
                    if (z) {
                        if (this.mPowerManager.isInteractive()) {
                            this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                            return;
                        } else {
                            this.mPowerManager.semWakeUp(
                                    SystemClock.uptimeMillis(), 103, "updatePowerState");
                            return;
                        }
                    }
                    if (this.mLcdOffDisabledByApp) {
                        synchronized (this.mLcdOffDisableListeners) {
                            try {
                                if (this.mLcdOffDisableListeners.isEmpty()) {
                                    enableLcdOffByCoverIfPossibleLocked();
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                }
                return;
            }
        }
        Feature.getInstance(this.mContext).getClass();
        if (!Feature.sIsSupportFlipCover
                || FactoryTest.isFactoryMode()
                || FactoryTest.isAutomaticTestMode(this.mContext)
                || FactoryTest.isRunningFactoryApp()) {
            return;
        }
        if (z) {
            if (this.mPowerManager.isInteractive()) {
                this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
            } else {
                this.mPowerManager.semWakeUp(SystemClock.uptimeMillis(), 103, "updatePowerState");
            }
            if (this.mGoToSleepRunnable != null) {
                this.mGoToSleepRunnable = null;
                return;
            }
            return;
        }
        if (!this.mLcdOffDisabledByApp) {
            if (!this.mBootComplete) {
                this.mGoToSleepRunnable = new StateNotifier$$ExternalSyntheticLambda0(this);
                return;
            } else {
                Log.addLogString(
                        "CoverManager_",
                        "goToSleep by cover close : mLcdOffDisabledByApp is false");
                this.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 20, 0);
                return;
            }
        }
        synchronized (this.mLcdOffDisableListeners) {
            try {
                if (this.mLcdOffDisableListeners.isEmpty()) {
                    this.mLcdOffDisabledByApp = false;
                    Log.addLogString(
                            "CoverManager_",
                            "goToSleep by cover close : mLcdOffDisableListeners is empty");
                    this.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 20, 0);
                } else {
                    Iterator it = this.mLcdOffDisableListeners.iterator();
                    while (it.hasNext()) {
                        LcdOffDisableListenerInfo lcdOffDisableListenerInfo =
                                (LcdOffDisableListenerInfo) it.next();
                        Log.d(
                                "CoverManager_StateNotifier",
                                "cover close: goToSleep disabled by: PID:"
                                        + lcdOffDisableListenerInfo.pid
                                        + " UID:"
                                        + lcdOffDisableListenerInfo.uid);
                    }
                }
            } finally {
            }
        }
    }
}
