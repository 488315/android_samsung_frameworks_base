package com.android.server.am;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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

    /* JADX WARN: Can't wrap try/catch for region: R(26:0|1|(24:50|(1:52)(2:53|(1:55)(2:56|(1:58)(2:59|(1:61))))|4|5|6|(1:8)|9|(3:11|(1:13)(1:15)|14)|16|(1:18)|19|(1:21)|22|(1:24)|25|(2:27|(1:31))|32|(1:34)|35|(1:37)|38|(1:40)|42|43)|3|4|5|6|(0)|9|(0)|16|(0)|19|(0)|22|(0)|25|(0)|32|(0)|35|(0)|38|(0)|42|43) */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0089, code lost:

       r10 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x011b, code lost:

       android.util.Log.e("MARsPackageInfo", "NumberFormatException !" + r10);
    */
    /* JADX WARN: Removed duplicated region for block: B:11:0x009a A[Catch: NumberFormatException -> 0x0089, TryCatch #0 {NumberFormatException -> 0x0089, blocks: (B:6:0x0078, B:8:0x007e, B:9:0x008c, B:11:0x009a, B:14:0x00a6, B:16:0x00a8, B:18:0x00ae, B:19:0x00b8, B:21:0x00be, B:22:0x00c8, B:24:0x00ce, B:25:0x00d8, B:27:0x00dc, B:29:0x00e6, B:31:0x00ea, B:32:0x00ed, B:34:0x00f3, B:35:0x00fd, B:37:0x0103, B:38:0x010d, B:40:0x0113), top: B:5:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00ae A[Catch: NumberFormatException -> 0x0089, TryCatch #0 {NumberFormatException -> 0x0089, blocks: (B:6:0x0078, B:8:0x007e, B:9:0x008c, B:11:0x009a, B:14:0x00a6, B:16:0x00a8, B:18:0x00ae, B:19:0x00b8, B:21:0x00be, B:22:0x00c8, B:24:0x00ce, B:25:0x00d8, B:27:0x00dc, B:29:0x00e6, B:31:0x00ea, B:32:0x00ed, B:34:0x00f3, B:35:0x00fd, B:37:0x0103, B:38:0x010d, B:40:0x0113), top: B:5:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00be A[Catch: NumberFormatException -> 0x0089, TryCatch #0 {NumberFormatException -> 0x0089, blocks: (B:6:0x0078, B:8:0x007e, B:9:0x008c, B:11:0x009a, B:14:0x00a6, B:16:0x00a8, B:18:0x00ae, B:19:0x00b8, B:21:0x00be, B:22:0x00c8, B:24:0x00ce, B:25:0x00d8, B:27:0x00dc, B:29:0x00e6, B:31:0x00ea, B:32:0x00ed, B:34:0x00f3, B:35:0x00fd, B:37:0x0103, B:38:0x010d, B:40:0x0113), top: B:5:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ce A[Catch: NumberFormatException -> 0x0089, TryCatch #0 {NumberFormatException -> 0x0089, blocks: (B:6:0x0078, B:8:0x007e, B:9:0x008c, B:11:0x009a, B:14:0x00a6, B:16:0x00a8, B:18:0x00ae, B:19:0x00b8, B:21:0x00be, B:22:0x00c8, B:24:0x00ce, B:25:0x00d8, B:27:0x00dc, B:29:0x00e6, B:31:0x00ea, B:32:0x00ed, B:34:0x00f3, B:35:0x00fd, B:37:0x0103, B:38:0x010d, B:40:0x0113), top: B:5:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00dc A[Catch: NumberFormatException -> 0x0089, TryCatch #0 {NumberFormatException -> 0x0089, blocks: (B:6:0x0078, B:8:0x007e, B:9:0x008c, B:11:0x009a, B:14:0x00a6, B:16:0x00a8, B:18:0x00ae, B:19:0x00b8, B:21:0x00be, B:22:0x00c8, B:24:0x00ce, B:25:0x00d8, B:27:0x00dc, B:29:0x00e6, B:31:0x00ea, B:32:0x00ed, B:34:0x00f3, B:35:0x00fd, B:37:0x0103, B:38:0x010d, B:40:0x0113), top: B:5:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00f3 A[Catch: NumberFormatException -> 0x0089, TryCatch #0 {NumberFormatException -> 0x0089, blocks: (B:6:0x0078, B:8:0x007e, B:9:0x008c, B:11:0x009a, B:14:0x00a6, B:16:0x00a8, B:18:0x00ae, B:19:0x00b8, B:21:0x00be, B:22:0x00c8, B:24:0x00ce, B:25:0x00d8, B:27:0x00dc, B:29:0x00e6, B:31:0x00ea, B:32:0x00ed, B:34:0x00f3, B:35:0x00fd, B:37:0x0103, B:38:0x010d, B:40:0x0113), top: B:5:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0103 A[Catch: NumberFormatException -> 0x0089, TryCatch #0 {NumberFormatException -> 0x0089, blocks: (B:6:0x0078, B:8:0x007e, B:9:0x008c, B:11:0x009a, B:14:0x00a6, B:16:0x00a8, B:18:0x00ae, B:19:0x00b8, B:21:0x00be, B:22:0x00c8, B:24:0x00ce, B:25:0x00d8, B:27:0x00dc, B:29:0x00e6, B:31:0x00ea, B:32:0x00ed, B:34:0x00f3, B:35:0x00fd, B:37:0x0103, B:38:0x010d, B:40:0x0113), top: B:5:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0113 A[Catch: NumberFormatException -> 0x0089, TRY_LEAVE, TryCatch #0 {NumberFormatException -> 0x0089, blocks: (B:6:0x0078, B:8:0x007e, B:9:0x008c, B:11:0x009a, B:14:0x00a6, B:16:0x00a8, B:18:0x00ae, B:19:0x00b8, B:21:0x00be, B:22:0x00c8, B:24:0x00ce, B:25:0x00d8, B:27:0x00dc, B:29:0x00e6, B:31:0x00ea, B:32:0x00ed, B:34:0x00f3, B:35:0x00fd, B:37:0x0103, B:38:0x010d, B:40:0x0113), top: B:5:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x007e A[Catch: NumberFormatException -> 0x0089, TryCatch #0 {NumberFormatException -> 0x0089, blocks: (B:6:0x0078, B:8:0x007e, B:9:0x008c, B:11:0x009a, B:14:0x00a6, B:16:0x00a8, B:18:0x00ae, B:19:0x00b8, B:21:0x00be, B:22:0x00c8, B:24:0x00ce, B:25:0x00d8, B:27:0x00dc, B:29:0x00e6, B:31:0x00ea, B:32:0x00ed, B:34:0x00f3, B:35:0x00fd, B:37:0x0103, B:38:0x010d, B:40:0x0113), top: B:5:0x0078 }] */
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

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003d  */
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
