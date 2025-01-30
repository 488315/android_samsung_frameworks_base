package com.android.server.am;

import android.os.UserHandle;
import android.util.Log;
import com.android.server.am.mars.database.FASEntity;
import com.android.server.am.mars.database.FASTableContract;
import com.android.server.am.mars.database.MARsVersionManager;

/* loaded from: classes.dex */
public class MARsPackageInfo {
  public static String TAG = "MARsPackageInfo";
  public double BatteryUsage;
  public MARsPolicyManager.Policy appliedPolicy;
  public int checkJobRunningCount;
  public int curLevel;
  public int disableReason;
  public long disableResetTime;
  public int disableType;
  public String fasReason;
  public int fasType;
  public boolean hasAppIcon;
  public boolean isDisabled;
  public boolean isFASEnabled;
  public boolean isInRestrictedBucket;
  public boolean isInUsageStats;
  public boolean isRemovedPkg;
  public boolean isSCPMTarget;
  public long lastUsedTime;
  public int maxLevel;
  public int mpsm;
  public String name;
  public long nextKillTimeForLongRunningProcess;
  public int optionFlag;
  public int packageType;
  public double preBatteryUsage;
  public long resetTime;
  public int sbike;
  public String sharedUidName;
  public int state;
  public int uds;
  public int uid;
  public int userId;

