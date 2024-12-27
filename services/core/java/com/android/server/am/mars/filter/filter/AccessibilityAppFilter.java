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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AccessibilityAppFilter implements IFilter {
    public Context mContext = null;
    public AnonymousClass1 mAccessibilityContentObserver = null;
    public boolean mRegisteredAccessibilityContentObserver = false;
    public final ArrayList mEnabledAccessibilityPackages = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class AccessibilityAppFilterHolder {
        public static final AccessibilityAppFilter INSTANCE = new AccessibilityAppFilter();
    }

    public final void addPackages(String str) {
        String[] split = str.split("/");
        if (split[0].length() > 0) {
            String str2 = split[0];
            synchronized (this.mEnabledAccessibilityPackages) {
                try {
                    if (!this.mEnabledAccessibilityPackages.contains(str2)) {
                        this.mEnabledAccessibilityPackages.add(str2);
                        if (MARsDebugConfig.DEBUG_FILTER) {
                            Slog.d("MARs:AccessibilityAppFilter", "getEnabledAccessibilityPackage: add mEnabledAccessibilityPackages " + str2);
                        }
                    }
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        synchronized (this.mEnabledAccessibilityPackages) {
            this.mEnabledAccessibilityPackages.clear();
        }
        try {
            if (this.mRegisteredAccessibilityContentObserver) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mAccessibilityContentObserver);
                this.mRegisteredAccessibilityContentObserver = false;
            }
        } catch (IllegalArgumentException unused) {
            Slog.e("MARs:AccessibilityAppFilter", "IllegalArgumentException occurred in unregisterContentObserver()");
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        ArrayList arrayList;
        Context context = this.mContext;
        if (context == null || i != context.getUserId() || str == null || (arrayList = this.mEnabledAccessibilityPackages) == null) {
            return 0;
        }
        synchronized (arrayList) {
            try {
                return this.mEnabledAccessibilityPackages.contains(str) ? 19 : 0;
            } finally {
            }
        }
    }

    public final void getEnabledAccessibilityPackage() {
        synchronized (this.mEnabledAccessibilityPackages) {
            this.mEnabledAccessibilityPackages.clear();
        }
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), "enabled_accessibility_services");
        if (string == null) {
            return;
        }
        if (!string.contains(":")) {
            if (string.contains("/")) {
                addPackages(string);
                return;
            }
            return;
        }
        for (String str : string.split(":")) {
            if (str != null && str.contains("/")) {
                addPackages(str);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.server.am.mars.filter.filter.AccessibilityAppFilter$1] */
    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        getEnabledAccessibilityPackage();
        if (this.mRegisteredAccessibilityContentObserver) {
            return;
        }
        this.mAccessibilityContentObserver = new ContentObserver(new Handler()) { // from class: com.android.server.am.mars.filter.filter.AccessibilityAppFilter.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                AccessibilityAppFilter.this.getEnabledAccessibilityPackage();
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("enabled_accessibility_services"), false, this.mAccessibilityContentObserver, this.mContext.getUserId());
        this.mRegisteredAccessibilityContentObserver = true;
    }
}
