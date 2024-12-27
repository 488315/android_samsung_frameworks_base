package com.samsung.android.ssdid;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.samsung.android.security.keystore.AttestParameterSpec;
import com.samsung.android.security.keystore.AttestationUtils;
import com.samsung.android.ssdid.ISemSsdidManagerService;
import com.samsung.android.wifi.SemWifiManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.ProviderException;
import java.security.cert.X509Certificate;
import java.util.Base64;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemSsdidManagerService extends ISemSsdidManagerService.Stub {
    public final Context mContext;
    public String mSsdid = "";

    public SemSsdidManagerService(Context context) {
        this.mContext = context;
        Slog.d("SemSsdidManagerService", "start");
    }

    public final String getSsdid() {
        AttestationUtils attestationUtils;
        String str;
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.READ_SSDID", "required permissions");
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", "required permissions");
        if (this.mSsdid.isEmpty()) {
            String str2 = "";
            String str3 = SystemProperties.get("ro.security.keystore.keytype", "");
            int i = 0;
            String str4 = str3.contains("sakv2") ? "sakv2" : (!str3.contains("sakm") || SystemProperties.getInt("ro.product.first_api_level", 0) < 34 || "m55xq".contains(Build.DEVICE)) ? "" : "sakm";
            String str5 = null;
            r10 = null;
            byte[] bArr = null;
            if (str4.isEmpty()) {
                Slog.i("SemSsdidManagerService", "getSsdid: can not read SAK");
                String serial = Build.getSerial();
                if (serial == null || "unknown".equals(serial)) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader("/efs/FactoryApp/serial_no", StandardCharsets.UTF_8));
                        try {
                            str = bufferedReader.readLine();
                            bufferedReader.close();
                        } finally {
                        }
                    } catch (IOException e) {
                        Slog.e("SemSsdidManagerService", "exception in readFromFile", e);
                        str = null;
                    }
                    serial = str != null ? str.split(",")[0] : null;
                }
                String factoryMacAddress = ((SemWifiManager) this.mContext.getSystemService("sem_wifi")).getFactoryMacAddress();
                if (serial != null && factoryMacAddress != null) {
                    String concat = serial.concat(factoryMacAddress);
                    if (concat != null) {
                        byte[] bytes = concat.getBytes(StandardCharsets.UTF_8);
                        try {
                            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                            messageDigest.update(bytes);
                            bArr = messageDigest.digest();
                        } catch (NoSuchAlgorithmException e2) {
                            Slog.e("SemSsdidManagerService", "exception during hash", e2);
                        }
                    }
                    str2 = Base64.getEncoder().encodeToString(bArr);
                }
                this.mSsdid = str2;
            } else {
                try {
                    attestationUtils = new AttestationUtils();
                } catch (IllegalArgumentException | NullPointerException | ProviderException e3) {
                    Slog.e("SemSsdidManagerService", "exception", e3);
                }
                if ("sakv2".equals(str4)) {
                    attestationUtils.generateKeyPair("SemSsdidManagerService", new byte[0]);
                } else if ("sakm".equals(str4)) {
                    attestationUtils.generateKeyPair(new AttestParameterSpec(new byte[0], false, true, new KeyGenParameterSpec.Builder("SemSsdidManagerService", 4).setDigests("NONE", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512").build()));
                } else {
                    Slog.e("SemSsdidManagerService", "invalid ".concat(str4));
                    this.mSsdid = str2;
                }
                X509Certificate x509Certificate = (X509Certificate) AttestationUtils.getCertificateChain("SemSsdidManagerService")[0];
                Principal issuerDN = "sakv2".equals(str4) ? x509Certificate.getIssuerDN() : x509Certificate.getSubjectDN();
                String[] split = issuerDN != null ? issuerDN.toString().split(",") : null;
                if (split != null) {
                    int length = split.length;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        String str6 = split[i];
                        if (str6.contains("UID")) {
                            str5 = str6;
                            break;
                        }
                        i++;
                    }
                    Slog.i("SemSsdidManagerService", "sakUid: " + str5);
                    if (str5 != null) {
                        String[] split2 = str5.replace("\"", "").split(":");
                        str2 = split2[split2.length - 1];
                    }
                } else {
                    Slog.e("SemSsdidManagerService", "not found UID in sak");
                }
                this.mSsdid = str2;
            }
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("getSsdid: "), this.mSsdid, "SemSsdidManagerService");
        return this.mSsdid;
    }
}
