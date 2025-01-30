package com.android.wm.shell.common.split;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CellUtil {
    public static boolean isCellInLeftOrTopBounds(int i, boolean z) {
        return !((i & 16) == 0 || z) || ((i & 8) != 0 && z);
    }
}
