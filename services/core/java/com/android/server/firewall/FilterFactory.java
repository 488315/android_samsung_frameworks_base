package com.android.server.firewall;

import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class FilterFactory {
    public final String mTag;

    public FilterFactory(String str) {
        str.getClass();
        this.mTag = str;
    }

    public abstract Filter newFilter(XmlPullParser xmlPullParser);
}
