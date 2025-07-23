package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa;

import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest;
import com.android.internal.org.bouncycastle.crypto.generators.DSAParametersGenerator;
import com.android.internal.org.bouncycastle.crypto.params.DSAParameterGenerationParameters;
import com.android.internal.org.bouncycastle.crypto.params.DSAParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;

/* loaded from: classes5.dex */
public class AlgorithmParameterGeneratorSpi extends BaseAlgorithmParameterGeneratorSpi {
    protected DSAParameterGenerationParameters params;
    protected SecureRandom random;
    protected int strength = 1024;

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(int i, SecureRandom secureRandom) {
        if (i < 512 || i > 3072) {
            throw new InvalidParameterException("strength must be from 512 - 3072");
        }
        if (i <= 1024 && i % 64 != 0) {
            throw new InvalidParameterException("strength must be a multiple of 64 below 1024 bits.");
        }
        if (i > 1024 && i % 1024 != 0) {
            throw new InvalidParameterException("strength must be a multiple of 1024 above 1024 bits.");
        }
        this.strength = i;
        this.random = secureRandom;
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DSA parameter generation.");
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected AlgorithmParameters engineGenerateParameters() {
        DSAParametersGenerator dSAParametersGenerator;
        if (this.strength <= 1024) {
            dSAParametersGenerator = new DSAParametersGenerator();
        } else {
            dSAParametersGenerator = new DSAParametersGenerator(SHA256Digest.newInstance());
        }
        if (this.random == null) {
            this.random = CryptoServicesRegistrar.getSecureRandom();
        }
        int defaultCertainty = PrimeCertaintyCalculator.getDefaultCertainty(this.strength);
        int i = this.strength;
        if (i == 1024) {
            DSAParameterGenerationParameters dSAParameterGenerationParameters = new DSAParameterGenerationParameters(1024, 160, defaultCertainty, this.random);
            this.params = dSAParameterGenerationParameters;
            dSAParametersGenerator.init(dSAParameterGenerationParameters);
        } else if (i > 1024) {
            DSAParameterGenerationParameters dSAParameterGenerationParameters2 = new DSAParameterGenerationParameters(this.strength, 256, defaultCertainty, this.random);
            this.params = dSAParameterGenerationParameters2;
            dSAParametersGenerator.init(dSAParameterGenerationParameters2);
        } else {
            dSAParametersGenerator.init(i, defaultCertainty, this.random);
        }
        DSAParameters generateParameters = dSAParametersGenerator.generateParameters();
        try {
            AlgorithmParameters createParametersInstance = createParametersInstance("DSA");
            createParametersInstance.init(new DSAParameterSpec(generateParameters.getP(), generateParameters.getQ(), generateParameters.getG()));
            return createParametersInstance;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
