package com.android.server.display.config;

import java.math.BigDecimal;

public final class BrightnessThresholds {
    public ThresholdPoints brightnessThresholdPoints;
    public BigDecimal minimum;

    /* JADX WARN: Code restructure failed: missing block: B:69:0x00cb, code lost:

       if (r5 != 3) goto L58;
    */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00cd, code lost:

       r0.brightnessThresholdPoints = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00d8, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("ThresholdPoints is not closed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00de, code lost:

       if (r1 != 3) goto L52;
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00e0, code lost:

       return r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00e8, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("BrightnessThresholds is not closed");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.display.config.BrightnessThresholds read(
            org.xmlpull.v1.XmlPullParser r8) {
        /*
            com.android.server.display.config.BrightnessThresholds r0 = new com.android.server.display.config.BrightnessThresholds
            r0.<init>()
            r8.getDepth()
        L8:
            int r1 = r8.next()
            r2 = 1
            r3 = 3
            if (r1 == r2) goto Lde
            if (r1 == r3) goto Lde
            int r1 = r8.getEventType()
            r4 = 2
            if (r1 == r4) goto L1a
            goto L8
        L1a:
            java.lang.String r1 = r8.getName()
            java.lang.String r5 = "minimum"
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L33
            java.lang.String r1 = com.android.server.display.config.XmlParser.readText(r8)
            java.math.BigDecimal r2 = new java.math.BigDecimal
            r2.<init>(r1)
            r0.minimum = r2
            goto L8
        L33:
            java.lang.String r5 = "brightnessThresholdPoints"
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto Ld9
            com.android.server.display.config.ThresholdPoints r1 = new com.android.server.display.config.ThresholdPoints
            r1.<init>()
            r8.getDepth()
        L44:
            int r5 = r8.next()
            if (r5 == r2) goto Lcb
            if (r5 == r3) goto Lcb
            int r5 = r8.getEventType()
            if (r5 == r4) goto L53
            goto L44
        L53:
            java.lang.String r5 = r8.getName()
            java.lang.String r6 = "brightnessThresholdPoint"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto Lc6
            com.android.server.display.config.ThresholdPoint r5 = new com.android.server.display.config.ThresholdPoint
            r5.<init>()
            r8.getDepth()
        L68:
            int r6 = r8.next()
            if (r6 == r2) goto La9
            if (r6 == r3) goto La9
            int r6 = r8.getEventType()
            if (r6 == r4) goto L77
            goto L68
        L77:
            java.lang.String r6 = r8.getName()
            java.lang.String r7 = "threshold"
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L90
            java.lang.String r6 = com.android.server.display.config.XmlParser.readText(r8)
            java.math.BigDecimal r7 = new java.math.BigDecimal
            r7.<init>(r6)
            r5.threshold = r7
            goto L68
        L90:
            java.lang.String r7 = "percentage"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto La5
            java.lang.String r6 = com.android.server.display.config.XmlParser.readText(r8)
            java.math.BigDecimal r7 = new java.math.BigDecimal
            r7.<init>(r6)
            r5.percentage = r7
            goto L68
        La5:
            com.android.server.display.config.XmlParser.skip(r8)
            goto L68
        La9:
            if (r6 != r3) goto Lbe
            java.util.List r6 = r1.brightnessThresholdPoint
            if (r6 != 0) goto Lb6
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r1.brightnessThresholdPoint = r6
        Lb6:
            java.util.List r6 = r1.brightnessThresholdPoint
            java.util.ArrayList r6 = (java.util.ArrayList) r6
            r6.add(r5)
            goto L44
        Lbe:
            javax.xml.datatype.DatatypeConfigurationException r8 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "ThresholdPoint is not closed"
            r8.<init>(r0)
            throw r8
        Lc6:
            com.android.server.display.config.XmlParser.skip(r8)
            goto L44
        Lcb:
            if (r5 != r3) goto Ld1
            r0.brightnessThresholdPoints = r1
            goto L8
        Ld1:
            javax.xml.datatype.DatatypeConfigurationException r8 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "ThresholdPoints is not closed"
            r8.<init>(r0)
            throw r8
        Ld9:
            com.android.server.display.config.XmlParser.skip(r8)
            goto L8
        Lde:
            if (r1 != r3) goto Le1
            return r0
        Le1:
            javax.xml.datatype.DatatypeConfigurationException r8 = new javax.xml.datatype.DatatypeConfigurationException
            java.lang.String r0 = "BrightnessThresholds is not closed"
            r8.<init>(r0)
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.config.BrightnessThresholds.read(org.xmlpull.v1.XmlPullParser):com.android.server.display.config.BrightnessThresholds");
    }
}
