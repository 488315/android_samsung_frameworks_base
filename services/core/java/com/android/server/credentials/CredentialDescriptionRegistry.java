package com.android.server.credentials;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CredentialDescriptionRegistry {
    public static final SparseArray sCredentialDescriptionSessionPerUser = new SparseArray();
    public static final ReentrantLock sLock = new ReentrantLock();
    public Map mCredentialDescriptions;
    public int mTotalDescriptionCount;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FilterResult {
        public final List mCredentialEntries;
        public final Set mElementKeys;
        public final String mPackageName;

        public FilterResult(String str, Set set, List list) {
            this.mPackageName = str;
            this.mElementKeys = set;
            this.mCredentialEntries = list;
        }
    }

    public static void clearAllSessions() {
        ReentrantLock reentrantLock = sLock;
        reentrantLock.lock();
        try {
            sCredentialDescriptionSessionPerUser.clear();
            reentrantLock.unlock();
        } catch (Throwable th) {
            sLock.unlock();
            throw th;
        }
    }

    public static CredentialDescriptionRegistry forUser(int i) {
        ReentrantLock reentrantLock = sLock;
        reentrantLock.lock();
        try {
            SparseArray sparseArray = sCredentialDescriptionSessionPerUser;
            CredentialDescriptionRegistry credentialDescriptionRegistry =
                    (CredentialDescriptionRegistry) sparseArray.get(i, null);
            if (credentialDescriptionRegistry == null) {
                credentialDescriptionRegistry = new CredentialDescriptionRegistry();
                credentialDescriptionRegistry.mCredentialDescriptions = new HashMap();
                credentialDescriptionRegistry.mTotalDescriptionCount = 0;
                sparseArray.put(i, credentialDescriptionRegistry);
            }
            reentrantLock.unlock();
            return credentialDescriptionRegistry;
        } catch (Throwable th) {
            sLock.unlock();
            throw th;
        }
    }

    public static void setSession(
            int i, CredentialDescriptionRegistry credentialDescriptionRegistry) {
        ReentrantLock reentrantLock = sLock;
        reentrantLock.lock();
        try {
            sCredentialDescriptionSessionPerUser.put(i, credentialDescriptionRegistry);
            reentrantLock.unlock();
        } catch (Throwable th) {
            sLock.unlock();
            throw th;
        }
    }
}
