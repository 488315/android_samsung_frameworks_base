package com.samsung.android.nexus.egl.world;

import android.opengl.Matrix;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class WorldPerspective extends BaseWorld {
    public WorldPerspective(int i, int i2) {
        Matrix.setLookAtM(this.mViewMatrix, 0, 0.0f, 0.0f, r3 * 2, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        float f = i;
        float f2 = i2;
        Matrix.frustumM(this.mProjectionMatrix, 0, f * (-0.5f) * 0.5f, f * 0.5f * 0.5f, (-0.5f) * f2 * 0.5f, f2 * 0.5f * 0.5f, (int) ((i2 > i ? i2 : i) * 0.5f), r3 * 3);
    }
}
