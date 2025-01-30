package com.android.systemui.wallpaper.theme.particle;

import android.content.Context;
import com.android.systemui.wallpaper.theme.DensityUtil;
import java.util.Random;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Leaf {

    /* renamed from: cx */
    public float f427cx;

    /* renamed from: cy */
    public float f428cy;
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
        this.f427cx = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
        this.f428cy = random.nextInt(DensityUtil.sMetricsHeight);
        this.mXSpeed = random.nextInt(2) - 1;
        this.mYSpeed = random.nextInt(2) + 2;
        this.leafKind = random.nextInt(4);
        this.leafSize = random.nextInt(6);
        this.rotate = 0;
    }
}
