package com.android.server.media.projection;

import android.content.SharedPreferences;

import java.time.InstantSource;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MediaProjectionTimestampStore {
    public static MediaProjectionTimestampStore sInstance;
    public static final Object sInstanceLock = new Object();
    public final InstantSource mInstantSource;
    public final SharedPreferences mSharedPreferences;
    public final Object mTimestampLock = new Object();

    public MediaProjectionTimestampStore(
            SharedPreferences sharedPreferences, InstantSource instantSource) {
        this.mSharedPreferences = sharedPreferences;
        this.mInstantSource = instantSource;
    }
}
