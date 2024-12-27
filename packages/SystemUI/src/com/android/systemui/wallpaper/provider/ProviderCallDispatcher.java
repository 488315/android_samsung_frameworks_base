package com.android.systemui.wallpaper.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.util.Log;
import com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher;
import com.samsung.android.wallpaper.live.sdk.provider.ProviderCallResult;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Result;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail$Result;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;

public final class ProviderCallDispatcher extends LiveWallpaperProviderCallDispatcher {
    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher
    public final GetScreenshot$Result onGetScreenshot(Context context, GetScreenshot$Params getScreenshot$Params) {
        Log.d("ImageWallpaper[ProviderCallDispatcher]", "onGetScreenshot");
        return super.onGetScreenshot(context, getScreenshot$Params);
    }

    /* JADX WARN: Type inference failed for: r7v8, types: [com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail$Result] */
    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher
    public final GetThumbnail$Result onGetThumbnail(Context context, GetThumbnail$Params getThumbnail$Params) {
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            final Bitmap generateThumbnail = new ThumbnailGenerator(context).generateThumbnail(getThumbnail$Params);
            final ParcelFileDescriptor encodeBitmapToPipe = generateThumbnail != null ? BitmapUtils.encodeBitmapToPipe(generateThumbnail, Bitmap.CompressFormat.JPEG, new Runnable() { // from class: com.android.systemui.wallpaper.provider.ProviderCallDispatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    generateThumbnail.recycle();
                }
            }) : null;
            Log.d("ImageWallpaper[ProviderCallDispatcher]", "onGetThumbnail: wpId=" + getThumbnail$Params.wallpaperId + ", which=" + getThumbnail$Params.which + ", srcWhich=" + getThumbnail$Params.sourceWhich + ", rotation=" + getThumbnail$Params.rotation + ", size=" + BitmapUtils.getBitmapSizeString(generateThumbnail) + ", elapsed=" + (SystemClock.elapsedRealtime() - elapsedRealtime));
            if (encodeBitmapToPipe != null) {
                return new ProviderCallResult(encodeBitmapToPipe) { // from class: com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail$Result
                    public final ParcelFileDescriptor mThumbnailFileDescriptor;

                    {
                        this.mThumbnailFileDescriptor = encodeBitmapToPipe;
                    }

                    @Override // com.samsung.android.wallpaper.live.sdk.provider.ProviderCallResult
                    public final Bundle toBundle() {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("thumbnail_file_descriptor", this.mThumbnailFileDescriptor);
                        return bundle;
                    }
                };
            }
            return null;
        } catch (Exception e) {
            Log.e("ImageWallpaper[ProviderCallDispatcher]", "onGetThumbnail: e=" + e, e);
            return null;
        }
    }
}
