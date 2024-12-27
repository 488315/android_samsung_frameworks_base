package com.android.server.display.config;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IdleScreenRefreshRateTimeout {
    public IdleScreenRefreshRateTimeoutLuxThresholds luxThresholds;

    /* JADX WARN: Code restructure failed: missing block: B:75:0x00c9, code lost:
    
        if (r1 != 3) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00cb, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00d3, code lost:
    
        throw new javax.xml.datatype.DatatypeConfigurationException("IdleScreenRefreshRateTimeout is not closed");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.display.config.IdleScreenRefreshRateTimeout read(org.xmlpull.v1.XmlPullParser r8) {
        /*
            com.android.server.display.config.IdleScreenRefreshRateTimeout r0 = new com.android.server.display.config.IdleScreenRefreshRateTimeout
            r0.<init>()
            r8.getDepth()
        L8:
            int r1 = r8.next()
            r2 = 1
            r3 = 3
            if (r1 == r2) goto Lc9
            if (r1 == r3) goto Lc9
            int r1 = r8.getEventType()
            r4 = 2
            if (r1 == r4) goto L1a
            goto L8
        L1a:
            java.lang.String r1 = r8.getName()
            java.lang.String r5 = "luxThresholds"
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto Lc4
            com.android.server.display.config.IdleScreenRefreshRateTimeoutLuxThresholds r1 = new com.android.server.display.config.IdleScreenRefreshRateTimeoutLuxThresholds
            r1.<init>()
            r8.getDepth()
        L2f:
            int r5 = r8.next()
            if (r5 == r2) goto Lb6
            if (r5 == r3) goto Lb6
            int r5 = r8.getEventType()
            if (r5 == r4) goto L3e
            goto L2f
        L3e:
            java.lang.String r5 = r8.getName()
            java.lang.String r6 = "point"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto Lb1
            com.android.server.display.config.IdleScreenRefreshRateTimeoutLuxThresholdPoint r5 = new com.android.server.display.config.IdleScreenRefreshRateTimeoutLuxThresholdPoint
            r5.<init>()
            r8.getDepth()
        L53:
            int r6 = r8.next()
            if (r6 == r2) goto L94
            if (r6 == r3) goto L94
            int r6 = r8.getEventType()
            if (r6 == r4) goto L62
            goto L53
        L62:
            java.lang.String r6 = r8.getName()
            java.lang.String r7 = "lux"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L7b
            java.lang.String r6 = com.android.server.display.config.XmlParser.readText(r8)
            java.math.BigInteger r7 = new java.math.BigInteger
            r7.<init>(r6)
            r5.lux = r7
            goto L53
        L7b:
            java.lang.String r7 = "timeout"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L90
            java.lang.String r6 = com.android.server.display.config.XmlParser.readText(r8)
            java.math.BigInteger r7 = new java.math.BigInteger
            r7.<init>(r6)
            r5.timeout = r7
            goto L53
        L90:
            com.android.server.display.config.XmlParser.skip(r8)
            goto L53
        L94:
            if (r6 != r3) goto La9
            java.util.List r6 = r1.point
            if (r6 != 0) goto La1
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r1.point = r6
        La1:
            java.util.List r6 = r1.point
            java.util.ArrayList r6 = (java.util.ArrayList) r6
            r6.add(r5)
            goto L2f
        La9:
            javax.xml.datatype.DatatypeConfigurationException r8 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "IdleScreenRefreshRateTimeoutLuxThresholdPoint is not closed"
            r8.<init>(r0)
            throw r8
        Lb1:
            com.android.server.display.config.XmlParser.skip(r8)
            goto L2f
        Lb6:
            if (r5 != r3) goto Lbc
            r0.luxThresholds = r1
            goto L8
        Lbc:
            javax.xml.datatype.DatatypeConfigurationException r8 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "IdleScreenRefreshRateTimeoutLuxThresholds is not closed"
            r8.<init>(r0)
            throw r8
        Lc4:
            com.android.server.display.config.XmlParser.skip(r8)
            goto L8
        Lc9:
            if (r1 != r3) goto Lcc
            return r0
        Lcc:
            javax.xml.datatype.DatatypeConfigurationException r8 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "IdleScreenRefreshRateTimeout is not closed"
            r8.<init>(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.config.IdleScreenRefreshRateTimeout.read(org.xmlpull.v1.XmlPullParser):com.android.server.display.config.IdleScreenRefreshRateTimeout");
    }
}
