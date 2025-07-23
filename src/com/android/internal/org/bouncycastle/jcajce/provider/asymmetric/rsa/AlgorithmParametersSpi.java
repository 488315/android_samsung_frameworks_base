package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.jcajce.provider.util.DigestFactory;
import com.android.internal.org.bouncycastle.jcajce.util.MessageDigestUtils;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

/* loaded from: classes5.dex */
public abstract class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    protected abstract AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) throws InvalidParameterSpecException;

    protected boolean isASN1FormatString(String str) {
        return str == null || str.equals("ASN.1");
    }

    @Override // java.security.AlgorithmParametersSpi
    protected AlgorithmParameterSpec engineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
        if (cls == null) {
            throw new NullPointerException("argument to getParameterSpec must not be null");
        }
        return localEngineGetParameterSpec(cls);
    }

    public static class OAEP extends AlgorithmParametersSpi {
        OAEPParameterSpec currentSpec;

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded() {
            try {
                return new RSAESOAEPparams(new AlgorithmIdentifier(DigestFactory.getOID(this.currentSpec.getDigestAlgorithm()), DERNull.INSTANCE), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(((MGF1ParameterSpec) this.currentSpec.getMGFParameters()).getDigestAlgorithm()), DERNull.INSTANCE)), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(((PSource.PSpecified) this.currentSpec.getPSource()).getValue()))).getEncoded(ASN1Encoding.DER);
            } catch (IOException unused) {
                throw new RuntimeException("Error encoding OAEPParameters");
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi
        protected AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
            if (cls == OAEPParameterSpec.class || cls == AlgorithmParameterSpec.class) {
                return this.currentSpec;
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to OAEP parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
            if (!(algorithmParameterSpec instanceof OAEPParameterSpec)) {
                throw new InvalidParameterSpecException("OAEPParameterSpec required to initialise an OAEP algorithm parameters object");
            }
            this.currentSpec = (OAEPParameterSpec) algorithmParameterSpec;
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr) throws IOException {
            try {
                RSAESOAEPparams rSAESOAEPparams = RSAESOAEPparams.getInstance(bArr);
                if (rSAESOAEPparams.getMaskGenAlgorithm().getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_mgf1)) {
                    this.currentSpec = new OAEPParameterSpec(MessageDigestUtils.getDigestName(rSAESOAEPparams.getHashAlgorithm().getAlgorithm()), OAEPParameterSpec.DEFAULT.getMGFAlgorithm(), new MGF1ParameterSpec(MessageDigestUtils.getDigestName(AlgorithmIdentifier.getInstance(rSAESOAEPparams.getMaskGenAlgorithm().getParameters()).getAlgorithm())), new PSource.PSpecified(ASN1OctetString.getInstance(rSAESOAEPparams.getPSourceAlgorithm().getParameters()).getOctets()));
                } else {
                    throw new IOException("unknown mask generation function: " + rSAESOAEPparams.getMaskGenAlgorithm().getAlgorithm());
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            } catch (ClassCastException unused2) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr, String str) throws IOException {
            if (str.equalsIgnoreCase("X.509") || str.equalsIgnoreCase("ASN.1")) {
                engineInit(bArr);
            } else {
                throw new IOException("Unknown parameter format " + str);
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected String engineToString() {
            return "OAEP Parameters";
        }
    }

    public static class PSS extends AlgorithmParametersSpi {
        PSSParameterSpec currentSpec;

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded() throws IOException {
            AlgorithmIdentifier algorithmIdentifier;
            PSSParameterSpec pSSParameterSpec = this.currentSpec;
            ASN1ObjectIdentifier oid = DigestFactory.getOID(pSSParameterSpec.getDigestAlgorithm());
            if (NISTObjectIdentifiers.id_shake128.equals((ASN1Primitive) oid) || NISTObjectIdentifiers.id_shake256.equals((ASN1Primitive) oid)) {
                algorithmIdentifier = new AlgorithmIdentifier(oid);
            } else {
                algorithmIdentifier = new AlgorithmIdentifier(oid, DERNull.INSTANCE);
            }
            MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) pSSParameterSpec.getMGFParameters();
            if (mGF1ParameterSpec != null) {
                return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(mGF1ParameterSpec.getDigestAlgorithm()), DERNull.INSTANCE)), new ASN1Integer(pSSParameterSpec.getSaltLength()), new ASN1Integer(pSSParameterSpec.getTrailerField())).getEncoded(ASN1Encoding.DER);
            }
            return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(pSSParameterSpec.getMGFAlgorithm().equals("SHAKE128") ? NISTObjectIdentifiers.id_shake128 : NISTObjectIdentifiers.id_shake256), new ASN1Integer(pSSParameterSpec.getSaltLength()), new ASN1Integer(pSSParameterSpec.getTrailerField())).getEncoded(ASN1Encoding.DER);
        }

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded(String str) throws IOException {
            if (str.equalsIgnoreCase("X.509") || str.equalsIgnoreCase("ASN.1")) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi
        protected AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) throws InvalidParameterSpecException {
            if (cls == PSSParameterSpec.class || cls == AlgorithmParameterSpec.class) {
                return this.currentSpec;
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PSS parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
            if (!(algorithmParameterSpec instanceof PSSParameterSpec)) {
                throw new InvalidParameterSpecException("PSSParameterSpec required to initialise an PSS algorithm parameters object");
            }
            this.currentSpec = (PSSParameterSpec) algorithmParameterSpec;
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr) throws IOException {
            try {
                RSASSAPSSparams rSASSAPSSparams = RSASSAPSSparams.getInstance(bArr);
                ASN1ObjectIdentifier algorithm = rSASSAPSSparams.getMaskGenAlgorithm().getAlgorithm();
                if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.id_mgf1)) {
                    this.currentSpec = new PSSParameterSpec(MessageDigestUtils.getDigestName(rSASSAPSSparams.getHashAlgorithm().getAlgorithm()), PSSParameterSpec.DEFAULT.getMGFAlgorithm(), new MGF1ParameterSpec(MessageDigestUtils.getDigestName(AlgorithmIdentifier.getInstance(rSASSAPSSparams.getMaskGenAlgorithm().getParameters()).getAlgorithm())), rSASSAPSSparams.getSaltLength().intValue(), rSASSAPSSparams.getTrailerField().intValue());
                    return;
                }
                if (!algorithm.equals((ASN1Primitive) NISTObjectIdentifiers.id_shake128) && !algorithm.equals((ASN1Primitive) NISTObjectIdentifiers.id_shake256)) {
                    throw new IOException("unknown mask generation function: " + rSASSAPSSparams.getMaskGenAlgorithm().getAlgorithm());
                }
                this.currentSpec = new PSSParameterSpec(MessageDigestUtils.getDigestName(rSASSAPSSparams.getHashAlgorithm().getAlgorithm()), algorithm.equals((ASN1Primitive) NISTObjectIdentifiers.id_shake128) ? "SHAKE128" : "SHAKE256", null, rSASSAPSSparams.getSaltLength().intValue(), rSASSAPSSparams.getTrailerField().intValue());
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            } catch (ClassCastException unused2) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr, String str) throws IOException {
            if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
                engineInit(bArr);
            } else {
                throw new IOException("Unknown parameter format " + str);
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected String engineToString() {
            return "PSS Parameters";
        }
    }
}
