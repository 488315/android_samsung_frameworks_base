package android.security.keystore;

import android.annotation.SystemApi;
import android.text.TextUtils;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes3.dex */
public final class KeyGenParameterSpec implements AlgorithmParameterSpec, UserAuthArgs {
    private final String mAttestKeyAlias;
    private final byte[] mAttestationChallenge;
    private final int[] mAttestationIds;
    private final String[] mBlockModes;
    private final long mBoundToSecureUserId;
    private final Date mCertificateNotAfter;
    private final Date mCertificateNotBefore;
    private final BigInteger mCertificateSerialNumber;
    private final X500Principal mCertificateSubject;
    private final boolean mCriticalToDeviceEncryption;
    private final boolean mDevicePropertiesAttestationIncluded;
    private final String[] mDigests;
    private final String[] mEncryptionPaddings;
    private final boolean mInvalidatedByBiometricEnrollment;
    private final boolean mIsStrongBoxBacked;
    private final int mKeySize;
    private final Date mKeyValidityForConsumptionEnd;
    private final Date mKeyValidityForOriginationEnd;
    private final Date mKeyValidityStart;
    private final String mKeystoreAlias;
    private final int mMaxUsageCount;
    private final Set<String> mMgf1Digests;
    private final int mNamespace;
    private final int mPurposes;
    private final boolean mRandomizedEncryptionRequired;
    private final String[] mSignaturePaddings;
    private final AlgorithmParameterSpec mSpec;
    private final boolean mUniqueIdIncluded;
    private final boolean mUnlockedDeviceRequired;
    private final boolean mUserAuthenticationRequired;
    private final int mUserAuthenticationType;
    private final boolean mUserAuthenticationValidWhileOnBody;
    private final int mUserAuthenticationValidityDurationSeconds;
    private final boolean mUserConfirmationRequired;
    private final boolean mUserPresenceRequired;
    private static final X500Principal DEFAULT_ATTESTATION_CERT_SUBJECT = new X500Principal("CN=Android Keystore Key");
    private static final X500Principal DEFAULT_SELF_SIGNED_CERT_SUBJECT = new X500Principal("CN=Fake");
    private static final BigInteger DEFAULT_CERT_SERIAL_NUMBER = new BigInteger("1");
    private static final Date DEFAULT_CERT_NOT_BEFORE = new Date(0);
    private static final Date DEFAULT_CERT_NOT_AFTER = new Date(2461449600000L);

