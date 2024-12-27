package android.security.identity;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.security.GateKeeper;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;

class CredstoreWritableIdentityCredential extends WritableIdentityCredential {
    private static final String TAG = "CredstoreWritableIdentityCredential";
    private IWritableCredential mBinder;
    private Context mContext;
    private String mCredentialName;
    private String mDocType;

    CredstoreWritableIdentityCredential(
            Context context, String credentialName, String docType, IWritableCredential binder) {
        this.mContext = context;
        this.mDocType = docType;
        this.mCredentialName = credentialName;
        this.mBinder = binder;
    }

    @Override // android.security.identity.WritableIdentityCredential
    public Collection<X509Certificate> getCredentialKeyCertificateChain(byte[] challenge) {
        try {
            byte[] certsBlob = this.mBinder.getCredentialKeyCertificateChain(challenge);
            ByteArrayInputStream bais = new ByteArrayInputStream(certsBlob);
            try {
                CertificateFactory factory = CertificateFactory.getInstance("X.509");
                Collection<? extends Certificate> certs = factory.generateCertificates(bais);
                ArrayList<X509Certificate> x509Certs = new ArrayList<>();
                for (Certificate cert : certs) {
                    x509Certs.add((X509Certificate) cert);
                }
                return x509Certs;
            } catch (CertificateException e) {
                throw new RuntimeException("Error decoding certificates", e);
            }
        } catch (RemoteException e2) {
            throw new RuntimeException("Unexpected RemoteException ", e2);
        } catch (ServiceSpecificException e3) {
            throw new RuntimeException(
                    "Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
        }
    }

    @Override // android.security.identity.WritableIdentityCredential
    public byte[] personalize(PersonalizationData personalizationData) {
        return personalize(this.mBinder, personalizationData);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static byte[] personalize(
            android.security.identity.IWritableCredential r22,
            android.security.identity.PersonalizationData r23) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.security.identity.CredstoreWritableIdentityCredential.personalize(android.security.identity.IWritableCredential,"
                    + " android.security.identity.PersonalizationData):byte[]");
    }

    private static long getRootSid() {
        long rootSid = GateKeeper.getSecureUserId();
        if (rootSid == 0) {
            throw new IllegalStateException(
                    "Secure lock screen must be enabled to create credentials requiring user"
                        + " authentication");
        }
        return rootSid;
    }
}
