package android.service.notification;

import android.annotation.SystemApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import com.android.internal.os.SomeArgs;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public abstract class NotificationAssistantService extends NotificationListenerService {
    public static final String ACTION_NOTIFICATION_ASSISTANT_DETAIL_SETTINGS = "android.service.notification.action.NOTIFICATION_ASSISTANT_DETAIL_SETTINGS";
    public static final String ACTION_NOTIFICATION_ASSISTANT_FEEDBACK_SETTINGS = "android.service.notification.action.NOTIFICATION_ASSISTANT_FEEDBACK_SETTINGS";
    public static final String EXTRA_NOTIFICATION_ADJUSTMENT = "android.service.notification.extra.NOTIFICATION_ADJUSTMENT";
    public static final String EXTRA_NOTIFICATION_KEY = "android.service.notification.extra.NOTIFICATION_KEY";
    public static final String FEEDBACK_RATING = "feedback.rating";
    public static final String SERVICE_INTERFACE = "android.service.notification.NotificationAssistantService";
    public static final int SOURCE_FROM_APP = 0;
    public static final int SOURCE_FROM_ASSISTANT = 1;
    private static final String TAG = "NotificationAssistants";
    protected Handler mHandler;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    public void onActionInvoked(String str, Notification.Action action, int i) {
    }

    public void onAllowedAdjustmentsChanged() {
    }

    public void onNotificationClicked(String str) {
    }

    public void onNotificationDirectReplied(String str) {
    }

    public abstract Adjustment onNotificationEnqueued(StatusBarNotification statusBarNotification);

    public void onNotificationExpansionChanged(String str, boolean z, boolean z2) {
    }

    public void onNotificationFeedbackReceived(String str, NotificationListenerService.RankingMap rankingMap, Bundle bundle) {
    }

    public abstract void onNotificationSnoozedUntilContext(StatusBarNotification statusBarNotification, String str);

    public void onNotificationVisibilityChanged(String str, boolean z) {
    }

    public void onNotificationsSeen(List<String> list) {
    }

    public void onPanelHidden() {
    }

    public void onPanelRevealed(int i) {
    }

    public void onSuggestedReplySent(String str, CharSequence charSequence, int i) {
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new MyHandler(getContext().getMainLooper());
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public final IBinder onBind(Intent intent) {
        if (this.mWrapper == null) {
            this.mWrapper = new NotificationAssistantServiceWrapper();
        }
        return this.mWrapper;
    }

    public Adjustment onNotificationEnqueued(StatusBarNotification statusBarNotification, NotificationChannel notificationChannel) {
        return onNotificationEnqueued(statusBarNotification);
    }

    public Adjustment onNotificationEnqueued(StatusBarNotification statusBarNotification, NotificationChannel notificationChannel, NotificationListenerService.RankingMap rankingMap) {
        return onNotificationEnqueued(statusBarNotification, notificationChannel);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, NotificationStats notificationStats, int i) {
        onNotificationRemoved(statusBarNotification, rankingMap, i);
    }

    public final void adjustNotification(Adjustment adjustment) {
        if (isBound()) {
            try {
                setAdjustmentIssuer(adjustment);
                getNotificationInterface().applyEnqueuedAdjustmentFromAssistant(this.mWrapper, adjustment);
            } catch (RemoteException e) {
                Log.v(TAG, "Unable to contact notification manager", e);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public final void adjustNotifications(List<Adjustment> list) {
        if (isBound()) {
            try {
                Iterator<Adjustment> it = list.iterator();
                while (it.hasNext()) {
                    setAdjustmentIssuer(it.next());
                }
                getNotificationInterface().applyAdjustmentsFromAssistant(this.mWrapper, list);
            } catch (RemoteException e) {
                Log.v(TAG, "Unable to contact notification manager", e);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public final void unsnoozeNotification(String str) {
        if (isBound()) {
            try {
                getNotificationInterface().unsnoozeNotificationFromAssistant(this.mWrapper, str);
            } catch (RemoteException e) {
                Log.v(TAG, "Unable to contact notification manager", e);
            }
        }
    }

    public final void setAdjustmentTypeSupportedState(String str, boolean z) {
        if (isBound()) {
            try {
                getNotificationInterface().setAdjustmentTypeSupportedState(this.mWrapper, str, z);
            } catch (RemoteException e) {
                Log.v(TAG, "Unable to contact notification manager", e);
            }
        }
    }

    private class NotificationAssistantServiceWrapper extends NotificationListenerService.NotificationListenerWrapper {
        private NotificationAssistantServiceWrapper() {
            super();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationEnqueuedWithChannel(IStatusBarNotificationHolder iStatusBarNotificationHolder, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) {
            try {
                StatusBarNotification statusBarNotification = iStatusBarNotificationHolder.get();
                if (statusBarNotification == null) {
                    Log.w(NotificationAssistantService.TAG, "onNotificationEnqueuedWithChannel: Error receiving StatusBarNotification");
                } else {
                    onNotificationEnqueuedWithChannelFull(statusBarNotification, notificationChannel, notificationRankingUpdate);
                }
            } catch (RemoteException e) {
                Log.w(NotificationAssistantService.TAG, "onNotificationEnqueued: Error receiving StatusBarNotification", e);
            }
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationEnqueuedWithChannelFull(StatusBarNotification statusBarNotification, NotificationChannel notificationChannel, NotificationRankingUpdate notificationRankingUpdate) {
            NotificationAssistantService.this.applyUpdateLocked(notificationRankingUpdate);
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = statusBarNotification;
            someArgsObtain.arg2 = notificationChannel;
            someArgsObtain.arg3 = NotificationAssistantService.this.getCurrentRanking();
            NotificationAssistantService.this.mHandler.obtainMessage(1, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationSnoozedUntilContext(IStatusBarNotificationHolder iStatusBarNotificationHolder, String str) {
            try {
                StatusBarNotification statusBarNotification = iStatusBarNotificationHolder.get();
                if (statusBarNotification == null) {
                    Log.w(NotificationAssistantService.TAG, "onNotificationSnoozed: Error receiving StatusBarNotification");
                } else {
                    onNotificationSnoozedUntilContextFull(statusBarNotification, str);
                }
            } catch (RemoteException e) {
                Log.w(NotificationAssistantService.TAG, "onNotificationSnoozed: Error receiving StatusBarNotification", e);
            }
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationSnoozedUntilContextFull(StatusBarNotification statusBarNotification, String str) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = statusBarNotification;
            someArgsObtain.arg2 = str;
            NotificationAssistantService.this.mHandler.obtainMessage(2, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationsSeen(List<String> list) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = list;
            NotificationAssistantService.this.mHandler.obtainMessage(3, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onPanelRevealed(int i) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.argi1 = i;
            NotificationAssistantService.this.mHandler.obtainMessage(9, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onPanelHidden() {
            NotificationAssistantService.this.mHandler.obtainMessage(10, SomeArgs.obtain()).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationVisibilityChanged(String str, boolean z) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.argi1 = z ? 1 : 0;
            NotificationAssistantService.this.mHandler.obtainMessage(11, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationExpansionChanged(String str, boolean z, boolean z2) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.argi1 = z ? 1 : 0;
            someArgsObtain.argi2 = z2 ? 1 : 0;
            NotificationAssistantService.this.mHandler.obtainMessage(4, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationDirectReply(String str) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            NotificationAssistantService.this.mHandler.obtainMessage(5, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onSuggestedReplySent(String str, CharSequence charSequence, int i) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.arg2 = charSequence;
            someArgsObtain.argi2 = i;
            NotificationAssistantService.this.mHandler.obtainMessage(6, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onActionClicked(String str, Notification.Action action, int i) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.arg2 = action;
            someArgsObtain.argi2 = i;
            NotificationAssistantService.this.mHandler.obtainMessage(7, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationClicked(String str) {
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            NotificationAssistantService.this.mHandler.obtainMessage(12, someArgsObtain).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onAllowedAdjustmentsChanged() {
            NotificationAssistantService.this.mHandler.obtainMessage(8).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService.NotificationListenerWrapper, android.service.notification.INotificationListener
        public void onNotificationFeedbackReceived(String str, NotificationRankingUpdate notificationRankingUpdate, Bundle bundle) {
            NotificationAssistantService.this.applyUpdateLocked(notificationRankingUpdate);
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = str;
            someArgsObtain.arg2 = NotificationAssistantService.this.getCurrentRanking();
            someArgsObtain.arg3 = bundle;
            NotificationAssistantService.this.mHandler.obtainMessage(13, someArgsObtain).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdjustmentIssuer(Adjustment adjustment) {
        if (adjustment != null) {
            adjustment.setIssuer(getOpPackageName() + "/" + getClass().getName());
        }
    }

    private final class MyHandler extends Handler {
        public static final int MSG_ON_ACTION_INVOKED = 7;
        public static final int MSG_ON_ALLOWED_ADJUSTMENTS_CHANGED = 8;
        public static final int MSG_ON_NOTIFICATIONS_SEEN = 3;
        public static final int MSG_ON_NOTIFICATION_CLICKED = 12;
        public static final int MSG_ON_NOTIFICATION_DIRECT_REPLY_SENT = 5;
        public static final int MSG_ON_NOTIFICATION_ENQUEUED = 1;
        public static final int MSG_ON_NOTIFICATION_EXPANSION_CHANGED = 4;
        public static final int MSG_ON_NOTIFICATION_FEEDBACK_RECEIVED = 13;
        public static final int MSG_ON_NOTIFICATION_SNOOZED = 2;
        public static final int MSG_ON_NOTIFICATION_VISIBILITY_CHANGED = 11;
        public static final int MSG_ON_PANEL_HIDDEN = 10;
        public static final int MSG_ON_PANEL_REVEALED = 9;
        public static final int MSG_ON_SUGGESTED_REPLY_SENT = 6;

        public MyHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            switch (message.what) {
                case 1:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    StatusBarNotification statusBarNotification = (StatusBarNotification) someArgs.arg1;
                    NotificationChannel notificationChannel = (NotificationChannel) someArgs.arg2;
                    NotificationListenerService.RankingMap rankingMap = (NotificationListenerService.RankingMap) someArgs.arg3;
                    someArgs.recycle();
                    Adjustment adjustmentOnNotificationEnqueued = NotificationAssistantService.this.onNotificationEnqueued(statusBarNotification, notificationChannel, rankingMap);
                    NotificationAssistantService.this.setAdjustmentIssuer(adjustmentOnNotificationEnqueued);
                    if (adjustmentOnNotificationEnqueued != null) {
                        if (!NotificationAssistantService.this.isBound()) {
                            Log.w(NotificationAssistantService.TAG, "MSG_ON_NOTIFICATION_ENQUEUED: service not bound, skip.");
                            return;
                        }
                        try {
                            NotificationAssistantService.this.getNotificationInterface().applyEnqueuedAdjustmentFromAssistant(NotificationAssistantService.this.mWrapper, adjustmentOnNotificationEnqueued);
                            return;
                        } catch (RemoteException e) {
                            Log.v(NotificationAssistantService.TAG, "Unable to contact notification manager", e);
                            throw e.rethrowFromSystemServer();
                        } catch (SecurityException e2) {
                            Log.w(NotificationAssistantService.TAG, "Enqueue adjustment failed; no longer connected", e2);
                            return;
                        }
                    }
                    return;
                case 2:
                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                    StatusBarNotification statusBarNotification2 = (StatusBarNotification) someArgs2.arg1;
                    String str = (String) someArgs2.arg2;
                    someArgs2.recycle();
                    NotificationAssistantService.this.onNotificationSnoozedUntilContext(statusBarNotification2, str);
                    return;
                case 3:
                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                    List<String> list = (List) someArgs3.arg1;
                    someArgs3.recycle();
                    NotificationAssistantService.this.onNotificationsSeen(list);
                    return;
                case 4:
                    SomeArgs someArgs4 = (SomeArgs) message.obj;
                    String str2 = (String) someArgs4.arg1;
                    boolean z2 = someArgs4.argi1 == 1;
                    z = someArgs4.argi2 == 1;
                    someArgs4.recycle();
                    NotificationAssistantService.this.onNotificationExpansionChanged(str2, z2, z);
                    return;
                case 5:
                    SomeArgs someArgs5 = (SomeArgs) message.obj;
                    String str3 = (String) someArgs5.arg1;
                    someArgs5.recycle();
                    NotificationAssistantService.this.onNotificationDirectReplied(str3);
                    return;
                case 6:
                    SomeArgs someArgs6 = (SomeArgs) message.obj;
                    String str4 = (String) someArgs6.arg1;
                    CharSequence charSequence = (CharSequence) someArgs6.arg2;
                    int i = someArgs6.argi2;
                    someArgs6.recycle();
                    NotificationAssistantService.this.onSuggestedReplySent(str4, charSequence, i);
                    return;
                case 7:
                    SomeArgs someArgs7 = (SomeArgs) message.obj;
                    String str5 = (String) someArgs7.arg1;
                    Notification.Action action = (Notification.Action) someArgs7.arg2;
                    int i2 = someArgs7.argi2;
                    someArgs7.recycle();
                    NotificationAssistantService.this.onActionInvoked(str5, action, i2);
                    return;
                case 8:
                    NotificationAssistantService.this.onAllowedAdjustmentsChanged();
                    return;
                case 9:
                    SomeArgs someArgs8 = (SomeArgs) message.obj;
                    int i3 = someArgs8.argi1;
                    someArgs8.recycle();
                    NotificationAssistantService.this.onPanelRevealed(i3);
                    return;
                case 10:
                    NotificationAssistantService.this.onPanelHidden();
                    return;
                case 11:
                    SomeArgs someArgs9 = (SomeArgs) message.obj;
                    String str6 = (String) someArgs9.arg1;
                    z = someArgs9.argi1 == 1;
                    someArgs9.recycle();
                    NotificationAssistantService.this.onNotificationVisibilityChanged(str6, z);
                    return;
                case 12:
                    SomeArgs someArgs10 = (SomeArgs) message.obj;
                    String str7 = (String) someArgs10.arg1;
                    someArgs10.recycle();
                    NotificationAssistantService.this.onNotificationClicked(str7);
                    return;
                case 13:
                    SomeArgs someArgs11 = (SomeArgs) message.obj;
                    String str8 = (String) someArgs11.arg1;
                    NotificationListenerService.RankingMap rankingMap2 = (NotificationListenerService.RankingMap) someArgs11.arg2;
                    Bundle bundle = (Bundle) someArgs11.arg3;
                    someArgs11.recycle();
                    NotificationAssistantService.this.onNotificationFeedbackReceived(str8, rankingMap2, bundle);
                    return;
                default:
                    return;
            }
        }
    }
}
