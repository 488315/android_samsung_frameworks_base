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
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.ArrayList;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
abstract class KeyStoreCryptoOperationUtils {
    private static volatile SecureRandom sRng;

    private KeyStoreCryptoOperationUtils() {
    }

    public static boolean canUserAuthorizationSucceed(AndroidKeyStoreKey androidKeyStoreKey) {
        ArrayList arrayList = new ArrayList();
        for (Authorization authorization : androidKeyStoreKey.getAuthorizations()) {
            if (authorization.keyParameter.tag == -1610612234) {
                arrayList.add(Long.valueOf(authorization.keyParameter.value.getLongInteger()));
            }
        }
        if (arrayList.isEmpty()) {
            return false;
        }
        long secureUserId = GateKeeper.getSecureUserId();
        if (secureUserId != 0 && arrayList.contains(Long.valueOf(secureUserId))) {
            return true;
        }
        long[] authenticatorIds = ((BiometricManager) ActivityThread.currentApplication().getSystemService(BiometricManager.class)).getAuthenticatorIds();
        boolean z = authenticatorIds.length > 0;
        int length = authenticatorIds.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if (!arrayList.contains(Long.valueOf(authenticatorIds[i]))) {
                z = false;
                break;
            }
            i++;
        }
        return z;
    }

    public static InvalidKeyException getInvalidKeyException(AndroidKeyStoreKey androidKeyStoreKey, KeyStoreException keyStoreException) {
        int errorCode = keyStoreException.getErrorCode();
        if (errorCode != 2 && errorCode != 3) {
            if (errorCode != 7 && errorCode != 17) {
                switch (errorCode) {
                    case -26:
                        break;
                    case -25:
                        return new KeyExpiredException();
                    case -24:
                        return new KeyNotYetValidException();
                    default:
                        return new InvalidKeyException("Keystore operation failed", keyStoreException);
                }
            } else {
                return new KeyPermanentlyInvalidatedException();
            }
        }
        return new UserNotAuthenticatedException();
    }

    public static GeneralSecurityException getExceptionForCipherInit(AndroidKeyStoreKey androidKeyStoreKey, KeyStoreException keyStoreException) {
        int errorCode = keyStoreException.getErrorCode();
        if (errorCode == -55) {
            return new InvalidAlgorithmParameterException("Caller-provided IV not permitted");
        }
        if (errorCode == -52) {
            return new InvalidAlgorithmParameterException("Invalid IV");
        }
        return getInvalidKeyException(androidKeyStoreKey, keyStoreException);
    }

    static byte[] getRandomBytesToMixIntoKeystoreRng(SecureRandom secureRandom, int i) {
        if (i <= 0) {
            return EmptyArray.BYTE;
        }
        if (secureRandom == null) {
            secureRandom = getRng();
        }
        byte[] bArr = new byte[i];
        secureRandom.nextBytes(bArr);
        return bArr;
    }

    private static SecureRandom getRng() {
        if (sRng == null) {
            sRng = new SecureRandom();
        }
        return sRng;
    }

    static void abortOperation(KeyStoreOperation keyStoreOperation) {
        if (keyStoreOperation != null) {
            try {
                keyStoreOperation.abort();
            } catch (KeyStoreException e) {
                if (e.getErrorCode() != -28) {
                    Log.w("KeyStoreCryptoOperationUtils", "Encountered error trying to abort a keystore operation.", e);
                }
            }
        }
    }

    static long getOrMakeOperationChallenge(KeyStoreOperation keyStoreOperation, AndroidKeyStoreKey androidKeyStoreKey) throws KeyPermanentlyInvalidatedException {
        if (keyStoreOperation.getChallenge() != null) {
            if (!canUserAuthorizationSucceed(androidKeyStoreKey)) {
                throw new KeyPermanentlyInvalidatedException();
            }
            return keyStoreOperation.getChallenge().longValue();
        }
        return getRng().nextLong();
    }
}
