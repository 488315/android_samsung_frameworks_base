package com.android.server.pm;

import android.content.IIntentReceiver;
import android.os.Bundle;
import android.util.SparseArray;

/* loaded from: classes3.dex */
public interface PackageSender {
  void notifyPackageRemoved(String str, int i);

  void sendPackageBroadcast(
      String str,
      String str2,
      Bundle bundle,
      int i,
      String str3,
      IIntentReceiver iIntentReceiver,
      int[] iArr,
      int[] iArr2,
      SparseArray sparseArray,
      Bundle bundle2);
}
