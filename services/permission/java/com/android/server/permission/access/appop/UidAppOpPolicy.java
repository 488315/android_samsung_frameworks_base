package com.android.server.permission.access.appop;

import android.app.AppOpsManager;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.permission.access.AccessUri;
import com.android.server.permission.access.GetStateScope;
import com.android.server.permission.access.MutateStateScope;
import com.android.server.permission.access.UserState;
import com.android.server.permission.access.WritableState;
import com.android.server.permission.access.collection.IndexedListSet;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

/* compiled from: UidAppOpPolicy.kt */
/* loaded from: classes2.dex */
public final class UidAppOpPolicy extends BaseAppOpPolicy {
    public volatile IndexedListSet onAppOpModeChangedListeners;
    public final Object onAppOpModeChangedListenersLock;

    @Override // com.android.server.permission.access.SchemePolicy
    public String getSubjectScheme() {
        return "uid";
    }

    public UidAppOpPolicy() {
        super(new UidAppOpPersistence());
        this.onAppOpModeChangedListeners = new IndexedListSet();
        this.onAppOpModeChangedListenersLock = new Object();
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public int getDecision(GetStateScope getStateScope, AccessUri accessUri, AccessUri accessUri2) {
        Intrinsics.checkNotNull(accessUri, "null cannot be cast to non-null type com.android.server.permission.access.UidUri");
        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
        Intrinsics.checkNotNull(accessUri2, "null cannot be cast to non-null type com.android.server.permission.access.AppOpUri");
        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri2);
        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
        throw null;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void setDecision(MutateStateScope mutateStateScope, AccessUri accessUri, AccessUri accessUri2, int i) {
        Intrinsics.checkNotNull(accessUri, "null cannot be cast to non-null type com.android.server.permission.access.UidUri");
        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
        Intrinsics.checkNotNull(accessUri2, "null cannot be cast to non-null type com.android.server.permission.access.AppOpUri");
        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri2);
        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
        throw null;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onStateMutated(GetStateScope getStateScope) {
        IndexedListSet indexedListSet = this.onAppOpModeChangedListeners;
        if (indexedListSet.size() <= 0) {
            return;
        }
        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(indexedListSet.elementAt(0));
        throw null;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onAppIdRemoved(MutateStateScope mutateStateScope, int i) {
        SparseArray userStates = mutateStateScope.getNewState().getUserStates();
        int size = userStates.size();
        for (int i2 = 0; i2 < size; i2++) {
            userStates.keyAt(i2);
            UserState userState = (UserState) userStates.valueAt(i2);
            userState.getUidAppOpModes().remove(i);
            WritableState.requestWrite$default(userState, false, 1, null);
        }
    }

    public final ArrayMap getAppOpModes(GetStateScope getStateScope, int i, int i2) {
        return (ArrayMap) ((UserState) getStateScope.getState().getUserStates().get(i2)).getUidAppOpModes().get(i);
    }

    public final boolean removeAppOpModes(MutateStateScope mutateStateScope, int i, int i2) {
        UserState userState = (UserState) mutateStateScope.getNewState().getUserStates().get(i2);
        boolean z = userState.getUidAppOpModes().removeReturnOld(i) != null;
        if (z) {
            WritableState.requestWrite$default(userState, false, 1, null);
        }
        return z;
    }

    public final int getAppOpMode(GetStateScope getStateScope, int i, int i2, String str) {
        int indexOfKey;
        ArrayMap arrayMap = (ArrayMap) ((UserState) getStateScope.getState().getUserStates().get(i2)).getUidAppOpModes().get(i);
        Object valueOf = Integer.valueOf(AppOpsManager.opToDefaultMode(str));
        if (arrayMap != null && (indexOfKey = arrayMap.indexOfKey(str)) >= 0) {
            valueOf = arrayMap.valueAt(indexOfKey);
        }
        return ((Number) valueOf).intValue();
    }

    public final boolean setAppOpMode(MutateStateScope mutateStateScope, int i, int i2, String str, int i3) {
        int indexOfKey;
        UserState userState = (UserState) mutateStateScope.getNewState().getUserStates().get(i2);
        SparseArray uidAppOpModes = userState.getUidAppOpModes();
        ArrayMap arrayMap = (ArrayMap) uidAppOpModes.get(i);
        int opToDefaultMode = AppOpsManager.opToDefaultMode(str);
        Object valueOf = Integer.valueOf(opToDefaultMode);
        if (arrayMap != null && (indexOfKey = arrayMap.indexOfKey(str)) >= 0) {
            valueOf = arrayMap.valueAt(indexOfKey);
        }
        if (((Number) valueOf).intValue() == i3) {
            return false;
        }
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            uidAppOpModes.set(i, arrayMap);
        }
        Integer valueOf2 = Integer.valueOf(i3);
        Integer valueOf3 = Integer.valueOf(opToDefaultMode);
        int indexOfKey2 = arrayMap.indexOfKey(str);
        if (indexOfKey2 >= 0) {
            if (!Intrinsics.areEqual(valueOf2, arrayMap.valueAt(indexOfKey2))) {
                if (Intrinsics.areEqual(valueOf2, valueOf3)) {
                    arrayMap.removeAt(indexOfKey2);
                } else {
                    arrayMap.setValueAt(indexOfKey2, valueOf2);
                }
            }
        } else if (!Intrinsics.areEqual(valueOf2, valueOf3)) {
            arrayMap.put(str, valueOf2);
        }
        if (arrayMap.isEmpty()) {
            uidAppOpModes.remove(i);
        }
        WritableState.requestWrite$default(userState, false, 1, null);
        IndexedListSet indexedListSet = this.onAppOpModeChangedListeners;
        if (indexedListSet.size() <= 0) {
            return true;
        }
        CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(indexedListSet.elementAt(0));
        throw null;
    }
}
