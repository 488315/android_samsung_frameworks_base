package com.android.systemui.wallpaper.glwallpaper;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ImageDarkModeFilter {
    public ImageDarkModeFilter(Context context) {
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static float[] getWallpaperFilterColor(Context context, SemWallpaperColors semWallpaperColors) {
        boolean z;
        if (semWallpaperColors == null) {
            Log.i("ImageDarkModeFilter", " color object is null");
            return null;
        }
        boolean z2 = (context.getApplicationContext().getResources().getConfiguration().uiMode & 32) != 0;
        boolean z3 = Settings.System.getInt(context.getContentResolver(), "display_night_theme", 0) == 1;
        Log.d("ImageDarkModeFilter", "isNightMode : Window = " + context.getResources().getConfiguration().uiMode + "App = " + context.getApplicationContext().getResources().getConfiguration().uiMode);
        StringBuilder sb = new StringBuilder("isNightMode: ");
        sb.append(z3);
        sb.append(" ui_mode ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, z2, "ImageDarkModeFilter");
        if (z3 || z2) {
            boolean z4 = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("display_night_theme_wallpaper").getIntValue() == 1;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isApplyToWallpaper: ", z4, "ImageDarkModeFilter");
            if (z4) {
                z = true;
                if (!z) {
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
        z = false;
        if (!z) {
        }
    }
}
