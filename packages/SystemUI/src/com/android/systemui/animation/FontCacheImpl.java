package com.android.systemui.animation;

import android.util.LruCache;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FontCacheImpl implements FontCache {
    public final int animationFrameCount;
    public final LruCache interpCache;
    public final LruCache verFontCache;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getDEFAULT_FONT_CACHE_MAX_ENTRIES$annotations() {
        }
    }

    static {
        new Companion(null);
    }

    public FontCacheImpl() {
        this(0, 1, null);
    }

    public FontCacheImpl(int i) {
        this.animationFrameCount = i;
        int i2 = i * 2;
        this.interpCache = new LruCache(i2);
        this.verFontCache = new LruCache(i2);
    }

    public /* synthetic */ FontCacheImpl(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 5 : i);
    }
}
