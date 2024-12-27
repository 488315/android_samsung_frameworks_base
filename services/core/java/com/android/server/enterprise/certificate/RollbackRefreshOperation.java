package com.android.server.enterprise.certificate;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.Credentials;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.certificate.RollbackRefreshOperation.TrustedStoreOperation;
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

public final class RollbackRefreshOperation {
    public final CertificatePolicy mCertPolicy;
    public final Context mContext;
    public int mOperation;
    public int mUserId;
    public final CertificateUtil mUtils;
    public final Object mKeyStoreLock = new Object();
    public final Set mPendingKeystoreAction = new HashSet();
    public final int mPriority = 10;
    public final EdmKeyStore mUserKeyStore = EdmKeyStore.getInstance(1);
    public final EdmKeyStore mNativeKeyStore = EdmKeyStore.getInstance(2);

    public final class TrustedStoreOperation extends AsyncTask {
        public final /* synthetic */ int $r8$classId = 1;
        public final Object mAliases;
        public final Object mCerts;
        public final int mOperation;
        public final int mUserId;

        public TrustedStoreOperation(int i, int i2, Set set, Map map) {
            this.mOperation = i;
            this.mUserId = i2;
            this.mAliases = set;
            this.mCerts = map;
        }

        public TrustedStoreOperation(int i, List list, List list2, int i2) {
            this.mOperation = i;
            this.mAliases = list;
            this.mCerts = list2;
            this.mUserId = i2;
        }

        public static Map getPemMap(Map map) {
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = (HashMap) map;
            HashSet hashSet = new HashSet(hashMap2.entrySet());
            Iterator it = hashMap2.entrySet().iterator();
            while (it.hasNext()) {
                String removeAliasSeparator =
                        removeAliasSeparator((String) ((Map.Entry) it.next()).getKey());
                ArrayList arrayList = new ArrayList();
                HashSet hashSet2 = new HashSet();
                Iterator it2 = hashSet.iterator();
                while (it2.hasNext()) {
                    Map.Entry entry = (Map.Entry) it2.next();
                    if (removeAliasSeparator.equals(
                            removeAliasSeparator((String) entry.getKey()))) {
                        arrayList.add((X509Certificate) entry.getValue());
                        hashSet2.add(entry);
                    }
                }
                if (arrayList.size() > 0) {
                    hashMap.put(
                            removeAliasSeparator, CertificateUtil.convertX509ListToPem(arrayList));
                    hashSet.removeAll(hashSet2);
                }
            }
            return hashMap;
        }

