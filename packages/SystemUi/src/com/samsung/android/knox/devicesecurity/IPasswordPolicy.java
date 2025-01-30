package com.samsung.android.knox.devicesecurity;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IPasswordPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.devicesecurity.IPasswordPolicy";

    boolean addRequiredPasswordPattern(ContextInfo contextInfo, String str);

    boolean clearResetPasswordToken(ContextInfo contextInfo, ComponentName componentName);

    boolean deleteAllRestrictions(ContextInfo contextInfo);

    boolean enforcePwdChange(ContextInfo contextInfo);

    boolean excludeExternalStorageForFailedPasswordsWipe(ContextInfo contextInfo, boolean z);

    int getCurrentFailedPasswordAttempts(ContextInfo contextInfo);

    int getCurrentFailedPasswordAttemptsInternal(ContextInfo contextInfo);

    List<String> getForbiddenStrings(ContextInfo contextInfo, boolean z);

    int getKeyguardDisabledFeatures(ContextInfo contextInfo, ComponentName componentName);

    int getKeyguardDisabledFeaturesInternal(ComponentName componentName, int i);

    int getMaximumCharacterOccurences(ContextInfo contextInfo);

    int getMaximumCharacterSequenceLength(ContextInfo contextInfo);

    int getMaximumFailedPasswordsForDisable(ContextInfo contextInfo);

    int getMaximumFailedPasswordsForWipe(ContextInfo contextInfo, ComponentName componentName);

    int getMaximumNumericSequenceLength(ContextInfo contextInfo);

    long getMaximumTimeToLock(ContextInfo contextInfo, ComponentName componentName);

    int getMinimumCharacterChangeLength(ContextInfo contextInfo);

    int getPasswordChangeTimeout(ContextInfo contextInfo);

    long getPasswordExpiration(ContextInfo contextInfo, ComponentName componentName);

    long getPasswordExpirationTimeout(ContextInfo contextInfo, ComponentName componentName);

    int getPasswordHistoryLength(ContextInfo contextInfo, ComponentName componentName);

    int getPasswordLockDelay(ContextInfo contextInfo);

    int getPasswordMinimumLength(ContextInfo contextInfo, ComponentName componentName);

    int getPasswordMinimumLetters(ContextInfo contextInfo, ComponentName componentName);

    int getPasswordMinimumLowerCase(ContextInfo contextInfo, ComponentName componentName);

    int getPasswordMinimumNonLetter(ContextInfo contextInfo, ComponentName componentName);

    int getPasswordMinimumNumeric(ContextInfo contextInfo, ComponentName componentName);

    int getPasswordMinimumSymbols(ContextInfo contextInfo, ComponentName componentName);

    int getPasswordMinimumUpperCase(ContextInfo contextInfo, ComponentName componentName);

    int getPasswordQuality(ContextInfo contextInfo, ComponentName componentName);

    String getRequiredPwdPatternRestrictions(ContextInfo contextInfo, boolean z);

    Map getSupportedBiometricAuthentications(ContextInfo contextInfo);

    boolean hasForbiddenCharacterSequence(ContextInfo contextInfo, String str);

    boolean hasForbiddenData(ContextInfo contextInfo, String str);

    boolean hasForbiddenNumericSequence(ContextInfo contextInfo, String str);

    boolean hasForbiddenStringDistance(ContextInfo contextInfo, String str, String str2);

    boolean hasMaxRepeatedCharacters(ContextInfo contextInfo, String str);

    boolean isActivePasswordSufficient(ContextInfo contextInfo);

    boolean isBiometricAuthenticationEnabled(ContextInfo contextInfo, int i);

    boolean isBiometricAuthenticationEnabledAsUser(int i, int i2);

    int isChangeRequested(ContextInfo contextInfo);

    int isChangeRequestedAsUser(int i);

    int isChangeRequestedForInner();

    boolean isClearLockAllowed();

    boolean isExternalStorageForFailedPasswordsWipeExcluded(ContextInfo contextInfo);

    boolean isMDMDisabledFP(int i);

    boolean isMultifactorAuthenticationEnabled(ContextInfo contextInfo);

    boolean isPasswordPatternMatched(ContextInfo contextInfo, String str);

    boolean isPasswordTableExist(ContextInfo contextInfo);

    boolean isPasswordVisibilityEnabled(ContextInfo contextInfo);

    boolean isPasswordVisibilityEnabledAsUser(int i);

    boolean isResetPasswordTokenActive(ContextInfo contextInfo, ComponentName componentName);

    boolean isScreenLockPatternVisibilityEnabled(ContextInfo contextInfo);

    boolean lock(ContextInfo contextInfo);

    void reboot(ContextInfo contextInfo, String str);

    boolean resetPassword(ContextInfo contextInfo, String str, int i);

    boolean resetPasswordWithToken(ContextInfo contextInfo, ComponentName componentName, String str, byte[] bArr, int i);

    boolean setBiometricAuthenticationEnabled(ContextInfo contextInfo, int i, boolean z);

    boolean setForbiddenStrings(ContextInfo contextInfo, List<String> list);

    void setKeyguardDisabledFeatures(ContextInfo contextInfo, ComponentName componentName, int i);

    void setKeyguardDisabledFeaturesInternal(ComponentName componentName, int i, int i2);

    boolean setMaximumCharacterOccurrences(ContextInfo contextInfo, int i);

    boolean setMaximumCharacterSequenceLength(ContextInfo contextInfo, int i);

    boolean setMaximumFailedPasswordsForDisable(ContextInfo contextInfo, int i);

    void setMaximumFailedPasswordsForWipe(ContextInfo contextInfo, ComponentName componentName, int i);

    boolean setMaximumNumericSequenceLength(ContextInfo contextInfo, int i);

    void setMaximumTimeToLock(ContextInfo contextInfo, ComponentName componentName, long j);

    boolean setMinimumCharacterChangeLength(ContextInfo contextInfo, int i);

    boolean setMultifactorAuthenticationEnabled(ContextInfo contextInfo, boolean z);

    boolean setPasswordChangeTimeout(ContextInfo contextInfo, int i);

    void setPasswordExpirationTimeout(ContextInfo contextInfo, ComponentName componentName, long j);

    void setPasswordHistoryLength(ContextInfo contextInfo, ComponentName componentName, int i);

    boolean setPasswordLockDelay(ContextInfo contextInfo, int i);

    void setPasswordMinimumLength(ContextInfo contextInfo, ComponentName componentName, int i);

    void setPasswordMinimumLetters(ContextInfo contextInfo, ComponentName componentName, int i);

    void setPasswordMinimumLowerCase(ContextInfo contextInfo, ComponentName componentName, int i);

    void setPasswordMinimumNonLetter(ContextInfo contextInfo, ComponentName componentName, int i);

    void setPasswordMinimumNumeric(ContextInfo contextInfo, ComponentName componentName, int i);

    void setPasswordMinimumSymbols(ContextInfo contextInfo, ComponentName componentName, int i);

    void setPasswordMinimumUpperCase(ContextInfo contextInfo, ComponentName componentName, int i);

    void setPasswordQuality(ContextInfo contextInfo, ComponentName componentName, int i);

    boolean setPasswordVisibilityEnabled(ContextInfo contextInfo, boolean z);

    boolean setPwdChangeRequested(ContextInfo contextInfo, int i);

    boolean setPwdChangeRequestedForInner(int i);

    boolean setRequiredPasswordPattern(ContextInfo contextInfo, String str);

    boolean setResetPasswordToken(ContextInfo contextInfo, ComponentName componentName, byte[] bArr);

    boolean setScreenLockPatternVisibilityEnabled(ContextInfo contextInfo, boolean z);

    void setTrustAgentConfiguration(ContextInfo contextInfo, ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle);

    boolean unlock(ContextInfo contextInfo);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IPasswordPolicy {
        public static final int TRANSACTION_addRequiredPasswordPattern = 87;
        public static final int TRANSACTION_clearResetPasswordToken = 82;
        public static final int TRANSACTION_deleteAllRestrictions = 4;
        public static final int TRANSACTION_enforcePwdChange = 16;
        public static final int TRANSACTION_excludeExternalStorageForFailedPasswordsWipe = 37;
        public static final int TRANSACTION_getCurrentFailedPasswordAttempts = 71;
        public static final int TRANSACTION_getCurrentFailedPasswordAttemptsInternal = 72;
        public static final int TRANSACTION_getForbiddenStrings = 23;
        public static final int TRANSACTION_getKeyguardDisabledFeatures = 79;
        public static final int TRANSACTION_getKeyguardDisabledFeaturesInternal = 85;
        public static final int TRANSACTION_getMaximumCharacterOccurences = 25;
        public static final int TRANSACTION_getMaximumCharacterSequenceLength = 31;
        public static final int TRANSACTION_getMaximumFailedPasswordsForDisable = 19;
        public static final int TRANSACTION_getMaximumFailedPasswordsForWipe = 74;
        public static final int TRANSACTION_getMaximumNumericSequenceLength = 21;
        public static final int TRANSACTION_getMaximumTimeToLock = 77;
        public static final int TRANSACTION_getMinimumCharacterChangeLength = 33;
        public static final int TRANSACTION_getPasswordChangeTimeout = 13;
        public static final int TRANSACTION_getPasswordExpiration = 69;
        public static final int TRANSACTION_getPasswordExpirationTimeout = 68;
        public static final int TRANSACTION_getPasswordHistoryLength = 66;
        public static final int TRANSACTION_getPasswordLockDelay = 2;
        public static final int TRANSACTION_getPasswordMinimumLength = 52;
        public static final int TRANSACTION_getPasswordMinimumLetters = 58;
        public static final int TRANSACTION_getPasswordMinimumLowerCase = 56;
        public static final int TRANSACTION_getPasswordMinimumNonLetter = 62;
        public static final int TRANSACTION_getPasswordMinimumNumeric = 60;
        public static final int TRANSACTION_getPasswordMinimumSymbols = 64;
        public static final int TRANSACTION_getPasswordMinimumUpperCase = 54;
        public static final int TRANSACTION_getPasswordQuality = 50;
        public static final int TRANSACTION_getRequiredPwdPatternRestrictions = 5;
        public static final int TRANSACTION_getSupportedBiometricAuthentications = 44;
        public static final int TRANSACTION_hasForbiddenCharacterSequence = 7;
        public static final int TRANSACTION_hasForbiddenData = 9;
        public static final int TRANSACTION_hasForbiddenNumericSequence = 6;
        public static final int TRANSACTION_hasForbiddenStringDistance = 8;
        public static final int TRANSACTION_hasMaxRepeatedCharacters = 10;
        public static final int TRANSACTION_isActivePasswordSufficient = 70;
        public static final int TRANSACTION_isBiometricAuthenticationEnabled = 40;
        public static final int TRANSACTION_isBiometricAuthenticationEnabledAsUser = 41;
        public static final int TRANSACTION_isChangeRequested = 14;
        public static final int TRANSACTION_isChangeRequestedAsUser = 15;
        public static final int TRANSACTION_isChangeRequestedForInner = 28;
        public static final int TRANSACTION_isClearLockAllowed = 88;
        public static final int TRANSACTION_isExternalStorageForFailedPasswordsWipeExcluded = 38;
        public static final int TRANSACTION_isMDMDisabledFP = 42;
        public static final int TRANSACTION_isMultifactorAuthenticationEnabled = 48;
        public static final int TRANSACTION_isPasswordPatternMatched = 11;
        public static final int TRANSACTION_isPasswordTableExist = 43;
        public static final int TRANSACTION_isPasswordVisibilityEnabled = 35;
        public static final int TRANSACTION_isPasswordVisibilityEnabledAsUser = 36;
        public static final int TRANSACTION_isResetPasswordTokenActive = 83;
        public static final int TRANSACTION_isScreenLockPatternVisibilityEnabled = 27;
        public static final int TRANSACTION_lock = 45;
        public static final int TRANSACTION_reboot = 86;
        public static final int TRANSACTION_resetPassword = 75;
        public static final int TRANSACTION_resetPasswordWithToken = 80;
        public static final int TRANSACTION_setBiometricAuthenticationEnabled = 39;
        public static final int TRANSACTION_setForbiddenStrings = 22;
        public static final int TRANSACTION_setKeyguardDisabledFeatures = 78;
        public static final int TRANSACTION_setKeyguardDisabledFeaturesInternal = 84;
        public static final int TRANSACTION_setMaximumCharacterOccurrences = 24;
        public static final int TRANSACTION_setMaximumCharacterSequenceLength = 30;
        public static final int TRANSACTION_setMaximumFailedPasswordsForDisable = 18;
        public static final int TRANSACTION_setMaximumFailedPasswordsForWipe = 73;
        public static final int TRANSACTION_setMaximumNumericSequenceLength = 20;
        public static final int TRANSACTION_setMaximumTimeToLock = 76;
        public static final int TRANSACTION_setMinimumCharacterChangeLength = 32;
        public static final int TRANSACTION_setMultifactorAuthenticationEnabled = 47;
        public static final int TRANSACTION_setPasswordChangeTimeout = 12;
        public static final int TRANSACTION_setPasswordExpirationTimeout = 67;
        public static final int TRANSACTION_setPasswordHistoryLength = 65;
        public static final int TRANSACTION_setPasswordLockDelay = 1;
        public static final int TRANSACTION_setPasswordMinimumLength = 51;
        public static final int TRANSACTION_setPasswordMinimumLetters = 57;
        public static final int TRANSACTION_setPasswordMinimumLowerCase = 55;
        public static final int TRANSACTION_setPasswordMinimumNonLetter = 61;
        public static final int TRANSACTION_setPasswordMinimumNumeric = 59;
        public static final int TRANSACTION_setPasswordMinimumSymbols = 63;
        public static final int TRANSACTION_setPasswordMinimumUpperCase = 53;
        public static final int TRANSACTION_setPasswordQuality = 49;
        public static final int TRANSACTION_setPasswordVisibilityEnabled = 34;
        public static final int TRANSACTION_setPwdChangeRequested = 17;
        public static final int TRANSACTION_setPwdChangeRequestedForInner = 29;
        public static final int TRANSACTION_setRequiredPasswordPattern = 3;
        public static final int TRANSACTION_setResetPasswordToken = 81;
        public static final int TRANSACTION_setScreenLockPatternVisibilityEnabled = 26;
        public static final int TRANSACTION_setTrustAgentConfiguration = 89;
        public static final int TRANSACTION_unlock = 46;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IPasswordPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean addRequiredPasswordPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean clearResetPasswordToken(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean deleteAllRestrictions(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean enforcePwdChange(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean excludeExternalStorageForFailedPasswordsWipe(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getCurrentFailedPasswordAttempts(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getCurrentFailedPasswordAttemptsInternal(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final List<String> getForbiddenStrings(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IPasswordPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getKeyguardDisabledFeatures(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getKeyguardDisabledFeaturesInternal(ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getMaximumCharacterOccurences(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getMaximumCharacterSequenceLength(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getMaximumFailedPasswordsForDisable(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getMaximumFailedPasswordsForWipe(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getMaximumNumericSequenceLength(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final long getMaximumTimeToLock(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getMinimumCharacterChangeLength(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordChangeTimeout(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final long getPasswordExpiration(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final long getPasswordExpirationTimeout(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordHistoryLength(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordLockDelay(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordMinimumLength(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordMinimumLetters(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordMinimumLowerCase(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordMinimumNonLetter(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordMinimumNumeric(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordMinimumSymbols(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordMinimumUpperCase(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int getPasswordQuality(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final String getRequiredPwdPatternRestrictions(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final Map getSupportedBiometricAuthentications(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean hasForbiddenCharacterSequence(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean hasForbiddenData(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean hasForbiddenNumericSequence(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean hasForbiddenStringDistance(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean hasMaxRepeatedCharacters(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isActivePasswordSufficient(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isBiometricAuthenticationEnabled(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isBiometricAuthenticationEnabledAsUser(int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int isChangeRequested(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int isChangeRequestedAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final int isChangeRequestedForInner() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isClearLockAllowed() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isExternalStorageForFailedPasswordsWipeExcluded(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isMDMDisabledFP(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isMultifactorAuthenticationEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isPasswordPatternMatched(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isPasswordTableExist(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isPasswordVisibilityEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isPasswordVisibilityEnabledAsUser(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isResetPasswordTokenActive(ContextInfo contextInfo, ComponentName componentName) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean isScreenLockPatternVisibilityEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean lock(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void reboot(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean resetPassword(ContextInfo contextInfo, String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean resetPasswordWithToken(ContextInfo contextInfo, ComponentName componentName, String str, byte[] bArr, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setBiometricAuthenticationEnabled(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setForbiddenStrings(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setKeyguardDisabledFeatures(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setKeyguardDisabledFeaturesInternal(ComponentName componentName, int i, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setMaximumCharacterOccurrences(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setMaximumCharacterSequenceLength(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setMaximumFailedPasswordsForDisable(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setMaximumFailedPasswordsForWipe(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setMaximumNumericSequenceLength(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setMaximumTimeToLock(ContextInfo contextInfo, ComponentName componentName, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setMinimumCharacterChangeLength(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setMultifactorAuthenticationEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setPasswordChangeTimeout(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordExpirationTimeout(ContextInfo contextInfo, ComponentName componentName, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordHistoryLength(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setPasswordLockDelay(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordMinimumLength(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordMinimumLetters(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordMinimumLowerCase(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordMinimumNonLetter(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordMinimumNumeric(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordMinimumSymbols(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordMinimumUpperCase(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setPasswordQuality(ContextInfo contextInfo, ComponentName componentName, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setPasswordVisibilityEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setPwdChangeRequested(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setPwdChangeRequestedForInner(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setRequiredPasswordPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setResetPasswordToken(ContextInfo contextInfo, ComponentName componentName, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean setScreenLockPatternVisibilityEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final void setTrustAgentConfiguration(ContextInfo contextInfo, ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(componentName2, 0);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
            public final boolean unlock(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPasswordPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IPasswordPolicy.DESCRIPTOR);
        }

        public static IPasswordPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPasswordPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPasswordPolicy)) ? new Proxy(iBinder) : (IPasswordPolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPasswordPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPasswordPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean passwordLockDelay = setPasswordLockDelay(contextInfo, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(passwordLockDelay);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordLockDelay2 = getPasswordLockDelay(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordLockDelay2);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean requiredPasswordPattern = setRequiredPasswordPattern(contextInfo3, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requiredPasswordPattern);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean deleteAllRestrictions = deleteAllRestrictions(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteAllRestrictions);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String requiredPwdPatternRestrictions = getRequiredPwdPatternRestrictions(contextInfo5, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeString(requiredPwdPatternRestrictions);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasForbiddenNumericSequence = hasForbiddenNumericSequence(contextInfo6, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasForbiddenNumericSequence);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasForbiddenCharacterSequence = hasForbiddenCharacterSequence(contextInfo7, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasForbiddenCharacterSequence);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasForbiddenStringDistance = hasForbiddenStringDistance(contextInfo8, readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasForbiddenStringDistance);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasForbiddenData = hasForbiddenData(contextInfo9, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasForbiddenData);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasMaxRepeatedCharacters = hasMaxRepeatedCharacters(contextInfo10, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasMaxRepeatedCharacters);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPasswordPatternMatched = isPasswordPatternMatched(contextInfo11, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPasswordPatternMatched);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean passwordChangeTimeout = setPasswordChangeTimeout(contextInfo12, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(passwordChangeTimeout);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordChangeTimeout2 = getPasswordChangeTimeout(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordChangeTimeout2);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int isChangeRequested = isChangeRequested(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeInt(isChangeRequested);
                    return true;
                case 15:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int isChangeRequestedAsUser = isChangeRequestedAsUser(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(isChangeRequestedAsUser);
                    return true;
                case 16:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean enforcePwdChange = enforcePwdChange(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enforcePwdChange);
                    return true;
                case 17:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean pwdChangeRequested = setPwdChangeRequested(contextInfo16, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pwdChangeRequested);
                    return true;
                case 18:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean maximumFailedPasswordsForDisable = setMaximumFailedPasswordsForDisable(contextInfo17, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maximumFailedPasswordsForDisable);
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int maximumFailedPasswordsForDisable2 = getMaximumFailedPasswordsForDisable(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeInt(maximumFailedPasswordsForDisable2);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean maximumNumericSequenceLength = setMaximumNumericSequenceLength(contextInfo19, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maximumNumericSequenceLength);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int maximumNumericSequenceLength2 = getMaximumNumericSequenceLength(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeInt(maximumNumericSequenceLength2);
                    return true;
                case 22:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean forbiddenStrings = setForbiddenStrings(contextInfo21, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forbiddenStrings);
                    return true;
                case 23:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<String> forbiddenStrings2 = getForbiddenStrings(contextInfo22, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeStringList(forbiddenStrings2);
                    return true;
                case 24:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean maximumCharacterOccurrences = setMaximumCharacterOccurrences(contextInfo23, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maximumCharacterOccurrences);
                    return true;
                case 25:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int maximumCharacterOccurences = getMaximumCharacterOccurences(contextInfo24);
                    parcel2.writeNoException();
                    parcel2.writeInt(maximumCharacterOccurences);
                    return true;
                case 26:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean screenLockPatternVisibilityEnabled = setScreenLockPatternVisibilityEnabled(contextInfo25, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(screenLockPatternVisibilityEnabled);
                    return true;
                case 27:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isScreenLockPatternVisibilityEnabled = isScreenLockPatternVisibilityEnabled(contextInfo26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenLockPatternVisibilityEnabled);
                    return true;
                case 28:
                    int isChangeRequestedForInner = isChangeRequestedForInner();
                    parcel2.writeNoException();
                    parcel2.writeInt(isChangeRequestedForInner);
                    return true;
                case 29:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean pwdChangeRequestedForInner = setPwdChangeRequestedForInner(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pwdChangeRequestedForInner);
                    return true;
                case 30:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean maximumCharacterSequenceLength = setMaximumCharacterSequenceLength(contextInfo27, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maximumCharacterSequenceLength);
                    return true;
                case 31:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int maximumCharacterSequenceLength2 = getMaximumCharacterSequenceLength(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeInt(maximumCharacterSequenceLength2);
                    return true;
                case 32:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean minimumCharacterChangeLength = setMinimumCharacterChangeLength(contextInfo29, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minimumCharacterChangeLength);
                    return true;
                case 33:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int minimumCharacterChangeLength2 = getMinimumCharacterChangeLength(contextInfo30);
                    parcel2.writeNoException();
                    parcel2.writeInt(minimumCharacterChangeLength2);
                    return true;
                case 34:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean passwordVisibilityEnabled = setPasswordVisibilityEnabled(contextInfo31, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(passwordVisibilityEnabled);
                    return true;
                case 35:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPasswordVisibilityEnabled = isPasswordVisibilityEnabled(contextInfo32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPasswordVisibilityEnabled);
                    return true;
                case 36:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPasswordVisibilityEnabledAsUser = isPasswordVisibilityEnabledAsUser(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPasswordVisibilityEnabledAsUser);
                    return true;
                case 37:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean excludeExternalStorageForFailedPasswordsWipe = excludeExternalStorageForFailedPasswordsWipe(contextInfo33, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(excludeExternalStorageForFailedPasswordsWipe);
                    return true;
                case 38:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isExternalStorageForFailedPasswordsWipeExcluded = isExternalStorageForFailedPasswordsWipeExcluded(contextInfo34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExternalStorageForFailedPasswordsWipeExcluded);
                    return true;
                case 39:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt12 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean biometricAuthenticationEnabled = setBiometricAuthenticationEnabled(contextInfo35, readInt12, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(biometricAuthenticationEnabled);
                    return true;
                case 40:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBiometricAuthenticationEnabled = isBiometricAuthenticationEnabled(contextInfo36, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBiometricAuthenticationEnabled);
                    return true;
                case 41:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBiometricAuthenticationEnabledAsUser = isBiometricAuthenticationEnabledAsUser(readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBiometricAuthenticationEnabledAsUser);
                    return true;
                case 42:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMDMDisabledFP = isMDMDisabledFP(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMDMDisabledFP);
                    return true;
                case 43:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPasswordTableExist = isPasswordTableExist(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPasswordTableExist);
                    return true;
                case 44:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    Map supportedBiometricAuthentications = getSupportedBiometricAuthentications(contextInfo38);
                    parcel2.writeNoException();
                    parcel2.writeMap(supportedBiometricAuthentications);
                    return true;
                case 45:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean lock = lock(contextInfo39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lock);
                    return true;
                case 46:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean unlock = unlock(contextInfo40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unlock);
                    return true;
                case 47:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean multifactorAuthenticationEnabled = setMultifactorAuthenticationEnabled(contextInfo41, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(multifactorAuthenticationEnabled);
                    return true;
                case 48:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMultifactorAuthenticationEnabled = isMultifactorAuthenticationEnabled(contextInfo42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMultifactorAuthenticationEnabled);
                    return true;
                case 49:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordQuality(contextInfo43, componentName, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordQuality = getPasswordQuality(contextInfo44, componentName2);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordQuality);
                    return true;
                case 51:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordMinimumLength(contextInfo45, componentName3, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordMinimumLength = getPasswordMinimumLength(contextInfo46, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumLength);
                    return true;
                case 53:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordMinimumUpperCase(contextInfo47, componentName5, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordMinimumUpperCase = getPasswordMinimumUpperCase(contextInfo48, componentName6);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumUpperCase);
                    return true;
                case 55:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordMinimumLowerCase(contextInfo49, componentName7, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordMinimumLowerCase = getPasswordMinimumLowerCase(contextInfo50, componentName8);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumLowerCase);
                    return true;
                case 57:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordMinimumLetters(contextInfo51, componentName9, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordMinimumLetters = getPasswordMinimumLetters(contextInfo52, componentName10);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumLetters);
                    return true;
                case 59:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordMinimumNumeric(contextInfo53, componentName11, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordMinimumNumeric = getPasswordMinimumNumeric(contextInfo54, componentName12);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumNumeric);
                    return true;
                case 61:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName13 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordMinimumNonLetter(contextInfo55, componentName13, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName14 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordMinimumNonLetter = getPasswordMinimumNonLetter(contextInfo56, componentName14);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumNonLetter);
                    return true;
                case 63:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName15 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordMinimumSymbols(contextInfo57, componentName15, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName16 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordMinimumSymbols = getPasswordMinimumSymbols(contextInfo58, componentName16);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordMinimumSymbols);
                    return true;
                case 65:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName17 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPasswordHistoryLength(contextInfo59, componentName17, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName18 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int passwordHistoryLength = getPasswordHistoryLength(contextInfo60, componentName18);
                    parcel2.writeNoException();
                    parcel2.writeInt(passwordHistoryLength);
                    return true;
                case 67:
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName19 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setPasswordExpirationTimeout(contextInfo61, componentName19, readLong);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName20 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    long passwordExpirationTimeout = getPasswordExpirationTimeout(contextInfo62, componentName20);
                    parcel2.writeNoException();
                    parcel2.writeLong(passwordExpirationTimeout);
                    return true;
                case 69:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName21 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    long passwordExpiration = getPasswordExpiration(contextInfo63, componentName21);
                    parcel2.writeNoException();
                    parcel2.writeLong(passwordExpiration);
                    return true;
                case 70:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isActivePasswordSufficient = isActivePasswordSufficient(contextInfo64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivePasswordSufficient);
                    return true;
                case 71:
                    ContextInfo contextInfo65 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int currentFailedPasswordAttempts = getCurrentFailedPasswordAttempts(contextInfo65);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentFailedPasswordAttempts);
                    return true;
                case 72:
                    ContextInfo contextInfo66 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int currentFailedPasswordAttemptsInternal = getCurrentFailedPasswordAttemptsInternal(contextInfo66);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentFailedPasswordAttemptsInternal);
                    return true;
                case 73:
                    ContextInfo contextInfo67 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName22 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMaximumFailedPasswordsForWipe(contextInfo67, componentName22, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    ContextInfo contextInfo68 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName23 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int maximumFailedPasswordsForWipe = getMaximumFailedPasswordsForWipe(contextInfo68, componentName23);
                    parcel2.writeNoException();
                    parcel2.writeInt(maximumFailedPasswordsForWipe);
                    return true;
                case 75:
                    ContextInfo contextInfo69 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetPassword = resetPassword(contextInfo69, readString9, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPassword);
                    return true;
                case 76:
                    ContextInfo contextInfo70 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName24 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setMaximumTimeToLock(contextInfo70, componentName24, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    ContextInfo contextInfo71 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName25 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    long maximumTimeToLock = getMaximumTimeToLock(contextInfo71, componentName25);
                    parcel2.writeNoException();
                    parcel2.writeLong(maximumTimeToLock);
                    return true;
                case 78:
                    ContextInfo contextInfo72 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName26 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setKeyguardDisabledFeatures(contextInfo72, componentName26, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    ContextInfo contextInfo73 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName27 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int keyguardDisabledFeatures = getKeyguardDisabledFeatures(contextInfo73, componentName27);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyguardDisabledFeatures);
                    return true;
                case 80:
                    ContextInfo contextInfo74 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName28 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString10 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean resetPasswordWithToken = resetPasswordWithToken(contextInfo74, componentName28, readString10, createByteArray, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPasswordWithToken);
                    return true;
                case 81:
                    ContextInfo contextInfo75 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName29 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean resetPasswordToken = setResetPasswordToken(contextInfo75, componentName29, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetPasswordToken);
                    return true;
                case 82:
                    ContextInfo contextInfo76 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName30 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearResetPasswordToken = clearResetPasswordToken(contextInfo76, componentName30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearResetPasswordToken);
                    return true;
                case 83:
                    ContextInfo contextInfo77 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName31 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isResetPasswordTokenActive = isResetPasswordTokenActive(contextInfo77, componentName31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isResetPasswordTokenActive);
                    return true;
                case 84:
                    ComponentName componentName32 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setKeyguardDisabledFeaturesInternal(componentName32, readInt30, readInt31);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    ComponentName componentName33 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int keyguardDisabledFeaturesInternal = getKeyguardDisabledFeaturesInternal(componentName33, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeInt(keyguardDisabledFeaturesInternal);
                    return true;
                case 86:
                    ContextInfo contextInfo78 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reboot(contextInfo78, readString11);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    ContextInfo contextInfo79 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addRequiredPasswordPattern = addRequiredPasswordPattern(contextInfo79, readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addRequiredPasswordPattern);
                    return true;
                case 88:
                    boolean isClearLockAllowed = isClearLockAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClearLockAllowed);
                    return true;
                case 89:
                    ContextInfo contextInfo80 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ComponentName componentName34 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    ComponentName componentName35 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTrustAgentConfiguration(contextInfo80, componentName34, componentName35, persistableBundle);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IPasswordPolicy {
        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean addRequiredPasswordPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean clearResetPasswordToken(ContextInfo contextInfo, ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean deleteAllRestrictions(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean enforcePwdChange(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean excludeExternalStorageForFailedPasswordsWipe(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getCurrentFailedPasswordAttempts(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getCurrentFailedPasswordAttemptsInternal(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final List<String> getForbiddenStrings(ContextInfo contextInfo, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getKeyguardDisabledFeatures(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getKeyguardDisabledFeaturesInternal(ComponentName componentName, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getMaximumCharacterOccurences(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getMaximumCharacterSequenceLength(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getMaximumFailedPasswordsForDisable(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getMaximumFailedPasswordsForWipe(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getMaximumNumericSequenceLength(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final long getMaximumTimeToLock(ContextInfo contextInfo, ComponentName componentName) {
            return 0L;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getMinimumCharacterChangeLength(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordChangeTimeout(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final long getPasswordExpiration(ContextInfo contextInfo, ComponentName componentName) {
            return 0L;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final long getPasswordExpirationTimeout(ContextInfo contextInfo, ComponentName componentName) {
            return 0L;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordHistoryLength(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordLockDelay(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordMinimumLength(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordMinimumLetters(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordMinimumLowerCase(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordMinimumNonLetter(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordMinimumNumeric(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordMinimumSymbols(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordMinimumUpperCase(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int getPasswordQuality(ContextInfo contextInfo, ComponentName componentName) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final String getRequiredPwdPatternRestrictions(ContextInfo contextInfo, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final Map getSupportedBiometricAuthentications(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean hasForbiddenCharacterSequence(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean hasForbiddenData(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean hasForbiddenNumericSequence(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean hasForbiddenStringDistance(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean hasMaxRepeatedCharacters(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isActivePasswordSufficient(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isBiometricAuthenticationEnabled(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isBiometricAuthenticationEnabledAsUser(int i, int i2) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int isChangeRequested(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int isChangeRequestedAsUser(int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final int isChangeRequestedForInner() {
            return 0;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isClearLockAllowed() {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isExternalStorageForFailedPasswordsWipeExcluded(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isMDMDisabledFP(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isMultifactorAuthenticationEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isPasswordPatternMatched(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isPasswordTableExist(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isPasswordVisibilityEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isPasswordVisibilityEnabledAsUser(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isResetPasswordTokenActive(ContextInfo contextInfo, ComponentName componentName) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean isScreenLockPatternVisibilityEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean lock(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean resetPassword(ContextInfo contextInfo, String str, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean resetPasswordWithToken(ContextInfo contextInfo, ComponentName componentName, String str, byte[] bArr, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setBiometricAuthenticationEnabled(ContextInfo contextInfo, int i, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setForbiddenStrings(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setMaximumCharacterOccurrences(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setMaximumCharacterSequenceLength(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setMaximumFailedPasswordsForDisable(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setMaximumNumericSequenceLength(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setMinimumCharacterChangeLength(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setMultifactorAuthenticationEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setPasswordChangeTimeout(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setPasswordLockDelay(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setPasswordVisibilityEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setPwdChangeRequested(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setPwdChangeRequestedForInner(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setRequiredPasswordPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setResetPasswordToken(ContextInfo contextInfo, ComponentName componentName, byte[] bArr) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean setScreenLockPatternVisibilityEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final boolean unlock(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void reboot(ContextInfo contextInfo, String str) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setKeyguardDisabledFeatures(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setKeyguardDisabledFeaturesInternal(ComponentName componentName, int i, int i2) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setMaximumFailedPasswordsForWipe(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setMaximumTimeToLock(ContextInfo contextInfo, ComponentName componentName, long j) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordExpirationTimeout(ContextInfo contextInfo, ComponentName componentName, long j) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordHistoryLength(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordMinimumLength(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordMinimumLetters(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordMinimumLowerCase(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordMinimumNonLetter(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordMinimumNumeric(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordMinimumSymbols(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordMinimumUpperCase(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setPasswordQuality(ContextInfo contextInfo, ComponentName componentName, int i) {
        }

        @Override // com.samsung.android.knox.devicesecurity.IPasswordPolicy
        public final void setTrustAgentConfiguration(ContextInfo contextInfo, ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) {
        }
    }
}
