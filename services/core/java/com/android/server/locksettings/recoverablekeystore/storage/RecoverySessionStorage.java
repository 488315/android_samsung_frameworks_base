package com.android.server.locksettings.recoverablekeystore.storage;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;
import javax.security.auth.Destroyable;

/* loaded from: classes2.dex */
public class RecoverySessionStorage implements Destroyable {
  public final SparseArray mSessionsByUid = new SparseArray();

  public Entry get(int i, String str) {
    ArrayList arrayList = (ArrayList) this.mSessionsByUid.get(i);
    if (arrayList == null) {
      return null;
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      Entry entry = (Entry) it.next();
      if (str.equals(entry.mSessionId)) {
        return entry;
      }
    }
    return null;
  }

  public void add(int i, Entry entry) {
    if (this.mSessionsByUid.get(i) == null) {
      this.mSessionsByUid.put(i, new ArrayList());
    }
    ((ArrayList) this.mSessionsByUid.get(i)).add(entry);
  }

  public void remove(int i, final String str) {
    if (this.mSessionsByUid.get(i) == null) {
      return;
    }
    ((ArrayList) this.mSessionsByUid.get(i))
        .removeIf(
            new Predicate() { // from class:
                              // com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$remove$0;
                lambda$remove$0 =
                    RecoverySessionStorage.lambda$remove$0(str, (RecoverySessionStorage.Entry) obj);
                return lambda$remove$0;
              }
            });
  }

  public static /* synthetic */ boolean lambda$remove$0(String str, Entry entry) {
    return entry.mSessionId.equals(str);
  }

  public void remove(int i) {
    ArrayList arrayList = (ArrayList) this.mSessionsByUid.get(i);
    if (arrayList == null) {
      return;
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      ((Entry) it.next()).destroy();
    }
    this.mSessionsByUid.remove(i);
  }

  @Override // javax.security.auth.Destroyable
  public void destroy() {
    int size = this.mSessionsByUid.size();
    for (int i = 0; i < size; i++) {
      Iterator it = ((ArrayList) this.mSessionsByUid.valueAt(i)).iterator();
      while (it.hasNext()) {
        ((Entry) it.next()).destroy();
      }
    }
  }

  public class Entry implements Destroyable {
    public final byte[] mKeyClaimant;
    public final byte[] mLskfHash;
    public final String mSessionId;
    public final byte[] mVaultParams;

    public Entry(String str, byte[] bArr, byte[] bArr2, byte[] bArr3) {
      this.mLskfHash = bArr;
      this.mSessionId = str;
      this.mKeyClaimant = bArr2;
      this.mVaultParams = bArr3;
    }

    public byte[] getLskfHash() {
      return this.mLskfHash;
    }

    public byte[] getKeyClaimant() {
      return this.mKeyClaimant;
    }

    public byte[] getVaultParams() {
      return this.mVaultParams;
    }

    @Override // javax.security.auth.Destroyable
    public void destroy() {
      Arrays.fill(this.mLskfHash, (byte) 0);
      Arrays.fill(this.mKeyClaimant, (byte) 0);
    }
  }
}
