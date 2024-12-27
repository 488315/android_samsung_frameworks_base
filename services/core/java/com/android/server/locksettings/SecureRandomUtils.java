package com.android.server.locksettings;

import java.security.SecureRandom;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class SecureRandomUtils {
    public static final SecureRandom RNG = new SecureRandom();

    public static byte[] randomBytes(int i) {
        byte[] bArr = new byte[i];
        RNG.nextBytes(bArr);
        return bArr;
    }
}
