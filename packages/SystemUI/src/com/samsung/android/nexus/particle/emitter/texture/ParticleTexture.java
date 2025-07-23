package com.samsung.android.nexus.particle.emitter.texture;

import android.content.Context;
import android.graphics.RectF;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ParticleTexture {
    public final RectF mBounds = new RectF();
    public final Context mContext;

    public ParticleTexture(Context context) {
        this.mContext = context;
    }
}
