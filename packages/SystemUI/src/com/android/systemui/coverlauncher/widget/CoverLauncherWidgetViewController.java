package com.android.systemui.coverlauncher.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class CoverLauncherWidgetViewController {
    public static final Companion Companion = new Companion(null);
    public static CoverLauncherWidgetViewController mController;
    public final HashMap appWidgetUpdating;
    public final Context mContext;
    public final Lazy mPackageUtils$delegate;

    public final class Companion {
        private Companion() {
        }

        public static CoverLauncherWidgetViewController getInstance(Context context) {
            if (CoverLauncherWidgetViewController.mController == null) {
                CoverLauncherWidgetViewController.mController = new CoverLauncherWidgetViewController(context);
            }
            CoverLauncherWidgetViewController coverLauncherWidgetViewController = CoverLauncherWidgetViewController.mController;
            Intrinsics.checkNotNull(coverLauncherWidgetViewController);
            return coverLauncherWidgetViewController;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CoverLauncherWidgetViewController(Context context) {
        this.mContext = context;
        HashMap hashMap = new HashMap();
        this.appWidgetUpdating = hashMap;
        this.mPackageUtils$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController$mPackageUtils$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new CoverLauncherPackageUtils(CoverLauncherWidgetViewController.this.mContext);
            }
        });
        hashMap.clear();
    }

    public final void updateAppWidget(boolean z) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.mContext);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, (Class<?>) CoverLauncherLargeWidgetProvider.class));
        if (!(appWidgetIds.length == 0)) {
            if (z) {
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.gridview);
            } else {
                new Thread(new CoverLauncherWidgetViewController$updateAppWidget$1(appWidgetIds, this, appWidgetManager)).start();
            }
        }
    }
}
