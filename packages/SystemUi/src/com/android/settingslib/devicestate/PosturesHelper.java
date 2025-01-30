package com.android.settingslib.devicestate;

import android.R;
import android.content.Context;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PosturesHelper {
    public final int[] foldedDeviceStates;
    public final int[] halfFoldedDeviceStates;
    public final int[] rearDisplayDeviceStates;
    public final int[] unfoldedDeviceStates;

    public PosturesHelper(Context context) {
        this.foldedDeviceStates = context.getResources().getIntArray(R.array.networks_not_clear_data);
        this.halfFoldedDeviceStates = context.getResources().getIntArray(R.array.preloaded_freeform_multi_window_drawables);
        this.unfoldedDeviceStates = context.getResources().getIntArray(17236271);
        this.rearDisplayDeviceStates = context.getResources().getIntArray(17236277);
    }

    public final int deviceStateToPosture(int i) {
        if (ArraysKt___ArraysKt.contains(i, this.foldedDeviceStates)) {
            return 0;
        }
        if (ArraysKt___ArraysKt.contains(i, this.halfFoldedDeviceStates)) {
            return 1;
        }
        if (ArraysKt___ArraysKt.contains(i, this.unfoldedDeviceStates)) {
            return 2;
        }
        return ArraysKt___ArraysKt.contains(i, this.rearDisplayDeviceStates) ? 3 : -1;
    }
}
