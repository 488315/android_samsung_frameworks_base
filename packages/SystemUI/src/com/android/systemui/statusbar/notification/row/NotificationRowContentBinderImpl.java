package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
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
import android.util.Pools;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.core.os.BundleKt;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.NotificationRowIconView;
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
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.ConversationNotificationManager$sam$java_util_function_BiFunction$0;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationUtils;
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
import com.android.systemui.statusbar.notification.row.RowImageInflater;
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
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingType;
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
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Pair;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import noticolorpicker.NotificationColorPicker;

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

    public abstract class ApplyCallback {
        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);
    }

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

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

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

        public static final InflationProgress access$doInBackgroundInternal(AsyncInflationTask asyncInflationTask) throws Resources.NotFoundException {
            HybridNotificationView hybridNotificationViewInflatePrivateSingleLineView;
            Set set;
            StatusBarNotification statusBarNotification = asyncInflationTask.entry.mSbn;
            try {
                Notification.addFieldsFromContext(asyncInflationTask.row.getContext().getPackageManager().getApplicationInfoAsUser(statusBarNotification.getPackageName(), 8192, UserHandle.getUserId(statusBarNotification.getUid())), statusBarNotification.getNotification());
            } catch (PackageManager.NameNotFoundException unused) {
            }
            Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(asyncInflationTask.row.getContext(), statusBarNotification.getNotification());
            Context packageContext = statusBarNotification.getPackageContext(asyncInflationTask.row.getContext());
            Context rtlEnabledContext = builderRecoverBuilder.usesTemplate() ? new RtlEnabledContext(packageContext) : packageContext;
            Companion companion = NotificationRowContentBinderImpl.Companion;
            InflationProgress inflationProgressAccess$beginInflationAsync = Companion.access$beginInflationAsync(companion, asyncInflationTask.reInflateFlags, asyncInflationTask.entry, builderRecoverBuilder, asyncInflationTask.bindParams, asyncInflationTask.row.getContext(), rtlEnabledContext, asyncInflationTask.row, asyncInflationTask.notifLayoutInflaterFactoryProvider, asyncInflationTask.headsUpStyleProvider, asyncInflationTask.conversationProcessor, asyncInflationTask.logger);
            asyncInflationTask.logger.logAsyncTaskProgress(asyncInflationTask.row.mLoggingKey, "getting existing smart reply state (on wrong thread!)");
            InflatedSmartReplyState inflatedSmartReplyState = asyncInflationTask.row.mPrivateLayout.mCurrentSmartReplyState;
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "inflating smart reply views");
            Companion.access$inflateSmartReplyViews(companion, inflationProgressAccess$beginInflationAsync, asyncInflationTask.reInflateFlags, asyncInflationTask.entry, asyncInflationTask.row.getContext(), rtlEnabledContext, inflatedSmartReplyState, asyncInflationTask.smartRepliesInflater, asyncInflationTask.logger);
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "inflating single line view");
            NotificationContentModel notificationContentModel = inflationProgressAccess$beginInflationAsync.contentModel;
            SingleLineViewModel singleLineViewModel = notificationContentModel.singleLineViewModel;
            HybridNotificationView hybridNotificationViewInflatePublicSingleLineView = null;
            if (singleLineViewModel != null) {
                hybridNotificationViewInflatePrivateSingleLineView = SingleLineViewInflater.inflatePrivateSingleLineView(singleLineViewModel.conversationData != null, asyncInflationTask.reInflateFlags, asyncInflationTask.entry, asyncInflationTask.row.getContext(), asyncInflationTask.logger);
            } else {
                hybridNotificationViewInflatePrivateSingleLineView = null;
            }
            inflationProgressAccess$beginInflationAsync.inflatedSingleLineView = hybridNotificationViewInflatePrivateSingleLineView;
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "inflating public single line view");
            SingleLineViewModel singleLineViewModel2 = notificationContentModel.publicSingleLineViewModel;
            if (singleLineViewModel2 != null) {
                hybridNotificationViewInflatePublicSingleLineView = SingleLineViewInflater.inflatePublicSingleLineView(singleLineViewModel2.conversationData != null, asyncInflationTask.reInflateFlags, asyncInflationTask.entry, asyncInflationTask.row.getContext(), asyncInflationTask.logger);
            }
            inflationProgressAccess$beginInflationAsync.inflatedPublicSingleLineView = hybridNotificationViewInflatePublicSingleLineView;
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "loading RON images");
            inflationProgressAccess$beginInflationAsync.rowImageInflater.getClass();
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "getting row image resolver (on wrong thread!)");
            final NotificationInlineImageResolver notificationInlineImageResolver = asyncInflationTask.row.mImageResolver;
            asyncInflationTask.logger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(asyncInflationTask.entry), "waiting for preloaded images");
            if (notificationInlineImageResolver.hasCache() && (set = notificationInlineImageResolver.mWantedUriSet) != null) {
                final long jElapsedRealtime = SystemClock.elapsedRealtime() + 1000;
                set.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NotificationInlineImageResolver notificationInlineImageResolver2 = notificationInlineImageResolver;
                        long j = jElapsedRealtime;
                        int i = NotificationInlineImageResolver.$r8$clinit;
                        notificationInlineImageResolver2.getClass();
                        notificationInlineImageResolver2.loadImageFromCache((Uri) obj, j - SystemClock.elapsedRealtime());
                    }
                });
            }
            return inflationProgressAccess$beginInflationAsync;
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
            return Result.m3440boximpl(m3081doInBackgroundIoAF18A());
        }

        /* renamed from: doInBackground-IoAF18A, reason: not valid java name */
        public final Object m3081doInBackgroundIoAF18A() {
            Object failure;
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
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
                if (zIsEnabled) {
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

        /* JADX WARN: Removed duplicated region for block: B:121:0x020d  */
        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onAsyncInflationFinished() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
            TextView textView;
            TextView textView2;
            View viewFindViewById;
            View viewFindViewById2;
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
                        View viewFindViewById3 = view.findViewById(R.id.media_carousel_layout);
                        if (viewFindViewById3 != null) {
                            viewGroup.removeView(viewFindViewById3);
                        }
                        View viewFindViewById4 = notificationContentView.mExpandedChild.findViewById(R.id.ongoing_activity_expand_normal_layout);
                        if (viewFindViewById4 != null) {
                            viewFindViewById4.setVisibility(8);
                        }
                        ViewGroup viewGroup2 = (ViewGroup) notificationContentView.mExpandedChild.findViewById(16909885);
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
                    boolean z = notificationContentView2.mNotificationEntry.mSbn.getNotification().findRemoteInputActionPair(true) != null;
                    View view2 = notificationContentView2.mExpandedChild;
                    if (view2 != null) {
                        NotificationContentView.RemoteInputViewData remoteInputViewDataApplyRemoteInput = notificationContentView2.applyRemoteInput(view2, notificationContentView2.mNotificationEntry, z, notificationContentView2.mPreviousExpandedRemoteInputIntent, notificationContentView2.mExpandedWrapper);
                        notificationContentView2.mExpandedRemoteInput = remoteInputViewDataApplyRemoteInput.mView;
                        RemoteInputViewController remoteInputViewController = remoteInputViewDataApplyRemoteInput.mController;
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
                    if (view3 != null && (viewFindViewById = view3.findViewById(android.R.id.overlay_display_window_title)) != null && (viewFindViewById instanceof MessagingImageMessage) && (viewFindViewById2 = notificationContentView2.mHeadsUpChild.findViewById(android.R.id.resolver_empty_state_icon)) != null) {
                        viewFindViewById2.setMinimumHeight(notificationContentView2.getResources().getDimensionPixelSize(R.dimen.notification_empty_text_area_min_height));
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
                        SmartReplyView smartReplyViewApplySmartReplyView = NotificationContentView.applySmartReplyView(notificationContentView2.mExpandedChild, notificationContentView2.mCurrentSmartReplyState, notificationContentView2.mNotificationEntry, notificationContentView2.mExpandedInflatedSmartReplies, false);
                        notificationContentView2.mExpandedSmartReplyView = smartReplyViewApplySmartReplyView;
                        if (smartReplyViewApplySmartReplyView != null) {
                            InflatedSmartReplyState inflatedSmartReplyState2 = notificationContentView2.mCurrentSmartReplyState;
                            SmartReplyView.SmartReplies smartReplies = inflatedSmartReplyState2.smartReplies;
                            SmartReplyView.SmartActions smartActions = inflatedSmartReplyState2.smartActions;
                            if (smartReplies != null || smartActions != null) {
                                int size = smartReplies == null ? 0 : smartReplies.choices.size();
                                int size2 = smartActions == null ? 0 : smartActions.actions.size();
                                boolean z2 = smartReplies == null ? smartActions.fromAssistant : smartReplies.fromAssistant;
                                if (smartReplies != null) {
                                    SmartReplyConstants smartReplyConstants = notificationContentView2.mSmartReplyConstants;
                                    int editChoicesBeforeSending = smartReplies.remoteInput.getEditChoicesBeforeSending();
                                    smartReplyConstants.getClass();
                                    boolean z3 = editChoicesBeforeSending != 1 ? editChoicesBeforeSending != 2 ? smartReplyConstants.mEditChoicesBeforeSending : true : false;
                                    SmartReplyController smartReplyController = notificationContentView2.mSmartReplyController;
                                    NotificationEntry notificationEntry2 = notificationContentView2.mNotificationEntry;
                                    smartReplyController.getClass();
                                    try {
                                        smartReplyController.mBarService.onNotificationSmartSuggestionsAdded(notificationEntry2.mSbn.getKey(), size, size2, z2, z3);
                                    } catch (RemoteException unused) {
                                    }
                                }
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
                notificationContentView2.updateSystemActionsMargin();
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
            expandableNotificationRow.mPublicLayout.updateExpandButtons(expandableNotificationRow.mShowPublicExpander);
            expandableNotificationRow.updateLimits();
            expandableNotificationRow.updateShelfIconColor();
            expandableNotificationRow.updateBackgroundColors();
            ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateAllTextViewColors(expandableNotificationRow, expandableNotificationRow.mDimmed);
            for (NotificationContentView notificationContentView3 : expandableNotificationRow.mLayouts) {
                View view9 = notificationContentView3.mContractedChild;
                if (view9 != null && (textView2 = (TextView) view9.findViewById(android.R.id.inter_word)) != null && textView2.getText().toString().contains("@")) {
                    notificationContentView3.mIsContractedHeaderContainAtMark = true;
                }
                View view10 = notificationContentView3.mExpandedChild;
                if (view10 != null && (textView = (TextView) view10.findViewById(android.R.id.inter_word)) != null && textView.getText().toString().contains("@")) {
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
            Object objM3442unboximpl = ((Result) obj).m3442unboximpl();
            if (objM3442unboximpl instanceof Result.Failure) {
                asyncInflationTask = this;
            } else {
                asyncInflationTask = this;
                asyncInflationTask.cancellationSignal = Companion.access$apply(NotificationRowContentBinderImpl.Companion, this.inflationExecutor, this.inflateSynchronously, this.bindParams.isMinimized, (InflationProgress) objM3442unboximpl, this.reInflateFlags, this.remoteViewCache, this.entry, this.row, this.remoteViewClickHandler, asyncInflationTask, this.logger, this.faceWidgetNotificationControllerWrapper);
            }
            Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(objM3442unboximpl);
            if (thM3441exceptionOrNullimpl != null) {
                asyncInflationTask.handleError$1((Exception) thM3441exceptionOrNullimpl);
            }
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            Trace.beginAsyncSection("NotificationRowContentBinderImpl.AsyncInflationTask", System.identityHashCode(this));
        }
    }

    public final class Companion {

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
                        function1.mo781invoke(view);
                        ((NotifRemoteViewCacheImpl) notifRemoteViewCache).putCachedView(notificationEntry, i, remoteViews);
                    } else if (z && remoteViews == null) {
                        function1.mo781invoke(null);
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

        public static final CancellationSignal access$apply(Companion companion, Executor executor, boolean z, boolean z2, final InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, RemoteViews.InteractionHandler interactionHandler, AsyncInflationTask asyncInflationTask, final NotificationRowContentBinderLogger notificationRowContentBinderLogger, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) throws InflationException {
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
            final HashMap<Integer, CancellationSignal> map = new HashMap<>();
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
                        notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry2), "contracted view applied");
                        inflationProgress.inflatedContentView = view;
                        if (notificationEntry2.isOngoingActivity()) {
                            NotificationRowContentBinderImplKt.setTooltipTextForOA(view);
                        }
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying contracted view");
                notificationContentView = notificationContentView4;
                companion.applyRemoteView(executor, z, z2, inflationProgress, i, 1, notifRemoteViewCacheImpl, notificationEntry, expandableNotificationRow, z3, interactionHandler, asyncInflationTask, notificationContentView3, notificationContentView3.mContractedChild, notificationContentView3.getVisibleWrapper(0), map, applyCallback, notificationRowContentBinderLogger, faceWidgetNotificationControllerWrapper);
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
                        View viewFromNowBar;
                        NotificationEntry notificationEntry2 = notificationEntry;
                        notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry2), "expanded view applied");
                        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                        String str = notificationEntry2.mKey;
                        ongoingActivityDataHelper.getClass();
                        OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(str);
                        if (pendingOngoingActivityData == null) {
                            pendingOngoingActivityData = OngoingActivityDataHelper.getOngoingActivityDataByKey(notificationEntry2.mKey);
                        }
                        if (pendingOngoingActivityData == null || pendingOngoingActivityData.mCustomExpandedCardView == null) {
                            viewFromNowBar = view;
                        } else {
                            viewFromNowBar = faceWidgetNotificationControllerWrapper2.getViewFromNowBar(view, BundleKt.bundleOf(new Pair("type", "ENR")));
                        }
                        if (notificationEntry2.isOngoingActivity()) {
                            NotificationRowContentBinderImplKt.setTooltipTextForOA(view);
                        }
                        inflationProgress.inflatedExpandedView = viewFromNowBar;
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying expanded view");
                FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper3 = faceWidgetNotificationControllerWrapper2;
                NotificationRowContentBinderLogger notificationRowContentBinderLogger3 = notificationRowContentBinderLogger2;
                i2 = i;
                companion.applyRemoteView(executor, z, z2, inflationProgress, i2, 2, notifRemoteViewCacheImpl2, notificationEntry, expandableNotificationRow, z4, interactionHandler, asyncInflationTask, notificationContentView3, notificationContentView3.mExpandedChild, notificationContentView3.getVisibleWrapper(1), map, applyCallback2, notificationRowContentBinderLogger3, faceWidgetNotificationControllerWrapper3);
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
                        View viewFromNowBar;
                        NotificationEntry notificationEntry2 = notificationEntry;
                        notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry2), "promoted ongoing view applied");
                        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                        String str = notificationEntry2.mKey;
                        ongoingActivityDataHelper.getClass();
                        OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(str);
                        if (pendingOngoingActivityData == null) {
                            pendingOngoingActivityData = OngoingActivityDataHelper.getOngoingActivityDataByKey(notificationEntry2.mKey);
                        }
                        if (pendingOngoingActivityData == null || pendingOngoingActivityData.mCustomExpandedCardView == null) {
                            viewFromNowBar = view;
                        } else {
                            viewFromNowBar = faceWidgetNotificationControllerWrapper2.getViewFromNowBar(view, BundleKt.bundleOf(new Pair("type", "OA")));
                        }
                        if (notificationEntry2.isOngoingActivity()) {
                            NotificationRowContentBinderImplKt.setTooltipTextForOA(view);
                        }
                        inflationProgress.inflatedPromotedOngoingView = viewFromNowBar;
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying promoted ongoing view");
                notificationContentView2 = notificationContentView3;
                NotificationRowContentBinderLogger notificationRowContentBinderLogger4 = notificationRowContentBinderLogger2;
                companion2 = companion;
                companion2.applyRemoteView(executor, z, z2, inflationProgress, i2, 256, notifRemoteViewCache, notificationEntry, expandableNotificationRow, true, interactionHandler, asyncInflationTask, null, null, null, map, applyCallback3, notificationRowContentBinderLogger4, faceWidgetNotificationControllerWrapper2);
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
                        notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "heads up view applied");
                        inflationProgress.inflatedHeadsUpView = view;
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying heads up view");
                NotificationContentView notificationContentView5 = notificationContentView2;
                NotificationRowContentBinderLogger notificationRowContentBinderLogger5 = notificationRowContentBinderLogger2;
                companion2.applyRemoteView(executor, z, z2, inflationProgress, i, 4, notifRemoteViewCacheImpl3, notificationEntry, expandableNotificationRow, z5, interactionHandler, asyncInflationTask, notificationContentView5, notificationContentView5.mHeadsUpChild, notificationContentView5.getVisibleWrapper(2), map, applyCallback4, notificationRowContentBinderLogger5, faceWidgetNotificationControllerWrapper);
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
                        notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "public view applied");
                        inflationProgress.inflatedPublicView = view;
                    }
                };
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "applying public view");
                NotificationContentView notificationContentView6 = notificationContentView;
                companion2.applyRemoteView(executor, z, z2, inflationProgress, i, 8, notifRemoteViewCacheImpl4, notificationEntry, expandableNotificationRow, z6, interactionHandler, asyncInflationTask, notificationContentView6, notificationContentView6.mContractedChild, notificationContentView6.getVisibleWrapper(0), map, applyCallback5, notificationRowContentBinderLogger2, faceWidgetNotificationControllerWrapper);
            }
            finishIfDone(inflationProgress, i, notifRemoteViewCache, map, asyncInflationTask, notificationEntry, expandableNotificationRow, notificationRowContentBinderLogger);
            CancellationSignal cancellationSignal = new CancellationSignal();
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$1
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    notificationRowContentBinderLogger.logAsyncTaskProgress(NotificationUtilsKt.getLogKey(notificationEntry), "apply cancelled");
                    Trace.endAsyncSection("NotificationRowContentBinderImpl#apply", System.identityHashCode(expandableNotificationRow));
                    map.values().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$apply$1.1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((CancellationSignal) obj).cancel();
                        }
                    });
                }
            });
            return cancellationSignal;
        }

        /* JADX WARN: Removed duplicated region for block: B:130:0x0337  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0111  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x011a  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x0137  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final InflationProgress access$beginInflationAsync(Companion companion, int i, final NotificationEntry notificationEntry, final Notification.Builder builder, NotificationRowContentBinder.BindParams bindParams, Context context, Context context2, ExpandableNotificationRow expandableNotificationRow, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, ConversationNotificationProcessor conversationNotificationProcessor, NotificationRowContentBinderLogger notificationRowContentBinderLogger) throws Resources.NotFoundException {
            int i2;
            RowImageInflaterStub rowImageInflaterStub;
            Notification.MessagingStyle messagingStyle;
            NotificationRowContentBinderLogger notificationRowContentBinderLogger2;
            NotificationRowContentBinder.BindParams bindParams2;
            RemoteViews remoteViewsAccess$createContentView;
            RemoteViews remoteViews;
            RemoteViews remoteViews2;
            Context context3;
            RemoteViews remoteViews3;
            RemoteViews remoteViewsCreateHeadsUpContentView;
            Context context4;
            RemoteViews remoteViews4;
            NotifLayoutInflaterFactory.Provider provider2;
            SingleLineViewModel singleLineViewModelInflateSingleLineViewModel;
            Notification.Builder builder2;
            SingleLineViewModel singleLineViewModelInflatePublicSingleLineViewModel;
            RemoteViews remoteViewsMakePublicContentView;
            RemoteViews remoteViewsAccess$createExpandedView;
            RemoteViews remoteViewsAccess$createExpandedView2;
            Notification.MessagingStyle messagingStyle2;
            int i3;
            companion.getClass();
            RowImageInflater.Companion companion2 = RowImageInflater.Companion;
            ImageModelIndex imageModelIndex = expandableNotificationRow.mImageModelIndex;
            int i4 = i & 1;
            companion2.getClass();
            RowImageInflaterStub rowImageInflaterStub2 = RowImageInflaterStub.INSTANCE;
            PromotedNotificationContentModel.Companion.getClass();
            if (notificationEntry.mRanking.isConversation()) {
                conversationNotificationProcessor.getClass();
                Notification.Style style = builder.getStyle();
                Notification.MessagingStyle messagingStyle3 = style instanceof Notification.MessagingStyle ? (Notification.MessagingStyle) style : null;
                if (messagingStyle3 == null) {
                    i2 = i4;
                    rowImageInflaterStub = rowImageInflaterStub2;
                    messagingStyle2 = null;
                } else {
                    boolean z = false;
                    messagingStyle3.setConversationType(notificationEntry.mRanking.getChannel().isImportantConversation() ? 2 : notificationEntry.mRanking.isConversation() ? 1 : 0);
                    ShortcutInfo conversationShortcutInfo = notificationEntry.mRanking.getConversationShortcutInfo();
                    if (conversationShortcutInfo != null) {
                        notificationRowContentBinderLogger.logAsyncTaskProgress(NotificationUtils.logKey(notificationEntry), "getting shortcut icon");
                        messagingStyle3.setShortcutIcon(conversationNotificationProcessor.launcherApps.getShortcutIcon(conversationShortcutInfo));
                        CharSequence label = conversationShortcutInfo.getLabel();
                        if (label != null) {
                            messagingStyle3.setConversationTitle(label);
                        }
                    }
                    boolean zEquals = notificationEntry.mSbn.getPackageName().equals("com.kakao.talk");
                    String str = notificationEntry.mKey;
                    if (zEquals) {
                        List<Bundle> listSemGetNotificationHistoryForPackage = ((NotificationManager) conversationNotificationProcessor.context.getSystemService(NotificationManager.class)).semGetNotificationHistoryForPackage(conversationNotificationProcessor.context.getPackageName(), conversationNotificationProcessor.context.getAttributionTag(), notificationEntry.mSbn.getUserId(), notificationEntry.mSbn.getPackageName(), notificationEntry.mSbn.getKey(), 5);
                        ArrayList arrayList = new ArrayList();
                        if (listSemGetNotificationHistoryForPackage != null) {
                            int i5 = 0;
                            for (Bundle bundle : listSemGetNotificationHistoryForPackage) {
                                i2 = i4;
                                rowImageInflaterStub = rowImageInflaterStub2;
                                if (bundle.getBoolean("isChecked", z) || (i3 = i5) == 5) {
                                    break;
                                }
                                arrayList.add(bundle);
                                i5 = i3 + 1;
                                rowImageInflaterStub2 = rowImageInflaterStub;
                                i4 = i2;
                                z = false;
                            }
                            i2 = i4;
                            rowImageInflaterStub = rowImageInflaterStub2;
                            String simpleName = Reflection.getOrCreateKotlinClass(ConversationNotificationProcessor.class).getSimpleName();
                            int size = arrayList.size();
                            List<Notification.MessagingStyle.Message> messages = messagingStyle3.getMessages();
                            Integer numValueOf = messages == null ? Integer.valueOf(messages.size()) : null;
                            StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(size, "addHistory to ", str, " h.size ", "  m.size ");
                            sbM890m.append(numValueOf);
                            Log.d(simpleName, sbM890m.toString());
                            if (arrayList.size() > 1) {
                                int size2 = arrayList.size();
                                List<Notification.MessagingStyle.Message> messages2 = messagingStyle3.getMessages();
                                if (size2 > (messages2 != null ? messages2.size() : 0)) {
                                    List<Notification.MessagingStyle.Message> messages3 = messagingStyle3.getMessages();
                                    if (messages3 != null) {
                                        messages3.clear();
                                    }
                                    int size3 = arrayList.size();
                                    int i6 = 0;
                                    int i7 = 0;
                                    while (i6 < size3) {
                                        Object obj = arrayList.get(i6);
                                        int i8 = i6 + 1;
                                        int i9 = i7 + 1;
                                        if (i7 < 0) {
                                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                                            throw null;
                                        }
                                        Bundle bundle2 = (Bundle) arrayList.get((arrayList.size() - 1) - i7);
                                        int i10 = size3;
                                        messagingStyle3.addMessage(bundle2.getString("text", ""), bundle2.getLong("when", 0L), bundle2.getString(UniversalCredentialUtil.AGENT_TITLE, "").equals("NOUI_2023") ? conversationNotificationProcessor.context.getString(R.string.notification_conversation_history_owner) : bundle2.getString(UniversalCredentialUtil.AGENT_TITLE, ""));
                                        i7 = i9;
                                        size3 = i10;
                                        i6 = i8;
                                    }
                                }
                            }
                        } else {
                            i2 = i4;
                            rowImageInflaterStub = rowImageInflaterStub2;
                            String simpleName2 = Reflection.getOrCreateKotlinClass(ConversationNotificationProcessor.class).getSimpleName();
                            int size4 = arrayList.size();
                            List<Notification.MessagingStyle.Message> messages4 = messagingStyle3.getMessages();
                            if (messages4 == null) {
                            }
                            StringBuilder sbM890m2 = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(size4, "addHistory to ", str, " h.size ", "  m.size ");
                            sbM890m2.append(numValueOf);
                            Log.d(simpleName2, sbM890m2.toString());
                            if (arrayList.size() > 1) {
                            }
                        }
                    } else {
                        i2 = i4;
                        rowImageInflaterStub = rowImageInflaterStub2;
                    }
                    final ConversationNotificationManager conversationNotificationManager = conversationNotificationProcessor.conversationNotificationManager;
                    Object objCompute = conversationNotificationManager.states.compute(str, new ConversationNotificationManager$sam$java_util_function_BiFunction$0(new Function2() { // from class: com.android.systemui.statusbar.notification.ConversationNotificationManager$$ExternalSyntheticLambda3
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            int i11 = 1;
                            Notification.Builder builder3 = builder;
                            ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) obj3;
                            int i12 = ConversationNotificationManager.$r8$clinit;
                            if (conversationState != null) {
                                ConversationNotificationManager conversationNotificationManager2 = conversationNotificationManager;
                                conversationNotificationManager2.getClass();
                                Notification notification2 = conversationState.f134notification;
                                boolean zAreStyledNotificationsVisiblyDifferent = (notification2.flags & 8) != 0 ? false : Notification.areStyledNotificationsVisiblyDifferent(Notification.Builder.recoverBuilder(conversationNotificationManager2.context, notification2), builder3);
                                int i13 = conversationState.unreadCount;
                                if (zAreStyledNotificationsVisiblyDifferent) {
                                    i13++;
                                }
                                i11 = i13;
                            }
                            return new ConversationNotificationManager.ConversationState(i11, notificationEntry.mSbn.getNotification());
                        }
                    }));
                    objCompute.getClass();
                    messagingStyle3.setUnreadMessageCount(((ConversationNotificationManager.ConversationState) objCompute).unreadCount);
                    messagingStyle2 = messagingStyle3;
                }
                messagingStyle = messagingStyle2;
            } else {
                i2 = i4;
                rowImageInflaterStub = rowImageInflaterStub2;
                messagingStyle = null;
            }
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("NotificationContentInflater.createRemoteViews");
            }
            if (i2 != 0) {
                try {
                    notificationRowContentBinderLogger2 = notificationRowContentBinderLogger;
                    notificationRowContentBinderLogger2.logAsyncTaskProgress(expandableNotificationRow.mLoggingKey, "creating contracted remote view");
                    OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                    String str2 = expandableNotificationRow.mEntry.mKey;
                    ongoingActivityDataHelper.getClass();
                    OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(str2);
                    if (pendingOngoingActivityData == null) {
                        pendingOngoingActivityData = OngoingActivityDataHelper.getOngoingActivityDataByKey(expandableNotificationRow.mEntry.mKey);
                    }
                    if (!expandableNotificationRow.mEntry.isOngoingActivity() || pendingOngoingActivityData == null) {
                        bindParams2 = bindParams;
                        remoteViewsAccess$createContentView = access$createContentView(NotificationRowContentBinderImpl.Companion, builder, bindParams2.isMinimized);
                    } else {
                        String str3 = pendingOngoingActivityData.mPrimaryInfo;
                        bindParams2 = bindParams;
                        if (bindParams2.isMinimized) {
                            if (str3.equals("No primary info")) {
                                str3 = pendingOngoingActivityData.mAppName;
                            }
                            Notification.Builder contentTitle = builder.setContentTitle(str3);
                            String str4 = pendingOngoingActivityData.mSecondaryInfo;
                            if (str4 == null) {
                                str4 = "";
                            }
                            contentTitle.setContentText(str4);
                            remoteViewsAccess$createContentView = builder.makeLowPriorityContentView(false);
                        } else {
                            boolean zBooleanValue = expandableNotificationRow.mEntry.mIsRon.booleanValue();
                            boolean z2 = bindParams2.isMinimized;
                            if (zBooleanValue) {
                                builder.setColorized(false);
                                remoteViewsAccess$createContentView = access$createContentView(NotificationRowContentBinderImpl.Companion, builder, z2);
                                remoteViewsAccess$createContentView.setInt(16909885, "setBackgroundResource", 0);
                            } else {
                                remoteViewsAccess$createContentView = pendingOngoingActivityData.mOngoingCollapsedView;
                                if (remoteViewsAccess$createContentView == null) {
                                    remoteViewsAccess$createContentView = access$createContentView(NotificationRowContentBinderImpl.Companion, builder, z2);
                                }
                            }
                        }
                    }
                    remoteViews = remoteViewsAccess$createContentView;
                } catch (Throwable th) {
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                    throw th;
                }
            } else {
                bindParams2 = bindParams;
                notificationRowContentBinderLogger2 = notificationRowContentBinderLogger;
                remoteViews = null;
            }
            if ((i & 2) != 0) {
                notificationRowContentBinderLogger2.logAsyncTaskProgress(expandableNotificationRow.mLoggingKey, "creating expanded remote view");
                OngoingActivityDataHelper ongoingActivityDataHelper2 = OngoingActivityDataHelper.INSTANCE;
                String str5 = expandableNotificationRow.mEntry.mKey;
                ongoingActivityDataHelper2.getClass();
                OngoingActivityData pendingOngoingActivityData2 = OngoingActivityDataHelper.getPendingOngoingActivityData(str5);
                if (pendingOngoingActivityData2 == null) {
                    pendingOngoingActivityData2 = OngoingActivityDataHelper.getOngoingActivityDataByKey(expandableNotificationRow.mEntry.mKey);
                }
                if (!expandableNotificationRow.mEntry.isOngoingActivity() || pendingOngoingActivityData2 == null) {
                    remoteViewsAccess$createExpandedView2 = access$createExpandedView(NotificationRowContentBinderImpl.Companion, builder, bindParams2.isMinimized);
                } else if (expandableNotificationRow.mEntry.mIsRon.booleanValue()) {
                    builder.setColorized(false);
                    remoteViewsAccess$createExpandedView2 = access$createExpandedView(NotificationRowContentBinderImpl.Companion, builder, bindParams2.isMinimized);
                    if (remoteViewsAccess$createExpandedView2 != null) {
                        remoteViewsAccess$createExpandedView2.setInt(16909885, "setBackgroundResource", 0);
                        remoteViewsAccess$createExpandedView2.addFlags(1);
                    }
                    remoteViews2 = null;
                } else {
                    remoteViewsAccess$createExpandedView2 = pendingOngoingActivityData2.mOngoingENRExpandView;
                    if (remoteViewsAccess$createExpandedView2 == null) {
                        remoteViewsAccess$createExpandedView2 = access$createContentView(NotificationRowContentBinderImpl.Companion, builder, bindParams2.isMinimized);
                    }
                }
                remoteViews2 = remoteViewsAccess$createExpandedView2;
            } else {
                remoteViews2 = null;
            }
            if ((i & 256) != 0) {
                notificationRowContentBinderLogger2.logAsyncTaskProgress(expandableNotificationRow.mLoggingKey, "creating promoted ongoing card view");
                OngoingActivityDataHelper ongoingActivityDataHelper3 = OngoingActivityDataHelper.INSTANCE;
                String str6 = expandableNotificationRow.mEntry.mKey;
                ongoingActivityDataHelper3.getClass();
                OngoingActivityData pendingOngoingActivityData3 = OngoingActivityDataHelper.getPendingOngoingActivityData(str6);
                if (pendingOngoingActivityData3 == null) {
                    pendingOngoingActivityData3 = OngoingActivityDataHelper.getOngoingActivityDataByKey(expandableNotificationRow.mEntry.mKey);
                }
                if (!expandableNotificationRow.mEntry.isOngoingActivity() || pendingOngoingActivityData3 == null) {
                    context3 = context;
                    remoteViews3 = null;
                } else {
                    if (expandableNotificationRow.mEntry.mIsRon.booleanValue()) {
                        builder.setColorized(false);
                        remoteViewsAccess$createExpandedView = access$createExpandedView(NotificationRowContentBinderImpl.Companion, builder, bindParams2.isMinimized);
                        if (remoteViewsAccess$createExpandedView != null) {
                            remoteViewsAccess$createExpandedView.setInt(16909885, "setBackgroundResource", 0);
                            OngoingActivityLayoutUtil.INSTANCE.getClass();
                            context3 = context;
                            OngoingActivityLayoutUtil.refactorRonLayout(remoteViewsAccess$createExpandedView, pendingOngoingActivityData3, context3);
                            pendingOngoingActivityData3.mOngoingOAExpandView = remoteViewsAccess$createExpandedView;
                        }
                        context3 = context;
                        remoteViews3 = null;
                    } else {
                        context3 = context;
                        remoteViewsAccess$createExpandedView = pendingOngoingActivityData3.mOngoingOAExpandView;
                    }
                    remoteViews3 = remoteViewsAccess$createExpandedView;
                }
            }
            if ((i & 4) != 0) {
                notificationRowContentBinderLogger2.logAsyncTaskProgress(expandableNotificationRow.mLoggingKey, "creating heads up remote view");
                headsUpStyleProvider.getClass();
                OngoingActivityDataHelper ongoingActivityDataHelper4 = OngoingActivityDataHelper.INSTANCE;
                String str7 = expandableNotificationRow.mEntry.mKey;
                ongoingActivityDataHelper4.getClass();
                OngoingActivityData pendingOngoingActivityData4 = OngoingActivityDataHelper.getPendingOngoingActivityData(str7);
                if (pendingOngoingActivityData4 == null) {
                    pendingOngoingActivityData4 = OngoingActivityDataHelper.getOngoingActivityDataByKey(expandableNotificationRow.mEntry.mKey);
                }
                remoteViewsCreateHeadsUpContentView = (!expandableNotificationRow.mEntry.isOngoingActivity() || pendingOngoingActivityData4 == null || expandableNotificationRow.mEntry.mIsRon.booleanValue()) ? builder.createHeadsUpContentView() : pendingOngoingActivityData4.mOngoingCollapsedView;
            } else {
                remoteViewsCreateHeadsUpContentView = null;
            }
            if ((i & 8) != 0) {
                notificationRowContentBinderLogger2.logAsyncTaskProgress(expandableNotificationRow.mLoggingKey, "creating public remote view");
                if (bindParams2.redactionType == 2) {
                    context4 = context2;
                    remoteViewsMakePublicContentView = access$createSensitiveContentMessageNotification(NotificationRowContentBinderImpl.Companion, notificationEntry.mSbn.getNotification(), builder.getStyle(), context3, context4).createContentView();
                } else {
                    context4 = context2;
                    remoteViewsMakePublicContentView = builder.makePublicContentView(bindParams2.isMinimized);
                }
                remoteViews4 = remoteViewsMakePublicContentView;
            } else {
                context4 = context2;
                remoteViews4 = null;
            }
            Companion companion3 = NotificationRowContentBinderImpl.Companion;
            NewRemoteViews newRemoteViews = new NewRemoteViews(remoteViews, remoteViewsCreateHeadsUpContentView, remoteViews2, remoteViews4, null, null, remoteViews3);
            companion3.getClass();
            RemoteViews remoteViews5 = newRemoteViews.contracted;
            if (remoteViews5 != null) {
                provider2 = provider;
                remoteViews5.setLayoutInflaterFactory(provider2.provide(expandableNotificationRow, 1));
            } else {
                provider2 = provider;
            }
            RemoteViews remoteViews6 = newRemoteViews.expanded;
            if (remoteViews6 != null) {
                remoteViews6.setLayoutInflaterFactory(provider2.provide(expandableNotificationRow, 2));
            }
            RemoteViews remoteViews7 = newRemoteViews.headsUp;
            if (remoteViews7 != null) {
                remoteViews7.setLayoutInflaterFactory(provider2.provide(expandableNotificationRow, 4));
            }
            RemoteViews remoteViews8 = newRemoteViews.f107public;
            if (remoteViews8 != null) {
                remoteViews8.setLayoutInflaterFactory(provider2.provide(expandableNotificationRow, 8));
            }
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            if ((i & 16) != 0) {
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtils.logKey(notificationEntry), "inflating single line view model");
                singleLineViewModelInflateSingleLineViewModel = SingleLineViewInflater.inflateSingleLineViewModel(notificationEntry.mSbn.getNotification(), messagingStyle, builder, context, false, notificationEntry.mSbn.getNotification().extras.getCharSequence("android.summarization"));
            } else {
                singleLineViewModelInflateSingleLineViewModel = null;
            }
            if ((i & 128) != 0) {
                notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtils.logKey(notificationEntry), "inflating public single line view model");
                if (bindParams2.redactionType == 2) {
                    singleLineViewModelInflatePublicSingleLineViewModel = SingleLineViewInflater.inflateSingleLineViewModel(notificationEntry.mSbn.getNotification(), messagingStyle, builder, context, true, null);
                    builder2 = builder;
                } else {
                    builder2 = builder;
                    singleLineViewModelInflatePublicSingleLineViewModel = SingleLineViewInflater.inflatePublicSingleLineViewModel(context, notificationEntry.mRanking.isConversation());
                }
            } else {
                builder2 = builder;
                singleLineViewModelInflatePublicSingleLineViewModel = null;
            }
            return new InflationProgress(context4, rowImageInflaterStub, newRemoteViews, new NotificationContentModel(new HeadsUpStatusBarModel(builder2.getHeadsUpStatusBarText(false), builder2.getHeadsUpStatusBarText(true)), singleLineViewModelInflateSingleLineViewModel, singleLineViewModelInflatePublicSingleLineViewModel), null);
        }

        public static final RemoteViews access$createContentView(Companion companion, Notification.Builder builder, boolean z) {
            companion.getClass();
            if (z) {
                RemoteViews remoteViewsMakeLowPriorityContentView = builder.makeLowPriorityContentView(false);
                remoteViewsMakeLowPriorityContentView.getClass();
                return remoteViewsMakeLowPriorityContentView;
            }
            RemoteViews remoteViewsCreateContentView = builder.createContentView();
            remoteViewsCreateContentView.getClass();
            return remoteViewsCreateContentView;
        }

        public static final RemoteViews access$createExpandedView(Companion companion, Notification.Builder builder, boolean z) {
            companion.getClass();
            RemoteViews remoteViewsCreateBigContentView = builder.createBigContentView();
            if (remoteViewsCreateBigContentView != null) {
                return remoteViewsCreateBigContentView;
            }
            if (!z) {
                return null;
            }
            RemoteViews remoteViewsCreateContentView = builder.createContentView();
            Notification.Builder.makeHeaderExpanded(remoteViewsCreateContentView);
            return remoteViewsCreateContentView;
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
                Notification.MessagingStyle.Message messageFindLatestIncomingMessage = Notification.MessagingStyle.findLatestIncomingMessage(messagingStyle.getMessages());
                if (messageFindLatestIncomingMessage != null) {
                    messagingStyle2.addMessage(new Notification.MessagingStyle.Message(string, messageFindLatestIncomingMessage.getTimestamp(), messageFindLatestIncomingMessage.getSenderPerson()));
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

        public static boolean finishIfDone(InflationProgress inflationProgress, int i, NotifRemoteViewCache notifRemoteViewCache, HashMap map, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinderLogger notificationRowContentBinderLogger) {
            HybridNotificationView hybridNotificationView;
            SingleLineViewModel singleLineViewModel;
            HybridNotificationView hybridNotificationView2;
            SingleLineViewModel singleLineViewModel2;
            View view;
            Assert.isMainThread();
            if (!map.isEmpty()) {
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
                    notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$3.mo781invoke(inflatedSmartReplyViewHolder);
                } else {
                    notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$3.mo781invoke(null);
                }
            }
            if ((i & 2) != 0) {
                expandableNotificationRow.mExpandable = newRemoteViews.expanded != null;
                expandableNotificationRow.mPrivateLayout.updateExpandButtons(expandableNotificationRow.isExpandable());
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
                    notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$6.mo781invoke(inflatedSmartReplyViewHolder2);
                } else {
                    notificationRowContentBinderImpl$Companion$setContentViewsFromRemoteViews$6.mo781invoke(null);
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

        public static void handleInflationError(HashMap map, Exception exc, ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationRowContentBinderLogger notificationRowContentBinderLogger, String str) {
            Assert.isMainThread();
            notificationRowContentBinderLogger.logAsyncTaskException(expandableNotificationRow != null ? expandableNotificationRow.mLoggingKey : null, str, exc);
            map.values().forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$handleInflationError$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((CancellationSignal) obj).cancel();
                }
            });
            if (inflationCallback != null) {
                inflationCallback.handleInflationException(notificationEntry, exc);
            }
        }

        public final void applyRemoteView(Executor executor, boolean z, final boolean z2, final InflationProgress inflationProgress, final int i, final int i2, final NotifRemoteViewCache notifRemoteViewCache, final NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, final boolean z3, final RemoteViews.InteractionHandler interactionHandler, final NotificationRowContentBinder.InflationCallback inflationCallback, final ViewGroup viewGroup, final View view, final NotificationViewWrapper notificationViewWrapper, final HashMap<Integer, CancellationSignal> map, final ApplyCallback applyCallback, final NotificationRowContentBinderLogger notificationRowContentBinderLogger, final FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper) throws InflationException {
            CancellationSignal cancellationSignalReapplyAsync;
            final RemoteViews remoteView = applyCallback.getRemoteView();
            if (!z) {
                RemoteViews.OnViewAppliedListener onViewAppliedListener = new RemoteViews.OnViewAppliedListener(notificationEntry, map, inflationCallback, notificationRowContentBinderLogger, i2, z3, applyCallback, notificationViewWrapper, inflationProgress, z2, i, notifRemoteViewCache, faceWidgetNotificationControllerWrapper, remoteView, viewGroup, interactionHandler, view) { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$applyRemoteView$listener$1
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
                        View viewApply;
                        try {
                            if (this.$isNewView) {
                                viewApply = this.$newContentView.apply(this.$result.packageContext, this.$parentLayout, this.$remoteViewClickHandler);
                            } else {
                                this.$newContentView.reapply(this.$result.packageContext, this.$existingView, this.$remoteViewClickHandler);
                                viewApply = this.$existingView;
                                viewApply.getClass();
                            }
                            Log.wtf("NotifContentInflater", "Async Inflation failed but normal inflation finished normally.", exc);
                            viewApply.getClass();
                            onViewApplied(viewApply);
                        } catch (Exception unused) {
                            this.$runningInflations.remove(Integer.valueOf(this.$inflationId));
                            NotificationRowContentBinderImpl.Companion companion = NotificationRowContentBinderImpl.Companion;
                            HashMap map2 = this.$runningInflations;
                            ExpandableNotificationRow expandableNotificationRow2 = this.$row;
                            NotificationEntry notificationEntry2 = this.$entry;
                            NotificationRowContentBinder.InflationCallback inflationCallback2 = this.$callback;
                            NotificationRowContentBinderLogger notificationRowContentBinderLogger2 = this.$logger;
                            companion.getClass();
                            NotificationRowContentBinderImpl.Companion.handleInflationError(map2, exc, expandableNotificationRow2, notificationEntry2, inflationCallback2, notificationRowContentBinderLogger2, "applying view");
                        }
                    }

                    /* JADX WARN: Removed duplicated region for block: B:34:0x00fc  */
                    /* JADX WARN: Removed duplicated region for block: B:50:0x017c A[Catch: NameNotFoundException -> 0x0179, TryCatch #0 {NameNotFoundException -> 0x0179, blocks: (B:39:0x0126, B:41:0x015f, B:43:0x0165, B:45:0x0170, B:59:0x0195, B:61:0x01a8, B:63:0x01b6, B:65:0x01bc, B:68:0x01e2, B:70:0x0202, B:72:0x0208, B:74:0x020e, B:75:0x0212, B:77:0x0225, B:66:0x01d7, B:67:0x01de, B:78:0x0229, B:50:0x017c, B:52:0x0184, B:54:0x018c), top: B:95:0x0126 }] */
                    /* JADX WARN: Removed duplicated region for block: B:59:0x0195 A[Catch: NameNotFoundException -> 0x0179, TryCatch #0 {NameNotFoundException -> 0x0179, blocks: (B:39:0x0126, B:41:0x015f, B:43:0x0165, B:45:0x0170, B:59:0x0195, B:61:0x01a8, B:63:0x01b6, B:65:0x01bc, B:68:0x01e2, B:70:0x0202, B:72:0x0208, B:74:0x020e, B:75:0x0212, B:77:0x0225, B:66:0x01d7, B:67:0x01de, B:78:0x0229, B:50:0x017c, B:52:0x0184, B:54:0x018c), top: B:95:0x0126 }] */
                    /* JADX WARN: Removed duplicated region for block: B:61:0x01a8 A[Catch: NameNotFoundException -> 0x0179, TryCatch #0 {NameNotFoundException -> 0x0179, blocks: (B:39:0x0126, B:41:0x015f, B:43:0x0165, B:45:0x0170, B:59:0x0195, B:61:0x01a8, B:63:0x01b6, B:65:0x01bc, B:68:0x01e2, B:70:0x0202, B:72:0x0208, B:74:0x020e, B:75:0x0212, B:77:0x0225, B:66:0x01d7, B:67:0x01de, B:78:0x0229, B:50:0x017c, B:52:0x0184, B:54:0x018c), top: B:95:0x0126 }] */
                    /* JADX WARN: Removed duplicated region for block: B:78:0x0229 A[Catch: NameNotFoundException -> 0x0179, TRY_LEAVE, TryCatch #0 {NameNotFoundException -> 0x0179, blocks: (B:39:0x0126, B:41:0x015f, B:43:0x0165, B:45:0x0170, B:59:0x0195, B:61:0x01a8, B:63:0x01b6, B:65:0x01bc, B:68:0x01e2, B:70:0x0202, B:72:0x0208, B:74:0x020e, B:75:0x0212, B:77:0x0225, B:66:0x01d7, B:67:0x01de, B:78:0x0229, B:50:0x017c, B:52:0x0184, B:54:0x018c), top: B:95:0x0126 }] */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onViewApplied(View view2) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
                        NotificationRowIconView notificationRowIconView;
                        boolean z4;
                        String strIsValidView = NotificationRowContentBinderImpl.Companion.isValidView(view2, this.$entry, this.$row.getResources());
                        if (strIsValidView != null) {
                            NotificationRowContentBinderImpl.Companion.handleInflationError(this.$runningInflations, new InflationException(strIsValidView), this.$row, this.$entry, this.$callback, this.$logger, "applied invalid view");
                            this.$runningInflations.remove(Integer.valueOf(this.$inflationId));
                            return;
                        }
                        if (this.$entry.isOngoingActivity() && !this.$entry.mIsRon.booleanValue()) {
                            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                            String str = this.$entry.mKey;
                            ongoingActivityDataHelper.getClass();
                            OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(str);
                            if (pendingOngoingActivityData == null) {
                                pendingOngoingActivityData = OngoingActivityDataHelper.getOngoingActivityDataByKey(this.$entry.mKey);
                            }
                            if (pendingOngoingActivityData != null) {
                                OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
                                Context context = this.$row.getContext();
                                OngoingType ongoingType = OngoingType.ENR;
                                ongoingActivityLayoutUtil.getClass();
                                OngoingActivityLayoutUtil.updateNowbarSports(context, view2, pendingOngoingActivityData, ongoingType);
                                OngoingActivityLayoutUtil.updateOngoingChronometer(view2, pendingOngoingActivityData, false);
                                OngoingActivityLayoutUtil.updateOngoingHeader(view2, pendingOngoingActivityData);
                                OngoingActivityLayoutUtil.updateOngoingDescription(view2);
                            }
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
                        NotificationRowContentBinderImpl.Companion.finishIfDone(this.$result, this.$reInflateFlags, this.$remoteViewCache, this.$runningInflations, this.$callback, this.$entry, this.$row, this.$logger);
                        final NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
                        if (view2.findViewById(android.R.id.icon) instanceof CachingIconView) {
                            notificationRowIconView = (NotificationRowIconView) view2.findViewById(android.R.id.icon);
                        } else if (!this.$entry.isOngoingActivity() || this.$entry.isPromotedState()) {
                            notificationRowIconView = null;
                        } else if (Intrinsics.areEqual(view2.getTag(), "ongoingCollapsed")) {
                            notificationRowIconView = (NotificationRowIconView) view2.findViewWithTag("ongoingCollapsedPrimaryIcon");
                        } else if (Intrinsics.areEqual(view2.getTag(), "ongoingExpand")) {
                            notificationRowIconView = (NotificationRowIconView) view2.findViewWithTag("ongoingExpandPrimaryIcon");
                        }
                        if (notificationRowIconView != null) {
                            Pools.SimplePool simplePool = ImageTransformState.sInstancePool;
                            notificationRowIconView.setTag(R.id.image_icon_tag, this.$row.mEntry.mSbn.getNotification().getSmallIcon());
                            if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                                try {
                                    PackageManager packageManager = this.$row.getContext().getPackageManager();
                                    String packageName = this.$row.mEntry.mSbn.getPackageName();
                                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 4202624);
                                    List<LauncherActivityInfo> activityList = ((LauncherApps) this.$row.getContext().getSystemService(LauncherApps.class)).getActivityList(packageName, UserHandle.getUserHandleForUid(applicationInfo.uid));
                                    if ((applicationInfo.flags & 129) == 0 || !activityList.isEmpty()) {
                                        z4 = (Intrinsics.areEqual(packageName, "android") || Intrinsics.areEqual(packageName, "com.android.systemui") || applicationInfo.icon == 0) ? false : true;
                                        if (z4) {
                                            z4 = !this.$entry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon");
                                        }
                                        if (z4) {
                                            ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateSmallIcon(view2, this.$row, notificationRowIconView);
                                        } else {
                                            Drawable drawableSemGetBadgedIconForIconTray = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isColorThemeAppIconSettingsOn() ? !activityList.isEmpty() ? activityList.get(0).semGetBadgedIconForIconTray(this.$row.getContext().getResources().getDisplayMetrics().densityDpi) : packageManager.semGetApplicationIconForIconTray(applicationInfo, 48) : packageManager.semGetApplicationIconForIconTray(applicationInfo, 1);
                                            notificationRowIconView.setColorFilter((ColorFilter) null);
                                            notificationRowIconView.setBackground((Drawable) null);
                                            notificationRowIconView.setPadding(0, 0, 0, 0);
                                            int dimensionPixelSize = this.$row.getContext().getResources().getDimensionPixelSize(R.dimen.notification_application_icon_size_squircle);
                                            int maxDrawableWidth = notificationRowIconView.getMaxDrawableWidth() > 0 ? notificationRowIconView.getMaxDrawableWidth() : dimensionPixelSize;
                                            if (notificationRowIconView.getMaxDrawableHeight() > 0) {
                                                dimensionPixelSize = notificationRowIconView.getMaxDrawableHeight();
                                            }
                                            notificationRowIconView.setImageDrawable(notificationColorPicker.resizeDrawable(drawableSemGetBadgedIconForIconTray, maxDrawableWidth, dimensionPixelSize));
                                            notificationRowIconView.setTag(R.id.use_app_icon, Boolean.TRUE);
                                            if (view2 instanceof ConversationLayout) {
                                                notificationColorPicker.applyShadow(view2);
                                            }
                                        }
                                    } else {
                                        packageName.getClass();
                                        if (packageName.startsWith("com.samsung") || packageName.startsWith("com.sec")) {
                                        }
                                        if (z4) {
                                        }
                                        if (z4) {
                                        }
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateSmallIcon(view2, this.$row, notificationRowIconView);
                            }
                        }
                        ExpandableNotificationRow expandableNotificationRow2 = this.$row;
                        if (expandableNotificationRow2.mAnimationRunning) {
                            expandableNotificationRow2.setAnimationRunning(true);
                        } else {
                            expandableNotificationRow2.setAnimationRunning(false);
                        }
                        Optional optionalOfNullable = Optional.ofNullable(this.$row);
                        final NotificationRowContentBinderImpl$Companion$applyRemoteView$listener$1$$ExternalSyntheticLambda0 notificationRowContentBinderImpl$Companion$applyRemoteView$listener$1$$ExternalSyntheticLambda0 = new NotificationRowContentBinderImpl$Companion$applyRemoteView$listener$1$$ExternalSyntheticLambda0();
                        Optional optionalFilter = optionalOfNullable.filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImplKt$sam$java_util_function_Predicate$0
                            @Override // java.util.function.Predicate
                            public final /* synthetic */ boolean test(Object obj) {
                                return ((Boolean) notificationRowContentBinderImpl$Companion$applyRemoteView$listener$1$$ExternalSyntheticLambda0.mo781invoke(obj)).booleanValue();
                            }
                        });
                        final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$Companion$applyRemoteView$listener$1$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) obj;
                                notificationColorPicker.updateAllTextViewColors(expandableNotificationRow3, expandableNotificationRow3.mDimmed);
                                return Unit.INSTANCE;
                            }
                        };
                        optionalFilter.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImplKt$sam$java_util_function_Consumer$0
                            @Override // java.util.function.Consumer
                            public final /* synthetic */ void accept(Object obj) {
                                function1.mo781invoke(obj);
                            }
                        });
                        ExpandableNotificationRow expandableNotificationRow3 = this.$row;
                        notificationColorPicker.getClass();
                        if (NotificationColorPicker.isNeedToUpdated(expandableNotificationRow3)) {
                            ExpandableNotificationRow expandableNotificationRow4 = this.$row;
                            if (expandableNotificationRow4.mDimmed) {
                                notificationColorPicker.updateBig(view2, notificationColorPicker.getAppPrimaryColor(expandableNotificationRow4), notificationColorPicker.isGrayScaleIcon(this.$row), this.$existingWrapper, true, this.$row);
                            }
                        }
                        if (this.$row.mPinnedStatus.isPinned()) {
                            this.$row.applyHeadsUpBackground(NotificationColorPicker.isCustom(this.$row));
                        }
                    }

                    public final void onViewInflated(View view2) {
                        if (view2 instanceof ImageMessageConsumer) {
                            ((ImageMessageConsumer) view2).setImageResolver(this.$row.mImageResolver);
                        }
                    }
                };
                if (z3) {
                    cancellationSignalReapplyAsync = remoteView.applyAsync(inflationProgress.packageContext, viewGroup, executor, onViewAppliedListener, interactionHandler);
                    cancellationSignalReapplyAsync.getClass();
                } else {
                    cancellationSignalReapplyAsync = remoteView.reapplyAsync(inflationProgress.packageContext, view, executor, onViewAppliedListener, interactionHandler);
                    cancellationSignalReapplyAsync.getClass();
                }
                map.put(Integer.valueOf(i2), cancellationSignalReapplyAsync);
                return;
            }
            try {
                if (z3) {
                    View viewApply = remoteView.apply(inflationProgress.packageContext, viewGroup, interactionHandler);
                    String strIsValidView = isValidView(viewApply, notificationEntry, expandableNotificationRow.getResources());
                    if (strIsValidView != null) {
                        throw new InflationException(strIsValidView);
                    }
                    applyCallback.setResultView(viewApply);
                    return;
                }
                if (view == null) {
                    throw new IllegalArgumentException("Required value was null.");
                }
                if (notificationViewWrapper == null) {
                    throw new IllegalArgumentException("Required value was null.");
                }
                remoteView.reapply(inflationProgress.packageContext, view, interactionHandler);
                String strIsValidView2 = isValidView(view, notificationEntry, expandableNotificationRow.getResources());
                if (strIsValidView2 != null) {
                    throw new InflationException(strIsValidView2);
                }
                notificationViewWrapper.onReinflated();
            } catch (Exception e) {
                handleInflationError(map, e, expandableNotificationRow, notificationEntry, inflationCallback, notificationRowContentBinderLogger, "applying view synchronously");
                map.put(Integer.valueOf(i2), new CancellationSignal());
            }
        }

        public final boolean canReapplyRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
            return (remoteViews == null && remoteViews2 == null) || !(remoteViews == null || remoteViews2 == null || remoteViews2.getPackage() == null || remoteViews.getPackage() == null || !Intrinsics.areEqual(remoteViews.getPackage(), remoteViews2.getPackage()) || remoteViews.getLayoutId() != remoteViews2.getLayoutId() || remoteViews2.hasFlags(1));
        }

        public final String isValidView(View view, NotificationEntry notificationEntry, Resources resources) {
            if (notificationEntry.targetSdk < 31) {
                Notification notification2 = notificationEntry.mSbn.getNotification();
                if (notification2.contentView != null || notification2.bigContentView != null || notification2.headsUpContentView != null) {
                    boolean zIsEnabled = Trace.isEnabled();
                    if (zIsEnabled) {
                        TraceUtilsKt.beginSlice("NotificationContentInflater#satisfiesMinHeightRequirement");
                    }
                    try {
                        view.measure(View.MeasureSpec.makeMeasureSpec(resources.getDimensionPixelSize(R.dimen.notification_validation_reference_width), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
                        z = view.getMeasuredHeight() >= resources.getDimensionPixelSize(R.dimen.notification_validation_minimum_allowed_height);
                    } finally {
                        if (zIsEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    }
                }
            }
            if (!z) {
                return "inflated notification does not meet minimum height requirement";
            }
            NotificationCustomContentMemoryVerifier notificationCustomContentMemoryVerifier = NotificationCustomContentMemoryVerifier.INSTANCE;
            return null;
        }

        private Companion() {
        }
    }

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
        LogMessage logMessageObtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
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
                    NotificationInlineImageResolver notificationInlineImageResolver2 = notificationInlineImageResolver;
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
            asyncInflationTask.onPostExecute(Result.m3440boximpl(asyncInflationTask.m3081doInBackgroundIoAF18A()));
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final boolean cancelBind(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        boolean zAbortTask = notificationEntry.abortTask();
        if (zAbortTask) {
            String str = expandableNotificationRow.mLoggingKey;
            NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.logger;
            notificationRowContentBinderLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotificationRowContentBinderLogger$$ExternalSyntheticLambda0 notificationRowContentBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowContentBinderLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = notificationRowContentBinderLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = str;
            logBuffer.commit(logMessageObtain);
        }
        return zAbortTask;
    }

    public final InflationProgress inflateNotificationViews(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotificationRowContentBinder.BindParams bindParams, boolean z, int i, Notification.Builder builder, Context context, SmartReplyStateInflater smartReplyStateInflater, PromotedNotificationContentExtractor promotedNotificationContentExtractor) throws Resources.NotFoundException, InflationException {
        HybridNotificationView hybridNotificationViewInflatePrivateSingleLineView;
        Context context2 = expandableNotificationRow.getContext();
        context2.getClass();
        HeadsUpStyleProvider headsUpStyleProvider = this.headsUpStyleProvider;
        ConversationNotificationProcessor conversationNotificationProcessor = this.conversationProcessor;
        Companion companion = Companion;
        InflationProgress inflationProgressAccess$beginInflationAsync = Companion.access$beginInflationAsync(companion, i, notificationEntry, builder, bindParams, context2, context, expandableNotificationRow, this.notifLayoutInflaterFactoryProvider, headsUpStyleProvider, conversationNotificationProcessor, this.logger);
        Companion.access$inflateSmartReplyViews(companion, inflationProgressAccess$beginInflationAsync, i, notificationEntry, context2, context, expandableNotificationRow.mPrivateLayout.mCurrentSmartReplyState, smartReplyStateInflater, this.logger);
        NotificationContentModel notificationContentModel = inflationProgressAccess$beginInflationAsync.contentModel;
        SingleLineViewModel singleLineViewModel = notificationContentModel.singleLineViewModel;
        HybridNotificationView hybridNotificationViewInflatePublicSingleLineView = null;
        NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.logger;
        if (singleLineViewModel != null) {
            hybridNotificationViewInflatePrivateSingleLineView = SingleLineViewInflater.inflatePrivateSingleLineView(singleLineViewModel.conversationData != null, i, notificationEntry, context2, notificationRowContentBinderLogger);
        } else {
            hybridNotificationViewInflatePrivateSingleLineView = null;
        }
        inflationProgressAccess$beginInflationAsync.inflatedSingleLineView = hybridNotificationViewInflatePrivateSingleLineView;
        SingleLineViewModel singleLineViewModel2 = notificationContentModel.publicSingleLineViewModel;
        if (singleLineViewModel2 != null) {
            hybridNotificationViewInflatePublicSingleLineView = SingleLineViewInflater.inflatePublicSingleLineView(singleLineViewModel2.conversationData != null, i, notificationEntry, context2, notificationRowContentBinderLogger);
        }
        inflationProgressAccess$beginInflationAsync.inflatedPublicSingleLineView = hybridNotificationViewInflatePublicSingleLineView;
        Companion.access$apply(companion, this.inflationExecutor, z, bindParams.isMinimized, inflationProgressAccess$beginInflationAsync, i, this.remoteViewCache, notificationEntry, expandableNotificationRow, this.remoteInputManager.mInteractionHandler, null, this.logger, this.faceWidgetNotificationControllerWrapper);
        return inflationProgressAccess$beginInflationAsync;
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
        LogMessage logMessageObtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
        int i2 = 1;
        while (i != 0) {
            if ((i & i2) != 0) {
                if (i2 == 1) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(0, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            expandableNotificationRow.mPrivateLayout.setContractedChild(null);
                            ((NotifRemoteViewCacheImpl) this.remoteViewCache).removeCachedView(notificationEntry, 1);
                        }
                    });
                } else if (i2 == 2) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(1, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$2
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            expandableNotificationRow.mPrivateLayout.setExpandedChild(null);
                            ((NotifRemoteViewCacheImpl) this.remoteViewCache).removeCachedView(notificationEntry, 2);
                        }
                    });
                } else if (i2 == 4) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(2, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$3
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            expandableNotificationRow.mPrivateLayout.setHeadsUpChild(null);
                            ((NotifRemoteViewCacheImpl) this.remoteViewCache).removeCachedView(notificationEntry, 4);
                            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
                            notificationContentView.mHeadsUpInflatedSmartReplies = null;
                            notificationContentView.mHeadsUpSmartReplyView = null;
                        }
                    });
                } else if (i2 == 8) {
                    expandableNotificationRow.mPublicLayout.performWhenContentInactive(0, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$4
                        @Override // java.lang.Runnable
                        public final void run() {
                            expandableNotificationRow.mPublicLayout.setContractedChild(null);
                            ((NotifRemoteViewCacheImpl) this.remoteViewCache).removeCachedView(notificationEntry, 8);
                        }
                    });
                } else if (i2 == 16) {
                    expandableNotificationRow.mPrivateLayout.performWhenContentInactive(3, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$5
                        @Override // java.lang.Runnable
                        public final void run() {
                            expandableNotificationRow.mPrivateLayout.setSingleLineView(null);
                        }
                    });
                } else if (i2 == 128) {
                    expandableNotificationRow.mPublicLayout.performWhenContentInactive(3, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.NotificationRowContentBinderImpl$freeNotificationView$6
                        @Override // java.lang.Runnable
                        public final void run() {
                            expandableNotificationRow.mPublicLayout.setSingleLineView(null);
                        }
                    });
                }
            }
            i &= ~i2;
            i2 <<= 1;
        }
    }
}
