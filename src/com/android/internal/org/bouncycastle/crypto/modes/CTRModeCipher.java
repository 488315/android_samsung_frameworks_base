package com.android.internal.org.bouncycastle.crypto.modes;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.MultiBlockCipher;
import com.android.internal.org.bouncycastle.crypto.SkippingStreamCipher;

/* loaded from: classes5.dex */
public interface CTRModeCipher extends MultiBlockCipher, SkippingStreamCipher {
    BlockCipher getUnderlyingCipher();
}
