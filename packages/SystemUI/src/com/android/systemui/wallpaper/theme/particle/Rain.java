package com.android.systemui.wallpaper.theme.particle;

import android.content.Context;
import com.android.systemui.wallpaper.theme.DensityUtil;
import java.util.Random;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Rain {
    public static final double[] speed = {0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.1d, 0.1d, 0.2d, 0.2d, 0.3d, 0.3d, 0.3d, 0.5d, 0.5d, 0.5d, 0.5d};
    public final Random mRandom;
    public final double mXSpeed;
    public final int mYSpeed;
    public float x;
    public float y;

    public Rain(Context context) {
        Random random = new Random();
        this.mRandom = random;
        this.x = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
        this.y = random.nextInt(DensityUtil.sMetricsHeight);
        this.mXSpeed = speed[random.nextInt(16)];
        this.mYSpeed = random.nextInt(2) + 6;
    }
}
