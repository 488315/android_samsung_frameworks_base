package android.security.keystore2;

import android.app.ActivityThread;
import android.hardware.biometrics.BiometricManager;
import android.security.GateKeeper;
import android.security.KeyStoreException;
import android.security.KeyStoreOperation;
import android.security.keystore.KeyExpiredException;
import android.security.keystore.KeyNotYetValidException;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.UserNotAuthenticatedException;
import android.system.keystore2.Authorization;
import android.util.Log;

import libcore.util.EmptyArray;

import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
abstract class KeyStoreCryptoOperationUtils {
    private static volatile SecureRandom sRng;

    private KeyStoreCryptoOperationUtils() {}

    public static boolean canUserAuthorizationSucceed(AndroidKeyStoreKey key) {
        List<Long> keySids = new ArrayList<>();
        for (Authorization p : key.getAuthorizations()) {
            switch (p.keyParameter.tag) {
                case -1610612234:
                    keySids.add(Long.valueOf(p.keyParameter.value.getLongInteger()));
                    break;
            }
        }
        if (keySids.isEmpty()) {
            return false;
        }
        long rootSid = GateKeeper.getSecureUserId();
        if (rootSid != 0 && keySids.contains(Long.valueOf(rootSid))) {
            return true;
        }
        long[] biometricSids =
                ((BiometricManager)
                                ActivityThread.currentApplication()
                                        .getSystemService(BiometricManager.class))
                        .getAuthenticatorIds();
        boolean canUnlockViaBiometrics = biometricSids.length > 0;
        int length = biometricSids.length;
        int i = 0;
        while (true) {
            if (i < length) {
                long sid = biometricSids[i];
                if (keySids.contains(Long.valueOf(sid))) {
                    i++;
                } else {
                    canUnlockViaBiometrics = false;
                }
            }
        }
        return canUnlockViaBiometrics;
    }

    public static InvalidKeyException getInvalidKeyException(
            AndroidKeyStoreKey key, KeyStoreException e) {
        switch (e.getErrorCode()) {
            case -26:
            case 2:
            case 3:
                return new UserNotAuthenticatedException();
            case -25:
                return new KeyExpiredException();
            case -24:
                return new KeyNotYetValidException();
            case 7:
            case 17:
                return new KeyPermanentlyInvalidatedException();
            default:
                return new InvalidKeyException("Keystore operation failed", e);
        }
    }

    public static GeneralSecurityException getExceptionForCipherInit(
            AndroidKeyStoreKey key, KeyStoreException e) {
        switch (e.getErrorCode()) {
            case -55:
                return new InvalidAlgorithmParameterException("Caller-provided IV not permitted");
            case -52:
                return new InvalidAlgorithmParameterException("Invalid IV");
            default:
                return getInvalidKeyException(key, e);
        }
    }

    static byte[] getRandomBytesToMixIntoKeystoreRng(SecureRandom rng, int sizeBytes) {
        if (sizeBytes <= 0) {
            return EmptyArray.BYTE;
        }
        if (rng == null) {
            rng = getRng();
        }
        byte[] result = new byte[sizeBytes];
        rng.nextBytes(result);
        return result;
    }

    private static SecureRandom getRng() {
        if (sRng == null) {
            sRng = new SecureRandom();
        }
        return sRng;
    }

    static void abortOperation(KeyStoreOperation operation) {
        if (operation != null) {
            try {
                operation.abort();
            } catch (KeyStoreException e) {
                if (e.getErrorCode() != -28) {
                    Log.w(
                            "KeyStoreCryptoOperationUtils",
                            "Encountered error trying to abort a keystore operation.",
                            e);
                }
            }
        }
    }

    static long getOrMakeOperationChallenge(KeyStoreOperation operation, AndroidKeyStoreKey key)
            throws KeyPermanentlyInvalidatedException {
        if (operation.getChallenge() != null) {
            if (!canUserAuthorizationSucceed(key)) {
                throw new KeyPermanentlyInvalidatedException();
            }
            return operation.getChallenge().longValue();
        }
        return getRng().nextLong();
    }
}
