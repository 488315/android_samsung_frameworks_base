package com.android.server.firewall;

import android.content.ComponentName;
import android.content.Intent;

import com.android.internal.util.XmlUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NotFilter implements Filter {
    public static final AnonymousClass1 FACTORY = new AnonymousClass1("not");
    public final Filter mChild;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.firewall.NotFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends FilterFactory {
        @Override // com.android.server.firewall.FilterFactory
        public final Filter newFilter(XmlPullParser xmlPullParser) {
            int depth = xmlPullParser.getDepth();
            Filter filter = null;
            while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                Filter parseFilter = IntentFirewall.parseFilter(xmlPullParser);
                if (filter != null) {
                    throw new XmlPullParserException(
                            "<not> tag can only contain a single child filter.",
                            xmlPullParser,
                            null);
                }
                filter = parseFilter;
            }
            return new NotFilter(filter);
        }
    }

    public NotFilter(Filter filter) {
        this.mChild = filter;
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
        return !this.mChild.matches(intentFirewall, componentName, intent, i, i2, str, i3);
    }
}
