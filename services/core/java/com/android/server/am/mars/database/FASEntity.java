package com.android.server.am.mars.database;

import java.io.Serializable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
        this.strPkgName = fASEntityBuilder.strPkgName;
        this.strUid = fASEntityBuilder.strUid;
        this.strMode = fASEntityBuilder.strMode;
        this.strNew = fASEntityBuilder.strNew;
        this.strFasReason = fASEntityBuilder.strFasReason;
        this.strExtras = fASEntityBuilder.strExtras;
        this.strResetTime = fASEntityBuilder.strResetTime;
        this.strPackageType = fASEntityBuilder.strPackageType;
        this.strLevel = fASEntityBuilder.strLevel;
        this.strDisableType = fASEntityBuilder.strDisableType;
        this.strDisableResetTIme = fASEntityBuilder.strDisableResetTime;
        this.strPreBatteryUsage = fASEntityBuilder.strPreBatteryUsage;
        this.strDisableReason = fASEntityBuilder.strDisableReason;
    }

    public final String getStrDisableReason() {
        return this.strDisableReason;
    }

    public final String getStrDisableResetTime() {
        return this.strDisableResetTIme;
    }

    public final String getStrDisableType() {
        return this.strDisableType;
    }

    public final String getStrExtras() {
        return this.strExtras;
    }

    public final String getStrFasReason() {
        return this.strFasReason;
    }

    public final String getStrLevel() {
        return this.strLevel;
    }

    public final String getStrMode() {
        return this.strMode;
    }

    public final String getStrNew() {
        return this.strNew;
    }

    public final String getStrPackageType() {
        return this.strPackageType;
    }

    public final String getStrPkgName() {
        return this.strPkgName;
    }

    public final String getStrPreBatteryUsage() {
        return this.strPreBatteryUsage;
    }

    public final String getStrResetTime() {
        return this.strResetTime;
    }

    public final String getStrUid() {
        return this.strUid;
    }
}
