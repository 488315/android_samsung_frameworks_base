package com.android.server.cocktailbar.mode;

import android.content.Intent;
import android.os.Debug;

import com.samsung.android.knox.SemPersonaManager;

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
