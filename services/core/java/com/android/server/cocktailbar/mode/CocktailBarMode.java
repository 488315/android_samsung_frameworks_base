package com.android.server.cocktailbar.mode;

import android.content.Intent;

public interface CocktailBarMode {
    int getCocktailType();

    int getModeId();

    String getModeName();

    int getRegistrationType();

    int onBroadcastReceived(Intent intent);

    int renewMode(int i);
}
