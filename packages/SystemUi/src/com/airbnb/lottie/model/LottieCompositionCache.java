package com.airbnb.lottie.model;

import androidx.collection.LruCache;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LottieCompositionCache {
    public static final LottieCompositionCache INSTANCE = new LottieCompositionCache();
    public final LruCache cache = new LruCache(20);
}
