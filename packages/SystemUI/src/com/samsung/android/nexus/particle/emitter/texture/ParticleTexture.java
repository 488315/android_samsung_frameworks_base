package com.samsung.android.nexus.particle.emitter.texture;

import android.content.Context;
import android.graphics.RectF;

/* loaded from: classes4.dex */
public abstract class ParticleTexture {
    public final RectF mBounds = new RectF();
    public final Context mContext;

    public ParticleTexture(Context context) {
        this.mContext = context;
    }
}
