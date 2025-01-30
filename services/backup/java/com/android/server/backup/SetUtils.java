package com.android.server.backup;

import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class SetUtils {
  public static Set union(Set set, Set set2) {
    HashSet hashSet = new HashSet(set);
    hashSet.addAll(set2);
    return hashSet;
  }
}
