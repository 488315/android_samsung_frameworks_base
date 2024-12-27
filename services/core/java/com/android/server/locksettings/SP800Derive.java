package com.android.server.locksettings;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class SP800Derive {
    public final byte[] mKeyBytes;

    public SP800Derive(byte[] bArr) {
        this.mKeyBytes = bArr;
    }

    public final byte[] withContext(byte[] bArr, byte[] bArr2) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(this.mKeyBytes, mac.getAlgorithm()));
            mac.update(ByteBuffer.allocate(4).putInt(1).array());
            mac.update(bArr);
            mac.update((byte) 0);
            mac.update(bArr2);
            mac.update(ByteBuffer.allocate(4).putInt(bArr2.length * 8).array());
            mac.update(ByteBuffer.allocate(4).putInt(256).array());
            return mac.doFinal();
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
