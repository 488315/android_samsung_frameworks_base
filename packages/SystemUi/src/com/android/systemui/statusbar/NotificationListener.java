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
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.NotificationListenerController;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins.C30841;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationListener extends NotificationListenerWithPlugins implements PipelineDumpable {
    public final Context mContext;
    public final NotificationListener$$ExternalSyntheticLambda1 mDispatchRankingUpdateRunnable;
    public final Executor mMainExecutor;
    public final Set mNotifLogging;
    public final List mNotificationHandlers;
    public final HandlerC25801 mNotificationHistoryHandler;
    public final SharedPreferences mNotificationKeySharedPrefs;
    public SharedPreferences.Editor mNotificationKeySharedPrefsEditor;
    public final NotificationManager mNotificationManager;
    public final Deque mRankingMapQueue;
    public final ArrayList mSettingsListeners;
    public long mSkippingRankingUpdatesSince;
    public final SystemClock mSystemClock;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface NotificationSettingsListener {
    }

    /* JADX WARN: Type inference failed for: r7v4, types: [com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r7v6, types: [com.android.systemui.statusbar.NotificationListener$1] */
    public NotificationListener(Context context, NotificationManager notificationManager, SystemClock systemClock, Executor executor, PluginManager pluginManager) {
        super(pluginManager);
        this.mNotificationHandlers = new ArrayList();
        this.mSettingsListeners = new ArrayList();
        this.mRankingMapQueue = new ConcurrentLinkedDeque();
        this.mDispatchRankingUpdateRunnable = new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                NotificationListener notificationListener = NotificationListener.this;
                NotificationListenerService.RankingMap rankingMap = (NotificationListenerService.RankingMap) ((ConcurrentLinkedDeque) notificationListener.mRankingMapQueue).pollFirst();
                if (rankingMap == null) {
                    Log.wtf("NotificationListener", "mRankingMapQueue was empty!");
                }
                if (!((ConcurrentLinkedDeque) notificationListener.mRankingMapQueue).isEmpty()) {
                    ((SystemClockImpl) notificationListener.mSystemClock).getClass();
                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
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
        this.mNotificationHistoryHandler = new Handler() { // from class: com.android.systemui.statusbar.NotificationListener.1
            /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
            /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
            @Override // android.os.Handler
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void handleMessage(Message message) {
                Uri uri;
                Bitmap bitmap;
                List<Notification.MessagingStyle.Message> messages;
                super.handleMessage(message);
                if (message.what != 100) {
                    return;
                }
                StatusBarNotification statusBarNotification = (StatusBarNotification) message.obj;
                NotificationListener notificationListener = NotificationListener.this;
                Context context2 = notificationListener.mContext;
                Notification notification2 = statusBarNotification.getNotification();
                notificationListener.getClass();
                byte[] bArr = null;
                if (notification2 != null) {
                    Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(context2, notification2);
                    if ((recoverBuilder.getStyle() instanceof Notification.MessagingStyle) && (messages = ((Notification.MessagingStyle) recoverBuilder.getStyle()).getMessages()) != null && messages.size() > 0) {
                        Notification.MessagingStyle.Message message2 = messages.get(messages.size() - 1);
                        if (message2.getDataUri() != null && message2.getDataMimeType() != null && message2.getDataMimeType().startsWith("image/")) {
                            uri = message2.getDataUri();
                            if (uri == null) {
                                NotificationListener notificationListener2 = NotificationListener.this;
                                notificationListener2.getClass();
                                try {
                                    Bitmap decodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(notificationListener2.mContext.getContentResolver(), uri));
                                    int width = decodeBitmap.getWidth();
                                    int height = decodeBitmap.getHeight();
                                    Context context3 = notificationListener2.mContext;
                                    double d = width;
                                    if (d > Math.round(context3.getResources().getDimensionPixelSize(R.dimen.subscreen_noti_detail_history_image_size_b5) * context3.getResources().getDisplayMetrics().density) * 1.0d) {
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
                                RecyclerView$$ExternalSyntheticOutline0.m46m(sb, bArr.length, "NotificationListener");
                                NotificationListener.this.mContext.getContentResolver().insert(NotificationHistoryImageProvider.CONTENT_URI, contentValues);
                                return;
                            }
                            return;
                        }
                    }
                }
                uri = null;
                if (uri == null) {
                }
            }
        };
        this.mContext = context;
        this.mNotificationManager = notificationManager;
        this.mSystemClock = systemClock;
        this.mMainExecutor = executor;
        SharedPreferences sharedPreferences = context.getSharedPreferences("notification_pref", 0);
        this.mNotificationKeySharedPrefs = sharedPreferences;
        if (sharedPreferences != null) {
            this.mNotifLogging = sharedPreferences.getStringSet("QPNS0002", new HashSet());
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
        int i = 1;
        this.mConnected = true;
        this.mPlugins.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.NotificationListenerWithPlugins$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationListenerWithPlugins notificationListenerWithPlugins = NotificationListenerWithPlugins.this;
                notificationListenerWithPlugins.getClass();
                ((NotificationListenerController) obj).onListenerConnected(notificationListenerWithPlugins.new C30841());
            }
        });
        StatusBarNotification[] activeNotifications = getActiveNotifications();
        if (activeNotifications == null) {
            Log.w("NotificationListener", "onListenerConnected unable to get active notifications.");
            return;
        }
        this.mMainExecutor.execute(new NotificationListener$$ExternalSyntheticLambda2(this, activeNotifications, getCurrentRanking(), i));
        onSilentStatusBarIconsVisibilityChanged(this.mNotificationManager.shouldHideSilentStatusBarIcons());
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationChannelModified(final String str, final UserHandle userHandle, final NotificationChannel notificationChannel, final int i) {
        boolean z;
        Iterator it = this.mPlugins.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (((NotificationListenerController) it.next()).onNotificationChannelModified(str, userHandle, notificationChannel, i)) {
                z = true;
                break;
            }
        }
        if (z) {
            return;
        }
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda3
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
        int i;
        boolean z;
        if (statusBarNotification != null) {
            Iterator it = this.mPlugins.iterator();
            while (true) {
                i = 0;
                if (!it.hasNext()) {
                    z = false;
                    break;
                } else if (((NotificationListenerController) it.next()).onNotificationPosted(statusBarNotification, rankingMap)) {
                    z = true;
                    break;
                }
            }
            if (z) {
                return;
            }
            this.mMainExecutor.execute(new NotificationListener$$ExternalSyntheticLambda2(this, statusBarNotification, rankingMap, i));
            if (statusBarNotification.getKey().contains("smartcapture") && statusBarNotification.getId() == 5755) {
                SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) ((SecPanelLogger) Dependency.get(SecPanelLogger.class));
                if (SecPanelLoggerImpl.DEBUG_MODE) {
                    secPanelLoggerImpl.getClass();
                    Log.d("SecPanelLogger", "GlobalScreenshot: Capture effect finished!");
                }
                secPanelLoggerImpl.writer.logPanel("PANEL_SCREEN_SHOT", "GlobalScreenshot: Capture effect finished!");
                PanelScreenShotLogger.INSTANCE.getClass();
                PanelScreenShotLogger.makeScreenShotLog();
            }
            this.mNotifLogging.add(statusBarNotification.getKey() + "|" + Integer.toHexString(statusBarNotification.getNotification().flags));
            SharedPreferences sharedPreferences = this.mNotificationKeySharedPrefs;
            if (sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                this.mNotificationKeySharedPrefsEditor = edit;
                edit.putStringSet("QPNS0002", this.mNotifLogging);
                this.mNotificationKeySharedPrefsEditor.apply();
            }
            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY) {
                obtainMessage(100, 0, 0, statusBarNotification).sendToTarget();
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
        boolean z;
        if (statusBarNotification != null) {
            Iterator it = this.mPlugins.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                } else if (((NotificationListenerController) it.next()).onNotificationRemoved(statusBarNotification, rankingMap)) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda0
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
            }
        }
        if (i == 25) {
            SystemUIAnalytics.sendEventLog("QPN001", "QPNE0027");
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onSilentStatusBarIconsVisibilityChanged(boolean z) {
        Iterator it = this.mSettingsListeners.iterator();
        while (it.hasNext()) {
            NotificationIconAreaController notificationIconAreaController = NotificationIconAreaController.this;
            notificationIconAreaController.mShowLowPriority = !z;
            notificationIconAreaController.updateStatusBarIcons();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface NotificationHandler {
        void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap);

        void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap);

        void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i);

        void onNotificationsInitialized();

        default void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    }
}
