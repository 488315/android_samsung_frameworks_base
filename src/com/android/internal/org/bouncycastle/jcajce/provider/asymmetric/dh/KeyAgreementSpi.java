package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.internal.org.bouncycastle.crypto.BasicAgreement;
import com.android.internal.org.bouncycastle.crypto.DerivationFunction;
import com.android.internal.org.bouncycastle.crypto.params.DHParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import com.android.internal.org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes5.dex */
public class KeyAgreementSpi extends BaseAgreementSpi {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private BigInteger g;
    private final BasicAgreement mqvAgreement;
    private BigInteger p;
    private byte[] result;
    private BigInteger x;

    public KeyAgreementSpi() {
        this("Diffie-Hellman", null);
    }

    public KeyAgreementSpi(String str, DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.mqvAgreement = null;
    }

    public KeyAgreementSpi(String str, BasicAgreement basicAgreement, DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.mqvAgreement = basicAgreement;
    }

    protected byte[] bigIntToBytes(BigInteger bigInteger) {
        return BigIntegers.asUnsignedByteArray((this.p.bitLength() + 7) / 8, bigInteger);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected Key engineDoPhase(Key key, boolean z) throws InvalidKeyException, IllegalStateException {
        if (this.x == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        }
        if (!(key instanceof DHPublicKey)) {
            throw new InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
        }
        DHPublicKey dHPublicKey = (DHPublicKey) key;
        if (!dHPublicKey.getParams().getG().equals(this.g) || !dHPublicKey.getParams().getP().equals(this.p)) {
            throw new InvalidKeyException("DHPublicKey not for this KeyAgreement!");
        }
        BigInteger y = dHPublicKey.getY();
        if (y != null && y.compareTo(TWO) >= 0) {
            BigInteger bigInteger = this.p;
            BigInteger bigInteger2 = ONE;
            if (y.compareTo(bigInteger.subtract(bigInteger2)) < 0) {
                BigInteger modPow = y.modPow(this.x, this.p);
                if (modPow.compareTo(bigInteger2) == 0) {
                    throw new InvalidKeyException("Shared key can't be 1");
                }
                this.result = bigIntToBytes(modPow);
                if (z) {
                    return null;
                }
                return new BCDHPublicKey(modPow, dHPublicKey.getParams());
            }
        }
        throw new InvalidKeyException("Invalid DH PublicKey");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi, javax.crypto.KeyAgreementSpi
    protected byte[] engineGenerateSecret() throws IllegalStateException {
        if (this.x == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        }
        return super.engineGenerateSecret();
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi, javax.crypto.KeyAgreementSpi
    protected int engineGenerateSecret(byte[] bArr, int i) throws IllegalStateException, ShortBufferException {
        if (this.x == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        }
        return super.engineGenerateSecret(bArr, i);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi, javax.crypto.KeyAgreementSpi
    protected SecretKey engineGenerateSecret(String str) throws NoSuchAlgorithmException {
        if (this.x == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        }
        if (str.equals("TlsPremasterSecret")) {
            return new SecretKeySpec(trimZeroes(this.result), str);
        }
        return super.engineGenerateSecret(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi
    protected void doInitFromKey(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!(key instanceof DHPrivateKey)) {
            throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
        }
        DHPrivateKey dHPrivateKey = (DHPrivateKey) key;
        if (algorithmParameterSpec != null) {
            if (algorithmParameterSpec instanceof DHParameterSpec) {
                DHParameterSpec dHParameterSpec = (DHParameterSpec) algorithmParameterSpec;
                this.p = dHParameterSpec.getP();
                this.g = dHParameterSpec.getG();
                this.ukmParameters = null;
            } else if (algorithmParameterSpec instanceof UserKeyingMaterialSpec) {
                if (this.kdf == null) {
                    throw new InvalidAlgorithmParameterException("no KDF specified for UserKeyingMaterialSpec");
                }
                this.p = dHPrivateKey.getParams().getP();
                this.g = dHPrivateKey.getParams().getG();
                this.ukmParameters = ((UserKeyingMaterialSpec) algorithmParameterSpec).getUserKeyingMaterial();
            } else {
                throw new InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
            }
        } else {
            this.p = dHPrivateKey.getParams().getP();
            this.g = dHPrivateKey.getParams().getG();
        }
        BigInteger x = dHPrivateKey.getX();
        this.x = x;
        this.result = bigIntToBytes(x);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi, javax.crypto.KeyAgreementSpi
    protected void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        if (!(key instanceof DHPrivateKey)) {
            throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
        }
        DHPrivateKey dHPrivateKey = (DHPrivateKey) key;
        this.p = dHPrivateKey.getParams().getP();
        this.g = dHPrivateKey.getParams().getG();
        BigInteger x = dHPrivateKey.getX();
        this.x = x;
        this.result = bigIntToBytes(x);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi
    protected byte[] doCalcSecret() {
        return this.result;
    }

    private DHPrivateKeyParameters generatePrivateKeyParameter(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey instanceof DHPrivateKey) {
            if (privateKey instanceof BCDHPrivateKey) {
                return ((BCDHPrivateKey) privateKey).engineGetKeyParameters();
            }
            DHPrivateKey dHPrivateKey = (DHPrivateKey) privateKey;
            DHParameterSpec params = dHPrivateKey.getParams();
            return new DHPrivateKeyParameters(dHPrivateKey.getX(), new DHParameters(params.getP(), params.getG(), null, params.getL()));
        }
        throw new InvalidKeyException("private key not a DHPrivateKey");
    }

    private DHPublicKeyParameters generatePublicKeyParameter(PublicKey publicKey) throws InvalidKeyException {
        if (publicKey instanceof DHPublicKey) {
            if (publicKey instanceof BCDHPublicKey) {
                return ((BCDHPublicKey) publicKey).engineGetKeyParameters();
            }
            DHPublicKey dHPublicKey = (DHPublicKey) publicKey;
            DHParameterSpec params = dHPublicKey.getParams();
            if (params instanceof DHDomainParameterSpec) {
                return new DHPublicKeyParameters(dHPublicKey.getY(), ((DHDomainParameterSpec) params).getDomainParameters());
            }
            return new DHPublicKeyParameters(dHPublicKey.getY(), new DHParameters(params.getP(), params.getG(), null, params.getL()));
        }
        throw new InvalidKeyException("public key not a DHPublicKey");
    }
}
