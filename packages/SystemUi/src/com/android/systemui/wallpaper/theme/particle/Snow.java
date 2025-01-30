package com.android.systemui.wallpaper.theme.particle;

import android.content.Context;
import com.android.systemui.wallpaper.theme.DensityUtil;
import java.util.Random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Snow {
    public int alpha;

    /* renamed from: cx */
    public float f431cx;

    /* renamed from: cy */
    public float f432cy;
    public final Random mRandom;
    public final int mXSpeed;
    public final int mYSpeed;
    public final int radius;
    public static final int[] FIXEDRADIUS = {2, 3, 3, 3, 4, 4, 5, 5, 5, 6};
    public static final int[] FIXEDALPHAS = {102, 102, 102, 128, 128, 128, 128, 128, 128, 255};

    public Snow(Context context) {
        Random random = new Random();
        this.mRandom = random;
        this.f431cx = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
        this.f432cy = random.nextInt(DensityUtil.sMetricsHeight);
        this.radius = FIXEDRADIUS[random.nextInt(10)];
        this.alpha = FIXEDALPHAS[random.nextInt(10)];
        this.mXSpeed = random.nextInt(2) - 1;
        this.mYSpeed = random.nextInt(2) + 2;
    }
}
