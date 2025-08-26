package com.android.systemui.model;

import com.android.systemui.Dumpable;

/* loaded from: classes2.dex */
public interface SysUiState extends Dumpable {

    public interface SysUiStateCallback {
        void onSystemUiStateChanged(int i, long j);
    }

    void commitUpdate();

    void destroy();

    int getDisplayId();

    long getFlags();

    SysUiState setFlag(long j, boolean z);
}
