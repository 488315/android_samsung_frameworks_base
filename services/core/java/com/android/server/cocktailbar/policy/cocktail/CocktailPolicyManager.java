package com.android.server.cocktailbar.policy.cocktail;

import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.server.cocktailbar.CocktailBarManagerServiceContainer;
import com.android.server.cocktailbar.CocktailBarManagerServiceImpl;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.settings.CocktailBarSettings;
import com.samsung.android.cocktailbar.Cocktail;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CocktailPolicyManager {
    public ArrayList mCocktailPolicys;
    public OnCocktailBarPolicyListener mListener;
    public SparseArray mUpdatableCocktailMap;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnCocktailBarPolicyListener {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UpadatableCocktailInfo {
        public SparseBooleanArray mCocktailList;
    }

    public final boolean canUpdateCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, CocktailBarModeManager cocktailBarModeManager) {
        AbsCocktailPolicy findMatchedPolicy = findMatchedPolicy(cocktail);
        if (findMatchedPolicy != null) {
            return findMatchedPolicy.isAcceptUpdateCocktail(cocktail, cocktailBarSettings, cocktailBarModeManager, i, isUpdatedCocktail(cocktail.getCocktailId(), i));
        }
        return false;
    }

    public final void disableUpdatableCocktail(int i, int i2) {
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo != null) {
            upadatableCocktailInfo.mCocktailList.delete(i);
        }
    }

    public final void enableUpdatableCocktail(int i, int i2) {
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo == null) {
            upadatableCocktailInfo = new UpadatableCocktailInfo();
            upadatableCocktailInfo.mCocktailList = new SparseBooleanArray();
            this.mUpdatableCocktailMap.put(i2, upadatableCocktailInfo);
        }
        upadatableCocktailInfo.mCocktailList.put(i, true);
    }

    public final void establishPolicy(Cocktail cocktail, int i) {
        AbsCocktailPolicy findCocktailPolicy = findCocktailPolicy();
        if (findCocktailPolicy != null) {
            findCocktailPolicy.establishPolicy(cocktail, i);
        }
    }

    public final AbsCocktailPolicy findCocktailPolicy() {
        Iterator it = this.mCocktailPolicys.iterator();
        while (it.hasNext()) {
            AbsCocktailPolicy absCocktailPolicy = (AbsCocktailPolicy) it.next();
            if (absCocktailPolicy.getCocktailType() == 6) {
                Slog.i("CocktailPolicyManager", "findPolicy: find policy = 6");
                return absCocktailPolicy;
            }
        }
        return null;
    }

    public final AbsCocktailPolicy findMatchedPolicy(Cocktail cocktail) {
        Iterator it = this.mCocktailPolicys.iterator();
        while (it.hasNext()) {
            AbsCocktailPolicy absCocktailPolicy = (AbsCocktailPolicy) it.next();
            if (absCocktailPolicy.isMatchedPolicy(cocktail)) {
                Slog.i("CocktailPolicyManager", "findMatchedPolicy: find policy = " + absCocktailPolicy.getCocktailType());
                return absCocktailPolicy;
            }
        }
        return null;
    }

    public final boolean isUpdatedCocktail(int i, int i2) {
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo != null) {
            return upadatableCocktailInfo.mCocktailList.get(i);
        }
        return false;
    }

    public final void onRemoveUpdatableCocktail(int i) {
        int i2 = i >> 16;
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo == null || !upadatableCocktailInfo.mCocktailList.get(i)) {
            return;
        }
        CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer = (CocktailBarManagerServiceContainer) this.mListener;
        if (!cocktailBarManagerServiceContainer.enforceCocktailBarService() || CocktailBarManagerServiceContainer.isNotEdgeRunnableId(i2)) {
            return;
        }
        CocktailBarManagerServiceImpl implForUser = cocktailBarManagerServiceContainer.getImplForUser(i2);
        synchronized (implForUser.mCocktailArr) {
            implForUser.removeCocktailLocked(i);
        }
    }

    public final void onUpdateCocktail(int i) {
        int i2 = i >> 16;
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo == null || upadatableCocktailInfo.mCocktailList.get(i)) {
            return;
        }
        CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer = (CocktailBarManagerServiceContainer) this.mListener;
        if (!cocktailBarManagerServiceContainer.enforceCocktailBarService() || CocktailBarManagerServiceContainer.isNotEdgeRunnableId(i2)) {
            return;
        }
        CocktailBarManagerServiceImpl implForUser = cocktailBarManagerServiceContainer.getImplForUser(i2);
        synchronized (implForUser.mCocktailArr) {
            try {
                Cocktail cocktail = (Cocktail) implForUser.mCocktailArr.get(i);
                if (cocktail != null) {
                    implForUser.sendEnableAndUpdateBroadcastLocked(cocktail);
                }
            } finally {
            }
        }
    }
}
