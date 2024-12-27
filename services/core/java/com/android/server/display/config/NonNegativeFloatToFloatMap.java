package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NonNegativeFloatToFloatMap {
    public List point;

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0086, code lost:

       if (r1 != 3) goto L32;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0088, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0090, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("NonNegativeFloatToFloatMap is not closed");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.display.config.NonNegativeFloatToFloatMap read(
            org.xmlpull.v1.XmlPullParser r7) {
        /*
            com.android.server.display.config.NonNegativeFloatToFloatMap r0 = new com.android.server.display.config.NonNegativeFloatToFloatMap
            r0.<init>()
            r7.getDepth()
        L8:
            int r1 = r7.next()
            r2 = 1
            r3 = 3
            if (r1 == r2) goto L86
            if (r1 == r3) goto L86
            int r1 = r7.getEventType()
            r4 = 2
            if (r1 == r4) goto L1a
            goto L8
        L1a:
            java.lang.String r1 = r7.getName()
            java.lang.String r5 = "point"
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L82
            com.android.server.display.config.NonNegativeFloatToFloatPoint r1 = new com.android.server.display.config.NonNegativeFloatToFloatPoint
            r1.<init>()
            r7.getDepth()
        L2f:
            int r5 = r7.next()
            if (r5 == r2) goto L70
            if (r5 == r3) goto L70
            int r5 = r7.getEventType()
            if (r5 == r4) goto L3e
            goto L2f
        L3e:
            java.lang.String r5 = r7.getName()
            java.lang.String r6 = "first"
            boolean r6 = r5.equals(r6)
            if (r6 == 0) goto L57
            java.lang.String r5 = com.android.server.display.config.XmlParser.readText(r7)
            java.math.BigDecimal r6 = new java.math.BigDecimal
            r6.<init>(r5)
            r1.first = r6
            goto L2f
        L57:
            java.lang.String r6 = "second"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L6c
            java.lang.String r5 = com.android.server.display.config.XmlParser.readText(r7)
            java.math.BigDecimal r6 = new java.math.BigDecimal
            r6.<init>(r5)
            r1.second = r6
            goto L2f
        L6c:
            com.android.server.display.config.XmlParser.skip(r7)
            goto L2f
        L70:
            if (r5 != r3) goto L7a
            java.util.List r2 = r0.getPoint()
            r2.add(r1)
            goto L8
        L7a:
            javax.xml.datatype.DatatypeConfigurationException r7 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "NonNegativeFloatToFloatPoint is not closed"
            r7.<init>(r0)
            throw r7
        L82:
            com.android.server.display.config.XmlParser.skip(r7)
            goto L8
        L86:
            if (r1 != r3) goto L89
            return r0
        L89:
            javax.xml.datatype.DatatypeConfigurationException r7 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "NonNegativeFloatToFloatMap is not closed"
            r7.<init>(r0)
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.config.NonNegativeFloatToFloatMap.read(org.xmlpull.v1.XmlPullParser):com.android.server.display.config.NonNegativeFloatToFloatMap");
    }

    public final List getPoint() {
        if (this.point == null) {
            this.point = new ArrayList();
        }
        return this.point;
    }
}
