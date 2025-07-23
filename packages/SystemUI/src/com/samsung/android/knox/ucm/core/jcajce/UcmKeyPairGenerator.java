package com.samsung.android.knox.ucm.core.jcajce;

import android.os.Bundle;
import android.os.Process;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class UcmKeyPairGenerator extends KeyPairGeneratorSpi {
    public String mAlgorithm;
    public String mAlias;
    public String[] mBlockModes;
    public String[] mDigests;
    public String mEcCurveName;
    public KeyGenParameterSpec mKeyGenParamSpec;
    public KeyPairGeneratorSpec mKeyPairGenSpec;
    public int mKeySize;
    public int mPurpose = 0;
    public String[] mSignaturePaddings;
    public String mSource;

    public UcmKeyPairGenerator(String str, String str2) {
        this.mSource = str;
        this.mAlgorithm = str2;
    }

    public static String getDefaultSignatureAlgorithmForKeyType(String str) {
        if ("RSA".equalsIgnoreCase(str)) {
            return "sha256WithRSA";
        }
        if ("DSA".equalsIgnoreCase(str)) {
            return "sha1WithDSA";
        }
        if (CertProvisionProfile.KEY_TYPE_EC.equalsIgnoreCase(str)) {
            return "sha256WithECDSA";
        }
        throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unsupported key type ", str));
    }

    @Override // java.security.KeyPairGeneratorSpi
    public KeyPair generateKeyPair() {
        if (this.mKeySize == 0 || TextUtils.isEmpty(this.mAlgorithm) || TextUtils.isEmpty(this.mAlias)) {
            throw new IllegalStateException("Must call initialize with an android.security.KeyPairGeneratorSpec or android.security.keystore.KeyGenParameterSpec first");
        }
        UniversalCredentialUtil universalCredentialUtil = UniversalCredentialUtil.getInstance();
        String build = new UniversalCredentialUtil.UcmUriBuilder(this.mSource).setResourceId(2).setUid(Process.myUid()).setAlias(this.mAlias).build();
        universalCredentialUtil.delete(build);
        Bundle bundle = new Bundle();
        bundle.putInt(UcmAgentProviderImpl.KEY_EXTRA_PURPOSE, this.mPurpose);
        String[] strArr = this.mBlockModes;
        if (strArr != null) {
            bundle.putStringArray(UcmAgentProviderImpl.KEY_EXTRA_BLOCK_MODES, strArr);
        }
        String[] strArr2 = this.mDigests;
        if (strArr2 != null) {
            bundle.putStringArray(UcmAgentProviderImpl.KEY_EXTRA_DIGESTS, strArr2);
        }
        String[] strArr3 = this.mSignaturePaddings;
        if (strArr3 != null) {
            bundle.putStringArray(UcmAgentProviderImpl.KEY_EXTRA_SIGNATURE_PADDINGS, strArr3);
        }
        String str = this.mEcCurveName;
        if (str != null) {
            bundle.putString(UcmAgentProviderImpl.KEY_EXTRA_EC_CURVE_NAME, str);
        }
        PublicKey publicKey = (PublicKey) universalCredentialUtil.generateKeyPair(build, this.mAlgorithm, this.mKeySize, bundle).getSerializable(UcmAgentService.PLUGIN_PUBLIC_KEY);
        if (publicKey != null) {
            return new KeyPair(publicKey, universalCredentialUtil.getPrivateKey(build));
        }
        throw new IllegalStateException("generateKeyPair returns null public key");
    }

    @Override // java.security.KeyPairGeneratorSpi
    public void initialize(int i, SecureRandom secureRandom) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override // java.security.KeyPairGeneratorSpi
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec == null) {
            throw new InvalidAlgorithmParameterException("must supply params of type android.security.KeyPairGeneratorSpec or android.security.keystore.KeyGenParameterSpec");
        }
        if (algorithmParameterSpec instanceof KeyPairGeneratorSpec) {
            KeyPairGeneratorSpec keyPairGeneratorSpec = (KeyPairGeneratorSpec) algorithmParameterSpec;
            this.mAlias = keyPairGeneratorSpec.getKeystoreAlias();
            this.mKeySize = keyPairGeneratorSpec.getKeySize();
            this.mAlgorithm = keyPairGeneratorSpec.getKeyType();
            AlgorithmParameterSpec algorithmParameterSpec2 = keyPairGeneratorSpec.getAlgorithmParameterSpec();
            if (algorithmParameterSpec2 == null || !(algorithmParameterSpec2 instanceof ECGenParameterSpec)) {
                return;
            }
            this.mEcCurveName = ((ECGenParameterSpec) algorithmParameterSpec2).getName();
            return;
        }
        if (!(algorithmParameterSpec instanceof KeyGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("params must be of type android.security.KeyPairGeneratorSpecor or android.security.keystore.KeyGenParameterSpec");
        }
        KeyGenParameterSpec keyGenParameterSpec = (KeyGenParameterSpec) algorithmParameterSpec;
        this.mAlias = keyGenParameterSpec.getKeystoreAlias();
        this.mKeySize = keyGenParameterSpec.getKeySize();
        this.mBlockModes = keyGenParameterSpec.getBlockModes();
        try {
            this.mDigests = keyGenParameterSpec.getDigests();
        } catch (IllegalStateException unused) {
        }
        this.mSignaturePaddings = keyGenParameterSpec.getSignaturePaddings();
        this.mPurpose = keyGenParameterSpec.getPurposes();
        AlgorithmParameterSpec algorithmParameterSpec3 = keyGenParameterSpec.getAlgorithmParameterSpec();
        if (algorithmParameterSpec3 == null || !(algorithmParameterSpec3 instanceof ECGenParameterSpec)) {
            return;
        }
        this.mEcCurveName = ((ECGenParameterSpec) algorithmParameterSpec3).getName();
    }
}
