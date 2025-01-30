package com.android.server.cocktailbar.mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.server.cocktailbar.mode.CocktailBarMode;

/* loaded from: classes.dex */
public abstract class AbsPrivateMode implements CocktailBarMode {
    public int mCocktailType;
    public Context mContext;
    public CocktailBarMode.OnCocktailBarModeListener mListener;
    public int mPrivateModeId;
    public String mPrivateModeName;
    public int mRegistratonType;

    public abstract int getDefinedCocktailType();

    public abstract String getDefinedPrivateModeName();

    public abstract boolean isEnableMode();

    public AbsPrivateMode(Context context, int i, CocktailBarMode.OnCocktailBarModeListener onCocktailBarModeListener) {
        this.mRegistratonType = 0;
        this.mCocktailType = 1;
        this.mContext = context;
        this.mPrivateModeId = i;
        this.mCocktailType = getDefinedCocktailType();
        this.mPrivateModeName = getDefinedPrivateModeName();
        this.mListener = onCocktailBarModeListener;
    }

    public AbsPrivateMode(Context context, int i, BroadcastReceiver broadcastReceiver, CocktailBarMode.OnCocktailBarModeListener onCocktailBarModeListener) {
        this(context, i, onCocktailBarModeListener);
        this.mRegistratonType = 1;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public int renewMode(int i) {
        return isEnableMode() ? getModeId() : i;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public String getModeName() {
        return this.mPrivateModeName;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public int getRegistrationType() {
        return this.mRegistratonType;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public int getCocktailType() {
        return this.mCocktailType;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public int getModeId() {
        return this.mPrivateModeId;
    }

    public void registerBroadcastReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        this.mContext.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    }
}
