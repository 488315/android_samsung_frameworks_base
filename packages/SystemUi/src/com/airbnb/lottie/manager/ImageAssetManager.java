package com.airbnb.lottie.manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieImageAsset;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ImageAssetManager {
    public static final Object bitmapHashLock = new Object();
    public final Context context;
    public final Map imageAssets;
    public final String imagesFolder;

    public ImageAssetManager(Drawable.Callback callback, String str, ImageAssetDelegate imageAssetDelegate, Map<String, LottieImageAsset> map) {
        if (TextUtils.isEmpty(str) || str.charAt(str.length() - 1) == '/') {
            this.imagesFolder = str;
        } else {
            this.imagesFolder = str.concat("/");
        }
        this.imageAssets = map;
        if (callback instanceof View) {
            this.context = ((View) callback).getContext().getApplicationContext();
        } else {
            this.context = null;
        }
    }
}
