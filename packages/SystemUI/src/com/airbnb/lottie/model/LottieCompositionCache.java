package com.airbnb.lottie.model;

import androidx.collection.LruCache;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LottieCompositionCache {
    public static final LottieCompositionCache INSTANCE = new LottieCompositionCache();
    public final LruCache cache = new LruCache(20);
}
