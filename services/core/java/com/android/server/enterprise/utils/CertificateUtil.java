package com.android.server.enterprise.utils;

import android.app.admin.IDevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.security.Credentials;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.util.Log;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.keystore.CertificateInfo;
import com.samsung.android.knox.keystore.ICertificatePolicy;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CertificateUtil {
    public final Context mContext;
    public final UserManager mUserManager;
    public PrivateKey mUserKey = null;
    public X509Certificate mUserCert = null;
    public List mCaCerts = new ArrayList();
    public final Random mRandom = new Random();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyChainCRUD {
        public final Context mContext;
        public final UserHandle mUser;
        public KeyChain.KeyChainConnection mConnection = null;
        public IKeyChainService mService = null;
        public IDevicePolicyManager mDpmsService = null;

        public KeyChainCRUD(Context context, int i) {
            this.mUser = null;
            this.mContext = null;
            this.mUser = new UserHandle(i);
            this.mContext = context;
            connect();
        }

        public final boolean connect() {
            if (this.mService != null && this.mConnection != null) {
                return true;
            }
            try {
                KeyChain.KeyChainConnection bindAsUser = KeyChain.bindAsUser(this.mContext, this.mUser);
                this.mConnection = bindAsUser;
                this.mService = bindAsUser.getService();
                this.mDpmsService = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
                return true;
            } catch (AssertionError unused) {
                Log.d("CertificateUtil", "Unable to connect to KeyChainService for user " + this.mUser.getIdentifier());
                disconnect();
                return false;
            } catch (Exception e) {
                Log.e("CertificateUtil", "Error binding KeyChain. Is KeyChainService running for user " + this.mUser.getIdentifier() + "?", e);
                disconnect();
                return false;
            }
        }

        public final boolean contains(int i, String str) {
            if (!connect()) {
                Log.d("CertificateUtil", "Aborting contains operation");
                return false;
            }
            try {
                return this.mService.contains(str, i);
            } catch (Exception e) {
                Log.e("CertificateUtil", "Error in KeyChainService.contains for alias " + str, e);
                return false;
            }
        }

        public final boolean deleteEntry(int i, String str) {
            if (!connect()) {
                Log.d("CertificateUtil", "Aborting deleteEntry operation");
                return false;
            }
            try {
                return this.mService.deleteEntry(str, i);
            } catch (Exception e) {
                Log.e("CertificateUtil", "Error in KeyChainService.deleteEntry for alias " + str, e);
                return false;
            }
        }

        public final void disconnect() {
            KeyChain.KeyChainConnection keyChainConnection = this.mConnection;
            if (keyChainConnection != null) {
                try {
                    keyChainConnection.close();
                } catch (Exception unused) {
                    Log.e("CertificateUtil", "Error disconnecting from KeyChain!");
                }
                this.mConnection = null;
                this.mService = null;
            }
        }

        public final byte[] get(int i, String str, String str2) {
            if (!connect()) {
                Log.d("CertificateUtil", "Aborting get operation");
                return null;
            }
            try {
                return this.mService.getCertificateSystem(str, str2, i);
            } catch (Exception e) {
                Log.e("CertificateUtil", "Error in KeyChainService.getCertificateSystem for alias " + str, e);
                return null;
            }
        }

        public final String installCaCertificate(byte[] bArr) {
            if (!connect()) {
                Log.d("CertificateUtil", "Aborting installCaCertificate operation");
                return null;
            }
            try {
                String installCaCertificate = this.mService.installCaCertificate(bArr);
                this.mDpmsService.approveCaCert(installCaCertificate, this.mUser.getIdentifier(), true);
                return installCaCertificate;
            } catch (Exception e) {
                Log.e("CertificateUtil", "Error in KeyChainService.installCaCertificate", e);
                return null;
            }
        }

        public final boolean isCertificateEntry(int i, String str) {
            if (!connect()) {
                Log.d("CertificateUtil", "Aborting isCertificateEntry operation.");
                return false;
            }
            try {
                return this.mService.isCertificateEntry(str, i);
            } catch (Exception e) {
                Log.e("CertificateUtil", "Error in KeyChainService.isCertificateEntry for alias " + str, e);
                return false;
            }
        }

        public final String[] listAliases(int i, String str) {
            if (!connect()) {
                Log.d("CertificateUtil", "Aborting listAliases operation");
                return null;
            }
            try {
                return this.mService.listAliases(str, i);
            } catch (Exception e) {
                Log.e("CertificateUtil", "Error in KeyChainService.listAliases for keystore " + i, e);
                return null;
            }
        }

        public final boolean put(String str, int i, byte[] bArr, byte[] bArr2, byte[] bArr3) {
            if (!connect()) {
                Log.d("CertificateUtil", "Aborting put operation.");
                return false;
            }
            try {
                boolean installKeyPair = this.mService.installKeyPair(bArr, bArr2, bArr3, str, i);
                if (bArr != null && installKeyPair) {
                    this.mService.setUserSelectable(str, true);
                }
                return installKeyPair;
            } catch (Exception e) {
                Log.e("CertificateUtil", "Error in KeyChainService.installKeyPair for alias " + str, e);
                return false;
            }
        }

        public final boolean updateKeyPair(int i, byte[] bArr, byte[] bArr2, String str) {
            if (!connect()) {
                Log.d("CertificateUtil", "Aborting updateKeyPair operation.");
                return false;
            }
            try {
                return this.mService.updateKeyPair(str, bArr, bArr2, i);
            } catch (Exception e) {
                Log.e("CertificateUtil", "Error in KeyChainService.updateKeyPair for alias " + str, e);
                return false;
            }
        }
    }

    public CertificateUtil(Context context) {
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService("user");
    }

    public static byte[] convertDerToPem(byte[] bArr) {
        Log.d("CertificateUtil", "Convert DER to PEM");
        if (bArr == null) {
            return null;
        }
        try {
            return convertX509ListToPem((List) CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(bArr)));
        } catch (CertificateException e) {
            Log.e("CertificateUtil", "Couldn't convert DER to PEM", e);
            return null;
        }
    }

    public static byte[] convertX509ListToPem(List list) {
        if (list == null) {
            return null;
        }
        try {
            return Credentials.convertToPem((X509Certificate[]) list.toArray(new X509Certificate[list.size()]));
        } catch (IOException unused) {
            Log.e("CertificateUtil", "Could not convert certificate.");
            return null;
        } catch (IllegalArgumentException e) {
            Log.d("CertificateUtil", "Not a certificate " + e.getMessage());
            return null;
        } catch (CertificateException unused2) {
            Log.e("CertificateUtil", "Could not convert certificate.");
            return null;
        }
    }

    public static boolean isCa(X509Certificate x509Certificate) {
        try {
            byte[] extensionValue = x509Certificate.getExtensionValue("2.5.29.19");
            if (extensionValue == null) {
                return false;
            }
            return BasicConstraints.getInstance(new ASN1InputStream(new ASN1InputStream(extensionValue).readObject().getOctets()).readObject()).isCA();
        } catch (IOException unused) {
            return false;
        }
    }

    public static List toCertificates(byte[] bArr) {
        try {
            return new ArrayList(KeyChain.toCertificates(bArr));
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public final boolean extractPkcs12(int i, String str, byte[] bArr) {
        KeyStore keyStore;
        KeyStore.PasswordProtection passwordProtection;
        Enumeration<String> aliases;
        if (str == null) {
            str = "";
        }
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            passwordProtection = new KeyStore.PasswordProtection(str.toCharArray());
            keyStore.load(new ByteArrayInputStream(bArr), passwordProtection.getPassword());
            aliases = keyStore.aliases();
        } catch (Exception e) {
            Log.w("CertificateUtil", "extractPkcs12(): " + e);
        }
        if (!aliases.hasMoreElements()) {
            return false;
        }
        while (aliases.hasMoreElements()) {
            String nextElement = aliases.nextElement();
            if (keyStore.isKeyEntry(nextElement)) {
                KeyStore.Entry entry = keyStore.getEntry(nextElement, passwordProtection);
                if (entry instanceof KeyStore.PrivateKeyEntry) {
                    return installFrom((KeyStore.PrivateKeyEntry) entry, i);
                }
            } else {
                Log.d("CertificateUtil", "Skipping non-key entry");
            }
        }
        return false;
    }

    public final List getAllUsersId() {
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List users = this.mUserManager.getUsers(true);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Iterator it = users.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((UserInfo) it.next()).getUserHandle().getIdentifier()));
            }
            return arrayList;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:111:0x0062, code lost:
    
        if (parseCert(r25, r21) == false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0158, code lost:
    
        if (r1.equalsIgnoreCase("CERT") != false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0027, code lost:
    
        if (extractPkcs12(r25, "", r21) != false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int installAsUser(java.lang.String r20, byte[] r21, java.lang.String r22, java.lang.String r23, int r24, int r25) {
        /*
            Method dump skipped, instructions count: 412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.utils.CertificateUtil.installAsUser(java.lang.String, byte[], java.lang.String, java.lang.String, int, int):int");
    }

    public final boolean installCaCertsToDefaultKeystore(KeyChainCRUD keyChainCRUD) {
        byte[] bArr;
        if (((ArrayList) this.mCaCerts).isEmpty()) {
            return true;
        }
        Iterator it = ((ArrayList) this.mCaCerts).iterator();
        boolean z = true;
        while (true) {
            byte[] bArr2 = null;
            if (!it.hasNext()) {
                break;
            }
            X509Certificate x509Certificate = (X509Certificate) it.next();
            if (x509Certificate.getSubjectX500Principal().equals(x509Certificate.getIssuerX500Principal())) {
                try {
                    bArr2 = Credentials.convertToPem(new Certificate[]{x509Certificate});
                } catch (Exception e) {
                    Log.e("CertificateUtil", "Error converting certificate to PEM: ", e);
                    z = false;
                }
                if (bArr2 != null) {
                    z &= keyChainCRUD.installCaCertificate(bArr2) != null;
                }
            }
        }
        Iterator it2 = ((ArrayList) this.mCaCerts).iterator();
        while (it2.hasNext()) {
            X509Certificate x509Certificate2 = (X509Certificate) it2.next();
            if (!x509Certificate2.getSubjectX500Principal().equals(x509Certificate2.getIssuerX500Principal())) {
                try {
                    bArr = Credentials.convertToPem(new Certificate[]{x509Certificate2});
                } catch (Exception e2) {
                    Log.e("CertificateUtil", "Error converting certificate to PEM: ", e2);
                    bArr = null;
                    z = false;
                }
                if (bArr != null) {
                    z = (keyChainCRUD.installCaCertificate(bArr) != null) & z;
                }
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("CaCerts put state for default keystore: ", "CertificateUtil", z);
        return z;
    }

    public final synchronized boolean installFrom(KeyStore.PrivateKeyEntry privateKeyEntry, int i) {
        try {
            ICertificatePolicy asInterface = ICertificatePolicy.Stub.asInterface(ServiceManager.getService("certificate_policy"));
            if (asInterface != null) {
                try {
                    try {
                        if (asInterface.isCertificateValidationAtInstallEnabledAsUser(i)) {
                            Certificate[] certificateChain = privateKeyEntry.getCertificateChain();
                            ArrayList arrayList = new ArrayList(certificateChain.length);
                            for (Certificate certificate : certificateChain) {
                                arrayList.add(new CertificateInfo((X509Certificate) certificate));
                            }
                            int validateChainAtInstallAsUser = asInterface.validateChainAtInstallAsUser(arrayList, i);
                            if (validateChainAtInstallAsUser != -1) {
                                Log.d("CertificateUtil", "certificate failed during validation");
                                asInterface.notifyCertificateFailureAsUser("installer_module", String.valueOf(validateChainAtInstallAsUser), false, i);
                                return false;
                            }
                        }
                    } catch (RemoteException unused) {
                        Log.d("CertificateUtil", "Failed talking to certificate policy");
                    }
                } catch (NullPointerException unused2) {
                    Log.d("CertificateUtil", "Certificate policy not found");
                }
            }
            this.mUserKey = privateKeyEntry.getPrivateKey();
            this.mUserCert = (X509Certificate) privateKeyEntry.getCertificate();
            Certificate[] certificateChain2 = privateKeyEntry.getCertificateChain();
            Log.d("CertificateUtil", "# certs extracted = " + certificateChain2.length);
            ArrayList arrayList2 = new ArrayList(certificateChain2.length);
            this.mCaCerts = arrayList2;
            for (Certificate certificate2 : certificateChain2) {
                X509Certificate x509Certificate = (X509Certificate) certificate2;
                if (isCa(x509Certificate)) {
                    arrayList2.add(x509Certificate);
                }
            }
            Log.d("CertificateUtil", "# ca certs extracted = " + ((ArrayList) this.mCaCerts).size());
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final boolean parseCert(int i, byte[] bArr) {
        int validateCertificateAtInstallAsUser;
        try {
            X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
            ICertificatePolicy asInterface = ICertificatePolicy.Stub.asInterface(ServiceManager.getService("certificate_policy"));
            if (asInterface != null) {
                try {
                    if (asInterface.isCertificateValidationAtInstallEnabledAsUser(i) && (validateCertificateAtInstallAsUser = asInterface.validateCertificateAtInstallAsUser(new CertificateInfo(x509Certificate), i)) != -1) {
                        Log.d("CertificateUtil", "certificate failed during validation");
                        asInterface.notifyCertificateFailureAsUser("installer_module", String.valueOf(validateCertificateAtInstallAsUser), false, i);
                        return false;
                    }
                } catch (RemoteException unused) {
                    Log.d("CertificateUtil", "Failed talking to certificate policy");
                } catch (NullPointerException unused2) {
                    Log.d("CertificateUtil", "Certificate policy not found");
                }
            }
            if (isCa(x509Certificate)) {
                Log.d("CertificateUtil", "got a CA cert");
                ((ArrayList) this.mCaCerts).add(x509Certificate);
            } else {
                Log.d("CertificateUtil", "got a user cert");
                this.mUserCert = x509Certificate;
            }
            return true;
        } catch (CertificateException e) {
            Log.w("CertificateUtil", "parseCert(): " + e);
            return false;
        }
    }

    public final void sendIntentToSettings(int i, boolean z) {
        List<UserInfo> enabledProfiles;
        UserManager userManager;
        if (z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.knox.intent.action.REFRESH_CREDENTIALS_UI_INTERNAL");
            ((PersonaManagerAdapter) ((IPersonaManagerAdapter) AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class))).getClass();
            if (SemPersonaManager.isKnoxId(i) && (userManager = this.mUserManager) != null) {
                i = userManager.getUserInfo(i).profileGroupId;
            }
            this.mContext.sendBroadcastAsUser(intent, new UserHandle(i), "com.samsung.android.knox.permission.KNOX_REFRESH_CREDENTIAL_UI_INTERNAL");
            UserManager userManager2 = this.mUserManager;
            if (userManager2 != null && (enabledProfiles = userManager2.getEnabledProfiles(i)) != null) {
                for (UserInfo userInfo : enabledProfiles) {
                    if (userInfo.isManagedProfile()) {
                        this.mContext.sendBroadcastAsUser(intent, new UserHandle(userInfo.id), "com.samsung.android.knox.permission.KNOX_REFRESH_CREDENTIAL_UI_INTERNAL");
                    }
                }
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
