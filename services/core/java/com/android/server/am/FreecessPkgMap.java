package com.android.server.am;

import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.SparseArray;

/* loaded from: classes.dex */
public class FreecessPkgMap {
  public final SparseArray mUidMap = new SparseArray();
  public final ArrayMap mUserIdMap = new ArrayMap();

  public Object getByUid(int i) {
    return this.mUidMap.get(i);
  }

  public Object getByUserId(int i, String str) {
    SparseArray sparseArray = (SparseArray) this.mUserIdMap.get(str);
    if (sparseArray == null) {
      return null;
    }
    return sparseArray.get(i);
  }

  public Object put(int i, String str, Object obj) {
    this.mUidMap.put(i, obj);
    int userId = UserHandle.getUserId(i);
    SparseArray sparseArray = (SparseArray) this.mUserIdMap.get(str);
    if (sparseArray == null) {
      sparseArray = new SparseArray(2);
      this.mUserIdMap.put(str, sparseArray);
    }
    sparseArray.put(userId, obj);
    return obj;
  }

  public Object remove(int i, String str) {
    this.mUidMap.remove(i);
    int userId = UserHandle.getUserId(i);
    SparseArray sparseArray = (SparseArray) this.mUserIdMap.get(str);
    if (sparseArray == null) {
      return null;
    }
    Object removeReturnOld = sparseArray.removeReturnOld(userId);
    if (sparseArray.size() == 0) {
      this.mUserIdMap.remove(str);
    }
    return removeReturnOld;
  }

  public SparseArray getUidMap() {
    return this.mUidMap;
  }

  public ArrayMap getUserIdMap() {
    return this.mUserIdMap;
  }

  public int sizeByUserId() {
    return this.mUserIdMap.size();
  }

  public int size() {
    return this.mUidMap.size();
  }

  public int totalSize() {
    int i = 0;
    for (int i2 = 0; i2 < this.mUserIdMap.size(); i2++) {
      for (int i3 = 0; i3 < ((SparseArray) this.mUserIdMap.valueAt(i2)).size(); i3++) {
        i++;
      }
    }
    return i;
  }
}
