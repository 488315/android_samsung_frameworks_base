package com.android.server.notification;

import android.R;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Slog;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.notification.NotificationManagerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class GroupHelper {
    public final int mAutoGroupAtCount;
    public final NotificationManagerService.AnonymousClass2 mCallback;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final ArrayMap mUngroupedNotifications = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationAttributes {
        public final int flags;
        public final Icon icon;
        public final int iconColor;
        public final int visibility;

        public NotificationAttributes(int i, Icon icon, int i2, int i3) {
            this.flags = i;
            this.icon = icon;
            this.iconColor = i2;
            this.visibility = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NotificationAttributes)) {
                return false;
            }
            NotificationAttributes notificationAttributes = (NotificationAttributes) obj;
            return this.flags == notificationAttributes.flags && this.iconColor == notificationAttributes.iconColor && this.icon.sameAs(notificationAttributes.icon) && this.visibility == notificationAttributes.visibility;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.flags), Integer.valueOf(this.iconColor), this.icon, Integer.valueOf(this.visibility));
        }
    }

    public GroupHelper(Context context, PackageManager packageManager, int i, NotificationManagerService.AnonymousClass2 anonymousClass2) {
        this.mAutoGroupAtCount = i;
        this.mCallback = anonymousClass2;
        this.mContext = context;
        this.mPackageManager = packageManager;
    }

    public static String generatePackageKey(int i, String str) {
        return i + "|" + str;
    }

    public final NotificationAttributes getAutobundledSummaryAttributes(String str, List list) {
        Iterator it = ((ArrayList) list).iterator();
        Icon icon = null;
        Icon icon2 = null;
        boolean z = true;
        boolean z2 = true;
        int i = 1;
        int i2 = 0;
        while (it.hasNext()) {
            NotificationAttributes notificationAttributes = (NotificationAttributes) it.next();
            if (icon2 == null) {
                icon2 = notificationAttributes.icon;
            } else if (!icon2.sameAs(notificationAttributes.icon)) {
                z = false;
            }
            if (i == 1) {
                i = notificationAttributes.iconColor;
            } else if (i != notificationAttributes.iconColor) {
                z2 = false;
            }
            if (notificationAttributes.visibility == 1) {
                i2 = 1;
            }
        }
        if (!z) {
            try {
                Drawable applicationIcon = this.mPackageManager.getApplicationIcon(str);
                if ((applicationIcon instanceof AdaptiveIconDrawable) && ((AdaptiveIconDrawable) applicationIcon).getMonochrome() != null) {
                    icon = Icon.createWithResourceAdaptiveDrawable(str, ((AdaptiveIconDrawable) applicationIcon).getSourceDrawableResId(), true, AdaptiveIconDrawable.getExtraInsetFraction() * (-2.0f));
                }
            } catch (PackageManager.NameNotFoundException e) {
                Slog.e("GroupHelper", "Failed to getApplicationIcon() in getMonochromeAppIcon()", e);
            }
            icon2 = icon != null ? icon : Icon.createWithResource(this.mContext, R.drawable.ic_qs_auto_rotate);
        }
        if (!z2) {
            i = 0;
        }
        return new NotificationAttributes(0, icon2, i, i2);
    }

    public int getAutogroupSummaryFlags(ArrayMap arrayMap) {
        boolean z = arrayMap.size() > 0;
        int i = 0;
        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            if ((((NotificationAttributes) arrayMap.valueAt(i2)).flags & 16) == 0) {
                z = false;
            }
            if ((((NotificationAttributes) arrayMap.valueAt(i2)).flags & 34) != 0) {
                i |= ((NotificationAttributes) arrayMap.valueAt(i2)).flags & 34;
            }
        }
        return (z ? 16 : 0) | 1792 | i;
    }

    public int getNotGroupedByAppCount(int i, String str) {
        int size;
        synchronized (this.mUngroupedNotifications) {
            size = ((ArrayMap) this.mUngroupedNotifications.getOrDefault(generatePackageKey(i, str), new ArrayMap())).size();
        }
        return size;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:69:0x023c  */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r36v0, types: [com.android.server.notification.GroupHelper] */
    /* JADX WARN: Type inference failed for: r6v11, types: [android.os.PowerManager$WakeLock] */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v18 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean maybeGroup(android.service.notification.StatusBarNotification r37, boolean r38) {
        /*
            Method dump skipped, instructions count: 698
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.GroupHelper.maybeGroup(android.service.notification.StatusBarNotification, boolean):boolean");
    }

    public final void maybeUngroup(StatusBarNotification statusBarNotification, boolean z, int i) {
        boolean z2;
        boolean z3;
        boolean z4;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mUngroupedNotifications) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mUngroupedNotifications.getOrDefault(generatePackageKey(statusBarNotification.getUserId(), statusBarNotification.getPackageName()), new ArrayMap());
                if (arrayMap.size() == 0) {
                    return;
                }
                int i2 = -1;
                if (arrayMap.containsKey(statusBarNotification.getKey())) {
                    if ((((NotificationAttributes) arrayMap.remove(statusBarNotification.getKey())).flags & 34) != 0) {
                        i2 = getAutogroupSummaryFlags(arrayMap);
                        z4 = true;
                    } else {
                        z4 = false;
                    }
                    z2 = (z || statusBarNotification.getOverrideGroupKey() == null) ? false : true;
                    if (arrayMap.size() == 0) {
                        z3 = true;
                    } else {
                        arrayList.addAll(arrayMap.values());
                        z3 = false;
                    }
                } else {
                    z2 = false;
                    z3 = false;
                    z4 = false;
                }
                if (z3) {
                    NotificationManagerService.AnonymousClass2 anonymousClass2 = this.mCallback;
                    String packageName = statusBarNotification.getPackageName();
                    synchronized (NotificationManagerService.this.mNotificationLock) {
                        NotificationManagerService.this.clearAutogroupSummaryLocked(i, packageName);
                    }
                } else {
                    Icon smallIcon = statusBarNotification.getNotification().getSmallIcon();
                    int i3 = statusBarNotification.getNotification().color;
                    NotificationAttributes notificationAttributes = new NotificationAttributes(i2, smallIcon, i3, 0);
                    Flags.autogroupSummaryIconUpdate();
                    NotificationAttributes autobundledSummaryAttributes = getAutobundledSummaryAttributes(statusBarNotification.getPackageName(), arrayList);
                    Icon icon = autobundledSummaryAttributes.icon;
                    if (icon != null) {
                        smallIcon = icon;
                    }
                    int i4 = autobundledSummaryAttributes.iconColor;
                    if (i4 != 1) {
                        i3 = i4;
                    }
                    NotificationAttributes notificationAttributes2 = new NotificationAttributes(i2, smallIcon, i3, autobundledSummaryAttributes.visibility);
                    boolean equals = notificationAttributes2.equals(notificationAttributes);
                    if (!equals) {
                        notificationAttributes = notificationAttributes2;
                    }
                    boolean z5 = !equals;
                    if (z4 || z5) {
                        this.mCallback.updateAutogroupSummary(i, statusBarNotification.getPackageName(), notificationAttributes);
                    }
                }
                if (z2) {
                    NotificationManagerService.AnonymousClass2 anonymousClass22 = this.mCallback;
                    String key = statusBarNotification.getKey();
                    synchronized (NotificationManagerService.this.mNotificationLock) {
                        NotificationManagerService notificationManagerService = NotificationManagerService.this;
                        NotificationRecord notificationRecord = (NotificationRecord) notificationManagerService.mNotificationsByKey.get(key);
                        if (notificationRecord == null) {
                            HeimdAllFsService$$ExternalSyntheticOutline0.m("Failed to remove autogroup ", key, "NotificationService");
                        } else if (notificationRecord.sbn.getOverrideGroupKey() != null) {
                            NotificationManagerService.addAutoGroupAdjustment(notificationRecord, null);
                            EventLog.writeEvent(275534, key);
                            ((NotificationManagerService.RankingHandlerWorker) notificationManagerService.mRankingHandler).requestSort();
                        }
                    }
                }
            } finally {
            }
        }
    }

    public final boolean onNotificationPosted(StatusBarNotification statusBarNotification, boolean z) {
        boolean z2 = false;
        try {
            if (statusBarNotification.getNotification().extras.getInt("android.ongoingActivityNoti.style", 0) != 0) {
                maybeUngroup(statusBarNotification, false, statusBarNotification.getUserId());
            } else if (statusBarNotification.isAppGroup()) {
                maybeUngroup(statusBarNotification, false, statusBarNotification.getUserId());
            } else {
                z2 = maybeGroup(statusBarNotification, z);
            }
        } catch (Exception e) {
            Slog.e("GroupHelper", "Failure processing new notification", e);
        }
        return z2;
    }
}
