package com.android.server.cocktailbar.settings;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.cocktailbar.utils.CocktailBarConfig;

import com.samsung.android.cocktailbar.Cocktail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class CocktailBarSettings {
    public final Context mContext;
    public final int mCurrentUserId;
    public String mEnabledCocktailsStrCache;
    public final ContentResolver mResolver;
    public final TextUtils.SimpleStringSplitter mEnabledCocktailsSplitter =
            new TextUtils.SimpleStringSplitter(';');
    public final HashMap mCocktailMap = new HashMap();
    public ArrayList mEnabledCocktailListCache = new ArrayList();
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    public final class CocktailInfo {
        public final int cocktailId;
        public final String packageName;

        public CocktailInfo(int i, String str) {
            this.cocktailId = i;
            this.packageName = str;
        }
    }

    public CocktailBarSettings(Context context, SparseArray sparseArray, int i) {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mCurrentUserId = i;
        initCocktailMap(sparseArray);
        updateEnabledCocktailList();
    }

    public final ArrayList getEnableCocktailIds() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mEnabledCocktailListCache.iterator();
        while (it.hasNext()) {
            CocktailInfo cocktailInfo = (CocktailInfo) this.mCocktailMap.get((String) it.next());
            if (cocktailInfo != null) {
                arrayList.add(Integer.valueOf(cocktailInfo.cocktailId));
            }
        }
        return arrayList;
    }

    public final String getEnabledCocktailsStr() {
        ContentResolver contentResolver = this.mResolver;
        int i = this.mCurrentUserId;
        this.mEnabledCocktailsStrCache =
                Settings.System.getStringForUser(
                        contentResolver, "cocktail_bar_enabled_cocktails", i);
        Slog.d(
                "CocktailBarSettings",
                "getEnabledCocktailsStr: " + this.mEnabledCocktailsStrCache + ", " + i);
        return this.mEnabledCocktailsStrCache;
    }

    public final void initCocktailMap(SparseArray sparseArray) {
        synchronized (this.mCocktailMap) {
            try {
                this.mCocktailMap.clear();
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    Cocktail cocktail = (Cocktail) sparseArray.valueAt(i);
                    ComponentName provider = cocktail.getProvider();
                    if (provider != null) {
                        this.mCocktailMap.put(
                                cocktail.getProvider().getClassName(),
                                new CocktailInfo(
                                        cocktail.getCocktailId(), provider.getPackageName()));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isEnabledCocktail(int i) {
        synchronized (this.mCocktailMap) {
            try {
                Iterator it = this.mEnabledCocktailListCache.iterator();
                StringBuffer stringBuffer = null;
                while (it.hasNext()) {
                    String str = (String) it.next();
                    CocktailInfo cocktailInfo = (CocktailInfo) this.mCocktailMap.get(str);
                    if (cocktailInfo == null) {
                        if (stringBuffer == null) {
                            stringBuffer = new StringBuffer("isEnabledCocktail: invalid ");
                            stringBuffer.append(" for cid=");
                            stringBuffer.append(i);
                            stringBuffer.append(" uid=");
                            stringBuffer.append(this.mCurrentUserId);
                        }
                        stringBuffer.append(" [");
                        stringBuffer.append(str);
                        stringBuffer.append("]");
                    } else if (cocktailInfo.cocktailId == i) {
                        return true;
                    }
                }
                if (stringBuffer != null) {
                    Slog.d("CocktailBarSettings", stringBuffer.toString());
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setEnabledCocktailsLocked(ArrayList arrayList) {
        int i = this.mCurrentUserId;
        int size = arrayList.size();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < size; i2++) {
            if (i2 > 0) {
                sb.append(';');
            }
            sb.append((String) arrayList.get(i2));
        }
        try {
            String sb2 = sb.toString();
            Settings.System.putStringForUser(
                    this.mResolver, "cocktail_bar_enabled_cocktails", sb2, i);
            this.mEnabledCocktailsStrCache = sb2;
            Slog.d("CocktailBarSettings", "putEnabledCocktailsStr: " + sb2);
            if (TextUtils.isEmpty(sb.toString())) {
                Settings.System.putStringForUser(
                        this.mResolver, "previous_enable_list", getEnabledCocktailsStr(), i);
                Settings.System.putInt(
                        this.mResolver, "previous_enable_id_cnt", getEnableCocktailIds().size());
            }
        } catch (Exception e) {
            Slog.d("CocktailBarSettings", "setEnabledCocktailsLocked: " + e.toString());
        }
    }

    public final void updateEnabledCocktailList() {
        if (Process.myPid() != Binder.getCallingPid()) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.cocktailbar.settings.CocktailBarSettings.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            synchronized (CocktailBarSettings.this.mCocktailMap) {
                                CocktailBarSettings.this.updateEnabledCocktailListLocked();
                            }
                        }
                    });
            return;
        }
        synchronized (this.mCocktailMap) {
            updateEnabledCocktailListLocked();
        }
    }

    public final boolean updateEnabledCocktailListLocked() {
        String enabledCocktailsStr = getEnabledCocktailsStr();
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(enabledCocktailsStr)) {
            return false;
        }
        this.mEnabledCocktailsSplitter.setString(enabledCocktailsStr);
        boolean z = false;
        while (this.mEnabledCocktailsSplitter.hasNext()) {
            String next = this.mEnabledCocktailsSplitter.next();
            if (((CocktailInfo) this.mCocktailMap.get(next)) != null) {
                arrayList.add(next);
            } else {
                String str =
                        (String)
                                CocktailBarConfig.getInstance(this.mContext)
                                        .mReplacedComponent
                                        .get(next);
                CocktailInfo cocktailInfo = (CocktailInfo) this.mCocktailMap.get(str);
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "CocktailBarSettings",
                        InitialConfiguration$$ExternalSyntheticOutline0.m(
                                "updateEnabledCocktailListLocked chagned old = ",
                                next,
                                ",new=",
                                str,
                                ",info="),
                        cocktailInfo != null);
                if (cocktailInfo != null) {
                    arrayList.add(str);
                }
                z = true;
            }
        }
        if (z) {
            setEnabledCocktailsLocked(arrayList);
        }
        this.mEnabledCocktailListCache = arrayList;
        return true;
    }
}
