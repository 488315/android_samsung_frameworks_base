package com.android.systemui.coverlauncher.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageInfo;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetUtils;
import com.android.systemui.coverlauncher.utils.badge.BadgeItem;
import com.android.systemui.coverlauncher.utils.badge.BadgeManager;
import com.android.systemui.coverlauncher.utils.badge.BadgeUtils;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverLauncherRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    public ArrayList mAppList;
    public final int mAppWidgetId;
    public final BadgeUtils mBadgeUtils;
    public final Context mContext;
    public final ArrayList mItemList = new ArrayList();
    public NotificationListener mNotificationListener;
    public final CoverLauncherPackageUtils mPackageUtil;
    public final int mType;
    public final CoverLauncherWidgetUtils mWidgetUtil;

    public CoverLauncherRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        this.mAppWidgetId = intent.getIntExtra("appWidgetId", 0);
        this.mPackageUtil = new CoverLauncherPackageUtils(context);
        this.mWidgetUtil = new CoverLauncherWidgetUtils(context);
        BadgeUtils badgeUtils = new BadgeUtils(context);
        this.mBadgeUtils = badgeUtils;
        this.mType = intent.getIntExtra("widgetType", 0);
        if (Settings.Secure.getInt(badgeUtils.mContext.getContentResolver(), "notification_badging", 0) != 0) {
            NotificationListener notificationListener = new NotificationListener();
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
        ArrayList appListFromDB = CoverLauncherWidgetUtils.getAppListFromDB(this.mContext, false);
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
        if (i < getCount() && i < this.mItemList.size()) {
            return (RemoteViews) this.mItemList.get(i);
        }
        return null;
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
        this.mAppList = CoverLauncherWidgetUtils.getAppListFromDB(this.mContext, false);
        setItemData(this.mType);
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final void onDataSetChanged() {
        TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("onDataSetChanged, id="), this.mAppWidgetId, "CoverLauncherRemoteViewsFactory");
        this.mAppList = CoverLauncherWidgetUtils.getAppListFromDB(this.mContext, false);
        setItemData(this.mType);
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final void onDestroy() {
        NotificationListener notificationListener = this.mNotificationListener;
        if (notificationListener != null) {
            HashSet hashSet = NotificationListener.sBlockChannelSet;
            if (notificationListener != null) {
                try {
                    notificationListener.unregisterAsSystemService();
                    notificationListener.mIsRegister = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.mNotificationListener = null;
        }
    }

    public final void setItemData(int i) {
        Drawable drawable;
        int i2;
        int i3;
        Bitmap bitmap;
        int count = getCount();
        this.mItemList.clear();
        int i4 = 0;
        for (int i5 = 0; i5 < count; i5++) {
            CoverLauncherPackageInfo coverLauncherPackageInfo = (CoverLauncherPackageInfo) this.mAppList.get(i5);
            String str = coverLauncherPackageInfo.mPackageName;
            int i6 = coverLauncherPackageInfo.mProfileId;
            TooltipPopup$$ExternalSyntheticOutline0.m13m(AbstractC0950x8906c950.m92m("createRemoteViews, packageName=", str, ", type=", i, ", id="), this.mAppWidgetId, "CoverLauncherRemoteViewsFactory");
            Context context = this.mWidgetUtil.mContext;
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), i == 2 ? R.layout.widget_item_layout_2_2 : i == 1 ? context.getResources().getConfiguration().isNightModeActive() ? R.layout.widget_item_layout_4_2_night : R.layout.widget_item_layout_4_2 : R.layout.widget_item_layout);
            Intent intent = new Intent(context, (Class<?>) CoverLauncherWidgetUtils.sWidgetClassArray[i]);
            intent.setAction("action_launch_app");
            CoverLauncherPackageUtils coverLauncherPackageUtils = new CoverLauncherPackageUtils(context);
            PackageManager packageManager = coverLauncherPackageUtils.mPackageManager;
            try {
                ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str, i4, i6);
                drawable = packageManager.semGetApplicationIconForIconTray(applicationInfoAsUser, 48);
                if (drawable != null && (applicationInfoAsUser.flags & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0) {
                    drawable.setColorFilter(CoverLauncherPackageUtils.getGrayFilter());
                }
            } catch (Exception e) {
                Log.e("CoverLauncherPackageUtils", "Failed to get Application Icon " + str + ", profileId : " + i6, e);
                coverLauncherPackageUtils.tryUpdateAppWidget();
                drawable = null;
            }
            if (drawable != null) {
                if (drawable instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    if (bitmapDrawable.getBitmap() != null) {
                        bitmap = bitmapDrawable.getBitmap();
                        int dimension = (int) context.getResources().getDimension(R.dimen.widget_launcher_item_icon_size);
                        remoteViews.setImageViewBitmap(R.id.app_icon, Bitmap.createScaledBitmap(bitmap, dimension, dimension, true));
                    }
                }
                Bitmap createBitmap = (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) ? Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) : Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(i4, i4, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                bitmap = createBitmap;
                int dimension2 = (int) context.getResources().getDimension(R.dimen.widget_launcher_item_icon_size);
                remoteViews.setImageViewBitmap(R.id.app_icon, Bitmap.createScaledBitmap(bitmap, dimension2, dimension2, true));
            }
            Bundle bundle = new Bundle();
            bundle.putString("key_package_name", str);
            bundle.putInt("key_profile_id", i6);
            intent.putExtras(bundle);
            remoteViews.setOnClickFillInIntent(R.id.app_item, intent);
            String applicationLabel = this.mPackageUtil.getApplicationLabel(str);
            if (i != 2) {
                remoteViews.setTextViewText(R.id.app_title, applicationLabel);
            }
            StringBuilder sb = new StringBuilder(applicationLabel);
            BadgeUtils badgeUtils = this.mBadgeUtils;
            badgeUtils.getClass();
            Context context2 = badgeUtils.mContext;
            if ((Settings.Secure.getInt(context2.getContentResolver(), "notification_badging", i4) != 0 ? 1 : i4) != 0) {
                BadgeItem badgeItem = (BadgeItem) BadgeManager.getInstance().mItems.get(str + ":" + i6);
                if (badgeItem != null) {
                    Log.i("BadgeUtils", "packageName : " + str + ", badgeItem : " + badgeItem);
                    Cursor query = context2.getContentResolver().query(BadgeUtils.BADGE_URI, BadgeUtils.COLUMNS, "package = ?", new String[]{str}, null);
                    if (query == null) {
                        try {
                            Log.i("BadgeUtils", "Cursor is null");
                        } finally {
                        }
                    } else if (query.getCount() <= 0) {
                        Log.i("BadgeUtils", "Cursor count is invalid");
                    } else {
                        int i7 = 1;
                        int i8 = i4;
                        while (query.moveToNext()) {
                            String string = query.getString(i4);
                            String string2 = query.getString(i7);
                            int i9 = query.getInt(2);
                            Log.i("BadgeUtils", "badge provider info, pkgName : " + string + ", className : " + string2 + ", badgeCount : " + i9);
                            i4 = 0;
                            i7 = 1;
                            i8 = i9;
                        }
                        i4 = i8;
                    }
                    if (query != null) {
                        query.close();
                    }
                    i3 = Math.max(Math.min(badgeItem.mTotalCount, 999), i4);
                } else {
                    i3 = 0;
                }
                if (i3 == 0) {
                    remoteViews.setViewVisibility(R.id.app_icon_badge, 8);
                    remoteViews.setViewVisibility(R.id.app_icon_badge_two_number, 8);
                    remoteViews.setViewVisibility(R.id.app_icon_badge_three_number, 8);
                    remoteViews.setViewVisibility(R.id.app_icon_dot_badge, 8);
                    i2 = R.id.app_icon;
                    i4 = 0;
                    remoteViews.setContentDescription(i2, sb);
                    this.mItemList.add(remoteViews);
                } else {
                    if (Settings.Secure.getInt(context2.getContentResolver(), "badge_app_icon_type", 0) != 0) {
                        remoteViews.setViewVisibility(R.id.app_icon_badge, 8);
                        remoteViews.setViewVisibility(R.id.app_icon_badge_two_number, 8);
                        remoteViews.setViewVisibility(R.id.app_icon_badge_three_number, 8);
                        remoteViews.setViewVisibility(R.id.app_icon_dot_badge, 0);
                    } else {
                        remoteViews.setViewVisibility(R.id.app_icon_dot_badge, 8);
                        if (i3 > 99) {
                            remoteViews.setViewVisibility(R.id.app_icon_badge, 8);
                            remoteViews.setViewVisibility(R.id.app_icon_badge_two_number, 8);
                            remoteViews.setViewVisibility(R.id.app_icon_badge_three_number, 0);
                            remoteViews.setTextViewText(R.id.app_icon_badge_three_number, String.valueOf(i3));
                        } else if (i3 > 9) {
                            remoteViews.setViewVisibility(R.id.app_icon_badge, 8);
                            remoteViews.setViewVisibility(R.id.app_icon_badge_two_number, 0);
                            remoteViews.setViewVisibility(R.id.app_icon_badge_three_number, 8);
                            remoteViews.setTextViewText(R.id.app_icon_badge_two_number, String.valueOf(i3));
                        } else {
                            remoteViews.setViewVisibility(R.id.app_icon_badge, 0);
                            remoteViews.setViewVisibility(R.id.app_icon_badge_two_number, 8);
                            remoteViews.setViewVisibility(R.id.app_icon_badge_three_number, 8);
                            remoteViews.setTextViewText(R.id.app_icon_badge, String.valueOf(i3));
                        }
                    }
                    sb.append(i3);
                    sb.append(context2.getString(R.string.notification_channel_alerts));
                    i4 = 0;
                }
            } else {
                remoteViews.setViewVisibility(R.id.app_icon_badge, 8);
                remoteViews.setViewVisibility(R.id.app_icon_badge_two_number, 8);
                remoteViews.setViewVisibility(R.id.app_icon_badge_three_number, 8);
                remoteViews.setViewVisibility(R.id.app_icon_dot_badge, 8);
            }
            i2 = R.id.app_icon;
            remoteViews.setContentDescription(i2, sb);
            this.mItemList.add(remoteViews);
        }
    }
}
