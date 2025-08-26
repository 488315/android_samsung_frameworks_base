package com.samsung.android.wallpaper.live.sdk.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotOptions;
import com.samsung.android.wallpaper.live.sdk.data.ScreenshotResults;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetBackgroundRegion$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetBackgroundRegion$Result;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Result;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperEngineManager;
import com.samsung.android.wallpaper.live.sdk.service.LiveWallpaperService;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import com.samsung.android.wallpaper.live.sdk.utils.SdkLog;
import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class LiveWallpaperProviderCallDispatcher {
    public GetBackgroundRegion$Result onGetBackgroundRegion(Context context, GetBackgroundRegion$Params getBackgroundRegion$Params) {
        return null;
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Result] */
    public GetScreenshot$Result onGetScreenshot(Context context, GetScreenshot$Params getScreenshot$Params) throws IOException {
        final Bitmap bitmapCopySurfaceToBitmapSync;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        LiveWallpaperService.BaseEngine engine = LiveWallpaperEngineManager.getInstance(context).getEngine(getScreenshot$Params.which);
        if (engine == null) {
            SdkLog.i("LiveWallpaperProviderCallDispatcher", "onGetScreenshot : engine is null. which=" + getScreenshot$Params.which);
            return null;
        }
        new EngineScreenshotHelper();
        ScreenshotResults screenshotResultsOnGetScreenshot = engine.onGetScreenshot(new ScreenshotOptions(getScreenshot$Params.purpose));
        if ((screenshotResultsOnGetScreenshot == null || (bitmapCopySurfaceToBitmapSync = screenshotResultsOnGetScreenshot.mBitmap) == null) && (bitmapCopySurfaceToBitmapSync = DisplayUtils.copySurfaceToBitmapSync(engine.getSurfaceHolder(), null, null)) == null) {
            SdkLog.e("EngineScreenshotHelper", "captureSurface : screenshot is null");
        }
        if (bitmapCopySurfaceToBitmapSync == null) {
            return null;
        }
        final ParcelFileDescriptor parcelFileDescriptorEncodeBitmapToPipe = BitmapUtils.encodeBitmapToPipe(bitmapCopySurfaceToBitmapSync, "png".equals(getScreenshot$Params.requiredImageFormat) ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, new Runnable() { // from class: com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                bitmapCopySurfaceToBitmapSync.recycle();
            }
        });
        StringBuilder sb = new StringBuilder("onGetScreenshot : elapsed=");
        sb.append(SystemClock.elapsedRealtime() - jElapsedRealtime);
        sb.append(", w=");
        sb.append(bitmapCopySurfaceToBitmapSync.getWidth());
        sb.append(", h=");
        sb.append(bitmapCopySurfaceToBitmapSync.getHeight());
        sb.append(", isSuccess=");
        sb.append(parcelFileDescriptorEncodeBitmapToPipe != null);
        SdkLog.i("LiveWallpaperProviderCallDispatcher", sb.toString());
        return new ProviderCallResult(parcelFileDescriptorEncodeBitmapToPipe) { // from class: com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Result
            public final ParcelFileDescriptor mScreenshotFileDescriptor;

            {
                this.mScreenshotFileDescriptor = parcelFileDescriptorEncodeBitmapToPipe;
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
