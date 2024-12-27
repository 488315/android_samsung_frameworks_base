package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;

public interface BlockCipherProvider {
    BlockCipher get();
}
