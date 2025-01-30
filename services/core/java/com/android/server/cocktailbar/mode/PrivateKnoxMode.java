package com.android.server.cocktailbar.mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import com.android.server.cocktailbar.CocktailBarManagerServiceListener;
import com.android.server.cocktailbar.mode.CocktailBarMode;
import com.samsung.android.knox.SemPersonaManager;

/* loaded from: classes.dex */
public class PrivateKnoxMode extends AbsPrivateMode {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public int mCurrentUserId;
    public CocktailBarManagerServiceListener mServiceListener;

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public int getDefinedCocktailType() {
        return 2;
    }

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public String getDefinedPrivateModeName() {
        return "knoxmode";
    }

    public PrivateKnoxMode(Context context, int i, BroadcastReceiver broadcastReceiver, CocktailBarMode.OnCocktailBarModeListener onCocktailBarModeListener, CocktailBarManagerServiceListener cocktailBarManagerServiceListener) {
        super(context, i, broadcastReceiver, onCocktailBarModeListener);
        this.mCurrentUserId = 0;
        this.mServiceListener = cocktailBarManagerServiceListener;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public int onBroadcastReceived(Intent intent) {
        intent.getAction();
        return 0;
    }

    @Override // com.android.server.cocktailbar.mode.AbsPrivateMode
    public boolean isEnableMode() {
        return SemPersonaManager.isKioskModeEnabled(this.mContext);
    }
}
