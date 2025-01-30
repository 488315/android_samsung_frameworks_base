package com.android.p038wm.shell.freeform;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.samsung.android.multiwindow.SmartPopupViewUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SmartPopupViewService extends NotificationListenerService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SmartPopupViewPackageListObserver mSmartPopupViewPackageListObserver;
    public int mZenMode = 0;
    public C39951 mPackageRemovedReceiver = null;
    public final List mEnabledList = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SmartPopupViewPackageListObserver extends ContentObserver {
        public final Uri mSmartPopupViewPackageListUri;
        public final Uri mZenModeUri;

        public SmartPopupViewPackageListObserver() {
            super(null);
            Uri uriFor = Settings.Secure.getUriFor("floating_noti_package_list");
            this.mSmartPopupViewPackageListUri = uriFor;
            Uri uriFor2 = Settings.Global.getUriFor("zen_mode");
            this.mZenModeUri = uriFor2;
            loadingEnabledListFromDB();
            SmartPopupViewService.this.getContentResolver().registerContentObserver(uriFor, false, this, -1);
            int i = Settings.Global.getInt(SmartPopupViewService.this.getContentResolver(), "zen_mode", 0);
            if (SmartPopupViewService.this.mZenMode != i) {
                SmartPopupViewService.this.mZenMode = i;
            }
            SmartPopupViewService.this.getContentResolver().registerContentObserver(uriFor2, false, this, -1);
        }

        public final void loadingEnabledListFromDB() {
            List packageStrListFromDB = SmartPopupViewUtil.getPackageStrListFromDB(SmartPopupViewService.this.getContext());
            Iterator it = ((ArrayList) SmartPopupViewService.this.mEnabledList).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (!packageStrListFromDB.contains(str)) {
                    FreeformContainerManager.getInstance(SmartPopupViewService.this).f447mH.sendMessage(24, str);
                }
            }
            ((ArrayList) SmartPopupViewService.this.mEnabledList).clear();
            ((ArrayList) SmartPopupViewService.this.mEnabledList).addAll(packageStrListFromDB);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.mSmartPopupViewPackageListUri.equals(uri)) {
                loadingEnabledListFromDB();
            }
            if (this.mZenModeUri.equals(uri)) {
                int i = Settings.Global.getInt(SmartPopupViewService.this.getContentResolver(), "zen_mode", 0);
                SmartPopupViewService smartPopupViewService = SmartPopupViewService.this;
                if (smartPopupViewService.mZenMode != i) {
                    smartPopupViewService.mZenMode = i;
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.wm.shell.freeform.SmartPopupViewService$1] */
    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.i("FreeformContainer", "[SmartPopupViewService] onBind()");
        try {
            registerAsSystemService(this, new ComponentName(this, (Class<?>) SmartPopupViewService.class), -2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.mSmartPopupViewPackageListObserver = new SmartPopupViewPackageListObserver();
        this.mPackageRemovedReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.freeform.SmartPopupViewService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent2) {
                String action = intent2.getAction();
                boolean booleanExtra = intent2.getBooleanExtra("android.intent.extra.REPLACING", false);
                if (!"android.intent.action.PACKAGE_REMOVED".equals(action) || booleanExtra) {
                    return;
                }
                Uri data = intent2.getData();
                Objects.requireNonNull(data);
                String[] split = data.toString().split(":");
                for (String str : split) {
                }
                String str2 = split[1];
                if (str2 != null) {
                    SmartPopupViewService smartPopupViewService = SmartPopupViewService.this;
                    int i = SmartPopupViewService.$r8$clinit;
                    if (((ArrayList) smartPopupViewService.mEnabledList).contains(str2)) {
                        Log.i("FreeformContainer", "[SmartPopupViewService] mPackageRemovedReceiver remove : ".concat(str2));
                        ((ArrayList) SmartPopupViewService.this.mEnabledList).remove(str2);
                        SmartPopupViewUtil.putPackageStrListToDB(SmartPopupViewService.this.getContext(), SmartPopupViewService.this.mEnabledList);
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        registerReceiver(this.mPackageRemovedReceiver, intentFilter, 2);
        FreeformContainerManager.getInstance(this).f447mH.sendMessage(21);
        return super.onBind(intent);
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    @Override // android.service.notification.NotificationListenerService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        String packageName = statusBarNotification.getPackageName();
        Notification notification2 = statusBarNotification.getNotification();
        String key = statusBarNotification.getKey();
        Log.i("FreeformContainer", "[SmartPopupViewService] onNotificationPosted: " + statusBarNotification);
        NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
        rankingMap.getRanking(statusBarNotification.getKey(), ranking);
        boolean z = true;
        if ((this.mZenMode == 0 || (ranking.getSuppressedVisualEffects() & 16) == 0) ? false : true) {
            Log.d("FreeformContainer", "[SmartPopupViewService] SuppressedVisibleEffects now. mZenMode=" + Settings.Global.zenModeToString(this.mZenMode));
            return;
        }
        if (packageName == null || notification2 == null || key == null) {
            Log.w("FreeformContainer", "[SmartPopupViewService] isSmartPopupViewTarget: there is empty parameter");
        } else if (((ArrayList) this.mEnabledList).contains(packageName)) {
            PendingIntent pendingIntent = notification2.contentIntent;
            if (pendingIntent == null || !pendingIntent.isActivity()) {
                Objects.toString(pendingIntent);
            } else {
                String str = notification2.category;
                if (str == null || (!str.equals("progress") && !notification2.category.equals("service"))) {
                    if (notification2.isGroupSummary()) {
                        Log.w("FreeformContainer", "[SmartPopupViewService] isSmartPopupViewTarget: group summary notification is not target");
                    } else {
                        if (!notification2.isForegroundService()) {
                            if (notification2.isBubbleNotification()) {
                                Log.w("FreeformContainer", "[SmartPopupViewService] isSmartPopupViewTarget: Freeform notification is not target");
                            }
                            if (z) {
                                return;
                            }
                            FreeformContainerManager freeformContainerManager = FreeformContainerManager.getInstance(this);
                            freeformContainerManager.f447mH.sendMessage(23, new SmartPopupViewItem(freeformContainerManager.mContext, packageName, notification2, key));
                            return;
                        }
                        Log.w("FreeformContainer", "[SmartPopupViewService] isSmartPopupViewTarget: forgroundservice notification is not target");
                    }
                }
            }
        }
        z = false;
        if (z) {
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        Log.i("FreeformContainer", "[SmartPopupViewService] onNotificationRemoved: " + statusBarNotification);
        FreeformContainerManager.getInstance(this).f447mH.sendMessage(24, statusBarNotification.getPackageName());
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        Log.i("FreeformContainer", "[SmartPopupViewService] onUnbind()");
        getContentResolver().unregisterContentObserver(this.mSmartPopupViewPackageListObserver);
        unregisterReceiver(this.mPackageRemovedReceiver);
        FreeformContainerManager.getInstance(this).f447mH.sendMessage(22);
        try {
            unregisterAsSystemService();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return super.onUnbind(intent);
    }
}
