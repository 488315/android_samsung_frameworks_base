package com.samsung.android.knox;

import android.content.ComponentName;

/* loaded from: classes5.dex */
public abstract class PersonaManagerInternal {
  public abstract void doKeyguardTimeout();

  public abstract ComponentName getAdminComponentNameFromEdm(int i);

  public abstract String getECName(int i);

  public abstract boolean isKnoxId(int i);

  public abstract void onDeviceLockedChanged(int i);

  public abstract boolean shouldConfirmCredentials(int i);
}
