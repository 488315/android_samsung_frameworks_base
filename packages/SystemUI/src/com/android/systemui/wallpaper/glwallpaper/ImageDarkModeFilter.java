package com.android.systemui.wallpaper.glwallpaper;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;

public final class ImageDarkModeFilter {
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
            boolean isApplyDarkFilterToWallpaper = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isApplyDarkFilterToWallpaper();
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isApplyToWallpaper: ", "ImageDarkModeFilter", isApplyDarkFilterToWallpaper);
            if (isApplyDarkFilterToWallpaper) {
                float darkModeDimOpacity = semWallpaperColors.getDarkModeDimOpacity();
                int parseColor = Color.parseColor("#000000");
                int red = Color.red(parseColor);
                int green = Color.green(parseColor);
                int blue = Color.blue(parseColor);
                Log.i("ImageDarkModeFilter", " Dark mode enabled : opacity :" + darkModeDimOpacity);
                if (darkModeDimOpacity > 0.25f) {
                    Log.i("ImageDarkModeFilter", " Over limit dark mode opacity. So change opacity");
                    darkModeDimOpacity = 0.25f;
                }
                return new float[]{red / 255.0f, green / 255.0f, blue / 255.0f, darkModeDimOpacity};
            }
        }
        int adaptiveDimColor = semWallpaperColors.getAdaptiveDimColor();
        float adaptiveDimOpacity = semWallpaperColors.getAdaptiveDimOpacity();
        if (adaptiveDimOpacity <= 0.0f) {
            return null;
        }
        int red2 = Color.red(adaptiveDimColor);
        int green2 = Color.green(adaptiveDimColor);
        int blue2 = Color.blue(adaptiveDimColor);
        Log.i("ImageDarkModeFilter", " Adaptive dim enabled : col" + adaptiveDimColor + " , opacity :" + adaptiveDimOpacity);
        return new float[]{red2 / 255.0f, green2 / 255.0f, blue2 / 255.0f, adaptiveDimOpacity};
    }
}
