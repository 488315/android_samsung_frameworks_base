package com.samsung.android.nexus.particle.emitter.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import com.android.systemui.edgelighting.plus.EdgeLightingPlusEffectView;
import com.android.systemui.edgelighting.plus.EdgeLightingPlusEffectView$$ExternalSyntheticLambda0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BitmapCache {
    public static final LruCache sLruCache = new LruCache(4194304);
    public static int uid = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class BitmapLoader {
        public final String TAG = getClass().getSimpleName();
        public int id = -1;
        public int retainCount = 0;

        public abstract Bitmap onLoad();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DrawBitmapLoader extends BitmapLoader {
        public final BitmapDrawer mDrawer;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public interface BitmapDrawer {
        }

        public DrawBitmapLoader(BitmapDrawer bitmapDrawer) {
            if (bitmapDrawer == null) {
                throw new IllegalArgumentException("null drawer");
            }
            this.mDrawer = bitmapDrawer;
        }

        @Override // com.samsung.android.nexus.particle.emitter.texture.BitmapCache.BitmapLoader
        public final Bitmap onLoad() {
            Bitmap bitmap = ((EdgeLightingPlusEffectView$$ExternalSyntheticLambda0) this.mDrawer).f$0;
            int i = EdgeLightingPlusEffectView.$r8$clinit;
            return bitmap;
        }

        public final String toString() {
            return "DrawBitmapLoader{id=" + this.id + ", mDrawer=" + this.mDrawer + '}';
        }
    }

    public static Bitmap loadToCache(Context context, BitmapLoader bitmapLoader) {
        Bitmap bitmap;
        bitmapLoader.getClass();
        try {
            bitmap = bitmapLoader.onLoad();
        } catch (Exception e) {
            Log.e(bitmapLoader.TAG, "load: ", e);
            bitmap = null;
        }
        if (bitmap == null) {
            Log.w("BitmapCache", "loadToCache: unable to load bitmap :" + bitmapLoader);
            return null;
        }
        Log.w("BitmapCache", "loadToCache: load bitmap :" + bitmapLoader);
        sLruCache.put(Integer.valueOf(bitmapLoader.id), bitmap);
        return bitmap;
    }
}
