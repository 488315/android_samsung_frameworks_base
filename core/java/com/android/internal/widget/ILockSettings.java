package com.android.internal.widget;

import android.app.PendingIntent;
import android.app.RemoteLockscreenValidationResult;
import android.app.RemoteLockscreenValidationSession;
import android.app.trust.IStrongAuthTracker;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.keystore.recovery.KeyChainProtectionParams;
import android.security.keystore.recovery.KeyChainSnapshot;
import android.security.keystore.recovery.RecoveryCertPath;
import android.security.keystore.recovery.WrappedApplicationKey;
import com.android.internal.widget.ICheckCredentialProgressCallback;
import com.android.internal.widget.IRemoteLockMonitorCallback;
import com.android.internal.widget.IWeakEscrowTokenActivatedListener;
import com.android.internal.widget.IWeakEscrowTokenRemovedListener;
import com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public interface ILockSettings extends IInterface {
    long addWeakEscrowToken(byte[] bArr, int i, IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) throws RemoteException;

    boolean changeToken(byte[] bArr, long j, byte[] bArr2, long j2, int i) throws RemoteException;

    boolean checkAppLockBackupPin(String str, int i) throws RemoteException;

    boolean checkAppLockFingerprintPassword(String str, int i) throws RemoteException;

    boolean checkAppLockPassword(String str, int i) throws RemoteException;

    boolean checkAppLockPatternWithHash(String str, int i, byte[] bArr) throws RemoteException;

    boolean checkAppLockPin(String str, int i) throws RemoteException;

    boolean checkCarrierPassword(byte[] bArr, int i) throws RemoteException;

    VerifyCredentialResponse checkCredential(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws RemoteException;

    VerifyCredentialResponse checkCredentialForDualDarDo(LockscreenCredential lockscreenCredential, int i, int i2, IDualDarAuthProgressCallback iDualDarAuthProgressCallback) throws RemoteException;

    boolean checkFMMPassword(byte[] bArr, int i) throws RemoteException;

    void checkRemoteLockPassword(int i, byte[] bArr, int i2, IRemoteCallback iRemoteCallback) throws RemoteException;

    void closeSession(String str) throws RemoteException;

    void expirePreviousData() throws RemoteException;

    String generateKey(String str) throws RemoteException;

    String generateKeyWithMetadata(String str, byte[] bArr) throws RemoteException;

    boolean getBoolean(String str, boolean z, int i) throws RemoteException;

    boolean getCarrierLock(int i) throws RemoteException;

    int getCredentialType(int i) throws RemoteException;

    long getExpireTimeForPrev() throws RemoteException;

    int getFailureCount(int i) throws RemoteException;

    byte[] getHashFactor(LockscreenCredential lockscreenCredential, int i) throws RemoteException;

    String getKey(String str) throws RemoteException;

    KeyChainSnapshot getKeyChainSnapshot() throws RemoteException;

    long getLong(String str, long j, int i) throws RemoteException;

    int getPinLength(int i) throws RemoteException;

    int[] getRecoverySecretTypes() throws RemoteException;

    Map getRecoveryStatus() throws RemoteException;

    boolean getSeparateProfileChallengeEnabled(int i) throws RemoteException;

    String getString(String str, String str2, int i) throws RemoteException;

    int getStrongAuthForUser(int i) throws RemoteException;

    boolean hasPendingEscrowToken(int i) throws RemoteException;

    boolean hasSecureLockScreen() throws RemoteException;

    boolean haveAppLockBackupPin(int i) throws RemoteException;

    boolean haveAppLockFingerprintPassword(int i) throws RemoteException;

    boolean haveAppLockPassword(int i) throws RemoteException;

    boolean haveAppLockPattern(int i) throws RemoteException;

    boolean haveAppLockPin(int i) throws RemoteException;

    boolean haveCarrierPassword(int i) throws RemoteException;

    boolean haveFMMPassword(int i) throws RemoteException;

    String importKey(String str, byte[] bArr) throws RemoteException;

    String importKeyWithMetadata(String str, byte[] bArr, byte[] bArr2) throws RemoteException;

    void initRecoveryServiceWithSigFile(String str, byte[] bArr, byte[] bArr2) throws RemoteException;

    boolean isRemoteLock(int i) throws RemoteException;

    boolean isSupportWeaver() throws RemoteException;

    boolean isWeakEscrowTokenActive(long j, int i) throws RemoteException;

    boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) throws RemoteException;

    void notifyPasswordChangedForEnterpriseUser(LockscreenCredential lockscreenCredential, int i) throws RemoteException;

    Map recoverKeyChainSnapshot(String str, byte[] bArr, List<WrappedApplicationKey> list) throws RemoteException;

    boolean refreshStoredPinLength(int i) throws RemoteException;

    void registerRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) throws RemoteException;

    void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException;

    boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws RemoteException;

    void removeCachedUnifiedChallenge(int i) throws RemoteException;

    void removeGatekeeperPasswordHandle(long j) throws RemoteException;

    void removeKey(String str) throws RemoteException;

    boolean removeWeakEscrowToken(long j, int i) throws RemoteException;

    void reportSuccessfulBiometricUnlock(boolean z, int i) throws RemoteException;

    void requestRemoteLockInfo(int i) throws RemoteException;

    void requireStrongAuth(int i, int i2) throws RemoteException;

    void resetKeyStore(int i) throws RemoteException;

    void scheduleNonStrongBiometricIdleTimeout(int i) throws RemoteException;

    void sendLockTypeChangedInfo(int i) throws RemoteException;

    void setAppLockBackupPin(String str, int i) throws RemoteException;

    void setAppLockFingerprintPassword(String str, int i) throws RemoteException;

    void setAppLockPassword(String str, int i) throws RemoteException;

    void setAppLockPattern(String str, int i) throws RemoteException;

    void setAppLockPin(String str, int i) throws RemoteException;

    void setBoolean(String str, boolean z, int i) throws RemoteException;

    void setCarrierLockEnabled(int i) throws RemoteException;

    boolean setKnoxGuard(int i, RemoteLockInfo remoteLockInfo) throws RemoteException;

    void setLockCarrierPassword(byte[] bArr, int i) throws RemoteException;

    boolean setLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) throws RemoteException;

    boolean setLockCredentialWithIgnoreNotifyIfNeeded(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) throws RemoteException;

    void setLockFMMPassword(byte[] bArr, int i) throws RemoteException;

    void setLockModeChangedCallback(IRemoteCallback iRemoteCallback) throws RemoteException;

    void setLong(String str, long j, int i) throws RemoteException;

    void setRecoverySecretTypes(int[] iArr) throws RemoteException;

    void setRecoveryStatus(String str, int i) throws RemoteException;

    void setRemoteLock(int i, RemoteLockInfo remoteLockInfo) throws RemoteException;

    void setSecurityDebugLevel(int i) throws RemoteException;

    void setSeparateProfileChallengeEnabled(int i, boolean z, LockscreenCredential lockscreenCredential) throws RemoteException;

    void setServerParams(byte[] bArr) throws RemoteException;

    void setShellCommandCallback(IRemoteCallback iRemoteCallback) throws RemoteException;

    void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) throws RemoteException;

    void setString(String str, String str2, int i) throws RemoteException;

    byte[] startRecoverySessionWithCertPath(String str, String str2, RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, List<KeyChainProtectionParams> list) throws RemoteException;

    RemoteLockscreenValidationSession startRemoteLockscreenValidation() throws RemoteException;

    void systemReady() throws RemoteException;

    boolean tryUnlockWithCachedUnifiedChallenge(int i) throws RemoteException;

    void unlockUserKeyIfUnsecured(int i) throws RemoteException;

    void unregisterRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) throws RemoteException;

    void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException;

    boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws RemoteException;

    boolean updateCarrierLock(int i) throws RemoteException;

    void updateSdpMdfppForSystem(int i, long j) throws RemoteException;

    void userPresent(int i) throws RemoteException;

    RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) throws RemoteException;

    VerifyCredentialResponse verifyCredential(LockscreenCredential lockscreenCredential, int i, int i2) throws RemoteException;

    VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) throws RemoteException;

    VerifyCredentialResponse verifyTiedProfileChallenge(LockscreenCredential lockscreenCredential, int i, int i2) throws RemoteException;

    VerifyCredentialResponse verifyToken(byte[] bArr, long j, int i) throws RemoteException;

    public static class Default implements ILockSettings {
        @Override // com.android.internal.widget.ILockSettings
        public void setBoolean(String key, boolean value, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setLong(String key, long value, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setString(String key, String value, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean getBoolean(String key, boolean defaultValue, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public long getLong(String key, long defaultValue, int userId) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String getString(String key, String defaultValue, int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean setLockCredential(LockscreenCredential credential, LockscreenCredential savedCredential, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void resetKeyStore(int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse checkCredential(LockscreenCredential credential, int userId, ICheckCredentialProgressCallback progressCallback) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse verifyCredential(LockscreenCredential credential, int userId, int flags) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse verifyTiedProfileChallenge(LockscreenCredential credential, int userId, int flags) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse verifyGatekeeperPasswordHandle(long gatekeeperPasswordHandle, long challenge, int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void removeGatekeeperPasswordHandle(long gatekeeperPasswordHandle) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getCredentialType(int userId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getPinLength(int userId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean refreshStoredPinLength(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public byte[] getHashFactor(LockscreenCredential currentCredential, int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setSeparateProfileChallengeEnabled(int userId, boolean enabled, LockscreenCredential managedUserPassword) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean getSeparateProfileChallengeEnabled(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void registerStrongAuthTracker(IStrongAuthTracker tracker) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void unregisterStrongAuthTracker(IStrongAuthTracker tracker) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void requireStrongAuth(int strongAuthReason, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void reportSuccessfulBiometricUnlock(boolean isStrongBiometric, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void scheduleNonStrongBiometricIdleTimeout(int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void systemReady() throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void userPresent(int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getStrongAuthForUser(int userId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean hasPendingEscrowToken(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void initRecoveryServiceWithSigFile(String rootCertificateAlias, byte[] recoveryServiceCertFile, byte[] recoveryServiceSigFile) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String generateKey(String alias) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String generateKeyWithMetadata(String alias, byte[] metadata) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String importKey(String alias, byte[] keyBytes) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String importKeyWithMetadata(String alias, byte[] keyBytes, byte[] metadata) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String getKey(String alias) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void removeKey(String alias) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setSnapshotCreatedPendingIntent(PendingIntent intent) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setServerParams(byte[] serverParams) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setRecoveryStatus(String alias, int status) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public Map getRecoveryStatus() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setRecoverySecretTypes(int[] secretTypes) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public int[] getRecoverySecretTypes() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public byte[] startRecoverySessionWithCertPath(String sessionId, String rootCertificateAlias, RecoveryCertPath verifierCertPath, byte[] vaultParams, byte[] vaultChallenge, List<KeyChainProtectionParams> secrets) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public Map recoverKeyChainSnapshot(String sessionId, byte[] recoveryKeyBlob, List<WrappedApplicationKey> applicationKeys) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void closeSession(String sessionId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public RemoteLockscreenValidationSession startRemoteLockscreenValidation() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] encryptedCredential) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean hasSecureLockScreen() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean tryUnlockWithCachedUnifiedChallenge(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void removeCachedUnifiedChallenge(int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener listener) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener listener) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public long addWeakEscrowToken(byte[] token, int userId, IWeakEscrowTokenActivatedListener callback) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean removeWeakEscrowToken(long handle, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isWeakEscrowTokenActive(long handle, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isWeakEscrowTokenValid(long handle, byte[] token, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void unlockUserKeyIfUnsecured(int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void registerRemoteLockCallback(int type, IRemoteLockMonitorCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void unregisterRemoteLockCallback(int type, IRemoteLockMonitorCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean setKnoxGuard(int userId, RemoteLockInfo data) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setRemoteLock(int userId, RemoteLockInfo data) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void checkRemoteLockPassword(int type, byte[] password, int userId, IRemoteCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void requestRemoteLockInfo(int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setLockFMMPassword(byte[] password, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveFMMPassword(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkFMMPassword(byte[] password, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean getCarrierLock(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean updateCarrierLock(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setCarrierLockEnabled(int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setLockCarrierPassword(byte[] password, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveCarrierPassword(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkCarrierPassword(byte[] password, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isRemoteLock(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setLockModeChangedCallback(IRemoteCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void sendLockTypeChangedInfo(int secureState) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getFailureCount(int userId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public long getExpireTimeForPrev() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void expirePreviousData() throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isSupportWeaver() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setSecurityDebugLevel(int level) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setShellCommandCallback(IRemoteCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockPin(String password, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockPassword(String password, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockPattern(String password, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockBackupPin(String password, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockFingerprintPassword(String password, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockPin(String password, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockPassword(String password, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockPatternWithHash(String password, int userId, byte[] hash) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockBackupPin(String password, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockFingerprintPassword(String password, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockPin(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockPassword(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockPattern(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockBackupPin(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockFingerprintPassword(int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean setLockCredentialWithIgnoreNotifyIfNeeded(LockscreenCredential credential, LockscreenCredential savedCredential, int userId, boolean ignoreNotifyPasswordChanged) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void notifyPasswordChangedForEnterpriseUser(LockscreenCredential newCredential, int userId) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse verifyToken(byte[] token, long tokenHandle, int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean changeToken(byte[] newToken, long newHandle, byte[] oldToken, long oldHandle, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void updateSdpMdfppForSystem(int userId, long value) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse checkCredentialForDualDarDo(LockscreenCredential credential, int userId, int option, IDualDarAuthProgressCallback progressCallback) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILockSettings {
        public static final String DESCRIPTOR = "com.android.internal.widget.ILockSettings";
        static final int TRANSACTION_addWeakEscrowToken = 53;
        static final int TRANSACTION_changeToken = 100;
        static final int TRANSACTION_checkAppLockBackupPin = 90;
        static final int TRANSACTION_checkAppLockFingerprintPassword = 91;
        static final int TRANSACTION_checkAppLockPassword = 88;
        static final int TRANSACTION_checkAppLockPatternWithHash = 89;
        static final int TRANSACTION_checkAppLockPin = 87;
        static final int TRANSACTION_checkCarrierPassword = 72;
        static final int TRANSACTION_checkCredential = 9;
        static final int TRANSACTION_checkCredentialForDualDarDo = 102;
        static final int TRANSACTION_checkFMMPassword = 66;
        static final int TRANSACTION_checkRemoteLockPassword = 62;
        static final int TRANSACTION_closeSession = 45;
        static final int TRANSACTION_expirePreviousData = 78;
        static final int TRANSACTION_generateKey = 31;
        static final int TRANSACTION_generateKeyWithMetadata = 32;
        static final int TRANSACTION_getBoolean = 4;
        static final int TRANSACTION_getCarrierLock = 67;
        static final int TRANSACTION_getCredentialType = 14;
        static final int TRANSACTION_getExpireTimeForPrev = 77;
        static final int TRANSACTION_getFailureCount = 76;
        static final int TRANSACTION_getHashFactor = 17;
        static final int TRANSACTION_getKey = 35;
        static final int TRANSACTION_getKeyChainSnapshot = 30;
        static final int TRANSACTION_getLong = 5;
        static final int TRANSACTION_getPinLength = 15;
        static final int TRANSACTION_getRecoverySecretTypes = 42;
        static final int TRANSACTION_getRecoveryStatus = 40;
        static final int TRANSACTION_getSeparateProfileChallengeEnabled = 19;
        static final int TRANSACTION_getString = 6;
        static final int TRANSACTION_getStrongAuthForUser = 27;
        static final int TRANSACTION_hasPendingEscrowToken = 28;
        static final int TRANSACTION_hasSecureLockScreen = 48;
        static final int TRANSACTION_haveAppLockBackupPin = 95;
        static final int TRANSACTION_haveAppLockFingerprintPassword = 96;
        static final int TRANSACTION_haveAppLockPassword = 93;
        static final int TRANSACTION_haveAppLockPattern = 94;
        static final int TRANSACTION_haveAppLockPin = 92;
        static final int TRANSACTION_haveCarrierPassword = 71;
        static final int TRANSACTION_haveFMMPassword = 65;
        static final int TRANSACTION_importKey = 33;
        static final int TRANSACTION_importKeyWithMetadata = 34;
        static final int TRANSACTION_initRecoveryServiceWithSigFile = 29;
        static final int TRANSACTION_isRemoteLock = 73;
        static final int TRANSACTION_isSupportWeaver = 79;
        static final int TRANSACTION_isWeakEscrowTokenActive = 55;
        static final int TRANSACTION_isWeakEscrowTokenValid = 56;
        static final int TRANSACTION_notifyPasswordChangedForEnterpriseUser = 98;
        static final int TRANSACTION_recoverKeyChainSnapshot = 44;
        static final int TRANSACTION_refreshStoredPinLength = 16;
        static final int TRANSACTION_registerRemoteLockCallback = 58;
        static final int TRANSACTION_registerStrongAuthTracker = 20;
        static final int TRANSACTION_registerWeakEscrowTokenRemovedListener = 51;
        static final int TRANSACTION_removeCachedUnifiedChallenge = 50;
        static final int TRANSACTION_removeGatekeeperPasswordHandle = 13;
        static final int TRANSACTION_removeKey = 36;
        static final int TRANSACTION_removeWeakEscrowToken = 54;
        static final int TRANSACTION_reportSuccessfulBiometricUnlock = 23;
        static final int TRANSACTION_requestRemoteLockInfo = 63;
        static final int TRANSACTION_requireStrongAuth = 22;
        static final int TRANSACTION_resetKeyStore = 8;
        static final int TRANSACTION_scheduleNonStrongBiometricIdleTimeout = 24;
        static final int TRANSACTION_sendLockTypeChangedInfo = 75;
        static final int TRANSACTION_setAppLockBackupPin = 85;
        static final int TRANSACTION_setAppLockFingerprintPassword = 86;
        static final int TRANSACTION_setAppLockPassword = 83;
        static final int TRANSACTION_setAppLockPattern = 84;
        static final int TRANSACTION_setAppLockPin = 82;
        static final int TRANSACTION_setBoolean = 1;
        static final int TRANSACTION_setCarrierLockEnabled = 69;
        static final int TRANSACTION_setKnoxGuard = 60;
        static final int TRANSACTION_setLockCarrierPassword = 70;
        static final int TRANSACTION_setLockCredential = 7;
        static final int TRANSACTION_setLockCredentialWithIgnoreNotifyIfNeeded = 97;
        static final int TRANSACTION_setLockFMMPassword = 64;
        static final int TRANSACTION_setLockModeChangedCallback = 74;
        static final int TRANSACTION_setLong = 2;
        static final int TRANSACTION_setRecoverySecretTypes = 41;
        static final int TRANSACTION_setRecoveryStatus = 39;
        static final int TRANSACTION_setRemoteLock = 61;
        static final int TRANSACTION_setSecurityDebugLevel = 80;
        static final int TRANSACTION_setSeparateProfileChallengeEnabled = 18;
        static final int TRANSACTION_setServerParams = 38;
        static final int TRANSACTION_setShellCommandCallback = 81;
        static final int TRANSACTION_setSnapshotCreatedPendingIntent = 37;
        static final int TRANSACTION_setString = 3;
        static final int TRANSACTION_startRecoverySessionWithCertPath = 43;
        static final int TRANSACTION_startRemoteLockscreenValidation = 46;
        static final int TRANSACTION_systemReady = 25;
        static final int TRANSACTION_tryUnlockWithCachedUnifiedChallenge = 49;
        static final int TRANSACTION_unlockUserKeyIfUnsecured = 57;
        static final int TRANSACTION_unregisterRemoteLockCallback = 59;
        static final int TRANSACTION_unregisterStrongAuthTracker = 21;
        static final int TRANSACTION_unregisterWeakEscrowTokenRemovedListener = 52;
        static final int TRANSACTION_updateCarrierLock = 68;
        static final int TRANSACTION_updateSdpMdfppForSystem = 101;
        static final int TRANSACTION_userPresent = 26;
        static final int TRANSACTION_validateRemoteLockscreen = 47;
        static final int TRANSACTION_verifyCredential = 10;
        static final int TRANSACTION_verifyGatekeeperPasswordHandle = 12;
        static final int TRANSACTION_verifyTiedProfileChallenge = 11;
        static final int TRANSACTION_verifyToken = 99;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILockSettings asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ILockSettings)) {
                return (ILockSettings) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setBoolean";
                case 2:
                    return "setLong";
                case 3:
                    return "setString";
                case 4:
                    return "getBoolean";
                case 5:
                    return "getLong";
                case 6:
                    return "getString";
                case 7:
                    return "setLockCredential";
                case 8:
                    return "resetKeyStore";
                case 9:
                    return "checkCredential";
                case 10:
                    return "verifyCredential";
                case 11:
                    return "verifyTiedProfileChallenge";
                case 12:
                    return "verifyGatekeeperPasswordHandle";
                case 13:
                    return "removeGatekeeperPasswordHandle";
                case 14:
                    return "getCredentialType";
                case 15:
                    return "getPinLength";
                case 16:
                    return "refreshStoredPinLength";
                case 17:
                    return "getHashFactor";
                case 18:
                    return "setSeparateProfileChallengeEnabled";
                case 19:
                    return "getSeparateProfileChallengeEnabled";
                case 20:
                    return "registerStrongAuthTracker";
                case 21:
                    return "unregisterStrongAuthTracker";
                case 22:
                    return "requireStrongAuth";
                case 23:
                    return "reportSuccessfulBiometricUnlock";
                case 24:
                    return "scheduleNonStrongBiometricIdleTimeout";
                case 25:
                    return "systemReady";
                case 26:
                    return "userPresent";
                case 27:
                    return "getStrongAuthForUser";
                case 28:
                    return "hasPendingEscrowToken";
                case 29:
                    return "initRecoveryServiceWithSigFile";
                case 30:
                    return "getKeyChainSnapshot";
                case 31:
                    return "generateKey";
                case 32:
                    return "generateKeyWithMetadata";
                case 33:
                    return "importKey";
                case 34:
                    return "importKeyWithMetadata";
                case 35:
                    return "getKey";
                case 36:
                    return "removeKey";
                case 37:
                    return "setSnapshotCreatedPendingIntent";
                case 38:
                    return "setServerParams";
                case 39:
                    return "setRecoveryStatus";
                case 40:
                    return "getRecoveryStatus";
                case 41:
                    return "setRecoverySecretTypes";
                case 42:
                    return "getRecoverySecretTypes";
                case 43:
                    return "startRecoverySessionWithCertPath";
                case 44:
                    return "recoverKeyChainSnapshot";
                case 45:
                    return "closeSession";
                case 46:
                    return "startRemoteLockscreenValidation";
                case 47:
                    return "validateRemoteLockscreen";
                case 48:
                    return "hasSecureLockScreen";
                case 49:
                    return "tryUnlockWithCachedUnifiedChallenge";
                case 50:
                    return "removeCachedUnifiedChallenge";
                case 51:
                    return "registerWeakEscrowTokenRemovedListener";
                case 52:
                    return "unregisterWeakEscrowTokenRemovedListener";
                case 53:
                    return "addWeakEscrowToken";
                case 54:
                    return "removeWeakEscrowToken";
                case 55:
                    return "isWeakEscrowTokenActive";
                case 56:
                    return "isWeakEscrowTokenValid";
                case 57:
                    return "unlockUserKeyIfUnsecured";
                case 58:
                    return "registerRemoteLockCallback";
                case 59:
                    return "unregisterRemoteLockCallback";
                case 60:
                    return "setKnoxGuard";
                case 61:
                    return "setRemoteLock";
                case 62:
                    return "checkRemoteLockPassword";
                case 63:
                    return "requestRemoteLockInfo";
                case 64:
                    return "setLockFMMPassword";
                case 65:
                    return "haveFMMPassword";
                case 66:
                    return "checkFMMPassword";
                case 67:
                    return "getCarrierLock";
                case 68:
                    return "updateCarrierLock";
                case 69:
                    return "setCarrierLockEnabled";
                case 70:
                    return "setLockCarrierPassword";
                case 71:
                    return "haveCarrierPassword";
                case 72:
                    return "checkCarrierPassword";
                case 73:
                    return "isRemoteLock";
                case 74:
                    return "setLockModeChangedCallback";
                case 75:
                    return "sendLockTypeChangedInfo";
                case 76:
                    return "getFailureCount";
                case 77:
                    return "getExpireTimeForPrev";
                case 78:
                    return "expirePreviousData";
                case 79:
                    return "isSupportWeaver";
                case 80:
                    return "setSecurityDebugLevel";
                case 81:
                    return "setShellCommandCallback";
                case 82:
                    return "setAppLockPin";
                case 83:
                    return "setAppLockPassword";
                case 84:
                    return "setAppLockPattern";
                case 85:
                    return "setAppLockBackupPin";
                case 86:
                    return "setAppLockFingerprintPassword";
                case 87:
                    return "checkAppLockPin";
                case 88:
                    return "checkAppLockPassword";
                case 89:
                    return "checkAppLockPatternWithHash";
                case 90:
                    return "checkAppLockBackupPin";
                case 91:
                    return "checkAppLockFingerprintPassword";
                case 92:
                    return "haveAppLockPin";
                case 93:
                    return "haveAppLockPassword";
                case 94:
                    return "haveAppLockPattern";
                case 95:
                    return "haveAppLockBackupPin";
                case 96:
                    return "haveAppLockFingerprintPassword";
                case 97:
                    return "setLockCredentialWithIgnoreNotifyIfNeeded";
                case 98:
                    return "notifyPasswordChangedForEnterpriseUser";
                case 99:
                    return "verifyToken";
                case 100:
                    return "changeToken";
                case 101:
                    return "updateSdpMdfppForSystem";
                case 102:
                    return "checkCredentialForDualDarDo";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    boolean _arg1 = data.readBoolean();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    setBoolean(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    long _arg12 = data.readLong();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    setLong(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    String _arg13 = data.readString();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    setString(_arg03, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    boolean _arg14 = data.readBoolean();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result = getBoolean(_arg04, _arg14, _arg24);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    long _arg15 = data.readLong();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result2 = getLong(_arg05, _arg15, _arg25);
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    String _arg16 = data.readString();
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result3 = getString(_arg06, _arg16, _arg26);
                    reply.writeNoException();
                    reply.writeString(_result3);
                    return true;
                case 7:
                    LockscreenCredential _arg07 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    LockscreenCredential _arg17 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result4 = setLockCredential(_arg07, _arg17, _arg27);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    resetKeyStore(_arg08);
                    reply.writeNoException();
                    return true;
                case 9:
                    LockscreenCredential _arg09 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    int _arg18 = data.readInt();
                    ICheckCredentialProgressCallback _arg28 = ICheckCredentialProgressCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    VerifyCredentialResponse _result5 = checkCredential(_arg09, _arg18, _arg28);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 10:
                    LockscreenCredential _arg010 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    int _arg19 = data.readInt();
                    int _arg29 = data.readInt();
                    data.enforceNoDataAvail();
                    VerifyCredentialResponse _result6 = verifyCredential(_arg010, _arg19, _arg29);
                    reply.writeNoException();
                    reply.writeTypedObject(_result6, 1);
                    return true;
                case 11:
                    LockscreenCredential _arg011 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    int _arg110 = data.readInt();
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    VerifyCredentialResponse _result7 = verifyTiedProfileChallenge(_arg011, _arg110, _arg210);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 12:
                    long _arg012 = data.readLong();
                    long _arg111 = data.readLong();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    VerifyCredentialResponse _result8 = verifyGatekeeperPasswordHandle(_arg012, _arg111, _arg211);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 13:
                    long _arg013 = data.readLong();
                    data.enforceNoDataAvail();
                    removeGatekeeperPasswordHandle(_arg013);
                    reply.writeNoException();
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result9 = getCredentialType(_arg014);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result10 = getPinLength(_arg015);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 16:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = refreshStoredPinLength(_arg016);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 17:
                    LockscreenCredential _arg017 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result12 = getHashFactor(_arg017, _arg112);
                    reply.writeNoException();
                    reply.writeByteArray(_result12);
                    return true;
                case 18:
                    int _arg018 = data.readInt();
                    boolean _arg113 = data.readBoolean();
                    LockscreenCredential _arg212 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    data.enforceNoDataAvail();
                    setSeparateProfileChallengeEnabled(_arg018, _arg113, _arg212);
                    reply.writeNoException();
                    return true;
                case 19:
                    int _arg019 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result13 = getSeparateProfileChallengeEnabled(_arg019);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 20:
                    IStrongAuthTracker _arg020 = IStrongAuthTracker.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerStrongAuthTracker(_arg020);
                    reply.writeNoException();
                    return true;
                case 21:
                    IStrongAuthTracker _arg021 = IStrongAuthTracker.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterStrongAuthTracker(_arg021);
                    reply.writeNoException();
                    return true;
                case 22:
                    int _arg022 = data.readInt();
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    requireStrongAuth(_arg022, _arg114);
                    reply.writeNoException();
                    return true;
                case 23:
                    boolean _arg023 = data.readBoolean();
                    int _arg115 = data.readInt();
                    data.enforceNoDataAvail();
                    reportSuccessfulBiometricUnlock(_arg023, _arg115);
                    reply.writeNoException();
                    return true;
                case 24:
                    int _arg024 = data.readInt();
                    data.enforceNoDataAvail();
                    scheduleNonStrongBiometricIdleTimeout(_arg024);
                    reply.writeNoException();
                    return true;
                case 25:
                    systemReady();
                    reply.writeNoException();
                    return true;
                case 26:
                    int _arg025 = data.readInt();
                    data.enforceNoDataAvail();
                    userPresent(_arg025);
                    reply.writeNoException();
                    return true;
                case 27:
                    int _arg026 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result14 = getStrongAuthForUser(_arg026);
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 28:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 = hasPendingEscrowToken(_arg027);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 29:
                    String _arg028 = data.readString();
                    byte[] _arg116 = data.createByteArray();
                    byte[] _arg213 = data.createByteArray();
                    data.enforceNoDataAvail();
                    initRecoveryServiceWithSigFile(_arg028, _arg116, _arg213);
                    reply.writeNoException();
                    return true;
                case 30:
                    KeyChainSnapshot _result16 = getKeyChainSnapshot();
                    reply.writeNoException();
                    reply.writeTypedObject(_result16, 1);
                    return true;
                case 31:
                    String _arg029 = data.readString();
                    data.enforceNoDataAvail();
                    String _result17 = generateKey(_arg029);
                    reply.writeNoException();
                    reply.writeString(_result17);
                    return true;
                case 32:
                    String _arg030 = data.readString();
                    byte[] _arg117 = data.createByteArray();
                    data.enforceNoDataAvail();
                    String _result18 = generateKeyWithMetadata(_arg030, _arg117);
                    reply.writeNoException();
                    reply.writeString(_result18);
                    return true;
                case 33:
                    String _arg031 = data.readString();
                    byte[] _arg118 = data.createByteArray();
                    data.enforceNoDataAvail();
                    String _result19 = importKey(_arg031, _arg118);
                    reply.writeNoException();
                    reply.writeString(_result19);
                    return true;
                case 34:
                    String _arg032 = data.readString();
                    byte[] _arg119 = data.createByteArray();
                    byte[] _arg214 = data.createByteArray();
                    data.enforceNoDataAvail();
                    String _result20 = importKeyWithMetadata(_arg032, _arg119, _arg214);
                    reply.writeNoException();
                    reply.writeString(_result20);
                    return true;
                case 35:
                    String _arg033 = data.readString();
                    data.enforceNoDataAvail();
                    String _result21 = getKey(_arg033);
                    reply.writeNoException();
                    reply.writeString(_result21);
                    return true;
                case 36:
                    String _arg034 = data.readString();
                    data.enforceNoDataAvail();
                    removeKey(_arg034);
                    reply.writeNoException();
                    return true;
                case 37:
                    PendingIntent _arg035 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    setSnapshotCreatedPendingIntent(_arg035);
                    reply.writeNoException();
                    return true;
                case 38:
                    byte[] _arg036 = data.createByteArray();
                    data.enforceNoDataAvail();
                    setServerParams(_arg036);
                    reply.writeNoException();
                    return true;
                case 39:
                    String _arg037 = data.readString();
                    int _arg120 = data.readInt();
                    data.enforceNoDataAvail();
                    setRecoveryStatus(_arg037, _arg120);
                    reply.writeNoException();
                    return true;
                case 40:
                    Map _result22 = getRecoveryStatus();
                    reply.writeNoException();
                    reply.writeMap(_result22);
                    return true;
                case 41:
                    int[] _arg038 = data.createIntArray();
                    data.enforceNoDataAvail();
                    setRecoverySecretTypes(_arg038);
                    reply.writeNoException();
                    return true;
                case 42:
                    int[] _result23 = getRecoverySecretTypes();
                    reply.writeNoException();
                    reply.writeIntArray(_result23);
                    return true;
                case 43:
                    String _arg039 = data.readString();
                    String _arg121 = data.readString();
                    RecoveryCertPath _arg215 = (RecoveryCertPath) data.readTypedObject(RecoveryCertPath.CREATOR);
                    byte[] _arg3 = data.createByteArray();
                    byte[] _arg4 = data.createByteArray();
                    List<KeyChainProtectionParams> _arg5 = data.createTypedArrayList(KeyChainProtectionParams.CREATOR);
                    data.enforceNoDataAvail();
                    byte[] _result24 = startRecoverySessionWithCertPath(_arg039, _arg121, _arg215, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeByteArray(_result24);
                    return true;
                case 44:
                    String _arg040 = data.readString();
                    byte[] _arg122 = data.createByteArray();
                    List<WrappedApplicationKey> _arg216 = data.createTypedArrayList(WrappedApplicationKey.CREATOR);
                    data.enforceNoDataAvail();
                    Map _result25 = recoverKeyChainSnapshot(_arg040, _arg122, _arg216);
                    reply.writeNoException();
                    reply.writeMap(_result25);
                    return true;
                case 45:
                    String _arg041 = data.readString();
                    data.enforceNoDataAvail();
                    closeSession(_arg041);
                    reply.writeNoException();
                    return true;
                case 46:
                    RemoteLockscreenValidationSession _result26 = startRemoteLockscreenValidation();
                    reply.writeNoException();
                    reply.writeTypedObject(_result26, 1);
                    return true;
                case 47:
                    byte[] _arg042 = data.createByteArray();
                    data.enforceNoDataAvail();
                    RemoteLockscreenValidationResult _result27 = validateRemoteLockscreen(_arg042);
                    reply.writeNoException();
                    reply.writeTypedObject(_result27, 1);
                    return true;
                case 48:
                    boolean _result28 = hasSecureLockScreen();
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 49:
                    int _arg043 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result29 = tryUnlockWithCachedUnifiedChallenge(_arg043);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 50:
                    int _arg044 = data.readInt();
                    data.enforceNoDataAvail();
                    removeCachedUnifiedChallenge(_arg044);
                    reply.writeNoException();
                    return true;
                case 51:
                    IWeakEscrowTokenRemovedListener _arg045 = IWeakEscrowTokenRemovedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result30 = registerWeakEscrowTokenRemovedListener(_arg045);
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 52:
                    IWeakEscrowTokenRemovedListener _arg046 = IWeakEscrowTokenRemovedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result31 = unregisterWeakEscrowTokenRemovedListener(_arg046);
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 53:
                    byte[] _arg047 = data.createByteArray();
                    int _arg123 = data.readInt();
                    IWeakEscrowTokenActivatedListener _arg217 = IWeakEscrowTokenActivatedListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    long _result32 = addWeakEscrowToken(_arg047, _arg123, _arg217);
                    reply.writeNoException();
                    reply.writeLong(_result32);
                    return true;
                case 54:
                    long _arg048 = data.readLong();
                    int _arg124 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result33 = removeWeakEscrowToken(_arg048, _arg124);
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 55:
                    long _arg049 = data.readLong();
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result34 = isWeakEscrowTokenActive(_arg049, _arg125);
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 56:
                    long _arg050 = data.readLong();
                    byte[] _arg126 = data.createByteArray();
                    int _arg218 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result35 = isWeakEscrowTokenValid(_arg050, _arg126, _arg218);
                    reply.writeNoException();
                    reply.writeBoolean(_result35);
                    return true;
                case 57:
                    int _arg051 = data.readInt();
                    data.enforceNoDataAvail();
                    unlockUserKeyIfUnsecured(_arg051);
                    reply.writeNoException();
                    return true;
                case 58:
                    int _arg052 = data.readInt();
                    IRemoteLockMonitorCallback _arg127 = IRemoteLockMonitorCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerRemoteLockCallback(_arg052, _arg127);
                    reply.writeNoException();
                    return true;
                case 59:
                    int _arg053 = data.readInt();
                    IRemoteLockMonitorCallback _arg128 = IRemoteLockMonitorCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterRemoteLockCallback(_arg053, _arg128);
                    reply.writeNoException();
                    return true;
                case 60:
                    int _arg054 = data.readInt();
                    RemoteLockInfo _arg129 = (RemoteLockInfo) data.readTypedObject(RemoteLockInfo.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result36 = setKnoxGuard(_arg054, _arg129);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 61:
                    int _arg055 = data.readInt();
                    RemoteLockInfo _arg130 = (RemoteLockInfo) data.readTypedObject(RemoteLockInfo.CREATOR);
                    data.enforceNoDataAvail();
                    setRemoteLock(_arg055, _arg130);
                    reply.writeNoException();
                    return true;
                case 62:
                    int _arg056 = data.readInt();
                    byte[] _arg131 = data.createByteArray();
                    int _arg219 = data.readInt();
                    IRemoteCallback _arg32 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    checkRemoteLockPassword(_arg056, _arg131, _arg219, _arg32);
                    reply.writeNoException();
                    return true;
                case 63:
                    int _arg057 = data.readInt();
                    data.enforceNoDataAvail();
                    requestRemoteLockInfo(_arg057);
                    reply.writeNoException();
                    return true;
                case 64:
                    byte[] _arg058 = data.createByteArray();
                    int _arg132 = data.readInt();
                    data.enforceNoDataAvail();
                    setLockFMMPassword(_arg058, _arg132);
                    reply.writeNoException();
                    return true;
                case 65:
                    int _arg059 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result37 = haveFMMPassword(_arg059);
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    return true;
                case 66:
                    byte[] _arg060 = data.createByteArray();
                    int _arg133 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result38 = checkFMMPassword(_arg060, _arg133);
                    reply.writeNoException();
                    reply.writeBoolean(_result38);
                    return true;
                case 67:
                    int _arg061 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result39 = getCarrierLock(_arg061);
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    return true;
                case 68:
                    int _arg062 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result40 = updateCarrierLock(_arg062);
                    reply.writeNoException();
                    reply.writeBoolean(_result40);
                    return true;
                case 69:
                    int _arg063 = data.readInt();
                    data.enforceNoDataAvail();
                    setCarrierLockEnabled(_arg063);
                    reply.writeNoException();
                    return true;
                case 70:
                    byte[] _arg064 = data.createByteArray();
                    int _arg134 = data.readInt();
                    data.enforceNoDataAvail();
                    setLockCarrierPassword(_arg064, _arg134);
                    reply.writeNoException();
                    return true;
                case 71:
                    int _arg065 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result41 = haveCarrierPassword(_arg065);
                    reply.writeNoException();
                    reply.writeBoolean(_result41);
                    return true;
                case 72:
                    byte[] _arg066 = data.createByteArray();
                    int _arg135 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result42 = checkCarrierPassword(_arg066, _arg135);
                    reply.writeNoException();
                    reply.writeBoolean(_result42);
                    return true;
                case 73:
                    int _arg067 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result43 = isRemoteLock(_arg067);
                    reply.writeNoException();
                    reply.writeBoolean(_result43);
                    return true;
                case 74:
                    IRemoteCallback _arg068 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setLockModeChangedCallback(_arg068);
                    return true;
                case 75:
                    int _arg069 = data.readInt();
                    data.enforceNoDataAvail();
                    sendLockTypeChangedInfo(_arg069);
                    return true;
                case 76:
                    int _arg070 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result44 = getFailureCount(_arg070);
                    reply.writeNoException();
                    reply.writeInt(_result44);
                    return true;
                case 77:
                    long _result45 = getExpireTimeForPrev();
                    reply.writeNoException();
                    reply.writeLong(_result45);
                    return true;
                case 78:
                    expirePreviousData();
                    return true;
                case 79:
                    boolean _result46 = isSupportWeaver();
                    reply.writeNoException();
                    reply.writeBoolean(_result46);
                    return true;
                case 80:
                    int _arg071 = data.readInt();
                    data.enforceNoDataAvail();
                    setSecurityDebugLevel(_arg071);
                    return true;
                case 81:
                    IRemoteCallback _arg072 = IRemoteCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setShellCommandCallback(_arg072);
                    return true;
                case 82:
                    String _arg073 = data.readString();
                    int _arg136 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppLockPin(_arg073, _arg136);
                    reply.writeNoException();
                    return true;
                case 83:
                    String _arg074 = data.readString();
                    int _arg137 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppLockPassword(_arg074, _arg137);
                    reply.writeNoException();
                    return true;
                case 84:
                    String _arg075 = data.readString();
                    int _arg138 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppLockPattern(_arg075, _arg138);
                    reply.writeNoException();
                    return true;
                case 85:
                    String _arg076 = data.readString();
                    int _arg139 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppLockBackupPin(_arg076, _arg139);
                    reply.writeNoException();
                    return true;
                case 86:
                    String _arg077 = data.readString();
                    int _arg140 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppLockFingerprintPassword(_arg077, _arg140);
                    reply.writeNoException();
                    return true;
                case 87:
                    String _arg078 = data.readString();
                    int _arg141 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result47 = checkAppLockPin(_arg078, _arg141);
                    reply.writeNoException();
                    reply.writeBoolean(_result47);
                    return true;
                case 88:
                    String _arg079 = data.readString();
                    int _arg142 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result48 = checkAppLockPassword(_arg079, _arg142);
                    reply.writeNoException();
                    reply.writeBoolean(_result48);
                    return true;
                case 89:
                    String _arg080 = data.readString();
                    int _arg143 = data.readInt();
                    byte[] _arg220 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result49 = checkAppLockPatternWithHash(_arg080, _arg143, _arg220);
                    reply.writeNoException();
                    reply.writeBoolean(_result49);
                    return true;
                case 90:
                    String _arg081 = data.readString();
                    int _arg144 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result50 = checkAppLockBackupPin(_arg081, _arg144);
                    reply.writeNoException();
                    reply.writeBoolean(_result50);
                    return true;
                case 91:
                    String _arg082 = data.readString();
                    int _arg145 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result51 = checkAppLockFingerprintPassword(_arg082, _arg145);
                    reply.writeNoException();
                    reply.writeBoolean(_result51);
                    return true;
                case 92:
                    int _arg083 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result52 = haveAppLockPin(_arg083);
                    reply.writeNoException();
                    reply.writeBoolean(_result52);
                    return true;
                case 93:
                    int _arg084 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result53 = haveAppLockPassword(_arg084);
                    reply.writeNoException();
                    reply.writeBoolean(_result53);
                    return true;
                case 94:
                    int _arg085 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result54 = haveAppLockPattern(_arg085);
                    reply.writeNoException();
                    reply.writeBoolean(_result54);
                    return true;
                case 95:
                    int _arg086 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result55 = haveAppLockBackupPin(_arg086);
                    reply.writeNoException();
                    reply.writeBoolean(_result55);
                    return true;
                case 96:
                    int _arg087 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result56 = haveAppLockFingerprintPassword(_arg087);
                    reply.writeNoException();
                    reply.writeBoolean(_result56);
                    return true;
                case 97:
                    LockscreenCredential _arg088 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    LockscreenCredential _arg146 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    int _arg221 = data.readInt();
                    boolean _arg33 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result57 = setLockCredentialWithIgnoreNotifyIfNeeded(_arg088, _arg146, _arg221, _arg33);
                    reply.writeNoException();
                    reply.writeBoolean(_result57);
                    return true;
                case 98:
                    LockscreenCredential _arg089 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    int _arg147 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyPasswordChangedForEnterpriseUser(_arg089, _arg147);
                    reply.writeNoException();
                    return true;
                case 99:
                    byte[] _arg090 = data.createByteArray();
                    long _arg148 = data.readLong();
                    int _arg222 = data.readInt();
                    data.enforceNoDataAvail();
                    VerifyCredentialResponse _result58 = verifyToken(_arg090, _arg148, _arg222);
                    reply.writeNoException();
                    reply.writeTypedObject(_result58, 1);
                    return true;
                case 100:
                    byte[] _arg091 = data.createByteArray();
                    long _arg149 = data.readLong();
                    byte[] _arg223 = data.createByteArray();
                    long _arg34 = data.readLong();
                    int _arg42 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result59 = changeToken(_arg091, _arg149, _arg223, _arg34, _arg42);
                    reply.writeNoException();
                    reply.writeBoolean(_result59);
                    return true;
                case 101:
                    int _arg092 = data.readInt();
                    long _arg150 = data.readLong();
                    data.enforceNoDataAvail();
                    updateSdpMdfppForSystem(_arg092, _arg150);
                    reply.writeNoException();
                    return true;
                case 102:
                    LockscreenCredential _arg093 = (LockscreenCredential) data.readTypedObject(LockscreenCredential.CREATOR);
                    int _arg151 = data.readInt();
                    int _arg224 = data.readInt();
                    IDualDarAuthProgressCallback _arg35 = IDualDarAuthProgressCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    VerifyCredentialResponse _result60 = checkCredentialForDualDarDo(_arg093, _arg151, _arg224, _arg35);
                    reply.writeNoException();
                    reply.writeTypedObject(_result60, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILockSettings {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setBoolean(String key, boolean value, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeBoolean(value);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLong(String key, long value, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeLong(value);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setString(String key, String value, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(value);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getBoolean(String key, boolean defaultValue, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeBoolean(defaultValue);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long getLong(String key, long defaultValue, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeLong(defaultValue);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String getString(String key, String defaultValue, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(defaultValue);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setLockCredential(LockscreenCredential credential, LockscreenCredential savedCredential, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(credential, 0);
                    _data.writeTypedObject(savedCredential, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void resetKeyStore(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse checkCredential(LockscreenCredential credential, int userId, ICheckCredentialProgressCallback progressCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(credential, 0);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(progressCallback);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    VerifyCredentialResponse _result = (VerifyCredentialResponse) _reply.readTypedObject(VerifyCredentialResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyCredential(LockscreenCredential credential, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(credential, 0);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    VerifyCredentialResponse _result = (VerifyCredentialResponse) _reply.readTypedObject(VerifyCredentialResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyTiedProfileChallenge(LockscreenCredential credential, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(credential, 0);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    VerifyCredentialResponse _result = (VerifyCredentialResponse) _reply.readTypedObject(VerifyCredentialResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyGatekeeperPasswordHandle(long gatekeeperPasswordHandle, long challenge, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(gatekeeperPasswordHandle);
                    _data.writeLong(challenge);
                    _data.writeInt(userId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    VerifyCredentialResponse _result = (VerifyCredentialResponse) _reply.readTypedObject(VerifyCredentialResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeGatekeeperPasswordHandle(long gatekeeperPasswordHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(gatekeeperPasswordHandle);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getCredentialType(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getPinLength(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean refreshStoredPinLength(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public byte[] getHashFactor(LockscreenCredential currentCredential, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(currentCredential, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSeparateProfileChallengeEnabled(int userId, boolean enabled, LockscreenCredential managedUserPassword) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeBoolean(enabled);
                    _data.writeTypedObject(managedUserPassword, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getSeparateProfileChallengeEnabled(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void registerStrongAuthTracker(IStrongAuthTracker tracker) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(tracker);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unregisterStrongAuthTracker(IStrongAuthTracker tracker) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(tracker);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void requireStrongAuth(int strongAuthReason, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(strongAuthReason);
                    _data.writeInt(userId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void reportSuccessfulBiometricUnlock(boolean isStrongBiometric, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isStrongBiometric);
                    _data.writeInt(userId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void scheduleNonStrongBiometricIdleTimeout(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void systemReady() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void userPresent(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getStrongAuthForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean hasPendingEscrowToken(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void initRecoveryServiceWithSigFile(String rootCertificateAlias, byte[] recoveryServiceCertFile, byte[] recoveryServiceSigFile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rootCertificateAlias);
                    _data.writeByteArray(recoveryServiceCertFile);
                    _data.writeByteArray(recoveryServiceSigFile);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    KeyChainSnapshot _result = (KeyChainSnapshot) _reply.readTypedObject(KeyChainSnapshot.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String generateKey(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String generateKeyWithMetadata(String alias, byte[] metadata) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(metadata);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String importKey(String alias, byte[] keyBytes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(keyBytes);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String importKeyWithMetadata(String alias, byte[] keyBytes, byte[] metadata) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(keyBytes);
                    _data.writeByteArray(metadata);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String getKey(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeKey(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSnapshotCreatedPendingIntent(PendingIntent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setServerParams(byte[] serverParams) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(serverParams);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRecoveryStatus(String alias, int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(status);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public Map getRecoveryStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRecoverySecretTypes(int[] secretTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(secretTypes);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int[] getRecoverySecretTypes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public byte[] startRecoverySessionWithCertPath(String sessionId, String rootCertificateAlias, RecoveryCertPath verifierCertPath, byte[] vaultParams, byte[] vaultChallenge, List<KeyChainProtectionParams> secrets) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sessionId);
                    _data.writeString(rootCertificateAlias);
                    _data.writeTypedObject(verifierCertPath, 0);
                    _data.writeByteArray(vaultParams);
                    _data.writeByteArray(vaultChallenge);
                    _data.writeTypedList(secrets, 0);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public Map recoverKeyChainSnapshot(String sessionId, byte[] recoveryKeyBlob, List<WrappedApplicationKey> applicationKeys) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sessionId);
                    _data.writeByteArray(recoveryKeyBlob);
                    _data.writeTypedList(applicationKeys, 0);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void closeSession(String sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(sessionId);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public RemoteLockscreenValidationSession startRemoteLockscreenValidation() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    RemoteLockscreenValidationSession _result = (RemoteLockscreenValidationSession) _reply.readTypedObject(RemoteLockscreenValidationSession.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] encryptedCredential) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(encryptedCredential);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    RemoteLockscreenValidationResult _result = (RemoteLockscreenValidationResult) _reply.readTypedObject(RemoteLockscreenValidationResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean hasSecureLockScreen() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean tryUnlockWithCachedUnifiedChallenge(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeCachedUnifiedChallenge(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long addWeakEscrowToken(byte[] token, int userId, IWeakEscrowTokenActivatedListener callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(token);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean removeWeakEscrowToken(long handle, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(handle);
                    _data.writeInt(userId);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isWeakEscrowTokenActive(long handle, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(handle);
                    _data.writeInt(userId);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isWeakEscrowTokenValid(long handle, byte[] token, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(handle);
                    _data.writeByteArray(token);
                    _data.writeInt(userId);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unlockUserKeyIfUnsecured(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void registerRemoteLockCallback(int type, IRemoteLockMonitorCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unregisterRemoteLockCallback(int type, IRemoteLockMonitorCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setKnoxGuard(int userId, RemoteLockInfo data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRemoteLock(int userId, RemoteLockInfo data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeTypedObject(data, 0);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void checkRemoteLockPassword(int type, byte[] password, int userId, IRemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeByteArray(password);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void requestRemoteLockInfo(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLockFMMPassword(byte[] password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveFMMPassword(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkFMMPassword(byte[] password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getCarrierLock(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean updateCarrierLock(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setCarrierLockEnabled(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLockCarrierPassword(byte[] password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveCarrierPassword(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkCarrierPassword(byte[] password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isRemoteLock(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLockModeChangedCallback(IRemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(74, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void sendLockTypeChangedInfo(int secureState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(secureState);
                    this.mRemote.transact(75, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getFailureCount(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long getExpireTimeForPrev() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void expirePreviousData() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isSupportWeaver() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSecurityDebugLevel(int level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(level);
                    this.mRemote.transact(80, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setShellCommandCallback(IRemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(81, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockPin(String password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockPassword(String password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockPattern(String password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockBackupPin(String password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockFingerprintPassword(String password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockPin(String password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockPassword(String password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockPatternWithHash(String password, int userId, byte[] hash) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    _data.writeByteArray(hash);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockBackupPin(String password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockFingerprintPassword(String password, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    _data.writeInt(userId);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockPin(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockPassword(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockPattern(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockBackupPin(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockFingerprintPassword(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setLockCredentialWithIgnoreNotifyIfNeeded(LockscreenCredential credential, LockscreenCredential savedCredential, int userId, boolean ignoreNotifyPasswordChanged) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(credential, 0);
                    _data.writeTypedObject(savedCredential, 0);
                    _data.writeInt(userId);
                    _data.writeBoolean(ignoreNotifyPasswordChanged);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void notifyPasswordChangedForEnterpriseUser(LockscreenCredential newCredential, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(newCredential, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyToken(byte[] token, long tokenHandle, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(token);
                    _data.writeLong(tokenHandle);
                    _data.writeInt(userId);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                    VerifyCredentialResponse _result = (VerifyCredentialResponse) _reply.readTypedObject(VerifyCredentialResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean changeToken(byte[] newToken, long newHandle, byte[] oldToken, long oldHandle, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(newToken);
                    _data.writeLong(newHandle);
                    _data.writeByteArray(oldToken);
                    _data.writeLong(oldHandle);
                    _data.writeInt(userId);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void updateSdpMdfppForSystem(int userId, long value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeLong(value);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse checkCredentialForDualDarDo(LockscreenCredential credential, int userId, int option, IDualDarAuthProgressCallback progressCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(credential, 0);
                    _data.writeInt(userId);
                    _data.writeInt(option);
                    _data.writeStrongInterface(progressCallback);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    VerifyCredentialResponse _result = (VerifyCredentialResponse) _reply.readTypedObject(VerifyCredentialResponse.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 101;
        }
    }
}
