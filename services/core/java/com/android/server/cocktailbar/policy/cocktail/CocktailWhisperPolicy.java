package com.android.server.cocktailbar.policy.cocktail;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.util.SparseArray;

import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.settings.CocktailBarSettings;

import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailProviderInfo;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CocktailWhisperPolicy extends AbsCocktailPolicy {
    public Context mContext;
    public SparseArray mCurrentWhisperInfo;
    public Object mLock;
    public SparseArray mWhisperInfoList;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WhisperInfo {
        public String packageName;
        public int uid;

        public WhisperInfo(Context context, String str, Cocktail cocktail) {
            cocktail.getCocktailId();
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && str != null) {
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
                    if (applicationInfo != null) {
                        this.packageName = str;
                        this.uid = applicationInfo.uid;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    SemLog.e("CocktailWhisperPolicy", "WhisperInfo packageName is wrong");
                }
            }
            if (this.packageName == null) {
                this.packageName =
                        cocktail.getProvider() != null
                                ? cocktail.getProvider().getPackageName()
                                : null;
                this.uid = cocktail.getUid();
            }
        }
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final void changeResumePackage(String str) {
        synchronized (this.mLock) {
            try {
                SparseArray sparseArray = this.mCurrentWhisperInfo;
                this.mCurrentWhisperInfo = new SparseArray();
                int size = this.mWhisperInfoList.size();
                for (int i = 0; i < size; i++) {
                    WhisperInfo whisperInfo = (WhisperInfo) this.mWhisperInfoList.valueAt(i);
                    if (whisperInfo != null && whisperInfo.packageName.equals(str)) {
                        int keyAt = this.mWhisperInfoList.keyAt(i);
                        this.mCurrentWhisperInfo.put(keyAt, whisperInfo);
                        this.mListener.onUpdateCocktail(keyAt);
                    }
                }
                int size2 = sparseArray.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    int keyAt2 = sparseArray.keyAt(i2);
                    if (this.mCurrentWhisperInfo.get(keyAt2) == null) {
                        this.mListener.onRemoveUpdatableCocktail(keyAt2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final void establishPolicy(Cocktail cocktail, int i) {
        if (cocktail.getProviderInfo().category == 512) {
            synchronized (this.mLock) {
                try {
                    if (i == 1 || i == 2) {
                        if (cocktail.getProviderInfo().whisper != null) {
                            this.mWhisperInfoList.put(
                                    cocktail.getCocktailId(),
                                    new WhisperInfo(
                                            this.mContext,
                                            cocktail.getProviderInfo().whisper,
                                            cocktail));
                        } else {
                            SparseArray sparseArray = this.mWhisperInfoList;
                            int cocktailId = cocktail.getCocktailId();
                            WhisperInfo whisperInfo = new WhisperInfo();
                            cocktail.getCocktailId();
                            whisperInfo.packageName =
                                    cocktail.getProvider() != null
                                            ? cocktail.getProvider().getPackageName()
                                            : null;
                            whisperInfo.uid = cocktail.getUid();
                            sparseArray.put(cocktailId, whisperInfo);
                        }
                    } else if (i == 3) {
                        this.mWhisperInfoList.remove(cocktail.getCocktailId());
                    }
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final int getCocktailType() {
        return 6;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isAcceptCloseCocktail(
            Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
        if (!z) {
            return true;
        }
        this.mListener.disableUpdatableCocktail(cocktail.getCocktailId(), i);
        return true;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isAcceptShowCocktail(
            Cocktail cocktail, CocktailBarSettings cocktailBarSettings, boolean z) {
        return false;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isAcceptUpdateCocktail(
            Cocktail cocktail,
            CocktailBarSettings cocktailBarSettings,
            CocktailBarModeManager cocktailBarModeManager,
            int i,
            boolean z) {
        synchronized (this.mLock) {
            try {
                int callingUid = Binder.getCallingUid();
                WhisperInfo whisperInfo =
                        (WhisperInfo) this.mCurrentWhisperInfo.get(cocktail.getCocktailId());
                if (whisperInfo == null
                        || (callingUid != cocktail.getUid() && callingUid != whisperInfo.uid)) {
                    return false;
                }
                this.mListener.enableUpdatableCocktail(cocktail.getCocktailId(), i);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy
    public final boolean isMatchedPolicy(Cocktail cocktail) {
        CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
        return providerInfo != null && providerInfo.category == 512;
    }
}
