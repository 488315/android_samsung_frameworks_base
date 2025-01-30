package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class QuickTilePackageFilter implements IFilter {
    public ArrayList mActiveQuickTilePackages;
    public Context mContext;
    public ContentObserver mQuickTileContentObserver;
    public boolean mRegisteredQuickTileContentObserver;
    public static String TAG = "MARs:" + QuickTilePackageFilter.class.getSimpleName();
    public static String CUSTOM_PREFIX = "custom";

    public abstract class QuickTilePackageFilterHolder {
        public static final QuickTilePackageFilter INSTANCE = new QuickTilePackageFilter();
    }

    public QuickTilePackageFilter() {
        this.mContext = null;
        this.mRegisteredQuickTileContentObserver = false;
        this.mQuickTileContentObserver = null;
        this.mActiveQuickTilePackages = new ArrayList();
    }

    public static QuickTilePackageFilter getInstance() {
        return QuickTilePackageFilterHolder.INSTANCE;
    }

    public final void setContext(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
        setContext(context);
        getActiveQuickTilePackages();
        registerContentObserver();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
        unregisterContentObserver();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        Context context = this.mContext;
        if (context == null || i != context.getUserId()) {
            return 0;
        }
        synchronized (this.mActiveQuickTilePackages) {
            if (str != null) {
                if (this.mActiveQuickTilePackages.contains(str)) {
                    return 21;
                }
            }
            return 0;
        }
    }

    public final void getActiveQuickTilePackages() {
        synchronized (this.mActiveQuickTilePackages) {
            this.mActiveQuickTilePackages.clear();
        }
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), "sysui_qs_tiles");
        if (string == null || !string.contains(",")) {
            return;
        }
        for (String str : string.split(",")) {
            if (str != null && str.contains(CUSTOM_PREFIX)) {
                try {
                    String substring = str.substring(str.indexOf("(") + 1, str.indexOf("/"));
                    synchronized (this.mActiveQuickTilePackages) {
                        if (!this.mActiveQuickTilePackages.contains(substring)) {
                            this.mActiveQuickTilePackages.add(substring);
                            if (MARsDebugConfig.DEBUG_FILTER) {
                                Slog.d(TAG, "getActiveQuickTilePackages: add mActiveQuickTilePackages " + substring);
                            }
                        }
                    }
                } catch (Exception e) {
                    Slog.e(TAG, "error occurred getActiveQuickTilePackages() ! " + e);
                }
            }
        }
    }

    public final void registerContentObserver() {
        if (this.mRegisteredQuickTileContentObserver) {
            return;
        }
        this.mQuickTileContentObserver = new ContentObserver(new Handler()) { // from class: com.android.server.am.mars.filter.filter.QuickTilePackageFilter.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                QuickTilePackageFilter.this.getActiveQuickTilePackages();
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("sysui_qs_tiles"), false, this.mQuickTileContentObserver, this.mContext.getUserId());
        this.mRegisteredQuickTileContentObserver = true;
    }

    public final void unregisterContentObserver() {
        try {
            if (this.mRegisteredQuickTileContentObserver) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mQuickTileContentObserver);
                this.mRegisteredQuickTileContentObserver = false;
            }
        } catch (IllegalArgumentException unused) {
            Slog.e(TAG, "IllegalArgumentException occurred in unregisterContentObserver()");
        }
    }
}
