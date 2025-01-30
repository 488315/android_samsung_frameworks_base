package com.android.server.security;

import com.android.framework.protobuf.ByteString;
import com.android.internal.org.bouncycastle.asn1.ASN1Boolean;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Enumerated;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.internal.org.bouncycastle.asn1.x509.Certificate;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class AndroidKeystoreAttestationVerificationAttributes {
    public ByteString mAttestationChallenge;
    public boolean mAttestationHardwareBacked;
    public SecurityLevel mAttestationSecurityLevel;
    public Integer mAttestationVersion;
    public String mDeviceBrand;
    public String mDeviceName;
    public String mDeviceProductName;
    public boolean mKeyAllowedForAllApplications;
    public Integer mKeyAuthenticatorType;
    public Integer mKeyBootPatchLevel;
    public Integer mKeyOsPatchLevel;
    public Integer mKeyOsVersion;
    public Boolean mKeyRequiresUnlockedDevice;
    public Integer mKeyVendorPatchLevel;
    public boolean mKeymasterHardwareBacked;
    public SecurityLevel mKeymasterSecurityLevel;
    public ByteString mKeymasterUniqueId;
    public Integer mKeymasterVersion;
    public ByteString mVerifiedBootHash;
    public ByteString mVerifiedBootKey;
    public Boolean mVerifiedBootLocked;
    public VerifiedBootState mVerifiedBootState;
    public Map mApplicationPackageNameVersion = null;
    public List mApplicationCertificateDigests = null;

    enum SecurityLevel {
        SOFTWARE,
        TRUSTED_ENVIRONMENT,
        STRONG_BOX
    }

    enum VerifiedBootState {
        VERIFIED,
        SELF_SIGNED,
        UNVERIFIED,
        FAILED
    }

    public static AndroidKeystoreAttestationVerificationAttributes fromCertificate(X509Certificate x509Certificate) {
        return new AndroidKeystoreAttestationVerificationAttributes(x509Certificate);
    }

    public int getAttestationVersion() {
        return this.mAttestationVersion.intValue();
    }

    public boolean isAttestationHardwareBacked() {
        return this.mAttestationHardwareBacked;
    }

    public int getKeymasterVersion() {
        return this.mKeymasterVersion.intValue();
    }

    public boolean isKeymasterHardwareBacked() {
        return this.mKeymasterHardwareBacked;
    }

    public ByteString getAttestationChallenge() {
        return this.mAttestationChallenge;
    }

    public int getKeyBootPatchLevel() {
        Integer num = this.mKeyBootPatchLevel;
        if (num == null) {
            throw new IllegalStateException("KeyBootPatchLevel is not set.");
        }
        return num.intValue();
    }

    public int getKeyOsPatchLevel() {
        Integer num = this.mKeyOsPatchLevel;
        if (num == null) {
            throw new IllegalStateException("KeyOsPatchLevel is not set.");
        }
        return num.intValue();
    }

    public int getKeyVendorPatchLevel() {
        Integer num = this.mKeyVendorPatchLevel;
        if (num == null) {
            throw new IllegalStateException("KeyVendorPatchLevel is not set.");
        }
        return num.intValue();
    }

    public int getKeyOsVersion() {
        Integer num = this.mKeyOsVersion;
        if (num == null) {
            throw new IllegalStateException("KeyOsVersion is not set.");
        }
        return num.intValue();
    }

    public boolean isVerifiedBootLocked() {
        Boolean bool = this.mVerifiedBootLocked;
        if (bool == null) {
            throw new IllegalStateException("VerifiedBootLocked is not set.");
        }
        return bool.booleanValue();
    }

    public VerifiedBootState getVerifiedBootState() {
        return this.mVerifiedBootState;
    }

    public Map getApplicationPackageNameVersion() {
        return Collections.unmodifiableMap(this.mApplicationPackageNameVersion);
    }

    public AndroidKeystoreAttestationVerificationAttributes(X509Certificate x509Certificate) {
        this.mAttestationVersion = null;
        this.mAttestationSecurityLevel = null;
        this.mAttestationHardwareBacked = false;
        this.mKeymasterVersion = null;
        this.mKeymasterSecurityLevel = null;
        this.mKeymasterHardwareBacked = false;
        this.mAttestationChallenge = null;
        this.mKeymasterUniqueId = null;
        this.mDeviceBrand = null;
        this.mDeviceName = null;
        this.mDeviceProductName = null;
        this.mKeyAllowedForAllApplications = false;
        this.mKeyAuthenticatorType = null;
        this.mKeyBootPatchLevel = null;
        this.mKeyOsPatchLevel = null;
        this.mKeyOsVersion = null;
        this.mKeyVendorPatchLevel = null;
        this.mKeyRequiresUnlockedDevice = null;
        this.mVerifiedBootHash = null;
        this.mVerifiedBootKey = null;
        this.mVerifiedBootLocked = null;
        this.mVerifiedBootState = null;
        ASN1Sequence extensionParsedValue = Certificate.getInstance(new ASN1InputStream(x509Certificate.getEncoded()).readObject()).getTBSCertificate().getExtensions().getExtensionParsedValue(new ASN1ObjectIdentifier("1.3.6.1.4.1.11129.2.1.17"));
        if (extensionParsedValue == null) {
            throw new CertificateEncodingException("No attestation extension found in certificate.");
        }
        this.mAttestationVersion = Integer.valueOf(getIntegerFromAsn1(extensionParsedValue.getObjectAt(0)));
        SecurityLevel securityLevelEnum = getSecurityLevelEnum(extensionParsedValue.getObjectAt(1));
        this.mAttestationSecurityLevel = securityLevelEnum;
        SecurityLevel securityLevel = SecurityLevel.TRUSTED_ENVIRONMENT;
        this.mAttestationHardwareBacked = securityLevelEnum == securityLevel;
        this.mAttestationChallenge = getOctetsFromAsn1(extensionParsedValue.getObjectAt(4));
        this.mKeymasterVersion = Integer.valueOf(getIntegerFromAsn1(extensionParsedValue.getObjectAt(2)));
        this.mKeymasterUniqueId = getOctetsFromAsn1(extensionParsedValue.getObjectAt(5));
        SecurityLevel securityLevelEnum2 = getSecurityLevelEnum(extensionParsedValue.getObjectAt(3));
        this.mKeymasterSecurityLevel = securityLevelEnum2;
        this.mKeymasterHardwareBacked = securityLevelEnum2 == securityLevel;
        for (ASN1TaggedObject aSN1TaggedObject : extensionParsedValue.getObjectAt(6).toArray()) {
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 509) {
                this.mKeyRequiresUnlockedDevice = getBoolFromAsn1(aSN1TaggedObject.getObject());
            } else if (tagNo == 709) {
                parseAttestationApplicationId(getOctetsFromAsn1(aSN1TaggedObject.getObject()).toByteArray());
            }
        }
        for (ASN1TaggedObject aSN1TaggedObject2 : extensionParsedValue.getObjectAt(7).toArray()) {
            int tagNo2 = aSN1TaggedObject2.getTagNo();
            if (tagNo2 == 503) {
                this.mKeyAuthenticatorType = 0;
            } else if (tagNo2 == 600) {
                this.mKeyAllowedForAllApplications = true;
            } else if (tagNo2 == 718) {
                this.mKeyVendorPatchLevel = Integer.valueOf(getIntegerFromAsn1(aSN1TaggedObject2.getObject()));
            } else if (tagNo2 != 719) {
                switch (tagNo2) {
                    case 704:
                        ASN1Sequence object = aSN1TaggedObject2.getObject();
                        this.mVerifiedBootKey = getOctetsFromAsn1(object.getObjectAt(0));
                        this.mVerifiedBootLocked = getBoolFromAsn1(object.getObjectAt(1));
                        this.mVerifiedBootState = getVerifiedBootStateEnum(object.getObjectAt(2));
                        if (this.mAttestationVersion.intValue() >= 3) {
                            this.mVerifiedBootHash = getOctetsFromAsn1(object.getObjectAt(3));
                            break;
                        } else {
                            break;
                        }
                    case 705:
                        this.mKeyOsVersion = Integer.valueOf(getIntegerFromAsn1(aSN1TaggedObject2.getObject()));
                        break;
                    case 706:
                        this.mKeyOsPatchLevel = Integer.valueOf(getIntegerFromAsn1(aSN1TaggedObject2.getObject()));
                        break;
                    default:
                        switch (tagNo2) {
                            case 710:
                                this.mDeviceBrand = getUtf8FromOctetsFromAsn1(aSN1TaggedObject2.getObject());
                                break;
                            case 711:
                                this.mDeviceName = getUtf8FromOctetsFromAsn1(aSN1TaggedObject2.getObject());
                                break;
                            case 712:
                                this.mDeviceProductName = getUtf8FromOctetsFromAsn1(aSN1TaggedObject2.getObject());
                                break;
                        }
                }
            } else {
                this.mKeyBootPatchLevel = Integer.valueOf(getIntegerFromAsn1(aSN1TaggedObject2.getObject()));
            }
        }
    }

    public final void parseAttestationApplicationId(byte[] bArr) {
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(new ASN1InputStream(bArr).readObject());
        HashMap hashMap = new HashMap();
        for (ASN1Sequence aSN1Sequence2 : aSN1Sequence.getObjectAt(0).toArray()) {
            hashMap.put(getUtf8FromOctetsFromAsn1(aSN1Sequence2.getObjectAt(0)), Long.valueOf(getLongFromAsn1(aSN1Sequence2.getObjectAt(1))));
        }
        ArrayList arrayList = new ArrayList();
        for (ASN1Encodable aSN1Encodable : aSN1Sequence.getObjectAt(1).toArray()) {
            arrayList.add(getOctetsFromAsn1(aSN1Encodable));
        }
        this.mApplicationPackageNameVersion = Collections.unmodifiableMap(hashMap);
        this.mApplicationCertificateDigests = Collections.unmodifiableList(arrayList);
    }

    public final VerifiedBootState getVerifiedBootStateEnum(ASN1Encodable aSN1Encodable) {
        int enumFromAsn1 = getEnumFromAsn1(aSN1Encodable);
        if (enumFromAsn1 == 0) {
            return VerifiedBootState.VERIFIED;
        }
        if (enumFromAsn1 == 1) {
            return VerifiedBootState.SELF_SIGNED;
        }
        if (enumFromAsn1 == 2) {
            return VerifiedBootState.UNVERIFIED;
        }
        if (enumFromAsn1 == 3) {
            return VerifiedBootState.FAILED;
        }
        throw new IllegalArgumentException("Invalid verified boot state.");
    }

    public final SecurityLevel getSecurityLevelEnum(ASN1Encodable aSN1Encodable) {
        int enumFromAsn1 = getEnumFromAsn1(aSN1Encodable);
        if (enumFromAsn1 == 0) {
            return SecurityLevel.SOFTWARE;
        }
        if (enumFromAsn1 == 1) {
            return SecurityLevel.TRUSTED_ENVIRONMENT;
        }
        if (enumFromAsn1 == 2) {
            return SecurityLevel.STRONG_BOX;
        }
        throw new IllegalArgumentException("Invalid security level.");
    }

    public final ByteString getOctetsFromAsn1(ASN1Encodable aSN1Encodable) {
        return ByteString.copyFrom(((ASN1OctetString) aSN1Encodable).getOctets());
    }

    public final String getUtf8FromOctetsFromAsn1(ASN1Encodable aSN1Encodable) {
        return new String(((ASN1OctetString) aSN1Encodable).getOctets(), StandardCharsets.UTF_8);
    }

    public final int getIntegerFromAsn1(ASN1Encodable aSN1Encodable) {
        return ((ASN1Integer) aSN1Encodable).getValue().intValueExact();
    }

    public final long getLongFromAsn1(ASN1Encodable aSN1Encodable) {
        return ((ASN1Integer) aSN1Encodable).getValue().longValueExact();
    }

    public final int getEnumFromAsn1(ASN1Encodable aSN1Encodable) {
        return ((ASN1Enumerated) aSN1Encodable).getValue().intValueExact();
    }

    public final Boolean getBoolFromAsn1(ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable instanceof ASN1Boolean) {
            return Boolean.valueOf(((ASN1Boolean) aSN1Encodable).isTrue());
        }
        return null;
    }
}
