package com.samsung.android.wallpaper.live.sdk.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Result;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperEngineManager;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class LiveWallpaperProviderCallDispatcher {
    /* JADX WARN: Type inference failed for: r7v3, types: [com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Result] */
    public GetScreenshot$Result onGetScreenshot(Context context, GetScreenshot$Params getScreenshot$Params) {
        final Bitmap copySurfaceToBitmapSync;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        LiveWallpaperService.BaseEngine engine = LiveWallpaperEngineManager.getInstance(context).getEngine(getScreenshot$Params.which);
        if (engine == null) {
            SdkLog.i("LiveWallpaperProviderCallDispatcher", "onGetScreenshot : engine is null. which=" + getScreenshot$Params.which);
            return null;
        }
        new EngineScreenshotHelper();
        ScreenshotResults onGetScreenshot = engine.onGetScreenshot(new ScreenshotOptions(getScreenshot$Params.purpose));
        if ((onGetScreenshot == null || (copySurfaceToBitmapSync = onGetScreenshot.mBitmap) == null) && (copySurfaceToBitmapSync = DisplayUtils.copySurfaceToBitmapSync(engine.getSurfaceHolder(), null, null)) == null) {
            SdkLog.e("EngineScreenshotHelper", "captureSurface : screenshot is null");
        }
        if (copySurfaceToBitmapSync == null) {
            return null;
        }
        final ParcelFileDescriptor encodeBitmapToPipe = BitmapUtils.encodeBitmapToPipe(copySurfaceToBitmapSync, "png".equals(getScreenshot$Params.requiredImageFormat) ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, new Runnable() { // from class: com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                copySurfaceToBitmapSync.recycle();
            }
        });
        StringBuilder sb = new StringBuilder("onGetScreenshot : elapsed=");
        sb.append(SystemClock.elapsedRealtime() - elapsedRealtime);
        sb.append(", w=");
        sb.append(copySurfaceToBitmapSync.getWidth());
        sb.append(", h=");
        sb.append(copySurfaceToBitmapSync.getHeight());
        sb.append(", isSuccess=");
        sb.append(encodeBitmapToPipe != null);
        SdkLog.i("LiveWallpaperProviderCallDispatcher", sb.toString());
        return new ProviderCallResult(encodeBitmapToPipe) { // from class: com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Result
            public final ParcelFileDescriptor mScreenshotFileDescriptor;

            {
                this.mScreenshotFileDescriptor = encodeBitmapToPipe;
            }

            @Override // com.samsung.android.wallpaper.live.sdk.provider.ProviderCallResult
            public final Bundle toBundle() {
                Bundle bundle = new Bundle();
                ParcelFileDescriptor parcelFileDescriptor = this.mScreenshotFileDescriptor;
                if (parcelFileDescriptor != null) {
                    bundle.putParcelable("image_file_descriptor", parcelFileDescriptor);
                }
                if (bundle.isEmpty()) {
                    return null;
                }
                return bundle;
            }
        };
    }

    public abstract GetThumbnail.Result onGetThumbnail(Context context, GetThumbnail.Params params);
}
