package com.samsung.android.nexus.particle.emitter.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import com.samsung.android.nexus.particle.emitter.texture.BitmapCache;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BitmapParticleTexture extends ParticleTexture {
    public Bitmap mBitmap;
    public final BitmapCache.BitmapLoader mBitmapLoader;

    public BitmapParticleTexture(Context context, BitmapCache.BitmapLoader bitmapLoader) {
        super(context);
        this.mBitmapLoader = bitmapLoader;
    }

    public final void onRelease() {
        LruCache lruCache = BitmapCache.sLruCache;
        BitmapCache.BitmapLoader bitmapLoader = this.mBitmapLoader;
        if (bitmapLoader == null) {
            throw new IllegalArgumentException("null loader");
        }
        int max = Math.max(0, bitmapLoader.retainCount - 1);
        bitmapLoader.retainCount = max;
        if (max == 0) {
            Log.w("BitmapCache", "release: clear cache :" + bitmapLoader);
            BitmapCache.sLruCache.remove(Integer.valueOf(bitmapLoader.id));
        }
    }
}
