package com.sec.android.iaft;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.SemSystemProperties;
import android.os.StatFs;
import android.telecom.Logging.Session;
import android.util.Slog;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.HashMap;
import java.util.Locale;

/* loaded from: classes6.dex */
public class IAFDDiagnosis {
    private static final int EXP_REPAIRINFO_AppFlag = 3;
    private static final int EXP_REPAIRINFO_MainLan = 4;
    private static final int EXP_REPAIRINFO_NoUpdateFlag = 2;
    private static final int EXP_REPAIRINFO_Only32BitApp = 6;
    private static final int EXP_REPAIRINFO_PileFlag = 0;
    private static final int EXP_REPAIRINFO_RepairModeFlag = 1;
    private static final int EXP_REPAIRINFO_SubLan = 5;
    private static final int EXP_RULE_32BITONLY = 4;
    private static final int EXP_RULE_LIBS = 2;
    private static final int EXP_RULE_NONE = 0;
    private static final int EXP_RULE_PKGN = 1;
    private static final int EXP_UNKNOW = -1;
    private static final int FLAG_SUPPORT3RDAPP = 1;
    private static final int FLAG_SUPPORTSYSAPP = 2;
    private static final String TAG = "IAFDDiagnosis";
    private String callstack;
    private String component;
    private int curAppFlag;
    private IAFD_ENTITY curExpEntity;
    private int dualUserId;
    private int expType;
    private boolean isCHNModel;
    private boolean isParseSuccess;
    private Context mContext;
    private IAFD_DATA mIFADData;
    private String mSalesCode;
    private String reason;

    private static class IAFDDiagnosisHolder {
        private static final IAFDDiagnosis INSTANCE = new IAFDDiagnosis();

        private IAFDDiagnosisHolder() {
        }
    }

    private IAFDDiagnosis() {
        this.mContext = null;
        this.mIFADData = null;
        this.mSalesCode = null;
        this.isCHNModel = false;
    }

    public static IAFDDiagnosis getInstance() {
        return IAFDDiagnosisHolder.INSTANCE;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mSalesCode = SemSystemProperties.getSalesCode();
        this.isCHNModel = "com.samsung.android.sm_cn".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool"));
        initData(false);
    }

    private void initData(boolean z) {
        try {
            IAFDDBManager.getInstance().init(this.mContext, this.mSalesCode, this.isCHNModel);
            this.mIFADData = IAFDDBManager.getInstance().getData();
            if (z) {
                int i = 10;
                while (this.mIFADData == null && i > 0) {
                    Slog.d(TAG, "initData wait...");
                    Thread.sleep(20);
                    i--;
                    this.mIFADData = IAFDDBManager.getInstance().getData();
                }
            }
        } catch (Exception e) {
            Slog.d("==IAFD==", "initData fail, callstack as the following:");
            e.printStackTrace();
        }
    }

    private boolean isNativeCrash(String str) {
        return str.contains("Native crash");
    }

    private boolean isContainExpClassName(String str, int i) {
        if (str == null) {
            return false;
        }
        int lastIndexOf = str.lastIndexOf(46) + 1;
        if (lastIndexOf < 0) {
            lastIndexOf = 0;
        }
        String substring = str.substring(lastIndexOf);
        if (substring == null || !this.mIFADData.hashMapJE_ClassNameTB.containsKey(substring)) {
            return false;
        }
        int intValue = this.mIFADData.hashMapJE_ClassNameTB.get(substring).intValue();
        if (i != (this.mIFADData.JE_ClassNameTB[intValue].supportFlag & i)) {
            return false;
        }
        this.expType = this.mIFADData.JE_ClassNameTB[intValue].expID;
        this.curExpEntity = this.mIFADData.JE_ClassNameTB[intValue];
        return this.mIFADData.JE_ClassNameTB[intValue].ruleType <= 0;
    }

