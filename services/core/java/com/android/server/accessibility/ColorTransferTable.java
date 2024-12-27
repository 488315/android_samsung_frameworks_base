package com.android.server.accessibility;

public final class ColorTransferTable {
    public static final double[] Protan_severity = {
        0.1d, 0.5d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d,
        1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d
    };
    public static final double[] Protan_userParameter = {
        0.1d, 0.2d, 0.4d, 0.5d, 0.5d, 0.6d, 0.6d, 0.7d, 0.7d, 0.8d, 0.8d, 0.9d, 0.9d, 1.0d, 1.0d,
        0.1d, 0.2d, 0.3d, 0.4d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d, 1.0d
    };
    public static final double[] Deutan_severity = {
        0.1d, 0.1d, 0.1d, 0.2d, 0.3d, 0.4d, 0.5d, 0.5d, 0.5d, 0.5d, 0.5d, 0.5d, 0.6d, 0.6d, 0.6d,
        0.6d, 0.7d, 0.7d, 0.8d, 0.9d, 0.9d, 0.9d, 1.0d, 1.0d, 1.0d
    };
    public static final double[] Deutan_userParameter = {
        0.0d, 0.1d, 0.2d, 0.3d, 0.4d, 0.5d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d, 1.0d, 0.0d, 0.1d, 0.2d,
        0.3d, 0.4d, 0.5d, 0.6d, 0.6d, 0.7d, 0.8d, 0.8d, 0.9d, 1.0d
    };
    public static final double[] Tritan_severity = {
        0.1d, 0.1d, 0.1d, 0.1d, 0.2d, 0.2d, 0.2d, 0.3d, 0.4d, 0.4d, 0.5d, 0.5d, 0.6d, 0.6d, 0.7d,
        0.7d, 0.8d, 0.8d, 0.9d, 0.9d, 0.9d, 0.9d, 0.9d, 1.0d, 1.0d
    };
    public static final double[] Tritan_userParameter = {
        0.0d, 0.1d, 0.2d, 0.3d, 0.3d, 0.4d, 0.5d, 0.6d, 0.8d, 0.9d, 0.9d, 1.0d, 0.0d, 0.3d, 0.5d,
        0.7d, 0.7d, 1.0d, 0.2d, 0.3d, 0.5d, 0.6d, 1.0d, 0.9d, 1.0d
    };

    /* JADX WARN: Code restructure failed: missing block: B:128:0x014c, code lost:

       if (r1 != 7) goto L120;
    */
    /* JADX WARN: Removed duplicated region for block: B:365:0x03d6 A[PHI: r8
    0x03d6: PHI (r8v15 int) = (r8v0 int), (r8v2 int), (r8v0 int), (r8v16 int) binds: [B:379:0x0403, B:380:0x0406, B:363:0x03d2, B:364:0x03d5] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getColorTransferValue_DMC(int r24, int r25, int r26, double r27, double r29) {
        /*
            Method dump skipped, instructions count: 2094
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.ColorTransferTable.getColorTransferValue_DMC(int,"
                    + " int, int, double, double):int");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
    	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
    	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
    	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
    	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
    	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
    	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
    	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
    	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
    	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
    */
    public static int getColorTransferValue_Hybrid(
            int r32, int r33, int r34, double r35, double r37) {
        /*
            Method dump skipped, instructions count: 2388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.ColorTransferTable.getColorTransferValue_Hybrid(int,"
                    + " int, int, double, double):int");
    }

    public static int roundHalfUp(double d) {
        return (int) Math.round(d * 10.0d);
    }
}
