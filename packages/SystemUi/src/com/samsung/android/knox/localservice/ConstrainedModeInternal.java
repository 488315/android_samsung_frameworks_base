package com.samsung.android.knox.localservice;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ConstrainedModeInternal {
    public abstract boolean checkConstrainedState();

    public abstract void cleanUpConstrainedState(int i);

    public abstract boolean disableConstrainedState(int i);

    public abstract boolean enableConstrainedState(int i, String str, String str2, String str3, String str4, int i2);

    public abstract int getConstrainedState();

    public abstract boolean isRestrictedByConstrainedState(int i);
}
