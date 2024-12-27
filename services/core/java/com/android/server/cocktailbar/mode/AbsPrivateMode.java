package com.android.server.cocktailbar.mode;

import android.content.Context;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AbsPrivateMode implements CocktailBarMode {
    public final int mCocktailType;
    public final Context mContext;
    public final int mPrivateModeId;
    public final String mPrivateModeName = getDefinedPrivateModeName();
    public final int mRegistratonType;

    public AbsPrivateMode(Context context, int i) {
        this.mRegistratonType = 0;
        this.mCocktailType = 1;
        this.mContext = context;
        this.mPrivateModeId = i;
        this.mCocktailType = getDefinedCocktailType();
        this.mRegistratonType = 1;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int getCocktailType() {
        return this.mCocktailType;
    }

    public abstract int getDefinedCocktailType();

    public abstract String getDefinedPrivateModeName();

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int getModeId() {
        return this.mPrivateModeId;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final String getModeName() {
        return this.mPrivateModeName;
    }

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int getRegistrationType() {
        return this.mRegistratonType;
    }

    public abstract boolean isEnableMode();

    @Override // com.android.server.cocktailbar.mode.CocktailBarMode
    public final int renewMode(int i) {
        return isEnableMode() ? this.mPrivateModeId : i;
    }
}
