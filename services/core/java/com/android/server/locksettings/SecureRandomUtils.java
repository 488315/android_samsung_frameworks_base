package com.android.server.locksettings;

import java.security.SecureRandom;

public abstract class SecureRandomUtils {
    public static final SecureRandom RNG = new SecureRandom();

    public static byte[] randomBytes(int i) {
        byte[] bArr = new byte[i];
        RNG.nextBytes(bArr);
        return bArr;
    }
}
