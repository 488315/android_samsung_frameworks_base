package com.samsung.android.nexus.particle.emitter.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import com.samsung.android.nexus.particle.emitter.texture.BitmapCache;

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
        int iMax = Math.max(0, bitmapLoader.retainCount - 1);
        bitmapLoader.retainCount = iMax;
        if (iMax == 0) {
            Log.w("BitmapCache", "release: clear cache :" + bitmapLoader);
            BitmapCache.sLruCache.remove(Integer.valueOf(bitmapLoader.id));
        }
    }
}
