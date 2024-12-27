package com.android.server.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.server.notification.NotificationComparator;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NotificationComparator implements Comparator {
    public final Context mContext;
    public String mDefaultPhoneApp;
    public final NotificationMessagingUtil mMessagingUtil;
    public final AnonymousClass1 mPhoneAppBroadcastReceiver;
    public final Object mStateLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.notification.NotificationComparator$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, final Intent intent) {
            BackgroundThread.getExecutor().execute(new Runnable() { // from class: com.android.server.notification.NotificationComparator$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationComparator.AnonymousClass1 anonymousClass1 = NotificationComparator.AnonymousClass1.this;
                    Intent intent2 = intent;
                    synchronized (NotificationComparator.this.mStateLock) {
                        NotificationComparator.this.mDefaultPhoneApp = intent2.getStringExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME");
                        Slog.d("NotificationComparator", "DefaultPhonApp Changed : " + NotificationComparator.this.mDefaultPhoneApp);
                    }
                }
            });
        }
    }

    public NotificationComparator(Context context) {
        Object obj = new Object();
        this.mStateLock = obj;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mContext = context;
        context.registerReceiver(anonymousClass1, new IntentFilter("android.telecom.action.DEFAULT_DIALER_CHANGED"));
        this.mMessagingUtil = new NotificationMessagingUtil(context, obj);
    }

    public static boolean isSystemMax(NotificationRecord notificationRecord) {
        if (notificationRecord.mImportance < 4) {
            return false;
        }
        String packageName = notificationRecord.sbn.getPackageName();
        return "android".equals(packageName) || Constants.SYSTEMUI_PACKAGE_NAME.equals(packageName);
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a2, code lost:
    
        if (r3 != 0) goto L46;
     */
    @Override // java.util.Comparator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int compare(java.lang.Object r9, java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationComparator.compare(java.lang.Object, java.lang.Object):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0051, code lost:
    
        if (java.util.Objects.equals(r0, r5.mDefaultPhoneApp) != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isImportantOngoing(com.android.server.notification.NotificationRecord r6) {
        /*
            r5 = this;
            int r0 = r6.mImportance
            r1 = 2
            r2 = 0
            if (r0 >= r1) goto L7
            return r2
        L7:
            android.service.notification.StatusBarNotification r0 = r6.sbn
            android.app.Notification r0 = r0.getNotification()
            java.lang.Class<android.app.Notification$CallStyle> r1 = android.app.Notification.CallStyle.class
            boolean r0 = r0.isStyle(r1)
            r1 = 1
            if (r0 == 0) goto L17
            return r1
        L17:
            android.service.notification.StatusBarNotification r0 = r6.sbn
            android.app.Notification r0 = r0.getNotification()
            boolean r0 = r0.isFgsOrUij()
            if (r0 != 0) goto L24
            return r2
        L24:
            java.lang.String r0 = "call"
            boolean r0 = r6.isCategory(r0)
            if (r0 == 0) goto L54
            android.service.notification.StatusBarNotification r0 = r6.sbn
            java.lang.String r0 = r0.getPackageName()
            java.lang.String r3 = r5.mDefaultPhoneApp
            if (r3 != 0) goto L4b
            android.content.Context r3 = r5.mContext
            java.lang.String r4 = "telecom"
            java.lang.Object r3 = r3.getSystemService(r4)
            android.telecom.TelecomManager r3 = (android.telecom.TelecomManager) r3
            if (r3 == 0) goto L48
            java.lang.String r3 = r3.getDefaultDialerPackage()
            goto L49
        L48:
            r3 = 0
        L49:
            r5.mDefaultPhoneApp = r3
        L4b:
            java.lang.String r5 = r5.mDefaultPhoneApp
            boolean r5 = java.util.Objects.equals(r0, r5)
            if (r5 == 0) goto L54
            goto L60
        L54:
            android.service.notification.StatusBarNotification r5 = r6.sbn
            android.app.Notification r5 = r5.getNotification()
            boolean r5 = r5.isMediaNotification()
            if (r5 == 0) goto L61
        L60:
            r2 = r1
        L61:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.notification.NotificationComparator.isImportantOngoing(com.android.server.notification.NotificationRecord):boolean");
    }
}
