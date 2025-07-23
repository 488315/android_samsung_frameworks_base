package android.security.keystore;

import java.security.spec.KeySpec;
import java.util.Date;

/* loaded from: classes3.dex */
public class KeyInfo implements KeySpec {
    private final String[] mBlockModes;
    private final String[] mDigests;
    private final String[] mEncryptionPaddings;
    private final boolean mInsideSecureHardware;
    private final boolean mInvalidatedByBiometricEnrollment;
    private final int mKeySize;
    private final Date mKeyValidityForConsumptionEnd;
    private final Date mKeyValidityForOriginationEnd;
    private final Date mKeyValidityStart;
    private final String mKeystoreAlias;
    private final int mOrigin;
    private final int mPurposes;
    private final int mRemainingUsageCount;
    private final int mSecurityLevel;
    private final String[] mSignaturePaddings;
    private final boolean mTrustedUserPresenceRequired;
    private final boolean mUnlockedDeviceRequired;
    private final boolean mUserAuthenticationRequired;
    private final boolean mUserAuthenticationRequirementEnforcedBySecureHardware;
    private final int mUserAuthenticationType;
    private final boolean mUserAuthenticationValidWhileOnBody;
    private final int mUserAuthenticationValidityDurationSeconds;
    private final boolean mUserConfirmationRequired;

    public KeyInfo(String str, boolean z, int i, int i2, Date date, Date date2, Date date3, int i3, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, boolean z2, int i4, int i5, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i6, int i7) {
        this.mKeystoreAlias = str;
        this.mInsideSecureHardware = z;
        this.mOrigin = i;
        this.mKeySize = i2;
        this.mKeyValidityStart = Utils.cloneIfNotNull(date);
        this.mKeyValidityForOriginationEnd = Utils.cloneIfNotNull(date2);
        this.mKeyValidityForConsumptionEnd = Utils.cloneIfNotNull(date3);
        this.mPurposes = i3;
        this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr));
        this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr2));
        this.mDigests = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr3));
        this.mBlockModes = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(strArr4));
        this.mUserAuthenticationRequired = z2;
        this.mUserAuthenticationValidityDurationSeconds = i4;
        this.mUserAuthenticationType = i5;
        this.mUserAuthenticationRequirementEnforcedBySecureHardware = z3;
        this.mUserAuthenticationValidWhileOnBody = z4;
        this.mUnlockedDeviceRequired = z5;
        this.mTrustedUserPresenceRequired = z6;
        this.mInvalidatedByBiometricEnrollment = z7;
        this.mUserConfirmationRequired = z8;
        this.mSecurityLevel = i6;
        this.mRemainingUsageCount = i7;
    }

    public String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    @Deprecated
    public boolean isInsideSecureHardware() {
        return this.mInsideSecureHardware;
    }

    public int getOrigin() {
        return this.mOrigin;
    }

    public int getKeySize() {
        return this.mKeySize;
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

    public String[] getBlockModes() {
        return ArrayUtils.cloneIfNotEmpty(this.mBlockModes);
    }

    public String[] getEncryptionPaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mEncryptionPaddings);
    }

    public String[] getSignaturePaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
    }

    public String[] getDigests() {
        return ArrayUtils.cloneIfNotEmpty(this.mDigests);
    }

    public boolean isUserAuthenticationRequired() {
        return this.mUserAuthenticationRequired;
    }

    public boolean isUnlockedDeviceRequired() {
        return this.mUnlockedDeviceRequired;
    }

    public boolean isUserConfirmationRequired() {
        return this.mUserConfirmationRequired;
    }

    public int getUserAuthenticationValidityDurationSeconds() {
        return this.mUserAuthenticationValidityDurationSeconds;
    }

    public int getUserAuthenticationType() {
        return this.mUserAuthenticationType;
    }

    public boolean isUserAuthenticationRequirementEnforcedBySecureHardware() {
        return this.mUserAuthenticationRequirementEnforcedBySecureHardware;
    }

    public boolean isUserAuthenticationValidWhileOnBody() {
        return this.mUserAuthenticationValidWhileOnBody;
    }

    public boolean isInvalidatedByBiometricEnrollment() {
        return this.mInvalidatedByBiometricEnrollment;
    }

    public boolean isTrustedUserPresenceRequired() {
        return this.mTrustedUserPresenceRequired;
    }

    public int getSecurityLevel() {
        return this.mSecurityLevel;
    }

    public int getRemainingUsageCount() {
        return this.mRemainingUsageCount;
    }
}
