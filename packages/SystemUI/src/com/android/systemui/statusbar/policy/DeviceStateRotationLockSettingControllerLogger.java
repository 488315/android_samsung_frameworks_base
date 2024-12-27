package com.android.systemui.statusbar.policy;

import android.R;
import android.content.Context;
import com.android.systemui.log.LogBuffer;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DeviceStateRotationLockSettingControllerLogger {
    public final int[] foldedStates;
    public final int[] halfFoldedStates;
    public final LogBuffer logBuffer;
    public final int[] rearDisplayStates;
    public final int[] unfoldedStates;

    public DeviceStateRotationLockSettingControllerLogger(LogBuffer logBuffer, Context context) {
        this.logBuffer = logBuffer;
        this.foldedStates = context.getResources().getIntArray(R.array.preloaded_freeform_multi_window_drawables);
        this.halfFoldedStates = context.getResources().getIntArray(R.array.sim_colors);
        this.unfoldedStates = context.getResources().getIntArray(17236280);
        this.rearDisplayStates = context.getResources().getIntArray(17236286);
    }

    public static final String access$toDevicePostureString(DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger, int i) {
        return ArraysKt___ArraysKt.contains(i, deviceStateRotationLockSettingControllerLogger.foldedStates) ? "Folded" : ArraysKt___ArraysKt.contains(i, deviceStateRotationLockSettingControllerLogger.unfoldedStates) ? "Unfolded" : ArraysKt___ArraysKt.contains(i, deviceStateRotationLockSettingControllerLogger.halfFoldedStates) ? "Half-Folded" : ArraysKt___ArraysKt.contains(i, deviceStateRotationLockSettingControllerLogger.rearDisplayStates) ? "Rear display" : i == -1 ? "Uninitialized" : "Unknown";
    }
}
