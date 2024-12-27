package com.android.server.accessibility.magnification;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.provider.Settings;

import java.util.concurrent.atomic.AtomicBoolean;

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
