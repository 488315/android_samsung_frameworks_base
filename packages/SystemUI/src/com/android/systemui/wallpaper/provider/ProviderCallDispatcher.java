package com.android.systemui.wallpaper.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0;
import com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Result;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ProviderCallDispatcher extends LiveWallpaperProviderCallDispatcher {
    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher
    public final GetScreenshot$Result onGetScreenshot(Context context, GetScreenshot$Params getScreenshot$Params) {
        Log.i("ImageWallpaper[ProviderCallDispatcher]", "onGetScreenshot");
        return super.onGetScreenshot(context, getScreenshot$Params);
    }

    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher
    public final GetThumbnail.Result onGetThumbnail(Context context, GetThumbnail.Params params) {
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            final Bitmap generateThumbnail = new ThumbnailGenerator(context).generateThumbnail(params);
            ParcelFileDescriptor encodeBitmapToPipe = generateThumbnail != null ? BitmapUtils.encodeBitmapToPipe(generateThumbnail, Bitmap.CompressFormat.JPEG, new Runnable() { // from class: com.android.systemui.wallpaper.provider.ProviderCallDispatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    generateThumbnail.recycle();
                }
            }) : null;
            Log.i("ImageWallpaper[ProviderCallDispatcher]", "onGetThumbnail: wpId=" + params.wallpaperId + ", which=" + params.which + ", srcWhich=" + params.sourceWhich + ", rotation=" + params.rotation + ", size=" + BitmapUtils.getBitmapSizeString(generateThumbnail) + ", elapsed=" + (SystemClock.elapsedRealtime() - elapsedRealtime));
            if (encodeBitmapToPipe != null) {
                return new GetThumbnail.Result(encodeBitmapToPipe);
            }
            return null;
        } catch (Exception e) {
            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("onGetThumbnail: e=", e, "ImageWallpaper[ProviderCallDispatcher]", e);
            return null;
        }
    }
}
