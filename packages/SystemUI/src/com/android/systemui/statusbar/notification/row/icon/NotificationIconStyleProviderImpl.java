package com.android.systemui.statusbar.notification.row.icon;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotifCollectionCache;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Collection;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationIconStyleProviderImpl implements NotificationIconStyleProvider, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final NotifCollectionCache cache;
    public final UserManager userManager;

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

    public NotificationIconStyleProviderImpl(UserManager userManager, DumpManager dumpManager) {
        this.userManager = userManager;
        dumpManager.registerNormalDumpable("NotificationIconStyleProviderImpl", this);
        this.cache = new NotifCollectionCache(0, 0L, null, 7, null);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("cache information:");
        asIndenting.increaseIndent();
        try {
            this.cache.dump(asIndenting, strArr);
        } finally {
            asIndenting.decreaseIndent();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider
    public final void purgeCache(Collection collection) {
        this.cache.purge(collection);
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider
    public final boolean shouldShowAppIcon(final Context context, final StatusBarNotification statusBarNotification) {
        return ((Boolean) this.cache.getOrFetch(statusBarNotification.getPackageName(), new Function1() { // from class: com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProviderImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                StatusBarNotification statusBarNotification2 = statusBarNotification;
                Context context2 = context;
                int i = NotificationIconStyleProviderImpl.$r8$clinit;
                Context packageContext = statusBarNotification2.getPackageContext(context2);
                packageContext.getClass();
                this.getClass();
                ApplicationInfo applicationInfo = packageContext.getApplicationInfo();
                boolean z = false;
                if (applicationInfo != null && (applicationInfo.flags & 1) != 0 && packageContext.getPackageManager().getLaunchIntentForPackage(applicationInfo.packageName, true) == null) {
                    z = true;
                }
                return Boolean.valueOf(!z);
            }
        })).booleanValue();
    }

    @Override // com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider
    public final boolean shouldShowWorkProfileBadge(Context context, StatusBarNotification statusBarNotification) {
        return this.userManager.isManagedProfile(statusBarNotification.getPackageContext(context).getUserId());
    }
}
