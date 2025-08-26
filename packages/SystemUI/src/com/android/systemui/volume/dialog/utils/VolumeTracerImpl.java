package com.android.systemui.volume.dialog.utils;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import kotlin.NoWhenBranchMatchedException;

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
