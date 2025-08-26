package com.samsung.android.knox.zt.service;

import android.content.Context;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.samsung.android.knox.zt.service.wrapper.AttestParameterSpec;
import com.samsung.android.knox.zt.service.wrapper.AttestationUtils;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.interfaces.ECKey;
import java.util.ArrayList;
import java.util.Base64;

/* loaded from: classes4.dex */
public class KeyAttestationHelper implements IKeyAttestationHelper {
    public static final String TAG = "KeyAttestationHelper";
    public final AttestationUtils mAttestationUtils;
    public final Context mContext;

    public KeyAttestationHelper(Context context) {
        this.mContext = context;
        this.mAttestationUtils = new AttestationUtils(context);
    }

    @Override // com.samsung.android.knox.zt.service.IKeyAttestationHelper
    public boolean attestKey(String str, byte[] bArr, boolean z) {
        try {
            AttestParameterSpec.Builder sAKUidRequired = new AttestParameterSpec.Builder(str, bArr).setVerifiableIntegrity(true).setPackageName(this.mContext.getPackageName()).setDeviceAttestation(z).setSAKUidRequired(true);
            this.mAttestationUtils.storeCertificateChain(str, z ? this.mAttestationUtils.attestDevice(sAKUidRequired.build()) : this.mAttestationUtils.attestKey(sAKUidRequired.build()));
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    @Override // com.samsung.android.knox.zt.service.IKeyAttestationHelper
    public Certificate[] getCertificateChain(String str) {
        try {
            KeyStore keyStore = KeyStore.getInstance(CertProvisionProfile.PROVIDER_ANDROID);
            keyStore.load(null);
            return keyStore.getCertificateChain(str);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    @Override // com.samsung.android.knox.zt.service.IKeyAttestationHelper
    public boolean setCertificateChain(String str, Certificate[] certificateArr) {
        try {
            ArrayList arrayList = new ArrayList();
            for (Certificate certificate : certificateArr) {
                StringWriter stringWriter = new StringWriter();
                writeToPem(certificate.getEncoded(), "CERTIFICATE", stringWriter);
                arrayList.add(stringWriter.toString().getBytes(StandardCharsets.UTF_8));
            }
            this.mAttestationUtils.storeCertificateChain(str, arrayList);
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    @Override // com.samsung.android.knox.zt.service.IKeyAttestationHelper
    public byte[] sign(String str, byte[] bArr) {
        try {
            KeyStore keyStore = KeyStore.getInstance(CertProvisionProfile.PROVIDER_ANDROID);
            keyStore.load(null);
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(str, null);
            Signature signature = Signature.getInstance(privateKey instanceof ECKey ? "SHA256withECDSA" : "SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(bArr);
            return signature.sign();
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public final void writeToPem(byte[] bArr, String str, Writer writer) throws IOException {
        Charset charset = StandardCharsets.US_ASCII;
        Base64.Encoder mimeEncoder = Base64.getMimeEncoder(64, "\n".getBytes(charset));
        writer.append("-----BEGIN ").append((CharSequence) str).append("-----\n");
        writer.append((CharSequence) new String(mimeEncoder.encode(bArr), charset));
        writer.append("\n-----END ").append((CharSequence) str).append("-----\n");
    }
}
