package com.android.server.cocktailbar.mode;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Debug;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.cocktailbar.constant.Constants;
import com.android.server.cocktailbar.mode.CocktailBarMode;
import com.samsung.android.emergencymode.SemEmergencyManager;

/* loaded from: classes.dex */
public class PrivateEmergencyMode extends AbsPrivateMode {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final String TAG = "PrivateEmergencyMode";

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public int getDefinedCocktailType() {
        return 1;
    }

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public String getDefinedPrivateModeName() {
        return "emergencymode";
    }

    public PrivateEmergencyMode(Context context, int i, BroadcastReceiver broadcastReceiver, CocktailBarMode.OnCocktailBarModeListener onCocktailBarModeListener) {
        super(context, i, broadcastReceiver, onCocktailBarModeListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        registerBroadcastReceiver(broadcastReceiver, intentFilter);
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public int onBroadcastReceived(Intent intent) {
        if (!"com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(intent.getAction())) {
            return 0;
        }
        int intExtra = intent.getIntExtra("reason", 0);
        if (DEBUG) {
            Slog.d(TAG, "EMERGENCY_STATE_CHANGED : reason = " + intExtra);
        }
        if (intExtra == 2) {
            return 2;
        }
        if (intExtra != 5) {
            return 1;
        }
        Slog.d(TAG, "start cocktailbarservice");
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.setClassName(Constants.COCKTAIL_BAR_PACKAGE_NAME, Constants.COCKTAIL_BAR_CLASS_NAME);
        this.mContext.startServiceAsUser(intent2, new UserHandle(ActivityManager.getCurrentUser()));
        return 3;
    }

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public boolean isEnableMode() {
        return SemEmergencyManager.isEmergencyMode(this.mContext);
    }
}
