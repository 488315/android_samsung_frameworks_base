package com.android.systemui.keyguardimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.WallpaperUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class WallpaperImageCreator implements ImageCreator {
    public final String TAG;
    public final Context mContext;
    public final CoverWallpaper mCoverWallpaper;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public final PluginWallpaperManager mPluginWallpaperManager;
    protected final SettingsHelper mSettingsHelper;

    public WallpaperImageCreator(String str, Context context, SettingsHelper settingsHelper, PluginWallpaperManager pluginWallpaperManager, CoverWallpaper coverWallpaper, KeyguardWallpaper keyguardWallpaper) {
        this.TAG = str;
        this.mContext = context;
        this.mSettingsHelper = settingsHelper;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mCoverWallpaper = coverWallpaper;
        this.mKeyguardWallpaper = keyguardWallpaper;
    }

    /* JADX WARN: Removed duplicated region for block: B:141:0x03c0 A[Catch: Exception -> 0x03a7, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Exception -> 0x03a7, blocks: (B:137:0x037e, B:158:0x03a3, B:161:0x03b5, B:141:0x03c0, B:172:0x03ce, B:171:0x03cb, B:153:0x0386, B:155:0x0395, B:157:0x039d, B:160:0x03ac, B:139:0x03b9, B:167:0x03c6), top: B:136:0x037e, inners: #3, #6 }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0386 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00de A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01ac  */
    @Override // com.android.systemui.keyguardimage.ImageCreator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Bitmap createImage(com.android.systemui.keyguardimage.ImageOptionCreator.ImageOption r18, android.graphics.Point r19) {
        /*
            Method dump skipped, instructions count: 1085
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguardimage.WallpaperImageCreator.createImage(com.android.systemui.keyguardimage.ImageOptionCreator$ImageOption, android.graphics.Point):android.graphics.Bitmap");
    }

    public final Bitmap makeResult(Bitmap bitmap, int i, int i2, int i3) {
        Bitmap bitmap2 = bitmap;
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "makeResult: w = ", ", h = ", ", rotation = ");
        m.append(i3);
        String sb = m.toString();
        String str = this.TAG;
        Log.i(str, sb);
        if (bitmap2 == null || bitmap2.isRecycled()) {
            Log.e(str, "makeResult: thumbnail is invalid");
            return null;
        }
        boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        if (bitmap2.isRecycled()) {
            bitmap2 = null;
        } else {
            int width = bitmap2.getWidth();
            int height = bitmap2.getHeight();
            float f = width;
            float f2 = f / 2.0f;
            float f3 = height;
            float f4 = f3 / 2.0f;
            float f5 = width * i2 > i * height ? (i2 / f3) * 1.0f : (i / f) * 1.0f;
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i2, i, "metricsHeight=", " metricsWidth=", "WallpaperUtils");
            float f6 = (i * 1.0f) / f5;
            float f7 = (i2 * 1.0f) / f5;
            float f8 = f2 - (f6 / 2.0f);
            if (f8 < 0.0f) {
                f8 = 0.0f;
            }
            float f9 = f4 - (f7 / 2.0f);
            float f10 = f9 >= 0.0f ? f9 : 0.0f;
            Log.d("WallpaperUtils", "widthOrigin = " + width);
            Log.d("WallpaperUtils", "heightOrigin = " + height);
            Log.d("WallpaperUtils", "scale = " + f5);
            Log.d("WallpaperUtils", "centerX = " + f2);
            Log.d("WallpaperUtils", "centerY = " + f4);
            Log.d("WallpaperUtils", "startX = " + f8);
            Log.d("WallpaperUtils", "startY = " + f10);
            Log.d("WallpaperUtils", "width = " + f6);
            Log.d("WallpaperUtils", "height = " + f7);
            if (Math.round(f8) == 0 && Math.round(f10) == 0 && width == Math.round(f6) && height == Math.round(f7)) {
                Log.d("WallpaperUtils", "It doesn't need to crop bitmap");
            } else {
                if (Math.round(f6) < 1 || Math.round(f7) < 1 || i < 1 || i2 < 1) {
                    Log.d("WallpaperUtils", "Math.round(width) < 1 || Math.round(height) < 1 || mMatricsWidth < 1 || mMatricsHeight < 1");
                } else {
                    if (Math.round(f8) + Math.round(f6) <= width) {
                        if (Math.round(f10) + Math.round(f7) <= height) {
                            Log.d("WallpaperUtils", "Cropping...");
                            bitmap2 = Bitmap.createBitmap(bitmap2, Math.round(f8), Math.round(f10), Math.round(f6), Math.round(f7));
                        }
                    }
                    Log.d("WallpaperUtils", "Calculated crop size error");
                }
                bitmap2 = null;
            }
        }
        return i3 <= 0 ? bitmap2 : WallpaperUtils.getRotatedBitmap(bitmap2, i3);
    }
}
