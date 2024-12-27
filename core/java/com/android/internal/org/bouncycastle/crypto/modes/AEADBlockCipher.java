package com.android.internal.org.bouncycastle.crypto.modes;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;

public interface AEADBlockCipher extends AEADCipher {
    BlockCipher getUnderlyingCipher();
}
