package com.android.server.am;


public final class MARsPackageInfo {
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
    public boolean isSCPMTarget;
    public long lastUsedTime;
    public int maxLevel;
    public final String name;
    public long nextKillTimeForLongRunningProcess;
    public int optionFlag;
    public int packageType;
    public long resetTime;
    public int sbike;
    public String sharedUidName;
    public int state;
    public final int uid;
    public final int userId;

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0089, code lost:

       r10 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x011b, code lost:

       android.util.Log.e("MARsPackageInfo", "NumberFormatException !" + r10);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MARsPackageInfo(com.android.server.am.mars.database.FASEntity r10) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.MARsPackageInfo.<init>(com.android.server.am.mars.database.FASEntity):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initOptionFlag() {
        /*
            r4 = this;
            r0 = 0
            r4.optionFlag = r0
            r0 = 0
            java.lang.String r1 = r4.name
            if (r1 == 0) goto L10
            java.lang.String r2 = ".cts."
            boolean r2 = r1.contains(r2)
            if (r2 != 0) goto L1c
        L10:
            java.lang.String[][] r2 = com.android.server.am.mars.database.MARsVersionManager.mMARsSettingsInfoDefault
            com.android.server.am.mars.database.MARsVersionManager r2 = com.android.server.am.mars.database.MARsVersionManager.MARsVersionManagerHolder.INSTANCE
            r3 = 19
            boolean r3 = r2.isAdjustRestrictionMatch(r3, r1, r0, r0)
            if (r3 == 0) goto L23
        L1c:
            int r2 = r4.optionFlag
            r2 = r2 | 2
            r4.optionFlag = r2
            goto L31
        L23:
            r3 = 11
            boolean r2 = r2.isAdjustRestrictionMatch(r3, r1, r0, r0)
            if (r2 == 0) goto L31
            int r2 = r4.optionFlag
            r2 = r2 | 1
            r4.optionFlag = r2
        L31:
            java.lang.String[][] r2 = com.android.server.am.mars.database.MARsVersionManager.mMARsSettingsInfoDefault
            com.android.server.am.mars.database.MARsVersionManager r2 = com.android.server.am.mars.database.MARsVersionManager.MARsVersionManagerHolder.INSTANCE
            r3 = 20
            boolean r0 = r2.isAdjustRestrictionMatch(r3, r1, r0, r0)
            if (r0 == 0) goto L43
            int r0 = r4.optionFlag
            r0 = r0 | 4
            r4.optionFlag = r0
        L43:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.MARsPackageInfo.initOptionFlag():void");
    }

    public final void updatePackageInfo(MARsPackageInfo mARsPackageInfo) {
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
        this.maxLevel = z ? Math.max(mARsPackageInfo.maxLevel, 2) : 1;
        this.disableType = mARsPackageInfo.disableType;
        this.disableResetTime = mARsPackageInfo.disableResetTime;
        this.BatteryUsage = mARsPackageInfo.BatteryUsage;
        this.disableReason = mARsPackageInfo.disableReason;
    }
}
