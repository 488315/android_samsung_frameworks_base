package com.android.systemui.statusbar.notification.row;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.os.CancellationSignal;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.app.tracing.TraceUtils;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NmSummarizationUiFlag;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.promoted.PromotedNotificationContentExtractor;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import com.android.systemui.statusbar.notification.row.NotificationContentInflater;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.shared.LockscreenOtpRedaction;
import com.android.systemui.statusbar.notification.row.shared.NotificationRowContentBinderRefactor;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import java.util.HashMap;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationContentInflater implements NotificationRowContentBinder {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    abstract class ApplyCallback {
        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class InflationProgress {
        public InflatedSmartReplyViewHolder expandedInflatedSmartReplies;
        public InflatedSmartReplyViewHolder headsUpInflatedSmartReplies;
        public final View inflatedContentView;
        public final View inflatedExpandedView;
        public final View inflatedHeadsUpView;
        public final View inflatedPromotedOngoingView;
        public final View inflatedPublicView;
        public InflatedSmartReplyState inflatedSmartReplyState;
        public HybridNotificationView mInflatedSingleLineView;
        public SingleLineViewModel mInflatedSingleLineViewModel;
        public HybridNotificationView mPublicInflatedSingleLineView;
        public SingleLineViewModel mPublicInflatedSingleLineViewModel;
        public RowImageInflaterStub mRowImageInflater;
        public RemoteViews newContentView;
        public RemoteViews newExpandedView;
        public RemoteViews newHeadsUpView;
        public RemoteViews newPromotedOngoingView;
        public RemoteViews newPublicView;
        Context packageContext;
    }

    public NotificationContentInflater(NotifRemoteViewCache notifRemoteViewCache, NotificationRemoteInputManager notificationRemoteInputManager, ConversationNotificationProcessor conversationNotificationProcessor, MediaFeatureFlag mediaFeatureFlag, Executor executor, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, PromotedNotificationContentExtractor promotedNotificationContentExtractor, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        int i = NotificationRowContentBinderRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        throw new IllegalStateException("Legacy code path not supported when com.android.systemui.notification_row_content_binder_refactor is enabled.");
    }

    public static void applyRemoteView(Executor executor, boolean z, boolean z2, InflationProgress inflationProgress, int i, int i2, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, boolean z3, RemoteViews.InteractionHandler interactionHandler, NotificationRowContentBinder.InflationCallback inflationCallback, ViewGroup viewGroup, View view, NotificationViewWrapper notificationViewWrapper, HashMap<Integer, CancellationSignal> hashMap, ApplyCallback applyCallback, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
        RemoteViews remoteView = applyCallback.getRemoteView();
        if (!z) {
            RemoteViews.OnViewAppliedListener onViewAppliedListener = new RemoteViews.OnViewAppliedListener(notificationEntry, hashMap, inflationCallback, notificationRowContentBinderLogger, i2, z3, applyCallback, notificationViewWrapper, inflationProgress, z2, i, notifRemoteViewCache, view, remoteView, viewGroup, interactionHandler) { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.8
                public final /* synthetic */ ApplyCallback val$applyCallback;
                public final /* synthetic */ NotificationRowContentBinder.InflationCallback val$callback;
                public final /* synthetic */ NotificationEntry val$entry;
                public final /* synthetic */ View val$existingView;
                public final /* synthetic */ NotificationViewWrapper val$existingWrapper;
                public final /* synthetic */ int val$inflationId;
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
                        NotificationContentInflater.handleInflationError(this.val$runningInflations, exc, ExpandableNotificationRow.this, this.val$entry, this.val$callback, this.val$logger, "applying view");
                    }
                }

                /* JADX WARN: Removed duplicated region for block: B:37:0x0142 A[Catch: NameNotFoundException -> 0x0126, TryCatch #0 {NameNotFoundException -> 0x0126, blocks: (B:26:0x00d6, B:28:0x010f, B:30:0x0115, B:32:0x011d, B:37:0x0142, B:39:0x0155, B:41:0x0163, B:43:0x0169, B:44:0x018d, B:46:0x01ad, B:47:0x01b3, B:49:0x01b9, B:50:0x01bd, B:52:0x01d0, B:54:0x0184, B:55:0x0189, B:56:0x01d4, B:57:0x0129, B:59:0x0131, B:61:0x0139), top: B:25:0x00d6 }] */
                /* JADX WARN: Removed duplicated region for block: B:39:0x0155 A[Catch: NameNotFoundException -> 0x0126, TryCatch #0 {NameNotFoundException -> 0x0126, blocks: (B:26:0x00d6, B:28:0x010f, B:30:0x0115, B:32:0x011d, B:37:0x0142, B:39:0x0155, B:41:0x0163, B:43:0x0169, B:44:0x018d, B:46:0x01ad, B:47:0x01b3, B:49:0x01b9, B:50:0x01bd, B:52:0x01d0, B:54:0x0184, B:55:0x0189, B:56:0x01d4, B:57:0x0129, B:59:0x0131, B:61:0x0139), top: B:25:0x00d6 }] */
                /* JADX WARN: Removed duplicated region for block: B:56:0x01d4 A[Catch: NameNotFoundException -> 0x0126, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x0126, blocks: (B:26:0x00d6, B:28:0x010f, B:30:0x0115, B:32:0x011d, B:37:0x0142, B:39:0x0155, B:41:0x0163, B:43:0x0169, B:44:0x018d, B:46:0x01ad, B:47:0x01b3, B:49:0x01b9, B:50:0x01bd, B:52:0x01d0, B:54:0x0184, B:55:0x0189, B:56:0x01d4, B:57:0x0129, B:59:0x0131, B:61:0x0139), top: B:25:0x00d6 }] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onViewApplied(android.view.View r14) {
                    /*
                        Method dump skipped, instructions count: 951
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.NotificationContentInflater.AnonymousClass8.onViewApplied(android.view.View):void");
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
            handleInflationError(hashMap, e, expandableNotificationRow, notificationEntry, inflationCallback, notificationRowContentBinderLogger, "applying view synchronously");
            hashMap.put(Integer.valueOf(i2), new CancellationSignal());
        }
    }

    public static boolean canReapplyRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
        return (remoteViews == null && remoteViews2 == null) || !(remoteViews == null || remoteViews2 == null || remoteViews2.getPackage() == null || remoteViews.getPackage() == null || !remoteViews.getPackage().equals(remoteViews2.getPackage()) || remoteViews.getLayoutId() != remoteViews2.getLayoutId() || remoteViews2.hasFlags(1));
    }

    public static void handleInflationError(HashMap hashMap, Exception exc, ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationRowContentBinderLogger notificationRowContentBinderLogger, String str) {
        Assert.isMainThread();
        notificationRowContentBinderLogger.logAsyncTaskException(expandableNotificationRow.mLoggingKey, str, exc);
        hashMap.values().forEach(new NotificationContentInflater$$ExternalSyntheticLambda0());
        if (inflationCallback != null) {
            inflationCallback.handleInflationException(notificationEntry, exc);
        }
    }

    public static boolean isCustomNotification(Notification notification2, View view, RemoteViews remoteViews) {
        if (view == null) {
            return false;
        }
        boolean z = remoteViews != null;
        Class notificationStyle = notification2.getNotificationStyle();
        return Notification.DecoratedCustomViewStyle.class.equals(notificationStyle) || Notification.DecoratedMediaCustomViewStyle.class.equals(notificationStyle) || z || !(view.getId() == 16909884 || view.getId() == 16909441);
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
                    view.measure(View.MeasureSpec.makeMeasureSpec(resources.getDimensionPixelSize(R.dimen.notification_validation_reference_width), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
                    r2 = view.getMeasuredHeight() >= resources.getDimensionPixelSize(R.dimen.notification_validation_minimum_allowed_height);
                } finally {
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            }
        }
        if (!r2) {
            return "inflated notification does not meet minimum height requirement";
        }
        NotificationCustomContentMemoryVerifier notificationCustomContentMemoryVerifier = NotificationCustomContentMemoryVerifier.INSTANCE;
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final void bindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i, NotificationRowContentBinder.BindParams bindParams, boolean z, RowContentBindStage.AnonymousClass1 anonymousClass1) {
        expandableNotificationRow.getClass();
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final boolean cancelBind(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        boolean abortTask = notificationEntry.abortTask();
        if (!abortTask) {
            return abortTask;
        }
        String str = expandableNotificationRow.mLoggingKey;
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.statusbar.notification.row.NotificationRowContentBinderLogger, java.lang.Throwable] */
    public InflationProgress inflateNotificationViews(NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, final NotificationRowContentBinder.BindParams bindParams, boolean z, final int i, final Notification.Builder builder, final Context context, final Context context2, SmartReplyStateInflater smartReplyStateInflater) {
        NotificationRowContentBinderLogger notificationRowContentBinderLogger;
        NotificationEntry notificationEntry2;
        NotificationRowContentBinderLogger notificationRowContentBinderLogger2 = null;
        final boolean isAllowPrivateNotificationsWhenUnsecure = NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE ? ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAllowPrivateNotificationsWhenUnsecure(ActivityManager.getCurrentUser()) : false;
        final NotificationRowContentBinderLogger notificationRowContentBinderLogger3 = null;
        final HeadsUpStyleProvider headsUpStyleProvider = null;
        final NotifLayoutInflaterFactory.Provider provider = null;
        InflationProgress inflationProgress = (InflationProgress) TraceUtils.trace("NotificationContentInflater.createRemoteViews", new Function0() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2;
                RemoteViews createBigContentView;
                RemoteViews createContentView;
                RemoteViews makeLowPriorityContentView;
                Notification.Builder builder2 = builder;
                Context context3 = context;
                Context context4 = context2;
                NotificationContentInflater.InflationProgress inflationProgress2 = new NotificationContentInflater.InflationProgress();
                int i3 = i;
                int i4 = i3 & 1;
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                ImageModelIndex imageModelIndex = expandableNotificationRow2.mImageModelIndex;
                RowImageInflater.Companion.getClass();
                inflationProgress2.mRowImageInflater = RowImageInflaterStub.INSTANCE;
                NotificationRowContentBinderLogger notificationRowContentBinderLogger4 = notificationRowContentBinderLogger3;
                NotificationRowContentBinder.BindParams bindParams2 = bindParams;
                if (i4 != 0) {
                    i2 = 4;
                    notificationRowContentBinderLogger4.logAsyncTaskProgress(expandableNotificationRow2.mLoggingKey, "creating contracted remote view");
                    OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                    String str = expandableNotificationRow2.mEntry.mKey;
                    ongoingActivityDataHelper.getClass();
                    OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(str);
                    if (pendingOngoingActivityData == null) {
                        pendingOngoingActivityData = OngoingActivityDataHelper.getOngoingActivityDataByKey(expandableNotificationRow2.mEntry.mKey);
                    }
                    if (!expandableNotificationRow2.mEntry.isOngoingActivity() || pendingOngoingActivityData == null) {
                        makeLowPriorityContentView = bindParams2.isMinimized ? builder2.makeLowPriorityContentView(false) : builder2.createContentView();
                    } else if (bindParams2.isMinimized) {
                        builder2.setContentTitle(pendingOngoingActivityData.mPrimaryInfo).setContentText(pendingOngoingActivityData.mSecondaryInfo);
                        makeLowPriorityContentView = builder2.makeLowPriorityContentView(false);
                    } else {
                        boolean booleanValue = expandableNotificationRow2.mEntry.mIsRon.booleanValue();
                        boolean z2 = bindParams2.isMinimized;
                        if (booleanValue) {
                            makeLowPriorityContentView = z2 ? builder2.makeLowPriorityContentView(false) : builder2.createContentView();
                            if (makeLowPriorityContentView != null) {
                                makeLowPriorityContentView.setInt(16909884, "setBackgroundResource", 0);
                            }
                        } else {
                            makeLowPriorityContentView = pendingOngoingActivityData.mOngoingCollapsedView;
                            if (makeLowPriorityContentView == null) {
                                makeLowPriorityContentView = z2 ? builder2.makeLowPriorityContentView(false) : builder2.createContentView();
                            }
                        }
                    }
                    inflationProgress2.newContentView = makeLowPriorityContentView;
                } else {
                    i2 = 4;
                }
                RemoteViews remoteViews = null;
                if ((i3 & 2) != 0) {
                    notificationRowContentBinderLogger4.logAsyncTaskProgress(expandableNotificationRow2.mLoggingKey, "creating expanded remote view");
                    OngoingActivityDataHelper ongoingActivityDataHelper2 = OngoingActivityDataHelper.INSTANCE;
                    String str2 = expandableNotificationRow2.mEntry.mKey;
                    ongoingActivityDataHelper2.getClass();
                    OngoingActivityData pendingOngoingActivityData2 = OngoingActivityDataHelper.getPendingOngoingActivityData(str2);
                    if (pendingOngoingActivityData2 == null) {
                        pendingOngoingActivityData2 = OngoingActivityDataHelper.getOngoingActivityDataByKey(expandableNotificationRow2.mEntry.mKey);
                    }
                    if (!expandableNotificationRow2.mEntry.isOngoingActivity() || pendingOngoingActivityData2 == null) {
                        boolean z3 = bindParams2.isMinimized;
                        createBigContentView = builder2.createBigContentView();
                        if (createBigContentView == null) {
                            if (z3) {
                                createContentView = builder2.createContentView();
                                Notification.Builder.makeHeaderExpanded(createContentView);
                                createBigContentView = createContentView;
                            } else {
                                createBigContentView = null;
                            }
                        }
                        inflationProgress2.newExpandedView = createBigContentView;
                    } else {
                        if (expandableNotificationRow2.mEntry.mIsRon.booleanValue()) {
                            boolean z4 = bindParams2.isMinimized;
                            createBigContentView = builder2.createBigContentView();
                            if (createBigContentView == null) {
                                if (z4) {
                                    createBigContentView = builder2.createContentView();
                                    Notification.Builder.makeHeaderExpanded(createBigContentView);
                                } else {
                                    createBigContentView = null;
                                }
                            }
                            if (createBigContentView != null) {
                                createBigContentView.setInt(16909884, "setBackgroundResource", 0);
                            }
                        } else {
                            createBigContentView = pendingOngoingActivityData2.mOngoingENRExpandView;
                            if (createBigContentView == null) {
                                createContentView = bindParams2.isMinimized ? builder2.makeLowPriorityContentView(false) : builder2.createContentView();
                                createBigContentView = createContentView;
                            }
                        }
                        inflationProgress2.newExpandedView = createBigContentView;
                    }
                }
                if ((i3 & 256) != 0) {
                    notificationRowContentBinderLogger4.logAsyncTaskProgress(expandableNotificationRow2.mLoggingKey, "creating promoted ongoing card view");
                    OngoingActivityDataHelper ongoingActivityDataHelper3 = OngoingActivityDataHelper.INSTANCE;
                    String str3 = expandableNotificationRow2.mEntry.mKey;
                    ongoingActivityDataHelper3.getClass();
                    OngoingActivityData pendingOngoingActivityData3 = OngoingActivityDataHelper.getPendingOngoingActivityData(str3);
                    if (pendingOngoingActivityData3 == null) {
                        pendingOngoingActivityData3 = OngoingActivityDataHelper.getOngoingActivityDataByKey(expandableNotificationRow2.mEntry.mKey);
                    }
                    if (expandableNotificationRow2.mEntry.isOngoingActivity() && pendingOngoingActivityData3 != null) {
                        if (expandableNotificationRow2.mEntry.mIsRon.booleanValue()) {
                            boolean z5 = bindParams2.isMinimized;
                            RemoteViews createBigContentView2 = builder2.createBigContentView();
                            if (createBigContentView2 != null) {
                                remoteViews = createBigContentView2;
                            } else if (z5) {
                                remoteViews = builder2.createContentView();
                                Notification.Builder.makeHeaderExpanded(remoteViews);
                            }
                            if (remoteViews != null) {
                                remoteViews.setInt(16909884, "setBackgroundResource", 0);
                            }
                        } else {
                            remoteViews = pendingOngoingActivityData3.mOngoingOAExpandView;
                        }
                    }
                    inflationProgress2.newPromotedOngoingView = remoteViews;
                }
                if ((i3 & 4) != 0) {
                    notificationRowContentBinderLogger4.logAsyncTaskProgress(expandableNotificationRow2.mLoggingKey, "creating heads up remote view");
                    headsUpStyleProvider.getClass();
                    inflationProgress2.newHeadsUpView = builder2.createHeadsUpContentView();
                }
                if ((i3 & 8) != 0) {
                    notificationRowContentBinderLogger4.logAsyncTaskProgress(expandableNotificationRow2.mLoggingKey, "creating public remote view");
                    int i5 = LockscreenOtpRedaction.$r8$clinit;
                    if (bindParams2.redactionType == 2) {
                        int i6 = NotificationBundleUi.$r8$clinit;
                        Notification notification2 = expandableNotificationRow2.getEntryLegacy().mSbn.getNotification();
                        Notification.Style style = builder2.getStyle();
                        Notification.Builder builder3 = new Notification.Builder(context4, notification2.getChannelId());
                        builder3.setContentTitle(notification2.extras.getCharSequence("android.title"));
                        CharSequence string = context3.getString(R.string.redacted_otp_notification_single_line_text);
                        builder3.setWhen(notification2.getWhen());
                        if (style instanceof Notification.MessagingStyle) {
                            Notification.MessagingStyle messagingStyle = (Notification.MessagingStyle) style;
                            Notification.MessagingStyle messagingStyle2 = new Notification.MessagingStyle(messagingStyle.getUser());
                            messagingStyle2.setConversationTitle(messagingStyle.getConversationTitle());
                            messagingStyle2.setGroupConversation(false);
                            messagingStyle2.setConversationType(messagingStyle.getConversationType());
                            messagingStyle2.setShortcutIcon(messagingStyle.getShortcutIcon());
                            messagingStyle2.setBuilder(builder3);
                            Notification.MessagingStyle.Message findLatestIncomingMessage = Notification.MessagingStyle.findLatestIncomingMessage(messagingStyle.getMessages());
                            if (findLatestIncomingMessage != null) {
                                messagingStyle2.addMessage(new Notification.MessagingStyle.Message(string, findLatestIncomingMessage.getTimestamp(), findLatestIncomingMessage.getSenderPerson()));
                            }
                            builder3.setStyle(messagingStyle2);
                        } else {
                            builder3.setContentText(string);
                        }
                        builder3.setLargeIcon(notification2.getLargeIcon());
                        builder3.setSmallIcon(notification2.getSmallIcon());
                        inflationProgress2.newPublicView = builder3.createContentView();
                    } else {
                        inflationProgress2.newPublicView = builder2.makePublicContentView(bindParams2.isMinimized, isAllowPrivateNotificationsWhenUnsecure);
                    }
                }
                int i7 = AsyncGroupHeaderViewInflation.$r8$clinit;
                RemoteViews remoteViews2 = inflationProgress2.newContentView;
                NotifLayoutInflaterFactory.Provider provider2 = provider;
                NotifLayoutInflaterFactory provide = provider2.provide(expandableNotificationRow2, 1);
                if (remoteViews2 != null) {
                    remoteViews2.setLayoutInflaterFactory(provide);
                }
                RemoteViews remoteViews3 = inflationProgress2.newExpandedView;
                NotifLayoutInflaterFactory provide2 = provider2.provide(expandableNotificationRow2, 2);
                if (remoteViews3 != null) {
                    remoteViews3.setLayoutInflaterFactory(provide2);
                }
                RemoteViews remoteViews4 = inflationProgress2.newHeadsUpView;
                NotifLayoutInflaterFactory provide3 = provider2.provide(expandableNotificationRow2, i2);
                if (remoteViews4 != null) {
                    remoteViews4.setLayoutInflaterFactory(provide3);
                }
                RemoteViews remoteViews5 = inflationProgress2.newPublicView;
                NotifLayoutInflaterFactory provide4 = provider2.provide(expandableNotificationRow2, 8);
                if (remoteViews5 != null) {
                    remoteViews5.setLayoutInflaterFactory(provide4);
                }
                inflationProgress2.packageContext = context4;
                builder2.getHeadsUpStatusBarText(false);
                builder2.getHeadsUpStatusBarText(true);
                return inflationProgress2;
            }
        });
        Context context3 = expandableNotificationRow.getContext();
        InflatedSmartReplyState inflatedSmartReplyState = expandableNotificationRow.mPrivateLayout.mCurrentSmartReplyState;
        boolean z2 = ((i & 1) == 0 || inflationProgress.newContentView == null) ? false : true;
        boolean z3 = ((i & 2) == 0 || inflationProgress.newExpandedView == null) ? false : true;
        boolean z4 = ((i & 4) == 0 || inflationProgress.newHeadsUpView == null) ? false : true;
        String logKey = NotificationUtils.logKey(notificationEntry);
        if (z2 || z3 || z4) {
            notificationRowContentBinderLogger2.logAsyncTaskProgress(logKey, "inflating contracted smart reply state");
            inflationProgress.inflatedSmartReplyState = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyState(notificationEntry);
        }
        if (z3) {
            notificationRowContentBinderLogger2.logAsyncTaskProgress(logKey, "inflating expanded smart reply state");
            notificationRowContentBinderLogger = null;
            inflationProgress.expandedInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context3, context2, notificationEntry, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        } else {
            notificationRowContentBinderLogger = null;
        }
        if (z4) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(logKey, "inflating heads up smart reply state");
            notificationEntry2 = notificationEntry;
            inflationProgress.headsUpInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context3, context2, notificationEntry2, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        } else {
            notificationEntry2 = notificationEntry;
        }
        boolean isConversation = notificationEntry2.mRanking.isConversation();
        int i2 = NmSummarizationUiFlag.$r8$clinit;
        if (isConversation) {
            ?? r2 = notificationRowContentBinderLogger;
            int i3 = AsyncHybridViewInflation.$r8$clinit;
            throw r2;
        }
        int i4 = AsyncHybridViewInflation.$r8$clinit;
        ?? r22 = notificationRowContentBinderLogger;
        SingleLineViewModel inflateSingleLineViewModel = SingleLineViewInflater.inflateSingleLineViewModel(notificationEntry2.mSbn.getNotification(), null, builder, expandableNotificationRow.getContext(), false, notificationEntry2.mRanking.getSummarization());
        boolean z5 = inflateSingleLineViewModel.conversationData != null;
        inflationProgress.mInflatedSingleLineViewModel = inflateSingleLineViewModel;
        inflationProgress.mInflatedSingleLineView = SingleLineViewInflater.inflatePrivateSingleLineView(z5, i, notificationEntry2, expandableNotificationRow.getContext(), r22);
        int i5 = LockscreenOtpRedaction.$r8$clinit;
        if (bindParams.redactionType == 2) {
            inflationProgress.mPublicInflatedSingleLineViewModel = SingleLineViewInflater.inflateSingleLineViewModel(notificationEntry2.mSbn.getNotification(), null, builder, expandableNotificationRow.getContext(), true, notificationEntry2.mRanking.getSummarization());
        } else {
            inflationProgress.mPublicInflatedSingleLineViewModel = SingleLineViewInflater.inflatePublicSingleLineViewModel(expandableNotificationRow.getContext(), z5);
        }
        inflationProgress.mPublicInflatedSingleLineView = SingleLineViewInflater.inflatePublicSingleLineView(z5, i, notificationEntry2, expandableNotificationRow.getContext(), r22);
        throw r22;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final void unbindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i) {
        String str = expandableNotificationRow.mLoggingKey;
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public void setInflateSynchronously(boolean z) {
    }
}
