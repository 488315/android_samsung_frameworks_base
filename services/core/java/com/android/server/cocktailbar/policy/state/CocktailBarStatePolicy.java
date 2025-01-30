package com.android.server.cocktailbar.policy.state;

import android.os.IBinder;
import com.samsung.android.cocktailbar.CocktailBarStateInfo;

/* loaded from: classes.dex */
public interface CocktailBarStatePolicy {

  public interface OnCocktailBarStateListener {
    boolean notifyCocktailBarState(CocktailBarStateInfo cocktailBarStateInfo);

    boolean notifyCocktailBarStateExceptCallingPackage(
        CocktailBarStateInfo cocktailBarStateInfo, String str);

    boolean notifyCocktailBarStateToBinder(
        IBinder iBinder, CocktailBarStateInfo cocktailBarStateInfo);
  }

  String dump();

  CocktailBarStateInfo getCocktailBarStateInfo();

  int getWindowType();

  void initialize();

  void notifyStateToBinder(IBinder iBinder);

  void updateActivate(boolean z);

  void updateCocktailBarWindowType(int i, String str);

  void updatePosition(int i);

  void updateVisibility(int i);
}
