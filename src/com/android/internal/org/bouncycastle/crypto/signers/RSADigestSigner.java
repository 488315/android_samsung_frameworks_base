package com.android.internal.org.bouncycastle.crypto.signers;

import android.security.keystore.KeyProperties;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.DigestInfo;
import com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoException;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.crypto.Signer;
import com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding;
import com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine;
import com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.util.Hashtable;

/* loaded from: classes5.dex */
public class RSADigestSigner implements Signer {
    private static final Hashtable oidMap;
    private final AlgorithmIdentifier algId;
    private final Digest digest;
    private boolean forSigning;
    private final AsymmetricBlockCipher rsaEngine;

    static {
        Hashtable hashtable = new Hashtable();
        oidMap = hashtable;
        hashtable.put("SHA-1", X509ObjectIdentifiers.id_SHA1);
        hashtable.put(KeyProperties.DIGEST_SHA224, NISTObjectIdentifiers.id_sha224);
        hashtable.put("SHA-256", NISTObjectIdentifiers.id_sha256);
        hashtable.put(KeyProperties.DIGEST_SHA384, NISTObjectIdentifiers.id_sha384);
        hashtable.put(KeyProperties.DIGEST_SHA512, NISTObjectIdentifiers.id_sha512);
        hashtable.put("SHA-512/224", NISTObjectIdentifiers.id_sha512_224);
        hashtable.put("SHA-512/256", NISTObjectIdentifiers.id_sha512_256);
        hashtable.put(KeyProperties.DIGEST_MD5, PKCSObjectIdentifiers.md5);
    }

    public RSADigestSigner(Digest digest) {
        this(digest, (ASN1ObjectIdentifier) oidMap.get(digest.getAlgorithmName()));
    }

    public RSADigestSigner(Digest digest, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.rsaEngine = new PKCS1Encoding(new RSABlindedEngine());
        this.digest = digest;
        if (aSN1ObjectIdentifier != null) {
            this.algId = new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
        } else {
            this.algId = null;
        }
    }

    public String getAlgorithmName() {
        return this.digest.getAlgorithmName() + "withRSA";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public void init(boolean z, CipherParameters cipherParameters) {
        AsymmetricKeyParameter asymmetricKeyParameter;
        this.forSigning = z;
        if (cipherParameters instanceof ParametersWithRandom) {
            asymmetricKeyParameter = (AsymmetricKeyParameter) ((ParametersWithRandom) cipherParameters).getParameters();
        } else {
            asymmetricKeyParameter = (AsymmetricKeyParameter) cipherParameters;
        }
        if (z && !asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("signing requires private key");
        }
        if (!z && asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("verification requires public key");
        }
        reset();
        this.rsaEngine.init(z, cipherParameters);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public void update(byte b) {
        this.digest.update(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public void update(byte[] bArr, int i, int i2) {
        this.digest.update(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public byte[] generateSignature() throws CryptoException, DataLengthException {
        if (!this.forSigning) {
            throw new IllegalStateException("RSADigestSigner not initialised for signature generation.");
        }
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        try {
            byte[] derEncode = derEncode(bArr);
            return this.rsaEngine.processBlock(derEncode, 0, derEncode.length);
        } catch (IOException e) {
            throw new CryptoException("unable to encode signature: " + e.getMessage(), e);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public boolean verifySignature(byte[] bArr) {
        byte[] processBlock;
        byte[] derEncode;
        if (this.forSigning) {
            throw new IllegalStateException("RSADigestSigner not initialised for verification");
        }
        int digestSize = this.digest.getDigestSize();
        byte[] bArr2 = new byte[digestSize];
        this.digest.doFinal(bArr2, 0);
        try {
            processBlock = this.rsaEngine.processBlock(bArr, 0, bArr.length);
            derEncode = derEncode(bArr2);
        } catch (Exception unused) {
        }
        if (processBlock.length == derEncode.length) {
            return Arrays.constantTimeAreEqual(processBlock, derEncode);
        }
        if (processBlock.length == derEncode.length - 2) {
            int length = (processBlock.length - digestSize) - 2;
            int length2 = (derEncode.length - digestSize) - 2;
            derEncode[1] = (byte) (derEncode[1] - 2);
            derEncode[3] = (byte) (derEncode[3] - 2);
            int i = 0;
            for (int i2 = 0; i2 < digestSize; i2++) {
                i |= processBlock[length + i2] ^ derEncode[length2 + i2];
            }
            for (int i3 = 0; i3 < length; i3++) {
                i |= processBlock[i3] ^ derEncode[i3];
            }
            return i == 0;
        }
        Arrays.constantTimeAreEqual(derEncode, derEncode);
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public void reset() {
        this.digest.reset();
    }

    private byte[] derEncode(byte[] bArr) throws IOException {
        if (this.algId == null) {
            try {
                DigestInfo.getInstance(bArr);
                return bArr;
            } catch (IllegalArgumentException e) {
                throw new IOException("malformed DigestInfo for NONEwithRSA hash: " + e.getMessage());
            }
        }
        return new DigestInfo(this.algId, bArr).getEncoded(ASN1Encoding.DER);
    }
}
