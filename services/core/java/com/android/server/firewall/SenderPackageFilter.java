package com.android.server.firewall;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Intent;
import android.os.RemoteException;
import android.os.UserHandle;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class SenderPackageFilter implements Filter {
    public static final AnonymousClass1 FACTORY = new AnonymousClass1("sender-package");
    public final String mPackageName;

    /* renamed from: com.android.server.firewall.SenderPackageFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends FilterFactory {
        @Override // com.android.server.firewall.FilterFactory
        public final Filter newFilter(XmlPullParser xmlPullParser) {
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                return new SenderPackageFilter(attributeValue);
            }
            throw new XmlPullParserException(
                    "A package name must be specified.", xmlPullParser, null);
        }
    }

    public SenderPackageFilter(String str) {
        this.mPackageName = str;
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
        try {
            i4 = AppGlobals.getPackageManager().getPackageUid(this.mPackageName, 4194304L, 0);
        } catch (RemoteException unused) {
            i4 = -1;
        }
        if (i4 == -1) {
            return false;
        }
        return UserHandle.isSameApp(i4, i);
    }
}
