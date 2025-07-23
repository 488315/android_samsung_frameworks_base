package android.security.keystore2;

import android.hardware.security.keymint.KeyParameter;
import java.security.InvalidKeyException;
import java.util.List;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreRSASignatureSpi extends AndroidKeyStoreSignatureSpiBase {
    private final int mKeymasterDigest;
    private final int mKeymasterPadding;

    static abstract class PKCS1Padding extends AndroidKeyStoreRSASignatureSpi {
        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected final int getAdditionalEntropyAmountForSign() {
            return 0;
        }

        PKCS1Padding(int i) {
            super(i, 5);
        }
    }

    public static final class NONEWithPKCS1Padding extends PKCS1Padding {
        public NONEWithPKCS1Padding() {
            super(0);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "NONEwithRSA";
        }
    }

    public static final class MD5WithPKCS1Padding extends PKCS1Padding {
        public MD5WithPKCS1Padding() {
            super(1);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "MD5withRSA";
        }
    }

    public static final class SHA1WithPKCS1Padding extends PKCS1Padding {
        public SHA1WithPKCS1Padding() {
            super(2);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA1withRSA";
        }
    }

    public static final class SHA224WithPKCS1Padding extends PKCS1Padding {
        public SHA224WithPKCS1Padding() {
            super(3);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA224withRSA";
        }
    }

    public static final class SHA256WithPKCS1Padding extends PKCS1Padding {
        public SHA256WithPKCS1Padding() {
            super(4);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA256withRSA";
        }
    }

    public static final class SHA384WithPKCS1Padding extends PKCS1Padding {
        public SHA384WithPKCS1Padding() {
            super(5);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA384withRSA";
        }
    }

    public static final class SHA512WithPKCS1Padding extends PKCS1Padding {
        public SHA512WithPKCS1Padding() {
            super(6);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA512withRSA";
        }
    }

    static abstract class PSSPadding extends AndroidKeyStoreRSASignatureSpi {
        private static final int SALT_LENGTH_BYTES = 20;

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected final int getAdditionalEntropyAmountForSign() {
            return 20;
        }

        PSSPadding(int i) {
            super(i, 3);
        }
    }

    public static final class SHA1WithPSSPadding extends PSSPadding {
        public SHA1WithPSSPadding() {
            super(2);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA1withRSA/PSS";
        }
    }

    public static final class SHA224WithPSSPadding extends PSSPadding {
        public SHA224WithPSSPadding() {
            super(3);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA224withRSA/PSS";
        }
    }

    public static final class SHA256WithPSSPadding extends PSSPadding {
        public SHA256WithPSSPadding() {
            super(4);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA256withRSA/PSS";
        }
    }

    public static final class SHA384WithPSSPadding extends PSSPadding {
        public SHA384WithPSSPadding() {
            super(5);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA384withRSA/PSS";
        }
    }

    public static final class SHA512WithPSSPadding extends PSSPadding {
        public SHA512WithPSSPadding() {
            super(6);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected String getAlgorithm() {
            return "SHA512withRSA/PSS";
        }
    }

    AndroidKeyStoreRSASignatureSpi(int i, int i2) {
        this.mKeymasterDigest = i;
        this.mKeymasterPadding = i2;
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void initKey(AndroidKeyStoreKey androidKeyStoreKey) throws InvalidKeyException {
        if (!"RSA".equalsIgnoreCase(androidKeyStoreKey.getAlgorithm())) {
            throw new InvalidKeyException("Unsupported key algorithm: " + androidKeyStoreKey.getAlgorithm() + ". Only RSA supported");
        }
        super.initKey(androidKeyStoreKey);
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void resetAll() {
        super.resetAll();
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list) {
        list.add(KeyStore2ParameterUtils.makeEnum(268435458, 1));
        list.add(KeyStore2ParameterUtils.makeEnum(536870917, this.mKeymasterDigest));
        list.add(KeyStore2ParameterUtils.makeEnum(536870918, this.mKeymasterPadding));
    }
}
