package com.android.internal.telephony.vzwavslibrary;

import android.content.Context;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.Signature;
import android.content.p002pm.SigningInfo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Locale;

/* loaded from: classes5.dex */
public class Utils {
    protected static final String TAG = "VZWAVSLibrary";

    public static String getCertFingerprint(Signature sig) {
        if (sig == null) {
            return null;
        }
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(sig.toByteArray());
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X509");
                X509Certificate c = (X509Certificate) cf.generateCertificate(is);
                MessageDigest md = MessageDigest.getInstance("SHA1");
                byte[] publicKey = md.digest(c.getEncoded());
                StringBuilder sb = new StringBuilder();
                for (byte b : publicKey) {
                    if (sb.length() > 0) {
                        sb.append(":");
                    }
                    sb.append(String.format(Locale.ENGLISH, "%02X", Byte.valueOf(b)));
                }
                String s = sb.toString();
                is.close();
                return s;
            } catch (Throwable th) {
                try {
                    is.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            return null;
        }
    }

    public static Signature[] getSigningCertificates(Context context, String packageName) throws PackageManager.NameNotFoundException {
        return getSigningCertificatesP(context, packageName);
    }

    private static Signature[] getSigningCertificatesP(Context context, String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 134217728);
        SigningInfo signingInfo = pi == null ? null : pi.signingInfo;
        if (signingInfo == null) {
            return new Signature[0];
        }
        Signature[] sigs = signingInfo.hasMultipleSigners() ? signingInfo.getApkContentsSigners() : signingInfo.getSigningCertificateHistory();
        return sigs == null ? new Signature[0] : sigs;
    }

    private static Signature[] legacyGetSigningCertificates(Context context, String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 64);
        Signature[] sigs = pi == null ? null : pi.signatures;
        return sigs == null ? new Signature[0] : sigs;
    }
}
