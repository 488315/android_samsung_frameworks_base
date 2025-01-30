package com.android.server.firewall;

import com.android.internal.util.XmlUtils;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public abstract class FilterList implements Filter {
    public final ArrayList children = new ArrayList();

    public FilterList readFromXml(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            readChild(xmlPullParser);
        }
        return this;
    }

    public void readChild(XmlPullParser xmlPullParser) {
        this.children.add(IntentFirewall.parseFilter(xmlPullParser));
    }
}
