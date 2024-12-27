package com.android.systemui.media.controls.ui.util;

import android.app.WallpaperColors;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.util.Log;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

public final class MediaArtworkHelper {
    public static final MediaArtworkHelper INSTANCE = new MediaArtworkHelper();

    private MediaArtworkHelper() {
    }

    public static ColorScheme getColorScheme(Context context, String str, String str2, Style style) {
        try {
            return new ColorScheme(WallpaperColors.fromDrawable(context.getPackageManager().getApplicationIcon(str)), true, style);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(str2, "Fail to get media app info", e);
            return null;
        }
    }

    public static Object getWallpaperColor(Context context, CoroutineDispatcher coroutineDispatcher, Icon icon, String str, Continuation continuation) {
        return BuildersKt.withContext(coroutineDispatcher, new MediaArtworkHelper$getWallpaperColor$2(icon, context, str, null), continuation);
    }
}
