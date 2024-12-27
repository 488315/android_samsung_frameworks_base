package com.android.systemui.statusbar.notification.init;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$bind$3;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.init.NotifPipelineInitializer;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager$attach$1;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager$viewRenderer$1;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.logging.NotificationLogger$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.Assert;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.Lazy;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function0;

public final class NotificationsControllerImpl implements NotificationsController {
    public final AnimatedImageNotificationManager animatedImageNotificationManager;
    public final Optional bubblesOptional;
    public final NotificationClicker.Builder clickerBuilder;
    public final Lazy commonNotifCollection;
    public final HeadsUpViewBinder headsUpViewBinder;
    public final NotifBindPipelineInitializer notifBindPipelineInitializer;
    public final NotifLiveDataStore notifLiveDataStore;
    public final Lazy notifPipeline;
    public final Lazy notifPipelineInitializer;
    public final NotificationListener notificationListener;
    public final Optional notificationLoggerOptional;
    public final NotificationRowBinderImpl notificationRowBinder;
    public final NotificationMediaManager notificationsMediaManager;
    public final PeopleSpaceWidgetManager peopleSpaceWidgetManager;
    public final TargetSdkResolver targetSdkResolver;

    public NotificationsControllerImpl(NotificationListener notificationListener, Lazy lazy, Lazy lazy2, NotifLiveDataStore notifLiveDataStore, TargetSdkResolver targetSdkResolver, Lazy lazy3, NotifBindPipelineInitializer notifBindPipelineInitializer, Optional<NotificationLogger> optional, NotificationRowBinderImpl notificationRowBinderImpl, NotificationMediaManager notificationMediaManager, HeadsUpViewBinder headsUpViewBinder, NotificationClicker.Builder builder, AnimatedImageNotificationManager animatedImageNotificationManager, PeopleSpaceWidgetManager peopleSpaceWidgetManager, Optional<Bubbles> optional2) {
        this.notificationListener = notificationListener;
        this.commonNotifCollection = lazy;
        this.notifPipeline = lazy2;
        this.notifLiveDataStore = notifLiveDataStore;
        this.targetSdkResolver = targetSdkResolver;
        this.notifPipelineInitializer = lazy3;
        this.notifBindPipelineInitializer = notifBindPipelineInitializer;
        this.notificationLoggerOptional = optional;
        this.notificationRowBinder = notificationRowBinderImpl;
        this.notificationsMediaManager = notificationMediaManager;
        this.headsUpViewBinder = headsUpViewBinder;
        this.clickerBuilder = builder;
        this.animatedImageNotificationManager = animatedImageNotificationManager;
        this.peopleSpaceWidgetManager = peopleSpaceWidgetManager;
        this.bubblesOptional = optional2;
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final int getActiveNotificationsCount() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationsLiveDataStoreRefactor.$r8$clinit;
        Flags.notificationsLiveDataStoreRefactor();
        return ((Number) ((NotifLiveDataStoreImpl) this.notifLiveDataStore).activeNotifCount.atomicValue.get()).intValue();
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final void initialize(NotificationPresenter notificationPresenter, final NotificationListContainer notificationListContainer, NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl, NotificationActivityStarter notificationActivityStarter) {
        NotificationListener notificationListener = this.notificationListener;
        notificationListener.registerAsSystemService();
        Lazy lazy = this.notifPipeline;
        ((NotifPipeline) lazy.get()).addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.init.NotificationsControllerImpl$initialize$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
                notificationStackScrollLayout.getClass();
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayout.mSwipeHelper;
                if (expandableNotificationRow == notificationSwipeHelper.mTranslatingParentView) {
                    notificationSwipeHelper.setTranslatingParentView(null);
                }
            }
        });
        Optional optional = this.bubblesOptional;
        NotificationClicker.Builder builder = this.clickerBuilder;
        builder.getClass();
        NotificationClicker notificationClicker = new NotificationClicker(builder.mLogger, builder.mPowerInteractor, optional, notificationActivityStarter, 0);
        NotificationRowBinderImpl notificationRowBinderImpl = this.notificationRowBinder;
        notificationRowBinderImpl.mNotificationClicker = notificationClicker;
        notificationRowBinderImpl.mPresenter = notificationPresenter;
        notificationRowBinderImpl.mListContainer = notificationListContainer;
        IconManager iconManager = notificationRowBinderImpl.mIconManager;
        ((NotifPipeline) iconManager.notifCollection).addCollectionListener(iconManager.entryListener);
        this.headsUpViewBinder.mNotificationPresenter = notificationPresenter;
        this.notifBindPipelineInitializer.initialize();
        final AnimatedImageNotificationManager animatedImageNotificationManager = this.animatedImageNotificationManager;
        animatedImageNotificationManager.getClass();
        ((BaseHeadsUpManager) animatedImageNotificationManager.headsUpManager).addListener(new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$bind$1
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
                AnimatedImageNotificationManager.access$updateAnimatedImageDrawables(AnimatedImageNotificationManager.this, notificationEntry);
            }
        });
        animatedImageNotificationManager.statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$bind$2
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onExpandedChanged(boolean z) {
                AnimatedImageNotificationManager animatedImageNotificationManager2 = AnimatedImageNotificationManager.this;
                animatedImageNotificationManager2.isStatusBarExpanded = z;
                Iterator it = ((NotifPipeline) animatedImageNotificationManager2.notifCollection).getAllNotifs().iterator();
                while (it.hasNext()) {
                    AnimatedImageNotificationManager.access$updateAnimatedImageDrawables(animatedImageNotificationManager2, (NotificationEntry) it.next());
                }
            }
        });
        animatedImageNotificationManager.bindEventManager.listeners.addIfAbsent(new AnimatedImageNotificationManager$bind$3(animatedImageNotificationManager));
        NotifPipelineInitializer notifPipelineInitializer = (NotifPipelineInitializer) this.notifPipelineInitializer.get();
        DumpManager dumpManager = notifPipelineInitializer.mDumpManager;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "NotifPipeline", notifPipelineInitializer);
        notifPipelineInitializer.mNotificationService = notificationListener;
        notifPipelineInitializer.mNotifInflater.mNotificationRowBinder = notificationRowBinderImpl;
        notifPipelineInitializer.mNotifPluggableCoordinators.attach(notifPipelineInitializer.mPipelineWrapper);
        ShadeViewManager create = notifPipelineInitializer.mShadeViewManagerFactory.create(notificationListContainer, notifStackControllerImpl);
        notifPipelineInitializer.mShadeViewManager = create;
        ShadeViewManager$viewRenderer$1 shadeViewManager$viewRenderer$1 = create.viewRenderer;
        RenderStageManager renderStageManager = notifPipelineInitializer.mRenderStageManager;
        renderStageManager.viewRenderer = shadeViewManager$viewRenderer$1;
        RenderStageManager$attach$1 renderStageManager$attach$1 = new RenderStageManager$attach$1(renderStageManager);
        Assert.isMainThread();
        final ShadeListBuilder shadeListBuilder = notifPipelineInitializer.mListBuilder;
        shadeListBuilder.mPipelineState.requireState();
        shadeListBuilder.mOnRenderListListener = renderStageManager$attach$1;
        Assert.isMainThread();
        DumpManager dumpManager2 = shadeListBuilder.mDumpManager;
        dumpManager2.getClass();
        DumpManager.registerDumpable$default(dumpManager2, "ShadeListBuilder", shadeListBuilder);
        NotifCollection notifCollection = notifPipelineInitializer.mNotifCollection;
        notifCollection.getClass();
        Assert.isMainThread();
        notifCollection.mNotifCollectionListeners.addIfAbsent(shadeListBuilder.mInteractionTracker);
        Assert.isMainThread();
        notifCollection.mBuildListener = shadeListBuilder.mReadyForBuildListener;
        ((NotifPipelineChoreographerImpl) shadeListBuilder.mChoreographer).listeners.addIfAbsent(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                ShadeListBuilder.this.buildList();
            }
        });
        Assert.isMainThread();
        if (notifCollection.mAttached) {
            throw new RuntimeException("attach() called twice");
        }
        notifCollection.mAttached = true;
        DumpManager dumpManager3 = notifCollection.mDumpManager;
        dumpManager3.getClass();
        DumpManager.registerDumpable$default(dumpManager3, "NotifCollection", notifCollection);
        NotifCollection.AnonymousClass1 anonymousClass1 = notifCollection.mNotifHandler;
        final GroupCoalescer groupCoalescer = notifPipelineInitializer.mGroupCoalescer;
        groupCoalescer.mHandler = anonymousClass1;
        final Map map = notifCollection.mNotificationSet;
        Objects.requireNonNull(map);
        final int i = 0;
        Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return ((Map) map).keySet();
                    default:
                        return Collections.unmodifiableSet(((ArrayMap) ((GroupCoalescer) map).mCoalescedEvents).keySet());
                }
            }
        };
        final int i2 = 1;
        Function0 function02 = new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return ((Map) groupCoalescer).keySet();
                    default:
                        return Collections.unmodifiableSet(((ArrayMap) ((GroupCoalescer) groupCoalescer).mCoalescedEvents).keySet());
                }
            }
        };
        NotifCollectionInconsistencyTracker notifCollectionInconsistencyTracker = notifCollection.mInconsistencyTracker;
        if (notifCollectionInconsistencyTracker.attached) {
            throw new RuntimeException("attach() called twice");
        }
        notifCollectionInconsistencyTracker.attached = true;
        notifCollectionInconsistencyTracker.collectedKeySetAccessor = function0;
        notifCollectionInconsistencyTracker.coalescedKeySetAccessor = function02;
        notifPipelineInitializer.mNotificationService.addNotificationHandler(groupCoalescer.mListener);
        Log.d("NotifPipeline", "Notif pipeline initialized. rendering=true");
        CommonNotifCollection commonNotifCollection = (CommonNotifCollection) lazy.get();
        final TargetSdkResolver targetSdkResolver = this.targetSdkResolver;
        targetSdkResolver.getClass();
        ((NotifPipeline) commonNotifCollection).addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.TargetSdkResolver$initialize$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
                ApplicationInfo applicationInfo;
                TargetSdkResolver targetSdkResolver2 = TargetSdkResolver.this;
                targetSdkResolver2.getClass();
                ApplicationInfo applicationInfo2 = (ApplicationInfo) statusBarNotification.getNotification().extras.getParcelable("android.appInfo", ApplicationInfo.class);
                if (applicationInfo2 == null) {
                    try {
                        applicationInfo = CentralSurfaces.getPackageManagerForUser(statusBarNotification.getUser().getIdentifier(), targetSdkResolver2.context).getApplicationInfo(statusBarNotification.getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e(targetSdkResolver2.TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Failed looking up ApplicationInfo for ", statusBarNotification.getPackageName()), e);
                        applicationInfo = null;
                    }
                    applicationInfo2 = applicationInfo;
                }
                notificationEntry.targetSdk = applicationInfo2 != null ? applicationInfo2.targetSdkVersion : 0;
            }
        });
        this.notificationsMediaManager.getClass();
        Flags.notificationsLiveDataStoreRefactor();
        this.notificationLoggerOptional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.init.NotificationsControllerImpl$initialize$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationLogger notificationLogger = (NotificationLogger) obj;
                NotificationListContainer notificationListContainer2 = NotificationListContainer.this;
                notificationLogger.mListContainer = notificationListContainer2;
                if (notificationLogger.mLogging) {
                    NotificationLogger$$ExternalSyntheticLambda2 notificationLogger$$ExternalSyntheticLambda2 = new NotificationLogger$$ExternalSyntheticLambda2(notificationLogger);
                    NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
                    notificationStackScrollLayout.getClass();
                    int i3 = NotificationsLiveDataStoreRefactor.$r8$clinit;
                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                    Flags.notificationsLiveDataStoreRefactor();
                    notificationStackScrollLayout.mListener = notificationLogger$$ExternalSyntheticLambda2;
                }
            }
        });
        notificationListener.addNotificationHandler(this.peopleSpaceWidgetManager.mListener);
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final void resetUserExpandedStates() {
        Iterator it = ((NotifPipeline) ((CommonNotifCollection) this.commonNotifCollection.get())).getAllNotifs().iterator();
        while (it.hasNext()) {
            ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
            if (expandableNotificationRow != null) {
                boolean isExpanded = expandableNotificationRow.isExpanded(false);
                expandableNotificationRow.mHasUserChangedExpansion = false;
                expandableNotificationRow.mUserExpanded = false;
                if (isExpanded != expandableNotificationRow.isExpanded(false)) {
                    if (expandableNotificationRow.mIsSummaryWithChildren) {
                        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                        if (notificationChildrenContainer.mIsMinimized) {
                            boolean z = notificationChildrenContainer.mUserLocked;
                            if (z) {
                                notificationChildrenContainer.setUserLocked(z);
                            }
                            notificationChildrenContainer.updateHeaderVisibility(true, false);
                        }
                        if (!notificationChildrenContainer.mUserLocked) {
                            notificationChildrenContainer.updateHeaderVisibility(true, false);
                        }
                    }
                    expandableNotificationRow.notifyHeightChanged(false);
                }
                expandableNotificationRow.updateShelfIconColor();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final void setNotificationSnoozed(StatusBarNotification statusBarNotification, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
        SnoozeCriterion snoozeCriterion = snoozeOption.getSnoozeCriterion();
        NotificationListener notificationListener = this.notificationListener;
        if (snoozeCriterion != null) {
            notificationListener.snoozeNotification(statusBarNotification.getKey(), snoozeOption.getSnoozeCriterion().getId());
        } else {
            notificationListener.snoozeNotification(statusBarNotification.getKey(), snoozeOption.getMinutesToSnoozeFor() * 60 * 1000);
        }
    }
}
