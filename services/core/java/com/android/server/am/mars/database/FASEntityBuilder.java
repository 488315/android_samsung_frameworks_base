package com.android.server.am.mars.database;

/* loaded from: classes.dex */
public class FASEntityBuilder {
  public String strPkgName = null;
  public String strUid = null;
  public String strMode = null;
  public String strNew = null;
  public String strFasReason = null;
  public String strExtras = null;
  public String strResetTime = null;
  public String strPackageType = null;
  public String strLevel = null;
  public String strDisableType = null;
  public String strDisableResetTime = null;
  public String strPreBatteryUsage = null;
  public String strCurBatteryUsage = null;
  public String strDisableReason = null;

  public FASEntity build() {
    if (this.strPkgName == null || this.strUid == null) {
      return null;
    }
    return new FASEntity(this);
  }

  public String getStrPkgName() {
    return this.strPkgName;
  }

  public FASEntityBuilder setStrPkgName(String str) {
    this.strPkgName = str;
    return this;
  }

  public String getStrUid() {
    return this.strUid;
  }

  public FASEntityBuilder setStrUid(String str) {
    this.strUid = str;
    return this;
  }

  public String getStrMode() {
    return this.strMode;
  }

  public FASEntityBuilder setStrMode(String str) {
    this.strMode = str;
    return this;
  }

  public String getStrNew() {
    return this.strNew;
  }

  public FASEntityBuilder setStrNew(String str) {
    this.strNew = str;
    return this;
  }

  public String getStrFasReason() {
    return this.strFasReason;
  }

  public FASEntityBuilder setStrFasReason(String str) {
    this.strFasReason = str;
    return this;
  }

  public String getStrExtras() {
    return this.strExtras;
  }

  public FASEntityBuilder setStrExtras(String str) {
    this.strExtras = str;
    return this;
  }

  public String getStrResetTime() {
    return this.strResetTime;
  }

  public FASEntityBuilder setStrResetTime(String str) {
    this.strResetTime = str;
    return this;
  }

  public String getStrPackageType() {
    return this.strPackageType;
  }

  public FASEntityBuilder setStrPackageType(String str) {
    this.strPackageType = str;
    return this;
  }

  public String getStrLevel() {
    return this.strLevel;
  }

  public FASEntityBuilder setStrLevel(String str) {
    this.strLevel = str;
    return this;
  }

  public String getStrDisableType() {
    return this.strDisableType;
  }

  public FASEntityBuilder setStrDisableType(String str) {
    this.strDisableType = str;
    return this;
  }

  public String getStrDisableResetTime() {
    return this.strDisableResetTime;
  }

  public FASEntityBuilder setStrDisableResetTime(String str) {
    this.strDisableResetTime = str;
    return this;
  }

  public String getStrPreBatteryUsage() {
    return this.strPreBatteryUsage;
  }

  public FASEntityBuilder setStrPreBatteryUsage(String str) {
    this.strPreBatteryUsage = str;
    return this;
  }

  public String getStrDisableReason() {
    return this.strDisableReason;
  }

  public FASEntityBuilder setStrDisableReason(String str) {
    this.strDisableReason = str;
    return this;
  }
}
