package com.android.systemui.blur.domain.interactor;

import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.android.systemui.blur.di.ScreenShotBitmapProvider;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.io.IOException;
import kotlin.io.CloseableKt;

/* loaded from: classes.dex */
public final class WallpaperScreenShotProvider implements ScreenShotBitmapProvider {
    public final Context context;
    public Bitmap prevWallPaper;
    public final SecBlurSettingsInteractor settingsInteractor;

    public WallpaperScreenShotProvider(Context context, SecBlurSettingsInteractor secBlurSettingsInteractor) {
        this.context = context;
        this.settingsInteractor = secBlurSettingsInteractor;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00be A[RETURN] */
    @Override // com.android.systemui.blur.di.ScreenShotBitmapProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bitmap getScreenShot() throws IOException {
        Bitmap bitmapDecodeFileDescriptor;
        ParcelFileDescriptor parcelFileDescriptorSemGetScreenshotFileDescriptor;
        if (((Boolean) this.settingsInteractor.blurReduced.$$delegate_0.getValue()).booleanValue()) {
            Log.d("ScreenshotBitMapProvider", "Power saving mode is set.");
            return null;
        }
        try {
            parcelFileDescriptorSemGetScreenshotFileDescriptor = WallpaperManager.getInstance(this.context).semGetScreenshotFileDescriptor(WallpaperUtils.sCurrentWhich, ActivityManager.semGetCurrentUser(), null);
            try {
            } finally {
            }
        } catch (Exception e) {
            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("createImage: e=", e, "ScreenShotBitmapProvider", e);
        }
        if (parcelFileDescriptorSemGetScreenshotFileDescriptor == null) {
            Log.e("ScreenShotBitmapProvider", "createImage: failed to get screenshot");
            CloseableKt.closeFinally(parcelFileDescriptorSemGetScreenshotFileDescriptor, null);
            bitmapDecodeFileDescriptor = null;
            if (bitmapDecodeFileDescriptor != null) {
                return null;
            }
            Log.d("ScreenShotBitmapProvider", "getNormalWallpaperScreenShot()");
            if (((bitmapDecodeFileDescriptor.getColor(bitmapDecodeFileDescriptor.getWidth() / 2, bitmapDecodeFileDescriptor.getHeight() / 2).toArgb() >>> 24) * 1.0f) / 255.0f == 0.0f) {
                Bitmap bitmap = this.prevWallPaper;
                if ((bitmap == null ? null : bitmap) != null) {
                    bitmapDecodeFileDescriptor = bitmap != null ? bitmap : null;
                }
            }
            this.prevWallPaper = bitmapDecodeFileDescriptor;
            int width = bitmapDecodeFileDescriptor.getWidth();
            ScreenShotBitmapProvider.Companion.getClass();
            int i = ScreenShotBitmapProvider.Companion.RESIZE_SCALE;
            return Bitmap.createScaledBitmap(bitmapDecodeFileDescriptor, width / i, bitmapDecodeFileDescriptor.getHeight() / i, true);
        }
        bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptorSemGetScreenshotFileDescriptor.getFileDescriptor(), null, new BitmapFactory.Options());
        if (bitmapDecodeFileDescriptor != null) {
            Log.d("ScreenShotBitmapProvider", "createImage1: live screenshot = " + bitmapDecodeFileDescriptor);
        } else {
            bitmapDecodeFileDescriptor = null;
        }
        parcelFileDescriptorSemGetScreenshotFileDescriptor.close();
        if (bitmapDecodeFileDescriptor != null) {
        }
    }
}
