package com.android.server.cocktailbar.policy.cocktail;

import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.policy.cocktail.CocktailPolicy;
import com.android.server.cocktailbar.settings.CocktailBarSettings;
import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailProviderInfo;

/* loaded from: classes.dex */
public class CocktailNormalPolicy extends AbsCocktailPolicy {
    public static final String TAG = CocktailContextualPolicy.class.getSimpleName();

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public int getCocktailType() {
        return 1;
    }

    public CocktailNormalPolicy(CocktailPolicy.OnCocktailPolicyListener onCocktailPolicyListener) {
        super(onCocktailPolicyListener);
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isAcceptUpdateCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, CocktailBarModeManager cocktailBarModeManager, int i, boolean z) {
        return cocktailBarSettings.isEnabledCocktail(cocktail.getCocktailId()) && z;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isAcceptShowCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
        return cocktailBarSettings.isEnabledCocktail(cocktail.getCocktailId()) && z;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isAcceptCloseCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
        return cocktailBarSettings.isEnabledCocktail(cocktail.getCocktailId()) && z;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isMatchedPolicy(Cocktail cocktail) {
        if (cocktail == null) {
            Slog.i(TAG, "isMatchedPolicy: cocktail is null");
            return false;
        }
        CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
        return (providerInfo == null || (providerInfo.category & FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP) == 0) ? false : true;
    }
}
