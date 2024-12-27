package com.android.server.enterprise.certificate;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.util.Log;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.SemPersonaManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.x500.X500Principal;

public final class EdmKeyStore {
    public static final String NATIVE_KEYSTORE_PATH;
    public static final String TRUSTED_KEYSTORE_PATH;
    public static final String UNTRUSTED_KEYSTORE_PATH;
    public static final String USER_KEYSTORE_PATH;
    public static EdmKeyStore mInstanceNative;
    public static EdmKeyStore mInstanceTrusted;
    public static EdmKeyStore mInstanceUntrusted;
    public static EdmKeyStore mInstanceUser;
    public final KeyStore mKeyStore;
    public final Object mKeyStoreLock;
    public final String mPath;
    public final int mType;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getDataSystemDirectory());
        String str = File.separator;
        TRUSTED_KEYSTORE_PATH =
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, str, "enterprise_cacerts.bks");
        UNTRUSTED_KEYSTORE_PATH =
                Environment.getDataSystemDirectory() + str + "enterprise_untrustedcerts.bks";
        USER_KEYSTORE_PATH =
                Environment.getDataSystemDirectory() + str + "enterprise_usercerts.bks";
        NATIVE_KEYSTORE_PATH =
                Environment.getDataSystemDirectory() + str + "enterprise_nativecerts.bks";
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public EdmKeyStore(java.lang.String r5, int r6) {
        /*
            r4 = this;
            r4.<init>()
            java.lang.Object r0 = new java.lang.Object
            r0.<init>()
            r4.mKeyStoreLock = r0
            r4.mPath = r5
            r4.mType = r6
            monitor-enter(r0)
            r6 = 0
            java.lang.String r1 = "BKS"
            java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1)     // Catch: java.lang.Throwable -> L75
            r4.mKeyStore = r1     // Catch: java.lang.Throwable -> L75
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L26
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L26
            r1.load(r2, r6)     // Catch: java.lang.Throwable -> L21 java.io.IOException -> L27
            goto L56
        L21:
            r4 = move-exception
            goto L63
        L23:
            r4 = move-exception
            r2 = r6
            goto L63
        L26:
            r2 = r6
        L27:
            if (r2 == 0) goto L2d
            r2.close()     // Catch: java.lang.Throwable -> L21
            r2 = r6
        L2d:
            java.security.KeyStore r1 = r4.mKeyStore     // Catch: java.lang.Throwable -> L21
            r1.load(r6, r6)     // Catch: java.lang.Throwable -> L21
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L21
            r1.<init>(r5)     // Catch: java.lang.Throwable -> L21
            java.security.KeyStore r3 = r4.mKeyStore     // Catch: java.lang.Throwable -> L61
            r3.store(r1, r6)     // Catch: java.lang.Throwable -> L61
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L61
            r3.<init>(r5)     // Catch: java.lang.Throwable -> L61
            java.security.KeyStore r4 = r4.mKeyStore     // Catch: java.lang.Throwable -> L5d
            r4.load(r3, r6)     // Catch: java.lang.Throwable -> L5d
            r1.close()     // Catch: java.lang.Throwable -> L4a java.io.IOException -> L4d
            goto L55
        L4a:
            r4 = move-exception
            r6 = r3
            goto L76
        L4d:
            java.lang.String r4 = "EdmKeyStore"
            java.lang.String r5 = "fos close failed"
            android.util.Log.d(r4, r5)     // Catch: java.lang.Throwable -> L4a
        L55:
            r2 = r3
        L56:
            r2.close()     // Catch: java.lang.Throwable -> L5b
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5b
            return
        L5b:
            r4 = move-exception
            goto L7c
        L5d:
            r4 = move-exception
            r6 = r1
            r2 = r3
            goto L63
        L61:
            r4 = move-exception
            r6 = r1
        L63:
            if (r6 == 0) goto L74
            r6.close()     // Catch: java.lang.Throwable -> L69 java.io.IOException -> L6c
            goto L74
        L69:
            r4 = move-exception
            r6 = r2
            goto L76
        L6c:
            java.lang.String r5 = "EdmKeyStore"
            java.lang.String r6 = "fos close failed"
            android.util.Log.d(r5, r6)     // Catch: java.lang.Throwable -> L69
        L74:
            throw r4     // Catch: java.lang.Throwable -> L69
        L75:
            r4 = move-exception
        L76:
            if (r6 == 0) goto L7b
            r6.close()     // Catch: java.lang.Throwable -> L5b
        L7b:
            throw r4     // Catch: java.lang.Throwable -> L5b
        L7c:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L5b
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.certificate.EdmKeyStore.<init>(java.lang.String,"
                    + " int):void");
    }

    public static String addUserIdToAlias(int i, String str) {
        return String.valueOf(i) + "_" + str;
    }

    public static String generateAlias(X500Principal x500Principal) {
        int i;
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(x500Principal.getEncoded());
            int i2 =
                    ((digest[3] & 255) << 24)
                            | (digest[0] & 255)
                            | ((digest[1] & 255) << 8)
                            | ((digest[2] & 255) << 16);
            char[] cArr = new char[8];
            int i3 = 8;
            while (true) {
                i3--;
                cArr[i3] = Utils.HEX_DIGITS[i2 & 15];
                i2 >>>= 4;
                if (i2 == 0 && (i = 8 - i3) >= 8) {
                    return new String(cArr, i3, i);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public static List generateAllAliasesForUser(
            CertificateCache certificateCache, X500Principal x500Principal, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        String generateAlias = generateAlias(x500Principal);
        arrayList.add(addUserIdToAlias(i, generateAlias));
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        if (SemPersonaManager.isKnoxId(i)) {
            if (isFromContainerOwner(certificateCache, generateAlias, i2)) {
                arrayList.add(addUserIdToAlias(0, generateAlias));
            }
        } else if (i != 0) {
            arrayList.add(addUserIdToAlias(0, generateAlias));
        }
        return arrayList;
    }

    public static synchronized EdmKeyStore getInstance(int i) {
        synchronized (EdmKeyStore.class) {
            if (i == 0) {
                if (mInstanceTrusted == null) {
                    try {
                        mInstanceTrusted = new EdmKeyStore(TRUSTED_KEYSTORE_PATH, i);
                    } catch (Exception e) {
                        Log.e("EdmKeyStore", "Should not happen! ", e);
                        mInstanceTrusted = null;
                    }
                }
                return mInstanceTrusted;
            }
            if (i == 1) {
                if (mInstanceUser == null) {
                    try {
                        mInstanceUser = new EdmKeyStore(USER_KEYSTORE_PATH, i);
                    } catch (Exception e2) {
                        Log.e("EdmKeyStore", "Should not happen! ", e2);
                        mInstanceUser = null;
                    }
                }
                return mInstanceUser;
            }
            if (i == 2) {
                if (mInstanceNative == null) {
                    try {
                        mInstanceNative = new EdmKeyStore(NATIVE_KEYSTORE_PATH, i);
                    } catch (Exception e3) {
                        Log.e("EdmKeyStore", "Should not happen! ", e3);
                        mInstanceNative = null;
                    }
                }
                return mInstanceNative;
            }
            if (i != 3) {
                return null;
            }
            if (mInstanceUntrusted == null) {
                try {
                    mInstanceUntrusted = new EdmKeyStore(UNTRUSTED_KEYSTORE_PATH, i);
                } catch (Exception e4) {
                    Log.e("EdmKeyStore", "Should not happen! ", e4);
                    mInstanceUntrusted = null;
                }
            }
            return mInstanceUntrusted;
        }
    }

    public static boolean isFromContainerOwner(
            CertificateCache certificateCache, String str, int i) {
        boolean contains;
        synchronized (certificateCache) {
            if (((HashMap) certificateCache.mCache).containsKey(0)) {
                Map map = (Map) ((HashMap) certificateCache.mCache).get(0);
                contains =
                        map.containsKey(str)
                                ? ((List) map.get(str)).contains(Integer.valueOf(i))
                                : false;
            }
        }
        return contains;
    }

    public static boolean isSelfSigned(X509Certificate x509Certificate) {
        if (!x509Certificate
                .getSubjectX500Principal()
                .equals(x509Certificate.getIssuerX500Principal())) {
            return false;
        }
        try {
            x509Certificate.verify(x509Certificate.getPublicKey());
            return true;
        } catch (InvalidKeyException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | SignatureException
                | CertificateException e) {
            Log.i("EdmKeyStore", "Verifying self-signed certificate: " + e.getMessage());
            return false;
        }
    }

    public final void changeAlias(String str, String str2) {
        try {
            X509Certificate x509Certificate = (X509Certificate) this.mKeyStore.getCertificate(str);
            this.mKeyStore.deleteEntry(str);
            this.mKeyStore.setCertificateEntry(str2, x509Certificate);
        } catch (KeyStoreException e) {
            Log.d("EdmKeyStore", "Exception with keystore " + e);
        }
    }

    public final void cleanUid(int i) {
        new ArrayList();
        Iterator it = ((ArrayList) getAliases()).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                synchronized (this.mKeyStoreLock) {
                    try {
                        if (str.startsWith(i + "_")) {
                            this.mKeyStore.deleteEntry(str);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (KeyStoreException e) {
                Log.d("EdmKeyStore", "Exception with keystore " + e);
            }
        }
        saveKeyStore();
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x004f, code lost:

       r4 = ((java.util.ArrayList) generateAllAliasesForUser(r10, r11.getIssuerX500Principal(), r12, r13)).iterator();
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0061, code lost:

       if (r4.hasNext() == false) goto L73;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0063, code lost:

       r5 = (java.security.cert.X509Certificate) r8.mKeyStore.getCertificate((java.lang.String) r4.next());
    */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0071, code lost:

       if (r5 == null) goto L76;
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0073, code lost:

       r11.verify(r5.getPublicKey());
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x007a, code lost:

       r6 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x007c, code lost:

       r6 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0080, code lost:

       r4 = ((java.util.ArrayList) getAliasesForUser(r10, r12, r13)).iterator();
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x008e, code lost:

       if (r4.hasNext() == false) goto L78;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0090, code lost:

       r5 = (java.security.cert.X509Certificate) r8.mKeyStore.getCertificate((java.lang.String) r4.next());
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x009e, code lost:

       if (r5 == null) goto L82;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00a0, code lost:

       r11.verify(r5.getPublicKey());
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00a7, code lost:

       r6 = true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00a9, code lost:

       r6 = false;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean containsCertificateOrChain(
            android.content.Context r9,
            com.android.server.enterprise.certificate.CertificateCache r10,
            java.security.cert.X509Certificate r11,
            int r12,
            int r13) {
        /*
            r8 = this;
            r0 = 0
            if (r11 == 0) goto Lf1
            java.lang.String r1 = r8.mPath
            java.lang.String r2 = com.android.server.enterprise.certificate.EdmKeyStore.TRUSTED_KEYSTORE_PATH
            boolean r3 = r1.equals(r2)
            if (r3 != 0) goto L15
            java.lang.String r3 = com.android.server.enterprise.certificate.EdmKeyStore.UNTRUSTED_KEYSTORE_PATH
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto Lf1
        L15:
            r3 = 1
            javax.security.auth.x500.X500Principal r4 = r11.getSubjectX500Principal()     // Catch: java.security.KeyStoreException -> L4c
            java.util.List r4 = generateAllAliasesForUser(r10, r4, r12, r13)     // Catch: java.security.KeyStoreException -> L4c
            java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch: java.security.KeyStoreException -> L4c
            java.util.Iterator r4 = r4.iterator()     // Catch: java.security.KeyStoreException -> L4c
        L24:
            boolean r5 = r4.hasNext()     // Catch: java.security.KeyStoreException -> L4c
            if (r5 == 0) goto L4f
            java.lang.Object r5 = r4.next()     // Catch: java.security.KeyStoreException -> L4c
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.security.KeyStoreException -> L4c
            java.security.KeyStore r6 = r8.mKeyStore     // Catch: java.security.KeyStoreException -> L4c
            java.security.cert.Certificate r5 = r6.getCertificate(r5)     // Catch: java.security.KeyStoreException -> L4c
            java.security.cert.X509Certificate r5 = (java.security.cert.X509Certificate) r5     // Catch: java.security.KeyStoreException -> L4c
            if (r5 == 0) goto L24
            byte[] r6 = r5.getEncoded()     // Catch: java.security.cert.CertificateEncodingException -> L47 java.security.KeyStoreException -> L4c
            byte[] r7 = r11.getEncoded()     // Catch: java.security.cert.CertificateEncodingException -> L47 java.security.KeyStoreException -> L4c
            boolean r6 = java.util.Arrays.equals(r6, r7)     // Catch: java.security.cert.CertificateEncodingException -> L47 java.security.KeyStoreException -> L4c
            goto L48
        L47:
            r6 = r0
        L48:
            if (r6 == 0) goto L24
            goto Ld2
        L4c:
            r4 = move-exception
            goto Lbd
        L4f:
            javax.security.auth.x500.X500Principal r4 = r11.getIssuerX500Principal()     // Catch: java.security.KeyStoreException -> L4c
            java.util.List r4 = generateAllAliasesForUser(r10, r4, r12, r13)     // Catch: java.security.KeyStoreException -> L4c
            java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch: java.security.KeyStoreException -> L4c
            java.util.Iterator r4 = r4.iterator()     // Catch: java.security.KeyStoreException -> L4c
        L5d:
            boolean r5 = r4.hasNext()     // Catch: java.security.KeyStoreException -> L4c
            if (r5 == 0) goto L80
            java.lang.Object r5 = r4.next()     // Catch: java.security.KeyStoreException -> L4c
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.security.KeyStoreException -> L4c
            java.security.KeyStore r6 = r8.mKeyStore     // Catch: java.security.KeyStoreException -> L4c
            java.security.cert.Certificate r5 = r6.getCertificate(r5)     // Catch: java.security.KeyStoreException -> L4c
            java.security.cert.X509Certificate r5 = (java.security.cert.X509Certificate) r5     // Catch: java.security.KeyStoreException -> L4c
            if (r5 == 0) goto L5d
            java.security.PublicKey r6 = r5.getPublicKey()     // Catch: java.lang.Exception -> L7c
            r11.verify(r6)     // Catch: java.lang.Exception -> L7c
            r6 = r3
            goto L7d
        L7c:
            r6 = r0
        L7d:
            if (r6 == 0) goto L5d
            goto Ld2
        L80:
            java.util.List r4 = r8.getAliasesForUser(r10, r12, r13)     // Catch: java.security.KeyStoreException -> L4c
            java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch: java.security.KeyStoreException -> L4c
            java.util.Iterator r4 = r4.iterator()     // Catch: java.security.KeyStoreException -> L4c
        L8a:
            boolean r5 = r4.hasNext()     // Catch: java.security.KeyStoreException -> L4c
            if (r5 == 0) goto Ld1
            java.lang.Object r5 = r4.next()     // Catch: java.security.KeyStoreException -> L4c
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.security.KeyStoreException -> L4c
            java.security.KeyStore r6 = r8.mKeyStore     // Catch: java.security.KeyStoreException -> L4c
            java.security.cert.Certificate r5 = r6.getCertificate(r5)     // Catch: java.security.KeyStoreException -> L4c
            java.security.cert.X509Certificate r5 = (java.security.cert.X509Certificate) r5     // Catch: java.security.KeyStoreException -> L4c
            if (r5 == 0) goto L8a
            java.security.PublicKey r6 = r5.getPublicKey()     // Catch: java.lang.Exception -> La9
            r11.verify(r6)     // Catch: java.lang.Exception -> La9
            r6 = r3
            goto Laa
        La9:
            r6 = r0
        Laa:
            if (r6 != 0) goto Ld2
            byte[] r6 = r5.getEncoded()     // Catch: java.security.KeyStoreException -> L4c java.security.cert.CertificateEncodingException -> Lb9
            byte[] r7 = r11.getEncoded()     // Catch: java.security.KeyStoreException -> L4c java.security.cert.CertificateEncodingException -> Lb9
            boolean r6 = java.util.Arrays.equals(r6, r7)     // Catch: java.security.KeyStoreException -> L4c java.security.cert.CertificateEncodingException -> Lb9
            goto Lba
        Lb9:
            r6 = r0
        Lba:
            if (r6 == 0) goto L8a
            goto Ld2
        Lbd:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "get error"
            r5.<init>(r6)
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            java.lang.String r5 = "EdmKeyStore"
            android.util.Log.e(r5, r4)
        Ld1:
            r5 = 0
        Ld2:
            if (r5 == 0) goto Ld5
            return r3
        Ld5:
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto Lf1
            boolean r1 = isSelfSigned(r11)
            if (r1 != 0) goto Lf1
            java.security.cert.X509Certificate r5 = com.android.server.enterprise.certificate.CertificatePolicy.findIssuerInAndroidKeystore(r9, r11, r12)
            if (r5 == 0) goto Lf1
            r2 = r8
            r3 = r9
            r4 = r10
            r6 = r12
            r7 = r13
            boolean r8 = r2.containsCertificateOrChain(r3, r4, r5, r6, r7)
            return r8
        Lf1:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.certificate.EdmKeyStore.containsCertificateOrChain(android.content.Context,"
                    + " com.android.server.enterprise.certificate.CertificateCache,"
                    + " java.security.cert.X509Certificate, int, int):boolean");
    }

    public final void dump(PrintWriter printWriter, String str) {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "Certificate aliases {");
        try {
            Enumeration<String> aliases = this.mKeyStore.aliases();
            while (aliases.hasMoreElements()) {
                m.append(aliases.nextElement());
                if (aliases.hasMoreElements()) {
                    m.append(", ");
                }
            }
        } catch (KeyStoreException e) {
            m.append("Could not dump alias from keystore ");
            m.append(e.getMessage());
        }
        m.append("}");
        m.append(System.lineSeparator());
        m.append(System.lineSeparator());
        printWriter.write(m.toString());
    }

    public final List getAliases() {
        ArrayList arrayList = new ArrayList();
        try {
            Enumeration<String> aliases = this.mKeyStore.aliases();
            while (aliases.hasMoreElements()) {
                arrayList.add(aliases.nextElement());
            }
        } catch (KeyStoreException e) {
            Log.d("EdmKeyStore", "Exception with keystore " + e);
        }
        return arrayList;
    }

    public final List getAliasesForUser(CertificateCache certificateCache, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        boolean isKnoxId = SemPersonaManager.isKnoxId(i);
        try {
            Enumeration<String> aliases = this.mKeyStore.aliases();
            while (aliases.hasMoreElements()) {
                String nextElement = aliases.nextElement();
                if (!nextElement.startsWith(String.valueOf(0) + "_")) {
                    if (nextElement.startsWith(String.valueOf(i) + "_") && i != 0) {
                        arrayList.add(nextElement);
                    }
                } else if (!isKnoxId) {
                    arrayList.add(nextElement);
                } else if (isFromContainerOwner(certificateCache, nextElement, i2)) {
                    arrayList.add(nextElement);
                }
            }
        } catch (KeyStoreException e) {
            Log.d("EdmKeyStore", "Exception with keystore " + e);
        }
        return arrayList;
    }

    public final Map getCertificates(int i, List list) {
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                synchronized (this.mKeyStoreLock) {
                    try {
                        X509Certificate x509Certificate =
                                (X509Certificate)
                                        this.mKeyStore.getCertificate(addUserIdToAlias(i, str));
                        if (x509Certificate != null) {
                            hashMap.put(str, x509Certificate);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (KeyStoreException e) {
                Log.d("EdmKeyStore", "Exception with keystore " + e);
            }
        }
        return hashMap;
    }

    public final void installCertificates(int i, Map map) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            try {
                synchronized (this.mKeyStoreLock) {
                    try {
                        String addUserIdToAlias = addUserIdToAlias(i, (String) entry.getKey());
                        this.mKeyStore.setCertificateEntry(
                                addUserIdToAlias, (Certificate) entry.getValue());
                        if (this.mKeyStore.isCertificateEntry(addUserIdToAlias)) {
                            arrayList.add((String) entry.getKey());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (KeyStoreException e) {
                Log.d("EdmKeyStore", "Exception with keystore " + e);
            }
        }
        saveKeyStore();
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000f, code lost:

       if (r1 != 3) goto L25;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void performKeystoreUpgrade() {
        /*
            r4 = this;
            java.util.List r0 = r4.getAliases()
            int r1 = r4.mType
            if (r1 == 0) goto L3f
            r2 = 1
            if (r1 == r2) goto L3f
            r3 = 2
            if (r1 == r3) goto L12
            r2 = 3
            if (r1 == r2) goto L3f
            goto L63
        L12:
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            java.util.Iterator r0 = r0.iterator()
        L18:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L63
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            int r3 = r1.length()
            if (r3 < r2) goto L18
            r3 = 0
            char r3 = r1.charAt(r3)
            boolean r3 = java.lang.Character.isLetter(r3)
            if (r3 == 0) goto L18
            java.lang.String r3 = "1010_"
            java.lang.String r3 = r3.concat(r1)
            r4.changeAlias(r1, r3)
            goto L18
        L3f:
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            java.util.Iterator r0 = r0.iterator()
        L45:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L63
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = "_"
            boolean r2 = r1.contains(r2)
            if (r2 != 0) goto L45
            java.lang.String r2 = "0_"
            java.lang.String r2 = r2.concat(r1)
            r4.changeAlias(r1, r2)
            goto L45
        L63:
            r4.saveKeyStore()
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.certificate.EdmKeyStore.performKeystoreUpgrade():void");
    }

    public final void removeCertificates(int i, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                synchronized (this.mKeyStoreLock) {
                    try {
                        String addUserIdToAlias = addUserIdToAlias(i, str);
                        this.mKeyStore.deleteEntry(addUserIdToAlias);
                        if (!this.mKeyStore.isCertificateEntry(addUserIdToAlias)) {
                            arrayList.add(str);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (KeyStoreException e) {
                Log.d("EdmKeyStore", "Exception with keystore " + e);
            }
        }
        saveKeyStore();
    }

    public final void saveKeyStore() {
        String str;
        String str2;
        FileOutputStream fileOutputStream;
        synchronized (this.mKeyStoreLock) {
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    fileOutputStream = new FileOutputStream(this.mPath);
                    try {
                        this.mKeyStore.store(fileOutputStream, null);
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream2 = fileOutputStream;
                        Log.e("EdmKeyStore", "save error" + e);
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException unused) {
                                str = "EdmKeyStore";
                                str2 = "fos close failed";
                                Log.d(str, str2);
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException unused2) {
                                Log.d("EdmKeyStore", "fos close failed");
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
            try {
                fileOutputStream.close();
            } catch (IOException unused3) {
                str = "EdmKeyStore";
                str2 = "fos close failed";
                Log.d(str, str2);
            }
        }
    }
}
