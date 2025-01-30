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
import java.util.Iterator;

/* loaded from: classes3.dex */
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

  /* JADX WARN: Incorrect condition in loop: B:3:0x0018 */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  static byte[] personalize(IWritableCredential binder, PersonalizationData personalizationData) {
    long secureUserId;
    Collection<AccessControlProfile> accessControlProfiles =
        personalizationData.getAccessControlProfiles();
    AccessControlProfileParcel[] acpParcels =
        new AccessControlProfileParcel[accessControlProfiles.size()];
    Iterator<AccessControlProfile> it = accessControlProfiles.iterator();
    int n = 0;
    int n2 = 0;
    while (usingUserAuthentication) {
      AccessControlProfile profile = it.next();
      acpParcels[n] = new AccessControlProfileParcel();
      acpParcels[n].f470id = profile.getAccessControlProfileId().getId();
      X509Certificate cert = profile.getReaderCertificate();
      if (cert != null) {
        try {
          acpParcels[n].readerCertificate = cert.getEncoded();
        } catch (CertificateException e) {
          throw new RuntimeException("Error encoding reader certificate", e);
        }
      } else {
        acpParcels[n].readerCertificate = new byte[0];
      }
      acpParcels[n].userAuthenticationRequired = profile.isUserAuthenticationRequired();
      acpParcels[n].userAuthenticationTimeoutMillis = profile.getUserAuthenticationTimeout();
      if (profile.isUserAuthenticationRequired()) {
        n2 = 1;
      }
      n++;
    }
    Collection<String> namespaces = personalizationData.getNamespaces();
    EntryNamespaceParcel[] ensParcels = new EntryNamespaceParcel[namespaces.size()];
    Iterator<String> it2 = namespaces.iterator();
    int n3 = 0;
    while (it2.hasNext()) {
      String namespaceName = it2.next();
      PersonalizationData.NamespaceData nsd = personalizationData.getNamespaceData(namespaceName);
      ensParcels[n3] = new EntryNamespaceParcel();
      ensParcels[n3].namespaceName = namespaceName;
      Collection<String> entryNames = nsd.getEntryNames();
      EntryParcel[] eParcels = new EntryParcel[entryNames.size()];
      int m = 0;
      for (String entryName : entryNames) {
        eParcels[m] = new EntryParcel();
        eParcels[m].name = entryName;
        String namespaceName2 = namespaceName;
        eParcels[m].value = nsd.getEntryValue(entryName);
        Collection<AccessControlProfileId> acpIds = nsd.getAccessControlProfileIds(entryName);
        Collection<AccessControlProfile> accessControlProfiles2 = accessControlProfiles;
        eParcels[m].accessControlProfileIds = new int[acpIds.size()];
        int o = 0;
        for (AccessControlProfileId acpId : acpIds) {
          eParcels[m].accessControlProfileIds[o] = acpId.getId();
          acpIds = acpIds;
          o++;
        }
        m++;
        namespaceName = namespaceName2;
        accessControlProfiles = accessControlProfiles2;
      }
      ensParcels[n3].entries = eParcels;
      n3++;
    }
    if (n2 == 0) {
      secureUserId = 0;
    } else {
      long secureUserId2 = getRootSid();
      secureUserId = secureUserId2;
    }
    try {
      byte[] personalizationReceipt = binder.personalize(acpParcels, ensParcels, secureUserId);
      return personalizationReceipt;
    } catch (RemoteException e2) {
      throw new RuntimeException("Unexpected RemoteException ", e2);
    } catch (ServiceSpecificException e3) {
      throw new RuntimeException(
          "Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
    }
  }

  private static long getRootSid() {
    long rootSid = GateKeeper.getSecureUserId();
    if (rootSid == 0) {
      throw new IllegalStateException(
          "Secure lock screen must be enabled to create credentials requiring user authentication");
    }
    return rootSid;
  }
}
