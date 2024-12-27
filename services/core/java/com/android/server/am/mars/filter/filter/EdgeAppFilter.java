package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EdgeAppFilter implements IFilter {
    public final ArrayList mCocktailBarList = new ArrayList();
    public Context mContext = null;
    ContentObserver mCocktailBarSettingsObserver = null;
    public boolean mRegisteredCocktailBarSettingsObserver = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class EdgeAppFilterHolder {
        public static final EdgeAppFilter INSTANCE = new EdgeAppFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        try {
            if (this.mRegisteredCocktailBarSettingsObserver) {
                this.mContext
                        .getContentResolver()
                        .unregisterContentObserver(this.mCocktailBarSettingsObserver);
                this.mRegisteredCocktailBarSettingsObserver = false;
            }
        } catch (IllegalArgumentException unused) {
            Slog.e(
                    "MARs:EdgeAppFilter",
                    "IllegalArgumentException occurred in unregisterEnabledCocktailBarChanged()");
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        ArrayList arrayList = this.mCocktailBarList;
        if (arrayList == null) {
            return 0;
        }
        synchronized (arrayList) {
            try {
                Iterator it = this.mCocktailBarList.iterator();
                while (it.hasNext()) {
                    if (!((String) it.next()).startsWith(str)
                            || (i3 != 4 && !MARsUtils.getScreenOnState())) {}
                    return 17;
                }
                return 0;
            } finally {
            }
        }
    }

    public final void getCocktailBarPackage() {
        String stringForUser =
                Settings.System.getStringForUser(
                        this.mContext.getContentResolver(),
                        "cocktail_bar_enabled_cocktails",
                        this.mContext.getUserId());
        if (stringForUser == null) {
            return;
        }
        synchronized (this.mCocktailBarList) {
            try {
                this.mCocktailBarList.clear();
                if (stringForUser.contains(";")) {
                    Collections.addAll(this.mCocktailBarList, stringForUser.split(";"));
                } else {
                    this.mCocktailBarList.add(stringForUser);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        if (!this.mRegisteredCocktailBarSettingsObserver) {
            this.mCocktailBarSettingsObserver =
                    new ContentObserver(
                            new Handler()) { // from class:
                                             // com.android.server.am.mars.filter.filter.EdgeAppFilter.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z, Uri uri) {
                            EdgeAppFilter.this.getCocktailBarPackage();
                        }
                    };
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("cocktail_bar_enabled_cocktails"),
                            false,
                            this.mCocktailBarSettingsObserver,
                            this.mContext.getUserId());
            this.mRegisteredCocktailBarSettingsObserver = true;
        }
        getCocktailBarPackage();
    }
}
