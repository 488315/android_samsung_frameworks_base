package com.android.server.firewall;

import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public abstract class FilterFactory {
    public final String mTag;

    public abstract Filter newFilter(XmlPullParser xmlPullParser);

    public FilterFactory(String str) {
        str.getClass();
        this.mTag = str;
    }

    public String getTagName() {
        return this.mTag;
    }
}
