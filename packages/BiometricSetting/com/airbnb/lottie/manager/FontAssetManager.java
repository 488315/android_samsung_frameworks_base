package com.airbnb.lottie.manager;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.airbnb.lottie.model.MutablePair;
import com.airbnb.lottie.utils.Logger;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class FontAssetManager {
    private final AssetManager assetManager;
    private final MutablePair<String> tempPair = new MutablePair<>();
    private final Map<MutablePair<String>, Typeface> fontMap = new HashMap();
    private final Map<String, Typeface> fontFamilies = new HashMap();
    private String defaultFontFileExtension = ".ttf";

    public FontAssetManager(Drawable.Callback callback) {
        if (callback instanceof View) {
            this.assetManager = ((View) callback).getContext().getAssets();
        } else {
            Logger.warning("LottieDrawable must be inside of a view for images to work.");
            this.assetManager = null;
        }
    }

    public final Typeface getTypeface(String str, String str2) {
        MutablePair<String> mutablePair = this.tempPair;
        mutablePair.set(str, str2);
        Map<MutablePair<String>, Typeface> map = this.fontMap;
        Typeface typeface = (Typeface) ((HashMap) map).get(mutablePair);
        if (typeface != null) {
            return typeface;
        }
        HashMap hashMap = (HashMap) this.fontFamilies;
        Typeface typeface2 = (Typeface) hashMap.get(str);
        if (typeface2 == null) {
            typeface2 = Typeface.createFromAsset(this.assetManager, "fonts/" + str + this.defaultFontFileExtension);
            hashMap.put(str, typeface2);
        }
        boolean contains = str2.contains("Italic");
        boolean contains2 = str2.contains("Bold");
        int i = (contains && contains2) ? 3 : contains ? 2 : contains2 ? 1 : 0;
        if (typeface2.getStyle() != i) {
            typeface2 = Typeface.create(typeface2, i);
        }
        ((HashMap) map).put(mutablePair, typeface2);
        return typeface2;
    }
}
