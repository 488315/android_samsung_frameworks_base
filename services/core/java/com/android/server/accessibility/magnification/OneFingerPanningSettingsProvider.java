package com.android.server.accessibility.magnification;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.provider.Settings;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OneFingerPanningSettingsProvider {
    static final String KEY = "accessibility_single_finger_panning_enabled";
    public AtomicBoolean mCached;
    ContentResolver mContentResolver;
    ContentObserver mObserver;

    static {
        Settings.Secure.getUriFor(KEY);
    }

    public static boolean isOneFingerPanningEnabledDefault(Context context) {
        try {
            return context.getResources()
                    .getBoolean(R.bool.config_faceAuthSupportsSelfIllumination);
        } catch (Resources.NotFoundException unused) {
            return false;
        }
    }
}
