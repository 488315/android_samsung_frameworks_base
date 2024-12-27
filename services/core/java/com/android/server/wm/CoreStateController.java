package com.android.server.wm;

import android.app.IApplicationThread;
import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.CoreStatesChangeItem;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.os.BackgroundThread;
import com.android.server.am.ActivityManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.server.corestate.CoreStateObserverController;
import com.samsung.android.server.corestate.CoreStateSettingObserver;
import com.samsung.android.server.corestate.CoreStateVolatileObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CoreStateController {
    public final ActivityTaskManagerService mAtm;
    public CoreStateObserverController mObserverController;
    public final Object mLock = new Object();
    public final SparseArray mStatesForUser = new SparseArray();
    public final ArrayList mCallbacks = new ArrayList();

    public CoreStateController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    public final Bundle getCoreStates(int i) {
        Bundle bundle;
        synchronized (this.mLock) {
            try {
                if (this.mStatesForUser.get(i) == null) {
                    this.mStatesForUser.put(i, new Bundle());
                }
                bundle = (Bundle) ((Bundle) this.mStatesForUser.get(i)).clone();
            } catch (Throwable th) {
                throw th;
            }
        }
        return bundle;
    }

    public final void notifyCoreStatesChangedLocked(int i, MultiWindowEnableController$$ExternalSyntheticLambda0 multiWindowEnableController$$ExternalSyntheticLambda0) {
        int i2;
        Bundle coreStates = getCoreStates(i);
        boolean z = this.mAtm.mWindowManager.mCurrentUserId == i;
        if (z) {
            int updateFrom = MultiWindowCoreState.getInstance().updateFrom(coreStates);
            synchronized (this.mCallbacks) {
                try {
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((MultiWindowEnableController) it.next()).onCoreStateChanged(updateFrom);
                    }
                } finally {
                }
            }
        }
        SparseArray sparseArray = this.mAtm.mProcessMap.mPidMap;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            WindowProcessController windowProcessController = (WindowProcessController) sparseArray.get(sparseArray.keyAt(size));
            if (windowProcessController.mPid != ActivityManagerService.MY_PID && ((i2 = windowProcessController.mUserId) == i || (z && i2 == 0 && Constants.SYSTEMUI_PACKAGE_NAME.equals(windowProcessController.mName)))) {
                try {
                    IApplicationThread iApplicationThread = windowProcessController.mThread;
                    if (iApplicationThread != null) {
                        ClientTransaction obtain = ClientTransaction.obtain(iApplicationThread);
                        obtain.addTransactionItem(CoreStatesChangeItem.obtain(coreStates));
                        this.mAtm.mLifecycleManager.getClass();
                        ClientLifecycleManager.scheduleTransaction(obtain);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        if (multiWindowEnableController$$ExternalSyntheticLambda0 != null) {
            multiWindowEnableController$$ExternalSyntheticLambda0.run();
        }
    }

    public final void onCoreStateChanged(Bundle bundle, int i, boolean z, MultiWindowEnableController$$ExternalSyntheticLambda0 multiWindowEnableController$$ExternalSyntheticLambda0) {
        synchronized (this.mLock) {
            this.mStatesForUser.put(i, bundle.deepCopy());
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            if (z) {
                try {
                    notifyCoreStatesChangedLocked(i, multiWindowEnableController$$ExternalSyntheticLambda0);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setSharedPreferenceEdited() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mObserverController.sendCoreState(false, 0, null);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void setVolatileState(String str, Object obj, int i, boolean z, boolean z2, MultiWindowEnableController$$ExternalSyntheticLambda0 multiWindowEnableController$$ExternalSyntheticLambda0) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mObserverController.setVolatileState(str, obj, i, z, z2, multiWindowEnableController$$ExternalSyntheticLambda0);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void startUserLocked(int i, boolean z, boolean z2) {
        CoreStateSettingObserver coreStateSettingObserver;
        CoreStateObserverController coreStateObserverController = this.mObserverController;
        Slog.d("CoreStateObserverController", "startUserLocked: " + coreStateObserverController.mStartedUserIds + ", u" + i);
        coreStateObserverController.mStartedUserIds.add(Integer.valueOf(i));
        if (z2 && (coreStateSettingObserver = coreStateObserverController.mSettingObserver) != null) {
            coreStateSettingObserver.beginObserveCoreStateSettingsForSingleUser(i);
        }
        if (z2 || z) {
            coreStateObserverController.sendCoreState(true, i, null);
        }
        notifyCoreStatesChangedLocked(i, null);
    }

    public final void stopUserLocked(int i, boolean z) {
        CoreStateVolatileObserver coreStateVolatileObserver;
        CoreStateObserverController coreStateObserverController = this.mObserverController;
        Slog.d("CoreStateObserverController", "stopUserLocked: " + coreStateObserverController.mStartedUserIds + ", u" + i);
        coreStateObserverController.mStartedUserIds.remove(Integer.valueOf(i));
        final CoreStateSettingObserver coreStateSettingObserver = coreStateObserverController.mSettingObserver;
        if (coreStateSettingObserver != null) {
            ArraySet arraySet = coreStateObserverController.mStartedUserIds;
            Slog.d("CoreStateSettingObserver", "endObserveCoreStateSettingsForSingleUser(u" + i + ")");
            BackgroundThread.getHandler().post(new Runnable() { // from class: com.samsung.android.server.corestate.CoreStateSettingObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CoreStateSettingObserver coreStateSettingObserver2 = CoreStateSettingObserver.this;
                    coreStateSettingObserver2.mContext.getContentResolver().unregisterContentObserver(coreStateSettingObserver2);
                }
            });
            coreStateSettingObserver.beginObserveCoreStateSettings();
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (intValue != 0 && intValue != i) {
                    coreStateSettingObserver.beginObserveCoreStateSettingsForSingleUser(intValue);
                }
            }
        }
        coreStateObserverController.mStateForUser.remove(i);
        if (z && (coreStateVolatileObserver = coreStateObserverController.mVolatileObserver) != null) {
            Iterator it2 = ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository).entrySet().iterator();
            while (it2.hasNext()) {
                SparseArray sparseArray = (SparseArray) ((Map.Entry) it2.next()).getValue();
                if (sparseArray != null) {
                    sparseArray.remove(i);
                }
            }
        }
        synchronized (this.mLock) {
            this.mStatesForUser.remove(i);
        }
    }
}
