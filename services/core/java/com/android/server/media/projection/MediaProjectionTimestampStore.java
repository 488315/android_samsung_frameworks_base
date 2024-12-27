package com.android.server.media.projection;

import android.content.SharedPreferences;

import java.time.InstantSource;

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
