package com.android.internal.telephony.vzwavslibrary;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Locale;

/* loaded from: classes4.dex */
public class Utils {
    protected static final String TAG = "VZWAVSLibrary";

    public static String getCertFingerprint(Signature signature) {
        if (signature == null) {
            return null;
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(signature.toByteArray());
            try {
                byte[] digest = MessageDigest.getInstance("SHA1").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(byteArrayInputStream)).getEncoded());
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    if (sb.length() > 0) {
                        sb.append(":");
                    }
                    sb.append(String.format(Locale.ENGLISH, "%02X", Byte.valueOf(b)));
                }
                String sb2 = sb.toString();
                byteArrayInputStream.close();
                return sb2;
            } catch (Throwable th) {
                try {
                    byteArrayInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | NoSuchAlgorithmException | CertificateException unused) {
            return null;
        }
    }

    public static Signature[] getSigningCertificates(Context context, String str) throws PackageManager.NameNotFoundException {
        return getSigningCertificatesP(context, str);
    }

    private static Signature[] getSigningCertificatesP(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 134217728);
        SigningInfo signingInfo = packageInfo == null ? null : packageInfo.signingInfo;
        if (signingInfo == null) {
            return new Signature[0];
        }
        Signature[] apkContentsSigners = signingInfo.hasMultipleSigners() ? signingInfo.getApkContentsSigners() : signingInfo.getSigningCertificateHistory();
        return apkContentsSigners == null ? new Signature[0] : apkContentsSigners;
    }

    private static Signature[] legacyGetSigningCertificates(Context context, String str) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 64);
        Signature[] signatureArr = packageInfo == null ? null : packageInfo.signatures;
        return signatureArr == null ? new Signature[0] : signatureArr;
    }
}
