package com.sec.android.iaft;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.p009os.Environment;
import android.p009os.SemSystemProperties;
import android.p009os.StatFs;
import android.telecom.Logging.Session;
import android.util.Slog;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.ims.options.SemCapabilities;
import com.samsung.android.p043sm.iafdlib.IafdConstant;
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

    private IAFDDiagnosisHolder() {}
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
    this.isCHNModel =
        "com.samsung.android.sm_cn"
            .equals(
                SemFloatingFeature.getInstance()
                    .getString(
                        "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME",
                        "com.samsung.android.lool"));
    initData(false);
  }

  private void initData(boolean isWait) {
    try {
      IAFDDBManager.getInstance().init(this.mContext, this.mSalesCode, this.isCHNModel);
      this.mIFADData = IAFDDBManager.getInstance().getData();
      if (isWait) {
        int maxCnt = 10;
        while (this.mIFADData == null && maxCnt > 0) {
          Slog.m113d(TAG, "initData wait...");
          Thread.sleep(20);
          maxCnt--;
          this.mIFADData = IAFDDBManager.getInstance().getData();
        }
      }
    } catch (Exception e) {
      Slog.m113d("==IAFD==", "initData fail, callstack as the following:");
      e.printStackTrace();
    }
  }

  private boolean isNativeCrash(String exceptionClassName) {
    return exceptionClassName.contains("Native crash");
  }

  private boolean isContainExpClassName(String srcStr, int curflag) {
    if (srcStr == null) {
      return false;
    }
    int i = srcStr.lastIndexOf(46) + 1;
    if (i < 0) {
      i = 0;
    }
    String sub = srcStr.substring(i);
    if (sub == null || !this.mIFADData.hashMapJE_ClassNameTB.containsKey(sub)) {
      return false;
    }
    int index = this.mIFADData.hashMapJE_ClassNameTB.get(sub).intValue();
    if (curflag != (this.mIFADData.JE_ClassNameTB[index].supportFlag & curflag)) {
      return false;
    }
    this.expType = this.mIFADData.JE_ClassNameTB[index].expID;
    this.curExpEntity = this.mIFADData.JE_ClassNameTB[index];
    return this.mIFADData.JE_ClassNameTB[index].ruleType <= 0;
  }

  private boolean isContainExpInfo(String srcStr, IAFD_ENTITY[] tbs, String csStr, int curflag) {
    if (srcStr == null) {
      return false;
    }
    for (IAFD_ENTITY i : tbs) {
      if (i.enable && (i.supportFlag & curflag) == curflag && srcStr.contains(i.keyWord)) {
        if (i.ruleType == 2) {
          if (csStr == null) {
            return false;
          }
          int cnt = i.rules.length;
          for (int j = 1; j < cnt; j++) {
            if (csStr.contains(i.rules[j])) {
              this.expType = i.expID;
              return true;
            }
          }
          return false;
        }
        int cnt2 = i.expID;
        this.expType = cnt2;
        this.curExpEntity = i;
        return (i.ruleType == 1 || i.ruleType == 4) ? false : true;
      }
    }
    return false;
  }

  private String getSubStringForNE(String srcStr, int limitLen, boolean isHeaderInfo) {
    if (isHeaderInfo) {
      boolean is64Bit = srcStr.charAt(srcStr.indexOf("ABI:") + 9) == '6';
      int istart = srcStr.indexOf("pid:");
      int iend = is64Bit ? srcStr.indexOf(" x0 ") : srcStr.indexOf(" r0 ");
      if (iend <= istart && (iend = srcStr.indexOf("backtrace:")) <= istart) {
        return null;
      }
      int imax = istart + limitLen;
      int imax2 = imax <= srcStr.length() ? imax : srcStr.length();
      return srcStr.substring(istart, iend <= imax2 ? iend : imax2);
    }
    int istart2 = srcStr.indexOf("backtrace:");
    int istart3 = istart2 < 0 ? 0 : istart2 + 11;
    int imax3 = istart3 + limitLen;
    return srcStr.substring(istart3, imax3 <= srcStr.length() ? imax3 : srcStr.length());
  }

  private String getCauseForNE(String srcStr, int limitLen) {
    int prefixLen = 6;
    int istart = srcStr.indexOf("Cause:");
    if (istart < 0) {
      istart = srcStr.indexOf("Abort message:");
      prefixLen = 14;
      if (istart < 0) {
        return null;
      }
    }
    int iend = istart + limitLen;
    String strtmp = srcStr.substring(istart, iend <= srcStr.length() ? iend : srcStr.length());
    if (strtmp == null) {
      return null;
    }
    int istart2 = prefixLen;
    int iend2 = istart2 + limitLen;
    int iend3 = iend2 <= srcStr.length() ? iend2 : srcStr.length();
    int j = strtmp.indexOf("\n");
    if (j > istart2) {
      iend3 = iend3 <= j ? iend3 : j;
    }
    return strtmp.substring(istart2, iend3);
  }

  private String getComponent(
      String reason, String callstack, int limitLen, boolean isNativeCrash) {
    int istart;
    int iend;
    int istart2;
    if (reason != null && (istart2 = reason.indexOf("com.")) >= 0) {
      return reason.substring(istart2, reason.length());
    }
    if (callstack != null) {
      if (isNativeCrash) {
        int ifrom = callstack.indexOf("/data/app/");
        if (ifrom < 0) {
          ifrom = callstack.indexOf("/app/");
        }
        istart = callstack.indexOf("com.", ifrom);
        if (istart < 0) {
          istart = callstack.indexOf("com.");
        }
        if (istart < 0 && ifrom >= 0) {
          istart = ifrom;
        }
      } else {
        istart = callstack.indexOf("com.");
      }
      if (istart >= 0) {
        int iend2 = callstack.indexOf("\n", istart);
        if (iend2 > 0) {
          iend = iend2 <= istart + limitLen ? iend2 : istart + limitLen;
        } else {
          int iend3 = istart + limitLen;
          iend = iend3 <= callstack.length() ? iend3 : callstack.length();
        }
        return callstack.substring(istart, iend);
      }
    }
    return reason;
  }

  private int findStringFromRtoL(String orgStr, String desStr, int rIndex, int limitlen) {
    int deslen = desStr.length();
    int icmpEnd = deslen - 1;
    int iorgend = orgStr.length() - limitlen;
    if (iorgend < 0) {
      iorgend = 0;
    }
    for (int ileft = (rIndex + 1) - deslen; ileft >= iorgend; ileft--) {
      int i = ileft;
      for (int j = 0; j < deslen && orgStr.charAt(i) == desStr.charAt(j); j++) {
        if (j != icmpEnd) {
          i++;
        } else {
          return ileft;
        }
      }
    }
    return -1;
  }

  private String getCallstackForJE(String srcStr, String startStr, int limitLen) {
    int maxlen = limitLen << 4;
    String outstring = "";
    int srclen = srcStr.length();
    if (srclen <= limitLen) {
      return srcStr;
    }
    int iend = srclen - 1;
    int icnt = 0;
    int curLimitlen = limitLen;
    while (icnt < 2) {
      icnt++;
      int istart = findStringFromRtoL(srcStr, "Caused by:", iend, maxlen);
      if (istart < 0) {
        break;
      }
      int curlen = iend - istart < curLimitlen ? iend - istart : curLimitlen;
      outstring = outstring + srcStr.substring(istart, istart + curlen);
      curLimitlen -= curlen;
      iend = istart;
    }
    if (curLimitlen > 0) {
      return outstring + srcStr.substring(0, curLimitlen);
    }
    return outstring;
  }

  private boolean isContainPkgname(String srcStr, String startStr, String pkgname) {
    String tmpStr;
    int istart = srcStr.indexOf(startStr);
    if (istart < 0
        || (tmpStr = srcStr.substring(istart, srcStr.length())) == null
        || !tmpStr.contains(pkgname)) {
      return false;
    }
    return true;
  }

  private boolean parseExpTypeInternal(
      String packageName,
      String nativeLibraryDir,
      int puserId,
      int appuid,
      int flags,
      String exceptionClassName,
      String exceptionMessage,
      String stackTrace) {
    IAFD_DATA iafd_data;
    this.dualUserId = 0;
    this.isParseSuccess = false;
    if (stackTrace == null) {
      return false;
    }
    try {
      this.curAppFlag = 1;
      if (packageName != null
          && ((flags & 1) != 0
              || (flags & 128) != 0
              || packageName.contains("com.samsung.")
              || packageName.contains("com.sec."))) {
        this.curAppFlag = 2;
      }
      initData(true);
      this.expType = -1;
      this.curExpEntity = null;
      iafd_data = this.mIFADData;
    } catch (Exception e) {
      Slog.m113d(TAG, "parseExpType fail, skip, callstack as the following:");
      e.printStackTrace();
    }
    if (iafd_data == null
        || !iafd_data.controlInfo.enable
        || this.mIFADData.controlInfo.isInWhiteList(packageName)) {
      return false;
    }
    Slog.m113d(TAG, "parseExpType start");
    this.reason = null;
    this.callstack = null;
    this.component = null;
    if (isNativeCrash(exceptionClassName)) {
      int max = this.mIFADData.controlInfo.NE_cstack_maxSize;
      String backTraceStr = getSubStringForNE(stackTrace, max, false);
      String causeHeaderStr = getSubStringForNE(stackTrace, max, true);
      if (causeHeaderStr != null) {
        this.reason = getCauseForNE(causeHeaderStr, this.mIFADData.controlInfo.reason_maxSize);
      }
      if (backTraceStr != null) {
        int curLen = backTraceStr.length();
        if (curLen > this.mIFADData.controlInfo.callstack_maxSize) {
          curLen = this.mIFADData.controlInfo.callstack_maxSize;
        }
        this.callstack = backTraceStr.substring(0, curLen);
      }
      this.component =
          getComponent(this.reason, backTraceStr, this.mIFADData.controlInfo.reason_maxSize, true);
      if (this.mIFADData.controlInfo.enableDetectAll32bitApps
          && (this.curAppFlag & this.mIFADData.controlInfo.supportflagDetectAll32bitApps)
              == this.curAppFlag
          && is32BitApp(nativeLibraryDir, null)) {
        this.expType = 30;
        return true;
      }
      if (backTraceStr != null) {
        if (!isContainExpInfo(
            backTraceStr, this.mIFADData.NE_CallStackTB, backTraceStr, this.curAppFlag)) {
          if (isContainExpInfo(
              backTraceStr, this.mIFADData.NE_HeaderInfoTB, backTraceStr, this.curAppFlag)) {
            this.isParseSuccess = true;
            return true;
          }
        } else {
          this.isParseSuccess = true;
          return true;
        }
      }
      if (causeHeaderStr != null
          && isContainExpInfo(
              causeHeaderStr, this.mIFADData.NE_HeaderInfoTB, backTraceStr, this.curAppFlag)) {
        this.isParseSuccess = true;
        return true;
      }
      IAFD_ENTITY iafd_entity = this.curExpEntity;
      if (iafd_entity != null) {
        if (iafd_entity.ruleType == 1 && this.curExpEntity.expID == this.expType) {
          if (isContainPkgname(
              backTraceStr, this.mIFADData.controlInfo.NE_cstack_start, packageName)) {
            this.isParseSuccess = true;
            return true;
          }
        } else if (this.curExpEntity.ruleType == 4
            && this.curExpEntity.expID == this.expType
            && is32BitApp(nativeLibraryDir, null)) {
          this.isParseSuccess = true;
          return true;
        }
      }
    } else {
      String subCsStr = "";
      if (exceptionMessage != null) {
        int curLen2 = exceptionMessage.length();
        if (curLen2 > this.mIFADData.controlInfo.reason_maxSize) {
          curLen2 = this.mIFADData.controlInfo.reason_maxSize;
        }
        this.reason = exceptionMessage.substring(0, curLen2);
      }
      if (stackTrace != null) {
        subCsStr =
            getCallstackForJE(
                stackTrace,
                this.mIFADData.controlInfo.JE_cstack_start,
                this.mIFADData.controlInfo.JE_cstack_maxSize);
        int curLen3 = subCsStr.length();
        this.callstack =
            subCsStr.substring(
                0,
                curLen3 > this.mIFADData.controlInfo.callstack_maxSize
                    ? this.mIFADData.controlInfo.callstack_maxSize
                    : curLen3);
      }
      this.component =
          getComponent(this.reason, subCsStr, this.mIFADData.controlInfo.reason_maxSize, false);
      if (!this.mIFADData.controlInfo.enableDetectAll32bitApps
          || (this.curAppFlag & this.mIFADData.controlInfo.supportflagDetectAll32bitApps)
              != this.curAppFlag
          || !is32BitApp(nativeLibraryDir, null)) {
        if (!isContainExpClassName(exceptionClassName, this.curAppFlag)) {
          if (!isContainExpInfo(
              exceptionMessage, this.mIFADData.JE_DetailMsgTB, subCsStr, this.curAppFlag)) {
            if (!isContainExpInfo(
                exceptionMessage, this.mIFADData.JE_ClassNameTB, subCsStr, this.curAppFlag)) {
              if (!isContainExpInfo(
                  subCsStr, this.mIFADData.JE_ClassNameTB, subCsStr, this.curAppFlag)) {
                if (!isContainExpInfo(
                    subCsStr, this.mIFADData.JE_DetailMsgTB, subCsStr, this.curAppFlag)) {
                  if (isContainExpInfo(
                      subCsStr, this.mIFADData.JE_CallStackTB, subCsStr, this.curAppFlag)) {
                    this.isParseSuccess = true;
                    return true;
                  }
                  IAFD_ENTITY iafd_entity2 = this.curExpEntity;
                  if (iafd_entity2 != null) {
                    if (iafd_entity2.ruleType == 1 && this.curExpEntity.expID == this.expType) {
                      if (isContainPkgname(
                          subCsStr, this.mIFADData.controlInfo.JE_cstack_start, packageName)) {
                        this.isParseSuccess = true;
                        return true;
                      }
                    } else if (this.curExpEntity.ruleType == 4
                        && this.curExpEntity.expID == this.expType
                        && is32BitApp(nativeLibraryDir, null)) {
                      this.isParseSuccess = true;
                      return true;
                    }
                  }
                } else {
                  this.isParseSuccess = true;
                  return true;
                }
              } else {
                this.isParseSuccess = true;
                return true;
              }
            } else {
              this.isParseSuccess = true;
              return true;
            }
          } else {
            this.isParseSuccess = true;
            return true;
          }
        } else {
          this.isParseSuccess = true;
          return true;
        }
      } else {
        this.expType = 30;
        return true;
      }
    }
    return false;
  }

  private boolean is32BitApp(String nativeLibraryDir, String pkgname) {
    String nLibDir = nativeLibraryDir;
    if (nLibDir == null) {
      try {
        PackageManager pm = this.mContext.getPackageManager();
        ApplicationInfo ai = pm.getApplicationInfo(pkgname, 0);
        nLibDir = ai.nativeLibraryDir;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    if (!nLibDir.contains("arm64")) {
      if (!nLibDir.contains(NativeLibraryHelper.LIB64_DIR_NAME)) {
        return true;
      }
    }
    return false;
  }

  private boolean isRemovableApp(String pkgname, int flag, int errType) {
    if (pkgname == null) {
      return false;
    }
    try {
      PackageManager pm = this.mContext.getPackageManager();
      ApplicationInfo appInfo = pm.getApplicationInfo(pkgname, flag);
      for (String path : this.mIFADData.controlInfo.reMovableAppPaths) {
        if (appInfo.nativeLibraryDir.contains(path)) {
          if (errType == 19) {
            if ((appInfo.flags & 128) == 0) {
              return false;
            }
            return true;
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
      StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
      long blockSize = stat.getBlockSizeLong();
      long avalilableSize = stat.getAvailableBlocksLong() * blockSize;
      if (avalilableSize <= 134217728) {
        return true;
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private boolean isAllFilesAccessOff(int appuid, String packageName) {
    try {
      AppOpsManager aps = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
      int appOpMode =
          aps.unsafeCheckOpNoThrow(
              AppOpsManager.OPSTR_MANAGE_EXTERNAL_STORAGE, appuid, packageName);
      return appOpMode != 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean parseExpType(
      String packageName,
      String nativeLibraryDir,
      int puserId,
      int appuid,
      int flags,
      String exceptionClassName,
      String exceptionMessage,
      String stackTrace) {
    try {
      if (parseExpTypeInternal(
          packageName,
          nativeLibraryDir,
          puserId,
          appuid,
          flags,
          exceptionClassName,
          exceptionMessage,
          stackTrace)) {
        switch (this.expType) {
          case 19:
            if (isRemovableApp(this.mIFADData.controlInfo.webView_pkgName, 1048576, 19)) {
              return true;
            }
            break;
          case 27:
            if (isAllFilesAccessOff(appuid, packageName)) {
              return true;
            }
            break;
          case 34:
            if ((!this.isCHNModel || getRepairType(34, packageName) != 0)
                && isAvalilableSizeNoEnough()) {
              return true;
            }
            break;
          case 35:
            this.dualUserId = puserId;
            return true;
          default:
            if (parseExpTypeInternalForRepairOnlyShow(packageName)) {
              this.expType = 39;
            }
            return true;
        }
      } else if (parseExpTypeInternalForRepairOnlyShow(packageName)) {
        this.expType = 39;
        return true;
      }
    } catch (Exception e) {
    }
    return false;
  }

  private boolean parseExpTypeInternalForRepairOnlyShow(String packageName) {
    String[] keyStr;
    if (this.mIFADData.controlInfo.isSupportRepair
        && (keyStr = this.mIFADData.controlInfo.gethashMapOfRepairDBInfo(packageName)) != null) {
      return !keyStr[6].equals("Only32bit") || is32BitApp(null, packageName);
    }
    return false;
  }

  private int getRepairType(int errtype, String pkgName) {
    String strkey = String.valueOf(errtype);
    if (!this.mIFADData.controlInfo.isSupportRepair) {
      return 0;
    }
    PackageManager pm = this.mContext.getPackageManager();
    try {
      PackageInfo pmInfo = pm.getPackageInfo("com.samsung.android.voc", 16384);
      if (pmInfo == null
          || pmInfo.getLongVersionCode() < this.mIFADData.controlInfo.minVocAppVersionCode) {
        return 0;
      }
      if (errtype == 39) {
        if (pmInfo.getLongVersionCode()
            < this.mIFADData.controlInfo.minVocAppVersionCodeForOnlyShow) {
          return 0;
        }
        strkey = pkgName;
      }
      if (errtype == 39) {
        if (pmInfo.getLongVersionCode()
            < this.mIFADData.controlInfo.minVocAppVersionCodeForOnlyShow) {
          return 0;
        }
      }
      String[] keyStr = this.mIFADData.controlInfo.gethashMapOfRepairDBInfo(strkey);
      if (keyStr == null) {
        return 0;
      }
      if (keyStr[0].equals("Pile")) {
        return 1;
      }
      return 2;
    } catch (Exception e) {
      return 0;
    }
  }

  public boolean showIAFDCrashDialogs(int puserId, int appuid, String packageName) {
    Intent intent;
    String targetUrlTmp;
    try {
      int repairType = getRepairType(getExpType(), packageName);
      if (repairType == 0) {
        intent = new Intent("com.samsung.android.sm.ACTION_START_THIRD_APP_ERROR_DIALOG");
        intent.setPackage(
            SemFloatingFeature.getInstance()
                .getString(
                    "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME",
                    "com.samsung.android.lool"));
        Slog.m113d(TAG, "Show3rdAppErrorUiExt() startService SM");
      } else {
        String strkey = String.valueOf(getExpType());
        intent = new Intent("com.sec.android.iaft.IAFDService");
        intent.setClassName("com.sec.android.iaft", "com.sec.android.iaft.IAFDService");
        if (getExpType() == 39) {
          strkey = packageName;
        }
        String[] values = this.mIFADData.controlInfo.gethashMapOfRepairDBInfo(strkey);
        if ("onekey".equals(values[1])) {
          intent.putExtra("OneKeyRepairMode", 1);
        } else if ("onejump".equals(values[1])) {
          intent.putExtra("OneKeyRepairMode", 2);
        } else {
          intent.putExtra("OneKeyRepairMode", 3);
        }
        if ("0".equals(values[2])) {
          intent.putExtra("CheckUpdateFlag", true);
        } else {
          intent.putExtra("CheckUpdateFlag", false);
        }
        int lanIndex = 4;
        String lan = Locale.getDefault().getLanguage();
        if (!this.mIFADData.controlInfo.mainLanguage.equals(lan)) {
          lanIndex = 5;
        }
        if (SemCapabilities.FEATURE_TAG_NULL.equals(this.mIFADData.controlInfo.domainRepair)) {
          targetUrlTmp =
              this.mIFADData.controlInfo.prefixRepair
                  + values[lanIndex]
                  + this.mIFADData.controlInfo.postfixRepair;
        } else {
          targetUrlTmp =
              this.mIFADData.controlInfo.domainRepair
                  + this.mIFADData.controlInfo.prefixRepair
                  + values[lanIndex]
                  + this.mIFADData.controlInfo.postfixRepair;
        }
        intent.putExtra("targetUrl", targetUrlTmp);
        intent.putExtra("repairTrigAPP", values[3]);
      }
      intent.putExtra(IafdConstant.KEY_PACKAGE_NAME, packageName);
      intent.putExtra(IafdConstant.KEY_USER_ID, puserId);
      intent.putExtra("type", getExpType());
      intent.putExtra("repeat", true);
      intent.putExtra("component", getComponent());
      intent.putExtra(IafdConstant.KEY_ERROR_STACK, getCallstack());
      intent.putExtra("pkgUserId", appuid);
      intent.putExtra("repairType", repairType);
      intent.putExtra("dualUserId", this.dualUserId);
      intent.putExtra("isCHNModel", this.isCHNModel);
      if (repairType > 0) {
        PackageManager pm = this.mContext.getPackageManager();
        try {
          PackageInfo pmInfo = pm.getPackageInfo(packageName, 0);
          intent.putExtra(IafdConstant.KEY_VERSION_CODE, pmInfo.getLongVersionCode());
          intent.putExtra("versionName", pmInfo.versionName);
          intent.putExtra("appName", pmInfo.applicationInfo.loadLabel(pm).toString());
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
    Slog.m113d(TAG, "IAFDDiagnosis Parse successful,expType=" + this.expType);
    return this.isParseSuccess;
  }

  public int getExpType() {
    Slog.m113d(TAG, "getExpType() expType=" + this.expType);
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

    IAFD_CONTROLINFO() {}

    IAFD_CONTROLINFO(
        boolean enable,
        int jcsms,
        String jcss,
        int ncsms,
        int nchms,
        String ncss,
        int rsms,
        int csms) {
      this.enable = enable;
      this.JE_cstack_maxSize = jcsms;
      this.JE_cstack_start = jcss;
      this.NE_cstack_maxSize = ncsms;
      this.NE_cHeader_maxSize = nchms;
      this.NE_cstack_start = ncss;
      this.reason_maxSize = rsms;
      this.callstack_maxSize = csms;
    }

    void setEnable(boolean enable) {
      this.enable = enable;
    }

    void setJE_cstack_maxSize(int jcsms) {
      this.JE_cstack_maxSize = jcsms;
    }

    void setJE_cstack_start(String jcss) {
      this.JE_cstack_start = jcss;
    }

    void setNE_cstack_maxSize(int ncsms) {
      this.NE_cstack_maxSize = ncsms;
    }

    void setNE_cHeader_maxSize(int nchms) {
      this.NE_cHeader_maxSize = nchms;
    }

    void setNE_cstack_start(String ncss) {
      this.NE_cstack_start = ncss;
    }

    void setReason_maxSize(int rsms) {
      this.reason_maxSize = rsms;
    }

    void setCallstack_maxSize(int csms) {
      this.callstack_maxSize = csms;
    }

    void setDBVersion(int dbVersion) {
      this.dbVersion = dbVersion;
    }

    int getDBVersion() {
      return this.dbVersion;
    }

    void setreMovableAppPaths(String rule) {
      if (rule.length() > 0) {
        this.reMovableAppPaths = rule.split(">,<");
      } else {
        this.reMovableAppPaths = null;
      }
    }

    void setwebView_pkgName(String pkgName) {
      this.webView_pkgName = pkgName;
    }

    void setenableDetectAll32bitApp(Boolean enable, String suggestion) {
      this.enableDetectAll32bitApps = enable.booleanValue();
      this.supportflagDetectAll32bitApps = 1;
      if (suggestion != null && suggestion.length() > 0) {
        String[] strArray = suggestion.split(">,<");
        if (strArray[0].equals("supportFlag")) {
          this.supportflagDetectAll32bitApps = Integer.parseInt(strArray[1]);
        }
      }
    }

    void setCSCFilter(String rule, String suggestion, String salesCode) {
      this.enableCSCFilter = true;
      this.supportCSCs = null;
      if (rule != null && rule.equals("0")) {
        this.enableCSCFilter = false;
      }
      if (suggestion != null && suggestion.length() > 0) {
        this.supportCSCs = suggestion.split(">,<");
      }
      if (this.enableCSCFilter) {
        boolean enableTmp = false;
        String[] strArr = this.supportCSCs;
        if (strArr != null && salesCode != null) {
          int length = strArr.length;
          int i = 0;
          while (true) {
            if (i >= length) {
              break;
            }
            String csc = strArr[i];
            if (!csc.equals(salesCode)) {
              i++;
            } else {
              enableTmp = true;
              break;
            }
          }
        }
        if (enableTmp && this.enable) {
          this.enable = true;
        } else {
          this.enable = false;
        }
      }
    }

    void setIAFDDBControlFeature(String rule, String suggestion, boolean isCHN) {
      this.IAFDDBControlFeature = false;
      if (rule != null && rule.equals("1")) {
        this.IAFDDBControlFeature = true;
      }
      if (this.IAFDDBControlFeature && suggestion != null && suggestion.length() > 0) {
        String[] strArray = suggestion.split(">,<");
        for (int i = 0; i < strArray.length; i += 2) {
          if ("Repair".equals(strArray[i])) {
            if ("CHNONLY".equals(strArray[i + 1]) && isCHN) {
              setSupportRepair(true);
            }
            if (SemInputDeviceManager.MOTION_CONTROL_TYPE_ALL.equals(strArray[i + 1])) {
              setSupportRepair(true);
            }
          }
        }
      }
    }

    void setWhiteList(String rule, String suggestion) {
      this.enableWhiteList = true;
      this.whiteList = null;
      if (rule != null && rule.equals("0")) {
        this.enableWhiteList = false;
      }
      if (suggestion != null && suggestion.length() > 0) {
        this.whiteList = suggestion.split(">,<");
      }
    }

    boolean isInWhiteList(String pkgName) {
      if (pkgName.equals("com.sec.android.iaft")) {
        return true;
      }
      if (!this.enableWhiteList) {
        return false;
      }
      for (String item : this.whiteList) {
        if (pkgName.startsWith(item)) {
          return true;
        }
      }
      return false;
    }

    void setSupportRepair(boolean isSupport) {
      this.isSupportRepair = false;
    }

    void inithashMapValues(
        HashMap<String, String[]> linkHMap, String orgStr, String a1, String a2, String splitStr) {
      String[] strTmp = orgStr.split(splitStr);
      String[] values = {"0", "0", "0", "vocApp", a1, a2, "0"};
      for (String str : strTmp) {
        if (str.equals("Pile")) {
          values[0] = str;
        } else if (str.equals("onekey")) {
          values[1] = str;
        } else if (str.equals("onejump")) {
          values[1] = str;
        } else if (str.equals("NoCheckUpdate")) {
          values[2] = str;
        } else if (str.equals("SmartMApp")) {
          values[3] = str;
        } else if (str.equals("IAFDSelf")) {
          values[3] = str;
        } else if (str.equals("Only32bit")) {
          values[6] = str;
        }
      }
      linkHMap.putIfAbsent(strTmp[0], values);
    }

    void sethashMapOfLinkForVocApp(String rule) {
      if (rule != null && rule.length() > 0) {
        String[] strArray = rule.split(">,<");
        HashMap<String, String[]> linkHMap = new HashMap<>();
        if (strArray[0].equals("pairlinks")) {
          this.minVocAppVersionCode = Long.valueOf(strArray[1]).longValue();
          this.domainRepair = strArray[2];
          this.prefixRepair = strArray[3];
          this.postfixRepair = strArray[4];
          this.mainLanguage = strArray[5];
          for (int i = 6; i < strArray.length; i += 3) {
            inithashMapValues(
                linkHMap,
                strArray[i],
                strArray[i + 1],
                strArray[i + 2],
                Session.SESSION_SEPARATION_CHAR_CHILD);
          }
        }
        this.hashMapOfRepairDBInfo = linkHMap;
      }
    }

    void sethashMapOfLinkForVocAppOnlyShow(String rule) {
      if (rule != null && rule.length() > 0) {
        String[] strArray = rule.split(">,<");
        if (strArray[0].equals("OnlyShowList")) {
          this.minVocAppVersionCodeForOnlyShow = Long.valueOf(strArray[1]).longValue();
          for (int i = 2; i < strArray.length; i += 3) {
            inithashMapValues(
                this.hashMapOfRepairDBInfo, strArray[i], strArray[i + 1], strArray[i + 2], ":;");
          }
        }
      }
    }

    String[] gethashMapOfRepairDBInfo(String type) {
      if (this.hashMapOfRepairDBInfo.containsKey(type)) {
        return this.hashMapOfRepairDBInfo.get(type);
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

    private void initENTITY(
        int tbID, int expID, boolean enable, String keyWord, String rule, String suggestion) {
      this.tbID = tbID;
      this.expID = expID;
      this.enable = enable;
      this.keyWord = keyWord;
      this.ruleType = 0;
      this.rules = null;
      if (rule != null && rule.length() > 0) {
        String[] strArray = rule.split(">,<");
        this.rules = strArray;
        if (IafdConstant.KEY_PACKAGE_NAME.equals(strArray[0])) {
          this.ruleType = 1;
        } else if ("libs".equals(strArray[0])) {
          this.ruleType = 2;
        } else if ("32bit".equals(strArray[0])) {
          this.ruleType = 4;
        }
      }
      this.suggestion = null;
      this.supportFlag = 1;
      if (suggestion != null && suggestion.length() > 0) {
        this.suggestion = suggestion;
        String[] strArray2 = suggestion.split(">,<");
        if (strArray2[0].equals("supportFlag")) {
          this.supportFlag = Integer.parseInt(strArray2[1]);
        }
      }
    }

    IAFD_ENTITY(
        int tbID, int expID, Boolean enable, String keyWord, String rule, String suggestion) {
      initENTITY(tbID, expID, enable.booleanValue(), keyWord, rule, suggestion);
    }

    IAFD_ENTITY(
        int tbID,
        int expID,
        Boolean enable,
        String keyWord,
        String rule,
        String suggestion,
        int index,
        HashMap<String, Integer> hmap) {
      initENTITY(tbID, expID, enable.booleanValue(), keyWord, rule, suggestion);
      if (enable.booleanValue()) {
        hmap.putIfAbsent(keyWord, Integer.valueOf(index));
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

    IAFD_DATA() {}
  }
}
