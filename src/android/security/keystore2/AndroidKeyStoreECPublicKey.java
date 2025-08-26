package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.security.keystore.KeyProperties;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyMetadata;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;

/* loaded from: classes3.dex */
public class AndroidKeyStoreECPublicKey extends AndroidKeyStorePublicKey implements ECPublicKey {
    private final ECParameterSpec mParams;
    private final ECPoint mW;

    public AndroidKeyStoreECPublicKey(KeyDescriptor keyDescriptor, KeyMetadata keyMetadata, byte[] bArr, KeyStoreSecurityLevel keyStoreSecurityLevel, ECParameterSpec eCParameterSpec, ECPoint eCPoint) {
        super(keyDescriptor, keyMetadata, bArr, KeyProperties.KEY_ALGORITHM_EC, keyStoreSecurityLevel);
        this.mParams = eCParameterSpec;
        this.mW = eCPoint;
    }

    public AndroidKeyStoreECPublicKey(KeyDescriptor keyDescriptor, KeyMetadata keyMetadata, KeyStoreSecurityLevel keyStoreSecurityLevel, ECPublicKey eCPublicKey) {
        this(keyDescriptor, keyMetadata, eCPublicKey.getEncoded(), keyStoreSecurityLevel, eCPublicKey.getParams(), eCPublicKey.getW());
        if ("X.509".equalsIgnoreCase(eCPublicKey.getFormat())) {
            return;
        }
        throw new IllegalArgumentException("Unsupported key export format: " + eCPublicKey.getFormat());
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0015, code lost:
    
        r0 = android.security.keystore2.KeymasterUtils.getCurveSpec(android.security.keystore2.KeymasterUtils.getEcCurveFromKeymaster(r4.keyParameter.value.getEcCurve()));
     */
    @Override // android.security.keystore2.AndroidKeyStorePublicKey
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AndroidKeyStorePrivateKey getPrivateKey() {
        ECParameterSpec curveSpec = this.mParams;
        Authorization[] authorizations = getAuthorizations();
        int length = authorizations.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Authorization authorization = authorizations[i];
            try {
                if (authorization.keyParameter.tag == 268435466) {
                    break;
                }
                i++;
            } catch (Exception unused) {
                throw new RuntimeException("Unable to parse EC curve " + authorization.keyParameter.value.getEcCurve());
            }
        }
        return new AndroidKeyStoreECPrivateKey(getUserKeyDescriptor(), getKeyIdDescriptor().nspace, getAuthorizations(), getSecurityLevel(), curveSpec);
    }

    @Override // java.security.interfaces.ECKey
    public ECParameterSpec getParams() {
        return this.mParams;
    }

    @Override // java.security.interfaces.ECPublicKey
    public ECPoint getW() {
        return this.mW;
    }
}
