package com.android.server.cocktailbar;

/* loaded from: classes.dex */
public interface CocktailBarManagerServiceListener {
  void onResetMode(int i, int i2, String str);

  void onSetMode(int i, int i2, String str, int i3);

  void onUnsetMode(int i, int i2, String str);
}
