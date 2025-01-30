package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.android.server.am.mars.filter.IFilter;

/* loaded from: classes.dex */
public class AppCastFilter implements IFilter {
    public static final String TAG = "MARs:" + AppCastFilter.class.getSimpleName();
    public static final Uri URI_APP_CAST_ENABLED = Uri.parse("content://com.samsung.android.smartmirroring/app_cast_sent_result");
    public static final Uri URI_APP_CAST_PACKAGE = Uri.parse("content://com.samsung.android.smartmirroring/app_cast_sent_top_package");
    public ContentObserver mAppCastEnabledObserver;
    public String mAppCastPackage;
    public ContentObserver mAppCastPackageObserver;
    public Context mContext;
    public boolean mIsAppCastEnabled;

    public abstract class AppCastFilterHolder {
        public static final AppCastFilter INSTANCE = new AppCastFilter();
    }

    public AppCastFilter() {
        this.mAppCastEnabledObserver = null;
        this.mAppCastPackageObserver = null;
        this.mContext = null;
        this.mIsAppCastEnabled = false;
        this.mAppCastPackage = null;
    }

    public static AppCastFilter getInstance() {
        return AppCastFilterHolder.INSTANCE;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
        this.mContext = context;
        registerContentObserver();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
        unregisterContentObserver();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        String str2;
        return (this.mIsAppCastEnabled && (str2 = this.mAppCastPackage) != null && str2.equals(str)) ? 24 : 0;
    }

    public final void registerContentObserver() {
        if (this.mContext != null) {
            try {
                if (this.mAppCastEnabledObserver == null) {
                    this.mAppCastEnabledObserver = new ContentObserver(new Handler()) { // from class: com.android.server.am.mars.filter.filter.AppCastFilter.1
                        @Override // android.database.ContentObserver
                        public void onChange(boolean z, Uri uri) {
                            String valueFromSmartMirroring = AppCastFilter.this.getValueFromSmartMirroring(AppCastFilter.URI_APP_CAST_ENABLED);
                            if (valueFromSmartMirroring != null) {
                                AppCastFilter.this.mIsAppCastEnabled = Boolean.parseBoolean(valueFromSmartMirroring);
                            }
                        }
                    };
                    this.mContext.getContentResolver().registerContentObserver(URI_APP_CAST_ENABLED, false, this.mAppCastEnabledObserver);
                }
                if (this.mAppCastPackageObserver == null) {
                    this.mAppCastPackageObserver = new ContentObserver(new Handler()) { // from class: com.android.server.am.mars.filter.filter.AppCastFilter.2
                        @Override // android.database.ContentObserver
                        public void onChange(boolean z, Uri uri) {
                            AppCastFilter appCastFilter = AppCastFilter.this;
                            appCastFilter.mAppCastPackage = appCastFilter.getValueFromSmartMirroring(AppCastFilter.URI_APP_CAST_PACKAGE);
                        }
                    };
                    this.mContext.getContentResolver().registerContentObserver(URI_APP_CAST_PACKAGE, false, this.mAppCastPackageObserver);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void unregisterContentObserver() {
        Context context = this.mContext;
        if (context != null) {
            if (this.mAppCastEnabledObserver != null) {
                context.getContentResolver().unregisterContentObserver(this.mAppCastEnabledObserver);
                this.mAppCastEnabledObserver = null;
            }
            if (this.mAppCastPackageObserver != null) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mAppCastPackageObserver);
                this.mAppCastPackageObserver = null;
            }
        }
    }

    public final String getValueFromSmartMirroring(Uri uri) {
        Context context = this.mContext;
        if (context == null || uri == null) {
            return null;
        }
        return context.getContentResolver().getType(uri);
    }
}
