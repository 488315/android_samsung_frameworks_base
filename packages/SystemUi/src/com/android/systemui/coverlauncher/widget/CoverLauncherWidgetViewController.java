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
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetUtils;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverLauncherWidgetViewController {
    public static CoverLauncherWidgetViewController mController;
    public final HashMap appWidgetUpdating = new HashMap();
    public final Context mContext;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController$2 */
    public final class RunnableC12122 implements Runnable {
        public final /* synthetic */ int[] val$appWidgetIds;
        public final /* synthetic */ AppWidgetManager val$appWidgetManager;

        public RunnableC12122(int[] iArr, AppWidgetManager appWidgetManager) {
            this.val$appWidgetIds = iArr;
            this.val$appWidgetManager = appWidgetManager;
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i;
            boolean z = false;
            for (int i2 : this.val$appWidgetIds) {
                if (CoverLauncherWidgetViewController.this.appWidgetUpdating.get(Integer.valueOf(i2)) == null || !((Boolean) CoverLauncherWidgetViewController.this.appWidgetUpdating.get(Integer.valueOf(i2))).booleanValue()) {
                    CoverLauncherWidgetViewController.this.appWidgetUpdating.put(Integer.valueOf(i2), Boolean.TRUE);
                    int size = CoverLauncherWidgetUtils.getAppListFromDB(CoverLauncherWidgetViewController.this.mContext, true).size();
                    CoverLauncherWidgetViewController coverLauncherWidgetViewController = CoverLauncherWidgetViewController.this;
                    AppWidgetManager appWidgetManager = this.val$appWidgetManager;
                    coverLauncherWidgetViewController.getClass();
                    if (appWidgetManager.getAppWidgetInfo(i2) == null) {
                        i = -1;
                    } else {
                        int i3 = appWidgetManager.getAppWidgetInfo(i2).targetCellWidth;
                        int i4 = appWidgetManager.getAppWidgetInfo(i2).targetCellHeight;
                        i = (i3 == 4 && i4 == 2) ? 1 : (i3 == 2 && i4 == 2) ? 2 : 0;
                    }
                    if (i == -1) {
                        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("invalid appWidgetId : appWidgetInfo is null id=", i2, "CoverLauncherWidgetViewController");
                        CoverLauncherWidgetViewController.this.appWidgetUpdating.put(Integer.valueOf(i2), Boolean.FALSE);
                    } else {
                        Log.i("CoverLauncherWidgetViewController", "update cover appWidget " + i2 + ", type=" + i);
                        CoverLauncherWidgetViewController coverLauncherWidgetViewController2 = CoverLauncherWidgetViewController.this;
                        coverLauncherWidgetViewController2.getClass();
                        Context context = coverLauncherWidgetViewController2.mContext;
                        boolean isNightModeActive = context.getResources().getConfiguration().isNightModeActive();
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), i == 2 ? isNightModeActive ? size == 0 ? R.layout.widget_layout_none_2_2_night : R.layout.widget_layout_2_2_night : size == 0 ? R.layout.widget_layout_none_2_2 : R.layout.widget_layout_2_2 : i == 1 ? isNightModeActive ? size == 0 ? R.layout.widget_layout_none_4_2_night : R.layout.widget_layout_4_2_night : size == 0 ? R.layout.widget_layout_none_4_2 : R.layout.widget_layout_4_2 : size == 0 ? R.layout.widget_layout_none : size > 8 ? R.layout.widget_layout : R.layout.widget_layout_2line);
                        if (size > 0) {
                            Intent intent = new Intent(context, (Class<?>) CoverLauncherWidgetUtils.sRemoteViewsClassArray[i]);
                            intent.setData(Uri.parse(intent.toUri(1)));
                            intent.putExtra("widgetType", i);
                            intent.putExtra("appWidgetId", i2);
                            remoteViews.setRemoteAdapter(R.id.gridview, intent);
                            Intent intent2 = new Intent(context, (Class<?>) CoverLauncherWidgetUtils.sWidgetClassArray[i]);
                            intent2.setAction("action_launch_app");
                            intent2.putExtra("appWidgetId", i2);
                            remoteViews.setPendingIntentTemplate(R.id.gridview, PendingIntent.getBroadcast(context, i2 * 100, intent2, 301989888));
                            int i5 = 2;
                            if (i != 2) {
                                i5 = 4;
                                if (i != 1) {
                                    if (size >= 4) {
                                        size = 4;
                                    }
                                    i5 = size;
                                }
                            }
                            remoteViews.setInt(R.id.gridview, "setNumColumns", i5);
                            try {
                                GridView.class.getMethod("semEnableSelectZeroOnLastFocusTab", Boolean.TYPE);
                                remoteViews.setBoolean(R.id.gridview, "semEnableSelectZeroOnLastFocusTab", true);
                            } catch (NoSuchMethodException e) {
                                Log.i("CoverLauncherWidgetViewController", e + " semEnableSelectZeroOnLastFocusTab");
                            }
                        } else if (size == 0) {
                            Intent intent3 = new Intent(context, (Class<?>) CoverLauncherWidgetHelper.class);
                            intent3.setFlags(872415232);
                            intent3.putExtra("appWidgetId", i2);
                            ActivityOptions makeBasic = ActivityOptions.makeBasic();
                            makeBasic.setLaunchDisplayId(0);
                            remoteViews.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(context, i2 * 100, intent3, 167772160, makeBasic.toBundle()));
                        }
                        this.val$appWidgetManager.updateAppWidget(i2, remoteViews);
                        CoverLauncherWidgetViewController.this.appWidgetUpdating.put(Integer.valueOf(i2), Boolean.FALSE);
                        z = true;
                    }
                } else {
                    Log.i("CoverLauncherWidgetViewController", "skip update cover appWidget");
                }
            }
            if (z) {
                this.val$appWidgetManager.notifyAppWidgetViewDataChanged(this.val$appWidgetIds, R.id.gridview);
            }
        }
    }

    public CoverLauncherWidgetViewController(Context context) {
        this.mContext = context;
    }

    public static CoverLauncherWidgetViewController getInstance(Context context) {
        if (mController == null) {
            mController = new CoverLauncherWidgetViewController(context);
        }
        return mController;
    }

    public final void notifyAppWidgetViewDataChanged() {
        Context context = this.mContext;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        for (Class cls : CoverLauncherWidgetUtils.sWidgetClassArray) {
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, (Class<?>) cls));
            if (appWidgetIds.length > 0) {
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.gridview);
            }
        }
    }
}
