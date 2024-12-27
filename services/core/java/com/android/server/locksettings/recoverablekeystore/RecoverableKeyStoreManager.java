package com.android.server.locksettings.recoverablekeystore;

import android.app.RemoteLockscreenValidationResult;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.security.KeyStore2;
import android.security.keystore.recovery.KeyChainProtectionParams;
import android.security.keystore.recovery.TrustedRootCertificates;
import android.security.keystore.recovery.WrappedApplicationKey;
import android.system.keystore2.KeyDescriptor;
import android.util.ArrayMap;
import android.util.Log;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.HexDump;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.security.SecureBox;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException;
import com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException;
import com.android.server.locksettings.recoverablekeystore.certificate.CertXml;
import com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage;
import com.android.server.locksettings.recoverablekeystore.storage.CleanupManager;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDbHelper;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage;
import com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPath;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;

import javax.crypto.AEADBadTagException;
import javax.crypto.KeyGenerator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RecoverableKeyStoreManager {
    public static RecoverableKeyStoreManager mInstance;
    public final ApplicationKeyStorage mApplicationKeyStorage;
    public final CleanupManager mCleanupManager;
    public final Context mContext;
    public final RecoverableKeyStoreDb mDatabase;
    public final ScheduledExecutorService mExecutorService;
    public final RecoverySnapshotListenersStorage mListenersStorage;
    public final PlatformKeyManager mPlatformKeyManager;
    public final RecoverableKeyGenerator mRecoverableKeyGenerator;
    public final RecoverySessionStorage mRecoverySessionStorage;
    public final RemoteLockscreenValidationSessionStorage mRemoteLockscreenValidationSessionStorage;
    public final RecoverySnapshotStorage mSnapshotStorage;
    public final TestOnlyInsecureCertificateHelper mTestCertHelper;

    public RecoverableKeyStoreManager(
            Context context,
            RecoverableKeyStoreDb recoverableKeyStoreDb,
            RecoverySessionStorage recoverySessionStorage,
            ScheduledExecutorService scheduledExecutorService,
            RecoverySnapshotStorage recoverySnapshotStorage,
            RecoverySnapshotListenersStorage recoverySnapshotListenersStorage,
            PlatformKeyManager platformKeyManager,
            ApplicationKeyStorage applicationKeyStorage,
            TestOnlyInsecureCertificateHelper testOnlyInsecureCertificateHelper,
            CleanupManager cleanupManager,
            RemoteLockscreenValidationSessionStorage remoteLockscreenValidationSessionStorage) {
        this.mContext = context;
        this.mDatabase = recoverableKeyStoreDb;
        this.mRecoverySessionStorage = recoverySessionStorage;
        this.mExecutorService = scheduledExecutorService;
        this.mListenersStorage = recoverySnapshotListenersStorage;
        this.mSnapshotStorage = recoverySnapshotStorage;
        this.mPlatformKeyManager = platformKeyManager;
        this.mApplicationKeyStorage = applicationKeyStorage;
        this.mTestCertHelper = testOnlyInsecureCertificateHelper;
        this.mCleanupManager = cleanupManager;
        try {
            cleanupManager.verifyKnownUsers();
        } catch (Exception e) {
            Log.e("RecoverableKeyStoreMgr", "Failed to verify known users", e);
        }
        try {
            this.mRecoverableKeyGenerator =
                    new RecoverableKeyGenerator(KeyGenerator.getInstance("AES"), this.mDatabase);
            this.mRemoteLockscreenValidationSessionStorage =
                    remoteLockscreenValidationSessionStorage;
        } catch (NoSuchAlgorithmException e2) {
            Log.wtf(
                    "RecoverableKeyStoreMgr",
                    "AES keygen algorithm not available. AOSP must support this.",
                    e2);
            throw new ServiceSpecificException(22, e2.getMessage());
        }
    }

    public static byte[] decryptRecoveryKey(RecoverySessionStorage.Entry entry, byte[] bArr) {
        try {
            try {
                return SecureBox.decrypt(
                        null,
                        entry.mLskfHash,
                        KeySyncUtils.LOCALLY_ENCRYPTED_RECOVERY_KEY_HEADER,
                        SecureBox.decrypt(
                                null,
                                entry.mKeyClaimant,
                                ArrayUtils.concat(
                                        new byte[][] {
                                            KeySyncUtils.RECOVERY_RESPONSE_HEADER,
                                            entry.mVaultParams
                                        }),
                                bArr));
            } catch (InvalidKeyException e) {
                Log.e(
                        "RecoverableKeyStoreMgr",
                        "Got InvalidKeyException during decrypting recovery key",
                        e);
                throw new ServiceSpecificException(
                        26, "Failed to decrypt recovery key " + e.getMessage());
            } catch (NoSuchAlgorithmException e2) {
                throw new ServiceSpecificException(22, e2.getMessage());
            } catch (AEADBadTagException e3) {
                Log.e(
                        "RecoverableKeyStoreMgr",
                        "Got AEADBadTagException during decrypting recovery key",
                        e3);
                throw new ServiceSpecificException(
                        26, "Failed to decrypt recovery key " + e3.getMessage());
            }
        } catch (InvalidKeyException e4) {
            Log.e(
                    "RecoverableKeyStoreMgr",
                    "Got InvalidKeyException during decrypting recovery claim response",
                    e4);
            throw new ServiceSpecificException(
                    26, "Failed to decrypt recovery key " + e4.getMessage());
        } catch (NoSuchAlgorithmException e5) {
            throw new ServiceSpecificException(22, e5.getMessage());
        } catch (AEADBadTagException e6) {
            Log.e(
                    "RecoverableKeyStoreMgr",
                    "Got AEADBadTagException during decrypting recovery claim response",
                    e6);
            throw new ServiceSpecificException(
                    26, "Failed to decrypt recovery key " + e6.getMessage());
        }
    }

    public static int lockPatternUtilsToKeyguardType(int i) {
        if (i == -1) {
            throw new IllegalStateException("Screen lock is not set");
        }
        if (i == 1) {
            return 2;
        }
        if (i == 3) {
            return 1;
        }
        if (i == 4) {
            return 0;
        }
        throw new IllegalStateException("Screen lock is not set");
    }

    public static Map recoverApplicationKeys(byte[] bArr, List list) {
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            WrappedApplicationKey wrappedApplicationKey = (WrappedApplicationKey) it.next();
            String alias = wrappedApplicationKey.getAlias();
            byte[] encryptedKeyMaterial = wrappedApplicationKey.getEncryptedKeyMaterial();
            byte[] metadata = wrappedApplicationKey.getMetadata();
            try {
                byte[] bArr2 = KeySyncUtils.ENCRYPTED_APPLICATION_KEY_HEADER;
                if (metadata != null) {
                    bArr2 = ArrayUtils.concat(new byte[][] {bArr2, metadata});
                }
                hashMap.put(alias, SecureBox.decrypt(null, bArr, bArr2, encryptedKeyMaterial));
            } catch (InvalidKeyException e) {
                Log.e(
                        "RecoverableKeyStoreMgr",
                        "Got InvalidKeyException during decrypting application key with alias: "
                                + alias,
                        e);
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "Failed to recover key with alias '", alias, "': ");
                m.append(e.getMessage());
                throw new ServiceSpecificException(26, m.toString());
            } catch (NoSuchAlgorithmException e2) {
                Log.wtf(
                        "RecoverableKeyStoreMgr",
                        "Missing SecureBox algorithm. AOSP required to support this.",
                        e2);
                throw new ServiceSpecificException(22, e2.getMessage());
            } catch (AEADBadTagException e3) {
                Log.e(
                        "RecoverableKeyStoreMgr",
                        "Got AEADBadTagException during decrypting application key with alias: "
                                + alias,
                        e3);
            }
        }
        if (list.isEmpty() || !hashMap.isEmpty()) {
            return hashMap;
        }
        Log.e("RecoverableKeyStoreMgr", "Failed to recover any of the application keys.");
        throw new ServiceSpecificException(26, "Failed to recover any of the application keys.");
    }

    public final void checkRecoverKeyStorePermission() {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.RECOVER_KEYSTORE",
                "Caller " + Binder.getCallingUid() + " doesn't have RecoverKeyStore permission.");
        int callingUserId = UserHandle.getCallingUserId();
        Binder.getCallingUid();
        this.mCleanupManager.registerRecoveryAgent(callingUserId);
    }

    public final void checkVerifyRemoteLockscreenPermission() {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.CHECK_REMOTE_LOCKSCREEN",
                "Caller "
                        + Binder.getCallingUid()
                        + " doesn't have CHECK_REMOTE_LOCKSCREEN permission.");
        int callingUserId = UserHandle.getCallingUserId();
        Binder.getCallingUid();
        this.mCleanupManager.registerRecoveryAgent(callingUserId);
    }

    public final String generateKeyWithMetadata(String str, byte[] bArr) {
        checkRecoverKeyStorePermission();
        Objects.requireNonNull(str, "alias is null");
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        try {
            try {
                this.mApplicationKeyStorage.setSymmetricKeyEntry(
                        callingUserId,
                        callingUid,
                        this.mRecoverableKeyGenerator.generateAndStoreKey(
                                this.mPlatformKeyManager.getEncryptKey(callingUserId),
                                callingUserId,
                                callingUid,
                                str,
                                bArr),
                        str);
                return getAlias(callingUserId, callingUid, str);
            } catch (RecoverableKeyStorageException | InvalidKeyException | KeyStoreException e) {
                throw new ServiceSpecificException(22, e.getMessage());
            }
        } catch (InsecureUserException e2) {
            throw new ServiceSpecificException(23, e2.getMessage());
        } catch (IOException | KeyStoreException | UnrecoverableKeyException e3) {
            throw new ServiceSpecificException(22, e3.getMessage());
        } catch (NoSuchAlgorithmException e4) {
            throw new RuntimeException(e4);
        }
    }

    public final String getAlias(int i, int i2, String str) {
        this.mApplicationKeyStorage.getClass();
        Locale locale = Locale.US;
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "Get ", "/", "/"),
                str,
                "RecoverableAppKeyStore");
        String internalAlias = ApplicationKeyStorage.getInternalAlias(i, i2, str);
        if (internalAlias == null) {
            return null;
        }
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = 0;
        keyDescriptor.nspace = -1L;
        keyDescriptor.alias = internalAlias;
        keyDescriptor.blob = null;
        try {
            return String.format(
                    "%s%016X",
                    "recoverable_key:",
                    Long.valueOf(KeyStore2.getInstance().grant(keyDescriptor, i2, 261).nspace));
        } catch (android.security.KeyStoreException e) {
            if (e.getNumericErrorCode() == 6) {
                Log.w(
                        "RecoverableAppKeyStore",
                        "Failed to get grant for KeyStore key - key not found");
                throw new ServiceSpecificException(30, e.getMessage());
            }
            Log.e("RecoverableAppKeyStore", "Failed to get grant for KeyStore key.", e);
            throw new ServiceSpecificException(22, e.getMessage());
        }
    }

    public final RemoteLockscreenValidationResult handleVerifyCredentialResponse(
            VerifyCredentialResponse verifyCredentialResponse, int i) {
        int responseCode = verifyCredentialResponse.getResponseCode();
        RecoverableKeyStoreDb recoverableKeyStoreDb = this.mDatabase;
        if (responseCode == 0) {
            recoverableKeyStoreDb.setBadRemoteGuessCounter(i, 0);
            this.mRemoteLockscreenValidationSessionStorage.finishSession(i);
            return new RemoteLockscreenValidationResult.Builder().setResultCode(1).build();
        }
        if (verifyCredentialResponse.getResponseCode() == 1) {
            return new RemoteLockscreenValidationResult.Builder()
                    .setResultCode(3)
                    .setTimeoutMillis(verifyCredentialResponse.getTimeout())
                    .build();
        }
        recoverableKeyStoreDb.setBadRemoteGuessCounter(
                i, recoverableKeyStoreDb.getBadRemoteGuessCounter(i) + 1);
        return new RemoteLockscreenValidationResult.Builder().setResultCode(2).build();
    }

    public final Map importKeyMaterials(int i, int i2, Map map) {
        HashMap hashMap = (HashMap) map;
        ArrayMap arrayMap = new ArrayMap(hashMap.size());
        for (String str : hashMap.keySet()) {
            this.mApplicationKeyStorage.setSymmetricKeyEntry(i, i2, (byte[]) hashMap.get(str), str);
            String alias = getAlias(i, i2, str);
            Locale locale = Locale.US;
            Log.i("RecoverableKeyStoreMgr", "Import " + str + " -> " + alias);
            arrayMap.put(str, alias);
        }
        return arrayMap;
    }

    public final String importKeyWithMetadata(String str, byte[] bArr, byte[] bArr2) {
        checkRecoverKeyStorePermission();
        Objects.requireNonNull(str, "alias is null");
        Objects.requireNonNull(bArr, "keyBytes is null");
        if (bArr.length != 32) {
            Log.e(
                    "RecoverableKeyStoreMgr",
                    "The given key for import doesn't have the required length 256");
            throw new ServiceSpecificException(27, "The given key does not contain 256 bits.");
        }
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        try {
            try {
                this.mRecoverableKeyGenerator.importKey(
                        this.mPlatformKeyManager.getEncryptKey(callingUserId),
                        callingUserId,
                        callingUid,
                        str,
                        bArr,
                        bArr2);
                this.mApplicationKeyStorage.setSymmetricKeyEntry(
                        callingUserId, callingUid, bArr, str);
                return getAlias(callingUserId, callingUid, str);
            } catch (RecoverableKeyStorageException | InvalidKeyException | KeyStoreException e) {
                throw new ServiceSpecificException(22, e.getMessage());
            }
        } catch (InsecureUserException e2) {
            throw new ServiceSpecificException(23, e2.getMessage());
        } catch (IOException | KeyStoreException | UnrecoverableKeyException e3) {
            throw new ServiceSpecificException(22, e3.getMessage());
        } catch (NoSuchAlgorithmException e4) {
            throw new RuntimeException(e4);
        }
    }

    public void initRecoveryService(String str, byte[] bArr) throws RemoteException {
        checkRecoverKeyStorePermission();
        int callingUserId = UserHandle.getCallingUserId();
        int callingUid = Binder.getCallingUid();
        this.mTestCertHelper.getClass();
        String defaultCertificateAliasIfEmpty =
                TestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(str);
        if (!TrustedRootCertificates.getRootCertificates()
                        .containsKey(defaultCertificateAliasIfEmpty)
                && !"TEST_ONLY_INSECURE_CERTIFICATE_ALIAS".equals(defaultCertificateAliasIfEmpty)) {
            throw new ServiceSpecificException(28, "Invalid root certificate alias");
        }
        RecoverableKeyStoreDb recoverableKeyStoreDb = this.mDatabase;
        String activeRootOfTrust =
                recoverableKeyStoreDb.getActiveRootOfTrust(callingUserId, callingUid);
        if (activeRootOfTrust == null) {
            Log.d(
                    "RecoverableKeyStoreMgr",
                    "Root of trust for recovery agent + "
                            + callingUid
                            + " is assigned for the first time to "
                            + defaultCertificateAliasIfEmpty);
        } else if (!activeRootOfTrust.equals(defaultCertificateAliasIfEmpty)) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            callingUid,
                            "Root of trust for recovery agent ",
                            " is changed to ",
                            defaultCertificateAliasIfEmpty,
                            " from  "),
                    activeRootOfTrust,
                    "RecoverableKeyStoreMgr");
        }
        RecoverableKeyStoreDbHelper recoverableKeyStoreDbHelper =
                recoverableKeyStoreDb.mKeyStoreDbHelper;
        SQLiteDatabase writableDatabase = recoverableKeyStoreDbHelper.getWritableDatabase();
        new ContentValues().put("active_root_of_trust", defaultCertificateAliasIfEmpty);
        recoverableKeyStoreDb.ensureRecoveryServiceMetadataEntryExists(callingUserId, callingUid);
        if (writableDatabase.update(
                        "recovery_service_metadata",
                        r5,
                        "user_id = ? AND uid = ?",
                        new String[] {String.valueOf(callingUserId), String.valueOf(callingUid)})
                < 0) {
            throw new ServiceSpecificException(
                    22, "Failed to set the root of trust in the local DB.");
        }
        try {
            CertXml parse = CertXml.parse(bArr);
            recoverableKeyStoreDb.mTestOnlyInsecureCertificateHelper.getClass();
            Cursor query =
                    recoverableKeyStoreDbHelper
                            .getReadableDatabase()
                            .query(
                                    "root_of_trust",
                                    new String[] {
                                        KnoxCustomManagerService.ID,
                                        "user_id",
                                        "uid",
                                        "root_alias",
                                        "cert_serial"
                                    },
                                    "user_id = ? AND uid = ? AND root_alias = ?",
                                    new String[] {
                                        Integer.toString(callingUserId),
                                        Integer.toString(callingUid),
                                        TestOnlyInsecureCertificateHelper
                                                .getDefaultCertificateAliasIfEmpty(
                                                        defaultCertificateAliasIfEmpty)
                                    },
                                    null,
                                    null,
                                    null);
            try {
                int count = query.getCount();
                Long l = null;
                if (count != 0) {
                    if (count > 1) {
                        Locale locale = Locale.US;
                        Log.wtf(
                                "RecoverableKeyStoreDb",
                                count
                                        + " entries found for userId="
                                        + callingUserId
                                        + " uid="
                                        + callingUid
                                        + ". Should only ever be 0 or 1.");
                    } else {
                        query.moveToFirst();
                        int columnIndexOrThrow = query.getColumnIndexOrThrow("cert_serial");
                        if (!query.isNull(columnIndexOrThrow)) {
                            l = Long.valueOf(query.getLong(columnIndexOrThrow));
                        }
                    }
                }
                query.close();
                long j = parse.serial;
                if (l != null
                        && l.longValue() >= j
                        && !"TEST_ONLY_INSECURE_CERTIFICATE_ALIAS"
                                .equals(defaultCertificateAliasIfEmpty)) {
                    if (l.longValue() == j) {
                        Log.i(
                                "RecoverableKeyStoreMgr",
                                "The cert file serial number is the same, so skip updating.");
                        return;
                    } else {
                        Log.e(
                                "RecoverableKeyStoreMgr",
                                "The cert file serial number is older than the one in database.");
                        throw new ServiceSpecificException(
                                29,
                                "The cert file serial number is older than the one in database.");
                    }
                }
                Log.i(
                        "RecoverableKeyStoreMgr",
                        "Updating the certificate with the new serial number " + j);
                X509Certificate rootCertificate =
                        TestOnlyInsecureCertificateHelper.getRootCertificate(
                                defaultCertificateAliasIfEmpty);
                Date validationDate =
                        TestOnlyInsecureCertificateHelper.getValidationDate(
                                defaultCertificateAliasIfEmpty);
                try {
                    Log.d(
                            "RecoverableKeyStoreMgr",
                            "Getting and validating a random endpoint certificate");
                    CertPath endpointCert =
                            parse.getEndpointCert(
                                    new SecureRandom().nextInt(parse.endpointCerts.size()),
                                    validationDate,
                                    rootCertificate);
                    try {
                        Log.d(
                                "RecoverableKeyStoreMgr",
                                "Saving the randomly chosen endpoint certificate to database");
                        long recoveryServiceCertPath =
                                recoverableKeyStoreDb.setRecoveryServiceCertPath(
                                        callingUserId,
                                        callingUid,
                                        defaultCertificateAliasIfEmpty,
                                        endpointCert);
                        if (recoveryServiceCertPath <= 0) {
                            if (recoveryServiceCertPath < 0) {
                                throw new ServiceSpecificException(
                                        22, "Failed to set the certificate path in the local DB.");
                            }
                        } else {
                            if (this.mDatabase.setRecoveryServiceCertSerial(
                                            callingUserId,
                                            callingUid,
                                            j,
                                            defaultCertificateAliasIfEmpty)
                                    < 0) {
                                throw new ServiceSpecificException(
                                        22,
                                        "Failed to set the certificate serial number in the local"
                                            + " DB.");
                            }
                            if (recoverableKeyStoreDb.getLong(
                                            callingUserId, callingUid, "snapshot_version")
                                    != null) {
                                recoverableKeyStoreDb.setShouldCreateSnapshot(
                                        callingUserId, callingUid);
                                Log.i(
                                        "RecoverableKeyStoreMgr",
                                        "This is a certificate change. Snapshot must be updated");
                            } else {
                                Log.i(
                                        "RecoverableKeyStoreMgr",
                                        "This is a certificate change. Snapshot didn't exist");
                            }
                            if (this.mDatabase.setLong(
                                            callingUserId,
                                            callingUid,
                                            new SecureRandom().nextLong(),
                                            "counter_id")
                                    < 0) {
                                Log.e(
                                        "RecoverableKeyStoreMgr",
                                        "Failed to set the counter id in the local DB.");
                            }
                        }
                    } catch (CertificateEncodingException e) {
                        Log.e("RecoverableKeyStoreMgr", "Failed to encode CertPath", e);
                        throw new ServiceSpecificException(25, e.getMessage());
                    }
                } catch (CertValidationException e2) {
                    Log.e("RecoverableKeyStoreMgr", "Invalid endpoint cert", e2);
                    throw new ServiceSpecificException(28, e2.getMessage());
                }
            } finally {
            }
        } catch (CertParsingException e3) {
            Log.d(
                    "RecoverableKeyStoreMgr",
                    "Failed to parse the input as a cert file: " + HexDump.toHexString(bArr));
            throw new ServiceSpecificException(25, e3.getMessage());
        }
    }

    public byte[] startRecoverySession(
            String str, byte[] bArr, byte[] bArr2, byte[] bArr3, List list) throws RemoteException {
        checkRecoverKeyStorePermission();
        int callingUid = Binder.getCallingUid();
        if (list.size() != 1) {
            throw new UnsupportedOperationException(
                    "Only a single KeyChainProtectionParams is supported");
        }
        try {
            byte[] bArr4 = KeySyncUtils.THM_ENCRYPTED_RECOVERY_KEY_HEADER;
            try {
                PublicKey generatePublic =
                        KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(bArr));
                if (!Arrays.equals(
                        SecureBox.encodePublicKey(generatePublic), Arrays.copyOf(bArr2, 65))) {
                    throw new ServiceSpecificException(
                            28,
                            "The public keys given in verifierPublicKey and vaultParams do not"
                                + " match.");
                }
                byte[] bArr5 = new byte[16];
                new SecureRandom().nextBytes(bArr5);
                byte[] secret = ((KeyChainProtectionParams) list.get(0)).getSecret();
                RecoverySessionStorage.Entry entry =
                        new RecoverySessionStorage.Entry(str, secret, bArr5, bArr2);
                RecoverySessionStorage recoverySessionStorage = this.mRecoverySessionStorage;
                if (recoverySessionStorage.mSessionsByUid.get(callingUid) == null) {
                    recoverySessionStorage.mSessionsByUid.put(callingUid, new ArrayList());
                }
                ((ArrayList) recoverySessionStorage.mSessionsByUid.get(callingUid)).add(entry);
                Log.i(
                        "RecoverableKeyStoreMgr",
                        "Received VaultParams for recovery: " + HexDump.toHexString(bArr2));
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    messageDigest.update(KeySyncUtils.THM_KF_HASH_PREFIX);
                    messageDigest.update(secret);
                    return SecureBox.encrypt(
                            generatePublic,
                            null,
                            ArrayUtils.concat(
                                    new byte[][] {
                                        KeySyncUtils.RECOVERY_CLAIM_HEADER, bArr2, bArr3
                                    }),
                            ArrayUtils.concat(new byte[][] {messageDigest.digest(), bArr5}));
                } catch (InvalidKeyException e) {
                    throw new ServiceSpecificException(25, e.getMessage());
                } catch (NoSuchAlgorithmException e2) {
                    Log.wtf(
                            "RecoverableKeyStoreMgr",
                            "SecureBox algorithm missing. AOSP must support this.",
                            e2);
                    throw new ServiceSpecificException(22, e2.getMessage());
                }
            } catch (NoSuchAlgorithmException e3) {
                throw new RuntimeException(e3);
            }
        } catch (InvalidKeySpecException e4) {
            throw new ServiceSpecificException(25, e4.getMessage());
        }
    }
}
