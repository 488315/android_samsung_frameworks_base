package com.android.server.firewall;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.Process;

import com.android.server.am.ActivityManagerService;
import com.android.server.pm.PackageManagerService;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class SenderFilter {
    public static final AnonymousClass1 FACTORY = new AnonymousClass1("sender");
    public static final AnonymousClass2 SIGNATURE;
    public static final AnonymousClass2 SYSTEM;
    public static final AnonymousClass2 SYSTEM_OR_SIGNATURE;
    public static final AnonymousClass2 USER_ID;

    /* renamed from: com.android.server.firewall.SenderFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends FilterFactory {
        @Override // com.android.server.firewall.FilterFactory
        public final Filter newFilter(XmlPullParser xmlPullParser) {
            String attributeValue = xmlPullParser.getAttributeValue(null, "type");
            if (attributeValue == null) {
                throw new XmlPullParserException(
                        "type attribute must be specified for <sender>", xmlPullParser, null);
            }
            if (attributeValue.equals("system")) {
                return SenderFilter.SYSTEM;
            }
            if (attributeValue.equals("signature")) {
                return SenderFilter.SIGNATURE;
            }
            if (attributeValue.equals("system|signature")) {
                return SenderFilter.SYSTEM_OR_SIGNATURE;
            }
            if (attributeValue.equals("userId")) {
                return SenderFilter.USER_ID;
            }
            throw new XmlPullParserException(
                    "Invalid type attribute for <sender>: ".concat(attributeValue),
                    xmlPullParser,
                    null);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.firewall.SenderFilter$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.firewall.SenderFilter$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.firewall.SenderFilter$2] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.firewall.SenderFilter$2] */
    static {
        final int i = 0;
        SIGNATURE =
                new Filter() { // from class: com.android.server.firewall.SenderFilter.2
                    @Override // com.android.server.firewall.Filter
                    public final boolean matches(
                            IntentFirewall intentFirewall,
                            ComponentName componentName,
                            Intent intent,
                            int i2,
                            int i3,
                            String str,
                            int i4) {
                        long clearCallingIdentity;
                        switch (i) {
                            case 0:
                                intentFirewall.getClass();
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    return ((PackageManagerService.PackageManagerInternalImpl)
                                                            intentFirewall.getPackageManager())
                                                    .mService
                                                    .snapshotComputer()
                                                    .checkUidSignaturesForAllUsers(i2, i4)
                                            == 0;
                                } finally {
                                }
                            case 1:
                                return SenderFilter.isPrivilegedApp(
                                        intentFirewall.getPackageManager(), i2, i3);
                            case 2:
                                if (SenderFilter.isPrivilegedApp(
                                        intentFirewall.getPackageManager(), i2, i3)) {
                                    return true;
                                }
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    return ((PackageManagerService.PackageManagerInternalImpl)
                                                            intentFirewall.getPackageManager())
                                                    .mService
                                                    .snapshotComputer()
                                                    .checkUidSignaturesForAllUsers(i2, i4)
                                            == 0;
                                } finally {
                                }
                            default:
                                intentFirewall.mAms.getClass();
                                return ActivityManagerService.checkComponentPermission(
                                                i3, i2, null, 0, i4, false)
                                        == 0;
                        }
                    }
                };
        final int i2 = 1;
        SYSTEM =
                new Filter() { // from class: com.android.server.firewall.SenderFilter.2
                    @Override // com.android.server.firewall.Filter
                    public final boolean matches(
                            IntentFirewall intentFirewall,
                            ComponentName componentName,
                            Intent intent,
                            int i22,
                            int i3,
                            String str,
                            int i4) {
                        long clearCallingIdentity;
                        switch (i2) {
                            case 0:
                                intentFirewall.getClass();
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    return ((PackageManagerService.PackageManagerInternalImpl)
                                                            intentFirewall.getPackageManager())
                                                    .mService
                                                    .snapshotComputer()
                                                    .checkUidSignaturesForAllUsers(i22, i4)
                                            == 0;
                                } finally {
                                }
                            case 1:
                                return SenderFilter.isPrivilegedApp(
                                        intentFirewall.getPackageManager(), i22, i3);
                            case 2:
                                if (SenderFilter.isPrivilegedApp(
                                        intentFirewall.getPackageManager(), i22, i3)) {
                                    return true;
                                }
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    return ((PackageManagerService.PackageManagerInternalImpl)
                                                            intentFirewall.getPackageManager())
                                                    .mService
                                                    .snapshotComputer()
                                                    .checkUidSignaturesForAllUsers(i22, i4)
                                            == 0;
                                } finally {
                                }
                            default:
                                intentFirewall.mAms.getClass();
                                return ActivityManagerService.checkComponentPermission(
                                                i3, i22, null, 0, i4, false)
                                        == 0;
                        }
                    }
                };
        final int i3 = 2;
        SYSTEM_OR_SIGNATURE =
                new Filter() { // from class: com.android.server.firewall.SenderFilter.2
                    @Override // com.android.server.firewall.Filter
                    public final boolean matches(
                            IntentFirewall intentFirewall,
                            ComponentName componentName,
                            Intent intent,
                            int i22,
                            int i32,
                            String str,
                            int i4) {
                        long clearCallingIdentity;
                        switch (i3) {
                            case 0:
                                intentFirewall.getClass();
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    return ((PackageManagerService.PackageManagerInternalImpl)
                                                            intentFirewall.getPackageManager())
                                                    .mService
                                                    .snapshotComputer()
                                                    .checkUidSignaturesForAllUsers(i22, i4)
                                            == 0;
                                } finally {
                                }
                            case 1:
                                return SenderFilter.isPrivilegedApp(
                                        intentFirewall.getPackageManager(), i22, i32);
                            case 2:
                                if (SenderFilter.isPrivilegedApp(
                                        intentFirewall.getPackageManager(), i22, i32)) {
                                    return true;
                                }
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    return ((PackageManagerService.PackageManagerInternalImpl)
                                                            intentFirewall.getPackageManager())
                                                    .mService
                                                    .snapshotComputer()
                                                    .checkUidSignaturesForAllUsers(i22, i4)
                                            == 0;
                                } finally {
                                }
                            default:
                                intentFirewall.mAms.getClass();
                                return ActivityManagerService.checkComponentPermission(
                                                i32, i22, null, 0, i4, false)
                                        == 0;
                        }
                    }
                };
        final int i4 = 3;
        USER_ID =
                new Filter() { // from class: com.android.server.firewall.SenderFilter.2
                    @Override // com.android.server.firewall.Filter
                    public final boolean matches(
                            IntentFirewall intentFirewall,
                            ComponentName componentName,
                            Intent intent,
                            int i22,
                            int i32,
                            String str,
                            int i42) {
                        long clearCallingIdentity;
                        switch (i4) {
                            case 0:
                                intentFirewall.getClass();
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    return ((PackageManagerService.PackageManagerInternalImpl)
                                                            intentFirewall.getPackageManager())
                                                    .mService
                                                    .snapshotComputer()
                                                    .checkUidSignaturesForAllUsers(i22, i42)
                                            == 0;
                                } finally {
                                }
                            case 1:
                                return SenderFilter.isPrivilegedApp(
                                        intentFirewall.getPackageManager(), i22, i32);
                            case 2:
                                if (SenderFilter.isPrivilegedApp(
                                        intentFirewall.getPackageManager(), i22, i32)) {
                                    return true;
                                }
                                clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    return ((PackageManagerService.PackageManagerInternalImpl)
                                                            intentFirewall.getPackageManager())
                                                    .mService
                                                    .snapshotComputer()
                                                    .checkUidSignaturesForAllUsers(i22, i42)
                                            == 0;
                                } finally {
                                }
                            default:
                                intentFirewall.mAms.getClass();
                                return ActivityManagerService.checkComponentPermission(
                                                i32, i22, null, 0, i42, false)
                                        == 0;
                        }
                    }
                };
    }

    public static boolean isPrivilegedApp(
            PackageManagerInternal packageManagerInternal, int i, int i2) {
        if (i == 1000 || i == 0 || i2 == Process.myPid() || i2 == 0) {
            return true;
        }
        return ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal)
                .mService
                .snapshotComputer()
                .isUidPrivileged(i);
    }
}
