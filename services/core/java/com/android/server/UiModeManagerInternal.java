package com.android.server;

/* loaded from: classes.dex */
public abstract class UiModeManagerInternal {
  public abstract boolean applyPackageNightModeIfNeeded(String str, int i);

  public abstract boolean isNightMode();

  public abstract boolean needToShowNightModeDialog(String str, int i);

  public abstract void onEarlySwitchUser(int i);

  public abstract void setNewDexMode(boolean z);

  public abstract void setNightModeDialogShown(String str, int i);

  public abstract boolean shouldIgnoreDialog();

  public abstract boolean toggleNewDexMode();
}