    public KeyGenParameterSpec(String str, int i, int i2, AlgorithmParameterSpec algorithmParameterSpec, X500Principal x500Principal, BigInteger bigInteger, Date date, Date date2, Date date3, Date date4, Date date5, int i3, String[] strArr, Set<String> set, String[] strArr2, String[] strArr3, String[] strArr4, boolean z, boolean z2, int i4, int i5, boolean z3, byte[] bArr, boolean z4, int[] iArr, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, int i6, String str2, long j) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("keyStoreAlias must not be empty");
        }
        if (x500Principal == null) {
            if (bArr == null) {
                x500Principal = DEFAULT_SELF_SIGNED_CERT_SUBJECT;
            } else {
                x500Principal = DEFAULT_ATTESTATION_CERT_SUBJECT;
            }
        }
        date = date == null ? DEFAULT_CERT_NOT_BEFORE : date;
        date2 = date2 == null ? DEFAULT_CERT_NOT_AFTER : date2;
        bigInteger = bigInteger == null ? DEFAULT_CERT_SERIAL_NUMBER : bigInteger;
        if (date2.before(date)) {
            throw new IllegalArgumentException("certificateNotAfter < certificateNotBefore");
        }
        this.mKeystoreAlias = str;
        this.mNamespace = i;
        this.mKeySize = i2;
        this.mSpec = algorithmParameterSpec;
        this.mCertificateSubject = x500Principal;
        this.mCertificateSerialNumber = bigInteger;
        this.mCertificateNotBefore = Utils.cloneIfNotNull(date);
        this.mCertificateNotAfter = Utils.cloneIfNotNull(date2);
        this.mKeyValidityStart = Utils.cloneIfNotNull(date3);
        this.mKeyValidityForOriginationEnd = Utils.cloneIfNotNull(date4);
        this.mKeyValidityForConsumptionEnd = Utils.cloneIfNotNull(date5);
        this.mPurposes = i3;
        this.mDigests = ArrayUtils.cloneIfNotEmpty(strArr);
        this.mMgf1Digests = set != null ? set : Collections.EMPTY_SET;
        this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr2));
        this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr3));
        this.mBlockModes = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr4));
        this.mRandomizedEncryptionRequired = z;
        this.mUserAuthenticationRequired = z2;
        this.mUserPresenceRequired = z3;
        this.mUserAuthenticationValidityDurationSeconds = i4;
        this.mUserAuthenticationType = i5;
        this.mAttestationChallenge = Utils.cloneIfNotNull(bArr);
        this.mDevicePropertiesAttestationIncluded = z4;
        this.mAttestationIds = iArr;
        this.mUniqueIdIncluded = z5;
        this.mUserAuthenticationValidWhileOnBody = z6;
        this.mInvalidatedByBiometricEnrollment = z7;
        this.mIsStrongBoxBacked = z8;
        this.mUserConfirmationRequired = z9;
        this.mUnlockedDeviceRequired = z10;
        this.mCriticalToDeviceEncryption = z11;
        this.mMaxUsageCount = i6;
        this.mAttestKeyAlias = str2;
        this.mBoundToSecureUserId = j;
    }

    public String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    @Deprecated
    public int getUid() {
        try {
            return KeyProperties.namespaceToLegacyUid(this.mNamespace);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("getUid called on KeyGenParameterSpec with non legacy keystore namespace.", e);
        }
    }

    @SystemApi
    public int getNamespace() {
        return this.mNamespace;
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.mSpec;
    }

    public X500Principal getCertificateSubject() {
        return this.mCertificateSubject;
    }

    public BigInteger getCertificateSerialNumber() {
        return this.mCertificateSerialNumber;
    }

    public Date getCertificateNotBefore() {
        return Utils.cloneIfNotNull(this.mCertificateNotBefore);
    }

    public Date getCertificateNotAfter() {
        return Utils.cloneIfNotNull(this.mCertificateNotAfter);
    }

    public Date getKeyValidityStart() {
        return Utils.cloneIfNotNull(this.mKeyValidityStart);
    }

    public Date getKeyValidityForConsumptionEnd() {
        return Utils.cloneIfNotNull(this.mKeyValidityForConsumptionEnd);
    }

    public Date getKeyValidityForOriginationEnd() {
        return Utils.cloneIfNotNull(this.mKeyValidityForOriginationEnd);
    }

    public int getPurposes() {
        return this.mPurposes;
    }

    public String[] getDigests() {
        String[] strArr = this.mDigests;
        if (strArr == null) {
            throw new IllegalStateException("Digests not specified");
        }
        return ArrayUtils.cloneIfNotEmpty(strArr);
    }

    public boolean isDigestsSpecified() {
        return this.mDigests != null;
    }

    public Set<String> getMgf1Digests() {
        if (this.mMgf1Digests.isEmpty()) {
            throw new IllegalStateException("Mask generation function (MGF) not specified");
        }
        return new HashSet(this.mMgf1Digests);
    }

    public boolean isMgf1DigestsSpecified() {
        return !this.mMgf1Digests.isEmpty();
    }

    public String[] getEncryptionPaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mEncryptionPaddings);
    }

    public String[] getSignaturePaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
    }

    public String[] getBlockModes() {
        return ArrayUtils.cloneIfNotEmpty(this.mBlockModes);
    }

    public boolean isRandomizedEncryptionRequired() {
        return this.mRandomizedEncryptionRequired;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserAuthenticationRequired() {
        return this.mUserAuthenticationRequired;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserConfirmationRequired() {
        return this.mUserConfirmationRequired;
    }

    @Override // android.security.keystore.UserAuthArgs
    public int getUserAuthenticationValidityDurationSeconds() {
        return this.mUserAuthenticationValidityDurationSeconds;
    }

    @Override // android.security.keystore.UserAuthArgs
    public int getUserAuthenticationType() {
        return this.mUserAuthenticationType;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserPresenceRequired() {
        return this.mUserPresenceRequired;
    }

    public byte[] getAttestationChallenge() {
        return Utils.cloneIfNotNull(this.mAttestationChallenge);
    }

    public boolean isDevicePropertiesAttestationIncluded() {
        return this.mDevicePropertiesAttestationIncluded;
    }

    @SystemApi
    public int[] getAttestationIds() {
        return (int[]) this.mAttestationIds.clone();
    }

    public boolean isUniqueIdIncluded() {
        return this.mUniqueIdIncluded;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserAuthenticationValidWhileOnBody() {
        return this.mUserAuthenticationValidWhileOnBody;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isInvalidatedByBiometricEnrollment() {
        return this.mInvalidatedByBiometricEnrollment;
    }

    public boolean isStrongBoxBacked() {
        return this.mIsStrongBoxBacked;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUnlockedDeviceRequired() {
        return this.mUnlockedDeviceRequired;
    }

    @Override // android.security.keystore.UserAuthArgs
    public long getBoundToSpecificSecureUserId() {
        return this.mBoundToSecureUserId;
    }

    public boolean isCriticalToDeviceEncryption() {
        return this.mCriticalToDeviceEncryption;
    }

    public int getMaxUsageCount() {
        return this.mMaxUsageCount;
    }

    public String getAttestKeyAlias() {
        return this.mAttestKeyAlias;
    }

    public static final class Builder {
        private String mAttestKeyAlias;
        private byte[] mAttestationChallenge;
        private int[] mAttestationIds;
        private String[] mBlockModes;
        private long mBoundToSecureUserId;
        private Date mCertificateNotAfter;
        private Date mCertificateNotBefore;
        private BigInteger mCertificateSerialNumber;
        private X500Principal mCertificateSubject;
        private boolean mCriticalToDeviceEncryption;
        private boolean mDevicePropertiesAttestationIncluded;
        private String[] mDigests;
        private String[] mEncryptionPaddings;
        private boolean mInvalidatedByBiometricEnrollment;
        private boolean mIsStrongBoxBacked;
        private int mKeySize;
        private Date mKeyValidityForConsumptionEnd;
        private Date mKeyValidityForOriginationEnd;
        private Date mKeyValidityStart;
        private final String mKeystoreAlias;
        private int mMaxUsageCount;
        private Set<String> mMgf1Digests;
        private int mNamespace;
        private int mPurposes;
        private boolean mRandomizedEncryptionRequired;
        private String[] mSignaturePaddings;
        private AlgorithmParameterSpec mSpec;
        private boolean mUniqueIdIncluded;
        private boolean mUnlockedDeviceRequired;
        private boolean mUserAuthenticationRequired;
        private int mUserAuthenticationType;
        private boolean mUserAuthenticationValidWhileOnBody;
        private int mUserAuthenticationValidityDurationSeconds;
        private boolean mUserConfirmationRequired;
        private boolean mUserPresenceRequired;

        public Builder(String str, int i) {
            this.mNamespace = -1;
            this.mKeySize = -1;
            this.mMgf1Digests = Collections.EMPTY_SET;
            this.mRandomizedEncryptionRequired = true;
            this.mUserAuthenticationValidityDurationSeconds = 0;
            this.mUserAuthenticationType = 2;
            this.mUserPresenceRequired = false;
            this.mAttestationChallenge = null;
            this.mDevicePropertiesAttestationIncluded = false;
            this.mAttestationIds = new int[0];
            this.mUniqueIdIncluded = false;
            this.mInvalidatedByBiometricEnrollment = true;
            this.mIsStrongBoxBacked = false;
            this.mUnlockedDeviceRequired = false;
            this.mCriticalToDeviceEncryption = false;
            this.mMaxUsageCount = -1;
            this.mAttestKeyAlias = null;
            this.mBoundToSecureUserId = 0L;
            if (str == null) {
                throw new NullPointerException("keystoreAlias == null");
            }
            if (str.isEmpty()) {
                throw new IllegalArgumentException("keystoreAlias must not be empty");
            }
            this.mKeystoreAlias = str;
            this.mPurposes = i;
        }

        public Builder(KeyGenParameterSpec keyGenParameterSpec) {
            this(keyGenParameterSpec.getKeystoreAlias(), keyGenParameterSpec.getPurposes());
            this.mNamespace = keyGenParameterSpec.getNamespace();
            this.mKeySize = keyGenParameterSpec.getKeySize();
            this.mSpec = keyGenParameterSpec.getAlgorithmParameterSpec();
            this.mCertificateSubject = keyGenParameterSpec.getCertificateSubject();
            this.mCertificateSerialNumber = keyGenParameterSpec.getCertificateSerialNumber();
            this.mCertificateNotBefore = keyGenParameterSpec.getCertificateNotBefore();
            this.mCertificateNotAfter = keyGenParameterSpec.getCertificateNotAfter();
            this.mKeyValidityStart = keyGenParameterSpec.getKeyValidityStart();
            this.mKeyValidityForOriginationEnd = keyGenParameterSpec.getKeyValidityForOriginationEnd();
            this.mKeyValidityForConsumptionEnd = keyGenParameterSpec.getKeyValidityForConsumptionEnd();
            this.mPurposes = keyGenParameterSpec.getPurposes();
            if (keyGenParameterSpec.isDigestsSpecified()) {
                this.mDigests = keyGenParameterSpec.getDigests();
            }
            if (keyGenParameterSpec.isMgf1DigestsSpecified()) {
                this.mMgf1Digests = keyGenParameterSpec.getMgf1Digests();
            }
            this.mEncryptionPaddings = keyGenParameterSpec.getEncryptionPaddings();
            this.mSignaturePaddings = keyGenParameterSpec.getSignaturePaddings();
            this.mBlockModes = keyGenParameterSpec.getBlockModes();
            this.mRandomizedEncryptionRequired = keyGenParameterSpec.isRandomizedEncryptionRequired();
            this.mUserAuthenticationRequired = keyGenParameterSpec.isUserAuthenticationRequired();
            this.mUserAuthenticationValidityDurationSeconds = keyGenParameterSpec.getUserAuthenticationValidityDurationSeconds();
            this.mUserAuthenticationType = keyGenParameterSpec.getUserAuthenticationType();
            this.mUserPresenceRequired = keyGenParameterSpec.isUserPresenceRequired();
            this.mAttestationChallenge = keyGenParameterSpec.getAttestationChallenge();
            this.mDevicePropertiesAttestationIncluded = keyGenParameterSpec.isDevicePropertiesAttestationIncluded();
            this.mAttestationIds = keyGenParameterSpec.getAttestationIds();
            this.mUniqueIdIncluded = keyGenParameterSpec.isUniqueIdIncluded();
            this.mUserAuthenticationValidWhileOnBody = keyGenParameterSpec.isUserAuthenticationValidWhileOnBody();
            this.mInvalidatedByBiometricEnrollment = keyGenParameterSpec.isInvalidatedByBiometricEnrollment();
            this.mIsStrongBoxBacked = keyGenParameterSpec.isStrongBoxBacked();
            this.mUserConfirmationRequired = keyGenParameterSpec.isUserConfirmationRequired();
            this.mUnlockedDeviceRequired = keyGenParameterSpec.isUnlockedDeviceRequired();
            this.mCriticalToDeviceEncryption = keyGenParameterSpec.isCriticalToDeviceEncryption();
            this.mMaxUsageCount = keyGenParameterSpec.getMaxUsageCount();
            this.mAttestKeyAlias = keyGenParameterSpec.getAttestKeyAlias();
            this.mBoundToSecureUserId = keyGenParameterSpec.getBoundToSpecificSecureUserId();
        }

        @SystemApi
        @Deprecated
        public Builder setUid(int i) {
            this.mNamespace = KeyProperties.legacyUidToNamespace(i);
            return this;
        }

        @SystemApi
        public Builder setNamespace(int i) {
            this.mNamespace = i;
            return this;
        }

        public Builder setKeySize(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("keySize < 0");
            }
            this.mKeySize = i;
            return this;
        }

        public Builder setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec == null) {
                throw new NullPointerException("spec == null");
            }
            this.mSpec = algorithmParameterSpec;
            return this;
        }

        public Builder setCertificateSubject(X500Principal x500Principal) {
            if (x500Principal == null) {
                throw new NullPointerException("subject == null");
            }
            this.mCertificateSubject = x500Principal;
            return this;
        }

        public Builder setCertificateSerialNumber(BigInteger bigInteger) {
            if (bigInteger == null) {
                throw new NullPointerException("serialNumber == null");
            }
            this.mCertificateSerialNumber = bigInteger;
            return this;
        }

        public Builder setCertificateNotBefore(Date date) {
            if (date == null) {
                throw new NullPointerException("date == null");
            }
            this.mCertificateNotBefore = Utils.cloneIfNotNull(date);
            return this;
        }

        public Builder setCertificateNotAfter(Date date) {
            if (date == null) {
                throw new NullPointerException("date == null");
            }
            this.mCertificateNotAfter = Utils.cloneIfNotNull(date);
            return this;
        }

        public Builder setKeyValidityStart(Date date) {
            this.mKeyValidityStart = Utils.cloneIfNotNull(date);
            return this;
        }

        public Builder setKeyValidityEnd(Date date) {
            setKeyValidityForOriginationEnd(date);
            setKeyValidityForConsumptionEnd(date);
            return this;
        }

        public Builder setKeyValidityForOriginationEnd(Date date) {
            this.mKeyValidityForOriginationEnd = Utils.cloneIfNotNull(date);
            return this;
        }

        public Builder setKeyValidityForConsumptionEnd(Date date) {
            this.mKeyValidityForConsumptionEnd = Utils.cloneIfNotNull(date);
            return this;
        }

        public Builder setDigests(String... strArr) {
            this.mDigests = ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public Builder setMgf1Digests(String... strArr) {
            this.mMgf1Digests = Set.of((Object[]) strArr);
            return this;
        }

        public Builder setEncryptionPaddings(String... strArr) {
            this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public Builder setSignaturePaddings(String... strArr) {
            this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public Builder setBlockModes(String... strArr) {
            this.mBlockModes = ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public Builder setRandomizedEncryptionRequired(boolean z) {
            this.mRandomizedEncryptionRequired = z;
            return this;
        }

        public Builder setUserAuthenticationRequired(boolean z) {
            this.mUserAuthenticationRequired = z;
            return this;
        }

        public Builder setUserConfirmationRequired(boolean z) {
            this.mUserConfirmationRequired = z;
            return this;
        }

        @Deprecated
        public Builder setUserAuthenticationValidityDurationSeconds(int i) {
            if (i < -1) {
                throw new IllegalArgumentException("seconds must be -1 or larger");
            }
            if (i == -1) {
                return setUserAuthenticationParameters(0, 2);
            }
            return setUserAuthenticationParameters(i, 3);
        }

        public Builder setUserAuthenticationParameters(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("timeout must be 0 or larger");
            }
            this.mUserAuthenticationValidityDurationSeconds = i;
            this.mUserAuthenticationType = i2;
            return this;
        }

        public Builder setUserPresenceRequired(boolean z) {
            this.mUserPresenceRequired = z;
            return this;
        }

        public Builder setAttestationChallenge(byte[] bArr) {
            this.mAttestationChallenge = bArr;
            return this;
        }

        public Builder setDevicePropertiesAttestationIncluded(boolean z) {
            this.mDevicePropertiesAttestationIncluded = z;
            return this;
        }

        @SystemApi
        public Builder setAttestationIds(int[] iArr) {
            this.mAttestationIds = iArr;
            return this;
        }

        public Builder setUniqueIdIncluded(boolean z) {
            this.mUniqueIdIncluded = z;
            return this;
        }

        public Builder setUserAuthenticationValidWhileOnBody(boolean z) {
            this.mUserAuthenticationValidWhileOnBody = z;
            return this;
        }

        public Builder setInvalidatedByBiometricEnrollment(boolean z) {
            this.mInvalidatedByBiometricEnrollment = z;
            return this;
        }

        public Builder setIsStrongBoxBacked(boolean z) {
            this.mIsStrongBoxBacked = z;
            return this;
        }

        public Builder setUnlockedDeviceRequired(boolean z) {
            this.mUnlockedDeviceRequired = z;
            return this;
        }

        public Builder setCriticalToDeviceEncryption(boolean z) {
            this.mCriticalToDeviceEncryption = z;
            return this;
        }

        public Builder setMaxUsageCount(int i) {
            if (i == -1 || i > 0) {
                this.mMaxUsageCount = i;
                return this;
            }
            throw new IllegalArgumentException("maxUsageCount is not valid");
        }

        public Builder setAttestKeyAlias(String str) {
            this.mAttestKeyAlias = str;
            return this;
        }

        public Builder setBoundToSpecificSecureUserId(long j) {
            this.mBoundToSecureUserId = j;
            return this;
        }

        public KeyGenParameterSpec build() {
            return new KeyGenParameterSpec(this.mKeystoreAlias, this.mNamespace, this.mKeySize, this.mSpec, this.mCertificateSubject, this.mCertificateSerialNumber, this.mCertificateNotBefore, this.mCertificateNotAfter, this.mKeyValidityStart, this.mKeyValidityForOriginationEnd, this.mKeyValidityForConsumptionEnd, this.mPurposes, this.mDigests, this.mMgf1Digests, this.mEncryptionPaddings, this.mSignaturePaddings, this.mBlockModes, this.mRandomizedEncryptionRequired, this.mUserAuthenticationRequired, this.mUserAuthenticationValidityDurationSeconds, this.mUserAuthenticationType, this.mUserPresenceRequired, this.mAttestationChallenge, this.mDevicePropertiesAttestationIncluded, this.mAttestationIds, this.mUniqueIdIncluded, this.mUserAuthenticationValidWhileOnBody, this.mInvalidatedByBiometricEnrollment, this.mIsStrongBoxBacked, this.mUserConfirmationRequired, this.mUnlockedDeviceRequired, this.mCriticalToDeviceEncryption, this.mMaxUsageCount, this.mAttestKeyAlias, this.mBoundToSecureUserId);
        }
    }
}
