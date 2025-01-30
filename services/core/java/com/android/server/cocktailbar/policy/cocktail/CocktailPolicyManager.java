package com.android.server.cocktailbar.policy.cocktail;

import android.content.Context;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.policy.cocktail.CocktailPolicy;
import com.android.server.cocktailbar.settings.CocktailBarSettings;
import com.android.server.cocktailbar.utils.CocktailBarConfig;
import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailProviderInfo;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class CocktailPolicyManager implements CocktailPolicy.OnCocktailPolicyListener {
    public static final String TAG = "CocktailPolicyManager";
    public OnCocktailBarPolicyListener mListener;
    public ArrayList mCocktailPolicys = new ArrayList();
    public SparseArray mUpdatableCocktailMap = new SparseArray();

    public interface OnCocktailBarPolicyListener {
        void onRemoveCocktail(int i, int i2);

        void onUpdateCocktail(int i, int i2);
    }

    public CocktailPolicyManager(Context context, OnCocktailBarPolicyListener onCocktailBarPolicyListener) {
        this.mListener = onCocktailBarPolicyListener;
        int categoryIds = CocktailProviderInfo.getCategoryIds(CocktailBarConfig.getInstance(context).getCategoryFilter());
        if (categoryIds == 0 || (65536 & categoryIds) != 0) {
            setupCocktailPolicy(new CocktailContextualPolicy(this));
        }
        if ((categoryIds & 512) != 0) {
            setupCocktailPolicy(new CocktailWhisperPolicy(context, this));
        }
        if (categoryIds == 0 || (categoryIds & FrameworkStatsLog.f636xd07885aa) != 0) {
            setupCocktailPolicy(new CocktailNativePolicy(this));
        }
        setupCocktailPolicy(new CocktailNormalPolicy(this));
    }

    public final void setupCocktailPolicy(CocktailPolicy cocktailPolicy) {
        this.mCocktailPolicys.add(cocktailPolicy);
    }

    public void establishPolicy(Cocktail cocktail, int i, int i2) {
        if (cocktail == null) {
            Slog.e(TAG, "establishPolicy: cocktail is null");
            return;
        }
        CocktailPolicy findCocktailPolicy = findCocktailPolicy(i);
        if (findCocktailPolicy != null) {
            findCocktailPolicy.establishPolicy(cocktail, i2);
        }
    }

    public void changeResumePackage(String str, int i) {
        CocktailPolicy findCocktailPolicy = findCocktailPolicy(i);
        if (findCocktailPolicy != null) {
            findCocktailPolicy.changeResumePackage(str);
        }
    }

    public void enableUpdatableCocktail(int i, int i2) {
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo == null) {
            upadatableCocktailInfo = new UpadatableCocktailInfo(i2);
            this.mUpdatableCocktailMap.put(i2, upadatableCocktailInfo);
        }
        upadatableCocktailInfo.enableUpdate(i);
    }

    public void disableUpdatableCocktail(int i, int i2) {
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo != null) {
            upadatableCocktailInfo.disableUpdate(i);
        }
    }

    public boolean isUpdatedCocktail(int i, int i2) {
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo != null) {
            return upadatableCocktailInfo.isUpdatedCocktail(i);
        }
        return false;
    }

    public boolean canSendUpdateIntent(Cocktail cocktail, CocktailBarSettings cocktailBarSettings) {
        return cocktailBarSettings.isEnabledCocktail(cocktail.getCocktailId());
    }

    public boolean canUpdateCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, CocktailBarModeManager cocktailBarModeManager) {
        if (cocktail == null) {
            Slog.e(TAG, "canUpdateCocktail: cocktail is null");
            return false;
        }
        CocktailPolicy findMatchedPolicy = findMatchedPolicy(cocktail);
        if (findMatchedPolicy != null) {
            return findMatchedPolicy.isAcceptUpdateCocktail(cocktail, cocktailBarSettings, cocktailBarModeManager, i, isUpdatedCocktail(cocktail.getCocktailId(), i));
        }
        return false;
    }

    public boolean canShowCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, CocktailBarModeManager cocktailBarModeManager) {
        if (cocktail == null) {
            Slog.e(TAG, "canShowCocktail: cocktail is null");
            return false;
        }
        CocktailPolicy findMatchedPolicy = findMatchedPolicy(cocktail);
        if (findMatchedPolicy != null) {
            return findMatchedPolicy.isAcceptShowCocktail(cocktail, cocktailBarSettings, i, isUpdatedCocktail(cocktail.getCocktailId(), i));
        }
        return false;
    }

    public boolean canCloseCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, CocktailBarModeManager cocktailBarModeManager) {
        if (cocktail == null) {
            Slog.e(TAG, "canCloseCocktail: cocktail is null");
            return false;
        }
        CocktailPolicy findMatchedPolicy = findMatchedPolicy(cocktail);
        if (findMatchedPolicy != null) {
            return findMatchedPolicy.isAcceptCloseCocktail(cocktail, cocktailBarSettings, i, isUpdatedCocktail(cocktail.getCocktailId(), i));
        }
        return false;
    }

    public final CocktailPolicy findMatchedPolicy(Cocktail cocktail) {
        Iterator it = this.mCocktailPolicys.iterator();
        while (it.hasNext()) {
            CocktailPolicy cocktailPolicy = (CocktailPolicy) it.next();
            if (cocktailPolicy.isMatchedPolicy(cocktail)) {
                Slog.i(TAG, "findMatchedPolicy: find policy = " + cocktailPolicy.getCocktailType());
                return cocktailPolicy;
            }
        }
        return null;
    }

    public final CocktailPolicy findCocktailPolicy(int i) {
        Iterator it = this.mCocktailPolicys.iterator();
        while (it.hasNext()) {
            CocktailPolicy cocktailPolicy = (CocktailPolicy) it.next();
            if (cocktailPolicy.getCocktailType() == i) {
                Slog.i(TAG, "findPolicy: find policy = " + i);
                return cocktailPolicy;
            }
        }
        return null;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy.OnCocktailPolicyListener
    public void onEanbleUpdatableCocktail(int i, int i2) {
        enableUpdatableCocktail(i, i2);
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy.OnCocktailPolicyListener
    public void onDisableUpdatableCocktail(int i, int i2) {
        disableUpdatableCocktail(i, i2);
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy.OnCocktailPolicyListener
    public void onUpdateCocktail(int i) {
        int i2 = i >> 16;
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo == null || upadatableCocktailInfo.isUpdatedCocktail(i)) {
            return;
        }
        this.mListener.onUpdateCocktail(i, i2);
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy.OnCocktailPolicyListener
    public void onRemoveUpdatableCocktail(int i) {
        int i2 = i >> 16;
        UpadatableCocktailInfo upadatableCocktailInfo = (UpadatableCocktailInfo) this.mUpdatableCocktailMap.get(i2);
        if (upadatableCocktailInfo == null || !upadatableCocktailInfo.isUpdatedCocktail(i)) {
            return;
        }
        this.mListener.onRemoveCocktail(i, i2);
    }

    public class UpadatableCocktailInfo {
        public SparseBooleanArray mCocktailList = new SparseBooleanArray();
        public final int mUserId;

        public UpadatableCocktailInfo(int i) {
            this.mUserId = i;
        }

        public void enableUpdate(int i) {
            this.mCocktailList.put(i, true);
        }

        public void disableUpdate(int i) {
            this.mCocktailList.delete(i);
        }

        public boolean isUpdatedCocktail(int i) {
            return this.mCocktailList.get(i);
        }
    }
}
