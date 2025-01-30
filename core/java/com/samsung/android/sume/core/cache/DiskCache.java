package com.samsung.android.sume.core.cache;

import java.io.File;
import java.util.function.Function;

/* loaded from: classes4.dex */
public interface DiskCache {
  void clear();

  void close();

  File get(String str);

  void put(String str, Function<File, Boolean> function);
}
