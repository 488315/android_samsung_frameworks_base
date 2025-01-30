package com.android.server.pm.pkg.component;

import android.util.ArrayMap;
import java.util.Set;

/* loaded from: classes3.dex */
public interface ParsedProcess {
  ArrayMap getAppClassNamesByPackage();

  Set getDeniedPermissions();

  int getGwpAsanMode();

  int getMemtagMode();

  String getName();

  int getNativeHeapZeroInitialized();
}
