package com.android.server.remoteappmode;

import android.content.Intent;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class InterceptedActivityRepo {
  public final Object mLock = new Object();
  public LinkedHashMap mInterceptedActivityInfoMap = new LinkedHashMap() { // from class:
        // com.android.server.remoteappmode.InterceptedActivityRepo.1
        @Override // java.util.LinkedHashMap
        public boolean removeEldestEntry(Map.Entry entry) {
          return size() > 10;
        }
      };

  public void put(Intent intent, InterceptedActivityInfo interceptedActivityInfo) {
    int hashCode = intent.toString().hashCode();
    Log.m81d(
        "[RAMS] InterceptedActivityRepo",
        "mInterceptedActivityInfoMap.put - hashcode : "
            + hashCode
            + ", intent : "
            + intent.toString());
    synchronized (this.mLock) {
      this.mInterceptedActivityInfoMap.put(Integer.valueOf(hashCode), interceptedActivityInfo);
      Log.m81d(
          "[RAMS] InterceptedActivityRepo",
          "mInterceptedActivityInfoMap size = " + this.mInterceptedActivityInfoMap.size());
    }
  }

  public InterceptedActivityInfo get(Intent intent) {
    InterceptedActivityInfo interceptedActivityInfo;
    int hashCode = intent.toString().hashCode();
    synchronized (this.mLock) {
      interceptedActivityInfo =
          this.mInterceptedActivityInfoMap.containsKey(Integer.valueOf(hashCode))
              ? (InterceptedActivityInfo)
                  this.mInterceptedActivityInfoMap.get(Integer.valueOf(hashCode))
              : null;
    }
    return interceptedActivityInfo;
  }

  public void remove(Intent intent) {
    int hashCode = intent.toString().hashCode();
    Log.m81d(
        "[RAMS] InterceptedActivityRepo",
        "mInterceptedActivityInfoMap.remove - hashcode : "
            + hashCode
            + ", intent : "
            + intent.toString());
    synchronized (this.mLock) {
      this.mInterceptedActivityInfoMap.remove(Integer.valueOf(hashCode));
      Log.m81d(
          "[RAMS] InterceptedActivityRepo",
          "mInterceptedActivityInfoMap size = " + this.mInterceptedActivityInfoMap.size());
    }
  }

  public void clear() {
    synchronized (this.mLock) {
      this.mInterceptedActivityInfoMap.clear();
    }
  }
}
