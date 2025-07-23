package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.internal.org.bouncycastle.crypto.Wrapper;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE;
import com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes5.dex */
public abstract class BaseWrapCipher extends CipherSpi implements PBE {
    private Class[] availableSpecs;
    protected AlgorithmParameters engineParams;
    private boolean forWrapping;
    private final JcaJceHelper helper;
    private byte[] iv;
    private int ivSize;
    protected int pbeHash;
    protected int pbeIvSize;
    protected int pbeKeySize;
    protected int pbeType;
    protected Wrapper wrapEngine;
    private ErasableOutputStream wrapStream;

    @Override // javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetOutputSize(int i) {
        return -1;
    }

    protected BaseWrapCipher() {
        this.availableSpecs = new Class[]{PBEParameterSpec.class, IvParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.engineParams = null;
        this.wrapEngine = null;
        this.wrapStream = null;
        this.helper = new DefaultJcaJceHelper();
    }

    protected BaseWrapCipher(Wrapper wrapper) {
        this(wrapper, 0);
    }

    protected BaseWrapCipher(Wrapper wrapper, int i) {
        this.availableSpecs = new Class[]{PBEParameterSpec.class, IvParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.engineParams = null;
        this.wrapEngine = null;
        this.wrapStream = null;
        this.helper = new DefaultJcaJceHelper();
        this.wrapEngine = wrapper;
        this.ivSize = i;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineGetIV() {
        return Arrays.clone(this.iv);
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    @Override // javax.crypto.CipherSpi
    protected AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null && this.iv != null) {
            String algorithmName = this.wrapEngine.getAlgorithmName();
            if (algorithmName.indexOf(47) >= 0) {
                algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
            }
            try {
                AlgorithmParameters createParametersInstance = createParametersInstance(algorithmName);
                this.engineParams = createParametersInstance;
                createParametersInstance.init(new IvParameterSpec(this.iv));
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
            }
        }
        return this.engineParams;
    }

    protected final AlgorithmParameters createParametersInstance(String str) throws NoSuchAlgorithmException, NoSuchProviderException {
        return this.helper.createAlgorithmParameters(str);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetMode(String str) throws NoSuchAlgorithmException {
        throw new NoSuchAlgorithmException("can't support mode " + str);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetPadding(String str) throws NoSuchPaddingException {
        throw new NoSuchPaddingException("Padding " + str + " unknown.");
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        CipherParameters keyParameter;
        int i2;
        if (key instanceof BCPBEKey) {
            BCPBEKey bCPBEKey = (BCPBEKey) key;
            if (algorithmParameterSpec instanceof PBEParameterSpec) {
                keyParameter = PBE.Util.makePBEParameters(bCPBEKey, algorithmParameterSpec, this.wrapEngine.getAlgorithmName());
            } else if (bCPBEKey.getParam() != null) {
                keyParameter = bCPBEKey.getParam();
            } else {
                throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
            }
        } else {
            keyParameter = new KeyParameter(key.getEncoded());
        }
        if (algorithmParameterSpec instanceof IvParameterSpec) {
            this.iv = ((IvParameterSpec) algorithmParameterSpec).getIV();
            keyParameter = new ParametersWithIV(keyParameter, this.iv);
        }
        if ((keyParameter instanceof KeyParameter) && (i2 = this.ivSize) != 0 && (i == 3 || i == 1)) {
            byte[] bArr = new byte[i2];
            this.iv = bArr;
            secureRandom.nextBytes(bArr);
            keyParameter = new ParametersWithIV(keyParameter, this.iv);
        }
        if (secureRandom != null) {
            keyParameter = new ParametersWithRandom(keyParameter, secureRandom);
        }
        try {
            if (i == 1) {
                this.wrapEngine.init(true, keyParameter);
                this.wrapStream = new ErasableOutputStream();
                this.forWrapping = true;
                return;
            }
            if (i == 2) {
                this.wrapEngine.init(false, keyParameter);
                this.wrapStream = new ErasableOutputStream();
                this.forWrapping = false;
            } else if (i == 3) {
                this.wrapEngine.init(true, keyParameter);
                this.wrapStream = null;
                this.forWrapping = true;
            } else {
                if (i == 4) {
                    this.wrapEngine.init(false, keyParameter);
                    this.wrapStream = null;
                    this.forWrapping = false;
                    return;
                }
                throw new InvalidParameterException("Unknown mode parameter passed to init.");
            }
        } catch (Exception e) {
            throw new InvalidKeyOrParametersException(e.getMessage(), e);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec algorithmParameterSpec;
        if (algorithmParameters != null) {
            algorithmParameterSpec = SpecUtil.extractSpec(algorithmParameters, this.availableSpecs);
            if (algorithmParameterSpec == null) {
                throw new InvalidAlgorithmParameterException("can't handle parameter " + algorithmParameters.toString());
            }
        } else {
            algorithmParameterSpec = null;
        }
        this.engineParams = algorithmParameters;
        engineInit(i, key, algorithmParameterSpec, secureRandom);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            engineInit(i, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyOrParametersException(e.getMessage(), e);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineUpdate(byte[] bArr, int i, int i2) {
        ErasableOutputStream erasableOutputStream = this.wrapStream;
        if (erasableOutputStream == null) {
            throw new IllegalStateException("not supported in a wrapping mode");
        }
        erasableOutputStream.write(bArr, i, i2);
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException {
        ErasableOutputStream erasableOutputStream = this.wrapStream;
        if (erasableOutputStream == null) {
            throw new IllegalStateException("not supported in a wrapping mode");
        }
        erasableOutputStream.write(bArr, i, i2);
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineDoFinal(byte[] bArr, int i, int i2) throws IllegalBlockSizeException, BadPaddingException {
        byte[] wrap;
        ErasableOutputStream erasableOutputStream = this.wrapStream;
        if (erasableOutputStream == null) {
            throw new IllegalStateException("not supported in a wrapping mode");
        }
        if (bArr != null) {
            erasableOutputStream.write(bArr, i, i2);
        }
        try {
            if (this.forWrapping) {
                try {
                    wrap = this.wrapEngine.wrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                    return wrap;
                } catch (Exception e) {
                    throw new IllegalBlockSizeException(e.getMessage());
                }
            }
            try {
                wrap = this.wrapEngine.unwrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                return wrap;
            } catch (InvalidCipherTextException e2) {
                throw new BadPaddingException(e2.getMessage());
            }
        } finally {
            this.wrapStream.erase();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0041 A[Catch: all -> 0x005f, TRY_LEAVE, TryCatch #2 {all -> 0x005f, blocks: (B:5:0x0007, B:9:0x000c, B:10:0x003c, B:12:0x0041, B:16:0x004c, B:17:0x0053, B:23:0x002a, B:20:0x0020, B:21:0x0029, B:26:0x0055, B:27:0x005e), top: B:4:0x0007, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004c A[Catch: all -> 0x005f, TRY_ENTER, TryCatch #2 {all -> 0x005f, blocks: (B:5:0x0007, B:9:0x000c, B:10:0x003c, B:12:0x0041, B:16:0x004c, B:17:0x0053, B:23:0x002a, B:20:0x0020, B:21:0x0029, B:26:0x0055, B:27:0x005e), top: B:4:0x0007, inners: #0, #1 }] */
    @Override // javax.crypto.CipherSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int engineDoFinal(byte[] r2, int r3, int r4, byte[] r5, int r6) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, javax.crypto.ShortBufferException {
        /*
            r1 = this;
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r0 = r1.wrapStream
            if (r0 == 0) goto L66
            r0.write(r2, r3, r4)
            boolean r2 = r1.forWrapping     // Catch: java.lang.Throwable -> L5f
            r3 = 0
            if (r2 == 0) goto L2a
            com.android.internal.org.bouncycastle.crypto.Wrapper r2 = r1.wrapEngine     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L5f
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r4 = r1.wrapStream     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L5f
            byte[] r4 = r4.getBuf()     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L5f
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r0 = r1.wrapStream     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L5f
            int r0 = r0.size()     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L5f
            byte[] r2 = r2.wrap(r4, r3, r0)     // Catch: java.lang.Exception -> L1f java.lang.Throwable -> L5f
            goto L3c
        L1f:
            r2 = move-exception
            javax.crypto.IllegalBlockSizeException r3 = new javax.crypto.IllegalBlockSizeException     // Catch: java.lang.Throwable -> L5f
            java.lang.String r2 = r2.getMessage()     // Catch: java.lang.Throwable -> L5f
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L5f
            throw r3     // Catch: java.lang.Throwable -> L5f
        L2a:
            com.android.internal.org.bouncycastle.crypto.Wrapper r2 = r1.wrapEngine     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L54 java.lang.Throwable -> L5f
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r4 = r1.wrapStream     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L54 java.lang.Throwable -> L5f
            byte[] r4 = r4.getBuf()     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L54 java.lang.Throwable -> L5f
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r0 = r1.wrapStream     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L54 java.lang.Throwable -> L5f
            int r0 = r0.size()     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L54 java.lang.Throwable -> L5f
            byte[] r2 = r2.unwrap(r4, r3, r0)     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L54 java.lang.Throwable -> L5f
        L3c:
            int r4 = r2.length     // Catch: java.lang.Throwable -> L5f
            int r4 = r4 + r6
            int r0 = r5.length     // Catch: java.lang.Throwable -> L5f
            if (r4 > r0) goto L4c
            int r4 = r2.length     // Catch: java.lang.Throwable -> L5f
            java.lang.System.arraycopy(r2, r3, r5, r6, r4)     // Catch: java.lang.Throwable -> L5f
            int r2 = r2.length     // Catch: java.lang.Throwable -> L5f
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r1 = r1.wrapStream
            r1.erase()
            return r2
        L4c:
            javax.crypto.ShortBufferException r2 = new javax.crypto.ShortBufferException     // Catch: java.lang.Throwable -> L5f
            java.lang.String r3 = "output buffer too short for input."
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L5f
            throw r2     // Catch: java.lang.Throwable -> L5f
        L54:
            r2 = move-exception
            javax.crypto.BadPaddingException r3 = new javax.crypto.BadPaddingException     // Catch: java.lang.Throwable -> L5f
            java.lang.String r2 = r2.getMessage()     // Catch: java.lang.Throwable -> L5f
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L5f
            throw r3     // Catch: java.lang.Throwable -> L5f
        L5f:
            r2 = move-exception
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r1 = r1.wrapStream
            r1.erase()
            throw r2
        L66:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "not supported in a wrapping mode"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher.engineDoFinal(byte[], int, int, byte[], int):int");
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new InvalidKeyException("Cannot wrap key, null encoding.");
        }
        try {
            Wrapper wrapper = this.wrapEngine;
            if (wrapper == null) {
                return engineDoFinal(encoded, 0, encoded.length);
            }
            return wrapper.wrap(encoded, 0, encoded.length);
        } catch (BadPaddingException e) {
            throw new IllegalBlockSizeException(e.getMessage());
        }
    }

    @Override // javax.crypto.CipherSpi
    protected Key engineUnwrap(byte[] bArr, String str, int i) throws InvalidKeyException, NoSuchAlgorithmException {
        try {
            Wrapper wrapper = this.wrapEngine;
            byte[] engineDoFinal = wrapper == null ? engineDoFinal(bArr, 0, bArr.length) : wrapper.unwrap(bArr, 0, bArr.length);
            if (i == 3) {
                return new SecretKeySpec(engineDoFinal, str);
            }
            if (str.equals("") && i == 2) {
                try {
                    PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(engineDoFinal);
                    PrivateKey privateKey = BouncyCastleProvider.getPrivateKey(privateKeyInfo);
                    if (privateKey != null) {
                        return privateKey;
                    }
                    throw new InvalidKeyException("algorithm " + privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm() + " not supported");
                } catch (Exception unused) {
                    throw new InvalidKeyException("Invalid key encoding.");
                }
            }
            try {
                KeyFactory createKeyFactory = this.helper.createKeyFactory(str);
                if (i == 1) {
                    return createKeyFactory.generatePublic(new X509EncodedKeySpec(engineDoFinal));
                }
                if (i == 2) {
                    return createKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(engineDoFinal));
                }
                throw new InvalidKeyException("Unknown key type " + i);
            } catch (NoSuchProviderException e) {
                throw new InvalidKeyException("Unknown key type " + e.getMessage());
            } catch (InvalidKeySpecException e2) {
                throw new InvalidKeyException("Unknown key type " + e2.getMessage());
            }
        } catch (InvalidCipherTextException e3) {
            throw new InvalidKeyException(e3.getMessage());
        } catch (BadPaddingException e4) {
            throw new InvalidKeyException(e4.getMessage());
        } catch (IllegalBlockSizeException e5) {
            throw new InvalidKeyException(e5.getMessage());
        }
    }

    protected static final class ErasableOutputStream extends ByteArrayOutputStream {
        public byte[] getBuf() {
            return this.buf;
        }

        public void erase() {
            Arrays.fill(this.buf, (byte) 0);
            reset();
        }
    }

    protected static class InvalidKeyOrParametersException extends InvalidKeyException {
        private final Throwable cause;

        InvalidKeyOrParametersException(String str, Throwable th) {
            super(str);
            this.cause = th;
        }

        @Override // java.lang.Throwable
        public Throwable getCause() {
            return this.cause;
        }
    }
}
