package com.android.server.people.data;

import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telecom.TelecomManager;

import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.telephony.SmsApplication;
import com.android.server.people.data.DataManager.CallLogContentObserver;
import com.android.server.people.data.DataManager.ContactsContentObserver;
import com.android.server.people.data.DataManager.MmsSmsContentObserver;
import com.android.server.people.data.DataManager.NotificationListener;
import com.android.server.people.data.DataManager.PerUserBroadcastReceiver;
import com.android.server.people.data.DataManager.PerUserPackageMonitor;
import com.android.server.people.data.DataManager.UsageStatsQueryRunnable;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DataManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DataManager f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DataManager$$ExternalSyntheticLambda0(
            DataManager dataManager, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = dataManager;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DataManager dataManager = this.f$0;
                int i = this.f$1;
                synchronized (dataManager.mLock) {
                    try {
                        UserData userData = (UserData) dataManager.mUserDataArray.get(i);
                        if (userData != null && !userData.mIsUnlocked) {
                            ContentResolver contentResolver =
                                    dataManager.mContext.getContentResolver();
                            if (dataManager.mUsageStatsQueryFutures.indexOfKey(i) >= 0) {
                                ((ScheduledFuture) dataManager.mUsageStatsQueryFutures.get(i))
                                        .cancel(true);
                            }
                            if (dataManager.mBroadcastReceivers.indexOfKey(i) >= 0) {
                                dataManager.mContext.unregisterReceiver(
                                        (BroadcastReceiver) dataManager.mBroadcastReceivers.get(i));
                            }
                            if (dataManager.mContactsContentObservers.indexOfKey(i) >= 0) {
                                contentResolver.unregisterContentObserver(
                                        (ContentObserver)
                                                dataManager.mContactsContentObservers.get(i));
                            }
                            if (dataManager.mNotificationListeners.indexOfKey(i) >= 0) {
                                try {
                                    ((DataManager.NotificationListener)
                                                    dataManager.mNotificationListeners.get(i))
                                            .unregisterAsSystemService();
                                } catch (RemoteException unused) {
                                }
                            }
                            if (dataManager.mPackageMonitors.indexOfKey(i) >= 0) {
                                ((PackageMonitor) dataManager.mPackageMonitors.get(i)).unregister();
                            }
                            if (i == 0) {
                                DataManager.CallLogContentObserver callLogContentObserver =
                                        dataManager.mCallLogContentObserver;
                                if (callLogContentObserver != null) {
                                    contentResolver.unregisterContentObserver(
                                            callLogContentObserver);
                                    dataManager.mCallLogContentObserver = null;
                                }
                                DataManager.MmsSmsContentObserver mmsSmsContentObserver =
                                        dataManager.mMmsSmsContentObserver;
                                if (mmsSmsContentObserver != null) {
                                    contentResolver.unregisterContentObserver(
                                            mmsSmsContentObserver);
                                    dataManager.mCallLogContentObserver = null;
                                }
                            }
                            Context context = dataManager.mContext;
                            int i2 = DataMaintenanceService.$r8$clinit;
                            ((JobScheduler) context.getSystemService(JobScheduler.class))
                                    .cancel(i + 204561367);
                            return;
                        }
                        return;
                    } finally {
                    }
                }
            default:
                DataManager dataManager2 = this.f$0;
                int i3 = this.f$1;
                synchronized (dataManager2.mLock) {
                    try {
                        UserData unlockedUserData = dataManager2.getUnlockedUserData(i3);
                        if (unlockedUserData == null) {
                            return;
                        }
                        unlockedUserData.loadUserData();
                        TelecomManager telecomManager =
                                (TelecomManager)
                                        dataManager2.mContext.getSystemService(
                                                TelecomManager.class);
                        unlockedUserData.mDefaultDialer =
                                telecomManager != null
                                        ? telecomManager.getDefaultDialerPackage(
                                                new UserHandle(unlockedUserData.mUserId))
                                        : null;
                        ComponentName defaultSmsApplicationAsUser =
                                SmsApplication.getDefaultSmsApplicationAsUser(
                                        dataManager2.mContext,
                                        false,
                                        UserHandle.of(unlockedUserData.mUserId));
                        unlockedUserData.mDefaultSmsApp =
                                defaultSmsApplicationAsUser != null
                                        ? defaultSmsApplicationAsUser.getPackageName()
                                        : null;
                        dataManager2.mUsageStatsQueryFutures.put(
                                i3,
                                dataManager2.mScheduledExecutor.scheduleAtFixedRate(
                                        dataManager2.new UsageStatsQueryRunnable(i3),
                                        1L,
                                        120L,
                                        TimeUnit.SECONDS));
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("android.telecom.action.DEFAULT_DIALER_CHANGED");
                        intentFilter.addAction(
                                "android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL");
                        if (dataManager2.mBroadcastReceivers.get(i3) == null) {
                            DataManager.PerUserBroadcastReceiver perUserBroadcastReceiver =
                                    dataManager2.new PerUserBroadcastReceiver(i3);
                            dataManager2.mBroadcastReceivers.put(i3, perUserBroadcastReceiver);
                            dataManager2.mContext.registerReceiverAsUser(
                                    perUserBroadcastReceiver,
                                    UserHandle.of(i3),
                                    intentFilter,
                                    null,
                                    null);
                        }
                        DataManager.ContactsContentObserver contactsContentObserver =
                                dataManager2
                                .new ContactsContentObserver(BackgroundThread.getHandler());
                        dataManager2.mContactsContentObservers.put(i3, contactsContentObserver);
                        dataManager2
                                .mContext
                                .getContentResolver()
                                .registerContentObserver(
                                        ContactsContract.Contacts.CONTENT_URI,
                                        true,
                                        contactsContentObserver,
                                        i3);
                        DataManager.NotificationListener notificationListener =
                                dataManager2.new NotificationListener(i3);
                        dataManager2.mNotificationListeners.put(i3, notificationListener);
                        try {
                            notificationListener.registerAsSystemService(
                                    dataManager2.mContext,
                                    new ComponentName(
                                            dataManager2.mContext, (Class<?>) DataManager.class),
                                    i3);
                        } catch (RemoteException unused2) {
                        }
                        if (dataManager2.mPackageMonitors.get(i3) == null) {
                            DataManager.PerUserPackageMonitor perUserPackageMonitor =
                                    dataManager2.new PerUserPackageMonitor();
                            perUserPackageMonitor.register(
                                    dataManager2.mContext, (Looper) null, UserHandle.of(i3), true);
                            dataManager2.mPackageMonitors.put(i3, perUserPackageMonitor);
                        }
                        if (i3 == 0) {
                            dataManager2.mCallLogContentObserver =
                                    dataManager2
                                    .new CallLogContentObserver(BackgroundThread.getHandler());
                            dataManager2
                                    .mContext
                                    .getContentResolver()
                                    .registerContentObserver(
                                            CallLog.CONTENT_URI,
                                            true,
                                            dataManager2.mCallLogContentObserver,
                                            0);
                            dataManager2.mMmsSmsContentObserver =
                                    dataManager2
                                    .new MmsSmsContentObserver(BackgroundThread.getHandler());
                            dataManager2
                                    .mContext
                                    .getContentResolver()
                                    .registerContentObserver(
                                            Telephony.MmsSms.CONTENT_URI,
                                            false,
                                            dataManager2.mMmsSmsContentObserver,
                                            0);
                        }
                        DataMaintenanceService.scheduleJob(dataManager2.mContext, i3);
                        return;
                    } finally {
                    }
                }
        }
    }
}
