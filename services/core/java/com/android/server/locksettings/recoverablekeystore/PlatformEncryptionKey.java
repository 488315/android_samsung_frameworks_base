package com.android.server.locksettings.recoverablekeystore;

import javax.crypto.SecretKey;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PlatformEncryptionKey {
    public final int mGenerationId;
    public final SecretKey mKey;

    public PlatformEncryptionKey(int i, SecretKey secretKey) {
        this.mGenerationId = i;
        this.mKey = secretKey;
    }
}
