package com.samsung.android.cocktailbar;

import com.android.internal.view.AppearanceRegion;

/* loaded from: classes5.dex */
public abstract class CocktailBarManagerInternal {
  public abstract void onSystemBarAppearanceChanged(
      int i, int i2, AppearanceRegion[] appearanceRegionArr);

  public abstract void topAppWindowChanged(int i, boolean z, boolean z2);

  public abstract void turnOffWakupCocktailBarFromPowerManager(int i, String str);

  public abstract void updateSysfsGripDisableFromWindowManager(boolean z);

  public abstract void wakupCocktailBarFromWindowManager(boolean z, int i, int i2);
}
