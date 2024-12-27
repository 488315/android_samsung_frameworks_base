package com.android.server.blob;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.Binder;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Base64;
import android.util.DebugUtils;
import android.util.IndentingPrintWriter;

import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.pm.PackageManagerService;

import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BlobAccessMode {
    public int mAccessType = 1;
    public final ArraySet mAllowedPackages = new ArraySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageIdentifier {
        public final byte[] certificate;
        public final String packageName;

        public PackageIdentifier(String str, byte[] bArr) {
            this.packageName = str;
            this.certificate = bArr;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof PackageIdentifier)) {
                return false;
            }
            PackageIdentifier packageIdentifier = (PackageIdentifier) obj;
            return this.packageName.equals(packageIdentifier.packageName)
                    && Arrays.equals(this.certificate, packageIdentifier.certificate);
        }

        public final int hashCode() {
            return Objects.hash(
                    this.packageName, Integer.valueOf(Arrays.hashCode(this.certificate)));
        }

        public final String toString() {
            return "[" + this.packageName + ", " + Base64.encodeToString(this.certificate, 2) + "]";
        }
    }

    public static BlobAccessMode createFromXml(XmlPullParser xmlPullParser) {
        BlobAccessMode blobAccessMode = new BlobAccessMode();
        blobAccessMode.mAccessType =
                XmlUtils.readIntAttribute(xmlPullParser, KnoxAnalyticsDataConverter.TIMESTAMP);
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("wl".equals(xmlPullParser.getName())) {
                String readStringAttribute =
                        XmlUtils.readStringAttribute(
                                xmlPullParser, KnoxAnalyticsDataConverter.PAYLOAD);
                byte[] readByteArrayAttribute =
                        XmlUtils.readByteArrayAttribute(xmlPullParser, "ct");
                blobAccessMode.mAccessType |= 8;
                blobAccessMode.mAllowedPackages.add(
                        new PackageIdentifier(readStringAttribute, readByteArrayAttribute));
            }
        }
        return blobAccessMode;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println(
                "accessType: "
                        + DebugUtils.flagsToString(
                                BlobAccessMode.class, "ACCESS_TYPE_", this.mAccessType));
        indentingPrintWriter.print("Explicitly allowed pkgs:");
        if (this.mAllowedPackages.isEmpty()) {
            indentingPrintWriter.println(" (Empty)");
            return;
        }
        indentingPrintWriter.increaseIndent();
        int size = this.mAllowedPackages.size();
        for (int i = 0; i < size; i++) {
            indentingPrintWriter.println(
                    ((PackageIdentifier) this.mAllowedPackages.valueAt(i)).toString());
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final boolean isAccessAllowedForCaller(Context context, String str, int i, int i2) {
        int i3 = this.mAccessType;
        if ((i3 & 2) != 0) {
            return true;
        }
        if ((i3 & 4) != 0) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (((PackageManagerService.PackageManagerInternalImpl)
                                        ((PackageManagerInternal)
                                                LocalServices.getService(
                                                        PackageManagerInternal.class)))
                                .mService
                                .snapshotComputer()
                                .checkUidSignaturesForAllUsers(i, i2)
                        == 0) {
                    return true;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if ((this.mAccessType & 8) != 0) {
            PackageManager packageManager =
                    context.createContextAsUser(UserHandle.of(UserHandle.getUserId(i)), 0)
                            .getPackageManager();
            for (int i4 = 0; i4 < this.mAllowedPackages.size(); i4++) {
                PackageIdentifier packageIdentifier =
                        (PackageIdentifier) this.mAllowedPackages.valueAt(i4);
                if (packageIdentifier.packageName.equals(str)
                        && packageManager.hasSigningCertificate(
                                str, packageIdentifier.certificate, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void writeToXml(XmlSerializer xmlSerializer) {
        XmlUtils.writeIntAttribute(
                xmlSerializer, KnoxAnalyticsDataConverter.TIMESTAMP, this.mAccessType);
        int size = this.mAllowedPackages.size();
        for (int i = 0; i < size; i++) {
            FastXmlSerializer fastXmlSerializer = (FastXmlSerializer) xmlSerializer;
            fastXmlSerializer.startTag((String) null, "wl");
            PackageIdentifier packageIdentifier =
                    (PackageIdentifier) this.mAllowedPackages.valueAt(i);
            XmlUtils.writeStringAttribute(
                    xmlSerializer,
                    KnoxAnalyticsDataConverter.PAYLOAD,
                    packageIdentifier.packageName);
            XmlUtils.writeByteArrayAttribute(xmlSerializer, "ct", packageIdentifier.certificate);
            fastXmlSerializer.endTag((String) null, "wl");
        }
    }
}
