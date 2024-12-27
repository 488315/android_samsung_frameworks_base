package com.android.systemui.wallpaper.theme.particle;

import android.content.Context;
import com.android.systemui.wallpaper.theme.DensityUtil;
import java.util.Random;

public final class Flower {
    public float cx;
    public float cy;
    public final int flowerKind;
    public final int flowerSize;
    public final Random mRandom;
    public final int mXSpeed;
    public final int mYSpeed;
    public int rotate;

    public Flower(Context context) {
        this.flowerKind = 0;
        this.flowerSize = 0;
        Random random = new Random();
        this.mRandom = random;
        this.cx = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
        this.cy = random.nextInt(DensityUtil.sMetricsHeight);
        this.mXSpeed = random.nextInt(2) - 1;
        this.mYSpeed = random.nextInt(2) + 2;
        this.flowerKind = random.nextInt(3);
        this.flowerSize = random.nextInt(6);
        this.rotate = 0;
    }
}
