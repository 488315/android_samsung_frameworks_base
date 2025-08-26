package com.android.systemui.coverlauncher.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageInfo;
import com.android.systemui.coverlauncher.utils.CoverLauncherPackageUtils;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetUtils;
import com.android.systemui.coverlauncher.utils.badge.BadgeItem;
import com.android.systemui.coverlauncher.utils.badge.BadgeManager;
import com.android.systemui.coverlauncher.utils.badge.BadgeUtils;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener;
import java.io.IOException;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
    /* JADX WARN: Removed duplicated region for block: B:19:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x021e  */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v8, types: [int] */
    /* JADX WARN: Type inference failed for: r6v4, types: [android.database.Cursor, java.io.Closeable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initWidgetData() throws PackageManager.NameNotFoundException, IOException {
        Drawable drawableSemGetApplicationIconForIconTray;
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Bitmap bitmapCreateBitmap;
        boolean z2 = true;
        int i6 = 0;
        ArrayList appListFromDB = this.mPackageUtil.getAppListFromDB(false);
        this.mAppList = appListFromDB;
        int size = appListFromDB.size();
        this.mItemList.clear();
        int i7 = 0;
        while (i7 < size) {
            CoverLauncherPackageInfo coverLauncherPackageInfo = (CoverLauncherPackageInfo) this.mAppList.get(i7);
            String str = coverLauncherPackageInfo.packageName;
            int i8 = coverLauncherPackageInfo.profileId;
            Log.i("CoverLauncherRemoteViewsFactory", "createRemoteViews, packageName=" + str + ", id=" + this.mAppWidgetId);
            CoverLauncherWidgetUtils coverLauncherWidgetUtils = this.mWidgetUtil;
            coverLauncherWidgetUtils.getClass();
            RemoteViews remoteViews = new RemoteViews(coverLauncherWidgetUtils.mContext.getPackageName(), R.layout.widget_item_layout);
            CoverLauncherPackageUtils coverLauncherPackageUtils = new CoverLauncherPackageUtils(coverLauncherWidgetUtils.mContext);
            try {
                ApplicationInfo applicationInfoAsUser = coverLauncherPackageUtils.mPackageManager.getApplicationInfoAsUser(str, i6, i8);
                drawableSemGetApplicationIconForIconTray = coverLauncherPackageUtils.mPackageManager.semGetApplicationIconForIconTray(applicationInfoAsUser, 48);
                if ((applicationInfoAsUser.flags & 1073741824) != 0) {
                    drawableSemGetApplicationIconForIconTray.setColorFilter(CoverLauncherPackageUtils.getGrayFilter());
                }
            } catch (Exception e) {
                Log.e("CoverLauncherPackageUtils", CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(i8, "Failed to get Application Icon ", str, ", profileId : "), e);
                coverLauncherPackageUtils.tryUpdateAppWidget();
                drawableSemGetApplicationIconForIconTray = null;
            }
            if (drawableSemGetApplicationIconForIconTray != null) {
                CoverLauncherWidgetUtils.Companion.getClass();
                if (drawableSemGetApplicationIconForIconTray instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawableSemGetApplicationIconForIconTray;
                    if (bitmapDrawable.getBitmap() != null) {
                        bitmapCreateBitmap = bitmapDrawable.getBitmap();
                    } else {
                        if (drawableSemGetApplicationIconForIconTray.getIntrinsicWidth() <= 0 || drawableSemGetApplicationIconForIconTray.getIntrinsicHeight() <= 0) {
                            bitmapCreateBitmap = Bitmap.createBitmap(z2 ? 1 : 0, z2 ? 1 : 0, Bitmap.Config.ARGB_8888);
                            bitmapCreateBitmap.getClass();
                        } else {
                            bitmapCreateBitmap = Bitmap.createBitmap(drawableSemGetApplicationIconForIconTray.getIntrinsicWidth(), drawableSemGetApplicationIconForIconTray.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                            bitmapCreateBitmap.getClass();
                        }
                        Canvas canvas = new Canvas(bitmapCreateBitmap);
                        drawableSemGetApplicationIconForIconTray.setBounds(i6, i6, canvas.getWidth(), canvas.getHeight());
                        drawableSemGetApplicationIconForIconTray.draw(canvas);
                    }
                    int dimension = (int) coverLauncherWidgetUtils.mContext.getResources().getDimension(R.dimen.widget_launcher_item_icon_size);
                    Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, dimension, dimension, z2);
                    remoteViews.setImageViewBitmap(R.id.app_icon, bitmapCreateScaledBitmap.copy(Bitmap.Config.ARGB_8888, bitmapCreateScaledBitmap.isMutable()));
                }
            }
            Intent intent = new Intent(coverLauncherWidgetUtils.mContext, (Class<?>) CoverLauncherLargeWidgetProvider.class);
            intent.setAction("action_launch_app");
            Bundle bundle = new Bundle();
            bundle.putString("key_package_name", str);
            bundle.putInt("key_profile_id", i8);
            intent.putExtras(bundle);
            remoteViews.setOnClickFillInIntent(R.id.app_item, intent);
            String applicationLabel = this.mPackageUtil.getApplicationLabel(str);
            if (applicationLabel == null) {
                applicationLabel = "";
            }
            remoteViews.setTextViewText(R.id.app_title, applicationLabel);
            StringBuilder sb = new StringBuilder(applicationLabel);
            BadgeUtils badgeUtils = this.mBadgeUtils;
            badgeUtils.getClass();
            if ((Settings.Secure.getInt(badgeUtils.mContext.getContentResolver(), "notification_badging", i6) != 0 ? z2 ? 1 : 0 : i6) != 0) {
                BadgeManager.Companion.getClass();
                BadgeItem badgeItem = (BadgeItem) BadgeManager.Companion.getInstance().items.get(str + ":" + i8);
                if (badgeItem != null) {
                    Log.i("BadgeUtils", "packageName : " + str + ", badgeItem : " + badgeItem);
                    ?? Query = badgeUtils.mContext.getContentResolver().query(BadgeUtils.BADGE_URI, BadgeUtils.COLUMNS, "package = ?", new String[]{str}, null);
                    if (Query == 0) {
                        try {
                            Log.i("BadgeUtils", "Cursor is null");
                        } finally {
                        }
                    } else if (Query.getCount() <= 0) {
                        Log.i("BadgeUtils", "Cursor count is invalid");
                    } else {
                        i4 = i6;
                        ?? r2 = z2;
                        while (Query.moveToNext()) {
                            String string = Query.getString(i6);
                            String string2 = Query.getString(r2);
                            boolean z3 = r2;
                            int i9 = Query.getInt(2);
                            Log.i("BadgeUtils", "badge provider info, pkgName : " + string + ", className : " + string2 + ", badgeCount : " + i9);
                            i4 = i9;
                            r2 = z3;
                            i6 = 0;
                        }
                        z = r2;
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(Query, null);
                        i5 = badgeItem.mTotalCount;
                        i3 = 999;
                        if (i5 > 999) {
                            i5 = 999;
                        }
                        if (i5 >= i4) {
                            i4 = i5;
                        }
                        if (i4 <= 999) {
                            i3 = i4;
                        }
                    }
                    z = z2 ? 1 : 0;
                    i4 = i6;
                    CloseableKt.closeFinally(Query, null);
                    i5 = badgeItem.mTotalCount;
                    i3 = 999;
                    if (i5 > 999) {
                    }
                    if (i5 >= i4) {
                    }
                    if (i4 <= 999) {
                    }
                } else {
                    z = z2 ? 1 : 0;
                    i3 = 0;
                }
                if (i3 == 0) {
                    remoteViews.setViewVisibility(R.id.app_icon_badge, 8);
                    remoteViews.setViewVisibility(R.id.app_icon_badge_two_number, 8);
                    remoteViews.setViewVisibility(R.id.app_icon_badge_three_number, 8);
                    remoteViews.setViewVisibility(R.id.app_icon_dot_badge, 8);
                    i2 = R.id.app_icon;
                    i = 0;
                    remoteViews.setContentDescription(i2, sb);
                    this.mItemList.add(remoteViews);
                    i7++;
                    i6 = i;
                    z2 = z;
                } else {
                    i = 0;
                    if (Settings.Secure.getInt(badgeUtils.mContext.getContentResolver(), "badge_app_icon_type", 0) != 0) {
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
                    sb.append(badgeUtils.mContext.getString(R.string.notification_channel_alerts));
                    z = z;
                }
            } else {
                z = z2 ? 1 : 0;
                i = i6;
                remoteViews.setViewVisibility(R.id.app_icon_badge, 8);
                remoteViews.setViewVisibility(R.id.app_icon_badge_two_number, 8);
                remoteViews.setViewVisibility(R.id.app_icon_badge_three_number, 8);
                remoteViews.setViewVisibility(R.id.app_icon_dot_badge, 8);
            }
            i2 = R.id.app_icon;
            remoteViews.setContentDescription(i2, sb);
            this.mItemList.add(remoteViews);
            i7++;
            i6 = i;
            z2 = z;
        }
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final void onCreate() throws PackageManager.NameNotFoundException, IOException {
        Log.i("CoverLauncherRemoteViewsFactory", "onCreate, id=" + this.mAppWidgetId);
        initWidgetData();
    }

    @Override // android.widget.RemoteViewsService.RemoteViewsFactory
    public final void onDataSetChanged() throws PackageManager.NameNotFoundException, IOException {
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
