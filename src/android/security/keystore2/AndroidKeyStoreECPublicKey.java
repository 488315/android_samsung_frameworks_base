package android.security.keystore2;

import android.security.KeyStoreSecurityLevel;
import android.security.keystore.KeyProperties;
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

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0015, code lost:
    
        r0 = android.security.keystore2.KeymasterUtils.getCurveSpec(android.security.keystore2.KeymasterUtils.getEcCurveFromKeymaster(r4.keyParameter.value.getEcCurve()));
     */
    @Override // android.security.keystore2.AndroidKeyStorePublicKey
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.security.keystore2.AndroidKeyStorePrivateKey getPrivateKey() {
        /*
            r8 = this;
            java.security.spec.ECParameterSpec r0 = r8.mParams
            android.system.keystore2.Authorization[] r1 = r8.getAuthorizations()
            int r2 = r1.length
            r3 = 0
        L8:
            if (r3 >= r2) goto L45
            r4 = r1[r3]
            android.hardware.security.keymint.KeyParameter r5 = r4.keyParameter     // Catch: java.lang.Exception -> L29
            int r5 = r5.tag     // Catch: java.lang.Exception -> L29
            r6 = 268435466(0x1000000a, float:2.524358E-29)
            if (r5 != r6) goto L26
            android.hardware.security.keymint.KeyParameter r0 = r4.keyParameter     // Catch: java.lang.Exception -> L29
            android.hardware.security.keymint.KeyParameterValue r0 = r0.value     // Catch: java.lang.Exception -> L29
            int r0 = r0.getEcCurve()     // Catch: java.lang.Exception -> L29
            java.lang.String r0 = android.security.keystore2.KeymasterUtils.getEcCurveFromKeymaster(r0)     // Catch: java.lang.Exception -> L29
            java.security.spec.ECParameterSpec r0 = android.security.keystore2.KeymasterUtils.getCurveSpec(r0)     // Catch: java.lang.Exception -> L29
            goto L45
        L26:
            int r3 = r3 + 1
            goto L8
        L29:
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Unable to parse EC curve "
            r0.<init>(r1)
            android.hardware.security.keymint.KeyParameter r1 = r4.keyParameter
            android.hardware.security.keymint.KeyParameterValue r1 = r1.value
            int r1 = r1.getEcCurve()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r8.<init>(r0)
            throw r8
        L45:
            r7 = r0
            android.security.keystore2.AndroidKeyStoreECPrivateKey r1 = new android.security.keystore2.AndroidKeyStoreECPrivateKey
            android.system.keystore2.KeyDescriptor r2 = r8.getUserKeyDescriptor()
            android.system.keystore2.KeyDescriptor r0 = r8.getKeyIdDescriptor()
            long r3 = r0.nspace
            android.system.keystore2.Authorization[] r5 = r8.getAuthorizations()
            android.security.KeyStoreSecurityLevel r6 = r8.getSecurityLevel()
            r1.<init>(r2, r3, r5, r6, r7)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.keystore2.AndroidKeyStoreECPublicKey.getPrivateKey():android.security.keystore2.AndroidKeyStorePrivateKey");
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
