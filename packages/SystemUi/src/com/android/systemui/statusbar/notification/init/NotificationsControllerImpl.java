package com.android.systemui.statusbar.notification.init;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.ForegroundServiceNotificationListener;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
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
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinatorsImpl;
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
import com.android.systemui.statusbar.notification.logging.NotificationMemoryDumper;
import com.android.systemui.statusbar.notification.logging.NotificationMemoryMonitor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.Assert;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationsControllerImpl implements NotificationsController {
    public final AnimatedImageNotificationManager animatedImageNotificationManager;
    public final Optional bubblesOptional;
    public final NotificationClicker.Builder clickerBuilder;
    public final Lazy commonNotifCollection;
    public final ForegroundServiceNotificationListener fgsNotifListener;
    public final HeadsUpViewBinder headsUpViewBinder;
    public final Lazy memoryMonitor;
    public final NotifBindPipelineInitializer notifBindPipelineInitializer;
    public final NotifLiveDataStore notifLiveDataStore;
    public final Lazy notifPipeline;
    public final Lazy notifPipelineInitializer;
    public final NotificationListener notificationListener;
    public final NotificationLogger notificationLogger;
    public final NotificationRowBinderImpl notificationRowBinder;
    public final NotificationMediaManager notificationsMediaManager;
    public final PeopleSpaceWidgetManager peopleSpaceWidgetManager;
    public final TargetSdkResolver targetSdkResolver;

    public NotificationsControllerImpl(NotificationListener notificationListener, Lazy lazy, Lazy lazy2, NotifLiveDataStore notifLiveDataStore, TargetSdkResolver targetSdkResolver, Lazy lazy3, NotifBindPipelineInitializer notifBindPipelineInitializer, NotificationLogger notificationLogger, NotificationRowBinderImpl notificationRowBinderImpl, NotificationMediaManager notificationMediaManager, HeadsUpViewBinder headsUpViewBinder, NotificationClicker.Builder builder, AnimatedImageNotificationManager animatedImageNotificationManager, PeopleSpaceWidgetManager peopleSpaceWidgetManager, Optional<Bubbles> optional, ForegroundServiceNotificationListener foregroundServiceNotificationListener, Lazy lazy4, FeatureFlags featureFlags) {
        this.notificationListener = notificationListener;
        this.commonNotifCollection = lazy;
        this.notifPipeline = lazy2;
        this.notifLiveDataStore = notifLiveDataStore;
        this.targetSdkResolver = targetSdkResolver;
        this.notifPipelineInitializer = lazy3;
        this.notifBindPipelineInitializer = notifBindPipelineInitializer;
        this.notificationLogger = notificationLogger;
        this.notificationRowBinder = notificationRowBinderImpl;
        this.notificationsMediaManager = notificationMediaManager;
        this.headsUpViewBinder = headsUpViewBinder;
        this.clickerBuilder = builder;
        this.animatedImageNotificationManager = animatedImageNotificationManager;
        this.peopleSpaceWidgetManager = peopleSpaceWidgetManager;
        this.bubblesOptional = optional;
        this.fgsNotifListener = foregroundServiceNotificationListener;
        this.memoryMonitor = lazy4;
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final int getActiveNotificationsCount() {
        return ((Number) ((NotifLiveDataStoreImpl) this.notifLiveDataStore).activeNotifCount.getValue()).intValue();
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public final void initialize(CentralSurfaces centralSurfaces, NotificationPresenter notificationPresenter, final NotificationListContainer notificationListContainer, NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl, NotificationActivityStarter notificationActivityStarter, NotificationRowBinderImpl.BindRowCallback bindRowCallback) {
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
        Optional ofNullable = Optional.ofNullable(centralSurfaces);
        Optional optional = this.bubblesOptional;
        NotificationClicker.Builder builder = this.clickerBuilder;
        builder.getClass();
        NotificationClicker notificationClicker = new NotificationClicker(builder.mLogger, ofNullable, optional, notificationActivityStarter, 0);
        NotificationRowBinderImpl notificationRowBinderImpl = this.notificationRowBinder;
        notificationRowBinderImpl.mNotificationClicker = notificationClicker;
        notificationRowBinderImpl.mPresenter = notificationPresenter;
        notificationRowBinderImpl.mListContainer = notificationListContainer;
        notificationRowBinderImpl.mBindRowCallback = bindRowCallback;
        IconManager iconManager = notificationRowBinderImpl.mIconManager;
        ((NotifPipeline) iconManager.notifCollection).addCollectionListener(iconManager.entryListener);
        this.headsUpViewBinder.mNotificationPresenter = notificationPresenter;
        this.notifBindPipelineInitializer.initialize();
        final AnimatedImageNotificationManager animatedImageNotificationManager = this.animatedImageNotificationManager;
        animatedImageNotificationManager.getClass();
        animatedImageNotificationManager.headsUpManager.addListener(new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$bind$1
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
        ((NotifCoordinatorsImpl) notifPipelineInitializer.mNotifPluggableCoordinators).attach(notifPipelineInitializer.mPipelineWrapper);
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
        ((ArrayList) notifCollection.mNotifCollectionListeners).add(shadeListBuilder.mInteractionTracker);
        Assert.isMainThread();
        notifCollection.mBuildListener = shadeListBuilder.mReadyForBuildListener;
        ((NotifPipelineChoreographerImpl) shadeListBuilder.mChoreographer).listeners.addIfAbsent(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ShadeListBuilder.this.buildList();
            }
        });
        Assert.isMainThread();
        if (notifCollection.mAttached) {
            throw new RuntimeException("attach() called twice");
        }
        final int i = 1;
        notifCollection.mAttached = true;
        DumpManager dumpManager3 = notifCollection.mDumpManager;
        dumpManager3.getClass();
        DumpManager.registerDumpable$default(dumpManager3, "NotifCollection", notifCollection);
        NotifCollection.C27901 c27901 = notifCollection.mNotifHandler;
        final GroupCoalescer groupCoalescer = notifPipelineInitializer.mGroupCoalescer;
        groupCoalescer.mHandler = c27901;
        final Map map = notifCollection.mNotificationSet;
        Objects.requireNonNull(map);
        final int i2 = 0;
        Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i3 = i2;
                Object obj = map;
                switch (i3) {
                    case 0:
                        return ((Map) obj).keySet();
                    default:
                        return Collections.unmodifiableSet(((ArrayMap) ((GroupCoalescer) obj).mCoalescedEvents).keySet());
                }
            }
        };
        Function0 function02 = new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i3 = i;
                Object obj = groupCoalescer;
                switch (i3) {
                    case 0:
                        return ((Map) obj).keySet();
                    default:
                        return Collections.unmodifiableSet(((ArrayMap) ((GroupCoalescer) obj).mCoalescedEvents).keySet());
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
                        Log.e(targetSdkResolver2.TAG, KeyAttributes$$ExternalSyntheticOutline0.m21m("Failed looking up ApplicationInfo for ", statusBarNotification.getPackageName()), e);
                        applicationInfo = null;
                    }
                    applicationInfo2 = applicationInfo;
                }
                notificationEntry.targetSdk = applicationInfo2 != null ? applicationInfo2.targetSdkVersion : 0;
            }
        });
        this.notificationsMediaManager.mPresenter = notificationPresenter;
        this.notificationLogger.mListContainer = notificationListContainer;
        notificationListener.addNotificationHandler(this.peopleSpaceWidgetManager.mListener);
        ForegroundServiceNotificationListener foregroundServiceNotificationListener = this.fgsNotifListener;
        foregroundServiceNotificationListener.getClass();
        foregroundServiceNotificationListener.mNotifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.ForegroundServiceNotificationListener.1
            public C09651() {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryAdded(NotificationEntry notificationEntry) {
                int importance = notificationEntry.getImportance();
                ForegroundServiceNotificationListener foregroundServiceNotificationListener2 = ForegroundServiceNotificationListener.this;
                foregroundServiceNotificationListener2.getClass();
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                foregroundServiceNotificationListener2.mForegroundServiceController.updateUserState(statusBarNotification.getUserId(), new ForegroundServiceNotificationListener$$ExternalSyntheticLambda0(foregroundServiceNotificationListener2, statusBarNotification, importance), true);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i3) {
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                ForegroundServiceNotificationListener foregroundServiceNotificationListener2 = ForegroundServiceNotificationListener.this;
                foregroundServiceNotificationListener2.getClass();
                foregroundServiceNotificationListener2.mForegroundServiceController.updateUserState(statusBarNotification.getUserId(), new ForegroundServiceController.UserStateUpdateCallback() { // from class: com.android.systemui.ForegroundServiceNotificationListener.2
                    public final /* synthetic */ StatusBarNotification val$sbn;

                    public C09662(StatusBarNotification statusBarNotification2) {
                        r2 = statusBarNotification2;
                    }

                    @Override // com.android.systemui.ForegroundServiceController.UserStateUpdateCallback
                    public final boolean updateUserState(ForegroundServicesUserState foregroundServicesUserState) {
                        boolean remove;
                        ForegroundServiceNotificationListener.this.mForegroundServiceController.getClass();
                        StatusBarNotification statusBarNotification2 = r2;
                        if (ForegroundServiceController.isDisclosureNotification(statusBarNotification2)) {
                            foregroundServicesUserState.mRunning = null;
                            foregroundServicesUserState.mServiceStartTime = 0L;
                            return true;
                        }
                        String packageName = statusBarNotification2.getPackageName();
                        String key = statusBarNotification2.getKey();
                        ArrayMap arrayMap = foregroundServicesUserState.mImportantNotifications;
                        ArraySet arraySet = (ArraySet) arrayMap.get(packageName);
                        boolean z = false;
                        if (arraySet == null) {
                            remove = false;
                        } else {
                            remove = arraySet.remove(key);
                            if (arraySet.size() == 0) {
                                arrayMap.remove(packageName);
                            }
                        }
                        boolean z2 = remove | false;
                        ArrayMap arrayMap2 = foregroundServicesUserState.mStandardLayoutNotifications;
                        ArraySet arraySet2 = (ArraySet) arrayMap2.get(packageName);
                        if (arraySet2 != null) {
                            z = arraySet2.remove(key);
                            if (arraySet2.size() == 0) {
                                arrayMap2.remove(packageName);
                            }
                        }
                        return z | z2;
                    }
                }, false);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry) {
                int importance = notificationEntry.getImportance();
                ForegroundServiceNotificationListener foregroundServiceNotificationListener2 = ForegroundServiceNotificationListener.this;
                foregroundServiceNotificationListener2.getClass();
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                foregroundServiceNotificationListener2.mForegroundServiceController.updateUserState(statusBarNotification.getUserId(), new ForegroundServiceNotificationListener$$ExternalSyntheticLambda0(foregroundServiceNotificationListener2, statusBarNotification, importance), true);
            }
        });
        NotificationMemoryMonitor notificationMemoryMonitor = (NotificationMemoryMonitor) this.memoryMonitor.get();
        notificationMemoryMonitor.getClass();
        Log.d("NotificationMemory", "NotificationMemoryMonitor initialized.");
        NotificationMemoryDumper notificationMemoryDumper = notificationMemoryMonitor.notificationMemoryDumper;
        notificationMemoryDumper.dumpManager.registerNormalDumpable("NotificationMemoryDumper", notificationMemoryDumper);
        Log.i("NotificationMemory", "Registered dumpable.");
        Flags.INSTANCE.getClass();
        notificationMemoryMonitor.featureFlags.getClass();
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
                        if (notificationChildrenContainer.mIsLowPriority) {
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
