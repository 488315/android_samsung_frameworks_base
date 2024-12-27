package com.android.server.cocktailbar.mode;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Debug;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PrivateEmergencyMode extends AbsPrivateMode {
    public static final boolean DEBUG = Debug.semIsProductDev();

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public final int getDefinedCocktailType() {
        return 1;
    }

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public final String getDefinedPrivateModeName() {
        return "emergencymode";
    }

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public final boolean isEnableMode() {
        return SemEmergencyManager.isEmergencyMode(this.mContext);
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int onBroadcastReceived(Intent intent) {
        if (!"com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(intent.getAction())) {
            return 0;
        }
        int intExtra = intent.getIntExtra("reason", 0);
        if (DEBUG) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    intExtra, "EMERGENCY_STATE_CHANGED : reason = ", "PrivateEmergencyMode");
        }
        if (intExtra == 2) {
            return 2;
        }
        if (intExtra != 5) {
            return 1;
        }
        Slog.d("PrivateEmergencyMode", "start cocktailbarservice");
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.setClassName(
                KnoxCustomManagerService.LAUNCHER_PACKAGE,
                "com.samsung.app.honeyspace.edge.CocktailBarService");
        this.mContext.startServiceAsUser(intent2, new UserHandle(ActivityManager.getCurrentUser()));
        return 3;
    }
}
