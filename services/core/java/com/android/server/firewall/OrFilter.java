package com.android.server.firewall;

import android.content.ComponentName;
import android.content.Intent;

import org.xmlpull.v1.XmlPullParser;

public final class OrFilter extends FilterList {
    public static final AnonymousClass1 FACTORY = new AnonymousClass1("or");

    /* renamed from: com.android.server.firewall.OrFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends FilterFactory {
        @Override // com.android.server.firewall.FilterFactory
        public final Filter newFilter(XmlPullParser xmlPullParser) {
            OrFilter orFilter = new OrFilter();
            orFilter.readFromXml(xmlPullParser);
            return orFilter;
        }
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
        for (int i4 = 0; i4 < this.children.size(); i4++) {
            if (((Filter) this.children.get(i4))
                    .matches(intentFirewall, componentName, intent, i, i2, str, i3)) {
                return true;
            }
        }
        return false;
    }
}
