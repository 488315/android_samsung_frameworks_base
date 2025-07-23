package com.android.systemui.wallpaper.theme.particle;

import android.content.Context;
import com.android.systemui.wallpaper.theme.DensityUtil;
import java.util.Random;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class Leaf {
    public float cx;
    public float cy;
    public final int leafKind;
    public final int leafSize;
    public final Random mRandom;
    public final int mXSpeed;
    public final int mYSpeed;
    public int rotate;

    public Leaf(Context context) {
        this.leafKind = 0;
        this.leafSize = 0;
        Random random = new Random();
        this.mRandom = random;
        this.cx = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
        this.cy = random.nextInt(DensityUtil.sMetricsHeight);
        this.mXSpeed = random.nextInt(2) - 1;
        this.mYSpeed = random.nextInt(2) + 2;
        this.leafKind = random.nextInt(4);
        this.leafSize = random.nextInt(6);
        this.rotate = 0;
    }
}
