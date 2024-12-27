package com.android.internal.org.bouncycastle.jce.interfaces;

import com.android.internal.org.bouncycastle.math.ec.ECPoint;

import java.security.PublicKey;

/* loaded from: classes5.dex */
public interface ECPublicKey extends ECKey, PublicKey {
    ECPoint getQ();
}
