package com.android.systemui.power.utils;

import android.util.Slog;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class ChargerAnimationUtils {
    public static final ChargerAnimationUtils INSTANCE = new ChargerAnimationUtils();

    private ChargerAnimationUtils() {
    }

    public static final boolean checkExceptionalLanguage() {
        String language = Locale.getDefault().getLanguage();
        return "he".equals(language) || "ur".equals(language) || "tr".equals(language) || "eu".equals(language);
    }

    public static String getLottieString(int i, int i2, boolean z) {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(AbstractResolvableFuture$$ExternalSyntheticOutline0.m((i2 == 1 ? "rtl_" : "").concat(z ? "nowbar_gradient_" : "indicator_gradient_"), i != 3 ? (i == 4 || i == 5) ? "superfast" : SystemUIAnalytics.QPNE_VID_NORMAL : "fast"), ".json");
    }

    public static float getProgressbarComputeWidth(float f, int i, int i2) {
        return i == 0 ? (i2 / 100.0f) * f : -((i2 / 100.0f) * f);
    }

    public static final boolean isAodOrLockScreen() {
        int state = ((SysuiStatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class)).getState();
        return state == 1 || state == 2;
    }

    public static boolean isWhiteWallpaper(boolean z) {
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(256L, z);
        Slog.d("PowerUI.ChargerAnimationUtil", "isWhiteWallpaper in NAVIBAR : " + zIsWhiteKeyguardWallpaper + " / CoverScreen : " + z);
        return zIsWhiteKeyguardWallpaper;
    }
}
