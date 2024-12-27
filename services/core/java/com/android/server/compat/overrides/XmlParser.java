package com.android.server.compat.overrides;

import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class XmlParser {
    /* JADX WARN: Code restructure failed: missing block: B:135:0x01ac, code lost:

       if (r6 != 3) goto L105;
    */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x01ae, code lost:

       r10.getChangeOverrides().add(r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x01be, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("ChangeOverrides is not closed");
    */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x01c4, code lost:

       if (r3 != 3) goto L97;
    */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x01c6, code lost:

       return r10;
    */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x01ce, code lost:

       throw new javax.xml.datatype.DatatypeConfigurationException("Overrides is not closed");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.compat.overrides.Overrides read(java.io.InputStream r10) {
        /*
            Method dump skipped, instructions count: 464
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.compat.overrides.XmlParser.read(java.io.InputStream):com.android.server.compat.overrides.Overrides");
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
