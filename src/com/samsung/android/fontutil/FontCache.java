package com.samsung.android.fontutil;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import java.util.Hashtable;

/* loaded from: classes6.dex */
public class FontCache {
    private static final Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static Typeface get(String str, AssetManager assetManager) {
        Hashtable<String, Typeface> hashtable = fontCache;
        Typeface typeface = hashtable.get(str);
        if (typeface != null) {
            return typeface;
        }
        try {
            Typeface createFromAsset = Typeface.createFromAsset(assetManager, str);
            hashtable.put(str, createFromAsset);
            return createFromAsset;
        } catch (Exception unused) {
            return null;
        }
    }
}
