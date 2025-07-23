package com.android.internal.org.bouncycastle.jce.provider;

import com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
abstract class PKIXCRLUtil {
    PKIXCRLUtil() {
    }

    static Set findCRLs(PKIXCRLStoreSelector pKIXCRLStoreSelector, Date date, List list, List list2) throws AnnotatedException {
        HashSet hashSet = new HashSet();
        try {
            findCRLs(hashSet, pKIXCRLStoreSelector, list2);
            findCRLs(hashSet, pKIXCRLStoreSelector, list);
            HashSet hashSet2 = new HashSet();
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                X509CRL x509crl = (X509CRL) it.next();
                Date nextUpdate = x509crl.getNextUpdate();
                if (nextUpdate == null || nextUpdate.after(date)) {
                    X509Certificate certificateChecking = pKIXCRLStoreSelector.getCertificateChecking();
                    if (certificateChecking == null || x509crl.getThisUpdate().before(certificateChecking.getNotAfter())) {
                        hashSet2.add(x509crl);
                    }
                }
            }
            return hashSet2;
        } catch (AnnotatedException e) {
            throw new AnnotatedException("Exception obtaining complete CRLs.", e);
        }
    }

    private static void findCRLs(HashSet hashSet, PKIXCRLStoreSelector pKIXCRLStoreSelector, List list) throws AnnotatedException {
        Iterator it = list.iterator();
        AnnotatedException annotatedException = null;
        boolean z = false;
        while (it.hasNext()) {
            try {
                hashSet.addAll(PKIXCRLStoreSelector.getCRLs(pKIXCRLStoreSelector, (CertStore) it.next()));
                z = true;
            } catch (CertStoreException e) {
                annotatedException = new AnnotatedException("Exception searching in X.509 CRL store.", e);
            }
        }
        if (!z && annotatedException != null) {
            throw annotatedException;
        }
    }
}
