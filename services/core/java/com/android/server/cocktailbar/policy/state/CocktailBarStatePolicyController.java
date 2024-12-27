package com.android.server.cocktailbar.policy.state;

import android.content.ComponentName;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;

import com.samsung.android.cocktailbar.CocktailBarStateInfo;
import com.samsung.android.cocktailbar.ICocktailBarStateCallback;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CocktailBarStatePolicyController
        implements CocktailBarStatePolicy$OnCocktailBarStateListener {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static CocktailBarStatePolicyController mInstance = null;
    public String mPackageName;
    public OverlayCocktailBarStatePolicy mPolicy;
    public ArrayList mStateListeners;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CocktailBarStateListenerInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final int pid;
        public final IBinder token;
        public final int uid;

        public CocktailBarStateListenerInfo(
                IBinder iBinder, ComponentName componentName, int i, int i2) {
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean z = CocktailBarStatePolicyController.DEBUG;
            Slog.v("CocktailBarStatePolicyController", "binderDied : binder = " + this.token);
            synchronized (CocktailBarStatePolicyController.this.mStateListeners) {
                try {
                    String str = CocktailBarStatePolicyController.this.mPackageName;
                    if (str != null && str.equals(this.component.getPackageName())) {
                        CocktailBarStatePolicyController cocktailBarStatePolicyController =
                                CocktailBarStatePolicyController.this;
                        cocktailBarStatePolicyController.mPackageName = null;
                        cocktailBarStatePolicyController.mPolicy.updateCocktailBarWindowType(
                                1, null);
                    }
                    CocktailBarStatePolicyController.this.mStateListeners.remove(this);
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final String dump() {
            StringBuilder sb = new StringBuilder("[CocktailBarStateListener: component:(");
            sb.append(this.component.toString());
            sb.append(") pid:(");
            sb.append(this.pid);
            sb.append(") uid:(");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.uid, sb, ")]");
        }

        public final void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo) {
            IBinder iBinder = this.token;
            if (iBinder == null) {
                boolean z = CocktailBarStatePolicyController.DEBUG;
                Slog.w(
                        "CocktailBarStatePolicyController",
                        "onCocktailBarStateChanged : token is null");
                return;
            }
            try {
                ICocktailBarStateCallback asInterface =
                        ICocktailBarStateCallback.Stub.asInterface(iBinder);
                if (asInterface != null) {
                    asInterface.onCocktailBarStateChanged(cocktailBarStateInfo);
                }
            } catch (RemoteException e) {
                boolean z2 = CocktailBarStatePolicyController.DEBUG;
                Slog.e(
                        "CocktailBarStatePolicyController",
                        "onCocktailBarStateChanged : RemoteException : ",
                        e);
            }
        }
    }

    public static synchronized CocktailBarStatePolicyController getInstance() {
        CocktailBarStatePolicyController cocktailBarStatePolicyController;
        synchronized (CocktailBarStatePolicyController.class) {
            try {
                if (mInstance == null) {
                    CocktailBarStatePolicyController cocktailBarStatePolicyController2 =
                            new CocktailBarStatePolicyController();
                    cocktailBarStatePolicyController2.mPackageName = null;
                    cocktailBarStatePolicyController2.mStateListeners = new ArrayList();
                    cocktailBarStatePolicyController2.mPolicy =
                            new OverlayCocktailBarStatePolicy(cocktailBarStatePolicyController2);
                    mInstance = cocktailBarStatePolicyController2;
                }
                cocktailBarStatePolicyController = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return cocktailBarStatePolicyController;
    }

    public final String dump() {
        StringBuilder sb = new StringBuilder("[CocktailBarStatePolicy] ");
        sb.append(this.mPackageName);
        sb.append("\n");
        synchronized (this.mStateListeners) {
            try {
                Iterator it = this.mStateListeners.iterator();
                while (it.hasNext()) {
                    sb.append(((CocktailBarStateListenerInfo) it.next()).dump());
                    sb.append("\n");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        OverlayCocktailBarStatePolicy overlayCocktailBarStatePolicy = this.mPolicy;
        StringBuilder m =
                Preconditions$$ExternalSyntheticOutline0.m(
                        "[LockState : " + overlayCocktailBarStatePolicy.mLockMap.toString(), " : ");
        m.append(overlayCocktailBarStatePolicy.mStateInfo.lockState);
        StringBuilder m2 =
                Preconditions$$ExternalSyntheticOutline0.m(m.toString(), " Visibility : ");
        m2.append(overlayCocktailBarStatePolicy.mStateInfo.visibility);
        StringBuilder m3 =
                Preconditions$$ExternalSyntheticOutline0.m(
                        m2.toString(), " CocktailBarWindowType : ");
        m3.append(overlayCocktailBarStatePolicy.mStateInfo.windowType);
        StringBuilder m4 =
                Preconditions$$ExternalSyntheticOutline0.m(m3.toString(), " WindowType : ");
        m4.append(overlayCocktailBarStatePolicy.mWindowType);
        sb.append(m4.toString());
        return sb.toString();
    }

    public final void notifyCocktailBarState(CocktailBarStateInfo cocktailBarStateInfo) {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("notifyCocktailBarState: visibility = ");
            sb.append(cocktailBarStateInfo.visibility);
            sb.append(" position = ");
            sb.append(cocktailBarStateInfo.position);
            sb.append(" showtimeout = ");
            sb.append(cocktailBarStateInfo.showTimeout);
            sb.append(" mode = ");
            sb.append(cocktailBarStateInfo.mode);
            sb.append(" activate = ");
            sb.append(cocktailBarStateInfo.activate);
            sb.append(" windowType = ");
            sb.append(cocktailBarStateInfo.windowType);
            sb.append(" changeFlag = ");
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    sb, cocktailBarStateInfo.changeFlag, "CocktailBarStatePolicyController");
        }
        synchronized (this.mStateListeners) {
            try {
                if (cocktailBarStateInfo.changeFlag == 128) {
                    Iterator it = this.mStateListeners.iterator();
                    while (it.hasNext()) {
                        CocktailBarStateListenerInfo cocktailBarStateListenerInfo =
                                (CocktailBarStateListenerInfo) it.next();
                        String str = this.mPackageName;
                        if (str != null
                                && str.equals(
                                        cocktailBarStateListenerInfo.component.getPackageName())) {}
                        cocktailBarStateListenerInfo.onCocktailBarStateChanged(
                                cocktailBarStateInfo);
                    }
                } else {
                    Iterator it2 = this.mStateListeners.iterator();
                    while (it2.hasNext()) {
                        ((CocktailBarStateListenerInfo) it2.next())
                                .onCocktailBarStateChanged(cocktailBarStateInfo);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
