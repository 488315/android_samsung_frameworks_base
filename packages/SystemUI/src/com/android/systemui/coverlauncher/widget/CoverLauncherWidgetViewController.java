package com.android.systemui.coverlauncher.widget;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.GridView;
import android.widget.RemoteViews;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetUtils;
import com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController;
import java.util.HashMap;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class CoverLauncherWidgetViewController {
    public static final Companion Companion = new Companion(null);
    public static CoverLauncherWidgetViewController mController;
    public final HashMap appWidgetUpdating;
    public final Context mContext;
    public final Lazy mPackageUtils$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static CoverLauncherWidgetViewController getInstance(Context context) {
            if (CoverLauncherWidgetViewController.mController == null) {
                CoverLauncherWidgetViewController.mController = new CoverLauncherWidgetViewController(context);
            }
            CoverLauncherWidgetViewController coverLauncherWidgetViewController = CoverLauncherWidgetViewController.mController;
            coverLauncherWidgetViewController.getClass();
            return coverLauncherWidgetViewController;
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController$updateAppWidget$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int[] $appWidgetIds;
        public final /* synthetic */ AppWidgetManager $appWidgetManager;
        public final /* synthetic */ CoverLauncherWidgetViewController this$0;

        public AnonymousClass1(int[] iArr, CoverLauncherWidgetViewController coverLauncherWidgetViewController, AppWidgetManager appWidgetManager) {
            this.$appWidgetIds = iArr;
            this.this$0 = coverLauncherWidgetViewController;
            this.$appWidgetManager = appWidgetManager;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0043  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void run() throws NoSuchMethodException, SecurityException {
            boolean z;
            boolean z2 = false;
            for (int i : this.$appWidgetIds) {
                if (this.this$0.appWidgetUpdating.get(Integer.valueOf(i)) != null) {
                    Object obj = this.this$0.appWidgetUpdating.get(Integer.valueOf(i));
                    obj.getClass();
                    if (((Boolean) obj).booleanValue()) {
                        Log.i("CoverLauncherWidgetViewController", "skip update cover appWidget");
                    } else {
                        this.this$0.appWidgetUpdating.put(Integer.valueOf(i), Boolean.TRUE);
                        int size = ((CoverLauncherPackageUtils) this.this$0.mPackageUtils$delegate.getValue()).getAppListFromDB(true).size();
                        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "update cover appWidget id=", "CoverLauncherWidgetViewController");
                        CoverLauncherWidgetViewController coverLauncherWidgetViewController = this.this$0;
                        coverLauncherWidgetViewController.getClass();
                        CoverLauncherWidgetUtils.Companion companion = CoverLauncherWidgetUtils.Companion;
                        Context context = coverLauncherWidgetViewController.mContext;
                        companion.getClass();
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), size == 0 ? R.layout.widget_layout_none : size > 8 ? R.layout.widget_layout : R.layout.widget_layout_2line);
                        if (size > 0) {
                            z = false;
                            Intent intent = new Intent(coverLauncherWidgetViewController.mContext, (Class<?>) CoverLauncherLargeRemoteViewService.class);
                            intent.setData(Uri.parse(intent.toUri(1)));
                            intent.putExtra("appWidgetId", i);
                            remoteViews.setRemoteAdapter(R.id.gridview, intent);
                            Intent intent2 = new Intent(coverLauncherWidgetViewController.mContext, (Class<?>) CoverLauncherLargeWidgetProvider.class);
                            intent2.setAction("action_launch_app");
                            intent2.putExtra("appWidgetId", i);
                            remoteViews.setPendingIntentTemplate(R.id.gridview, PendingIntent.getBroadcast(coverLauncherWidgetViewController.mContext, i * 100, intent2, 301989888));
                            remoteViews.setInt(R.id.gridview, "setNumColumns", 4);
                            try {
                                GridView.class.getMethod("semEnableSelectZeroOnLastFocusTab", Boolean.TYPE);
                                remoteViews.setBoolean(R.id.gridview, "semEnableSelectZeroOnLastFocusTab", true);
                                Unit unit = Unit.INSTANCE;
                            } catch (NoSuchMethodException e) {
                                Log.i("CoverLauncherWidgetViewController", e + " semEnableSelectZeroOnLastFocusTab");
                            }
                        } else {
                            z = false;
                            if (size == 0) {
                                Intent intent3 = new Intent(coverLauncherWidgetViewController.mContext, (Class<?>) CoverLauncherWidgetHelper.class);
                                intent3.setFlags(872415232);
                                intent3.putExtra("appWidgetId", i);
                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                activityOptionsMakeBasic.setLaunchDisplayId(0);
                                remoteViews.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(coverLauncherWidgetViewController.mContext, i * 100, intent3, 167772160, activityOptionsMakeBasic.toBundle()));
                            }
                            Log.i("CoverLauncherWidgetViewController", "Create remoteViews and set intent, id=" + i + ", size=" + size);
                            this.$appWidgetManager.updateAppWidget(i, remoteViews);
                            this.this$0.appWidgetUpdating.put(Integer.valueOf(i), Boolean.FALSE);
                            z2 = true;
                        }
                        Log.i("CoverLauncherWidgetViewController", "Create remoteViews and set intent, id=" + i + ", size=" + size);
                        this.$appWidgetManager.updateAppWidget(i, remoteViews);
                        this.this$0.appWidgetUpdating.put(Integer.valueOf(i), Boolean.FALSE);
                        z2 = true;
                    }
                }
            }
            if (z2) {
                this.$appWidgetManager.notifyAppWidgetViewDataChanged(this.$appWidgetIds, R.id.gridview);
            }
        }
    }

    public CoverLauncherWidgetViewController(Context context) {
        this.mContext = context;
        HashMap map = new HashMap();
        this.appWidgetUpdating = map;
        this.mPackageUtils$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CoverLauncherWidgetViewController.Companion companion = CoverLauncherWidgetViewController.Companion;
                return new CoverLauncherPackageUtils(this.f$0.mContext);
            }
        });
        map.clear();
    }

    public final void updateAppWidget(boolean z) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.mContext);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, (Class<?>) CoverLauncherLargeWidgetProvider.class));
        if (appWidgetIds.length == 0) {
            return;
        }
        if (z) {
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.gridview);
        } else {
            new Thread(new AnonymousClass1(appWidgetIds, this, appWidgetManager)).start();
        }
    }
}
