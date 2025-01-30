package com.samsung.android.nexus.particle.emitter.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.LruCache;
import com.samsung.android.nexus.particle.emitter.texture.BitmapCache;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BitmapParticleTexture extends ParticleTexture {
    public Bitmap mBitmap;
    public final BitmapCache.BitmapLoader mBitmapLoader;

    public BitmapParticleTexture(Context context, BitmapCache.BitmapLoader bitmapLoader) {
        super(context);
        this.mBitmapLoader = bitmapLoader;
    }

    @Override // com.samsung.android.nexus.particle.emitter.texture.ParticleTexture
    public final void draw(Canvas canvas, Paint paint) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, (Rect) null, this.mBounds, paint);
        }
    }

    @Override // com.samsung.android.nexus.particle.emitter.texture.ParticleTexture
    public final void onCreate() {
        Bitmap bitmap;
        if (this.mBitmap == null) {
            LruCache lruCache = BitmapCache.mLruCache;
            BitmapCache.BitmapLoader bitmapLoader = this.mBitmapLoader;
            if (bitmapLoader == null) {
                throw new IllegalArgumentException("null loader");
            }
            int i = bitmapLoader.f495id;
            Context context = this.mContext;
            if (i < 0) {
                int i2 = BitmapCache.uid;
                BitmapCache.uid = i2 + 1;
                bitmapLoader.f495id = i2;
                bitmap = BitmapCache.loadToCache(context, bitmapLoader);
            } else {
                bitmap = (Bitmap) BitmapCache.mLruCache.get(Integer.valueOf(i));
                if (bitmap == null) {
                    bitmap = BitmapCache.loadToCache(context, bitmapLoader);
                }
            }
            if (bitmap != null) {
                bitmapLoader.retainCount.incrementAndGet();
            }
            this.mBitmap = bitmap;
        }
    }

    @Override // com.samsung.android.nexus.particle.emitter.texture.ParticleTexture
    public final void onDestroy() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mBitmap = null;
        }
    }

    @Override // com.samsung.android.nexus.particle.emitter.texture.ParticleTexture
    public final void onRelease() {
        AtomicInteger atomicInteger;
        int max;
        LruCache lruCache = BitmapCache.mLruCache;
        BitmapCache.BitmapLoader bitmapLoader = this.mBitmapLoader;
        if (bitmapLoader == null) {
            throw new IllegalArgumentException("null loader");
        }
        do {
            atomicInteger = bitmapLoader.retainCount;
            max = Math.max(0, atomicInteger.get() - 1);
        } while (atomicInteger.compareAndSet(max, max));
    }

    @Override // com.samsung.android.nexus.particle.emitter.texture.ParticleTexture
    public final void onBoundChanged() {
    }
}
