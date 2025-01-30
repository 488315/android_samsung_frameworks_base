package com.samsung.android.rune;

import android.p009os.Build;
import android.p009os.SystemProperties;
import android.util.ArraySet;
import com.samsung.android.core.p036pm.PmUtils;
import com.samsung.android.core.p036pm.multiuser.MultiUserSupportsHelper;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class PMRune {
    public static final boolean APEX_SUPPORTED_TARGET = true;
    public static final boolean APP_OPS_IGNORE_SYSTEMUI_LOCATION_ACCESS = true;
    public static final boolean CST_FEATURE_ENABLED = true;
    public static final boolean INSTD_VM_TRACE = true;
    public static final boolean LA_CHECK_COMP_AVAILABLE = true;
    public static final boolean LA_CHECK_USERID = true;
    public static final boolean LA_LOG = true;
    public static final boolean LA_SEP = true;
    public static final boolean OMS_EXT_SUPPORT = true;
    public static final boolean OM_DISABLE_THEMING_LIST = true;
    public static final boolean PERM_BUG_FIX = true;
    public static final boolean PERM_CALLBACK_STABILITY = true;
    public static final boolean PERM_ENFORCE_PRIV_PERM_CONTROL = true;
    public static final boolean PERM_LABEL = true;
    public static final boolean PERM_LOG = true;
    public static final boolean PERM_POLICY_PERF = true;
    public static final boolean PERM_PREGRANT_FOR_CUSTOM_PARTITION = true;
    public static final boolean PERM_PRIV_PERM_WARNNING = true;
    public static final boolean PERM_RECONSIDER_WHEN_ROLLBACK = true;
    public static final boolean PM_32BIT_APP_RUNNING_IN_ABI64;
    public static final boolean PM_ALLOW_STAGED_INSTALL = true;
    public static final boolean PM_ALLOW_TO_SET_CATEGORY = true;
    public static final boolean PM_BACKUP_PACKAGE_FILE = true;
    public static final boolean PM_BACKUP_SETTINGS = true;
    public static final boolean PM_BADGE_ON_MONETIZED_APP_SUPPORTED;
    public static final boolean PM_BG_DEXOPT_DEBUG = true;
    public static final boolean PM_BG_DEXOPT_WHEN_FULL_CHARGE = true;
    public static final boolean PM_BOOT_DEXOPT_PROGRESS = true;
    public static final boolean PM_BR_ADD_USER_ID = true;
    public static final boolean PM_BR_ALLOW_LIST = true;
    public static final boolean PM_BR_ALLOW_LIST_ENFORCE = true;
    public static final boolean PM_CALCULATE_SDCARD_APP_DATA = true;
    public static final boolean PM_CHECK_VERIFIERES = true;
    public static final boolean PM_CLEANUP_CORRUPTED_FILE = true;
    public static final boolean PM_CLEAR_ALL_PREF = true;
    public static final boolean PM_CONTENT_DISPATCHER = true;
    public static final boolean PM_DELETE_PREF = true;
    public static final boolean PM_DESKTOP_MODE = true;
    public static final boolean PM_ENABLE_GMS;
    public static final boolean PM_FROZEN_TOAST = true;
    public static final boolean PM_GET_AVAILABLE_ID = true;
    public static final boolean PM_GRANT_ALL_RUNTIME_PERMISSION_UNPACK;
    public static final boolean PM_INSTALL_DEXOPT = true;
    public static final boolean PM_INSTALL_DIRECTLY = true;
    public static final boolean PM_INSTALL_DUPLICATE_APPS = true;
    public static final boolean PM_INSTALL_MUM_POLICY = true;
    public static final boolean PM_INSTALL_OVERHEAT_STANDBY = true;
    public static final boolean PM_INSTALL_RUNTIME_MANIFEST = true;
    public static final boolean PM_INSTALL_SCAN = true;
    public static final boolean PM_INSTALL_SILENT_UNINSTALLER = true;
    public static final boolean PM_INSTALL_SKIP_DEXOPT = true;
    public static final boolean PM_INSTALL_TO_SDCARD;
    public static final boolean PM_LDU;
    public static final boolean PM_LIFECYCLE = true;
    public static final boolean PM_LOG = true;
    public static final boolean PM_MAINTENANCE_MODE = true;
    public static final boolean PM_MAINTENANCE_MODE_AT_COMMAND = true;
    public static final boolean PM_MAINTENANCE_MODE_GUARD = true;
    public static final boolean PM_MOVE_TO_SDCARD = true;
    public static final boolean PM_NO_SIGNING = true;
    public static final boolean PM_NPE = true;
    public static final boolean PM_OMC_DELETE_AT_FIRST_BOOT = true;
    public static final boolean PM_OMC_GRANT_DEFAULT_PERMISSION = true;
    public static final boolean PM_OMC_SCAN_APKS = true;
    public static final boolean PM_PACKAGE_CACHE_CHECKER = true;
    public static final boolean PM_PDP = true;
    public static final boolean PM_PERF_RESOLVER = true;
    public static final boolean PM_PERF_THROTTLE_WRITE_PERMS = true;
    public static final boolean PM_PERF_WRITE_PERMS = true;
    public static final boolean PM_PKG_CACHE_DATE = true;
    public static final boolean PM_PREINSTALLER_ENABLED = true;
    public static final boolean PM_PROTECT_REQUIRED_APPS = true;
    public static final boolean PM_RDU = true;
    public static final boolean PM_RDX_LOG = true;
    public static final boolean PM_REMOVE_UPDATE_OWNER = true;
    public static final boolean PM_RESOLVER_PREFERRED = true;
    public static final boolean PM_SEP = true;
    public static final boolean PM_SHELL_RESTRICTIONS = true;
    public static final boolean PM_STATSD_LOG = true;
    public static final boolean PM_SUSPENDED_APP_ACTIVITY_KEEP_AFTER_UNLOCK = true;
    public static final boolean PM_SYSTEM_COMPRESSED_APP = true;
    public static final boolean PM_SYSTEM_TO_DATA_APP = true;
    public static final boolean PM_TEST = true;
    public static final boolean PM_UNKNOWN_SOURCE_IMPL = true;
    public static final boolean PM_WA_BG_DEXOPT_CTS = true;
    public static final boolean PM_WA_BOOTABLE_DAMAGED_XML = true;
    public static final boolean PM_WA_PARCELED_LIST;
    public static boolean PM_WA_WORK_COMP_CHANGED = false;
    public static final boolean RBM_BUG_FIX = true;
    public static final boolean RESOURCES_BUG_FIX = true;
    public static final boolean RES_FIX_ASSET_NAVIBAR = true;
    public static final boolean RM_FIX_RECEIVER_LEAK = true;
    public static final boolean RM_ROLE_LOGGER = true;
    public static final boolean RM_SEP = true;
    public static final boolean SM_ADD_LOCK = true;
    public static final boolean SM_BNR = true;
    public static final boolean SM_BUG_CLEAR_DATA = true;
    public static final boolean SM_BUG_FIX = true;
    public static final boolean SM_DESKTOP_MODE = true;
    public static final boolean SM_FIX_MISSING_XML = true;
    public static final boolean SM_HANDLE_EXCEPTION = true;
    public static final boolean SM_PRIVACY_LOG = true;
    public static final boolean SM_PROVIDE_INTENT_TO_DESKTOP_LAUNCHER = true;
    public static final boolean SM_PRUNING_RESERVE_FILE = true;
    public static final boolean SM_RESCHEDULE_SAVE_USER = true;
    public static final boolean SM_SAVE_ASYNC = true;
    public static final boolean SM_SAVE_USER_PERF = true;
    public static final boolean SM_SAVE_WHEN_SHUTDOWN = true;
    public static final boolean SM_UPSM = true;
    public static final boolean SM_USE_SHORTCUT_THREAD = true;
    public static final boolean THEME_MULTITARGET_OVERLAY = true;
    public static final boolean THEME_RESOURCE_MAPPING = true;
    public static final boolean THEME_SUPPORT = true;
    public static final boolean THEME_WALLPAPER_THEMING = true;
    public static final boolean THEME_WALLPAPER_THEMING_DEBUG;
    public static final boolean UM_BMODE;
    public static final boolean UM_DELETE_APP_WITH_PARENT = true;
    public static final boolean UM_HIDE_USER_NAME = true;
    public static final boolean UM_LOG = true;
    public static final boolean UM_MUM_ICON = true;
    public static final boolean UM_NEW_HANDLER_THREAD = true;
    public static final boolean UM_SEP = true;
    public static final boolean UM_SET_GUEST_RESTRCTION = true;
    public static final boolean UM_SUPPORT_MUM = true;
    public static final boolean UM_USER_SWITCH_SETTING;
    public static final boolean ZIPPED_OVERLAY_SUPPORT = true;
    public static final boolean PM_SPEED_INSTALL = isChinaDevice();
    public static final boolean PM_NAL_GET_APP_LIST = isChinaDevice();

    static {
        PM_LDU = PmUtils.isLduSkuBinary() || "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", "Unknown"));
        isSwaRegionDevice();
        PM_INSTALL_TO_SDCARD = false;
        isIndiaRegionDevice();
        PM_BADGE_ON_MONETIZED_APP_SUPPORTED = false;
        PM_32BIT_APP_RUNNING_IN_ABI64 = !is64bitOnlyDevice();
        PM_WA_PARCELED_LIST = isChinaDevice();
        PM_WA_WORK_COMP_CHANGED = Build.VERSION.DEVICE_INITIAL_SDK_INT >= 34;
        PM_ENABLE_GMS = isChinaDevice() && Build.VERSION.DEVICE_INITIAL_SDK_INT >= 32;
        PM_GRANT_ALL_RUNTIME_PERMISSION_UNPACK = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK");
        UM_BMODE = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportTwoPhoneService", false);
        UM_USER_SWITCH_SETTING = !MultiUserSupportsHelper.IS_TABLET;
        THEME_WALLPAPER_THEMING_DEBUG = SystemProperties.getBoolean("debug.wallpaper.theme.enable", false);
    }

    private static boolean isChinaDevice() {
        return "CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
    }

    private static boolean is64bitOnlyDevice() {
        return Build.SUPPORTED_32_BIT_ABIS.length == 0;
    }

    private static boolean isIndiaRegionDevice() {
        String salesCode = SystemProperties.get("ro.csc.sales_code");
        return "INS".equals(salesCode) || "INU".equals(salesCode) || "SUP".equals(salesCode);
    }

    private static boolean isSwaRegionDevice() {
        ArraySet<String> swaSalesCode = new ArraySet<>(Arrays.asList("INS", "INU", "NPB", "NPL", "SLK", "SLI", "BNG", "BKD"));
        return swaSalesCode.contains(SystemProperties.get("ro.csc.sales_code"));
    }
}
