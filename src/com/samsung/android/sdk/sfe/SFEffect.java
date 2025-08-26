package com.samsung.android.sdk.sfe;

import android.os.Build;
import android.util.Log;
import com.samsung.android.sdk.sfe.font.FontManager;

/* loaded from: classes6.dex */
public class SFEffect {
    public static boolean DEBUG = false;
    private static final String TAG = "SFEffect";
    private static FontManager mFontManager = null;
    private static boolean mIsInitialized = false;

    public static void initialize() {
        boolean zEquals = "eng".equals(Build.TYPE);
        DEBUG = zEquals;
        if (zEquals) {
            Log.d(TAG, "initialize");
        }
        if (mIsInitialized) {
            if (DEBUG) {
                Log.d(TAG, "Skip... Already init");
            }
        } else {
            if (!loadLibrary("SFEffect.fonteffect.samsung")) {
                Log.e(TAG, "SFEffect libraries is not loaded by loadLibrary!!");
                return;
            }
            mFontManager = new FontManager();
            mIsInitialized = true;
            if (DEBUG) {
                Log.d(TAG, "Initialization complete");
            }
        }
    }

    public static boolean loadLibrary(String str) {
        try {
            System.loadLibrary(str);
            return true;
        } catch (Error | Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
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
