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
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.logging.PanelScreenShotBufferLogger;
import com.android.systemui.logging.PanelScreenShotBufferLogger$$ExternalSyntheticLambda0;
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
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.server.notification.NotificationHistoryImageProvider;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationListener extends NotificationListenerWithPlugins implements PipelineDumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final NotificationListener$$ExternalSyntheticLambda0 mDispatchRankingUpdateRunnable;
    public final Executor mMainExecutor;
    public final Set mNotifLogging;
    public final Set mNotifPackageCountLogging;
    public final List mNotificationHandlers;
    public final NotificationManager mNotificationManager;
    public final Deque mRankingMapQueue;
    public final ArrayList mSettingsListeners;
    public long mSkippingRankingUpdatesSince;
    public final SystemClock mSystemClock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface NotificationSettingsListener {
    }

    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda0] */
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
                ArrayList arrayList = (ArrayList) notificationListener.mNotificationHandlers;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((NotificationListener.NotificationHandler) obj).onNotificationRankingUpdate(rankingMap);
                }
            }
        };
        this.mSkippingRankingUpdatesSince = -1L;
        new HashSet();
        new HashSet();
        this.mContext = context;
        this.mNotificationManager = notificationManager;
        this.mSystemClock = systemClock;
        this.mMainExecutor = executor;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SystemUIAnalytics.NOTIFICATION_PREF_NAME, 0);
        if (sharedPreferences != null) {
            this.mNotifLogging = sharedPreferences.getStringSet(SystemUIAnalytics.STID_NOTIFICATION_POSTED_KEY, new HashSet());
            sharedPreferences.edit();
            this.mNotifPackageCountLogging = sharedPreferences.getStringSet(SystemUIAnalytics.STID_NOTIFICATION_POSTED_PACKAGE_AND_COUNT, new HashSet());
        }
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
                NotificationListener notificationListener = NotificationListener.this;
                int i = NotificationListenerWithPlugins.$r8$clinit;
                ((NotificationListenerController) obj).onListenerConnected(new NotificationListenerWithPlugins.AnonymousClass1());
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
        ArrayList arrayList = this.mPlugins;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            if (((NotificationListenerController) obj).onNotificationChannelModified(str, userHandle, notificationChannel, i)) {
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
                int i3 = i;
                ArrayList arrayList2 = (ArrayList) notificationListener.mNotificationHandlers;
                int size2 = arrayList2.size();
                int i4 = 0;
                while (i4 < size2) {
                    Object obj2 = arrayList2.get(i4);
                    i4++;
                    ((NotificationListener.NotificationHandler) obj2).onNotificationChannelModified(str2, userHandle2, notificationChannel2, i3);
                }
            }
        });
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        List<Notification.MessagingStyle.Message> messages;
        if (statusBarNotification != null) {
            ArrayList arrayList = this.mPlugins;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                if (((NotificationListenerController) obj).onNotificationPosted(statusBarNotification, rankingMap)) {
                    return;
                }
            }
            this.mMainExecutor.execute(new NotificationListener$$ExternalSyntheticLambda1(this, statusBarNotification, rankingMap, 1));
            final Uri uri = null;
            if (statusBarNotification.getKey().contains("smartcapture") && statusBarNotification.getId() == 5755) {
                PanelScreenShotLogger.INSTANCE.getClass();
                ArrayList arrayList2 = PanelScreenShotLogger.assembledLogs;
                arrayList2.clear();
                arrayList2.add("\n");
                Iterator it = ((LinkedHashMap) PanelScreenShotLogger.providers).values().iterator();
                while (it.hasNext()) {
                    arrayList2.addAll(((PanelScreenShotLogger.LogProvider) it.next()).gatherState());
                    arrayList2.add("\n");
                }
                PanelScreenShotLogger.INSTANCE.getClass();
                StringBuilder sb = new StringBuilder();
                IntProgressionIterator it2 = new IntRange(0, arrayList2.size() - 1).iterator();
                while (it2.hasNext) {
                    sb.append((String) arrayList2.get(it2.nextInt()));
                    sb.append("\n");
                }
                String sb2 = sb.toString();
                PanelScreenShotBufferLogger panelScreenShotBufferLogger = PanelScreenShotLogger.panelScreenShotBufferLogger;
                panelScreenShotBufferLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                PanelScreenShotBufferLogger$$ExternalSyntheticLambda0 panelScreenShotBufferLogger$$ExternalSyntheticLambda0 = new PanelScreenShotBufferLogger$$ExternalSyntheticLambda0();
                LogBuffer logBuffer = panelScreenShotBufferLogger.buffer;
                LogMessage obtain = logBuffer.obtain("PanelScreenShotLog", logLevel, panelScreenShotBufferLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).str2 = sb2;
                logBuffer.commit(obtain);
            }
            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY) {
                Context context = this.mContext;
                Notification notification2 = statusBarNotification.getNotification();
                if (notification2 != null) {
                    Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(context, notification2);
                    if ((recoverBuilder.getStyle() instanceof Notification.MessagingStyle) && (messages = ((Notification.MessagingStyle) recoverBuilder.getStyle()).getMessages()) != null && messages.size() > 0) {
                        Notification.MessagingStyle.Message message = (Notification.MessagingStyle.Message) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, messages);
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
                            int i2 = NotificationListener.$r8$clinit;
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
                            StringBuilder sb3 = new StringBuilder("uri= ");
                            sb3.append(uri.toString());
                            sb3.append(", image= ");
                            RecyclerView$$ExternalSyntheticOutline0.m(bArr.length, "NotificationListener", sb3);
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
            ArrayList arrayList = this.mPlugins;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                rankingMap = ((NotificationListenerController) obj).getCurrentRanking(rankingMap);
            }
            ((ConcurrentLinkedDeque) this.mRankingMapQueue).addLast(rankingMap);
            this.mMainExecutor.execute(this.mDispatchRankingUpdateRunnable);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(final StatusBarNotification statusBarNotification, final NotificationListenerService.RankingMap rankingMap, final int i) {
        if (statusBarNotification != null) {
            ArrayList arrayList = this.mPlugins;
            int size = arrayList.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationListener notificationListener = NotificationListener.this;
                            StatusBarNotification statusBarNotification2 = statusBarNotification;
                            NotificationListenerService.RankingMap rankingMap2 = rankingMap;
                            int i3 = i;
                            ArrayList arrayList2 = (ArrayList) notificationListener.mNotificationHandlers;
                            int size2 = arrayList2.size();
                            int i4 = 0;
                            while (i4 < size2) {
                                Object obj = arrayList2.get(i4);
                                i4++;
                                ((NotificationListener.NotificationHandler) obj).onNotificationRemoved(statusBarNotification2, rankingMap2, i3);
                            }
                        }
                    });
                    break;
                }
                Object obj = arrayList.get(i2);
                i2++;
                if (((NotificationListenerController) obj).onNotificationRemoved(statusBarNotification, rankingMap)) {
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
        ArrayList arrayList = this.mSettingsListeners;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            LegacyNotificationIconAreaControllerImpl legacyNotificationIconAreaControllerImpl = LegacyNotificationIconAreaControllerImpl.this;
            legacyNotificationIconAreaControllerImpl.mShowLowPriority = !z;
            legacyNotificationIconAreaControllerImpl.updateStatusBarIcons();
        }
    }

    public final void registerAsSystemService() {
        try {
            registerAsSystemService(this.mContext, new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName()), -1);
        } catch (RemoteException e) {
            Log.e("NotificationListener", "Unable to register notification listener", e);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        onNotificationRemoved(statusBarNotification, rankingMap, 0);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface NotificationHandler {
        void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap);

        void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap);

        void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i);

        void onNotificationsInitialized();

        default void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    }
}
