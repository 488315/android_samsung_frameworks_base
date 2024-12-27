package com.android.server.am;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.SemSystemProperties;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Pair;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.bgslotmanager.BgAppPropManager;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BGProtectManager {
    public static final String[] BOOTING_EMPTY_KILL_SKIP_ARRAY;
    public static final String[] CAMERA_GUARD_ARRAY;
    public static final String[][] DHA_NEVERKILLEXCEPT_ARRAY;
    public static final String[][] DHA_NEVERKILLEXCEPT_ARRAY_BY_KEY;
    public static final String[][] DHA_STATICEXCEPT_PROC_ARRAY;
    public static final String[] LMKD_CAM_CLIENT_EXCEPT_ARRAY;
    public static final String[] PROVIDER_LIFEGUARD_ARRAY;
    public static final ArrayList dha_MLexcept_map;
    public static final HashMap dha_amsexcept_map;
    public static final HashMap dha_cameraguard_map;
    public static final int dha_keep_onlyact_key;
    public static final int dha_keepchimera_key;
    public static final int dha_keepempty_chn_key;
    public static int dha_keepempty_key;
    public static int dha_keepempty_key_knox;
    public static final HashMap dha_keepempty_map;
    public static final int dha_neverkillexcept_key;
    public static final HashMap dha_neverkillexcept_map;
    public static final ArrayList sBEKS_processList;
    public static final int sProvider_lifeguard_key;
    public static final int sProvider_lifeguard_memory_TH;
    public boolean AMSExceptionProviderUpgradeAdjEnable;
    public boolean BOOTING_EMPTY_KILL_SKIP_ENABLE;
    public boolean CLEANUP_WEBVIEW_ENABLE;
    public int DIALER_EXCEPTION_TH;
    public boolean NEVERKILL_SQETOOL_ENABLE;
    public ProcessRecord NapProcessSlotDefault;
    public int NapProcessSlotLimit;
    public ArrayList PICKED_ADJ_EXCEPT;
    public int PICKED_ADJ_TIME_LIMIT;
    public Context mContext;
    public int mDhaKeepEmptyEnable;
    public int mDhaKeepEmptyEnableKnox;
    public boolean mKnoxAMSExceptionEnable;
    public int recentActivityProcessLimit;
    public ArrayList recentActivityProcessList;
    public boolean removeContactExceptList;
    public static final long mTotalMemMb = Process.getTotalMemory() / 1048576;
    public static boolean mAMSExceptionEnable =
            BgAppPropManager.getSlmkPropertyBool("ams_exception_enable", "true");
    public static final int WEBVIEW_ADJ_THRESHOLD =
            BgAppPropManager.getSlmkPropertyInt("webview_adj_th", Integer.toString(920));
    public static final boolean mCameraGuardEnable =
            BgAppPropManager.getSlmkPropertyBool("camera_guard_enable", "true");
    public static final int beks_package_key_bit =
            BgAppPropManager.getSlmkPropertyInt("beks_key", "31");
    public static boolean allowListCleared = false;
    public static final String[] DHA_DYNAMICEXCEPT_PROC_ARRAY = {
        DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5wcm9jZXNzLmdhcHBz"),
        DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmdhbGxlcnkzZA=="),
        DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="),
        DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdt"),
        DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmFwcHMubWFwcw=="),
        DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5hcHAubm90ZXM="),
        DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQudmVuZGluZw=="),
        DynamicHiddenApp.decodeToStr("UmVzZXJ2ZWQ="),
        DynamicHiddenApp.decodeToStr("SU5DQUxMVUk="),
        DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE="),
        DynamicHiddenApp.decodeToStr("TU1T"),
        DynamicHiddenApp.decodeToStr("Y29tLmJhaWR1LkJhaWR1TWFw"),
        DynamicHiddenApp.decodeToStr("UmVzZXJ2ZWQ=")
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageValidationInfo {
        public final String packageName;
        public final int privateFlagsMask;

        public PackageValidationInfo(String str, int i) {
            this.packageName = str;
            this.privateFlagsMask = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum exceptFlag {
        NORMALANDKNOXPWHL("NORMALANDKNOXPWHL"),
        /* JADX INFO: Fake field, exist only in values array */
        EF1("NORMALONLY"),
        NORMALANDKNOX("NORMALANDKNOX"),
        KNOXONLY("KNOXONLY"),
        SANDBOX("SANDBOX"),
        CAMERAGUARD("CAMERAGUARD"),
        BROWSERMAIN("BROWSERMAIN"),
        HOMEHUB("HOMEHUB"),
        CAMERAMEDIA("CAMERAMEDIA");

        private final int value;

        exceptFlag(String str) {
            this.value = r2;
        }

        public final String getString() {
            return Integer.toString(this.value);
        }

        public final int getValue() {
            return this.value;
        }
    }

    static {
        String decodeToStr = DynamicHiddenApp.decodeToStr("YW5kcm9pZC5wcm9jZXNzLmFjb3Jl");
        exceptFlag exceptflag = exceptFlag.NORMALANDKNOX;
        String[] strArr = {decodeToStr, exceptflag.getString()};
        String decodeToStr2 = DynamicHiddenApp.decodeToStr("Q09OVEFDVFM=");
        exceptFlag exceptflag2 = exceptFlag.NORMALANDKNOXPWHL;
        DHA_STATICEXCEPT_PROC_ARRAY =
                new String[][] {
                    strArr,
                    new String[] {decodeToStr2, exceptflag2.getString()},
                    new String[] {
                        DynamicHiddenApp.decodeToStr("RElBTEVS"), exceptflag2.getString()
                    },
                    new String[] {
                        DynamicHiddenApp.decodeToStr("SE9NRUhVQg=="), exceptflag2.getString()
                    },
                    new String[] {
                        DynamicHiddenApp.decodeToStr("YW5kcm9pZC5wcm9jZXNzLm1lZGlh"),
                        exceptFlag.CAMERAMEDIA.getString()
                    },
                    new String[] {DynamicHiddenApp.decodeToStr("Q01I"), exceptflag.getString()},
                    new String[] {
                        DynamicHiddenApp.decodeToStr("QklYQlk="), exceptFlag.HOMEHUB.getString()
                    }
                };
        PROVIDER_LIFEGUARD_ARRAY =
                new String[] {
                    DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5wcm9jZXNzLmdhcHBz"),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5tb2JpbGVzZXJ2aWNl"),
                    DynamicHiddenApp.decodeToStr("Y29tLm9zcC5hcHAuc2lnbmlu"),
                    DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5wcm9jZXNzLmdzZXJ2aWNlcw=="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLnByb3ZpZGVyLmJhZGdl"),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuaXBzZXJ2aWNl"),
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5zb3VuZGFsaXZl"),
                    DynamicHiddenApp.decodeToStr(
                            "Y29tLmdvb2dsZS5hbmRyb2lkLmdvb2dsZXF1aWNrc2VhcmNoYm94OnNlYXJjaA=="),
                    DynamicHiddenApp.decodeToStr(
                            "Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcjpwcml2aWxlZ2VkX3Byb2Nlc3Mw"),
                    DynamicHiddenApp.decodeToStr("Y29tLnZlcml6b24ubWVzc2FnaW5nLnZ6bXNncw=="),
                    DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQucHJvdmlkZXJzLmNhbGVuZGFy"),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5zY2xvdWQ="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5zYW1zdW5ncGFzcw=="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5hcHAucmVtaW5kZXI="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5hcHAucm91dGluZXM="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy51bmlmaWVkd2Zj"),
                    DynamicHiddenApp.decodeToStr(
                            "Y29tLnNhbXN1bmcuYW5kcm9pZC5nbG9iYWxwb3N0cHJvY21ncg==")
                };
        DHA_NEVERKILLEXCEPT_ARRAY =
                new String[][] {
                    new String[] {
                        DynamicHiddenApp.decodeToStr("Y29tLnNhbGFiLmFjdA=="),
                        "2",
                        DynamicHiddenApp.decodeToStr("Y29tLnNhbGFiLmFjdA=="),
                        "plat"
                    },
                    new String[] {
                        DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC50aW55bQ=="),
                        "2",
                        DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC50aW55bQ=="),
                        "plat"
                    },
                    new String[] {
                        DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hZWNtb25pdG9y"),
                        "2",
                        DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hZWNtb25pdG9y"),
                        "plat"
                    },
                    new String[] {DynamicHiddenApp.decodeToStr("RkFDVE9SWQ=="), "2", "", ""}
                };
        DHA_NEVERKILLEXCEPT_ARRAY_BY_KEY =
                new String[][] {
                    new String[] {
                        DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdtcw=="),
                        "1",
                        DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdtcw=="),
                        "priv"
                    },
                    new String[] {
                        DynamicHiddenApp.decodeToStr(
                                "Y29tLmdvb2dsZS5hbmRyb2lkLmdtcy5wZXJzaXN0ZW50"),
                        "1",
                        DynamicHiddenApp.decodeToStr("Y29tLmdvb2dsZS5hbmRyb2lkLmdtcw=="),
                        "priv"
                    }
                };
        BOOTING_EMPTY_KILL_SKIP_ARRAY =
                new String[] {
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5teWZpbGVz"),
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="),
                    DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQuc2V0dGluZ3M="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmdhbGxlcnkzZA=="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5kaWFsZXI="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5tZXNzYWdpbmc="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5hcHAubm90ZXM="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5jbG9ja3BhY2thZ2U="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5jYWxlbmRhcg=="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC52b2ljZW5vdGU=")
                };
        CAMERA_GUARD_ARRAY =
                new String[] {DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmdhbGxlcnkzZA==")};
        LMKD_CAM_CLIENT_EXCEPT_ARRAY =
                new String[] {
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYWRhcHRpdmVicmlnaHRuZXNzZ28="),
                    DynamicHiddenApp.decodeToStr("YW5kcm9pZC5zeXN0ZW0="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5zbWFydGZhY2U="),
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5iaW8uZmFjZS5zZXJ2aWNl")
                };
        sBEKS_processList = new ArrayList();
        dha_keepempty_map = new HashMap();
        dha_amsexcept_map = new HashMap();
        dha_MLexcept_map = new ArrayList();
        dha_neverkillexcept_map = new HashMap();
        dha_cameraguard_map = new HashMap();
        dha_keepempty_key = BgAppPropManager.getSlmkPropertyInt("dha_pwhl_key", "512");
        dha_keepempty_key_knox = BgAppPropManager.getSlmkPropertyInt("dha_pwhl_key_knox", "1539");
        dha_keepempty_chn_key =
                BgAppPropManager.getSlmkPropertyInt(
                        "dha_pwhl_chn_key", Integer.toString(dha_keepempty_key));
        dha_keepchimera_key = BgAppPropManager.getSlmkPropertyInt("dha_chimerawhl_key", "0");
        dha_keep_onlyact_key = BgAppPropManager.getSlmkPropertyInt("dha_onlyact_key", "0");
        dha_neverkillexcept_key = BgAppPropManager.getSlmkPropertyInt("dha_neverkill_key", "0");
        BgAppPropManager.getSlmkPropertyBool("add_protect", "false");
        sProvider_lifeguard_memory_TH =
                BgAppPropManager.getSlmkPropertyInt("plg_memory_th", "4096");
        sProvider_lifeguard_key = BgAppPropManager.getSlmkPropertyInt("plg_key", "3");
    }

    public static boolean IsProtected(ProcessRecord processRecord) {
        int i;
        int i2;
        boolean z = DynamicHiddenApp.sHH_AMSExceptionEnable;
        exceptFlag exceptflag = exceptFlag.SANDBOX;
        return z
                ? (processRecord.isAMSException
                                && processRecord.AMSExceptionFlag != exceptflag.getValue())
                        || ((i2 = processRecord.dhaKeepEmptyFlag) > 0 && i2 < 3)
                        || isOnlyActCheck(processRecord)
                : !(!processRecord.isAMSException
                                || processRecord.AMSExceptionFlag == exceptflag.getValue()
                                || processRecord.AMSExceptionFlag == exceptFlag.HOMEHUB.getValue())
                        || ((i = processRecord.dhaKeepEmptyFlag) > 0 && i < 3)
                        || isOnlyActCheck(processRecord);
    }

    public static void addBEKSList(boolean z) {
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

    public static int appIsPickedProcess(int i, String str) {
        String str2 = String.valueOf(i) + "_&_" + str;
        ArrayList arrayList = dha_MLexcept_map;
        if (arrayList == null || !arrayList.contains(str2)) {
            return -1;
        }
        return arrayList.indexOf(str2);
    }

    public static String getContactsPackageName(Context context) {
        String decodeToStr = DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQuY29udGFjdHM=");
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_CONTACTS_CONFIG_PACKAGE_NAME", decodeToStr);
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

    public static String getInCallUIPackageName(Context context) {
        String decodeToStr = DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQuaW5jYWxsdWk=");
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_VOICECALL_CONFIG_INCALLUI_PACKAGE_NAME",
                                decodeToStr);
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

    public static String getMessagePackageName(Context context) {
        String decodeToStr = DynamicHiddenApp.decodeToStr("Y29tLmFuZHJvaWQubW1z");
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_MESSAGE_CONFIG_PACKAGE_NAME", decodeToStr);
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

    public static boolean isCachedOrPickedActivityProcess(ProcessRecord processRecord) {
        int i;
        int i2 = processRecord.mState.mCurAdj;
        if (i2 < 850 || i2 > 999) {
            return false;
        }
        return (processRecord.mWindowProcessController.mHasActivities
                        && ((i = processRecord.mState.mCurProcState) == 10 || i == 15))
                || processRecord.mState.mCurProcState == 16;
    }

    public static int isDhaKeepEmptyProcess(String str) {
        HashMap hashMap = dha_keepempty_map;
        if (hashMap == null || !hashMap.containsKey(str)) {
            return -1;
        }
        return ((Integer) hashMap.get(str)).intValue();
    }

    public static boolean isOnlyActCheck(ProcessRecord processRecord) {
        return processRecord.dhaKeepEmptyFlag == 4
                && processRecord.mWindowProcessController.mHasActivities;
    }

    public static int isWebviewProcess(ProcessRecord processRecord) {
        if (processRecord.mHostingRecord == null
                || processRecord.mHostingRecord.mHostingZygote != 1) {
            if (processRecord.processName.contains(":sandboxed_process")) {
                return 4;
            }
            return KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME.equals(
                            processRecord.processName)
                    ? 6
                    : -1;
        }
        if (processRecord.mHostingRecord.mHostingName == null) {
            BootReceiver$$ExternalSyntheticOutline0.m59m(
                    new StringBuilder("check webview name : "),
                    processRecord.processName,
                    "check hostingname webview : null ",
                    "DynamicHiddenApp_BGProtectManager");
            return 2;
        }
        StringBuilder sb = new StringBuilder("check webview name : ");
        sb.append(processRecord.processName);
        sb.append("check hostingname webview : ");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                sb, processRecord.mHostingRecord.mHostingName, "DynamicHiddenApp_BGProtectManager");
        return 2;
    }

    public final void addAllowlistList(boolean z) {
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
            int i10 = this.mDhaKeepEmptyEnable;
            if (i10 == 1 && (dha_keepchimera_key & i9) != 0) {
                dhaAddPackageName(dha_keepempty_map, strArr10[i8], 3, z);
            }
            if (this.mDhaKeepEmptyEnableKnox == 1 && (dha_keepempty_key_knox & i9) != 0) {
                dhaAddPackageName(dha_keepempty_map, strArr10[i8], 2, z);
            }
            if (i10 == 1 && (dha_keepempty_key & i9) != 0) {
                dhaAddPackageName(dha_keepempty_map, strArr10[i8], 1, z);
            }
            if (i10 == 1 && (dha_keep_onlyact_key & i9) != 0) {
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

    public final void dhaAddNeverKilledPackageName(
            HashMap hashMap, String str, int i, String str2, String str3) {
        if (this.mContext == null) {
            return;
        }
        int i2 = "plat".equals(str3) ? 1048576 : "priv".equals(str3) ? 8 : 0;
        if (!DynamicHiddenApp.decodeToStr("RkFDVE9SWQ==").equals(str)) {
            hashMap.put(str, new Pair(Integer.valueOf(i), new PackageValidationInfo(str2, i2)));
            return;
        }
        if (SemSystemProperties.getInt("ro.debuggable", Integer.parseInt("0")) == 1) {
            Slog.i(
                    "DynamicHiddenApp_BGProtectManager",
                    "it's debuggable binary!! add FACTORY in allowlist");
            int i3 = i2 | 1048576;
            hashMap.put(
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWNhdGZ1bmN0aW9u"),
                    new Pair(
                            Integer.valueOf(i),
                            new PackageValidationInfo(
                                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWNhdGZ1bmN0aW9u"),
                                    i3)));
            hashMap.put(
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWN1aWZ1bmN0aW9u"),
                    new Pair(
                            Integer.valueOf(i),
                            new PackageValidationInfo(
                                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWN1aWZ1bmN0aW9u"),
                                    i3)));
            hashMap.put(
                    DynamicHiddenApp.decodeToStr(
                            "Y29tLnNhbXN1bmcuYW5kcm9pZC5haXJjb21tYW5kbWFuYWdlcg=="),
                    new Pair(
                            Integer.valueOf(i),
                            new PackageValidationInfo(
                                    DynamicHiddenApp.decodeToStr(
                                            "Y29tLnNhbXN1bmcuYW5kcm9pZC5haXJjb21tYW5kbWFuYWdlcg=="),
                                    i3)));
        }
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
        if (z
                && (decodeToStr.equals(str)
                        || decodeToStr2.equals(str)
                        || decodeToStr3.equals(str)
                        || decodeToStr4.equals(str))) {
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
                hashMap.put(
                        DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5kaWFsZXI="),
                        Integer.valueOf(i));
                return;
            }
            return;
        }
        if (decodeToStr5.equals(str)) {
            hashMap.put(
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5ob21laHVi"),
                    Integer.valueOf(i));
            return;
        }
        if (decodeToStr6.equals(str)) {
            hashMap.put(
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuYW5kcm9pZC5iaXhieS5hZ2VudA=="),
                    Integer.valueOf(i));
            return;
        }
        if (decodeToStr3.equals(str)) {
            hashMap.put(getInCallUIPackageName(this.mContext), Integer.valueOf(i));
            return;
        }
        if (decodeToStr7.equals(str)
                && SemSystemProperties.getInt("ro.debuggable", Integer.parseInt("0")) == 1) {
            Slog.i(
                    "DynamicHiddenApp_BGProtectManager",
                    "it's debuggable binary!! add FACTORY in allowlist");
            hashMap.put(
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWNhdGZ1bmN0aW9u"),
                    Integer.valueOf(i));
            hashMap.put(
                    DynamicHiddenApp.decodeToStr("Y29tLnNlYy5mYWN1aWZ1bmN0aW9u"),
                    Integer.valueOf(i));
            hashMap.put(
                    DynamicHiddenApp.decodeToStr(
                            "Y29tLnNhbXN1bmcuYW5kcm9pZC5haXJjb21tYW5kbWFuYWdlcg=="),
                    Integer.valueOf(i));
            return;
        }
        if (!decodeToStr8.equals(str)) {
            hashMap.put(str, Integer.valueOf(i));
        } else {
            hashMap.put(
                    DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuY21oOkNNSA=="),
                    Integer.valueOf(i));
            hashMap.put(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuY21o"), Integer.valueOf(i));
        }
    }

    public final void dhaDeletePackageName(String str, HashMap hashMap) {
        if (this.mContext == null) {
            return;
        }
        String decodeToStr = DynamicHiddenApp.decodeToStr("TU1T");
        String decodeToStr2 = DynamicHiddenApp.decodeToStr("Q09OVEFDVFM=");
        String decodeToStr3 = DynamicHiddenApp.decodeToStr("SU5DQUxMVUk=");
        String decodeToStr4 = DynamicHiddenApp.decodeToStr("RElBTEVS");
        String decodeToStr5 = DynamicHiddenApp.decodeToStr("Q01I");
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
        } else if (!decodeToStr5.equals(str)) {
            hashMap.remove(str);
        } else {
            hashMap.remove(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuY21oOkNNSA=="));
            hashMap.remove(DynamicHiddenApp.decodeToStr("Y29tLnNhbXN1bmcuY21o"));
        }
    }

    public final void initBGProtectManagerPostBoot() {
        addAllowlistList(false);
        if (this.BOOTING_EMPTY_KILL_SKIP_ENABLE) {
            addBEKSList(false);
        }
        if ("on".equals(SystemProperties.get("persist.sys.bub_onoff", "on"))) {
            removeAllowlistByBUB();
        }
    }

    public final boolean isBEKCondition(ProcessRecord processRecord) {
        return this.BOOTING_EMPTY_KILL_SKIP_ENABLE
                && sBEKS_processList.contains(processRecord.processName)
                && SystemClock.uptimeMillis() <= 600000;
    }

    public final void removeAllowlistByBUB() {
        HashMap hashMap = dha_amsexcept_map;
        dhaDeletePackageName(DynamicHiddenApp.decodeToStr("Q09OVEFDVFM="), hashMap);
        dhaDeletePackageName(DynamicHiddenApp.decodeToStr("RElBTEVS"), hashMap);
        if (mTotalMemMb > sProvider_lifeguard_memory_TH) {
            int i = 1;
            int i2 = 0;
            while (true) {
                String[] strArr = PROVIDER_LIFEGUARD_ARRAY;
                if (i2 >= strArr.length) {
                    break;
                }
                if ((sProvider_lifeguard_key & i) != 0) {
                    dhaDeletePackageName(strArr[i2], dha_amsexcept_map);
                }
                i <<= 1;
                i2++;
            }
        }
        dha_keepempty_map.clear();
        dha_cameraguard_map.clear();
    }
}
