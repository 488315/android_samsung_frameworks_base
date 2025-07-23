package com.android.internal.org.bouncycastle.crypto.agreement;

import com.android.internal.org.bouncycastle.crypto.BasicAgreement;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.internal.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.internal.org.bouncycastle.math.ec.ECConstants;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class ECDHBasicAgreement implements BasicAgreement {
    private ECPrivateKeyParameters key;

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public void init(CipherParameters cipherParameters) {
        ECPrivateKeyParameters eCPrivateKeyParameters = (ECPrivateKeyParameters) cipherParameters;
        this.key = eCPrivateKeyParameters;
        CryptoServicesRegistrar.checkConstraints(Utils.getDefaultProperties("ECDH", eCPrivateKeyParameters));
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public int getFieldSize() {
        return (this.key.getParameters().getCurve().getFieldSize() + 7) / 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public BigInteger calculateAgreement(CipherParameters cipherParameters) {
        ECPublicKeyParameters eCPublicKeyParameters = (ECPublicKeyParameters) cipherParameters;
        ECDomainParameters parameters = this.key.getParameters();
        if (!parameters.equals(eCPublicKeyParameters.getParameters())) {
            throw new IllegalStateException("ECDH public key has wrong domain parameters");
        }
        BigInteger d = this.key.getD();
        ECPoint cleanPoint = ECAlgorithms.cleanPoint(parameters.getCurve(), eCPublicKeyParameters.getQ());
        if (cleanPoint.isInfinity()) {
            throw new IllegalStateException("Infinity is not a valid public key for ECDH");
        }
        BigInteger h = parameters.getH();
        if (!h.equals(ECConstants.ONE)) {
            d = parameters.getHInv().multiply(d).mod(parameters.getN());
            cleanPoint = ECAlgorithms.referenceMultiply(cleanPoint, h);
        }
        ECPoint normalize = cleanPoint.multiply(d).normalize();
        if (normalize.isInfinity()) {
            throw new IllegalStateException("Infinity is not a valid agreement value for ECDH");
        }
        return normalize.getAffineXCoord().toBigInteger();
    }
}
