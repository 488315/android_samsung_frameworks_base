package com.android.server.am;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import com.android.server.bgslotmanager.BgAppPropManager;
import com.android.server.bgslotmanager.MemInfoGetter;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class BGProtectManager {
    public static final String[] BOOTING_EMPTY_KILL_SKIP_ARRAY;
    public static final String[] CAMERA_GUARD_ARRAY;
    public static final String[][] DHA_NEVERKILLEXCEPT_ARRAY;
    public static final String[][] DHA_NEVERKILLEXCEPT_ARRAY_BY_KEY;
    public static final String[][] DHA_STATICEXCEPT_PROC_ARRAY;
    public static final String[] LMKD_CAM_CLIENT_EXCEPT_ARRAY;
    public static final String[] PROVIDER_LIFEGUARD_ARRAY;
    public static boolean addProtect;
    public static ArrayList dha_MLexcept_map;
    public static HashMap dha_amsexcept_map;
    public static HashMap dha_cameraguard_map;
    public static int dha_keep_onlyact_key;
    public static int dha_keepchimera_key;
    public static int dha_keepempty_chn_key;
    public static int dha_keepempty_key;
    public static int dha_keepempty_key_knox;
    public static HashMap dha_keepempty_map;
    public static int dha_neverkillexcept_key;
    public static HashMap dha_neverkillexcept_map;
    public static String forceKillHeavyProcess1;
    public static String forceKillHeavyProcess2;
    public static String forceKillHeavyProcess3;
    public static ArrayList forceKillHeavyProcessList;
    public static ArrayList sBEKS_processList;
    public static int sProvider_lifeguard_key;
    public static int sProvider_lifeguard_memory_TH;
    public Context mContext;
    public static final long mTotalMemMb = MemInfoGetter.getTotalMemoryMB();
    public static boolean mAMSExceptionEnable = BgAppPropManager.getSlmkPropertyBool("ams_exception_enable", "true");
    public static int WEBVIEW_ADJ_THRESHOLD = BgAppPropManager.getSlmkPropertyInt("webview_adj_th", Integer.toString(920));
    public static boolean mCameraGuardEnable = BgAppPropManager.getSlmkPropertyBool("camera_guard_enable", "true");
    public static int beks_package_key_bit = BgAppPropManager.getSlmkPropertyInt("beks_key", "31");
    public static boolean allowListCleared = false;
    public static final String[] DHA_DYNAMICEXCEPT_PROC_ARRAY = {DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5wcm9jZXNzLmdhcHBz"), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmdhbGxlcnkzZA=="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="), DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdt"), DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmFwcHMubWFwcw=="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5hcHAubm90ZXM="), DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQudmVuZGluZw=="), DynamicHiddenApp.decodeToStr("UmVzZXJ2ZWQ="), DynamicHiddenApp.decodeToStr("SU5DQUxMVUk="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE="), DynamicHiddenApp.decodeToStr("TU1T"), DynamicHiddenApp.decodeToStr("Y29tLmJhaWR1LkJhaWR1TWFw"), DynamicHiddenApp.decodeToStr("UmVzZXJ2ZWQ=")};
    public String mPlatform = BgAppPropManager.getSystemPropertyString("ro.board.platform", "");
    public boolean removeContactExceptList = BgAppPropManager.getSlmkPropertyBool("remove_contact_except_list", "false");
    public int mDhaKeepEmptyEnable = BgAppPropManager.getSlmkPropertyInt("dha_pallowlist_enable", "1");
    public int mDhaKeepEmptyEnableKnox = BgAppPropManager.getSlmkPropertyInt("dha_knox_plist_enable", "0");
    public boolean AMSExceptionProviderUpgradeAdjEnable = BgAppPropManager.getSlmkPropertyBool("provider_upgrade_adj", "false");
    public int forceKillHeavyProcessLimit = Math.min(BgAppPropManager.getSlmkPropertyInt("dha_cached_min", "4"), 4);
    public boolean doKillRestrict = false;
    public boolean mKnoxAMSExceptionEnable = BgAppPropManager.getSlmkPropertyBool("ams_knoxexpt_enable", "true");
    public int DIALER_EXCEPTION_TH = BgAppPropManager.getSlmkPropertyInt("dha_dialer_except_th", "3072");
    public boolean CLEANUP_WEBVIEW_ENABLE = BgAppPropManager.getSlmkPropertyBool("cleanup_webview_enable", "false");
    public int PICKED_ADJ_TIME_LIMIT = BgAppPropManager.getSlmkPropertyInt("picked_adj_tm", "1800000");
    public ArrayList PICKED_ADJ_EXCEPT = new ArrayList();
    public boolean NEVERKILL_SQETOOL_ENABLE = BgAppPropManager.getSlmkPropertyBool("neverkill_sqetool_enable", "true");
    public boolean BOOTING_EMPTY_KILL_SKIP_ENABLE = BgAppPropManager.getSlmkPropertyBool("beks_enable", "false");
    public int recentActivityProcessLimit = BgAppPropManager.getSlmkPropertyInt("bora_cached_num", "3");
    public ArrayList recentActivityProcessList = new ArrayList();
    public ProcessRecord NapProcessSlotDefault = null;
    public int NapProcessSlotLimit = 1;

    static {
        String decodeToStr = DynamicHiddenApp.decodeToStr("YW5kcm9pZC5wcm9jZXNzLmFjb3Jl");
        exceptFlag exceptflag = exceptFlag.NORMALANDKNOX;
        String[] strArr = {decodeToStr, exceptflag.getString()};
        String decodeToStr2 = DynamicHiddenApp.decodeToStr("Q09OVEFDVFM=");
        exceptFlag exceptflag2 = exceptFlag.NORMALANDKNOXPWHL;
        DHA_STATICEXCEPT_PROC_ARRAY = new String[][]{strArr, new String[]{decodeToStr2, exceptflag2.getString()}, new String[]{DynamicHiddenApp.decodeToStr("RElBTEVS"), exceptflag2.getString()}, new String[]{DynamicHiddenApp.decodeToStr("SE9NRUhVQg=="), exceptflag2.getString()}, new String[]{DynamicHiddenApp.decodeToStr("YW5kcm9pZC5wcm9jZXNzLm1lZGlh"), exceptFlag.CAMERAMEDIA.getString()}, new String[]{DynamicHiddenApp.decodeToStr("Q01I"), exceptflag.getString()}, new String[]{DynamicHiddenApp.decodeToStr("QklYQlk="), exceptFlag.HOMEHUB.getString()}};
        PROVIDER_LIFEGUARD_ARRAY = new String[]{DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5wcm9jZXNzLmdhcHBz"), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5tb2JpbGVzZXJ2aWNl"), DynamicHiddenApp.decodeToStr("Y29tLm9zcC5hcHAuc2lnbmlu"), DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5wcm9jZXNzLmdzZXJ2aWNlcw=="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLnByb3ZpZGVyLmJhZGdl"), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuaXBzZXJ2aWNl"), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5zb3VuZGFsaXZl"), DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdvb2dsZXF1aWNrc2VhcmNoYm94OnNlYXJjaA=="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcjpwcml2aWxlZ2VkX3Byb2Nlc3Mw"), DynamicHiddenApp.decodeToStr("Y29tLnZlcml6b24ubWVzc2FnaW5nLnZ6bXNncw=="), DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQucHJvdmlkZXJzLmNhbGVuZGFy"), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5zY2xvdWQ="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5zYW1zdW5ncGFzcw=="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5hcHAucmVtaW5kZXI="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5hcHAucm91dGluZXM="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy51bmlmaWVkd2Zj"), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5nbG9iYWxwb3N0cHJvY21ncg==")};
        DHA_NEVERKILLEXCEPT_ARRAY = new String[][]{new String[]{DynamicHiddenApp.decodeToStr("Y29tLnNhbGFiLmFjdA=="), "2", DynamicHiddenApp.decodeToStr("Y29tLnNhbGFiLmFjdA=="), "plat"}, new String[]{DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC50aW55bQ=="), "2", DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC50aW55bQ=="), "plat"}, new String[]{DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hZWNtb25pdG9y"), "2", DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hZWNtb25pdG9y"), "plat"}, new String[]{DynamicHiddenApp.decodeToStr("RkFDVE9SWQ=="), "2", "", ""}};
        DHA_NEVERKILLEXCEPT_ARRAY_BY_KEY = new String[][]{new String[]{DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdtcw=="), "1", DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdtcw=="), "priv"}, new String[]{DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdtcy5wZXJzaXN0ZW50"), "1", DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdtcw=="), "priv"}};
        BOOTING_EMPTY_KILL_SKIP_ARRAY = new String[]{DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5teWZpbGVz"), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="), DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQuc2V0dGluZ3M="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmdhbGxlcnkzZA=="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5kaWFsZXI="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5tZXNzYWdpbmc="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5hcHAubm90ZXM="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5jbG9ja3BhY2thZ2U="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5jYWxlbmRhcg=="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC52b2ljZW5vdGU=")};
        CAMERA_GUARD_ARRAY = new String[]{DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmdhbGxlcnkzZA==")};
        LMKD_CAM_CLIENT_EXCEPT_ARRAY = new String[]{DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYWRhcHRpdmVicmlnaHRuZXNzZ28="), DynamicHiddenApp.decodeToStr("YW5kcm9pZC5zeXN0ZW0="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5zbWFydGZhY2U="), DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5iaW8uZmFjZS5zZXJ2aWNl")};
        forceKillHeavyProcess1 = DynamicHiddenApp.decodeToStr("Y29tLmtpbG9vLnN1YndheXN1cmY=");
        forceKillHeavyProcess2 = DynamicHiddenApp.decodeToStr("Y29tLmF2YXN0LmFuZHJvaWQubW9iaWxlc2VjdXJpdHk=");
        forceKillHeavyProcess3 = DynamicHiddenApp.decodeToStr("Y29tLnNreXBlLnJhaWRlcg==");
        forceKillHeavyProcessList = new ArrayList(Arrays.asList(forceKillHeavyProcess1, forceKillHeavyProcess2, forceKillHeavyProcess3));
        sBEKS_processList = new ArrayList();
        dha_keepempty_map = new HashMap();
        dha_amsexcept_map = new HashMap();
        dha_MLexcept_map = new ArrayList();
        dha_neverkillexcept_map = new HashMap();
        dha_cameraguard_map = new HashMap();
        dha_keepempty_key = BgAppPropManager.getSlmkPropertyInt("dha_pwhl_key", "512");
        dha_keepempty_key_knox = BgAppPropManager.getSlmkPropertyInt("dha_pwhl_key_knox", "1539");
        dha_keepempty_chn_key = BgAppPropManager.getSlmkPropertyInt("dha_pwhl_chn_key", Integer.toString(dha_keepempty_key));
        dha_keepchimera_key = BgAppPropManager.getSlmkPropertyInt("dha_chimerawhl_key", "0");
        dha_keep_onlyact_key = BgAppPropManager.getSlmkPropertyInt("dha_onlyact_key", "0");
        dha_neverkillexcept_key = BgAppPropManager.getSlmkPropertyInt("dha_neverkill_key", "0");
        addProtect = BgAppPropManager.getSlmkPropertyBool("add_protect", "false");
        sProvider_lifeguard_memory_TH = BgAppPropManager.getSlmkPropertyInt("plg_memory_th", "4096");
        sProvider_lifeguard_key = BgAppPropManager.getSlmkPropertyInt("plg_key", "3");
    }

    public void initBGProtectManager(Context context) {
        this.mContext = context;
        addAllowlistList(true);
        if (this.BOOTING_EMPTY_KILL_SKIP_ENABLE) {
            addBEKSList(true);
        }
        this.NapProcessSlotLimit = 1;
    }

    public void initBGProtectManagerPostBoot() {
        addAllowlistList(false);
        if (this.BOOTING_EMPTY_KILL_SKIP_ENABLE) {
            addBEKSList(false);
        }
        if ("on".equals(BgAppPropManager.getSystemPropertyString("persist.sys.bub_onoff", "on"))) {
            removeAllowlistByBUB();
        }
    }

    public void addAllowlistList(boolean z) {
        if (!z) {
            dha_amsexcept_map.clear();
            dha_keepempty_map.clear();
            dha_neverkillexcept_map.clear();
            dha_cameraguard_map.clear();
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            String[][] strArr = DHA_STATICEXCEPT_PROC_ARRAY;
            if (i2 >= strArr.length) {
                break;
            }
            HashMap hashMap = dha_amsexcept_map;
            String[] strArr2 = strArr[i2];
            dhaAddPackageName(hashMap, strArr2[0], Integer.parseInt(strArr2[1]), z);
            i2++;
        }
        if (mTotalMemMb > sProvider_lifeguard_memory_TH) {
            int i3 = 0;
            int i4 = 1;
            while (true) {
                String[] strArr3 = PROVIDER_LIFEGUARD_ARRAY;
                if (i3 >= strArr3.length) {
                    break;
                }
                if ((sProvider_lifeguard_key & i4) != 0) {
                    dhaAddPackageName(dha_amsexcept_map, strArr3[i3], 1, z);
                }
                i4 <<= 1;
                i3++;
            }
        }
        if (this.NEVERKILL_SQETOOL_ENABLE) {
            int i5 = 0;
            while (true) {
                String[][] strArr4 = DHA_NEVERKILLEXCEPT_ARRAY;
                if (i5 >= strArr4.length) {
                    break;
                }
                HashMap hashMap2 = dha_neverkillexcept_map;
                String[] strArr5 = strArr4[i5];
                String str = strArr5[0];
                int parseInt = Integer.parseInt(strArr5[1]);
                String[] strArr6 = strArr4[i5];
                dhaAddNeverKilledPackageName(hashMap2, str, parseInt, strArr6[2], strArr6[3]);
                i5++;
            }
        }
        int i6 = 0;
        int i7 = 1;
        while (true) {
            String[][] strArr7 = DHA_NEVERKILLEXCEPT_ARRAY_BY_KEY;
            if (i6 >= strArr7.length) {
                break;
            }
            if ((dha_neverkillexcept_key & i7) != 0) {
                HashMap hashMap3 = dha_neverkillexcept_map;
                String[] strArr8 = strArr7[i6];
                String str2 = strArr8[0];
                int parseInt2 = Integer.parseInt(strArr8[1]);
                String[] strArr9 = strArr7[i6];
                dhaAddNeverKilledPackageName(hashMap3, str2, parseInt2, strArr9[2], strArr9[3]);
            }
            i7 <<= 1;
            i6++;
        }
        int i8 = 0;
        int i9 = 1;
        while (true) {
            String[] strArr10 = DHA_DYNAMICEXCEPT_PROC_ARRAY;
            if (i8 >= strArr10.length) {
                break;
            }
            if (this.mDhaKeepEmptyEnable == 1 && (dha_keepchimera_key & i9) != 0) {
                dhaAddPackageName(dha_keepempty_map, strArr10[i8], 3, z);
            }
            if (this.mDhaKeepEmptyEnableKnox == 1 && (dha_keepempty_key_knox & i9) != 0) {
                dhaAddPackageName(dha_keepempty_map, strArr10[i8], 2, z);
            }
            if (this.mDhaKeepEmptyEnable == 1 && (dha_keepempty_key & i9) != 0) {
                dhaAddPackageName(dha_keepempty_map, strArr10[i8], 1, z);
            }
            if (this.mDhaKeepEmptyEnable == 1 && (dha_keep_onlyact_key & i9) != 0) {
                dhaAddPackageName(dha_keepempty_map, strArr10[i8], 4, z);
            }
            i9 <<= 1;
            i8++;
        }
        while (mCameraGuardEnable) {
            String[] strArr11 = CAMERA_GUARD_ARRAY;
            if (i >= strArr11.length) {
                return;
            }
            dhaAddPackageName(dha_cameraguard_map, strArr11[i], 1, z);
            i++;
        }
    }

    public void addBEKSList(boolean z) {
        if (!z) {
            sBEKS_processList.clear();
        }
        int i = 1;
        int i2 = 0;
        while (true) {
            String[] strArr = BOOTING_EMPTY_KILL_SKIP_ARRAY;
            if (i2 >= strArr.length) {
                return;
            }
            if ((beks_package_key_bit & i) != 0) {
                sBEKS_processList.add(strArr[i2]);
            }
            i <<= 1;
            i2++;
        }
    }

    public final void removeProviderLifeguardProcs() {
        if (mTotalMemMb <= sProvider_lifeguard_memory_TH) {
            return;
        }
        int i = 1;
        int i2 = 0;
        while (true) {
            String[] strArr = PROVIDER_LIFEGUARD_ARRAY;
            if (i2 >= strArr.length) {
                return;
            }
            if ((sProvider_lifeguard_key & i) != 0) {
                dhaDeletePackageName(dha_amsexcept_map, strArr[i2], false);
            }
            i <<= 1;
            i2++;
        }
    }

    public void removeAllowlistByBUB() {
        dhaDeletePackageName(dha_amsexcept_map, DynamicHiddenApp.decodeToStr("Q09OVEFDVFM="), false);
        dhaDeletePackageName(dha_amsexcept_map, DynamicHiddenApp.decodeToStr("RElBTEVS"), false);
        removeProviderLifeguardProcs();
        dha_keepempty_map.clear();
        dha_cameraguard_map.clear();
    }

    public void updatePickedProcessLists(List list) {
        dha_MLexcept_map.clear();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                dha_MLexcept_map.add((String) it.next());
            }
        }
    }

    public void updateNapProcessProtection(ProcessRecord processRecord) {
        switch (processRecord.mState.getCurProcState()) {
            case 16:
            case 17:
            case 18:
                break;
            default:
                long elapsedRealtime = SystemClock.elapsedRealtime() - processRecord.getMlLaunchTime();
                ProcessRecord processRecord2 = this.NapProcessSlotDefault;
                if (processRecord2 != null) {
                    if (processRecord2.getIpmLaunchtype() == 0 && processRecord.getMlLaunchTime() > this.NapProcessSlotDefault.getMlLaunchTime()) {
                        this.NapProcessSlotDefault.setMlLaunchTime(-1L);
                        this.NapProcessSlotDefault.setIpmLaunchType(-1);
                        if (this.NapProcessSlotDefault.mState.getCurProcState() >= 19 && this.NapProcessSlotDefault.mState.getCurRawAdj() > 860) {
                            this.NapProcessSlotDefault.killLocked("ML_Kill: over " + this.NapProcessSlotLimit + " slots kill a", 13, true);
                        }
                    }
                    this.NapProcessSlotDefault = processRecord;
                } else {
                    Log.e("DynamicHiddenApp_BGProtectManager", "app launch time is " + processRecord.getMlLaunchTime() + " tmp time is ");
                    this.NapProcessSlotDefault = processRecord;
                }
                if (processRecord.getIpmLaunchtype() == 0 && processRecord.getMlLaunchTime() != -1 && elapsedRealtime > 1800000 && processRecord.mState.getCurProcState() >= 19 && processRecord.mState.getCurRawAdj() > 860) {
                    processRecord.killLocked("ML_Kill: timeout " + elapsedRealtime + " slots kill a", 13, true);
                    this.NapProcessSlotDefault = null;
                    break;
                }
                break;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x002d, code lost:
    
        if (com.android.server.am.DynamicHiddenApp.sHH_AMSExceptionEnable != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0038, code lost:
    
        if (r8.AMSExceptionFlag == com.android.server.am.BGProtectManager.exceptFlag.CAMERAMEDIA.getValue()) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0069, code lost:
    
        if (r8.AMSExceptionFlag == com.android.server.am.BGProtectManager.exceptFlag.CAMERAMEDIA.getValue()) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int checkKeptProcess(ProcessRecord processRecord) {
        int i;
        boolean z;
        int i2;
        if (mAMSExceptionEnable && processRecord.isAMSException) {
            if (UserHandle.getUserId(processRecord.uid) < 100) {
                if (processRecord.AMSExceptionFlag > exceptFlag.NORMALANDKNOX.getValue()) {
                    if (processRecord.AMSExceptionFlag == exceptFlag.HOMEHUB.getValue()) {
                    }
                }
                i = 1;
                z = false;
            } else {
                if (this.mKnoxAMSExceptionEnable) {
                    if (processRecord.AMSExceptionFlag < exceptFlag.NORMALANDKNOX.getValue() || processRecord.AMSExceptionFlag > exceptFlag.KNOXONLY.getValue()) {
                        if (processRecord.AMSExceptionFlag == exceptFlag.NORMALANDKNOXPWHL.getValue()) {
                            z = true;
                            i = 0;
                        }
                    }
                    i = 1;
                    z = false;
                }
                i = 0;
                z = false;
            }
            if (processRecord.mState.getCurProcState() == 17 && processRecord.AMSExceptionFlag == exceptFlag.SANDBOX.getValue()) {
                i = 1;
            }
        } else {
            i = 0;
            z = false;
        }
        if ((this.mDhaKeepEmptyEnable == 1 || this.mDhaKeepEmptyEnableKnox == 1) && (i2 = processRecord.dhaKeepEmptyFlag) > 0 && i2 < 3) {
            z = true;
        }
        if (i != 1 && z) {
            i = 2;
        }
        if (isOnlyActCheck(processRecord)) {
            i = 2;
        }
        int i3 = !processRecord.isActiveLaunch() ? i : 3;
        if (processRecord.getIpmLaunchtype() == 0) {
            i3 = 4;
        }
        if (processRecord.getIpmLaunchtype() == 1) {
            i3 = 5;
        }
        if (isForKeepingCheck(processRecord)) {
            i3 = 6;
        }
        if (processRecord.isNeverKillException) {
            i3 = 8;
        }
        if (this.doKillRestrict && IsForceKillHeavyProcess(processRecord.processName) && processRecord.mState.getCurAdj() >= 100 && forceKillHeavyProcess2.equals(processRecord.processName)) {
            processRecord.killLocked("bg_restriction", 13, 2, true);
            this.doKillRestrict = false;
        }
        return i3;
    }

    public void resetKillExceptFlag(ProcessRecord processRecord) {
        int isDhaKeepEmptyProcess = isDhaKeepEmptyProcess(processRecord.processName);
        if (isDhaKeepEmptyProcess != -1) {
            if (this.mDhaKeepEmptyEnable == 1 && isDhaKeepEmptyProcess == 1) {
                processRecord.dhaKeepEmptyFlag = 1;
                return;
            }
            if (this.mDhaKeepEmptyEnableKnox == 1 && isDhaKeepEmptyProcess == 2 && SemPersonaManager.isKnoxId(processRecord.userId)) {
                processRecord.dhaKeepEmptyFlag = 2;
                return;
            }
            int i = this.mDhaKeepEmptyEnable;
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
        int isAMSExceptionProcess = isAMSExceptionProcess(processRecord.processName);
        if (isAMSExceptionProcess != -1) {
            processRecord.isAMSException = true;
            processRecord.AMSExceptionFlag = isAMSExceptionProcess;
            return;
        }
        int isWebviewProcess = isWebviewProcess(processRecord);
        if (isWebviewProcess == -1) {
            if (isCameraGuardProcess(processRecord.processName)) {
                processRecord.AMSExceptionFlag = exceptFlag.CAMERAGUARD.getValue();
                return;
            }
            processRecord.dhaKeepEmptyFlag = 0;
            processRecord.isAMSException = false;
            processRecord.AMSExceptionFlag = -1;
            return;
        }
        if (isWebviewProcess == 2) {
            processRecord.isAMSException = true;
            processRecord.AMSExceptionFlag = exceptFlag.SANDBOX.getValue();
        } else if (isWebviewProcess == 4) {
            processRecord.AMSExceptionFlag = exceptFlag.SANDBOX.getValue();
        } else if (isWebviewProcess == 6) {
            processRecord.AMSExceptionFlag = exceptFlag.BROWSERMAIN.getValue();
        }
    }

    public void setLmkdProtectFlag(ProcessRecord processRecord) {
        int isNeverKillExceptionProcess = isNeverKillExceptionProcess(processRecord);
        if (isNeverKillExceptionProcess != -1) {
            processRecord.isNeverKillException = true;
            processRecord.AMSExceptionFlag = isNeverKillExceptionProcess;
            return;
        }
        int isDhaKeepEmptyProcess = isDhaKeepEmptyProcess(processRecord.processName);
        if (isDhaKeepEmptyProcess != -1) {
            if (this.mDhaKeepEmptyEnable == 1 && isDhaKeepEmptyProcess == 1) {
                processRecord.dhaKeepEmptyFlag = 1;
                return;
            }
            if (this.mDhaKeepEmptyEnableKnox == 1 && isDhaKeepEmptyProcess == 2 && SemPersonaManager.isKnoxId(processRecord.userId)) {
                processRecord.dhaKeepEmptyFlag = 2;
                return;
            }
            int i = this.mDhaKeepEmptyEnable;
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
        if (appIsPickedProcess(processRecord.processName, processRecord.userId) != -1) {
            Slog.d("DynamicHiddenApp_BGProtectManager", "[SecIpm] it's a ML_TYPE_EMPTYPROCESS protected process " + processRecord.processName);
            processRecord.setIpmLaunchType(1);
            return;
        }
        int isAMSExceptionProcess = isAMSExceptionProcess(processRecord.processName);
        if (isAMSExceptionProcess != -1) {
            processRecord.isAMSException = true;
            processRecord.AMSExceptionFlag = isAMSExceptionProcess;
            return;
        }
        int isWebviewProcess = isWebviewProcess(processRecord);
        if (isWebviewProcess == -1) {
            if (isCameraGuardProcess(processRecord.processName)) {
                processRecord.AMSExceptionFlag = exceptFlag.CAMERAGUARD.getValue();
            }
        } else if (isWebviewProcess == 2) {
            processRecord.isAMSException = true;
            processRecord.AMSExceptionFlag = exceptFlag.SANDBOX.getValue();
        } else if (isWebviewProcess == 4) {
            processRecord.AMSExceptionFlag = exceptFlag.SANDBOX.getValue();
        } else if (isWebviewProcess == 6) {
            processRecord.AMSExceptionFlag = exceptFlag.BROWSERMAIN.getValue();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0214  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int setCustomADJAndGetProcState(ProcessRecord processRecord) {
        ProcessStateRecord processStateRecord;
        int i;
        if (processRecord == null || (processStateRecord = processRecord.mState) == null) {
            return -1;
        }
        int curProcState = processStateRecord.getCurProcState();
        if (processStateRecord.getCurAdj() != processStateRecord.getSetAdj()) {
            if (processStateRecord.getCurAdj() >= 850 && processStateRecord.getCurAdj() <= 999) {
                if (DynamicHiddenApp.LMK_ENABLE_USERSPACE_LMK && DynamicHiddenApp.LMK_ENABLE_REENTRY_LMK) {
                    if (processRecord.hasActivities() && (processStateRecord.getCurProcState() == 10 || processStateRecord.getCurProcState() == 15)) {
                        curProcState = 16;
                    }
                } else if (IsProtected(processRecord)) {
                    processStateRecord.setCurAdj(900);
                    processStateRecord.setCurRawAdj(processStateRecord.getCurAdj());
                }
                if (DynamicHiddenApp.PICKED_ADJ_ENABLE) {
                    if (processRecord.userId == 0 && IsProtected(processRecord)) {
                        if (IsAllowListCleared()) {
                            processStateRecord.setCurAdj(850);
                            processStateRecord.setCurRawAdj(processStateRecord.getCurAdj());
                        } else if (SystemClock.uptimeMillis() - processStateRecord.getLastStateTime() >= this.PICKED_ADJ_TIME_LIMIT) {
                            if (!isContainPickedExceptList(processRecord.processName) && isDhaKeepEmptyProcess(processRecord.processName) != -1) {
                                processStateRecord.setCurAdj(900);
                                processStateRecord.setCurRawAdj(processStateRecord.getCurAdj());
                                addPickedExceptList(processRecord.processName);
                                Slog.i("DynamicHiddenApp_BGProtectManager", "Picked process timeout " + processRecord.processName + " (pid: " + processRecord.getPid() + ")");
                            }
                        } else {
                            processStateRecord.setCurAdj(850);
                            processStateRecord.setCurRawAdj(processStateRecord.getCurAdj());
                            removePickedExceptList(processRecord.processName);
                        }
                    }
                    if (processRecord.getIpmLaunchtype() == 1) {
                        processStateRecord.setCurAdj(850);
                        processStateRecord.setCurRawAdj(processStateRecord.getCurAdj());
                    }
                }
            }
            int i2 = 92;
            int i3 = 99;
            if (processStateRecord.getCurAdj() >= 250 && processStateRecord.getCurAdj() <= 999) {
                if (processRecord.userId == 0 && processRecord.AMSExceptionFlag == exceptFlag.CAMERAGUARD.getValue()) {
                    curProcState = processStateRecord.getCurProcState() == 16 ? 91 : 90;
                }
                if (IsProtected(processRecord)) {
                    if (this.AMSExceptionProviderUpgradeAdjEnable && ("android.process.acore".equals(processRecord.processName) || "android.process.media".equals(processRecord.processName))) {
                        processStateRecord.setCurAdj(200);
                        processStateRecord.setCurRawAdj(processStateRecord.getCurAdj());
                    }
                    curProcState = processRecord.AMSExceptionFlag == exceptFlag.CAMERAMEDIA.getValue() ? 92 : 99;
                }
            }
            if (processStateRecord.getCurAdj() >= 800 && processStateRecord.getCurAdj() <= 999) {
                if (processRecord.AMSExceptionFlag == exceptFlag.SANDBOX.getValue()) {
                    if (!processRecord.isAMSException) {
                        curProcState = 98;
                    } else if (!this.CLEANUP_WEBVIEW_ENABLE || processStateRecord.getCurAdj() <= WEBVIEW_ADJ_THRESHOLD) {
                        curProcState = 99;
                    }
                }
                if (!processRecord.isActiveLaunch() || processStateRecord.getCurProcState() != 19) {
                    i = (processRecord.getIpmLaunchtype() == 0 && processStateRecord.getCurProcState() == 19) ? 96 : 97;
                    if (processRecord.AMSExceptionFlag == exceptFlag.HOMEHUB.getValue() && DynamicHiddenApp.sHH_AMSExceptionEnable) {
                        curProcState = 99;
                    }
                }
                curProcState = i;
                if (processRecord.AMSExceptionFlag == exceptFlag.HOMEHUB.getValue()) {
                    curProcState = 99;
                }
            }
            if (DynamicHiddenApp.BORA_POLICY_ENABLE) {
                if (processRecord.AMSExceptionFlag != exceptFlag.CAMERAMEDIA.getValue() || processStateRecord.getCurAdj() < 250) {
                    if (processStateRecord.getCurAdj() == 700 && processRecord.hasActivities()) {
                        i2 = 150;
                    } else if (isRecentActivityProcess(processRecord)) {
                        i2 = getIndexOfRecentActivityProcess(processRecord) + 180;
                        if (i2 > 199) {
                            i2 = 199;
                        }
                    }
                }
                if (processStateRecord.getCurAdj() < 200 && processStateRecord.getCurAdj() >= 0 && processRecord.AMSExceptionFlag == exceptFlag.BROWSERMAIN.getValue()) {
                    i2 = 95;
                }
                if (!processRecord.isSDException && processRecord.isNeverKillException) {
                    if (processRecord.userId != 0) {
                        processStateRecord.setCurAdj(-700);
                    } else if (processRecord.AMSExceptionFlag >= exceptFlag.NORMALANDKNOX.getValue()) {
                        processStateRecord.setCurAdj(-700);
                    }
                    curProcState = (processStateRecord.getCurProcState() == 19 || !isBEKCondition(processRecord)) ? i3 : 93;
                    if (CoreRune.ALLIED_PROC_PROTECTION_LMKD) {
                        synchronized (DynamicHiddenApp.alliedProtectedProcList) {
                            if (DynamicHiddenApp.alliedProtectedProcList.contains(processRecord.info.packageName)) {
                                curProcState = 141;
                            }
                        }
                    }
                }
                i3 = i2;
                if (processStateRecord.getCurProcState() == 19) {
                }
                if (CoreRune.ALLIED_PROC_PROTECTION_LMKD) {
                }
            }
            i2 = curProcState;
            if (processStateRecord.getCurAdj() < 200) {
                i2 = 95;
            }
            if (!processRecord.isSDException) {
                if (processRecord.userId != 0) {
                }
                if (processStateRecord.getCurProcState() == 19) {
                }
                if (CoreRune.ALLIED_PROC_PROTECTION_LMKD) {
                }
            }
            i3 = i2;
            if (processStateRecord.getCurProcState() == 19) {
            }
            if (CoreRune.ALLIED_PROC_PROTECTION_LMKD) {
            }
        }
        return curProcState;
    }

    public boolean isContainPickedExceptList(String str) {
        return this.PICKED_ADJ_EXCEPT.contains(str);
    }

    public void addPickedExceptList(String str) {
        this.PICKED_ADJ_EXCEPT.add(str);
    }

    public void removePickedExceptList(String str) {
        if (isContainPickedExceptList(str)) {
            this.PICKED_ADJ_EXCEPT.remove(str);
        }
    }

    public boolean IsForceKillHeavyProcess(String str) {
        if (allowListCleared) {
            return false;
        }
        return forceKillHeavyProcessList.contains(str);
    }

    public boolean IsProtected(ProcessRecord processRecord) {
        int i;
        int i2;
        return DynamicHiddenApp.sHH_AMSExceptionEnable ? (processRecord.isAMSException && processRecord.AMSExceptionFlag != exceptFlag.SANDBOX.getValue()) || ((i2 = processRecord.dhaKeepEmptyFlag) > 0 && i2 < 3) || isOnlyActCheck(processRecord) : !(!processRecord.isAMSException || processRecord.AMSExceptionFlag == exceptFlag.SANDBOX.getValue() || processRecord.AMSExceptionFlag == exceptFlag.HOMEHUB.getValue()) || ((i = processRecord.dhaKeepEmptyFlag) > 0 && i < 3) || isOnlyActCheck(processRecord);
    }

    public boolean isProtectedInChimera(ProcessRecord processRecord) {
        int i;
        int i2;
        boolean z = false;
        if (!DynamicHiddenApp.sHH_AMSExceptionEnable ? !(((i = processRecord.dhaKeepEmptyFlag) <= 0 || i >= 4) && (!processRecord.isAMSException || processRecord.AMSExceptionFlag == exceptFlag.BROWSERMAIN.getValue())) : !(((i2 = processRecord.dhaKeepEmptyFlag) <= 0 || i2 >= 4) && !processRecord.isAMSException)) {
            z = true;
        }
        if (isOnlyActCheck(processRecord)) {
            return true;
        }
        return z;
    }

    public static int isDhaKeepEmptyProcess(String str) {
        HashMap hashMap = dha_keepempty_map;
        if (hashMap == null || !hashMap.containsKey(str)) {
            return -1;
        }
        return ((Integer) dha_keepempty_map.get(str)).intValue();
    }

    public static int isAMSExceptionProcess(String str) {
        HashMap hashMap = dha_amsexcept_map;
        if (hashMap == null || !hashMap.containsKey(str)) {
            return -1;
        }
        return ((Integer) dha_amsexcept_map.get(str)).intValue();
    }

    public static boolean isCameraGuardProcess(String str) {
        HashMap hashMap;
        return mCameraGuardEnable && (hashMap = dha_cameraguard_map) != null && hashMap.containsKey(str);
    }

    public static int isNeverKillExceptionProcess(ProcessRecord processRecord) {
        HashMap hashMap = dha_neverkillexcept_map;
        if (hashMap == null || !hashMap.containsKey(processRecord.processName)) {
            return -1;
        }
        Pair pair = (Pair) dha_neverkillexcept_map.get(processRecord.processName);
        if (((PackageValidationInfo) pair.second).validate(processRecord)) {
            return ((Integer) pair.first).intValue();
        }
        return -1;
    }

    public static int isWebviewProcess(ProcessRecord processRecord) {
        if (processRecord.getHostingRecord() != null && processRecord.getHostingRecord().usesWebviewZygote()) {
            if (processRecord.getHostingRecord().getName() != null) {
                Slog.i("DynamicHiddenApp_BGProtectManager", "check webview name : " + processRecord.processName + "check hostingname webview : " + processRecord.getHostingRecord().getName());
                return 2;
            }
            Slog.i("DynamicHiddenApp_BGProtectManager", "check webview name : " + processRecord.processName + "check hostingname webview : null ");
            return 2;
        }
        if (processRecord.processName.contains(":sandboxed_process")) {
            return 4;
        }
        return KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME.equals(processRecord.processName) ? 6 : -1;
    }

    public static boolean isOnlyActCheck(ProcessRecord processRecord) {
        return processRecord.dhaKeepEmptyFlag == 4 && processRecord.hasActivities();
    }

    public boolean isForKeepingCheck(ProcessRecord processRecord) {
        if (!processRecord.isForKeeping()) {
            return false;
        }
        if (SystemClock.elapsedRealtime() - processRecord.appKeepingTime <= 1000) {
            return true;
        }
        processRecord.setIsforKeeping(false);
        return false;
    }

    public int appIsPickedProcess(String str, int i) {
        String str2 = String.valueOf(i) + "_&_" + str;
        ArrayList arrayList = dha_MLexcept_map;
        if (arrayList == null || !arrayList.contains(str2)) {
            return -1;
        }
        return dha_MLexcept_map.indexOf(str2);
    }

    public boolean isBEKCondition(ProcessRecord processRecord) {
        return this.BOOTING_EMPTY_KILL_SKIP_ENABLE && sBEKS_processList.contains(processRecord.processName) && SystemClock.uptimeMillis() <= 600000;
    }

    public boolean isForceKillHeavyCondition(ProcessRecord processRecord, int i) {
        return IsForceKillHeavyProcess(processRecord.processName) && (i > this.forceKillHeavyProcessLimit || processRecord.mState.getCurAdj() >= 920);
    }

    public void clearRecentActivityProcess() {
        this.recentActivityProcessList.clear();
    }

    public void addRecentActivityProcess(ProcessRecord processRecord) {
        if (this.recentActivityProcessList.size() >= this.recentActivityProcessLimit || !isCachedOrPickedActivityProcess(processRecord)) {
            return;
        }
        this.recentActivityProcessList.add(processRecord);
    }

    public boolean IsAllowListCleared() {
        return allowListCleared;
    }

    public final boolean isRecentActivityProcess(ProcessRecord processRecord) {
        return this.recentActivityProcessList.contains(processRecord) && isCachedOrPickedActivityProcess(processRecord);
    }

    public final int getIndexOfRecentActivityProcess(ProcessRecord processRecord) {
        return this.recentActivityProcessList.indexOf(processRecord);
    }

    public final boolean isCachedOrPickedActivityProcess(ProcessRecord processRecord) {
        if (processRecord.mState.getCurAdj() < 850 || processRecord.mState.getCurAdj() > 999) {
            return false;
        }
        return (processRecord.hasActivities() && (processRecord.mState.getCurProcState() == 10 || processRecord.mState.getCurProcState() == 15)) || processRecord.mState.getCurProcState() == 16;
    }

    public final void dhaAddPackageName(HashMap hashMap, String str, int i, boolean z) {
        if (this.mContext == null) {
            return;
        }
        String decodeToStr = DynamicHiddenApp.decodeToStr("TU1T");
        String decodeToStr2 = DynamicHiddenApp.decodeToStr("Q09OVEFDVFM=");
        String decodeToStr3 = DynamicHiddenApp.decodeToStr("SU5DQUxMVUk=");
        String decodeToStr4 = DynamicHiddenApp.decodeToStr("RElBTEVS");
        String decodeToStr5 = DynamicHiddenApp.decodeToStr("SE9NRUhVQg==");
        String decodeToStr6 = DynamicHiddenApp.decodeToStr("QklYQlk=");
        String decodeToStr7 = DynamicHiddenApp.decodeToStr("RkFDVE9SWQ==");
        String decodeToStr8 = DynamicHiddenApp.decodeToStr("Q01I");
        if (z && (decodeToStr.equals(str) || decodeToStr2.equals(str) || decodeToStr3.equals(str) || decodeToStr4.equals(str))) {
            return;
        }
        if (decodeToStr.equals(str)) {
            hashMap.put(getMessagePackageName(this.mContext), Integer.valueOf(i));
            return;
        }
        if (decodeToStr2.equals(str) && !this.removeContactExceptList) {
            hashMap.put(getContactsPackageName(this.mContext), Integer.valueOf(i));
            return;
        }
        if (decodeToStr4.equals(str)) {
            if (mTotalMemMb > this.DIALER_EXCEPTION_TH) {
                hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5kaWFsZXI="), Integer.valueOf(i));
                return;
            }
            return;
        }
        if (decodeToStr5.equals(str)) {
            hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5ob21laHVi"), Integer.valueOf(i));
            return;
        }
        if (decodeToStr6.equals(str)) {
            hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5iaXhieS5hZ2VudA=="), Integer.valueOf(i));
            return;
        }
        if (decodeToStr3.equals(str)) {
            hashMap.put(getInCallUIPackageName(this.mContext), Integer.valueOf(i));
            return;
        }
        if (decodeToStr7.equals(str) && BgAppPropManager.getSemSystemPropertyInt("ro.debuggable", "0") == 1) {
            Slog.i("DynamicHiddenApp_BGProtectManager", "it's debuggable binary!! add FACTORY in allowlist");
            hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWNhdGZ1bmN0aW9u"), Integer.valueOf(i));
            hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWN1aWZ1bmN0aW9u"), Integer.valueOf(i));
            hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5haXJjb21tYW5kbWFuYWdlcg=="), Integer.valueOf(i));
            return;
        }
        if (decodeToStr8.equals(str)) {
            hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuY21oOkNNSA=="), Integer.valueOf(i));
            hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuY21o"), Integer.valueOf(i));
        } else {
            hashMap.put(str, Integer.valueOf(i));
        }
    }

    public final void dhaAddNeverKilledPackageName(HashMap hashMap, String str, int i, String str2, String str3) {
        int i2;
        if (this.mContext == null) {
            return;
        }
        if ("plat".equals(str3)) {
            i2 = 1048576;
        } else {
            i2 = "priv".equals(str3) ? 8 : 0;
        }
        if (DynamicHiddenApp.decodeToStr("RkFDVE9SWQ==").equals(str)) {
            if (BgAppPropManager.getSemSystemPropertyInt("ro.debuggable", "0") == 1) {
                Slog.i("DynamicHiddenApp_BGProtectManager", "it's debuggable binary!! add FACTORY in allowlist");
                int i3 = i2 | 1048576;
                hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWNhdGZ1bmN0aW9u"), new Pair(Integer.valueOf(i), new PackageValidationInfo(DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWNhdGZ1bmN0aW9u"), i3)));
                hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWN1aWZ1bmN0aW9u"), new Pair(Integer.valueOf(i), new PackageValidationInfo(DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWN1aWZ1bmN0aW9u"), i3)));
                hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5haXJjb21tYW5kbWFuYWdlcg=="), new Pair(Integer.valueOf(i), new PackageValidationInfo(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5haXJjb21tYW5kbWFuYWdlcg=="), i3)));
                return;
            }
            return;
        }
        hashMap.put(str, new Pair(Integer.valueOf(i), new PackageValidationInfo(str2, i2)));
    }

    public final void dhaDeletePackageName(HashMap hashMap, String str, boolean z) {
        if (this.mContext == null) {
            return;
        }
        String decodeToStr = DynamicHiddenApp.decodeToStr("TU1T");
        String decodeToStr2 = DynamicHiddenApp.decodeToStr("Q09OVEFDVFM=");
        String decodeToStr3 = DynamicHiddenApp.decodeToStr("SU5DQUxMVUk=");
        String decodeToStr4 = DynamicHiddenApp.decodeToStr("RElBTEVS");
        String decodeToStr5 = DynamicHiddenApp.decodeToStr("Q01I");
        if (z && (decodeToStr.equals(str) || decodeToStr2.equals(str) || decodeToStr3.equals(str) || decodeToStr4.equals(str))) {
            return;
        }
        if (decodeToStr.equals(str)) {
            hashMap.remove(getMessagePackageName(this.mContext));
            return;
        }
        if (decodeToStr2.equals(str)) {
            hashMap.remove(getContactsPackageName(this.mContext));
            return;
        }
        if (decodeToStr4.equals(str)) {
            hashMap.remove(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5kaWFsZXI="));
            return;
        }
        if (decodeToStr3.equals(str)) {
            hashMap.remove(getInCallUIPackageName(this.mContext));
        } else if (decodeToStr5.equals(str)) {
            hashMap.remove(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuY21oOkNNSA=="));
            hashMap.remove(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuY21o"));
        } else {
            hashMap.remove(str);
        }
    }

    public final String getMessagePackageName(Context context) {
        String decodeToStr = DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQubW1z");
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_MESSAGE_CONFIG_PACKAGE_NAME", decodeToStr);
        if (decodeToStr.equals(string)) {
            return decodeToStr;
        }
        try {
            context.getApplicationContext().getPackageManager().getPackageInfo(string, 0);
            return string;
        } catch (PackageManager.NameNotFoundException unused) {
            return decodeToStr;
        }
    }

    public String getContactsPackageName(Context context) {
        String decodeToStr = DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQuY29udGFjdHM=");
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_CONTACTS_CONFIG_PACKAGE_NAME", decodeToStr);
        if (decodeToStr.equals(string)) {
            return decodeToStr;
        }
        try {
            context.getApplicationContext().getPackageManager().getPackageInfo(string, 0);
            return string;
        } catch (PackageManager.NameNotFoundException unused) {
            return decodeToStr;
        }
    }

    public final String getInCallUIPackageName(Context context) {
        String decodeToStr = DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQuaW5jYWxsdWk=");
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_VOICECALL_CONFIG_INCALLUI_PACKAGE_NAME", decodeToStr);
        if (decodeToStr.equals(string)) {
            return decodeToStr;
        }
        try {
            context.getApplicationContext().getPackageManager().getPackageInfo(string, 0);
            return string;
        } catch (PackageManager.NameNotFoundException unused) {
            return decodeToStr;
        }
    }

    enum exceptFlag {
        NORMALANDKNOXPWHL(0),
        NORMALONLY(1),
        NORMALANDKNOX(2),
        KNOXONLY(3),
        SANDBOX(4),
        CAMERAGUARD(5),
        BROWSERMAIN(6),
        HOMEHUB(7),
        CAMERAMEDIA(8);

        private final int value;

        exceptFlag(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }

        public String getString() {
            return Integer.toString(this.value);
        }
    }

    public void dumpMLList(PrintWriter printWriter) {
        if (this.NapProcessSlotDefault != null) {
            printWriter.println("  MLList NAP Process name : " + this.NapProcessSlotDefault.processName);
        } else {
            printWriter.println("  MLList NAP Process name : []");
        }
        if (dha_MLexcept_map != null) {
            printWriter.println("  MLList AUF Process name : " + dha_MLexcept_map.toString());
        }
        printWriter.println();
    }

    public class PackageValidationInfo {
        public String packageName;
        public int privateFlagsMask;

        public PackageValidationInfo(String str, int i) {
            this.packageName = str;
            this.privateFlagsMask = i;
        }

        public boolean validate(ProcessRecord processRecord) {
            if (processRecord != null && processRecord.info != null) {
                int i = processRecord.info.privateFlags;
                int i2 = this.privateFlagsMask;
                if ((i & i2) == i2 && processRecord.info.packageName != null && processRecord.info.packageName.equals(this.packageName)) {
                    return true;
                }
            }
            return false;
        }
    }
}
