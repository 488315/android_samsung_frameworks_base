package com.samsung.android.nexus.particle.emitter.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import com.android.systemui.edgelighting.plus.EdgeLightingPlusEffectView;
import com.android.systemui.edgelighting.plus.EdgeLightingPlusEffectView$$ExternalSyntheticLambda0;

/* loaded from: classes4.dex */
public class BitmapCache {
    public static final LruCache sLruCache = new LruCache(4194304);
    public static int uid = 0;

    public abstract class BitmapLoader {
        public final String TAG = getClass().getSimpleName();
        public int id = -1;
        public int retainCount = 0;

        public abstract Bitmap onLoad();
    }

    public class DrawBitmapLoader extends BitmapLoader {
        public final BitmapDrawer mDrawer;

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
        Bitmap bitmapOnLoad;
        bitmapLoader.getClass();
        try {
            bitmapOnLoad = bitmapLoader.onLoad();
        } catch (Exception e) {
            Log.e(bitmapLoader.TAG, "load: ", e);
            bitmapOnLoad = null;
        }
        if (bitmapOnLoad == null) {
            Log.w("BitmapCache", "loadToCache: unable to load bitmap :" + bitmapLoader);
            return null;
        }
        Log.w("BitmapCache", "loadToCache: load bitmap :" + bitmapLoader);
        sLruCache.put(Integer.valueOf(bitmapLoader.id), bitmapOnLoad);
        return bitmapOnLoad;
    }
}
