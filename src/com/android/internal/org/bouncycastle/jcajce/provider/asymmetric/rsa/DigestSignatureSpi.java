package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.DigestInfo;
import com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding;
import com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine;
import com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes5.dex */
public class DigestSignatureSpi extends SignatureSpi {
    private AlgorithmIdentifier algId;
    private AsymmetricBlockCipher cipher;
    private Digest digest;

    @Override // java.security.SignatureSpi
    protected Object engineGetParameter(String str) {
        return null;
    }

    @Override // java.security.SignatureSpi
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    protected DigestSignatureSpi(Digest digest, AsymmetricBlockCipher asymmetricBlockCipher) {
        this.digest = digest;
        this.cipher = asymmetricBlockCipher;
        this.algId = null;
    }

    protected DigestSignatureSpi(ASN1ObjectIdentifier aSN1ObjectIdentifier, Digest digest, AsymmetricBlockCipher asymmetricBlockCipher) {
        this.digest = digest;
        this.cipher = asymmetricBlockCipher;
        this.algId = new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        if (!(publicKey instanceof RSAPublicKey)) {
            throw new InvalidKeyException("Supplied key (" + getType(publicKey) + ") is not a RSAPublicKey instance");
        }
        RSAKeyParameters generatePublicKeyParameter = RSAUtil.generatePublicKeyParameter((RSAPublicKey) publicKey);
        this.digest.reset();
        this.cipher.init(false, generatePublicKeyParameter);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        if (!(privateKey instanceof RSAPrivateKey)) {
            throw new InvalidKeyException("Supplied key (" + getType(privateKey) + ") is not a RSAPrivateKey instance");
        }
        RSAKeyParameters generatePrivateKeyParameter = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey) privateKey);
        this.digest.reset();
        this.cipher.init(true, generatePrivateKeyParameter);
    }

    private String getType(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getName();
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b) throws SignatureException {
        this.digest.update(b);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) throws SignatureException {
        this.digest.update(bArr, i, i2);
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws SignatureException {
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        try {
            byte[] derEncode = derEncode(bArr);
            return this.cipher.processBlock(derEncode, 0, derEncode.length);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new SignatureException("key too small for signature type");
        } catch (Exception e) {
            throw new SignatureException(e.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws SignatureException {
        byte[] processBlock;
        byte[] derEncode;
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr2, 0);
        try {
            processBlock = this.cipher.processBlock(bArr, 0, bArr.length);
            derEncode = derEncode(bArr2);
        } catch (Exception unused) {
        }
        if (processBlock.length == derEncode.length) {
            return Arrays.constantTimeAreEqual(processBlock, derEncode);
        }
        if (processBlock.length == derEncode.length - 2) {
            derEncode[1] = (byte) (derEncode[1] - 2);
            byte b = (byte) (derEncode[3] - 2);
            derEncode[3] = b;
            int i = b + 4;
            int i2 = b + 6;
            int i3 = 0;
            for (int i4 = 0; i4 < derEncode.length - i2; i4++) {
                i3 |= processBlock[i + i4] ^ derEncode[i2 + i4];
            }
            for (int i5 = 0; i5 < i; i5++) {
                i3 |= processBlock[i5] ^ derEncode[i5];
            }
            return i3 == 0;
        }
        Arrays.constantTimeAreEqual(derEncode, derEncode);
        return false;
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(String str, Object obj) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    private byte[] derEncode(byte[] bArr) throws IOException {
        return this.algId == null ? bArr : new DigestInfo(this.algId, bArr).getEncoded(ASN1Encoding.DER);
    }

    public static class SHA1 extends DigestSignatureSpi {
        public SHA1() {
            super(OIWObjectIdentifiers.idSHA1, AndroidDigestFactory.getSHA1(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA224 extends DigestSignatureSpi {
        public SHA224() {
            super(NISTObjectIdentifiers.id_sha224, AndroidDigestFactory.getSHA224(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA256 extends DigestSignatureSpi {
        public SHA256() {
            super(NISTObjectIdentifiers.id_sha256, AndroidDigestFactory.getSHA256(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA384 extends DigestSignatureSpi {
        public SHA384() {
            super(NISTObjectIdentifiers.id_sha384, AndroidDigestFactory.getSHA384(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class SHA512 extends DigestSignatureSpi {
        public SHA512() {
            super(NISTObjectIdentifiers.id_sha512, AndroidDigestFactory.getSHA512(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }

    public static class MD5 extends DigestSignatureSpi {
        public MD5() {
            super(PKCSObjectIdentifiers.md5, AndroidDigestFactory.getMD5(), new PKCS1Encoding(new RSABlindedEngine()));
        }
    }
}
