package com.android.server.cocktailbar.mode;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.android.server.cocktailbar.CocktailBarManagerServiceContainer;
import com.android.server.cocktailbar.CocktailBarManagerServiceListener;

import com.samsung.android.knox.SemPersonaManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class CocktailBarModeManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public int mCocktailBarModeId;
    public Context mContext;
    public PrivateKnoxMode mKnoxMode;
    public CocktailBarManagerServiceListener mListener;
    public NormalMode mNormalMode;
    public HashMap mPrivateModeMap;
    public ArrayList mPrivateModes;

    public final CocktailBarMode getCocktailBarMode(int i) {
        if (i != 0) {
            NormalMode normalMode = this.mNormalMode;
            if (i == 1) {
                return normalMode;
            }
            if (i == 2) {
                return !SemPersonaManager.isKioskModeEnabled(this.mContext)
                        ? normalMode
                        : this.mKnoxMode;
            }
            Iterator it = this.mPrivateModes.iterator();
            while (it.hasNext()) {
                CocktailBarMode cocktailBarMode = (CocktailBarMode) it.next();
                if (cocktailBarMode.getModeId() == i) {
                    return cocktailBarMode;
                }
            }
        }
        return null;
    }

    public final void onSetMode(int i, CocktailBarMode cocktailBarMode) {
        if (cocktailBarMode == null || this.mCocktailBarModeId == cocktailBarMode.getModeId()) {
            return;
        }
        CocktailBarMode cocktailBarMode2 = getCocktailBarMode(this.mCocktailBarModeId);
        CocktailBarManagerServiceListener cocktailBarManagerServiceListener = this.mListener;
        if (cocktailBarMode2 != null) {
            ((CocktailBarManagerServiceContainer) cocktailBarManagerServiceListener)
                    .onUnsetMode(i, this.mCocktailBarModeId, cocktailBarMode2.getModeName());
        }
        ((CocktailBarManagerServiceContainer) cocktailBarManagerServiceListener)
                .onSetMode(
                        i,
                        cocktailBarMode.getModeId(),
                        cocktailBarMode.getCocktailType(),
                        cocktailBarMode.getModeName());
        this.mCocktailBarModeId = cocktailBarMode.getModeId();
        if (DEBUG) {
            StringBuffer stringBuffer = new StringBuffer("onSetMode: ");
            stringBuffer.append(
                    cocktailBarMode2 != null ? cocktailBarMode2.getModeName() : " no-current");
            stringBuffer.append(" -> ");
            stringBuffer.append(cocktailBarMode.getModeName());
            Log.d("CocktailBarModeManager", stringBuffer.toString());
        }
    }

    public final void onSetModeForcely(CocktailBarMode cocktailBarMode) {
        if (cocktailBarMode != null) {
            CocktailBarMode cocktailBarMode2 = getCocktailBarMode(this.mCocktailBarModeId);
            CocktailBarManagerServiceListener cocktailBarManagerServiceListener = this.mListener;
            if (cocktailBarMode2 != null) {
                ((CocktailBarManagerServiceContainer) cocktailBarManagerServiceListener)
                        .onUnsetMode(0, this.mCocktailBarModeId, cocktailBarMode2.getModeName());
            }
            ((CocktailBarManagerServiceContainer) cocktailBarManagerServiceListener)
                    .onSetMode(
                            0,
                            cocktailBarMode.getModeId(),
                            cocktailBarMode.getCocktailType(),
                            cocktailBarMode.getModeName());
            this.mCocktailBarModeId = cocktailBarMode.getModeId();
            if (DEBUG) {
                StringBuffer stringBuffer =
                        new StringBuffer("CocktailBarModeManageronSetModeForcely: ");
                stringBuffer.append(
                        cocktailBarMode2 != null ? cocktailBarMode2.getModeName() : " no-current");
                stringBuffer.append(" -> ");
                stringBuffer.append(cocktailBarMode.getModeName());
                Log.d("CocktailBarModeManager", stringBuffer.toString());
            }
        }
    }

    public final void setupPrivateMode(AbsPrivateMode absPrivateMode) {
        if (this.mPrivateModeMap.get(absPrivateMode.mPrivateModeName) == null) {
            this.mPrivateModes.add(absPrivateMode);
            this.mPrivateModeMap.put(absPrivateMode.mPrivateModeName, absPrivateMode);
        } else {
            Log.e(
                    "CocktailBarModeManager",
                    "setupPrivateMode : exist duplicated privateMode : "
                            + absPrivateMode.mPrivateModeName);
        }
    }
}
