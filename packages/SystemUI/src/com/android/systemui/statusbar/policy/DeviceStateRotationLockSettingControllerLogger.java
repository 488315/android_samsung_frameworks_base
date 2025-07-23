package com.android.systemui.statusbar.policy;

import android.R;
import android.content.Context;
import com.android.systemui.log.LogBuffer;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceStateRotationLockSettingControllerLogger {
    public final int[] foldedStates;
    public final int[] halfFoldedStates;
    public final LogBuffer logBuffer;
    public final int[] rearDisplayStates;
    public final int[] unfoldedStates;

    public DeviceStateRotationLockSettingControllerLogger(LogBuffer logBuffer, Context context) {
        this.logBuffer = logBuffer;
        this.foldedStates = context.getResources().getIntArray(R.array.special_locale_codes);
        this.halfFoldedStates = context.getResources().getIntArray(R.array.vendor_disallowed_apps_managed_user);
        this.unfoldedStates = context.getResources().getIntArray(17236290);
        this.rearDisplayStates = context.getResources().getIntArray(17236298);
    }

    public final String toDevicePostureString(int i) {
        return ArraysKt___ArraysKt.contains(i, this.foldedStates) ? "Folded" : ArraysKt___ArraysKt.contains(i, this.unfoldedStates) ? "Unfolded" : ArraysKt___ArraysKt.contains(i, this.halfFoldedStates) ? "Half-Folded" : ArraysKt___ArraysKt.contains(i, this.rearDisplayStates) ? "Rear display" : i == -1 ? "Uninitialized" : C2paManifestList.UNKNOWN_VALUE;
    }
}
