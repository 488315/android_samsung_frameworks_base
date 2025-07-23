package android.security.keystore;

import java.security.KeyStore;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes3.dex */
public final class KeyProtection implements KeyStore.ProtectionParameter, UserAuthArgs {
    private final String[] mBlockModes;
    private final long mBoundToSecureUserId;
    private final boolean mCriticalToDeviceEncryption;
    private final String[] mDigests;
    private final String[] mEncryptionPaddings;
    private final boolean mInvalidatedByBiometricEnrollment;
    private final boolean mIsStrongBoxBacked;
    private final Date mKeyValidityForConsumptionEnd;
    private final Date mKeyValidityForOriginationEnd;
    private final Date mKeyValidityStart;
    private final int mMaxUsageCount;
    private final Set<String> mMgf1Digests;
    private final int mPurposes;
    private final boolean mRandomizedEncryptionRequired;
    private final boolean mRollbackResistant;
    private final String[] mSignaturePaddings;
    private final boolean mUnlockedDeviceRequired;
    private final boolean mUserAuthenticationRequired;
    private final int mUserAuthenticationType;
    private final boolean mUserAuthenticationValidWhileOnBody;
    private final int mUserAuthenticationValidityDurationSeconds;
    private final boolean mUserConfirmationRequired;
    private final boolean mUserPresenceRequred;

