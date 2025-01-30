package com.android.server.policy;

/* loaded from: classes3.dex */
public interface GlobalActionsProvider {

  public interface GlobalActionsListener {
    void onGlobalActionsAvailableChanged(boolean z);

    void onGlobalActionsDismissed();

    void onGlobalActionsShown();
  }

  boolean isGlobalActionsDisabled();

  void setGlobalActionsListener(GlobalActionsListener globalActionsListener);

  void showGlobalActions(int i);
}
