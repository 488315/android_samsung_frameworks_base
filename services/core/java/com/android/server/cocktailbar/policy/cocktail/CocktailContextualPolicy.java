package com.android.server.cocktailbar.policy.cocktail;

import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.settings.CocktailBarSettings;

import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailInfo;
import com.samsung.android.cocktailbar.CocktailProviderInfo;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CocktailContextualPolicy extends AbsCocktailPolicy {
    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final int getCocktailType() {
        return 2;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isAcceptCloseCocktail(
            Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
        if (!z) {
            return false;
        }
        this.mListener.disableUpdatableCocktail(cocktail.getCocktailId(), i);
        return true;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isAcceptShowCocktail(
            Cocktail cocktail, CocktailBarSettings cocktailBarSettings, boolean z) {
        return z;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isAcceptUpdateCocktail(
            Cocktail cocktail,
            CocktailBarSettings cocktailBarSettings,
            CocktailBarModeManager cocktailBarModeManager,
            int i,
            boolean z) {
        if (cocktailBarModeManager.mCocktailBarModeId != 1) {
            return z;
        }
        this.mListener.enableUpdatableCocktail(cocktail.getCocktailId(), i);
        return true;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isMatchedPolicy(Cocktail cocktail) {
        CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
        CocktailInfo cocktailInfo = cocktail.getCocktailInfo();
        if (providerInfo == null
                || (providerInfo.category
                                & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT)
                        == 0) {
            return false;
        }
        return cocktailInfo == null
                || (cocktailInfo.getCategory()
                                & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT)
                        != 0;
    }
}