    private KeyProtection(Date date, Date date2, Date date3, int i, String[] strArr, String[] strArr2, String[] strArr3, Set<String> set, String[] strArr4, boolean z, boolean z2, int i2, int i3, boolean z3, boolean z4, boolean z5, long j, boolean z6, boolean z7, boolean z8, boolean z9, int i4, boolean z10) {
        this.mKeyValidityStart = Utils.cloneIfNotNull(date);
        this.mKeyValidityForOriginationEnd = Utils.cloneIfNotNull(date2);
        this.mKeyValidityForConsumptionEnd = Utils.cloneIfNotNull(date3);
        this.mPurposes = i;
        this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr));
        this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr2));
        this.mDigests = ArrayUtils.cloneIfNotEmpty(strArr3);
        this.mMgf1Digests = set;
        this.mBlockModes = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr4));
        this.mRandomizedEncryptionRequired = z;
        this.mUserAuthenticationRequired = z2;
        this.mUserAuthenticationType = i2;
        this.mUserAuthenticationValidityDurationSeconds = i3;
        this.mUserPresenceRequred = z3;
        this.mUserAuthenticationValidWhileOnBody = z4;
        this.mInvalidatedByBiometricEnrollment = z5;
        this.mBoundToSecureUserId = j;
        this.mCriticalToDeviceEncryption = z6;
        this.mUserConfirmationRequired = z7;
        this.mUnlockedDeviceRequired = z8;
        this.mIsStrongBoxBacked = z9;
        this.mMaxUsageCount = i4;
        this.mRollbackResistant = z10;
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

    public String[] getEncryptionPaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mEncryptionPaddings);
    }

    public String[] getSignaturePaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
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
    public int getUserAuthenticationType() {
        return this.mUserAuthenticationType;
    }

    @Override // android.security.keystore.UserAuthArgs
    public int getUserAuthenticationValidityDurationSeconds() {
        return this.mUserAuthenticationValidityDurationSeconds;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserPresenceRequired() {
        return this.mUserPresenceRequred;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUserAuthenticationValidWhileOnBody() {
        return this.mUserAuthenticationValidWhileOnBody;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isInvalidatedByBiometricEnrollment() {
        return this.mInvalidatedByBiometricEnrollment;
    }

    @Override // android.security.keystore.UserAuthArgs
    public long getBoundToSpecificSecureUserId() {
        return this.mBoundToSecureUserId;
    }

    public boolean isCriticalToDeviceEncryption() {
        return this.mCriticalToDeviceEncryption;
    }

    @Override // android.security.keystore.UserAuthArgs
    public boolean isUnlockedDeviceRequired() {
        return this.mUnlockedDeviceRequired;
    }

    public boolean isStrongBoxBacked() {
        return this.mIsStrongBoxBacked;
    }

    public int getMaxUsageCount() {
        return this.mMaxUsageCount;
    }

    public boolean isRollbackResistant() {
        return this.mRollbackResistant;
    }

    public static final class Builder {
        private String[] mBlockModes;
        private String[] mDigests;
        private String[] mEncryptionPaddings;
        private Date mKeyValidityForConsumptionEnd;
        private Date mKeyValidityForOriginationEnd;
        private Date mKeyValidityStart;
        private int mPurposes;
        private String[] mSignaturePaddings;
        private boolean mUserAuthenticationRequired;
        private boolean mUserAuthenticationValidWhileOnBody;
        private boolean mUserConfirmationRequired;
        private Set<String> mMgf1Digests = Collections.EMPTY_SET;
        private boolean mRandomizedEncryptionRequired = true;
        private int mUserAuthenticationValidityDurationSeconds = 0;
        private int mUserAuthenticationType = 2;
        private boolean mUserPresenceRequired = false;
        private boolean mInvalidatedByBiometricEnrollment = true;
        private boolean mUnlockedDeviceRequired = false;
        private long mBoundToSecureUserId = 0;
        private boolean mCriticalToDeviceEncryption = false;
        private boolean mIsStrongBoxBacked = false;
        private int mMaxUsageCount = -1;
        private String mAttestKeyAlias = null;
        private boolean mRollbackResistant = false;

        public Builder(int i) {
            this.mPurposes = i;
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

        public Builder setEncryptionPaddings(String... strArr) {
            this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(strArr);
            return this;
        }

        public Builder setSignaturePaddings(String... strArr) {
            this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(strArr);
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

        public Builder setUserAuthenticationValidWhileOnBody(boolean z) {
            this.mUserAuthenticationValidWhileOnBody = z;
            return this;
        }

        public Builder setInvalidatedByBiometricEnrollment(boolean z) {
            this.mInvalidatedByBiometricEnrollment = z;
            return this;
        }

        public Builder setBoundToSpecificSecureUserId(long j) {
            this.mBoundToSecureUserId = j;
            return this;
        }

        public Builder setCriticalToDeviceEncryption(boolean z) {
            this.mCriticalToDeviceEncryption = z;
            return this;
        }

        public Builder setUnlockedDeviceRequired(boolean z) {
            this.mUnlockedDeviceRequired = z;
            return this;
        }

        public Builder setIsStrongBoxBacked(boolean z) {
            this.mIsStrongBoxBacked = z;
            return this;
        }

        public Builder setMaxUsageCount(int i) {
            if (i == -1 || i > 0) {
                this.mMaxUsageCount = i;
                return this;
            }
            throw new IllegalArgumentException("maxUsageCount is not valid");
        }

        public Builder setRollbackResistant(boolean z) {
            this.mRollbackResistant = z;
            return this;
        }

        public KeyProtection build() {
            return new KeyProtection(this.mKeyValidityStart, this.mKeyValidityForOriginationEnd, this.mKeyValidityForConsumptionEnd, this.mPurposes, this.mEncryptionPaddings, this.mSignaturePaddings, this.mDigests, this.mMgf1Digests, this.mBlockModes, this.mRandomizedEncryptionRequired, this.mUserAuthenticationRequired, this.mUserAuthenticationType, this.mUserAuthenticationValidityDurationSeconds, this.mUserPresenceRequired, this.mUserAuthenticationValidWhileOnBody, this.mInvalidatedByBiometricEnrollment, this.mBoundToSecureUserId, this.mCriticalToDeviceEncryption, this.mUserConfirmationRequired, this.mUnlockedDeviceRequired, this.mIsStrongBoxBacked, this.mMaxUsageCount, this.mRollbackResistant);
        }
    }
}
