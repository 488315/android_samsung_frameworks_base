package com.samsung.android.desktopmode;

import android.content.Context;
import android.content.res.Resources;
import android.media.audio.common.AudioDeviceDescription;
import android.os.Build;
import android.os.Debug;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes6.dex */
public class DesktopModeFeature {
    public static final boolean DEBUG;

    @Deprecated
    public static final boolean ENABLED = false;

    @Deprecated
    public static final boolean FEATURE_AUTO_OPEN_LAST_APP = false;

    @Deprecated
    public static final boolean FEATURE_COVERS = false;

    @Deprecated
    public static final boolean FEATURE_OFFICIAL_ADAPTERS = false;

    @Deprecated
    public static final boolean FEATURE_SPEN = false;

    @Deprecated
    public static final boolean FEATURE_STANDALONE_MODE_WALLPAPER;

    @Deprecated
    public static final boolean FEATURE_TOUCHPAD = false;

    @Deprecated
    public static final boolean FEATURE_UNOFFICIAL_ADAPTERS = false;

    @Deprecated
    public static final boolean FOLDABLE_TYPE_FOLD;

    @Deprecated
    public static final boolean IS_FOLDABLE = false;

    @Deprecated
    public static final boolean IS_TABLET;

    @Deprecated
    public static final boolean SPEN_INBOX_MODEL;

    @Deprecated
    private static final int SPEN_USP_LEVEL;

    @Deprecated
    public static final Set<String> SUPPORTED_MODES;

    @Deprecated
    public static final boolean SUPPORT_DEX_ON_PC;

    @Deprecated
    public static final boolean SUPPORT_DUAL;

    @Deprecated
    public static final boolean SUPPORT_NEW_DEX;

    @Deprecated
    public static final boolean SUPPORT_SFC;

    @Deprecated
    public static final boolean SUPPORT_SPEN;

    @Deprecated
    public static final boolean SUPPORT_STANDALONE;

    @Deprecated
    public static final boolean SUPPORT_UIBC_EXTENSION_MOUSE_ICON_SYNC;

    @Deprecated
    public static final boolean SUPPORT_WIRELESS_DEX;

    @Deprecated
    public static boolean isDesktopMode(Resources resources) {
        return false;
    }

    static {
        boolean z = true;
        DEBUG = Debug.semIsProductDev() || Build.IS_DEBUGGABLE || Log.isLoggable("DMS", 3);
        FEATURE_STANDALONE_MODE_WALLPAPER = Build.VERSION.SEM_PLATFORM_INT < 140100;
        IS_TABLET = SystemProperties.get("ro.build.characteristics").contains(BnRConstants.DEVICETYPE_TABLET) || isDebuggableAndSysPropSet(BnRConstants.DEVICETYPE_TABLET);
        Set<String> unmodifiableSet = Collections.unmodifiableSet(new ArraySet(Arrays.asList(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE").split(","))));
        SUPPORTED_MODES = unmodifiableSet;
        SUPPORT_DUAL = unmodifiableSet.contains("dual") || isDebuggableAndSysPropSet("dual");
        SUPPORT_DEX_ON_PC = unmodifiableSet.contains("dexforpc") || isDebuggableAndSysPropSet("dop");
        SUPPORT_STANDALONE = unmodifiableSet.contains("standalone") || isDebuggableAndSysPropSet("standalone");
        boolean z2 = unmodifiableSet.contains(AudioDeviceDescription.CONNECTION_WIRELESS) || isDebuggableAndSysPropSet(AudioDeviceDescription.CONNECTION_WIRELESS);
        SUPPORT_WIRELESS_DEX = z2;
        if (!unmodifiableSet.contains("newdex") && !isDebuggableAndSysPropSet("newdex")) {
            z = false;
        }
        SUPPORT_NEW_DEX = z;
        SUPPORT_UIBC_EXTENSION_MOUSE_ICON_SYNC = z2;
        SPEN_USP_LEVEL = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION");
        SUPPORT_SPEN = false;
        SPEN_INBOX_MODEL = false;
        SUPPORT_SFC = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PD_HV");
        FOLDABLE_TYPE_FOLD = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
    }

    private static boolean isDebuggableAndSysPropSet(String str) {
        if (Build.IS_DEBUGGABLE) {
            if (SystemProperties.getBoolean("persist.service.dex." + str, false)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean isDesktopMode(Context context) {
        return isDesktopMode(context.getResources());
    }
}
