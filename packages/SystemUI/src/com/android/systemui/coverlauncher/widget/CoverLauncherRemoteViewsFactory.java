package com.android.systemui.coverlauncher.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetUtils;
import com.android.systemui.coverlauncher.utils.badge.BadgeUtils;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CoverLauncherRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    public ArrayList mAppList;
    public final int mAppWidgetId;
    public final BadgeUtils mBadgeUtils;
    public final Context mContext;
    public final ArrayList mItemList = new ArrayList();
    public NotificationListener mNotificationListener;
    public final CoverLauncherPackageUtils mPackageUtil;
    public final CoverLauncherWidgetUtils mWidgetUtil;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CoverLauncherRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        this.mAppWidgetId = intent.getIntExtra("appWidgetId", 0);
        CoverLauncherPackageUtils coverLauncherPackageUtils = new CoverLauncherPackageUtils(context);
        this.mPackageUtil = coverLauncherPackageUtils;
        this.mWidgetUtil = new CoverLauncherWidgetUtils(context);
        this.mAppList = coverLauncherPackageUtils.getAppListFromDB(true);
        BadgeUtils badgeUtils = new BadgeUtils(context);
        this.mBadgeUtils = badgeUtils;
        if (Settings.Secure.getInt(badgeUtils.mContext.getContentResolver(), "notification_badging", 0) != 0) {
            NotificationListener.Companion.getClass();
            NotificationListener notificationListener = new NotificationListener(context);
            notificationListener.mContext = context;
            try {
                notificationListener.unregisterAsSystemService();
                notificationListener.registerAsSystemService(context, new ComponentName(context, (Class<?>) NotificationListener.class), UserHandle.semGetMyUserId());
                notificationListener.mIsRegister = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mNotificationListener = notificationListener;
        }
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final int getCount() {
        return this.mAppList.size();
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final RemoteViews getLoadingView() {
        return new RemoteViews(this.mContext.getPackageName(), R.layout.widget_item_loading_layout);
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final RemoteViews getViewAt(int i) {
        return i >= this.mItemList.size() ? new RemoteViews(this.mContext.getPackageName(), R.layout.widget_item_loading_layout) : (RemoteViews) this.mItemList.get(i);
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final int getViewTypeCount() {
        return 1;
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final boolean hasStableIds() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x021e  */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v8, types: [int] */
    /* JADX WARN: Type inference failed for: r6v4, types: [android.database.Cursor, java.io.Closeable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initWidgetData() {
        /*
            Method dump skipped, instructions count: 749
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.coverlauncher.widget.CoverLauncherRemoteViewsFactory.initWidgetData():void");
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final void onCreate() {
        Log.i("CoverLauncherRemoteViewsFactory", "onCreate, id=" + this.mAppWidgetId);
        initWidgetData();
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final void onDataSetChanged() {
        Log.i("CoverLauncherRemoteViewsFactory", "onDataSetChanged, id=" + this.mAppWidgetId);
        initWidgetData();
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final void onDestroy() {
        NotificationListener notificationListener = this.mNotificationListener;
        if (notificationListener != null) {
            NotificationListener.Companion.getClass();
            try {
                notificationListener.unregisterAsSystemService();
                notificationListener.mIsRegister = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mNotificationListener = null;
        }
    }
}
