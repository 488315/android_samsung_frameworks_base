package com.android.server.asks;

import android.util.Slog;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AuthorizedTokenManager {
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0056, code lost:
    
        android.util.Slog.i("PackageInformation_Token", "signature is same");
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x005c, code lost:
    
        r11 = new java.lang.StringBuilder("[Token] Signature matched : ");
        r4 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0072, code lost:
    
        if ("0x1".equals(android.os.SystemProperties.get("ro.boot.em.status")) == false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0074, code lost:
    
        r2 = r9.signature;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0078, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r11, r2, "PackageInformation_Token");
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0084, code lost:
    
        if (r9.limit.isEmpty() != false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x008c, code lost:
    
        if (r9.limitCondition.isEmpty() != false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x008e, code lost:
    
        r11 = r9.limit;
        r6 = r9.limitCondition;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0094, code lost:
    
        android.util.Slog.i("PackageInformation_Token", "[Token] checkLimit : " + r11 + " , " + r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00b1, code lost:
    
        if (r11.equals("0") == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00b3, code lost:
    
        r11 = r6.equals(java.lang.Integer.toString(android.os.SystemProperties.getInt("ro.build.version.oneui", 0)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00cb, code lost:
    
        if (r11.equals("1") == false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00cd, code lost:
    
        r11 = r6.equals(java.lang.Integer.toString(android.os.Build.VERSION.SDK_INT));
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00e4, code lost:
    
        r11 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00c3, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00d8, code lost:
    
        com.android.server.NandswapManager$$ExternalSyntheticOutline0.m(r11, new java.lang.StringBuilder("checkLimit error : "), "PackageInformation_Token");
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e3, code lost:
    
        r11 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0114, code lost:
    
        android.util.Slog.i("PackageInformation_Token", "[Token] not exist limit condition ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x011d, code lost:
    
        if (r9.checkExpiredDate(r12) == false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x011f, code lost:
    
        r11 = new java.lang.StringBuilder("[Token] expired date matched");
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x012e, code lost:
    
        if ("0x1".equals(android.os.SystemProperties.get("ro.boot.em.status")) == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0130, code lost:
    
        r4 = " token policy :" + r9.policy;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x013e, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r11, r4, "PackageInformation_Token");
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0141, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0077, code lost:
    
        r2 = "";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean checkTokenConditions(com.android.server.asks.AuthorizedToken r9, java.lang.String r10, android.content.pm.Signature[] r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 350
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.asks.AuthorizedTokenManager.checkTokenConditions(com.android.server.asks.AuthorizedToken, java.lang.String, android.content.pm.Signature[], java.lang.String):boolean");
    }

    public static AuthorizedToken parseXML(XmlPullParser xmlPullParser) {
        AuthorizedToken authorizedToken = new AuthorizedToken();
        authorizedToken.packageName = "";
        authorizedToken.signature = "";
        authorizedToken.policy = "";
        authorizedToken.installAuthority = "";
        authorizedToken.limit = "";
        authorizedToken.limitCondition = "";
        authorizedToken.createData = "";
        authorizedToken.expireDate = "";
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            try {
                String name = xmlPullParser.getName();
                if (eventType == 2) {
                    if (name != null && name.equalsIgnoreCase("PACKAGE")) {
                        authorizedToken.packageName = xmlPullParser.getAttributeValue(null, "name");
                    } else if (name != null && name.equalsIgnoreCase("SIGNATURE")) {
                        authorizedToken.signature = xmlPullParser.getAttributeValue(null, "value");
                    } else if (name != null && name.equalsIgnoreCase("POLICY")) {
                        authorizedToken.policy = xmlPullParser.getAttributeValue(null, "value");
                    } else if (name != null && name.equalsIgnoreCase("INSTALL_AUTHORITY")) {
                        authorizedToken.installAuthority = xmlPullParser.getAttributeValue(null, "value");
                    } else if (name != null && name.equalsIgnoreCase("LIMIT")) {
                        authorizedToken.limit = xmlPullParser.getAttributeValue(null, "value");
                    } else if (name != null && name.equalsIgnoreCase("LIMIT_CONDITION")) {
                        authorizedToken.limitCondition = xmlPullParser.getAttributeValue(null, "value");
                    } else if (name != null && name.equalsIgnoreCase("CREATE_DATE")) {
                        authorizedToken.createData = xmlPullParser.getAttributeValue(null, "value");
                    } else if (name != null && name.equalsIgnoreCase("EXPIRE_DATE")) {
                        authorizedToken.expireDate = xmlPullParser.getAttributeValue(null, "value");
                    }
                }
                eventType = xmlPullParser.next();
            } catch (IOException e) {
                Slog.e("PackageInformation_Token", "parseXML : " + e.getMessage());
            }
        }
        return authorizedToken;
    }
}
