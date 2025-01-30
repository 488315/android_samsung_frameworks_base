package com.android.server.cocktailbar.policy.cocktail;

import com.samsung.android.cocktailbar.Cocktail;

/* loaded from: classes.dex */
public abstract class AbsCocktailPolicy implements CocktailPolicy {
  public CocktailPolicy.OnCocktailPolicyListener mListener;

  @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
  public void changeResumePackage(String str) {}

  @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
  public void establishPolicy(Cocktail cocktail, int i) {}

  public AbsCocktailPolicy(CocktailPolicy.OnCocktailPolicyListener onCocktailPolicyListener) {
    this.mListener = onCocktailPolicyListener;
  }
}
