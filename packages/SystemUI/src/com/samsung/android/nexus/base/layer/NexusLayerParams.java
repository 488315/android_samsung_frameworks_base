package com.samsung.android.nexus.base.layer;

import android.graphics.Rect;

/* loaded from: classes4.dex */
public class NexusLayerParams {
    public final int mHeight;
    public final int mWidth;

    public NexusLayerParams(NexusLayerParams nexusLayerParams) {
        this.mWidth = nexusLayerParams.mWidth;
        this.mHeight = nexusLayerParams.mHeight;
    }

    public NexusLayerParams(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        new Rect();
    }

    public NexusLayerParams(int i, int i2, int i3) {
        this(i, i2);
    }
}
