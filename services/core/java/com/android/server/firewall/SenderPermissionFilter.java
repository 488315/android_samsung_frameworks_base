package com.android.server.firewall;

import android.content.ComponentName;
import android.content.Intent;

import com.android.server.am.ActivityManagerService;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class SenderPermissionFilter implements Filter {
    public static final AnonymousClass1 FACTORY = new AnonymousClass1("sender-permission");
    public final String mPermission;

    /* renamed from: com.android.server.firewall.SenderPermissionFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends FilterFactory {
        @Override // com.android.server.firewall.FilterFactory
        public final Filter newFilter(XmlPullParser xmlPullParser) {
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                return new SenderPermissionFilter(attributeValue);
            }
            throw new XmlPullParserException(
                    "Permission name must be specified.", xmlPullParser, null);
        }
    }

    public SenderPermissionFilter(String str) {
        this.mPermission = str;
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
        intentFirewall.mAms.getClass();
        return ActivityManagerService.checkComponentPermission(i2, i, this.mPermission, 0, i3, true)
                == 0;
    }
}
