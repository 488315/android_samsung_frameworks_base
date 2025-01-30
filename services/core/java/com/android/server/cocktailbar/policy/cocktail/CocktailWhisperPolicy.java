package com.android.server.cocktailbar.policy.cocktail;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.cocktailbar.mode.CocktailBarModeManager;
import com.android.server.cocktailbar.policy.cocktail.CocktailPolicy;
import com.android.server.cocktailbar.settings.CocktailBarSettings;
import com.samsung.android.cocktailbar.Cocktail;
import com.samsung.android.cocktailbar.CocktailProviderInfo;
import com.samsung.android.util.SemLog;

/* loaded from: classes.dex */
public class CocktailWhisperPolicy extends AbsCocktailPolicy {
    public static final String TAG = "CocktailWhisperPolicy";
    public Context mContext;
    public SparseArray mCurrentWhisperInfo;
    public Object mLock;
    public SparseArray mWhisperInfoList;

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public int getCocktailType() {
        return 6;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isAcceptShowCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
        return false;
    }

    public CocktailWhisperPolicy(Context context, CocktailPolicy.OnCocktailPolicyListener onCocktailPolicyListener) {
        super(onCocktailPolicyListener);
        this.mWhisperInfoList = new SparseArray();
        this.mCurrentWhisperInfo = new SparseArray();
        this.mLock = new Object();
        this.mContext = context;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isAcceptUpdateCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, CocktailBarModeManager cocktailBarModeManager, int i, boolean z) {
        synchronized (this.mLock) {
            int callingUid = Binder.getCallingUid();
            WhisperInfo whisperInfo = (WhisperInfo) this.mCurrentWhisperInfo.get(cocktail.getCocktailId());
            if (whisperInfo == null || !(callingUid == cocktail.getUid() || callingUid == whisperInfo.uid)) {
                return false;
            }
            this.mListener.onEanbleUpdatableCocktail(cocktail.getCocktailId(), i);
            return true;
        }
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isAcceptCloseCocktail(Cocktail cocktail, CocktailBarSettings cocktailBarSettings, int i, boolean z) {
        if (!z) {
            return true;
        }
        this.mListener.onDisableUpdatableCocktail(cocktail.getCocktailId(), i);
        return true;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public boolean isMatchedPolicy(Cocktail cocktail) {
        if (cocktail == null) {
            Slog.i(TAG, "isMatchedPolicy: cocktail is null");
            return false;
        }
        CocktailProviderInfo providerInfo = cocktail.getProviderInfo();
        return providerInfo != null && providerInfo.category == 512;
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy, com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public void establishPolicy(Cocktail cocktail, int i) {
        if (cocktail.getProviderInfo().category == 512) {
            synchronized (this.mLock) {
                if (i == 1 || i == 2) {
                    if (cocktail.getProviderInfo().whisper != null) {
                        this.mWhisperInfoList.put(cocktail.getCocktailId(), new WhisperInfo(this.mContext, cocktail.getProviderInfo().whisper, cocktail));
                    } else {
                        this.mWhisperInfoList.put(cocktail.getCocktailId(), new WhisperInfo(cocktail));
                    }
                } else if (i == 3) {
                    this.mWhisperInfoList.remove(cocktail.getCocktailId());
                }
            }
        }
    }

    @Override // com.android.server.cocktailbar.policy.cocktail.AbsCocktailPolicy, com.android.server.cocktailbar.policy.cocktail.CocktailPolicy
    public void changeResumePackage(String str) {
        synchronized (this.mLock) {
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
        }
    }

    public class WhisperInfo {
        public int cocktailId;
        public String packageName;
        public int uid;

        public WhisperInfo(Cocktail cocktail) {
            this.cocktailId = cocktail.getCocktailId();
            this.packageName = cocktail.getProvider() != null ? cocktail.getProvider().getPackageName() : null;
            this.uid = cocktail.getUid();
        }

        public WhisperInfo(Context context, String str, Cocktail cocktail) {
            this.cocktailId = cocktail.getCocktailId();
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && str != null) {
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
                    if (applicationInfo != null) {
                        this.packageName = str;
                        this.uid = applicationInfo.uid;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    SemLog.e(CocktailWhisperPolicy.TAG, "WhisperInfo packageName is wrong");
                }
            }
            if (this.packageName == null) {
                this.packageName = cocktail.getProvider() != null ? cocktail.getProvider().getPackageName() : null;
                this.uid = cocktail.getUid();
            }
        }
    }
}
