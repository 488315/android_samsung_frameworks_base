package com.android.server.firewall;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PortFilter implements Filter {
    public static final AnonymousClass1 FACTORY = new AnonymousClass1("port");
    public final int mLowerBound;
    public final int mUpperBound;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.firewall.PortFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends FilterFactory {
        @Override // com.android.server.firewall.FilterFactory
        public final Filter newFilter(XmlPullParser xmlPullParser) {
            int parseInt;
            String attributeValue = xmlPullParser.getAttributeValue(null, "equals");
            if (attributeValue != null) {
                try {
                    parseInt = Integer.parseInt(attributeValue);
                } catch (NumberFormatException unused) {
                    throw new XmlPullParserException(
                            "Invalid port value: ".concat(attributeValue), xmlPullParser, null);
                }
            } else {
                parseInt = -1;
            }
            int i = parseInt;
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "min");
            String attributeValue3 = xmlPullParser.getAttributeValue(null, "max");
            if (attributeValue2 != null || attributeValue3 != null) {
                if (attributeValue != null) {
                    throw new XmlPullParserException(
                            "Port filter cannot use both equals and range filtering",
                            xmlPullParser,
                            null);
                }
                if (attributeValue2 != null) {
                    try {
                        parseInt = Integer.parseInt(attributeValue2);
                    } catch (NumberFormatException unused2) {
                        throw new XmlPullParserException(
                                "Invalid minimum port value: ".concat(attributeValue2),
                                xmlPullParser,
                                null);
                    }
                }
                if (attributeValue3 != null) {
                    try {
                        i = Integer.parseInt(attributeValue3);
                    } catch (NumberFormatException unused3) {
                        throw new XmlPullParserException(
                                "Invalid maximum port value: ".concat(attributeValue3),
                                xmlPullParser,
                                null);
                    }
                }
            }
            return new PortFilter(parseInt, i);
        }
    }

    public PortFilter(int i, int i2) {
        this.mLowerBound = i;
        this.mUpperBound = i2;
    }

    @Override // com.android.server.firewall.Filter
    public final boolean matches(
            IntentFirewall intentFirewall,
            ComponentName componentName,
            Intent intent,
            int i,
            int i2,
            String str,
            int i3) {
        int i4;
        int i5;
        Uri data = intent.getData();
        int port = data != null ? data.getPort() : -1;
        return port != -1
                && ((i4 = this.mLowerBound) == -1 || i4 <= port)
                && ((i5 = this.mUpperBound) == -1 || i5 >= port);
    }
}
