package com.android.systemui.wallpaper.theme.particle;

import android.content.Context;
import com.android.systemui.wallpaper.theme.DensityUtil;
import java.util.Random;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class Snow {
    public int alpha;
    public float cx;
    public float cy;
    public final Random mRandom;
    public final int mXSpeed;
    public final int mYSpeed;
    public final int radius;
    public static final int[] FIXEDRADIUS = {2, 3, 3, 3, 4, 4, 5, 5, 5, 6};
    public static final int[] FIXEDALPHAS = {102, 102, 102, 128, 128, 128, 128, 128, 128, 255};

    public Snow(Context context) {
        Random random = new Random();
        this.mRandom = random;
        this.cx = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
        this.cy = random.nextInt(DensityUtil.sMetricsHeight);
        this.radius = FIXEDRADIUS[random.nextInt(10)];
        this.alpha = FIXEDALPHAS[random.nextInt(10)];
        this.mXSpeed = random.nextInt(2) - 1;
        this.mYSpeed = random.nextInt(2) + 2;
    }
}
