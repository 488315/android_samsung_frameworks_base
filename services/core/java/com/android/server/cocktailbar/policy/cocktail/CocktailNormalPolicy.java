package com.android.server.cocktailbar.policy.cocktail;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.settings.CocktailBarSettings;

import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailProviderInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CocktailNormalPolicy extends AbsCocktailPolicy {
    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final int getCocktailType() {
        return 1;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isAcceptCloseCocktail(
            Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
        return cocktailBarSettings.isEnabledCocktail(cocktail.getCocktailId()) && z;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isAcceptShowCocktail(
            Cocktail cocktail, CocktailBarSettings cocktailBarSettings, boolean z) {
        return cocktailBarSettings.isEnabledCocktail(cocktail.getCocktailId()) && z;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isAcceptUpdateCocktail(
            Cocktail cocktail,
            CocktailBarSettings cocktailBarSettings,
            CocktailBarModeManager cocktailBarModeManager,
            int i,
            boolean z) {
        return cocktailBarSettings.isEnabledCocktail(cocktail.getCocktailId()) && z;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isMatchedPolicy(Cocktail cocktail) {
        CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
        return (providerInfo == null
                        || (providerInfo.category
                                        & FrameworkStatsLog
                                                .HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP)
                                == 0)
                ? false
                : true;
    }
}
