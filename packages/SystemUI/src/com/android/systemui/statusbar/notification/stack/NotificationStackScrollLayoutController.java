package com.android.systemui.statusbar.notification.stack;

import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.MathUtils;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewGroupKt$children$1;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardStatusViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.media.controls.ui.controller.KeyguardMediaController;
import com.android.systemui.notification.FullExpansionPanelNotiAlphaController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.DragDownHelper;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
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
import com.android.systemui.statusbar.notification.collection.provider.VisibilityLocationProviderDelegator;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.AppLockNotificationController;
import com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.custom.CustomDeviceManager;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import javax.inject.Provider;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;
import noticolorpicker.NotificationColorPicker;

public final class NotificationStackScrollLayoutController implements Dumpable, NotificationRoundnessManager.SectionStateProvider {
    public static final AnonymousClass4 HIDE_ALPHA_PROPERTY = new AnonymousClass4(Float.class, "HideNotificationsAlpha");
    public final ActivityStarter mActivityStarter;
    public final boolean mAllowLongPress;
    public final AppLockNotificationController mAppLockNotificationController;
    public int mBarState;
    public boolean mBlockHideAmountVisibility;
    public final ColorUpdateLogger mColorUpdateLogger;
    public final ConfigurationController mConfigurationController;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final NotificationDismissibilityProvider mDismissibilityProvider;
    public final DynamicPrivacyController mDynamicPrivacyController;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final GroupExpansionManager mGroupExpansionManager;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final HeadsUpManager mHeadsUpManager;
    public Boolean mHistoryEnabled;
    public boolean mIsStartFromContentsBound;
    public final InteractionJankMonitor mJankMonitor;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardMediaController mKeyguardMediaController;
    public final KeyguardTransitionRepository mKeyguardTransitionRepo;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public final AnonymousClass9 mLockscreenUserChangeListener;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final NotificationStackScrollLogger mLogger;
    public View mLongPressedView;
    public final float mMaxAlphaForGlanceableHub;
    public float mMaxAlphaForKeyguard;
    public String mMaxAlphaForKeyguardSource;
    public float mMaxAlphaForUnhide;
    public final float mMaxAlphaFromView;
    public final AnonymousClass10 mMenuEventListener;
    public final MetricsLogger mMetricsLogger;
    public final NotifCollection mNotifCollection;
    public final NotifStackControllerImpl mNotifStackController;
    public NotifStats mNotifStats;
    public NotificationActivityStarter mNotificationActivityStarter;
    final NotificationSwipeHelper.NotificationCallback mNotificationCallback;
    public final NotificationGutsManager mNotificationGutsManager;
    public final NotificationListContainerImpl mNotificationListContainer;
    public final NotificationRoundnessManager mNotificationRoundnessManager;
    public final NotificationStackSizeCalculator mNotificationStackSizeCalculator;
    public final NotificationTargetsHelper mNotificationTargetsHelper;
    public final NotificationsController mNotificationsController;
    public final AnonymousClass12 mOnHeadsUpChangedListener;
    public SecPanelSplitHelper mPanelSplitHelper;
    public final PowerInteractor mPowerInteractor;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public final SecureSettings mSecureSettings;
    public final SeenNotificationsInteractor mSeenNotificationsInteractor;
    public final SensitiveNotificationProtectionController mSensitiveNotificationProtectionController;
    public final ShadeController mShadeController;
    public final NotificationShelfManager mShelfManager;
    private final SettingsHelper.OnChangedCallback mSplitCallback;
    public final StackStateLogger mStackStateLogger;
    public final AnonymousClass8 mStateListener;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final NotificationSwipeHelper mSwipeHelper;
    public final TouchHandler mTouchHandler;
    public final TunerService mTunerService;
    public final UiEventLogger mUiEventLogger;
    public final NotificationStackScrollLayout mView;
    public final NotificationListViewBinder mViewBinder;
    public final NotificationVisibilityProvider mVisibilityProvider;
    private final SettingsHelper.OnChangedCallback mWallpaperThemeCallback;
    public final ZenModeController mZenModeController;
    public final AnonymousClass13 mZenModeControllerCallback;
    public boolean mIsInTransitionToAod = false;
    public final NotificationStackScrollLayoutController$$ExternalSyntheticLambda0 mKeyguardVisibilityListener = new IntConsumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda0
        @Override // java.util.function.IntConsumer
        public final void accept(int i) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            if (i == 4 && notificationStackScrollLayoutController.mHasDelayedForceLayout) {
                Log.d("StackScrollerController", "do delayed stackScroller forceLayout");
                notificationStackScrollLayout.forceLayout();
            }
            notificationStackScrollLayoutController.mHasDelayedForceLayout = false;
            notificationStackScrollLayout.removeCallbacks(notificationStackScrollLayoutController.mForceLayoutTimeOutRunnable);
        }
    };
    public boolean mHasDelayedForceLayout = false;
    public boolean mMusicItemExpanded = false;
    public final AnonymousClass1 mForceLayoutTimeOutRunnable = new AnonymousClass1();
    public final AnonymousClass2 mFoldOpenModeListener = new AnonymousClass2();
    final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.3
        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            NotificationStackScrollLayoutController.this.mColorUpdateLogger.getClass();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).addCallback(notificationStackScrollLayoutController.mConfigurationListener);
            int i = FooterViewRefactor.$r8$clinit;
            Flags.notificationsFooterViewRefactor();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
            ((ZenModeControllerImpl) notificationStackScrollLayoutController2.mZenModeController).addCallback(notificationStackScrollLayoutController2.mZenModeControllerCallback);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = NotificationStackScrollLayoutController.this;
            int i2 = ((StatusBarStateControllerImpl) notificationStackScrollLayoutController3.mStatusBarStateController).mState;
            if (i2 != notificationStackScrollLayoutController3.mBarState) {
                notificationStackScrollLayoutController3.mStateListener.onStateChanged(i2);
                NotificationStackScrollLayoutController.this.mStateListener.onStatePostChange();
            }
            NotificationShelfManager notificationShelfManager = NotificationStackScrollLayoutController.this.mShelfManager;
            ((ConfigurationControllerImpl) notificationShelfManager.configurationController).addCallback(notificationShelfManager.configurationListener);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController4 = NotificationStackScrollLayoutController.this;
            SysuiStatusBarStateController sysuiStatusBarStateController = notificationStackScrollLayoutController4.mStatusBarStateController;
            AnonymousClass8 anonymousClass8 = notificationStackScrollLayoutController4.mStateListener;
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController;
            synchronized (statusBarStateControllerImpl.mListeners) {
                statusBarStateControllerImpl.addListenerInternalLocked(anonymousClass8, 2);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            NotificationStackScrollLayoutController.this.mColorUpdateLogger.getClass();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).removeCallback(notificationStackScrollLayoutController.mConfigurationListener);
            int i = FooterViewRefactor.$r8$clinit;
            Flags.notificationsFooterViewRefactor();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
            ((ZenModeControllerImpl) notificationStackScrollLayoutController2.mZenModeController).removeCallback(notificationStackScrollLayoutController2.mZenModeControllerCallback);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = NotificationStackScrollLayoutController.this;
            ((StatusBarStateControllerImpl) notificationStackScrollLayoutController3.mStatusBarStateController).removeCallback((StatusBarStateController.StateListener) notificationStackScrollLayoutController3.mStateListener);
        }
    };
    public ObjectAnimator mHideAlphaAnimator = null;
    public final AnonymousClass5 mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.5
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
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            boolean isCurrentUserSetup = ((DeviceProvisionedControllerImpl) notificationStackScrollLayoutController.mDeviceProvisionedController).isCurrentUserSetup();
            notificationStackScrollLayout.getClass();
            FooterViewRefactor.assertInLegacyMode();
            if (notificationStackScrollLayout.mIsCurrentUserSetup != isCurrentUserSetup) {
                notificationStackScrollLayout.mIsCurrentUserSetup = isCurrentUserSetup;
                notificationStackScrollLayout.updateFooter();
            }
        }
    };
    public final AnonymousClass6 mSensitiveStateChangedListener = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.6
        @Override // java.lang.Runnable
        public final void run() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
            notificationStackScrollLayoutController.updateSensitivenessWithAnimation(false);
        }
    };
    public final NotificationStackScrollLayoutController$$ExternalSyntheticLambda1 mDynamicPrivacyControllerListener = new DynamicPrivacyController.Listener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda1
        @Override // com.android.systemui.statusbar.notification.DynamicPrivacyController.Listener
        public final void onDynamicPrivacyChanged() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.getClass();
            int i = FooterViewRefactor.$r8$clinit;
            Flags.notificationsFooterViewRefactor();
            notificationStackScrollLayoutController.mView.post(new NotificationStackScrollLayoutController$$ExternalSyntheticLambda8(notificationStackScrollLayoutController, 3));
        }
    };
    final ConfigurationController.ConfigurationListener mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.7
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
            NotificationStackScrollLayoutController.this.mNotificationStackSizeCalculator.updateResources();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            StringBuilder sb = new StringBuilder("updateShowEmptyShadeView: CurrentState: ");
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            sb.append(StatusBarState.toString(((StatusBarStateControllerImpl) notificationStackScrollLayoutController.mStatusBarStateController).mUpcomingState));
            sb.append(" isQsFullScreen: ");
            sb.append(notificationStackScrollLayoutController.mView.mQsFullScreen);
            sb.append(" VisibleNotificationCount: ");
            sb.append(notificationStackScrollLayoutController.getVisibleNotificationCount());
            Log.d("StackScrollerController", sb.toString());
            Flags.notificationsFooterViewRefactor();
            notificationStackScrollLayoutController.updateShowEmptyShadeView();
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.getClass();
            int i = FooterViewRefactor.$r8$clinit;
            Flags.notificationsFooterViewRefactor();
            notificationStackScrollLayout.inflateFooterView();
            notificationStackScrollLayout.updateFooter();
            notificationStackScrollLayout.inflateEmptyShadeView();
            notificationStackScrollLayout.inflateDndView();
            notificationStackScrollLayout.mSectionsManager.reinflateViews();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onThemeChanged() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            ColorUpdateLogger colorUpdateLogger = notificationStackScrollLayoutController.mColorUpdateLogger;
            ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).getClass();
            colorUpdateLogger.getClass();
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            int dimensionPixelSize = notificationStackScrollLayout.getResources().getDimensionPixelSize(R.dimen.notification_corner_radius);
            if (notificationStackScrollLayout.mCornerRadius != dimensionPixelSize) {
                notificationStackScrollLayout.mCornerRadius = dimensionPixelSize;
                notificationStackScrollLayout.invalidate();
            }
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
            for (int i = 0; i < notificationStackScrollLayout2.getChildCount(); i++) {
                View childAt = notificationStackScrollLayout2.getChildAt(i);
                if (childAt instanceof ActivatableNotificationView) {
                    ((ActivatableNotificationView) childAt).updateBackgroundColors();
                }
            }
            notificationStackScrollLayoutController.mView.updateDecorViews();
            NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout3.getClass();
            int i2 = FooterViewRefactor.$r8$clinit;
            Flags.notificationsFooterViewRefactor();
            notificationStackScrollLayout3.inflateFooterView();
            notificationStackScrollLayout3.updateFooter();
            notificationStackScrollLayout3.inflateEmptyShadeView();
            notificationStackScrollLayout3.inflateDndView();
            notificationStackScrollLayout3.mSectionsManager.reinflateViews();
            Flags.notificationsFooterViewRefactor();
            notificationStackScrollLayoutController.updateShowEmptyShadeView();
            notificationStackScrollLayoutController.updateFooter();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onUiModeChanged() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            ColorUpdateLogger colorUpdateLogger = notificationStackScrollLayoutController.mColorUpdateLogger;
            ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).getClass();
            colorUpdateLogger.getClass();
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            for (int i = 0; i < notificationStackScrollLayout.getChildCount(); i++) {
                View childAt = notificationStackScrollLayout.getChildAt(i);
                if (childAt instanceof ActivatableNotificationView) {
                    ((ActivatableNotificationView) childAt).updateBackgroundColors();
                }
            }
            notificationStackScrollLayoutController.mView.updateDecorViews();
            notificationStackScrollLayoutController.mView.updateSectionColor();
        }
    };

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
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

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$11, reason: invalid class name */
    public final class AnonymousClass11 implements NotificationSwipeHelper.NotificationCallback {
        public AnonymousClass11() {
        }

        public final boolean canChildBeDismissed(View view) {
            boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
            if (!(view instanceof ExpandableNotificationRow)) {
                return false;
            }
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            if (expandableNotificationRow.areGutsExposed() || !expandableNotificationRow.mEntry.hasFinishedInitialization()) {
                return false;
            }
            return expandableNotificationRow.canViewBeDismissed$1();
        }

        public final ExpandableView getChildAtPosition(MotionEvent motionEvent) {
            ExpandableNotificationRow expandableNotificationRow;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            Flags.FEATURE_FLAGS.getClass();
            ExpandableView childAtPosition = notificationStackScrollLayout.getChildAtPosition(x, y, true, false);
            if (!(childAtPosition instanceof ExpandableNotificationRow) || (expandableNotificationRow = ((ExpandableNotificationRow) childAtPosition).mNotificationParent) == null || !expandableNotificationRow.mChildrenExpanded) {
                return childAtPosition;
            }
            if (!expandableNotificationRow.areGutsExposed() && notificationStackScrollLayoutController.mSwipeHelper.mMenuExposedView != expandableNotificationRow) {
                if (expandableNotificationRow.getAttachedChildren().size() != 1) {
                    return childAtPosition;
                }
                if (!(!((NotificationDismissibilityProviderImpl) notificationStackScrollLayoutController.mDismissibilityProvider).nonDismissableEntryKeys.contains(expandableNotificationRow.mEntry.mKey))) {
                    return childAtPosition;
                }
            }
            return expandableNotificationRow;
        }

        public final void handleChildViewDismissed(View view) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            notificationStackScrollLayout.updateFirstAndLastBackgroundViews();
            notificationStackScrollLayout.mController.mNotificationRoundnessManager.setViewsAffectedBySwipe(null, null, null);
            notificationStackScrollLayout.mShelf.updateAppearance();
            if (notificationStackScrollLayoutController.mView.mClearAllInProgress) {
                Log.d("StackScrollerController", "dismiss notification, but ClearAllInProgressing..");
                return;
            }
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.mIsHeadsUp) {
                    ((HeadsUpManagerPhone) notificationStackScrollLayoutController.mHeadsUpManager).mSwipedOutKeys.add(expandableNotificationRow.mEntry.mSbn.getKey());
                }
                if (NotiRune.NOTI_INSIGNIFICANT && expandableNotificationRow.isInsignificantSummary()) {
                    FooterViewRefactor.assertInLegacyMode();
                    FooterViewRefactor.assertInLegacyMode();
                    notificationStackScrollLayoutController.mView.clearNotifications(2, true ^ notificationStackScrollLayoutController.hasNotifications(1, true));
                } else {
                    expandableNotificationRow.performDismiss(false);
                }
            }
            notificationStackScrollLayoutController.mView.mSwipedOutViews.add(view);
            notificationStackScrollLayoutController.mFalsingCollector.getClass();
        }

        public final void onBeginDrag(View view) {
            Roundable roundable;
            ExpandableView expandableView;
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
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
                        ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) next;
                        Intrinsics.checkNotNull(expandableNotificationRow3);
                        if (expandableNotificationRow3.getVisibility() == 0) {
                            arrayList.add(next);
                        }
                    }
                    int indexOf2 = arrayList.indexOf(expandableNotificationRow);
                    roundable = (ExpandableNotificationRow) CollectionsKt___CollectionsKt.getOrNull(indexOf2 - 1, arrayList);
                    if (roundable == null) {
                        roundable = notificationChildrenContainer.mGroupHeaderWrapper;
                    }
                    expandableView = (ExpandableNotificationRow) CollectionsKt___CollectionsKt.getOrNull(indexOf2 + 1, arrayList);
                    if (expandableView == null) {
                        expandableView = (ExpandableView) CollectionsKt___CollectionsKt.getOrNull(list.indexOf(expandableNotificationRow2) + 1, list);
                    }
                }
                RoundableTargets roundableTargets = new RoundableTargets(roundable, expandableNotificationRow, expandableView);
                notificationStackScrollLayout.mController.mNotificationRoundnessManager.setViewsAffectedBySwipe(roundableTargets.before, roundableTargets.swiped, roundableTargets.after);
                notificationStackScrollLayout.updateFirstAndLastBackgroundViews();
                notificationStackScrollLayout.requestDisallowInterceptTouchEvent(true);
                notificationStackScrollLayout.updateContinuousShadowDrawing();
                notificationStackScrollLayout.requestChildrenUpdate();
            }
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$18, reason: invalid class name */
    public final class AnonymousClass18 implements RemoteInputController.Delegate {
        public AnonymousClass18() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$4, reason: invalid class name */
    public final class AnonymousClass4 extends Property {
        public AnonymousClass4(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public final Object get(Object obj) {
            return Float.valueOf(((NotificationStackScrollLayoutController) obj).mMaxAlphaForUnhide);
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = (NotificationStackScrollLayoutController) obj;
            notificationStackScrollLayoutController.mMaxAlphaForUnhide = ((Float) obj2).floatValue();
            notificationStackScrollLayoutController.updateAlpha$1$1();
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$8, reason: invalid class name */
    public final class AnonymousClass8 implements StatusBarStateController.StateListener {
        public AnonymousClass8() {
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mBarState = i;
            notificationStackScrollLayoutController.mView.setStatusBarState(i);
            if (i == 1) {
                GroupExpansionManagerImpl groupExpansionManagerImpl = (GroupExpansionManagerImpl) notificationStackScrollLayoutController.mGroupExpansionManager;
                groupExpansionManagerImpl.getClass();
                Iterator it = new ArrayList(groupExpansionManagerImpl.mExpandedGroups).iterator();
                while (it.hasNext()) {
                    groupExpansionManagerImpl.setGroupExpanded((NotificationEntry) it.next(), false);
                }
            }
            notificationStackScrollLayoutController.mView.updateDndView(((ZenModeControllerImpl) notificationStackScrollLayoutController.mZenModeController).mZenMode);
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStatePostChange() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.updateSensitivenessWithAnimation(((StatusBarStateControllerImpl) notificationStackScrollLayoutController.mStatusBarStateController).goingToFullShade());
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            int i = ((StatusBarStateControllerImpl) notificationStackScrollLayoutController.mStatusBarStateController).mLastState;
            boolean onKeyguard = notificationStackScrollLayout.onKeyguard();
            if (notificationStackScrollLayout.mAmbientState.isNeedsToExpandLocksNoti() && onKeyguard) {
                notificationStackScrollLayout.mAmbientState.mDimmed = false;
            } else {
                notificationStackScrollLayout.mAmbientState.mDimmed = onKeyguard;
            }
            HeadsUpAppearanceController headsUpAppearanceController = notificationStackScrollLayout.mHeadsUpAppearanceController;
            if (headsUpAppearanceController != null) {
                headsUpAppearanceController.updateTopEntry();
            }
            notificationStackScrollLayout.mExpandHelper.mEnabled = !onKeyguard;
            int i2 = FooterViewRefactor.$r8$clinit;
            Flags.notificationsFooterViewRefactor();
            notificationStackScrollLayout.updateFooter();
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.changeViewPosition(notificationStackScrollLayout.mEmptyShadeView, notificationStackScrollLayout.getChildCount() - 1);
            notificationStackScrollLayout.changeViewPosition(notificationStackScrollLayout.mShelf, notificationStackScrollLayout.getChildCount() - 2);
            notificationStackScrollLayout.updateVisibility$1();
            Flags.notificationsFooterViewRefactor();
            notificationStackScrollLayoutController.updateImportantForAccessibility();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStatePreChange(int i, int i2) {
            if (i == 2 && i2 == 1) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.mLockscreenNotificationManager.getClass();
                if (LockscreenNotificationManager.isNotificationIconsOnlyShowing()) {
                    NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                    if (notificationStackScrollLayout.mIsExpanded && notificationStackScrollLayout.mAnimationsEnabled) {
                        notificationStackScrollLayout.mEverythingNeedsAnimation = false;
                        notificationStackScrollLayout.mNeedsAnimation = false;
                        notificationStackScrollLayout.requestChildrenUpdate();
                        return;
                    }
                    return;
                }
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                if (notificationStackScrollLayout2.mIsExpanded && notificationStackScrollLayout2.mAnimationsEnabled) {
                    notificationStackScrollLayout2.mEverythingNeedsAnimation = true;
                    notificationStackScrollLayout2.mNeedsAnimation = true;
                    notificationStackScrollLayout2.requestChildrenUpdate();
                }
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onUpcomingStateChanged(int i) {
            int i2 = FooterViewRefactor.$r8$clinit;
            Flags.notificationsFooterViewRefactor();
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            notificationStackScrollLayout.getClass();
            FooterViewRefactor.assertInLegacyMode();
            notificationStackScrollLayout.mUpcomingStatusBarState = i;
            if (i != notificationStackScrollLayout.mStatusBarState) {
                notificationStackScrollLayout.updateFooter();
            }
        }
    }

    public final class NotifStackControllerImpl implements NotifStackController {
        public /* synthetic */ NotifStackControllerImpl(NotificationStackScrollLayoutController notificationStackScrollLayoutController, int i) {
            this();
        }

        public final void setNotifStats(NotifStats notifStats) {
            FooterViewRefactor.assertInLegacyMode();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mNotifStats = notifStats;
            Flags.notificationsFooterViewRefactor();
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            boolean booleanValue = ((Boolean) notificationStackScrollLayoutController.mSeenNotificationsInteractor.hasFilteredOutSeenNotifications.getValue()).booleanValue();
            notificationStackScrollLayout.getClass();
            FooterViewRefactor.assertInLegacyMode();
            notificationStackScrollLayout.mHasFilteredOutSeenNotifications = booleanValue;
            notificationStackScrollLayoutController.updateFooter();
            notificationStackScrollLayoutController.updateShowEmptyShadeView();
            notificationStackScrollLayoutController.updateImportantForAccessibility();
            notificationStackScrollLayoutController.mShelfManager.updateClearButton();
        }

        private NotifStackControllerImpl() {
        }
    }

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
            AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
            NotificationStackScrollLayoutController.this.getClass();
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            return (expandableNotificationRow == null || (expandableNotificationRow.mViewState.location & 5) == 0 || expandableNotificationRow.getVisibility() != 0) ? false : true;
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public final void onHeightChanged(ExpandableView expandableView, boolean z) {
            NotificationStackScrollLayoutController.this.mView.onChildHeightChanged(expandableView, z);
        }

        @Override // com.android.systemui.statusbar.notification.row.ExpandableView.OnHeightChangedListener
        public final void onReset(ExpandableView expandableView) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            boolean z = (notificationStackScrollLayout.mAnimationsEnabled || notificationStackScrollLayout.mPulsing) && (notificationStackScrollLayout.mIsExpanded || NotificationStackScrollLayout.isPinnedHeadsUp(expandableView));
            boolean z2 = expandableView instanceof ExpandableNotificationRow;
            if (z2) {
                ((ExpandableNotificationRow) expandableView).setAnimationRunning(z);
            }
            if (z2) {
                ((ExpandableNotificationRow) expandableView).setChronometerRunning(notificationStackScrollLayout.mIsExpanded);
            }
            if (notificationStackScrollLayout.mTopHeadsUpRow == expandableView) {
                Iterator it = notificationStackScrollLayout.mHeadsUpHeightChangedListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
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

    public final class TouchHandler implements Gefingerpoken {
        public final QuickPanelLogger mQuickPanelLogger = new QuickPanelLogger("NSSLC");
        public final StringBuilder mQuickPanelLogBuilder = new StringBuilder();
        public boolean shelfOnDown = false;
        public boolean panelSliderIntercepted = false;

        public TouchHandler() {
        }

        /* JADX WARN: Removed duplicated region for block: B:104:0x010a  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x009a  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x00e4  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0108  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x0111 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:78:0x0126  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x012e A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:86:0x0140  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x0174 A[ADDED_TO_REGION] */
        @Override // com.android.systemui.Gefingerpoken
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onInterceptTouchEvent(android.view.MotionEvent r13) {
            /*
                Method dump skipped, instructions count: 382
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.TouchHandler.onInterceptTouchEvent(android.view.MotionEvent):boolean");
        }

        /* JADX WARN: Removed duplicated region for block: B:100:0x018b A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:107:0x0137  */
        /* JADX WARN: Removed duplicated region for block: B:121:0x00d6  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x00c1  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x00db  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x00f5  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x0116  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x012e  */
        /* JADX WARN: Removed duplicated region for block: B:89:0x0158  */
        @Override // com.android.systemui.Gefingerpoken
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onTouchEvent(android.view.MotionEvent r15) {
            /*
                Method dump skipped, instructions count: 405
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.TouchHandler.onTouchEvent(android.view.MotionEvent):boolean");
        }
    }

    /* renamed from: -$$Nest$mupdateEventAvailability, reason: not valid java name */
    public static void m2223$$Nest$mupdateEventAvailability(NotificationStackScrollLayoutController notificationStackScrollLayoutController, MotionEvent motionEvent) {
        notificationStackScrollLayoutController.getClass();
        int action = motionEvent.getAction();
        if (action == 0) {
            float rawX = motionEvent.getRawX();
            motionEvent.getY();
            notificationStackScrollLayoutController.mIsStartFromContentsBound = notificationStackScrollLayoutController.isInContentBounds$2(rawX);
        } else if (action == 1) {
            notificationStackScrollLayoutController.mIsStartFromContentsBound = false;
        } else {
            if (action != 3) {
                return;
            }
            notificationStackScrollLayoutController.mIsStartFromContentsBound = false;
        }
    }

    /* JADX WARN: Type inference failed for: r10v2, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r6v12, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$9] */
    /* JADX WARN: Type inference failed for: r6v13, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$10] */
    /* JADX WARN: Type inference failed for: r6v15, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$12] */
    /* JADX WARN: Type inference failed for: r6v16, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$13] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$5] */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$6] */
    /* JADX WARN: Type inference failed for: r6v6, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda1] */
    public NotificationStackScrollLayoutController(NotificationStackScrollLayout notificationStackScrollLayout, boolean z, NotificationGutsManager notificationGutsManager, NotificationsController notificationsController, NotificationVisibilityProvider notificationVisibilityProvider, NotificationWakeUpCoordinator notificationWakeUpCoordinator, HeadsUpManager headsUpManager, NotificationRoundnessManager notificationRoundnessManager, TunerService tunerService, DeviceProvisionedController deviceProvisionedController, DynamicPrivacyController dynamicPrivacyController, ConfigurationController configurationController, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardMediaController keyguardMediaController, KeyguardBypassController keyguardBypassController, PowerInteractor powerInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, KeyguardTransitionRepository keyguardTransitionRepository, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, MetricsLogger metricsLogger, ColorUpdateLogger colorUpdateLogger, DumpManager dumpManager, FalsingCollector falsingCollector, FalsingManager falsingManager, NotificationSwipeHelper.Builder builder, GroupExpansionManager groupExpansionManager, SectionHeaderController sectionHeaderController, NotifPipeline notifPipeline, NotifCollection notifCollection, LockscreenShadeTransitionController lockscreenShadeTransitionController, UiEventLogger uiEventLogger, NotificationRemoteInputManager notificationRemoteInputManager, VisibilityLocationProviderDelegator visibilityLocationProviderDelegator, SeenNotificationsInteractor seenNotificationsInteractor, NotificationListViewBinder notificationListViewBinder, ShadeController shadeController, Provider provider, InteractionJankMonitor interactionJankMonitor, StackStateLogger stackStateLogger, NotificationStackScrollLogger notificationStackScrollLogger, NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationTargetsHelper notificationTargetsHelper, SecureSettings secureSettings, NotificationDismissibilityProvider notificationDismissibilityProvider, ActivityStarter activityStarter, SplitShadeStateController splitShadeStateController, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, NotificationShelfManager notificationShelfManager, LockscreenNotificationManager lockscreenNotificationManager, Lazy lazy, AppLockNotificationController appLockNotificationController) {
        int i = 0;
        this.mNotificationListContainer = new NotificationListContainerImpl(this, i);
        this.mNotifStackController = new NotifStackControllerImpl(this, i);
        NotifStats.Companion.getClass();
        this.mNotifStats = NotifStats.empty;
        this.mMaxAlphaForKeyguard = 1.0f;
        this.mMaxAlphaForKeyguardSource = "constructor";
        this.mMaxAlphaForUnhide = 1.0f;
        this.mMaxAlphaFromView = 1.0f;
        this.mMaxAlphaForGlanceableHub = 1.0f;
        this.mStateListener = new AnonymousClass8();
        this.mLockscreenUserChangeListener = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.9
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onUserChanged(int i2) {
                AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.updateSensitivenessWithAnimation(false);
                notificationStackScrollLayoutController.mHistoryEnabled = null;
                int i3 = FooterViewRefactor.$r8$clinit;
                Flags.notificationsFooterViewRefactor();
                notificationStackScrollLayoutController.updateFooter();
            }
        };
        this.mMenuEventListener = new NotificationMenuRowPlugin.OnMenuEventListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.10
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
                    NotificationSwipeHelper.NotificationCallback notificationCallback = notificationSwipeHelper.mCallback;
                    notificationCallback.getClass();
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
        this.mNotificationCallback = new AnonymousClass11();
        this.mOnHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.12
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z2) {
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
                notificationStackScrollLayout2.mInHeadsUpPinnedMode = z2;
                notificationStackScrollLayout2.updateClipping$1();
            }

            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z2) {
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                BaseHeadsUpManager.HeadsUpEntry topHeadsUpEntry = ((BaseHeadsUpManager) notificationStackScrollLayoutController.mHeadsUpManager).getTopHeadsUpEntry();
                NotificationEntry notificationEntry2 = topHeadsUpEntry != null ? topHeadsUpEntry.mEntry : null;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout2.mTopHeadsUpRow = notificationEntry2 != null ? notificationEntry2.row : null;
                Iterator it = notificationStackScrollLayout2.mHeadsUpHeightChangedListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
                NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout3.getClass();
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                notificationStackScrollLayout3.generateHeadsUpAnimation(notificationEntry.row, z2);
            }
        };
        this.mZenModeControllerCallback = new ZenModeController.Callback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.13
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i2) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.updateShowEmptyShadeView();
                notificationStackScrollLayoutController.mView.updateDndView(((ZenModeControllerImpl) notificationStackScrollLayoutController.mZenModeController).mZenMode);
            }
        };
        this.mWallpaperThemeCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.14
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                if (settingsHelper.isWallpaperThemeSettingsOn() && uri.equals(Settings.Global.getUriFor(SettingsHelper.INDEX_NOTI_POLICY_APPLY_WALPAPER_THEME))) {
                    Log.d("StackScrollerController", "apply notification icon color to".concat(settingsHelper.isApplyWallpaperThemeToNotif() ? " WALLPAPER COLOR" : " SMALL ICON COLOR"));
                    NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
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
        this.mSplitCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.15
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && uri.equals(Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL))) {
                    NotificationStackScrollLayoutController.this.mView.requestLayout();
                }
            }
        };
        this.mIsStartFromContentsBound = false;
        this.mView = notificationStackScrollLayout;
        this.mKeyguardTransitionRepo = keyguardTransitionRepository;
        this.mViewBinder = notificationListViewBinder;
        this.mStackStateLogger = stackStateLogger;
        this.mAllowLongPress = z;
        this.mNotificationGutsManager = notificationGutsManager;
        this.mNotificationsController = notificationsController;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mHeadsUpManager = headsUpManager;
        this.mNotificationRoundnessManager = notificationRoundnessManager;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mConfigurationController = configurationController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mPowerInteractor = powerInteractor;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mZenModeController = zenModeController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mMetricsLogger = metricsLogger;
        this.mColorUpdateLogger = colorUpdateLogger;
        this.mFalsingCollector = falsingCollector;
        this.mFalsingManager = falsingManager;
        this.mJankMonitor = interactionJankMonitor;
        this.mNotificationStackSizeCalculator = notificationStackSizeCalculator;
        this.mGroupExpansionManager = groupExpansionManager;
        this.mNotifCollection = notifCollection;
        this.mUiEventLogger = uiEventLogger;
        this.mSeenNotificationsInteractor = seenNotificationsInteractor;
        this.mShadeController = shadeController;
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mNotificationTargetsHelper = notificationTargetsHelper;
        this.mSecureSettings = secureSettings;
        this.mDismissibilityProvider = notificationDismissibilityProvider;
        this.mActivityStarter = activityStarter;
        this.mSensitiveNotificationProtectionController = sensitiveNotificationProtectionController;
        NotificationStackScrollLayout notificationStackScrollLayout2 = this.mView;
        notificationStackScrollLayout2.mSplitShadeStateController = splitShadeStateController;
        notificationStackScrollLayout2.updateSplitNotificationShade();
        int i2 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        dumpManager.registerDumpable(this);
        this.mShelfManager = notificationShelfManager;
        ((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).addVisibilityChangedListener(this.mKeyguardVisibilityListener);
        KeyguardFoldController keyguardFoldController = (KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class);
        AnonymousClass2 anonymousClass2 = this.mFoldOpenModeListener;
        KeyguardFoldControllerImpl keyguardFoldControllerImpl = (KeyguardFoldControllerImpl) keyguardFoldController;
        if (!((ArrayList) keyguardFoldControllerImpl.foldOpenModeListeners).contains(anonymousClass2)) {
            ((ArrayList) keyguardFoldControllerImpl.foldOpenModeListeners).add(anonymousClass2);
        }
        ((NotificationSectionsManager) Dependency.sDependency.getDependencyInner(NotificationSectionsManager.class)).sectionStateProvider = this;
        if (NotiRune.NOTI_STYLE_APP_LOCK) {
            this.mAppLockNotificationController = appLockNotificationController;
        }
        notificationStackSizeCalculator.updateResources();
        NotificationStackScrollLayout notificationStackScrollLayout3 = this.mView;
        notificationStackScrollLayout3.mStateAnimator.mLogger = this.mStackStateLogger;
        notificationStackScrollLayout3.mController = this;
        notificationRoundnessManager.mAnimatedChildren = notificationStackScrollLayout3.mChildrenToAddAnimated;
        notificationStackScrollLayout3.mLogger = notificationStackScrollLogger;
        TouchHandler touchHandler = new TouchHandler();
        this.mTouchHandler = touchHandler;
        NotificationStackScrollLayout notificationStackScrollLayout4 = this.mView;
        notificationStackScrollLayout4.mTouchHandler = touchHandler;
        Objects.requireNonNull(notificationsController);
        notificationStackScrollLayout4.mResetUserExpandedStatesRunnable = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda8(notificationsController, 5);
        NotificationStackScrollLayout notificationStackScrollLayout5 = this.mView;
        notificationStackScrollLayout5.mActivityStarter = activityStarter;
        notificationStackScrollLayout5.mClearAllAnimationListener = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(this);
        notificationStackScrollLayout5.mClearAllListener = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(this);
        notificationStackScrollLayout5.mShelfManager = notificationShelfManager;
        int i3 = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        NotificationStackScrollLayout notificationStackScrollLayout6 = this.mView;
        NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 notificationStackScrollLayoutController$$ExternalSyntheticLambda6 = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(this);
        notificationStackScrollLayout6.getClass();
        FooterViewRefactor.assertInLegacyMode();
        notificationStackScrollLayout6.mFooterClearAllListener = notificationStackScrollLayoutController$$ExternalSyntheticLambda6;
        NotificationStackScrollLayout notificationStackScrollLayout7 = this.mView;
        boolean isRemoteInputActive = notificationRemoteInputManager.isRemoteInputActive();
        notificationStackScrollLayout7.getClass();
        FooterViewRefactor.assertInLegacyMode();
        notificationStackScrollLayout7.mIsRemoteInputActive = isRemoteInputActive;
        notificationStackScrollLayout7.updateFooter();
        notificationRemoteInputManager.addControllerCallback(new RemoteInputController.Callback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.16
            @Override // com.android.systemui.statusbar.RemoteInputController.Callback
            public final void onRemoteInputActive(boolean z2) {
                NotificationStackScrollLayout notificationStackScrollLayout8 = NotificationStackScrollLayoutController.this.mView;
                notificationStackScrollLayout8.getClass();
                FooterViewRefactor.assertInLegacyMode();
                notificationStackScrollLayout8.mIsRemoteInputActive = z2;
                notificationStackScrollLayout8.updateFooter();
            }
        });
        NotificationStackScrollLayout notificationStackScrollLayout8 = this.mView;
        notificationStackScrollLayout8.mClearAllFinishedWhilePanelExpandedRunnable = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda8(this, 0);
        notificationStackScrollLayout8.mCollapseShadeDelayedWhenNoViewsToAnimateAwayRunnable = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda8(this, 4);
        dumpManager.registerDumpable(notificationStackScrollLayout8);
        final int i4 = 0;
        keyguardBypassController.registerOnBypassStateChangedListener(new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda10
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z2) {
                switch (i4) {
                    case 0:
                        ((NotificationStackScrollLayoutController) this).mNotificationRoundnessManager.mRoundForPulsingViews = !z2;
                        break;
                    default:
                        ((NotificationStackScrollLayout) this).mKeyguardBypassEnabled = z2;
                        break;
                }
            }
        });
        notificationRoundnessManager.mRoundForPulsingViews = !keyguardBypassController.getBypassEnabled();
        builder.mNotificationCallback = this.mNotificationCallback;
        builder.mOnMenuEventListener = this.mMenuEventListener;
        NotificationSwipeHelper notificationSwipeHelper = new NotificationSwipeHelper(builder.mResources, builder.mViewConfiguration, builder.mFalsingManager, builder.mFeatureFlags, builder.mNotificationCallback, builder.mOnMenuEventListener, builder.mNotificationRoundnessManager);
        builder.mDumpManager.registerDumpable(notificationSwipeHelper);
        this.mSwipeHelper = notificationSwipeHelper;
        notifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.17
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry) {
                NotificationStackScrollLayout notificationStackScrollLayout9 = NotificationStackScrollLayoutController.this.mView;
                notificationStackScrollLayout9.getClass();
                if (!notificationEntry.rowExists() || notificationEntry.mSbn.isClearable()) {
                    return;
                }
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                boolean z2 = notificationStackScrollLayout9.mIsExpanded || NotificationStackScrollLayout.isPinnedHeadsUp(expandableNotificationRow);
                NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
                if (notificationMenuRowPlugin != null) {
                    notificationStackScrollLayout9.mSwipeHelper.snapChildIfNeeded(expandableNotificationRow, notificationMenuRowPlugin.isMenuVisible() ? expandableNotificationRow.getTranslation() : 0.0f, z2);
                }
            }
        });
        NotificationStackScrollLayout notificationStackScrollLayout9 = this.mView;
        notificationStackScrollLayout9.initView(notificationStackScrollLayout9.getContext(), this.mSwipeHelper, notificationStackSizeCalculator);
        this.mView.updateDndView(((ZenModeControllerImpl) zenModeController).mZenMode);
        this.mView.mKeyguardBypassEnabled = keyguardBypassController.getBypassEnabled();
        final NotificationStackScrollLayout notificationStackScrollLayout10 = this.mView;
        Objects.requireNonNull(notificationStackScrollLayout10);
        final int i5 = 1;
        keyguardBypassController.registerOnBypassStateChangedListener(new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda10
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z2) {
                switch (i5) {
                    case 0:
                        ((NotificationStackScrollLayoutController) notificationStackScrollLayout10).mNotificationRoundnessManager.mRoundForPulsingViews = !z2;
                        break;
                    default:
                        ((NotificationStackScrollLayout) notificationStackScrollLayout10).mKeyguardBypassEnabled = z2;
                        break;
                }
            }
        });
        Flags.notificationsFooterViewRefactor();
        NotificationStackScrollLayout notificationStackScrollLayout11 = this.mView;
        NotificationStackScrollLayoutController$$ExternalSyntheticLambda5 notificationStackScrollLayoutController$$ExternalSyntheticLambda5 = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda5(this, i5);
        notificationStackScrollLayout11.getClass();
        FooterViewRefactor.assertInLegacyMode();
        notificationStackScrollLayout11.mManageButtonClickListener = notificationStackScrollLayoutController$$ExternalSyntheticLambda5;
        FooterView footerView = notificationStackScrollLayout11.mFooterView;
        if (footerView != null) {
            footerView.mManageOrHistoryButton.setOnClickListener(notificationStackScrollLayoutController$$ExternalSyntheticLambda5);
        }
        NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
        Flags.notificationsHeadsUpRefactor();
        ((BaseHeadsUpManager) headsUpManager).addListener(this.mOnHeadsUpChangedListener);
        NotificationStackScrollLayout notificationStackScrollLayout12 = this.mView;
        Objects.requireNonNull(notificationStackScrollLayout12);
        ((HeadsUpManagerPhone) headsUpManager).mAnimationStateHandler = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda13(notificationStackScrollLayout12);
        dynamicPrivacyController.mListeners.add(this.mDynamicPrivacyControllerListener);
        lockscreenShadeTransitionController.nsslController = this;
        NotificationStackScrollLayout.AnonymousClass15 anonymousClass15 = this.mView.mExpandHelperCallback;
        DragDownHelper dragDownHelper = lockscreenShadeTransitionController.touchHelper;
        dragDownHelper.expandCallback = anonymousClass15;
        dragDownHelper.notificationStackScrollLayoutController = this;
        ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).addUserChangedListener(this.mLockscreenUserChangeListener);
        visibilityLocationProviderDelegator.delegate = new VisibilityLocationProvider() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda3
            @Override // com.android.systemui.statusbar.notification.VisibilityLocationProvider
            public final boolean isInVisibleLocation(NotificationEntry notificationEntry) {
                NotificationStackScrollLayoutController.this.getClass();
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                return (expandableNotificationRow == null || (expandableNotificationRow.mViewState.location & 5) == 0 || expandableNotificationRow.getVisibility() != 0) ? false : true;
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
                } else if (str.equals(SettingsHelper.INDEX_COVER_SCREEN_NOTIFICATION_HISTORY)) {
                    notificationStackScrollLayoutController.mHistoryEnabled = null;
                    int i6 = FooterViewRefactor.$r8$clinit;
                    Flags.notificationsFooterViewRefactor();
                    notificationStackScrollLayoutController.updateFooter();
                }
            }
        }, "high_priority", SettingsHelper.INDEX_COVER_SCREEN_NOTIFICATION_HISTORY);
        keyguardMediaController.getClass();
        Flags.notificationsFooterViewRefactor();
        ((DeviceProvisionedControllerImpl) deviceProvisionedController).addCallback(this.mDeviceProvisionedListener);
        updateCurrentUserIsSetup();
        com.android.server.notification.Flags.screenshareNotificationHiding();
        ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).mListeners.addIfAbsent(this.mSensitiveStateChangedListener);
        if (this.mView.isAttachedToWindow()) {
            this.mOnAttachStateChangeListener.onViewAttachedToWindow(this.mView);
        }
        this.mView.addOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
        Flags.notificationsFooterViewRefactor();
        NotificationStackScrollLayoutController$$ExternalSyntheticLambda5 notificationStackScrollLayoutController$$ExternalSyntheticLambda52 = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda5(this, 0);
        SectionHeaderNodeControllerImpl sectionHeaderNodeControllerImpl = (SectionHeaderNodeControllerImpl) sectionHeaderController;
        sectionHeaderNodeControllerImpl.clearAllClickListener = notificationStackScrollLayoutController$$ExternalSyntheticLambda52;
        SectionHeaderView sectionHeaderView = sectionHeaderNodeControllerImpl._view;
        if (sectionHeaderView != null) {
            sectionHeaderView.mOnClearClickListener = notificationStackScrollLayoutController$$ExternalSyntheticLambda52;
            sectionHeaderView.mClearAllButton.setOnClickListener(notificationStackScrollLayoutController$$ExternalSyntheticLambda52);
        }
        ((HashSet) ((GroupExpansionManagerImpl) groupExpansionManager).mOnGroupChangeListeners).add(new NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(this));
        this.mViewBinder.bindWhileAttached(this.mView, this);
        Flags.notificationsFooterViewRefactor();
        JavaAdapterKt.collectFlow(this.mView, ((KeyguardTransitionRepositoryImpl) this.mKeyguardTransitionRepo).transitions, new Consumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationStackScrollLayoutController.this.onKeyguardTransitionChanged((TransitionStep) obj);
            }
        });
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mWallpaperThemeCallback, Settings.Global.getUriFor(SettingsHelper.INDEX_NOTI_POLICY_APPLY_WALPAPER_THEME));
        if (NotiRune.NOTI_STYLE_EMPTY_SHADE) {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSplitCallback, Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL));
        }
    }

    public final void checkSnoozeLeavebehind() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        if (notificationStackScrollLayout.mCheckForLeavebehind) {
            this.mNotificationGutsManager.closeAndSaveGuts(true, false, false, false);
            notificationStackScrollLayout.mCheckForLeavebehind = false;
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        CarrierTextController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(KeyguardStatusViewController$$ExternalSyntheticOutline0.m(new StringBuilder("mMaxAlphaFromView="), this.mMaxAlphaFromView, printWriter, "mMaxAlphaForUnhide="), this.mMaxAlphaForUnhide, printWriter, "mMaxAlphaForGlanceableHub="), this.mMaxAlphaForGlanceableHub, printWriter, "mMaxAlphaForKeyguard="), this.mMaxAlphaForKeyguard, printWriter, "mMaxAlphaForKeyguardSource="), this.mMaxAlphaForKeyguardSource, printWriter);
    }

    public final int getNotGoneChildCount() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        int childCount = notificationStackScrollLayout.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i2);
            if (expandableView.getVisibility() != 8 && !expandableView.mWillBeGone && expandableView != notificationStackScrollLayout.mShelf) {
                i++;
            }
        }
        return i;
    }

    public TouchHandler getTouchHandler() {
        return this.mTouchHandler;
    }

    public final int getVisibleNotificationCount() {
        FooterViewRefactor.assertInLegacyMode();
        return this.mNotifStats.numActiveNotifs;
    }

    public final boolean hasNotifications(int i, boolean z) {
        FooterViewRefactor.assertInLegacyMode();
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
        throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Bad selection: "));
    }

    public final boolean isAddOrRemoveAnimationPending() {
        SceneContainerFlag.assertInLegacyMode();
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        return (notificationStackScrollLayout == null || !notificationStackScrollLayout.mNeedsAnimation || (notificationStackScrollLayout.mChildrenToAddAnimated.isEmpty() && notificationStackScrollLayout.mChildrenToRemoveAnimated.isEmpty())) ? false : true;
    }

    public final boolean isHistoryEnabled() {
        Boolean bool = this.mHistoryEnabled;
        if (bool == null) {
            NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
            if (notificationStackScrollLayout == null || notificationStackScrollLayout.getContext() == null) {
                Log.wtf("StackScrollerController", "isHistoryEnabled failed to initialize its value");
                return false;
            }
            bool = Boolean.valueOf(this.mSecureSettings.getIntForUser(SettingsHelper.INDEX_COVER_SCREEN_NOTIFICATION_HISTORY, 0, -2) == 1);
            this.mHistoryEnabled = bool;
        }
        return bool.booleanValue();
    }

    public final boolean isInContentBounds$2(float f) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        float f2 = notificationStackScrollLayout.mSidePaddings;
        float x = notificationStackScrollLayout.getX() + f2;
        return x < f && f < (((float) this.mView.getWidth()) + x) - (f2 * 2.0f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0019, code lost:
    
        if (r3.to.equals(com.android.systemui.keyguard.shared.model.KeyguardState.DOZING) != false) goto L8;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onKeyguardTransitionChanged(com.android.systemui.keyguard.shared.model.TransitionStep r3) {
        /*
            r2 = this;
            com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor.assertInLegacyMode()
            com.android.systemui.keyguard.shared.model.KeyguardState r0 = r3.to
            com.android.systemui.keyguard.shared.model.KeyguardState r1 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L1b
            boolean r0 = com.android.systemui.NotiRune.NOTI_STYLE_EMPTY_SHADE
            if (r0 == 0) goto L2e
            com.android.systemui.keyguard.shared.model.KeyguardState r0 = com.android.systemui.keyguard.shared.model.KeyguardState.DOZING
            com.android.systemui.keyguard.shared.model.KeyguardState r1 = r3.to
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L2e
        L1b:
            com.android.systemui.keyguard.shared.model.KeyguardState r0 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            com.android.systemui.keyguard.shared.model.KeyguardState r3 = r3.from
            boolean r0 = r3.equals(r0)
            if (r0 != 0) goto L30
            com.android.systemui.keyguard.shared.model.KeyguardState r0 = com.android.systemui.keyguard.shared.model.KeyguardState.OCCLUDED
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L2e
            goto L30
        L2e:
            r3 = 0
            goto L31
        L30:
            r3 = 1
        L31:
            boolean r0 = r2.mIsInTransitionToAod
            if (r0 == r3) goto L3a
            r2.mIsInTransitionToAod = r3
            r2.updateShowEmptyShadeView()
        L3a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.onKeyguardTransitionChanged(com.android.systemui.keyguard.shared.model.TransitionStep):void");
    }

    public final void setMaxDisplayedNotifications(int i) {
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        if (notificationStackScrollLayout.mMaxDisplayedNotifications != i) {
            notificationStackScrollLayout.mMaxDisplayedNotifications = i;
            notificationStackScrollLayout.updateContentHeight();
            notificationStackScrollLayout.notifyHeightChangeListener(notificationStackScrollLayout.mShelf, false);
        }
    }

    public final void setOverExpansion(float f) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        notificationStackScrollLayout.mAmbientState.mOverExpansion = f;
        notificationStackScrollLayout.mStackScrollAlgorithm.mOverExpansionAmount = f;
        Flags.FEATURE_FLAGS.getClass();
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        SceneContainerFlag.assertInLegacyMode();
        if (notificationStackScrollLayout.mRoundedRectClippingYTranslation != 0) {
            notificationStackScrollLayout.mRoundedRectClippingYTranslation = 0;
            notificationStackScrollLayout.updateRoundedClipPath();
        }
        notificationStackScrollLayout.updateStackPosition(false);
        notificationStackScrollLayout.requestChildrenUpdate();
    }

    public final void setPanelFlinging(boolean z) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        if (ambientState.isOnKeyguard() && !z && ambientState.mIsFlinging) {
            ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = false;
        }
        ambientState.mIsFlinging = z;
        if (z) {
            return;
        }
        notificationStackScrollLayout.updateStackPosition(false);
    }

    public final void setQsExpansionFraction(float f) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        float f2 = notificationStackScrollLayout.mQsExpansionFraction;
        boolean z = f2 != f && (f2 == 1.0f || f == 1.0f);
        notificationStackScrollLayout.mQsExpansionFraction = f;
        notificationStackScrollLayout.mAmbientState.mQsExpansionFraction = f;
        notificationStackScrollLayout.updateUseRoundedRectClipping();
        int i = notificationStackScrollLayout.mOwnScrollY;
        if (i > 0) {
            notificationStackScrollLayout.setOwnScrollY((int) MathUtils.lerp(i, 0, notificationStackScrollLayout.mQsExpansionFraction));
        }
        int i2 = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        if (z) {
            notificationStackScrollLayout.updateFooter();
        }
        FullExpansionPanelNotiAlphaController fullExpansionPanelNotiAlphaController = notificationStackScrollLayout.mFullExpansionPanelNotiAlphaController;
        float f3 = notificationStackScrollLayout.mQsExpansionFraction;
        fullExpansionPanelNotiAlphaController.getClass();
        if (SecPanelSplitHelper.isEnabled() || QsAnimatorState.isCustomizerShowing || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailOpening || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailClosing || QsAnimatorState.isCustomizerShowing) {
            return;
        }
        if (fullExpansionPanelNotiAlphaController.mStackScrollerOverscrolling) {
            fullExpansionPanelNotiAlphaController.mStackScrollerAlphaAnimator.setPosition(0.0f);
        } else {
            fullExpansionPanelNotiAlphaController.mStackScrollerAlphaAnimator.setPosition(f3);
        }
    }

    public final void updateAlpha$1$1() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        if (notificationStackScrollLayout != null) {
            notificationStackScrollLayout.setAlpha(Math.min(Math.min(this.mMaxAlphaFromView, this.mMaxAlphaForKeyguard), Math.min(this.mMaxAlphaForUnhide, this.mMaxAlphaForGlanceableHub)));
        }
    }

    public final void updateFooter() {
        FooterViewRefactor.assertInLegacyMode();
        Trace.beginSection("NSSLC.updateFooter");
        this.mView.updateFooter();
        Trace.endSection();
    }

    public final void updateImportantForAccessibility() {
        FooterViewRefactor.assertInLegacyMode();
        int visibleNotificationCount = getVisibleNotificationCount();
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        if (visibleNotificationCount == 0 && notificationStackScrollLayout.onKeyguard()) {
            notificationStackScrollLayout.setImportantForAccessibility(2);
        } else {
            notificationStackScrollLayout.setImportantForAccessibility(1);
        }
    }

    public final void updateNotificationsContainerVisibility(boolean z, boolean z2) {
        ObjectAnimator objectAnimator = this.mHideAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        float f = z ? 1.0f : 0.0f;
        AnonymousClass4 anonymousClass4 = HIDE_ALPHA_PROPERTY;
        if (!z2) {
            anonymousClass4.set(this, Float.valueOf(f));
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, anonymousClass4, f);
        ofFloat.setInterpolator(Interpolators.STANDARD);
        ofFloat.setDuration(360L);
        this.mHideAlphaAnimator = ofFloat;
        ofFloat.start();
    }

    public final void updateSensitivenessWithAnimation(boolean z) {
        Trace.beginSection("NSSLC.updateSensitivenessWithAnimation");
        boolean z2 = NotiRune.NOTI_STYLE_APP_LOCK;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        SensitiveNotificationProtectionController sensitiveNotificationProtectionController = this.mSensitiveNotificationProtectionController;
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.mLockscreenUserManager;
        if (z2) {
            boolean isAnyProfilePublicMode = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isAnyProfilePublicMode();
            com.android.server.notification.Flags.screenshareNotificationHiding();
            boolean isSensitiveStateActive = ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).isSensitiveStateActive();
            notificationStackScrollLayout.updateSensitiveness(z && !isSensitiveStateActive, isAnyProfilePublicMode || isSensitiveStateActive || ((AppLockNotificationControllerImpl) this.mAppLockNotificationController).isAppLockEnabled());
        } else {
            com.android.server.notification.Flags.screenshareNotificationHiding();
            boolean isAnyProfilePublicMode2 = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isAnyProfilePublicMode();
            boolean isSensitiveStateActive2 = ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).isSensitiveStateActive();
            notificationStackScrollLayout.updateSensitiveness(z && !isSensitiveStateActive2, isAnyProfilePublicMode2 || isSensitiveStateActive2);
        }
        Trace.endSection();
    }

    public final void updateShowEmptyShadeView() {
        FooterViewRefactor.assertInLegacyMode();
        Trace.beginSection("NSSLC.updateShowEmptyShadeView");
        boolean z = false;
        boolean z2 = (getVisibleNotificationCount() != 0 || this.mIsInTransitionToAod || this.mPrimaryBouncerInteractor.isBouncerShowing()) ? false : true;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) this.mZenModeController;
        if (zenModeControllerImpl.mZenMode != 0 && (zenModeControllerImpl.mConsolidatedNotificationPolicy.suppressedVisualEffects & 256) != 0) {
            z = true;
        }
        notificationStackScrollLayout.getClass();
        if (!NotiRune.NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW) {
            FooterViewRefactor.assertInLegacyMode();
            notificationStackScrollLayout.updateEmptyShadeView(z2, z, notificationStackScrollLayout.mHasFilteredOutSeenNotifications);
        }
        Trace.endSection();
    }
}
