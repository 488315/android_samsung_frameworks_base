package com.android.server.cocktailbar.mode;

import android.content.Intent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NormalMode implements CocktailBarMode {
    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int getCocktailType() {
        return 0;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int getModeId() {
        return 1;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final String getModeName() {
        return null;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int getRegistrationType() {
        return 0;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int onBroadcastReceived(Intent intent) {
        return 0;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int renewMode(int i) {
        return 1;
    }
}
