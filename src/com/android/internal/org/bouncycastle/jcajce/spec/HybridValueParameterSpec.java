package com.android.internal.org.bouncycastle.jcajce.spec;

import com.android.internal.org.bouncycastle.util.Arrays;
import java.security.spec.AlgorithmParameterSpec;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.security.auth.Destroyable;

/* loaded from: classes5.dex */
public class HybridValueParameterSpec implements AlgorithmParameterSpec, Destroyable {
    private volatile AlgorithmParameterSpec baseSpec;
    private final AtomicBoolean hasBeenDestroyed = new AtomicBoolean(false);
    private volatile byte[] t;

    public HybridValueParameterSpec(byte[] bArr, AlgorithmParameterSpec algorithmParameterSpec) {
        this.t = bArr;
        this.baseSpec = algorithmParameterSpec;
    }

    public byte[] getT() {
        byte[] bArr = this.t;
        checkDestroyed();
        return bArr;
    }

    public AlgorithmParameterSpec getBaseParameterSpec() {
        AlgorithmParameterSpec algorithmParameterSpec = this.baseSpec;
        checkDestroyed();
        return algorithmParameterSpec;
    }

    @Override // javax.security.auth.Destroyable
    public boolean isDestroyed() {
        return this.hasBeenDestroyed.get();
    }

    @Override // javax.security.auth.Destroyable
    public void destroy() {
        if (this.hasBeenDestroyed.getAndSet(true)) {
            return;
        }
        Arrays.clear(this.t);
        this.t = null;
        this.baseSpec = null;
    }

    private void checkDestroyed() {
        if (isDestroyed()) {
            throw new IllegalStateException("spec has been destroyed");
        }
    }
}
