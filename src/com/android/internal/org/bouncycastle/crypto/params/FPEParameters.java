package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.util.RadixConverter;
import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public final class FPEParameters implements CipherParameters {
    private final KeyParameter key;
    private final RadixConverter radixConverter;
    private final byte[] tweak;
    private final boolean useInverse;

    public FPEParameters(KeyParameter keyParameter, int i, byte[] bArr) {
        this(keyParameter, i, bArr, false);
    }

    public FPEParameters(KeyParameter keyParameter, int i, byte[] bArr, boolean z) {
        this(keyParameter, new RadixConverter(i), bArr, z);
    }

    public FPEParameters(KeyParameter keyParameter, RadixConverter radixConverter, byte[] bArr, boolean z) {
        this.key = keyParameter;
        this.radixConverter = radixConverter;
        this.tweak = Arrays.clone(bArr);
        this.useInverse = z;
    }

    public KeyParameter getKey() {
        return this.key;
    }

    public int getRadix() {
        return this.radixConverter.getRadix();
    }

    public RadixConverter getRadixConverter() {
        return this.radixConverter;
    }

    public byte[] getTweak() {
        return Arrays.clone(this.tweak);
    }

    public boolean isUsingInverseFunction() {
        return this.useInverse;
    }
}
