package com.android.server.firewall;

import org.xmlpull.v1.XmlPullParser;

public abstract class FilterFactory {
    public final String mTag;

    public FilterFactory(String str) {
        str.getClass();
        this.mTag = str;
    }

    public abstract Filter newFilter(XmlPullParser xmlPullParser);
}
