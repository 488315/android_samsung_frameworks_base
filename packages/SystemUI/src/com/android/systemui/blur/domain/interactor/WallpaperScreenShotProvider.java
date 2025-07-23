package com.android.systemui.blur.domain.interactor;

import android.content.Context;
import android.graphics.Bitmap;
import com.android.systemui.blur.di.ScreenShotBitmapProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WallpaperScreenShotProvider implements ScreenShotBitmapProvider {
    public final Context context;
    public Bitmap prevWallPaper;
    public final SecBlurSettingsInteractor settingsInteractor;

    public WallpaperScreenShotProvider(Context context, SecBlurSettingsInteractor secBlurSettingsInteractor) {
        this.context = context;
        this.settingsInteractor = secBlurSettingsInteractor;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00be A[RETURN] */
    @Override // com.android.systemui.blur.di.ScreenShotBitmapProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.Bitmap getScreenShot() {
        /*
            r6 = this;
            java.lang.String r0 = "ScreenShotBitmapProvider"
            com.android.systemui.blur.domain.interactor.SecBlurSettingsInteractor r1 = r6.settingsInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r1.blurReduced
            kotlinx.coroutines.flow.StateFlow r1 = r1.$$delegate_0
            java.lang.Object r1 = r1.getValue()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            r2 = 0
            if (r1 == 0) goto L1d
            java.lang.String r6 = "ScreenshotBitMapProvider"
            java.lang.String r0 = "Power saving mode is set."
            android.util.Log.d(r6, r0)
            return r2
        L1d:
            java.lang.String r1 = "createImage1: live screenshot = "
            android.content.Context r3 = r6.context
            android.app.WallpaperManager r3 = android.app.WallpaperManager.getInstance(r3)
            int r4 = com.android.systemui.wallpaper.WallpaperUtils.sCurrentWhich     // Catch: java.lang.Exception -> L3a
            int r5 = android.app.ActivityManager.semGetCurrentUser()     // Catch: java.lang.Exception -> L3a
            android.os.ParcelFileDescriptor r3 = r3.semGetScreenshotFileDescriptor(r4, r5, r2)     // Catch: java.lang.Exception -> L3a
            if (r3 != 0) goto L3e
            java.lang.String r1 = "createImage: failed to get screenshot"
            android.util.Log.e(r0, r1)     // Catch: java.lang.Throwable -> L3c
            kotlin.io.CloseableKt.closeFinally(r3, r2)     // Catch: java.lang.Exception -> L3a
            goto L6d
        L3a:
            r1 = move-exception
            goto L68
        L3c:
            r1 = move-exception
            goto L62
        L3e:
            java.io.FileDescriptor r4 = r3.getFileDescriptor()     // Catch: java.lang.Throwable -> L3c
            android.graphics.BitmapFactory$Options r5 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> L3c
            r5.<init>()     // Catch: java.lang.Throwable -> L3c
            android.graphics.Bitmap r4 = android.graphics.BitmapFactory.decodeFileDescriptor(r4, r2, r5)     // Catch: java.lang.Throwable -> L3c
            if (r4 == 0) goto L5d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3c
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L3c
            r5.append(r4)     // Catch: java.lang.Throwable -> L3c
            java.lang.String r1 = r5.toString()     // Catch: java.lang.Throwable -> L3c
            android.util.Log.d(r0, r1)     // Catch: java.lang.Throwable -> L3c
            goto L5e
        L5d:
            r4 = r2
        L5e:
            r3.close()     // Catch: java.lang.Exception -> L3a
            goto L6e
        L62:
            throw r1     // Catch: java.lang.Throwable -> L63
        L63:
            r4 = move-exception
            kotlin.io.CloseableKt.closeFinally(r3, r1)     // Catch: java.lang.Exception -> L3a
            throw r4     // Catch: java.lang.Exception -> L3a
        L68:
            java.lang.String r3 = "createImage: e="
            com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m(r3, r1, r0, r1)
        L6d:
            r4 = r2
        L6e:
            if (r4 == 0) goto Lbe
            java.lang.String r1 = "getNormalWallpaperScreenShot()"
            android.util.Log.d(r0, r1)
            int r0 = r4.getWidth()
            int r0 = r0 / 2
            int r1 = r4.getHeight()
            int r1 = r1 / 2
            android.graphics.Color r0 = r4.getColor(r0, r1)
            int r0 = r0.toArgb()
            int r0 = r0 >>> 24
            float r0 = (float) r0
            r1 = 1065353216(0x3f800000, float:1.0)
            float r0 = r0 * r1
            r1 = 1132396544(0x437f0000, float:255.0)
            float r0 = r0 / r1
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 != 0) goto La5
            android.graphics.Bitmap r0 = r6.prevWallPaper
            if (r0 != 0) goto L9d
            r1 = r2
            goto L9e
        L9d:
            r1 = r0
        L9e:
            if (r1 == 0) goto La5
            if (r0 != 0) goto La3
            goto La4
        La3:
            r2 = r0
        La4:
            r4 = r2
        La5:
            r6.prevWallPaper = r4
            int r6 = r4.getWidth()
            com.android.systemui.blur.di.ScreenShotBitmapProvider$Companion r0 = com.android.systemui.blur.di.ScreenShotBitmapProvider.Companion
            r0.getClass()
            int r0 = com.android.systemui.blur.di.ScreenShotBitmapProvider.Companion.RESIZE_SCALE
            int r6 = r6 / r0
            int r1 = r4.getHeight()
            int r1 = r1 / r0
            r0 = 1
            android.graphics.Bitmap r6 = android.graphics.Bitmap.createScaledBitmap(r4, r6, r1, r0)
            return r6
        Lbe:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider.getScreenShot():android.graphics.Bitmap");
    }
}
