package com.android.internal.org.bouncycastle.jce.netscape;

import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1IA5String;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Object;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DERBitString;
import com.android.internal.org.bouncycastle.asn1.DERIA5String;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/* loaded from: classes5.dex */
public class NetscapeCertRequest extends ASN1Object {
    String challenge;
    DERBitString content;
    AlgorithmIdentifier keyAlg;
    PublicKey pubkey;
    AlgorithmIdentifier sigAlg;
    byte[] sigBits;

    private static ASN1Sequence getReq(byte[] bArr) throws IOException {
        return ASN1Sequence.getInstance(new ASN1InputStream(new ByteArrayInputStream(bArr)).readObject());
    }

    public NetscapeCertRequest(byte[] bArr) throws IOException {
        this(getReq(bArr));
    }

    public NetscapeCertRequest(ASN1Sequence aSN1Sequence) {
        try {
            if (aSN1Sequence.size() != 3) {
                throw new IllegalArgumentException("invalid SPKAC (size):" + aSN1Sequence.size());
            }
            this.sigAlg = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
            this.sigBits = ((DERBitString) aSN1Sequence.getObjectAt(2)).getOctets();
            ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(0);
            if (aSN1Sequence2.size() != 2) {
                throw new IllegalArgumentException("invalid PKAC (len): " + aSN1Sequence2.size());
            }
            this.challenge = ((ASN1IA5String) aSN1Sequence2.getObjectAt(1)).getString();
            this.content = new DERBitString(aSN1Sequence2);
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(aSN1Sequence2.getObjectAt(0));
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(new DERBitString(subjectPublicKeyInfo).getBytes());
            AlgorithmIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm();
            this.keyAlg = algorithm;
            this.pubkey = KeyFactory.getInstance(algorithm.getAlgorithm().getId()).generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.toString());
        }
    }

    public NetscapeCertRequest(String str, AlgorithmIdentifier algorithmIdentifier, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        this.challenge = str;
        this.sigAlg = algorithmIdentifier;
        this.pubkey = publicKey;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(getKeySpec());
        aSN1EncodableVector.add(new DERIA5String(str));
        try {
            this.content = new DERBitString(new DERSequence(aSN1EncodableVector));
        } catch (IOException e) {
            throw new InvalidKeySpecException("exception encoding key: " + e.toString());
        }
    }

    public String getChallenge() {
        return this.challenge;
    }

    public void setChallenge(String str) {
        this.challenge = str;
    }

    public AlgorithmIdentifier getSigningAlgorithm() {
        return this.sigAlg;
    }

    public void setSigningAlgorithm(AlgorithmIdentifier algorithmIdentifier) {
        this.sigAlg = algorithmIdentifier;
    }

    public AlgorithmIdentifier getKeyAlgorithm() {
        return this.keyAlg;
    }

    public void setKeyAlgorithm(AlgorithmIdentifier algorithmIdentifier) {
        this.keyAlg = algorithmIdentifier;
    }

    public PublicKey getPublicKey() {
        return this.pubkey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.pubkey = publicKey;
    }

    public boolean verify(String str) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException {
        if (!str.equals(this.challenge)) {
            return false;
        }
        Signature signature = Signature.getInstance(this.sigAlg.getAlgorithm().getId());
        signature.initVerify(this.pubkey);
        signature.update(this.content.getBytes());
        return signature.verify(this.sigBits);
    }

    public void sign(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidKeySpecException {
        sign(privateKey, null);
    }

    public void sign(PrivateKey privateKey, SecureRandom secureRandom) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidKeySpecException {
        Signature signature = Signature.getInstance(this.sigAlg.getAlgorithm().getId());
        if (secureRandom != null) {
            signature.initSign(privateKey, secureRandom);
        } else {
            signature.initSign(privateKey);
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(getKeySpec());
        aSN1EncodableVector.add(new DERIA5String(this.challenge));
        try {
            signature.update(new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER));
            this.sigBits = signature.sign();
        } catch (IOException e) {
            throw new SignatureException(e.getMessage());
        }
    }

    private ASN1Primitive getKeySpec() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(this.pubkey.getEncoded());
            byteArrayOutputStream.close();
            return new ASN1InputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())).readObject();
        } catch (IOException e) {
            throw new InvalidKeySpecException(e.getMessage());
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        try {
            aSN1EncodableVector2.add(getKeySpec());
        } catch (Exception unused) {
        }
        aSN1EncodableVector2.add(new DERIA5String(this.challenge));
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        aSN1EncodableVector.add(this.sigAlg);
        aSN1EncodableVector.add(new DERBitString(this.sigBits));
        return new DERSequence(aSN1EncodableVector);
    }
}
