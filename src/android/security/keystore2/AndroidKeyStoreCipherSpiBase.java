package android.security.keystore2;

import android.hardware.security.keymint.KeyParameter;
import android.os.StrictMode;
import android.security.KeyStoreException;
import android.security.KeyStoreOperation;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyStoreCryptoOperation;
import android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer;
import android.system.keystore2.Authorization;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreCipherSpiBase extends CipherSpi implements KeyStoreCryptoOperation {
    public static final String DEFAULT_MGF1_DIGEST = "SHA-1";
    private static final String TAG = "AndroidKeyStoreCipherSpiBase";
    private KeyStoreOperation mOperation = null;
    private boolean mEncrypting = false;
    private int mKeymasterPurposeOverride = -1;
    private AndroidKeyStoreKey mKey = null;
    private SecureRandom mRng = null;
    private long mOperationChallenge = 0;
    private KeyStoreCryptoOperationStreamer mMainDataStreamer = null;
    private KeyStoreCryptoOperationStreamer mAdditionalAuthenticationDataStreamer = null;
    private boolean mAdditionalAuthenticationDataStreamerClosed = false;
    private Exception mCachedException = null;
    private Cipher mCipher = null;

    protected abstract void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list);

    protected KeyStoreCryptoOperationStreamer createAdditionalAuthenticationDataStreamer(KeyStoreOperation keyStoreOperation) {
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected abstract AlgorithmParameters engineGetParameters();

    protected abstract int getAdditionalEntropyAmountForBegin();

    protected abstract int getAdditionalEntropyAmountForFinish();

    protected abstract String getTransform();

    protected abstract void initAlgorithmSpecificParameters() throws InvalidKeyException;

    protected abstract void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException;

    protected abstract void initAlgorithmSpecificParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException;

    protected abstract void initKey(int i, Key key) throws InvalidKeyException;

    protected abstract void loadAlgorithmSpecificParametersFromBeginResult(KeyParameter[] keyParameterArr);

    AndroidKeyStoreCipherSpiBase() {
    }

    private Authorization[] getKeyCharacteristics(Key key) {
        if (!(key instanceof AndroidKeyStoreKey)) {
            return new Authorization[0];
        }
        return ((AndroidKeyStoreKey) key).getAuthorizations();
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        resetAll();
        if (!(key instanceof AndroidKeyStorePrivateKey) && ((key instanceof PrivateKey) || (key instanceof PublicKey))) {
            try {
                StrictMode.noteSlowCall("engineInit");
                this.mCipher = Cipher.getInstance(getTransform());
                String transform = getTransform();
                if ("RSA/ECB/OAEPWithSHA-224AndMGF1Padding".equals(transform)) {
                    this.mCipher.init(i, key, new OAEPParameterSpec(KeyProperties.DIGEST_SHA224, "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT), secureRandom);
                    return;
                }
                if ("RSA/ECB/OAEPWithSHA-256AndMGF1Padding".equals(transform)) {
                    this.mCipher.init(i, key, new OAEPParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT), secureRandom);
                    return;
                } else if ("RSA/ECB/OAEPWithSHA-384AndMGF1Padding".equals(transform)) {
                    this.mCipher.init(i, key, new OAEPParameterSpec(KeyProperties.DIGEST_SHA384, "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT), secureRandom);
                    return;
                } else if ("RSA/ECB/OAEPWithSHA-512AndMGF1Padding".equals(transform)) {
                    this.mCipher.init(i, key, new OAEPParameterSpec(KeyProperties.DIGEST_SHA512, "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT), secureRandom);
                    return;
                } else {
                    this.mCipher.init(i, key, secureRandom);
                    return;
                }
            } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException e) {
                throw new InvalidKeyException(e);
            }
        }
        try {
            init(i, key, secureRandom);
            initAlgorithmSpecificParameters();
            try {
                ensureKeystoreOperationInitialized(getKeyCharacteristics(key));
            } catch (InvalidAlgorithmParameterException e2) {
                throw new InvalidKeyException(e2);
            }
        } catch (Throwable th) {
            resetAll();
            throw th;
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        resetAll();
        if (!(key instanceof AndroidKeyStorePrivateKey) && ((key instanceof PrivateKey) || (key instanceof PublicKey))) {
            try {
                StrictMode.noteSlowCall("engineInit");
                Cipher cipher = Cipher.getInstance(getTransform());
                this.mCipher = cipher;
                cipher.init(i, key, algorithmParameters, secureRandom);
                return;
            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                throw new InvalidKeyException(e);
            }
        }
        try {
            init(i, key, secureRandom);
            initAlgorithmSpecificParameters(algorithmParameters);
            ensureKeystoreOperationInitialized(getKeyCharacteristics(key));
        } catch (Throwable th) {
            resetAll();
            throw th;
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        resetAll();
        if (!(key instanceof AndroidKeyStorePrivateKey) && ((key instanceof PrivateKey) || (key instanceof PublicKey))) {
            try {
                StrictMode.noteSlowCall("engineInit");
                Cipher cipher = Cipher.getInstance(getTransform());
                this.mCipher = cipher;
                cipher.init(i, key, algorithmParameterSpec, secureRandom);
                return;
            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                throw new InvalidKeyException(e);
            }
        }
        try {
            init(i, key, secureRandom);
            initAlgorithmSpecificParameters(algorithmParameterSpec);
            ensureKeystoreOperationInitialized(getKeyCharacteristics(key));
        } catch (Throwable th) {
            resetAll();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void init(int r3, java.security.Key r4, java.security.SecureRandom r5) throws java.security.InvalidKeyException {
        /*
            r2 = this;
            r0 = 1
            if (r3 == r0) goto L25
            r1 = 2
            if (r3 == r1) goto L21
            r1 = 3
            if (r3 == r1) goto L25
            r0 = 4
            if (r3 != r0) goto Ld
            goto L21
        Ld:
            java.security.InvalidParameterException r2 = new java.security.InvalidParameterException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Unsupported opmode: "
            r4.<init>(r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            r2.<init>(r3)
            throw r2
        L21:
            r0 = 0
            r2.mEncrypting = r0
            goto L27
        L25:
            r2.mEncrypting = r0
        L27:
            r2.initKey(r3, r4)
            android.security.keystore2.AndroidKeyStoreKey r3 = r2.mKey
            if (r3 == 0) goto L31
            r2.mRng = r5
            return
        L31:
            java.security.ProviderException r2 = new java.security.ProviderException
            java.lang.String r3 = "initKey did not initialize the key"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.keystore2.AndroidKeyStoreCipherSpiBase.init(int, java.security.Key, java.security.SecureRandom):void");
    }

    private void abortOperation() {
        KeyStoreCryptoOperationUtils.abortOperation(this.mOperation);
        this.mOperation = null;
    }

    protected void resetAll() {
        abortOperation();
        this.mEncrypting = false;
        this.mKeymasterPurposeOverride = -1;
        this.mKey = null;
        this.mRng = null;
        this.mOperationChallenge = 0L;
        this.mMainDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamerClosed = false;
        this.mCachedException = null;
        this.mCipher = null;
    }

    protected void resetWhilePreservingInitState() {
        abortOperation();
        this.mOperationChallenge = 0L;
        this.mMainDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamerClosed = false;
        this.mCachedException = null;
    }

    private void ensureKeystoreOperationInitialized(Authorization[] authorizationArr) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.mMainDataStreamer == null && this.mCachedException == null) {
            if (this.mKey == null) {
                throw new IllegalStateException("Not initialized");
            }
            ArrayList arrayList = new ArrayList();
            addAlgorithmSpecificParametersToBegin(arrayList, authorizationArr);
            int i = this.mKeymasterPurposeOverride;
            if (i == -1) {
                i = !this.mEncrypting ? 1 : 0;
            }
            arrayList.add(KeyStore2ParameterUtils.makeEnum(536870913, i));
            try {
                StrictMode.noteDiskRead();
                KeyStoreOperation createOperation = this.mKey.getSecurityLevel().createOperation(this.mKey.getKeyIdDescriptor(), arrayList);
                this.mOperation = createOperation;
                this.mOperationChallenge = KeyStoreCryptoOperationUtils.getOrMakeOperationChallenge(createOperation, this.mKey);
                loadAlgorithmSpecificParametersFromBeginResult(this.mOperation.getParameters());
                this.mMainDataStreamer = createMainDataStreamer(this.mOperation);
                this.mAdditionalAuthenticationDataStreamer = createAdditionalAuthenticationDataStreamer(this.mOperation);
                this.mAdditionalAuthenticationDataStreamerClosed = false;
            } catch (KeyStoreException e) {
                GeneralSecurityException exceptionForCipherInit = KeyStoreCryptoOperationUtils.getExceptionForCipherInit(this.mKey, e);
                if (exceptionForCipherInit instanceof InvalidKeyException) {
                    throw ((InvalidKeyException) exceptionForCipherInit);
                }
                if (exceptionForCipherInit instanceof InvalidAlgorithmParameterException) {
                    throw ((InvalidAlgorithmParameterException) exceptionForCipherInit);
                }
                throw new ProviderException("Unexpected exception type", exceptionForCipherInit);
            }
        }
    }

    protected KeyStoreCryptoOperationStreamer createMainDataStreamer(KeyStoreOperation keyStoreOperation) {
        return new KeyStoreCryptoOperationChunkedStreamer(new KeyStoreCryptoOperationChunkedStreamer.MainDataStream(keyStoreOperation), 0);
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineUpdate(byte[] bArr, int i, int i2) {
        Cipher cipher = this.mCipher;
        if (cipher != null) {
            return cipher.update(bArr, i, i2);
        }
        if (this.mCachedException != null) {
            return null;
        }
        try {
            ensureKeystoreOperationInitialized(getKeyCharacteristics(this.mKey));
            if (i2 == 0) {
                return null;
            }
            try {
                flushAAD();
                byte[] update = this.mMainDataStreamer.update(bArr, i, i2);
                if (update.length == 0) {
                    return null;
                }
                return update;
            } catch (KeyStoreException e) {
                this.mCachedException = e;
                return null;
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e2) {
            this.mCachedException = e2;
            return null;
        }
    }

    private void flushAAD() throws KeyStoreException {
        KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer = this.mAdditionalAuthenticationDataStreamer;
        if (keyStoreCryptoOperationStreamer == null || this.mAdditionalAuthenticationDataStreamerClosed) {
            return;
        }
        try {
            byte[] doFinal = keyStoreCryptoOperationStreamer.doFinal(EmptyArray.BYTE, 0, 0, null);
            if (doFinal == null || doFinal.length <= 0) {
                return;
            }
            throw new ProviderException("AAD update unexpectedly returned data: " + doFinal.length + " bytes");
        } finally {
            this.mAdditionalAuthenticationDataStreamerClosed = true;
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException {
        Cipher cipher = this.mCipher;
        if (cipher != null) {
            return cipher.update(bArr, i, i2, bArr2);
        }
        byte[] engineUpdate = engineUpdate(bArr, i, i2);
        if (engineUpdate == null) {
            return 0;
        }
        int length = bArr2.length - i3;
        if (engineUpdate.length > length) {
            throw new ShortBufferException("Output buffer too short. Produced: " + engineUpdate.length + ", available: " + length);
        }
        System.arraycopy(engineUpdate, 0, bArr2, i3, engineUpdate.length);
        return engineUpdate.length;
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineUpdate(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws ShortBufferException {
        byte[] engineUpdate;
        Cipher cipher = this.mCipher;
        if (cipher != null) {
            return cipher.update(byteBuffer, byteBuffer2);
        }
        if (byteBuffer == null) {
            throw new NullPointerException("input == null");
        }
        if (byteBuffer2 == null) {
            throw new NullPointerException("output == null");
        }
        int remaining = byteBuffer.remaining();
        if (byteBuffer.hasArray()) {
            engineUpdate = engineUpdate(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), remaining);
            byteBuffer.position(byteBuffer.position() + remaining);
        } else {
            byte[] bArr = new byte[remaining];
            byteBuffer.get(bArr);
            engineUpdate = engineUpdate(bArr, 0, remaining);
        }
        int length = engineUpdate != null ? engineUpdate.length : 0;
        if (length <= 0) {
            return length;
        }
        int remaining2 = byteBuffer2.remaining();
        try {
            byteBuffer2.put(engineUpdate);
            return length;
        } catch (BufferOverflowException unused) {
            throw new ShortBufferException("Output buffer too small. Produced: " + length + ", available: " + remaining2);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineUpdateAAD(byte[] bArr, int i, int i2) {
        if (this.mCipher != null) {
            StrictMode.noteSlowCall("engineUpdateAAD");
            this.mCipher.updateAAD(bArr, i, i2);
            return;
        }
        if (this.mCachedException != null) {
            return;
        }
        try {
            ensureKeystoreOperationInitialized(getKeyCharacteristics(this.mKey));
            if (this.mAdditionalAuthenticationDataStreamerClosed) {
                throw new IllegalStateException("AAD can only be provided before Cipher.update is invoked");
            }
            KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer = this.mAdditionalAuthenticationDataStreamer;
            if (keyStoreCryptoOperationStreamer == null) {
                throw new IllegalStateException("This cipher does not support AAD");
            }
            try {
                byte[] update = keyStoreCryptoOperationStreamer.update(bArr, i, i2);
                if (update == null || update.length <= 0) {
                    return;
                }
                throw new ProviderException("AAD update unexpectedly produced output: " + update.length + " bytes");
            } catch (KeyStoreException e) {
                this.mCachedException = e;
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e2) {
            this.mCachedException = e2;
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineUpdateAAD(ByteBuffer byteBuffer) {
        int remaining;
        byte[] bArr;
        int i;
        if (this.mCipher != null) {
            StrictMode.noteSlowCall("engineUpdateAAD");
            this.mCipher.updateAAD(byteBuffer);
            return;
        }
        if (byteBuffer == null) {
            throw new IllegalArgumentException("src == null");
        }
        if (byteBuffer.hasRemaining()) {
            if (byteBuffer.hasArray()) {
                bArr = byteBuffer.array();
                i = byteBuffer.arrayOffset() + byteBuffer.position();
                remaining = byteBuffer.remaining();
                byteBuffer.position(byteBuffer.limit());
            } else {
                remaining = byteBuffer.remaining();
                bArr = new byte[remaining];
                byteBuffer.get(bArr);
                i = 0;
            }
            engineUpdateAAD(bArr, i, remaining);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineDoFinal(byte[] bArr, int i, int i2) throws IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = this.mCipher;
        if (cipher != null) {
            if (bArr == null && i2 == 0) {
                return cipher.doFinal();
            }
            return cipher.doFinal(bArr, i, i2);
        }
        if (this.mCachedException != null) {
            throw ((IllegalBlockSizeException) new IllegalBlockSizeException().initCause(this.mCachedException));
        }
        try {
            ensureKeystoreOperationInitialized(getKeyCharacteristics(this.mKey));
            try {
                flushAAD();
                byte[] doFinal = this.mMainDataStreamer.doFinal(bArr, i, i2, null);
                resetWhilePreservingInitState();
                return doFinal;
            } catch (KeyStoreException e) {
                int errorCode = e.getErrorCode();
                if (errorCode == -38) {
                    throw ((BadPaddingException) new BadPaddingException().initCause(e));
                }
                if (errorCode == -30) {
                    throw ((AEADBadTagException) new AEADBadTagException().initCause(e));
                }
                throw ((IllegalBlockSizeException) new IllegalBlockSizeException().initCause(e));
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e2) {
            throw ((IllegalBlockSizeException) new IllegalBlockSizeException().initCause(e2));
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = this.mCipher;
        if (cipher != null) {
            return cipher.doFinal(bArr, i, i2, bArr2);
        }
        byte[] engineDoFinal = engineDoFinal(bArr, i, i2);
        if (engineDoFinal == null) {
            return 0;
        }
        int length = bArr2.length - i3;
        if (engineDoFinal.length > length) {
            throw new ShortBufferException("Output buffer too short. Produced: " + engineDoFinal.length + ", available: " + length);
        }
        System.arraycopy(engineDoFinal, 0, bArr2, i3, engineDoFinal.length);
        return engineDoFinal.length;
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineDoFinal(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        byte[] engineDoFinal;
        Cipher cipher = this.mCipher;
        if (cipher != null) {
            return cipher.doFinal(byteBuffer, byteBuffer2);
        }
        if (byteBuffer == null) {
            throw new NullPointerException("input == null");
        }
        if (byteBuffer2 == null) {
            throw new NullPointerException("output == null");
        }
        int remaining = byteBuffer.remaining();
        if (byteBuffer.hasArray()) {
            engineDoFinal = engineDoFinal(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), remaining);
            byteBuffer.position(byteBuffer.position() + remaining);
        } else {
            byte[] bArr = new byte[remaining];
            byteBuffer.get(bArr);
            engineDoFinal = engineDoFinal(bArr, 0, remaining);
        }
        int length = engineDoFinal != null ? engineDoFinal.length : 0;
        if (length <= 0) {
            return length;
        }
        int remaining2 = byteBuffer2.remaining();
        try {
            byteBuffer2.put(engineDoFinal);
            return length;
        } catch (BufferOverflowException unused) {
            throw new ShortBufferException("Output buffer too small. Produced: " + length + ", available: " + remaining2);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        byte[] encoded;
        Cipher cipher = this.mCipher;
        if (cipher != null) {
            return cipher.wrap(key);
        }
        if (this.mKey == null) {
            throw new IllegalStateException("Not initilized");
        }
        if (!isEncrypting()) {
            throw new IllegalStateException("Cipher must be initialized in Cipher.WRAP_MODE to wrap keys");
        }
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        StrictMode.noteSlowCall("engineWrap");
        if (key instanceof SecretKey) {
            encoded = "RAW".equalsIgnoreCase(key.getFormat()) ? key.getEncoded() : null;
            if (encoded == null) {
                try {
                    encoded = ((SecretKeySpec) SecretKeyFactory.getInstance(key.getAlgorithm()).getKeySpec((SecretKey) key, SecretKeySpec.class)).getEncoded();
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new InvalidKeyException("Failed to wrap key because it does not export its key material", e);
                }
            }
        } else if (key instanceof PrivateKey) {
            encoded = "PKCS8".equalsIgnoreCase(key.getFormat()) ? key.getEncoded() : null;
            if (encoded == null) {
                try {
                    encoded = ((PKCS8EncodedKeySpec) KeyFactory.getInstance(key.getAlgorithm()).getKeySpec(key, PKCS8EncodedKeySpec.class)).getEncoded();
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e2) {
                    throw new InvalidKeyException("Failed to wrap key because it does not export its key material", e2);
                }
            }
        } else if (key instanceof PublicKey) {
            encoded = "X.509".equalsIgnoreCase(key.getFormat()) ? key.getEncoded() : null;
            if (encoded == null) {
                try {
                    encoded = ((X509EncodedKeySpec) KeyFactory.getInstance(key.getAlgorithm()).getKeySpec(key, X509EncodedKeySpec.class)).getEncoded();
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e3) {
                    throw new InvalidKeyException("Failed to wrap key because it does not export its key material", e3);
                }
            }
        } else {
            throw new InvalidKeyException("Unsupported key type: " + key.getClass().getName());
        }
        if (encoded == null) {
            throw new InvalidKeyException("Failed to wrap key because it does not export its key material");
        }
        try {
            return engineDoFinal(encoded, 0, encoded.length);
        } catch (BadPaddingException e4) {
            throw ((IllegalBlockSizeException) new IllegalBlockSizeException().initCause(e4));
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final Key engineUnwrap(byte[] bArr, String str, int i) throws InvalidKeyException, NoSuchAlgorithmException {
        Cipher cipher = this.mCipher;
        if (cipher != null) {
            return cipher.unwrap(bArr, str, i);
        }
        if (this.mKey == null) {
            throw new IllegalStateException("Not initilized");
        }
        if (isEncrypting()) {
            throw new IllegalStateException("Cipher must be initialized in Cipher.WRAP_MODE to wrap keys");
        }
        if (bArr == null) {
            throw new NullPointerException("wrappedKey == null");
        }
        try {
            byte[] engineDoFinal = engineDoFinal(bArr, 0, bArr.length);
            StrictMode.noteSlowCall("engineUnwrap");
            if (i == 1) {
                try {
                    return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(engineDoFinal));
                } catch (InvalidKeySpecException e) {
                    throw new InvalidKeyException("Failed to create public key from its X.509 encoded form", e);
                }
            }
            if (i == 2) {
                try {
                    return KeyFactory.getInstance(str).generatePrivate(new PKCS8EncodedKeySpec(engineDoFinal));
                } catch (InvalidKeySpecException e2) {
                    throw new InvalidKeyException("Failed to create private key from its PKCS#8 encoded form", e2);
                }
            }
            if (i == 3) {
                return new SecretKeySpec(engineDoFinal, str);
            }
            throw new InvalidParameterException("Unsupported wrappedKeyType: " + i);
        } catch (BadPaddingException | IllegalBlockSizeException e3) {
            throw new InvalidKeyException("Failed to unwrap key", e3);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineSetMode(String str) throws NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override // javax.crypto.CipherSpi
    protected final void engineSetPadding(String str) throws NoSuchPaddingException {
        throw new UnsupportedOperationException();
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetKeySize(Key key) throws InvalidKeyException {
        throw new UnsupportedOperationException();
    }

    public void finalize() throws Throwable {
        try {
            abortOperation();
        } finally {
            super.finalize();
        }
    }

    @Override // android.security.keystore.KeyStoreCryptoOperation
    public final long getOperationHandle() {
        return this.mOperationChallenge;
    }

    protected final void setKey(AndroidKeyStoreKey androidKeyStoreKey) {
        this.mKey = androidKeyStoreKey;
    }

    protected final void setKeymasterPurposeOverride(int i) {
        this.mKeymasterPurposeOverride = i;
    }

    protected final int getKeymasterPurposeOverride() {
        return this.mKeymasterPurposeOverride;
    }

    protected final boolean isEncrypting() {
        return this.mEncrypting;
    }

    protected final long getConsumedInputSizeBytes() {
        KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer = this.mMainDataStreamer;
        if (keyStoreCryptoOperationStreamer == null) {
            throw new IllegalStateException("Not initialized");
        }
        return keyStoreCryptoOperationStreamer.getConsumedInputSizeBytes();
    }

    protected final long getProducedOutputSizeBytes() {
        KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer = this.mMainDataStreamer;
        if (keyStoreCryptoOperationStreamer == null) {
            throw new IllegalStateException("Not initialized");
        }
        return keyStoreCryptoOperationStreamer.getProducedOutputSizeBytes();
    }

    static String opmodeToString(int i) {
        if (i == 1) {
            return "ENCRYPT_MODE";
        }
        if (i == 2) {
            return "DECRYPT_MODE";
        }
        if (i == 3) {
            return "WRAP_MODE";
        }
        if (i == 4) {
            return "UNWRAP_MODE";
        }
        return String.valueOf(i);
    }

    protected final int getKeySecurityLevel() {
        try {
            return ((KeyInfo) KeyFactory.getInstance(this.mKey.getAlgorithm(), AndroidKeyStoreSpi.NAME).getKeySpec(this.mKey, KeyInfo.class)).getSecurityLevel();
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }

    protected void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list, Authorization[] authorizationArr) {
        addAlgorithmSpecificParametersToBegin(list);
    }
}
