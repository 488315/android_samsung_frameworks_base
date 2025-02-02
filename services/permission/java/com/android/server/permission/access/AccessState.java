package com.android.server.permission.access;

import android.util.SparseArray;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public final class AccessState {
  public final SystemState systemState;
  public final SparseArray userStates;

  public AccessState(SystemState systemState, SparseArray sparseArray) {
    this.systemState = systemState;
    this.userStates = sparseArray;
  }

  public final SystemState getSystemState() {
    return this.systemState;
  }

  public final SparseArray getUserStates() {
    return this.userStates;
  }

  public AccessState() {
    this(new SystemState(), new SparseArray());
  }

  public final AccessState copy() {
    SystemState copy = this.systemState.copy();
    SparseArray clone = this.userStates.clone();
    int size = clone.size();
    for (int i = 0; i < size; i++) {
      clone.setValueAt(i, ((UserState) clone.valueAt(i)).copy());
    }
    return new AccessState(copy, clone);
  }
}
