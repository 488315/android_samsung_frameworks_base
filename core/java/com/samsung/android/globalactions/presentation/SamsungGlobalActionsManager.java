package com.samsung.android.globalactions.presentation;

/* loaded from: classes5.dex */
public interface SamsungGlobalActionsManager {
  boolean isFOTAAvailableForGlobalActions();

  void onGlobalActionsHidden();

  void onGlobalActionsShown();

  void reboot(boolean z);

  void shutdown();
}
