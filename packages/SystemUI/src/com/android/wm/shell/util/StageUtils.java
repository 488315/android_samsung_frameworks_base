package com.android.wm.shell.util;

/* loaded from: classes3.dex */
public class StageUtils {
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
