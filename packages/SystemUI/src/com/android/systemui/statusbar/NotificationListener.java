package com.android.systemui.statusbar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.NotificationListenerController;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.domain.interactor.SilentNotificationStatusIconsVisibilityInteractor;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.phone.LegacyNotificationIconAreaControllerImpl;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins.AnonymousClass1;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.server.notification.NotificationHistoryImageProvider;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class NotificationListener extends NotificationListenerWithPlugins implements PipelineDumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final NotificationListener$$ExternalSyntheticLambda0 mDispatchRankingUpdateRunnable;
    public final Executor mMainExecutor;
    public final Set mNotifLogging;
    public final List mNotificationHandlers;
    public final SharedPreferences mNotificationKeySharedPrefs;
    public SharedPreferences.Editor mNotificationKeySharedPrefsEditor;
    public final NotificationManager mNotificationManager;
    public final Deque mRankingMapQueue;
    public final ArrayList mSettingsListeners;
    public long mSkippingRankingUpdatesSince;
    public final SystemClock mSystemClock;

    public interface NotificationSettingsListener {
    }

    public NotificationListener(Context context, NotificationManager notificationManager, SilentNotificationStatusIconsVisibilityInteractor silentNotificationStatusIconsVisibilityInteractor, SystemClock systemClock, Executor executor, PluginManager pluginManager) {
        super(pluginManager);
        this.mNotificationHandlers = new ArrayList();
        this.mSettingsListeners = new ArrayList();
        this.mRankingMapQueue = new ConcurrentLinkedDeque();
        this.mDispatchRankingUpdateRunnable = new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationListener notificationListener = NotificationListener.this;
                NotificationListenerService.RankingMap rankingMap = (NotificationListenerService.RankingMap) ((ConcurrentLinkedDeque) notificationListener.mRankingMapQueue).pollFirst();
                if (rankingMap == null) {
                    Log.wtf("NotificationListener", "mRankingMapQueue was empty!");
                }
                if (!((ConcurrentLinkedDeque) notificationListener.mRankingMapQueue).isEmpty()) {
                    long elapsedRealtime = notificationListener.mSystemClock.elapsedRealtime();
                    if (notificationListener.mSkippingRankingUpdatesSince == -1) {
                        notificationListener.mSkippingRankingUpdatesSince = elapsedRealtime;
                    }
                    if (elapsedRealtime - notificationListener.mSkippingRankingUpdatesSince < 500) {
                        return;
                    }
                }
                notificationListener.mSkippingRankingUpdatesSince = -1L;
                Iterator it = ((ArrayList) notificationListener.mNotificationHandlers).iterator();
                while (it.hasNext()) {
                    ((NotificationListener.NotificationHandler) it.next()).onNotificationRankingUpdate(rankingMap);
                }
            }
        };
        this.mSkippingRankingUpdatesSince = -1L;
        this.mNotifLogging = new HashSet();
        this.mContext = context;
        this.mNotificationManager = notificationManager;
        this.mSystemClock = systemClock;
        this.mMainExecutor = executor;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SystemUIAnalytics.NOTIFICATION_PREF_NAME, 0);
        this.mNotificationKeySharedPrefs = sharedPreferences;
        if (sharedPreferences != null) {
            this.mNotifLogging = sharedPreferences.getStringSet(SystemUIAnalytics.STID_NOTIFICATION_POSTED_KEY, new HashSet());
        }
    }

    public final void addNotificationHandler(NotificationHandler notificationHandler) {
        if (((ArrayList) this.mNotificationHandlers).contains(notificationHandler)) {
            throw new IllegalArgumentException("Listener is already added");
        }
        ((ArrayList) this.mNotificationHandlers).add(notificationHandler);
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.mNotificationHandlers, "notificationHandlers");
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onListenerConnected() {
        this.mConnected = true;
        this.mPlugins.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.NotificationListenerWithPlugins$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationListenerWithPlugins notificationListenerWithPlugins = NotificationListenerWithPlugins.this;
                notificationListenerWithPlugins.getClass();
                ((NotificationListenerController) obj).onListenerConnected(notificationListenerWithPlugins.new AnonymousClass1());
            }
        });
        StatusBarNotification[] activeNotifications = getActiveNotifications();
        if (activeNotifications == null) {
            Log.w("NotificationListener", "onListenerConnected unable to get active notifications.");
            return;
        }
        this.mMainExecutor.execute(new NotificationListener$$ExternalSyntheticLambda1(this, activeNotifications, getCurrentRanking(), 0));
        onSilentStatusBarIconsVisibilityChanged(this.mNotificationManager.shouldHideSilentStatusBarIcons());
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationChannelModified(final String str, final UserHandle userHandle, final NotificationChannel notificationChannel, final int i) {
        Iterator it = this.mPlugins.iterator();
        while (it.hasNext()) {
            if (((NotificationListenerController) it.next()).onNotificationChannelModified(str, userHandle, notificationChannel, i)) {
                return;
            }
        }
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                NotificationListener notificationListener = NotificationListener.this;
                String str2 = str;
                UserHandle userHandle2 = userHandle;
                NotificationChannel notificationChannel2 = notificationChannel;
                int i2 = i;
                Iterator it2 = ((ArrayList) notificationListener.mNotificationHandlers).iterator();
                while (it2.hasNext()) {
                    ((NotificationListener.NotificationHandler) it2.next()).onNotificationChannelModified(str2, userHandle2, notificationChannel2, i2);
                }
            }
        });
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        List<Notification.MessagingStyle.Message> messages;
        if (statusBarNotification != null) {
            Iterator it = this.mPlugins.iterator();
            while (it.hasNext()) {
                if (((NotificationListenerController) it.next()).onNotificationPosted(statusBarNotification, rankingMap)) {
                    return;
                }
            }
            this.mMainExecutor.execute(new NotificationListener$$ExternalSyntheticLambda1(this, statusBarNotification, rankingMap, 1));
            if (statusBarNotification.getKey().contains("smartcapture") && statusBarNotification.getId() == 5755) {
                PanelScreenShotLogger.INSTANCE.getClass();
                PanelScreenShotLogger.makeScreenShotLog();
            }
            this.mNotifLogging.add(statusBarNotification.getKey() + "|" + Integer.toHexString(statusBarNotification.getNotification().flags));
            SharedPreferences sharedPreferences = this.mNotificationKeySharedPrefs;
            if (sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                this.mNotificationKeySharedPrefsEditor = edit;
                edit.putStringSet(SystemUIAnalytics.STID_NOTIFICATION_POSTED_KEY, this.mNotifLogging);
                this.mNotificationKeySharedPrefsEditor.apply();
            }
            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY) {
                Context context = this.mContext;
                Notification notification2 = statusBarNotification.getNotification();
                final Uri uri = null;
                if (notification2 != null) {
                    Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(context, notification2);
                    if ((recoverBuilder.getStyle() instanceof Notification.MessagingStyle) && (messages = ((Notification.MessagingStyle) recoverBuilder.getStyle()).getMessages()) != null && messages.size() > 0) {
                        Notification.MessagingStyle.Message message = (Notification.MessagingStyle.Message) PrioritySet$$ExternalSyntheticOutline0.m(1, messages);
                        if (message.getDataUri() != null && message.getDataMimeType() != null && message.getDataMimeType().startsWith("image/")) {
                            uri = message.getDataUri();
                        }
                    }
                }
                if (uri != null) {
                    new Thread(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Bitmap bitmap;
                            NotificationListener notificationListener = NotificationListener.this;
                            Uri uri2 = uri;
                            int i = NotificationListener.$r8$clinit;
                            notificationListener.getClass();
                            byte[] bArr = null;
                            try {
                                Bitmap decodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(notificationListener.mContext.getContentResolver(), uri2));
                                int width = decodeBitmap.getWidth();
                                int height = decodeBitmap.getHeight();
                                Context context2 = notificationListener.mContext;
                                double d = width;
                                if (d > Math.round(context2.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_history_image_size_b5) * context2.getResources().getDisplayMetrics().density) * 1.0d) {
                                    double round = Math.round((d / r5) * 100.0d) / 100.0d;
                                    width = (int) (d / round);
                                    height = (int) (height / round);
                                }
                                bitmap = Bitmap.createScaledBitmap(decodeBitmap, width, height, true);
                            } catch (Exception e) {
                                e.printStackTrace();
                                bitmap = null;
                            }
                            if (bitmap != null) {
                                try {
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.WEBP, 50, byteArrayOutputStream);
                                    bArr = byteArrayOutputStream.toByteArray();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                            if (bArr == null || bArr.length == 0) {
                                return;
                            }
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("uri_id", uri.toString());
                            contentValues.put("image", bArr);
                            contentValues.put("time", Long.valueOf(System.currentTimeMillis()));
                            StringBuilder sb = new StringBuilder("uri= ");
                            sb.append(uri.toString());
                            sb.append(", image= ");
                            RecyclerView$$ExternalSyntheticOutline0.m(bArr.length, "NotificationListener", sb);
                            NotificationListener.this.mContext.getContentResolver().insert(NotificationHistoryImageProvider.CONTENT_URI, contentValues);
                        }
                    }).start();
                }
            }
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        if (rankingMap != null) {
            Iterator it = this.mPlugins.iterator();
            while (it.hasNext()) {
                rankingMap = ((NotificationListenerController) it.next()).getCurrentRanking(rankingMap);
            }
            ((ConcurrentLinkedDeque) this.mRankingMapQueue).addLast(rankingMap);
            this.mMainExecutor.execute(this.mDispatchRankingUpdateRunnable);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(final StatusBarNotification statusBarNotification, final NotificationListenerService.RankingMap rankingMap, final int i) {
        if (statusBarNotification != null) {
            Iterator it = this.mPlugins.iterator();
            while (true) {
                if (!it.hasNext()) {
                    this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationListener notificationListener = NotificationListener.this;
                            StatusBarNotification statusBarNotification2 = statusBarNotification;
                            NotificationListenerService.RankingMap rankingMap2 = rankingMap;
                            int i2 = i;
                            Iterator it2 = ((ArrayList) notificationListener.mNotificationHandlers).iterator();
                            while (it2.hasNext()) {
                                ((NotificationListener.NotificationHandler) it2.next()).onNotificationRemoved(statusBarNotification2, rankingMap2, i2);
                            }
                        }
                    });
                    break;
                } else if (((NotificationListenerController) it.next()).onNotificationRemoved(statusBarNotification, rankingMap)) {
                    break;
                }
            }
        }
        if (i == 25) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_MAXIMUM_NUMBER_REACHED);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onSilentStatusBarIconsVisibilityChanged(boolean z) {
        int i = NotificationIconContainerRefactor.$r8$clinit;
        Iterator it = this.mSettingsListeners.iterator();
        while (it.hasNext()) {
            LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl = LegacyNotificationIconAreaControllerImpl.this;
            legacyNotificationIconAreaControllerImpl.mShowLowPriority = !z;
            legacyNotificationIconAreaControllerImpl.updateStatusBarIcons();
        }
    }

    public final void registerAsSystemService() {
        try {
            registerAsSystemService(this.mContext, new ComponentName(this.mContext.getPackageName(), NotificationListener.class.getCanonicalName()), -1);
        } catch (RemoteException e) {
            Log.e("NotificationListener", "Unable to register notification listener", e);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        onNotificationRemoved(statusBarNotification, rankingMap, 0);
    }

    public interface NotificationHandler {
        void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap);

        void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap);

        void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i);

        void onNotificationsInitialized();

        default void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    }
}
