package android.security.keystore2;

import android.hardware.security.keymint.KeyParameter;
import android.security.keystore.KeyProperties;
import android.system.keystore2.Authorization;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.util.List;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreRSACipherSpi extends AndroidKeyStoreCipherSpiBase {
    private final int mKeymasterPadding;
    private int mKeymasterPaddingOverride;
    private int mModulusSizeBytes = -1;

    protected boolean adjustConfigForEncryptingWithPrivateKey() {
        return false;
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetBlockSize() {
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineGetIV() {
        return null;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void loadAlgorithmSpecificParametersFromBeginResult(KeyParameter[] keyParameterArr) {
    }

    public static final class NoPadding extends AndroidKeyStoreRSACipherSpi {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
        protected AlgorithmParameters engineGetParameters() {
            return null;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForBegin() {
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForFinish() {
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters() throws InvalidKeyException {
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws Throwable {
            super.finalize();
        }

        public NoPadding() {
            super(1);
        }

        @Override // android.security.keystore2.AndroidKeyStoreRSACipherSpi
        protected boolean adjustConfigForEncryptingWithPrivateKey() {
            setKeymasterPurposeOverride(2);
            return true;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
            if (algorithmParameterSpec == null) {
                return;
            }
            throw new InvalidAlgorithmParameterException("Unexpected parameters: " + algorithmParameterSpec + ". No parameters supported");
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
            if (algorithmParameters == null) {
                return;
            }
            throw new InvalidAlgorithmParameterException("Unexpected parameters: " + algorithmParameters + ". No parameters supported");
        }
    }

    public static final class PKCS1Padding extends AndroidKeyStoreRSACipherSpi {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
        protected AlgorithmParameters engineGetParameters() {
            return null;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForBegin() {
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters() throws InvalidKeyException {
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws Throwable {
            super.finalize();
        }

        public PKCS1Padding() {
            super(4);
        }

        @Override // android.security.keystore2.AndroidKeyStoreRSACipherSpi
        protected boolean adjustConfigForEncryptingWithPrivateKey() {
            setKeymasterPurposeOverride(2);
            setKeymasterPaddingOverride(5);
            return true;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
            if (algorithmParameterSpec == null) {
                return;
            }
            throw new InvalidAlgorithmParameterException("Unexpected parameters: " + algorithmParameterSpec + ". No parameters supported");
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
            if (algorithmParameters == null) {
                return;
            }
            throw new InvalidAlgorithmParameterException("Unexpected parameters: " + algorithmParameters + ". No parameters supported");
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForFinish() {
            if (isEncrypting()) {
                return getModulusSizeBytes();
            }
            return 0;
        }
    }

    static abstract class OAEPWithMGF1Padding extends AndroidKeyStoreRSACipherSpi {
        private static final String MGF_ALGORITHM_MGF1 = "MGF1";
        private int mDigestOutputSizeBytes;
        private int mKeymasterDigest;
        private int mKeymasterMgf1Digest;

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForBegin() {
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters() throws InvalidKeyException {
        }

        OAEPWithMGF1Padding(int i) {
            super(2);
            this.mKeymasterMgf1Digest = 2;
            this.mKeymasterDigest = i;
            this.mDigestOutputSizeBytes = (KeymasterUtils.getDigestOutputSizeBits(i) + 7) / 8;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
            if (algorithmParameterSpec == null) {
                return;
            }
            if (!(algorithmParameterSpec instanceof OAEPParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Unsupported parameter spec: " + algorithmParameterSpec + ". Only OAEPParameterSpec supported");
            }
            OAEPParameterSpec oAEPParameterSpec = (OAEPParameterSpec) algorithmParameterSpec;
            if (!MGF_ALGORITHM_MGF1.equalsIgnoreCase(oAEPParameterSpec.getMGFAlgorithm())) {
                throw new InvalidAlgorithmParameterException("Unsupported MGF: " + oAEPParameterSpec.getMGFAlgorithm() + ". Only MGF1 supported");
            }
            String digestAlgorithm = oAEPParameterSpec.getDigestAlgorithm();
            try {
                int keymaster = KeyProperties.Digest.toKeymaster(digestAlgorithm);
                if (keymaster != 2 && keymaster != 3 && keymaster != 4 && keymaster != 5 && keymaster != 6) {
                    throw new InvalidAlgorithmParameterException("Unsupported digest: " + digestAlgorithm);
                }
                AlgorithmParameterSpec mGFParameters = oAEPParameterSpec.getMGFParameters();
                if (mGFParameters == null) {
                    throw new InvalidAlgorithmParameterException("MGF parameters must be provided");
                }
                if (!(mGFParameters instanceof MGF1ParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("Unsupported MGF parameters: " + mGFParameters + ". Only MGF1ParameterSpec supported");
                }
                String digestAlgorithm2 = ((MGF1ParameterSpec) mGFParameters).getDigestAlgorithm();
                PSource pSource = oAEPParameterSpec.getPSource();
                if (!(pSource instanceof PSource.PSpecified)) {
                    throw new InvalidAlgorithmParameterException("Unsupported source of encoding input P: " + pSource + ". Only pSpecifiedEmpty (PSource.PSpecified.DEFAULT) supported");
                }
                byte[] value = ((PSource.PSpecified) pSource).getValue();
                if (value != null && value.length > 0) {
                    throw new InvalidAlgorithmParameterException("Unsupported source of encoding input P: " + pSource + ". Only pSpecifiedEmpty (PSource.PSpecified.DEFAULT) supported");
                }
                this.mKeymasterDigest = keymaster;
                this.mKeymasterMgf1Digest = KeyProperties.Digest.toKeymaster(digestAlgorithm2);
                this.mDigestOutputSizeBytes = (KeymasterUtils.getDigestOutputSizeBits(keymaster) + 7) / 8;
            } catch (IllegalArgumentException e) {
                throw new InvalidAlgorithmParameterException("Unsupported digest: " + digestAlgorithm, e);
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
            if (algorithmParameters == null) {
                return;
            }
            try {
                OAEPParameterSpec oAEPParameterSpec = (OAEPParameterSpec) algorithmParameters.getParameterSpec(OAEPParameterSpec.class);
                if (oAEPParameterSpec == null) {
                    throw new InvalidAlgorithmParameterException("OAEP parameters required, but not provided in parameters: " + algorithmParameters);
                }
                initAlgorithmSpecificParameters(oAEPParameterSpec);
            } catch (InvalidParameterSpecException e) {
                throw new InvalidAlgorithmParameterException("OAEP parameters required, but not found in parameters: " + algorithmParameters, e);
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
        protected final AlgorithmParameters engineGetParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException {
            OAEPParameterSpec oAEPParameterSpec = new OAEPParameterSpec(KeyProperties.Digest.fromKeymaster(this.mKeymasterDigest), MGF_ALGORITHM_MGF1, KeyProperties.Digest.fromKeymasterToMGF1ParameterSpec(this.mKeymasterMgf1Digest), PSource.PSpecified.DEFAULT);
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("OAEP");
                algorithmParameters.init(oAEPParameterSpec);
                return algorithmParameters;
            } catch (NoSuchAlgorithmException e) {
                throw new ProviderException("Failed to obtain OAEP AlgorithmParameters", e);
            } catch (InvalidParameterSpecException e2) {
                throw new ProviderException("Failed to initialize OAEP AlgorithmParameters with an IV", e2);
            }
        }

        private static boolean isMgfDigestTagPresentInKeyProperties(Authorization[] authorizationArr) {
            for (Authorization authorization : authorizationArr) {
                if (authorization.keyParameter.tag == 536871115) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list, Authorization[] authorizationArr) {
            super.addAlgorithmSpecificParametersToBegin(list, authorizationArr);
            list.add(KeyStore2ParameterUtils.makeEnum(536870917, this.mKeymasterDigest));
            if (isMgfDigestTagPresentInKeyProperties(authorizationArr)) {
                list.add(KeyStore2ParameterUtils.makeEnum(536871115, this.mKeymasterMgf1Digest));
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreRSACipherSpi, android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void loadAlgorithmSpecificParametersFromBeginResult(KeyParameter[] keyParameterArr) {
            super.loadAlgorithmSpecificParametersFromBeginResult(keyParameterArr);
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForFinish() {
            if (isEncrypting()) {
                return this.mDigestOutputSizeBytes;
            }
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreRSACipherSpi, android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final String getTransform() {
            int i = this.mKeymasterDigest;
            if (i == 2) {
                return "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
            }
            if (i == 3) {
                return "RSA/ECB/OAEPWithSHA-224AndMGF1Padding";
            }
            if (i == 4) {
                return "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
            }
            if (i == 5) {
                return "RSA/ECB/OAEPWithSHA-384AndMGF1Padding";
            }
            if (i == 6) {
                return "RSA/ECB/OAEPWithSHA-512AndMGF1Padding";
            }
            return "RSA/ECB/OAEPPadding";
        }
    }

    public static class OAEPWithSHA1AndMGF1Padding extends OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws Throwable {
            super.finalize();
        }

        public OAEPWithSHA1AndMGF1Padding() {
            super(2);
        }
    }

    public static class OAEPWithSHA224AndMGF1Padding extends OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws Throwable {
            super.finalize();
        }

        public OAEPWithSHA224AndMGF1Padding() {
            super(3);
        }
    }

    public static class OAEPWithSHA256AndMGF1Padding extends OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws Throwable {
            super.finalize();
        }

        public OAEPWithSHA256AndMGF1Padding() {
            super(4);
        }
    }

    public static class OAEPWithSHA384AndMGF1Padding extends OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws Throwable {
            super.finalize();
        }

        public OAEPWithSHA384AndMGF1Padding() {
            super(5);
        }
    }

    public static class OAEPWithSHA512AndMGF1Padding extends OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws Throwable {
            super.finalize();
        }

        public OAEPWithSHA512AndMGF1Padding() {
            super(6);
        }
    }

    AndroidKeyStoreRSACipherSpi(int i) {
        this.mKeymasterPadding = i;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected String getTransform() {
        return "RSA/ECB/" + KeyProperties.EncryptionPadding.fromKeymaster(this.mKeymasterPadding);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0043  */
    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final void initKey(int i, Key key) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException("Unsupported key: null");
        }
        if (!"RSA".equalsIgnoreCase(key.getAlgorithm())) {
            throw new InvalidKeyException("Unsupported key algorithm: " + key.getAlgorithm() + ". Only RSA supported");
        }
        if ((key instanceof AndroidKeyStorePrivateKey) || (key instanceof AndroidKeyStorePublicKey)) {
            AndroidKeyStoreKey androidKeyStoreKey = (AndroidKeyStoreKey) key;
            if (androidKeyStoreKey instanceof PrivateKey) {
                if (i == 1) {
                    if (!adjustConfigForEncryptingWithPrivateKey()) {
                        throw new InvalidKeyException("RSA private keys cannot be used with " + opmodeToString(i) + " and padding " + KeyProperties.EncryptionPadding.fromKeymaster(this.mKeymasterPadding) + ". Only RSA public keys supported for this mode");
                    }
                } else if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            throw new InvalidKeyException("RSA private keys cannot be used with opmode: " + i);
                        }
                    }
                }
            } else if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            throw new InvalidKeyException("RSA public keys cannot be used with " + opmodeToString(i));
                        }
                    }
                }
                throw new InvalidKeyException("RSA public keys cannot be used with " + opmodeToString(i) + " and padding " + KeyProperties.EncryptionPadding.fromKeymaster(this.mKeymasterPadding) + ". Only RSA private keys supported for this opmode.");
            }
            long unsignedInt = -1;
            for (Authorization authorization : androidKeyStoreKey.getAuthorizations()) {
                if (authorization.keyParameter.tag == 805306371) {
                    unsignedInt = KeyStore2ParameterUtils.getUnsignedInt(authorization);
                }
            }
            if (unsignedInt == -1) {
                throw new InvalidKeyException("Size of key not known");
            }
            if (unsignedInt > 2147483647L) {
                throw new InvalidKeyException("Key too large: " + unsignedInt + " bits");
            }
            this.mModulusSizeBytes = (int) ((unsignedInt + 7) / 8);
            setKey(androidKeyStoreKey);
            return;
        }
        throw new InvalidKeyException("Unsupported key type: " + key);
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void resetAll() {
        this.mModulusSizeBytes = -1;
        this.mKeymasterPaddingOverride = -1;
        super.resetAll();
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void addAlgorithmSpecificParametersToBegin(List<KeyParameter> list) {
        list.add(KeyStore2ParameterUtils.makeEnum(268435458, 1));
        int keymasterPaddingOverride = getKeymasterPaddingOverride();
        if (keymasterPaddingOverride == -1) {
            keymasterPaddingOverride = this.mKeymasterPadding;
        }
        list.add(KeyStore2ParameterUtils.makeEnum(536870918, keymasterPaddingOverride));
        int keymasterPurposeOverride = getKeymasterPurposeOverride();
        if (keymasterPurposeOverride != -1) {
            if (keymasterPurposeOverride == 2 || keymasterPurposeOverride == 3) {
                list.add(KeyStore2ParameterUtils.makeEnum(536870917, 0));
            }
        }
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetOutputSize(int i) {
        return getModulusSizeBytes();
    }

    protected final int getModulusSizeBytes() {
        int i = this.mModulusSizeBytes;
        if (i != -1) {
            return i;
        }
        throw new IllegalStateException("Not initialized");
    }

    protected final void setKeymasterPaddingOverride(int i) {
        this.mKeymasterPaddingOverride = i;
    }

    protected final int getKeymasterPaddingOverride() {
        return this.mKeymasterPaddingOverride;
    }
}
