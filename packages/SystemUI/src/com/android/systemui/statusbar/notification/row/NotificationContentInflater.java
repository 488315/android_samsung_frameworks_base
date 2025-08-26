package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.CancellationSignal;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Log;
import android.util.Pools;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.app.tracing.TraceUtils;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.internal.widget.NotificationRowIconView;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NmSummarizationUiFlag;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.promoted.PromotedNotificationContentExtractor;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import com.android.systemui.statusbar.notification.row.NotificationContentInflater;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.shared.LockscreenOtpRedaction;
import com.android.systemui.statusbar.notification.row.shared.NotificationRowContentBinderRefactor;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.SingleLineViewBinder;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil;
import com.android.systemui.statusbar.policy.InflatedSmartReplyState;
import com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function0;
import noticolorpicker.NotificationColorPicker;

/* loaded from: classes3.dex */
public class NotificationContentInflater implements NotificationRowContentBinder {

    abstract class ApplyCallback {
        public abstract RemoteViews getRemoteView();

        public abstract void setResultView(View view);
    }

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

    public static void applyRemoteView(Executor executor, boolean z, boolean z2, InflationProgress inflationProgress, int i, int i2, NotifRemoteViewCache notifRemoteViewCache, NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, boolean z3, RemoteViews.InteractionHandler interactionHandler, NotificationRowContentBinder.InflationCallback inflationCallback, ViewGroup viewGroup, View view, NotificationViewWrapper notificationViewWrapper, HashMap<Integer, CancellationSignal> map, ApplyCallback applyCallback, NotificationRowContentBinderLogger notificationRowContentBinderLogger) throws InflationException {
        RemoteViews remoteView = applyCallback.getRemoteView();
        if (!z) {
            RemoteViews.OnViewAppliedListener onViewAppliedListener = new RemoteViews.OnViewAppliedListener(notificationEntry, map, inflationCallback, notificationRowContentBinderLogger, i2, z3, applyCallback, notificationViewWrapper, inflationProgress, z2, i, notifRemoteViewCache, view, remoteView, viewGroup, interactionHandler) { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater.8
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
                        View viewApply = this.val$existingView;
                        if (this.val$isNewView) {
                            viewApply = this.val$newContentView.apply(this.val$result.packageContext, this.val$parentLayout, this.val$remoteViewClickHandler);
                        } else {
                            this.val$newContentView.reapply(this.val$result.packageContext, viewApply, this.val$remoteViewClickHandler);
                        }
                        Log.wtf("NotifContentInflater", "Async Inflation failed but normal inflation finished normally.", exc);
                        onViewApplied(viewApply);
                    } catch (Exception unused) {
                        this.val$runningInflations.remove(Integer.valueOf(this.val$inflationId));
                        NotificationContentInflater.handleInflationError(this.val$runningInflations, exc, this.val$row, this.val$entry, this.val$callback, this.val$logger, "applying view");
                    }
                }

                public final void onViewApplied(View view2) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
                    NotificationRowIconView notificationRowIconViewFindViewById;
                    String strIsValidView = NotificationContentInflater.isValidView(view2, this.val$entry, this.val$row.getResources());
                    if (strIsValidView != null) {
                        NotificationContentInflater.handleInflationError(this.val$runningInflations, new InflationException(strIsValidView), this.val$row, this.val$entry, this.val$callback, this.val$logger, "applied invalid view");
                        this.val$runningInflations.remove(Integer.valueOf(this.val$inflationId));
                        return;
                    }
                    if (this.val$entry.isOngoingActivity()) {
                        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
                        String str = this.val$entry.mKey;
                        ongoingActivityDataHelper.getClass();
                        OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(str);
                        if (pendingOngoingActivityData == null) {
                            pendingOngoingActivityData = OngoingActivityDataHelper.getOngoingActivityDataByKey(this.val$entry.mKey);
                        }
                        if (pendingOngoingActivityData != null) {
                            OngoingActivityLayoutUtil.INSTANCE.getClass();
                            OngoingActivityLayoutUtil.updateOngoingChronometer(view2, pendingOngoingActivityData, false);
                            OngoingActivityLayoutUtil.updateOngoingHeader(view2, pendingOngoingActivityData);
                            OngoingActivityLayoutUtil.updateOngoingDescription(view2);
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
                    InflationProgress inflationProgress2 = this.val$result;
                    int i3 = this.val$reInflateFlags;
                    NotifRemoteViewCache notifRemoteViewCache2 = this.val$remoteViewCache;
                    HashMap map2 = this.val$runningInflations;
                    NotificationEntry notificationEntry2 = this.val$entry;
                    ExpandableNotificationRow expandableNotificationRow2 = this.val$row;
                    NotificationRowContentBinderLogger notificationRowContentBinderLogger2 = this.val$logger;
                    Assert.isMainThread();
                    if (!map2.isEmpty()) {
                        final NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
                        if ((view2.findViewById(R.id.icon) instanceof CachingIconView) && (notificationRowIconViewFindViewById = view2.findViewById(R.id.icon)) != null) {
                            Pools.SimplePool simplePool = ImageTransformState.sInstancePool;
                            notificationRowIconViewFindViewById.setTag(com.android.systemui.R.id.image_icon_tag, this.val$row.mEntry.mSbn.getNotification().getSmallIcon());
                            if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowNotificationAppIconEnabled()) {
                                try {
                                    PackageManager packageManager = this.val$row.getContext().getPackageManager();
                                    String packageName = this.val$row.mEntry.mSbn.getPackageName();
                                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 4202624);
                                    List<LauncherActivityInfo> activityList = ((LauncherApps) this.val$row.getContext().getSystemService(LauncherApps.class)).getActivityList(packageName, UserHandle.getUserHandleForUid(applicationInfo.uid));
                                    boolean z4 = (((applicationInfo.flags & 129) != 0 && activityList.isEmpty() && !packageName.startsWith("com.samsung") && !packageName.startsWith("com.sec")) || packageName.equals("android") || packageName.equals("com.android.systemui") || applicationInfo.icon == 0) ? false : true;
                                    if (z4) {
                                        z4 = !this.val$entry.mSbn.getNotification().extras.getBoolean("android.showSmallIcon");
                                    }
                                    if (z4) {
                                        Drawable drawableSemGetApplicationIconForIconTray = (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isColorThemeAppIconSettingsOn() || activityList.isEmpty()) ? packageManager.semGetApplicationIconForIconTray(applicationInfo, 1) : activityList.get(0).semGetBadgedIconForIconTray(this.val$row.getContext().getResources().getDisplayMetrics().densityDpi);
                                        notificationRowIconViewFindViewById.setColorFilter((ColorFilter) null);
                                        notificationRowIconViewFindViewById.setBackground((Drawable) null);
                                        notificationRowIconViewFindViewById.setPadding(0, 0, 0, 0);
                                        int dimensionPixelSize = this.val$row.getContext().getResources().getDimensionPixelSize(com.android.systemui.R.dimen.notification_application_icon_size_squircle);
                                        int maxDrawableWidth = notificationRowIconViewFindViewById.getMaxDrawableWidth() > 0 ? notificationRowIconViewFindViewById.getMaxDrawableWidth() : dimensionPixelSize;
                                        if (notificationRowIconViewFindViewById.getMaxDrawableHeight() > 0) {
                                            dimensionPixelSize = notificationRowIconViewFindViewById.getMaxDrawableHeight();
                                        }
                                        notificationRowIconViewFindViewById.setImageDrawable(notificationColorPicker.resizeDrawable(drawableSemGetApplicationIconForIconTray, maxDrawableWidth, dimensionPixelSize));
                                        notificationRowIconViewFindViewById.setTag(com.android.systemui.R.id.use_app_icon, Boolean.TRUE);
                                        if (view2 instanceof ConversationLayout) {
                                            notificationColorPicker.applyShadow(view2);
                                        }
                                    } else {
                                        ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateSmallIcon(view2, this.val$row, notificationRowIconViewFindViewById);
                                    }
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateSmallIcon(view2, this.val$row, notificationRowIconViewFindViewById);
                            }
                        }
                        ExpandableNotificationRow expandableNotificationRow3 = this.val$row;
                        if (expandableNotificationRow3.mAnimationRunning) {
                            expandableNotificationRow3.setAnimationRunning(true);
                        } else {
                            expandableNotificationRow3.setAnimationRunning(false);
                        }
                        Optional.ofNullable(this.val$row).filter(new NotificationContentInflater$8$$ExternalSyntheticLambda0()).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$8$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) obj;
                                notificationColorPicker.updateAllTextViewColors(expandableNotificationRow4, expandableNotificationRow4.mDimmed);
                            }
                        });
                        ExpandableNotificationRow expandableNotificationRow4 = this.val$row;
                        notificationColorPicker.getClass();
                        if (NotificationColorPicker.isNeedToUpdated(expandableNotificationRow4)) {
                            ExpandableNotificationRow expandableNotificationRow5 = this.val$row;
                            if (expandableNotificationRow5.mDimmed) {
                                notificationColorPicker.updateBig(view2, notificationColorPicker.getAppPrimaryColor(expandableNotificationRow5), notificationColorPicker.isGrayScaleIcon(this.val$row), this.val$existingWrapper, true, this.val$row);
                            }
                        }
                        if (this.val$row.mPinnedStatus.isPinned()) {
                            this.val$row.applyHeadsUpBackground(NotificationColorPicker.isCustom(this.val$row));
                            return;
                        }
                        return;
                    }
                    NotificationContentView notificationContentView = expandableNotificationRow2.mPrivateLayout;
                    NotificationContentView notificationContentView2 = expandableNotificationRow2.mPublicLayout;
                    notificationRowContentBinderLogger2.logAsyncTaskProgress(NotificationUtils.logKey(notificationEntry2), "finishing");
                    inflationProgress2.mRowImageInflater.getClass();
                    expandableNotificationRow2.mImageModelIndex = null;
                    PromotedNotificationContentModel.Companion.getClass();
                    if ((i3 & 1) != 0) {
                        inflationProgress2.getClass();
                        NotifRemoteViewCacheImpl notifRemoteViewCacheImpl = (NotifRemoteViewCacheImpl) notifRemoteViewCache2;
                        if (notifRemoteViewCacheImpl.getCachedView(notificationEntry2, 1) != null) {
                            notifRemoteViewCacheImpl.putCachedView(notificationEntry2, 1, inflationProgress2.newContentView);
                        }
                    }
                    expandableNotificationRow2.mIsCustomNotification = NotificationContentInflater.isCustomNotification(notificationEntry2.mSbn.getNotification(), notificationContentView.mContractedChild, notificationEntry2.mSbn.getNotification().contentView);
                    if ((i3 & 2) != 0) {
                        inflationProgress2.getClass();
                        if (inflationProgress2.newExpandedView == null) {
                            notificationContentView.setExpandedChild(null);
                            ((NotifRemoteViewCacheImpl) notifRemoteViewCache2).removeCachedView(notificationEntry2, 2);
                        } else {
                            NotifRemoteViewCacheImpl notifRemoteViewCacheImpl2 = (NotifRemoteViewCacheImpl) notifRemoteViewCache2;
                            if (notifRemoteViewCacheImpl2.getCachedView(notificationEntry2, 2) != null) {
                                notifRemoteViewCacheImpl2.putCachedView(notificationEntry2, 2, inflationProgress2.newExpandedView);
                            }
                        }
                        RemoteViews remoteViews = inflationProgress2.newExpandedView;
                        if (remoteViews != null) {
                            InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder = inflationProgress2.expandedInflatedSmartReplies;
                            notificationContentView.mExpandedInflatedSmartReplies = inflatedSmartReplyViewHolder;
                            if (inflatedSmartReplyViewHolder == null) {
                                notificationContentView.mExpandedSmartReplyView = null;
                            }
                        } else {
                            notificationContentView.mExpandedInflatedSmartReplies = null;
                            notificationContentView.mExpandedSmartReplyView = null;
                        }
                        expandableNotificationRow2.mExpandable = remoteViews != null;
                        expandableNotificationRow2.mPrivateLayout.updateExpandButtonsDuringLayout(expandableNotificationRow2.isExpandable(), false);
                        expandableNotificationRow2.mIsCustomBigNotification = NotificationContentInflater.isCustomNotification(notificationEntry2.mSbn.getNotification(), notificationContentView.mExpandedChild, notificationEntry2.mSbn.getNotification().bigContentView);
                    }
                    if ((i3 & 256) != 0) {
                        inflationProgress2.getClass();
                    }
                    if ((i3 & 4) != 0) {
                        inflationProgress2.getClass();
                        if (inflationProgress2.newHeadsUpView == null) {
                            notificationContentView.setHeadsUpChild(null);
                            ((NotifRemoteViewCacheImpl) notifRemoteViewCache2).removeCachedView(notificationEntry2, 4);
                        } else {
                            NotifRemoteViewCacheImpl notifRemoteViewCacheImpl3 = (NotifRemoteViewCacheImpl) notifRemoteViewCache2;
                            if (notifRemoteViewCacheImpl3.getCachedView(notificationEntry2, 4) != null) {
                                notifRemoteViewCacheImpl3.putCachedView(notificationEntry2, 4, inflationProgress2.newHeadsUpView);
                            }
                        }
                        if (inflationProgress2.newHeadsUpView != null) {
                            InflatedSmartReplyViewHolder inflatedSmartReplyViewHolder2 = inflationProgress2.headsUpInflatedSmartReplies;
                            notificationContentView.mHeadsUpInflatedSmartReplies = inflatedSmartReplyViewHolder2;
                            if (inflatedSmartReplyViewHolder2 == null) {
                                notificationContentView.mHeadsUpSmartReplyView = null;
                            }
                        } else {
                            notificationContentView.mHeadsUpInflatedSmartReplies = null;
                            notificationContentView.mHeadsUpSmartReplyView = null;
                        }
                        expandableNotificationRow2.mIsCustomHeadsUpNotification = NotificationContentInflater.isCustomNotification(notificationEntry2.mSbn.getNotification(), notificationContentView.mHeadsUpChild, notificationEntry2.mSbn.getNotification().headsUpContentView);
                    }
                    int i4 = AsyncHybridViewInflation.$r8$clinit;
                    if ((i3 & 16) != 0) {
                        HybridNotificationView hybridNotificationView = inflationProgress2.mInflatedSingleLineView;
                        SingleLineViewModel singleLineViewModel = inflationProgress2.mInflatedSingleLineViewModel;
                        if (hybridNotificationView != null && singleLineViewModel != null) {
                            SingleLineViewBinder.bind(singleLineViewModel, hybridNotificationView);
                            notificationContentView.setSingleLineView(inflationProgress2.mInflatedSingleLineView);
                        }
                    }
                    int i5 = LockscreenOtpRedaction.$r8$clinit;
                    if ((i3 & 128) != 0) {
                        HybridNotificationView hybridNotificationView2 = inflationProgress2.mPublicInflatedSingleLineView;
                        SingleLineViewModel singleLineViewModel2 = inflationProgress2.mPublicInflatedSingleLineViewModel;
                        if (hybridNotificationView2 != null && singleLineViewModel2 != null) {
                            SingleLineViewBinder.bind(singleLineViewModel2, hybridNotificationView2);
                            notificationContentView2.setSingleLineView(inflationProgress2.mPublicInflatedSingleLineView);
                        }
                    }
                    notificationContentView.mCurrentSmartReplyState = inflationProgress2.inflatedSmartReplyState;
                    if ((i3 & 8) != 0) {
                        inflationProgress2.getClass();
                        NotifRemoteViewCacheImpl notifRemoteViewCacheImpl4 = (NotifRemoteViewCacheImpl) notifRemoteViewCache2;
                        if (notifRemoteViewCacheImpl4.getCachedView(notificationEntry2, 8) != null) {
                            notifRemoteViewCacheImpl4.putCachedView(notificationEntry2, 8, inflationProgress2.newPublicView);
                        }
                        if (notificationEntry2.mSbn.getNotification().publicVersion != null) {
                            expandableNotificationRow2.mIsCustomPublicNotification = NotificationContentInflater.isCustomNotification(notificationEntry2.mSbn.getNotification().publicVersion, notificationContentView2.mContractedChild, notificationEntry2.mSbn.getNotification().publicVersion.contentView);
                        }
                    }
                    int i6 = AsyncGroupHeaderViewInflation.$r8$clinit;
                    int i7 = NotificationRowContentBinderRefactor.$r8$clinit;
                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                    throw new IllegalStateException("Legacy code path not supported when com.android.systemui.notification_row_content_binder_refactor is enabled.");
                }

                public final void onViewInflated(View view2) {
                    if (view2 instanceof ImageMessageConsumer) {
                        ((ImageMessageConsumer) view2).setImageResolver(this.val$row.mImageResolver);
                    }
                }
            };
            map.put(Integer.valueOf(i2), z3 ? remoteView.applyAsync(inflationProgress.packageContext, viewGroup, executor, onViewAppliedListener, interactionHandler) : remoteView.reapplyAsync(inflationProgress.packageContext, view, executor, onViewAppliedListener, interactionHandler));
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

    public static boolean canReapplyRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2) {
        return (remoteViews == null && remoteViews2 == null) || !(remoteViews == null || remoteViews2 == null || remoteViews2.getPackage() == null || remoteViews.getPackage() == null || !remoteViews.getPackage().equals(remoteViews2.getPackage()) || remoteViews.getLayoutId() != remoteViews2.getLayoutId() || remoteViews2.hasFlags(1));
    }

    public static void handleInflationError(HashMap map, Exception exc, ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationRowContentBinderLogger notificationRowContentBinderLogger, String str) {
        Assert.isMainThread();
        notificationRowContentBinderLogger.logAsyncTaskException(expandableNotificationRow.mLoggingKey, str, exc);
        map.values().forEach(new NotificationContentInflater$$ExternalSyntheticLambda0());
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
        return Notification.DecoratedCustomViewStyle.class.equals(notificationStyle) || Notification.DecoratedMediaCustomViewStyle.class.equals(notificationStyle) || z || !(view.getId() == 16909885 || view.getId() == 16909441);
    }

    public static String isValidView(View view, NotificationEntry notificationEntry, Resources resources) {
        if (notificationEntry.targetSdk < 31) {
            Notification notification2 = notificationEntry.mSbn.getNotification();
            if (notification2.contentView != null || notification2.bigContentView != null || notification2.headsUpContentView != null) {
                boolean zIsEnabled = Trace.isEnabled();
                if (zIsEnabled) {
                    TraceUtilsKt.beginSlice("NotificationContentInflater#satisfiesMinHeightRequirement");
                }
                try {
                    view.measure(View.MeasureSpec.makeMeasureSpec(resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_validation_reference_width), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
                    z = view.getMeasuredHeight() >= resources.getDimensionPixelSize(com.android.systemui.R.dimen.notification_validation_minimum_allowed_height);
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

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final void bindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i, NotificationRowContentBinder.BindParams bindParams, boolean z, RowContentBindStage.AnonymousClass1 anonymousClass1) {
        expandableNotificationRow.getClass();
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder
    public final boolean cancelBind(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        boolean zAbortTask = notificationEntry.abortTask();
        if (!zAbortTask) {
            return zAbortTask;
        }
        String str = expandableNotificationRow.mLoggingKey;
        throw null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.statusbar.notification.row.NotificationRowContentBinderLogger, java.lang.Throwable] */
    public InflationProgress inflateNotificationViews(NotificationEntry notificationEntry, final ExpandableNotificationRow expandableNotificationRow, final NotificationRowContentBinder.BindParams bindParams, boolean z, final int i, final Notification.Builder builder, final Context context, final Context context2, SmartReplyStateInflater smartReplyStateInflater) throws Resources.NotFoundException {
        NotificationRowContentBinderLogger notificationRowContentBinderLogger;
        NotificationEntry notificationEntry2;
        NotificationRowContentBinderLogger notificationRowContentBinderLogger2 = null;
        final boolean zIsAllowPrivateNotificationsWhenUnsecure = NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE ? ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAllowPrivateNotificationsWhenUnsecure(ActivityManager.getCurrentUser()) : false;
        final NotificationRowContentBinderLogger notificationRowContentBinderLogger3 = null;
        final HeadsUpStyleProvider headsUpStyleProvider = null;
        final NotifLayoutInflaterFactory.Provider provider = null;
        InflationProgress inflationProgress = (InflationProgress) TraceUtils.trace("NotificationContentInflater.createRemoteViews", new Function0() { // from class: com.android.systemui.statusbar.notification.row.NotificationContentInflater$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2;
                RemoteViews remoteViewsCreateBigContentView;
                RemoteViews remoteViewsCreateContentView;
                RemoteViews remoteViewsMakeLowPriorityContentView;
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
                        remoteViewsMakeLowPriorityContentView = bindParams2.isMinimized ? builder2.makeLowPriorityContentView(false) : builder2.createContentView();
                    } else if (bindParams2.isMinimized) {
                        builder2.setContentTitle(pendingOngoingActivityData.mPrimaryInfo).setContentText(pendingOngoingActivityData.mSecondaryInfo);
                        remoteViewsMakeLowPriorityContentView = builder2.makeLowPriorityContentView(false);
                    } else {
                        boolean zBooleanValue = expandableNotificationRow2.mEntry.mIsRon.booleanValue();
                        boolean z2 = bindParams2.isMinimized;
                        if (zBooleanValue) {
                            remoteViewsMakeLowPriorityContentView = z2 ? builder2.makeLowPriorityContentView(false) : builder2.createContentView();
                            if (remoteViewsMakeLowPriorityContentView != null) {
                                remoteViewsMakeLowPriorityContentView.setInt(16909885, "setBackgroundResource", 0);
                            }
                        } else {
                            remoteViewsMakeLowPriorityContentView = pendingOngoingActivityData.mOngoingCollapsedView;
                            if (remoteViewsMakeLowPriorityContentView == null) {
                                remoteViewsMakeLowPriorityContentView = z2 ? builder2.makeLowPriorityContentView(false) : builder2.createContentView();
                            }
                        }
                    }
                    inflationProgress2.newContentView = remoteViewsMakeLowPriorityContentView;
                } else {
                    i2 = 4;
                }
                RemoteViews remoteViewsCreateContentView2 = null;
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
                        remoteViewsCreateBigContentView = builder2.createBigContentView();
                        if (remoteViewsCreateBigContentView == null) {
                            if (z3) {
                                remoteViewsCreateContentView = builder2.createContentView();
                                Notification.Builder.makeHeaderExpanded(remoteViewsCreateContentView);
                                remoteViewsCreateBigContentView = remoteViewsCreateContentView;
                            } else {
                                remoteViewsCreateBigContentView = null;
                            }
                        }
                        inflationProgress2.newExpandedView = remoteViewsCreateBigContentView;
                    } else {
                        if (expandableNotificationRow2.mEntry.mIsRon.booleanValue()) {
                            boolean z4 = bindParams2.isMinimized;
                            remoteViewsCreateBigContentView = builder2.createBigContentView();
                            if (remoteViewsCreateBigContentView == null) {
                                if (z4) {
                                    remoteViewsCreateBigContentView = builder2.createContentView();
                                    Notification.Builder.makeHeaderExpanded(remoteViewsCreateBigContentView);
                                } else {
                                    remoteViewsCreateBigContentView = null;
                                }
                            }
                            if (remoteViewsCreateBigContentView != null) {
                                remoteViewsCreateBigContentView.setInt(16909885, "setBackgroundResource", 0);
                            }
                        } else {
                            remoteViewsCreateBigContentView = pendingOngoingActivityData2.mOngoingENRExpandView;
                            if (remoteViewsCreateBigContentView == null) {
                                remoteViewsCreateContentView = bindParams2.isMinimized ? builder2.makeLowPriorityContentView(false) : builder2.createContentView();
                                remoteViewsCreateBigContentView = remoteViewsCreateContentView;
                            }
                        }
                        inflationProgress2.newExpandedView = remoteViewsCreateBigContentView;
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
                            RemoteViews remoteViewsCreateBigContentView2 = builder2.createBigContentView();
                            if (remoteViewsCreateBigContentView2 != null) {
                                remoteViewsCreateContentView2 = remoteViewsCreateBigContentView2;
                            } else if (z5) {
                                remoteViewsCreateContentView2 = builder2.createContentView();
                                Notification.Builder.makeHeaderExpanded(remoteViewsCreateContentView2);
                            }
                            if (remoteViewsCreateContentView2 != null) {
                                remoteViewsCreateContentView2.setInt(16909885, "setBackgroundResource", 0);
                            }
                        } else {
                            remoteViewsCreateContentView2 = pendingOngoingActivityData3.mOngoingOAExpandView;
                        }
                    }
                    inflationProgress2.newPromotedOngoingView = remoteViewsCreateContentView2;
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
                        CharSequence string = context3.getString(com.android.systemui.R.string.redacted_otp_notification_single_line_text);
                        builder3.setWhen(notification2.getWhen());
                        if (style instanceof Notification.MessagingStyle) {
                            Notification.MessagingStyle messagingStyle = (Notification.MessagingStyle) style;
                            Notification.MessagingStyle messagingStyle2 = new Notification.MessagingStyle(messagingStyle.getUser());
                            messagingStyle2.setConversationTitle(messagingStyle.getConversationTitle());
                            messagingStyle2.setGroupConversation(false);
                            messagingStyle2.setConversationType(messagingStyle.getConversationType());
                            messagingStyle2.setShortcutIcon(messagingStyle.getShortcutIcon());
                            messagingStyle2.setBuilder(builder3);
                            Notification.MessagingStyle.Message messageFindLatestIncomingMessage = Notification.MessagingStyle.findLatestIncomingMessage(messagingStyle.getMessages());
                            if (messageFindLatestIncomingMessage != null) {
                                messagingStyle2.addMessage(new Notification.MessagingStyle.Message(string, messageFindLatestIncomingMessage.getTimestamp(), messageFindLatestIncomingMessage.getSenderPerson()));
                            }
                            builder3.setStyle(messagingStyle2);
                        } else {
                            builder3.setContentText(string);
                        }
                        builder3.setLargeIcon(notification2.getLargeIcon());
                        builder3.setSmallIcon(notification2.getSmallIcon());
                        inflationProgress2.newPublicView = builder3.createContentView();
                    } else {
                        inflationProgress2.newPublicView = builder2.makePublicContentView(bindParams2.isMinimized, zIsAllowPrivateNotificationsWhenUnsecure);
                    }
                }
                int i7 = AsyncGroupHeaderViewInflation.$r8$clinit;
                RemoteViews remoteViews = inflationProgress2.newContentView;
                NotifLayoutInflaterFactory.Provider provider2 = provider;
                NotifLayoutInflaterFactory notifLayoutInflaterFactoryProvide = provider2.provide(expandableNotificationRow2, 1);
                if (remoteViews != null) {
                    remoteViews.setLayoutInflaterFactory(notifLayoutInflaterFactoryProvide);
                }
                RemoteViews remoteViews2 = inflationProgress2.newExpandedView;
                NotifLayoutInflaterFactory notifLayoutInflaterFactoryProvide2 = provider2.provide(expandableNotificationRow2, 2);
                if (remoteViews2 != null) {
                    remoteViews2.setLayoutInflaterFactory(notifLayoutInflaterFactoryProvide2);
                }
                RemoteViews remoteViews3 = inflationProgress2.newHeadsUpView;
                NotifLayoutInflaterFactory notifLayoutInflaterFactoryProvide3 = provider2.provide(expandableNotificationRow2, i2);
                if (remoteViews3 != null) {
                    remoteViews3.setLayoutInflaterFactory(notifLayoutInflaterFactoryProvide3);
                }
                RemoteViews remoteViews4 = inflationProgress2.newPublicView;
                NotifLayoutInflaterFactory notifLayoutInflaterFactoryProvide4 = provider2.provide(expandableNotificationRow2, 8);
                if (remoteViews4 != null) {
                    remoteViews4.setLayoutInflaterFactory(notifLayoutInflaterFactoryProvide4);
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
        String strLogKey = NotificationUtils.logKey(notificationEntry);
        if (z2 || z3 || z4) {
            notificationRowContentBinderLogger2.logAsyncTaskProgress(strLogKey, "inflating contracted smart reply state");
            inflationProgress.inflatedSmartReplyState = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyState(notificationEntry);
        }
        if (z3) {
            notificationRowContentBinderLogger2.logAsyncTaskProgress(strLogKey, "inflating expanded smart reply state");
            notificationRowContentBinderLogger = null;
            inflationProgress.expandedInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context3, context2, notificationEntry, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        } else {
            notificationRowContentBinderLogger = null;
        }
        if (z4) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(strLogKey, "inflating heads up smart reply state");
            notificationEntry2 = notificationEntry;
            inflationProgress.headsUpInflatedSmartReplies = ((SmartReplyStateInflaterImpl) smartReplyStateInflater).inflateSmartReplyViewHolder(context3, context2, notificationEntry2, inflatedSmartReplyState, inflationProgress.inflatedSmartReplyState);
        } else {
            notificationEntry2 = notificationEntry;
        }
        boolean zIsConversation = notificationEntry2.mRanking.isConversation();
        int i2 = NmSummarizationUiFlag.$r8$clinit;
        if (zIsConversation) {
            ?? r2 = notificationRowContentBinderLogger;
            int i3 = AsyncHybridViewInflation.$r8$clinit;
            throw r2;
        }
        int i4 = AsyncHybridViewInflation.$r8$clinit;
        ?? r22 = notificationRowContentBinderLogger;
        SingleLineViewModel singleLineViewModelInflateSingleLineViewModel = SingleLineViewInflater.inflateSingleLineViewModel(notificationEntry2.mSbn.getNotification(), null, builder, expandableNotificationRow.getContext(), false, notificationEntry2.mRanking.getSummarization());
        boolean z5 = singleLineViewModelInflateSingleLineViewModel.conversationData != null;
        inflationProgress.mInflatedSingleLineViewModel = singleLineViewModelInflateSingleLineViewModel;
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
