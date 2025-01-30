package com.android.server.companion;

import android.companion.AssociationInfo;
import android.net.MacAddress;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.CollectionUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class AssociationStoreImpl implements AssociationStore {
  public final Object mLock = new Object();
  public final Map mIdMap = new HashMap();
  public final Map mAddressMap = new HashMap();
  public final SparseArray mCachedPerUser = new SparseArray();
  public final Set mListeners = new LinkedHashSet();

  public void addAssociation(AssociationInfo associationInfo) {
    checkNotRevoked(associationInfo);
    int id = associationInfo.getId();
    Log.i("CDM_AssociationStore", "addAssociation() " + associationInfo.toShortString());
    Log.d("CDM_AssociationStore", "  association=" + associationInfo);
    synchronized (this.mLock) {
      if (this.mIdMap.containsKey(Integer.valueOf(id))) {
        Slog.e("CDM_AssociationStore", "Association with id " + id + " already exists.");
        return;
      }
      this.mIdMap.put(Integer.valueOf(id), associationInfo);
      MacAddress deviceMacAddress = associationInfo.getDeviceMacAddress();
      if (deviceMacAddress != null) {
        ((Set)
                this.mAddressMap.computeIfAbsent(
                    deviceMacAddress,
                    new Function() { // from class:
                      // com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda4
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Set lambda$addAssociation$0;
                        lambda$addAssociation$0 =
                            AssociationStoreImpl.lambda$addAssociation$0((MacAddress) obj);
                        return lambda$addAssociation$0;
                      }
                    }))
            .add(Integer.valueOf(id));
      }
      invalidateCacheForUserLocked(associationInfo.getUserId());
      broadcastChange(0, associationInfo);
    }
  }

  public static /* synthetic */ Set lambda$addAssociation$0(MacAddress macAddress) {
    return new HashSet();
  }

  public void updateAssociation(AssociationInfo associationInfo) {
    checkNotRevoked(associationInfo);
    int id = associationInfo.getId();
    Log.i("CDM_AssociationStore", "updateAssociation() " + associationInfo.toShortString());
    Log.d("CDM_AssociationStore", "  updated=" + associationInfo);
    synchronized (this.mLock) {
      AssociationInfo associationInfo2 = (AssociationInfo) this.mIdMap.get(Integer.valueOf(id));
      if (associationInfo2 == null) {
        Log.w("CDM_AssociationStore", "Association with id " + id + " does not exist.");
        return;
      }
      if (associationInfo2.equals(associationInfo)) {
        return;
      }
      this.mIdMap.put(Integer.valueOf(id), associationInfo);
      invalidateCacheForUserLocked(associationInfo2.getUserId());
      MacAddress deviceMacAddress = associationInfo.getDeviceMacAddress();
      MacAddress deviceMacAddress2 = associationInfo2.getDeviceMacAddress();
      boolean z = !Objects.equals(deviceMacAddress2, deviceMacAddress);
      if (z) {
        if (deviceMacAddress2 != null) {
          ((Set) this.mAddressMap.get(deviceMacAddress2)).remove(Integer.valueOf(id));
        }
        if (deviceMacAddress != null) {
          ((Set)
                  this.mAddressMap.computeIfAbsent(
                      deviceMacAddress,
                      new Function() { // from class:
                        // com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          Set lambda$updateAssociation$1;
                          lambda$updateAssociation$1 =
                              AssociationStoreImpl.lambda$updateAssociation$1((MacAddress) obj);
                          return lambda$updateAssociation$1;
                        }
                      }))
              .add(Integer.valueOf(id));
        }
      }
      broadcastChange(z ? 2 : 3, associationInfo);
    }
  }

  public static /* synthetic */ Set lambda$updateAssociation$1(MacAddress macAddress) {
    return new HashSet();
  }

  public void removeAssociation(int i) {
    Log.i("CDM_AssociationStore", "removeAssociation() id=" + i);
    synchronized (this.mLock) {
      AssociationInfo associationInfo = (AssociationInfo) this.mIdMap.remove(Integer.valueOf(i));
      if (associationInfo == null) {
        Log.w("CDM_AssociationStore", "Association with id " + i + " is not stored.");
        return;
      }
      Log.i("CDM_AssociationStore", "removed " + associationInfo.toShortString());
      Log.d("CDM_AssociationStore", "  association=" + associationInfo);
      MacAddress deviceMacAddress = associationInfo.getDeviceMacAddress();
      if (deviceMacAddress != null) {
        ((Set) this.mAddressMap.get(deviceMacAddress)).remove(Integer.valueOf(i));
      }
      invalidateCacheForUserLocked(associationInfo.getUserId());
      broadcastChange(1, associationInfo);
    }
  }

  @Override // com.android.server.companion.AssociationStore
  public Collection getAssociations() {
    List copyOf;
    synchronized (this.mLock) {
      copyOf = List.copyOf(this.mIdMap.values());
    }
    return copyOf;
  }

  public List getAssociationsForUser(int i) {
    List associationsForUserLocked;
    synchronized (this.mLock) {
      associationsForUserLocked = getAssociationsForUserLocked(i);
    }
    return associationsForUserLocked;
  }

  @Override // com.android.server.companion.AssociationStore
  public List getAssociationsForPackage(int i, final String str) {
    return Collections.unmodifiableList(
        CollectionUtils.filter(
            getAssociationsForUser(i),
            new Predicate() { // from class:
              // com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$getAssociationsForPackage$2;
                lambda$getAssociationsForPackage$2 =
                    AssociationStoreImpl.lambda$getAssociationsForPackage$2(
                        str, (AssociationInfo) obj);
                return lambda$getAssociationsForPackage$2;
              }
            }));
  }

  public static /* synthetic */ boolean lambda$getAssociationsForPackage$2(
      String str, AssociationInfo associationInfo) {
    return associationInfo.getPackageName().equals(str);
  }

  public AssociationInfo getAssociationsForPackageWithAddress(
      final int i, final String str, String str2) {
    return (AssociationInfo)
        CollectionUtils.find(
            getAssociationsByAddress(str2),
            new Predicate() { // from class:
              // com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda2
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$getAssociationsForPackageWithAddress$3;
                lambda$getAssociationsForPackageWithAddress$3 =
                    AssociationStoreImpl.lambda$getAssociationsForPackageWithAddress$3(
                        i, str, (AssociationInfo) obj);
                return lambda$getAssociationsForPackageWithAddress$3;
              }
            });
  }

  public static /* synthetic */ boolean lambda$getAssociationsForPackageWithAddress$3(
      int i, String str, AssociationInfo associationInfo) {
    return associationInfo.belongsToPackage(i, str);
  }

  @Override // com.android.server.companion.AssociationStore
  public AssociationInfo getAssociationById(int i) {
    AssociationInfo associationInfo;
    synchronized (this.mLock) {
      associationInfo = (AssociationInfo) this.mIdMap.get(Integer.valueOf(i));
    }
    return associationInfo;
  }

  @Override // com.android.server.companion.AssociationStore
  public List getAssociationsByAddress(String str) {
    MacAddress fromString = MacAddress.fromString(str);
    synchronized (this.mLock) {
      Set set = (Set) this.mAddressMap.get(fromString);
      if (set == null) {
        return Collections.emptyList();
      }
      ArrayList arrayList = new ArrayList(set.size());
      Iterator it = set.iterator();
      while (it.hasNext()) {
        arrayList.add((AssociationInfo) this.mIdMap.get((Integer) it.next()));
      }
      return Collections.unmodifiableList(arrayList);
    }
  }

  public final List getAssociationsForUserLocked(int i) {
    List list = (List) this.mCachedPerUser.get(i);
    if (list != null) {
      return list;
    }
    ArrayList arrayList = new ArrayList();
    for (AssociationInfo associationInfo : this.mIdMap.values()) {
      if (associationInfo.getUserId() == i) {
        arrayList.add(associationInfo);
      }
    }
    List unmodifiableList = Collections.unmodifiableList(arrayList);
    this.mCachedPerUser.set(i, unmodifiableList);
    return unmodifiableList;
  }

  public final void invalidateCacheForUserLocked(int i) {
    this.mCachedPerUser.delete(i);
  }

  @Override // com.android.server.companion.AssociationStore
  public void registerListener(AssociationStore.OnChangeListener onChangeListener) {
    synchronized (this.mListeners) {
      this.mListeners.add(onChangeListener);
    }
  }

  public void dump(PrintWriter printWriter) {
    printWriter.append("Companion Device Associations: ");
    if (getAssociations().isEmpty()) {
      printWriter.append("<empty>\n");
      return;
    }
    printWriter.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    Iterator it = getAssociations().iterator();
    while (it.hasNext()) {
      printWriter
          .append("  ")
          .append((CharSequence) ((AssociationInfo) it.next()).toString())
          .append('\n');
    }
  }

  public final void broadcastChange(int i, AssociationInfo associationInfo) {
    synchronized (this.mListeners) {
      Iterator it = this.mListeners.iterator();
      while (it.hasNext()) {
        ((AssociationStore.OnChangeListener) it.next()).onAssociationChanged(i, associationInfo);
      }
    }
  }

  public void setAssociations(Collection collection) {
    collection.forEach(
        new Consumer() { // from class:
          // com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            AssociationStoreImpl.checkNotRevoked((AssociationInfo) obj);
          }
        });
    synchronized (this.mLock) {
      setAssociationsLocked(collection);
    }
  }

  public final void setAssociationsLocked(Collection collection) {
    clearLocked();
    Iterator it = collection.iterator();
    while (it.hasNext()) {
      AssociationInfo associationInfo = (AssociationInfo) it.next();
      int id = associationInfo.getId();
      this.mIdMap.put(Integer.valueOf(id), associationInfo);
      MacAddress deviceMacAddress = associationInfo.getDeviceMacAddress();
      if (deviceMacAddress != null) {
        ((Set)
                this.mAddressMap.computeIfAbsent(
                    deviceMacAddress,
                    new Function() { // from class:
                      // com.android.server.companion.AssociationStoreImpl$$ExternalSyntheticLambda5
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        Set lambda$setAssociationsLocked$5;
                        lambda$setAssociationsLocked$5 =
                            AssociationStoreImpl.lambda$setAssociationsLocked$5((MacAddress) obj);
                        return lambda$setAssociationsLocked$5;
                      }
                    }))
            .add(Integer.valueOf(id));
      }
    }
  }

  public static /* synthetic */ Set lambda$setAssociationsLocked$5(MacAddress macAddress) {
    return new HashSet();
  }

  public final void clearLocked() {
    this.mIdMap.clear();
    this.mAddressMap.clear();
    this.mCachedPerUser.clear();
  }

  public static void checkNotRevoked(AssociationInfo associationInfo) {
    if (associationInfo.isRevoked()) {
      throw new IllegalArgumentException(
          "Revoked (removed) associations MUST NOT appear in the AssociationStore");
    }
  }
}
