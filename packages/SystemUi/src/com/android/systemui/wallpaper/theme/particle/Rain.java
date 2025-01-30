package com.android.systemui.wallpaper.theme.particle;

import android.content.Context;
import com.android.systemui.wallpaper.theme.DensityUtil;
import java.util.Random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Rain {
    public static final double[] speed = {0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.1d, 0.1d, 0.2d, 0.2d, 0.3d, 0.3d, 0.3d, 0.5d, 0.5d, 0.5d, 0.5d};
    public final Random mRandom;
    public final double mXSpeed;
    public final int mYSpeed;

    /* renamed from: x */
    public float f429x;

    /* renamed from: y */
    public float f430y;

    public Rain(Context context) {
        Random random = new Random();
        this.mRandom = random;
        this.f429x = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
        this.f430y = random.nextInt(DensityUtil.sMetricsHeight);
        this.mXSpeed = speed[random.nextInt(16)];
        this.mYSpeed = random.nextInt(2) + 6;
    }
}
