package com.android.server.am.mars.database;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
