package com.android.server.display.config.layout;

import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class XmlParser {
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0180, code lost:
    
        if (r6 != 3) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0184, code lost:
    
        if (r9.layout != null) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0186, code lost:
    
        r9.layout = new java.util.ArrayList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x018d, code lost:
    
        ((java.util.ArrayList) r9.layout).add(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x019d, code lost:
    
        throw new javax.xml.datatype.DatatypeConfigurationException("Layout is not closed");
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x01a3, code lost:
    
        if (r3 != 3) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x01a5, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x01ad, code lost:
    
        throw new javax.xml.datatype.DatatypeConfigurationException("Layouts is not closed");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.display.config.layout.Layouts read(java.io.InputStream r9) {
        /*
            Method dump skipped, instructions count: 431
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.config.layout.XmlParser.read(java.io.InputStream):com.android.server.display.config.layout.Layouts");
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
