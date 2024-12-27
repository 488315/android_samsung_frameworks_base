package com.samsung.android.sdk.sfe;

import android.os.Build;
import android.util.Log;

import com.samsung.android.sdk.sfe.font.FontManager;

public class SFEffect {
    private static final String LOG_TAG = "SFEffect";
    private static FontManager mFontManager;
    public static boolean DEBUG = false;
    private static boolean mIsInitialized = false;

    public static void initialize() {
        DEBUG = "eng".equals(Build.TYPE);
        if (DEBUG) {
            Log.d(LOG_TAG, "initialize");
        }
        if (mIsInitialized) {
            if (DEBUG) {
                Log.d(LOG_TAG, "Skip... Already init");
            }
        } else {
            if (!loadLibrary("SFEffect.fonteffect.samsung")) {
                Log.e(LOG_TAG, "SFEffect libraries is not loaded by loadLibrary!!");
                return;
            }
            mFontManager = new FontManager();
            mIsInitialized = true;
            if (DEBUG) {
                Log.d(LOG_TAG, "Initialization complete");
            }
        }
    }

    public static boolean loadLibrary(String libraryName) {
        try {
            System.loadLibrary(libraryName);
            return true;
        } catch (Error | Exception error) {
            error.printStackTrace();
            Log.e(LOG_TAG, error.getMessage());
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
