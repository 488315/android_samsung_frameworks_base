package com.android.server.firewall;

import com.android.internal.util.XmlUtils;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class FilterList implements Filter {
    public final ArrayList children = new ArrayList();

    public void readChild(XmlPullParser xmlPullParser) {
        this.children.add(IntentFirewall.parseFilter(xmlPullParser));
    }

    public FilterList readFromXml(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            readChild(xmlPullParser);
        }
        return this;
    }
}
