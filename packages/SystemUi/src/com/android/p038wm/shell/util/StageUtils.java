package com.android.p038wm.shell.util;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StageUtils {
    public static int convertStagePositionToDockSide(int i) {
        if (i == 8) {
            return 1;
        }
        if (i == 16) {
            return 2;
        }
        if (i != 32) {
            return i != 64 ? -1 : 4;
        }
        return 3;
    }

    public static int getMultiSplitLaunchPosition(int i, boolean z) {
        return i != 0 ? z ? (i & 8) != 0 ? 24 : 96 : (i & 16) != 0 ? 48 : 72 : z ? 96 : 48;
    }
}
