package com.android.systemui.volume.dialog.utils;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeTracerImpl implements VolumeTracer {
    public static String getMethodName(VolumeDialogVisibilityModel volumeDialogVisibilityModel) {
        if (volumeDialogVisibilityModel instanceof VolumeDialogVisibilityModel.Visible) {
            return "VolumeDialog#show";
        }
        if (volumeDialogVisibilityModel instanceof VolumeDialogVisibilityModel.Dismissed) {
            return "VolumeDialog#dismiss";
        }
        if (volumeDialogVisibilityModel instanceof VolumeDialogVisibilityModel.Invisible) {
            throw new IllegalStateException("Invisible is unsupported");
        }
        throw new NoWhenBranchMatchedException();
    }
}
