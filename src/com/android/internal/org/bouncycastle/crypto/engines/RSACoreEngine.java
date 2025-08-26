package com.android.internal.org.bouncycastle.crypto.engines;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServicePurpose;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.constraints.ConstraintUtils;
import com.android.internal.org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

/* loaded from: classes5.dex */
class RSACoreEngine {
    private boolean forEncryption;
    private RSAKeyParameters key;

    RSACoreEngine() {
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithRandom) {
            this.key = (RSAKeyParameters) ((ParametersWithRandom) cipherParameters).getParameters();
        } else {
            this.key = (RSAKeyParameters) cipherParameters;
        }
        this.forEncryption = z;
        int iBitsOfSecurityFor = ConstraintUtils.bitsOfSecurityFor(this.key.getModulus());
        RSAKeyParameters rSAKeyParameters = this.key;
        CryptoServicesRegistrar.checkConstraints(new DefaultServiceProperties("RSA", iBitsOfSecurityFor, rSAKeyParameters, getPurpose(rSAKeyParameters.isPrivate(), z)));
    }

    public int getInputBlockSize() {
        int iBitLength = this.key.getModulus().bitLength();
        if (this.forEncryption) {
            return ((iBitLength + 7) / 8) - 1;
        }
        return (iBitLength + 7) / 8;
    }

    public int getOutputBlockSize() {
        int iBitLength = this.key.getModulus().bitLength();
        if (this.forEncryption) {
            return (iBitLength + 7) / 8;
        }
        return ((iBitLength + 7) / 8) - 1;
    }

    public BigInteger convertInput(byte[] bArr, int i, int i2) {
        if (i2 > getInputBlockSize() + 1) {
            throw new DataLengthException("input too large for RSA cipher.");
        }
        if (i2 == getInputBlockSize() + 1 && !this.forEncryption) {
            throw new DataLengthException("input too large for RSA cipher.");
        }
        if (i != 0 || i2 != bArr.length) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            bArr = bArr2;
        }
        BigInteger bigInteger = new BigInteger(1, bArr);
        if (bigInteger.compareTo(this.key.getModulus()) < 0) {
            return bigInteger;
        }
        throw new DataLengthException("input too large for RSA cipher.");
    }

    public byte[] convertOutput(BigInteger bigInteger) {
        byte[] bArr;
        byte[] byteArray = bigInteger.toByteArray();
        if (this.forEncryption) {
            if (byteArray[0] == 0 && byteArray.length > getOutputBlockSize()) {
                int length = byteArray.length - 1;
                byte[] bArr2 = new byte[length];
                System.arraycopy(byteArray, 1, bArr2, 0, length);
                return bArr2;
            }
            if (byteArray.length >= getOutputBlockSize()) {
                return byteArray;
            }
            int outputBlockSize = getOutputBlockSize();
            byte[] bArr3 = new byte[outputBlockSize];
            System.arraycopy(byteArray, 0, bArr3, outputBlockSize - byteArray.length, byteArray.length);
            return bArr3;
        }
        if (byteArray[0] == 0) {
            int length2 = byteArray.length - 1;
            bArr = new byte[length2];
            System.arraycopy(byteArray, 1, bArr, 0, length2);
        } else {
            int length3 = byteArray.length;
            bArr = new byte[length3];
            System.arraycopy(byteArray, 0, bArr, 0, length3);
        }
        Arrays.fill(byteArray, (byte) 0);
        return bArr;
    }

    public BigInteger processBlock(BigInteger bigInteger) {
        RSAPrivateCrtKeyParameters rSAPrivateCrtKeyParameters;
        BigInteger publicExponent;
        RSAKeyParameters rSAKeyParameters = this.key;
        if ((rSAKeyParameters instanceof RSAPrivateCrtKeyParameters) && (publicExponent = (rSAPrivateCrtKeyParameters = (RSAPrivateCrtKeyParameters) rSAKeyParameters).getPublicExponent()) != null) {
            BigInteger p = rSAPrivateCrtKeyParameters.getP();
            BigInteger q = rSAPrivateCrtKeyParameters.getQ();
            BigInteger dp = rSAPrivateCrtKeyParameters.getDP();
            BigInteger dq = rSAPrivateCrtKeyParameters.getDQ();
            BigInteger qInv = rSAPrivateCrtKeyParameters.getQInv();
            BigInteger bigIntegerModPow = bigInteger.remainder(p).modPow(dp, p);
            BigInteger bigIntegerModPow2 = bigInteger.remainder(q).modPow(dq, q);
            BigInteger bigIntegerAdd = bigIntegerModPow.subtract(bigIntegerModPow2).multiply(qInv).mod(p).multiply(q).add(bigIntegerModPow2);
            if (bigIntegerAdd.modPow(publicExponent, rSAPrivateCrtKeyParameters.getModulus()).equals(bigInteger)) {
                return bigIntegerAdd;
            }
            throw new IllegalStateException("RSA engine faulty decryption/signing detected");
        }
        return bigInteger.modPow(this.key.getExponent(), this.key.getModulus());
    }

    private CryptoServicePurpose getPurpose(boolean z, boolean z2) {
        boolean z3 = z && z2;
        boolean z4 = !z && z2;
        boolean z5 = (z || z2) ? false : true;
        if (z3) {
            return CryptoServicePurpose.SIGNING;
        }
        if (z4) {
            return CryptoServicePurpose.ENCRYPTION;
        }
        if (z5) {
            return CryptoServicePurpose.VERIFYING;
        }
        return CryptoServicePurpose.DECRYPTION;
    }
}
