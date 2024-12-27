package com.android.server.cocktailbar.mode;

import android.content.Intent;

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