        public static String removeAliasSeparator(String str) {
            int lastIndexOf = str.lastIndexOf("_#_");
            if (lastIndexOf == -1) {
                return str;
            }
            String substring = str.substring(lastIndexOf + 3);
            char[] cArr = Utils.HEX_DIGITS;
            try {
                Integer.parseInt(substring);
                return str.substring(0, lastIndexOf);
            } catch (NullPointerException | NumberFormatException unused) {
                return str;
            }
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            CertificateUtil.KeyChainCRUD keyChainCRUD;
            switch (this.$r8$classId) {
                case 0:
                    Boolean bool =
                            (Boolean)
                                    Binder.withCleanCallingIdentity(
                                            new FunctionalUtils.ThrowingSupplier() { // from class:
                                                // com.android.server.enterprise.certificate.RollbackRefreshOperation$TrustedStoreOperation$$ExternalSyntheticLambda0
                                                public final Object getOrThrow() {
                                                    RollbackRefreshOperation.TrustedStoreOperation
                                                            trustedStoreOperation =
                                                                    RollbackRefreshOperation
                                                                            .TrustedStoreOperation
                                                                            .this;
                                                    CertificateUtil.KeyChainCRUD keyChainCRUD2 =
                                                            new CertificateUtil.KeyChainCRUD(
                                                                    RollbackRefreshOperation.this
                                                                            .mContext,
                                                                    trustedStoreOperation.mUserId);
                                                    int i = trustedStoreOperation.mOperation;
                                                    if (i == 0) {
                                                        Iterator it =
                                                                ((List)
                                                                                trustedStoreOperation
                                                                                        .mCerts)
                                                                        .iterator();
                                                        while (it.hasNext()) {
                                                            keyChainCRUD2.installCaCertificate(
                                                                    Credentials.convertToPem(
                                                                            new Certificate[] {
                                                                                (X509Certificate)
                                                                                        it.next()
                                                                            }));
                                                        }
                                                    } else if (i == 1) {
                                                        for (String str :
                                                                (List)
                                                                        trustedStoreOperation
                                                                                .mAliases) {
                                                            if (keyChainCRUD2.connect()) {
                                                                try {
                                                                    keyChainCRUD2.mService
                                                                            .deleteCaCertificate(
                                                                                    str);
                                                                } catch (Exception e) {
                                                                    Log.e(
                                                                            "CertificateUtil",
                                                                            "Error in"
                                                                                + " KeyChainService.deleteCaCertificate",
                                                                            e);
                                                                }
                                                            } else {
                                                                Log.d(
                                                                        "CertificateUtil",
                                                                        "Aborting"
                                                                            + " deleteCaCertificate"
                                                                            + " operation");
                                                            }
                                                        }
                                                    }
                                                    keyChainCRUD2.disconnect();
                                                    return Boolean.TRUE;
                                                }
                                            });
                    bool.getClass();
                    return bool;
                default:
                    int intValue = ((Integer[]) objArr)[0].intValue();
                    int i = this.mOperation;
                    if (i == 0) {
                        synchronized (RollbackRefreshOperation.this.mKeyStoreLock) {
                            keyChainCRUD =
                                    new CertificateUtil.KeyChainCRUD(
                                            RollbackRefreshOperation.this.mContext, intValue);
                            try {
                                for (Map.Entry entry :
                                        ((HashMap) getPemMap((Map) this.mCerts)).entrySet()) {
                                    String[] m499$$Nest$smsplitCertTypeAlias =
                                            RollbackRefreshOperation
                                                    .m499$$Nest$smsplitCertTypeAlias(
                                                            (String) entry.getKey());
                                    if (m499$$Nest$smsplitCertTypeAlias != null) {
                                        reinstallCert(
                                                m499$$Nest$smsplitCertTypeAlias[0],
                                                m499$$Nest$smsplitCertTypeAlias[1],
                                                (byte[]) entry.getValue(),
                                                keyChainCRUD);
                                    }
                                }
                            } finally {
                                keyChainCRUD.disconnect();
                            }
                        }
                    } else if (i == 1) {
                        synchronized (RollbackRefreshOperation.this.mKeyStoreLock) {
                            try {
                                int i2 = this.mUserId == 4 ? -1 : 1010;
                                keyChainCRUD =
                                        new CertificateUtil.KeyChainCRUD(
                                                RollbackRefreshOperation.this.mContext, intValue);
                                Iterator it = ((Set) this.mAliases).iterator();
                                while (it.hasNext()) {
                                    String[] m499$$Nest$smsplitCertTypeAlias2 =
                                            RollbackRefreshOperation
                                                    .m499$$Nest$smsplitCertTypeAlias(
                                                            removeAliasSeparator(
                                                                    (String) it.next()));
                                    if (m499$$Nest$smsplitCertTypeAlias2 != null) {
                                        String str = m499$$Nest$smsplitCertTypeAlias2[0];
                                        String str2 = m499$$Nest$smsplitCertTypeAlias2[1];
                                        if (!"USRCERT_".equals(str)
                                                && !keyChainCRUD.isCertificateEntry(i2, str2)) {
                                            byte[] bArr = keyChainCRUD.get(i2, str2, "USRCERT_");
                                            String str3 = "alias = " + str2 + ", uid = " + i2;
                                            if (bArr == null) {
                                                Log.e(
                                                        "RollbackRefreshOperation",
                                                        "NativeKeyStoreOperation - Could not"
                                                            + " retrieve user certificate from "
                                                                + str3);
                                            } else if (keyChainCRUD.updateKeyPair(
                                                    i2, bArr, null, str2)) {
                                                Log.d(
                                                        "RollbackRefreshOperation",
                                                        "NativeKeyStoreOperation - certificate"
                                                            + " chain deleted successfully - "
                                                                + str3);
                                            } else {
                                                Log.e(
                                                        "RollbackRefreshOperation",
                                                        "NativeKeyStoreOperation - Could not delete"
                                                            + " certificate chain - "
                                                                + str3);
                                            }
                                        }
                                        String str4 =
                                                "alias = "
                                                        + str2
                                                        + ", type = "
                                                        + str
                                                        + ", uid = "
                                                        + i2;
                                        if (keyChainCRUD.deleteEntry(i2, str2)) {
                                            Log.d(
                                                    "RollbackRefreshOperation",
                                                    "NativeKeyStoreOperation - Entry deleted"
                                                        + " successfully - "
                                                            + str4);
                                        } else {
                                            Log.e(
                                                    "RollbackRefreshOperation",
                                                    "NativeKeyStoreOperation - Fail to delete entry"
                                                        + " - "
                                                            + str4);
                                        }
                                    }
                                }
                                keyChainCRUD.disconnect();
                            } catch (Throwable th) {
                                throw th;
                            } finally {
                            }
                        }
                    }
                    return Boolean.TRUE;
            }
        }

