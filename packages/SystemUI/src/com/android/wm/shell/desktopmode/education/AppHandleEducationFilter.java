package com.android.wm.shell.desktopmode.education;

import android.app.usage.UsageStatsManager;
import android.content.Context;
import com.android.wm.shell.desktopmode.education.data.AppHandleEducationDatastoreRepository;

/* loaded from: classes3.dex */
public final class AppHandleEducationFilter {
    public final AppHandleEducationDatastoreRepository appHandleEducationDatastoreRepository;
    public final Context context;
    public final UsageStatsManager usageStatsManager;

    public AppHandleEducationFilter(Context context, AppHandleEducationDatastoreRepository appHandleEducationDatastoreRepository) {
        this.context = context;
        this.appHandleEducationDatastoreRepository = appHandleEducationDatastoreRepository;
        this.usageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
    }
}
