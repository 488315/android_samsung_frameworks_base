package com.android.wm.shell.common.split;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplitState {
    public int mState = -1;

    public final boolean isSplitStashed() {
        int i = this.mState;
        return i == 14 || i == 15;
    }
}
