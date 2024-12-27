package com.android.server.cocktailbar.mode;

import android.content.Intent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface CocktailBarMode {
    int getCocktailType();

    int getModeId();

    String getModeName();

    int getRegistrationType();

    int onBroadcastReceived(Intent intent);

    int renewMode(int i);
}
