package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.android.server.am.mars.filter.IFilter;

public final class AppCastFilter implements IFilter {
    public static final Uri URI_APP_CAST_ENABLED =
            Uri.parse("content://com.samsung.android.smartmirroring/app_cast_sent_result");
    public static final Uri URI_APP_CAST_PACKAGE =
            Uri.parse("content://com.samsung.android.smartmirroring/app_cast_sent_top_package");
    public AnonymousClass1 mAppCastEnabledObserver;
    public String mAppCastPackage;
    public AnonymousClass1 mAppCastPackageObserver;
    public Context mContext;
    public boolean mIsAppCastEnabled;

    public abstract class AppCastFilterHolder {
        public static final AppCastFilter INSTANCE;

        static {
            AppCastFilter appCastFilter = new AppCastFilter();
            appCastFilter.mAppCastEnabledObserver = null;
            appCastFilter.mAppCastPackageObserver = null;
            appCastFilter.mContext = null;
            appCastFilter.mIsAppCastEnabled = false;
            appCastFilter.mAppCastPackage = null;
            INSTANCE = appCastFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {
        Context context = this.mContext;
        if (context != null) {
            if (this.mAppCastEnabledObserver != null) {
                context.getContentResolver()
                        .unregisterContentObserver(this.mAppCastEnabledObserver);
                this.mAppCastEnabledObserver = null;
            }
            if (this.mAppCastPackageObserver != null) {
                this.mContext
                        .getContentResolver()
                        .unregisterContentObserver(this.mAppCastPackageObserver);
                this.mAppCastPackageObserver = null;
            }
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        String str2;
        return (this.mIsAppCastEnabled && (str2 = this.mAppCastPackage) != null && str2.equals(str))
                ? 24
                : 0;
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.server.am.mars.filter.filter.AppCastFilter$1] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.android.server.am.mars.filter.filter.AppCastFilter$1] */
    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {
        this.mContext = context;
        if (context != null) {
            try {
                if (this.mAppCastEnabledObserver == null) {
                    final int i = 0;
                    this.mAppCastEnabledObserver =
                            new ContentObserver(
                                    this,
                                    new Handler()) { // from class:
                                                     // com.android.server.am.mars.filter.filter.AppCastFilter.1
                                public final /* synthetic */ AppCastFilter this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.database.ContentObserver
                                public final void onChange(boolean z, Uri uri) {
                                    switch (i) {
                                        case 0:
                                            AppCastFilter appCastFilter = this.this$0;
                                            Uri uri2 = AppCastFilter.URI_APP_CAST_ENABLED;
                                            Context context2 = appCastFilter.mContext;
                                            String type =
                                                    (context2 == null || uri2 == null)
                                                            ? null
                                                            : context2.getContentResolver()
                                                                    .getType(uri2);
                                            if (type != null) {
                                                this.this$0.mIsAppCastEnabled =
                                                        Boolean.parseBoolean(type);
                                                break;
                                            }
                                            break;
                                        default:
                                            AppCastFilter appCastFilter2 = this.this$0;
                                            Uri uri3 = AppCastFilter.URI_APP_CAST_PACKAGE;
                                            Context context3 = appCastFilter2.mContext;
                                            appCastFilter2.mAppCastPackage =
                                                    (context3 == null || uri3 == null)
                                                            ? null
                                                            : context3.getContentResolver()
                                                                    .getType(uri3);
                                            break;
                                    }
                                }
                            };
                    this.mContext
                            .getContentResolver()
                            .registerContentObserver(
                                    URI_APP_CAST_ENABLED, false, this.mAppCastEnabledObserver);
                }
                if (this.mAppCastPackageObserver == null) {
                    final int i2 = 1;
                    this.mAppCastPackageObserver =
                            new ContentObserver(
                                    this,
                                    new Handler()) { // from class:
                                                     // com.android.server.am.mars.filter.filter.AppCastFilter.1
                                public final /* synthetic */ AppCastFilter this$0;

                                {
                                    this.this$0 = this;
                                }

                                @Override // android.database.ContentObserver
                                public final void onChange(boolean z, Uri uri) {
                                    switch (i2) {
                                        case 0:
                                            AppCastFilter appCastFilter = this.this$0;
                                            Uri uri2 = AppCastFilter.URI_APP_CAST_ENABLED;
                                            Context context2 = appCastFilter.mContext;
                                            String type =
                                                    (context2 == null || uri2 == null)
                                                            ? null
                                                            : context2.getContentResolver()
                                                                    .getType(uri2);
                                            if (type != null) {
                                                this.this$0.mIsAppCastEnabled =
                                                        Boolean.parseBoolean(type);
                                                break;
                                            }
                                            break;
                                        default:
                                            AppCastFilter appCastFilter2 = this.this$0;
                                            Uri uri3 = AppCastFilter.URI_APP_CAST_PACKAGE;
                                            Context context3 = appCastFilter2.mContext;
                                            appCastFilter2.mAppCastPackage =
                                                    (context3 == null || uri3 == null)
                                                            ? null
                                                            : context3.getContentResolver()
                                                                    .getType(uri3);
                                            break;
                                    }
                                }
                            };
                    this.mContext
                            .getContentResolver()
                            .registerContentObserver(
                                    URI_APP_CAST_PACKAGE, false, this.mAppCastPackageObserver);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
