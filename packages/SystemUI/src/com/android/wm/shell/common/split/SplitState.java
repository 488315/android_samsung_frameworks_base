package com.android.wm.shell.common.split;

/* loaded from: classes3.dex */
public class SplitState {
    public int mState = -1;

    public final boolean isSplitStashed() {
        int i = this.mState;
        return i == 14 || i == 15;
    }
}
