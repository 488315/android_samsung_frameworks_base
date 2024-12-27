package com.android.server.policy.devicestate.config;

import org.xmlpull.v1.XmlPullParser;

public abstract class XmlParser {
    /* JADX WARN: Code restructure failed: missing block: B:214:0x0260, code lost:

       if (r5 != 3) goto L151;
    */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x0264, code lost:

       if (r11.deviceState != null) goto L134;
    */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0266, code lost:

       r11.deviceState = new java.util.ArrayList();
    */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x026d, code lost:

       ((java.util.ArrayList) r11.deviceState).add(r1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x027d, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("DeviceState is not closed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x0283, code lost:

       if (r1 != 3) goto L140;
    */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x0285, code lost:

       return r11;
    */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x028d, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("DeviceStateConfig is not closed");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.policy.devicestate.config.DeviceStateConfig read(
            java.io.InputStream r11) {
        /*
            Method dump skipped, instructions count: 655
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.devicestate.config.XmlParser.read(java.io.InputStream):com.android.server.policy.devicestate.config.DeviceStateConfig");
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
