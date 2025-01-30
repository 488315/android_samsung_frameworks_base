package com.android.server;

import android.annotation.SystemApi;
import android.util.ArrayMap;
import java.util.Map;
import java.util.Objects;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes.dex */
public final class LocalManagerRegistry {
  public static final Map sManagers = new ArrayMap();

  public static Object getManager(Class cls) {
    Object obj;
    Map map = sManagers;
    synchronized (map) {
      obj = map.get(cls);
    }
    return obj;
  }

  public static Object getManagerOrThrow(Class cls) {
    Object manager = getManager(cls);
    if (manager != null) {
      return manager;
    }
    throw new ManagerNotFoundException(cls);
  }

  public static void addManager(Class cls, Object obj) {
    Objects.requireNonNull(cls, "managerClass");
    Objects.requireNonNull(obj, "manager");
    Map map = sManagers;
    synchronized (map) {
      if (map.containsKey(cls)) {
        throw new IllegalStateException(cls.getName() + " is already registered");
      }
      map.put(cls, obj);
    }
  }

  public class ManagerNotFoundException extends Exception {
    public ManagerNotFoundException(Class cls) {
      super("Local manager " + cls.getName() + " does not exist or is not ready");
    }
  }
}
