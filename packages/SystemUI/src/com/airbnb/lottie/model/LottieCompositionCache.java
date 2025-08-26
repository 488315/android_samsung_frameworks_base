package com.airbnb.lottie.model;

import androidx.collection.LruCache;

/* loaded from: classes.dex */
public class LottieCompositionCache {
    public static final LottieCompositionCache INSTANCE = new LottieCompositionCache();
    public final LruCache cache = new LruCache(20);
}
