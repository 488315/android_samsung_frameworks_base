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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public interface ILockSettings extends IInterface {

    public static class Default implements ILockSettings {
        @Override // com.android.internal.widget.ILockSettings
        public long addWeakEscrowToken(byte[] bArr, int i, IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean changeToken(byte[] bArr, long j, byte[] bArr2, long j2, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockBackupPin(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockFingerprintPassword(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockPassword(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockPatternWithHash(String str, int i, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkAppLockPin(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkCarrierPassword(byte[] bArr, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse checkCredential(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse checkCredentialForDualDarDo(LockscreenCredential lockscreenCredential, int i, int i2, IDualDarAuthProgressCallback iDualDarAuthProgressCallback) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean checkFMMPassword(byte[] bArr, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void checkRemoteLockPassword(int i, byte[] bArr, int i2, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void closeSession(String str) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void expirePreviousData() throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public String generateKey(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String generateKeyWithMetadata(String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean getBoolean(String str, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean getCarrierLock(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getCredentialType(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public long getExpireTimeForPrev() throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getFailureCount(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public byte[] getHashFactor(LockscreenCredential lockscreenCredential, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String getKey(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public long getLong(String str, long j, int i) throws RemoteException {
            return 0L;
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getPinLength(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public int[] getRecoverySecretTypes() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public Map getRecoveryStatus() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean getSeparateProfileChallengeEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String getString(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public int getStrongAuthForUser(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean hasPendingEscrowToken(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean hasSecureLockScreen() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockBackupPin(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockFingerprintPassword(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockPassword(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockPattern(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveAppLockPin(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveCarrierPassword(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean haveFMMPassword(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String importKey(String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public String importKeyWithMetadata(String str, byte[] bArr, byte[] bArr2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void initRecoveryServiceWithSigFile(String str, byte[] bArr, byte[] bArr2) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isRemoteLock(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isSupportWeaver() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isWeakEscrowTokenActive(long j, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void notifyPasswordChangedForEnterpriseUser(LockscreenCredential lockscreenCredential, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public Map recoverKeyChainSnapshot(String str, byte[] bArr, List<WrappedApplicationKey> list) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean refreshStoredPinLength(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void registerRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void removeCachedUnifiedChallenge(int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void removeGatekeeperPasswordHandle(long j) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void removeKey(String str) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean removeWeakEscrowToken(long j, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void reportSuccessfulBiometricUnlock(boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void requestRemoteLockInfo(int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void requireStrongAuth(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void resetKeyStore(int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void scheduleNonStrongBiometricIdleTimeout(int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void sendLockTypeChangedInfo(int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockBackupPin(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockFingerprintPassword(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockPassword(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockPattern(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setAppLockPin(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setBoolean(String str, boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setCarrierLockEnabled(int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean setKnoxGuard(int i, RemoteLockInfo remoteLockInfo) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setLockCarrierPassword(byte[] bArr, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean setLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean setLockCredentialWithIgnoreNotifyIfNeeded(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setLockFMMPassword(byte[] bArr, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setLockModeChangedCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setLong(String str, long j, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setRecoverySecretTypes(int[] iArr) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setRecoveryStatus(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setRemoteLock(int i, RemoteLockInfo remoteLockInfo) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setSecurityDebugLevel(int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setSeparateProfileChallengeEnabled(int i, boolean z, LockscreenCredential lockscreenCredential) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setServerParams(byte[] bArr) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setShellCommandCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void setString(String str, String str2, int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public byte[] startRecoverySessionWithCertPath(String str, String str2, RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, List<KeyChainProtectionParams> list) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public RemoteLockscreenValidationSession startRemoteLockscreenValidation() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void systemReady() throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean tryUnlockWithCachedUnifiedChallenge(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void unlockUserKeyIfUnsecured(int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void unregisterRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean updateCarrierLock(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.widget.ILockSettings
        public void updateSdpMdfppForSystem(int i, long j) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public void userPresent(int i) throws RemoteException {
        }

        @Override // com.android.internal.widget.ILockSettings
        public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse verifyCredential(LockscreenCredential lockscreenCredential, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse verifyTiedProfileChallenge(LockscreenCredential lockscreenCredential, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public VerifyCredentialResponse verifyToken(byte[] bArr, long j, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.widget.ILockSettings
        public boolean writeRepairModeCredential(int i) throws RemoteException {
            return false;
        }
    }

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

    boolean writeRepairModeCredential(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ILockSettings {
        public static final String DESCRIPTOR = "com.android.internal.widget.ILockSettings";
        static final int TRANSACTION_addWeakEscrowToken = 53;
        static final int TRANSACTION_changeToken = 101;
        static final int TRANSACTION_checkAppLockBackupPin = 91;
        static final int TRANSACTION_checkAppLockFingerprintPassword = 92;
        static final int TRANSACTION_checkAppLockPassword = 89;
        static final int TRANSACTION_checkAppLockPatternWithHash = 90;
        static final int TRANSACTION_checkAppLockPin = 88;
        static final int TRANSACTION_checkCarrierPassword = 73;
        static final int TRANSACTION_checkCredential = 9;
        static final int TRANSACTION_checkCredentialForDualDarDo = 103;
        static final int TRANSACTION_checkFMMPassword = 67;
        static final int TRANSACTION_checkRemoteLockPassword = 63;
        static final int TRANSACTION_closeSession = 45;
        static final int TRANSACTION_expirePreviousData = 79;
        static final int TRANSACTION_generateKey = 31;
        static final int TRANSACTION_generateKeyWithMetadata = 32;
        static final int TRANSACTION_getBoolean = 4;
        static final int TRANSACTION_getCarrierLock = 68;
        static final int TRANSACTION_getCredentialType = 14;
        static final int TRANSACTION_getExpireTimeForPrev = 78;
        static final int TRANSACTION_getFailureCount = 77;
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
        static final int TRANSACTION_haveAppLockBackupPin = 96;
        static final int TRANSACTION_haveAppLockFingerprintPassword = 97;
        static final int TRANSACTION_haveAppLockPassword = 94;
        static final int TRANSACTION_haveAppLockPattern = 95;
        static final int TRANSACTION_haveAppLockPin = 93;
        static final int TRANSACTION_haveCarrierPassword = 72;
        static final int TRANSACTION_haveFMMPassword = 66;
        static final int TRANSACTION_importKey = 33;
        static final int TRANSACTION_importKeyWithMetadata = 34;
        static final int TRANSACTION_initRecoveryServiceWithSigFile = 29;
        static final int TRANSACTION_isRemoteLock = 74;
        static final int TRANSACTION_isSupportWeaver = 80;
        static final int TRANSACTION_isWeakEscrowTokenActive = 55;
        static final int TRANSACTION_isWeakEscrowTokenValid = 56;
        static final int TRANSACTION_notifyPasswordChangedForEnterpriseUser = 99;
        static final int TRANSACTION_recoverKeyChainSnapshot = 44;
        static final int TRANSACTION_refreshStoredPinLength = 16;
        static final int TRANSACTION_registerRemoteLockCallback = 59;
        static final int TRANSACTION_registerStrongAuthTracker = 20;
        static final int TRANSACTION_registerWeakEscrowTokenRemovedListener = 51;
        static final int TRANSACTION_removeCachedUnifiedChallenge = 50;
        static final int TRANSACTION_removeGatekeeperPasswordHandle = 13;
        static final int TRANSACTION_removeKey = 36;
        static final int TRANSACTION_removeWeakEscrowToken = 54;
        static final int TRANSACTION_reportSuccessfulBiometricUnlock = 23;
        static final int TRANSACTION_requestRemoteLockInfo = 64;
        static final int TRANSACTION_requireStrongAuth = 22;
        static final int TRANSACTION_resetKeyStore = 8;
        static final int TRANSACTION_scheduleNonStrongBiometricIdleTimeout = 24;
        static final int TRANSACTION_sendLockTypeChangedInfo = 76;
        static final int TRANSACTION_setAppLockBackupPin = 86;
        static final int TRANSACTION_setAppLockFingerprintPassword = 87;
        static final int TRANSACTION_setAppLockPassword = 84;
        static final int TRANSACTION_setAppLockPattern = 85;
        static final int TRANSACTION_setAppLockPin = 83;
        static final int TRANSACTION_setBoolean = 1;
        static final int TRANSACTION_setCarrierLockEnabled = 70;
        static final int TRANSACTION_setKnoxGuard = 61;
        static final int TRANSACTION_setLockCarrierPassword = 71;
        static final int TRANSACTION_setLockCredential = 7;
        static final int TRANSACTION_setLockCredentialWithIgnoreNotifyIfNeeded = 98;
        static final int TRANSACTION_setLockFMMPassword = 65;
        static final int TRANSACTION_setLockModeChangedCallback = 75;
        static final int TRANSACTION_setLong = 2;
        static final int TRANSACTION_setRecoverySecretTypes = 41;
        static final int TRANSACTION_setRecoveryStatus = 39;
        static final int TRANSACTION_setRemoteLock = 62;
        static final int TRANSACTION_setSecurityDebugLevel = 81;
        static final int TRANSACTION_setSeparateProfileChallengeEnabled = 18;
        static final int TRANSACTION_setServerParams = 38;
        static final int TRANSACTION_setShellCommandCallback = 82;
        static final int TRANSACTION_setSnapshotCreatedPendingIntent = 37;
        static final int TRANSACTION_setString = 3;
        static final int TRANSACTION_startRecoverySessionWithCertPath = 43;
        static final int TRANSACTION_startRemoteLockscreenValidation = 46;
        static final int TRANSACTION_systemReady = 25;
        static final int TRANSACTION_tryUnlockWithCachedUnifiedChallenge = 49;
        static final int TRANSACTION_unlockUserKeyIfUnsecured = 57;
        static final int TRANSACTION_unregisterRemoteLockCallback = 60;
        static final int TRANSACTION_unregisterStrongAuthTracker = 21;
        static final int TRANSACTION_unregisterWeakEscrowTokenRemovedListener = 52;
        static final int TRANSACTION_updateCarrierLock = 69;
        static final int TRANSACTION_updateSdpMdfppForSystem = 102;
        static final int TRANSACTION_userPresent = 26;
        static final int TRANSACTION_validateRemoteLockscreen = 47;
        static final int TRANSACTION_verifyCredential = 10;
        static final int TRANSACTION_verifyGatekeeperPasswordHandle = 12;
        static final int TRANSACTION_verifyTiedProfileChallenge = 11;
        static final int TRANSACTION_verifyToken = 100;
        static final int TRANSACTION_writeRepairModeCredential = 58;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 102;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ILockSettings asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ILockSettings)) {
                return (ILockSettings) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "writeRepairModeCredential";
                case 59:
                    return "registerRemoteLockCallback";
                case 60:
                    return "unregisterRemoteLockCallback";
                case 61:
                    return "setKnoxGuard";
                case 62:
                    return "setRemoteLock";
                case 63:
                    return "checkRemoteLockPassword";
                case 64:
                    return "requestRemoteLockInfo";
                case 65:
                    return "setLockFMMPassword";
                case 66:
                    return "haveFMMPassword";
                case 67:
                    return "checkFMMPassword";
                case 68:
                    return "getCarrierLock";
                case 69:
                    return "updateCarrierLock";
                case 70:
                    return "setCarrierLockEnabled";
                case 71:
                    return "setLockCarrierPassword";
                case 72:
                    return "haveCarrierPassword";
                case 73:
                    return "checkCarrierPassword";
                case 74:
                    return "isRemoteLock";
                case 75:
                    return "setLockModeChangedCallback";
                case 76:
                    return "sendLockTypeChangedInfo";
                case 77:
                    return "getFailureCount";
                case 78:
                    return "getExpireTimeForPrev";
                case 79:
                    return "expirePreviousData";
                case 80:
                    return "isSupportWeaver";
                case 81:
                    return "setSecurityDebugLevel";
                case 82:
                    return "setShellCommandCallback";
                case 83:
                    return "setAppLockPin";
                case 84:
                    return "setAppLockPassword";
                case 85:
                    return "setAppLockPattern";
                case 86:
                    return "setAppLockBackupPin";
                case 87:
                    return "setAppLockFingerprintPassword";
                case 88:
                    return "checkAppLockPin";
                case 89:
                    return "checkAppLockPassword";
                case 90:
                    return "checkAppLockPatternWithHash";
                case 91:
                    return "checkAppLockBackupPin";
                case 92:
                    return "checkAppLockFingerprintPassword";
                case 93:
                    return "haveAppLockPin";
                case 94:
                    return "haveAppLockPassword";
                case 95:
                    return "haveAppLockPattern";
                case 96:
                    return "haveAppLockBackupPin";
                case 97:
                    return "haveAppLockFingerprintPassword";
                case 98:
                    return "setLockCredentialWithIgnoreNotifyIfNeeded";
                case 99:
                    return "notifyPasswordChangedForEnterpriseUser";
                case 100:
                    return "verifyToken";
                case 101:
                    return "changeToken";
                case 102:
                    return "updateSdpMdfppForSystem";
                case 103:
                    return "checkCredentialForDualDarDo";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    boolean z = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBoolean(string, z, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    long j = parcel.readLong();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLong(string2, j, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setString(string3, string4, i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string5 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean z3 = getBoolean(string5, z2, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z3);
                    return true;
                case 5:
                    String string6 = parcel.readString();
                    long j2 = parcel.readLong();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long j3 = getLong(string6, j2, i7);
                    parcel2.writeNoException();
                    parcel2.writeLong(j3);
                    return true;
                case 6:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String string9 = getString(string7, string8, i8);
                    parcel2.writeNoException();
                    parcel2.writeString(string9);
                    return true;
                case 7:
                    LockscreenCredential lockscreenCredential = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    LockscreenCredential lockscreenCredential2 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean lockCredential = setLockCredential(lockscreenCredential, lockscreenCredential2, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockCredential);
                    return true;
                case 8:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetKeyStore(i10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    LockscreenCredential lockscreenCredential3 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int i11 = parcel.readInt();
                    ICheckCredentialProgressCallback iCheckCredentialProgressCallbackAsInterface = ICheckCredentialProgressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyCredentialResponseCheckCredential = checkCredential(lockscreenCredential3, i11, iCheckCredentialProgressCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyCredentialResponseCheckCredential, 1);
                    return true;
                case 10:
                    LockscreenCredential lockscreenCredential4 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyCredentialResponseVerifyCredential = verifyCredential(lockscreenCredential4, i12, i13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyCredentialResponseVerifyCredential, 1);
                    return true;
                case 11:
                    LockscreenCredential lockscreenCredential5 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyCredentialResponseVerifyTiedProfileChallenge = verifyTiedProfileChallenge(lockscreenCredential5, i14, i15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyCredentialResponseVerifyTiedProfileChallenge, 1);
                    return true;
                case 12:
                    long j4 = parcel.readLong();
                    long j5 = parcel.readLong();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyCredentialResponseVerifyGatekeeperPasswordHandle = verifyGatekeeperPasswordHandle(j4, j5, i16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyCredentialResponseVerifyGatekeeperPasswordHandle, 1);
                    return true;
                case 13:
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    removeGatekeeperPasswordHandle(j6);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int credentialType = getCredentialType(i17);
                    parcel2.writeNoException();
                    parcel2.writeInt(credentialType);
                    return true;
                case 15:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int pinLength = getPinLength(i18);
                    parcel2.writeNoException();
                    parcel2.writeInt(pinLength);
                    return true;
                case 16:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRefreshStoredPinLength = refreshStoredPinLength(i19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRefreshStoredPinLength);
                    return true;
                case 17:
                    LockscreenCredential lockscreenCredential6 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] hashFactor = getHashFactor(lockscreenCredential6, i20);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(hashFactor);
                    return true;
                case 18:
                    int i21 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    LockscreenCredential lockscreenCredential7 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSeparateProfileChallengeEnabled(i21, z4, lockscreenCredential7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean separateProfileChallengeEnabled = getSeparateProfileChallengeEnabled(i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(separateProfileChallengeEnabled);
                    return true;
                case 20:
                    IStrongAuthTracker iStrongAuthTrackerAsInterface = IStrongAuthTracker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStrongAuthTracker(iStrongAuthTrackerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IStrongAuthTracker iStrongAuthTrackerAsInterface2 = IStrongAuthTracker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStrongAuthTracker(iStrongAuthTrackerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requireStrongAuth(i23, i24);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean z5 = parcel.readBoolean();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportSuccessfulBiometricUnlock(z5, i25);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleNonStrongBiometricIdleTimeout(i26);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    systemReady();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    userPresent(i27);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int strongAuthForUser = getStrongAuthForUser(i28);
                    parcel2.writeNoException();
                    parcel2.writeInt(strongAuthForUser);
                    return true;
                case 28:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasPendingEscrowToken = hasPendingEscrowToken(i29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasPendingEscrowToken);
                    return true;
                case 29:
                    String string10 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    initRecoveryServiceWithSigFile(string10, bArrCreateByteArray, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    KeyChainSnapshot keyChainSnapshot = getKeyChainSnapshot();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyChainSnapshot, 1);
                    return true;
                case 31:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strGenerateKey = generateKey(string11);
                    parcel2.writeNoException();
                    parcel2.writeString(strGenerateKey);
                    return true;
                case 32:
                    String string12 = parcel.readString();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String strGenerateKeyWithMetadata = generateKeyWithMetadata(string12, bArrCreateByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeString(strGenerateKeyWithMetadata);
                    return true;
                case 33:
                    String string13 = parcel.readString();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String strImportKey = importKey(string13, bArrCreateByteArray4);
                    parcel2.writeNoException();
                    parcel2.writeString(strImportKey);
                    return true;
                case 34:
                    String string14 = parcel.readString();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String strImportKeyWithMetadata = importKeyWithMetadata(string14, bArrCreateByteArray5, bArrCreateByteArray6);
                    parcel2.writeNoException();
                    parcel2.writeString(strImportKeyWithMetadata);
                    return true;
                case 35:
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String key = getKey(string15);
                    parcel2.writeNoException();
                    parcel2.writeString(key);
                    return true;
                case 36:
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeKey(string16);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSnapshotCreatedPendingIntent(pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setServerParams(bArrCreateByteArray7);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String string17 = parcel.readString();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRecoveryStatus(string17, i30);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    Map recoveryStatus = getRecoveryStatus();
                    parcel2.writeNoException();
                    parcel2.writeMap(recoveryStatus);
                    return true;
                case 41:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setRecoverySecretTypes(iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int[] recoverySecretTypes = getRecoverySecretTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(recoverySecretTypes);
                    return true;
                case 43:
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    RecoveryCertPath recoveryCertPath = (RecoveryCertPath) parcel.readTypedObject(RecoveryCertPath.CREATOR);
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(KeyChainProtectionParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] bArrStartRecoverySessionWithCertPath = startRecoverySessionWithCertPath(string18, string19, recoveryCertPath, bArrCreateByteArray8, bArrCreateByteArray9, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrStartRecoverySessionWithCertPath);
                    return true;
                case 44:
                    String string20 = parcel.readString();
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(WrappedApplicationKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    Map mapRecoverKeyChainSnapshot = recoverKeyChainSnapshot(string20, bArrCreateByteArray10, arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeMap(mapRecoverKeyChainSnapshot);
                    return true;
                case 45:
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSession(string21);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    RemoteLockscreenValidationSession remoteLockscreenValidationSessionStartRemoteLockscreenValidation = startRemoteLockscreenValidation();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(remoteLockscreenValidationSessionStartRemoteLockscreenValidation, 1);
                    return true;
                case 47:
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    RemoteLockscreenValidationResult remoteLockscreenValidationResultValidateRemoteLockscreen = validateRemoteLockscreen(bArrCreateByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(remoteLockscreenValidationResultValidateRemoteLockscreen, 1);
                    return true;
                case 48:
                    boolean zHasSecureLockScreen = hasSecureLockScreen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasSecureLockScreen);
                    return true;
                case 49:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zTryUnlockWithCachedUnifiedChallenge = tryUnlockWithCachedUnifiedChallenge(i31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTryUnlockWithCachedUnifiedChallenge);
                    return true;
                case 50:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeCachedUnifiedChallenge(i32);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListenerAsInterface = IWeakEscrowTokenRemovedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterWeakEscrowTokenRemovedListener = registerWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterWeakEscrowTokenRemovedListener);
                    return true;
                case 52:
                    IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListenerAsInterface2 = IWeakEscrowTokenRemovedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterWeakEscrowTokenRemovedListener = unregisterWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterWeakEscrowTokenRemovedListener);
                    return true;
                case 53:
                    byte[] bArrCreateByteArray12 = parcel.createByteArray();
                    int i33 = parcel.readInt();
                    IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListenerAsInterface = IWeakEscrowTokenActivatedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    long jAddWeakEscrowToken = addWeakEscrowToken(bArrCreateByteArray12, i33, iWeakEscrowTokenActivatedListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAddWeakEscrowToken);
                    return true;
                case 54:
                    long j7 = parcel.readLong();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveWeakEscrowToken = removeWeakEscrowToken(j7, i34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveWeakEscrowToken);
                    return true;
                case 55:
                    long j8 = parcel.readLong();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWeakEscrowTokenActive = isWeakEscrowTokenActive(j8, i35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWeakEscrowTokenActive);
                    return true;
                case 56:
                    long j9 = parcel.readLong();
                    byte[] bArrCreateByteArray13 = parcel.createByteArray();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWeakEscrowTokenValid = isWeakEscrowTokenValid(j9, bArrCreateByteArray13, i36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWeakEscrowTokenValid);
                    return true;
                case 57:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unlockUserKeyIfUnsecured(i37);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zWriteRepairModeCredential = writeRepairModeCredential(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zWriteRepairModeCredential);
                    return true;
                case 59:
                    int i39 = parcel.readInt();
                    IRemoteLockMonitorCallback iRemoteLockMonitorCallbackAsInterface = IRemoteLockMonitorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteLockCallback(i39, iRemoteLockMonitorCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int i40 = parcel.readInt();
                    IRemoteLockMonitorCallback iRemoteLockMonitorCallbackAsInterface2 = IRemoteLockMonitorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteLockCallback(i40, iRemoteLockMonitorCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int i41 = parcel.readInt();
                    RemoteLockInfo remoteLockInfo = (RemoteLockInfo) parcel.readTypedObject(RemoteLockInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean knoxGuard = setKnoxGuard(i41, remoteLockInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(knoxGuard);
                    return true;
                case 62:
                    int i42 = parcel.readInt();
                    RemoteLockInfo remoteLockInfo2 = (RemoteLockInfo) parcel.readTypedObject(RemoteLockInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRemoteLock(i42, remoteLockInfo2);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int i43 = parcel.readInt();
                    byte[] bArrCreateByteArray14 = parcel.createByteArray();
                    int i44 = parcel.readInt();
                    IRemoteCallback iRemoteCallbackAsInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    checkRemoteLockPassword(i43, bArrCreateByteArray14, i44, iRemoteCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestRemoteLockInfo(i45);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    byte[] bArrCreateByteArray15 = parcel.createByteArray();
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockFMMPassword(bArrCreateByteArray15, i46);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHaveFMMPassword = haveFMMPassword(i47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHaveFMMPassword);
                    return true;
                case 67:
                    byte[] bArrCreateByteArray16 = parcel.createByteArray();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckFMMPassword = checkFMMPassword(bArrCreateByteArray16, i48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckFMMPassword);
                    return true;
                case 68:
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean carrierLock = getCarrierLock(i49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(carrierLock);
                    return true;
                case 69:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateCarrierLock = updateCarrierLock(i50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateCarrierLock);
                    return true;
                case 70:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCarrierLockEnabled(i51);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    byte[] bArrCreateByteArray17 = parcel.createByteArray();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockCarrierPassword(bArrCreateByteArray17, i52);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHaveCarrierPassword = haveCarrierPassword(i53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHaveCarrierPassword);
                    return true;
                case 73:
                    byte[] bArrCreateByteArray18 = parcel.createByteArray();
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckCarrierPassword = checkCarrierPassword(bArrCreateByteArray18, i54);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckCarrierPassword);
                    return true;
                case 74:
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRemoteLock = isRemoteLock(i55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRemoteLock);
                    return true;
                case 75:
                    IRemoteCallback iRemoteCallbackAsInterface2 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setLockModeChangedCallback(iRemoteCallbackAsInterface2);
                    return true;
                case 76:
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendLockTypeChangedInfo(i56);
                    return true;
                case 77:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int failureCount = getFailureCount(i57);
                    parcel2.writeNoException();
                    parcel2.writeInt(failureCount);
                    return true;
                case 78:
                    long expireTimeForPrev = getExpireTimeForPrev();
                    parcel2.writeNoException();
                    parcel2.writeLong(expireTimeForPrev);
                    return true;
                case 79:
                    expirePreviousData();
                    return true;
                case 80:
                    boolean zIsSupportWeaver = isSupportWeaver();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportWeaver);
                    return true;
                case 81:
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSecurityDebugLevel(i58);
                    return true;
                case 82:
                    IRemoteCallback iRemoteCallbackAsInterface3 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setShellCommandCallback(iRemoteCallbackAsInterface3);
                    return true;
                case 83:
                    String string22 = parcel.readString();
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockPin(string22, i59);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    String string23 = parcel.readString();
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockPassword(string23, i60);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    String string24 = parcel.readString();
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockPattern(string24, i61);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    String string25 = parcel.readString();
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockBackupPin(string25, i62);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    String string26 = parcel.readString();
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockFingerprintPassword(string26, i63);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    String string27 = parcel.readString();
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckAppLockPin = checkAppLockPin(string27, i64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckAppLockPin);
                    return true;
                case 89:
                    String string28 = parcel.readString();
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckAppLockPassword = checkAppLockPassword(string28, i65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckAppLockPassword);
                    return true;
                case 90:
                    String string29 = parcel.readString();
                    int i66 = parcel.readInt();
                    byte[] bArrCreateByteArray19 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zCheckAppLockPatternWithHash = checkAppLockPatternWithHash(string29, i66, bArrCreateByteArray19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckAppLockPatternWithHash);
                    return true;
                case 91:
                    String string30 = parcel.readString();
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckAppLockBackupPin = checkAppLockBackupPin(string30, i67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckAppLockBackupPin);
                    return true;
                case 92:
                    String string31 = parcel.readString();
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckAppLockFingerprintPassword = checkAppLockFingerprintPassword(string31, i68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckAppLockFingerprintPassword);
                    return true;
                case 93:
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHaveAppLockPin = haveAppLockPin(i69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHaveAppLockPin);
                    return true;
                case 94:
                    int i70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHaveAppLockPassword = haveAppLockPassword(i70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHaveAppLockPassword);
                    return true;
                case 95:
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHaveAppLockPattern = haveAppLockPattern(i71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHaveAppLockPattern);
                    return true;
                case 96:
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHaveAppLockBackupPin = haveAppLockBackupPin(i72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHaveAppLockBackupPin);
                    return true;
                case 97:
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHaveAppLockFingerprintPassword = haveAppLockFingerprintPassword(i73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHaveAppLockFingerprintPassword);
                    return true;
                case 98:
                    LockscreenCredential lockscreenCredential8 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    LockscreenCredential lockscreenCredential9 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int i74 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean lockCredentialWithIgnoreNotifyIfNeeded = setLockCredentialWithIgnoreNotifyIfNeeded(lockscreenCredential8, lockscreenCredential9, i74, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockCredentialWithIgnoreNotifyIfNeeded);
                    return true;
                case 99:
                    LockscreenCredential lockscreenCredential10 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPasswordChangedForEnterpriseUser(lockscreenCredential10, i75);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    byte[] bArrCreateByteArray20 = parcel.createByteArray();
                    long j10 = parcel.readLong();
                    int i76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyCredentialResponseVerifyToken = verifyToken(bArrCreateByteArray20, j10, i76);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyCredentialResponseVerifyToken, 1);
                    return true;
                case 101:
                    byte[] bArrCreateByteArray21 = parcel.createByteArray();
                    long j11 = parcel.readLong();
                    byte[] bArrCreateByteArray22 = parcel.createByteArray();
                    long j12 = parcel.readLong();
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zChangeToken = changeToken(bArrCreateByteArray21, j11, bArrCreateByteArray22, j12, i77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeToken);
                    return true;
                case 102:
                    int i78 = parcel.readInt();
                    long j13 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateSdpMdfppForSystem(i78, j13);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    LockscreenCredential lockscreenCredential11 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int i79 = parcel.readInt();
                    int i80 = parcel.readInt();
                    IDualDarAuthProgressCallback iDualDarAuthProgressCallbackAsInterface = IDualDarAuthProgressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyCredentialResponseCheckCredentialForDualDarDo = checkCredentialForDualDarDo(lockscreenCredential11, i79, i80, iDualDarAuthProgressCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyCredentialResponseCheckCredentialForDualDarDo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ILockSettings {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setBoolean(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLong(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setString(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getBoolean(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long getLong(String str, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String getString(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(lockscreenCredential, 0);
                    parcelObtain.writeTypedObject(lockscreenCredential2, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void resetKeyStore(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse checkCredential(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(lockscreenCredential, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCheckCredentialProgressCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VerifyCredentialResponse) parcelObtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyCredential(LockscreenCredential lockscreenCredential, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(lockscreenCredential, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VerifyCredentialResponse) parcelObtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyTiedProfileChallenge(LockscreenCredential lockscreenCredential, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(lockscreenCredential, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VerifyCredentialResponse) parcelObtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VerifyCredentialResponse) parcelObtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeGatekeeperPasswordHandle(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getCredentialType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getPinLength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean refreshStoredPinLength(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public byte[] getHashFactor(LockscreenCredential lockscreenCredential, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(lockscreenCredential, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSeparateProfileChallengeEnabled(int i, boolean z, LockscreenCredential lockscreenCredential) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(lockscreenCredential, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getSeparateProfileChallengeEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStrongAuthTracker);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStrongAuthTracker);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void requireStrongAuth(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void reportSuccessfulBiometricUnlock(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void scheduleNonStrongBiometricIdleTimeout(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void systemReady() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void userPresent(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getStrongAuthForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean hasPendingEscrowToken(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void initRecoveryServiceWithSigFile(String str, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (KeyChainSnapshot) parcelObtain2.readTypedObject(KeyChainSnapshot.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String generateKey(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String generateKeyWithMetadata(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String importKey(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String importKeyWithMetadata(String str, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String getKey(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeKey(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setServerParams(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRecoveryStatus(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public Map getRecoveryStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRecoverySecretTypes(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int[] getRecoverySecretTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public byte[] startRecoverySessionWithCertPath(String str, String str2, RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, List<KeyChainProtectionParams> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(recoveryCertPath, 0);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public Map recoverKeyChainSnapshot(String str, byte[] bArr, List<WrappedApplicationKey> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void closeSession(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public RemoteLockscreenValidationSession startRemoteLockscreenValidation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RemoteLockscreenValidationSession) parcelObtain2.readTypedObject(RemoteLockscreenValidationSession.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RemoteLockscreenValidationResult) parcelObtain2.readTypedObject(RemoteLockscreenValidationResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean hasSecureLockScreen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean tryUnlockWithCachedUnifiedChallenge(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeCachedUnifiedChallenge(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWeakEscrowTokenRemovedListener);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWeakEscrowTokenRemovedListener);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long addWeakEscrowToken(byte[] bArr, int i, IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iWeakEscrowTokenActivatedListener);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean removeWeakEscrowToken(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isWeakEscrowTokenActive(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unlockUserKeyIfUnsecured(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean writeRepairModeCredential(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void registerRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRemoteLockMonitorCallback);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unregisterRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRemoteLockMonitorCallback);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setKnoxGuard(int i, RemoteLockInfo remoteLockInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(remoteLockInfo, 0);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRemoteLock(int i, RemoteLockInfo remoteLockInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(remoteLockInfo, 0);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void checkRemoteLockPassword(int i, byte[] bArr, int i2, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void requestRemoteLockInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLockFMMPassword(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveFMMPassword(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkFMMPassword(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getCarrierLock(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean updateCarrierLock(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setCarrierLockEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLockCarrierPassword(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveCarrierPassword(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkCarrierPassword(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isRemoteLock(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLockModeChangedCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(75, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void sendLockTypeChangedInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(76, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getFailureCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long getExpireTimeForPrev() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void expirePreviousData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isSupportWeaver() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSecurityDebugLevel(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setShellCommandCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(82, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockPin(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockPassword(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockPattern(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockBackupPin(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockFingerprintPassword(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockPin(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockPassword(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockPatternWithHash(String str, int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockBackupPin(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockFingerprintPassword(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockPin(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockPassword(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockPattern(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockBackupPin(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockFingerprintPassword(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setLockCredentialWithIgnoreNotifyIfNeeded(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(lockscreenCredential, 0);
                    parcelObtain.writeTypedObject(lockscreenCredential2, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void notifyPasswordChangedForEnterpriseUser(LockscreenCredential lockscreenCredential, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(lockscreenCredential, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyToken(byte[] bArr, long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VerifyCredentialResponse) parcelObtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean changeToken(byte[] bArr, long j, byte[] bArr2, long j2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void updateSdpMdfppForSystem(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse checkCredentialForDualDarDo(LockscreenCredential lockscreenCredential, int i, int i2, IDualDarAuthProgressCallback iDualDarAuthProgressCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(lockscreenCredential, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iDualDarAuthProgressCallback);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VerifyCredentialResponse) parcelObtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
