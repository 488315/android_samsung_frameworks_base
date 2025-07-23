package com.android.systemui.coverlauncher.widget;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
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
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CoverLauncherWidgetViewController$updateAppWidget$1 implements Runnable {
    public final /* synthetic */ int[] $appWidgetIds;
    public final /* synthetic */ AppWidgetManager $appWidgetManager;
    public final /* synthetic */ CoverLauncherWidgetViewController this$0;

    public CoverLauncherWidgetViewController$updateAppWidget$1(int[] iArr, CoverLauncherWidgetViewController coverLauncherWidgetViewController, AppWidgetManager appWidgetManager) {
        this.$appWidgetIds = iArr;
        this.this$0 = coverLauncherWidgetViewController;
        this.$appWidgetManager = appWidgetManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        boolean z2 = false;
        for (int i : this.$appWidgetIds) {
            if (this.this$0.appWidgetUpdating.get(Integer.valueOf(i)) != null) {
                Object obj = this.this$0.appWidgetUpdating.get(Integer.valueOf(i));
                obj.getClass();
                if (((Boolean) obj).booleanValue()) {
                    Log.i("CoverLauncherWidgetViewController", "skip update cover appWidget");
                }
            }
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
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setLaunchDisplayId(0);
                    remoteViews.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(coverLauncherWidgetViewController.mContext, i * 100, intent3, 167772160, makeBasic.toBundle()));
                    Log.i("CoverLauncherWidgetViewController", "Create remoteViews and set intent, id=" + i + ", size=" + size);
                    this.$appWidgetManager.updateAppWidget(i, remoteViews);
                    this.this$0.appWidgetUpdating.put(Integer.valueOf(i), Boolean.FALSE);
                    z2 = true;
                }
            }
            Log.i("CoverLauncherWidgetViewController", "Create remoteViews and set intent, id=" + i + ", size=" + size);
            this.$appWidgetManager.updateAppWidget(i, remoteViews);
            this.this$0.appWidgetUpdating.put(Integer.valueOf(i), Boolean.FALSE);
            z2 = true;
        }
        if (z2) {
            this.$appWidgetManager.notifyAppWidgetViewDataChanged(this.$appWidgetIds, R.id.gridview);
        }
    }
}
