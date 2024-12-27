package com.android.server.firewall;

import com.android.internal.util.XmlUtils;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

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
