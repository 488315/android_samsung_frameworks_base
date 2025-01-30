package com.android.server.vr;

import android.content.ComponentName;
import android.service.vr.IPersistentVrStateCallbacks;

/* loaded from: classes3.dex */
public abstract class VrManagerInternal {
    public abstract void addPersistentVrModeStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks);

    public abstract int hasVrPackage(ComponentName componentName, int i);

    public abstract boolean isCurrentVrListener(String str, int i);

    public abstract void onScreenStateChanged(boolean z);

    public abstract void setVrMode(boolean z, ComponentName componentName, int i, int i2, ComponentName componentName2);
}
