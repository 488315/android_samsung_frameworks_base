package com.samsung.android.nexus.particle.emitter.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import com.android.systemui.edgelighting.plus.EdgeLightingPlusEffectView$$ExternalSyntheticLambda0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BitmapCache {
    public static final LruCache mLruCache = new LruCache(QuickStepContract.SYSUI_STATE_BACK_DISABLED);
    public static int uid = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class BitmapLoader {
        public final String TAG = getClass().getSimpleName();

        /* renamed from: id */
        public int f495id = -1;
        public final AtomicInteger retainCount = new AtomicInteger(0);

        public abstract Bitmap onLoad();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DrawBitmapLoader extends BitmapLoader {
        public final BitmapDrawer mDrawer;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return ((EdgeLightingPlusEffectView$$ExternalSyntheticLambda0) this.mDrawer).f$0;
        }

        public final String toString() {
            return "DrawBitmapLoader{id=" + this.f495id + ", mDrawer=" + this.mDrawer + '}';
        }
    }

    public static Bitmap loadToCache(Context context, BitmapLoader bitmapLoader) {
        Bitmap bitmap;
        try {
            bitmap = bitmapLoader.onLoad();
        } catch (Exception e) {
            Log.e(bitmapLoader.TAG, "load: ", e);
            bitmap = null;
        }
        if (bitmap != null) {
            mLruCache.put(Integer.valueOf(bitmapLoader.f495id), bitmap);
            return bitmap;
        }
        Log.w("BitmapCache", "loadToCache: unable to load bitmap :" + bitmapLoader);
        return null;
    }
}