    private boolean isContainExpInfo(String str, IAFD_ENTITY[] iafd_entityArr, String str2, int i) {
        if (str == null) {
            return false;
        }
        for (IAFD_ENTITY iafd_entity : iafd_entityArr) {
            if (iafd_entity.enable && (iafd_entity.supportFlag & i) == i && str.contains(iafd_entity.keyWord)) {
                if (iafd_entity.ruleType != 2) {
                    this.expType = iafd_entity.expID;
                    this.curExpEntity = iafd_entity;
                    return (iafd_entity.ruleType == 1 || iafd_entity.ruleType == 4) ? false : true;
                }
                if (str2 == null) {
                    return false;
                }
                int length = iafd_entity.rules.length;
                for (int i2 = 1; i2 < length; i2++) {
                    if (str2.contains(iafd_entity.rules[i2])) {
                        this.expType = iafd_entity.expID;
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private String getSubStringForNE(String str, int i, boolean z) {
        int i2;
        int indexOf;
        if (z) {
            i2 = str.charAt(str.indexOf("ABI:") + 9) == '6' ? 1 : 0;
            int indexOf2 = str.indexOf("pid:");
            if (i2 != 0) {
                indexOf = str.indexOf(" x0 ");
            } else {
                indexOf = str.indexOf(" r0 ");
            }
            if (indexOf <= indexOf2 && (indexOf = str.indexOf("backtrace:")) <= indexOf2) {
                return null;
            }
            int i3 = i + indexOf2;
            if (i3 > str.length()) {
                i3 = str.length();
            }
            if (indexOf > i3) {
                indexOf = i3;
            }
            return str.substring(indexOf2, indexOf);
        }
        int indexOf3 = str.indexOf("backtrace:");
        i2 = indexOf3 >= 0 ? indexOf3 + 11 : 0;
        int i4 = i + i2;
        if (i4 > str.length()) {
            i4 = str.length();
        }
        return str.substring(i2, i4);
    }

    private String getCauseForNE(String str, int i) {
        int i2;
        int indexOf = str.indexOf("Cause:");
        if (indexOf < 0) {
            indexOf = str.indexOf("Abort message:");
            if (indexOf < 0) {
                return null;
            }
            i2 = 14;
        } else {
            i2 = 6;
        }
        int i3 = indexOf + i;
        if (i3 > str.length()) {
            i3 = str.length();
        }
        String substring = str.substring(indexOf, i3);
        if (substring == null) {
            return null;
        }
        int i4 = i + i2;
        if (i4 > substring.length()) {
            i4 = substring.length();
        }
        int indexOf2 = substring.indexOf(ShaderAssembler.NEWLINE);
        if (indexOf2 > i2 && i4 > indexOf2) {
            i4 = indexOf2;
        }
        return substring.substring(i2, i4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0045, code lost:
    
        if (r1 <= r4) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getComponent(java.lang.String r2, java.lang.String r3, int r4, boolean r5) {
        /*
            r1 = this;
            java.lang.String r1 = "com."
            if (r2 == 0) goto L13
            int r0 = r2.indexOf(r1)
            if (r0 < 0) goto L13
            int r1 = r2.length()
            java.lang.String r1 = r2.substring(r0, r1)
            return r1
        L13:
            if (r3 == 0) goto L5b
            if (r5 == 0) goto L36
            java.lang.String r5 = "/data/app/"
            int r5 = r3.indexOf(r5)
            if (r5 >= 0) goto L25
            java.lang.String r5 = "/app/"
            int r5 = r3.indexOf(r5)
        L25:
            int r0 = r3.indexOf(r1, r5)
            if (r0 >= 0) goto L2f
            int r0 = r3.indexOf(r1)
        L2f:
            if (r0 >= 0) goto L34
            if (r5 < 0) goto L34
            goto L3a
        L34:
            r5 = r0
            goto L3a
        L36:
            int r5 = r3.indexOf(r1)
        L3a:
            if (r5 < 0) goto L5b
            java.lang.String r1 = "\n"
            int r1 = r3.indexOf(r1, r5)
            if (r1 <= 0) goto L4a
            int r4 = r4 + r5
            if (r1 > r4) goto L48
            goto L56
        L48:
            r1 = r4
            goto L56
        L4a:
            int r4 = r4 + r5
            int r1 = r3.length()
            if (r4 > r1) goto L52
            goto L48
        L52:
            int r1 = r3.length()
        L56:
            java.lang.String r1 = r3.substring(r5, r1)
            return r1
        L5b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.iaft.IAFDDiagnosis.getComponent(java.lang.String, java.lang.String, int, boolean):java.lang.String");
    }

    private int findStringFromRtoL(String str, String str2, int i, int i2) {
        int length = str2.length();
        int i3 = length - 1;
        int length2 = str.length() - i2;
        if (length2 < 0) {
            length2 = 0;
        }
        for (int i4 = (i + 1) - length; i4 >= length2; i4--) {
            int i5 = i4;
            for (int i6 = 0; i6 < length && str.charAt(i5) == str2.charAt(i6); i6++) {
                if (i6 == i3) {
                    return i4;
                }
                i5++;
            }
        }
        return -1;
    }

    private String getCallstackForJE(String str, String str2, int i) {
        int i2 = i << 4;
        int length = str.length();
        if (length <= i) {
            return str;
        }
        int i3 = length - 1;
        String str3 = "";
        int i4 = 0;
        while (i4 < 2) {
            i4++;
            int findStringFromRtoL = findStringFromRtoL(str, "Caused by:", i3, i2);
            if (findStringFromRtoL < 0) {
                break;
            }
            int i5 = i3 - findStringFromRtoL;
            if (i5 >= i) {
                i5 = i;
            }
            str3 = str3 + str.substring(findStringFromRtoL, findStringFromRtoL + i5);
            i -= i5;
            i3 = findStringFromRtoL;
        }
        if (i <= 0) {
            return str3;
        }
        return str3 + str.substring(0, i);
    }

    private boolean isContainPkgname(String str, String str2, String str3) {
        String substring;
        int indexOf = str.indexOf(str2);
        return indexOf >= 0 && (substring = str.substring(indexOf, str.length())) != null && substring.contains(str3);
    }

    private boolean parseExpTypeInternal(String str, String str2, int i, int i2, int i3, String str3, String str4, String str5) {
        IAFD_DATA iafd_data;
        this.dualUserId = 0;
        this.isParseSuccess = false;
        if (str5 == null) {
            return false;
        }
        try {
            this.curAppFlag = 1;
            if (str != null && ((i3 & 1) != 0 || (i3 & 128) != 0 || str.contains("com.samsung.") || str.contains("com.sec."))) {
                this.curAppFlag = 2;
            }
            initData(true);
            this.expType = -1;
            this.curExpEntity = null;
            iafd_data = this.mIFADData;
        } catch (Exception e) {
            Slog.d(TAG, "parseExpType fail, skip, callstack as the following:");
            e.printStackTrace();
        }
        if (iafd_data == null || !iafd_data.controlInfo.enable || this.mIFADData.controlInfo.isInWhiteList(str)) {
            return false;
        }
        Slog.d(TAG, "parseExpType start");
        this.reason = null;
        this.callstack = null;
        this.component = null;
        if (isNativeCrash(str3)) {
            int i4 = this.mIFADData.controlInfo.NE_cstack_maxSize;
            String subStringForNE = getSubStringForNE(str5, i4, false);
            String subStringForNE2 = getSubStringForNE(str5, i4, true);
            if (subStringForNE2 != null) {
                this.reason = getCauseForNE(subStringForNE2, this.mIFADData.controlInfo.reason_maxSize);
            }
            if (subStringForNE != null) {
                int length = subStringForNE.length();
                if (length > this.mIFADData.controlInfo.callstack_maxSize) {
                    length = this.mIFADData.controlInfo.callstack_maxSize;
                }
                this.callstack = subStringForNE.substring(0, length);
            }
            this.component = getComponent(this.reason, subStringForNE, this.mIFADData.controlInfo.reason_maxSize, true);
            if (this.mIFADData.controlInfo.enableDetectAll32bitApps && (this.curAppFlag & this.mIFADData.controlInfo.supportflagDetectAll32bitApps) == this.curAppFlag && is32BitApp(str2, null)) {
                this.expType = 30;
                return true;
            }
            if (subStringForNE != null) {
                if (isContainExpInfo(subStringForNE, this.mIFADData.NE_CallStackTB, subStringForNE, this.curAppFlag)) {
                    this.isParseSuccess = true;
                    return true;
                }
                if (isContainExpInfo(subStringForNE, this.mIFADData.NE_HeaderInfoTB, subStringForNE, this.curAppFlag)) {
                    this.isParseSuccess = true;
                    return true;
                }
            }
            if (subStringForNE2 != null && isContainExpInfo(subStringForNE2, this.mIFADData.NE_HeaderInfoTB, subStringForNE, this.curAppFlag)) {
                this.isParseSuccess = true;
                return true;
            }
            IAFD_ENTITY iafd_entity = this.curExpEntity;
            if (iafd_entity != null) {
                if (iafd_entity.ruleType == 1 && this.curExpEntity.expID == this.expType) {
                    if (isContainPkgname(subStringForNE, this.mIFADData.controlInfo.NE_cstack_start, str)) {
                        this.isParseSuccess = true;
                        return true;
                    }
                } else if (this.curExpEntity.ruleType == 4 && this.curExpEntity.expID == this.expType && is32BitApp(str2, null)) {
                    this.isParseSuccess = true;
                    return true;
                }
            }
        } else {
            String str6 = "";
            if (str4 != null) {
                int length2 = str4.length();
                if (length2 > this.mIFADData.controlInfo.reason_maxSize) {
                    length2 = this.mIFADData.controlInfo.reason_maxSize;
                }
                this.reason = str4.substring(0, length2);
            }
            if (str5 != null) {
                str6 = getCallstackForJE(str5, this.mIFADData.controlInfo.JE_cstack_start, this.mIFADData.controlInfo.JE_cstack_maxSize);
                int length3 = str6.length();
                if (length3 > this.mIFADData.controlInfo.callstack_maxSize) {
                    length3 = this.mIFADData.controlInfo.callstack_maxSize;
                }
                this.callstack = str6.substring(0, length3);
            }
            this.component = getComponent(this.reason, str6, this.mIFADData.controlInfo.reason_maxSize, false);
            if (this.mIFADData.controlInfo.enableDetectAll32bitApps && (this.curAppFlag & this.mIFADData.controlInfo.supportflagDetectAll32bitApps) == this.curAppFlag && is32BitApp(str2, null)) {
                this.expType = 30;
                return true;
            }
            if (isContainExpClassName(str3, this.curAppFlag)) {
                this.isParseSuccess = true;
                return true;
            }
            if (isContainExpInfo(str4, this.mIFADData.JE_DetailMsgTB, str6, this.curAppFlag)) {
                this.isParseSuccess = true;
                return true;
            }
            if (isContainExpInfo(str4, this.mIFADData.JE_ClassNameTB, str6, this.curAppFlag)) {
                this.isParseSuccess = true;
                return true;
            }
            if (isContainExpInfo(str6, this.mIFADData.JE_ClassNameTB, str6, this.curAppFlag)) {
                this.isParseSuccess = true;
                return true;
            }
            if (isContainExpInfo(str6, this.mIFADData.JE_DetailMsgTB, str6, this.curAppFlag)) {
                this.isParseSuccess = true;
                return true;
            }
            if (isContainExpInfo(str6, this.mIFADData.JE_CallStackTB, str6, this.curAppFlag)) {
                this.isParseSuccess = true;
                return true;
            }
            IAFD_ENTITY iafd_entity2 = this.curExpEntity;
            if (iafd_entity2 != null) {
                if (iafd_entity2.ruleType == 1 && this.curExpEntity.expID == this.expType) {
                    if (isContainPkgname(str6, this.mIFADData.controlInfo.JE_cstack_start, str)) {
                        this.isParseSuccess = true;
                        return true;
                    }
                } else if (this.curExpEntity.ruleType == 4 && this.curExpEntity.expID == this.expType && is32BitApp(str2, null)) {
                    this.isParseSuccess = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean is32BitApp(String str, String str2) {
        if (str == null) {
            try {
                str = this.mContext.getPackageManager().getApplicationInfo(str2, 0).nativeLibraryDir;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        if (!str.contains("arm64")) {
            if (!str.contains(NativeLibraryHelper.LIB64_DIR_NAME)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRemovableApp(String str, int i, int i2) {
        if (str == null) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, i);
            for (String str2 : this.mIFADData.controlInfo.reMovableAppPaths) {
                if (applicationInfo.nativeLibraryDir.contains(str2)) {
                    if (i2 == 19) {
                        return (applicationInfo.flags & 128) != 0;
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isAvalilableSizeNoEnough() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong() <= 134217728;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isAllFilesAccessOff(int i, String str) {
        try {
            return ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).unsafeCheckOpNoThrow(AppOpsManager.OPSTR_MANAGE_EXTERNAL_STORAGE, i, str) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean parseExpType(String str, String str2, int i, int i2, int i3, String str3, String str4, String str5) {
        try {
            if (parseExpTypeInternal(str, str2, i, i2, i3, str3, str4, str5)) {
                int i4 = this.expType;
                if (i4 != 19) {
                    if (i4 != 27) {
                        if (i4 != 34) {
                            if (i4 == 35) {
                                this.dualUserId = i;
                                return true;
                            }
                            if (parseExpTypeInternalForRepairOnlyShow(str)) {
                                this.expType = 39;
                            }
                            return true;
                        }
                        if ((!this.isCHNModel || getRepairType(34, str) != 0) && isAvalilableSizeNoEnough()) {
                            return true;
                        }
                    } else if (isAllFilesAccessOff(i2, str)) {
                        return true;
                    }
                } else if (isRemovableApp(this.mIFADData.controlInfo.webView_pkgName, 1048576, 19)) {
                    return true;
                }
            } else if (parseExpTypeInternalForRepairOnlyShow(str)) {
                this.expType = 39;
                return true;
            }
        } catch (Exception unused) {
        }
        return false;
    }

    private boolean parseExpTypeInternalForRepairOnlyShow(String str) {
        String[] strArr;
        if (this.mIFADData.controlInfo.isSupportRepair && (strArr = this.mIFADData.controlInfo.gethashMapOfRepairDBInfo(str)) != null) {
            return !strArr[6].equals("Only32bit") || is32BitApp(null, str);
        }
        return false;
    }

    private int getRepairType(int i, String str) {
        PackageInfo packageInfo;
        String valueOf = String.valueOf(i);
        if (!this.mIFADData.controlInfo.isSupportRepair) {
            return 0;
        }
        try {
            packageInfo = this.mContext.getPackageManager().getPackageInfo("com.samsung.android.voc", 16384);
        } catch (Exception unused) {
        }
        if (packageInfo == null || packageInfo.getLongVersionCode() < this.mIFADData.controlInfo.minVocAppVersionCode) {
            return 0;
        }
        if (i != 39) {
            str = valueOf;
        } else if (packageInfo.getLongVersionCode() < this.mIFADData.controlInfo.minVocAppVersionCodeForOnlyShow) {
            return 0;
        }
        if (i == 39) {
            if (packageInfo.getLongVersionCode() < this.mIFADData.controlInfo.minVocAppVersionCodeForOnlyShow) {
                return 0;
            }
        }
        String[] strArr = this.mIFADData.controlInfo.gethashMapOfRepairDBInfo(str);
        if (strArr != null) {
            return strArr[0].equals("Pile") ? 1 : 2;
        }
        return 0;
    }

    public boolean showIAFDCrashDialogs(int i, int i2, String str) {
        String str2;
        Intent intent;
        try {
            int repairType = getRepairType(getExpType(), str);
            if (repairType == 0) {
                intent = new Intent("com.samsung.android.sm.ACTION_START_THIRD_APP_ERROR_DIALOG");
                intent.setPackage(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool"));
                Slog.d(TAG, "Show3rdAppErrorUiExt() startService SM");
            } else {
                String valueOf = String.valueOf(getExpType());
                Intent intent2 = new Intent("com.sec.android.iaft.IAFDService");
                intent2.setClassName("com.sec.android.iaft", "com.sec.android.iaft.IAFDService");
                if (getExpType() == 39) {
                    valueOf = str;
                }
                String[] strArr = this.mIFADData.controlInfo.gethashMapOfRepairDBInfo(valueOf);
                if ("onekey".equals(strArr[1])) {
                    intent2.putExtra("OneKeyRepairMode", 1);
                } else if ("onejump".equals(strArr[1])) {
                    intent2.putExtra("OneKeyRepairMode", 2);
                } else {
                    intent2.putExtra("OneKeyRepairMode", 3);
                }
                if ("0".equals(strArr[2])) {
                    intent2.putExtra("CheckUpdateFlag", true);
                } else {
                    intent2.putExtra("CheckUpdateFlag", false);
                }
                char c = !this.mIFADData.controlInfo.mainLanguage.equals(Locale.getDefault().getLanguage()) ? (char) 5 : (char) 4;
                if (PerfettoProtoLogImpl.NULL_STRING.equals(this.mIFADData.controlInfo.domainRepair)) {
                    str2 = this.mIFADData.controlInfo.prefixRepair + strArr[c] + this.mIFADData.controlInfo.postfixRepair;
                } else {
                    str2 = this.mIFADData.controlInfo.domainRepair + this.mIFADData.controlInfo.prefixRepair + strArr[c] + this.mIFADData.controlInfo.postfixRepair;
                }
                intent2.putExtra("targetUrl", str2);
                intent2.putExtra("repairTrigAPP", strArr[3]);
                intent = intent2;
            }
            intent.putExtra(SmLib_IafdConstant.KEY_PACKAGE_NAME, str);
            intent.putExtra(SmLib_IafdConstant.KEY_USER_ID, i);
            intent.putExtra("type", getExpType());
            intent.putExtra("repeat", true);
            intent.putExtra("component", getComponent());
            intent.putExtra(SmLib_IafdConstant.KEY_ERROR_STACK, getCallstack());
            intent.putExtra("pkgUserId", i2);
            intent.putExtra("repairType", repairType);
            intent.putExtra("dualUserId", this.dualUserId);
            intent.putExtra("isCHNModel", this.isCHNModel);
            if (repairType > 0) {
                PackageManager packageManager = this.mContext.getPackageManager();
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                    intent.putExtra(SmLib_IafdConstant.KEY_VERSION_CODE, packageInfo.getLongVersionCode());
                    intent.putExtra("versionName", packageInfo.versionName);
                    intent.putExtra("appName", packageInfo.applicationInfo.loadLabel(packageManager).toString());
                    intent.putExtra("hasUpdate", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent.putExtra("commandType", 1);
            }
            this.mContext.startService(intent);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public boolean getParseStatus() {
        Slog.d(TAG, "IAFDDiagnosis Parse successful,expType=" + this.expType);
        return this.isParseSuccess;
    }

    public int getExpType() {
        Slog.d(TAG, "getExpType() expType=" + this.expType);
        return this.expType;
    }

    private String getReason() {
        return this.reason;
    }

    private String getComponent() {
        return this.component;
    }

    private String getCallstack() {
        return this.callstack;
    }

    static class IAFD_CONTROLINFO {
        private boolean IAFDDBControlFeature;
        private int JE_cstack_maxSize;
        private String JE_cstack_start;
        private int NE_cHeader_maxSize;
        private int NE_cstack_maxSize;
        private String NE_cstack_start;
        private int callstack_maxSize;
        private int dbVersion;
        private String domainRepair;
        private boolean enable;
        private boolean enableCSCFilter;
        private boolean enableDetectAll32bitApps;
        private boolean enableWhiteList;
        private HashMap<String, String[]> hashMapOfRepairDBInfo;
        private boolean isSupportRepair;
        private String mainLanguage;
        private long minVocAppVersionCode;
        private long minVocAppVersionCodeForOnlyShow;
        private String postfixRepair;
        private String prefixRepair;
        private String[] reMovableAppPaths;
        private int reason_maxSize;
        private String[] supportCSCs;
        private int supportflagDetectAll32bitApps;
        private String webView_pkgName;
        private String[] whiteList;

        IAFD_CONTROLINFO() {
        }

        IAFD_CONTROLINFO(boolean z, int i, String str, int i2, int i3, String str2, int i4, int i5) {
            this.enable = z;
            this.JE_cstack_maxSize = i;
            this.JE_cstack_start = str;
            this.NE_cstack_maxSize = i2;
            this.NE_cHeader_maxSize = i3;
            this.NE_cstack_start = str2;
            this.reason_maxSize = i4;
            this.callstack_maxSize = i5;
        }

        void setEnable(boolean z) {
            this.enable = z;
        }

        void setJE_cstack_maxSize(int i) {
            this.JE_cstack_maxSize = i;
        }

        void setJE_cstack_start(String str) {
            this.JE_cstack_start = str;
        }

        void setNE_cstack_maxSize(int i) {
            this.NE_cstack_maxSize = i;
        }

        void setNE_cHeader_maxSize(int i) {
            this.NE_cHeader_maxSize = i;
        }

        void setNE_cstack_start(String str) {
            this.NE_cstack_start = str;
        }

        void setReason_maxSize(int i) {
            this.reason_maxSize = i;
        }

        void setCallstack_maxSize(int i) {
            this.callstack_maxSize = i;
        }

        void setDBVersion(int i) {
            this.dbVersion = i;
        }

        int getDBVersion() {
            return this.dbVersion;
        }

        void setreMovableAppPaths(String str) {
            if (str.length() > 0) {
                this.reMovableAppPaths = str.split(">,<");
            } else {
                this.reMovableAppPaths = null;
            }
        }

        void setwebView_pkgName(String str) {
            this.webView_pkgName = str;
        }

        void setenableDetectAll32bitApp(Boolean bool, String str) {
            this.enableDetectAll32bitApps = bool.booleanValue();
            this.supportflagDetectAll32bitApps = 1;
            if (str == null || str.length() <= 0) {
                return;
            }
            String[] split = str.split(">,<");
            if (split[0].equals("supportFlag")) {
                this.supportflagDetectAll32bitApps = Integer.parseInt(split[1]);
            }
        }

        void setCSCFilter(String str, String str2, String str3) {
            this.enableCSCFilter = true;
            this.supportCSCs = null;
            if (str != null && str.equals("0")) {
                this.enableCSCFilter = false;
            }
            if (str2 != null && str2.length() > 0) {
                this.supportCSCs = str2.split(">,<");
            }
            if (this.enableCSCFilter) {
                String[] strArr = this.supportCSCs;
                if (strArr != null && str3 != null) {
                    int length = strArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        if (!strArr[i].equals(str3)) {
                            i++;
                        } else if (this.enable) {
                            this.enable = true;
                            return;
                        }
                    }
                }
                this.enable = false;
            }
        }

        void setIAFDDBControlFeature(String str, String str2, boolean z) {
            this.IAFDDBControlFeature = false;
            if (str != null && str.equals("1")) {
                this.IAFDDBControlFeature = true;
            }
            if (this.IAFDDBControlFeature && str2 != null && str2.length() > 0) {
                String[] split = str2.split(">,<");
                for (int i = 0; i < split.length; i += 2) {
                    if ("Repair".equals(split[i])) {
                        int i2 = i + 1;
                        if ("CHNONLY".equals(split[i2]) && z) {
                            setSupportRepair(true);
                        }
                        if ("ALL".equals(split[i2])) {
                            setSupportRepair(true);
                        }
                    }
                }
            }
        }

        void setWhiteList(String str, String str2) {
            this.enableWhiteList = true;
            this.whiteList = null;
            if (str != null && str.equals("0")) {
                this.enableWhiteList = false;
            }
            if (str2 == null || str2.length() <= 0) {
                return;
            }
            this.whiteList = str2.split(">,<");
        }

        boolean isInWhiteList(String str) {
            if (str.equals("com.sec.android.iaft")) {
                return true;
            }
            if (!this.enableWhiteList) {
                return false;
            }
            for (String str2 : this.whiteList) {
                if (str.startsWith(str2)) {
                    return true;
                }
            }
            return false;
        }

        void setSupportRepair(boolean z) {
            this.isSupportRepair = z;
        }

        void inithashMapValues(HashMap<String, String[]> hashMap, String str, String str2, String str3, String str4) {
            String[] split = str.split(str4);
            String[] strArr = {"0", "0", "0", "vocApp", str2, str3, "0"};
            for (String str5 : split) {
                if (str5.equals("Pile")) {
                    strArr[0] = str5;
                } else if (str5.equals("onekey")) {
                    strArr[1] = str5;
                } else if (str5.equals("onejump")) {
                    strArr[1] = str5;
                } else if (str5.equals("NoCheckUpdate")) {
                    strArr[2] = str5;
                } else if (str5.equals("SmartMApp")) {
                    strArr[3] = str5;
                } else if (str5.equals("IAFDSelf")) {
                    strArr[3] = str5;
                } else if (str5.equals("Only32bit")) {
                    strArr[6] = str5;
                }
            }
            hashMap.putIfAbsent(split[0], strArr);
        }

        void sethashMapOfLinkForVocApp(String str) {
            if (str == null || str.length() <= 0) {
                return;
            }
            String[] split = str.split(">,<");
            HashMap<String, String[]> hashMap = new HashMap<>();
            if (split[0].equals("pairlinks")) {
                this.minVocAppVersionCode = Long.valueOf(split[1]).longValue();
                this.domainRepair = split[2];
                this.prefixRepair = split[3];
                this.postfixRepair = split[4];
                this.mainLanguage = split[5];
                for (int i = 6; i < split.length; i += 3) {
                    inithashMapValues(hashMap, split[i], split[i + 1], split[i + 2], Session.SESSION_SEPARATION_CHAR_CHILD);
                }
            }
            this.hashMapOfRepairDBInfo = hashMap;
        }

        void sethashMapOfLinkForVocAppOnlyShow(String str) {
            if (str == null || str.length() <= 0) {
                return;
            }
            String[] split = str.split(">,<");
            if (split[0].equals("OnlyShowList")) {
                this.minVocAppVersionCodeForOnlyShow = Long.valueOf(split[1]).longValue();
                for (int i = 2; i < split.length; i += 3) {
                    inithashMapValues(this.hashMapOfRepairDBInfo, split[i], split[i + 1], split[i + 2], ":;");
                }
            }
        }

        String[] gethashMapOfRepairDBInfo(String str) {
            if (this.hashMapOfRepairDBInfo.containsKey(str)) {
                return this.hashMapOfRepairDBInfo.get(str);
            }
            return null;
        }
    }

    static class IAFD_ENTITY {
        private boolean enable;
        private int expID;
        private String keyWord;
        private int ruleType;
        private String[] rules;
        private String suggestion;
        private int supportFlag;
        private int tbID;

        private void initENTITY(int i, int i2, boolean z, String str, String str2, String str3) {
            this.tbID = i;
            this.expID = i2;
            this.enable = z;
            this.keyWord = str;
            this.ruleType = 0;
            this.rules = null;
            if (str2 != null && str2.length() > 0) {
                String[] split = str2.split(">,<");
                this.rules = split;
                if (SmLib_IafdConstant.KEY_PACKAGE_NAME.equals(split[0])) {
                    this.ruleType = 1;
                } else if ("libs".equals(split[0])) {
                    this.ruleType = 2;
                } else if ("32bit".equals(split[0])) {
                    this.ruleType = 4;
                }
            }
            this.suggestion = null;
            this.supportFlag = 1;
            if (str3 == null || str3.length() <= 0) {
                return;
            }
            this.suggestion = str3;
            String[] split2 = str3.split(">,<");
            if (split2[0].equals("supportFlag")) {
                this.supportFlag = Integer.parseInt(split2[1]);
            }
        }

        IAFD_ENTITY(int i, int i2, Boolean bool, String str, String str2, String str3) {
            initENTITY(i, i2, bool.booleanValue(), str, str2, str3);
        }

        IAFD_ENTITY(int i, int i2, Boolean bool, String str, String str2, String str3, int i3, HashMap<String, Integer> hashMap) {
            initENTITY(i, i2, bool.booleanValue(), str, str2, str3);
            if (bool.booleanValue()) {
                hashMap.putIfAbsent(str, Integer.valueOf(i3));
            }
        }
    }

    static class IAFD_DATA {
        IAFD_ENTITY[] JE_CallStackTB;
        IAFD_ENTITY[] JE_ClassNameTB;
        IAFD_ENTITY[] JE_DetailMsgTB;
        IAFD_ENTITY[] NE_CallStackTB;
        IAFD_ENTITY[] NE_HeaderInfoTB;
        IAFD_CONTROLINFO controlInfo;
        HashMap<String, Integer> hashMapJE_ClassNameTB;

        IAFD_DATA() {
        }
    }
}
