package com.android.server.wm;

import android.app.IApplicationThread;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.CoreStatesChangeItem;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.am.ActivityManagerService;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.corestate.CoreStateCallback;
import com.samsung.android.server.corestate.CoreStateObserverController;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class CoreStateController implements CoreStateObserverController.Callback {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public static final String TAG = "CoreStateController";
    public final ActivityTaskManagerService mAtm;

    /* renamed from: mH */
    public Handler f1736mH;
    public CoreStateObserverController mObserverController;
    public final Object mLock = new Object();
    public final SparseArray mStatesForUser = new SparseArray();
    public final ArrayList mCallbacks = new ArrayList();

    public CoreStateController(ActivityTaskManagerService activityTaskManagerService) {
        if (DEBUG) {
            Slog.d(TAG, "CoreStateController()");
        }
        this.mAtm = activityTaskManagerService;
    }

    public void initializeLocked() {
        Slog.d(TAG, "initializeLocked()");
        this.f1736mH = new Handler(this.mAtm.f1734mH.getLooper());
        CoreStateObserverController coreStateObserverController = new CoreStateObserverController(this.mAtm.mContext, this, this.f1736mH);
        this.mObserverController = coreStateObserverController;
        coreStateObserverController.init();
        notifyCoreStatesChangedLocked(0, null);
    }

    public void registerCallbackLocked(CoreStateCallback coreStateCallback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.add(coreStateCallback);
        }
    }

    public void startUserLocked(int i, boolean z, boolean z2) {
        if (DEBUG) {
            Slog.d(TAG, "startUserLocked: u" + i + ", " + z + ", " + z2);
        }
        this.mObserverController.startUserLocked(i, z, z2);
        notifyCoreStatesChangedLocked(i, null);
    }

    public void stopUserLocked(int i, boolean z) {
        if (DEBUG) {
            Slog.d(TAG, "stopUserLocked(u" + i + ")");
        }
        this.mObserverController.stopUserLocked(i, z);
        removeCoreStates(i);
    }

    public Bundle getCoreStates(int i) {
        Bundle bundle;
        synchronized (this.mLock) {
            if (this.mStatesForUser.get(i) == null) {
                this.mStatesForUser.put(i, new Bundle());
            }
            bundle = (Bundle) ((Bundle) this.mStatesForUser.get(i)).clone();
        }
        return bundle;
    }

    public final void insertCoreStates(Bundle bundle, int i) {
        synchronized (this.mLock) {
            Bundle bundle2 = (Bundle) this.mStatesForUser.get(i);
            this.mStatesForUser.put(i, bundle.deepCopy());
            if (DEBUG) {
                Slog.d(TAG, "insertCoreStates: u" + i + ", prev=" + bundle2 + ", next=" + bundle);
            }
        }
    }

    public final void removeCoreStates(int i) {
        synchronized (this.mLock) {
            this.mStatesForUser.remove(i);
        }
    }

    @Override // com.samsung.android.server.corestate.CoreStateObserverController.Callback
    public void onCoreStateChanged(Bundle bundle, int i, boolean z, Runnable runnable) {
        insertCoreStates(bundle, i);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            if (z) {
                try {
                    notifyCoreStatesChangedLocked(i, runnable);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void notifyCoreStatesChangedLocked(int i, Runnable runnable) {
        Bundle coreStates = getCoreStates(i);
        boolean z = this.mAtm.mWindowManager.mCurrentUserId == i;
        if (z) {
            notifyToSystemThreadLocked(coreStates);
        }
        SparseArray pidMap = this.mAtm.mProcessMap.getPidMap();
        for (int size = pidMap.size() - 1; size >= 0; size--) {
            int keyAt = pidMap.keyAt(size);
            WindowProcessController windowProcessController = (WindowProcessController) pidMap.get(keyAt);
            if (windowProcessController.getPid() != ActivityManagerService.MY_PID && (windowProcessController.mUserId == i || (z && isPrimarySystemUIProcess(windowProcessController)))) {
                try {
                    IApplicationThread thread = windowProcessController.getThread();
                    if (thread != null) {
                        if (DEBUG) {
                            Slog.d(TAG, "notifyCoreStatesChangedLocked: schedule CoreStateChangeItem, pid=" + keyAt);
                        }
                        ClientTransaction.obtain(thread, (IBinder) null).addCallback(CoreStatesChangeItem.obtain(coreStates));
                        this.mAtm.getLifecycleManager().scheduleTransaction(thread, (IBinder) null, (ClientTransactionItem) CoreStatesChangeItem.obtain(coreStates));
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public final void notifyToSystemThreadLocked(Bundle bundle) {
        int updateFrom = MultiWindowCoreState.getInstance().updateFrom(bundle);
        synchronized (this.mCallbacks) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((CoreStateCallback) it.next()).onCoreStateChanged(updateFrom);
            }
        }
    }

    public static boolean isPrimarySystemUIProcess(WindowProcessController windowProcessController) {
        return windowProcessController.mUserId == 0 && "com.android.systemui".equals(windowProcessController.mName);
    }

    public void setSharedPreferenceEdited(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mObserverController.onCoreStateChanged(i);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setVolatileState(String str, Object obj, int i, boolean z, boolean z2, Runnable runnable) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mObserverController.setVolatileState(str, obj, i, z, z2, runnable);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }
}
