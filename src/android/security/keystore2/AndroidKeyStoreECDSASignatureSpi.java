package android.security.keystore2;

import android.hardware.security.keymint.KeyParameter;
import android.security.KeyStoreException;
import android.security.KeyStoreOperation;
import android.security.keystore.KeyProperties;
import android.system.keystore2.Authorization;
import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.spec.NamedParameterSpec;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreECDSASignatureSpi extends AndroidKeyStoreSignatureSpiBase {
    private static final Set<String> ACCEPTED_SIGNING_SCHEMES = Set.of(KeyProperties.KEY_ALGORITHM_EC.toLowerCase(), NamedParameterSpec.ED25519.getName().toLowerCase(), "eddsa");
    private int mGroupSizeBits = -1;
    private final int mKeymasterDigest;

    public static final class NONE extends AndroidKeyStoreECDSASignatureSpi {
        public NONE() {
            super(0);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "NONEwithECDSA";
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected KeyStoreCryptoOperationStreamer createMainDataStreamer(KeyStoreOperation keyStoreOperation) {
            return new TruncateToFieldSizeMessageStreamer(super.createMainDataStreamer(keyStoreOperation), getGroupSizeBits());
        }

        private static class TruncateToFieldSizeMessageStreamer implements KeyStoreCryptoOperationStreamer {
            private long mConsumedInputSizeBytes;
            private final KeyStoreCryptoOperationStreamer mDelegate;
            private final int mGroupSizeBits;
            private final ByteArrayOutputStream mInputBuffer;

            private TruncateToFieldSizeMessageStreamer(KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer, int i) {
                this.mInputBuffer = new ByteArrayOutputStream();
                this.mDelegate = keyStoreCryptoOperationStreamer;
                this.mGroupSizeBits = i;
            }

            @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
            public byte[] update(byte[] bArr, int i, int i2) throws KeyStoreException {
                if (i2 > 0) {
                    this.mInputBuffer.write(bArr, i, i2);
                    this.mConsumedInputSizeBytes += i2;
                }
                return EmptyArray.BYTE;
            }

            @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
            public byte[] doFinal(byte[] bArr, int i, int i2, byte[] bArr2) throws KeyStoreException {
                if (i2 > 0) {
                    this.mConsumedInputSizeBytes += i2;
                    this.mInputBuffer.write(bArr, i, i2);
                }
                byte[] byteArray = this.mInputBuffer.toByteArray();
                this.mInputBuffer.reset();
                return this.mDelegate.doFinal(byteArray, 0, Math.min(byteArray.length, (this.mGroupSizeBits + 7) / 8), bArr2);
            }

            @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
            public long getConsumedInputSizeBytes() {
                return this.mConsumedInputSizeBytes;
            }

            @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
            public long getProducedOutputSizeBytes() {
                return this.mDelegate.getProducedOutputSizeBytes();
            }
        }
    }

    public static final class Ed25519 extends AndroidKeyStoreECDSASignatureSpi {
        public Ed25519() {
            super(0);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return NamedParameterSpec.ED25519.getName();
        }
    }

    public static final class SHA1 extends AndroidKeyStoreECDSASignatureSpi {
        public SHA1() {
            super(2);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA1withECDSA";
        }
    }

    public static final class SHA224 extends AndroidKeyStoreECDSASignatureSpi {
        public SHA224() {
            super(3);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA224withECDSA";
        }
    }

    public static final class SHA256 extends AndroidKeyStoreECDSASignatureSpi {
        public SHA256() {
            super(4);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA256withECDSA";
        }
    }

    public static final class SHA384 extends AndroidKeyStoreECDSASignatureSpi {
        public SHA384() {
            super(5);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA384withECDSA";
        }
    }

    public static final class SHA512 extends AndroidKeyStoreECDSASignatureSpi {
        public SHA512() {
            super(6);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA512withECDSA";
        }
    }

    AndroidKeyStoreECDSASignatureSpi(int i) {
        this.mKeymasterDigest = i;
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void initKey(AndroidKeyStoreKey androidKeyStoreKey) throws InvalidKeyException {
        long j;
        Set<String> set = ACCEPTED_SIGNING_SCHEMES;
        if (!set.contains(androidKeyStoreKey.getAlgorithm().toLowerCase())) {
            throw new InvalidKeyException("Unsupported key algorithm: " + androidKeyStoreKey.getAlgorithm() + ". Only " + Arrays.toString(set.stream().toArray()) + " supported");
        }
        Authorization[] authorizations = androidKeyStoreKey.getAuthorizations();
        int length = authorizations.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                j = -1;
                break;
            }
            Authorization authorization = authorizations[i];
            if (authorization.keyParameter.tag == 805306371) {
                j = KeyStore2ParameterUtils.getUnsignedInt(authorization);
                break;
            } else {
                if (authorization.keyParameter.tag == 268435466) {
                    j = KeyProperties.EcCurve.fromKeymasterCurve(authorization.keyParameter.value.getEcCurve());
                    break;
                }
                i++;
            }
        }
        if (j == -1) {
            throw new InvalidKeyException("Size of key not known");
        }
        if (j > 2147483647L) {
            throw new InvalidKeyException("Key too large: " + j + " bits");
        }
        this.mGroupSizeBits = (int) j;
        super.initKey(androidKeyStoreKey);
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void resetAll() {
        this.mGroupSizeBits = -1;
        super.resetAll();
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list) {
        list.add(KeyStore2ParameterUtils.makeEnum(268435458, 3));
        list.add(KeyStore2ParameterUtils.makeEnum(536870917, this.mKeymasterDigest));
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final int getAdditionalEntropyAmountForSign() {
        return (this.mGroupSizeBits + 7) / 8;
    }

    protected final int getGroupSizeBits() {
        int i = this.mGroupSizeBits;
        if (i != -1) {
            return i;
        }
        throw new IllegalStateException("Not initialized");
    }
}