        public void reinstallCert(
                String str, String str2, byte[] bArr, CertificateUtil.KeyChainCRUD keyChainCRUD) {
            Log.d(
                    "RollbackRefreshOperation",
                    "reinstallCert - type = " + str + ", alias = " + str2);
            if (str == null || str2 == null || bArr == null) {
                return;
            }
            int i = this.mUserId == 4 ? -1 : 1010;
            String m =
                    SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                            .m(i, "alias = ", str2, ", uid = ");
            if ("USRCERT_".equals(str)) {
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        "reinstallCert - Could not perform rollback of a user certificate due to"
                            + " keystore2 changes - ",
                        m,
                        "RollbackRefreshOperation");
                return;
            }
            if ("CACERT_".equals(str)) {
                if (!keyChainCRUD.contains(i, str2)) {
                    if (keyChainCRUD.put(str2, i, null, null, bArr)) {
                        DualAppManagerService$$ExternalSyntheticOutline0.m(
                                "reinstallCert - CA cert successfully reinstalled - ",
                                m,
                                "RollbackRefreshOperation");
                        return;
                    } else {
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                "reinstallCert - Could not install CA cert - ",
                                m,
                                "RollbackRefreshOperation");
                        return;
                    }
                }
                byte[] bArr2 = keyChainCRUD.get(i, str2, "USRCERT_");
                if (bArr2 == null) {
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            "reinstallCert - Could not find user certificate in this entry - ",
                            m,
                            "RollbackRefreshOperation");
                    return;
                } else if (keyChainCRUD.updateKeyPair(i, bArr2, bArr, str2)) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "reinstallCert - CA cert(s) successfully reinserted in key entry - ",
                            m,
                            "RollbackRefreshOperation");
                    return;
                } else {
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            "reinstallCert - Could not reinsert CA cert(s) in key entry - ",
                            m,
                            "RollbackRefreshOperation");
                    return;
                }
            }
            if ("CACERT_CE_".equals(str)) {
                if (keyChainCRUD.put(str2, i, null, null, bArr)) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "reinstallCert - CA cert successfully reinstalled - ",
                            m,
                            "RollbackRefreshOperation");
                    return;
                } else {
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            "reinstallCert - Could not install CA cert - ",
                            m,
                            "RollbackRefreshOperation");
                    return;
                }
            }
            if ("CACERT_KE_".equals(str)) {
                if (!keyChainCRUD.contains(i, str2)) {
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            "reinstallCert - CA cert(s) cannot be reinstalled anymore as entry has"
                                + " been deleted - ",
                            m,
                            "RollbackRefreshOperation");
                    return;
                }
                byte[] bArr3 = keyChainCRUD.get(i, str2, "USRCERT_");
                if (bArr3 == null) {
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            "reinstallCert - Could not find user certificate in this entry - ",
                            m,
                            "RollbackRefreshOperation");
                } else if (keyChainCRUD.updateKeyPair(i, bArr3, bArr, str2)) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "reinstallCert - CA cert(s) successfully reinserted in key entry - ",
                            m,
                            "RollbackRefreshOperation");
                } else {
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            "reinstallCert - Could not reinsert CA cert(s) in key entry - ",
                            m,
                            "RollbackRefreshOperation");
                }
            }
        }
    }

    /* renamed from: -$$Nest$smsplitCertTypeAlias, reason: not valid java name */
    public static String[] m499$$Nest$smsplitCertTypeAlias(String str) {
        String[] strArr = {"CACERT_CE_", "CACERT_KE_", "CACERT_", "USRCERT_", "USRPKEY_"};
        for (int i = 0; i < 5; i++) {
            String str2 = strArr[i];
            if (str.startsWith(str2)) {
                return new String[] {str2, str.substring(str2.length())};
            }
        }
        return null;
    }

    public RollbackRefreshOperation(CertificatePolicy certificatePolicy, Context context) {
        this.mUtils = new CertificateUtil(context);
        this.mCertPolicy = certificatePolicy;
        this.mContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0049 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void execute(int r5, int r6) {
        /*
            r4 = this;
            r4.mOperation = r5
            r4.mUserId = r6
            int r5 = r4.mPriority
            android.os.Process.setThreadPriority(r5)
            int r5 = r4.mUserId
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            com.android.server.enterprise.utils.CertificateUtil r0 = r4.mUtils
            java.util.List r1 = r0.getAllUsersId()
            if (r5 == 0) goto L33
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            boolean r1 = r1.contains(r2)
            if (r1 == 0) goto L31
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r1.add(r5)
            goto L33
        L31:
            r5 = 0
            goto L47
        L33:
            com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda2 r5 = new com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda2
            r5.<init>()
            android.os.Binder.withCleanCallingIdentity(r5)
            int r5 = r6.size()
            java.lang.Integer[] r5 = new java.lang.Integer[r5]
            java.lang.Object[] r5 = r6.toArray(r5)
            java.lang.Integer[] r5 = (java.lang.Integer[]) r5
        L47:
            if (r5 != 0) goto L4a
            return
        L4a:
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            long r1 = android.os.Binder.clearCallingIdentity()
            android.os.UserManager r0 = r0.mUserManager     // Catch: java.lang.Throwable -> Lb3
            r3 = 1
            java.util.List r0 = r0.getUsers(r3)     // Catch: java.lang.Throwable -> Lb3
            android.os.Binder.restoreCallingIdentity(r1)
            int r1 = r4.mUserId
            if (r1 != 0) goto L63
            r6 = r0
            goto L7d
        L63:
            java.util.Iterator r0 = r0.iterator()
        L67:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L7d
            java.lang.Object r1 = r0.next()
            android.content.pm.UserInfo r1 = (android.content.pm.UserInfo) r1
            int r2 = r1.id
            int r3 = r4.mUserId
            if (r2 != r3) goto L67
            r6.add(r1)
            goto L67
        L7d:
            com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda0 r0 = new com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda0
            r0.<init>()
            java.lang.Object r6 = android.os.Binder.withCleanCallingIdentity(r0)
            java.util.List r6 = (java.util.List) r6
            com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda1 r0 = new com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda1
            r0.<init>()
            android.os.Binder.withCleanCallingIdentity(r0)
            java.util.Iterator r5 = r6.iterator()
        L94:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto Lb2
            java.lang.Object r6 = r5.next()
            android.content.pm.UserInfo r6 = (android.content.pm.UserInfo) r6
            android.os.UserHandle r6 = r6.getUserHandle()
            int r6 = r6.getIdentifier()
            com.android.server.enterprise.certificate.CertificatePolicy r0 = r4.mCertPolicy
            com.android.server.enterprise.utils.CertificateUtil r1 = r0.mUtils
            boolean r0 = r0.mBootCompleted
            r1.sendIntentToSettings(r6, r0)
            goto L94
        Lb2:
            return
        Lb3:
            r4 = move-exception
            android.os.Binder.restoreCallingIdentity(r1)
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.certificate.RollbackRefreshOperation.execute(int,"
                    + " int):void");
    }

    public final void executeRefreshOperation(Integer[] numArr, List list) {
        for (Integer num : numArr) {
            refreshNativeKeyStoreAsUser(4, num.intValue());
            if (num.intValue() == 0) {
                refreshNativeKeyStoreAsUser(2, 0);
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            final int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            CertificatePolicy certificatePolicy = this.mCertPolicy;
            final List genericListAsUser =
                    certificatePolicy.getGenericListAsUser(identifier, "systemDisabledList");
            final List genericListAsUser2 =
                    certificatePolicy.getGenericListAsUser(identifier, "systemPrevDisabledList");
            final Map certificates =
                    this.mUserKeyStore.getCertificates(
                            identifier,
                            certificatePolicy.getGenericListAsUser(identifier, "userRemovedList"));
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda3
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Type inference failed for: r18v0, types: [com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda3] */
                        /* JADX WARN: Type inference failed for: r18v1 */
                        /* JADX WARN: Type inference failed for: r18v16 */
                        /* JADX WARN: Type inference failed for: r18v2 */
                        /* JADX WARN: Type inference failed for: r18v26 */
                        /* JADX WARN: Type inference failed for: r18v27 */
                        /* JADX WARN: Type inference failed for: r18v28 */
                        /* JADX WARN: Type inference failed for: r18v29 */
                        /* JADX WARN: Type inference failed for: r18v3 */
                        /* JADX WARN: Type inference failed for: r18v4 */
                        /* JADX WARN: Type inference failed for: r18v5 */
                        /* JADX WARN: Type inference failed for: r18v6 */
                        /* JADX WARN: Type inference failed for: r18v7 */
                        /* JADX WARN: Type inference failed for: r18v8 */
                        /* JADX WARN: Type inference failed for: r18v9 */
                        public final void runOrThrow() {
                            String str;
                            Object obj;
                            IKeyChainService service;
                            CertificatePolicy certificatePolicy2;
                            IKeyChainService iKeyChainService;
                            byte[] certificateFromTrustCredential;
                            Iterator it2;
                            RollbackRefreshOperation rollbackRefreshOperation =
                                    RollbackRefreshOperation.this;
                            int i = identifier;
                            List list2 = genericListAsUser;
                            List list3 = genericListAsUser2;
                            Map map = certificates;
                            rollbackRefreshOperation.getClass();
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                KeyChain.KeyChainConnection bindAsUser =
                                                        KeyChain.bindAsUser(
                                                                rollbackRefreshOperation.mContext,
                                                                new UserHandle(i));
                                                try {
                                                    service = bindAsUser.getService();
                                                } catch (RemoteException e) {
                                                    e = e;
                                                    obj = ") ";
                                                } catch (IOException e2) {
                                                    e = e2;
                                                    obj = ") ";
                                                } catch (CertificateException e3) {
                                                    e = e3;
                                                    obj = ") ";
                                                } catch (Throwable th) {
                                                    th = th;
                                                    this = ") ";
                                                    bindAsUser.close();
                                                    throw th;
                                                }
                                                if (service == null) {
                                                    bindAsUser.close();
                                                    return;
                                                }
                                                Iterator it3 =
                                                        service.allSystemAliases().iterator();
                                                while (true) {
                                                    boolean hasNext = it3.hasNext();
                                                    certificatePolicy2 =
                                                            rollbackRefreshOperation.mCertPolicy;
                                                    if (!hasNext) {
                                                        break;
                                                    }
                                                    String str2 = (String) it3.next();
                                                    if (!list2.contains(str2)
                                                            && (certificateFromTrustCredential =
                                                                            service
                                                                                    .getCertificateFromTrustCredential(
                                                                                            str2,
                                                                                            true))
                                                                    != null) {
                                                        for (X509Certificate x509Certificate :
                                                                Credentials.convertFromPem(
                                                                        certificateFromTrustCredential)) {
                                                            if (x509Certificate != null) {
                                                                it2 = it3;
                                                                if (!certificatePolicy2
                                                                        .verifyCertificateTrustful(
                                                                                x509Certificate,
                                                                                2,
                                                                                i)) {
                                                                    list2.add(str2);
                                                                    if (!service.containsAlias(
                                                                            str2)) {
                                                                        list3.add(str2);
                                                                    }
                                                                }
                                                            } else {
                                                                it2 = it3;
                                                            }
                                                            it3 = it2;
                                                        }
                                                    }
                                                    it3 = it3;
                                                }
                                                for (String str3 : service.userAliases()) {
                                                    byte[] certificateFromTrustCredential2 =
                                                            service
                                                                    .getCertificateFromTrustCredential(
                                                                            str3, false);
                                                    if (certificateFromTrustCredential2 != null) {
                                                        for (X509Certificate x509Certificate2 :
                                                                Credentials.convertFromPem(
                                                                        certificateFromTrustCredential2)) {
                                                            if (x509Certificate2 != null) {
                                                                iKeyChainService = service;
                                                                if (!certificatePolicy2
                                                                        .verifyCertificateTrustful(
                                                                                x509Certificate2,
                                                                                3,
                                                                                i)) {
                                                                    map.put(str3, x509Certificate2);
                                                                }
                                                            } else {
                                                                iKeyChainService = service;
                                                            }
                                                            service = iKeyChainService;
                                                        }
                                                    }
                                                    service = service;
                                                }
                                                rollbackRefreshOperation.mUserKeyStore
                                                        .installCertificates(i, map);
                                                ArrayList arrayList = new ArrayList();
                                                arrayList.addAll(list2);
                                                arrayList.addAll(map.keySet());
                                                obj = ") ";
                                                try {
                                                    rollbackRefreshOperation
                                                            .new TrustedStoreOperation(
                                                                    1, arrayList, (List) null, i)
                                                            .execute(new Void[0]);
                                                    certificatePolicy2.putGenericListAsUser(
                                                            i, "systemDisabledList", list2);
                                                    certificatePolicy2.putGenericListAsUser(
                                                            i, "systemPrevDisabledList", list3);
                                                    certificatePolicy2.putGenericListAsUser(
                                                            i, "userRemovedList", map.keySet());
                                                    this = obj;
                                                } catch (RemoteException e4) {
                                                    e = e4;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "refreshSystemKeyStoreAsUser " + e);
                                                    this = obj;
                                                    bindAsUser.close();
                                                } catch (IOException e5) {
                                                    e = e5;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "refreshSystemKeyStoreAsUser " + e);
                                                    this = obj;
                                                    bindAsUser.close();
                                                } catch (CertificateException e6) {
                                                    e = e6;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "refreshSystemKeyStoreAsUser " + e);
                                                    this = obj;
                                                    bindAsUser.close();
                                                }
                                                try {
                                                    bindAsUser.close();
                                                } catch (AssertionError e7) {
                                                    e = e7;
                                                    str = this;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "refreshSystemKeyStoreAsUser("
                                                                    + i
                                                                    + str
                                                                    + e);
                                                } catch (RuntimeException e8) {
                                                    e = e8;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "refreshSystemKeyStoreAsUser("
                                                                    + i
                                                                    + this
                                                                    + e);
                                                }
                                            } catch (Throwable th2) {
                                                th = th2;
                                            }
                                        } catch (AssertionError e9) {
                                            e = e9;
                                            this = ") ";
                                            str = this;
                                            Log.e(
                                                    "RollbackRefreshOperation",
                                                    "refreshSystemKeyStoreAsUser(" + i + str + e);
                                        }
                                    } catch (InterruptedException e10) {
                                        Log.e(
                                                "RollbackRefreshOperation",
                                                "refreshSystemKeyStoreAsUser " + e10);
                                    }
                                } catch (AssertionError e11) {
                                    e = e11;
                                    str = ") ";
                                }
                            } catch (RuntimeException e12) {
                                e = e12;
                                this = ") ";
                            }
                        }
                    });
        }
    }

    public final void executeRollbackOperation(Integer[] numArr, List list) {
        for (Integer num : numArr) {
            rollbackNativeKeyStoreAsUser(4, num.intValue());
            if (num.intValue() == 0) {
                rollbackNativeKeyStoreAsUser(2, 0);
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            final int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            CertificatePolicy certificatePolicy = this.mCertPolicy;
            final List genericListAsUser =
                    certificatePolicy.getGenericListAsUser(identifier, "systemDisabledList");
            final List genericListAsUser2 =
                    certificatePolicy.getGenericListAsUser(identifier, "systemPrevDisabledList");
            final List genericListAsUser3 =
                    certificatePolicy.getGenericListAsUser(identifier, "userRemovedList");
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                            .ThrowingRunnable() { // from class:
                                                  // com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda4
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Type inference failed for: r20v0, types: [com.android.server.enterprise.certificate.RollbackRefreshOperation$$ExternalSyntheticLambda4] */
                        /* JADX WARN: Type inference failed for: r20v1 */
                        /* JADX WARN: Type inference failed for: r20v2 */
                        /* JADX WARN: Type inference failed for: r20v3 */
                        /* JADX WARN: Type inference failed for: r20v4 */
                        public final void runOrThrow() {
                            String str;
                            String str2;
                            IKeyChainService service;
                            CertificatePolicy certificatePolicy2;
                            Iterator it2;
                            String str3;
                            String str4;
                            IKeyChainService iKeyChainService;
                            Iterator it3;
                            RollbackRefreshOperation rollbackRefreshOperation =
                                    RollbackRefreshOperation.this;
                            int i = identifier;
                            List list2 = genericListAsUser;
                            List list3 = genericListAsUser2;
                            List list4 = arrayList;
                            List list5 = genericListAsUser3;
                            List list6 = arrayList2;
                            String str5 = ") ";
                            String str6 = "rollbackSystemKeyStoreAsUser(";
                            EdmKeyStore edmKeyStore = rollbackRefreshOperation.mUserKeyStore;
                            try {
                                try {
                                    try {
                                    } catch (InterruptedException e) {
                                        Log.e(
                                                "RollbackRefreshOperation",
                                                "rollbackSystemKeyStoreAsUser " + e);
                                        return;
                                    }
                                } catch (AssertionError e2) {
                                    e = e2;
                                    str = ") ";
                                    str2 = "rollbackSystemKeyStoreAsUser(";
                                }
                                try {
                                    try {
                                        try {
                                            KeyChain.KeyChainConnection bindAsUser =
                                                    KeyChain.bindAsUser(
                                                            rollbackRefreshOperation.mContext,
                                                            new UserHandle(i));
                                            try {
                                                service = bindAsUser.getService();
                                            } catch (RemoteException e3) {
                                                e = e3;
                                            } catch (IOException e4) {
                                                e = e4;
                                            } catch (CertificateException e5) {
                                                e = e5;
                                            } catch (Throwable th) {
                                                th = th;
                                            }
                                            if (service == null) {
                                                bindAsUser.close();
                                                return;
                                            }
                                            ArrayList arrayList3 = new ArrayList();
                                            Iterator it4 = list2.iterator();
                                            while (true) {
                                                boolean hasNext = it4.hasNext();
                                                String str7 = str5;
                                                certificatePolicy2 =
                                                        rollbackRefreshOperation.mCertPolicy;
                                                if (!hasNext) {
                                                    break;
                                                }
                                                try {
                                                    it2 = it4;
                                                    str3 = (String) it4.next();
                                                    str4 = str6;
                                                } catch (RemoteException e6) {
                                                    e = e6;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "rollbackSystemKeyStoreAsUser " + e);
                                                    bindAsUser.close();
                                                } catch (IOException e7) {
                                                    e = e7;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "rollbackSystemKeyStoreAsUser " + e);
                                                    bindAsUser.close();
                                                } catch (CertificateException e8) {
                                                    e = e8;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "rollbackSystemKeyStoreAsUser " + e);
                                                    bindAsUser.close();
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    bindAsUser.close();
                                                    throw th;
                                                }
                                                try {
                                                    byte[] certificateFromTrustCredential =
                                                            service
                                                                    .getCertificateFromTrustCredential(
                                                                            str3, true);
                                                    if (certificateFromTrustCredential != null) {
                                                        Iterator it5 =
                                                                Credentials.convertFromPem(
                                                                                certificateFromTrustCredential)
                                                                        .iterator();
                                                        while (it5.hasNext()) {
                                                            IKeyChainService iKeyChainService2 =
                                                                    service;
                                                            X509Certificate x509Certificate =
                                                                    (X509Certificate) it5.next();
                                                            if (x509Certificate != null) {
                                                                it3 = it5;
                                                                if (certificatePolicy2
                                                                        .verifyCertificateTrustful(
                                                                                x509Certificate,
                                                                                2,
                                                                                i)) {
                                                                    arrayList3.add(str3);
                                                                    if (list3.contains(str3)) {
                                                                        list3.remove(str3);
                                                                    } else {
                                                                        list4.add(x509Certificate);
                                                                    }
                                                                }
                                                            } else {
                                                                it3 = it5;
                                                            }
                                                            it5 = it3;
                                                            service = iKeyChainService2;
                                                        }
                                                        iKeyChainService = service;
                                                    } else {
                                                        iKeyChainService = service;
                                                        arrayList3.add(str3);
                                                        if (list3.contains(str3)) {
                                                            list3.remove(str3);
                                                        }
                                                    }
                                                    str5 = str7;
                                                    str6 = str4;
                                                    it4 = it2;
                                                    service = iKeyChainService;
                                                } catch (RemoteException e9) {
                                                    e = e9;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "rollbackSystemKeyStoreAsUser " + e);
                                                    bindAsUser.close();
                                                } catch (IOException e10) {
                                                    e = e10;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "rollbackSystemKeyStoreAsUser " + e);
                                                    bindAsUser.close();
                                                } catch (CertificateException e11) {
                                                    e = e11;
                                                    Log.e(
                                                            "RollbackRefreshOperation",
                                                            "rollbackSystemKeyStoreAsUser " + e);
                                                    bindAsUser.close();
                                                }
                                            }
                                            Iterator it6 = arrayList3.iterator();
                                            while (it6.hasNext()) {
                                                list2.remove((String) it6.next());
                                            }
                                            for (Map.Entry entry :
                                                    ((HashMap)
                                                                    edmKeyStore.getCertificates(
                                                                            i, list5))
                                                            .entrySet()) {
                                                String str8 = (String) entry.getKey();
                                                X509Certificate x509Certificate2 =
                                                        (X509Certificate) entry.getValue();
                                                if (x509Certificate2 != null
                                                        && certificatePolicy2
                                                                .verifyCertificateTrustful(
                                                                        x509Certificate2, 3, i)) {
                                                    list5.remove(str8);
                                                    list6.add(str8);
                                                    list4.add(x509Certificate2);
                                                }
                                            }
                                            edmKeyStore.removeCertificates(i, list6);
                                            rollbackRefreshOperation
                                                    .new TrustedStoreOperation(
                                                            0, (List) null, list4, i)
                                                    .execute(new Void[0]);
                                            certificatePolicy2.putGenericListAsUser(
                                                    i, "systemDisabledList", list2);
                                            certificatePolicy2.putGenericListAsUser(
                                                    i, "systemPrevDisabledList", list3);
                                            certificatePolicy2.putGenericListAsUser(
                                                    i, "userRemovedList", list5);
                                            bindAsUser.close();
                                        } catch (AssertionError e12) {
                                            e = e12;
                                            str = this;
                                            str2 = "rollbackSystemKeyStoreAsUser(";
                                            Log.e("RollbackRefreshOperation", str2 + i + str + e);
                                        } catch (RuntimeException e13) {
                                            e = e13;
                                            Log.e(
                                                    "RollbackRefreshOperation",
                                                    "rollbackSystemKeyStoreAsUser(" + i + this + e);
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                    }
                                } catch (AssertionError e14) {
                                    e = e14;
                                    this = ") ";
                                    str = this;
                                    str2 = "rollbackSystemKeyStoreAsUser(";
                                    Log.e("RollbackRefreshOperation", str2 + i + str + e);
                                }
                            } catch (RuntimeException e15) {
                                e = e15;
                                this = ") ";
                            }
                        }
                    });
        }
    }

    public final Map getRemovedCertificates(
            String str, int i, int i2, CertificateUtil.KeyChainCRUD keyChainCRUD) {
        int i3 = i == 4 ? -1 : 1010;
        String[] listAliases = keyChainCRUD.listAliases(i3, str);
        if (listAliases == null) {
            return Collections.EMPTY_MAP;
        }
        HashMap hashMap = new HashMap();
        for (String str2 : listAliases) {
            byte[] bArr = keyChainCRUD.get(i3, str2, str);
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
                        hashMap.put(
                                ("CACERT_".equals(str)
                                                ? keyChainCRUD.isCertificateEntry(i3, str2)
                                                        ? "CACERT_CE_"
                                                        : "CACERT_KE_"
                                                : str)
                                        + str2
                                        + "_#_"
                                        + i4,
                                x509Certificate);
                    }
                    i4++;
                }
            }
        }
        return hashMap;
    }

    public final void refreshNativeKeyStoreAsUser(int i, int i2) {
        String str = i == 2 ? "nativeRemovedList_wifi" : i == 4 ? "nativeRemovedList" : null;
        List genericListAsUser = this.mCertPolicy.getGenericListAsUser(i2, str);
        int i3 = i == 4 ? i2 : 1010;
        Map certificates = this.mNativeKeyStore.getCertificates(i3, genericListAsUser);
        synchronized (this.mKeyStoreLock) {
            try {
                if (i == 4 || i == 2) {
                    CertificateUtil.KeyChainCRUD keyChainCRUD =
                            new CertificateUtil.KeyChainCRUD(this.mContext, i2);
                    HashMap hashMap = (HashMap) certificates;
                    hashMap.putAll(getRemovedCertificates("CACERT_", i, i2, keyChainCRUD));
                    hashMap.putAll(getRemovedCertificates("USRCERT_", i, i2, keyChainCRUD));
                    keyChainCRUD.disconnect();
                    this.mNativeKeyStore.installCertificates(i3, certificates);
                    new TrustedStoreOperation(1, i, hashMap.keySet(), (Map) null)
                            .execute(Integer.valueOf(i2));
                    this.mCertPolicy.putGenericListAsUser(i2, str, hashMap.keySet());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void rollbackNativeKeyStoreAsUser(int i, int i2) {
        String str = i == 2 ? "nativeRemovedList_wifi" : i == 4 ? "nativeRemovedList" : null;
        CertificatePolicy certificatePolicy = this.mCertPolicy;
        List genericListAsUser = certificatePolicy.getGenericListAsUser(i2, str);
        int i3 = i == 4 ? i2 : 1010;
        EdmKeyStore edmKeyStore = this.mNativeKeyStore;
        Map certificates = edmKeyStore.getCertificates(i3, genericListAsUser);
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = (HashMap) certificates;
        for (Map.Entry entry : hashMap2.entrySet()) {
            X509Certificate x509Certificate = (X509Certificate) entry.getValue();
            if (x509Certificate != null
                    && certificatePolicy.verifyCertificateTrustful(x509Certificate, 3, i2)) {
                arrayList.add((String) entry.getKey());
                hashMap.put((String) entry.getKey(), x509Certificate);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            hashMap2.remove((String) it.next());
        }
        new TrustedStoreOperation(0, i, (Set) null, hashMap).execute(Integer.valueOf(i2));
        edmKeyStore.removeCertificates(i3, arrayList);
        certificatePolicy.putGenericListAsUser(i2, str, hashMap2.keySet());
    }
}
