package com.android.internal.org.bouncycastle.jcajce.util;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable;
import com.android.internal.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint;
import com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves;
import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import java.io.IOException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;

/* loaded from: classes5.dex */
public class ECKeyUtil {
    public static ECPublicKey createKeyWithCompression(ECPublicKey eCPublicKey) {
        return new ECPublicKeyWithCompression(eCPublicKey);
    }

    private static class ECPublicKeyWithCompression implements ECPublicKey {
        private final ECPublicKey ecPublicKey;

        public ECPublicKeyWithCompression(ECPublicKey eCPublicKey) {
            this.ecPublicKey = eCPublicKey;
        }

        @Override // java.security.interfaces.ECPublicKey
        public ECPoint getW() {
            return this.ecPublicKey.getW();
        }

        @Override // java.security.Key
        public String getAlgorithm() {
            return this.ecPublicKey.getAlgorithm();
        }

        @Override // java.security.Key
        public String getFormat() {
            return this.ecPublicKey.getFormat();
        }

        @Override // java.security.Key
        public byte[] getEncoded() {
            ECCurve curve;
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(this.ecPublicKey.getEncoded());
            X962Parameters x962Parameters = X962Parameters.getInstance(subjectPublicKeyInfo.getAlgorithm().getParameters());
            if (x962Parameters.isNamedCurve()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) x962Parameters.getParameters();
                X9ECParametersHolder byOIDLazy = CustomNamedCurves.getByOIDLazy(aSN1ObjectIdentifier);
                if (byOIDLazy == null) {
                    byOIDLazy = ECNamedCurveTable.getByOIDLazy(aSN1ObjectIdentifier);
                }
                curve = byOIDLazy.getCurve();
            } else {
                if (x962Parameters.isImplicitlyCA()) {
                    throw new IllegalStateException("unable to identify implictlyCA");
                }
                curve = X9ECParameters.getInstance(x962Parameters.getParameters()).getCurve();
            }
            try {
                return new SubjectPublicKeyInfo(subjectPublicKeyInfo.getAlgorithm(), ASN1OctetString.getInstance(new X9ECPoint(curve.decodePoint(subjectPublicKeyInfo.getPublicKeyData().getOctets()), true).toASN1Primitive()).getOctets()).getEncoded();
            } catch (IOException e) {
                throw new IllegalStateException("unable to encode EC public key: " + e.getMessage());
            }
        }

        @Override // java.security.interfaces.ECKey
        public ECParameterSpec getParams() {
            return this.ecPublicKey.getParams();
        }
    }
}
