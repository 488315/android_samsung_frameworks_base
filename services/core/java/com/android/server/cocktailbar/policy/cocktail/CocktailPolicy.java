package com.android.server.cocktailbar.policy.cocktail;

import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.settings.CocktailBarSettings;
import com.samsung.android.cocktailbar.Cocktail;

/* loaded from: classes.dex */
public interface CocktailPolicy {

    public interface OnCocktailPolicyListener {
        void onDisableUpdatableCocktail(int i, int i2);

        void onEanbleUpdatableCocktail(int i, int i2);

        void onRemoveUpdatableCocktail(int i);

        void onUpdateCocktail(int i);
    }

    void changeResumePackage(String str);

    void establishPolicy(Cocktail cocktail, int i);

    int getCocktailType();

    boolean isAcceptCloseCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z);

    boolean isAcceptShowCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z);

    boolean isAcceptUpdateCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, CocktailBarModeManager cocktailBarModeManager, int i, boolean z);

    boolean isMatchedPolicy(Cocktail cocktail);
}
