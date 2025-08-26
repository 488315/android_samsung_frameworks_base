package com.android.systemui.wallpaper.glwallpaper;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes3.dex */
public class ImageDarkModeFilter {
    public ImageDarkModeFilter(Context context) {
    }

    public static float[] getWallpaperFilterColor(Context context, SemWallpaperColors semWallpaperColors) {
        if (semWallpaperColors == null) {
            Log.i("ImageDarkModeFilter", " color object is null");
            return null;
        }
        boolean z = (context.getApplicationContext().getResources().getConfiguration().uiMode & 32) != 0;
        boolean z2 = Settings.System.getInt(context.getContentResolver(), SettingsHelper.INDEX_DARK_THEME, 0) == 1;
        Log.d("ImageDarkModeFilter", "isNightMode : Window = " + context.getResources().getConfiguration().uiMode + "App = " + context.getApplicationContext().getResources().getConfiguration().uiMode);
        Log.d("ImageDarkModeFilter", "isNightMode: " + z2 + " ui_mode " + z);
        if (z2 || z) {
            boolean zIsApplyDarkFilterToWallpaper = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isApplyDarkFilterToWallpaper();
            EmergencyButtonController$$ExternalSyntheticOutline0.m("isApplyToWallpaper: ", "ImageDarkModeFilter", zIsApplyDarkFilterToWallpaper);
            if (zIsApplyDarkFilterToWallpaper) {
                float darkModeDimOpacity = semWallpaperColors.getDarkModeDimOpacity();
                int color = Color.parseColor("#000000");
                int iRed = Color.red(color);
                int iGreen = Color.green(color);
                int iBlue = Color.blue(color);
                Log.i("ImageDarkModeFilter", " Dark mode enabled : opacity :" + darkModeDimOpacity);
                if (darkModeDimOpacity > 0.25f) {
                    Log.i("ImageDarkModeFilter", " Over limit dark mode opacity. So change opacity");
                    darkModeDimOpacity = 0.25f;
                }
                return new float[]{iRed / 255.0f, iGreen / 255.0f, iBlue / 255.0f, darkModeDimOpacity};
            }
        }
        int adaptiveDimColor = semWallpaperColors.getAdaptiveDimColor();
        float adaptiveDimOpacity = semWallpaperColors.getAdaptiveDimOpacity();
        if (adaptiveDimOpacity <= 0.0f) {
            return null;
        }
        int iRed2 = Color.red(adaptiveDimColor);
        int iGreen2 = Color.green(adaptiveDimColor);
        int iBlue2 = Color.blue(adaptiveDimColor);
        Log.i("ImageDarkModeFilter", " Adaptive dim enabled : col" + adaptiveDimColor + " , opacity :" + adaptiveDimOpacity);
        return new float[]{iRed2 / 255.0f, iGreen2 / 255.0f, iBlue2 / 255.0f, adaptiveDimOpacity};
    }
}
