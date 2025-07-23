package com.android.systemui.model;

import com.android.systemui.Dumpable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface SysUiState extends Dumpable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SysUiStateCallback {
        void onSystemUiStateChanged(int i, long j);
    }

    void commitUpdate();

    void destroy();

    int getDisplayId();

    long getFlags();

    SysUiState setFlag(long j, boolean z);
}
