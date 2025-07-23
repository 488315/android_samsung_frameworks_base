package com.android.internal.org.bouncycastle.crypto;

import javax.security.auth.Destroyable;

/* loaded from: classes5.dex */
public interface SecretWithEncapsulation extends Destroyable {
    byte[] getEncapsulation();

    byte[] getSecret();
}
