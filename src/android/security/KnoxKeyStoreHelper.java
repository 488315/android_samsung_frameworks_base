package android.security;

import android.os.Process;
import android.os.UserHandle;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.certificate.CertificatePolicy;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class KnoxKeyStoreHelper {
    private static final String TAG = "KnoxKeyStoreHelper";

    private KnoxKeyStoreHelper() {
    }

    public static void notifyCertificateRemovedAsUser(List<X509Certificate> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        CertificatePolicy certificatePolicy = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
        Iterator<X509Certificate> it = list.iterator();
        while (it.hasNext()) {
            certificatePolicy.notifyCertificateRemovedAsUser(it.next().getSubjectX500Principal().getName(), getUserId());
        }
    }

    private static byte[] convertCertificatesToPem(Certificate[] certificateArr) {
        if (certificateArr == null) {
            return null;
        }
        try {
            return Credentials.convertToPem(certificateArr);
        } catch (IOException unused) {
            Log.e(TAG, "Could not convert certificate.");
            return null;
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "Not a certificate " + e.getMessage());
            return null;
        } catch (CertificateException unused2) {
            Log.e(TAG, "Could not convert certificate.");
            return null;
        }
    }

    private static List<X509Certificate> mergeUserCertAndChain(byte[] bArr, byte[] bArr2) {
        X509Certificate certificate = toCertificate(bArr);
        if (certificate == null) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(certificate);
        if (bArr2 != null) {
            arrayList.addAll(toCertificates(bArr2));
        }
        return arrayList;
    }

    public static void checkCertificateTrustful(byte[] bArr, byte[] bArr2) throws KeyStoreException {
        CertificatePolicy certificatePolicy = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
        if (bArr != null) {
            List<X509Certificate> mergeUserCertAndChain = mergeUserCertAndChain(bArr, bArr2);
            bArr2 = convertCertificatesToPem((Certificate[]) mergeUserCertAndChain.toArray(new X509Certificate[mergeUserCertAndChain.size()]));
        } else if (bArr2 == null) {
            bArr2 = null;
        }
        if (certificatePolicy == null || bArr2 == null) {
            return;
        }
        int userId = getUserId();
        if (!((!certificatePolicy.isCertificateValidationAtInstallEnabledAsUser(userId) || certificatePolicy.validateCertificateAtInstallAsUser(bArr2, userId) == -1) ? !certificatePolicy.isCertificateTrustedUntrustedEnabledAsUser(userId) || certificatePolicy.isCaCertificateTrustedAsUser(bArr2, false, userId) : false)) {
            throw new KeyStoreException(6, "Certificate not trusted by MDM");
        }
    }

    private static X509Certificate toCertificate(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
        } catch (CertificateException e) {
            Log.w(TAG, "Couldn't parse certificate in keystore", e);
            return null;
        }
    }

    public static List<X509Certificate> toCertificates(byte[] bArr) {
        if (bArr == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            return (List) CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(bArr));
        } catch (CertificateException e) {
            Log.w(TAG, "Couldn't parse certificates in keystore", e);
            return Collections.EMPTY_LIST;
        }
    }

    private static int getUserId() {
        return UserHandle.getUserId(Process.myUid());
    }
}
