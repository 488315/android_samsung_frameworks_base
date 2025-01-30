package com.samsung.android.knox.zt.service;

import android.content.Context;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.samsung.android.knox.zt.service.wrapper.AttestParameterSpec;
import com.samsung.android.knox.zt.service.wrapper.AttestationUtils;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.interfaces.ECKey;
import java.util.ArrayList;
import java.util.Base64;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KeyAttestationHelper implements IKeyAttestationHelper {
    public static final String TAG = "KeyAttestationHelper";
    public final AttestationUtils mAttestationUtils;
    public final Context mContext;

    public KeyAttestationHelper(Context context) {
        this.mContext = context;
        this.mAttestationUtils = new AttestationUtils(context);
    }

    @Override // com.samsung.android.knox.zt.service.IKeyAttestationHelper
    public final boolean attestKey(String str, byte[] bArr, boolean z) {
        try {
            AttestParameterSpec.Builder deviceAttestation = new AttestParameterSpec.Builder(str, bArr).setVerifiableIntegrity(true).setPackageName(this.mContext.getPackageName()).setDeviceAttestation(z);
            this.mAttestationUtils.storeCertificateChain(str, z ? this.mAttestationUtils.attestDevice(deviceAttestation.build()) : this.mAttestationUtils.attestKey(deviceAttestation.build()));
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    @Override // com.samsung.android.knox.zt.service.IKeyAttestationHelper
    public final Certificate[] getCertificateChain(String str) {
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
    public final boolean setCertificateChain(String str, Certificate[] certificateArr) {
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
    public final byte[] sign(String str, byte[] bArr) {
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

    public final void writeToPem(byte[] bArr, String str, Writer writer) {
        Base64.Encoder mimeEncoder = Base64.getMimeEncoder(64, "\n".getBytes(StandardCharsets.US_ASCII));
        writer.append("-----BEGIN ").append((CharSequence) str).append("-----\n");
        writer.append((CharSequence) new String(mimeEncoder.encode(bArr), StandardCharsets.US_ASCII));
        writer.append("\n-----END ").append((CharSequence) str).append("-----\n");
    }
}
