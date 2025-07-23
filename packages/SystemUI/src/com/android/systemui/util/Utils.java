package com.android.systemui.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.devicestate.DeviceStateManager;
import android.provider.Settings;
import android.view.DisplayCutout;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.system.QuickStepContract;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public static boolean isDeviceFoldable(Resources resources, DeviceStateManager deviceStateManager) {
        List supportedDeviceStates = deviceStateManager.getSupportedDeviceStates();
        for (int i = 0; i < supportedDeviceStates.size(); i++) {
            android.hardware.devicestate.DeviceState deviceState = (android.hardware.devicestate.DeviceState) supportedDeviceStates.get(i);
            if (deviceState.hasProperty(11) || deviceState.hasProperty(12)) {
                return true;
            }
        }
        return false;
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
            sUseQsMediaPlayer = Boolean.valueOf(Settings.Global.getInt(context.getContentResolver(), "qs_media_controls", 1) > 0 && context.getResources().getBoolean(android.R.bool.config_smppsim_response_via_ims));
        }
        return sUseQsMediaPlayer.booleanValue();
    }
}
