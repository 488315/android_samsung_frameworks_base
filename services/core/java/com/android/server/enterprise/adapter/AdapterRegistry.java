package com.android.server.enterprise.adapter;

import java.util.HashMap;

/* loaded from: classes2.dex */
public abstract class AdapterRegistry {
  public static HashMap mAdapterHandles = new HashMap();

  public static Object getAdapter(Class cls) {
    return mAdapterHandles.get(cls);
  }

  public static void registerAdapter(Class cls, IAdapterHandle iAdapterHandle) {
    mAdapterHandles.put(cls, iAdapterHandle);
  }
}
