package com.samsung.android.nexus.particle.emitter.texture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ParticleTexture {
    public final RectF mBounds = new RectF();
    public final Context mContext;

    public ParticleTexture(Context context) {
        this.mContext = context;
    }

    public abstract void draw(Canvas canvas, Paint paint);

    public abstract void onBoundChanged();

    public abstract void onCreate();

    public abstract void onDestroy();

    public abstract void onRelease();
}
