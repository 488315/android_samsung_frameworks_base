package com.android.internal.org.bouncycastle.crypto.modes;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.MultiBlockCipher;

/* loaded from: classes5.dex */
public interface CBCModeCipher extends MultiBlockCipher {
    BlockCipher getUnderlyingCipher();
}
