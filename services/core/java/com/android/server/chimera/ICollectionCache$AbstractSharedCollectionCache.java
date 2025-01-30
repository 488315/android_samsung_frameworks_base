package com.android.server.chimera;

import android.content.SharedPreferences;
import android.util.ArraySet;
import java.util.Arrays;
import java.util.Set;

/* compiled from: ChimeraDataCache.java */
/* loaded from: classes.dex */
public abstract class ICollectionCache$AbstractSharedCollectionCache {
  public final Set apps = new ArraySet();
  public SharedPreferences mSharedPreferences;
  public final SystemRepository mSystemRepository;

  public abstract String getKey();

  public ICollectionCache$AbstractSharedCollectionCache(SystemRepository systemRepository) {
    this.mSystemRepository = systemRepository;
    init();
  }

  public String toString() {
    return this.apps.toString();
  }

  public boolean contains(String str) {
    init();
    return this.apps.contains(str);
  }

  public boolean update(String str) {
    if (this.apps.contains(str)) {
      return true;
    }
    this.apps.add(str);
    if (!init()) {
      return false;
    }
    String key = getKey();
    if (!this.mSharedPreferences.getString(key, "").isEmpty()) {
      str = String.join(",", this.apps);
    }
    SharedPreferences.Editor edit = this.mSharedPreferences.edit();
    edit.putString(key, str);
    edit.commit();
    return true;
  }

  public boolean init() {
    if (this.mSharedPreferences != null) {
      return true;
    }
    SharedPreferences sharedPreferences = this.mSystemRepository.getSharedPreferences();
    this.mSharedPreferences = sharedPreferences;
    if (sharedPreferences == null) {
      return false;
    }
    String string = sharedPreferences.getString(getKey(), "");
    if (!string.isEmpty()) {
      this.apps.addAll(Arrays.asList(string.split(",")));
    }
    return true;
  }
}
