package com.android.server.pm;

import android.content.pm.Signature;
import android.content.pm.SigningDetails;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.security.cert.CertificateException;
import java.util.ArrayList;

public final class PackageSignatures {
    public SigningDetails mSigningDetails = SigningDetails.UNKNOWN;

    /* JADX WARN: Removed duplicated region for block: B:36:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int readCertsListXml(
            com.android.modules.utils.TypedXmlPullParser r18,
            java.util.ArrayList r19,
            java.util.ArrayList r20,
            int r21,
            boolean r22,
            android.content.pm.SigningDetails.Builder r23) {
        /*
            Method dump skipped, instructions count: 575
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageSignatures.readCertsListXml(com.android.modules.utils.TypedXmlPullParser,"
                    + " java.util.ArrayList, java.util.ArrayList, int, boolean,"
                    + " android.content.pm.SigningDetails$Builder):int");
    }

    public static void writeCertsListXml(
            TypedXmlSerializer typedXmlSerializer,
            ArrayList arrayList,
            Signature[] signatureArr,
            boolean z) {
        for (Signature signature : signatureArr) {
            typedXmlSerializer.startTag((String) null, "cert");
            int hashCode = signature.hashCode();
            int size = arrayList.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                Signature signature2 = (Signature) arrayList.get(i);
                if (signature2.hashCode() == hashCode && signature2.equals(signature)) {
                    typedXmlSerializer.attributeInt(
                            (String) null, LauncherConfigurationInternal.KEY_INDEX_INT, i);
                    break;
                }
                i++;
            }
            if (i >= size) {
                arrayList.add(signature);
                typedXmlSerializer.attributeInt(
                        (String) null, LauncherConfigurationInternal.KEY_INDEX_INT, size);
                signature.writeToXmlAttributeBytesHex(typedXmlSerializer, null, "key");
            }
            if (z) {
                typedXmlSerializer.attributeInt((String) null, "flags", signature.getFlags());
            }
            typedXmlSerializer.endTag((String) null, "cert");
        }
    }

    public final void readXml(TypedXmlPullParser typedXmlPullParser, ArrayList arrayList) {
        SigningDetails.Builder builder = new SigningDetails.Builder();
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "count", -1);
        if (attributeInt == -1) {
            String m =
                    CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(
                            typedXmlPullParser,
                            new StringBuilder(
                                    "Error in package manager settings: <sigs> has no count at "));
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(5, m);
            XmlUtils.skipCurrentTag(typedXmlPullParser);
            return;
        }
        int attributeInt2 = typedXmlPullParser.getAttributeInt((String) null, "schemeVersion", 0);
        if (attributeInt2 == 0) {
            String m2 =
                    CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(
                            typedXmlPullParser,
                            new StringBuilder(
                                    "Error in package manager settings: <sigs> has no schemeVersion"
                                        + " at "));
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(5, m2);
        }
        builder.setSignatureSchemeVersion(attributeInt2);
        ArrayList arrayList2 = new ArrayList();
        int readCertsListXml =
                readCertsListXml(
                        typedXmlPullParser, arrayList, arrayList2, attributeInt, false, builder);
        builder.setSignatures((Signature[]) arrayList2.toArray(new Signature[arrayList2.size()]));
        if (readCertsListXml < attributeInt) {
            String m3 =
                    CrossProfileIntentFilter$$ExternalSyntheticOutline0.m(
                            typedXmlPullParser,
                            new StringBuilder(
                                    "Error in package manager settings: <sigs> count does not match"
                                        + " number of  <cert> entries"));
            boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(5, m3);
        }
        try {
            this.mSigningDetails = builder.build();
        } catch (CertificateException unused) {
            boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
            PackageManagerServiceUtils.logCriticalInfo(
                    5,
                    "Error in package manager settings: <sigs> unable to convert certificate(s) to"
                        + " public key(s).");
            this.mSigningDetails = SigningDetails.UNKNOWN;
        }
    }

    public final String toString() {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "PackageSignatures{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(" version:");
        m.append(this.mSigningDetails.getSignatureSchemeVersion());
        m.append(", signatures:[");
        if (this.mSigningDetails.getSignatures() != null) {
            for (int i = 0; i < this.mSigningDetails.getSignatures().length; i++) {
                if (i > 0) {
                    m.append(", ");
                }
                m.append(Integer.toHexString(this.mSigningDetails.getSignatures()[i].hashCode()));
            }
        }
        m.append("], past signatures:[");
        if (this.mSigningDetails.getPastSigningCertificates() != null) {
            for (int i2 = 0; i2 < this.mSigningDetails.getPastSigningCertificates().length; i2++) {
                if (i2 > 0) {
                    m.append(", ");
                }
                m.append(
                        Integer.toHexString(
                                this.mSigningDetails.getPastSigningCertificates()[i2].hashCode()));
                m.append(" flags: ");
                m.append(
                        Integer.toHexString(
                                this.mSigningDetails.getPastSigningCertificates()[i2].getFlags()));
            }
        }
        m.append("]}");
        return m.toString();
    }

    public final void writeXml(
            TypedXmlSerializer typedXmlSerializer, String str, ArrayList arrayList) {
        if (this.mSigningDetails.getSignatures() == null) {
            return;
        }
        typedXmlSerializer.startTag((String) null, str);
        typedXmlSerializer.attributeInt(
                (String) null, "count", this.mSigningDetails.getSignatures().length);
        typedXmlSerializer.attributeInt(
                (String) null, "schemeVersion", this.mSigningDetails.getSignatureSchemeVersion());
        writeCertsListXml(
                typedXmlSerializer, arrayList, this.mSigningDetails.getSignatures(), false);
        if (this.mSigningDetails.getPastSigningCertificates() != null) {
            typedXmlSerializer.startTag((String) null, "pastSigs");
            typedXmlSerializer.attributeInt(
                    (String) null,
                    "count",
                    this.mSigningDetails.getPastSigningCertificates().length);
            writeCertsListXml(
                    typedXmlSerializer,
                    arrayList,
                    this.mSigningDetails.getPastSigningCertificates(),
                    true);
            typedXmlSerializer.endTag((String) null, "pastSigs");
        }
        typedXmlSerializer.endTag((String) null, str);
    }
}
