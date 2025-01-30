package com.android.server.am.mars.database;

import java.io.Serializable;

/* loaded from: classes.dex */
public class FASEntity implements Serializable {
  private String strCurBatteryUsage;
  private String strDisableReason;
  private String strDisableResetTIme;
  private String strDisableType;
  private String strExtras;
  private String strFasReason;
  private String strLevel;
  private String strMode;
  private String strNew;
  private String strPackageType;
  private String strPkgName;
  private String strPreBatteryUsage;
  private String strResetTime;
  private String strUid;

  public FASEntity(FASEntityBuilder fASEntityBuilder) {
    this.strPkgName = fASEntityBuilder.getStrPkgName();
    this.strUid = fASEntityBuilder.getStrUid();
    this.strMode = fASEntityBuilder.getStrMode();
    this.strNew = fASEntityBuilder.getStrNew();
    this.strFasReason = fASEntityBuilder.getStrFasReason();
    this.strExtras = fASEntityBuilder.getStrExtras();
    this.strResetTime = fASEntityBuilder.getStrResetTime();
    this.strPackageType = fASEntityBuilder.getStrPackageType();
    this.strLevel = fASEntityBuilder.getStrLevel();
    this.strDisableType = fASEntityBuilder.getStrDisableType();
    this.strDisableResetTIme = fASEntityBuilder.getStrDisableResetTime();
    this.strPreBatteryUsage = fASEntityBuilder.getStrPreBatteryUsage();
    this.strDisableReason = fASEntityBuilder.getStrDisableReason();
  }

  public String getStrPkgName() {
    return this.strPkgName;
  }

  public String getStrUid() {
    return this.strUid;
  }

  public String getStrMode() {
    return this.strMode;
  }

  public String getStrNew() {
    return this.strNew;
  }

  public String getStrFasReason() {
    return this.strFasReason;
  }

  public String getStrExtras() {
    return this.strExtras;
  }

  public String getStrResetTime() {
    return this.strResetTime;
  }

  public String getStrPackageType() {
    return this.strPackageType;
  }

  public String getStrLevel() {
    return this.strLevel;
  }

  public String getStrDisableType() {
    return this.strDisableType;
  }

  public String getStrDisableResetTime() {
    return this.strDisableResetTIme;
  }

  public String getStrPreBatteryUsage() {
    return this.strPreBatteryUsage;
  }

  public String getStrDisableReason() {
    return this.strDisableReason;
  }
}
