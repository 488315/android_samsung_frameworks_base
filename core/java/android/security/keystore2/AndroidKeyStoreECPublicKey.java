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

    public AndroidKeyStoreECPublicKey(
            KeyDescriptor descriptor,
            KeyMetadata metadata,
            byte[] x509EncodedForm,
            KeyStoreSecurityLevel securityLevel,
            ECParameterSpec params,
            ECPoint w) {
        super(descriptor, metadata, x509EncodedForm, KeyProperties.KEY_ALGORITHM_EC, securityLevel);
        this.mParams = params;
        this.mW = w;
    }

    public AndroidKeyStoreECPublicKey(
            KeyDescriptor descriptor,
            KeyMetadata metadata,
            KeyStoreSecurityLevel securityLevel,
            ECPublicKey info) {
        this(descriptor, metadata, info.getEncoded(), securityLevel, info.getParams(), info.getW());
        if (!"X.509".equalsIgnoreCase(info.getFormat())) {
            throw new IllegalArgumentException(
                    "Unsupported key export format: " + info.getFormat());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0025, code lost:

       r0 = android.security.keystore2.KeymasterUtils.getCurveSpec(android.security.keystore2.KeymasterUtils.getEcCurveFromKeymaster(r4.keyParameter.value.getEcCurve()));
    */
    @Override // android.security.keystore2.AndroidKeyStorePublicKey
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.security.keystore2.AndroidKeyStorePrivateKey getPrivateKey() {
        /*
            r12 = this;
            java.security.spec.ECParameterSpec r0 = r12.mParams
            android.system.keystore2.Authorization[] r1 = r12.getAuthorizations()
            int r2 = r1.length
            r3 = 0
        L8:
            if (r3 >= r2) goto L4d
            r4 = r1[r3]
            android.hardware.security.keymint.KeyParameter r5 = r4.keyParameter     // Catch: java.lang.Exception -> L2b
            int r5 = r5.tag     // Catch: java.lang.Exception -> L2b
            r6 = 268435466(0x1000000a, float:2.524358E-29)
            if (r5 != r6) goto L27
            android.hardware.security.keymint.KeyParameter r1 = r4.keyParameter     // Catch: java.lang.Exception -> L2b
            android.hardware.security.keymint.KeyParameterValue r1 = r1.value     // Catch: java.lang.Exception -> L2b
            int r1 = r1.getEcCurve()     // Catch: java.lang.Exception -> L2b
            java.lang.String r1 = android.security.keystore2.KeymasterUtils.getEcCurveFromKeymaster(r1)     // Catch: java.lang.Exception -> L2b
            java.security.spec.ECParameterSpec r1 = android.security.keystore2.KeymasterUtils.getCurveSpec(r1)     // Catch: java.lang.Exception -> L2b
            r0 = r1
            goto L4d
        L27:
            int r3 = r3 + 1
            goto L8
        L2b:
            r1 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Unable to parse EC curve "
            java.lang.StringBuilder r3 = r3.append(r5)
            android.hardware.security.keymint.KeyParameter r5 = r4.keyParameter
            android.hardware.security.keymint.KeyParameterValue r5 = r5.value
            int r5 = r5.getEcCurve()
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L4d:
            android.security.keystore2.AndroidKeyStoreECPrivateKey r1 = new android.security.keystore2.AndroidKeyStoreECPrivateKey
            android.system.keystore2.KeyDescriptor r6 = r12.getUserKeyDescriptor()
            android.system.keystore2.KeyDescriptor r2 = r12.getKeyIdDescriptor()
            long r7 = r2.nspace
            android.system.keystore2.Authorization[] r9 = r12.getAuthorizations()
            android.security.KeyStoreSecurityLevel r10 = r12.getSecurityLevel()
            r5 = r1
            r11 = r0
            r5.<init>(r6, r7, r9, r10, r11)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.security.keystore2.AndroidKeyStoreECPublicKey.getPrivateKey():android.security.keystore2.AndroidKeyStorePrivateKey");
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
