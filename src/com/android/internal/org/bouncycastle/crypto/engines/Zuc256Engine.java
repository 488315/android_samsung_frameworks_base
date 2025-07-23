package com.android.internal.org.bouncycastle.crypto.engines;

import com.android.internal.org.bouncycastle.util.Memoable;

/* loaded from: classes5.dex */
public final class Zuc256Engine extends Zuc256CoreEngine {
    public Zuc256Engine() {
    }

    public Zuc256Engine(int i) {
        super(i);
    }

    private Zuc256Engine(Zuc256Engine zuc256Engine) {
        super(zuc256Engine);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.engines.Zuc256CoreEngine, com.android.internal.org.bouncycastle.crypto.engines.Zuc128CoreEngine, com.android.internal.org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new Zuc256Engine(this);
    }
}
