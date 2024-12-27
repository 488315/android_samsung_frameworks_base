package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pools;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.app.tracing.TraceUtils;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.NotiRune;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.shared.NotificationRowContentBinderRefactor;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import noticolorpicker.NotificationColorPicker;

public class NotificationContentInflater implements NotificationRowContentBinder {
    public final ConversationNotificationProcessor mConversationProcessor;
    public final HeadsUpStyleProvider mHeadsUpStyleProvider;
    public boolean mInflateSynchronously = false;
    public final Executor mInflationExecutor;
    public final boolean mIsMediaInQS;
    public final NotificationRowContentBinderLogger mLogger;
    public final NotifLayoutInflaterFactory.Provider mNotifLayoutInflaterFactoryProvider;
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final NotifRemoteViewCache mRemoteViewCache;
    public final SmartReplyStateInflater mSmartReplyStateInflater;

    abstract class ApplyCallback {
        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);
    }

    public final class AsyncInflationTask extends AsyncTask implements NotificationRowContentBinder.InflationCallback, InflationTask {
        public final NotificationRowContentBinder.InflationCallback mCallback;
        public CancellationSignal mCancellationSignal;
        public final Context mContext;
        public final ConversationNotificationProcessor mConversationProcessor;
        public final NotificationEntry mEntry;
        public Exception mError;
        public final HeadsUpStyleProvider mHeadsUpStyleProvider;
        public final boolean mInflateSynchronously;
        public final Executor mInflationExecutor;
        public final boolean mIsAllowPrivateNotificationsWhenUnsecure;
        public final boolean mIsMinimized;
        public final NotificationRowContentBinderLogger mLogger;
        public final NotifLayoutInflaterFactory.Provider mNotifLayoutInflaterFactoryProvider;
        public final int mReInflateFlags;
        public final NotifRemoteViewCache mRemoteViewCache;
        public final RemoteViews.InteractionHandler mRemoteViewClickHandler;
        public final ExpandableNotificationRow mRow;
        public final SmartReplyStateInflater mSmartRepliesInflater;
        public final boolean mUsesIncreasedHeadsUpHeight;
        public final boolean mUsesIncreasedHeight;

        public final class RtlEnabledContext extends ContextWrapper {
            public /* synthetic */ RtlEnabledContext(Context context, int i) {
                this(context);
            }

            @Override // android.content.ContextWrapper, android.content.Context
            public final ApplicationInfo getApplicationInfo() {
                ApplicationInfo applicationInfo = new ApplicationInfo(super.getApplicationInfo());
                applicationInfo.flags |= QuickStepContract.SYSUI_STATE_BACK_DISABLED;
                return applicationInfo;
            }

            private RtlEnabledContext(Context context) {
                super(context);
            }
        }

        public /* synthetic */ AsyncInflationTask(Executor executor, boolean z, int i, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ConversationNotificationProcessor conversationNotificationProcessor, ExpandableNotificationRow expandableNotificationRow, boolean z2, boolean z3, boolean z4, RowContentBindStage.AnonymousClass1 anonymousClass1, NotificationRemoteInputManager.AnonymousClass1 anonymousClass12, boolean z5, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, NotificationRowContentBinderLogger notificationRowContentBinderLogger, boolean z6) {
            this(executor, z, i, notifRemoteViewCache, notificationEntry, conversationNotificationProcessor, expandableNotificationRow, z2, z3, z4, (NotificationRowContentBinder.InflationCallback) anonymousClass1, (RemoteViews.InteractionHandler) anonymousClass12, z5, smartReplyStateInflater, provider, headsUpStyleProvider, notificationRowContentBinderLogger, z6);
        }

        @Override // com.android.systemui.statusbar.InflationTask
        public final void abort() {
            this.mLogger.logAsyncTaskProgress(this.mEntry, "cancelling inflate");
            cancel(true);
            if (this.mCancellationSignal != null) {
                this.mLogger.logAsyncTaskProgress(this.mEntry, "cancelling apply");
                this.mCancellationSignal.cancel();
            }
            this.mLogger.logAsyncTaskProgress(this.mEntry, "aborted");
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            InflationProgress inflationProgress;
            int i = TraceUtils.$r8$clinit;
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("NotificationContentInflater.AsyncInflationTask#doInBackground");
            }
            try {
                try {
                    inflationProgress = doInBackgroundInternal();
                } catch (Exception e) {
                    this.mError = e;
                    this.mLogger.logAsyncTaskException(this.mEntry, "inflating", e);
                    inflationProgress = null;
                }
                return inflationProgress;
            } finally {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        }

        public final InflationProgress doInBackgroundInternal() {
            Set set;
            StatusBarNotification statusBarNotification = this.mEntry.mSbn;
            try {
                Notification.addFieldsFromContext(this.mContext.getPackageManager().getApplicationInfoAsUser(statusBarNotification.getPackageName(), 8192, UserHandle.getUserId(statusBarNotification.getUid())), statusBarNotification.getNotification());
            } catch (PackageManager.NameNotFoundException unused) {
            }
            Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(this.mContext, statusBarNotification.getNotification());
            Context packageContext = statusBarNotification.getPackageContext(this.mContext);
            if (recoverBuilder.usesTemplate()) {
                packageContext = new RtlEnabledContext(packageContext, 0);
            }
            if (this.mEntry.mRanking.isConversation()) {
                this.mConversationProcessor.processNotification(this.mEntry, recoverBuilder, this.mLogger);
            }
            int i = this.mReInflateFlags;
            boolean z = this.mIsMinimized;
            boolean z2 = this.mUsesIncreasedHeight;
            boolean z3 = this.mUsesIncreasedHeadsUpHeight;
            NotificationContentInflater$$ExternalSyntheticLambda2 notificationContentInflater$$ExternalSyntheticLambda2 = new NotificationContentInflater$$ExternalSyntheticLambda2(this.mRow, i, this.mLogger, z, recoverBuilder, z2, this.mHeadsUpStyleProvider, z3, this.mIsAllowPrivateNotificationsWhenUnsecure, this.mNotifLayoutInflaterFactoryProvider, packageContext);
            int i2 = TraceUtils.$r8$clinit;
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("NotificationContentInflater.createRemoteViews");
            }
            try {
                Object invoke = notificationContentInflater$$ExternalSyntheticLambda2.invoke();
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                InflationProgress inflationProgress = (InflationProgress) invoke;
                this.mLogger.logAsyncTaskProgress(this.mEntry, "getting existing smart reply state (on wrong thread!)");
                InflatedSmartReplyState inflatedSmartReplyState = this.mRow.mPrivateLayout.mCurrentSmartReplyState;
                this.mLogger.logAsyncTaskProgress(this.mEntry, "inflating smart reply views");
                NotificationContentInflater.inflateSmartReplyViews(inflationProgress, this.mReInflateFlags, this.mEntry, this.mContext, packageContext, inflatedSmartReplyState, this.mSmartRepliesInflater, this.mLogger);
                int i3 = AsyncHybridViewInflation.$r8$clinit;
                Flags.notificationAsyncHybridViewInflation();
                this.mLogger.logAsyncTaskProgress(this.mEntry, "getting row image resolver (on wrong thread!)");
                NotificationInlineImageResolver notificationInlineImageResolver = this.mRow.mImageResolver;
                this.mLogger.logAsyncTaskProgress(this.mEntry, "waiting for preloaded images");
                if (notificationInlineImageResolver.hasCache() && (set = notificationInlineImageResolver.mWantedUriSet) != null) {
                    set.forEach(new NotificationInlineImageResolver$$ExternalSyntheticLambda1(notificationInlineImageResolver, SystemClock.elapsedRealtime() + 1000));
                }
                return inflationProgress;
            } finally {
            }
        }

        public int getReInflateFlags() {
            return this.mReInflateFlags;
        }

        public final void handleError(Exception exc) {
            NotificationEntry notificationEntry = this.mEntry;
            notificationEntry.mRunningTask = null;
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            Log.e("CentralSurfaces", "couldn't inflate view for notification " + (statusBarNotification.getPackageName() + "/0x" + Integer.toHexString(statusBarNotification.getId())), exc);
            NotificationRowContentBinder.InflationCallback inflationCallback = this.mCallback;
            if (inflationCallback != null) {
                inflationCallback.handleInflationException(this.mRow.mEntry, new InflationException("Couldn't inflate contentViews" + exc));
            }
            this.mRow.mImageResolver.cancelRunningTasks();
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
            handleError(exc);
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void onAsyncInflationFinished(NotificationEntry notificationEntry) {
            this.mEntry.mRunningTask = null;
            this.mRow.onNotificationUpdated();
            NotificationRowContentBinder.InflationCallback inflationCallback = this.mCallback;
            if (inflationCallback != null) {
                inflationCallback.onAsyncInflationFinished(this.mEntry);
            }
            NotificationInlineImageResolver notificationInlineImageResolver = this.mRow.mImageResolver;
            if (notificationInlineImageResolver.hasCache()) {
                NotificationInlineImageCache notificationInlineImageCache = (NotificationInlineImageCache) notificationInlineImageResolver.mImageCache;
                notificationInlineImageCache.mCache.entrySet().removeIf(new NotificationInlineImageCache$$ExternalSyntheticLambda0(notificationInlineImageCache.mResolver.mWantedUriSet));
            }
            this.mRow.mImageResolver.cancelRunningTasks();
        }

        @Override // android.os.AsyncTask
        public final void onCancelled(Object obj) {
            Trace.endAsyncSection("NotificationContentInflater.AsyncInflationTask", System.identityHashCode(this));
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            Trace.beginAsyncSection("NotificationContentInflater.AsyncInflationTask", System.identityHashCode(this));
        }

        private AsyncInflationTask(Executor executor, boolean z, int i, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ConversationNotificationProcessor conversationNotificationProcessor, ExpandableNotificationRow expandableNotificationRow, boolean z2, boolean z3, boolean z4, NotificationRowContentBinder.InflationCallback inflationCallback, RemoteViews.InteractionHandler interactionHandler, boolean z5, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, NotificationRowContentBinderLogger notificationRowContentBinderLogger, boolean z6) {
            this.mEntry = notificationEntry;
            this.mRow = expandableNotificationRow;
            this.mInflationExecutor = executor;
            this.mInflateSynchronously = z;
            this.mReInflateFlags = i;
            this.mRemoteViewCache = notifRemoteViewCache;
            this.mSmartRepliesInflater = smartReplyStateInflater;
            this.mContext = expandableNotificationRow.getContext();
            this.mIsMinimized = z2;
            this.mUsesIncreasedHeight = z3;
            this.mUsesIncreasedHeadsUpHeight = z4;
            this.mRemoteViewClickHandler = interactionHandler;
            this.mCallback = inflationCallback;
            this.mConversationProcessor = conversationNotificationProcessor;
            this.mNotifLayoutInflaterFactoryProvider = provider;
            this.mHeadsUpStyleProvider = headsUpStyleProvider;
            this.mLogger = notificationRowContentBinderLogger;
            this.mIsAllowPrivateNotificationsWhenUnsecure = z6;
            notificationEntry.abortTask();
            notificationEntry.mRunningTask = this;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(InflationProgress inflationProgress) {
            Trace.endAsyncSection("NotificationContentInflater.AsyncInflationTask", System.identityHashCode(this));
            Exception exc = this.mError;
            if (exc == null) {
                this.mCancellationSignal = NotificationContentInflater.apply(this.mInflationExecutor, this.mInflateSynchronously, this.mIsMinimized, inflationProgress, this.mReInflateFlags, this.mRemoteViewCache, this.mEntry, this.mRow, this.mRemoteViewClickHandler, this, this.mLogger);
            } else {
                handleError(exc);
            }
        }
    }

    class InflationProgress {
        public InflatedSmartReplyViewHolder expandedInflatedSmartReplies;
        public InflatedSmartReplyViewHolder headsUpInflatedSmartReplies;
        public CharSequence headsUpStatusBarText;
        public CharSequence headsUpStatusBarTextPublic;
        public View inflatedContentView;
        public View inflatedExpandedView;
        public View inflatedHeadsUpView;
        public View inflatedPublicView;
        public InflatedSmartReplyState inflatedSmartReplyState;
        public RemoteViews newContentView;
        public RemoteViews newExpandedView;
        public RemoteViews newHeadsUpView;
        public RemoteViews newPublicView;
        Context packageContext;
    }

    public NotificationContentInflater(NotifRemoteViewCache notifRemoteViewCache, NotificationRemoteInputManager notificationRemoteInputManager, ConversationNotificationProcessor conversationNotificationProcessor, MediaFeatureFlag mediaFeatureFlag, Executor executor, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        int i = NotificationRowContentBinderRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Flags.FEATURE_FLAGS.getClass();
        this.mRemoteViewCache = notifRemoteViewCache;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mConversationProcessor = conversationNotificationProcessor;
        this.mIsMediaInQS = Utils.useQsMediaPlayer(mediaFeatureFlag.context);
        this.mInflationExecutor = executor;
        this.mSmartReplyStateInflater = smartReplyStateInflater;
        this.mNotifLayoutInflaterFactoryProvider = provider;
        this.mHeadsUpStyleProvider = headsUpStyleProvider;
        this.mLogger = notificationRowContentBinderLogger;
    }

    public static CancellationSignal apply(Executor executor, boolean z, boolean z2, final InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, RemoteViews.InteractionHandler interactionHandler, NotificationRowContentBinder.InflationCallback inflationCallback, final NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        HashMap hashMap;
        NotificationContentView notificationContentView;
        NotificationContentView notificationContentView2;
        int i2;
        RemoteViews remoteViews;
        RemoteViews remoteViews2;
        Trace.beginAsyncSection("NotificationContentInflater#apply", System.identityHashCode(expandableNotificationRow));
        NotificationContentView notificationContentView3 = expandableNotificationRow.mPrivateLayout;
        NotificationContentView notificationContentView4 = expandableNotificationRow.mPublicLayout;
        HashMap hashMap2 = new HashMap();
        if ((i & 1) != 0) {
            NotifRemoteViewCacheImpl notifRemoteViewCacheImpl = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
            boolean z3 = !canReapplyRemoteView(inflationProgress.newContentView, notifRemoteViewCacheImpl.getCachedView(notificationEntry, 1));
            ApplyCallback applyCallback = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.1
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final RemoteViews getRemoteView() {
                    return inflationProgress.newContentView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final void setResultView(View view) {
                    NotificationRowContentBinderLogger.this.logAsyncTaskProgress(notificationEntry, "contracted view applied");
                    inflationProgress.inflatedContentView = view;
                }
            };
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "applying contracted view");
            hashMap = hashMap2;
            notificationContentView = notificationContentView4;
            notificationContentView2 = notificationContentView3;
            applyRemoteView(executor, z, z2, inflationProgress, i, 1, notifRemoteViewCacheImpl, notificationEntry, expandableNotificationRow, z3, interactionHandler, inflationCallback, notificationContentView2, notificationContentView3.mContractedChild, notificationContentView3.getVisibleWrapper(0), hashMap, applyCallback, notificationRowContentBinderLogger);
            i2 = 2;
        } else {
            hashMap = hashMap2;
            notificationContentView = notificationContentView4;
            notificationContentView2 = notificationContentView3;
            i2 = 2;
        }
        if ((i & 2) != 0 && (remoteViews2 = inflationProgress.newExpandedView) != null) {
            NotifRemoteViewCacheImpl notifRemoteViewCacheImpl2 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
            boolean z4 = !canReapplyRemoteView(remoteViews2, notifRemoteViewCacheImpl2.getCachedView(notificationEntry, i2));
            ApplyCallback applyCallback2 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.2
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final RemoteViews getRemoteView() {
                    return inflationProgress.newExpandedView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final void setResultView(View view) {
                    NotificationRowContentBinderLogger.this.logAsyncTaskProgress(notificationEntry, "expanded view applied");
                    inflationProgress.inflatedExpandedView = view;
                }
            };
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "applying expanded view");
            NotificationContentView notificationContentView5 = notificationContentView2;
            notificationContentView2 = notificationContentView5;
            applyRemoteView(executor, z, z2, inflationProgress, i, 2, notifRemoteViewCacheImpl2, notificationEntry, expandableNotificationRow, z4, interactionHandler, inflationCallback, notificationContentView2, notificationContentView5.mExpandedChild, notificationContentView5.getVisibleWrapper(1), hashMap, applyCallback2, notificationRowContentBinderLogger);
        }
        if ((i & 4) != 0 && (remoteViews = inflationProgress.newHeadsUpView) != null) {
            NotifRemoteViewCacheImpl notifRemoteViewCacheImpl3 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
            boolean z5 = !canReapplyRemoteView(remoteViews, notifRemoteViewCacheImpl3.getCachedView(notificationEntry, 4));
            ApplyCallback applyCallback3 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.3
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final RemoteViews getRemoteView() {
                    return inflationProgress.newHeadsUpView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final void setResultView(View view) {
                    NotificationRowContentBinderLogger.this.logAsyncTaskProgress(notificationEntry, "heads up view applied");
                    inflationProgress.inflatedHeadsUpView = view;
                }
            };
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "applying heads up view");
            NotificationContentView notificationContentView6 = notificationContentView2;
            applyRemoteView(executor, z, z2, inflationProgress, i, 4, notifRemoteViewCacheImpl3, notificationEntry, expandableNotificationRow, z5, interactionHandler, inflationCallback, notificationContentView6, notificationContentView6.mHeadsUpChild, notificationContentView6.getVisibleWrapper(2), hashMap, applyCallback3, notificationRowContentBinderLogger);
        }
        if ((i & 8) != 0) {
            NotifRemoteViewCacheImpl notifRemoteViewCacheImpl4 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
            boolean z6 = !canReapplyRemoteView(inflationProgress.newPublicView, notifRemoteViewCacheImpl4.getCachedView(notificationEntry, 8));
            ApplyCallback applyCallback4 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.4
                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final RemoteViews getRemoteView() {
                    return inflationProgress.newPublicView;
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationContentInflater.ApplyCallback
                public final void setResultView(View view) {
                    NotificationRowContentBinderLogger.this.logAsyncTaskProgress(notificationEntry, "public view applied");
                    inflationProgress.inflatedPublicView = view;
                }
            };
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "applying public view");
            NotificationContentView notificationContentView7 = notificationContentView;
            applyRemoteView(executor, z, z2, inflationProgress, i, 8, notifRemoteViewCacheImpl4, notificationEntry, expandableNotificationRow, z6, interactionHandler, inflationCallback, notificationContentView7, notificationContentView7.mContractedChild, notificationContentView7.getVisibleWrapper(0), hashMap, applyCallback4, notificationRowContentBinderLogger);
        }
        int i3 = AsyncGroupHeaderViewInflation.$r8$clinit;
        Flags.notificationAsyncGroupHeaderInflation();
        finishIfDone(inflationProgress, i, notifRemoteViewCache, hashMap, inflationCallback, notificationEntry, expandableNotificationRow, notificationRowContentBinderLogger);
        CancellationSignal cancellationSignal = new CancellationSignal();
        final HashMap hashMap3 = hashMap;
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda0
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                NotificationRowContentBinderLogger notificationRowContentBinderLogger2 = NotificationRowContentBinderLogger.this;
                NotificationEntry notificationEntry2 = notificationEntry;
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                HashMap hashMap4 = hashMap3;
                notificationRowContentBinderLogger2.logAsyncTaskProgress(notificationEntry2, "apply cancelled");
                Trace.endAsyncSection("NotificationContentInflater#apply", System.identityHashCode(expandableNotificationRow2));
                hashMap4.values().forEach(new NotificationContentInflater$$ExternalSyntheticLambda1());
            }
        });
        return cancellationSignal;
    }

    public static void applyRemoteView(Executor executor, boolean z, boolean z2, InflationProgress inflationProgress, int i, int i2, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, boolean z3, RemoteViews.InteractionHandler interactionHandler, NotificationRowContentBinder.InflationCallback inflationCallback, ViewGroup viewGroup, View view, NotificationViewWrapper notificationViewWrapper, HashMap<Integer, CancellationSignal> hashMap, ApplyCallback applyCallback, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        RemoteViews remoteView = applyCallback.getRemoteView();
        if (!z) {
            RemoteViews.OnViewAppliedListener onViewAppliedListener = new RemoteViews.OnViewAppliedListener(notificationEntry, hashMap, inflationCallback, notificationRowContentBinderLogger, i2, z3, applyCallback, notificationViewWrapper, inflationProgress, z2, i, notifRemoteViewCache, view, remoteView, viewGroup, interactionHandler) { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.7
                public final /* synthetic */ ApplyCallback val$applyCallback;
                public final /* synthetic */ NotificationRowContentBinder.InflationCallback val$callback;
                public final /* synthetic */ NotificationEntry val$entry;
                public final /* synthetic */ View val$existingView;
                public final /* synthetic */ NotificationViewWrapper val$existingWrapper;
                public final /* synthetic */ int val$inflationId;
                public final /* synthetic */ boolean val$isMinimized;
                public final /* synthetic */ boolean val$isNewView;
                public final /* synthetic */ NotificationRowContentBinderLogger val$logger;
                public final /* synthetic */ RemoteViews val$newContentView;
                public final /* synthetic */ ViewGroup val$parentLayout;
                public final /* synthetic */ int val$reInflateFlags;
                public final /* synthetic */ NotifRemoteViewCache val$remoteViewCache;
                public final /* synthetic */ RemoteViews.InteractionHandler val$remoteViewClickHandler;
                public final /* synthetic */ InflationProgress val$result;
                public final /* synthetic */ HashMap val$runningInflations;

                {
                    this.val$reInflateFlags = i;
                    this.val$remoteViewCache = notifRemoteViewCache;
                    this.val$existingView = view;
                    this.val$newContentView = remoteView;
                    this.val$parentLayout = viewGroup;
                    this.val$remoteViewClickHandler = interactionHandler;
                }

                public final void onError(Exception exc) {
                    try {
                        View view2 = this.val$existingView;
                        if (this.val$isNewView) {
                            view2 = this.val$newContentView.apply(this.val$result.packageContext, this.val$parentLayout, this.val$remoteViewClickHandler);
                        } else {
                            this.val$newContentView.reapply(this.val$result.packageContext, view2, this.val$remoteViewClickHandler);
                        }
                        Log.wtf("NotifContentInflater", "Async Inflation failed but normal inflation finished normally.", exc);
                        onViewApplied(view2);
                    } catch (Exception unused) {
                        this.val$runningInflations.remove(Integer.valueOf(this.val$inflationId));
                        NotificationContentInflater.handleInflationError(this.val$runningInflations, exc, ExpandableNotificationRow.this.mEntry, this.val$callback, this.val$logger, "applying view");
                    }
                }

                public final void onViewApplied(View view2) {
                    CachingIconView findViewById;
                    Drawable semGetApplicationIconForIconTray;
                    String isValidView = NotificationContentInflater.isValidView(view2, this.val$entry, ExpandableNotificationRow.this.getResources());
                    if (isValidView != null) {
                        NotificationContentInflater.handleInflationError(this.val$runningInflations, new InflationException(isValidView), ExpandableNotificationRow.this.mEntry, this.val$callback, this.val$logger, "applied invalid view");
                        this.val$runningInflations.remove(Integer.valueOf(this.val$inflationId));
                        return;
                    }
                    if (this.val$entry.isOngoingAcitivty()) {
                        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                        String str = this.val$entry.mKey;
                        ongoingActivityDataHelper.getClass();
                        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
                        if (ongoingActivityDataByKey != null) {
                            OngoingActivityDataHelper.updateOngoingChronometerText(view2);
                            String str2 = ongoingActivityDataByKey.mChronometerTag;
                            if (str2 != null) {
                                OngoingActivityDataHelper.setChronometerWidth(view2, str2);
                            }
                        }
                    }
                    if (this.val$isNewView) {
                        this.val$applyCallback.setResultView(view2);
                    } else {
                        NotificationViewWrapper notificationViewWrapper2 = this.val$existingWrapper;
                        if (notificationViewWrapper2 != null) {
                            notificationViewWrapper2.onReinflated();
                        }
                    }
                    this.val$runningInflations.remove(Integer.valueOf(this.val$inflationId));
                    NotificationContentInflater.finishIfDone(this.val$result, this.val$reInflateFlags, this.val$remoteViewCache, this.val$runningInflations, this.val$callback, this.val$entry, ExpandableNotificationRow.this, this.val$logger);
                    final NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
                    if ((view2.findViewById(R.id.icon) instanceof CachingIconView) && (findViewById = view2.findViewById(R.id.icon)) != null) {
                        Pools.SimplePool simplePool = ImageTransformState.sInstancePool;
                        findViewById.setTag(com.android.systemui.R.id.image_icon_tag, ExpandableNotificationRow.this.mEntry.mSbn.getNotification().getSmallIcon());
                        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                            try {
                                PackageManager packageManager = ExpandableNotificationRow.this.getContext().getPackageManager();
                                String packageName = ExpandableNotificationRow.this.mEntry.mSbn.getPackageName();
                                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 4202624);
                                boolean z4 = (packageName.equals("android") || packageName.equals("com.android.systemui") || applicationInfo.icon == 0) ? false : true;
                                if (z4) {
                                    z4 = !this.val$entry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon");
                                }
                                if (z4) {
                                    if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isColorThemeAppIconSettingsOn()) {
                                        List<LauncherActivityInfo> activityList = ((LauncherApps) ExpandableNotificationRow.this.getContext().getSystemService(LauncherApps.class)).getActivityList(packageName, UserHandle.getUserHandleForUid(applicationInfo.uid));
                                        semGetApplicationIconForIconTray = !activityList.isEmpty() ? activityList.get(0).semGetBadgedIconForIconTray(ExpandableNotificationRow.this.getContext().getResources().getDisplayMetrics().densityDpi) : packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                                    } else {
                                        semGetApplicationIconForIconTray = packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                                    }
                                    findViewById.setColorFilter((ColorFilter) null);
                                    findViewById.setBackground((Drawable) null);
                                    findViewById.setPadding(0, 0, 0, 0);
                                    findViewById.setImageDrawable(semGetApplicationIconForIconTray);
                                    findViewById.setTag(com.android.systemui.R.id.use_app_icon, Boolean.TRUE);
                                    if (view2 instanceof ConversationLayout) {
                                        notificationColorPicker.applyShawdow(view2);
                                    }
                                } else {
                                    ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateSmallIcon(view2, ExpandableNotificationRow.this, findViewById);
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateSmallIcon(view2, ExpandableNotificationRow.this, findViewById);
                        }
                    }
                    ExpandableNotificationRow expandableNotificationRow2 = ExpandableNotificationRow.this;
                    if (expandableNotificationRow2.mAnimationRunning) {
                        expandableNotificationRow2.setAnimationRunning(true);
                    } else {
                        expandableNotificationRow2.setAnimationRunning(false);
                    }
                    Optional.ofNullable(ExpandableNotificationRow.this).filter(new NotificationContentInflater$7$$ExternalSyntheticLambda0()).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$7$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) obj;
                            NotificationColorPicker.this.updateAllTextViewColors(expandableNotificationRow3, expandableNotificationRow3.mDimmed);
                        }
                    });
                    ExpandableNotificationRow expandableNotificationRow3 = ExpandableNotificationRow.this;
                    notificationColorPicker.getClass();
                    if (NotificationColorPicker.isNeedToUpdated(expandableNotificationRow3)) {
                        ExpandableNotificationRow expandableNotificationRow4 = ExpandableNotificationRow.this;
                        if (expandableNotificationRow4.mDimmed) {
                            notificationColorPicker.updateBig(view2, notificationColorPicker.getAppPrimaryColor(expandableNotificationRow4), notificationColorPicker.isGrayScaleIcon(ExpandableNotificationRow.this), this.val$existingWrapper, true, ExpandableNotificationRow.this);
                        }
                    }
                    ExpandableNotificationRow expandableNotificationRow5 = ExpandableNotificationRow.this;
                    if (expandableNotificationRow5.mIsPinned) {
                        ExpandableNotificationRow.this.applyHeadsUpBackground(NotificationColorPicker.isCustom(expandableNotificationRow5));
                    }
                }

                public final void onViewInflated(View view2) {
                    if (view2 instanceof ImageMessageConsumer) {
                        ((ImageMessageConsumer) view2).setImageResolver(ExpandableNotificationRow.this.mImageResolver);
                    }
                }
            };
            hashMap.put(Integer.valueOf(i2), z3 ? remoteView.applyAsync(inflationProgress.packageContext, viewGroup, executor, onViewAppliedListener, interactionHandler) : remoteView.reapplyAsync(inflationProgress.packageContext, view, executor, onViewAppliedListener, interactionHandler));
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

    public static boolean canReapplyRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
        if (remoteViews == null && remoteViews2 == null) {
            return true;
        }
        return (remoteViews == null || remoteViews2 == null || remoteViews2.getPackage() == null || remoteViews.getPackage() == null || !remoteViews.getPackage().equals(remoteViews2.getPackage()) || remoteViews.getLayoutId() != remoteViews2.getLayoutId() || remoteViews2.hasFlags(1)) ? false : true;
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
            if (view != null) {
                notificationContentView.setContractedChild(view);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 1, inflationProgress.newContentView);
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                if (notifRemoteViewCacheImpl.hasCachedView(notificationEntry, 1)) {
                    notifRemoteViewCacheImpl.putCachedView(notificationEntry, 1, inflationProgress.newContentView);
                }
            }
        }
        expandableNotificationRow.mIsCustomNotification = isCustomNotification(notificationEntry.mSbn.getNotification(), notificationContentView.mContractedChild, notificationEntry.mSbn.getNotification().contentView);
        if ((i & 2) != 0) {
            View view2 = inflationProgress.inflatedExpandedView;
            if (view2 != null) {
                notificationContentView.setExpandedChild(view2);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 2, inflationProgress.newExpandedView);
            } else if (inflationProgress.newExpandedView == null) {
                notificationContentView.setExpandedChild(null);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).removeCachedView(notificationEntry, 2);
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl2 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                if (notifRemoteViewCacheImpl2.hasCachedView(notificationEntry, 2)) {
                    notifRemoteViewCacheImpl2.putCachedView(notificationEntry, 2, inflationProgress.newExpandedView);
                }
            }
            RemoteViews remoteViews = inflationProgress.newExpandedView;
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
            expandableNotificationRow.mIsCustomBigNotification = isCustomNotification(notificationEntry.mSbn.getNotification(), notificationContentView.mExpandedChild, notificationEntry.mSbn.getNotification().bigContentView);
        }
        if ((i & 4) != 0) {
            View view3 = inflationProgress.inflatedHeadsUpView;
            if (view3 != null) {
                notificationContentView.setHeadsUpChild(view3);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 4, inflationProgress.newHeadsUpView);
            } else if (inflationProgress.newHeadsUpView == null) {
                notificationContentView.setHeadsUpChild(null);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).removeCachedView(notificationEntry, 4);
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl3 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                if (notifRemoteViewCacheImpl3.hasCachedView(notificationEntry, 4)) {
                    notifRemoteViewCacheImpl3.putCachedView(notificationEntry, 4, inflationProgress.newHeadsUpView);
                }
            }
            if (inflationProgress.newHeadsUpView != null) {
                notificationContentView.setHeadsUpInflatedSmartReplies(inflationProgress.headsUpInflatedSmartReplies);
            } else {
                notificationContentView.setHeadsUpInflatedSmartReplies(null);
            }
            expandableNotificationRow.mIsCustomHeadsUpNotification = isCustomNotification(notificationEntry.mSbn.getNotification(), notificationContentView.mHeadsUpChild, notificationEntry.mSbn.getNotification().headsUpContentView);
        }
        int i2 = AsyncHybridViewInflation.$r8$clinit;
        Flags.notificationAsyncHybridViewInflation();
        notificationContentView.mCurrentSmartReplyState = inflationProgress.inflatedSmartReplyState;
        if ((i & 8) != 0) {
            View view4 = inflationProgress.inflatedPublicView;
            if (view4 != null) {
                notificationContentView2.setContractedChild(view4);
                ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, 8, inflationProgress.newPublicView);
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl4 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                if (notifRemoteViewCacheImpl4.hasCachedView(notificationEntry, 8)) {
                    notifRemoteViewCacheImpl4.putCachedView(notificationEntry, 8, inflationProgress.newPublicView);
                }
            }
            if (notificationEntry.mSbn.getNotification().publicVersion != null) {
                expandableNotificationRow.mIsCustomPublicNotification = isCustomNotification(notificationEntry.mSbn.getNotification().publicVersion, notificationContentView2.mContractedChild, notificationEntry.mSbn.getNotification().publicVersion.contentView);
            }
        }
        int i3 = AsyncGroupHeaderViewInflation.$r8$clinit;
        Flags.notificationAsyncGroupHeaderInflation();
        CharSequence charSequence = inflationProgress.headsUpStatusBarText;
        int i4 = NotificationRowContentBinderRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Flags.FEATURE_FLAGS.getClass();
        notificationEntry.mHeadsUpStatusBarText.setValue(charSequence);
        CharSequence charSequence2 = inflationProgress.headsUpStatusBarTextPublic;
        Flags.FEATURE_FLAGS.getClass();
        notificationEntry.mHeadsUpStatusBarTextPublic.setValue(charSequence2);
        Trace.endAsyncSection("NotificationContentInflater#apply", System.identityHashCode(expandableNotificationRow));
        if (inflationCallback != null) {
            inflationCallback.onAsyncInflationFinished(notificationEntry);
        }
        return true;
    }

    public static void handleInflationError(HashMap hashMap, Exception exc, NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationRowContentBinderLogger notificationRowContentBinderLogger, String str) {
        Assert.isMainThread();
        notificationRowContentBinderLogger.logAsyncTaskException(notificationEntry, str, exc);
        hashMap.values().forEach(new NotificationContentInflater$$ExternalSyntheticLambda1());
        if (inflationCallback != null) {
            inflationCallback.handleInflationException(notificationEntry, exc);
        }
    }

    public static void inflateSmartReplyViews(InflationProgress inflationProgress, int i, NotificationEntry notificationEntry, Context context, Context context2, InflatedSmartReplyState inflatedSmartReplyState, SmartReplyStateInflater smartReplyStateInflater, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        boolean z = ((i & 1) == 0 || inflationProgress.newContentView == null) ? false : true;
        boolean z2 = ((i & 2) == 0 || inflationProgress.newExpandedView == null) ? false : true;
        boolean z3 = ((i & 4) == 0 || inflationProgress.newHeadsUpView == null) ? false : true;
        if (z || z2 || z3) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "inflating contracted smart reply state");
            inflationProgress.inflatedSmartReplyState = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyState(notificationEntry);
        }
        if (z2) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "inflating expanded smart reply state");
            inflationProgress.expandedInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        }
        if (z3) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "inflating heads up smart reply state");
            inflationProgress.headsUpInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        }
    }

    public static boolean isCustomNotification(Notification notification2, View view, RemoteViews remoteViews) {
        if (view == null) {
            return false;
        }
        boolean z = remoteViews != null;
        Class notificationStyle = notification2.getNotificationStyle();
        return Notification.DecoratedCustomViewStyle.class.equals(notificationStyle) || Notification.DecoratedMediaCustomViewStyle.class.equals(notificationStyle) || z || !(view.getId() == 16909822 || view.getId() == 16909398);
    }

    public static String isValidView(View view, NotificationEntry notificationEntry, Resources resources) {
        if (notificationEntry.targetSdk < 31) {
            Notification notification2 = notificationEntry.mSbn.getNotification();
            if (notification2.contentView != null || notification2.bigContentView != null || notification2.headsUpContentView != null) {
                boolean isEnabled = Trace.isEnabled();
                if (isEnabled) {
                    TraceUtilsKt.beginSlice("NotificationContentInflater#satisfiesMinHeightRequirement");
                }
                try {
                    view.measure(View.MeasureSpec.makeMeasureSpec(resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_validation_reference_width), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
                    r2 = view.getMeasuredHeight() >= resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_validation_minimum_allowed_height);
                } finally {
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            }
        }
        if (r2) {
            return null;
        }
        return "inflated notification does not meet minimum height requirement";
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final void bindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i, NotificationRowContentBinder.BindParams bindParams, boolean z, RowContentBindStage.AnonymousClass1 anonymousClass1) {
        AsyncInflationTask asyncInflationTask;
        InflationProgress inflationProgress;
        SparseArray sparseArray;
        expandableNotificationRow.getClass();
        NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.mLogger;
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
        if (z && (sparseArray = (SparseArray) ((ArrayMap) ((NotifRemoteViewCacheImpl) this.mRemoteViewCache).mNotifCachedContentViews).get(notificationEntry)) != null) {
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
        int i2 = AsyncHybridViewInflation.$r8$clinit;
        Flags.notificationAsyncHybridViewInflation();
        AsyncInflationTask asyncInflationTask2 = new AsyncInflationTask(this.mInflationExecutor, this.mInflateSynchronously, i, this.mRemoteViewCache, notificationEntry, this.mConversationProcessor, expandableNotificationRow, bindParams.isMinimized, bindParams.usesIncreasedHeight, bindParams.usesIncreasedHeadsUpHeight, anonymousClass1, this.mRemoteInputManager.mInteractionHandler, this.mIsMediaInQS, this.mSmartReplyStateInflater, this.mNotifLayoutInflaterFactoryProvider, this.mHeadsUpStyleProvider, this.mLogger, NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE ? ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAllowPrivateNotificationsWhenUnsecure(ActivityManager.getCurrentUser()) : false);
        if (!this.mInflateSynchronously) {
            asyncInflationTask2.executeOnExecutor(this.mInflationExecutor, new Void[0]);
            return;
        }
        Void[] voidArr = new Void[0];
        int i3 = TraceUtils.$r8$clinit;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NotificationContentInflater.AsyncInflationTask#doInBackground");
        }
        try {
            try {
                inflationProgress = asyncInflationTask2.doInBackgroundInternal();
                asyncInflationTask = asyncInflationTask2;
            } catch (Exception e) {
                asyncInflationTask = asyncInflationTask2;
                asyncInflationTask.mError = e;
                asyncInflationTask.mLogger.logAsyncTaskException(asyncInflationTask.mEntry, "inflating", e);
                inflationProgress = null;
            }
            asyncInflationTask.onPostExecute(inflationProgress);
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final boolean cancelBind(NotificationEntry notificationEntry) {
        boolean abortTask = notificationEntry.abortTask();
        if (abortTask) {
            NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.mLogger;
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

    public InflationProgress inflateNotificationViews(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinder.BindParams bindParams, boolean z, int i, Notification.Builder builder, Context context, SmartReplyStateInflater smartReplyStateInflater) {
        NotificationContentInflater$$ExternalSyntheticLambda2 notificationContentInflater$$ExternalSyntheticLambda2 = new NotificationContentInflater$$ExternalSyntheticLambda2(expandableNotificationRow, i, this.mLogger, bindParams.isMinimized, builder, bindParams.usesIncreasedHeight, this.mHeadsUpStyleProvider, bindParams.usesIncreasedHeadsUpHeight, NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE ? ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAllowPrivateNotificationsWhenUnsecure(ActivityManager.getCurrentUser()) : false, this.mNotifLayoutInflaterFactoryProvider, context);
        int i2 = TraceUtils.$r8$clinit;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NotificationContentInflater.createRemoteViews");
        }
        try {
            Object invoke = notificationContentInflater$$ExternalSyntheticLambda2.invoke();
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            InflationProgress inflationProgress = (InflationProgress) invoke;
            inflateSmartReplyViews(inflationProgress, i, notificationEntry, expandableNotificationRow.getContext(), context, expandableNotificationRow.mPrivateLayout.mCurrentSmartReplyState, smartReplyStateInflater, this.mLogger);
            int i3 = AsyncHybridViewInflation.$r8$clinit;
            Flags.notificationAsyncHybridViewInflation();
            apply(this.mInflationExecutor, z, bindParams.isMinimized, inflationProgress, i, this.mRemoteViewCache, notificationEntry, expandableNotificationRow, this.mRemoteInputManager.mInteractionHandler, null, this.mLogger);
            return inflationProgress;
        } finally {
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public void setInflateSynchronously(boolean z) {
        this.mInflateSynchronously = z;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final void unbindContent(final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, int i) {
        final int i2 = 2;
        final int i3 = 0;
        final int i4 = 1;
        NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.mLogger;
        notificationRowContentBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowContentBinderLogger$logUnbinding$2 notificationRowContentBinderLogger$logUnbinding$2 = NotificationRowContentBinderLogger$logUnbinding$2.INSTANCE;
        LogBuffer logBuffer = notificationRowContentBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$logUnbinding$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
        int i5 = 1;
        while (i != 0) {
            if ((i & i5) != 0) {
                if (i5 == 1) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(0, new Runnable(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda3
                        public final /* synthetic */ NotificationContentInflater f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i3) {
                                case 0:
                                    NotificationContentInflater notificationContentInflater = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                                    NotificationEntry notificationEntry2 = notificationEntry;
                                    notificationContentInflater.getClass();
                                    expandableNotificationRow2.mPrivateLayout.setContractedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater.mRemoteViewCache).removeCachedView(notificationEntry2, 1);
                                    break;
                                case 1:
                                    NotificationContentInflater notificationContentInflater2 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow;
                                    NotificationEntry notificationEntry3 = notificationEntry;
                                    notificationContentInflater2.getClass();
                                    expandableNotificationRow3.mPrivateLayout.setExpandedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater2.mRemoteViewCache).removeCachedView(notificationEntry3, 2);
                                    break;
                                case 2:
                                    NotificationContentInflater notificationContentInflater3 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow;
                                    NotificationEntry notificationEntry4 = notificationEntry;
                                    notificationContentInflater3.getClass();
                                    expandableNotificationRow4.mPrivateLayout.setHeadsUpChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater3.mRemoteViewCache).removeCachedView(notificationEntry4, 4);
                                    expandableNotificationRow4.mPrivateLayout.setHeadsUpInflatedSmartReplies(null);
                                    break;
                                default:
                                    NotificationContentInflater notificationContentInflater4 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow5 = expandableNotificationRow;
                                    NotificationEntry notificationEntry5 = notificationEntry;
                                    notificationContentInflater4.getClass();
                                    expandableNotificationRow5.mPublicLayout.setContractedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater4.mRemoteViewCache).removeCachedView(notificationEntry5, 8);
                                    break;
                            }
                        }
                    });
                } else if (i5 == 2) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(1, new Runnable(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda3
                        public final /* synthetic */ NotificationContentInflater f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i4) {
                                case 0:
                                    NotificationContentInflater notificationContentInflater = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                                    NotificationEntry notificationEntry2 = notificationEntry;
                                    notificationContentInflater.getClass();
                                    expandableNotificationRow2.mPrivateLayout.setContractedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater.mRemoteViewCache).removeCachedView(notificationEntry2, 1);
                                    break;
                                case 1:
                                    NotificationContentInflater notificationContentInflater2 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow;
                                    NotificationEntry notificationEntry3 = notificationEntry;
                                    notificationContentInflater2.getClass();
                                    expandableNotificationRow3.mPrivateLayout.setExpandedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater2.mRemoteViewCache).removeCachedView(notificationEntry3, 2);
                                    break;
                                case 2:
                                    NotificationContentInflater notificationContentInflater3 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow;
                                    NotificationEntry notificationEntry4 = notificationEntry;
                                    notificationContentInflater3.getClass();
                                    expandableNotificationRow4.mPrivateLayout.setHeadsUpChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater3.mRemoteViewCache).removeCachedView(notificationEntry4, 4);
                                    expandableNotificationRow4.mPrivateLayout.setHeadsUpInflatedSmartReplies(null);
                                    break;
                                default:
                                    NotificationContentInflater notificationContentInflater4 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow5 = expandableNotificationRow;
                                    NotificationEntry notificationEntry5 = notificationEntry;
                                    notificationContentInflater4.getClass();
                                    expandableNotificationRow5.mPublicLayout.setContractedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater4.mRemoteViewCache).removeCachedView(notificationEntry5, 8);
                                    break;
                            }
                        }
                    });
                } else if (i5 == 4) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(2, new Runnable(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda3
                        public final /* synthetic */ NotificationContentInflater f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i2) {
                                case 0:
                                    NotificationContentInflater notificationContentInflater = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                                    NotificationEntry notificationEntry2 = notificationEntry;
                                    notificationContentInflater.getClass();
                                    expandableNotificationRow2.mPrivateLayout.setContractedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater.mRemoteViewCache).removeCachedView(notificationEntry2, 1);
                                    break;
                                case 1:
                                    NotificationContentInflater notificationContentInflater2 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow;
                                    NotificationEntry notificationEntry3 = notificationEntry;
                                    notificationContentInflater2.getClass();
                                    expandableNotificationRow3.mPrivateLayout.setExpandedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater2.mRemoteViewCache).removeCachedView(notificationEntry3, 2);
                                    break;
                                case 2:
                                    NotificationContentInflater notificationContentInflater3 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow;
                                    NotificationEntry notificationEntry4 = notificationEntry;
                                    notificationContentInflater3.getClass();
                                    expandableNotificationRow4.mPrivateLayout.setHeadsUpChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater3.mRemoteViewCache).removeCachedView(notificationEntry4, 4);
                                    expandableNotificationRow4.mPrivateLayout.setHeadsUpInflatedSmartReplies(null);
                                    break;
                                default:
                                    NotificationContentInflater notificationContentInflater4 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow5 = expandableNotificationRow;
                                    NotificationEntry notificationEntry5 = notificationEntry;
                                    notificationContentInflater4.getClass();
                                    expandableNotificationRow5.mPublicLayout.setContractedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater4.mRemoteViewCache).removeCachedView(notificationEntry5, 8);
                                    break;
                            }
                        }
                    });
                } else if (i5 == 8) {
                    final int i6 = 3;
                    expandableNotificationRow.mPublicLayout.performWhenContentInactive(0, new Runnable(this) { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda3
                        public final /* synthetic */ NotificationContentInflater f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i6) {
                                case 0:
                                    NotificationContentInflater notificationContentInflater = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                                    NotificationEntry notificationEntry2 = notificationEntry;
                                    notificationContentInflater.getClass();
                                    expandableNotificationRow2.mPrivateLayout.setContractedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater.mRemoteViewCache).removeCachedView(notificationEntry2, 1);
                                    break;
                                case 1:
                                    NotificationContentInflater notificationContentInflater2 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow;
                                    NotificationEntry notificationEntry3 = notificationEntry;
                                    notificationContentInflater2.getClass();
                                    expandableNotificationRow3.mPrivateLayout.setExpandedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater2.mRemoteViewCache).removeCachedView(notificationEntry3, 2);
                                    break;
                                case 2:
                                    NotificationContentInflater notificationContentInflater3 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow;
                                    NotificationEntry notificationEntry4 = notificationEntry;
                                    notificationContentInflater3.getClass();
                                    expandableNotificationRow4.mPrivateLayout.setHeadsUpChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater3.mRemoteViewCache).removeCachedView(notificationEntry4, 4);
                                    expandableNotificationRow4.mPrivateLayout.setHeadsUpInflatedSmartReplies(null);
                                    break;
                                default:
                                    NotificationContentInflater notificationContentInflater4 = this.f$0;
                                    ExpandableNotificationRow expandableNotificationRow5 = expandableNotificationRow;
                                    NotificationEntry notificationEntry5 = notificationEntry;
                                    notificationContentInflater4.getClass();
                                    expandableNotificationRow5.mPublicLayout.setContractedChild(null);
                                    ((NotifRemoteViewCacheImpl) notificationContentInflater4.mRemoteViewCache).removeCachedView(notificationEntry5, 8);
                                    break;
                            }
                        }
                    });
                } else if (i5 == 16) {
                    int i7 = AsyncHybridViewInflation.$r8$clinit;
                    Flags.notificationAsyncHybridViewInflation();
                }
            }
            i &= ~i5;
            i5 <<= 1;
        }
    }
}
