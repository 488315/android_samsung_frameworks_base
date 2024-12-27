package com.android.server.cocktailbar.mode;

import android.content.Intent;
import android.os.Debug;

import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PrivateKnoxMode extends AbsPrivateMode {
    static {
        Debug.semIsProductDev();
    }

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public final int getDefinedCocktailType() {
        return 2;
    }

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public final String getDefinedPrivateModeName() {
        return "knoxmode";
    }

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public final boolean isEnableMode() {
        return SemPersonaManager.isKioskModeEnabled(this.mContext);
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int onBroadcastReceived(Intent intent) {
        intent.getAction();
        return 0;
    }
}
