package com.android.server.locksettings.recoverablekeystore;

import javax.crypto.SecretKey;

public final class PlatformDecryptionKey {
    public final int mGenerationId;
    public final SecretKey mKey;

    public PlatformDecryptionKey(int i, SecretKey secretKey) {
        this.mGenerationId = i;
        this.mKey = secretKey;
    }
}
