package com.samsung.android.cocktailbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/* loaded from: classes6.dex */
public class SemCocktailProvider extends BroadcastReceiver {
    private static final String ACTION_COCKTAIL_DISABLED = "com.samsung.android.cocktail.action.COCKTAIL_DISABLED";
    private static final String ACTION_COCKTAIL_ENABLED = "com.samsung.android.cocktail.action.COCKTAIL_ENABLED";
    private static final String ACTION_COCKTAIL_UPDATE = "com.samsung.android.cocktail.action.COCKTAIL_UPDATE";
    private static final String ACTION_COCKTAIL_UPDATE_V2 = "com.samsung.android.cocktail.v2.action.COCKTAIL_UPDATE";
    private static final String ACTION_COCKTAIL_VISIBILITY_CHANGED = "com.samsung.android.cocktail.action.COCKTAIL_VISIBILITY_CHANGED";
    private static final String EXTRA_COCKTAIL_ID = "cocktailId";
    private static final String EXTRA_COCKTAIL_IDS = "cocktailIds";
    private static final String EXTRA_COCKTAIL_VISIBILITY = "cocktailVisibility";
    private static final String TAG = "SemCocktailProvider";

    public void onCocktailDisabled(Context context) {
    }

    public void onCocktailEnabled(Context context) {
    }

    public void onCocktailUpdate(Context context, SemCocktailBarManager semCocktailBarManager, int[] iArr) {
    }

    public void onCocktailVisibilityChanged(Context context, int i, int i2) {
    }

    public void onDisabled(Context context) {
    }

    public void onEnabled(Context context) {
    }

    public void onUpdate(Context context, SemCocktailBarManager semCocktailBarManager, int[] iArr) {
    }

    public void onVisibilityChanged(Context context, int i, int i2) {
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Bundle extras;
        String action = intent.getAction();
        if ("com.samsung.android.cocktail.action.COCKTAIL_UPDATE".equals(action) || "com.samsung.android.cocktail.v2.action.COCKTAIL_UPDATE".equals(action)) {
            Bundle extras2 = intent.getExtras();
            if (extras2 == null || !extras2.containsKey("cocktailIds")) {
                return;
            }
            int[] intArray = extras2.getIntArray("cocktailIds");
            onCocktailUpdate(context, SemCocktailBarManager.getInstance(context), intArray);
            onUpdate(context, SemCocktailBarManager.getInstance(context), intArray);
            return;
        }
        if ("com.samsung.android.cocktail.action.COCKTAIL_ENABLED".equals(action)) {
            onCocktailEnabled(context);
            onEnabled(context);
            return;
        }
        if ("com.samsung.android.cocktail.action.COCKTAIL_DISABLED".equals(action)) {
            onCocktailDisabled(context);
            onDisabled(context);
        } else if ("com.samsung.android.cocktail.action.COCKTAIL_VISIBILITY_CHANGED".equals(action) && (extras = intent.getExtras()) != null && extras.containsKey("cocktailId")) {
            int i = extras.getInt("cocktailId");
            if (extras.containsKey("cocktailVisibility")) {
                int i2 = extras.getInt("cocktailVisibility");
                onCocktailVisibilityChanged(context, i, i2);
                onVisibilityChanged(context, i, i2);
            }
        }
    }
}
