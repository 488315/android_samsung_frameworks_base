package com.android.server.credentials;

import android.credentials.CredentialDescription;
import android.credentials.RegisterCredentialDescriptionRequest;
import android.credentials.UnregisterCredentialDescriptionRequest;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes.dex */
public class CredentialDescriptionRegistry {
  public static final SparseArray sCredentialDescriptionSessionPerUser = new SparseArray();
  public static final ReentrantLock sLock = new ReentrantLock();
  public Map mCredentialDescriptions = new HashMap();
  public int mTotalDescriptionCount = 0;

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

  public static CredentialDescriptionRegistry forUser(int i) {
    ReentrantLock reentrantLock = sLock;
    reentrantLock.lock();
    try {
      SparseArray sparseArray = sCredentialDescriptionSessionPerUser;
      CredentialDescriptionRegistry credentialDescriptionRegistry =
          (CredentialDescriptionRegistry) sparseArray.get(i, null);
      if (credentialDescriptionRegistry == null) {
        credentialDescriptionRegistry = new CredentialDescriptionRegistry();
        sparseArray.put(i, credentialDescriptionRegistry);
      }
      reentrantLock.unlock();
      return credentialDescriptionRegistry;
    } catch (Throwable th) {
      sLock.unlock();
      throw th;
    }
  }

  public static void clearUserSession(int i) {
    ReentrantLock reentrantLock = sLock;
    reentrantLock.lock();
    try {
      sCredentialDescriptionSessionPerUser.remove(i);
      reentrantLock.unlock();
    } catch (Throwable th) {
      sLock.unlock();
      throw th;
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

  public void executeRegisterRequest(
      RegisterCredentialDescriptionRequest registerCredentialDescriptionRequest, String str) {
    if (!this.mCredentialDescriptions.containsKey(str)) {
      this.mCredentialDescriptions.put(str, new HashSet());
    }
    if (this.mTotalDescriptionCount > 128
        || ((Set) this.mCredentialDescriptions.get(str)).size() > 16) {
      return;
    }
    Set<CredentialDescription> credentialDescriptions =
        registerCredentialDescriptionRequest.getCredentialDescriptions();
    int size = ((Set) this.mCredentialDescriptions.get(str)).size();
    ((Set) this.mCredentialDescriptions.get(str)).addAll(credentialDescriptions);
    this.mTotalDescriptionCount += ((Set) this.mCredentialDescriptions.get(str)).size() - size;
  }

  public void executeUnregisterRequest(
      UnregisterCredentialDescriptionRequest unregisterCredentialDescriptionRequest, String str) {
    if (this.mCredentialDescriptions.containsKey(str)) {
      int size = ((Set) this.mCredentialDescriptions.get(str)).size();
      ((Set) this.mCredentialDescriptions.get(str))
          .removeAll(unregisterCredentialDescriptionRequest.getCredentialDescriptions());
      this.mTotalDescriptionCount -= size - ((Set) this.mCredentialDescriptions.get(str)).size();
    }
  }

  public Set getFilteredResultForProvider(String str, Set set) {
    HashSet hashSet = new HashSet();
    if (!this.mCredentialDescriptions.containsKey(str)) {
      return hashSet;
    }
    for (CredentialDescription credentialDescription :
        (Set) this.mCredentialDescriptions.get(str)) {
      if (checkForMatch(credentialDescription.getSupportedElementKeys(), set)) {
        hashSet.add(
            new FilterResult(
                str,
                credentialDescription.getSupportedElementKeys(),
                credentialDescription.getCredentialEntries()));
      }
    }
    return hashSet;
  }

  public Set getMatchingProviders(Set set) {
    HashSet hashSet = new HashSet();
    for (String str : this.mCredentialDescriptions.keySet()) {
      for (CredentialDescription credentialDescription :
          (Set) this.mCredentialDescriptions.get(str)) {
        if (canProviderSatisfyAny(credentialDescription.getSupportedElementKeys(), set)) {
          hashSet.add(
              new FilterResult(
                  str,
                  credentialDescription.getSupportedElementKeys(),
                  credentialDescription.getCredentialEntries()));
        }
      }
    }
    return hashSet;
  }

  public void evictProviderWithPackageName(String str) {
    if (this.mCredentialDescriptions.containsKey(str)) {
      this.mCredentialDescriptions.remove(str);
    }
  }

  public static boolean canProviderSatisfyAny(Set set, Set set2) {
    Iterator it = set2.iterator();
    while (it.hasNext()) {
      if (set.containsAll((Set) it.next())) {
        return true;
      }
    }
    return false;
  }

  public static boolean checkForMatch(Set set, Set set2) {
    return set.containsAll(set2);
  }
}
