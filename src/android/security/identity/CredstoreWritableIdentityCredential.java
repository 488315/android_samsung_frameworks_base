package android.security.identity;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.security.GateKeeper;
import android.security.identity.PersonalizationData;
import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes3.dex */
class CredstoreWritableIdentityCredential extends WritableIdentityCredential {
    private static final String TAG = "CredstoreWritableIdentityCredential";
    private IWritableCredential mBinder;
    private Context mContext;
    private String mCredentialName;
    private String mDocType;

    CredstoreWritableIdentityCredential(Context context, String str, String str2, IWritableCredential iWritableCredential) {
        this.mContext = context;
        this.mDocType = str2;
        this.mCredentialName = str;
        this.mBinder = iWritableCredential;
    }

    @Override // android.security.identity.WritableIdentityCredential
    public Collection<X509Certificate> getCredentialKeyCertificateChain(byte[] bArr) throws CertificateException {
        try {
            try {
                Collection<? extends Certificate> collectionGenerateCertificates = CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(this.mBinder.getCredentialKeyCertificateChain(bArr)));
                ArrayList arrayList = new ArrayList();
                Iterator<? extends Certificate> it = collectionGenerateCertificates.iterator();
                while (it.hasNext()) {
                    arrayList.add((X509Certificate) it.next());
                }
                return arrayList;
            } catch (CertificateException e) {
                throw new RuntimeException("Error decoding certificates", e);
            }
        } catch (RemoteException e2) {
            throw new RuntimeException("Unexpected RemoteException ", e2);
        } catch (ServiceSpecificException e3) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
        }
    }

    @Override // android.security.identity.WritableIdentityCredential
    public byte[] personalize(PersonalizationData personalizationData) {
        return personalize(this.mBinder, personalizationData);
    }

    static byte[] personalize(IWritableCredential iWritableCredential, PersonalizationData personalizationData) {
        Collection<AccessControlProfile> accessControlProfiles = personalizationData.getAccessControlProfiles();
        AccessControlProfileParcel[] accessControlProfileParcelArr = new AccessControlProfileParcel[accessControlProfiles.size()];
        int i = 0;
        boolean z = false;
        for (AccessControlProfile accessControlProfile : accessControlProfiles) {
            AccessControlProfileParcel accessControlProfileParcel = new AccessControlProfileParcel();
            accessControlProfileParcelArr[i] = accessControlProfileParcel;
            accessControlProfileParcel.id = accessControlProfile.getAccessControlProfileId().getId();
            X509Certificate readerCertificate = accessControlProfile.getReaderCertificate();
            if (readerCertificate != null) {
                try {
                    accessControlProfileParcelArr[i].readerCertificate = readerCertificate.getEncoded();
                } catch (CertificateException e) {
                    throw new RuntimeException("Error encoding reader certificate", e);
                }
            } else {
                accessControlProfileParcelArr[i].readerCertificate = new byte[0];
            }
            accessControlProfileParcelArr[i].userAuthenticationRequired = accessControlProfile.isUserAuthenticationRequired();
            accessControlProfileParcelArr[i].userAuthenticationTimeoutMillis = accessControlProfile.getUserAuthenticationTimeout();
            if (accessControlProfile.isUserAuthenticationRequired()) {
                z = true;
            }
            i++;
        }
        Collection<String> namespaces = personalizationData.getNamespaces();
        EntryNamespaceParcel[] entryNamespaceParcelArr = new EntryNamespaceParcel[namespaces.size()];
        int i2 = 0;
        for (String str : namespaces) {
            PersonalizationData.NamespaceData namespaceData = personalizationData.getNamespaceData(str);
            EntryNamespaceParcel entryNamespaceParcel = new EntryNamespaceParcel();
            entryNamespaceParcelArr[i2] = entryNamespaceParcel;
            entryNamespaceParcel.namespaceName = str;
            Collection<String> entryNames = namespaceData.getEntryNames();
            EntryParcel[] entryParcelArr = new EntryParcel[entryNames.size()];
            int i3 = 0;
            for (String str2 : entryNames) {
                EntryParcel entryParcel = new EntryParcel();
                entryParcelArr[i3] = entryParcel;
                entryParcel.name = str2;
                entryParcelArr[i3].value = namespaceData.getEntryValue(str2);
                Collection<AccessControlProfileId> accessControlProfileIds = namespaceData.getAccessControlProfileIds(str2);
                entryParcelArr[i3].accessControlProfileIds = new int[accessControlProfileIds.size()];
                Iterator<AccessControlProfileId> it = accessControlProfileIds.iterator();
                int i4 = 0;
                while (it.hasNext()) {
                    entryParcelArr[i3].accessControlProfileIds[i4] = it.next().getId();
                    i4++;
                }
                i3++;
            }
            entryNamespaceParcelArr[i2].entries = entryParcelArr;
            i2++;
        }
        try {
            return iWritableCredential.personalize(accessControlProfileParcelArr, entryNamespaceParcelArr, z ? getRootSid() : 0L);
        } catch (RemoteException e2) {
            throw new RuntimeException("Unexpected RemoteException ", e2);
        } catch (ServiceSpecificException e3) {
            throw new RuntimeException("Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
        }
    }

    private static long getRootSid() throws IllegalStateException {
        long secureUserId = GateKeeper.getSecureUserId();
        if (secureUserId != 0) {
            return secureUserId;
        }
        throw new IllegalStateException("Secure lock screen must be enabled to create credentials requiring user authentication");
    }
}
