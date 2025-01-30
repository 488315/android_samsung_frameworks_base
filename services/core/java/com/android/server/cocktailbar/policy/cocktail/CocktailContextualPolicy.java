package com.android.server.cocktailbar.policy.cocktail;

import android.util.Slog;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.policy.cocktail.CocktailPolicy;
import com.android.server.cocktailbar.settings.CocktailBarSettings;
import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailInfo;
import com.samsung.android.cocktailbar.CocktailProviderInfo;

/* loaded from: classes.dex */
public class CocktailContextualPolicy extends AbsCocktailPolicy {
    public static final String TAG = "CocktailContextualPolicy";

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public int getCocktailType() {
        return 2;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isAcceptShowCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
        return z;
    }

    public CocktailContextualPolicy(CocktailPolicy.OnCocktailPolicyListener onCocktailPolicyListener) {
        super(onCocktailPolicyListener);
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isAcceptUpdateCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, CocktailBarModeManager cocktailBarModeManager, int i, boolean z) {
        if (cocktailBarModeManager.getCurrentModeId() != 1) {
            return z;
        }
        this.mListener.onEanbleUpdatableCocktail(cocktail.getCocktailId(), i);
        return true;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isAcceptCloseCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
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
        CocktailInfo cocktailInfo = cocktail.getCocktailInfo();
        if (providerInfo == null || (providerInfo.category & 65536) == 0) {
            return false;
        }
        return cocktailInfo == null || (cocktailInfo.getCategory() & 65536) != 0;
    }
}
