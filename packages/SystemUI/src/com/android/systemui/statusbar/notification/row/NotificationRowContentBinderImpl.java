package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.shared.HeadsUpStatusBarModel;
import com.android.systemui.statusbar.notification.row.shared.NewRemoteViews;
import com.android.systemui.statusbar.notification.row.shared.NotificationContentModel;
import com.android.systemui.statusbar.notification.row.shared.NotificationRowContentBinderRefactor;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl;
import com.android.systemui.util.Assert;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Result;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class NotificationRowContentBinderImpl implements NotificationRowContentBinder {
    public static final Companion Companion = new Companion(null);
    public final ConversationNotificationProcessor conversationProcessor;
    public final HeadsUpStyleProvider headsUpStyleProvider;
    public boolean inflateSynchronously;
    public final Executor inflationExecutor;
    public final NotificationRowContentBinderLogger logger;
    public final NotifLayoutInflaterFactory.Provider notifLayoutInflaterFactoryProvider;
    public final NotificationRemoteInputManager remoteInputManager;
    public final NotifRemoteViewCache remoteViewCache;
    public final SmartReplyStateInflater smartReplyStateInflater;

    public abstract class ApplyCallback {
        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);
    }

    public final class AsyncInflationTask extends AsyncTask implements NotificationRowContentBinder.InflationCallback, InflationTask {
        public final NotificationRowContentBinder.InflationCallback callback;
        public CancellationSignal cancellationSignal;
        public final ConversationNotificationProcessor conversationProcessor;
        public final NotificationEntry entry;
        public final HeadsUpStyleProvider headsUpStyleProvider;
        public final boolean inflateSynchronously;
        public final Executor inflationExecutor;
        public final boolean isMinimized;
        public final NotificationRowContentBinderLogger logger;
        public final NotifLayoutInflaterFactory.Provider notifLayoutInflaterFactoryProvider;
        public final int reInflateFlags;
        public final NotifRemoteViewCache remoteViewCache;
        public final RemoteViews.InteractionHandler remoteViewClickHandler;
        public final ExpandableNotificationRow row;
        public final SmartReplyStateInflater smartRepliesInflater;
        public final boolean usesIncreasedHeadsUpHeight;
        public final boolean usesIncreasedHeight;

        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public final class RtlEnabledContext extends ContextWrapper {
            public RtlEnabledContext(Context context) {
                super(context);
            }

            @Override // android.content.ContextWrapper, android.content.Context
            public final ApplicationInfo getApplicationInfo() {
                ApplicationInfo applicationInfo = new ApplicationInfo(super.getApplicationInfo());
                applicationInfo.flags |= QuickStepContract.SYSUI_STATE_BACK_DISABLED;
                return applicationInfo;
            }
        }

        static {
            new Companion(null);
        }

        public AsyncInflationTask(Executor executor, boolean z, int i, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ConversationNotificationProcessor conversationNotificationProcessor, ExpandableNotificationRow expandableNotificationRow, boolean z2, boolean z3, boolean z4, NotificationRowContentBinder.InflationCallback inflationCallback, RemoteViews.InteractionHandler interactionHandler, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
            this.inflationExecutor = executor;
            this.inflateSynchronously = z;
            this.reInflateFlags = i;
            this.remoteViewCache = notifRemoteViewCache;
            this.entry = notificationEntry;
            this.conversationProcessor = conversationNotificationProcessor;
            this.row = expandableNotificationRow;
            this.isMinimized = z2;
            this.usesIncreasedHeight = z3;
            this.usesIncreasedHeadsUpHeight = z4;
            this.callback = inflationCallback;
            this.remoteViewClickHandler = interactionHandler;
            this.smartRepliesInflater = smartReplyStateInflater;
            this.notifLayoutInflaterFactoryProvider = provider;
            this.headsUpStyleProvider = headsUpStyleProvider;
            this.logger = notificationRowContentBinderLogger;
            notificationEntry.abortTask();
            notificationEntry.mRunningTask = this;
        }

        public static final InflationProgress access$doInBackgroundInternal(AsyncInflationTask asyncInflationTask) {
            Set set;
            StatusBarNotification statusBarNotification = asyncInflationTask.entry.mSbn;
            try {
                Notification.addFieldsFromContext(asyncInflationTask.row.getContext().getPackageManager().getApplicationInfoAsUser(statusBarNotification.getPackageName(), 8192, UserHandle.getUserId(statusBarNotification.getUid())), statusBarNotification.getNotification());
            } catch (PackageManager.NameNotFoundException unused) {
            }
            Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(asyncInflationTask.row.getContext(), statusBarNotification.getNotification());
            Context packageContext = statusBarNotification.getPackageContext(asyncInflationTask.row.getContext());
            if (recoverBuilder.usesTemplate()) {
                packageContext = new RtlEnabledContext(packageContext);
            }
            Companion companion = NotificationRowContentBinderImpl.Companion;
            int i = asyncInflationTask.reInflateFlags;
            NotificationEntry notificationEntry = asyncInflationTask.entry;
            boolean z = asyncInflationTask.isMinimized;
            boolean z2 = asyncInflationTask.usesIncreasedHeight;
            boolean z3 = asyncInflationTask.usesIncreasedHeadsUpHeight;
            asyncInflationTask.row.getContext();
            InflationProgress access$beginInflationAsync = Companion.access$beginInflationAsync(companion, i, notificationEntry, recoverBuilder, z, z2, z3, packageContext, asyncInflationTask.row, asyncInflationTask.notifLayoutInflaterFactoryProvider, asyncInflationTask.headsUpStyleProvider, asyncInflationTask.conversationProcessor, asyncInflationTask.logger);
            asyncInflationTask.logger.logAsyncTaskProgress(asyncInflationTask.entry, "getting existing smart reply state (on wrong thread!)");
            InflatedSmartReplyState inflatedSmartReplyState = asyncInflationTask.row.mPrivateLayout.mCurrentSmartReplyState;
            asyncInflationTask.logger.logAsyncTaskProgress(asyncInflationTask.entry, "inflating smart reply views");
            Companion.access$inflateSmartReplyViews(companion, access$beginInflationAsync, asyncInflationTask.reInflateFlags, asyncInflationTask.entry, asyncInflationTask.row.getContext(), packageContext, inflatedSmartReplyState, asyncInflationTask.smartRepliesInflater, asyncInflationTask.logger);
            Flags.notificationAsyncHybridViewInflation();
            asyncInflationTask.logger.logAsyncTaskProgress(asyncInflationTask.entry, "getting row image resolver (on wrong thread!)");
            NotificationInlineImageResolver notificationInlineImageResolver = asyncInflationTask.row.mImageResolver;
            asyncInflationTask.logger.logAsyncTaskProgress(asyncInflationTask.entry, "waiting for preloaded images");
            if (notificationInlineImageResolver.hasCache() && (set = notificationInlineImageResolver.mWantedUriSet) != null) {
                set.forEach(new NotificationInlineImageResolver$$ExternalSyntheticLambda1(notificationInlineImageResolver, SystemClock.elapsedRealtime() + 1000));
            }
            return access$beginInflationAsync;
        }

        @Override // com.android.systemui.statusbar.InflationTask
        public final void abort() {
            this.logger.logAsyncTaskProgress(this.entry, "cancelling inflate");
            cancel(true);
            if (this.cancellationSignal != null) {
                this.logger.logAsyncTaskProgress(this.entry, "cancelling apply");
                CancellationSignal cancellationSignal = this.cancellationSignal;
                Intrinsics.checkNotNull(cancellationSignal);
                cancellationSignal.cancel();
            }
            this.logger.logAsyncTaskProgress(this.entry, "aborted");
        }

        @Override // android.os.AsyncTask
        public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return Result.m2526boximpl(m2222doInBackgroundIoAF18A());
        }

        /* renamed from: doInBackground-IoAF18A, reason: not valid java name */
        public final Object m2222doInBackgroundIoAF18A() {
            Object failure;
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("NotificationContentInflater.AsyncInflationTask#doInBackground");
            }
            try {
                try {
                    int i = Result.$r8$clinit;
                    failure = access$doInBackgroundInternal(this);
                } catch (Exception e) {
                    this.logger.logAsyncTaskException(this.entry, "inflating", e);
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(e);
                }
                return failure;
            } finally {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }

        public final int getReInflateFlags() {
            return this.reInflateFlags;
        }

        public final void handleError$1(Exception exc) {
            NotificationEntry notificationEntry = this.entry;
            notificationEntry.mRunningTask = null;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            Log.e("NotifContentInflater", "couldn't inflate view for notification " + AbstractResolvableFuture$$ExternalSyntheticOutline0.m(statusBarNotification.getPackageName(), "/0x", Integer.toHexString(statusBarNotification.getId())), exc);
            NotificationRowContentBinder.InflationCallback inflationCallback = this.callback;
            if (inflationCallback != null) {
                inflationCallback.handleInflationException(this.row.mEntry, new InflationException("Couldn't inflate contentViews" + exc));
            }
            this.row.mImageResolver.cancelRunningTasks();
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
            handleError$1(exc);
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void onAsyncInflationFinished(NotificationEntry notificationEntry) {
            this.entry.mRunningTask = null;
            this.row.onNotificationUpdated();
            NotificationRowContentBinder.InflationCallback inflationCallback = this.callback;
            if (inflationCallback != null) {
                inflationCallback.onAsyncInflationFinished(this.entry);
            }
            NotificationInlineImageResolver notificationInlineImageResolver = this.row.mImageResolver;
            if (notificationInlineImageResolver.hasCache()) {
                NotificationInlineImageCache notificationInlineImageCache = (NotificationInlineImageCache) notificationInlineImageResolver.mImageCache;
                notificationInlineImageCache.mCache.entrySet().removeIf(new NotificationInlineImageCache$$ExternalSyntheticLambda0(notificationInlineImageCache.mResolver.mWantedUriSet));
            }
            this.row.mImageResolver.cancelRunningTasks();
        }

        @Override // android.os.AsyncTask
        public final void onCancelled(Object obj) {
            Trace.endAsyncSection("NotificationRowContentBinderImpl.AsyncInflationTask", System.identityHashCode(this));
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            Trace.endAsyncSection("NotificationRowContentBinderImpl.AsyncInflationTask", System.identityHashCode(this));
            Object m2528unboximpl = ((Result) obj).m2528unboximpl();
            if (!(m2528unboximpl instanceof Result.Failure)) {
                this.cancellationSignal = Companion.access$apply(NotificationRowContentBinderImpl.Companion, this.inflationExecutor, this.inflateSynchronously, this.isMinimized, (InflationProgress) m2528unboximpl, this.reInflateFlags, this.remoteViewCache, this.entry, this.row, this.remoteViewClickHandler, this, this.logger);
            }
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(m2528unboximpl);
            if (m2527exceptionOrNullimpl != null) {
                handleError$1((Exception) m2527exceptionOrNullimpl);
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            Trace.beginAsyncSection("NotificationRowContentBinderImpl.AsyncInflationTask", System.identityHashCode(this));
        }
    }

    public final class Companion {
        private Companion() {
        }

        public static final CancellationSignal access$apply(Companion companion, Executor executor, boolean z, boolean z2, final InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, RemoteViews.InteractionHandler interactionHandler, NotificationRowContentBinder.InflationCallback inflationCallback, final NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
            HashMap<Integer, CancellationSignal> hashMap;
            NotificationContentView notificationContentView;
            NotificationContentView notificationContentView2;
            RemoteViews remoteViews;
            RemoteViews remoteViews2;
            companion.getClass();
            Trace.beginAsyncSection("NotificationRowContentBinderImpl#apply", System.identityHashCode(expandableNotificationRow));
            NotificationContentView notificationContentView3 = expandableNotificationRow.mPrivateLayout;
            NotificationContentView notificationContentView4 = expandableNotificationRow.mPublicLayout;
            HashMap<Integer, CancellationSignal> hashMap2 = new HashMap<>();
            if ((i & 1) != 0) {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                boolean z3 = !companion.canReapplyRemoteView(inflationProgress.remoteViews.contracted, notifRemoteViewCacheImpl.getCachedView(notificationEntry, 1));
                ApplyCallback applyCallback = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$applyCallback$1
                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final RemoteViews getRemoteView() {
                        RemoteViews remoteViews3 = inflationProgress.remoteViews.contracted;
                        Intrinsics.checkNotNull(remoteViews3);
                        return remoteViews3;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final void setResultView(View view) {
                        NotificationRowContentBinderLogger.this.logAsyncTaskProgress(notificationEntry, "contracted view applied");
                        inflationProgress.inflatedContentView = view;
                    }
                };
                notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "applying contracted view");
                hashMap = hashMap2;
                notificationContentView = notificationContentView4;
                notificationContentView2 = notificationContentView3;
                companion.applyRemoteView(executor, z, z2, inflationProgress, i, 1, notifRemoteViewCacheImpl, notificationEntry, expandableNotificationRow, z3, interactionHandler, inflationCallback, notificationContentView2, notificationContentView3.mContractedChild, notificationContentView3.getVisibleWrapper(0), hashMap, applyCallback, notificationRowContentBinderLogger);
            } else {
                hashMap = hashMap2;
                notificationContentView = notificationContentView4;
                notificationContentView2 = notificationContentView3;
            }
            if ((i & 2) != 0 && (remoteViews2 = inflationProgress.remoteViews.expanded) != null) {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl2 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                boolean z4 = !companion.canReapplyRemoteView(remoteViews2, notifRemoteViewCacheImpl2.getCachedView(notificationEntry, 2));
                ApplyCallback applyCallback2 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$applyCallback$2
                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final RemoteViews getRemoteView() {
                        return inflationProgress.remoteViews.expanded;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final void setResultView(View view) {
                        NotificationRowContentBinderLogger.this.logAsyncTaskProgress(notificationEntry, "expanded view applied");
                        inflationProgress.inflatedExpandedView = view;
                    }
                };
                notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "applying expanded view");
                NotificationContentView notificationContentView5 = notificationContentView2;
                notificationContentView2 = notificationContentView5;
                companion.applyRemoteView(executor, z, z2, inflationProgress, i, 2, notifRemoteViewCacheImpl2, notificationEntry, expandableNotificationRow, z4, interactionHandler, inflationCallback, notificationContentView2, notificationContentView5.mExpandedChild, notificationContentView5.getVisibleWrapper(1), hashMap, applyCallback2, notificationRowContentBinderLogger);
            }
            if ((i & 4) != 0 && (remoteViews = inflationProgress.remoteViews.headsUp) != null) {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl3 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                boolean z5 = !companion.canReapplyRemoteView(remoteViews, notifRemoteViewCacheImpl3.getCachedView(notificationEntry, 4));
                ApplyCallback applyCallback3 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$applyCallback$3
                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final RemoteViews getRemoteView() {
                        return inflationProgress.remoteViews.headsUp;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final void setResultView(View view) {
                        NotificationRowContentBinderLogger.this.logAsyncTaskProgress(notificationEntry, "heads up view applied");
                        inflationProgress.inflatedHeadsUpView = view;
                    }
                };
                notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "applying heads up view");
                NotificationContentView notificationContentView6 = notificationContentView2;
                companion.applyRemoteView(executor, z, z2, inflationProgress, i, 4, notifRemoteViewCacheImpl3, notificationEntry, expandableNotificationRow, z5, interactionHandler, inflationCallback, notificationContentView6, notificationContentView6.mHeadsUpChild, notificationContentView6.getVisibleWrapper(2), hashMap, applyCallback3, notificationRowContentBinderLogger);
            }
            if ((i & 8) != 0) {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl4 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                boolean z6 = !companion.canReapplyRemoteView(inflationProgress.remoteViews.f77public, notifRemoteViewCacheImpl4.getCachedView(notificationEntry, 8));
                ApplyCallback applyCallback4 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$applyCallback$4
                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final RemoteViews getRemoteView() {
                        RemoteViews remoteViews3 = inflationProgress.remoteViews.f77public;
                        Intrinsics.checkNotNull(remoteViews3);
                        return remoteViews3;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final void setResultView(View view) {
                        NotificationRowContentBinderLogger.this.logAsyncTaskProgress(notificationEntry, "public view applied");
                        inflationProgress.inflatedPublicView = view;
                    }
                };
                notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "applying public view");
                NotificationContentView notificationContentView7 = notificationContentView;
                companion.applyRemoteView(executor, z, z2, inflationProgress, i, 8, notifRemoteViewCacheImpl4, notificationEntry, expandableNotificationRow, z6, interactionHandler, inflationCallback, notificationContentView7, notificationContentView7.mContractedChild, notificationContentView7.getVisibleWrapper(0), hashMap, applyCallback4, notificationRowContentBinderLogger);
            }
            Flags.notificationAsyncGroupHeaderInflation();
            finishIfDone(inflationProgress, i, notifRemoteViewCache, hashMap, inflationCallback, notificationEntry, expandableNotificationRow, notificationRowContentBinderLogger);
            CancellationSignal cancellationSignal = new CancellationSignal();
            final HashMap<Integer, CancellationSignal> hashMap3 = hashMap;
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$1
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    NotificationRowContentBinderLogger.this.logAsyncTaskProgress(notificationEntry, "apply cancelled");
                    Trace.endAsyncSection("NotificationRowContentBinderImpl#apply", System.identityHashCode(expandableNotificationRow));
                    hashMap3.values().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$1.1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((CancellationSignal) obj).cancel();
                        }
                    });
                }
            });
            return cancellationSignal;
        }

        public static final InflationProgress access$beginInflationAsync(Companion companion, int i, NotificationEntry notificationEntry, Notification.Builder builder, boolean z, boolean z2, boolean z3, Context context, ExpandableNotificationRow expandableNotificationRow, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, ConversationNotificationProcessor conversationNotificationProcessor, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
            RemoteViews remoteViews;
            RemoteViews remoteViews2;
            RemoteViews remoteViews3;
            RemoteViews remoteViews4;
            RemoteViews createContentView;
            companion.getClass();
            if (notificationEntry.mRanking.isConversation()) {
                conversationNotificationProcessor.processNotification(notificationEntry, builder, notificationRowContentBinderLogger);
            }
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("NotificationContentInflater.createRemoteViews");
            }
            try {
                NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
                if ((i & 1) != 0) {
                    notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry2, "creating contracted remote view");
                    NotificationRowContentBinderImpl.Companion.getClass();
                    if (z) {
                        createContentView = builder.makeLowPriorityContentView(false);
                        Intrinsics.checkNotNull(createContentView);
                    } else {
                        createContentView = builder.createContentView(z2);
                    }
                    remoteViews = createContentView;
                } else {
                    remoteViews = null;
                }
                if ((i & 2) != 0) {
                    notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry2, "creating expanded remote view");
                    NotificationRowContentBinderImpl.Companion.getClass();
                    RemoteViews createBigContentView = builder.createBigContentView();
                    if (createBigContentView == null) {
                        if (z) {
                            createBigContentView = builder.createContentView();
                            Notification.Builder.makeHeaderExpanded(createBigContentView);
                        } else {
                            createBigContentView = null;
                        }
                    }
                    remoteViews2 = createBigContentView;
                } else {
                    remoteViews2 = null;
                }
                if ((i & 4) != 0) {
                    notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry2, "creating heads up remote view");
                    remoteViews3 = ((HeadsUpStyleProviderImpl) headsUpStyleProvider).shouldApplyCompactStyle() ? builder.createCompactHeadsUpContentView() : builder.createHeadsUpContentView(z3);
                } else {
                    remoteViews3 = null;
                }
                if ((i & 8) != 0) {
                    notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry2, "creating public remote view");
                    remoteViews4 = builder.makePublicContentView(z);
                } else {
                    remoteViews4 = null;
                }
                Flags.notificationAsyncGroupHeaderInflation();
                Flags.notificationAsyncGroupHeaderInflation();
                Companion companion2 = NotificationRowContentBinderImpl.Companion;
                NewRemoteViews newRemoteViews = new NewRemoteViews(remoteViews, remoteViews3, remoteViews2, remoteViews4, null, null);
                companion2.getClass();
                RemoteViews remoteViews5 = newRemoteViews.contracted;
                if (remoteViews5 != null) {
                    remoteViews5.setLayoutInflaterFactory(provider.provide(expandableNotificationRow, 1));
                }
                RemoteViews remoteViews6 = newRemoteViews.expanded;
                if (remoteViews6 != null) {
                    remoteViews6.setLayoutInflaterFactory(provider.provide(expandableNotificationRow, 2));
                }
                RemoteViews remoteViews7 = newRemoteViews.headsUp;
                if (remoteViews7 != null) {
                    remoteViews7.setLayoutInflaterFactory(provider.provide(expandableNotificationRow, 4));
                }
                RemoteViews remoteViews8 = newRemoteViews.f77public;
                if (remoteViews8 != null) {
                    remoteViews8.setLayoutInflaterFactory(provider.provide(expandableNotificationRow, 8));
                }
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                Flags.notificationAsyncHybridViewInflation();
                return new InflationProgress(context, newRemoteViews, new NotificationContentModel(new HeadsUpStatusBarModel(builder.getHeadsUpStatusBarText(false), builder.getHeadsUpStatusBarText(true)), null));
            } catch (Throwable th) {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }

        public static final void access$inflateSmartReplyViews(Companion companion, InflationProgress inflationProgress, int i, NotificationEntry notificationEntry, Context context, Context context2, InflatedSmartReplyState inflatedSmartReplyState, SmartReplyStateInflater smartReplyStateInflater, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
            companion.getClass();
            int i2 = i & 1;
            NewRemoteViews newRemoteViews = inflationProgress.remoteViews;
            boolean z = (i2 == 0 || newRemoteViews.contracted == null) ? false : true;
            boolean z2 = ((i & 2) == 0 || newRemoteViews.expanded == null) ? false : true;
            boolean z3 = ((i & 4) == 0 || newRemoteViews.headsUp == null) ? false : true;
            if (z || z2 || z3) {
                notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "inflating contracted smart reply state");
                inflationProgress.inflatedSmartReplyState = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyState(notificationEntry);
            }
            if (z2) {
                notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "inflating expanded smart reply state");
                InflatedSmartReplyState inflatedSmartReplyState2 = inflationProgress.inflatedSmartReplyState;
                Intrinsics.checkNotNull(inflatedSmartReplyState2);
                inflationProgress.expandedInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflatedSmartReplyState2);
            }
            if (z3) {
                notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "inflating heads up smart reply state");
                InflatedSmartReplyState inflatedSmartReplyState3 = inflationProgress.inflatedSmartReplyState;
                Intrinsics.checkNotNull(inflatedSmartReplyState3);
                inflationProgress.headsUpInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflatedSmartReplyState3);
            }
        }

        public static boolean finishIfDone(InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, HashMap hashMap, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
            Assert.isMainThread();
            if (!hashMap.isEmpty()) {
                return false;
            }
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            NotificationContentView notificationContentView2 = expandableNotificationRow.mPublicLayout;
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "finishing");
            if ((i & 1) != 0) {
                View view = inflationProgress.inflatedContentView;
                NewRemoteViews newRemoteViews = inflationProgress.remoteViews;
                if (view != null) {
                    notificationContentView.setContractedChild(view);
                    ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 1, newRemoteViews.contracted);
                } else {
                    NotifRemoteViewCacheImpl notifRemoteViewCacheImpl = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                    if (notifRemoteViewCacheImpl.hasCachedView(notificationEntry, 1)) {
                        notifRemoteViewCacheImpl.putCachedView(notificationEntry, 1, newRemoteViews.contracted);
                    }
                }
            }
            if ((i & 2) != 0) {
                View view2 = inflationProgress.inflatedExpandedView;
                NewRemoteViews newRemoteViews2 = inflationProgress.remoteViews;
                if (view2 != null) {
                    notificationContentView.setExpandedChild(view2);
                    ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 2, newRemoteViews2.expanded);
                } else if (newRemoteViews2.expanded == null) {
                    notificationContentView.setExpandedChild(null);
                    ((NotifRemoteViewCacheImpl) notifRemoteViewCache).removeCachedView(notificationEntry, 2);
                } else {
                    NotifRemoteViewCacheImpl notifRemoteViewCacheImpl2 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                    if (notifRemoteViewCacheImpl2.hasCachedView(notificationEntry, 2)) {
                        notifRemoteViewCacheImpl2.putCachedView(notificationEntry, 2, newRemoteViews2.expanded);
                    }
                }
                RemoteViews remoteViews = newRemoteViews2.expanded;
                if (remoteViews != null) {
                    InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder = inflationProgress.expandedInflatedSmartReplies;
                    notificationContentView.mExpandedInflatedSmartReplies = inflatedSmartReplyViewHolder;
                    if (inflatedSmartReplyViewHolder == null) {
                        notificationContentView.mExpandedSmartReplyView = null;
                    }
                } else {
                    notificationContentView.mExpandedInflatedSmartReplies = null;
                    notificationContentView.mExpandedSmartReplyView = null;
                }
                expandableNotificationRow.mExpandable = remoteViews != null;
                expandableNotificationRow.mPrivateLayout.updateExpandButtonsDuringLayout(expandableNotificationRow.isExpandable$1(), false);
            }
            if ((i & 4) != 0) {
                View view3 = inflationProgress.inflatedHeadsUpView;
                NewRemoteViews newRemoteViews3 = inflationProgress.remoteViews;
                if (view3 != null) {
                    notificationContentView.setHeadsUpChild(view3);
                    ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 4, newRemoteViews3.headsUp);
                } else if (newRemoteViews3.headsUp == null) {
                    notificationContentView.setHeadsUpChild(null);
                    ((NotifRemoteViewCacheImpl) notifRemoteViewCache).removeCachedView(notificationEntry, 4);
                } else {
                    NotifRemoteViewCacheImpl notifRemoteViewCacheImpl3 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                    if (notifRemoteViewCacheImpl3.hasCachedView(notificationEntry, 4)) {
                        notifRemoteViewCacheImpl3.putCachedView(notificationEntry, 4, newRemoteViews3.headsUp);
                    }
                }
                if (newRemoteViews3.headsUp != null) {
                    notificationContentView.setHeadsUpInflatedSmartReplies(inflationProgress.headsUpInflatedSmartReplies);
                } else {
                    notificationContentView.setHeadsUpInflatedSmartReplies(null);
                }
            }
            Flags.notificationAsyncHybridViewInflation();
            InflatedSmartReplyState inflatedSmartReplyState = inflationProgress.inflatedSmartReplyState;
            if (inflatedSmartReplyState != null) {
                notificationContentView.mCurrentSmartReplyState = inflatedSmartReplyState;
            }
            if ((i & 8) != 0) {
                View view4 = inflationProgress.inflatedPublicView;
                NewRemoteViews newRemoteViews4 = inflationProgress.remoteViews;
                if (view4 != null) {
                    notificationContentView2.setContractedChild(view4);
                    ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 8, newRemoteViews4.f77public);
                } else {
                    NotifRemoteViewCacheImpl notifRemoteViewCacheImpl4 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                    if (notifRemoteViewCacheImpl4.hasCachedView(notificationEntry, 8)) {
                        notifRemoteViewCacheImpl4.putCachedView(notificationEntry, 8, newRemoteViews4.f77public);
                    }
                }
            }
            Flags.notificationAsyncGroupHeaderInflation();
            int i2 = NotificationRowContentBinderRefactor.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            Flags.FEATURE_FLAGS.getClass();
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notification_row_content_binder_refactor to be enabled.");
            Trace.endAsyncSection("NotificationRowContentBinderImpl#apply", System.identityHashCode(expandableNotificationRow));
            if (inflationCallback != null) {
                inflationCallback.onAsyncInflationFinished(notificationEntry);
            }
            return true;
        }

        public static void handleInflationError(HashMap hashMap, Exception exc, NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationRowContentBinderLogger notificationRowContentBinderLogger, String str) {
            Assert.isMainThread();
            notificationRowContentBinderLogger.logAsyncTaskException(notificationEntry, str, exc);
            hashMap.values().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$handleInflationError$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((CancellationSignal) obj).cancel();
                }
            });
            if (inflationCallback != null) {
                inflationCallback.handleInflationException(notificationEntry, exc);
            }
        }

        public final void applyRemoteView(Executor executor, boolean z, final boolean z2, final InflationProgress inflationProgress, final int i, final int i2, final NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, final boolean z3, final RemoteViews.InteractionHandler interactionHandler, final NotificationRowContentBinder.InflationCallback inflationCallback, final ViewGroup viewGroup, final View view, final NotificationViewWrapper notificationViewWrapper, final HashMap<Integer, CancellationSignal> hashMap, final ApplyCallback applyCallback, final NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
            CancellationSignal reapplyAsync;
            final RemoteViews remoteView = applyCallback.getRemoteView();
            if (!z) {
                RemoteViews.OnViewAppliedListener onViewAppliedListener = new RemoteViews.OnViewAppliedListener(notificationEntry, hashMap, inflationCallback, notificationRowContentBinderLogger, i2, z3, applyCallback, notificationViewWrapper, inflationProgress, z2, i, notifRemoteViewCache, remoteView, viewGroup, interactionHandler, view) { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$applyRemoteView$listener$1
                    public final /* synthetic */ NotificationRowContentBinderImpl.ApplyCallback $applyCallback;
                    public final /* synthetic */ NotificationRowContentBinder.InflationCallback $callback;
                    public final /* synthetic */ NotificationEntry $entry;
                    public final /* synthetic */ View $existingView;
                    public final /* synthetic */ NotificationViewWrapper $existingWrapper;
                    public final /* synthetic */ int $inflationId;
                    public final /* synthetic */ boolean $isMinimized;
                    public final /* synthetic */ boolean $isNewView;
                    public final /* synthetic */ NotificationRowContentBinderLogger $logger;
                    public final /* synthetic */ RemoteViews $newContentView;
                    public final /* synthetic */ ViewGroup $parentLayout;
                    public final /* synthetic */ int $reInflateFlags;
                    public final /* synthetic */ NotifRemoteViewCache $remoteViewCache;
                    public final /* synthetic */ RemoteViews.InteractionHandler $remoteViewClickHandler;
                    public final /* synthetic */ NotificationRowContentBinderImpl.InflationProgress $result;
                    public final /* synthetic */ HashMap $runningInflations;

                    {
                        this.$reInflateFlags = i;
                        this.$remoteViewCache = notifRemoteViewCache;
                        this.$newContentView = remoteView;
                        this.$parentLayout = viewGroup;
                        this.$remoteViewClickHandler = interactionHandler;
                        this.$existingView = view;
                    }

                    public final void onError(Exception exc) {
                        View view2;
                        try {
                            if (this.$isNewView) {
                                view2 = this.$newContentView.apply(this.$result.packageContext, this.$parentLayout, this.$remoteViewClickHandler);
                            } else {
                                this.$newContentView.reapply(this.$result.packageContext, this.$existingView, this.$remoteViewClickHandler);
                                view2 = this.$existingView;
                                Intrinsics.checkNotNull(view2);
                            }
                            Log.wtf("NotifContentInflater", "Async Inflation failed but normal inflation finished normally.", exc);
                            Intrinsics.checkNotNull(view2);
                            onViewApplied(view2);
                        } catch (Exception unused) {
                            this.$runningInflations.remove(Integer.valueOf(this.$inflationId));
                            NotificationRowContentBinderImpl.Companion companion = NotificationRowContentBinderImpl.Companion;
                            HashMap hashMap2 = this.$runningInflations;
                            NotificationEntry notificationEntry2 = ExpandableNotificationRow.this.mEntry;
                            NotificationRowContentBinder.InflationCallback inflationCallback2 = this.$callback;
                            NotificationRowContentBinderLogger notificationRowContentBinderLogger2 = this.$logger;
                            companion.getClass();
                            NotificationRowContentBinderImpl.Companion.handleInflationError(hashMap2, exc, notificationEntry2, inflationCallback2, notificationRowContentBinderLogger2, "applying view");
                        }
                    }

                    public final void onViewApplied(View view2) {
                        String isValidView = NotificationRowContentBinderImpl.Companion.isValidView(view2, this.$entry, ExpandableNotificationRow.this.getResources());
                        if (isValidView != null) {
                            NotificationRowContentBinderImpl.Companion.handleInflationError(this.$runningInflations, new InflationException(isValidView), ExpandableNotificationRow.this.mEntry, this.$callback, this.$logger, "applied invalid view");
                            this.$runningInflations.remove(Integer.valueOf(this.$inflationId));
                            return;
                        }
                        if (this.$isNewView) {
                            this.$applyCallback.setResultView(view2);
                        } else {
                            NotificationViewWrapper notificationViewWrapper2 = this.$existingWrapper;
                            if (notificationViewWrapper2 != null) {
                                notificationViewWrapper2.onReinflated();
                            }
                        }
                        this.$runningInflations.remove(Integer.valueOf(this.$inflationId));
                        NotificationRowContentBinderImpl.Companion.finishIfDone(this.$result, this.$reInflateFlags, this.$remoteViewCache, this.$runningInflations, this.$callback, this.$entry, ExpandableNotificationRow.this, this.$logger);
                    }

                    public final void onViewInflated(View view2) {
                        if (view2 instanceof ImageMessageConsumer) {
                            ((ImageMessageConsumer) view2).setImageResolver(ExpandableNotificationRow.this.mImageResolver);
                        }
                    }
                };
                if (z3) {
                    reapplyAsync = remoteView.applyAsync(inflationProgress.packageContext, viewGroup, executor, onViewAppliedListener, interactionHandler);
                    Intrinsics.checkNotNull(reapplyAsync);
                } else {
                    reapplyAsync = remoteView.reapplyAsync(inflationProgress.packageContext, view, executor, onViewAppliedListener, interactionHandler);
                    Intrinsics.checkNotNull(reapplyAsync);
                }
                hashMap.put(Integer.valueOf(i2), reapplyAsync);
                return;
            }
            try {
                if (z3) {
                    View apply = remoteView.apply(inflationProgress.packageContext, viewGroup, interactionHandler);
                    String isValidView = isValidView(apply, notificationEntry, expandableNotificationRow.getResources());
                    if (isValidView != null) {
                        throw new InflationException(isValidView);
                    }
                    applyCallback.setResultView(apply);
                    return;
                }
                if (view == null) {
                    throw new IllegalArgumentException("Required value was null.".toString());
                }
                if (notificationViewWrapper == null) {
                    throw new IllegalArgumentException("Required value was null.".toString());
                }
                remoteView.reapply(inflationProgress.packageContext, view, interactionHandler);
                String isValidView2 = isValidView(view, notificationEntry, expandableNotificationRow.getResources());
                if (isValidView2 != null) {
                    throw new InflationException(isValidView2);
                }
                notificationViewWrapper.onReinflated();
            } catch (Exception e) {
                handleInflationError(hashMap, e, expandableNotificationRow.mEntry, inflationCallback, notificationRowContentBinderLogger, "applying view synchronously");
                hashMap.put(Integer.valueOf(i2), new CancellationSignal());
            }
        }

        public final boolean canReapplyRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
            if (remoteViews == null && remoteViews2 == null) {
                return true;
            }
            return (remoteViews == null || remoteViews2 == null || remoteViews2.getPackage() == null || remoteViews.getPackage() == null || !Intrinsics.areEqual(remoteViews.getPackage(), remoteViews2.getPackage()) || remoteViews.getLayoutId() != remoteViews2.getLayoutId() || remoteViews2.hasFlags(1)) ? false : true;
        }

        public final String isValidView(View view, NotificationEntry notificationEntry, Resources resources) {
            if (notificationEntry.targetSdk < 31) {
                Notification notification2 = notificationEntry.mSbn.getNotification();
                if (notification2.contentView != null || notification2.bigContentView != null || notification2.headsUpContentView != null) {
                    boolean isEnabled = Trace.isEnabled();
                    if (isEnabled) {
                        TraceUtilsKt.beginSlice("NotificationContentInflater#satisfiesMinHeightRequirement");
                    }
                    try {
                        view.measure(View.MeasureSpec.makeMeasureSpec(resources.getDimensionPixelSize(R.dimen.notification_validation_reference_width), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
                        r1 = view.getMeasuredHeight() >= resources.getDimensionPixelSize(R.dimen.notification_validation_minimum_allowed_height);
                    } finally {
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    }
                }
            }
            if (r1) {
                return null;
            }
            return "inflated notification does not meet minimum height requirement";
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class InflationProgress {
        public InflatedSmartReplyViewHolder expandedInflatedSmartReplies;
        public InflatedSmartReplyViewHolder headsUpInflatedSmartReplies;
        public View inflatedContentView;
        public View inflatedExpandedView;
        public View inflatedHeadsUpView;
        public View inflatedPublicView;
        public InflatedSmartReplyState inflatedSmartReplyState;
        public final Context packageContext;
        public final NewRemoteViews remoteViews;

        public InflationProgress(Context context, NewRemoteViews newRemoteViews, NotificationContentModel notificationContentModel) {
            this.packageContext = context;
            this.remoteViews = newRemoteViews;
        }
    }

    public NotificationRowContentBinderImpl(NotifRemoteViewCache notifRemoteViewCache, NotificationRemoteInputManager notificationRemoteInputManager, ConversationNotificationProcessor conversationNotificationProcessor, Executor executor, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        this.remoteViewCache = notifRemoteViewCache;
        this.remoteInputManager = notificationRemoteInputManager;
        this.conversationProcessor = conversationNotificationProcessor;
        this.inflationExecutor = executor;
        this.smartReplyStateInflater = smartReplyStateInflater;
        this.notifLayoutInflaterFactoryProvider = provider;
        this.headsUpStyleProvider = headsUpStyleProvider;
        this.logger = notificationRowContentBinderLogger;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationRowContentBinderRefactor.$r8$clinit;
        Flags.FEATURE_FLAGS.getClass();
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notification_row_content_binder_refactor to be enabled.");
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final void bindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i, NotificationRowContentBinder.BindParams bindParams, boolean z, RowContentBindStage.AnonymousClass1 anonymousClass1) {
        SparseArray sparseArray;
        NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.logger;
        notificationRowContentBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowContentBinderLogger$logBinding$2 notificationRowContentBinderLogger$logBinding$2 = NotificationRowContentBinderLogger$logBinding$2.INSTANCE;
        LogBuffer logBuffer = notificationRowContentBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$logBinding$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
        expandableNotificationRow.mImageResolver.preloadImages(notificationEntry.mSbn.getNotification());
        if (z && (sparseArray = (SparseArray) ((ArrayMap) ((NotifRemoteViewCacheImpl) this.remoteViewCache).mNotifCachedContentViews).get(notificationEntry)) != null) {
            sparseArray.clear();
        }
        if ((i & 1) != 0) {
            expandableNotificationRow.mPrivateLayout.removeContentInactiveRunnable(0);
        }
        if ((i & 2) != 0) {
            expandableNotificationRow.mPrivateLayout.removeContentInactiveRunnable(1);
        }
        if ((i & 4) != 0) {
            expandableNotificationRow.mPrivateLayout.removeContentInactiveRunnable(2);
        }
        if ((i & 8) != 0) {
            expandableNotificationRow.mPublicLayout.removeContentInactiveRunnable(0);
        }
        Flags.notificationAsyncHybridViewInflation();
        AsyncInflationTask asyncInflationTask = new AsyncInflationTask(this.inflationExecutor, this.inflateSynchronously, i, this.remoteViewCache, notificationEntry, this.conversationProcessor, expandableNotificationRow, bindParams.isMinimized, bindParams.usesIncreasedHeight, bindParams.usesIncreasedHeadsUpHeight, anonymousClass1, this.remoteInputManager.mInteractionHandler, this.smartReplyStateInflater, this.notifLayoutInflaterFactoryProvider, this.headsUpStyleProvider, this.logger);
        if (!this.inflateSynchronously) {
            asyncInflationTask.executeOnExecutor(this.inflationExecutor, new Void[0]);
        } else {
            Void[] voidArr = new Void[0];
            asyncInflationTask.onPostExecute(Result.m2526boximpl(asyncInflationTask.m2222doInBackgroundIoAF18A()));
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final boolean cancelBind(NotificationEntry notificationEntry) {
        boolean abortTask = notificationEntry.abortTask();
        if (abortTask) {
            NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.logger;
            notificationRowContentBinderLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotificationRowContentBinderLogger$logCancelBindAbortedTask$2 notificationRowContentBinderLogger$logCancelBindAbortedTask$2 = NotificationRowContentBinderLogger$logCancelBindAbortedTask$2.INSTANCE;
            LogBuffer logBuffer = notificationRowContentBinderLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$logCancelBindAbortedTask$2, null);
            ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(obtain);
        }
        return abortTask;
    }

    public final InflationProgress inflateNotificationViews(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinder.BindParams bindParams, boolean z, int i, Notification.Builder builder, Context context, SmartReplyStateInflater smartReplyStateInflater) {
        Context context2 = expandableNotificationRow.getContext();
        boolean z2 = bindParams.isMinimized;
        boolean z3 = bindParams.usesIncreasedHeight;
        boolean z4 = bindParams.usesIncreasedHeadsUpHeight;
        Intrinsics.checkNotNull(context2);
        NotifLayoutInflaterFactory.Provider provider = this.notifLayoutInflaterFactoryProvider;
        HeadsUpStyleProvider headsUpStyleProvider = this.headsUpStyleProvider;
        Companion companion = Companion;
        InflationProgress access$beginInflationAsync = Companion.access$beginInflationAsync(companion, i, notificationEntry, builder, z2, z3, z4, context, expandableNotificationRow, provider, headsUpStyleProvider, this.conversationProcessor, this.logger);
        Companion.access$inflateSmartReplyViews(companion, access$beginInflationAsync, i, notificationEntry, context2, context, expandableNotificationRow.mPrivateLayout.mCurrentSmartReplyState, smartReplyStateInflater, this.logger);
        Flags.notificationAsyncHybridViewInflation();
        Companion.access$apply(companion, this.inflationExecutor, z, bindParams.isMinimized, access$beginInflationAsync, i, this.remoteViewCache, notificationEntry, expandableNotificationRow, this.remoteInputManager.mInteractionHandler, null, this.logger);
        return access$beginInflationAsync;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public void setInflateSynchronously(boolean z) {
        this.inflateSynchronously = z;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final void unbindContent(final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, int i) {
        NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.logger;
        notificationRowContentBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowContentBinderLogger$logUnbinding$2 notificationRowContentBinderLogger$logUnbinding$2 = NotificationRowContentBinderLogger$logUnbinding$2.INSTANCE;
        LogBuffer logBuffer = notificationRowContentBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$logUnbinding$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
        int i2 = 1;
        while (i != 0) {
            if ((i & i2) != 0) {
                if (i2 == 1) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(0, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ExpandableNotificationRow.this.mPrivateLayout.setContractedChild(null);
                            ((NotifRemoteViewCacheImpl) this.remoteViewCache).removeCachedView(notificationEntry, 1);
                        }
                    });
                } else if (i2 == 2) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(1, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ExpandableNotificationRow.this.mPrivateLayout.setExpandedChild(null);
                            ((NotifRemoteViewCacheImpl) this.remoteViewCache).removeCachedView(notificationEntry, 2);
                        }
                    });
                } else if (i2 == 4) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(2, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$3
                        @Override // java.lang.Runnable
                        public final void run() {
                            ExpandableNotificationRow.this.mPrivateLayout.setHeadsUpChild(null);
                            ((NotifRemoteViewCacheImpl) this.remoteViewCache).removeCachedView(notificationEntry, 4);
                            ExpandableNotificationRow.this.mPrivateLayout.setHeadsUpInflatedSmartReplies(null);
                        }
                    });
                } else if (i2 == 8) {
                    expandableNotificationRow.mPublicLayout.performWhenContentInactive(0, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$4
                        @Override // java.lang.Runnable
                        public final void run() {
                            ExpandableNotificationRow.this.mPublicLayout.setContractedChild(null);
                            ((NotifRemoteViewCacheImpl) this.remoteViewCache).removeCachedView(notificationEntry, 8);
                        }
                    });
                } else if (i2 == 16) {
                    Flags.notificationAsyncHybridViewInflation();
                }
            }
            i &= ~i2;
            i2 <<= 1;
        }
    }
}
