package com.android.internal.org.bouncycastle.util.io.pem;

public interface PemObjectGenerator {
    PemObject generate() throws PemGenerationException;
}
