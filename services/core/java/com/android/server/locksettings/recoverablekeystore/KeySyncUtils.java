package com.android.server.locksettings.recoverablekeystore;

import android.util.Pair;

import com.android.internal.util.ArrayUtils;
import com.android.security.SecureBox;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

public abstract class KeySyncUtils {
    public static final byte[] ENCRYPTED_APPLICATION_KEY_HEADER;
    public static final byte[] LOCALLY_ENCRYPTED_RECOVERY_KEY_HEADER;
    public static final byte[] RECOVERY_CLAIM_HEADER;
    public static final byte[] RECOVERY_RESPONSE_HEADER;
    public static final byte[] THM_ENCRYPTED_RECOVERY_KEY_HEADER;
    public static final byte[] THM_KF_HASH_PREFIX;

    static {
        Charset charset = StandardCharsets.UTF_8;
        THM_ENCRYPTED_RECOVERY_KEY_HEADER = "V1 THM_encrypted_recovery_key".getBytes(charset);
        LOCALLY_ENCRYPTED_RECOVERY_KEY_HEADER =
                "V1 locally_encrypted_recovery_key".getBytes(charset);
        ENCRYPTED_APPLICATION_KEY_HEADER = "V1 encrypted_application_key".getBytes(charset);
        RECOVERY_CLAIM_HEADER = "V1 KF_claim".getBytes(charset);
        RECOVERY_RESPONSE_HEADER = "V1 reencrypted_recovery_key".getBytes(charset);
        THM_KF_HASH_PREFIX = "THM_KF_hash".getBytes(charset);
    }

    public static Map encryptKeysWithRecoveryKey(SecretKey secretKey, Map map) {
        HashMap hashMap = new HashMap();
        for (String str : map.keySet()) {
            SecretKey secretKey2 = (SecretKey) ((Pair) map.get(str)).first;
            byte[] bArr = (byte[]) ((Pair) map.get(str)).second;
            byte[] bArr2 = ENCRYPTED_APPLICATION_KEY_HEADER;
            if (bArr != null) {
                bArr2 = ArrayUtils.concat(new byte[][] {bArr2, bArr});
            }
            hashMap.put(
                    str,
                    SecureBox.encrypt(
                            null, secretKey.getEncoded(), bArr2, secretKey2.getEncoded()));
        }
        return hashMap;
    }

    public static byte[] locallyEncryptRecoveryKey(byte[] bArr, SecretKey secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException {
        return SecureBox.encrypt(
                null, bArr, LOCALLY_ENCRYPTED_RECOVERY_KEY_HEADER, secretKey.getEncoded());
    }
}
