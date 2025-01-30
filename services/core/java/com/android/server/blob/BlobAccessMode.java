package com.android.server.blob;

import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Base64;
import android.util.DebugUtils;
import android.util.IndentingPrintWriter;
import com.android.internal.util.XmlUtils;
import com.android.server.LocalServices;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import java.util.Arrays;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class BlobAccessMode {
  public int mAccessType = 1;
  public final ArraySet mAllowedPackages = new ArraySet();

  public void allow(BlobAccessMode blobAccessMode) {
    if ((blobAccessMode.mAccessType & 8) != 0) {
      this.mAllowedPackages.addAll(blobAccessMode.mAllowedPackages);
    }
    this.mAccessType = blobAccessMode.mAccessType | this.mAccessType;
  }

  public void allowPublicAccess() {
    this.mAccessType |= 2;
  }

  public void allowSameSignatureAccess() {
    this.mAccessType |= 4;
  }

  public void allowPackageAccess(String str, byte[] bArr) {
    this.mAccessType |= 8;
    this.mAllowedPackages.add(PackageIdentifier.create(str, bArr));
  }

  public boolean isPublicAccessAllowed() {
    return (this.mAccessType & 2) != 0;
  }

  public boolean isSameSignatureAccessAllowed() {
    return (this.mAccessType & 4) != 0;
  }

  public boolean isPackageAccessAllowed(String str, byte[] bArr) {
    if ((this.mAccessType & 8) == 0) {
      return false;
    }
    return this.mAllowedPackages.contains(PackageIdentifier.create(str, bArr));
  }

  public boolean isAccessAllowedForCaller(Context context, String str, int i, int i2) {
    int i3 = this.mAccessType;
    if ((i3 & 2) != 0) {
      return true;
    }
    if ((i3 & 4) != 0 && checkSignatures(i, i2)) {
      return true;
    }
    if ((this.mAccessType & 8) != 0) {
      PackageManager packageManager =
          context
              .createContextAsUser(UserHandle.of(UserHandle.getUserId(i)), 0)
              .getPackageManager();
      for (int i4 = 0; i4 < this.mAllowedPackages.size(); i4++) {
        PackageIdentifier packageIdentifier = (PackageIdentifier) this.mAllowedPackages.valueAt(i4);
        if (packageIdentifier.packageName.equals(str)
            && packageManager.hasSigningCertificate(str, packageIdentifier.certificate, 1)) {
          return true;
        }
      }
    }
    return false;
  }

  public final boolean checkSignatures(int i, int i2) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
              .checkUidSignaturesForAllUsers(i, i2)
          == 0;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public int getAccessType() {
    return this.mAccessType;
  }

  public int getAllowedPackagesCount() {
    return this.mAllowedPackages.size();
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println(
        "accessType: "
            + DebugUtils.flagsToString(BlobAccessMode.class, "ACCESS_TYPE_", this.mAccessType));
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

  public void writeToXml(XmlSerializer xmlSerializer) {
    XmlUtils.writeIntAttribute(
        xmlSerializer, KnoxAnalyticsDataConverter.TIMESTAMP, this.mAccessType);
    int size = this.mAllowedPackages.size();
    for (int i = 0; i < size; i++) {
      xmlSerializer.startTag(null, "wl");
      PackageIdentifier packageIdentifier = (PackageIdentifier) this.mAllowedPackages.valueAt(i);
      XmlUtils.writeStringAttribute(
          xmlSerializer, KnoxAnalyticsDataConverter.PAYLOAD, packageIdentifier.packageName);
      XmlUtils.writeByteArrayAttribute(xmlSerializer, "ct", packageIdentifier.certificate);
      xmlSerializer.endTag(null, "wl");
    }
  }

  public static BlobAccessMode createFromXml(XmlPullParser xmlPullParser) {
    BlobAccessMode blobAccessMode = new BlobAccessMode();
    blobAccessMode.mAccessType =
        XmlUtils.readIntAttribute(xmlPullParser, KnoxAnalyticsDataConverter.TIMESTAMP);
    int depth = xmlPullParser.getDepth();
    while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
      if ("wl".equals(xmlPullParser.getName())) {
        blobAccessMode.allowPackageAccess(
            XmlUtils.readStringAttribute(xmlPullParser, KnoxAnalyticsDataConverter.PAYLOAD),
            XmlUtils.readByteArrayAttribute(xmlPullParser, "ct"));
      }
    }
    return blobAccessMode;
  }

  public final class PackageIdentifier {
    public final byte[] certificate;
    public final String packageName;

    public PackageIdentifier(String str, byte[] bArr) {
      this.packageName = str;
      this.certificate = bArr;
    }

    public static PackageIdentifier create(String str, byte[] bArr) {
      return new PackageIdentifier(str, bArr);
    }

    public boolean equals(Object obj) {
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

    public int hashCode() {
      return Objects.hash(this.packageName, Integer.valueOf(Arrays.hashCode(this.certificate)));
    }

    public String toString() {
      return "[" + this.packageName + ", " + Base64.encodeToString(this.certificate, 2) + "]";
    }
  }
}
