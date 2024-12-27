package com.android.systemui.coverlauncher.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.provider.Settings;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetUtils;
import com.android.systemui.coverlauncher.utils.badge.BadgeUtils;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class CoverLauncherRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    public ArrayList mAppList;
    public final int mAppWidgetId;
    public final BadgeUtils mBadgeUtils;
    public final Context mContext;
    public final ArrayList mItemList = new ArrayList();
    public NotificationListener mNotificationListener;
    public final CoverLauncherPackageUtils mPackageUtil;
    public final CoverLauncherWidgetUtils mWidgetUtil;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        ArrayList appListFromDB = this.mPackageUtil.getAppListFromDB(false);
        this.mAppList = appListFromDB;
        return appListFromDB.size();
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
        if (i >= getCount() || i >= this.mItemList.size()) {
            return null;
        }
        return (RemoteViews) this.mItemList.get(i);
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final int getViewTypeCount() {
        return 1;
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final void onCreate() {
        this.mAppList = this.mPackageUtil.getAppListFromDB(false);
        setItemData();
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final void onDataSetChanged() {
        KeyguardSecPatternView$$ExternalSyntheticOutline0.m(this.mAppWidgetId, "onDataSetChanged, id=", "CoverLauncherRemoteViewsFactory");
        this.mAppList = this.mPackageUtil.getAppListFromDB(false);
        setItemData();
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setItemData() {
        /*
            Method dump skipped, instructions count: 725
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.coverlauncher.widget.CoverLauncherRemoteViewsFactory.setItemData():void");
    }
}
