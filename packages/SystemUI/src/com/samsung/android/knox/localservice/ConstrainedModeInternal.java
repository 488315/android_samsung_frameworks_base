package com.samsung.android.knox.localservice;

import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ConstrainedModeInternal {
    public abstract boolean checkConstrainedState();

    public abstract void cleanUpConstrainedState(int i);

    public abstract boolean disableConstrainedState(int i);

    public abstract void dump(PrintWriter printWriter);

    public abstract boolean enableConstrainedState(int i, String str, String str2, String str3, String str4, int i2);

    public abstract int getConstrainedState();

    public abstract boolean isRestrictedByConstrainedState(int i);
}
