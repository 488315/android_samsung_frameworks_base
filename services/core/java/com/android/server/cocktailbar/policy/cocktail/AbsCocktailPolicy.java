package com.android.server.cocktailbar.policy.cocktail;

import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.settings.CocktailBarSettings;

import com.samsung.android.cocktailbar.Cocktail;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AbsCocktailPolicy {
    public final CocktailPolicyManager mListener;

    public AbsCocktailPolicy(CocktailPolicyManager cocktailPolicyManager) {
        this.mListener = cocktailPolicyManager;
    }

    public void changeResumePackage(String str) {}

    public void establishPolicy(Cocktail cocktail, int i) {}

    public abstract int getCocktailType();

    public abstract boolean isAcceptCloseCocktail(
            Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z);

    public abstract boolean isAcceptShowCocktail(
            Cocktail cocktail, CocktailBarSettings cocktailBarSettings, boolean z);

    public abstract boolean isAcceptUpdateCocktail(
            Cocktail cocktail,
            CocktailBarSettings cocktailBarSettings,
            CocktailBarModeManager cocktailBarModeManager,
            int i,
            boolean z);

    public abstract boolean isMatchedPolicy(Cocktail cocktail);
}
