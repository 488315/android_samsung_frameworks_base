package com.android.server.chimera;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public class WakeLockManager {
  public final SystemRepository mSystemRepository;
  public final Set mWakeLockPackages = new HashSet();

  public WakeLockManager(SystemRepository systemRepository) {
    this.mSystemRepository = systemRepository;
  }

  public void update() {
    this.mWakeLockPackages.clear();
    Collections.addAll(this.mWakeLockPackages, this.mSystemRepository.getWakeLockPackageList());
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    Iterator it = this.mWakeLockPackages.iterator();
    while (it.hasNext()) {
      sb.append((String) it.next());
      sb.append(" ");
    }
    return sb.toString();
  }

  public boolean contains(String str) {
    return this.mWakeLockPackages.contains(str);
  }
}
