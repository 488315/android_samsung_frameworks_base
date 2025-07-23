package com.android.internal.org.bouncycastle.crypto.signers;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.DSAExt;
import com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.internal.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.internal.org.bouncycastle.math.ec.ECConstants;
import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECFieldElement;
import com.android.internal.org.bouncycastle.math.ec.ECMultiplier;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.ec.FixedPointCombMultiplier;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes5.dex */
public class ECDSASigner implements ECConstants, DSAExt {
    private final DSAKCalculator kCalculator;
    private ECKeyParameters key;
    private SecureRandom random;

    public ECDSASigner() {
        this.kCalculator = new RandomDSAKCalculator();
    }

    public ECDSASigner(DSAKCalculator dSAKCalculator) {
        this.kCalculator = dSAKCalculator;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSA
    public void init(boolean z, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        if (z) {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.key = (ECPrivateKeyParameters) parametersWithRandom.getParameters();
                secureRandom = parametersWithRandom.getRandom();
                CryptoServicesRegistrar.checkConstraints(Utils.getDefaultProperties("ECDSA", this.key, z));
                this.random = initSecureRandom((z || this.kCalculator.isDeterministic()) ? false : true, secureRandom);
            }
            this.key = (ECPrivateKeyParameters) cipherParameters;
        } else {
            this.key = (ECPublicKeyParameters) cipherParameters;
        }
        secureRandom = null;
        CryptoServicesRegistrar.checkConstraints(Utils.getDefaultProperties("ECDSA", this.key, z));
        this.random = initSecureRandom((z || this.kCalculator.isDeterministic()) ? false : true, secureRandom);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSAExt
    public BigInteger getOrder() {
        return this.key.getParameters().getN();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSA
    public BigInteger[] generateSignature(byte[] bArr) {
        ECDomainParameters parameters = this.key.getParameters();
        BigInteger n = parameters.getN();
        BigInteger calculateE = calculateE(n, bArr);
        BigInteger d = ((ECPrivateKeyParameters) this.key).getD();
        if (this.kCalculator.isDeterministic()) {
            this.kCalculator.init(n, d, bArr);
        } else {
            this.kCalculator.init(n, this.random);
        }
        ECMultiplier createBasePointMultiplier = createBasePointMultiplier();
        while (true) {
            BigInteger nextK = this.kCalculator.nextK();
            BigInteger mod = createBasePointMultiplier.multiply(parameters.getG(), nextK).normalize().getAffineXCoord().toBigInteger().mod(n);
            if (!mod.equals(ZERO)) {
                BigInteger mod2 = BigIntegers.modOddInverse(n, nextK).multiply(calculateE.add(d.multiply(mod))).mod(n);
                if (!mod2.equals(ZERO)) {
                    return new BigInteger[]{mod, mod2};
                }
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSA
    public boolean verifySignature(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger cofactor;
        ECFieldElement denominator;
        ECDomainParameters parameters = this.key.getParameters();
        BigInteger n = parameters.getN();
        BigInteger calculateE = calculateE(n, bArr);
        if (bigInteger.compareTo(ONE) < 0 || bigInteger.compareTo(n) >= 0 || bigInteger2.compareTo(ONE) < 0 || bigInteger2.compareTo(n) >= 0) {
            return false;
        }
        BigInteger modOddInverseVar = BigIntegers.modOddInverseVar(n, bigInteger2);
        ECPoint sumOfTwoMultiplies = ECAlgorithms.sumOfTwoMultiplies(parameters.getG(), calculateE.multiply(modOddInverseVar).mod(n), ((ECPublicKeyParameters) this.key).getQ(), bigInteger.multiply(modOddInverseVar).mod(n));
        if (sumOfTwoMultiplies.isInfinity()) {
            return false;
        }
        ECCurve curve = sumOfTwoMultiplies.getCurve();
        if (curve != null && (cofactor = curve.getCofactor()) != null && cofactor.compareTo(EIGHT) <= 0 && (denominator = getDenominator(curve.getCoordinateSystem(), sumOfTwoMultiplies)) != null && !denominator.isZero()) {
            ECFieldElement xCoord = sumOfTwoMultiplies.getXCoord();
            while (curve.isValidFieldElement(bigInteger)) {
                if (curve.fromBigInteger(bigInteger).multiply(denominator).equals(xCoord)) {
                    return true;
                }
                bigInteger = bigInteger.add(n);
            }
            return false;
        }
        return sumOfTwoMultiplies.normalize().getAffineXCoord().toBigInteger().mod(n).equals(bigInteger);
    }

    protected BigInteger calculateE(BigInteger bigInteger, byte[] bArr) {
        int bitLength = bigInteger.bitLength();
        int length = bArr.length * 8;
        BigInteger bigInteger2 = new BigInteger(1, bArr);
        return bitLength < length ? bigInteger2.shiftRight(length - bitLength) : bigInteger2;
    }

    protected ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    protected ECFieldElement getDenominator(int i, ECPoint eCPoint) {
        if (i != 1) {
            if (i == 2 || i == 3 || i == 4) {
                return eCPoint.getZCoord(0).square();
            }
            if (i != 6 && i != 7) {
                return null;
            }
        }
        return eCPoint.getZCoord(0);
    }

    protected SecureRandom initSecureRandom(boolean z, SecureRandom secureRandom) {
        if (z) {
            return CryptoServicesRegistrar.getSecureRandom(secureRandom);
        }
        return null;
    }
}
