package com.android.internal.org.bouncycastle.crypto;

import com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/* loaded from: classes5.dex */
public interface EncapsulatedSecretGenerator {
    SecretWithEncapsulation generateEncapsulated(AsymmetricKeyParameter asymmetricKeyParameter);
}
