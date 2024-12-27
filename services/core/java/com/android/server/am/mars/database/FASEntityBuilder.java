package com.android.server.am.mars.database;

public final class FASEntityBuilder {
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
    public String strDisableReason = null;

    public final FASEntity build() {
        if (this.strPkgName == null || this.strUid == null) {
            return null;
        }
        return new FASEntity(this);
    }
}
