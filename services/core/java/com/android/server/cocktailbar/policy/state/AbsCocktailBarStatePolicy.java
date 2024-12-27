package com.android.server.cocktailbar.policy.state;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.hal.GnssNative;

import com.samsung.android.cocktailbar.CocktailBarStateInfo;
import com.samsung.android.util.SemLog;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AbsCocktailBarStatePolicy {
    public CocktailBarStateHandler mCocktailBarStateHandler;
    public HandlerThread mCocktailBarStateThread;
    public final CocktailBarStatePolicy$OnCocktailBarStateListener mListener;
    public final CocktailBarStateInfo mStateInfo = new CocktailBarStateInfo(2);
    public final int mWindowType = 1;
    public final SparseArray mLockMap = new SparseArray();
    public final Object mLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.cocktailbar.policy.state.AbsCocktailBarStatePolicy$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(-4);
            Process.setCanSelfBackground(false);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CocktailBarStateHandler extends Handler {
        public CocktailBarStateHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SemLog.i("AbsCocktailBarStatePolicy", "handleMessage: entry what = " + message.what);
            int i = message.what;
            if (i == 1) {
                AbsCocktailBarStatePolicy absCocktailBarStatePolicy =
                        AbsCocktailBarStatePolicy.this;
                int i2 = message.arg1;
                OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy =
                        (OverlayCocktailBarStatePolicy) absCocktailBarStatePolicy;
                if (OverlayCocktailBarStatePolicy.DEBUG) {
                    Slog.i(
                            "OverlayCocktailBarStatePolicy",
                            "handleUpdateVisibility: mVisibility = "
                                    + overlayCocktailBarStatePolicy.mStateInfo.visibility
                                    + " visibility = "
                                    + i2);
                }
                if (overlayCocktailBarStatePolicy.mStateInfo.visibility != i2) {
                    CocktailBarStateInfo cocktailBarStateInfo = new CocktailBarStateInfo(i2);
                    cocktailBarStateInfo.changeFlag = 1 | cocktailBarStateInfo.changeFlag;
                    ((CocktailBarStatePolicyController) overlayCocktailBarStatePolicy.mListener)
                            .notifyCocktailBarState(cocktailBarStateInfo);
                    overlayCocktailBarStatePolicy.mStateInfo.visibility = i2;
                    return;
                }
                return;
            }
            if (i == 4) {
                AbsCocktailBarStatePolicy absCocktailBarStatePolicy2 =
                        AbsCocktailBarStatePolicy.this;
                int i3 = message.arg1;
                OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy2 =
                        (OverlayCocktailBarStatePolicy) absCocktailBarStatePolicy2;
                if (OverlayCocktailBarStatePolicy.DEBUG) {
                    Slog.i(
                            "OverlayCocktailBarStatePolicy",
                            "handleUpdatePosition: mPosition = "
                                    + overlayCocktailBarStatePolicy2.mStateInfo.position
                                    + " position = "
                                    + i3);
                }
                if (overlayCocktailBarStatePolicy2.mStateInfo.position != i3) {
                    CocktailBarStateInfo cocktailBarStateInfo2 =
                            new CocktailBarStateInfo(
                                    overlayCocktailBarStatePolicy2.mStateInfo.visibility);
                    cocktailBarStateInfo2.position = i3;
                    cocktailBarStateInfo2.changeFlag |= 4;
                    ((CocktailBarStatePolicyController) overlayCocktailBarStatePolicy2.mListener)
                            .notifyCocktailBarState(cocktailBarStateInfo2);
                }
                AbsCocktailBarStatePolicy.this.mStateInfo.position = message.arg1;
                return;
            }
            if (i == 6) {
                AbsCocktailBarStatePolicy absCocktailBarStatePolicy3 =
                        AbsCocktailBarStatePolicy.this;
                int i4 = message.arg1;
                Object obj = message.obj;
                String str = obj != null ? (String) obj : null;
                OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy3 =
                        (OverlayCocktailBarStatePolicy) absCocktailBarStatePolicy3;
                overlayCocktailBarStatePolicy3.getClass();
                CocktailBarStateInfo cocktailBarStateInfo3 =
                        new CocktailBarStateInfo(
                                overlayCocktailBarStatePolicy3.mStateInfo.visibility);
                cocktailBarStateInfo3.windowType = i4;
                cocktailBarStateInfo3.changeFlag |= 128;
                CocktailBarStatePolicyController cocktailBarStatePolicyController =
                        (CocktailBarStatePolicyController) overlayCocktailBarStatePolicy3.mListener;
                if (CocktailBarStatePolicyController.DEBUG) {
                    cocktailBarStatePolicyController.getClass();
                    StringBuilder sb =
                            new StringBuilder(
                                    "notifyCocktailBarStateExceptCallingPackage: visibility = ");
                    sb.append(cocktailBarStateInfo3.visibility);
                    sb.append(" windowType = ");
                    sb.append(cocktailBarStateInfo3.windowType);
                    sb.append(" changeFlag = ");
                    SystemServiceManager$$ExternalSyntheticOutline0.m(
                            sb,
                            cocktailBarStateInfo3.changeFlag,
                            "CocktailBarStatePolicyController");
                }
                synchronized (cocktailBarStatePolicyController.mStateListeners) {
                    try {
                        if (cocktailBarStateInfo3.changeFlag == 128) {
                            Iterator it =
                                    cocktailBarStatePolicyController.mStateListeners.iterator();
                            while (it.hasNext()) {
                                CocktailBarStatePolicyController.CocktailBarStateListenerInfo
                                        cocktailBarStateListenerInfo =
                                                (CocktailBarStatePolicyController
                                                                .CocktailBarStateListenerInfo)
                                                        it.next();
                                if (str != null
                                        && str.equals(
                                                cocktailBarStateListenerInfo.component
                                                        .getPackageName())) {}
                                cocktailBarStateListenerInfo.onCocktailBarStateChanged(
                                        cocktailBarStateInfo3);
                            }
                        } else {
                            Iterator it2 =
                                    cocktailBarStatePolicyController.mStateListeners.iterator();
                            while (it2.hasNext()) {
                                ((CocktailBarStatePolicyController.CocktailBarStateListenerInfo)
                                                it2.next())
                                        .onCocktailBarStateChanged(cocktailBarStateInfo3);
                            }
                        }
                    } finally {
                    }
                }
                AbsCocktailBarStatePolicy.this.mStateInfo.windowType = message.arg1;
                return;
            }
            if (i == 49) {
                boolean z = message.arg1 == 1;
                OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy4 =
                        (OverlayCocktailBarStatePolicy) AbsCocktailBarStatePolicy.this;
                if (overlayCocktailBarStatePolicy4.mStateInfo.activate != z) {
                    CocktailBarStateInfo cocktailBarStateInfo4 =
                            new CocktailBarStateInfo(
                                    overlayCocktailBarStatePolicy4.mStateInfo.visibility);
                    cocktailBarStateInfo4.activate = z;
                    cocktailBarStateInfo4.changeFlag |= 64;
                    ((CocktailBarStatePolicyController) overlayCocktailBarStatePolicy4.mListener)
                            .notifyCocktailBarState(cocktailBarStateInfo4);
                }
                AbsCocktailBarStatePolicy.this.mStateInfo.activate = z;
                return;
            }
            if (i != 51) {
                if (i != 101) {
                    return;
                }
                AbsCocktailBarStatePolicy absCocktailBarStatePolicy4 =
                        AbsCocktailBarStatePolicy.this;
                synchronized (absCocktailBarStatePolicy4.mLock) {
                    try {
                        HandlerThread handlerThread =
                                absCocktailBarStatePolicy4.mCocktailBarStateThread;
                        if (handlerThread != null) {
                            handlerThread.quitSafely();
                            absCocktailBarStatePolicy4.mCocktailBarStateThread = null;
                            absCocktailBarStatePolicy4.mCocktailBarStateHandler = null;
                        }
                    } finally {
                    }
                }
                return;
            }
            AbsCocktailBarStatePolicy absCocktailBarStatePolicy5 = AbsCocktailBarStatePolicy.this;
            IBinder iBinder = (IBinder) message.obj;
            OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy5 =
                    (OverlayCocktailBarStatePolicy) absCocktailBarStatePolicy5;
            if (OverlayCocktailBarStatePolicy.DEBUG) {
                overlayCocktailBarStatePolicy5.getClass();
                Slog.i("OverlayCocktailBarStatePolicy", "handleNotifyCurrentStateToBinder");
            }
            CocktailBarStateInfo clone = overlayCocktailBarStatePolicy5.mStateInfo.clone();
            clone.lockState &= GnssNative.GNSS_AIDING_TYPE_ALL;
            clone.changeFlag = 77;
            CocktailBarStatePolicyController cocktailBarStatePolicyController2 =
                    (CocktailBarStatePolicyController) overlayCocktailBarStatePolicy5.mListener;
            if (CocktailBarStatePolicyController.DEBUG) {
                cocktailBarStatePolicyController2.getClass();
                StringBuilder sb2 =
                        new StringBuilder("notifyCocktailBarStateToBinder: visibility = ");
                sb2.append(clone.visibility);
                sb2.append(" position = ");
                sb2.append(clone.position);
                sb2.append(" showtimeout = ");
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        sb2, clone.showTimeout, "CocktailBarStatePolicyController");
            }
            synchronized (cocktailBarStatePolicyController2.mStateListeners) {
                try {
                    Iterator it3 = cocktailBarStatePolicyController2.mStateListeners.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        CocktailBarStatePolicyController.CocktailBarStateListenerInfo
                                cocktailBarStateListenerInfo2 =
                                        (CocktailBarStatePolicyController
                                                        .CocktailBarStateListenerInfo)
                                                it3.next();
                        if (iBinder.equals(cocktailBarStateListenerInfo2.token)) {
                            cocktailBarStateListenerInfo2.onCocktailBarStateChanged(clone);
                            break;
                        }
                    }
                } finally {
                }
            }
        }
    }

    public AbsCocktailBarStatePolicy(
            CocktailBarStatePolicyController cocktailBarStatePolicyController) {
        this.mListener = cocktailBarStatePolicyController;
    }

    public final void enqueueMessageLocked(Message message, boolean z) {
        if (this.mCocktailBarStateThread == null) {
            HandlerThread handlerThread = new HandlerThread("CocktailBarVisibility");
            this.mCocktailBarStateThread = handlerThread;
            handlerThread.start();
            synchronized (this.mLock) {
                CocktailBarStateHandler cocktailBarStateHandler =
                        new CocktailBarStateHandler(this.mCocktailBarStateThread.getLooper());
                this.mCocktailBarStateHandler = cocktailBarStateHandler;
                cocktailBarStateHandler.post(new AnonymousClass1());
            }
        }
        if (z) {
            int i = message.what;
            CocktailBarStateHandler cocktailBarStateHandler2 = this.mCocktailBarStateHandler;
            if (cocktailBarStateHandler2 != null) {
                cocktailBarStateHandler2.removeMessages(i);
            }
        }
        CocktailBarStateHandler cocktailBarStateHandler3 = this.mCocktailBarStateHandler;
        if (cocktailBarStateHandler3 != null) {
            cocktailBarStateHandler3.sendMessageDelayed(message, 0L);
            this.mCocktailBarStateHandler.removeMessages(101);
            this.mCocktailBarStateHandler.sendEmptyMessageDelayed(101, 5000L);
        }
    }

    public final void updateActivate(boolean z) {
        synchronized (this.mLock) {
            Message obtain = Message.obtain();
            obtain.what = 49;
            obtain.arg1 = z ? 1 : 0;
            enqueueMessageLocked(obtain, true);
        }
    }

    public final void updateCocktailBarWindowType(int i, String str) {
        synchronized (this.mLock) {
            Message obtain = Message.obtain();
            obtain.what = 6;
            obtain.arg1 = i;
            obtain.obj = str;
            enqueueMessageLocked(obtain, true);
        }
    }
}
