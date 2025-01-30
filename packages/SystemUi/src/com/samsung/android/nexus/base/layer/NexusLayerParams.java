package com.samsung.android.nexus.base.layer;

import android.graphics.Rect;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NexusLayerParams {
    public final int mHeight;
    public final int mWidth;

    /* renamed from: mX */
    public final int f493mX;

    /* renamed from: mY */
    public final int f494mY;

    public NexusLayerParams(NexusLayerParams nexusLayerParams) {
        this.mWidth = nexusLayerParams.mWidth;
        this.mHeight = nexusLayerParams.mHeight;
        this.f493mX = nexusLayerParams.f493mX;
        this.f494mY = nexusLayerParams.f494mY;
    }

    public NexusLayerParams(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        this.f494mY = 0;
        this.f493mX = 0;
        new Rect();
    }

    public NexusLayerParams(int i, int i2, int i3) {
        this(i, i2);
    }
}
