package com.android.server.security;

import com.android.framework.protobuf.ByteString;
import com.android.internal.org.bouncycastle.asn1.ASN1Boolean;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;
import com.android.internal.util.FrameworkStatsLog;

import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AndroidKeystoreAttestationVerificationAttributes {
    public Map mApplicationPackageNameVersion;
    public ByteString mAttestationChallenge;
    public boolean mAttestationHardwareBacked;
    public Integer mAttestationVersion;
    public Integer mKeyBootPatchLevel;
    public Integer mKeyOsPatchLevel;
    public Integer mKeyOsVersion;
    public Integer mKeyVendorPatchLevel;
    public boolean mKeymasterHardwareBacked;
    public Integer mKeymasterVersion;
    public Boolean mVerifiedBootLocked;
    public VerifiedBootState mVerifiedBootState;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class SecurityLevel {
        public static final /* synthetic */ SecurityLevel[] $VALUES;
        public static final SecurityLevel SOFTWARE;
        public static final SecurityLevel STRONG_BOX;
        public static final SecurityLevel TRUSTED_ENVIRONMENT;

        static {
            SecurityLevel securityLevel = new SecurityLevel("SOFTWARE", 0);
            SOFTWARE = securityLevel;
            SecurityLevel securityLevel2 = new SecurityLevel("TRUSTED_ENVIRONMENT", 1);
            TRUSTED_ENVIRONMENT = securityLevel2;
            SecurityLevel securityLevel3 = new SecurityLevel("STRONG_BOX", 2);
            STRONG_BOX = securityLevel3;
            $VALUES = new SecurityLevel[] {securityLevel, securityLevel2, securityLevel3};
        }

        public static SecurityLevel valueOf(String str) {
            return (SecurityLevel) Enum.valueOf(SecurityLevel.class, str);
        }

        public static SecurityLevel[] values() {
            return (SecurityLevel[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class VerifiedBootState {
        public static final /* synthetic */ VerifiedBootState[] $VALUES;
        public static final VerifiedBootState FAILED;
        public static final VerifiedBootState SELF_SIGNED;
        public static final VerifiedBootState UNVERIFIED;
        public static final VerifiedBootState VERIFIED;

        static {
            VerifiedBootState verifiedBootState = new VerifiedBootState("VERIFIED", 0);
            VERIFIED = verifiedBootState;
            VerifiedBootState verifiedBootState2 = new VerifiedBootState("SELF_SIGNED", 1);
            SELF_SIGNED = verifiedBootState2;
            VerifiedBootState verifiedBootState3 = new VerifiedBootState("UNVERIFIED", 2);
            UNVERIFIED = verifiedBootState3;
            VerifiedBootState verifiedBootState4 = new VerifiedBootState("FAILED", 3);
            FAILED = verifiedBootState4;
            $VALUES =
                    new VerifiedBootState[] {
                        verifiedBootState,
                        verifiedBootState2,
                        verifiedBootState3,
                        verifiedBootState4
                    };
        }

        public static VerifiedBootState valueOf(String str) {
            return (VerifiedBootState) Enum.valueOf(VerifiedBootState.class, str);
        }

        public static VerifiedBootState[] values() {
            return (VerifiedBootState[]) $VALUES.clone();
        }
    }

    public static AndroidKeystoreAttestationVerificationAttributes fromCertificate(
            X509Certificate x509Certificate) {
        SecurityLevel securityLevel;
        VerifiedBootState verifiedBootState;
        AndroidKeystoreAttestationVerificationAttributes
                androidKeystoreAttestationVerificationAttributes =
                        new AndroidKeystoreAttestationVerificationAttributes();
        androidKeystoreAttestationVerificationAttributes.mAttestationVersion = null;
        androidKeystoreAttestationVerificationAttributes.mAttestationHardwareBacked = false;
        androidKeystoreAttestationVerificationAttributes.mKeymasterVersion = null;
        androidKeystoreAttestationVerificationAttributes.mKeymasterHardwareBacked = false;
        androidKeystoreAttestationVerificationAttributes.mAttestationChallenge = null;
        androidKeystoreAttestationVerificationAttributes.mKeyBootPatchLevel = null;
        androidKeystoreAttestationVerificationAttributes.mKeyOsPatchLevel = null;
        androidKeystoreAttestationVerificationAttributes.mKeyOsVersion = null;
        androidKeystoreAttestationVerificationAttributes.mKeyVendorPatchLevel = null;
        androidKeystoreAttestationVerificationAttributes.mVerifiedBootLocked = null;
        androidKeystoreAttestationVerificationAttributes.mVerifiedBootState = null;
        androidKeystoreAttestationVerificationAttributes.mApplicationPackageNameVersion = null;
        ASN1Sequence extensionParsedValue =
                Certificate.getInstance(
                                new ASN1InputStream(x509Certificate.getEncoded()).readObject())
                        .getTBSCertificate()
                        .getExtensions()
                        .getExtensionParsedValue(
                                new ASN1ObjectIdentifier("1.3.6.1.4.1.11129.2.1.17"));
        if (extensionParsedValue == null) {
            throw new CertificateEncodingException(
                    "No attestation extension found in certificate.");
        }
        androidKeystoreAttestationVerificationAttributes.mAttestationVersion =
                Integer.valueOf(getIntegerFromAsn1(extensionParsedValue.getObjectAt(0)));
        int intValueExact = extensionParsedValue.getObjectAt(1).getValue().intValueExact();
        SecurityLevel securityLevel2 = SecurityLevel.SOFTWARE;
        SecurityLevel securityLevel3 = SecurityLevel.TRUSTED_ENVIRONMENT;
        SecurityLevel securityLevel4 = SecurityLevel.STRONG_BOX;
        if (intValueExact == 0) {
            securityLevel = securityLevel2;
        } else if (intValueExact == 1) {
            securityLevel = securityLevel3;
        } else {
            if (intValueExact != 2) {
                throw new IllegalArgumentException("Invalid security level.");
            }
            securityLevel = securityLevel4;
        }
        androidKeystoreAttestationVerificationAttributes.mAttestationHardwareBacked =
                securityLevel == securityLevel3;
        androidKeystoreAttestationVerificationAttributes.mAttestationChallenge =
                getOctetsFromAsn1(extensionParsedValue.getObjectAt(4));
        androidKeystoreAttestationVerificationAttributes.mKeymasterVersion =
                Integer.valueOf(getIntegerFromAsn1(extensionParsedValue.getObjectAt(2)));
        getOctetsFromAsn1(extensionParsedValue.getObjectAt(5));
        int intValueExact2 = extensionParsedValue.getObjectAt(3).getValue().intValueExact();
        if (intValueExact2 != 0) {
            if (intValueExact2 == 1) {
                securityLevel2 = securityLevel3;
            } else {
                if (intValueExact2 != 2) {
                    throw new IllegalArgumentException("Invalid security level.");
                }
                securityLevel2 = securityLevel4;
            }
        }
        androidKeystoreAttestationVerificationAttributes.mKeymasterHardwareBacked =
                securityLevel2 == securityLevel3;
        for (ASN1TaggedObject aSN1TaggedObject : extensionParsedValue.getObjectAt(6).toArray()) {
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 509) {
                ASN1Boolean object = aSN1TaggedObject.getObject();
                if (object instanceof ASN1Boolean) {
                    object.isTrue();
                }
            } else if (tagNo == 709) {
                ASN1Sequence aSN1Sequence =
                        ASN1Sequence.getInstance(
                                new ASN1InputStream(
                                                getOctetsFromAsn1(aSN1TaggedObject.getObject())
                                                        .toByteArray())
                                        .readObject());
                HashMap hashMap = new HashMap();
                for (ASN1Sequence aSN1Sequence2 : aSN1Sequence.getObjectAt(0).toArray()) {
                    hashMap.put(
                            getUtf8FromOctetsFromAsn1(aSN1Sequence2.getObjectAt(0)),
                            Long.valueOf(aSN1Sequence2.getObjectAt(1).getValue().longValueExact()));
                }
                ArrayList arrayList = new ArrayList();
                for (ASN1Encodable aSN1Encodable : aSN1Sequence.getObjectAt(1).toArray()) {
                    arrayList.add(getOctetsFromAsn1(aSN1Encodable));
                }
                androidKeystoreAttestationVerificationAttributes.mApplicationPackageNameVersion =
                        Collections.unmodifiableMap(hashMap);
                Collections.unmodifiableList(arrayList);
            }
        }
        for (ASN1TaggedObject aSN1TaggedObject2 : extensionParsedValue.getObjectAt(7).toArray()) {
            int tagNo2 = aSN1TaggedObject2.getTagNo();
            if (tagNo2 == 718) {
                androidKeystoreAttestationVerificationAttributes.mKeyVendorPatchLevel =
                        Integer.valueOf(getIntegerFromAsn1(aSN1TaggedObject2.getObject()));
            } else if (tagNo2 != 719) {
                switch (tagNo2) {
                    case 704:
                        ASN1Sequence object2 = aSN1TaggedObject2.getObject();
                        getOctetsFromAsn1(object2.getObjectAt(0));
                        ASN1Boolean objectAt = object2.getObjectAt(1);
                        androidKeystoreAttestationVerificationAttributes.mVerifiedBootLocked =
                                objectAt instanceof ASN1Boolean
                                        ? Boolean.valueOf(objectAt.isTrue())
                                        : null;
                        int intValueExact3 = object2.getObjectAt(2).getValue().intValueExact();
                        if (intValueExact3 == 0) {
                            verifiedBootState = VerifiedBootState.VERIFIED;
                        } else if (intValueExact3 == 1) {
                            verifiedBootState = VerifiedBootState.SELF_SIGNED;
                        } else if (intValueExact3 == 2) {
                            verifiedBootState = VerifiedBootState.UNVERIFIED;
                        } else {
                            if (intValueExact3 != 3) {
                                throw new IllegalArgumentException("Invalid verified boot state.");
                            }
                            verifiedBootState = VerifiedBootState.FAILED;
                        }
                        androidKeystoreAttestationVerificationAttributes.mVerifiedBootState =
                                verifiedBootState;
                        if (androidKeystoreAttestationVerificationAttributes.mAttestationVersion
                                        .intValue()
                                >= 3) {
                            getOctetsFromAsn1(object2.getObjectAt(3));
                            break;
                        } else {
                            break;
                        }
                    case FrameworkStatsLog.DREAM_SETTING_CHANGED /* 705 */:
                        androidKeystoreAttestationVerificationAttributes.mKeyOsVersion =
                                Integer.valueOf(getIntegerFromAsn1(aSN1TaggedObject2.getObject()));
                        break;
                    case 706:
                        androidKeystoreAttestationVerificationAttributes.mKeyOsPatchLevel =
                                Integer.valueOf(getIntegerFromAsn1(aSN1TaggedObject2.getObject()));
                        break;
                    default:
                        switch (tagNo2) {
                            case 710:
                                getUtf8FromOctetsFromAsn1(aSN1TaggedObject2.getObject());
                                break;
                            case 711:
                                getUtf8FromOctetsFromAsn1(aSN1TaggedObject2.getObject());
                                break;
                            case 712:
                                getUtf8FromOctetsFromAsn1(aSN1TaggedObject2.getObject());
                                break;
                        }
                }
            } else {
                androidKeystoreAttestationVerificationAttributes.mKeyBootPatchLevel =
                        Integer.valueOf(getIntegerFromAsn1(aSN1TaggedObject2.getObject()));
            }
        }
        return androidKeystoreAttestationVerificationAttributes;
    }

    public static int getIntegerFromAsn1(ASN1Encodable aSN1Encodable) {
        return ((ASN1Integer) aSN1Encodable).getValue().intValueExact();
    }

    public static ByteString getOctetsFromAsn1(ASN1Encodable aSN1Encodable) {
        return ByteString.copyFrom(((ASN1OctetString) aSN1Encodable).getOctets());
    }

    public static String getUtf8FromOctetsFromAsn1(ASN1Encodable aSN1Encodable) {
        return new String(((ASN1OctetString) aSN1Encodable).getOctets(), StandardCharsets.UTF_8);
    }
}
