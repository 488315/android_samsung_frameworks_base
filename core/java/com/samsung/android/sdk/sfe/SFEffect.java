package com.samsung.android.sdk.sfe;

import android.content.ContentResolver;
import android.p009os.Build;
import android.util.Log;
import com.samsung.android.sdk.sfe.font.FontManager;

/* loaded from: classes5.dex */
public class SFEffect {
    private static final String LOG_TAG = "SFEffect";
    private static FontManager mFontManager;
    public static boolean DEBUG = false;
    private static boolean mIsInitialized = false;

    public static void initialize() {
        boolean equals = "eng".equals(Build.TYPE);
        DEBUG = equals;
        if (equals) {
            Log.m94d(LOG_TAG, ContentResolver.SYNC_EXTRAS_INITIALIZE);
        }
        if (mIsInitialized) {
            if (DEBUG) {
                Log.m94d(LOG_TAG, "Skip... Already init");
            }
        } else {
            if (!loadLibrary("SFEffect.fonteffect.samsung")) {
                Log.m96e(LOG_TAG, "SFEffect libraries is not loaded by loadLibrary!!");
                return;
            }
            mFontManager = new FontManager();
            mIsInitialized = true;
            if (DEBUG) {
                Log.m94d(LOG_TAG, "Initialization complete");
            }
        }
    }

    public static boolean loadLibrary(String libraryName) {
        try {
            System.loadLibrary(libraryName);
            return true;
        } catch (Error error) {
            error.printStackTrace();
            Log.m96e(LOG_TAG, error.getMessage());
            return false;
        } catch (Exception error2) {
            error2.printStackTrace();
            Log.m96e(LOG_TAG, error2.getMessage());
            return false;
        }
    }

    public static FontManager getFontManager() {
        return mFontManager;
    }

    public static boolean isInitialized() {
        return mIsInitialized;
    }
}
