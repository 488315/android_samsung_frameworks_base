package com.android.systemui.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.provider.Settings;
import android.view.DisplayCutout;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.QuickStepContract;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class Utils {
    public static boolean SPF_SupportInstantHotspot = true;
    public static boolean SPF_SupportMobileApEnhanced = true;
    public static boolean SPF_SupportMobileApEnhancedLite = false;
    public static boolean SPF_SupportMobileApEnhancedWifiOnlyLite = false;
    private static Boolean sUseQsMediaPlayer;

    @Deprecated
    public static int getStatusBarHeaderHeightKeyguard(Context context) {
        int statusBarHeight = SystemBarUtils.getStatusBarHeight(context);
        DisplayCutout cutout = context.getDisplay().getCutout();
        return Math.max(statusBarHeight, context.getResources().getDimensionPixelSize(R.dimen.status_bar_header_height_keyguard) + (cutout == null ? 0 : cutout.getWaterfallInsets().top));
    }

    public static boolean isDeviceFoldable(Context context) {
        return context.getResources().getIntArray(android.R.array.preloaded_freeform_multi_window_drawables).length != 0;
    }

    public static boolean isGesturalModeOnDefaultDisplay(Context context, DisplayTracker displayTracker, int i) {
        int displayId = context.getDisplayId();
        displayTracker.getClass();
        return displayId == 0 && QuickStepContract.isGesturalMode(i);
    }

    public static boolean isHeadlessRemoteDisplayProvider(PackageManager packageManager, String str) {
        if (packageManager.checkPermission("android.permission.REMOTE_DISPLAY_PROVIDER", str) != 0) {
            return false;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        return packageManager.queryIntentActivities(intent, 0).isEmpty();
    }

    @Deprecated
    public static <T> void safeForeach(List<T> list, Consumer<T> consumer) {
        for (int size = list.size() - 1; size >= 0; size--) {
            T t = list.get(size);
            if (t != null) {
                consumer.accept(t);
            }
        }
    }

    public static boolean useCollapsedMediaInLandscape(Resources resources) {
        return resources.getBoolean(R.bool.config_quickSettingsMediaLandscapeCollapsed);
    }

    public static boolean useMediaResumption(Context context) {
        Settings.Secure.getInt(context.getContentResolver(), "qs_media_resumption", 1);
        return false;
    }

    public static boolean useQsMediaPlayer(Context context) {
        if (sUseQsMediaPlayer == null) {
            sUseQsMediaPlayer = Boolean.valueOf(Settings.Global.getInt(context.getContentResolver(), "qs_media_controls", 1) > 0 && context.getResources().getBoolean(android.R.bool.config_secondaryBuiltInDisplayIsRound));
        }
        return sUseQsMediaPlayer.booleanValue();
    }
}
