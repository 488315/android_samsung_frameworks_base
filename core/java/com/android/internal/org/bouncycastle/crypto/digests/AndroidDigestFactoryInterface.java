package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.crypto.Digest;

interface AndroidDigestFactoryInterface {
    Digest getMD5();

    Digest getSHA1();

    Digest getSHA224();

    Digest getSHA256();

    Digest getSHA384();

    Digest getSHA512();
}
