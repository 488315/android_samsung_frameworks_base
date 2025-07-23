package com.samsung.android.nexus.egl.world;

import android.renderscript.Matrix4f;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class WorldOrthographic extends BaseWorld {
    public WorldOrthographic(int i, int i2) {
        int i3 = i2 > i ? i2 : i;
        float f = i;
        float f2 = f * (-0.5f);
        float f3 = f * 0.5f;
        float f4 = i2;
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.loadOrtho(f2, f3, f4 * (-0.5f), f4 * 0.5f, i3, -i3);
        this.mProjectionMatrix = matrix4f.getArray();
    }
}
