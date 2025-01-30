package com.android.systemui.wallpaper.theme.particle;

import android.content.Context;
import com.android.systemui.wallpaper.theme.DensityUtil;
import java.util.Random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Flower {

    /* renamed from: cx */
    public float f425cx;

    /* renamed from: cy */
    public float f426cy;
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
        this.f425cx = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
        this.f426cy = random.nextInt(DensityUtil.sMetricsHeight);
        this.mXSpeed = random.nextInt(2) - 1;
        this.mYSpeed = random.nextInt(2) + 2;
        this.flowerKind = random.nextInt(3);
        this.flowerSize = random.nextInt(6);
        this.rotate = 0;
    }
}
