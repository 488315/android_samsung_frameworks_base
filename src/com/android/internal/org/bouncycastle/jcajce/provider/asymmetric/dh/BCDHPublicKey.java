package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.asn1.x9.DomainParameters;
import com.android.internal.org.bouncycastle.asn1.x9.ValidationParams;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.internal.org.bouncycastle.crypto.params.DHParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import com.android.internal.org.bouncycastle.jcajce.spec.DHExtendedPublicKeySpec;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

/* loaded from: classes5.dex */
public class BCDHPublicKey implements DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private transient DHPublicKeyParameters dhPublicKey;
    private transient DHParameterSpec dhSpec;
    private transient SubjectPublicKeyInfo info;
    private BigInteger y;

    BCDHPublicKey(DHPublicKeySpec dHPublicKeySpec) {
        this.y = dHPublicKeySpec.getY();
        if (dHPublicKeySpec instanceof DHExtendedPublicKeySpec) {
            this.dhSpec = ((DHExtendedPublicKeySpec) dHPublicKeySpec).getParams();
        } else {
            this.dhSpec = new DHParameterSpec(dHPublicKeySpec.getP(), dHPublicKeySpec.getG());
        }
        DHParameterSpec dHParameterSpec = this.dhSpec;
        if (dHParameterSpec instanceof DHDomainParameterSpec) {
            this.dhPublicKey = new DHPublicKeyParameters(this.y, ((DHDomainParameterSpec) dHParameterSpec).getDomainParameters());
        } else {
            this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(dHPublicKeySpec.getP(), dHPublicKeySpec.getG()));
        }
    }

    BCDHPublicKey(DHPublicKey dHPublicKey) {
        this.y = dHPublicKey.getY();
        DHParameterSpec params = dHPublicKey.getParams();
        this.dhSpec = params;
        if (params instanceof DHDomainParameterSpec) {
            this.dhPublicKey = new DHPublicKeyParameters(this.y, ((DHDomainParameterSpec) params).getDomainParameters());
        } else {
            this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
        }
    }

    BCDHPublicKey(DHPublicKeyParameters dHPublicKeyParameters) {
        this.y = dHPublicKeyParameters.getY();
        this.dhSpec = new DHDomainParameterSpec(dHPublicKeyParameters.getParameters());
        this.dhPublicKey = dHPublicKeyParameters;
    }

    BCDHPublicKey(BigInteger bigInteger, DHParameterSpec dHParameterSpec) {
        this.y = bigInteger;
        this.dhSpec = dHParameterSpec;
        if (dHParameterSpec instanceof DHDomainParameterSpec) {
            this.dhPublicKey = new DHPublicKeyParameters(bigInteger, ((DHDomainParameterSpec) dHParameterSpec).getDomainParameters());
        } else {
            this.dhPublicKey = new DHPublicKeyParameters(bigInteger, new DHParameters(dHParameterSpec.getP(), dHParameterSpec.getG()));
        }
    }

    public BCDHPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.info = subjectPublicKeyInfo;
        try {
            this.y = ((ASN1Integer) subjectPublicKeyInfo.parsePublicKey()).getValue();
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
            ASN1ObjectIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
            if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.dhKeyAgreement) || isPKCSParam(aSN1Sequence)) {
                DHParameter dHParameter = DHParameter.getInstance(aSN1Sequence);
                if (dHParameter.getL() != null) {
                    this.dhSpec = new DHParameterSpec(dHParameter.getP(), dHParameter.getG(), dHParameter.getL().intValue());
                    this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG(), null, this.dhSpec.getL()));
                    return;
                } else {
                    this.dhSpec = new DHParameterSpec(dHParameter.getP(), dHParameter.getG());
                    this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
                    return;
                }
            }
            if (algorithm.equals((ASN1Primitive) X9ObjectIdentifiers.dhpublicnumber)) {
                DomainParameters domainParameters = DomainParameters.getInstance(aSN1Sequence);
                ValidationParams validationParams = domainParameters.getValidationParams();
                if (validationParams != null) {
                    this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(domainParameters.getP(), domainParameters.getG(), domainParameters.getQ(), domainParameters.getJ(), new DHValidationParameters(validationParams.getSeed(), validationParams.getPgenCounter().intValue())));
                } else {
                    this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(domainParameters.getP(), domainParameters.getG(), domainParameters.getQ(), domainParameters.getJ(), (DHValidationParameters) null));
                }
                this.dhSpec = new DHDomainParameterSpec(this.dhPublicKey.getParameters());
                return;
            }
            throw new IllegalArgumentException("unknown algorithm type: " + algorithm);
        } catch (IOException unused) {
            throw new IllegalArgumentException("invalid info structure in DH public key");
        }
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return "DH";
    }

    @Override // java.security.Key
    public String getFormat() {
        return "X.509";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        SubjectPublicKeyInfo subjectPublicKeyInfo = this.info;
        if (subjectPublicKeyInfo != null) {
            return KeyUtil.getEncodedSubjectPublicKeyInfo(subjectPublicKeyInfo);
        }
        DHParameterSpec dHParameterSpec = this.dhSpec;
        if ((dHParameterSpec instanceof DHDomainParameterSpec) && ((DHDomainParameterSpec) dHParameterSpec).getQ() != null) {
            DHParameters domainParameters = ((DHDomainParameterSpec) this.dhSpec).getDomainParameters();
            DHValidationParameters validationParameters = domainParameters.getValidationParameters();
            return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.dhpublicnumber, new DomainParameters(domainParameters.getP(), domainParameters.getG(), domainParameters.getQ(), domainParameters.getJ(), validationParameters != null ? new ValidationParams(validationParameters.getSeed(), validationParameters.getCounter()) : null).toASN1Primitive()), new ASN1Integer(this.y));
        }
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL()).toASN1Primitive()), new ASN1Integer(this.y));
    }

    public String toString() {
        return DHUtil.publicKeyToString("DH", this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
    }

    @Override // javax.crypto.interfaces.DHKey
    public DHParameterSpec getParams() {
        return this.dhSpec;
    }

    @Override // javax.crypto.interfaces.DHPublicKey
    public BigInteger getY() {
        return this.y;
    }

    public DHPublicKeyParameters engineGetKeyParameters() {
        return this.dhPublicKey;
    }

    private boolean isPKCSParam(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 2) {
            return true;
        }
        if (aSN1Sequence.size() > 3) {
            return false;
        }
        return ASN1Integer.getInstance(aSN1Sequence.getObjectAt(2)).getValue().compareTo(BigInteger.valueOf((long) ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue().bitLength())) <= 0;
    }

    public int hashCode() {
        return getParams().getL() ^ ((getY().hashCode() ^ getParams().getG().hashCode()) ^ getParams().getP().hashCode());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DHPublicKey)) {
            return false;
        }
        DHPublicKey dHPublicKey = (DHPublicKey) obj;
        return getY().equals(dHPublicKey.getY()) && getParams().getG().equals(dHPublicKey.getParams().getG()) && getParams().getP().equals(dHPublicKey.getParams().getP()) && getParams().getL() == dHPublicKey.getParams().getL();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.dhSpec = new DHParameterSpec((BigInteger) objectInputStream.readObject(), (BigInteger) objectInputStream.readObject(), objectInputStream.readInt());
        this.info = null;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
    }
}
