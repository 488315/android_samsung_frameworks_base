package com.android.server.display.config;

import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class XmlParser {
    /* JADX WARN: Code restructure failed: missing block: B:1110:0x02cc, code lost:

       if (r6 != 3) goto L724;
    */
    /* JADX WARN: Code restructure failed: missing block: B:1111:0x02ce, code lost:

       r14.thermalThrottling = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:1115:0x02d9, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("ThermalThrottling is not closed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:1192:0x0d55, code lost:

       if (r3 != 3) goto L705;
    */
    /* JADX WARN: Code restructure failed: missing block: B:1193:0x0d57, code lost:

       return r14;
    */
    /* JADX WARN: Code restructure failed: missing block: B:1195:0x0d5f, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("DisplayConfiguration is not closed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x0d02, code lost:

       if (r6 != 3) goto L803;
    */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x0d04, code lost:

       r14.evenDimmer = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x0d0f, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("EvenDimmerMode is not closed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:464:0x0917, code lost:

       if (r8 != 3) goto L752;
    */
    /* JADX WARN: Code restructure failed: missing block: B:465:0x0919, code lost:

       r3.refreshRateZoneProfiles = r6;
    */
    /* JADX WARN: Code restructure failed: missing block: B:469:0x0924, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("RefreshRateZoneProfiles is not closed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:479:0x0989, code lost:

       if (r6 != 3) goto L755;
    */
    /* JADX WARN: Code restructure failed: missing block: B:480:0x098b, code lost:

       r14.refreshRate = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:484:0x0996, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("RefreshRateConfigs is not closed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:857:0x0486, code lost:

       if (r6 != 3) goto L732;
    */
    /* JADX WARN: Code restructure failed: missing block: B:858:0x0488, code lost:

       r14.luxThrottling = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:862:0x0493, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("LuxThrottling is not closed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:938:0x03c4, code lost:

       if (r7 != 3) goto L729;
    */
    /* JADX WARN: Code restructure failed: missing block: B:940:0x03c8, code lost:

       if (r3.powerThrottlingMap != null) goto L215;
    */
    /* JADX WARN: Code restructure failed: missing block: B:941:0x03ca, code lost:

       r3.powerThrottlingMap = new java.util.ArrayList();
    */
    /* JADX WARN: Code restructure failed: missing block: B:942:0x03d1, code lost:

       ((java.util.ArrayList) r3.powerThrottlingMap).add(r6);
    */
    /* JADX WARN: Code restructure failed: missing block: B:946:0x03e1, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("PowerThrottlingMap is not closed");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.display.config.DisplayConfiguration read(
            java.io.InputStream r14) {
        /*
            Method dump skipped, instructions count: 3425
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.config.XmlParser.read(java.io.InputStream):com.android.server.display.config.DisplayConfiguration");
    }

    public static String readText(XmlPullParser xmlPullParser) {
        if (xmlPullParser.next() != 4) {
            return "";
        }
        String text = xmlPullParser.getText();
        xmlPullParser.nextTag();
        return text;
    }

    public static void skip(XmlPullParser xmlPullParser) {
        if (xmlPullParser.getEventType() != 2) {
            throw new IllegalStateException();
        }
        int i = 1;
        while (i != 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }
}
