package com.samsung.android.server.corestate;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.server.wm.CoreStateController;
import com.android.server.wm.MultiWindowEnableController$$ExternalSyntheticLambda0;

import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CoreStateObserverController {
    public final CoreStateController mCallback;
    public final CoreStatePreferenceObserver mPreferenceObserver;
    public final CoreStateSettingObserver mSettingObserver;
    public final CoreStateSystemFeatureObserver mSystemFeatureObserver;
    public final CoreStateVolatileObserver mVolatileObserver;
    public final SparseArray mStateForUser = new SparseArray();
    public final ArraySet mStartedUserIds = new ArraySet();

    public CoreStateObserverController(
            Context context, CoreStateController coreStateController, Handler handler) {
        this.mCallback = coreStateController;
        this.mSettingObserver = new CoreStateSettingObserver(context, handler, this);
        this.mSystemFeatureObserver = new CoreStateSystemFeatureObserver(context);
        this.mVolatileObserver = new CoreStateVolatileObserver(handler, this);
        this.mPreferenceObserver = new CoreStatePreferenceObserver(context);
    }

    public final Bundle copyCoreStateLocked(int i) {
        Bundle bundle = (Bundle) this.mStateForUser.get(i);
        return bundle == null ? new Bundle() : (Bundle) bundle.clone();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:134:0x023d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01e4 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int populateCoreState(int r15) {
        /*
            Method dump skipped, instructions count: 580
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.corestate.CoreStateObserverController.populateCoreState(int):int");
    }

    public final void sendCoreState(
            boolean z,
            int i,
            MultiWindowEnableController$$ExternalSyntheticLambda0
                    multiWindowEnableController$$ExternalSyntheticLambda0) {
        int populateCoreState = populateCoreState(i);
        CoreStateController coreStateController = this.mCallback;
        if (z) {
            coreStateController.onCoreStateChanged(
                    copyCoreStateLocked(i),
                    i,
                    false,
                    multiWindowEnableController$$ExternalSyntheticLambda0);
            return;
        }
        if ((populateCoreState & 1) == 0) {
            if ((populateCoreState & 2) != 0) {
                coreStateController.onCoreStateChanged(
                        copyCoreStateLocked(i),
                        i,
                        true,
                        multiWindowEnableController$$ExternalSyntheticLambda0);
            }
        } else {
            Iterator it = this.mStartedUserIds.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if (intValue != i) {
                    populateCoreState(intValue);
                }
                coreStateController.onCoreStateChanged(
                        copyCoreStateLocked(intValue),
                        intValue,
                        true,
                        multiWindowEnableController$$ExternalSyntheticLambda0);
            }
        }
    }

    public final void setVolatileState(
            String str,
            Object obj,
            final int i,
            boolean z,
            boolean z2,
            final MultiWindowEnableController$$ExternalSyntheticLambda0
                    multiWindowEnableController$$ExternalSyntheticLambda0) {
        final CoreStateVolatileObserver coreStateVolatileObserver = this.mVolatileObserver;
        if (coreStateVolatileObserver != null) {
            if (((SparseArray)
                            ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                                    .get(str))
                    == null) {
                if (obj instanceof String) {
                    ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                            .put(str, new SparseArray());
                } else if (obj instanceof Integer) {
                    ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                            .put(str, new SparseArray());
                } else if (obj instanceof Float) {
                    ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                            .put(str, new SparseArray());
                } else if (obj instanceof Long) {
                    ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                            .put(str, new SparseArray());
                } else {
                    if (!(obj instanceof Boolean)) {
                        return;
                    }
                    ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                            .put(str, new SparseArray());
                }
            }
            if (obj instanceof String) {
                ((SparseArray)
                                ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                                        .get(str))
                        .put(i, (String) obj);
            } else if (obj instanceof Integer) {
                ((SparseArray)
                                ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                                        .get(str))
                        .put(i, (Integer) obj);
            } else if (obj instanceof Float) {
                ((SparseArray)
                                ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                                        .get(str))
                        .put(i, (Float) obj);
            } else if (obj instanceof Long) {
                ((SparseArray)
                                ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                                        .get(str))
                        .put(i, (Long) obj);
            } else if (obj instanceof Boolean) {
                ((SparseArray)
                                ((HashMap) coreStateVolatileObserver.mVolatileStatesRepository)
                                        .get(str))
                        .put(i, (Boolean) obj);
            }
            if (z) {
                if (z2) {
                    coreStateVolatileObserver.mController.sendCoreState(
                            false, i, multiWindowEnableController$$ExternalSyntheticLambda0);
                } else {
                    coreStateVolatileObserver.mHandler.post(
                            new Runnable() { // from class:
                                             // com.samsung.android.server.corestate.CoreStateVolatileObserver$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CoreStateVolatileObserver coreStateVolatileObserver2 =
                                            CoreStateVolatileObserver.this;
                                    coreStateVolatileObserver2.mController.sendCoreState(
                                            false,
                                            i,
                                            (MultiWindowEnableController$$ExternalSyntheticLambda0)
                                                    multiWindowEnableController$$ExternalSyntheticLambda0);
                                }
                            });
                }
            }
        }
    }
}
