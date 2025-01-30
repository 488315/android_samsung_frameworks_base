package com.samsung.android.nexus.egl.world;

import android.opengl.Matrix;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class WorldPerspective extends BaseWorld {
    public WorldPerspective(int i, int i2) {
        Matrix.setLookAtM(this.mViewMatrix, 0, 0.0f, 0.0f, r3 * 2, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        float f = i;
        float f2 = i2;
        Matrix.frustumM(this.mProjectionMatrix, 0, f * (-0.5f) * 0.5f, f * 0.5f * 0.5f, (-0.5f) * f2 * 0.5f, f2 * 0.5f * 0.5f, (int) ((i2 > i ? i2 : i) * 0.5f), r3 * 3);
    }
}
