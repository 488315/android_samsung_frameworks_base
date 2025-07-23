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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILockSettings)) {
                return (ILockSettings) queryLocalInterface;
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
                    String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBoolean(readString, readBoolean, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLong(readString2, readLong, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setString(readString3, readString4, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString5 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean z = getBoolean(readString5, readBoolean2, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(z);
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long j = getLong(readString6, readLong2, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeLong(j);
                    return true;
                case 6:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String string = getString(readString7, readString8, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeString(string);
                    return true;
                case 7:
                    LockscreenCredential lockscreenCredential = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    LockscreenCredential lockscreenCredential2 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean lockCredential = setLockCredential(lockscreenCredential, lockscreenCredential2, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockCredential);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetKeyStore(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    LockscreenCredential lockscreenCredential3 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int readInt9 = parcel.readInt();
                    ICheckCredentialProgressCallback asInterface = ICheckCredentialProgressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse checkCredential = checkCredential(lockscreenCredential3, readInt9, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(checkCredential, 1);
                    return true;
                case 10:
                    LockscreenCredential lockscreenCredential4 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyCredential = verifyCredential(lockscreenCredential4, readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyCredential, 1);
                    return true;
                case 11:
                    LockscreenCredential lockscreenCredential5 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyTiedProfileChallenge = verifyTiedProfileChallenge(lockscreenCredential5, readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyTiedProfileChallenge, 1);
                    return true;
                case 12:
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyGatekeeperPasswordHandle = verifyGatekeeperPasswordHandle(readLong3, readLong4, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyGatekeeperPasswordHandle, 1);
                    return true;
                case 13:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    removeGatekeeperPasswordHandle(readLong5);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int credentialType = getCredentialType(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(credentialType);
                    return true;
                case 15:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int pinLength = getPinLength(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(pinLength);
                    return true;
                case 16:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean refreshStoredPinLength = refreshStoredPinLength(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(refreshStoredPinLength);
                    return true;
                case 17:
                    LockscreenCredential lockscreenCredential6 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] hashFactor = getHashFactor(lockscreenCredential6, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(hashFactor);
                    return true;
                case 18:
                    int readInt19 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    LockscreenCredential lockscreenCredential7 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSeparateProfileChallengeEnabled(readInt19, readBoolean3, lockscreenCredential7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean separateProfileChallengeEnabled = getSeparateProfileChallengeEnabled(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(separateProfileChallengeEnabled);
                    return true;
                case 20:
                    IStrongAuthTracker asInterface2 = IStrongAuthTracker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStrongAuthTracker(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IStrongAuthTracker asInterface3 = IStrongAuthTracker.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStrongAuthTracker(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requireStrongAuth(readInt21, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportSuccessfulBiometricUnlock(readBoolean4, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    scheduleNonStrongBiometricIdleTimeout(readInt24);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    systemReady();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    userPresent(readInt25);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int strongAuthForUser = getStrongAuthForUser(readInt26);
                    parcel2.writeNoException();
                    parcel2.writeInt(strongAuthForUser);
                    return true;
                case 28:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasPendingEscrowToken = hasPendingEscrowToken(readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasPendingEscrowToken);
                    return true;
                case 29:
                    String readString9 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    initRecoveryServiceWithSigFile(readString9, createByteArray, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    KeyChainSnapshot keyChainSnapshot = getKeyChainSnapshot();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyChainSnapshot, 1);
                    return true;
                case 31:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String generateKey = generateKey(readString10);
                    parcel2.writeNoException();
                    parcel2.writeString(generateKey);
                    return true;
                case 32:
                    String readString11 = parcel.readString();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String generateKeyWithMetadata = generateKeyWithMetadata(readString11, createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeString(generateKeyWithMetadata);
                    return true;
                case 33:
                    String readString12 = parcel.readString();
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String importKey = importKey(readString12, createByteArray4);
                    parcel2.writeNoException();
                    parcel2.writeString(importKey);
                    return true;
                case 34:
                    String readString13 = parcel.readString();
                    byte[] createByteArray5 = parcel.createByteArray();
                    byte[] createByteArray6 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    String importKeyWithMetadata = importKeyWithMetadata(readString13, createByteArray5, createByteArray6);
                    parcel2.writeNoException();
                    parcel2.writeString(importKeyWithMetadata);
                    return true;
                case 35:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String key = getKey(readString14);
                    parcel2.writeNoException();
                    parcel2.writeString(key);
                    return true;
                case 36:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeKey(readString15);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSnapshotCreatedPendingIntent(pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    byte[] createByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setServerParams(createByteArray7);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String readString16 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRecoveryStatus(readString16, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    Map recoveryStatus = getRecoveryStatus();
                    parcel2.writeNoException();
                    parcel2.writeMap(recoveryStatus);
                    return true;
                case 41:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setRecoverySecretTypes(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int[] recoverySecretTypes = getRecoverySecretTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(recoverySecretTypes);
                    return true;
                case 43:
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    RecoveryCertPath recoveryCertPath = (RecoveryCertPath) parcel.readTypedObject(RecoveryCertPath.CREATOR);
                    byte[] createByteArray8 = parcel.createByteArray();
                    byte[] createByteArray9 = parcel.createByteArray();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(KeyChainProtectionParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] startRecoverySessionWithCertPath = startRecoverySessionWithCertPath(readString17, readString18, recoveryCertPath, createByteArray8, createByteArray9, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(startRecoverySessionWithCertPath);
                    return true;
                case 44:
                    String readString19 = parcel.readString();
                    byte[] createByteArray10 = parcel.createByteArray();
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(WrappedApplicationKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    Map recoverKeyChainSnapshot = recoverKeyChainSnapshot(readString19, createByteArray10, createTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeMap(recoverKeyChainSnapshot);
                    return true;
                case 45:
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSession(readString20);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    RemoteLockscreenValidationSession startRemoteLockscreenValidation = startRemoteLockscreenValidation();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startRemoteLockscreenValidation, 1);
                    return true;
                case 47:
                    byte[] createByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    RemoteLockscreenValidationResult validateRemoteLockscreen = validateRemoteLockscreen(createByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(validateRemoteLockscreen, 1);
                    return true;
                case 48:
                    boolean hasSecureLockScreen = hasSecureLockScreen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSecureLockScreen);
                    return true;
                case 49:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean tryUnlockWithCachedUnifiedChallenge = tryUnlockWithCachedUnifiedChallenge(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(tryUnlockWithCachedUnifiedChallenge);
                    return true;
                case 50:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeCachedUnifiedChallenge(readInt30);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    IWeakEscrowTokenRemovedListener asInterface4 = IWeakEscrowTokenRemovedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerWeakEscrowTokenRemovedListener = registerWeakEscrowTokenRemovedListener(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerWeakEscrowTokenRemovedListener);
                    return true;
                case 52:
                    IWeakEscrowTokenRemovedListener asInterface5 = IWeakEscrowTokenRemovedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterWeakEscrowTokenRemovedListener = unregisterWeakEscrowTokenRemovedListener(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterWeakEscrowTokenRemovedListener);
                    return true;
                case 53:
                    byte[] createByteArray12 = parcel.createByteArray();
                    int readInt31 = parcel.readInt();
                    IWeakEscrowTokenActivatedListener asInterface6 = IWeakEscrowTokenActivatedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    long addWeakEscrowToken = addWeakEscrowToken(createByteArray12, readInt31, asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeLong(addWeakEscrowToken);
                    return true;
                case 54:
                    long readLong6 = parcel.readLong();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeWeakEscrowToken = removeWeakEscrowToken(readLong6, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeWeakEscrowToken);
                    return true;
                case 55:
                    long readLong7 = parcel.readLong();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWeakEscrowTokenActive = isWeakEscrowTokenActive(readLong7, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWeakEscrowTokenActive);
                    return true;
                case 56:
                    long readLong8 = parcel.readLong();
                    byte[] createByteArray13 = parcel.createByteArray();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWeakEscrowTokenValid = isWeakEscrowTokenValid(readLong8, createByteArray13, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWeakEscrowTokenValid);
                    return true;
                case 57:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unlockUserKeyIfUnsecured(readInt35);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean writeRepairModeCredential = writeRepairModeCredential(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(writeRepairModeCredential);
                    return true;
                case 59:
                    int readInt37 = parcel.readInt();
                    IRemoteLockMonitorCallback asInterface7 = IRemoteLockMonitorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRemoteLockCallback(readInt37, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt38 = parcel.readInt();
                    IRemoteLockMonitorCallback asInterface8 = IRemoteLockMonitorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRemoteLockCallback(readInt38, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt39 = parcel.readInt();
                    RemoteLockInfo remoteLockInfo = (RemoteLockInfo) parcel.readTypedObject(RemoteLockInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean knoxGuard = setKnoxGuard(readInt39, remoteLockInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(knoxGuard);
                    return true;
                case 62:
                    int readInt40 = parcel.readInt();
                    RemoteLockInfo remoteLockInfo2 = (RemoteLockInfo) parcel.readTypedObject(RemoteLockInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRemoteLock(readInt40, remoteLockInfo2);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int readInt41 = parcel.readInt();
                    byte[] createByteArray14 = parcel.createByteArray();
                    int readInt42 = parcel.readInt();
                    IRemoteCallback asInterface9 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    checkRemoteLockPassword(readInt41, createByteArray14, readInt42, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestRemoteLockInfo(readInt43);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    byte[] createByteArray15 = parcel.createByteArray();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockFMMPassword(createByteArray15, readInt44);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean haveFMMPassword = haveFMMPassword(readInt45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(haveFMMPassword);
                    return true;
                case 67:
                    byte[] createByteArray16 = parcel.createByteArray();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkFMMPassword = checkFMMPassword(createByteArray16, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkFMMPassword);
                    return true;
                case 68:
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean carrierLock = getCarrierLock(readInt47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(carrierLock);
                    return true;
                case 69:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateCarrierLock = updateCarrierLock(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateCarrierLock);
                    return true;
                case 70:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCarrierLockEnabled(readInt49);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    byte[] createByteArray17 = parcel.createByteArray();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockCarrierPassword(createByteArray17, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean haveCarrierPassword = haveCarrierPassword(readInt51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(haveCarrierPassword);
                    return true;
                case 73:
                    byte[] createByteArray18 = parcel.createByteArray();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkCarrierPassword = checkCarrierPassword(createByteArray18, readInt52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkCarrierPassword);
                    return true;
                case 74:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRemoteLock = isRemoteLock(readInt53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRemoteLock);
                    return true;
                case 75:
                    IRemoteCallback asInterface10 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setLockModeChangedCallback(asInterface10);
                    return true;
                case 76:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendLockTypeChangedInfo(readInt54);
                    return true;
                case 77:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int failureCount = getFailureCount(readInt55);
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
                    boolean isSupportWeaver = isSupportWeaver();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportWeaver);
                    return true;
                case 81:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSecurityDebugLevel(readInt56);
                    return true;
                case 82:
                    IRemoteCallback asInterface11 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setShellCommandCallback(asInterface11);
                    return true;
                case 83:
                    String readString21 = parcel.readString();
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockPin(readString21, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    String readString22 = parcel.readString();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockPassword(readString22, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    String readString23 = parcel.readString();
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockPattern(readString23, readInt59);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    String readString24 = parcel.readString();
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockBackupPin(readString24, readInt60);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    String readString25 = parcel.readString();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppLockFingerprintPassword(readString25, readInt61);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    String readString26 = parcel.readString();
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkAppLockPin = checkAppLockPin(readString26, readInt62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkAppLockPin);
                    return true;
                case 89:
                    String readString27 = parcel.readString();
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkAppLockPassword = checkAppLockPassword(readString27, readInt63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkAppLockPassword);
                    return true;
                case 90:
                    String readString28 = parcel.readString();
                    int readInt64 = parcel.readInt();
                    byte[] createByteArray19 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean checkAppLockPatternWithHash = checkAppLockPatternWithHash(readString28, readInt64, createByteArray19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkAppLockPatternWithHash);
                    return true;
                case 91:
                    String readString29 = parcel.readString();
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkAppLockBackupPin = checkAppLockBackupPin(readString29, readInt65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkAppLockBackupPin);
                    return true;
                case 92:
                    String readString30 = parcel.readString();
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkAppLockFingerprintPassword = checkAppLockFingerprintPassword(readString30, readInt66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkAppLockFingerprintPassword);
                    return true;
                case 93:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean haveAppLockPin = haveAppLockPin(readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(haveAppLockPin);
                    return true;
                case 94:
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean haveAppLockPassword = haveAppLockPassword(readInt68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(haveAppLockPassword);
                    return true;
                case 95:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean haveAppLockPattern = haveAppLockPattern(readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(haveAppLockPattern);
                    return true;
                case 96:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean haveAppLockBackupPin = haveAppLockBackupPin(readInt70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(haveAppLockBackupPin);
                    return true;
                case 97:
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean haveAppLockFingerprintPassword = haveAppLockFingerprintPassword(readInt71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(haveAppLockFingerprintPassword);
                    return true;
                case 98:
                    LockscreenCredential lockscreenCredential8 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    LockscreenCredential lockscreenCredential9 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int readInt72 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean lockCredentialWithIgnoreNotifyIfNeeded = setLockCredentialWithIgnoreNotifyIfNeeded(lockscreenCredential8, lockscreenCredential9, readInt72, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(lockCredentialWithIgnoreNotifyIfNeeded);
                    return true;
                case 99:
                    LockscreenCredential lockscreenCredential10 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPasswordChangedForEnterpriseUser(lockscreenCredential10, readInt73);
                    parcel2.writeNoException();
                    return true;
                case 100:
                    byte[] createByteArray20 = parcel.createByteArray();
                    long readLong9 = parcel.readLong();
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse verifyToken = verifyToken(createByteArray20, readLong9, readInt74);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyToken, 1);
                    return true;
                case 101:
                    byte[] createByteArray21 = parcel.createByteArray();
                    long readLong10 = parcel.readLong();
                    byte[] createByteArray22 = parcel.createByteArray();
                    long readLong11 = parcel.readLong();
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean changeToken = changeToken(createByteArray21, readLong10, createByteArray22, readLong11, readInt75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeToken);
                    return true;
                case 102:
                    int readInt76 = parcel.readInt();
                    long readLong12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateSdpMdfppForSystem(readInt76, readLong12);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    LockscreenCredential lockscreenCredential11 = (LockscreenCredential) parcel.readTypedObject(LockscreenCredential.CREATOR);
                    int readInt77 = parcel.readInt();
                    int readInt78 = parcel.readInt();
                    IDualDarAuthProgressCallback asInterface12 = IDualDarAuthProgressCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    VerifyCredentialResponse checkCredentialForDualDarDo = checkCredentialForDualDarDo(lockscreenCredential11, readInt77, readInt78, asInterface12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(checkCredentialForDualDarDo, 1);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLong(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setString(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getBoolean(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long getLong(String str, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String getString(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeTypedObject(lockscreenCredential2, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void resetKeyStore(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse checkCredential(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCheckCredentialProgressCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VerifyCredentialResponse) obtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyCredential(LockscreenCredential lockscreenCredential, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VerifyCredentialResponse) obtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyTiedProfileChallenge(LockscreenCredential lockscreenCredential, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VerifyCredentialResponse) obtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VerifyCredentialResponse) obtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeGatekeeperPasswordHandle(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getCredentialType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getPinLength(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean refreshStoredPinLength(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public byte[] getHashFactor(LockscreenCredential lockscreenCredential, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSeparateProfileChallengeEnabled(int i, boolean z, LockscreenCredential lockscreenCredential) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getSeparateProfileChallengeEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStrongAuthTracker);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStrongAuthTracker);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void requireStrongAuth(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void reportSuccessfulBiometricUnlock(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void scheduleNonStrongBiometricIdleTimeout(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void systemReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void userPresent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getStrongAuthForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean hasPendingEscrowToken(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void initRecoveryServiceWithSigFile(String str, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public KeyChainSnapshot getKeyChainSnapshot() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KeyChainSnapshot) obtain2.readTypedObject(KeyChainSnapshot.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String generateKey(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String generateKeyWithMetadata(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String importKey(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String importKeyWithMetadata(String str, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public String getKey(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeKey(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setServerParams(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRecoveryStatus(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public Map getRecoveryStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRecoverySecretTypes(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int[] getRecoverySecretTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public byte[] startRecoverySessionWithCertPath(String str, String str2, RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, List<KeyChainProtectionParams> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(recoveryCertPath, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public Map recoverKeyChainSnapshot(String str, byte[] bArr, List<WrappedApplicationKey> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void closeSession(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public RemoteLockscreenValidationSession startRemoteLockscreenValidation() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RemoteLockscreenValidationSession) obtain2.readTypedObject(RemoteLockscreenValidationSession.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RemoteLockscreenValidationResult) obtain2.readTypedObject(RemoteLockscreenValidationResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean hasSecureLockScreen() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean tryUnlockWithCachedUnifiedChallenge(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void removeCachedUnifiedChallenge(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWeakEscrowTokenRemovedListener);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWeakEscrowTokenRemovedListener);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long addWeakEscrowToken(byte[] bArr, int i, IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iWeakEscrowTokenActivatedListener);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean removeWeakEscrowToken(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isWeakEscrowTokenActive(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unlockUserKeyIfUnsecured(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean writeRepairModeCredential(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void registerRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteLockMonitorCallback);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void unregisterRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteLockMonitorCallback);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setKnoxGuard(int i, RemoteLockInfo remoteLockInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteLockInfo, 0);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setRemoteLock(int i, RemoteLockInfo remoteLockInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteLockInfo, 0);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void checkRemoteLockPassword(int i, byte[] bArr, int i2, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void requestRemoteLockInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLockFMMPassword(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveFMMPassword(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkFMMPassword(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean getCarrierLock(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean updateCarrierLock(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setCarrierLockEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLockCarrierPassword(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveCarrierPassword(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkCarrierPassword(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isRemoteLock(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setLockModeChangedCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(75, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void sendLockTypeChangedInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public int getFailureCount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public long getExpireTimeForPrev() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void expirePreviousData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean isSupportWeaver() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setSecurityDebugLevel(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setShellCommandCallback(IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(82, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockPin(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockPassword(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockPattern(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockBackupPin(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void setAppLockFingerprintPassword(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockPin(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockPassword(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockPatternWithHash(String str, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockBackupPin(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean checkAppLockFingerprintPassword(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockPin(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockPassword(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockPattern(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockBackupPin(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean haveAppLockFingerprintPassword(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean setLockCredentialWithIgnoreNotifyIfNeeded(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeTypedObject(lockscreenCredential2, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void notifyPasswordChangedForEnterpriseUser(LockscreenCredential lockscreenCredential, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse verifyToken(byte[] bArr, long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VerifyCredentialResponse) obtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public boolean changeToken(byte[] bArr, long j, byte[] bArr2, long j2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeLong(j);
                    obtain.writeByteArray(bArr2);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public void updateSdpMdfppForSystem(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.widget.ILockSettings
            public VerifyCredentialResponse checkCredentialForDualDarDo(LockscreenCredential lockscreenCredential, int i, int i2, IDualDarAuthProgressCallback iDualDarAuthProgressCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lockscreenCredential, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iDualDarAuthProgressCallback);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VerifyCredentialResponse) obtain2.readTypedObject(VerifyCredentialResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
