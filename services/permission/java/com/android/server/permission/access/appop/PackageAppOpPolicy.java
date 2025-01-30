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

/* compiled from: PackageAppOpPolicy.kt */
/* loaded from: classes2.dex */
public final class PackageAppOpPolicy extends BaseAppOpPolicy {
  public volatile IndexedListSet onAppOpModeChangedListeners;
  public final Object onAppOpModeChangedListenersLock;

  @Override // com.android.server.permission.access.SchemePolicy
  public String getSubjectScheme() {
    return "package";
  }

  public PackageAppOpPolicy() {
    super(new PackageAppOpPersistence());
    this.onAppOpModeChangedListeners = new IndexedListSet();
    this.onAppOpModeChangedListenersLock = new Object();
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public int getDecision(GetStateScope getStateScope, AccessUri accessUri, AccessUri accessUri2) {
    Intrinsics.checkNotNull(
        accessUri,
        "null cannot be cast to non-null type com.android.server.permission.access.PackageUri");
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
    Intrinsics.checkNotNull(
        accessUri2,
        "null cannot be cast to non-null type com.android.server.permission.access.AppOpUri");
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri2);
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
    throw null;
  }

  @Override // com.android.server.permission.access.SchemePolicy
  public void setDecision(
      MutateStateScope mutateStateScope, AccessUri accessUri, AccessUri accessUri2, int i) {
    Intrinsics.checkNotNull(
        accessUri,
        "null cannot be cast to non-null type com.android.server.permission.access.PackageUri");
    CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(accessUri);
    Intrinsics.checkNotNull(
        accessUri2,
        "null cannot be cast to non-null type com.android.server.permission.access.AppOpUri");
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
  public void onPackageRemoved(MutateStateScope mutateStateScope, String str, int i) {
    SparseArray userStates = mutateStateScope.getNewState().getUserStates();
    int size = userStates.size();
    for (int i2 = 0; i2 < size; i2++) {
      userStates.keyAt(i2);
      UserState userState = (UserState) userStates.valueAt(i2);
      userState.getPackageAppOpModes().remove(str);
      WritableState.requestWrite$default(userState, false, 1, null);
    }
  }

  public final ArrayMap getAppOpModes(GetStateScope getStateScope, String str, int i) {
    return (ArrayMap)
        ((UserState) getStateScope.getState().getUserStates().get(i))
            .getPackageAppOpModes()
            .get(str);
  }

  public final boolean removeAppOpModes(MutateStateScope mutateStateScope, String str, int i) {
    UserState userState = (UserState) mutateStateScope.getNewState().getUserStates().get(i);
    boolean z = userState.getPackageAppOpModes().remove(str) != null;
    if (z) {
      WritableState.requestWrite$default(userState, false, 1, null);
    }
    return z;
  }

  public final int getAppOpMode(GetStateScope getStateScope, String str, int i, String str2) {
    int indexOfKey;
    ArrayMap arrayMap =
        (ArrayMap)
            ((UserState) getStateScope.getState().getUserStates().get(i))
                .getPackageAppOpModes()
                .get(str);
    Object valueOf = Integer.valueOf(AppOpsManager.opToDefaultMode(str2));
    if (arrayMap != null && (indexOfKey = arrayMap.indexOfKey(str2)) >= 0) {
      valueOf = arrayMap.valueAt(indexOfKey);
    }
    return ((Number) valueOf).intValue();
  }

  public final boolean setAppOpMode(
      MutateStateScope mutateStateScope, String str, int i, String str2, int i2) {
    int indexOfKey;
    UserState userState = (UserState) mutateStateScope.getNewState().getUserStates().get(i);
    ArrayMap packageAppOpModes = userState.getPackageAppOpModes();
    ArrayMap arrayMap = (ArrayMap) packageAppOpModes.get(str);
    int opToDefaultMode = AppOpsManager.opToDefaultMode(str2);
    Object valueOf = Integer.valueOf(opToDefaultMode);
    if (arrayMap != null && (indexOfKey = arrayMap.indexOfKey(str2)) >= 0) {
      valueOf = arrayMap.valueAt(indexOfKey);
    }
    if (((Number) valueOf).intValue() == i2) {
      return false;
    }
    if (arrayMap == null) {
      arrayMap = new ArrayMap();
      packageAppOpModes.put(str, arrayMap);
    }
    Integer valueOf2 = Integer.valueOf(i2);
    Integer valueOf3 = Integer.valueOf(opToDefaultMode);
    int indexOfKey2 = arrayMap.indexOfKey(str2);
    if (indexOfKey2 >= 0) {
      if (!Intrinsics.areEqual(valueOf2, arrayMap.valueAt(indexOfKey2))) {
        if (Intrinsics.areEqual(valueOf2, valueOf3)) {
          arrayMap.removeAt(indexOfKey2);
        } else {
          arrayMap.setValueAt(indexOfKey2, valueOf2);
        }
      }
    } else if (!Intrinsics.areEqual(valueOf2, valueOf3)) {
      arrayMap.put(str2, valueOf2);
    }
    if (arrayMap.isEmpty()) {
      packageAppOpModes.remove(str);
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
