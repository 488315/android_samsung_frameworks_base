package com.android.systemui.statusbar.notification.stack;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewGroupKt$children$1;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.ExpandHelper;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.log.SecTouchLogHelper;
import com.android.systemui.media.controls.p010ui.KeyguardMediaController;
import com.android.systemui.notification.FullExpansionPanelNotiAlphaController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scrim.ScrimView;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.iconsOnly.NotificationIconTransitionController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.provider.SeenNotificationsProvider;
import com.android.systemui.statusbar.notification.collection.provider.VisibilityLocationProviderDelegator;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.row.NotificationSnooze;
import com.android.systemui.statusbar.notification.shelf.p022ui.viewbinder.NotificationShelfViewBinder;
import com.android.systemui.statusbar.notification.shelf.p022ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.notification.stack.p023ui.viewbinder.NotificationListViewBinder;
import com.android.systemui.statusbar.notification.stack.p023ui.viewmodel.NotificationListViewModel;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationStackScrollLayoutController implements NotificationRoundnessManager.SectionStateProvider {
    public final ActivityStarter mActivityStarter;
    public final boolean mAllowLongPress;
    public int mBarState;
    public final CentralSurfaces mCentralSurfaces;
    public final ConfigurationController mConfigurationController;
    final ConfigurationController.ConfigurationListener mConfigurationListener;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final C29624 mDeviceProvisionedListener;
    public final NotificationDismissibilityProvider mDismissibilityProvider;
    public final DynamicPrivacyController mDynamicPrivacyController;
    public final C2941xbae1b0c0 mDynamicPrivacyControllerListener;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final RunnableC29521 mForceLayoutTimeOutRunnable;
    public final GroupExpansionManager mGroupExpansionManager;
    public boolean mHasDelayedForceLayout;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public Boolean mHistoryEnabled;
    public boolean mIsStartFromContentsBound;
    public final InteractionJankMonitor mJankMonitor;
    public KeyguardUpdateMonitorCallback mKeyguardUpdateCallback;
    public final C2940xbae1b0bf mKeyguardVisibilityListener;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public final C29657 mLockscreenUserChangeListener;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public View mLongPressedView;
    public boolean mMediaPlayerVisible;
    public final C29668 mMenuEventListener;
    public final MetricsLogger mMetricsLogger;
    public final NotifCollection mNotifCollection;
    public final NotificationIconAreaController mNotifIconAreaController;
    public final NotifStackControllerImpl mNotifStackController;
    public NotifStats mNotifStats;
    public NotificationActivityStarter mNotificationActivityStarter;
    final NotificationSwipeHelper.NotificationCallback mNotificationCallback;
    public final NotificationGutsManager mNotificationGutsManager;
    public final NotificationListContainerImpl mNotificationListContainer;
    public final NotificationRoundnessManager mNotificationRoundnessManager;
    public final NotificationStackSizeCalculator mNotificationStackSizeCalculator;
    public final NotificationTargetsHelper mNotificationTargetsHelper;
    final View.OnAttachStateChangeListener mOnAttachStateChangeListener;
    public final C295310 mOnHeadsUpChangedListener;
    public boolean mProgressingShadeLockedFromNotiIcon;
    public final SecureSettings mSecureSettings;
    public final SeenNotificationsProvider mSeenNotificationsProvider;
    public final NotificationShelfManager mShelfManager;
    public final C29646 mStateListener;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public NotificationSwipeHelper mSwipeHelper;
    public final UiEventLogger mUiEventLogger;
    public final NotificationStackScrollLayout mView;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public final C295512 mWallpaperThemeCallback;
    public final ZenModeController mZenModeController;
    public final C295411 mZenModeControllerCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$1 */
    public final class RunnableC29521 implements Runnable {
        public RunnableC29521() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            if (notificationStackScrollLayoutController.mHasDelayedForceLayout) {
                notificationStackScrollLayoutController.mHasDelayedForceLayout = false;
                Log.d("StackScrollerController", "delayed force layout time out. do forcelayout");
                NotificationStackScrollLayoutController.this.mView.forceLayout();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$15 */
    public final class C295815 implements RemoteInputController.Delegate {
        public C295815() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$2 */
    public final class C29602 {
        public C29602() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$6 */
    public final class C29646 implements StatusBarStateController.StateListener {
        public C29646() {
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mBarState = i;
            notificationStackScrollLayoutController.mView.setStatusBarState(i);
            if (i == 1) {
                ((GroupExpansionManagerImpl) notificationStackScrollLayoutController.mGroupExpansionManager).collapseGroups();
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStatePostChange() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mView.updateSensitiveness(((StatusBarStateControllerImpl) notificationStackScrollLayoutController.mStatusBarStateController).goingToFullShade(), ((NotificationLockscreenUserManagerImpl) notificationStackScrollLayoutController.mLockscreenUserManager).isAnyProfilePublicMode());
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            int i = 1;
            boolean z = ((StatusBarStateControllerImpl) notificationStackScrollLayoutController.mStatusBarStateController).mLastState == 2;
            boolean onKeyguard = notificationStackScrollLayout.onKeyguard();
            notificationStackScrollLayout.mAmbientState.mDimmed = onKeyguard;
            HeadsUpAppearanceController headsUpAppearanceController = notificationStackScrollLayout.mHeadsUpAppearanceController;
            if (headsUpAppearanceController != null) {
                headsUpAppearanceController.updateTopEntry();
            }
            notificationStackScrollLayout.setDimmed(onKeyguard, z);
            notificationStackScrollLayout.mExpandHelper.mEnabled = !onKeyguard;
            notificationStackScrollLayout.updateFooter();
            notificationStackScrollLayout.requestChildrenUpdate();
            if (NotiRune.NOTI_STYLE_EMPTY_SHADE) {
                notificationStackScrollLayout.changeViewPosition(notificationStackScrollLayout.mEmptyShadeView, notificationStackScrollLayout.getChildCount() - 1);
                i = 2;
            }
            notificationStackScrollLayout.changeViewPosition(notificationStackScrollLayout.mShelf, notificationStackScrollLayout.getChildCount() - i);
            notificationStackScrollLayout.updateVisibility();
            notificationStackScrollLayoutController.updateImportantForAccessibility();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStatePreChange(int i, int i2) {
            if (i == 2 && i2 == 1) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                int i3 = notificationStackScrollLayoutController.mLockscreenNotificationManager.mCurrentNotificationType;
                boolean z = i3 == 1 || i3 == 2;
                NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                if (z) {
                    if (notificationStackScrollLayout.mIsExpanded && notificationStackScrollLayout.mAnimationsEnabled) {
                        notificationStackScrollLayout.mEverythingNeedsAnimation = false;
                        notificationStackScrollLayout.mNeedsAnimation = false;
                        notificationStackScrollLayout.requestChildrenUpdate();
                        return;
                    }
                    return;
                }
                if (notificationStackScrollLayout.mIsExpanded && notificationStackScrollLayout.mAnimationsEnabled) {
                    notificationStackScrollLayout.mEverythingNeedsAnimation = true;
                    notificationStackScrollLayout.mNeedsAnimation = true;
                    notificationStackScrollLayout.requestChildrenUpdate();
                }
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onUpcomingStateChanged(int i) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            notificationStackScrollLayout.mUpcomingStatusBarState = i;
            if (i != notificationStackScrollLayout.mStatusBarState) {
                notificationStackScrollLayout.updateFooter();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$9 */
    public final class C29679 implements NotificationSwipeHelper.NotificationCallback {
        public C29679() {
        }

        public final boolean canChildBeDismissed(View view) {
            int i = NotificationStackScrollLayout.$r8$clinit;
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (!expandableNotificationRow.areGutsExposed() && expandableNotificationRow.mEntry.hasFinishedInitialization()) {
                    return expandableNotificationRow.canViewBeDismissed$1();
                }
            }
            return false;
        }

        public final ExpandableView getChildAtPosition(MotionEvent motionEvent) {
            ExpandableNotificationRow expandableNotificationRow;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            ExpandableView childAtPosition = notificationStackScrollLayoutController.mView.getChildAtPosition(motionEvent.getX(), true, false, motionEvent.getY());
            if (!(childAtPosition instanceof ExpandableNotificationRow) || (expandableNotificationRow = ((ExpandableNotificationRow) childAtPosition).mNotificationParent) == null || !expandableNotificationRow.mChildrenExpanded) {
                return childAtPosition;
            }
            if (!expandableNotificationRow.areGutsExposed() && notificationStackScrollLayoutController.mSwipeHelper.mMenuExposedView != expandableNotificationRow) {
                if (expandableNotificationRow.getAttachedChildren().size() != 1) {
                    return childAtPosition;
                }
                if (!((NotificationDismissibilityProviderImpl) notificationStackScrollLayoutController.mDismissibilityProvider).isDismissable(expandableNotificationRow.mEntry)) {
                    return childAtPosition;
                }
            }
            return expandableNotificationRow;
        }

        public final void handleChildViewDismissed(View view) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.updateFirstAndLastBackgroundViews();
            notificationStackScrollLayout.mController.mNotificationRoundnessManager.getClass();
            notificationStackScrollLayout.mShelf.updateAppearance();
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
            if (notificationStackScrollLayout2.mClearAllInProgress) {
                Log.d("StackScrollerController", "dismiss notification, but ClearAllInProgressing..");
                return;
            }
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.mIsHeadsUp) {
                    notificationStackScrollLayoutController.mHeadsUpManager.mSwipedOutKeys.add(expandableNotificationRow.mEntry.mSbn.getKey());
                }
                expandableNotificationRow.performDismiss(false);
            }
            notificationStackScrollLayout2.mSwipedOutViews.add(view);
            FalsingCollector falsingCollector = notificationStackScrollLayoutController.mFalsingCollector;
            falsingCollector.getClass();
            falsingCollector.getClass();
        }

        public final void onBeginDrag(View view) {
            Roundable roundable;
            ExpandableView expandableView;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mFalsingCollector.getClass();
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            if (view instanceof ExpandableNotificationRow) {
                notificationStackScrollLayout.mSectionsManager.updateFirstAndLastViewsForAllSections(notificationStackScrollLayout.mSections, notificationStackScrollLayout.getChildrenWithBackground());
                NotificationTargetsHelper notificationTargetsHelper = notificationStackScrollLayout.mController.mNotificationTargetsHelper;
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                NotificationSectionsManager notificationSectionsManager = notificationStackScrollLayout.mSectionsManager;
                notificationTargetsHelper.getClass();
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
                ExpandableView expandableView2 = null;
                NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2 != null ? expandableNotificationRow2.mChildrenContainer : null;
                List list = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new ViewGroupKt$children$1(notificationStackScrollLayout), new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationTargetsHelper$findRoundableTargets$$inlined$filterIsInstance$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(obj instanceof ExpandableView);
                    }
                }), new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationTargetsHelper$findRoundableTargets$visibleStackChildren$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(((ExpandableView) obj).getVisibility() == 0);
                    }
                }));
                if (expandableNotificationRow2 == null || notificationChildrenContainer == null) {
                    int indexOf = list.indexOf(expandableNotificationRow);
                    ExpandableView expandableView3 = (ExpandableView) CollectionsKt___CollectionsKt.getOrNull(indexOf - 1, list);
                    roundable = (expandableView3 == null || !(notificationSectionsManager.beginsSection(expandableNotificationRow, expandableView3) ^ true)) ? null : expandableView3;
                    ExpandableView expandableView4 = (ExpandableView) CollectionsKt___CollectionsKt.getOrNull(indexOf + 1, list);
                    if (expandableView4 != null && (!notificationSectionsManager.beginsSection(expandableView4, expandableNotificationRow))) {
                        expandableView2 = expandableView4;
                    }
                    expandableView = expandableView2;
                } else {
                    List list2 = notificationChildrenContainer.mAttachedChildren;
                    ArrayList arrayList = new ArrayList();
                    Iterator it = ((ArrayList) list2).iterator();
                    while (it.hasNext()) {
                        Object next = it.next();
                        if (((ExpandableNotificationRow) next).getVisibility() == 0) {
                            arrayList.add(next);
                        }
                    }
                    int indexOf2 = arrayList.indexOf(expandableNotificationRow);
                    roundable = (ExpandableNotificationRow) CollectionsKt___CollectionsKt.getOrNull(indexOf2 - 1, arrayList);
                    if (roundable == null) {
                        roundable = notificationChildrenContainer.mNotificationHeaderWrapper;
                    }
                    expandableView = (ExpandableNotificationRow) CollectionsKt___CollectionsKt.getOrNull(indexOf2 + 1, arrayList);
                    if (expandableView == null) {
                        expandableView = (ExpandableView) CollectionsKt___CollectionsKt.getOrNull(list.indexOf(expandableNotificationRow2) + 1, list);
                    }
                }
                new RoundableTargets(roundable, expandableNotificationRow, expandableView);
                notificationStackScrollLayout.mController.mNotificationRoundnessManager.getClass();
                notificationStackScrollLayout.updateFirstAndLastBackgroundViews();
                notificationStackScrollLayout.requestDisallowInterceptTouchEvent(true);
                notificationStackScrollLayout.updateContinuousShadowDrawing();
                notificationStackScrollLayout.updateContinuousBackgroundDrawing();
                notificationStackScrollLayout.requestChildrenUpdate();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotifStackControllerImpl implements NotifStackController {
        public /* synthetic */ NotifStackControllerImpl(NotificationStackScrollLayoutController notificationStackScrollLayoutController, int i) {
            this();
        }

        private NotifStackControllerImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotificationListContainerImpl implements NotificationListContainer, PipelineDumpable {
        public /* synthetic */ NotificationListContainerImpl(NotificationStackScrollLayoutController notificationStackScrollLayoutController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
        public final void dumpPipeline(PipelineDumper pipelineDumper) {
            pipelineDumper.dump(NotificationStackScrollLayoutController.this, "NotificationStackScrollLayoutController.this");
        }

        @Override // com.android.systemui.statusbar.notification.VisibilityLocationProvider
        public final boolean isInVisibleLocation(NotificationEntry notificationEntry) {
            NotificationStackScrollLayoutController.this.getClass();
            return NotificationStackScrollLayoutController.isInVisibleLocation(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public final void onHeightChanged(ExpandableView expandableView, boolean z) {
            NotificationStackScrollLayoutController.this.mView.onChildHeightChanged(expandableView, z);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public final void onReset(ExpandableNotificationRow expandableNotificationRow) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            expandableNotificationRow.setAnimationRunning((notificationStackScrollLayout.mAnimationsEnabled || notificationStackScrollLayout.mPulsing) && (notificationStackScrollLayout.mIsExpanded || NotificationStackScrollLayout.isPinnedHeadsUp(expandableNotificationRow)));
            expandableNotificationRow.setChronometerRunning(notificationStackScrollLayout.mIsExpanded);
        }

        public final void setExpandingNotification(ExpandableNotificationRow expandableNotificationRow) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            ExpandableNotificationRow expandableNotificationRow2 = notificationStackScrollLayout.mExpandingNotificationRow;
            if (expandableNotificationRow2 != null && expandableNotificationRow == null) {
                expandableNotificationRow2.mExpandingClipPath = null;
                expandableNotificationRow2.invalidate();
                ExpandableNotificationRow expandableNotificationRow3 = notificationStackScrollLayout.mExpandingNotificationRow.mNotificationParent;
                if (expandableNotificationRow3 != null) {
                    expandableNotificationRow3.mExpandingClipPath = null;
                    expandableNotificationRow3.invalidate();
                }
            }
            notificationStackScrollLayout.mExpandingNotificationRow = expandableNotificationRow;
            notificationStackScrollLayout.updateLaunchedNotificationClipPath();
            notificationStackScrollLayout.requestChildrenUpdate();
        }

        private NotificationListContainerImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum NotificationPanelEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        DISMISS_ALL_NOTIFICATIONS_PANEL(312),
        DISMISS_SILENT_NOTIFICATIONS_PANEL(314);

        private final int mId;

        NotificationPanelEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TouchHandler implements Gefingerpoken {
        public final SecPanelLogger mPanelLogger = (SecPanelLogger) Dependency.get(SecPanelLogger.class);
        public final SecTouchLogHelper mTouchLogHelper = new SecTouchLogHelper();

        public TouchHandler() {
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            this.mTouchLogHelper.printOnInterceptTouchEventLog(motionEvent, "StackScrollerController", "");
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            NotificationStackScrollLayoutController.m1712$$Nest$mupdateEventAvailability(notificationStackScrollLayoutController, motionEvent);
            if (!notificationStackScrollLayoutController.mIsStartFromContentsBound) {
                float rawX = motionEvent.getRawX();
                motionEvent.getRawY();
                if (!notificationStackScrollLayoutController.isInContentBounds(rawX)) {
                    return false;
                }
            }
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            if (motionEvent.getAction() == 0) {
                notificationStackScrollLayout.mExpandedInThisMotion = false;
                notificationStackScrollLayout.mOnlyScrollingInThisMotion = !notificationStackScrollLayout.mScroller.isFinished();
                notificationStackScrollLayout.mDisallowScrollingInThisMotion = false;
                notificationStackScrollLayout.mDisallowDismissInThisMotion = false;
                notificationStackScrollLayout.mTouchIsClick = true;
                notificationStackScrollLayout.mInitialTouchX = motionEvent.getX();
                notificationStackScrollLayout.mInitialTouchY = motionEvent.getY();
            }
            notificationStackScrollLayout.handleEmptySpaceClick(motionEvent);
            NotificationGutsManager notificationGutsManager = notificationStackScrollLayoutController.mNotificationGutsManager;
            NotificationGuts notificationGuts = notificationGutsManager.mNotificationGutsExposed;
            boolean onInterceptTouchEvent = notificationStackScrollLayoutController.mLongPressedView != null ? notificationStackScrollLayoutController.mSwipeHelper.onInterceptTouchEvent(motionEvent) : false;
            boolean onInterceptTouchEvent2 = (notificationStackScrollLayoutController.mLongPressedView != null || notificationStackScrollLayoutController.mSwipeHelper.mIsSwiping || notificationStackScrollLayout.mOnlyScrollingInThisMotion || notificationGuts != null) ? false : notificationStackScrollLayout.mExpandHelper.onInterceptTouchEvent(motionEvent);
            boolean onInterceptTouchEventScroll = (notificationStackScrollLayoutController.mLongPressedView != null || notificationStackScrollLayoutController.mSwipeHelper.mIsSwiping || notificationStackScrollLayout.mExpandingNotification) ? false : notificationStackScrollLayout.onInterceptTouchEventScroll(motionEvent);
            boolean onInterceptTouchEvent3 = (notificationStackScrollLayoutController.mLongPressedView != null || notificationStackScrollLayout.mIsBeingDragged || notificationStackScrollLayout.mExpandingNotification || notificationStackScrollLayout.mExpandedInThisMotion || notificationStackScrollLayout.mOnlyScrollingInThisMotion || notificationStackScrollLayout.mDisallowDismissInThisMotion) ? false : notificationStackScrollLayoutController.mSwipeHelper.onInterceptTouchEvent(motionEvent);
            boolean z = motionEvent.getActionMasked() == 1;
            if (!NotificationSwipeHelper.isTouchInView(notificationGuts, motionEvent) && z && !onInterceptTouchEvent3 && !onInterceptTouchEvent2 && !onInterceptTouchEventScroll) {
                notificationStackScrollLayout.mCheckForLeavebehind = false;
                notificationGutsManager.closeAndSaveGuts(true, false, false, false);
            }
            if (motionEvent.getActionMasked() == 1) {
                notificationStackScrollLayout.mCheckForLeavebehind = true;
            }
            InteractionJankMonitor interactionJankMonitor = notificationStackScrollLayoutController.mJankMonitor;
            if (interactionJankMonitor != null && onInterceptTouchEventScroll && motionEvent.getActionMasked() != 0) {
                interactionJankMonitor.begin(notificationStackScrollLayout, 2);
            }
            return onInterceptTouchEvent3 || onInterceptTouchEventScroll || onInterceptTouchEvent2 || onInterceptTouchEvent;
        }

        @Override // com.android.systemui.Gefingerpoken
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z;
            this.mTouchLogHelper.printOnTouchEventLog(motionEvent, "StackScrollerController", "");
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            if (!notificationStackScrollLayoutController.mIsStartFromContentsBound) {
                float rawX = motionEvent.getRawX();
                motionEvent.getRawY();
                if (!notificationStackScrollLayoutController.isInContentBounds(rawX)) {
                    return false;
                }
            }
            NotificationGuts notificationGuts = notificationStackScrollLayoutController.mNotificationGutsManager.mNotificationGutsExposed;
            boolean z2 = motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.handleEmptySpaceClick(motionEvent);
            boolean onTouchEvent = (notificationGuts == null || notificationStackScrollLayoutController.mLongPressedView == null) ? false : notificationStackScrollLayoutController.mSwipeHelper.onTouchEvent(motionEvent);
            boolean z3 = notificationStackScrollLayout.mOnlyScrollingInThisMotion;
            boolean z4 = notificationStackScrollLayout.mExpandingNotification;
            if (notificationStackScrollLayoutController.mLongPressedView == null && notificationStackScrollLayout.mIsExpanded && !notificationStackScrollLayoutController.mSwipeHelper.mIsSwiping && !z3 && notificationGuts == null) {
                ExpandHelper expandHelper = notificationStackScrollLayout.mExpandHelper;
                if (z2) {
                    expandHelper.mOnlyMovements = false;
                }
                z = expandHelper.onTouchEvent(motionEvent);
                boolean z5 = notificationStackScrollLayout.mExpandingNotification;
                if (notificationStackScrollLayout.mExpandedInThisMotion && !z5 && z4 && !notificationStackScrollLayout.mDisallowScrollingInThisMotion) {
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.setAction(0);
                    notificationStackScrollLayout.onScrollTouch(obtain);
                    obtain.recycle();
                }
                z4 = z5;
            } else {
                z = false;
            }
            boolean onScrollTouch = (notificationStackScrollLayoutController.mLongPressedView != null || !notificationStackScrollLayout.mIsExpanded || notificationStackScrollLayoutController.mSwipeHelper.mIsSwiping || z4 || notificationStackScrollLayout.mDisallowScrollingInThisMotion) ? false : notificationStackScrollLayout.onScrollTouch(motionEvent);
            boolean onTouchEvent2 = (notificationStackScrollLayoutController.mLongPressedView != null || notificationStackScrollLayout.mIsBeingDragged || z4 || notificationStackScrollLayout.mExpandedInThisMotion || z3 || notificationStackScrollLayout.mDisallowDismissInThisMotion) ? false : notificationStackScrollLayoutController.mSwipeHelper.onTouchEvent(motionEvent);
            if (notificationGuts != null && !NotificationSwipeHelper.isTouchInView(notificationGuts, motionEvent)) {
                NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
                if ((gutsContent instanceof NotificationSnooze) && ((((NotificationSnooze) gutsContent).mExpanded && z2) || (!onTouchEvent2 && onScrollTouch))) {
                    notificationStackScrollLayoutController.checkSnoozeLeavebehind();
                }
            }
            if (motionEvent.getActionMasked() == 1) {
                if (!onTouchEvent2) {
                    notificationStackScrollLayoutController.mFalsingManager.isFalseTouch(11);
                }
                notificationStackScrollLayout.mCheckForLeavebehind = true;
            }
            NotificationStackScrollLayoutController.m1712$$Nest$mupdateEventAvailability(notificationStackScrollLayoutController, motionEvent);
            boolean z6 = notificationStackScrollLayout.mIsExpanded;
            SecPanelLoggerImpl secPanelLoggerImpl = (SecPanelLoggerImpl) this.mPanelLogger;
            if (((StatusBarStateControllerImpl) secPanelLoggerImpl.sysuiStatusBarStateController).mState != 1 && motionEvent.getAction() != 2) {
                StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("NSSL.onTouchEvent(), horizontalSwipeWantsIt:", onTouchEvent2, ", scrollerWantsIt:", onScrollTouch, ", expandWantsIt:");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, z, ", expandWantsIt:", onTouchEvent, ", isExpanded:");
                m69m.append(z6);
                secPanelLoggerImpl.writer.logPanel("NSSL_TOUCH", m69m.toString());
            }
            int actionMasked = motionEvent.getActionMasked();
            InteractionJankMonitor interactionJankMonitor = notificationStackScrollLayoutController.mJankMonitor;
            if (interactionJankMonitor == null) {
                Log.w("StackScrollerController", "traceJankOnTouchEvent, mJankMonitor is null");
            } else if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked == 3 && onScrollTouch) {
                        interactionJankMonitor.cancel(2);
                    }
                } else if (onScrollTouch && !notificationStackScrollLayout.mFlingAfterUpEvent) {
                    interactionJankMonitor.end(2);
                }
            } else if (onScrollTouch) {
                interactionJankMonitor.begin(notificationStackScrollLayout, 2);
            }
            return onTouchEvent2 || onScrollTouch || z || onTouchEvent;
        }
    }

    /* renamed from: -$$Nest$mupdateEventAvailability, reason: not valid java name */
    public static void m1712$$Nest$mupdateEventAvailability(NotificationStackScrollLayoutController notificationStackScrollLayoutController, MotionEvent motionEvent) {
        notificationStackScrollLayoutController.getClass();
        int action = motionEvent.getAction();
        if (action == 0) {
            float rawX = motionEvent.getRawX();
            motionEvent.getY();
            notificationStackScrollLayoutController.mIsStartFromContentsBound = notificationStackScrollLayoutController.isInContentBounds(rawX);
        } else if (action == 1) {
            notificationStackScrollLayoutController.mIsStartFromContentsBound = false;
        } else {
            if (action != 3) {
                return;
            }
            notificationStackScrollLayoutController.mIsStartFromContentsBound = false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v5, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v10, types: [com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin$OnMenuEventListener, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$8] */
    /* JADX WARN: Type inference failed for: r12v11, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$10, com.android.systemui.statusbar.policy.OnHeadsUpChangedListener] */
    /* JADX WARN: Type inference failed for: r12v12, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$11] */
    /* JADX WARN: Type inference failed for: r12v13, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$12, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r12v3, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda0, java.util.function.IntConsumer] */
    /* JADX WARN: Type inference failed for: r12v4, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$4, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v9, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7, java.lang.Object] */
    public NotificationStackScrollLayoutController(final NotificationStackScrollLayout notificationStackScrollLayout, boolean z, NotificationGutsManager notificationGutsManager, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManagerPhone headsUpManagerPhone, NotificationRoundnessManager notificationRoundnessManager, TunerService tunerService, DeviceProvisionedController deviceProvisionedController, DynamicPrivacyController dynamicPrivacyController, ConfigurationController configurationController, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardMediaController keyguardMediaController, KeyguardBypassController keyguardBypassController, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, Optional<NotificationListViewModel> optional, MetricsLogger metricsLogger, DumpManager dumpManager, FalsingCollector falsingCollector, FalsingManager falsingManager, Resources resources, NotificationSwipeHelper.Builder builder, CentralSurfaces centralSurfaces, ScrimController scrimController, GroupExpansionManager groupExpansionManager, SectionHeaderController sectionHeaderController, NotifPipeline notifPipeline, NotifPipelineFlags notifPipelineFlags, NotifCollection notifCollection, LockscreenShadeTransitionController lockscreenShadeTransitionController, UiEventLogger uiEventLogger, NotificationRemoteInputManager notificationRemoteInputManager, VisibilityLocationProviderDelegator visibilityLocationProviderDelegator, SeenNotificationsProvider seenNotificationsProvider, ShadeController shadeController, InteractionJankMonitor interactionJankMonitor, StackStateLogger stackStateLogger, NotificationStackScrollLogger notificationStackScrollLogger, NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationIconAreaController notificationIconAreaController, FeatureFlags featureFlags, NotificationTargetsHelper notificationTargetsHelper, SecureSettings secureSettings, NotificationDismissibilityProvider notificationDismissibilityProvider, ActivityStarter activityStarter, NotificationShelfManager notificationShelfManager, LockscreenNotificationManager lockscreenNotificationManager) {
        View.OnAttachStateChangeListener onAttachStateChangeListener;
        int i = 0;
        this.mNotificationListContainer = new NotificationListContainerImpl(this, i);
        this.mNotifStackController = new NotifStackControllerImpl(this, i);
        ?? r12 = new IntConsumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                if (i2 == 4 && notificationStackScrollLayoutController.mHasDelayedForceLayout) {
                    Log.d("StackScrollerController", "do delayed stackScroller forceLayout");
                    notificationStackScrollLayout2.forceLayout();
                }
                notificationStackScrollLayoutController.mHasDelayedForceLayout = false;
                notificationStackScrollLayout2.removeCallbacks(notificationStackScrollLayoutController.mForceLayoutTimeOutRunnable);
            }
        };
        this.mKeyguardVisibilityListener = r12;
        this.mHasDelayedForceLayout = false;
        this.mForceLayoutTimeOutRunnable = new RunnableC29521();
        C29602 c29602 = new C29602();
        View.OnAttachStateChangeListener onAttachStateChangeListener2 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.3
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).addCallback(notificationStackScrollLayoutController.mConfigurationListener);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
                ((ZenModeControllerImpl) notificationStackScrollLayoutController2.mZenModeController).addCallback(notificationStackScrollLayoutController2.mZenModeControllerCallback);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = NotificationStackScrollLayoutController.this;
                int i2 = ((StatusBarStateControllerImpl) notificationStackScrollLayoutController3.mStatusBarStateController).mState;
                if (i2 != notificationStackScrollLayoutController3.mBarState) {
                    notificationStackScrollLayoutController3.mStateListener.onStateChanged(i2);
                    NotificationStackScrollLayoutController.this.mStateListener.onStatePostChange();
                }
                NotificationShelfManager notificationShelfManager2 = NotificationStackScrollLayoutController.this.mShelfManager;
                ((ConfigurationControllerImpl) notificationShelfManager2.configurationController).addCallback(notificationShelfManager2);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController4 = NotificationStackScrollLayoutController.this;
                SysuiStatusBarStateController sysuiStatusBarStateController2 = notificationStackScrollLayoutController4.mStatusBarStateController;
                C29646 c29646 = notificationStackScrollLayoutController4.mStateListener;
                StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController2;
                synchronized (statusBarStateControllerImpl.mListeners) {
                    statusBarStateControllerImpl.addListenerInternalLocked(c29646, 2);
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).removeCallback(notificationStackScrollLayoutController.mConfigurationListener);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
                ((ZenModeControllerImpl) notificationStackScrollLayoutController2.mZenModeController).removeCallback(notificationStackScrollLayoutController2.mZenModeControllerCallback);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = NotificationStackScrollLayoutController.this;
                ((StatusBarStateControllerImpl) notificationStackScrollLayoutController3.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) notificationStackScrollLayoutController3.mStateListener);
            }
        };
        this.mOnAttachStateChangeListener = onAttachStateChangeListener2;
        ?? r122 = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.4
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onDeviceProvisionedChanged() {
                updateCurrentUserIsSetup();
            }

            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                updateCurrentUserIsSetup();
            }

            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSwitched() {
                updateCurrentUserIsSetup();
            }

            public final void updateCurrentUserIsSetup() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                boolean isCurrentUserSetup = ((DeviceProvisionedControllerImpl) notificationStackScrollLayoutController.mDeviceProvisionedController).isCurrentUserSetup();
                if (notificationStackScrollLayout2.mIsCurrentUserSetup != isCurrentUserSetup) {
                    notificationStackScrollLayout2.mIsCurrentUserSetup = isCurrentUserSetup;
                    notificationStackScrollLayout2.updateFooter();
                }
            }
        };
        this.mDeviceProvisionedListener = r122;
        ?? r10 = new DynamicPrivacyController.Listener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.DynamicPrivacyController.Listener
            public final void onDynamicPrivacyChanged() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                if (notificationStackScrollLayout2.mIsExpanded) {
                    notificationStackScrollLayout2.mAnimateBottomOnLayout = true;
                }
                notificationStackScrollLayout2.post(new RunnableC2943xbae1b0c1(notificationStackScrollLayoutController, 0));
            }
        };
        this.mDynamicPrivacyControllerListener = r10;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.5
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                NotificationStackScrollLayoutController.this.mNotificationStackSizeCalculator.updateResources();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                StringBuilder sb = new StringBuilder("updateShowEmptyShadeView: CurrentState: ");
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                sb.append(StatusBarState.toString(((StatusBarStateControllerImpl) notificationStackScrollLayoutController.mStatusBarStateController).mUpcomingState));
                sb.append(" isQsFullScreen: ");
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                sb.append(notificationStackScrollLayout2.mQsFullScreen);
                sb.append(" VisibleNotificationCount: ");
                sb.append(notificationStackScrollLayoutController.mNotifStats.numActiveNotifs);
                Log.d("StackScrollerController", sb.toString());
                notificationStackScrollLayoutController.updateShowEmptyShadeView();
                notificationStackScrollLayout2.inflateFooterView();
                notificationStackScrollLayout2.inflateEmptyShadeView();
                notificationStackScrollLayout2.updateFooter();
                notificationStackScrollLayout2.mSectionsManager.reinflateViews();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                int dimensionPixelSize = notificationStackScrollLayout2.getResources().getDimensionPixelSize(R.dimen.notification_corner_radius);
                if (notificationStackScrollLayout2.mCornerRadius != dimensionPixelSize) {
                    notificationStackScrollLayout2.mCornerRadius = dimensionPixelSize;
                    notificationStackScrollLayout2.invalidate();
                }
                NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout3.updateBgColor();
                notificationStackScrollLayout3.updateDecorViews();
                notificationStackScrollLayout3.inflateFooterView();
                notificationStackScrollLayout3.inflateEmptyShadeView();
                notificationStackScrollLayout3.updateFooter();
                notificationStackScrollLayout3.mSectionsManager.reinflateViews();
                notificationStackScrollLayoutController.updateShowEmptyShadeView();
                notificationStackScrollLayoutController.updateFooter();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.mView.updateBgColor();
                notificationStackScrollLayoutController.mView.updateDecorViews();
                notificationStackScrollLayoutController.mView.updateSectionColor();
            }
        };
        NotifStats.Companion.getClass();
        this.mNotifStats = NotifStats.empty;
        this.mStateListener = new C29646();
        ?? r123 = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.7
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onUserChanged(int i2) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.mView.updateSensitiveness(false, ((NotificationLockscreenUserManagerImpl) notificationStackScrollLayoutController.mLockscreenUserManager).isAnyProfilePublicMode());
                notificationStackScrollLayoutController.mHistoryEnabled = null;
                notificationStackScrollLayoutController.updateFooter();
            }
        };
        this.mLockscreenUserChangeListener = r123;
        ?? r124 = new NotificationMenuRowPlugin.OnMenuEventListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.8
            @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.OnMenuEventListener
            public final void onMenuClicked(View view, int i2, int i3, NotificationMenuRowPlugin.MenuItem menuItem) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                if (notificationStackScrollLayoutController.mAllowLongPress) {
                    if (view instanceof ExpandableNotificationRow) {
                        notificationStackScrollLayoutController.mMetricsLogger.write(((ExpandableNotificationRow) view).mEntry.mSbn.getLogMaker().setCategory(333).setType(4));
                    }
                    notificationStackScrollLayoutController.mNotificationGutsManager.openGuts(view, i2, i3, menuItem);
                }
            }

            @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.OnMenuEventListener
            public final void onMenuReset(View view) {
                NotificationSwipeHelper notificationSwipeHelper = NotificationStackScrollLayoutController.this.mSwipeHelper;
                View view2 = notificationSwipeHelper.mTranslatingParentView;
                if (view2 == null || view != view2) {
                    return;
                }
                notificationSwipeHelper.mMenuExposedView = null;
                notificationSwipeHelper.setTranslatingParentView(null);
            }

            @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.OnMenuEventListener
            public final void onMenuShown(View view) {
                if (view instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                    notificationStackScrollLayoutController.mMetricsLogger.write(expandableNotificationRow.mEntry.mSbn.getLogMaker().setCategory(CustomDeviceManager.DESTINATION_ADDRESS).setType(4));
                    NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayoutController.mSwipeHelper;
                    notificationSwipeHelper.mMenuExposedView = notificationSwipeHelper.mTranslatingParentView;
                    C29679 c29679 = (C29679) notificationSwipeHelper.mCallback;
                    NotificationStackScrollLayoutController.this.mFalsingCollector.getClass();
                    Handler handler = notificationSwipeHelper.getHandler();
                    if (NotificationStackScrollLayoutController.this.mView.onKeyguard()) {
                        handler.removeCallbacks(notificationSwipeHelper.getFalsingCheck());
                        handler.postDelayed(notificationSwipeHelper.getFalsingCheck(), 4000L);
                    }
                    NotificationGutsManager notificationGutsManager2 = notificationStackScrollLayoutController.mNotificationGutsManager;
                    notificationGutsManager2.closeAndSaveGuts(true, false, false, false);
                    NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
                    if (notificationMenuRowPlugin.shouldShowGutsOnSnapOpen()) {
                        NotificationMenuRowPlugin.MenuItem menuItemToExposeOnSnap = notificationMenuRowPlugin.menuItemToExposeOnSnap();
                        if (menuItemToExposeOnSnap != null) {
                            Point revealAnimationOrigin = notificationMenuRowPlugin.getRevealAnimationOrigin();
                            notificationGutsManager2.openGuts(view, revealAnimationOrigin.x, revealAnimationOrigin.y, menuItemToExposeOnSnap);
                        } else {
                            Log.e("StackScrollerController", "Provider has shouldShowGutsOnSnapOpen, but provided no menu item in menuItemtoExposeOnSnap. Skipping.");
                        }
                        notificationStackScrollLayoutController.mSwipeHelper.resetExposedMenuView(false, true);
                    }
                }
            }
        };
        this.mMenuEventListener = r124;
        C29679 c29679 = new C29679();
        this.mNotificationCallback = c29679;
        ?? r125 = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.10
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z2) {
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
                notificationStackScrollLayout2.mInHeadsUpPinnedMode = z2;
                notificationStackScrollLayout2.updateClipping();
            }

            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z2) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                long count = notificationStackScrollLayoutController.mHeadsUpManager.getAllEntries().count();
                HeadsUpManager.HeadsUpEntry topHeadsUpEntry = notificationStackScrollLayoutController.mHeadsUpManager.getTopHeadsUpEntry();
                NotificationEntry notificationEntry2 = topHeadsUpEntry != null ? topHeadsUpEntry.mEntry : null;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout2.mNumHeadsUp = count;
                notificationStackScrollLayout2.mAmbientState.getClass();
                notificationStackScrollLayout2.mTopHeadsUpEntry = notificationEntry2;
                notificationStackScrollLayout2.generateHeadsUpAnimation(notificationEntry.row, z2);
            }
        };
        this.mOnHeadsUpChangedListener = r125;
        this.mZenModeControllerCallback = new ZenModeController.Callback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.11
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i2) {
                NotificationStackScrollLayoutController.this.updateShowEmptyShadeView();
            }
        };
        ?? r126 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.12
            /* JADX WARN: Code restructure failed: missing block: B:4:0x001d, code lost:
            
                if (r0.mItemLists.get("wallpapertheme_state").getIntValue() == 1) goto L8;
             */
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onChanged(Uri uri) {
                SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
                settingsHelper.getClass();
                boolean z2 = CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER;
                if (z2 && uri.equals(Settings.Global.getUriFor("notification_apply_wallpaper_theme"))) {
                    Log.d("StackScrollerController", "apply notification icon color to".concat(settingsHelper.isApplyWallpaperThemeToNotif() ? " WALLPAPER COLOR" : " SMALL ICON COLOR"));
                    NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.get(NotificationColorPicker.class);
                    NotifCollection notifCollection2 = NotificationStackScrollLayoutController.this.mNotifCollection;
                    notifCollection2.getClass();
                    Assert.isMainThread();
                    Iterator it = notifCollection2.mReadOnlyNotificationSet.iterator();
                    while (it.hasNext()) {
                        ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
                        if (expandableNotificationRow != null) {
                            notificationColorPicker.updateAllTextViewColors(expandableNotificationRow, expandableNotificationRow.mDimmed);
                        }
                    }
                }
            }
        };
        this.mWallpaperThemeCallback = r126;
        this.mKeyguardUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.16
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i2, BiometricSourceType biometricSourceType, boolean z2) {
                if (biometricSourceType != BiometricSourceType.FACE) {
                    if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getClass();
                        return;
                    }
                    return;
                }
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                if (!((CentralSurfacesImpl) notificationStackScrollLayoutController.mCentralSurfaces).mBiometricUnlockController.mKeyguardBypassController.getLockStayEnabled()) {
                    ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getClass();
                    return;
                }
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout2.mEverythingNeedsAnimation = true;
                notificationStackScrollLayout2.mNeedsAnimation = true;
                notificationStackScrollLayout2.updateContentHeight();
                notificationStackScrollLayout2.requestChildrenUpdate();
            }
        };
        this.mIsStartFromContentsBound = false;
        this.mView = notificationStackScrollLayout;
        this.mAllowLongPress = z;
        this.mNotificationGutsManager = notificationGutsManager;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mNotificationRoundnessManager = notificationRoundnessManager;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mConfigurationController = configurationController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mZenModeController = zenModeController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mMetricsLogger = metricsLogger;
        this.mFalsingCollector = falsingCollector;
        this.mFalsingManager = falsingManager;
        this.mCentralSurfaces = centralSurfaces;
        this.mJankMonitor = interactionJankMonitor;
        this.mNotificationStackSizeCalculator = notificationStackSizeCalculator;
        this.mGroupExpansionManager = groupExpansionManager;
        this.mNotifCollection = notifCollection;
        this.mUiEventLogger = uiEventLogger;
        this.mSeenNotificationsProvider = seenNotificationsProvider;
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mNotifIconAreaController = notificationIconAreaController;
        this.mFeatureFlags = featureFlags;
        this.mNotificationTargetsHelper = notificationTargetsHelper;
        this.mSecureSettings = secureSettings;
        this.mDismissibilityProvider = notificationDismissibilityProvider;
        this.mActivityStarter = activityStarter;
        this.mShelfManager = notificationShelfManager;
        ((NotificationSectionsManager) Dependency.get(NotificationSectionsManager.class)).sectionStateProvider = this;
        notificationStackSizeCalculator.updateResources();
        notificationStackScrollLayout.mStateAnimator.mLogger = stackStateLogger;
        notificationStackScrollLayout.mController = this;
        notificationRoundnessManager.mAnimatedChildren = notificationStackScrollLayout.mChildrenToAddAnimated;
        notificationStackScrollLayout.mLogger = notificationStackScrollLogger;
        notificationStackScrollLayout.mTouchHandler = new TouchHandler();
        notificationStackScrollLayout.mCentralSurfaces = centralSurfaces;
        notificationStackScrollLayout.mActivityStarter = activityStarter;
        notificationStackScrollLayout.mClearAllAnimationListener = new C2944xbae1b0c2(this);
        notificationStackScrollLayout.mClearAllListener = new C2944xbae1b0c2(this);
        notificationStackScrollLayout.mIsRemoteInputActive = notificationRemoteInputManager.isRemoteInputActive();
        notificationStackScrollLayout.updateFooter();
        notificationRemoteInputManager.addControllerCallback(new RemoteInputController.Callback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.13
            @Override // com.android.systemui.statusbar.RemoteInputController.Callback
            public final void onRemoteInputActive(boolean z2) {
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
                notificationStackScrollLayout2.mIsRemoteInputActive = z2;
                notificationStackScrollLayout2.updateFooter();
            }
        });
        notificationStackScrollLayout.mShadeController = shadeController;
        notificationStackScrollLayout.mShelfManager = notificationShelfManager;
        dumpManager.registerDumpable(notificationStackScrollLayout);
        final int i2 = 0;
        keyguardBypassController.registerOnBypassStateChangedListener(new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda7
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z2) {
                int i3 = i2;
                Object obj = this;
                switch (i3) {
                    case 0:
                        ((NotificationStackScrollLayoutController) obj).mNotificationRoundnessManager.mRoundForPulsingViews = !z2;
                        break;
                    default:
                        ((NotificationStackScrollLayout) obj).mKeyguardBypassEnabled = z2;
                        break;
                }
            }
        });
        notificationRoundnessManager.mRoundForPulsingViews = !keyguardBypassController.getBypassEnabled();
        builder.mNotificationCallback = c29679;
        builder.mOnMenuEventListener = r124;
        NotificationSwipeHelper notificationSwipeHelper = new NotificationSwipeHelper(builder.mResources, builder.mViewConfiguration, builder.mFalsingManager, builder.mFeatureFlags, builder.mNotificationCallback, builder.mOnMenuEventListener, builder.mNotificationRoundnessManager);
        builder.mDumpManager.registerDumpable(notificationSwipeHelper);
        this.mSwipeHelper = notificationSwipeHelper;
        notifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.14
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry) {
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
                notificationStackScrollLayout2.getClass();
                if (!notificationEntry.rowExists() || notificationEntry.mSbn.isClearable()) {
                    return;
                }
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                boolean z2 = notificationStackScrollLayout2.mIsExpanded || NotificationStackScrollLayout.isPinnedHeadsUp(expandableNotificationRow);
                NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
                if (notificationMenuRowPlugin != null) {
                    notificationStackScrollLayout2.mSwipeHelper.snapChildIfNeeded(expandableNotificationRow, notificationMenuRowPlugin.isMenuVisible() ? expandableNotificationRow.getTranslation() : 0.0f, z2);
                }
            }
        });
        notificationStackScrollLayout.initView(notificationStackScrollLayout.getContext(), this.mSwipeHelper, notificationStackSizeCalculator);
        notificationStackScrollLayout.mKeyguardBypassEnabled = keyguardBypassController.getBypassEnabled();
        final int i3 = 1;
        keyguardBypassController.registerOnBypassStateChangedListener(new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda7
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z2) {
                int i32 = i3;
                Object obj = notificationStackScrollLayout;
                switch (i32) {
                    case 0:
                        ((NotificationStackScrollLayoutController) obj).mNotificationRoundnessManager.mRoundForPulsingViews = !z2;
                        break;
                    default:
                        ((NotificationStackScrollLayout) obj).mKeyguardBypassEnabled = z2;
                        break;
                }
            }
        });
        headsUpManagerPhone.addListener(r125);
        headsUpManagerPhone.mAnimationStateHandler = new C2944xbae1b0c2(notificationStackScrollLayout);
        dynamicPrivacyController.mListeners.add(r10);
        RunnableC2943xbae1b0c1 runnableC2943xbae1b0c1 = new RunnableC2943xbae1b0c1(notificationStackScrollLayout, 1);
        ScrimView scrimView = scrimController.mScrimBehind;
        if (scrimView == null) {
            scrimController.mScrimBehindChangeRunnable = runnableC2943xbae1b0c1;
        } else {
            Executor executor = scrimController.mMainExecutor;
            scrimView.mChangeRunnable = runnableC2943xbae1b0c1;
            scrimView.mChangeRunnableExecutor = executor;
        }
        lockscreenShadeTransitionController.nsslController = this;
        lockscreenShadeTransitionController.touchHelper.expandCallback = notificationStackScrollLayout.mExpandHelperCallback;
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager;
        ((ArrayList) notificationLockscreenUserManagerImpl.mListeners).add(r123);
        notificationLockscreenUserManagerImpl.mNotifStateChangedListeners.addIfAbsent(new NotificationLockscreenUserManager.NotificationStateChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda9
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.NotificationStateChangedListener
            public final void onNotificationStateChanged() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.mView.updateSensitiveness(false, ((NotificationLockscreenUserManagerImpl) notificationStackScrollLayoutController.mLockscreenUserManager).isAnyProfilePublicMode());
            }
        });
        visibilityLocationProviderDelegator.delegate = new VisibilityLocationProvider() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda10
            @Override // com.android.systemui.statusbar.notification.VisibilityLocationProvider
            public final boolean isInVisibleLocation(NotificationEntry notificationEntry) {
                NotificationStackScrollLayoutController.this.getClass();
                return NotificationStackScrollLayoutController.isInVisibleLocation(notificationEntry);
            }
        };
        tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda4
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.getClass();
                str.getClass();
                if (str.equals("high_priority")) {
                    notificationStackScrollLayoutController.mView.mHighPriorityBeforeSpeedBump = "1".equals(str2);
                } else if (str.equals("notification_history_enabled")) {
                    notificationStackScrollLayoutController.mHistoryEnabled = null;
                    notificationStackScrollLayoutController.updateFooter();
                }
            }
        }, "high_priority", "notification_history_enabled");
        keyguardMediaController.getClass();
        ((DeviceProvisionedControllerImpl) deviceProvisionedController).addCallback(r122);
        r122.updateCurrentUserIsSetup();
        if (notificationStackScrollLayout.isAttachedToWindow()) {
            onAttachStateChangeListener = onAttachStateChangeListener2;
            onAttachStateChangeListener.onViewAttachedToWindow(notificationStackScrollLayout);
        } else {
            onAttachStateChangeListener = onAttachStateChangeListener2;
        }
        notificationStackScrollLayout.addOnAttachStateChangeListener(onAttachStateChangeListener);
        ViewOnClickListenerC2949xbae1b0c7 viewOnClickListenerC2949xbae1b0c7 = new ViewOnClickListenerC2949xbae1b0c7(this, 1);
        SectionHeaderNodeControllerImpl sectionHeaderNodeControllerImpl = (SectionHeaderNodeControllerImpl) sectionHeaderController;
        sectionHeaderNodeControllerImpl.clearAllClickListener = viewOnClickListenerC2949xbae1b0c7;
        SectionHeaderView sectionHeaderView = sectionHeaderNodeControllerImpl._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.mOnClearClickListener = viewOnClickListenerC2949xbae1b0c7;
            sectionHeaderView.mClearAllButton.setOnClickListener(viewOnClickListenerC2949xbae1b0c7);
        }
        ((HashSet) ((GroupExpansionManagerImpl) groupExpansionManager).mOnGroupChangeListeners).add(new C2946xbae1b0c4(this));
        optional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                FalsingManager falsingManager2 = notificationStackScrollLayoutController.mFalsingManager;
                int i4 = NotificationListViewBinder.$r8$clinit;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                NotificationShelf notificationShelf = (NotificationShelf) LayoutInflater.from(notificationStackScrollLayout2.getContext()).inflate(R.layout.status_bar_notification_shelf, (ViewGroup) notificationStackScrollLayout2, false);
                NotificationShelfViewBinder notificationShelfViewBinder = NotificationShelfViewBinder.INSTANCE;
                NotificationShelfViewModel notificationShelfViewModel = ((NotificationListViewModel) obj).shelf;
                notificationShelfViewBinder.getClass();
                NotificationShelfViewBinder.bind(notificationShelf, notificationShelfViewModel, falsingManager2, notificationStackScrollLayoutController.mNotifIconAreaController);
                FeatureFlags featureFlags2 = notificationStackScrollLayout2.mAmbientState.mFeatureFlags;
                NotificationShelfController.checkRefactorFlagEnabled();
            }
        });
        ((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).mNotificationStackScrollLayout = notificationStackScrollLayout;
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).registerCallback(this.mKeyguardUpdateCallback);
        }
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(r126, Settings.Global.getUriFor("notification_apply_wallpaper_theme"));
        ((KeyguardVisibilityMonitor) Dependency.get(KeyguardVisibilityMonitor.class)).addVisibilityChangedListener(r12);
        ArrayList arrayList = (ArrayList) ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).foldOpenModeListeners;
        if (arrayList.contains(c29602)) {
            return;
        }
        arrayList.add(c29602);
    }

    public static boolean isInVisibleLocation(NotificationEntry notificationEntry) {
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        return (expandableNotificationRow == null || (expandableNotificationRow.mViewState.location & 5) == 0 || expandableNotificationRow.getVisibility() != 0) ? false : true;
    }

    public final void checkSnoozeLeavebehind() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        if (notificationStackScrollLayout.mCheckForLeavebehind) {
            this.mNotificationGutsManager.closeAndSaveGuts(true, false, false, false);
            notificationStackScrollLayout.mCheckForLeavebehind = false;
        }
    }

    public final int getHeight() {
        return this.mView.getHeight();
    }

    public final int getNotGoneChildCount() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        int childCount = notificationStackScrollLayout.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            ExpandableView childAtIndex = notificationStackScrollLayout.getChildAtIndex(i2);
            if (childAtIndex.getVisibility() != 8 && !childAtIndex.mWillBeGone && childAtIndex != notificationStackScrollLayout.mShelf) {
                i++;
            }
        }
        return i;
    }

    public final boolean hasNotifications(int i, boolean z) {
        boolean z2 = z ? this.mNotifStats.hasClearableAlertingNotifs : this.mNotifStats.hasNonClearableAlertingNotifs;
        boolean z3 = z ? this.mNotifStats.hasClearableSilentNotifs : this.mNotifStats.hasNonClearableSilentNotifs;
        if (i == 0) {
            return z3 || z2;
        }
        if (i == 1) {
            return z2;
        }
        if (i == 2) {
            return z3;
        }
        throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Bad selection: ", i));
    }

    public final boolean isAddOrRemoveAnimationPending() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        if (notificationStackScrollLayout != null) {
            return notificationStackScrollLayout.mNeedsAnimation && (!notificationStackScrollLayout.mChildrenToAddAnimated.isEmpty() || !notificationStackScrollLayout.mChildrenToRemoveAnimated.isEmpty());
        }
        return false;
    }

    public final boolean isHistoryEnabled() {
        Boolean bool = this.mHistoryEnabled;
        if (bool == null) {
            NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
            if (notificationStackScrollLayout == null || notificationStackScrollLayout.getContext() == null) {
                Log.wtf("StackScrollerController", "isHistoryEnabled failed to initialize its value");
                return false;
            }
            bool = Boolean.valueOf(this.mSecureSettings.getIntForUser(0, -2, "notification_history_enabled") == 1);
            this.mHistoryEnabled = bool;
        }
        return bool.booleanValue();
    }

    public final boolean isInContentBounds(float f) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        float f2 = notificationStackScrollLayout.mSidePaddings;
        float x = notificationStackScrollLayout.getX() + f2;
        return x < f && f < (((float) notificationStackScrollLayout.getWidth()) + x) - (f2 * 2.0f);
    }

    public final void setOverScrollAmount(int i) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        notificationStackScrollLayout.mExtraTopInsetForFullShadeTransition = i;
        notificationStackScrollLayout.updateStackPosition(false);
        notificationStackScrollLayout.requestChildrenUpdate();
    }

    public final void setQsExpansionFraction(float f) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        float f2 = notificationStackScrollLayout.mQsExpansionFraction;
        boolean z = true;
        boolean z2 = f2 != f && (f2 == 1.0f || f == 1.0f);
        notificationStackScrollLayout.mQsExpansionFraction = f;
        notificationStackScrollLayout.updateUseRoundedRectClipping();
        int i = notificationStackScrollLayout.mOwnScrollY;
        if (i > 0) {
            notificationStackScrollLayout.setOwnScrollY((int) MathUtils.lerp(i, 0, notificationStackScrollLayout.mQsExpansionFraction));
        }
        if (z2) {
            notificationStackScrollLayout.updateFooter();
        }
        FullExpansionPanelNotiAlphaController fullExpansionPanelNotiAlphaController = notificationStackScrollLayout.mFullExpansionPanelNotiAlphaController;
        float f3 = notificationStackScrollLayout.mQsExpansionFraction;
        if (!fullExpansionPanelNotiAlphaController.mKeyguardTouchAnimator.isViRunning() && !((NotificationIconTransitionController) Dependency.get(NotificationIconTransitionController.class)).misTransformAnimating && !((KeyguardEditModeControllerImpl) fullExpansionPanelNotiAlphaController.mKeyguardEditModeController).getVIRunning()) {
            z = false;
        }
        if (z) {
            return;
        }
        if (fullExpansionPanelNotiAlphaController.mStackScrollerOverscrolling) {
            fullExpansionPanelNotiAlphaController.mStackScrollerAlphaAnimator.setPosition((NotiRune.NOTI_STYLE_TABLET_BG && fullExpansionPanelNotiAlphaController.mStackScrollLayout.mQsExpandedImmediate) ? 1.0f : 0.0f);
        } else {
            fullExpansionPanelNotiAlphaController.mStackScrollerAlphaAnimator.setPosition((NotiRune.NOTI_STYLE_TABLET_BG && fullExpansionPanelNotiAlphaController.mStackScrollLayout.mQsExpandedImmediate) ? 1.0f : f3);
        }
    }

    public final void updateFooter() {
        Trace.beginSection("NSSLC.updateFooter");
        this.mView.updateFooter();
        Trace.endSection();
    }

    public final void updateImportantForAccessibility() {
        int i = this.mNotifStats.numActiveNotifs;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        if (i == 0 && notificationStackScrollLayout.onKeyguard()) {
            notificationStackScrollLayout.setImportantForAccessibility(2);
        } else {
            notificationStackScrollLayout.setImportantForAccessibility(1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateShowEmptyShadeView() {
        boolean z;
        Trace.beginSection("NSSLC.updateShowEmptyShadeView");
        int i = this.mNotifStats.numActiveNotifs;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        if (i == 0 && !notificationStackScrollLayout.mQsFullScreen) {
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
            int i2 = statusBarStateControllerImpl.mState;
            int i3 = statusBarStateControllerImpl.mUpcomingState;
            if (!(i2 != i3 && i3 == 1) && !((CentralSurfacesImpl) this.mCentralSurfaces).mBouncerShowing && !this.mMediaPlayerVisible) {
                z = true;
                ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) this.mZenModeController;
                boolean z2 = zenModeControllerImpl.mZenMode == 0 && (zenModeControllerImpl.mConsolidatedNotificationPolicy.suppressedVisualEffects & 256) != 0;
                notificationStackScrollLayout.getClass();
                if (!NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
                    if (NotiRune.NOTI_STYLE_EMPTY_SHADE && z) {
                        notificationStackScrollLayout.updateEmptyShadeViewHeight();
                        notificationStackScrollLayout.mEmptyShadeView.mEndAlpha = 1.0f;
                    }
                    notificationStackScrollLayout.mEmptyShadeView.setVisible(z, notificationStackScrollLayout.mIsExpanded && notificationStackScrollLayout.mAnimationsEnabled);
                    if (z2) {
                        notificationStackScrollLayout.updateEmptyShadeView(R.string.dnd_suppressing_shade_text, 0, 0);
                    } else if (notificationStackScrollLayout.mHasFilteredOutSeenNotifications) {
                        notificationStackScrollLayout.updateEmptyShadeView(R.string.no_unseen_notif_text, R.string.unlock_to_see_notif_text, R.drawable.ic_friction_lock_closed);
                    } else {
                        notificationStackScrollLayout.updateEmptyShadeView(R.string.empty_shade_text, 0, 0);
                    }
                }
                Trace.endSection();
            }
        }
        z = false;
        ZenModeControllerImpl zenModeControllerImpl2 = (ZenModeControllerImpl) this.mZenModeController;
        if (zenModeControllerImpl2.mZenMode == 0) {
        }
        notificationStackScrollLayout.getClass();
        if (!NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
        }
        Trace.endSection();
    }
}
