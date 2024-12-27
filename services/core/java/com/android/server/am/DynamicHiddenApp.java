package com.android.server.am;

import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.bgslotmanager.BGSlotManager;
import com.android.server.bgslotmanager.BgAppPropManager;
import com.android.server.bgslotmanager.CameraKillModeManager;
import com.android.server.bgslotmanager.CustomEFKManager;
import com.android.server.bgslotmanager.MemInfoGetter;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public final class DynamicHiddenApp {
    public static boolean DEBUG;
    public static final boolean IS_HIGH_CAPACITY_RAM;
    public static final boolean LMKD_REENTRY_MODE_ENABLE;
    public static int LMK_CUSTOM_SW_LIMIT;
    public static int LMK_CUSTOM_TM_LIMIT;
    public static final boolean LMK_ENABLE_REENTRY_LMK;
    public static boolean LMK_ENABLE_UPGRADE_CRIADJ;
    public static final boolean LMK_ENABLE_USERSPACE_LMK;
    public static boolean LMK_FREELIMIT_ENABLE;
    public static int LMK_FREELIMIT_VAL;
    public static final boolean LMK_LOW_MEM_KEEP_ENABLE;
    public static int LMK_PSI_CRITICAL_TH;
    public static int LMK_PSI_MEDIUM_TH;
    public static int LMK_UPGRADE_PRESSURE;
    public static boolean LMK_USE_MINFREE_LEVELS;
    public static final int MAX_NEVERKILLEDAPP_NUM;
    public static long lastStartTime;
    public static long lastTime;
    public static final long mTotalMemMb;
    public static int reentryCount;
    public static final HashMap reentryMap;
    public int ActiveLaunchCount;
    public final int ActiveLaunchLimit;
    public ArrayList ActiveLaunchSlot;
    public boolean HomeHubState;
    public ArrayList MlLaunchSlot;
    public boolean isReentryMode;
    public ActivityManagerService mAm;
    public final BGProtectManager mBGProtectManager;
    public final BGSlotManager mBGSlotManager;
    public BgAppPropManager mBgAppPropManager;
    public CameraKillModeManager mCameraKillModeManager;
    public ActivityManagerConstants mConstants;
    public Context mContext;
    public final CustomEFKManager mCustomEFKManager;
    public final MemInfoGetter mInfo;
    public ProcessList mProcessList;
    public static float mLMKScale =
            Float.parseFloat(BgAppPropManager.getSlmkPropertyString("dha_lmk_scale", "-1"));
    public static String mLMKArray =
            BgAppPropManager.getSlmkPropertyString("dha_lmk_array", "none");
    public static boolean PICKED_ADJ_ENABLE =
            BgAppPropManager.getSlmkPropertyBool("enable_picked_adj", "true");
    public static final boolean BORA_POLICY_ENABLE =
            BgAppPropManager.getSlmkPropertyBool("bora_policy_enable", "false");
    public static boolean sHH_AMSExceptionEnable =
            BgAppPropManager.getSlmkPropertyBool("hh_ams_exception", "false");
    public static final Base64.Decoder sPkgDecoder = Base64.getDecoder();
    public static final ArrayList alliedProtectedProcList = new ArrayList();

    public abstract class DhaClassLazyHolder {
        public static final DynamicHiddenApp INSTANCE = new DynamicHiddenApp();
        public static boolean isinitClass = false;
    }

    static {
        int slmkPropertyInt = BgAppPropManager.getSlmkPropertyInt("neverkill_num_lowram", "0");
        int slmkPropertyInt2 = BgAppPropManager.getSlmkPropertyInt("neverkill_num_8G", "1");
        int slmkPropertyInt3 = BgAppPropManager.getSlmkPropertyInt("neverkill_num_12G", "3");
        int slmkPropertyInt4 = BgAppPropManager.getSlmkPropertyInt("neverkill_num_16G", "5");
        long totalMemory = Process.getTotalMemory() / 1048576;
        mTotalMemMb = totalMemory;
        IS_HIGH_CAPACITY_RAM = totalMemory > 10240;
        if (totalMemory > 12288) {
            MAX_NEVERKILLEDAPP_NUM = slmkPropertyInt4;
        } else if (totalMemory > 8192) {
            MAX_NEVERKILLEDAPP_NUM = slmkPropertyInt3;
        } else if (totalMemory > 6144) {
            MAX_NEVERKILLEDAPP_NUM = slmkPropertyInt2;
        } else {
            MAX_NEVERKILLEDAPP_NUM = slmkPropertyInt;
        }
        BgAppPropManager.getSlmkPropertyInt("low", Constants.ERROR_KGTA_INIT_FAILED);
        BgAppPropManager.getSlmkPropertyInt("medium", "850");
        BgAppPropManager.getSlmkPropertyInt("critical", "0");
        BgAppPropManager.getSlmkPropertyBool("debug", "false");
        BgAppPropManager.getSlmkPropertyBool("critical_upgrade", "false");
        LMK_UPGRADE_PRESSURE = BgAppPropManager.getSlmkPropertyInt("upgrade_pressure", "80");
        BgAppPropManager.getSlmkPropertyInt("downgrade_pressure", "100");
        BgAppPropManager.getSlmkPropertyBool("kill_heaviest_task", "true");
        Boolean.parseBoolean(SystemProperties.get("ro.config.low_ram", "false"));
        BgAppPropManager.getSlmkPropertyInt("kill_timeout_ms", "100");
        LMK_USE_MINFREE_LEVELS =
                BgAppPropManager.getSlmkPropertyBool("use_minfree_levels", "false");
        LMK_ENABLE_USERSPACE_LMK = true;
        LMK_ENABLE_REENTRY_LMK = true;
        BgAppPropManager.getSlmkPropertyBool("enable_cmarbinfree_sub", "true");
        LMK_ENABLE_UPGRADE_CRIADJ =
                BgAppPropManager.getSlmkPropertyBool("enable_upgrade_criadj", "false");
        LMK_FREELIMIT_ENABLE = BgAppPropManager.getSlmkPropertyBool("freelimit_enable", "true");
        LMK_LOW_MEM_KEEP_ENABLE = true;
        LMK_FREELIMIT_VAL =
                BgAppPropManager.getSlmkPropertyInt(
                        "freelimit_val", Constants.OTP_BIT_KG_COMPLETED);
        LMK_CUSTOM_SW_LIMIT = BgAppPropManager.getSlmkPropertyInt("custom_sw_limit", "500");
        LMK_CUSTOM_TM_LIMIT = BgAppPropManager.getSlmkPropertyInt("custom_tm_limit", "1000");
        BgAppPropManager.getSlmkPropertyInt("psi_low", "70");
        LMK_PSI_MEDIUM_TH = BgAppPropManager.getSlmkPropertyInt("psi_medium", "70");
        LMK_PSI_CRITICAL_TH = BgAppPropManager.getSlmkPropertyInt("psi_critical", "120");
        LMKD_REENTRY_MODE_ENABLE =
                BgAppPropManager.getSlmkPropertyBool("reentry_mode_enable", "true");
        reentryMap = new HashMap();
        reentryCount = 0;
        lastTime = 0L;
        lastStartTime = 0L;
    }

    public DynamicHiddenApp() {
        CustomEFKManager customEFKManager = new CustomEFKManager();
        customEFKManager.addBonusEFK = BgAppPropManager.getSlmkPropertyInt("add_bonusEFK", "-1");
        customEFKManager.v_BonusEFK = BgAppPropManager.getSlmkPropertyInt("v_bonusEFK", "0");
        customEFKManager.origin_EFK = -1;
        customEFKManager.v_bonusEFKWhileBoot =
                BgAppPropManager.getSlmkPropertyInt("v_BootEFK", "204800");
        customEFKManager.vDecreaseEFK = BgAppPropManager.getSlmkPropertyInt("v_decrease_EFK", "0");
        int slmkPropertyInt = BgAppPropManager.getSlmkPropertyInt("tm_decrease_EFK", "1000");
        customEFKManager.vDecreaseEFKTime = slmkPropertyInt;
        customEFKManager.v_watermark_scale = 0;
        customEFKManager.vWatermarkScaleGetPerTickTime = slmkPropertyInt;
        customEFKManager.vWatermarkScaleGetTime = slmkPropertyInt * 5;
        customEFKManager.last_Watermark_EFK = -1;
        customEFKManager.isBlockDecEFK = false;
        customEFKManager.isStillDecEFK = false;
        this.mCustomEFKManager = customEFKManager;
        MemInfoGetter memInfoGetter = new MemInfoGetter();
        this.mInfo = memInfoGetter;
        this.mBGSlotManager = new BGSlotManager(memInfoGetter);
        BGProtectManager bGProtectManager = new BGProtectManager();
        SystemProperties.get("ro.board.platform", "");
        bGProtectManager.removeContactExceptList =
                BgAppPropManager.getSlmkPropertyBool("remove_contact_except_list", "false");
        bGProtectManager.mDhaKeepEmptyEnable =
                BgAppPropManager.getSlmkPropertyInt("dha_pallowlist_enable", "1");
        bGProtectManager.mDhaKeepEmptyEnableKnox =
                BgAppPropManager.getSlmkPropertyInt("dha_knox_plist_enable", "0");
        bGProtectManager.AMSExceptionProviderUpgradeAdjEnable =
                BgAppPropManager.getSlmkPropertyBool("provider_upgrade_adj", "false");
        bGProtectManager.mKnoxAMSExceptionEnable =
                BgAppPropManager.getSlmkPropertyBool("ams_knoxexpt_enable", "true");
        bGProtectManager.DIALER_EXCEPTION_TH =
                BgAppPropManager.getSlmkPropertyInt("dha_dialer_except_th", "3072");
        bGProtectManager.CLEANUP_WEBVIEW_ENABLE =
                BgAppPropManager.getSlmkPropertyBool("cleanup_webview_enable", "false");
        bGProtectManager.PICKED_ADJ_TIME_LIMIT =
                BgAppPropManager.getSlmkPropertyInt("picked_adj_tm", "1800000");
        bGProtectManager.PICKED_ADJ_EXCEPT = new ArrayList();
        bGProtectManager.NEVERKILL_SQETOOL_ENABLE =
                BgAppPropManager.getSlmkPropertyBool("neverkill_sqetool_enable", "true");
        bGProtectManager.BOOTING_EMPTY_KILL_SKIP_ENABLE =
                BgAppPropManager.getSlmkPropertyBool("beks_enable", "false");
        bGProtectManager.recentActivityProcessLimit =
                BgAppPropManager.getSlmkPropertyInt("bora_cached_num", "3");
        bGProtectManager.recentActivityProcessList = new ArrayList();
        bGProtectManager.NapProcessSlotDefault = null;
        bGProtectManager.NapProcessSlotLimit = 1;
        this.mBGProtectManager = bGProtectManager;
        this.ActiveLaunchCount = 0;
        this.ActiveLaunchLimit = 1;
        this.ActiveLaunchSlot = new ArrayList();
        this.MlLaunchSlot = new ArrayList();
        this.HomeHubState = false;
        this.isReentryMode = false;
    }

    public static String decodeToStr(String str) {
        Base64.Decoder decoder = sPkgDecoder;
        if (decoder != null) {
            try {
                return new String(decoder.decode(str));
            } catch (IllegalArgumentException unused) {
            }
        }
        return "";
    }

    public static boolean setKpmParams(String str, String str2) {
        if ("ro.slmk.kpm_debug_trigger".equals(str)) {
            KillPolicyManager.sWarmUpTrigger = Integer.parseInt(str2);
            return true;
        }
        if ("ro.slmk.kpm_policy_trigger".equals(str)) {
            KillPolicyManager.sPolicyTrigger = Integer.parseInt(str2);
            return true;
        }
        if ("ro.slmk.kpm_debug_cycles".equals(str)) {
            KillPolicyManager.sWarmUpCycles = Integer.parseInt(str2);
            return true;
        }
        if (!"ro.slmk.kpm_boot_enable".equals(str)) {
            return false;
        }
        KillPolicyManager.KPM_BTIME_ENABLE = Boolean.parseBoolean(str2);
        return true;
    }

    public static void setLmkdCameraKillBoost(int i, int i2, int i3) {
        int i4 = ProcessList.PAGE_SIZE;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        ByteBuffer allocate = ByteBuffer.allocate(16);
        allocate.putInt(103);
        allocate.putInt(i);
        allocate.putInt(i2);
        allocate.putInt(i3);
        ProcessList.writeLmkd(allocate, null);
        long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
        if (elapsedRealtime2 > 250) {
            Slog.w("ActivityManager", "SLOW setLmkdCameraKillBoost: " + elapsedRealtime2);
        }
    }

    public final void dumpLMKDParameter(PrintWriter printWriter) {
        BgAppPropManager bgAppPropManager = this.mBgAppPropManager;
        if (bgAppPropManager != null) {
            bgAppPropManager.getClass();
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("  DHA_CACHE_MIN: "),
                                            BGSlotManager.MIN_CACHED_APPS,
                                            printWriter,
                                            "  DHA_CACHE_MAX: "),
                                    BGSlotManager.MAX_CACHED_APPS,
                                    printWriter,
                                    "  DHA_EMPTY_MIN: "),
                            BGSlotManager.MIN_EMPTY_APPS,
                            printWriter,
                            "  DHA_EMPTY_MAX: "),
                    BGSlotManager.MAX_EMPTY_APPS,
                    printWriter);
            DynamicHiddenApp dynamicHiddenApp = bgAppPropManager.mDynamicHiddenApp;
            if (dynamicHiddenApp.mConstants != null) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("  AMC_CUR_MAX_CACHED: "),
                                dynamicHiddenApp.mConstants.CUR_MAX_CACHED_PROCESSES,
                                printWriter,
                                "  AMC_CUR_MAX_EMPTY: "),
                        dynamicHiddenApp.mConstants.CUR_MAX_EMPTY_PROCESSES,
                        printWriter);
            }
            printWriter.println();
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m(
                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                            .m(
                                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                            .m(
                                                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                                            .m(
                                                                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                                                                            .m(
                                                                                                                                                    new StringBuilder(
                                                                                                                                                            "  LMKD_enable_userspace_lmk"
                                                                                                                                                                + " "),
                                                                                                                                                    LMK_ENABLE_USERSPACE_LMK,
                                                                                                                                                    printWriter,
                                                                                                                                                    "  LMKD_use_minfree_levels"
                                                                                                                                                        + " "),
                                                                                                                                    LMK_USE_MINFREE_LEVELS,
                                                                                                                                    printWriter,
                                                                                                                                    "  LMKD_enable_upgrade_criadj"
                                                                                                                                        + " "),
                                                                                                                    LMK_ENABLE_UPGRADE_CRIADJ,
                                                                                                                    printWriter,
                                                                                                                    "  LMKD_freelimit_enable"
                                                                                                                        + " "),
                                                                                                    LMK_FREELIMIT_ENABLE,
                                                                                                    printWriter,
                                                                                                    "  LMKD_freelimit_val"
                                                                                                        + " "),
                                                                                    LMK_FREELIMIT_VAL,
                                                                                    printWriter,
                                                                                    "  LMKD_upgrade_pressure"
                                                                                        + " "),
                                                                    LMK_UPGRADE_PRESSURE,
                                                                    printWriter,
                                                                    "  LMKD_custom_sw_limit "),
                                                    LMK_CUSTOM_SW_LIMIT,
                                                    printWriter,
                                                    "  LMKD_custom_tm_limit "),
                                            LMK_CUSTOM_TM_LIMIT,
                                            printWriter,
                                            "  LMKD_psi_medium_th "),
                                    LMK_PSI_MEDIUM_TH,
                                    printWriter,
                                    "  LMKD_psi_critical_th "),
                            LMK_PSI_CRITICAL_TH,
                            printWriter,
                            "  LMKD_use_lowmem_keep_except "),
                    LMK_LOW_MEM_KEEP_ENABLE,
                    printWriter);
            ActivityManagerService activityManagerService = dynamicHiddenApp.mAm;
            if (activityManagerService != null) {
                CachedAppOptimizer cachedAppOptimizer =
                        activityManagerService.mOomAdjuster.mCachedAppOptimizer;
                printWriter.println("  APPCOMPACTOR_ENABLE false");
            }
            printWriter.println();
        }
    }

    public final void initDynamicHiddenApp(
            ActivityManagerService activityManagerService,
            ProcessList processList,
            ActivityManagerConstants activityManagerConstants) {
        if (DhaClassLazyHolder.isinitClass) {
            return;
        }
        this.mAm = activityManagerService;
        this.mProcessList = processList;
        if (activityManagerService != null) {
            this.mContext = activityManagerService.mContext;
        }
        this.mConstants = activityManagerConstants;
        ProcessList processList2 = this.mProcessList;
        BgAppPropManager bgAppPropManager = new BgAppPropManager();
        bgAppPropManager.mProcessList = processList2;
        bgAppPropManager.mDynamicHiddenApp = this;
        this.mBgAppPropManager = bgAppPropManager;
        BGSlotManager bGSlotManager = this.mBGSlotManager;
        bGSlotManager.mDynamicHiddenApp = this;
        boolean z = BGSlotManager.IS_CHINA_MODEL;
        if (z) {
            BGSlotManager.MAX_CACHED_APPS = 512 - bGSlotManager.CHN_REDUCE_CACHED;
            BGProtectManager.dha_keepempty_key = BGProtectManager.dha_keepempty_chn_key;
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "is china model : ", "DynamicHiddenApp_BGSlotManager", z);
        }
        if (mTotalMemMb > 6144) {
            int i = BGSlotManager.MAX_EMPTY_APPS;
            int i2 = BGSlotManager.STATIC_MAX_EMPTY_FOR_OVER_8GB;
            if (i < i2) {
                BGSlotManager.MAX_EMPTY_APPS = i2;
            }
        }
        bGSlotManager.originCachedMax = BGSlotManager.MAX_CACHED_APPS;
        bGSlotManager.originCachedMin = BGSlotManager.MIN_CACHED_APPS;
        bGSlotManager.originEmptyMax = BGSlotManager.MAX_EMPTY_APPS;
        bGSlotManager.originEmptyMin = BGSlotManager.MIN_EMPTY_APPS;
        int slmkPropertyInt = BgAppPropManager.getSlmkPropertyInt("cur_trim_cached_num", "0");
        int slmkPropertyInt2 = BgAppPropManager.getSlmkPropertyInt("cur_trim_empty_num", "0");
        ActivityManagerConstants activityManagerConstants2 = this.mConstants;
        if (activityManagerConstants2 != null) {
            if (slmkPropertyInt != 0) {
                activityManagerConstants2.CUSTOM_CUR_TRIM_CACHED_PROCESSES = slmkPropertyInt;
            }
            if (slmkPropertyInt2 != 0) {
                activityManagerConstants2.CUSTOM_CUR_TRIM_EMPTY_PROCESSES = slmkPropertyInt2;
            }
        }
        Context context = this.mContext;
        BGProtectManager bGProtectManager = this.mBGProtectManager;
        bGProtectManager.mContext = context;
        bGProtectManager.addAllowlistList(true);
        if (bGProtectManager.BOOTING_EMPTY_KILL_SKIP_ENABLE) {
            BGProtectManager.addBEKSList(true);
        }
        bGProtectManager.NapProcessSlotLimit = 1;
        ProcessList processList3 = this.mProcessList;
        processList3.updateOomLevels(processList3.mDisplayWidth, processList3.mDisplayHeight, true);
        DhaClassLazyHolder.isinitClass = true;
    }

    public final void resetKillExceptFlag(ProcessRecord processRecord) {
        HashMap hashMap;
        BGProtectManager bGProtectManager = this.mBGProtectManager;
        bGProtectManager.getClass();
        String str = processRecord.processName;
        int isDhaKeepEmptyProcess = BGProtectManager.isDhaKeepEmptyProcess(str);
        if (isDhaKeepEmptyProcess != -1) {
            int i = bGProtectManager.mDhaKeepEmptyEnable;
            if (i == 1 && isDhaKeepEmptyProcess == 1) {
                processRecord.dhaKeepEmptyFlag = 1;
                return;
            }
            if (bGProtectManager.mDhaKeepEmptyEnableKnox == 1
                    && isDhaKeepEmptyProcess == 2
                    && SemPersonaManager.isKnoxId(processRecord.userId)) {
                processRecord.dhaKeepEmptyFlag = 2;
                return;
            }
            if (i == 1 && isDhaKeepEmptyProcess == 3) {
                processRecord.dhaKeepEmptyFlag = 3;
                return;
            } else {
                if (i == 1 && isDhaKeepEmptyProcess == 4) {
                    processRecord.dhaKeepEmptyFlag = 4;
                    return;
                }
                return;
            }
        }
        HashMap hashMap2 = BGProtectManager.dha_amsexcept_map;
        int intValue =
                (hashMap2 == null || !hashMap2.containsKey(str))
                        ? -1
                        : ((Integer) hashMap2.get(str)).intValue();
        if (intValue != -1) {
            processRecord.isAMSException = true;
            processRecord.AMSExceptionFlag = intValue;
            return;
        }
        int isWebviewProcess = BGProtectManager.isWebviewProcess(processRecord);
        if (isWebviewProcess == -1) {
            if (BGProtectManager.mCameraGuardEnable
                    && (hashMap = BGProtectManager.dha_cameraguard_map) != null
                    && hashMap.containsKey(str)) {
                processRecord.AMSExceptionFlag = BGProtectManager.exceptFlag.CAMERAGUARD.getValue();
                return;
            }
            processRecord.dhaKeepEmptyFlag = 0;
            processRecord.isAMSException = false;
            processRecord.AMSExceptionFlag = -1;
            return;
        }
        BGProtectManager.exceptFlag exceptflag = BGProtectManager.exceptFlag.SANDBOX;
        if (isWebviewProcess == 2) {
            processRecord.isAMSException = true;
            processRecord.AMSExceptionFlag = exceptflag.getValue();
        } else if (isWebviewProcess == 4) {
            processRecord.AMSExceptionFlag = exceptflag.getValue();
        } else if (isWebviewProcess == 6) {
            processRecord.AMSExceptionFlag = BGProtectManager.exceptFlag.BROWSERMAIN.getValue();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01f6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int setCustomADJAndGetProcState(com.android.server.am.ProcessRecord r14) {
        /*
            Method dump skipped, instructions count: 525
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.DynamicHiddenApp.setCustomADJAndGetProcState(com.android.server.am.ProcessRecord):int");
    }
}
