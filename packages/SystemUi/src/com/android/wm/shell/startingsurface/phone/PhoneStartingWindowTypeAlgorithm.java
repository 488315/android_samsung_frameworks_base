package com.android.wm.shell.startingsurface.phone;

import android.window.StartingWindowInfo;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.startingsurface.StartingWindowTypeAlgorithm;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PhoneStartingWindowTypeAlgorithm implements StartingWindowTypeAlgorithm {
    @Override // com.android.wm.shell.startingsurface.StartingWindowTypeAlgorithm
    public final int getSuggestedWindowType(StartingWindowInfo startingWindowInfo) {
        int i = startingWindowInfo.startingWindowTypeParameter;
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 2) != 0;
        boolean z3 = (i & 4) != 0;
        boolean z4 = (i & 8) != 0;
        boolean z5 = (i & 16) != 0;
        boolean z6 = (i & 32) != 0;
        boolean z7 = (Integer.MIN_VALUE & i) != 0;
        boolean z8 = (i & 64) != 0;
        boolean z9 = (i & 256) != 0;
        boolean z10 = startingWindowInfo.taskInfo.topActivityType == 2;
        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 1535727272, 1048575, null, Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), Boolean.valueOf(z5), Boolean.valueOf(z6), Boolean.valueOf(z7), Boolean.valueOf(z8), Boolean.valueOf(z9), Boolean.valueOf(z10));
        }
        if (z9) {
            return 5;
        }
        if (!z10 && (!z3 || z || (z2 && !z5))) {
            if (z6) {
                return 3;
            }
            return z7 ? 4 : 1;
        }
        if (!z2) {
            return 0;
        }
        if (z4) {
            if (startingWindowInfo.taskSnapshot != null) {
                return 2;
            }
            if (!z10) {
                return 3;
            }
        }
        if (z8 || z10) {
            return 0;
        }
        if (z6) {
            return 3;
        }
        return z7 ? 4 : 1;
    }
}
