package com.android.server.cocktailbar.mode;

import android.content.Intent;

/* loaded from: classes.dex */
public interface CocktailBarMode {

    public interface OnCocktailBarModeListener {
    }

    int getCocktailType();

    int getModeId();

    String getModeName();

    int getRegistrationType();

    int onBroadcastReceived(Intent intent);

    int renewMode(int i);
}
