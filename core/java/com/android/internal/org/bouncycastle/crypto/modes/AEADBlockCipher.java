package com.android.internal.org.bouncycastle.crypto.modes;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;

/* loaded from: classes5.dex */
public interface AEADBlockCipher extends AEADCipher {
  BlockCipher getUnderlyingCipher();
}
