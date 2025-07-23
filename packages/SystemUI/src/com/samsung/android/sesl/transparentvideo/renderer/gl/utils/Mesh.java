package com.samsung.android.sesl.transparentvideo.renderer.gl.utils;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Mesh {
    public static final Companion Companion = new Companion(null);
    public static final Mesh QUAD_2D = new Mesh(new float[]{-1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f}, 2, 5);
    public static final Mesh QUAD_2D_UV = new Mesh(new float[]{0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f}, 2, 5);
    public static final Mesh QUAD_2D_UV_FLIP = new Mesh(new float[]{0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f}, 2, 5);
    public final float[] data;
    public final int dimension;
    public final int order;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Mesh(new float[]{-0.5f, -0.5f, 0.0f, -0.5f, 0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f}, 3, 5);
        new Mesh(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f}, 3, 5);
    }

    public Mesh(float[] fArr, int i, int i2) {
        this.data = fArr;
        this.dimension = i;
        this.order = i2;
    }
}
