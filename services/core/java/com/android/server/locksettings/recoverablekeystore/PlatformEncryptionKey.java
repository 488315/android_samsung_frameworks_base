package com.android.server.locksettings.recoverablekeystore;

import javax.crypto.SecretKey;

public final class PlatformEncryptionKey {
    public final int mGenerationId;
    public final SecretKey mKey;

    public PlatformEncryptionKey(int i, SecretKey secretKey) {
        this.mGenerationId = i;
        this.mKey = secretKey;
    }
}
