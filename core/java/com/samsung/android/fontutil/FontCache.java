package com.samsung.android.fontutil;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import java.util.Hashtable;

/* loaded from: classes6.dex */
public class FontCache {
    private static final Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static Typeface get(String name, AssetManager assetmanager) {
        Typeface typeface = fontCache.get(name);
        if (typeface != null) {
            return typeface;
        }
        try {
            Typeface typeface2 = Typeface.createFromAsset(assetmanager, name);
            fontCache.put(name, typeface2);
            return typeface2;
        } catch (Exception e) {
            return null;
        }
    }
}
