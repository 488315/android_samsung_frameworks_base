package com.android.server.cocktailbar.policy.cocktail;

import android.util.Slog;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.settings.CocktailBarSettings;
import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailInfo;
import com.samsung.android.cocktailbar.CocktailProviderInfo;

/* loaded from: classes.dex */
public class CocktailNativePolicy extends AbsCocktailPolicy {
  public static final String TAG = "CocktailNativePolicy";

  @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
  public int getCocktailType() {
    return 5;
  }

  @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
  public boolean isAcceptShowCocktail(
      Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
    return z;
  }

  public CocktailNativePolicy(CocktailPolicy.OnCocktailPolicyListener onCocktailPolicyListener) {
    super(onCocktailPolicyListener);
  }

  @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
  public boolean isAcceptUpdateCocktail(
      Cocktail cocktail,
      CocktailBarSettings cocktailBarSettings,
      CocktailBarModeManager cocktailBarModeManager,
      int i,
      boolean z) {
    CocktailInfo cocktailInfo = cocktail.getCocktailInfo();
    if (cocktailInfo == null || (cocktailInfo.getCategory() & 65536) == 0) {
      return z;
    }
    this.mListener.onEanbleUpdatableCocktail(cocktail.getCocktailId(), i);
    return true;
  }

  @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
  public boolean isAcceptCloseCocktail(
      Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
    if (!z) {
      return false;
    }
    this.mListener.onDisableUpdatableCocktail(cocktail.getCocktailId(), i);
    return true;
  }

  @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
  public boolean isMatchedPolicy(Cocktail cocktail) {
    if (cocktail == null) {
      Slog.i(TAG, "isMatchedPolicy: cocktail is null");
      return false;
    }
    CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
    if (providerInfo == null) {
      return false;
    }
    int i = providerInfo.category;
    return i == 4 || i == 32 || i == 128;
  }
}
