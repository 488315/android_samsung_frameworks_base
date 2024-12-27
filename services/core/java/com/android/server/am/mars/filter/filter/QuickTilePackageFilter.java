package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class QuickTilePackageFilter implements IFilter {
    public ArrayList mActiveQuickTilePackages;
    public Context mContext;
    public AnonymousClass1 mQuickTileContentObserver;
    public boolean mRegisteredQuickTileContentObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class QuickTilePackageFilterHolder {
        public static final QuickTilePackageFilter INSTANCE;

        static {
            QuickTilePackageFilter quickTilePackageFilter = new QuickTilePackageFilter();
            quickTilePackageFilter.mContext = null;
            quickTilePackageFilter.mRegisteredQuickTileContentObserver = false;
            quickTilePackageFilter.mQuickTileContentObserver = null;
            quickTilePackageFilter.mActiveQuickTilePackages = new ArrayList();
            INSTANCE = quickTilePackageFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        try {
            if (this.mRegisteredQuickTileContentObserver) {
                this.mContext
                        .getContentResolver()
                        .unregisterContentObserver(this.mQuickTileContentObserver);
                this.mRegisteredQuickTileContentObserver = false;
            }
        } catch (IllegalArgumentException unused) {
            Slog.e(
                    "MARs:QuickTilePackageFilter",
                    "IllegalArgumentException occurred in unregisterContentObserver()");
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        Context context = this.mContext;
        if (context == null || i != context.getUserId()) {
            return 0;
        }
        synchronized (this.mActiveQuickTilePackages) {
            if (str != null) {
                try {
                    if (this.mActiveQuickTilePackages.contains(str)) {
                        return 21;
                    }
                } finally {
                }
            }
            return 0;
        }
    }

    public final void getActiveQuickTilePackages() {
        synchronized (this.mActiveQuickTilePackages) {
            this.mActiveQuickTilePackages.clear();
        }
        String string =
                Settings.Secure.getString(this.mContext.getContentResolver(), "sysui_qs_tiles");
        if (string == null || !string.contains(",")) {
            return;
        }
        for (String str : string.split(",")) {
            if (str != null && str.contains("custom")) {
                try {
                    String substring = str.substring(str.indexOf("(") + 1, str.indexOf("/"));
                    synchronized (this.mActiveQuickTilePackages) {
                        try {
                            if (!this.mActiveQuickTilePackages.contains(substring)) {
                                this.mActiveQuickTilePackages.add(substring);
                                if (MARsDebugConfig.DEBUG_FILTER) {
                                    Slog.d(
                                            "MARs:QuickTilePackageFilter",
                                            "getActiveQuickTilePackages: add"
                                                + " mActiveQuickTilePackages "
                                                    + substring);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } catch (Exception e) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            e,
                            "error occurred getActiveQuickTilePackages() ! ",
                            "MARs:QuickTilePackageFilter");
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.server.am.mars.filter.filter.QuickTilePackageFilter$1] */
    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        getActiveQuickTilePackages();
        if (this.mRegisteredQuickTileContentObserver) {
            return;
        }
        this.mQuickTileContentObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.android.server.am.mars.filter.filter.QuickTilePackageFilter.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        QuickTilePackageFilter.this.getActiveQuickTilePackages();
                    }
                };
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("sysui_qs_tiles"),
                        false,
                        this.mQuickTileContentObserver,
                        this.mContext.getUserId());
        this.mRegisteredQuickTileContentObserver = true;
    }
}
