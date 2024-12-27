package com.android.server.pm;

import android.content.pm.ApplicationInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;

import libcore.io.IoUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SELinuxMMAC {
    public static final List sMacPermissions;
    public static final List sPolicies = new ArrayList();
    public static boolean sPolicyRead;

    static {
        ArrayList arrayList = new ArrayList();
        sMacPermissions = arrayList;
        arrayList.add(
                new File(Environment.getRootDirectory(), "/etc/selinux/plat_mac_permissions.xml"));
        File file =
                new File(
                        Environment.getSystemExtDirectory(),
                        "/etc/selinux/system_ext_mac_permissions.xml");
        if (file.exists()) {
            arrayList.add(file);
        }
        File file2 =
                new File(
                        Environment.getProductDirectory(),
                        "/etc/selinux/product_mac_permissions.xml");
        if (file2.exists()) {
            arrayList.add(file2);
        }
        File file3 =
                new File(
                        Environment.getVendorDirectory(),
                        "/etc/selinux/vendor_mac_permissions.xml");
        if (file3.exists()) {
            arrayList.add(file3);
        }
        File file4 =
                new File(Environment.getOdmDirectory(), "/etc/selinux/odm_mac_permissions.xml");
        if (file4.exists()) {
            arrayList.add(file4);
        }
    }

    public static String getSeInfo(
            PackageStateInternal packageStateInternal,
            AndroidPackage androidPackage,
            SharedUserSetting sharedUserSetting,
            PlatformCompat platformCompat) {
        int max;
        if (sharedUserSetting == null || sharedUserSetting.getPackages().size() == 0) {
            ApplicationInfo appInfoWithoutState =
                    ((AndroidPackageHidden) androidPackage).toAppInfoWithoutState();
            max =
                    platformCompat.isChangeEnabledInternal(143539591L, appInfoWithoutState)
                            ? Math.max(10000, androidPackage.getTargetSdkVersion())
                            : platformCompat.isChangeEnabledInternal(
                                            168782947L, appInfoWithoutState)
                                    ? Math.max(30, androidPackage.getTargetSdkVersion())
                                    : androidPackage.getTargetSdkVersion();
        } else {
            max = sharedUserSetting.seInfoTargetSdkVersion;
        }
        return getSeInfo(
                packageStateInternal,
                androidPackage,
                sharedUserSetting != null
                        ? sharedUserSetting.isPrivileged() | packageStateInternal.isPrivileged()
                        : packageStateInternal.isPrivileged(),
                max);
    }

    public static String getSeInfo(
            PackageStateInternal packageStateInternal,
            AndroidPackage androidPackage,
            boolean z,
            int i) {
        String str;
        List list = sPolicies;
        synchronized (list) {
            try {
                str = null;
                if (sPolicyRead) {
                    Iterator it = ((ArrayList) list).iterator();
                    while (it.hasNext()
                            && (str = ((Policy) it.next()).getMatchedSeInfo(androidPackage))
                                    == null) {}
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (str == null) {
            str = "default";
        }
        if (z) {
            str = str.concat(":privapp");
        }
        String m = VpnManagerService$$ExternalSyntheticOutline0.m(i, str, ":targetSdkVersion=");
        String str2 =
                packageStateInternal.isSystemExt()
                        ? "system_ext"
                        : packageStateInternal.isProduct()
                                ? "product"
                                : packageStateInternal.isVendor()
                                        ? "vendor"
                                        : packageStateInternal.isOem()
                                                ? "oem"
                                                : packageStateInternal.isOdm()
                                                        ? "odm"
                                                        : packageStateInternal.isSystem()
                                                                ? "system"
                                                                : "";
        return !str2.isEmpty()
                ? AnyMotionDetector$$ExternalSyntheticOutline0.m(m, ":partition=", str2)
                : m;
    }

    public static void readInstallPolicy() {
        synchronized (sPolicies) {
            try {
                if (sPolicyRead) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                XmlPullParser newPullParser = Xml.newPullParser();
                int size = ((ArrayList) sMacPermissions).size();
                FileReader fileReader = null;
                int i = 0;
                while (i < size) {
                    File file = (File) ((ArrayList) sMacPermissions).get(i);
                    try {
                        try {
                            FileReader fileReader2 = new FileReader(file);
                            try {
                                Slog.d("SELinuxMMAC", "Using policy file " + file);
                                newPullParser.setInput(fileReader2);
                                newPullParser.nextTag();
                                newPullParser.require(2, null, "policy");
                                while (newPullParser.next() != 3) {
                                    if (newPullParser.getEventType() == 2) {
                                        String name = newPullParser.getName();
                                        if (name.hashCode() == -902467798
                                                && name.equals("signer")) {
                                            arrayList.add(readSignerOrThrow(newPullParser));
                                        }
                                        skip(newPullParser);
                                    }
                                }
                                IoUtils.closeQuietly(fileReader2);
                                i++;
                                fileReader = fileReader2;
                            } catch (IOException e) {
                                e = e;
                                fileReader = fileReader2;
                                Slog.w("SELinuxMMAC", "Exception parsing " + file, e);
                                IoUtils.closeQuietly(fileReader);
                                return;
                            } catch (IllegalArgumentException
                                    | IllegalStateException
                                    | XmlPullParserException e2) {
                                e = e2;
                                fileReader = fileReader2;
                                Slog.w(
                                        "SELinuxMMAC",
                                        "Exception @"
                                                + newPullParser.getPositionDescription()
                                                + " while parsing "
                                                + file
                                                + ":"
                                                + e);
                                IoUtils.closeQuietly(fileReader);
                                return;
                            } catch (Throwable th) {
                                th = th;
                                fileReader = fileReader2;
                                IoUtils.closeQuietly(fileReader);
                                throw th;
                            }
                        } catch (IOException e3) {
                            e = e3;
                        } catch (IllegalArgumentException
                                | IllegalStateException
                                | XmlPullParserException e4) {
                            e = e4;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
                PolicyComparator policyComparator = new PolicyComparator();
                policyComparator.duplicateFound = false;
                Collections.sort(arrayList, policyComparator);
                if (policyComparator.duplicateFound) {
                    Slog.w(
                            "SELinuxMMAC",
                            "ERROR! Duplicate entries found parsing mac_permissions.xml files");
                    return;
                }
                List list = sPolicies;
                synchronized (list) {
                    ((ArrayList) list).clear();
                    ((ArrayList) list).addAll(arrayList);
                    sPolicyRead = true;
                }
            } finally {
            }
        }
    }

    public static Policy readSignerOrThrow(XmlPullParser xmlPullParser) {
        xmlPullParser.require(2, null, "signer");
        Policy.PolicyBuilder policyBuilder = new Policy.PolicyBuilder();
        String attributeValue = xmlPullParser.getAttributeValue(null, "signature");
        if (attributeValue != null) {
            policyBuilder.addSignature(attributeValue);
        }
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if ("seinfo".equals(name)) {
                    String attributeValue2 = xmlPullParser.getAttributeValue(null, "value");
                    if (attributeValue2 == null || !attributeValue2.matches("\\A[\\.\\w]+\\z")) {
                        throw new IllegalArgumentException(
                                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                        "Invalid seinfo value ", attributeValue2));
                    }
                    String str = policyBuilder.mSeinfo;
                    if (str != null && !str.equals(attributeValue2)) {
                        throw new IllegalStateException("Duplicate seinfo tag found");
                    }
                    policyBuilder.mSeinfo = attributeValue2;
                    xmlPullParser.require(2, null, "seinfo");
                    xmlPullParser.nextTag();
                } else if ("package".equals(name)) {
                    xmlPullParser.require(2, null, "package");
                    String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                    while (xmlPullParser.next() != 3) {
                        if (xmlPullParser.getEventType() == 2) {
                            if ("seinfo".equals(xmlPullParser.getName())) {
                                String attributeValue4 =
                                        xmlPullParser.getAttributeValue(null, "value");
                                if (attributeValue3 == null
                                        || !attributeValue3.matches("\\A[\\.\\w]+\\z")) {
                                    throw new IllegalArgumentException(
                                            ConnectivityModuleConnector$$ExternalSyntheticOutline0
                                                    .m("Invalid package name ", attributeValue3));
                                }
                                if (attributeValue4 == null
                                        || !attributeValue4.matches("\\A[\\.\\w]+\\z")) {
                                    throw new IllegalArgumentException(
                                            ConnectivityModuleConnector$$ExternalSyntheticOutline0
                                                    .m("Invalid seinfo value ", attributeValue4));
                                }
                                String str2 =
                                        (String)
                                                ((HashMap) policyBuilder.mPkgMap)
                                                        .get(attributeValue3);
                                if (str2 != null && !str2.equals(attributeValue4)) {
                                    throw new IllegalStateException(
                                            "Conflicting seinfo value found");
                                }
                                ((HashMap) policyBuilder.mPkgMap)
                                        .put(attributeValue3, attributeValue4);
                                xmlPullParser.require(2, null, "seinfo");
                                xmlPullParser.nextTag();
                            } else {
                                skip(xmlPullParser);
                            }
                        }
                    }
                } else if ("cert".equals(name)) {
                    policyBuilder.addSignature(xmlPullParser.getAttributeValue(null, "signature"));
                    xmlPullParser.require(2, null, "cert");
                    xmlPullParser.nextTag();
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        Policy policy = new Policy(policyBuilder);
        if (policy.mCerts.isEmpty()) {
            throw new IllegalStateException(
                    "Missing certs with signer tag. Expecting at least one.");
        }
        if ((policy.mSeinfo == null) ^ policy.mPkgMap.isEmpty()) {
            return policy;
        }
        throw new IllegalStateException(
                "Only seinfo tag XOR package tags are allowed within a signer stanza.");
    }

    public static void skip(XmlPullParser xmlPullParser) {
        if (xmlPullParser.getEventType() != 2) {
            throw new IllegalStateException();
        }
        int i = 1;
        while (i != 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }
}