  /* JADX WARN: Code restructure failed: missing block: B:15:0x0047, code lost:

     if (java.lang.Integer.parseInt(r23.getStrMode()) == 1) goto L82;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public MARsPackageInfo(FASEntity fASEntity) {
    NumberFormatException numberFormatException;
    long j;
    int i;
    int i2;
    boolean z;
    int i3;
    int i4;
    int i5;
    long j2;
    int i6;
    int i7;
    double d;
    boolean z2;
    int i8;
    int i9;
    long parseLong;
    String strPkgName = fASEntity.getStrPkgName();
    String strFasReason = fASEntity.getStrFasReason();
    int convertFASReasonToValue = FASTableContract.convertFASReasonToValue(strFasReason);
    int convertDBValueToState = FASTableContract.convertDBValueToState(fASEntity.getStrExtras());
    int convertDBValueToDisableReason =
        FASTableContract.convertDBValueToDisableReason(fASEntity.getStrDisableReason());
    try {
      i8 = fASEntity.getStrUid() != null ? Integer.parseInt(fASEntity.getStrUid()) : -1;
    } catch (NumberFormatException e) {
      numberFormatException = e;
      j = 0;
      i = 0;
      i2 = -1;
    }
    try {
      i3 = UserHandle.getUserId(i8);
    } catch (NumberFormatException e2) {
      numberFormatException = e2;
      j = 0;
      i2 = i8;
      i = 0;
      z = false;
      i3 = -1;
      i4 = 0;
      i5 = -1;
      String str = TAG;
      StringBuilder sb = new StringBuilder();
      int i10 = i;
      sb.append("NumberFormatException !");
      sb.append(numberFormatException);
      Log.e(str, sb.toString());
      j2 = r9;
      r9 = j;
      i6 = i4;
      i7 = i5;
      d = 0.0d;
      z2 = z;
      i8 = i2;
      i9 = i10;
      this.name = strPkgName;
      this.uid = i8;
      this.userId = i3;
      this.isFASEnabled = z2;
      this.isInRestrictedBucket = false;
      this.isDisabled = false;
      this.fasReason = strFasReason;
      this.fasType = convertFASReasonToValue;
      this.state = convertDBValueToState;
      this.resetTime = j2;
      this.lastUsedTime = -900000L;
      this.packageType = i6;
      this.maxLevel = i9;
      this.disableType = i7;
      this.disableResetTime = r9;
      this.preBatteryUsage = d;
      this.isRemovedPkg = false;
      this.sharedUidName = null;
      this.disableReason = convertDBValueToDisableReason;
      this.isInUsageStats = false;
      this.checkJobRunningCount = 0;
    }
    try {
      z2 = fASEntity.getStrMode() != null;
      try {
        parseLong =
            fASEntity.getStrResetTime() != null ? Long.parseLong(fASEntity.getStrResetTime()) : 0L;
        try {
          i4 =
              fASEntity.getStrPackageType() != null
                  ? Integer.parseInt(fASEntity.getStrPackageType())
                  : 0;
          try {
            i9 = fASEntity.getStrLevel() != null ? Integer.parseInt(fASEntity.getStrLevel()) : 0;
            if (z2) {
              try {
                if (MARsPolicyManager.getInstance().isFirstTimeTriggerAutorun() && i9 != 4) {
                  i9 = 3;
                }
              } catch (NumberFormatException e3) {
                numberFormatException = e3;
                i = i9;
                i2 = i8;
                z = z2;
                i5 = -1;
                long j3 = r9;
                r9 = parseLong;
                j = j3;
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                int i102 = i;
                sb2.append("NumberFormatException !");
                sb2.append(numberFormatException);
                Log.e(str2, sb2.toString());
                j2 = r9;
                r9 = j;
                i6 = i4;
                i7 = i5;
                d = 0.0d;
                z2 = z;
                i8 = i2;
                i9 = i102;
                this.name = strPkgName;
                this.uid = i8;
                this.userId = i3;
                this.isFASEnabled = z2;
                this.isInRestrictedBucket = false;
                this.isDisabled = false;
                this.fasReason = strFasReason;
                this.fasType = convertFASReasonToValue;
                this.state = convertDBValueToState;
                this.resetTime = j2;
                this.lastUsedTime = -900000L;
                this.packageType = i6;
                this.maxLevel = i9;
                this.disableType = i7;
                this.disableResetTime = r9;
                this.preBatteryUsage = d;
                this.isRemovedPkg = false;
                this.sharedUidName = null;
                this.disableReason = convertDBValueToDisableReason;
                this.isInUsageStats = false;
                this.checkJobRunningCount = 0;
              }
            }
            i5 =
                fASEntity.getStrDisableType() != null
                    ? Integer.parseInt(fASEntity.getStrDisableType())
                    : -1;
          } catch (NumberFormatException e4) {
            numberFormatException = e4;
            i2 = i8;
            z = z2;
            i = 0;
          }
        } catch (NumberFormatException e5) {
          numberFormatException = e5;
          i2 = i8;
          z = z2;
          i = 0;
          i4 = 0;
        }
      } catch (NumberFormatException e6) {
        numberFormatException = e6;
        i2 = i8;
        z = z2;
        i = 0;
        i4 = 0;
        i5 = -1;
        j = 0;
      }
    } catch (NumberFormatException e7) {
      numberFormatException = e7;
      j = 0;
      i2 = i8;
      i = 0;
      z = false;
      i4 = 0;
      i5 = -1;
      String str22 = TAG;
      StringBuilder sb22 = new StringBuilder();
      int i1022 = i;
      sb22.append("NumberFormatException !");
      sb22.append(numberFormatException);
      Log.e(str22, sb22.toString());
      j2 = r9;
      r9 = j;
      i6 = i4;
      i7 = i5;
      d = 0.0d;
      z2 = z;
      i8 = i2;
      i9 = i1022;
      this.name = strPkgName;
      this.uid = i8;
      this.userId = i3;
      this.isFASEnabled = z2;
      this.isInRestrictedBucket = false;
      this.isDisabled = false;
      this.fasReason = strFasReason;
      this.fasType = convertFASReasonToValue;
      this.state = convertDBValueToState;
      this.resetTime = j2;
      this.lastUsedTime = -900000L;
      this.packageType = i6;
      this.maxLevel = i9;
      this.disableType = i7;
      this.disableResetTime = r9;
      this.preBatteryUsage = d;
      this.isRemovedPkg = false;
      this.sharedUidName = null;
      this.disableReason = convertDBValueToDisableReason;
      this.isInUsageStats = false;
      this.checkJobRunningCount = 0;
    }
    try {
      r9 =
          fASEntity.getStrDisableResetTime() != null
              ? Long.parseLong(fASEntity.getStrDisableResetTime())
              : 0L;
      i6 = i4;
      i7 = i5;
      d =
          fASEntity.getStrPreBatteryUsage() != null
              ? Double.parseDouble(fASEntity.getStrPreBatteryUsage())
              : 0.0d;
      j2 = parseLong;
    } catch (NumberFormatException e8) {
      numberFormatException = e8;
      i = i9;
      i2 = i8;
      z = z2;
      long j32 = r9;
      r9 = parseLong;
      j = j32;
      String str222 = TAG;
      StringBuilder sb222 = new StringBuilder();
      int i10222 = i;
      sb222.append("NumberFormatException !");
      sb222.append(numberFormatException);
      Log.e(str222, sb222.toString());
      j2 = r9;
      r9 = j;
      i6 = i4;
      i7 = i5;
      d = 0.0d;
      z2 = z;
      i8 = i2;
      i9 = i10222;
      this.name = strPkgName;
      this.uid = i8;
      this.userId = i3;
      this.isFASEnabled = z2;
      this.isInRestrictedBucket = false;
      this.isDisabled = false;
      this.fasReason = strFasReason;
      this.fasType = convertFASReasonToValue;
      this.state = convertDBValueToState;
      this.resetTime = j2;
      this.lastUsedTime = -900000L;
      this.packageType = i6;
      this.maxLevel = i9;
      this.disableType = i7;
      this.disableResetTime = r9;
      this.preBatteryUsage = d;
      this.isRemovedPkg = false;
      this.sharedUidName = null;
      this.disableReason = convertDBValueToDisableReason;
      this.isInUsageStats = false;
      this.checkJobRunningCount = 0;
    }
    this.name = strPkgName;
    this.uid = i8;
    this.userId = i3;
    this.isFASEnabled = z2;
    this.isInRestrictedBucket = false;
    this.isDisabled = false;
    this.fasReason = strFasReason;
    this.fasType = convertFASReasonToValue;
    this.state = convertDBValueToState;
    this.resetTime = j2;
    this.lastUsedTime = -900000L;
    this.packageType = i6;
    this.maxLevel = i9;
    this.disableType = i7;
    this.disableResetTime = r9;
    this.preBatteryUsage = d;
    this.isRemovedPkg = false;
    this.sharedUidName = null;
    this.disableReason = convertDBValueToDisableReason;
    this.isInUsageStats = false;
    this.checkJobRunningCount = 0;
  }

  public void updatePackageInfo(MARsPackageInfo mARsPackageInfo) {
    int i;
    long j = this.resetTime;
    long j2 = mARsPackageInfo.resetTime;
    if (j == j2
        && this.state == mARsPackageInfo.state
        && this.isFASEnabled == mARsPackageInfo.isFASEnabled
        && this.fasType == mARsPackageInfo.fasType
        && this.maxLevel == mARsPackageInfo.maxLevel
        && this.isDisabled == mARsPackageInfo.isDisabled) {
      return;
    }
    boolean z = mARsPackageInfo.isFASEnabled;
    this.isFASEnabled = z;
    this.isDisabled = mARsPackageInfo.isDisabled;
    this.fasType = mARsPackageInfo.fasType;
    this.state = mARsPackageInfo.state;
    this.resetTime = j2;
    this.packageType = mARsPackageInfo.packageType;
    if (z) {
      i = mARsPackageInfo.maxLevel;
      if (i <= 2) {
        i = 2;
      }
    } else {
      i = 1;
    }
    this.maxLevel = i;
    this.disableType = mARsPackageInfo.disableType;
    this.disableResetTime = mARsPackageInfo.disableResetTime;
    this.BatteryUsage = mARsPackageInfo.BatteryUsage;
    this.preBatteryUsage = mARsPackageInfo.preBatteryUsage;
    this.disableReason = mARsPackageInfo.disableReason;
  }

  public void setIsInUsageStats(boolean z) {
    this.isInUsageStats = z;
  }

  public String getName() {
    return this.name;
  }

  public void initOptionFlag() {
    this.optionFlag = 0;
    String str = this.name;
    if ((str != null && str.contains(".cts."))
        || MARsVersionManager.getInstance().isAdjustRestrictionMatch(19, this.name, null, null)) {
      this.optionFlag |= 2;
    } else if (MARsVersionManager.getInstance()
        .isAdjustRestrictionMatch(11, this.name, null, null)) {
      this.optionFlag |= 1;
    }
    if (MARsVersionManager.getInstance().isAdjustRestrictionMatch(20, this.name, null, null)) {
      this.optionFlag |= 4;
    }
  }

  public int getUid() {
    return this.uid;
  }

  public int getUserId() {
    return this.userId;
  }

  public boolean getFASEnabled() {
    return this.isFASEnabled;
  }

  public void setFASEnabled(boolean z) {
    this.isFASEnabled = z;
  }

  public boolean getIsInRestrictedBucket() {
    return this.isInRestrictedBucket;
  }

  public void setIsInRestrictedBucket(boolean z) {
    this.isInRestrictedBucket = z;
  }

  public String getFasReason() {
    return this.fasReason;
  }

  public void setFasReason(String str) {
    this.fasReason = str;
  }

  public boolean getDisabled() {
    return this.isDisabled;
  }

  public void setDisabled(boolean z) {
    this.isDisabled = z;
  }

  public int getFasType() {
    return this.fasType;
  }

  public void setFasType(int i) {
    this.fasType = i;
  }

  public int getState() {
    return this.state;
  }

  public void setState(int i) {
    this.state = i;
  }

  public long getResetTime() {
    return this.resetTime;
  }

  public void setResetTime(long j) {
    this.resetTime = j;
  }

  public long getLastUsedTime() {
    return this.lastUsedTime;
  }

  public void setLastUsedTime(long j) {
    this.lastUsedTime = j;
  }

  public int getPackageType() {
    return this.packageType;
  }

  public int getMaxLevel() {
    return this.maxLevel;
  }

  public void setMaxLevel(int i) {
    this.maxLevel = i;
  }

  public int getCurLevel() {
    return this.curLevel;
  }

  public void setCurLevel(int i) {
    this.curLevel = i;
  }

  public int getDisableType() {
    return this.disableType;
  }

  public void setDisableType(int i) {
    this.disableType = i;
  }

  public long getDisableResetTime() {
    return this.disableResetTime;
  }

  public void setDisableResetTime(long j) {
    this.disableResetTime = j;
  }

  public double getBatteryUsage() {
    return this.BatteryUsage;
  }

  public void setBatteryUsage(double d) {
    this.BatteryUsage = d;
  }

  public boolean getHasAppIcon() {
    return this.hasAppIcon;
  }

  public void setHasAppIcon(boolean z) {
    this.hasAppIcon = z;
  }

  public String getSharedUidName() {
    return this.sharedUidName;
  }

  public void setSharedUidName(String str) {
    this.sharedUidName = str;
  }

  public int getDisableReason() {
    return this.disableReason;
  }

  public void setDisableReason(int i) {
    this.disableReason = i;
  }

  public int getUds() {
    return this.uds;
  }

  public void setUds(int i) {
    this.uds = i;
  }

  public int getSBike() {
    return this.sbike;
  }

  public void setSBike(int i) {
    this.sbike = i;
  }

  public int getMpsm() {
    return this.mpsm;
  }

  public void setMpsm(int i) {
    this.mpsm = i;
  }

  public MARsPolicyManager.Policy getAppliedPolicy() {
    return this.appliedPolicy;
  }

  public void setAppliedPolicy(MARsPolicyManager.Policy policy) {
    this.appliedPolicy = policy;
  }

  public int getCheckJobRunningCount() {
    return this.checkJobRunningCount;
  }

  public void setCheckJobRunningCount(int i) {
    this.checkJobRunningCount = i;
  }

  public void setIsSCPMTarget(boolean z) {
    this.isSCPMTarget = z;
  }

  public boolean isSCPMTarget() {
    return this.isSCPMTarget;
  }
}
