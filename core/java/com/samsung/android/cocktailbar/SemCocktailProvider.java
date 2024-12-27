package com.samsung.android.cocktailbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SemCocktailProvider extends BroadcastReceiver {
    private static final String ACTION_COCKTAIL_DISABLED =
            "com.samsung.android.cocktail.action.COCKTAIL_DISABLED";
    private static final String ACTION_COCKTAIL_ENABLED =
            "com.samsung.android.cocktail.action.COCKTAIL_ENABLED";
    private static final String ACTION_COCKTAIL_UPDATE =
            "com.samsung.android.cocktail.action.COCKTAIL_UPDATE";
    private static final String ACTION_COCKTAIL_UPDATE_V2 =
            "com.samsung.android.cocktail.v2.action.COCKTAIL_UPDATE";
    private static final String ACTION_COCKTAIL_VISIBILITY_CHANGED =
            "com.samsung.android.cocktail.action.COCKTAIL_VISIBILITY_CHANGED";
    private static final String EXTRA_COCKTAIL_ID = "cocktailId";
    private static final String EXTRA_COCKTAIL_IDS = "cocktailIds";
    private static final String EXTRA_COCKTAIL_VISIBILITY = "cocktailVisibility";
    private static final String TAG = "SemCocktailProvider";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Bundle extras;
        String action = intent.getAction();
        if ("com.samsung.android.cocktail.action.COCKTAIL_UPDATE".equals(action)
                || "com.samsung.android.cocktail.v2.action.COCKTAIL_UPDATE".equals(action)) {
            Bundle extras2 = intent.getExtras();
            if (extras2 != null && extras2.containsKey("cocktailIds")) {
                int[] cocktailIds = extras2.getIntArray("cocktailIds");
                onCocktailUpdate(context, SemCocktailBarManager.getInstance(context), cocktailIds);
                onUpdate(context, SemCocktailBarManager.getInstance(context), cocktailIds);
                return;
            }
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
        } else if ("com.samsung.android.cocktail.action.COCKTAIL_VISIBILITY_CHANGED".equals(action)
                && (extras = intent.getExtras()) != null
                && extras.containsKey("cocktailId")) {
            int cocktailId = extras.getInt("cocktailId");
            if (extras.containsKey("cocktailVisibility")) {
                int visibility = extras.getInt("cocktailVisibility");
                onCocktailVisibilityChanged(context, cocktailId, visibility);
                onVisibilityChanged(context, cocktailId, visibility);
            }
        }
    }

    public void onUpdate(
            Context context, SemCocktailBarManager cocktailManager, int[] cocktailIds) {}

    public void onEnabled(Context context) {}

    public void onDisabled(Context context) {}

    public void onVisibilityChanged(Context context, int cocktailId, int visibility) {}

    public void onCocktailUpdate(
            Context context, SemCocktailBarManager cocktailManager, int[] cocktailIds) {}

    public void onCocktailEnabled(Context context) {}

    public void onCocktailDisabled(Context context) {}

    public void onCocktailVisibilityChanged(Context context, int cocktailId, int visibility) {}
}
