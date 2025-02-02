package com.airbnb.lottie.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class ImageAssetManager {
    private static final Object bitmapHashLock = new Object();
    private final Context context;
    private final Map<String, LottieImageAsset> imageAssets;
    private String imagesFolder;

    public ImageAssetManager(Drawable.Callback callback, String str, Map map) {
        this.imagesFolder = str;
        if (!TextUtils.isEmpty(str)) {
            if (this.imagesFolder.charAt(r4.length() - 1) != '/') {
                this.imagesFolder += '/';
            }
        }
        if (callback instanceof View) {
            this.context = ((View) callback).getContext();
            this.imageAssets = map;
        } else {
            Logger.warning("LottieDrawable must be inside of a view for images to work.");
            this.imageAssets = new HashMap();
            this.context = null;
        }
    }

    public final Bitmap bitmapForId(String str) {
        LottieImageAsset lottieImageAsset = this.imageAssets.get(str);
        if (lottieImageAsset == null) {
            return null;
        }
        Bitmap bitmap = lottieImageAsset.getBitmap();
        if (bitmap != null) {
            return bitmap;
        }
        String fileName = lottieImageAsset.getFileName();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inDensity = 160;
        if (fileName.startsWith("data:") && fileName.indexOf("base64,") > 0) {
            try {
                byte[] decode = Base64.decode(fileName.substring(fileName.indexOf(44) + 1), 0);
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(decode, 0, decode.length, options);
                synchronized (bitmapHashLock) {
                    this.imageAssets.get(str).setBitmap(decodeByteArray);
                }
                return decodeByteArray;
            } catch (IllegalArgumentException e) {
                Logger.warning("data URL did not have correct base64 format.", e);
                return null;
            }
        }
        try {
            if (TextUtils.isEmpty(this.imagesFolder)) {
                throw new IllegalStateException("You must set an images folder before loading an image. Set it with LottieComposition#setImagesFolder or LottieDrawable#setImagesFolder");
            }
            Bitmap decodeStream = BitmapFactory.decodeStream(this.context.getAssets().open(this.imagesFolder + fileName), null, options);
            int width = lottieImageAsset.getWidth();
            int height = lottieImageAsset.getHeight();
            int i = Utils.$r8$clinit;
            if (decodeStream.getWidth() != width || decodeStream.getHeight() != height) {
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeStream, width, height, true);
                decodeStream.recycle();
                decodeStream = createScaledBitmap;
            }
            synchronized (bitmapHashLock) {
                this.imageAssets.get(str).setBitmap(decodeStream);
            }
            return decodeStream;
        } catch (IOException e2) {
            Logger.warning("Unable to open asset.", e2);
            return null;
        }
    }

    public final boolean hasSameContext(Context context) {
        Context context2 = this.context;
        return (context == null && context2 == null) || context2.equals(context);
    }
}
