package com.android.wm.shell.desktopmode.education;

import android.app.usage.UsageStatsManager;
import android.content.Context;
import com.android.wm.shell.desktopmode.education.data.AppHandleEducationDatastoreRepository;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
