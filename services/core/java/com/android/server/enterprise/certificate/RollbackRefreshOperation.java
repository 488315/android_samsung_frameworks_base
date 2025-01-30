package com.android.server.enterprise.certificate;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.Credentials;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.security.KeyStore;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.certificate.RollbackRefreshOperation;
import com.android.server.enterprise.utils.CertificateUtil;
import com.android.server.enterprise.utils.Utils;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class RollbackRefreshOperation {
    public final CertificatePolicy mCertPolicy;
    public Context mContext;
    public int mOperation;
    public int mUserId;
    public CertificateUtil mUtils;
    public final Object mKeyStoreLock = new Object();
    public Set mPendingKeystoreAction = new HashSet();
    public final int mPriority = 10;
    public EdmKeyStore mUserKeyStore = EdmKeyStore.getInstance(1);
    public EdmKeyStore mNativeKeyStore = EdmKeyStore.getInstance(2);

    public final String selectNativeKeystoreUid(int i) {
        if (i == 2) {
            return "nativeRemovedList_wifi";
        }
        if (i == 4) {
            return "nativeRemovedList";
        }
        return null;
    }

    public RollbackRefreshOperation(CertificatePolicy certificatePolicy, Context context) {
        this.mUtils = new CertificateUtil(context);
        this.mCertPolicy = certificatePolicy;
        this.mContext = context;
    }

    public final void executeRefreshOperation(Integer[] numArr, List list) {
        for (Integer num : numArr) {
            refreshNativeKeyStoreAsUser(4, num.intValue());
            if (num.intValue() == 0) {
                refreshNativeKeyStore(2);
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            refreshSystemKeyStoreAsUser(((UserInfo) it.next()).getUserHandle().getIdentifier());
        }
    }

    public final void executeRollbackOperation(Integer[] numArr, List list) {
        for (Integer num : numArr) {
            rollbackNativeKeyStoreAsUser(4, num.intValue());
            if (num.intValue() == 0) {
                rollbackNativeKeyStore(2);
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            rollbackSystemKeyStoreAsUser(((UserInfo) it.next()).getUserHandle().getIdentifier());
        }
    }

    public void execute(int i, int i2) {
        this.mOperation = i;
        this.mUserId = i2;
        Process.setThreadPriority(this.mPriority);
        final Integer[] usersKeystoreUnlocked = getUsersKeystoreUnlocked(this.mUserId);
        if (usersKeystoreUnlocked == null) {
            return;
        }
        final List arrayList = new ArrayList();
        List<UserInfo> allUsersInfo = this.mUtils.getAllUsersInfo();
        if (this.mUserId == 0) {
            arrayList = allUsersInfo;
        } else {
            for (UserInfo userInfo : allUsersInfo) {
                if (userInfo.id == this.mUserId) {
                    arrayList.add(userInfo);
                }
            }
        }
        final List list = (List) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda0
            public final Object getOrThrow() {
                List lambda$execute$0;
                lambda$execute$0 = RollbackRefreshOperation.this.lambda$execute$0(arrayList);
                return lambda$execute$0;
            }
        });
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda1
            public final void runOrThrow() {
                RollbackRefreshOperation.this.lambda$execute$1(usersKeystoreUnlocked, list);
            }
        });
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.mCertPolicy.sendIntentToSettings(((UserInfo) it.next()).getUserHandle().getIdentifier());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ List lambda$execute$0(List list) {
        return "2.0".equals(getPersonaManagerAdapter().getKnoxInfo().getString("version")) ? pruneDeletedContainerProfiles(list) : list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$execute$1(Integer[] numArr, List list) {
        int i = this.mOperation;
        if (i == 0) {
            executeRollbackOperation(numArr, list);
            return;
        }
        if (i == 1) {
            executeRefreshOperation(numArr, list);
        } else {
            if (i != 2) {
                return;
            }
            executeRollbackOperation(numArr, list);
            executeRefreshOperation(numArr, list);
        }
    }

    public final IPersonaManagerAdapter getPersonaManagerAdapter() {
        return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
    }

    public final List pruneDeletedContainerProfiles(List list) {
        int i = 0;
        while (i < list.size()) {
            UserInfo userInfo = (UserInfo) list.get(i);
            if (userInfo != null && getPersonaManagerAdapter().isValidKnoxId(userInfo.id) && !userInfo.isEnabled()) {
                list.remove(i);
                i--;
            }
            i++;
        }
        return list;
    }

    public final void rollbackSystemKeyStoreAsUser(final int i) {
        final List genericListAsUser = this.mCertPolicy.getGenericListAsUser("systemDisabledList", i);
        final List genericListAsUser2 = this.mCertPolicy.getGenericListAsUser("systemPrevDisabledList", i);
        final List genericListAsUser3 = this.mCertPolicy.getGenericListAsUser("userRemovedList", i);
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda2
            public final void runOrThrow() {
                RollbackRefreshOperation.this.lambda$rollbackSystemKeyStoreAsUser$2(i, genericListAsUser, genericListAsUser2, arrayList, genericListAsUser3, arrayList2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rollbackSystemKeyStoreAsUser$2(int i, List list, List list2, List list3, List list4, List list5) {
        String str;
        String str2;
        KeyChain.KeyChainConnection bindAsUser;
        IKeyChainService service;
        IKeyChainService iKeyChainService;
        Iterator it;
        Iterator it2;
        Iterator it3;
        try {
            try {
                try {
                    bindAsUser = KeyChain.bindAsUser(this.mContext, new UserHandle(i));
                } catch (InterruptedException e) {
                    Log.e("RollbackRefreshOperation", "rollbackSystemKeyStoreAsUser " + e);
                    return;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                service = bindAsUser.getService();
            } catch (RemoteException e2) {
                e = e2;
                str = ") ";
            } catch (IOException e3) {
                e = e3;
                str = ") ";
            } catch (CertificateException e4) {
                e = e4;
                str = ") ";
            } catch (Throwable th2) {
                th = th2;
                str = ") ";
                bindAsUser.close();
                throw th;
            }
            if (service != null) {
                ArrayList arrayList = new ArrayList();
                Iterator it4 = list.iterator();
                while (it4.hasNext()) {
                    String str3 = (String) it4.next();
                    byte[] certificateFromTrustCredential = service.getCertificateFromTrustCredential(str3, true);
                    if (certificateFromTrustCredential != null) {
                        Iterator it5 = Credentials.convertFromPem(certificateFromTrustCredential).iterator();
                        while (it5.hasNext()) {
                            IKeyChainService iKeyChainService2 = service;
                            X509Certificate x509Certificate = (X509Certificate) it5.next();
                            if (x509Certificate != null) {
                                it2 = it4;
                                it3 = it5;
                                if (this.mCertPolicy.verifyCertificateTrustful(x509Certificate, 2, i)) {
                                    arrayList.add(str3);
                                    if (!list2.contains(str3)) {
                                        list3.add(x509Certificate);
                                    } else {
                                        list2.remove(str3);
                                    }
                                }
                            } else {
                                it2 = it4;
                                it3 = it5;
                            }
                            it4 = it2;
                            service = iKeyChainService2;
                            it5 = it3;
                        }
                        iKeyChainService = service;
                        it = it4;
                    } else {
                        iKeyChainService = service;
                        it = it4;
                        arrayList.add(str3);
                        if (list2.contains(str3)) {
                            list2.remove(str3);
                        }
                    }
                    it4 = it;
                    service = iKeyChainService;
                }
                Iterator it6 = arrayList.iterator();
                while (it6.hasNext()) {
                    list.remove((String) it6.next());
                }
                for (Map.Entry entry : this.mUserKeyStore.getCertificates(list4, i).entrySet()) {
                    String str4 = (String) entry.getKey();
                    X509Certificate x509Certificate2 = (X509Certificate) entry.getValue();
                    if (x509Certificate2 != null && this.mCertPolicy.verifyCertificateTrustful(x509Certificate2, 3, i)) {
                        list4.remove(str4);
                        list5.add(str4);
                        list3.add(x509Certificate2);
                    }
                }
                this.mUserKeyStore.removeCertificates(list5, i);
                str = ") ";
                try {
                    try {
                        new TrustedStoreOperation(0, null, list3, i).execute(new Void[0]);
                        this.mCertPolicy.putGenericListAsUser("systemDisabledList", list, i);
                        this.mCertPolicy.putGenericListAsUser("systemPrevDisabledList", list2, i);
                        this.mCertPolicy.putGenericListAsUser("userRemovedList", list4, i);
                    } catch (RemoteException e5) {
                        e = e5;
                        Log.e("RollbackRefreshOperation", "rollbackSystemKeyStoreAsUser " + e);
                        bindAsUser.close();
                        return;
                    } catch (IOException e6) {
                        e = e6;
                        Log.e("RollbackRefreshOperation", "rollbackSystemKeyStoreAsUser " + e);
                        bindAsUser.close();
                        return;
                    } catch (CertificateException e7) {
                        e = e7;
                        Log.e("RollbackRefreshOperation", "rollbackSystemKeyStoreAsUser " + e);
                        bindAsUser.close();
                        return;
                    }
                    bindAsUser.close();
                    return;
                } catch (AssertionError e8) {
                    e = e8;
                    str2 = str;
                    Log.e("RollbackRefreshOperation", "rollbackSystemKeyStoreAsUser(" + i + str2 + e);
                } catch (RuntimeException e9) {
                    e = e9;
                    Log.e("RollbackRefreshOperation", "rollbackSystemKeyStoreAsUser(" + i + str + e);
                }
            }
            bindAsUser.close();
        } catch (AssertionError e10) {
            e = e10;
            str2 = ") ";
            Log.e("RollbackRefreshOperation", "rollbackSystemKeyStoreAsUser(" + i + str2 + e);
        } catch (RuntimeException e11) {
            e = e11;
            str = ") ";
            Log.e("RollbackRefreshOperation", "rollbackSystemKeyStoreAsUser(" + i + str + e);
        }
    }

    public final void rollbackNativeKeyStore(int i) {
        rollbackNativeKeyStoreAsUser(i, 0);
    }

    public final void rollbackNativeKeyStoreAsUser(int i, int i2) {
        String selectNativeKeystoreUid = selectNativeKeystoreUid(i);
        List genericListAsUser = this.mCertPolicy.getGenericListAsUser(selectNativeKeystoreUid, i2);
        int i3 = i == 4 ? i2 : 1010;
        Map certificates = this.mNativeKeyStore.getCertificates(genericListAsUser, i3);
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : certificates.entrySet()) {
            X509Certificate x509Certificate = (X509Certificate) entry.getValue();
            if (x509Certificate != null && this.mCertPolicy.verifyCertificateTrustful(x509Certificate, 3, i2)) {
                arrayList.add((String) entry.getKey());
                hashMap.put((String) entry.getKey(), x509Certificate);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            certificates.remove((String) it.next());
        }
        new NativeKeyStoreOperation(0, i, null, hashMap).execute(Integer.valueOf(i2));
        this.mNativeKeyStore.removeCertificates(arrayList, i3);
        this.mCertPolicy.putGenericListAsUser(selectNativeKeystoreUid, certificates.keySet(), i2);
    }

    public final void refreshSystemKeyStoreAsUser(final int i) {
        final List genericListAsUser = this.mCertPolicy.getGenericListAsUser("systemDisabledList", i);
        final List genericListAsUser2 = this.mCertPolicy.getGenericListAsUser("systemPrevDisabledList", i);
        final Map certificates = this.mUserKeyStore.getCertificates(this.mCertPolicy.getGenericListAsUser("userRemovedList", i), i);
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                RollbackRefreshOperation.this.lambda$refreshSystemKeyStoreAsUser$3(i, genericListAsUser, genericListAsUser2, certificates);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshSystemKeyStoreAsUser$3(int i, List list, List list2, Map map) {
        IKeyChainService service;
        byte[] certificateFromTrustCredential;
        try {
            KeyChain.KeyChainConnection bindAsUser = KeyChain.bindAsUser(this.mContext, new UserHandle(i));
            try {
                try {
                    try {
                        service = bindAsUser.getService();
                    } catch (CertificateException e) {
                        Log.e("RollbackRefreshOperation", "refreshSystemKeyStoreAsUser " + e);
                    }
                } catch (RemoteException e2) {
                    Log.e("RollbackRefreshOperation", "refreshSystemKeyStoreAsUser " + e2);
                } catch (IOException e3) {
                    Log.e("RollbackRefreshOperation", "refreshSystemKeyStoreAsUser " + e3);
                }
                if (service == null) {
                    return;
                }
                for (String str : service.allSystemAliases()) {
                    if (!list.contains(str) && (certificateFromTrustCredential = service.getCertificateFromTrustCredential(str, true)) != null) {
                        for (X509Certificate x509Certificate : Credentials.convertFromPem(certificateFromTrustCredential)) {
                            if (x509Certificate != null && !this.mCertPolicy.verifyCertificateTrustful(x509Certificate, 2, i)) {
                                list.add(str);
                                if (!service.containsAlias(str)) {
                                    list2.add(str);
                                }
                            }
                        }
                    }
                }
                for (String str2 : service.userAliases()) {
                    byte[] certificateFromTrustCredential2 = service.getCertificateFromTrustCredential(str2, false);
                    if (certificateFromTrustCredential2 != null) {
                        for (X509Certificate x509Certificate2 : Credentials.convertFromPem(certificateFromTrustCredential2)) {
                            if (x509Certificate2 != null && !this.mCertPolicy.verifyCertificateTrustful(x509Certificate2, 3, i)) {
                                map.put(str2, x509Certificate2);
                            }
                        }
                    }
                }
                this.mUserKeyStore.installCertificates(map, i);
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(list);
                arrayList.addAll(map.keySet());
                new TrustedStoreOperation(1, arrayList, null, i).execute(new Void[0]);
                this.mCertPolicy.putGenericListAsUser("systemDisabledList", list, i);
                this.mCertPolicy.putGenericListAsUser("systemPrevDisabledList", list2, i);
                this.mCertPolicy.putGenericListAsUser("userRemovedList", map.keySet(), i);
            } finally {
                bindAsUser.close();
            }
        } catch (AssertionError e4) {
            Log.e("RollbackRefreshOperation", "refreshSystemKeyStoreAsUser(" + i + ") " + e4);
        } catch (InterruptedException e5) {
            Log.e("RollbackRefreshOperation", "refreshSystemKeyStoreAsUser " + e5);
        } catch (RuntimeException e6) {
            Log.e("RollbackRefreshOperation", "refreshSystemKeyStoreAsUser(" + i + ") " + e6);
        }
    }

    public final void refreshNativeKeyStore(int i) {
        refreshNativeKeyStoreAsUser(i, 0);
    }

    public final void refreshNativeKeyStoreAsUser(int i, int i2) {
        String selectNativeKeystoreUid = selectNativeKeystoreUid(i);
        List genericListAsUser = this.mCertPolicy.getGenericListAsUser(selectNativeKeystoreUid, i2);
        int i3 = i == 4 ? i2 : 1010;
        Map certificates = this.mNativeKeyStore.getCertificates(genericListAsUser, i3);
        synchronized (this.mKeyStoreLock) {
            if (i == 4 || i == 2) {
                CertificateUtil.KeyChainCRUD keyChainCRUD = new CertificateUtil.KeyChainCRUD(this.mContext, i2);
                certificates.putAll(getRemovedCertificates("CACERT_", i, i2, keyChainCRUD));
                certificates.putAll(getRemovedCertificates("USRCERT_", i, i2, keyChainCRUD));
                keyChainCRUD.disconnect();
                this.mNativeKeyStore.installCertificates(certificates, i3);
                new NativeKeyStoreOperation(1, i, certificates.keySet(), null).execute(Integer.valueOf(i2));
                this.mCertPolicy.putGenericListAsUser(selectNativeKeystoreUid, certificates.keySet(), i2);
            }
        }
    }

    public final Map getRemovedCertificates(String str, int i, int i2, CertificateUtil.KeyChainCRUD keyChainCRUD) {
        String str2;
        int i3 = i == 4 ? -1 : 1010;
        String[] listAliases = keyChainCRUD.listAliases(str, i3);
        if (listAliases == null) {
            return Collections.EMPTY_MAP;
        }
        HashMap hashMap = new HashMap();
        for (String str3 : listAliases) {
            byte[] bArr = keyChainCRUD.get(str3, str, i3);
            if (bArr == null) {
                return hashMap;
            }
            List<X509Certificate> certificates = CertificateUtil.toCertificates(bArr);
            if (certificates.isEmpty()) {
                return hashMap;
            }
            int i4 = 1;
            for (X509Certificate x509Certificate : certificates) {
                if (x509Certificate != null) {
                    if (!this.mCertPolicy.verifyCertificateTrustful(x509Certificate, 3, i2)) {
                        if ("CACERT_".equals(str)) {
                            str2 = keyChainCRUD.isCertificateEntry(str3, i3) ? "CACERT_CE_" : "CACERT_KE_";
                        } else {
                            str2 = str;
                        }
                        hashMap.put(str2 + str3 + "_#_" + i4, x509Certificate);
                    }
                    i4++;
                }
            }
        }
        return hashMap;
    }

    public static String[] splitCertTypeAlias(String str) {
        String[] strArr = {"CACERT_CE_", "CACERT_KE_", "CACERT_", "USRCERT_", "USRPKEY_"};
        for (int i = 0; i < 5; i++) {
            String str2 = strArr[i];
            if (str.startsWith(str2)) {
                return new String[]{str2, str.substring(str2.length())};
            }
        }
        return null;
    }

    public class TrustedStoreOperation extends AsyncTask {
        public List mAliases;
        public List mCerts;
        public int mOperation;
        public int mUserId;

        public TrustedStoreOperation(int i, List list, List list2, int i2) {
            this.mOperation = i;
            this.mAliases = list;
            this.mCerts = list2;
            this.mUserId = i2;
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.enterprise.certificate.RollbackRefreshOperation$TrustedStoreOperation$$ExternalSyntheticLambda0
                public final Object getOrThrow() {
                    Boolean lambda$doInBackground$0;
                    lambda$doInBackground$0 = RollbackRefreshOperation.TrustedStoreOperation.this.lambda$doInBackground$0();
                    return lambda$doInBackground$0;
                }
            })).booleanValue());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Boolean lambda$doInBackground$0() {
            try {
                KeyChain.KeyChainConnection bindAsUser = KeyChain.bindAsUser(RollbackRefreshOperation.this.mContext, new UserHandle(this.mUserId));
                IKeyChainService service = bindAsUser.getService();
                try {
                    if (service == null) {
                        bindAsUser.close();
                        return Boolean.FALSE;
                    }
                    try {
                        try {
                            int i = this.mOperation;
                            if (i == 0) {
                                Iterator it = this.mCerts.iterator();
                                while (it.hasNext()) {
                                    service.installCaCertificate(Credentials.convertToPem(new Certificate[]{(X509Certificate) it.next()}));
                                }
                            } else if (i == 1) {
                                Iterator it2 = this.mAliases.iterator();
                                while (it2.hasNext()) {
                                    service.deleteCaCertificate((String) it2.next());
                                }
                            }
                            return Boolean.TRUE;
                        } catch (RemoteException e) {
                            Log.e("RollbackRefreshOperation", "TrustedStoreOperation " + e);
                            return Boolean.FALSE;
                        }
                    } catch (IOException e2) {
                        Log.e("RollbackRefreshOperation", "TrustedStoreOperation " + e2);
                        return Boolean.FALSE;
                    } catch (CertificateException e3) {
                        Log.e("RollbackRefreshOperation", "TrustedStoreOperation " + e3);
                        return Boolean.FALSE;
                    }
                } finally {
                    bindAsUser.close();
                }
            } catch (AssertionError e4) {
                Log.e("RollbackRefreshOperation", "TrustedStoreOperation(" + this.mUserId + ") " + e4);
            } catch (InterruptedException e5) {
                Log.e("RollbackRefreshOperation", "TrustedStoreOperation " + e5);
            } catch (RuntimeException e6) {
                Log.e("RollbackRefreshOperation", "TrustedStoreOperation(" + this.mUserId + ") " + e6);
            }
        }
    }

    public class NativeKeyStoreOperation extends AsyncTask {
        public Set mAliases;
        public Map mCerts;
        public int mKeystoreType;
        public int mOperation;

        public NativeKeyStoreOperation(int i, int i2, Set set, Map map) {
            this.mOperation = i;
            this.mKeystoreType = i2;
            this.mAliases = set;
            this.mCerts = map;
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Integer... numArr) {
            CertificateUtil.KeyChainCRUD keyChainCRUD;
            int intValue = numArr[0].intValue();
            int i = this.mOperation;
            if (i == 0) {
                synchronized (RollbackRefreshOperation.this.mKeyStoreLock) {
                    keyChainCRUD = new CertificateUtil.KeyChainCRUD(RollbackRefreshOperation.this.mContext, intValue);
                    try {
                        for (Map.Entry entry : getPemMap(this.mCerts).entrySet()) {
                            String[] splitCertTypeAlias = RollbackRefreshOperation.splitCertTypeAlias((String) entry.getKey());
                            if (splitCertTypeAlias != null) {
                                reinstallCert(splitCertTypeAlias[0], splitCertTypeAlias[1], (byte[]) entry.getValue(), keyChainCRUD);
                            }
                        }
                    } finally {
                    }
                }
            } else if (i == 1) {
                synchronized (RollbackRefreshOperation.this.mKeyStoreLock) {
                    int i2 = this.mKeystoreType == 4 ? -1 : 1010;
                    keyChainCRUD = new CertificateUtil.KeyChainCRUD(RollbackRefreshOperation.this.mContext, intValue);
                    try {
                        Iterator it = this.mAliases.iterator();
                        while (it.hasNext()) {
                            String[] splitCertTypeAlias2 = RollbackRefreshOperation.splitCertTypeAlias(removeAliasSeparator((String) it.next()));
                            if (splitCertTypeAlias2 != null) {
                                String str = splitCertTypeAlias2[0];
                                String str2 = splitCertTypeAlias2[1];
                                if (!"USRCERT_".equals(str) && !keyChainCRUD.isCertificateEntry(str2, i2)) {
                                    byte[] bArr = keyChainCRUD.get(str2, "USRCERT_", i2);
                                    String str3 = "alias = " + str2 + ", uid = " + i2;
                                    if (bArr == null) {
                                        Log.e("RollbackRefreshOperation", "NativeKeyStoreOperation - Could not retrieve user certificate from " + str3);
                                    } else if (keyChainCRUD.updateKeyPair(str2, bArr, null, i2)) {
                                        Log.d("RollbackRefreshOperation", "NativeKeyStoreOperation - certificate chain deleted successfully - " + str3);
                                    } else {
                                        Log.e("RollbackRefreshOperation", "NativeKeyStoreOperation - Could not delete certificate chain - " + str3);
                                    }
                                }
                                String str4 = "alias = " + str2 + ", type = " + str + ", uid = " + i2;
                                if (keyChainCRUD.deleteEntry(str2, i2)) {
                                    Log.d("RollbackRefreshOperation", "NativeKeyStoreOperation - Entry deleted successfully - " + str4);
                                } else {
                                    Log.e("RollbackRefreshOperation", "NativeKeyStoreOperation - Fail to delete entry - " + str4);
                                }
                            }
                        }
                        keyChainCRUD.disconnect();
                    } finally {
                    }
                }
            }
            return Boolean.TRUE;
        }

        public final String removeAliasSeparator(String str) {
            int lastIndexOf = str.lastIndexOf("_#_");
            return (lastIndexOf == -1 || !Utils.isInteger(str.substring(lastIndexOf + 3))) ? str : str.substring(0, lastIndexOf);
        }

        public final Map getPemMap(Map map) {
            HashMap hashMap = new HashMap();
            HashSet hashSet = new HashSet(map.entrySet());
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                String removeAliasSeparator = removeAliasSeparator((String) ((Map.Entry) it.next()).getKey());
                ArrayList arrayList = new ArrayList();
                HashSet hashSet2 = new HashSet();
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    Map.Entry entry = (Map.Entry) it2.next();
                    if (removeAliasSeparator.equals(removeAliasSeparator((String) entry.getKey()))) {
                        arrayList.add((X509Certificate) entry.getValue());
                        hashSet2.add(entry);
                    }
                }
                if (arrayList.size() > 0) {
                    hashMap.put(removeAliasSeparator, CertificateUtil.convertX509ListToPem(arrayList));
                    hashSet.removeAll(hashSet2);
                }
            }
            return hashMap;
        }

        public final void reinstallCert(String str, String str2, byte[] bArr, CertificateUtil.KeyChainCRUD keyChainCRUD) {
            Log.d("RollbackRefreshOperation", "reinstallCert - type = " + str + ", alias = " + str2);
            if (str == null || str2 == null || bArr == null) {
                return;
            }
            int i = this.mKeystoreType == 4 ? -1 : 1010;
            String str3 = "alias = " + str2 + ", uid = " + i;
            if ("USRCERT_".equals(str)) {
                Log.e("RollbackRefreshOperation", "reinstallCert - Could not perform rollback of a user certificate due to keystore2 changes - " + str3);
                return;
            }
            if ("CACERT_".equals(str)) {
                if (keyChainCRUD.contains(str2, i)) {
                    byte[] bArr2 = keyChainCRUD.get(str2, "USRCERT_", i);
                    if (bArr2 != null) {
                        if (!keyChainCRUD.updateKeyPair(str2, bArr2, bArr, i)) {
                            Log.e("RollbackRefreshOperation", "reinstallCert - Could not reinsert CA cert(s) in key entry - " + str3);
                            return;
                        }
                        Log.d("RollbackRefreshOperation", "reinstallCert - CA cert(s) successfully reinserted in key entry - " + str3);
                        return;
                    }
                    Log.e("RollbackRefreshOperation", "reinstallCert - Could not find user certificate in this entry - " + str3);
                    return;
                }
                if (!keyChainCRUD.put(null, null, bArr, str2, i)) {
                    Log.e("RollbackRefreshOperation", "reinstallCert - Could not install CA cert - " + str3);
                    return;
                }
                Log.d("RollbackRefreshOperation", "reinstallCert - CA cert successfully reinstalled - " + str3);
                return;
            }
            if ("CACERT_CE_".equals(str)) {
                if (!keyChainCRUD.put(null, null, bArr, str2, i)) {
                    Log.e("RollbackRefreshOperation", "reinstallCert - Could not install CA cert - " + str3);
                    return;
                }
                Log.d("RollbackRefreshOperation", "reinstallCert - CA cert successfully reinstalled - " + str3);
                return;
            }
            if ("CACERT_KE_".equals(str)) {
                if (keyChainCRUD.contains(str2, i)) {
                    byte[] bArr3 = keyChainCRUD.get(str2, "USRCERT_", i);
                    if (bArr3 != null) {
                        if (!keyChainCRUD.updateKeyPair(str2, bArr3, bArr, i)) {
                            Log.e("RollbackRefreshOperation", "reinstallCert - Could not reinsert CA cert(s) in key entry - " + str3);
                            return;
                        }
                        Log.d("RollbackRefreshOperation", "reinstallCert - CA cert(s) successfully reinserted in key entry - " + str3);
                        return;
                    }
                    Log.e("RollbackRefreshOperation", "reinstallCert - Could not find user certificate in this entry - " + str3);
                    return;
                }
                Log.e("RollbackRefreshOperation", "reinstallCert - CA cert(s) cannot be reinstalled anymore as entry has been deleted - " + str3);
            }
        }
    }

    public final Integer[] getUsersKeystoreUnlocked(int i) {
        final ArrayList arrayList = new ArrayList();
        final List allUsersId = this.mUtils.getAllUsersId();
        if (i != 0) {
            if (!allUsersId.contains(Integer.valueOf(i))) {
                return null;
            }
            allUsersId = new ArrayList();
            allUsersId.add(Integer.valueOf(i));
        }
        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                RollbackRefreshOperation.this.lambda$getUsersKeystoreUnlocked$4(allUsersId, arrayList);
            }
        });
        return (Integer[]) arrayList.toArray(new Integer[arrayList.size()]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getUsersKeystoreUnlocked$4(List list, List list2) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (isNativeKeyStoreUnlockedAsUser(intValue)) {
                list2.add(Integer.valueOf(intValue));
            } else {
                this.mPendingKeystoreAction.add(Integer.valueOf(intValue));
            }
        }
    }

    public boolean hasPendingActionForUser(int i) {
        return this.mPendingKeystoreAction.contains(Integer.valueOf(i));
    }

    public boolean removeUserIdFromPendingList(int i) {
        return this.mPendingKeystoreAction.remove(Integer.valueOf(i));
    }

    public void initPendingActionList() {
        Iterator it = this.mUtils.getAllUsersId().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (!isNativeKeyStoreUnlockedAsUser(intValue)) {
                this.mPendingKeystoreAction.add(Integer.valueOf(intValue));
            } else {
                execute(2, intValue);
            }
        }
    }

    public final boolean isNativeKeyStoreUnlockedAsUser(int i) {
        try {
            return KeyStore.getInstance().state(i) != KeyStore.State.LOCKED;
        } catch (AssertionError e) {
            Log.d("RollbackRefreshOperation", "Keystore State Error: " + e.getMessage());
            return false;
        }
    }
}
