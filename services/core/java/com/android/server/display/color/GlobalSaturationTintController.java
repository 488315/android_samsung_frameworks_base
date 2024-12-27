package com.android.server.display.color;

import android.util.Slog;

import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GlobalSaturationTintController extends TintController {
    public final float[] mMatrixGlobalSaturation = new float[16];

    @Override // com.android.server.display.color.TintController
    public final int getLevel() {
        return 150;
    }

    @Override // com.android.server.display.color.TintController
    public final float[] getMatrix() {
        if (this.mIsActivationLocked) {
            Slog.d("ColorDisplayService", "GlobalSaturationTintController: activation lock");
            return Arrays.copyOf(ColorDisplayService.MATRIX_IDENTITY, 16);
        }
        float[] fArr = this.mMatrixGlobalSaturation;
        return Arrays.copyOf(fArr, fArr.length);
    }
}
