package com.android.server.display.config;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NitsMap {
    public String interpolation;
    public List point;

    /* JADX WARN: Code restructure failed: missing block: B:55:0x009e, code lost:
    
        if (r1 != 3) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00a0, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00a8, code lost:
    
        throw new javax.xml.datatype.DatatypeConfigurationException("NitsMap is not closed");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.display.config.NitsMap read(org.xmlpull.v1.XmlPullParser r7) {
        /*
            com.android.server.display.config.NitsMap r0 = new com.android.server.display.config.NitsMap
            r0.<init>()
            r1 = 0
            java.lang.String r2 = "interpolation"
            java.lang.String r1 = r7.getAttributeValue(r1, r2)
            if (r1 == 0) goto L11
            r0.interpolation = r1
        L11:
            r7.getDepth()
        L14:
            int r1 = r7.next()
            r2 = 1
            r3 = 3
            if (r1 == r2) goto L9e
            if (r1 == r3) goto L9e
            int r1 = r7.getEventType()
            r4 = 2
            if (r1 == r4) goto L26
            goto L14
        L26:
            java.lang.String r1 = r7.getName()
            java.lang.String r5 = "point"
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L99
            com.android.server.display.config.Point r1 = new com.android.server.display.config.Point
            r1.<init>()
            r7.getDepth()
        L3b:
            int r5 = r7.next()
            if (r5 == r2) goto L7c
            if (r5 == r3) goto L7c
            int r5 = r7.getEventType()
            if (r5 == r4) goto L4a
            goto L3b
        L4a:
            java.lang.String r5 = r7.getName()
            java.lang.String r6 = "value"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L63
            java.lang.String r5 = com.android.server.display.config.XmlParser.readText(r7)
            java.math.BigDecimal r6 = new java.math.BigDecimal
            r6.<init>(r5)
            r1.value = r6
            goto L3b
        L63:
            java.lang.String r6 = "nits"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L78
            java.lang.String r5 = com.android.server.display.config.XmlParser.readText(r7)
            java.math.BigDecimal r6 = new java.math.BigDecimal
            r6.<init>(r5)
            r1.nits = r6
            goto L3b
        L78:
            com.android.server.display.config.XmlParser.skip(r7)
            goto L3b
        L7c:
            if (r5 != r3) goto L91
            java.util.List r2 = r0.point
            if (r2 != 0) goto L89
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r0.point = r2
        L89:
            java.util.List r2 = r0.point
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            r2.add(r1)
            goto L14
        L91:
            javax.xml.datatype.DatatypeConfigurationException r7 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "Point is not closed"
            r7.<init>(r0)
            throw r7
        L99:
            com.android.server.display.config.XmlParser.skip(r7)
            goto L14
        L9e:
            if (r1 != r3) goto La1
            return r0
        La1:
            javax.xml.datatype.DatatypeConfigurationException r7 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "NitsMap is not closed"
            r7.<init>(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.config.NitsMap.read(org.xmlpull.v1.XmlPullParser):com.android.server.display.config.NitsMap");
    }
}
