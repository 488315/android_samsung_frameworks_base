package com.android.server.cocktailbar.mode;

import android.content.Intent;

/* loaded from: classes.dex */
public class NormalMode implements CocktailBarMode {
  @Override // com.android.server.cocktailbar.mode.CocktailBarMode
  public int getCocktailType() {
    return 0;
  }

  @Override // com.android.server.cocktailbar.mode.CocktailBarMode
  public int getModeId() {
    return 1;
  }

  @Override // com.android.server.cocktailbar.mode.CocktailBarMode
  public String getModeName() {
    return null;
  }

  @Override // com.android.server.cocktailbar.mode.CocktailBarMode
  public int getRegistrationType() {
    return 0;
  }

  @Override // com.android.server.cocktailbar.mode.CocktailBarMode
  public int onBroadcastReceived(Intent intent) {
    return 0;
  }

  @Override // com.android.server.cocktailbar.mode.CocktailBarMode
  public int renewMode(int i) {
    return getModeId();
  }
}
