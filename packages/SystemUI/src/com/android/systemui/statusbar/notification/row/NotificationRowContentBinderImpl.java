package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.app.RemoteInput;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcelable;
import android.os.RemoteException;
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
import android.widget.TextView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.os.BundleKt;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingMessage;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.InflationTask;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.statusbar.notification.promoted.PromotedNotificationContentExtractor;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageCache;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.HeadsUpStatusBarModel;
import com.android.systemui.statusbar.notification.row.shared.NewRemoteViews;
import com.android.systemui.statusbar.notification.row.shared.NotificationContentModel;
import com.android.systemui.statusbar.notification.row.shared.NotificationRowContentBinderRefactor;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.SingleLineViewBinder;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.ExpandHeadsUpOnInlineReply;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl;
import com.android.systemui.statusbar.policy.SmartReplyView;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Pair;
import kotlin.Result;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationRowContentBinderImpl implements NotificationRowContentBinder {
    public static final Companion Companion = new Companion(null);
    public final ConversationNotificationProcessor conversationProcessor;
    public final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
    public final HeadsUpStyleProvider headsUpStyleProvider;
    public boolean inflateSynchronously;
    public final Executor inflationExecutor;
    public final NotificationRowContentBinderLogger logger;
    public final NotifLayoutInflaterFactory.Provider notifLayoutInflaterFactoryProvider;
    public final PromotedNotificationContentExtractor promotedNotificationContentExtractor;
    public final NotificationRemoteInputManager remoteInputManager;
    public final NotifRemoteViewCache remoteViewCache;
    public final SmartReplyStateInflater smartReplyStateInflater;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class ApplyCallback {
        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AsyncInflationTask extends AsyncTask implements NotificationRowContentBinder.InflationCallback, InflationTask {
        public final NotificationRowContentBinder.BindParams bindParams;
        public final NotificationRowContentBinder.InflationCallback callback;
        public CancellationSignal cancellationSignal;
        public final ConversationNotificationProcessor conversationProcessor;
        public final NotificationEntry entry;
        public final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper;
        public final HeadsUpStyleProvider headsUpStyleProvider;
        public final boolean inflateSynchronously;
        public final Executor inflationExecutor;
        public final NotificationRowContentBinderLogger logger;
        public final NotifLayoutInflaterFactory.Provider notifLayoutInflaterFactoryProvider;
        public final PromotedNotificationContentExtractor promotedNotificationContentExtractor;
        public final int reInflateFlags;
        public final NotifRemoteViewCache remoteViewCache;
        public final RemoteViews.InteractionHandler remoteViewClickHandler;
        public final ExpandableNotificationRow row;
        public final SmartReplyStateInflater smartRepliesInflater;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class RtlEnabledContext extends ContextWrapper {
            public RtlEnabledContext(Context context) {
                super(context);
            }

            @Override // android.content.ContextWrapper, android.content.Context
            public final ApplicationInfo getApplicationInfo() {
                ApplicationInfo applicationInfo = new ApplicationInfo(super.getApplicationInfo());
                applicationInfo.flags |= 4194304;
                return applicationInfo;
            }
        }

        static {
            new Companion(null);
        }

        public AsyncInflationTask(Executor executor, boolean z, int i, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ConversationNotificationProcessor conversationNotificationProcessor, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinder.BindParams bindParams, NotificationRowContentBinder.InflationCallback inflationCallback, RemoteViews.InteractionHandler interactionHandler, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, PromotedNotificationContentExtractor promotedNotificationContentExtractor, NotificationRowContentBinderLogger notificationRowContentBinderLogger, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
            this.inflationExecutor = executor;
            this.inflateSynchronously = z;
            this.reInflateFlags = i;
            this.remoteViewCache = notifRemoteViewCache;
            this.entry = notificationEntry;
            this.conversationProcessor = conversationNotificationProcessor;
            this.row = expandableNotificationRow;
            this.bindParams = bindParams;
            this.callback = inflationCallback;
            this.remoteViewClickHandler = interactionHandler;
            this.smartRepliesInflater = smartReplyStateInflater;
            this.notifLayoutInflaterFactoryProvider = provider;
            this.headsUpStyleProvider = headsUpStyleProvider;
            this.promotedNotificationContentExtractor = promotedNotificationContentExtractor;
            this.logger = notificationRowContentBinderLogger;
            this.faceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
            notificationEntry.abortTask();
            notificationEntry.mRunningTask = this;
        }

        public static final InflationProgress access$doInBackgroundInternal(AsyncInflationTask asyncInflationTask) {
            HybridNotificationView hybridNotificationView;
            Set set;
            StatusBarNotification statusBarNotification = asyncInflationTask.entry.mSbn;
            try {
                Notification.addFieldsFromContext(asyncInflationTask.row.getContext().getPackageManager().getApplicationInfoAsUser(statusBarNotification.getPackageName(), 8192, UserHandle.getUserId(statusBarNotification.getUid())), statusBarNotification.getNotification());
            } catch (PackageManager.NameNotFoundException unused) {
            }
            Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(asyncInflationTask.row.getContext(), statusBarNotification.getNotification());
            Context packageContext = statusBarNotification.getPackageContext(asyncInflationTask.row.getContext());
            Context rtlEnabledContext = recoverBuilder.usesTemplate() ? new RtlEnabledContext(packageContext) : packageContext;
            Companion companion = NotificationRowContentBinderImpl.Companion;
            InflationProgress access$beginInflationAsync = Companion.access$beginInflationAsync(companion, asyncInflationTask.reInflateFlags, asyncInflationTask.entry, recoverBuilder, asyncInflationTask.bindParams, asyncInflationTask.row.getContext(), rtlEnabledContext, asyncInflationTask.row, asyncInflationTask.notifLayoutInflaterFactoryProvider, asyncInflationTask.headsUpStyleProvider, asyncInflationTask.conversationProcessor, asyncInflationTask.logger);
            asyncInflationTask.logger.logAsyncTaskProgress(asyncInflationTask.row.mLoggingKey, "getting existing smart reply state (on wrong thread!)");
            InflatedSmartReplyState inflatedSmartReplyState = asyncInflationTask.row.mPrivateLayout.mCurrentSmartReplyState;
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "inflating smart reply views");
            Companion.access$inflateSmartReplyViews(companion, access$beginInflationAsync, asyncInflationTask.reInflateFlags, asyncInflationTask.entry, asyncInflationTask.row.getContext(), rtlEnabledContext, inflatedSmartReplyState, asyncInflationTask.smartRepliesInflater, asyncInflationTask.logger);
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "inflating single line view");
            NotificationContentModel notificationContentModel = access$beginInflationAsync.contentModel;
            SingleLineViewModel singleLineViewModel = notificationContentModel.singleLineViewModel;
            HybridNotificationView hybridNotificationView2 = null;
            if (singleLineViewModel != null) {
                hybridNotificationView = SingleLineViewInflater.inflatePrivateSingleLineView(singleLineViewModel.conversationData != null, asyncInflationTask.reInflateFlags, asyncInflationTask.entry, asyncInflationTask.row.getContext(), asyncInflationTask.logger);
            } else {
                hybridNotificationView = null;
            }
            access$beginInflationAsync.inflatedSingleLineView = hybridNotificationView;
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "inflating public single line view");
            SingleLineViewModel singleLineViewModel2 = notificationContentModel.publicSingleLineViewModel;
            if (singleLineViewModel2 != null) {
                hybridNotificationView2 = SingleLineViewInflater.inflatePublicSingleLineView(singleLineViewModel2.conversationData != null, asyncInflationTask.reInflateFlags, asyncInflationTask.entry, asyncInflationTask.row.getContext(), asyncInflationTask.logger);
            }
            access$beginInflationAsync.inflatedPublicSingleLineView = hybridNotificationView2;
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "loading RON images");
            access$beginInflationAsync.rowImageInflater.getClass();
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "getting row image resolver (on wrong thread!)");
            final NotificationInlineImageResolver notificationInlineImageResolver = asyncInflationTask.row.mImageResolver;
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "waiting for preloaded images");
            if (notificationInlineImageResolver.hasCache() && (set = notificationInlineImageResolver.mWantedUriSet) != null) {
                final long elapsedRealtime = SystemClock.elapsedRealtime() + 1000;
                set.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NotificationInlineImageResolver notificationInlineImageResolver2 = NotificationInlineImageResolver.this;
                        long j = elapsedRealtime;
                        int i = NotificationInlineImageResolver.$r8$clinit;
                        notificationInlineImageResolver2.getClass();
                        notificationInlineImageResolver2.loadImageFromCache((Uri) obj, j - SystemClock.elapsedRealtime());
                    }
                });
            }
            return access$beginInflationAsync;
        }

        @Override // com.android.systemui.statusbar.InflationTask
        public final void abort() {
            this.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(this.entry), "cancelling inflate");
            cancel(true);
            if (this.cancellationSignal != null) {
                this.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(this.entry), "cancelling apply");
                CancellationSignal cancellationSignal = this.cancellationSignal;
                cancellationSignal.getClass();
                cancellationSignal.cancel();
            }
            this.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(this.entry), "aborted");
        }

        @Override // android.os.AsyncTask
        public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return Result.m3421boximpl(m3066doInBackgroundIoAF18A());
        }

        /* renamed from: doInBackground-IoAF18A, reason: not valid java name */
        public final Object m3066doInBackgroundIoAF18A() {
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
                    this.logger.logAsyncTaskException(NotificationUtilsKt.getLogKey(this.entry), "inflating", e);
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
                inflationCallback.handleInflationException(this.row.getEntryLegacy(), new InflationException("Couldn't inflate contentViews" + exc));
            }
            this.row.mImageResolver.cancelRunningTasks();
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void handleInflationException(Exception exc) {
            handleError$1(exc);
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void onAsyncInflationFinished() {
            TextView textView;
            TextView textView2;
            boolean z;
            View findViewById;
            View findViewById2;
            this.entry.mRunningTask = null;
            ExpandableNotificationRow expandableNotificationRow = this.row;
            expandableNotificationRow.getClass();
            expandableNotificationRow.mBubbleButtonViews = new ArrayList();
            if (expandableNotificationRow.mIsSummaryWithChildren) {
                Trace.beginSection("ExpNotRow#onNotifUpdated (summary)");
            } else {
                Trace.beginSection("ExpNotRow#onNotifUpdated (leaf)");
            }
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            String str = expandableNotificationRow.mEntry.mKey;
            ongoingActivityDataHelper.getClass();
            OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(str);
            if (pendingOngoingActivityData == null) {
                pendingOngoingActivityData = OngoingActivityDataHelper.getOngoingActivityDataByKey(expandableNotificationRow.mEntry.mKey);
            }
            if (expandableNotificationRow.mEntry.isPromotedState() && pendingOngoingActivityData != null && pendingOngoingActivityData.mIsMediaOngoingData) {
                NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
                SecMediaHost secMediaHost = expandableNotificationRow.mMediaHost;
                notificationContentView.mMediaHost = secMediaHost;
                if (secMediaHost == null) {
                    Log.d("NotificationContentView", "makeMediaOngoingCard(). mMediaHost is null");
                } else {
                    View view = notificationContentView.mExpandedChild;
                    if (view != null && (view instanceof ViewGroup)) {
                        ViewGroup viewGroup = (ViewGroup) view;
                        View findViewById3 = view.findViewById(R.id.media_carousel_layout);
                        if (findViewById3 != null) {
                            viewGroup.removeView(findViewById3);
                        }
                        View findViewById4 = notificationContentView.mExpandedChild.findViewById(R.id.ongoing_activity_expand_normal_layout);
                        if (findViewById4 != null) {
                            findViewById4.setVisibility(8);
                        }
                        ViewGroup viewGroup2 = (ViewGroup) notificationContentView.mExpandedChild.findViewById(16909884);
                        if (viewGroup2 != null) {
                            int childCount = viewGroup2.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View childAt = viewGroup2.getChildAt(i);
                                if (childAt.getVisibility() == 0 && childAt.getId() != R.id.media_carousel_layout) {
                                    childAt.setVisibility(8);
                                }
                            }
                        }
                    }
                    Log.i("NotificationContentView", "makeMediaOngoingCard(). removeMediaFrame / addMediaFrame");
                    SecMediaHost secMediaHost2 = notificationContentView.mMediaHost;
                    MediaType mediaType = MediaType.ENR;
                    secMediaHost2.removeMediaFrame(mediaType);
                    notificationContentView.mMediaHost.addMediaFrame(mediaType, notificationContentView.mExpandedChild);
                }
            }
            for (NotificationContentView notificationContentView2 : expandableNotificationRow.mLayouts) {
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                notificationContentView2.mNotificationEntry = notificationEntry;
                notificationContentView2.mBeforeN = notificationEntry.targetSdk < 24;
                NotificationContentView.updateAllSingleLineViews();
                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                if (notificationContentView2.mContractedChild != null) {
                    notificationContentView2.mContractedWrapper.onContentUpdated(expandableNotificationRow2);
                }
                if (notificationContentView2.mExpandedChild != null) {
                    notificationContentView2.mExpandedWrapper.onContentUpdated(expandableNotificationRow2);
                }
                if (notificationContentView2.mHeadsUpChild != null) {
                    notificationContentView2.mHeadsUpWrapper.onContentUpdated(expandableNotificationRow2);
                }
                if (notificationContentView2.mRemoteInputController != null) {
                    boolean z2 = notificationContentView2.mNotificationEntry.mSbn.getNotification().findRemoteInputActionPair(true) != null;
                    View view2 = notificationContentView2.mExpandedChild;
                    if (view2 != null) {
                        NotificationContentView.RemoteInputViewData applyRemoteInput = notificationContentView2.applyRemoteInput(view2, notificationContentView2.mNotificationEntry, z2, notificationContentView2.mPreviousExpandedRemoteInputIntent, notificationContentView2.mExpandedWrapper);
                        notificationContentView2.mExpandedRemoteInput = applyRemoteInput.mView;
                        RemoteInputViewController remoteInputViewController = applyRemoteInput.mController;
                        notificationContentView2.mExpandedRemoteInputController = remoteInputViewController;
                        if (remoteInputViewController != null) {
                            RemoteInputViewControllerImpl remoteInputViewControllerImpl = (RemoteInputViewControllerImpl) remoteInputViewController;
                            if (!remoteInputViewControllerImpl.isBound) {
                                remoteInputViewControllerImpl.isBound = true;
                                RemoteInput remoteInput = remoteInputViewControllerImpl.remoteInput;
                                RemoteInputView remoteInputView = remoteInputViewControllerImpl.view;
                                if (remoteInput != null) {
                                    remoteInputView.mEditText.setHint(remoteInput.getLabel());
                                    remoteInputView.setSupportedMimeTypes(remoteInput.getAllowedDataTypes());
                                    remoteInputView.updateRemoteInputLimitToastResources(remoteInput);
                                }
                                remoteInputView.mEditTextFocusChangeListeners.add(remoteInputViewControllerImpl.onFocusChangeListener);
                                remoteInputView.mOnSendListeners.add(remoteInputViewControllerImpl.onSendRemoteInputListener);
                            }
                        }
                    } else {
                        notificationContentView2.mExpandedRemoteInput = null;
                        RemoteInputViewController remoteInputViewController2 = notificationContentView2.mExpandedRemoteInputController;
                        if (remoteInputViewController2 != null) {
                            ((RemoteInputViewControllerImpl) remoteInputViewController2).unbind();
                        }
                        notificationContentView2.mExpandedRemoteInputController = null;
                    }
                    RemoteInputView remoteInputView2 = notificationContentView2.mCachedExpandedRemoteInput;
                    if (remoteInputView2 != null && remoteInputView2 != notificationContentView2.mExpandedRemoteInput) {
                        remoteInputView2.dispatchFinishTemporaryDetach();
                    }
                    notificationContentView2.mCachedExpandedRemoteInput = null;
                    notificationContentView2.getClass();
                    int i2 = ExpandHeadsUpOnInlineReply.$r8$clinit;
                    View view3 = notificationContentView2.mHeadsUpChild;
                    if (view3 != null && (findViewById = view3.findViewById(android.R.id.overlay_display_window_title)) != null && (findViewById instanceof MessagingImageMessage) && (findViewById2 = notificationContentView2.mHeadsUpChild.findViewById(android.R.id.resolver_empty_state_icon)) != null) {
                        findViewById2.setMinimumHeight(notificationContentView2.getResources().getDimensionPixelSize(R.dimen.notification_empty_text_area_min_height));
                    }
                }
                InflatedSmartReplyState inflatedSmartReplyState = notificationContentView2.mCurrentSmartReplyState;
                if (inflatedSmartReplyState != null) {
                    View view4 = notificationContentView2.mContractedChild;
                    if (view4 != null) {
                        NotificationContentView.applyExternalSmartReplyState(view4, inflatedSmartReplyState);
                    }
                    View view5 = notificationContentView2.mExpandedChild;
                    if (view5 != null) {
                        NotificationContentView.applyExternalSmartReplyState(view5, notificationContentView2.mCurrentSmartReplyState);
                        SmartReplyView applySmartReplyView = NotificationContentView.applySmartReplyView(notificationContentView2.mExpandedChild, notificationContentView2.mCurrentSmartReplyState, notificationContentView2.mNotificationEntry, notificationContentView2.mExpandedInflatedSmartReplies, false);
                        notificationContentView2.mExpandedSmartReplyView = applySmartReplyView;
                        if (applySmartReplyView != null) {
                            InflatedSmartReplyState inflatedSmartReplyState2 = notificationContentView2.mCurrentSmartReplyState;
                            SmartReplyView.SmartReplies smartReplies = inflatedSmartReplyState2.smartReplies;
                            SmartReplyView.SmartActions smartActions = inflatedSmartReplyState2.smartActions;
                            if (smartReplies != null || smartActions != null) {
                                int size = smartReplies == null ? 0 : smartReplies.choices.size();
                                int size2 = smartActions == null ? 0 : smartActions.actions.size();
                                boolean z3 = smartReplies == null ? smartActions.fromAssistant : smartReplies.fromAssistant;
                                try {
                                    if (smartReplies != null) {
                                        SmartReplyConstants smartReplyConstants = notificationContentView2.mSmartReplyConstants;
                                        int editChoicesBeforeSending = smartReplies.remoteInput.getEditChoicesBeforeSending();
                                        smartReplyConstants.getClass();
                                        if (editChoicesBeforeSending != 1 ? editChoicesBeforeSending != 2 ? smartReplyConstants.mEditChoicesBeforeSending : true : false) {
                                            z = true;
                                            SmartReplyController smartReplyController = notificationContentView2.mSmartReplyController;
                                            NotificationEntry notificationEntry2 = notificationContentView2.mNotificationEntry;
                                            smartReplyController.getClass();
                                            smartReplyController.mBarService.onNotificationSmartSuggestionsAdded(notificationEntry2.mSbn.getKey(), size, size2, z3, z);
                                        }
                                    }
                                    smartReplyController.mBarService.onNotificationSmartSuggestionsAdded(notificationEntry2.mSbn.getKey(), size, size2, z3, z);
                                } catch (RemoteException unused) {
                                }
                                z = false;
                                SmartReplyController smartReplyController2 = notificationContentView2.mSmartReplyController;
                                NotificationEntry notificationEntry22 = notificationContentView2.mNotificationEntry;
                                smartReplyController2.getClass();
                            }
                        }
                    }
                    View view6 = notificationContentView2.mHeadsUpChild;
                    if (view6 != null) {
                        NotificationContentView.applyExternalSmartReplyState(view6, notificationContentView2.mCurrentSmartReplyState);
                        if (notificationContentView2.mSmartReplyConstants.mShowInHeadsUp) {
                            notificationContentView2.mHeadsUpSmartReplyView = NotificationContentView.applySmartReplyView(notificationContentView2.mHeadsUpChild, notificationContentView2.mCurrentSmartReplyState, notificationContentView2.mNotificationEntry, notificationContentView2.mHeadsUpInflatedSmartReplies, true);
                        }
                    }
                }
                notificationContentView2.updateLegacy();
                notificationContentView2.mForceSelectNextLayout = true;
                notificationContentView2.mPreviousExpandedRemoteInputIntent = null;
                View view7 = notificationContentView2.mExpandedChild;
                notificationContentView2.applySnoozeAction(view7);
                notificationContentView2.applyBubbleAction(view7, notificationEntry);
                View view8 = notificationContentView2.mHeadsUpChild;
                notificationContentView2.applySnoozeAction(view8);
                notificationContentView2.applyBubbleAction(view8, notificationEntry);
                if (notificationContentView2.mContainingNotification != null) {
                    View view9 = notificationContentView2.mContractedChild;
                    if (view9 != null) {
                        notificationContentView2.updateContentViewMarginBottom(view9, false);
                    }
                    View view10 = notificationContentView2.mExpandedChild;
                    if (view10 != null) {
                        notificationContentView2.updateContentViewMarginBottom(view10, true);
                    }
                    View view11 = notificationContentView2.mHeadsUpChild;
                    if (view11 != null) {
                        notificationContentView2.updateContentViewMarginBottom(view11, false);
                    }
                }
            }
            expandableNotificationRow.mShowingPublicInitialized = false;
            NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
            if (notificationMenuRowPlugin != null) {
                notificationMenuRowPlugin.onNotificationUpdated();
                expandableNotificationRow.mMenuRow.setAppName(expandableNotificationRow.mAppName);
            }
            if (expandableNotificationRow.mIsSummaryWithChildren) {
                int i3 = AsyncGroupHeaderViewInflation.$r8$clinit;
                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                ExpandableNotificationRow.AnonymousClass1 anonymousClass1 = expandableNotificationRow.mExpandClickListener;
                int i4 = NotificationBundleUi.$r8$clinit;
                notificationChildrenContainer.recreateNotificationHeader(anonymousClass1, ((PeopleNotificationIdentifierImpl) expandableNotificationRow.mPeopleNotificationIdentifier).getPeopleNotificationType(expandableNotificationRow.getEntryLegacy()) != 0);
                expandableNotificationRow.mChildrenContainer.onNotificationUpdated();
            }
            if (expandableNotificationRow.mAnimationRunning) {
                expandableNotificationRow.setAnimationRunning(true);
            }
            if (expandableNotificationRow.mLastChronometerRunning) {
                expandableNotificationRow.setChronometerRunning(true);
            }
            ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow.mNotificationParent;
            if (expandableNotificationRow3 != null && expandableNotificationRow3.mIsSummaryWithChildren) {
                expandableNotificationRow3.mChildrenContainer.updateChildrenAppearance();
            }
            expandableNotificationRow.onAttachedChildrenCountChanged();
            expandableNotificationRow.mPublicLayout.updateExpandButtonsDuringLayout(expandableNotificationRow.mShowPublicExpander, false);
            expandableNotificationRow.updateLimits();
            expandableNotificationRow.updateShelfIconColor();
            expandableNotificationRow.updateBackgroundColors();
            ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateAllTextViewColors(expandableNotificationRow, expandableNotificationRow.mDimmed);
            for (NotificationContentView notificationContentView3 : expandableNotificationRow.mLayouts) {
                View view12 = notificationContentView3.mContractedChild;
                if (view12 != null && (textView2 = (TextView) view12.findViewById(android.R.id.inter_word)) != null && textView2.getText().toString().contains("@")) {
                    notificationContentView3.mIsContractedHeaderContainAtMark = true;
                }
                View view13 = notificationContentView3.mExpandedChild;
                if (view13 != null && (textView = (TextView) view13.findViewById(android.R.id.inter_word)) != null && textView.getText().toString().contains("@")) {
                    notificationContentView3.mIsExpandedHeaderContainAtMark = true;
                }
            }
            Trace.endSection();
            NotificationRowContentBinder.InflationCallback inflationCallback = this.callback;
            if (inflationCallback != null) {
                inflationCallback.onAsyncInflationFinished(this.entry);
            }
            NotificationInlineImageResolver notificationInlineImageResolver = this.row.mImageResolver;
            if (notificationInlineImageResolver.hasCache()) {
                NotificationInlineImageCache notificationInlineImageCache = (NotificationInlineImageCache) notificationInlineImageResolver.mImageCache;
                final Set set = notificationInlineImageCache.mResolver.mWantedUriSet;
                notificationInlineImageCache.mCache.entrySet().removeIf(new Predicate() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageCache$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return !set.contains(((Map.Entry) obj).getKey());
                    }
                });
            }
            this.row.mImageResolver.cancelRunningTasks();
        }

        @Override // android.os.AsyncTask
        public final void onCancelled(Object obj) {
            Trace.endAsyncSection("NotificationRowContentBinderImpl.AsyncInflationTask", System.identityHashCode(this));
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            AsyncInflationTask asyncInflationTask;
            Trace.endAsyncSection("NotificationRowContentBinderImpl.AsyncInflationTask", System.identityHashCode(this));
            Object m3423unboximpl = ((Result) obj).m3423unboximpl();
            if (m3423unboximpl instanceof Result.Failure) {
                asyncInflationTask = this;
            } else {
                asyncInflationTask = this;
                asyncInflationTask.cancellationSignal = Companion.access$apply(NotificationRowContentBinderImpl.Companion, this.inflationExecutor, this.inflateSynchronously, this.bindParams.isMinimized, (InflationProgress) m3423unboximpl, this.reInflateFlags, this.remoteViewCache, this.entry, this.row, this.remoteViewClickHandler, asyncInflationTask, this.logger, this.faceWidgetNotificationControllerWrapper);
            }
            Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(m3423unboximpl);
            if (m3422exceptionOrNullimpl != null) {
                asyncInflationTask.handleError$1((Exception) m3422exceptionOrNullimpl);
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            Trace.beginAsyncSection("NotificationRowContentBinderImpl.AsyncInflationTask", System.identityHashCode(this));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class RemoteViewsUpdater {
            public final NotificationEntry entry;
            public final int reInflateFlags;
            public final NotifRemoteViewCache remoteViewCache;

            public RemoteViewsUpdater(int i, NotificationEntry notificationEntry, NotifRemoteViewCache notifRemoteViewCache) {
                this.reInflateFlags = i;
                this.entry = notificationEntry;
                this.remoteViewCache = notifRemoteViewCache;
            }

            public final void setContentView(int i, RemoteViews remoteViews, View view, Function1 function1) {
                boolean z = (i & 6) != 0;
                if ((this.reInflateFlags & i) != 0) {
                    NotificationEntry notificationEntry = this.entry;
                    NotifRemoteViewCache notifRemoteViewCache = this.remoteViewCache;
                    if (view != null) {
                        function1.mo779invoke(view);
                        ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, i, remoteViews);
                    } else if (z && remoteViews == null) {
                        function1.mo779invoke(null);
                        ((NotifRemoteViewCacheImpl) notifRemoteViewCache).removeCachedView(notificationEntry, i);
                    } else {
                        NotifRemoteViewCacheImpl notifRemoteViewCacheImpl = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                        if (notifRemoteViewCacheImpl.getCachedView(notificationEntry, i) != null) {
                            notifRemoteViewCacheImpl.putCachedView(notificationEntry, i, remoteViews);
                        }
                    }
                }
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final CancellationSignal access$apply(Companion companion, Executor executor, boolean z, boolean z2, final InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, RemoteViews.InteractionHandler interactionHandler, AsyncInflationTask asyncInflationTask, final NotificationRowContentBinderLogger notificationRowContentBinderLogger, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
            NotificationContentView notificationContentView;
            final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper2;
            int i2;
            Companion companion2;
            NotificationContentView notificationContentView2;
            RemoteViews remoteViews;
            RemoteViews remoteViews2;
            RemoteViews remoteViews3;
            final NotificationRowContentBinderLogger notificationRowContentBinderLogger2 = notificationRowContentBinderLogger;
            companion.getClass();
            Trace.beginAsyncSection("NotificationRowContentBinderImpl#apply", System.identityHashCode(expandableNotificationRow));
            NotificationContentView notificationContentView3 = expandableNotificationRow.mPrivateLayout;
            NotificationContentView notificationContentView4 = expandableNotificationRow.mPublicLayout;
            final HashMap<Integer, CancellationSignal> hashMap = new HashMap<>();
            if ((i & 1) == 0 || (remoteViews3 = inflationProgress.remoteViews.contracted) == null) {
                notificationContentView = notificationContentView4;
                faceWidgetNotificationControllerWrapper2 = faceWidgetNotificationControllerWrapper;
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                boolean z3 = !companion.canReapplyRemoteView(remoteViews3, notifRemoteViewCacheImpl.getCachedView(notificationEntry, 1));
                ApplyCallback applyCallback = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$applyCallback$1
                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final RemoteViews getRemoteView() {
                        return inflationProgress.remoteViews.contracted;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final void setResultView(View view) {
                        NotificationEntry notificationEntry2 = notificationEntry;
                        NotificationRowContentBinderLogger.this.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry2), "contracted view applied");
                        inflationProgress.inflatedContentView = view;
                        if (notificationEntry2.isOngoingActivity()) {
                            NotificationRowContentBinderImplKt.setTooltipTextForOA(view);
                        }
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying contracted view");
                notificationContentView = notificationContentView4;
                companion.applyRemoteView(executor, z, z2, inflationProgress, i, 1, notifRemoteViewCacheImpl, notificationEntry, expandableNotificationRow, z3, interactionHandler, asyncInflationTask, notificationContentView3, notificationContentView3.mContractedChild, notificationContentView3.getVisibleWrapper(0), hashMap, applyCallback, notificationRowContentBinderLogger, faceWidgetNotificationControllerWrapper);
                notificationRowContentBinderLogger2 = notificationRowContentBinderLogger;
                faceWidgetNotificationControllerWrapper2 = faceWidgetNotificationControllerWrapper;
            }
            if ((i & 2) == 0 || (remoteViews2 = inflationProgress.remoteViews.expanded) == null) {
                i2 = i;
            } else {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl2 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                boolean z4 = !companion.canReapplyRemoteView(remoteViews2, notifRemoteViewCacheImpl2.getCachedView(notificationEntry, 2));
                ApplyCallback applyCallback2 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$applyCallback$2
                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final RemoteViews getRemoteView() {
                        return inflationProgress.remoteViews.expanded;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final void setResultView(View view) {
                        View view2;
                        NotificationEntry notificationEntry2 = notificationEntry;
                        NotificationRowContentBinderLogger.this.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry2), "expanded view applied");
                        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                        String str = notificationEntry2.mKey;
                        ongoingActivityDataHelper.getClass();
                        OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(str);
                        if (pendingOngoingActivityData == null) {
                            pendingOngoingActivityData = OngoingActivityDataHelper.getOngoingActivityDataByKey(notificationEntry2.mKey);
                        }
                        if (pendingOngoingActivityData == null || pendingOngoingActivityData.mCustomExpandedCardView == null) {
                            view2 = view;
                        } else {
                            view2 = faceWidgetNotificationControllerWrapper2.getViewFromNowBar(view, BundleKt.bundleOf(new Pair("type", "ENR")));
                        }
                        if (notificationEntry2.isOngoingActivity()) {
                            NotificationRowContentBinderImplKt.setTooltipTextForOA(view);
                        }
                        inflationProgress.inflatedExpandedView = view2;
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying expanded view");
                FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper3 = faceWidgetNotificationControllerWrapper2;
                NotificationRowContentBinderLogger notificationRowContentBinderLogger3 = notificationRowContentBinderLogger2;
                i2 = i;
                companion.applyRemoteView(executor, z, z2, inflationProgress, i2, 2, notifRemoteViewCacheImpl2, notificationEntry, expandableNotificationRow, z4, interactionHandler, asyncInflationTask, notificationContentView3, notificationContentView3.mExpandedChild, notificationContentView3.getVisibleWrapper(1), hashMap, applyCallback2, notificationRowContentBinderLogger3, faceWidgetNotificationControllerWrapper3);
                notificationRowContentBinderLogger2 = notificationRowContentBinderLogger3;
                faceWidgetNotificationControllerWrapper2 = faceWidgetNotificationControllerWrapper3;
            }
            if ((i2 & 256) == 0 || inflationProgress.remoteViews.promotedView == null) {
                companion2 = companion;
                notificationContentView2 = notificationContentView3;
            } else {
                ApplyCallback applyCallback3 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$applyCallback$3
                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final RemoteViews getRemoteView() {
                        return inflationProgress.remoteViews.promotedView;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final void setResultView(View view) {
                        View view2;
                        NotificationEntry notificationEntry2 = notificationEntry;
                        NotificationRowContentBinderLogger.this.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry2), "promoted ongoing view applied");
                        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                        String str = notificationEntry2.mKey;
                        ongoingActivityDataHelper.getClass();
                        OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(str);
                        if (pendingOngoingActivityData == null) {
                            pendingOngoingActivityData = OngoingActivityDataHelper.getOngoingActivityDataByKey(notificationEntry2.mKey);
                        }
                        if (pendingOngoingActivityData == null || pendingOngoingActivityData.mCustomExpandedCardView == null) {
                            view2 = view;
                        } else {
                            view2 = faceWidgetNotificationControllerWrapper2.getViewFromNowBar(view, BundleKt.bundleOf(new Pair("type", "OA")));
                        }
                        if (notificationEntry2.isOngoingActivity()) {
                            NotificationRowContentBinderImplKt.setTooltipTextForOA(view);
                        }
                        inflationProgress.inflatedPromotedOngoingView = view2;
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying promoted ongoing view");
                notificationContentView2 = notificationContentView3;
                NotificationRowContentBinderLogger notificationRowContentBinderLogger4 = notificationRowContentBinderLogger2;
                companion2 = companion;
                companion2.applyRemoteView(executor, z, z2, inflationProgress, i2, 256, notifRemoteViewCache, notificationEntry, expandableNotificationRow, true, interactionHandler, asyncInflationTask, null, null, null, hashMap, applyCallback3, notificationRowContentBinderLogger4, faceWidgetNotificationControllerWrapper2);
                notificationRowContentBinderLogger2 = notificationRowContentBinderLogger4;
            }
            if ((i & 4) != 0 && (remoteViews = inflationProgress.remoteViews.headsUp) != null) {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl3 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                boolean z5 = !companion2.canReapplyRemoteView(remoteViews, notifRemoteViewCacheImpl3.getCachedView(notificationEntry, 4));
                ApplyCallback applyCallback4 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$applyCallback$4
                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final RemoteViews getRemoteView() {
                        return inflationProgress.remoteViews.headsUp;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final void setResultView(View view) {
                        NotificationRowContentBinderLogger.this.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "heads up view applied");
                        inflationProgress.inflatedHeadsUpView = view;
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying heads up view");
                NotificationContentView notificationContentView5 = notificationContentView2;
                NotificationRowContentBinderLogger notificationRowContentBinderLogger5 = notificationRowContentBinderLogger2;
                companion2.applyRemoteView(executor, z, z2, inflationProgress, i, 4, notifRemoteViewCacheImpl3, notificationEntry, expandableNotificationRow, z5, interactionHandler, asyncInflationTask, notificationContentView5, notificationContentView5.mHeadsUpChild, notificationContentView5.getVisibleWrapper(2), hashMap, applyCallback4, notificationRowContentBinderLogger5, faceWidgetNotificationControllerWrapper);
                notificationRowContentBinderLogger2 = notificationRowContentBinderLogger5;
            }
            if ((i & 8) != 0) {
                NotifRemoteViewCacheImpl notifRemoteViewCacheImpl4 = (NotifRemoteViewCacheImpl) notifRemoteViewCache;
                boolean z6 = !companion2.canReapplyRemoteView(inflationProgress.remoteViews.f107public, notifRemoteViewCacheImpl4.getCachedView(notificationEntry, 8));
                ApplyCallback applyCallback5 = new ApplyCallback() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$applyCallback$5
                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final RemoteViews getRemoteView() {
                        RemoteViews remoteViews4 = inflationProgress.remoteViews.f107public;
                        remoteViews4.getClass();
                        return remoteViews4;
                    }

                    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.ApplyCallback
                    public final void setResultView(View view) {
                        NotificationRowContentBinderLogger.this.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "public view applied");
                        inflationProgress.inflatedPublicView = view;
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying public view");
                NotificationContentView notificationContentView6 = notificationContentView;
                companion2.applyRemoteView(executor, z, z2, inflationProgress, i, 8, notifRemoteViewCacheImpl4, notificationEntry, expandableNotificationRow, z6, interactionHandler, asyncInflationTask, notificationContentView6, notificationContentView6.mContractedChild, notificationContentView6.getVisibleWrapper(0), hashMap, applyCallback5, notificationRowContentBinderLogger2, faceWidgetNotificationControllerWrapper);
            }
            finishIfDone(inflationProgress, i, notifRemoteViewCache, hashMap, asyncInflationTask, notificationEntry, expandableNotificationRow, notificationRowContentBinderLogger);
            CancellationSignal cancellationSignal = new CancellationSignal();
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$1
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    NotificationRowContentBinderLogger.this.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "apply cancelled");
                    Trace.endAsyncSection("NotificationRowContentBinderImpl#apply", System.identityHashCode(expandableNotificationRow));
                    hashMap.values().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$1.1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((CancellationSignal) obj).cancel();
                        }
                    });
                }
            });
            return cancellationSignal;
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x02e6 A[Catch: all -> 0x023a, TryCatch #0 {all -> 0x023a, blocks: (B:108:0x01f3, B:110:0x020b, B:111:0x0213, B:114:0x021d, B:116:0x0225, B:118:0x022d, B:119:0x022f, B:122:0x023d, B:15:0x027c, B:17:0x0281, B:19:0x0297, B:20:0x029f, B:23:0x02a9, B:25:0x02b3, B:27:0x02c0, B:29:0x02e2, B:31:0x02e6, B:33:0x02fc, B:34:0x0304, B:37:0x030e, B:39:0x0318, B:41:0x0325, B:43:0x0341, B:45:0x0345, B:47:0x035e, B:48:0x0366, B:51:0x0370, B:53:0x037a, B:55:0x0386, B:57:0x038b, B:59:0x0396, B:61:0x03bc, B:63:0x03d0, B:64:0x03dc, B:66:0x03e0, B:67:0x03e7, B:69:0x03eb, B:70:0x03f3, B:72:0x03f7, B:96:0x03af, B:98:0x037f, B:100:0x033c, B:102:0x02cc, B:104:0x02d0, B:105:0x02d9, B:124:0x0247, B:127:0x0253, B:128:0x0260, B:130:0x0264, B:131:0x026b), top: B:107:0x01f3 }] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0345 A[Catch: all -> 0x023a, TryCatch #0 {all -> 0x023a, blocks: (B:108:0x01f3, B:110:0x020b, B:111:0x0213, B:114:0x021d, B:116:0x0225, B:118:0x022d, B:119:0x022f, B:122:0x023d, B:15:0x027c, B:17:0x0281, B:19:0x0297, B:20:0x029f, B:23:0x02a9, B:25:0x02b3, B:27:0x02c0, B:29:0x02e2, B:31:0x02e6, B:33:0x02fc, B:34:0x0304, B:37:0x030e, B:39:0x0318, B:41:0x0325, B:43:0x0341, B:45:0x0345, B:47:0x035e, B:48:0x0366, B:51:0x0370, B:53:0x037a, B:55:0x0386, B:57:0x038b, B:59:0x0396, B:61:0x03bc, B:63:0x03d0, B:64:0x03dc, B:66:0x03e0, B:67:0x03e7, B:69:0x03eb, B:70:0x03f3, B:72:0x03f7, B:96:0x03af, B:98:0x037f, B:100:0x033c, B:102:0x02cc, B:104:0x02d0, B:105:0x02d9, B:124:0x0247, B:127:0x0253, B:128:0x0260, B:130:0x0264, B:131:0x026b), top: B:107:0x01f3 }] */
        /* JADX WARN: Removed duplicated region for block: B:57:0x038b A[Catch: all -> 0x023a, TryCatch #0 {all -> 0x023a, blocks: (B:108:0x01f3, B:110:0x020b, B:111:0x0213, B:114:0x021d, B:116:0x0225, B:118:0x022d, B:119:0x022f, B:122:0x023d, B:15:0x027c, B:17:0x0281, B:19:0x0297, B:20:0x029f, B:23:0x02a9, B:25:0x02b3, B:27:0x02c0, B:29:0x02e2, B:31:0x02e6, B:33:0x02fc, B:34:0x0304, B:37:0x030e, B:39:0x0318, B:41:0x0325, B:43:0x0341, B:45:0x0345, B:47:0x035e, B:48:0x0366, B:51:0x0370, B:53:0x037a, B:55:0x0386, B:57:0x038b, B:59:0x0396, B:61:0x03bc, B:63:0x03d0, B:64:0x03dc, B:66:0x03e0, B:67:0x03e7, B:69:0x03eb, B:70:0x03f3, B:72:0x03f7, B:96:0x03af, B:98:0x037f, B:100:0x033c, B:102:0x02cc, B:104:0x02d0, B:105:0x02d9, B:124:0x0247, B:127:0x0253, B:128:0x0260, B:130:0x0264, B:131:0x026b), top: B:107:0x01f3 }] */
        /* JADX WARN: Removed duplicated region for block: B:63:0x03d0 A[Catch: all -> 0x023a, TryCatch #0 {all -> 0x023a, blocks: (B:108:0x01f3, B:110:0x020b, B:111:0x0213, B:114:0x021d, B:116:0x0225, B:118:0x022d, B:119:0x022f, B:122:0x023d, B:15:0x027c, B:17:0x0281, B:19:0x0297, B:20:0x029f, B:23:0x02a9, B:25:0x02b3, B:27:0x02c0, B:29:0x02e2, B:31:0x02e6, B:33:0x02fc, B:34:0x0304, B:37:0x030e, B:39:0x0318, B:41:0x0325, B:43:0x0341, B:45:0x0345, B:47:0x035e, B:48:0x0366, B:51:0x0370, B:53:0x037a, B:55:0x0386, B:57:0x038b, B:59:0x0396, B:61:0x03bc, B:63:0x03d0, B:64:0x03dc, B:66:0x03e0, B:67:0x03e7, B:69:0x03eb, B:70:0x03f3, B:72:0x03f7, B:96:0x03af, B:98:0x037f, B:100:0x033c, B:102:0x02cc, B:104:0x02d0, B:105:0x02d9, B:124:0x0247, B:127:0x0253, B:128:0x0260, B:130:0x0264, B:131:0x026b), top: B:107:0x01f3 }] */
        /* JADX WARN: Removed duplicated region for block: B:66:0x03e0 A[Catch: all -> 0x023a, TryCatch #0 {all -> 0x023a, blocks: (B:108:0x01f3, B:110:0x020b, B:111:0x0213, B:114:0x021d, B:116:0x0225, B:118:0x022d, B:119:0x022f, B:122:0x023d, B:15:0x027c, B:17:0x0281, B:19:0x0297, B:20:0x029f, B:23:0x02a9, B:25:0x02b3, B:27:0x02c0, B:29:0x02e2, B:31:0x02e6, B:33:0x02fc, B:34:0x0304, B:37:0x030e, B:39:0x0318, B:41:0x0325, B:43:0x0341, B:45:0x0345, B:47:0x035e, B:48:0x0366, B:51:0x0370, B:53:0x037a, B:55:0x0386, B:57:0x038b, B:59:0x0396, B:61:0x03bc, B:63:0x03d0, B:64:0x03dc, B:66:0x03e0, B:67:0x03e7, B:69:0x03eb, B:70:0x03f3, B:72:0x03f7, B:96:0x03af, B:98:0x037f, B:100:0x033c, B:102:0x02cc, B:104:0x02d0, B:105:0x02d9, B:124:0x0247, B:127:0x0253, B:128:0x0260, B:130:0x0264, B:131:0x026b), top: B:107:0x01f3 }] */
        /* JADX WARN: Removed duplicated region for block: B:69:0x03eb A[Catch: all -> 0x023a, TryCatch #0 {all -> 0x023a, blocks: (B:108:0x01f3, B:110:0x020b, B:111:0x0213, B:114:0x021d, B:116:0x0225, B:118:0x022d, B:119:0x022f, B:122:0x023d, B:15:0x027c, B:17:0x0281, B:19:0x0297, B:20:0x029f, B:23:0x02a9, B:25:0x02b3, B:27:0x02c0, B:29:0x02e2, B:31:0x02e6, B:33:0x02fc, B:34:0x0304, B:37:0x030e, B:39:0x0318, B:41:0x0325, B:43:0x0341, B:45:0x0345, B:47:0x035e, B:48:0x0366, B:51:0x0370, B:53:0x037a, B:55:0x0386, B:57:0x038b, B:59:0x0396, B:61:0x03bc, B:63:0x03d0, B:64:0x03dc, B:66:0x03e0, B:67:0x03e7, B:69:0x03eb, B:70:0x03f3, B:72:0x03f7, B:96:0x03af, B:98:0x037f, B:100:0x033c, B:102:0x02cc, B:104:0x02d0, B:105:0x02d9, B:124:0x0247, B:127:0x0253, B:128:0x0260, B:130:0x0264, B:131:0x026b), top: B:107:0x01f3 }] */
        /* JADX WARN: Removed duplicated region for block: B:72:0x03f7 A[Catch: all -> 0x023a, TRY_LEAVE, TryCatch #0 {all -> 0x023a, blocks: (B:108:0x01f3, B:110:0x020b, B:111:0x0213, B:114:0x021d, B:116:0x0225, B:118:0x022d, B:119:0x022f, B:122:0x023d, B:15:0x027c, B:17:0x0281, B:19:0x0297, B:20:0x029f, B:23:0x02a9, B:25:0x02b3, B:27:0x02c0, B:29:0x02e2, B:31:0x02e6, B:33:0x02fc, B:34:0x0304, B:37:0x030e, B:39:0x0318, B:41:0x0325, B:43:0x0341, B:45:0x0345, B:47:0x035e, B:48:0x0366, B:51:0x0370, B:53:0x037a, B:55:0x0386, B:57:0x038b, B:59:0x0396, B:61:0x03bc, B:63:0x03d0, B:64:0x03dc, B:66:0x03e0, B:67:0x03e7, B:69:0x03eb, B:70:0x03f3, B:72:0x03f7, B:96:0x03af, B:98:0x037f, B:100:0x033c, B:102:0x02cc, B:104:0x02d0, B:105:0x02d9, B:124:0x0247, B:127:0x0253, B:128:0x0260, B:130:0x0264, B:131:0x026b), top: B:107:0x01f3 }] */
        /* JADX WARN: Removed duplicated region for block: B:81:0x0402  */
        /* JADX WARN: Removed duplicated region for block: B:84:0x0409  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x0434  */
        /* JADX WARN: Removed duplicated region for block: B:93:0x0463  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x042f  */
        /* JADX WARN: Removed duplicated region for block: B:95:0x03da  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x03b8  */
        /* JADX WARN: Removed duplicated region for block: B:99:0x0384  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static final com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.InflationProgress access$beginInflationAsync(com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.Companion r27, int r28, final com.android.systemui.statusbar.notification.collection.NotificationEntry r29, final android.app.Notification.Builder r30, com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.BindParams r31, android.content.Context r32, android.content.Context r33, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r34, com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory.Provider r35, com.android.systemui.statusbar.notification.row.HeadsUpStyleProvider r36, com.android.systemui.statusbar.notification.ConversationNotificationProcessor r37, com.android.systemui.statusbar.notification.row.NotificationRowContentBinderLogger r38) {
            /*
                Method dump skipped, instructions count: 1170
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl.Companion.access$beginInflationAsync(com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion, int, com.android.systemui.statusbar.notification.collection.NotificationEntry, android.app.Notification$Builder, com.android.systemui.statusbar.notification.row.NotificationRowContentBinder$BindParams, android.content.Context, android.content.Context, com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory$Provider, com.android.systemui.statusbar.notification.row.HeadsUpStyleProvider, com.android.systemui.statusbar.notification.ConversationNotificationProcessor, com.android.systemui.statusbar.notification.row.NotificationRowContentBinderLogger):com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$InflationProgress");
        }

        public static final RemoteViews access$createContentView(Companion companion, Notification.Builder builder, boolean z) {
            companion.getClass();
            if (z) {
                RemoteViews makeLowPriorityContentView = builder.makeLowPriorityContentView(false);
                makeLowPriorityContentView.getClass();
                return makeLowPriorityContentView;
            }
            RemoteViews createContentView = builder.createContentView();
            createContentView.getClass();
            return createContentView;
        }

        public static final RemoteViews access$createExpandedView(Companion companion, Notification.Builder builder, boolean z) {
            companion.getClass();
            RemoteViews createBigContentView = builder.createBigContentView();
            if (createBigContentView != null) {
                return createBigContentView;
            }
            if (!z) {
                return null;
            }
            RemoteViews createContentView = builder.createContentView();
            Notification.Builder.makeHeaderExpanded(createContentView);
            return createContentView;
        }

        public static final Notification.Builder access$createSensitiveContentMessageNotification(Companion companion, Notification notification2, Notification.Style style, Context context, Context context2) {
            companion.getClass();
            Notification.Builder builder = new Notification.Builder(context2, notification2.getChannelId());
            builder.setContentTitle(notification2.extras.getCharSequence("android.title"));
            CharSequence string = context.getString(R.string.redacted_otp_notification_single_line_text);
            if (style instanceof Notification.MessagingStyle) {
                Notification.MessagingStyle messagingStyle = (Notification.MessagingStyle) style;
                Notification.MessagingStyle messagingStyle2 = new Notification.MessagingStyle(messagingStyle.getUser());
                messagingStyle2.setConversationTitle(messagingStyle.getConversationTitle());
                messagingStyle2.setGroupConversation(false);
                messagingStyle2.setConversationType(messagingStyle.getConversationType());
                messagingStyle2.setShortcutIcon(messagingStyle.getShortcutIcon());
                messagingStyle2.setBuilder(builder);
                Notification.MessagingStyle.Message findLatestIncomingMessage = Notification.MessagingStyle.findLatestIncomingMessage(messagingStyle.getMessages());
                if (findLatestIncomingMessage != null) {
                    messagingStyle2.addMessage(new Notification.MessagingStyle.Message(string, findLatestIncomingMessage.getTimestamp(), findLatestIncomingMessage.getSenderPerson()));
                }
                builder.setStyle(messagingStyle2);
            } else {
                builder.setContentText(string).getClass();
            }
            builder.setLargeIcon(notification2.getLargeIcon());
            builder.setSmallIcon(notification2.getSmallIcon());
            builder.setWhen(notification2.getWhen());
            return builder;
        }

        public static final void access$inflateSmartReplyViews(Companion companion, InflationProgress inflationProgress, int i, NotificationEntry notificationEntry, Context context, Context context2, InflatedSmartReplyState inflatedSmartReplyState, SmartReplyStateInflater smartReplyStateInflater, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
            companion.getClass();
            int i2 = i & 1;
            NewRemoteViews newRemoteViews = inflationProgress.remoteViews;
            boolean z = (i2 == 0 || newRemoteViews.contracted == null) ? false : true;
            boolean z2 = ((i & 2) == 0 || newRemoteViews.expanded == null) ? false : true;
            boolean z3 = ((i & 4) == 0 || newRemoteViews.headsUp == null) ? false : true;
            if (z || z2 || z3) {
                notificationRowContentBinderLogger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "inflating contracted smart reply state");
                inflationProgress.inflatedSmartReplyState = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyState(notificationEntry);
            }
            if (z2) {
                notificationRowContentBinderLogger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "inflating expanded smart reply state");
                InflatedSmartReplyState inflatedSmartReplyState2 = inflationProgress.inflatedSmartReplyState;
                inflatedSmartReplyState2.getClass();
                inflationProgress.expandedInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflatedSmartReplyState2);
            }
            if (z3) {
                notificationRowContentBinderLogger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "inflating heads up smart reply state");
                InflatedSmartReplyState inflatedSmartReplyState3 = inflationProgress.inflatedSmartReplyState;
                inflatedSmartReplyState3.getClass();
                inflationProgress.headsUpInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context, context2, notificationEntry, inflatedSmartReplyState, inflatedSmartReplyState3);
            }
        }

        public static boolean finishIfDone(InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, HashMap hashMap, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
            HybridNotificationView hybridNotificationView;
            SingleLineViewModel singleLineViewModel;
            HybridNotificationView hybridNotificationView2;
            SingleLineViewModel singleLineViewModel2;
            View view;
            Assert.isMainThread();
            if (!hashMap.isEmpty()) {
                return false;
            }
            notificationRowContentBinderLogger.logAsyncTaskProgress(expandableNotificationRow.mLoggingKey, "finishing");
            expandableNotificationRow.mImageModelIndex = inflationProgress.rowImageInflater.getNewImageIndex();
            notificationEntry.getClass();
            int i2 = NotificationRowContentBinderRefactor.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            NotificationContentModel notificationContentModel = inflationProgress.contentModel;
            HeadsUpStatusBarModel headsUpStatusBarModel = notificationContentModel.headsUpStatusBarModel;
            notificationEntry.mHeadsUpStatusBarText.setValue(headsUpStatusBarModel.privateText);
            notificationEntry.mHeadsUpStatusBarTextPublic.setValue(headsUpStatusBarModel.publicText);
            PromotedNotificationContentModel.Companion.getClass();
            InflatedSmartReplyState inflatedSmartReplyState = inflationProgress.inflatedSmartReplyState;
            if (inflatedSmartReplyState != null) {
                expandableNotificationRow.mPrivateLayout.mCurrentSmartReplyState = inflatedSmartReplyState;
            }
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            NotificationContentView notificationContentView2 = expandableNotificationRow.mPublicLayout;
            RemoteViewsUpdater remoteViewsUpdater = new RemoteViewsUpdater(i, notificationEntry, notifRemoteViewCache);
            NewRemoteViews newRemoteViews = inflationProgress.remoteViews;
            remoteViewsUpdater.setContentView(1, newRemoteViews.contracted, inflationProgress.inflatedContentView, new NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$1(notificationContentView));
            expandableNotificationRow.mIsCustomNotification = NotificationContentInflater.isCustomNotification(notificationEntry.mSbn.getNotification(), notificationContentView.mContractedChild, notificationEntry.mSbn.getNotification().contentView);
            remoteViewsUpdater.setContentView(2, newRemoteViews.expanded, inflationProgress.inflatedExpandedView, new NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$2(notificationContentView));
            RemoteViews remoteViews = newRemoteViews.expanded;
            InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder = inflationProgress.expandedInflatedSmartReplies;
            NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$3 notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$3 = new NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$3(notificationContentView);
            int i3 = remoteViewsUpdater.reInflateFlags;
            if ((i3 & 2) != 0) {
                if (remoteViews != null) {
                    notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$3.mo779invoke(inflatedSmartReplyViewHolder);
                } else {
                    notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$3.mo779invoke(null);
                }
            }
            if ((i & 2) != 0) {
                expandableNotificationRow.mExpandable = newRemoteViews.expanded != null;
                expandableNotificationRow.mPrivateLayout.updateExpandButtonsDuringLayout(expandableNotificationRow.isExpandable(), false);
            }
            expandableNotificationRow.mIsCustomBigNotification = NotificationContentInflater.isCustomNotification(notificationEntry.mSbn.getNotification(), notificationContentView.mExpandedChild, notificationEntry.mSbn.getNotification().bigContentView);
            if ((i & 256) != 0 && (view = inflationProgress.inflatedPromotedOngoingView) != null) {
                notificationEntry.mPromotedOngoingView = view;
                remoteViewsUpdater.setContentView(256, newRemoteViews.promotedView, view, new NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$4(notificationContentView));
            }
            remoteViewsUpdater.setContentView(4, newRemoteViews.headsUp, inflationProgress.inflatedHeadsUpView, new NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$5(notificationContentView));
            RemoteViews remoteViews2 = newRemoteViews.headsUp;
            InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder2 = inflationProgress.headsUpInflatedSmartReplies;
            NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$6 notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$6 = new NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$6(notificationContentView);
            if ((i3 & 4) != 0) {
                if (remoteViews2 != null) {
                    notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$6.mo779invoke(inflatedSmartReplyViewHolder2);
                } else {
                    notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$6.mo779invoke(null);
                }
            }
            expandableNotificationRow.mIsCustomHeadsUpNotification = NotificationContentInflater.isCustomNotification(notificationEntry.mSbn.getNotification(), notificationContentView.mHeadsUpChild, notificationEntry.mSbn.getNotification().headsUpContentView);
            remoteViewsUpdater.setContentView(8, newRemoteViews.f107public, inflationProgress.inflatedPublicView, new NotificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$7(notificationContentView2));
            if (notificationEntry.mSbn.getNotification().publicVersion != null) {
                expandableNotificationRow.mIsCustomPublicNotification = NotificationContentInflater.isCustomNotification(notificationEntry.mSbn.getNotification().publicVersion, notificationContentView2.mContractedChild, notificationEntry.mSbn.getNotification().publicVersion.contentView);
            }
            if ((i & 16) != 0 && (hybridNotificationView2 = inflationProgress.inflatedSingleLineView) != null && (singleLineViewModel2 = notificationContentModel.singleLineViewModel) != null) {
                SingleLineViewBinder.bind(singleLineViewModel2, hybridNotificationView2);
                expandableNotificationRow.mPrivateLayout.setSingleLineView(inflationProgress.inflatedSingleLineView);
            }
            if ((i & 128) != 0 && (hybridNotificationView = inflationProgress.inflatedPublicSingleLineView) != null && (singleLineViewModel = notificationContentModel.publicSingleLineViewModel) != null) {
                SingleLineViewBinder.bind(singleLineViewModel, hybridNotificationView);
                expandableNotificationRow.mPublicLayout.setSingleLineView(inflationProgress.inflatedPublicSingleLineView);
            }
            Trace.endAsyncSection("NotificationRowContentBinderImpl#apply", System.identityHashCode(expandableNotificationRow));
            if (inflationCallback != null) {
                inflationCallback.onAsyncInflationFinished(notificationEntry);
            }
            return true;
        }

        public static void handleInflationError(HashMap hashMap, Exception exc, ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationRowContentBinderLogger notificationRowContentBinderLogger, String str) {
            Assert.isMainThread();
            notificationRowContentBinderLogger.logAsyncTaskException(expandableNotificationRow != null ? expandableNotificationRow.mLoggingKey : null, str, exc);
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

        public final void applyRemoteView(Executor executor, boolean z, final boolean z2, final InflationProgress inflationProgress, final int i, final int i2, final NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, final boolean z3, final RemoteViews.InteractionHandler interactionHandler, final NotificationRowContentBinder.InflationCallback inflationCallback, final ViewGroup viewGroup, final View view, final NotificationViewWrapper notificationViewWrapper, final HashMap<Integer, CancellationSignal> hashMap, final ApplyCallback applyCallback, final NotificationRowContentBinderLogger notificationRowContentBinderLogger, final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
            CancellationSignal reapplyAsync;
            final RemoteViews remoteView = applyCallback.getRemoteView();
            if (!z) {
                RemoteViews.OnViewAppliedListener onViewAppliedListener = new RemoteViews.OnViewAppliedListener(notificationEntry, hashMap, inflationCallback, notificationRowContentBinderLogger, i2, z3, applyCallback, notificationViewWrapper, inflationProgress, z2, i, notifRemoteViewCache, faceWidgetNotificationControllerWrapper, remoteView, viewGroup, interactionHandler, view) { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$applyRemoteView$listener$1
                    public final /* synthetic */ NotificationRowContentBinderImpl.ApplyCallback $applyCallback;
                    public final /* synthetic */ NotificationRowContentBinder.InflationCallback $callback;
                    public final /* synthetic */ NotificationEntry $entry;
                    public final /* synthetic */ View $existingView;
                    public final /* synthetic */ NotificationViewWrapper $existingWrapper;
                    public final /* synthetic */ FaceWidgetNotificationControllerWrapper $faceWidgetNotificationControllerWrapper;
                    public final /* synthetic */ int $inflationId;
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
                        this.$faceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
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
                                view2.getClass();
                            }
                            Log.wtf("NotifContentInflater", "Async Inflation failed but normal inflation finished normally.", exc);
                            view2.getClass();
                            onViewApplied(view2);
                        } catch (Exception unused) {
                            this.$runningInflations.remove(Integer.valueOf(this.$inflationId));
                            NotificationRowContentBinderImpl.Companion companion = NotificationRowContentBinderImpl.Companion;
                            HashMap hashMap2 = this.$runningInflations;
                            ExpandableNotificationRow expandableNotificationRow2 = ExpandableNotificationRow.this;
                            NotificationEntry notificationEntry2 = this.$entry;
                            NotificationRowContentBinder.InflationCallback inflationCallback2 = this.$callback;
                            NotificationRowContentBinderLogger notificationRowContentBinderLogger2 = this.$logger;
                            companion.getClass();
                            NotificationRowContentBinderImpl.Companion.handleInflationError(hashMap2, exc, expandableNotificationRow2, notificationEntry2, inflationCallback2, notificationRowContentBinderLogger2, "applying view");
                        }
                    }

                    /* JADX WARN: Removed duplicated region for block: B:38:0x0192 A[Catch: NameNotFoundException -> 0x0176, TryCatch #0 {NameNotFoundException -> 0x0176, blocks: (B:27:0x0123, B:29:0x015c, B:31:0x0162, B:33:0x016d, B:38:0x0192, B:40:0x01a5, B:42:0x01b3, B:44:0x01b9, B:45:0x01df, B:47:0x01ff, B:48:0x0205, B:50:0x020b, B:51:0x020f, B:53:0x0222, B:55:0x01d4, B:56:0x01db, B:57:0x0226, B:58:0x0179, B:60:0x0181, B:62:0x0189), top: B:26:0x0123 }] */
                    /* JADX WARN: Removed duplicated region for block: B:40:0x01a5 A[Catch: NameNotFoundException -> 0x0176, TryCatch #0 {NameNotFoundException -> 0x0176, blocks: (B:27:0x0123, B:29:0x015c, B:31:0x0162, B:33:0x016d, B:38:0x0192, B:40:0x01a5, B:42:0x01b3, B:44:0x01b9, B:45:0x01df, B:47:0x01ff, B:48:0x0205, B:50:0x020b, B:51:0x020f, B:53:0x0222, B:55:0x01d4, B:56:0x01db, B:57:0x0226, B:58:0x0179, B:60:0x0181, B:62:0x0189), top: B:26:0x0123 }] */
                    /* JADX WARN: Removed duplicated region for block: B:57:0x0226 A[Catch: NameNotFoundException -> 0x0176, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x0176, blocks: (B:27:0x0123, B:29:0x015c, B:31:0x0162, B:33:0x016d, B:38:0x0192, B:40:0x01a5, B:42:0x01b3, B:44:0x01b9, B:45:0x01df, B:47:0x01ff, B:48:0x0205, B:50:0x020b, B:51:0x020f, B:53:0x0222, B:55:0x01d4, B:56:0x01db, B:57:0x0226, B:58:0x0179, B:60:0x0181, B:62:0x0189), top: B:26:0x0123 }] */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onViewApplied(android.view.View r13) {
                        /*
                            Method dump skipped, instructions count: 685
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$applyRemoteView$listener$1.onViewApplied(android.view.View):void");
                    }

                    public final void onViewInflated(View view2) {
                        if (view2 instanceof ImageMessageConsumer) {
                            ((ImageMessageConsumer) view2).setImageResolver(ExpandableNotificationRow.this.mImageResolver);
                        }
                    }
                };
                if (z3) {
                    reapplyAsync = remoteView.applyAsync(inflationProgress.packageContext, viewGroup, executor, onViewAppliedListener, interactionHandler);
                    reapplyAsync.getClass();
                } else {
                    reapplyAsync = remoteView.reapplyAsync(inflationProgress.packageContext, view, executor, onViewAppliedListener, interactionHandler);
                    reapplyAsync.getClass();
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
                    throw new IllegalArgumentException("Required value was null.");
                }
                if (notificationViewWrapper == null) {
                    throw new IllegalArgumentException("Required value was null.");
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

        public final boolean canReapplyRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
            return (remoteViews == null && remoteViews2 == null) || !(remoteViews == null || remoteViews2 == null || remoteViews2.getPackage() == null || remoteViews.getPackage() == null || !Intrinsics.areEqual(remoteViews.getPackage(), remoteViews2.getPackage()) || remoteViews.getLayoutId() != remoteViews2.getLayoutId() || remoteViews2.hasFlags(1));
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
            if (!r1) {
                return "inflated notification does not meet minimum height requirement";
            }
            NotificationCustomContentMemoryVerifier notificationCustomContentMemoryVerifier = NotificationCustomContentMemoryVerifier.INSTANCE;
            return null;
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InflationProgress {
        public final NotificationContentModel contentModel;
        public InflatedSmartReplyViewHolder expandedInflatedSmartReplies;
        public InflatedSmartReplyViewHolder headsUpInflatedSmartReplies;
        public View inflatedContentView;
        public View inflatedExpandedView;
        public View inflatedHeadsUpView;
        public View inflatedPromotedOngoingView;
        public HybridNotificationView inflatedPublicSingleLineView;
        public View inflatedPublicView;
        public HybridNotificationView inflatedSingleLineView;
        public InflatedSmartReplyState inflatedSmartReplyState;
        public final Context packageContext;
        public final NewRemoteViews remoteViews;
        public final RowImageInflater rowImageInflater;

        public InflationProgress(Context context, RowImageInflater rowImageInflater, NewRemoteViews newRemoteViews, NotificationContentModel notificationContentModel, PromotedNotificationContentModels promotedNotificationContentModels) {
            this.packageContext = context;
            this.rowImageInflater = rowImageInflater;
            this.remoteViews = newRemoteViews;
            this.contentModel = notificationContentModel;
        }
    }

    public NotificationRowContentBinderImpl(NotifRemoteViewCache notifRemoteViewCache, NotificationRemoteInputManager notificationRemoteInputManager, ConversationNotificationProcessor conversationNotificationProcessor, Executor executor, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, PromotedNotificationContentExtractor promotedNotificationContentExtractor, NotificationRowContentBinderLogger notificationRowContentBinderLogger, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) {
        this.remoteViewCache = notifRemoteViewCache;
        this.remoteInputManager = notificationRemoteInputManager;
        this.conversationProcessor = conversationNotificationProcessor;
        this.inflationExecutor = executor;
        this.smartReplyStateInflater = smartReplyStateInflater;
        this.notifLayoutInflaterFactoryProvider = provider;
        this.headsUpStyleProvider = headsUpStyleProvider;
        this.promotedNotificationContentExtractor = promotedNotificationContentExtractor;
        this.logger = notificationRowContentBinderLogger;
        this.faceWidgetNotificationControllerWrapper = faceWidgetNotificationControllerWrapper;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationRowContentBinderRefactor.$r8$clinit;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final void bindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i, NotificationRowContentBinder.BindParams bindParams, boolean z, RowContentBindStage.AnonymousClass1 anonymousClass1) {
        SparseArray sparseArray;
        expandableNotificationRow.getClass();
        String str = expandableNotificationRow.mLoggingKey;
        NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.logger;
        notificationRowContentBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowContentBinderLogger$$ExternalSyntheticLambda0 notificationRowContentBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowContentBinderLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = notificationRowContentBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
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
                List<Notification.MessagingStyle.Message> messagesFromBundleArray2 = parcelableArray2 != null ? Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray2) : null;
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
        if ((i & 16) != 0) {
            expandableNotificationRow.mPrivateLayout.removeContentInactiveRunnable(3);
        }
        if ((i & 128) != 0) {
            expandableNotificationRow.mPublicLayout.removeContentInactiveRunnable(3);
        }
        AsyncInflationTask asyncInflationTask = new AsyncInflationTask(this.inflationExecutor, this.inflateSynchronously, i, this.remoteViewCache, notificationEntry, this.conversationProcessor, expandableNotificationRow, bindParams, anonymousClass1, this.remoteInputManager.mInteractionHandler, this.smartReplyStateInflater, this.notifLayoutInflaterFactoryProvider, this.headsUpStyleProvider, this.promotedNotificationContentExtractor, this.logger, this.faceWidgetNotificationControllerWrapper);
        if (!this.inflateSynchronously) {
            asyncInflationTask.executeOnExecutor(this.inflationExecutor, new Void[0]);
        } else {
            Void[] voidArr = new Void[0];
            asyncInflationTask.onPostExecute(Result.m3421boximpl(asyncInflationTask.m3066doInBackgroundIoAF18A()));
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final boolean cancelBind(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        boolean abortTask = notificationEntry.abortTask();
        if (abortTask) {
            String str = expandableNotificationRow.mLoggingKey;
            NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.logger;
            notificationRowContentBinderLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotificationRowContentBinderLogger$$ExternalSyntheticLambda0 notificationRowContentBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowContentBinderLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = notificationRowContentBinderLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).str1 = str;
            logBuffer.commit(obtain);
        }
        return abortTask;
    }

    public final InflationProgress inflateNotificationViews(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinder.BindParams bindParams, boolean z, int i, Notification.Builder builder, Context context, SmartReplyStateInflater smartReplyStateInflater, PromotedNotificationContentExtractor promotedNotificationContentExtractor) {
        HybridNotificationView hybridNotificationView;
        Context context2 = expandableNotificationRow.getContext();
        context2.getClass();
        HeadsUpStyleProvider headsUpStyleProvider = this.headsUpStyleProvider;
        ConversationNotificationProcessor conversationNotificationProcessor = this.conversationProcessor;
        Companion companion = Companion;
        InflationProgress access$beginInflationAsync = Companion.access$beginInflationAsync(companion, i, notificationEntry, builder, bindParams, context2, context, expandableNotificationRow, this.notifLayoutInflaterFactoryProvider, headsUpStyleProvider, conversationNotificationProcessor, this.logger);
        Companion.access$inflateSmartReplyViews(companion, access$beginInflationAsync, i, notificationEntry, context2, context, expandableNotificationRow.mPrivateLayout.mCurrentSmartReplyState, smartReplyStateInflater, this.logger);
        NotificationContentModel notificationContentModel = access$beginInflationAsync.contentModel;
        SingleLineViewModel singleLineViewModel = notificationContentModel.singleLineViewModel;
        HybridNotificationView hybridNotificationView2 = null;
        NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.logger;
        if (singleLineViewModel != null) {
            hybridNotificationView = SingleLineViewInflater.inflatePrivateSingleLineView(singleLineViewModel.conversationData != null, i, notificationEntry, context2, notificationRowContentBinderLogger);
        } else {
            hybridNotificationView = null;
        }
        access$beginInflationAsync.inflatedSingleLineView = hybridNotificationView;
        SingleLineViewModel singleLineViewModel2 = notificationContentModel.publicSingleLineViewModel;
        if (singleLineViewModel2 != null) {
            hybridNotificationView2 = SingleLineViewInflater.inflatePublicSingleLineView(singleLineViewModel2.conversationData != null, i, notificationEntry, context2, notificationRowContentBinderLogger);
        }
        access$beginInflationAsync.inflatedPublicSingleLineView = hybridNotificationView2;
        Companion.access$apply(companion, this.inflationExecutor, z, bindParams.isMinimized, access$beginInflationAsync, i, this.remoteViewCache, notificationEntry, expandableNotificationRow, this.remoteInputManager.mInteractionHandler, null, this.logger, this.faceWidgetNotificationControllerWrapper);
        return access$beginInflationAsync;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public void setInflateSynchronously(boolean z) {
        this.inflateSynchronously = z;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final void unbindContent(final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, int i) {
        String str = expandableNotificationRow.mLoggingKey;
        NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.logger;
        notificationRowContentBinderLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowContentBinderLogger$$ExternalSyntheticLambda0 notificationRowContentBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowContentBinderLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = notificationRowContentBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
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
                            NotificationContentView notificationContentView = ExpandableNotificationRow.this.mPrivateLayout;
                            notificationContentView.mHeadsUpInflatedSmartReplies = null;
                            notificationContentView.mHeadsUpSmartReplyView = null;
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
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(3, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$5
                        @Override // java.lang.Runnable
                        public final void run() {
                            ExpandableNotificationRow.this.mPrivateLayout.setSingleLineView(null);
                        }
                    });
                } else if (i2 == 128) {
                    expandableNotificationRow.mPublicLayout.performWhenContentInactive(3, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$6
                        @Override // java.lang.Runnable
                        public final void run() {
                            ExpandableNotificationRow.this.mPublicLayout.setSingleLineView(null);
                        }
                    });
                }
            }
            i &= ~i2;
            i2 <<= 1;
        }
    }
}
