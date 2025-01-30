package com.android.systemui.statusbar.notification.row;

import android.app.ActivityManager;
import android.app.Notification;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PowerManager;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.widget.MessagingMessage;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationContentInflater;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageCache;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RowContentBindStage extends BindStage {
    public final NotificationRowContentBinder mBinder;
    public final RowContentBindStageLogger mLogger;
    public final NotifInflationErrorManager mNotifInflationErrorManager;
    public final PowerManager mPm;

    public RowContentBindStage(NotificationRowContentBinder notificationRowContentBinder, NotifInflationErrorManager notifInflationErrorManager, RowContentBindStageLogger rowContentBindStageLogger, PowerManager powerManager) {
        this.mBinder = notificationRowContentBinder;
        this.mNotifInflationErrorManager = notifInflationErrorManager;
        this.mLogger = rowContentBindStageLogger;
        this.mPm = powerManager;
    }

    @Override // com.android.systemui.statusbar.notification.row.BindStage
    public final void abortStage(NotificationEntry notificationEntry) {
        WakeLock wakeLock;
        ((NotificationContentInflater) this.mBinder).getClass();
        notificationEntry.abortTask();
        if ((NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) && (wakeLock = notificationEntry.mInflationWakeLock) != null) {
            wakeLock.release(notificationEntry.mKey);
            notificationEntry.mInflationWakeLock = null;
        }
    }

    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.statusbar.notification.row.RowContentBindStage$1] */
    @Override // com.android.systemui.statusbar.notification.row.BindStage
    public final void executeStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, final NotifBindPipeline$$ExternalSyntheticLambda0 notifBindPipeline$$ExternalSyntheticLambda0) {
        SparseArray sparseArray;
        RowContentBindParams rowContentBindParams = (RowContentBindParams) getStageParams(notificationEntry);
        RowContentBindStageLogger rowContentBindStageLogger = this.mLogger;
        rowContentBindStageLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        RowContentBindStageLogger$logStageParams$2 rowContentBindStageLogger$logStageParams$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStageLogger$logStageParams$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m32m("Invalidated notif ", logMessage.getStr1(), " with params: ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = rowContentBindStageLogger.buffer;
        LogMessage obtain = logBuffer.obtain("RowContentBindStage", logLevel, rowContentBindStageLogger$logStageParams$2, null);
        obtain.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        obtain.setStr2(rowContentBindParams.toString());
        logBuffer.commit(obtain);
        int i = rowContentBindParams.mContentViews;
        int i2 = rowContentBindParams.mDirtyContentViews & i;
        int i3 = i ^ 15;
        NotificationContentInflater notificationContentInflater = (NotificationContentInflater) this.mBinder;
        notificationContentInflater.getClass();
        int i4 = 1;
        int i5 = 1;
        while (true) {
            int i6 = 0;
            int i7 = 2;
            if (i3 == 0) {
                break;
            }
            if ((i3 & i5) != 0) {
                if (i5 == 1) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(0, new NotificationContentInflater$$ExternalSyntheticLambda2(notificationContentInflater, expandableNotificationRow, notificationEntry, i6));
                } else if (i5 == 2) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(1, new NotificationContentInflater$$ExternalSyntheticLambda2(notificationContentInflater, expandableNotificationRow, notificationEntry, i4));
                } else if (i5 == 4) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(2, new NotificationContentInflater$$ExternalSyntheticLambda2(notificationContentInflater, expandableNotificationRow, notificationEntry, i7));
                } else if (i5 == 8) {
                    expandableNotificationRow.mPublicLayout.performWhenContentInactive(0, new NotificationContentInflater$$ExternalSyntheticLambda2(notificationContentInflater, expandableNotificationRow, notificationEntry, 3));
                }
            }
            i3 &= ~i5;
            i5 <<= 1;
        }
        NotificationRowContentBinder.BindParams bindParams = new NotificationRowContentBinder.BindParams();
        bindParams.isLowPriority = rowContentBindParams.mUseLowPriority;
        bindParams.usesIncreasedHeight = rowContentBindParams.mUseIncreasedHeight;
        bindParams.usesIncreasedHeadsUpHeight = rowContentBindParams.mUseIncreasedHeadsUpHeight;
        boolean z = rowContentBindParams.mViewsNeedReinflation;
        ?? r10 = new NotificationRowContentBinder.InflationCallback() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStage.1
            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public final void handleInflationException(NotificationEntry notificationEntry2, Exception exc) {
                WakeLock wakeLock;
                RowContentBindStage.this.mNotifInflationErrorManager.setInflationError(notificationEntry2, exc);
                if ((NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) && (wakeLock = notificationEntry2.mInflationWakeLock) != null) {
                    wakeLock.release(notificationEntry2.mKey);
                    notificationEntry2.mInflationWakeLock = null;
                }
            }

            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public final void onAsyncInflationFinished(NotificationEntry notificationEntry2) {
                WakeLock wakeLock;
                RowContentBindStage rowContentBindStage = RowContentBindStage.this;
                rowContentBindStage.mNotifInflationErrorManager.clearInflationError(notificationEntry2);
                ((RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry2)).mDirtyContentViews = 0;
                NotifBindPipeline notifBindPipeline = ((NotifBindPipeline$$ExternalSyntheticLambda0) notifBindPipeline$$ExternalSyntheticLambda0).f$0;
                NotifBindPipeline.BindEntry bindEntry = (NotifBindPipeline.BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry2);
                ArraySet arraySet = (ArraySet) bindEntry.callbacks;
                int size = arraySet.size();
                NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
                notifBindPipelineLogger.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                NotifBindPipelineLogger$logFinishedPipeline$2 notifBindPipelineLogger$logFinishedPipeline$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$logFinishedPipeline$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return "Finished pipeline for notif " + logMessage.getStr1() + " with " + logMessage.getInt1() + " callbacks";
                    }
                };
                LogBuffer logBuffer2 = notifBindPipelineLogger.buffer;
                LogMessage obtain2 = logBuffer2.obtain("NotifBindPipeline", logLevel2, notifBindPipelineLogger$logFinishedPipeline$2, null);
                obtain2.setStr1(NotificationUtilsKt.getLogKey(notificationEntry2));
                obtain2.setInt1(size);
                logBuffer2.commit(obtain2);
                bindEntry.invalidated = false;
                ArrayList arrayList = (ArrayList) notifBindPipeline.mScratchCallbacksList;
                arrayList.addAll(arraySet);
                arraySet.clear();
                for (int i8 = 0; i8 < arrayList.size(); i8++) {
                    ((NotifBindPipeline.BindCallback) arrayList.get(i8)).onBindFinished(notificationEntry2);
                }
                arrayList.clear();
                if ((NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) && (wakeLock = notificationEntry2.mInflationWakeLock) != null) {
                    wakeLock.release(notificationEntry2.mKey);
                    notificationEntry2.mInflationWakeLock = null;
                }
            }
        };
        notificationContentInflater.getClass();
        notificationEntry.abortTask();
        notificationContentInflater.getClass();
        expandableNotificationRow.getClass();
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        final NotificationInlineImageResolver notificationInlineImageResolver = expandableNotificationRow.mImageResolver;
        Notification notification2 = statusBarNotification.getNotification();
        if (notificationInlineImageResolver.hasCache()) {
            HashSet hashSet = new HashSet();
            Bundle bundle = notification2.extras;
            if (bundle != null) {
                Parcelable[] parcelableArray = bundle.getParcelableArray("android.messages");
                List<Notification.MessagingStyle.Message> messagesFromBundleArray = parcelableArray == null ? null : Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray);
                if (messagesFromBundleArray != null) {
                    for (Notification.MessagingStyle.Message message : messagesFromBundleArray) {
                        if (MessagingMessage.hasImage(message)) {
                            hashSet.add(message.getDataUri());
                        }
                    }
                }
                Parcelable[] parcelableArray2 = bundle.getParcelableArray("android.messages.historic");
                List<Notification.MessagingStyle.Message> messagesFromBundleArray2 = parcelableArray2 == null ? null : Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray2);
                if (messagesFromBundleArray2 != null) {
                    for (Notification.MessagingStyle.Message message2 : messagesFromBundleArray2) {
                        if (MessagingMessage.hasImage(message2)) {
                            hashSet.add(message2.getDataUri());
                        }
                    }
                }
                notificationInlineImageResolver.mWantedUriSet = hashSet;
            }
            notificationInlineImageResolver.mWantedUriSet.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NotificationInlineImageResolver notificationInlineImageResolver2 = NotificationInlineImageResolver.this;
                    Uri uri = (Uri) obj;
                    if (((NotificationInlineImageCache) notificationInlineImageResolver2.mImageCache).mCache.containsKey(uri)) {
                        return;
                    }
                    NotificationInlineImageCache notificationInlineImageCache = (NotificationInlineImageCache) notificationInlineImageResolver2.mImageCache;
                    notificationInlineImageCache.getClass();
                    NotificationInlineImageCache.PreloadImageTask preloadImageTask = new NotificationInlineImageCache.PreloadImageTask(notificationInlineImageCache.mResolver);
                    preloadImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
                    notificationInlineImageCache.mCache.put(uri, preloadImageTask);
                }
            });
        }
        if (z && (sparseArray = (SparseArray) ((ArrayMap) ((NotifRemoteViewCacheImpl) notificationContentInflater.mRemoteViewCache).mNotifCachedContentViews).get(notificationEntry)) != null) {
            sparseArray.clear();
        }
        if ((i2 & 1) != 0) {
            expandableNotificationRow.mPrivateLayout.removeContentInactiveRunnable(0);
        }
        if ((i2 & 2) != 0) {
            expandableNotificationRow.mPrivateLayout.removeContentInactiveRunnable(1);
        }
        if ((i2 & 4) != 0) {
            expandableNotificationRow.mPrivateLayout.removeContentInactiveRunnable(2);
        }
        if ((i2 & 8) != 0) {
            expandableNotificationRow.mPublicLayout.removeContentInactiveRunnable(0);
        }
        NotificationContentInflater.AsyncInflationTask asyncInflationTask = new NotificationContentInflater.AsyncInflationTask(notificationContentInflater.mBgExecutor, notificationContentInflater.mInflateSynchronously, i2, notificationContentInflater.mRemoteViewCache, notificationEntry, notificationContentInflater.mConversationProcessor, expandableNotificationRow, bindParams.isLowPriority, bindParams.usesIncreasedHeight, bindParams.usesIncreasedHeadsUpHeight, (C28911) r10, notificationContentInflater.mRemoteInputManager.mInteractionHandler, notificationContentInflater.mIsMediaInQS, notificationContentInflater.mSmartReplyStateInflater, NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE ? ((SettingsHelper) Dependency.get(SettingsHelper.class)).isAllowPrivateNotificationsWhenUnsecure(ActivityManager.getCurrentUser()) : false);
        if (notificationContentInflater.mInflateSynchronously) {
            asyncInflationTask.onPostExecute(asyncInflationTask.doInBackground());
        } else {
            asyncInflationTask.executeOnExecutor(notificationContentInflater.mBgExecutor, new Void[0]);
        }
        if ((NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) && !this.mPm.isInteractive()) {
            WakeLock.Builder builder = new WakeLock.Builder(expandableNotificationRow.getContext(), null);
            builder.mMaxTimeout = 1000L;
            StringBuilder sb = new StringBuilder("@Inflate:");
            String str = notificationEntry.mKey;
            sb.append(str);
            builder.mTag = sb.toString();
            WakeLock build = builder.build();
            notificationEntry.mInflationWakeLock = build;
            build.acquire(str);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.BindStage
    public final RowContentBindParams newStageParams() {
        return new RowContentBindParams();
    }
}
