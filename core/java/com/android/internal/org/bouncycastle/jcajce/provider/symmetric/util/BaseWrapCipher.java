package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.internal.org.bouncycastle.crypto.Wrapper;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom;
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

    protected BaseWrapCipher() {
        this.availableSpecs = new Class[] {PBEParameterSpec.class, IvParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.engineParams = null;
        this.wrapEngine = null;
        this.wrapStream = null;
        this.helper = new DefaultJcaJceHelper();
    }

    protected BaseWrapCipher(Wrapper wrapEngine) {
        this(wrapEngine, 0);
    }

    protected BaseWrapCipher(Wrapper wrapEngine, int ivSize) {
        this.availableSpecs = new Class[] {PBEParameterSpec.class, IvParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.engineParams = null;
        this.wrapEngine = null;
        this.wrapStream = null;
        this.helper = new DefaultJcaJceHelper();
        this.wrapEngine = wrapEngine;
        this.ivSize = ivSize;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        return 0;
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
    protected int engineGetOutputSize(int inputLen) {
        return -1;
    }

    @Override // javax.crypto.CipherSpi
    protected AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null && this.iv != null) {
            String name = this.wrapEngine.getAlgorithmName();
            if (name.indexOf(47) >= 0) {
                name = name.substring(0, name.indexOf(47));
            }
            try {
                this.engineParams = createParametersInstance(name);
                this.engineParams.init(new IvParameterSpec(this.iv));
            } catch (Exception e) {
                throw new RuntimeException(e.toString());
            }
        }
        return this.engineParams;
    }

    protected final AlgorithmParameters createParametersInstance(String algorithm)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        return this.helper.createAlgorithmParameters(algorithm);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetMode(String mode) throws NoSuchAlgorithmException {
        throw new NoSuchAlgorithmException("can't support mode " + mode);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetPadding(String padding) throws NoSuchPaddingException {
        throw new NoSuchPaddingException("Padding " + padding + " unknown.");
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(
            int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random)
            throws InvalidKeyException, InvalidAlgorithmParameterException {
        CipherParameters param;
        if (key instanceof BCPBEKey) {
            BCPBEKey k = (BCPBEKey) key;
            if (params instanceof PBEParameterSpec) {
                param = PBE.Util.makePBEParameters(k, params, this.wrapEngine.getAlgorithmName());
            } else {
                CipherParameters param2 = k.getParam();
                if (param2 != null) {
                    param = k.getParam();
                } else {
                    throw new InvalidAlgorithmParameterException(
                            "PBE requires PBE parameters to be set.");
                }
            }
        } else {
            param = new KeyParameter(key.getEncoded());
        }
        if (params instanceof IvParameterSpec) {
            IvParameterSpec ivSpec = (IvParameterSpec) params;
            this.iv = ivSpec.getIV();
            param = new ParametersWithIV(param, this.iv);
        }
        if ((param instanceof KeyParameter) && this.ivSize != 0 && (opmode == 3 || opmode == 1)) {
            this.iv = new byte[this.ivSize];
            random.nextBytes(this.iv);
            param = new ParametersWithIV(param, this.iv);
        }
        if (random != null) {
            param = new ParametersWithRandom(param, random);
        }
        try {
            switch (opmode) {
                case 1:
                    this.wrapEngine.init(true, param);
                    this.wrapStream = new ErasableOutputStream();
                    this.forWrapping = true;
                    return;
                case 2:
                    this.wrapEngine.init(false, param);
                    this.wrapStream = new ErasableOutputStream();
                    this.forWrapping = false;
                    return;
                case 3:
                    this.wrapEngine.init(true, param);
                    this.wrapStream = null;
                    this.forWrapping = true;
                    return;
                case 4:
                    this.wrapEngine.init(false, param);
                    this.wrapStream = null;
                    this.forWrapping = false;
                    return;
                default:
                    throw new InvalidParameterException("Unknown mode parameter passed to init.");
            }
        } catch (Exception e) {
            throw new InvalidKeyOrParametersException(e.getMessage(), e);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random)
            throws InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec paramSpec = null;
        if (params != null
                && (paramSpec = SpecUtil.extractSpec(params, this.availableSpecs)) == null) {
            throw new InvalidAlgorithmParameterException(
                    "can't handle parameter " + params.toString());
        }
        this.engineParams = params;
        engineInit(opmode, key, paramSpec, random);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int opmode, Key key, SecureRandom random) throws InvalidKeyException {
        try {
            engineInit(opmode, key, (AlgorithmParameterSpec) null, random);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyOrParametersException(e.getMessage(), e);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        if (this.wrapStream == null) {
            throw new IllegalStateException("not supported in a wrapping mode");
        }
        this.wrapStream.write(input, inputOffset, inputLen);
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineUpdate(
            byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
            throws ShortBufferException {
        if (this.wrapStream == null) {
            throw new IllegalStateException("not supported in a wrapping mode");
        }
        this.wrapStream.write(input, inputOffset, inputLen);
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen)
            throws IllegalBlockSizeException, BadPaddingException {
        if (this.wrapStream == null) {
            throw new IllegalStateException("not supported in a wrapping mode");
        }
        if (input != null) {
            this.wrapStream.write(input, inputOffset, inputLen);
        }
        try {
            if (this.forWrapping) {
                try {
                    return this.wrapEngine.wrap(
                            this.wrapStream.getBuf(), 0, this.wrapStream.size());
                } catch (Exception e) {
                    throw new IllegalBlockSizeException(e.getMessage());
                }
            }
            try {
                return this.wrapEngine.unwrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
            } catch (InvalidCipherTextException e2) {
                throw new BadPaddingException(e2.getMessage());
            }
        } finally {
            this.wrapStream.erase();
        }
        this.wrapStream.erase();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0044 A[Catch: all -> 0x0063, TRY_LEAVE, TryCatch #0 {all -> 0x0063, blocks: (B:5:0x0009, B:23:0x000e, B:10:0x003f, B:12:0x0044, B:16:0x004f, B:17:0x0057, B:9:0x002c, B:26:0x0022, B:27:0x002b, B:20:0x0059, B:21:0x0062), top: B:4:0x0009, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004f A[Catch: all -> 0x0063, TRY_ENTER, TryCatch #0 {all -> 0x0063, blocks: (B:5:0x0009, B:23:0x000e, B:10:0x003f, B:12:0x0044, B:16:0x004f, B:17:0x0057, B:9:0x002c, B:26:0x0022, B:27:0x002b, B:20:0x0059, B:21:0x0062), top: B:4:0x0009, inners: #1, #2 }] */
    @Override // javax.crypto.CipherSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int engineDoFinal(byte[] r5, int r6, int r7, byte[] r8, int r9)
            throws javax.crypto.IllegalBlockSizeException,
                    javax.crypto.BadPaddingException,
                    javax.crypto.ShortBufferException {
        /*
            r4 = this;
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r0 = r4.wrapStream
            if (r0 == 0) goto L6a
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r0 = r4.wrapStream
            r0.write(r5, r6, r7)
            boolean r0 = r4.forWrapping     // Catch: java.lang.Throwable -> L63
            r1 = 0
            if (r0 == 0) goto L2c
            com.android.internal.org.bouncycastle.crypto.Wrapper r0 = r4.wrapEngine     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L63
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r2 = r4.wrapStream     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L63
            byte[] r2 = r2.getBuf()     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L63
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r3 = r4.wrapStream     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L63
            int r3 = r3.size()     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L63
            byte[] r0 = r0.wrap(r2, r1, r3)     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L63
            goto L3f
        L21:
            r0 = move-exception
            javax.crypto.IllegalBlockSizeException r1 = new javax.crypto.IllegalBlockSizeException     // Catch: java.lang.Throwable -> L63
            java.lang.String r2 = r0.getMessage()     // Catch: java.lang.Throwable -> L63
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L63
            throw r1     // Catch: java.lang.Throwable -> L63
        L2c:
            com.android.internal.org.bouncycastle.crypto.Wrapper r0 = r4.wrapEngine     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L58 java.lang.Throwable -> L63
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r2 = r4.wrapStream     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L58 java.lang.Throwable -> L63
            byte[] r2 = r2.getBuf()     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L58 java.lang.Throwable -> L63
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r3 = r4.wrapStream     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L58 java.lang.Throwable -> L63
            int r3 = r3.size()     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L58 java.lang.Throwable -> L63
            byte[] r0 = r0.unwrap(r2, r1, r3)     // Catch: com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException -> L58 java.lang.Throwable -> L63
        L3f:
            int r2 = r0.length     // Catch: java.lang.Throwable -> L63
            int r2 = r2 + r9
            int r3 = r8.length     // Catch: java.lang.Throwable -> L63
            if (r2 > r3) goto L4f
            int r2 = r0.length     // Catch: java.lang.Throwable -> L63
            java.lang.System.arraycopy(r0, r1, r8, r9, r2)     // Catch: java.lang.Throwable -> L63
            int r1 = r0.length     // Catch: java.lang.Throwable -> L63
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r2 = r4.wrapStream
            r2.erase()
            return r1
        L4f:
            javax.crypto.ShortBufferException r1 = new javax.crypto.ShortBufferException     // Catch: java.lang.Throwable -> L63
            java.lang.String r2 = "output buffer too short for input."
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L63
            throw r1     // Catch: java.lang.Throwable -> L63
        L58:
            r0 = move-exception
            javax.crypto.BadPaddingException r1 = new javax.crypto.BadPaddingException     // Catch: java.lang.Throwable -> L63
            java.lang.String r2 = r0.getMessage()     // Catch: java.lang.Throwable -> L63
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L63
            throw r1     // Catch: java.lang.Throwable -> L63
        L63:
            r0 = move-exception
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher$ErasableOutputStream r1 = r4.wrapStream
            r1.erase()
            throw r0
        L6a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "not supported in a wrapping mode"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher.engineDoFinal(byte[],"
                    + " int, int, byte[], int):int");
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new InvalidKeyException("Cannot wrap key, null encoding.");
        }
        try {
            if (this.wrapEngine == null) {
                return engineDoFinal(encoded, 0, encoded.length);
            }
            return this.wrapEngine.wrap(encoded, 0, encoded.length);
        } catch (BadPaddingException e) {
            throw new IllegalBlockSizeException(e.getMessage());
        }
    }

    @Override // javax.crypto.CipherSpi
    protected Key engineUnwrap(byte[] wrappedKey, String wrappedKeyAlgorithm, int wrappedKeyType)
            throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] encoded;
        try {
            if (this.wrapEngine == null) {
                encoded = engineDoFinal(wrappedKey, 0, wrappedKey.length);
            } else {
                encoded = this.wrapEngine.unwrap(wrappedKey, 0, wrappedKey.length);
            }
            if (wrappedKeyType == 3) {
                return new SecretKeySpec(encoded, wrappedKeyAlgorithm);
            }
            if (wrappedKeyAlgorithm.equals("") && wrappedKeyType == 2) {
                try {
                    PrivateKeyInfo in = PrivateKeyInfo.getInstance(encoded);
                    PrivateKey privKey = BouncyCastleProvider.getPrivateKey(in);
                    if (privKey != null) {
                        return privKey;
                    }
                    throw new InvalidKeyException(
                            "algorithm "
                                    + in.getPrivateKeyAlgorithm().getAlgorithm()
                                    + " not supported");
                } catch (Exception e) {
                    throw new InvalidKeyException("Invalid key encoding.");
                }
            }
            try {
                KeyFactory kf = this.helper.createKeyFactory(wrappedKeyAlgorithm);
                if (wrappedKeyType == 1) {
                    return kf.generatePublic(new X509EncodedKeySpec(encoded));
                }
                if (wrappedKeyType == 2) {
                    return kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
                }
                throw new InvalidKeyException("Unknown key type " + wrappedKeyType);
            } catch (NoSuchProviderException e2) {
                throw new InvalidKeyException("Unknown key type " + e2.getMessage());
            } catch (InvalidKeySpecException e22) {
                throw new InvalidKeyException("Unknown key type " + e22.getMessage());
            }
        } catch (InvalidCipherTextException e3) {
            throw new InvalidKeyException(e3.getMessage());
        } catch (BadPaddingException e4) {
            throw new InvalidKeyException(e4.getMessage());
        } catch (IllegalBlockSizeException e23) {
            throw new InvalidKeyException(e23.getMessage());
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

        InvalidKeyOrParametersException(String msg, Throwable cause) {
            super(msg);
            this.cause = cause;
        }

        @Override // java.lang.Throwable
        public Throwable getCause() {
            return this.cause;
        }
    }
}
