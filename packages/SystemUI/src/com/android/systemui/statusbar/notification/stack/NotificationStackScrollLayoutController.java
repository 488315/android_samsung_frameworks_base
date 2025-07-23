package com.android.systemui.statusbar.notification.stack;

import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import android.util.Log;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.KeyguardVisibilityMonitor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.controller.KeyguardMediaController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.DragDownHelper;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.chips.notification.shared.StatusBarNotifChips;
import com.android.systemui.statusbar.domain.interactor.SecStatusBarWindowViewTouchedInteractor;
import com.android.systemui.statusbar.headsup.shared.StatusBarNoHunBehavior;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.SubscreenSubRoomNotification;
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
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import com.android.systemui.statusbar.notification.headsup.HeadsUpAnimationEvent;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.shared.NotificationThrottleHun;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.AppLockNotificationController;
import com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.wallpapers.domain.interactor.WallpaperInteractor;
import com.samsung.android.knox.custom.CustomDeviceManager;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntConsumer;
import javax.inject.Provider;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationStackScrollLayoutController implements Dumpable {
    public static final AnonymousClass4 HIDE_ALPHA_PROPERTY = new AnonymousClass4(Float.class, "HideNotificationsAlpha");
    public static final AnonymousClass5 HIDE_DURING_REBINDING_PROPERTY = new AnonymousClass5(Float.class, "HideNotificationsAlphaDuringRebind");
    public final ActiveNotificationsInteractor mActiveNotificationsInteractor;
    public final ActivityStarter mActivityStarter;
    public final boolean mAllowLongPress;
    public final AppLockNotificationController mAppLockNotificationController;
    public int mBarState;
    public boolean mBlockHideAmountVisibility;
    public final ColorUpdateLogger mColorUpdateLogger;
    public final ConfigurationController mConfigurationController;
    final ConfigurationController.ConfigurationListener mConfigurationListener;
    public final NotificationDismissibilityProvider mDismissibilityProvider;
    public final DynamicPrivacyController mDynamicPrivacyController;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final AnonymousClass1 mForceLayoutTimeOutRunnable;
    public final GroupExpansionManager mGroupExpansionManager;
    public boolean mHasDelayedForceLayout;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final HeadsUpManager mHeadsUpManager;
    public ObjectAnimator mHideAlphaAnimator;
    public boolean mIsStartFromContentsBound;
    public final InteractionJankMonitor mJankMonitor;
    public final KeyguardBypassController mKeyguardBypassController;
    public final NotificationStackScrollLayoutController$$ExternalSyntheticLambda0 mKeyguardVisibilityListener;
    public final LockscreenNotificationManager mLockscreenNotificationManager;
    public final AnonymousClass9 mLockscreenUserChangeListener;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final NotificationStackScrollLogger mLogger;
    public ExpandableView mLongPressedView;
    public final MagneticNotificationRowManager mMagneticNotificationRowManager;
    public float mMaxAlphaForGlanceableHub;
    public float mMaxAlphaForKeyguard;
    public String mMaxAlphaForKeyguardSource;
    public float mMaxAlphaForRebind;
    public float mMaxAlphaForUnhide;
    public float mMaxAlphaFromView;
    public final AnonymousClass10 mMenuEventListener;
    public final MetricsLogger mMetricsLogger;
    public boolean mMusicItemExpanded;
    public final NotifCollection mNotifCollection;
    final NotificationSwipeHelper.NotificationCallback mNotificationCallback;
    public final NotificationGutsManager mNotificationGutsManager;
    public final NotificationListContainerImpl mNotificationListContainer;
    public final NotificationRoundnessManager mNotificationRoundnessManager;
    public final NotificationStackSizeCalculator mNotificationStackSizeCalculator;
    public final NotificationTargetsHelper mNotificationTargetsHelper;
    public final NotificationsController mNotificationsController;
    final View.OnAttachStateChangeListener mOnAttachStateChangeListener;
    public final AnonymousClass12 mOnHeadsUpChangedListener;
    public SecPanelSplitHelper mPanelSplitHelper;
    public final PowerInteractor mPowerInteractor;
    public ObjectAnimator mRebindAlphaAnimator;
    SettingsHelper.OnChangedCallback mReduceTransparencyAndBlurCallback;
    public final NotificationSectionsManager mSectionsManager;
    public final SecureSettings mSecureSettings;
    public final SensitiveNotificationProtectionController mSensitiveNotificationProtectionController;
    public final AnonymousClass6 mSensitiveStateChangedListener;
    public final ShadeController mShadeController;
    public final NotificationShelfManager mShelfManager;
    private final SettingsHelper.OnChangedCallback mSplitCallback;
    public final AnonymousClass8 mStateListener;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public final NotificationSwipeHelper mSwipeHelper;
    public final TouchHandler mTouchHandler;
    public final UiEventLogger mUiEventLogger;
    public final NotificationStackScrollLayout mView;
    public final NotificationVisibilityProvider mVisibilityProvider;
    private final SettingsHelper.OnChangedCallback mWallpaperThemeCallback;
    public final ZenModeController mZenModeController;
    public final AnonymousClass13 mZenModeControllerCallback;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            if (notificationStackScrollLayoutController.mHasDelayedForceLayout) {
                notificationStackScrollLayoutController.mHasDelayedForceLayout = false;
                Log.d("StackScrollerController", "delayed force layout time out. do forcelayout");
                ((View) NotificationStackScrollLayoutController.this.mView.getParent()).forceLayout();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$11, reason: invalid class name */
    public class AnonymousClass11 implements NotificationSwipeHelper.NotificationCallback {
        public AnonymousClass11() {
        }

        public final boolean canChildBeDismissed(View view) {
            boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
            if (view instanceof ExpandableNotificationRow) {
                return ((ExpandableNotificationRow) view).canExpandableViewBeDismissed();
            }
            return false;
        }

        public final ExpandableView getChildAtPosition(MotionEvent motionEvent) {
            ExpandableNotificationRow expandableNotificationRow;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            ExpandableView childAtPosition = notificationStackScrollLayoutController.mView.getChildAtPosition(motionEvent.getX(), motionEvent.getY(), true, false);
            if ((childAtPosition instanceof NotificationShelf) && !NotificationSwipeHelper.isTouchInView((NotificationShelf) childAtPosition, motionEvent)) {
                return null;
            }
            if ((childAtPosition instanceof ExpandableNotificationRow) && (expandableNotificationRow = ((ExpandableNotificationRow) childAtPosition).mNotificationParent) != null && expandableNotificationRow.mChildrenExpanded) {
                if (!expandableNotificationRow.areGutsExposed() && notificationStackScrollLayoutController.mSwipeHelper.mMenuExposedView != expandableNotificationRow) {
                    if (((ArrayList) expandableNotificationRow.getAttachedChildren()).size() == 1) {
                        if (!((NotificationDismissibilityProviderImpl) notificationStackScrollLayoutController.mDismissibilityProvider).nonDismissableEntryKeys.contains(expandableNotificationRow.getKey())) {
                        }
                    }
                }
                return expandableNotificationRow;
            }
            return childAtPosition;
        }

        public final void handleChildViewDismissed(View view) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mMagneticNotificationRowManager.resetRoundness();
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
                    ((HeadsUpManagerImpl) notificationStackScrollLayoutController.mHeadsUpManager).mSwipedOutKeys.add(expandableNotificationRow.getKey());
                }
                if (!expandableNotificationRow.isInsignificantSummary()) {
                    expandableNotificationRow.performDismiss(false);
                }
            }
            notificationStackScrollLayoutController.mView.mSwipedOutViews.add(view);
            notificationStackScrollLayoutController.mFalsingCollector.getClass();
        }

        public final void onBeginDrag(View view) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            notificationStackScrollLayout.getClass();
            if (view instanceof ExpandableNotificationRow) {
                if (view instanceof ExpandableNotificationRow) {
                    notificationStackScrollLayout.mSwipeCancelledView.remove((ExpandableNotificationRow) view);
                }
                notificationStackScrollLayout.mSectionsManager.updateFirstAndLastViewsForAllSections(notificationStackScrollLayout.mSections, notificationStackScrollLayout.getChildrenWithBackground());
                NotificationTargetsHelper notificationTargetsHelper = notificationStackScrollLayout.mController.mNotificationTargetsHelper;
                NotificationSectionsManager notificationSectionsManager = notificationStackScrollLayout.mSectionsManager;
                notificationTargetsHelper.getClass();
                RoundableTargets findRoundableTargets = NotificationTargetsHelper.findRoundableTargets((ExpandableNotificationRow) view, notificationStackScrollLayout, notificationSectionsManager);
                notificationStackScrollLayout.mController.mNotificationRoundnessManager.setViewsAffectedBySwipe(findRoundableTargets.before, findRoundableTargets.swiped, findRoundableTargets.after);
                NotificationRoundnessManager notificationRoundnessManager = notificationStackScrollLayout.mController.mNotificationRoundnessManager;
                Roundable roundable = notificationRoundnessManager.mViewBeforeSwipedView;
                SourceType$Companion$from$1 sourceType$Companion$from$1 = NotificationRoundnessManager.DISMISS_ANIMATION;
                if (roundable != null) {
                    roundable.requestBottomRoundness(1.0f, sourceType$Companion$from$1, roundable.getRoundableState().targetView.isShown());
                }
                ExpandableNotificationRow expandableNotificationRow = notificationRoundnessManager.mSwipedView;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.requestRoundness(1.0f, 1.0f, sourceType$Companion$from$1, expandableNotificationRow.getRoundableState().targetView.isShown());
                }
                Roundable roundable2 = notificationRoundnessManager.mViewAfterSwipedView;
                if (roundable2 != null) {
                    roundable2.requestTopRoundness(1.0f, sourceType$Companion$from$1, roundable2.getRoundableState().targetView.isShown());
                }
                notificationStackScrollLayout.updateFirstAndLastBackgroundViews();
                notificationStackScrollLayout.requestDisallowInterceptTouchEvent(true);
                notificationStackScrollLayout.updateContinuousShadowDrawing();
                notificationStackScrollLayout.requestChildrenUpdate();
            }
        }

        public final void onChildDismissed(View view) {
            if (view instanceof ActivatableNotificationView) {
                ActivatableNotificationView activatableNotificationView = (ActivatableNotificationView) view;
                if (!activatableNotificationView.mDismissed) {
                    handleChildViewDismissed(view);
                }
                activatableNotificationView.removeFromTransientContainer();
                if (activatableNotificationView instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) activatableNotificationView).removeChildrenWithKeepInParent();
                }
            }
        }

        public final void onDragCancelled(View view) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            notificationStackScrollLayout.getClass();
            if (view instanceof ExpandableNotificationRow) {
                notificationStackScrollLayout.mSwipeCancelledView.add((ExpandableNotificationRow) view);
            }
        }

        public final void onMagneticInteractionEnd(float f, View view) {
            if (view instanceof ExpandableNotificationRow) {
                NotificationStackScrollLayoutController.this.mMagneticNotificationRowManager.onMagneticInteractionEnd((ExpandableNotificationRow) view, Float.valueOf(f));
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$18, reason: invalid class name */
    public class AnonymousClass18 implements RemoteInputController.Delegate {
        public AnonymousClass18() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$4, reason: invalid class name */
    public class AnonymousClass4 extends Property {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$5, reason: invalid class name */
    public class AnonymousClass5 extends Property {
        public AnonymousClass5(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public final Object get(Object obj) {
            return Float.valueOf(((NotificationStackScrollLayoutController) obj).mMaxAlphaForRebind);
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = (NotificationStackScrollLayoutController) obj;
            notificationStackScrollLayoutController.mMaxAlphaForRebind = ((Float) obj2).floatValue();
            notificationStackScrollLayoutController.updateAlpha$1$1();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$8, reason: invalid class name */
    public class AnonymousClass8 implements StatusBarStateController.StateListener {
        public AnonymousClass8() {
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozingChanged(boolean z) {
            if (z) {
                return;
            }
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.getClass();
            int i = 0;
            if (notificationStackScrollLayoutController.mBlockHideAmountVisibility) {
                notificationStackScrollLayoutController.mBlockHideAmountVisibility = false;
                i = 4;
            }
            notificationStackScrollLayoutController.mView.setVisibility(i);
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mBarState = i;
            notificationStackScrollLayoutController.mView.setStatusBarState(i);
            if (i == 1) {
                ((GroupExpansionManagerImpl) NotificationStackScrollLayoutController.this.mGroupExpansionManager).collapseGroups();
            }
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController2.mView.mDndStatus = ((ZenModeControllerImpl) notificationStackScrollLayoutController2.mZenModeController).mZenMode;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController3.mView.mZenModeController = notificationStackScrollLayoutController3.mZenModeController;
            notificationStackScrollLayoutController3.updateShowDndStatusView();
            if (i != 1) {
                Log.d("StackScrollerController", " stateChanged setMaxDisplayedNotifications as -1");
                NotificationStackScrollLayoutController.this.setMaxDisplayedNotifications(-1);
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStatePostChange() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) notificationStackScrollLayoutController.mStatusBarStateController;
            boolean z = true;
            notificationStackScrollLayoutController.updateSensitivenessWithAnimation(statusBarStateControllerImpl.mState == 0 && statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide);
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
                int i2 = StatusBarNoHunBehavior.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                headsUpAppearanceController.updatePinnedStatus();
            }
            notificationStackScrollLayout.mExpandHelper.mEnabled = !onKeyguard;
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.changeViewPosition(notificationStackScrollLayout.mEmptyShadeView, notificationStackScrollLayout.getChildCount() - 1);
            notificationStackScrollLayout.changeViewPosition(notificationStackScrollLayout.mShelf, notificationStackScrollLayout.getChildCount() - 2);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayout.mController;
            if (notificationStackScrollLayout.mAmbientState.isFullyHidden() && notificationStackScrollLayout.onKeyguard()) {
                z = false;
            }
            notificationStackScrollLayoutController2.getClass();
            if (z && notificationStackScrollLayoutController2.mBlockHideAmountVisibility) {
                notificationStackScrollLayoutController2.mBlockHideAmountVisibility = false;
                z = false;
            }
            notificationStackScrollLayoutController2.mView.setVisibility(z ? 0 : 4);
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStatePreChange(int i, int i2) {
            int i3 = SceneContainerFlag.$r8$clinit;
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
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class NotificationListContainerImpl implements NotificationListContainer, PipelineDumpable {
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

        public final void onHeightChanged(ExpandableView expandableView, boolean z) {
            NotificationStackScrollLayoutController.this.mView.onChildHeightChanged(expandableView, z);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TouchHandler implements Gefingerpoken {
        public final QuickPanelLogger mQuickPanelLogger = new QuickPanelLogger("NSSLC");
        public final StringBuilder mQuickPanelLogBuilder = new StringBuilder();
        public final SecStatusBarWindowViewTouchedInteractor mStatusBarWindowViewTouchedInteractor = (SecStatusBarWindowViewTouchedInteractor) Dependency.sDependency.getDependencyInner(SecStatusBarWindowViewTouchedInteractor.class);
        public boolean shelfOnDown = false;
        public boolean panelSliderIntercepted = false;

        public TouchHandler() {
        }

        /* JADX WARN: Removed duplicated region for block: B:106:0x0197 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:109:0x0125  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00ac  */
        /* JADX WARN: Removed duplicated region for block: B:63:0x00ff  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x0123  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x012c A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:86:0x0141  */
        /* JADX WARN: Removed duplicated region for block: B:89:0x0149 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:94:0x015b  */
        @Override // com.android.systemui.Gefingerpoken
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onInterceptTouchEvent(android.view.MotionEvent r13) {
            /*
                Method dump skipped, instructions count: 415
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.TouchHandler.onInterceptTouchEvent(android.view.MotionEvent):boolean");
        }
    }

    /* renamed from: -$$Nest$mupdateEventAvailability, reason: not valid java name */
    public static void m3067$$Nest$mupdateEventAvailability(NotificationStackScrollLayoutController notificationStackScrollLayoutController, MotionEvent motionEvent) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v5, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$6, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r15v7, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$10] */
    /* JADX WARN: Type inference failed for: r15v8, types: [com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$12] */
    /* JADX WARN: Type inference failed for: r15v9, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$13] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda0, java.util.function.IntConsumer] */
    /* JADX WARN: Type inference failed for: r8v7, types: [com.android.systemui.statusbar.NotificationLockscreenUserManager$UserChangedListener, com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$9] */
    public NotificationStackScrollLayoutController(final NotificationStackScrollLayout notificationStackScrollLayout, boolean z, NotificationGutsManager notificationGutsManager, NotificationsController notificationsController, NotificationVisibilityProvider notificationVisibilityProvider, NotificationWakeUpCoordinator notificationWakeUpCoordinator, HeadsUpManager headsUpManager, Provider provider, NotificationRoundnessManager notificationRoundnessManager, TunerService tunerService, DynamicPrivacyController dynamicPrivacyController, ConfigurationController configurationController, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardMediaController keyguardMediaController, KeyguardBypassController keyguardBypassController, PowerInteractor powerInteractor, NotificationLockscreenUserManager notificationLockscreenUserManager, MetricsLogger metricsLogger, ColorUpdateLogger colorUpdateLogger, DumpManager dumpManager, FalsingCollector falsingCollector, FalsingManager falsingManager, NotificationSwipeHelper.Builder builder, GroupExpansionManager groupExpansionManager, NotifPipeline notifPipeline, NotifCollection notifCollection, LockscreenShadeTransitionController lockscreenShadeTransitionController, UiEventLogger uiEventLogger, VisibilityLocationProviderDelegator visibilityLocationProviderDelegator, NotificationListViewBinder notificationListViewBinder, ShadeController shadeController, Provider provider2, InteractionJankMonitor interactionJankMonitor, StackStateLogger stackStateLogger, NotificationStackScrollLogger notificationStackScrollLogger, NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationTargetsHelper notificationTargetsHelper, SecureSettings secureSettings, NotificationDismissibilityProvider notificationDismissibilityProvider, ActivityStarter activityStarter, SplitShadeStateController splitShadeStateController, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, WallpaperInteractor wallpaperInteractor, MagneticNotificationRowManager magneticNotificationRowManager, NotificationSectionsManager notificationSectionsManager, ZenModeController zenModeController, NotificationShelfManager notificationShelfManager, LockscreenNotificationManager lockscreenNotificationManager, Lazy lazy, AppLockNotificationController appLockNotificationController, SecMediaHost secMediaHost, MediaDataManager mediaDataManager, ActiveNotificationsInteractor activeNotificationsInteractor) {
        View.OnAttachStateChangeListener onAttachStateChangeListener;
        final int i = 1;
        ?? r8 = new IntConsumer() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                if (i2 == 4 && notificationStackScrollLayoutController.mHasDelayedForceLayout) {
                    Log.d("StackScrollerController", "do delayed stackScroller forceLayout");
                    ((View) notificationStackScrollLayout2.getParent()).forceLayout();
                    notificationStackScrollLayout2.forceLayout();
                }
                notificationStackScrollLayoutController.mHasDelayedForceLayout = false;
                notificationStackScrollLayout2.removeCallbacks(notificationStackScrollLayoutController.mForceLayoutTimeOutRunnable);
            }
        };
        this.mKeyguardVisibilityListener = r8;
        this.mHasDelayedForceLayout = false;
        this.mMusicItemExpanded = false;
        this.mForceLayoutTimeOutRunnable = new AnonymousClass1();
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mNotificationListContainer = new NotificationListContainerImpl(this, 0);
        View.OnAttachStateChangeListener onAttachStateChangeListener2 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.3
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                NotificationStackScrollLayoutController.this.mColorUpdateLogger.getClass();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).addCallback(notificationStackScrollLayoutController.mConfigurationListener);
                int state = NotificationStackScrollLayoutController.this.mStatusBarStateController.getState();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
                if (state != notificationStackScrollLayoutController2.mBarState) {
                    notificationStackScrollLayoutController2.mStateListener.onStateChanged(state);
                    NotificationStackScrollLayoutController.this.mStateListener.onStatePostChange();
                }
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = NotificationStackScrollLayoutController.this;
                ((ZenModeControllerImpl) notificationStackScrollLayoutController3.mZenModeController).addCallback(notificationStackScrollLayoutController3.mZenModeControllerCallback);
                NotificationShelfManager notificationShelfManager2 = NotificationStackScrollLayoutController.this.mShelfManager;
                ((ConfigurationControllerImpl) notificationShelfManager2.configurationController).addCallback(notificationShelfManager2.configurationListener);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController4 = NotificationStackScrollLayoutController.this;
                SysuiStatusBarStateController sysuiStatusBarStateController2 = notificationStackScrollLayoutController4.mStatusBarStateController;
                AnonymousClass8 anonymousClass8 = notificationStackScrollLayoutController4.mStateListener;
                StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) sysuiStatusBarStateController2;
                synchronized (statusBarStateControllerImpl.mListeners) {
                    statusBarStateControllerImpl.addListenerInternalLocked(anonymousClass8, 2);
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                NotificationStackScrollLayoutController.this.mColorUpdateLogger.getClass();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).removeCallback(notificationStackScrollLayoutController.mConfigurationListener);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController2.mStatusBarStateController.removeCallback(notificationStackScrollLayoutController2.mStateListener);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = NotificationStackScrollLayoutController.this;
                ((ZenModeControllerImpl) notificationStackScrollLayoutController3.mZenModeController).removeCallback(notificationStackScrollLayoutController3.mZenModeControllerCallback);
            }
        };
        this.mOnAttachStateChangeListener = onAttachStateChangeListener2;
        this.mHideAlphaAnimator = null;
        this.mRebindAlphaAnimator = null;
        ?? r15 = new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.6
            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
                notificationStackScrollLayoutController.updateSensitivenessWithAnimation(false);
            }
        };
        this.mSensitiveStateChangedListener = r15;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.7
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
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout2.getClass();
                int i2 = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                ActionBarContextView$$ExternalSyntheticOutline0.m(sb, notificationStackScrollLayout2.mQsFullScreen, "StackScrollerController");
                NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout3.getClass();
                int i3 = ModesEmptyShadeFix.$r8$clinit;
                notificationStackScrollLayout3.inflateEmptyShadeView();
                notificationStackScrollLayout3.inflateDndView();
                notificationStackScrollLayout3.mSectionsManager.reinflateViews();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onOrientationChanged(int i2) {
                ((AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class)).mOrientation = i2;
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                ColorUpdateLogger colorUpdateLogger2 = notificationStackScrollLayoutController.mColorUpdateLogger;
                ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).getClass();
                colorUpdateLogger2.getClass();
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                int dimensionPixelSize = notificationStackScrollLayout2.getResources().getDimensionPixelSize(R.dimen.notification_corner_radius);
                if (notificationStackScrollLayout2.mCornerRadius != dimensionPixelSize) {
                    notificationStackScrollLayout2.mCornerRadius = dimensionPixelSize;
                    notificationStackScrollLayout2.invalidate();
                }
                notificationStackScrollLayoutController.mView.updateBgColor();
                notificationStackScrollLayoutController.mView.updateDecorViews();
                NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout3.getClass();
                int i2 = ModesEmptyShadeFix.$r8$clinit;
                notificationStackScrollLayout3.inflateEmptyShadeView();
                notificationStackScrollLayout3.inflateDndView();
                notificationStackScrollLayout3.mSectionsManager.reinflateViews();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                ColorUpdateLogger colorUpdateLogger2 = notificationStackScrollLayoutController.mColorUpdateLogger;
                ((ConfigurationControllerImpl) notificationStackScrollLayoutController.mConfigurationController).getClass();
                colorUpdateLogger2.getClass();
                notificationStackScrollLayoutController.mView.updateBgColor();
                notificationStackScrollLayoutController.mView.updateDecorViews();
                notificationStackScrollLayoutController.mView.updateSectionColor();
            }
        };
        this.mMaxAlphaForKeyguard = 1.0f;
        this.mMaxAlphaForKeyguardSource = "constructor";
        this.mMaxAlphaForUnhide = 1.0f;
        this.mMaxAlphaForRebind = 1.0f;
        this.mMaxAlphaFromView = 1.0f;
        this.mMaxAlphaForGlanceableHub = 1.0f;
        new ArrayList();
        this.mStateListener = new AnonymousClass8();
        ?? r82 = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.9
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onUserChanged(int i2) {
                AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.updateSensitivenessWithAnimation(false);
                notificationStackScrollLayoutController.getClass();
            }
        };
        this.mLockscreenUserChangeListener = r82;
        ?? r152 = new NotificationMenuRowPlugin.OnMenuEventListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.10
            @Override // com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin.OnMenuEventListener
            public final void onMenuClicked(View view, int i2, int i3, NotificationMenuRowPlugin.MenuItem menuItem) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                if (notificationStackScrollLayoutController.mAllowLongPress) {
                    if (view instanceof ExpandableNotificationRow) {
                        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                        int i4 = NotificationBundleUi.$r8$clinit;
                        if (expandableNotificationRow.getEntryLegacy().mSbn != null) {
                            notificationStackScrollLayoutController.mMetricsLogger.write(expandableNotificationRow.mEntry.mSbn.getLogMaker().setCategory(333).setType(4));
                        }
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
                    int i2 = NotificationBundleUi.$r8$clinit;
                    StatusBarNotification statusBarNotification = expandableNotificationRow.getEntryLegacy().mSbn;
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                    if (statusBarNotification != null) {
                        notificationStackScrollLayoutController.mMetricsLogger.write(expandableNotificationRow.mEntry.mSbn.getLogMaker().setCategory(CustomDeviceManager.DESTINATION_ADDRESS).setType(4));
                    }
                    NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayoutController.mSwipeHelper;
                    notificationSwipeHelper.mMenuExposedView = notificationSwipeHelper.mTranslatingParentView;
                    AnonymousClass11 anonymousClass11 = (AnonymousClass11) notificationSwipeHelper.mCallback;
                    anonymousClass11.onDragCancelled(view);
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
                        notificationStackScrollLayoutController.mSwipeHelper.resetExposedMenuView$1(false, true);
                    }
                }
            }
        };
        this.mMenuEventListener = r152;
        AnonymousClass11 anonymousClass11 = new AnonymousClass11();
        this.mNotificationCallback = anonymousClass11;
        ?? r153 = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.12
            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z2) {
                int i2 = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
                notificationStackScrollLayout2.mInHeadsUpPinnedMode = z2;
                notificationStackScrollLayout2.updateClipping$1();
            }

            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z2) {
                int i2 = SceneContainerFlag.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                HeadsUpManagerImpl.HeadsUpEntry topHeadsUpEntry = ((HeadsUpManagerImpl) notificationStackScrollLayoutController.mHeadsUpManager).getTopHeadsUpEntry();
                NotificationEntry notificationEntry2 = topHeadsUpEntry != null ? topHeadsUpEntry.mEntry : null;
                NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
                notificationStackScrollLayout2.mTopHeadsUpRow = notificationEntry2 != null ? notificationEntry2.row : null;
                Iterator it = notificationStackScrollLayout2.mHeadsUpHeightChangedListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
                notificationStackScrollLayoutController.generateHeadsUpAnimation(notificationEntry, z2);
            }
        };
        this.mOnHeadsUpChangedListener = r153;
        this.mZenModeControllerCallback = new ZenModeController.Callback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.13
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onConfigChanged(ZenModeConfig zenModeConfig) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.mView.mDndStatus = ((ZenModeControllerImpl) notificationStackScrollLayoutController.mZenModeController).mZenMode;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController2.mView.mZenModeController = notificationStackScrollLayoutController2.mZenModeController;
                notificationStackScrollLayoutController2.updateShowDndStatusView();
            }

            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i2) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.mView.mDndStatus = ((ZenModeControllerImpl) notificationStackScrollLayoutController.mZenModeController).mZenMode;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController2.mView.mZenModeController = notificationStackScrollLayoutController2.mZenModeController;
                notificationStackScrollLayoutController2.updateShowDndStatusView();
                if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;
                    SubscreenSubRoomNotification subRoomNotification = subscreenDeviceModelParent != null ? subscreenDeviceModelParent.getSubRoomNotification() : null;
                    SubscreenSubRoomNotification subscreenSubRoomNotification = subRoomNotification != null ? subRoomNotification : null;
                    if (subscreenSubRoomNotification == null || subscreenSubRoomNotification.mIsShownDetail || subscreenSubRoomNotification.mIsShownGroup) {
                        return;
                    }
                    subscreenSubRoomNotification.setListAdpater();
                }
            }
        };
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.14
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
        this.mWallpaperThemeCallback = onChangedCallback;
        SettingsHelper.OnChangedCallback onChangedCallback2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.15
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && uri.equals(Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL))) {
                    NotificationStackScrollLayoutController.this.mView.requestLayout();
                }
            }
        };
        this.mSplitCallback = onChangedCallback2;
        this.mReduceTransparencyAndBlurCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.16
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY))) {
                    NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
                    boolean isReduceTransparencyEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled();
                    notificationColorPicker.mReduceTransparencyAndBlurOn = isReduceTransparencyEnabled;
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                    notificationStackScrollLayoutController.mView.updateBgColor();
                    NotifCollection notifCollection2 = notificationStackScrollLayoutController.mNotifCollection;
                    notifCollection2.getClass();
                    Assert.isMainThread();
                    Iterator it = notifCollection2.mReadOnlyNotificationSet.iterator();
                    while (it.hasNext()) {
                        ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
                        if (expandableNotificationRow != null) {
                            notificationColorPicker.updateAllTextViewColors(expandableNotificationRow, expandableNotificationRow.mDimmed);
                            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                            if (notificationChildrenContainer != null) {
                                notificationChildrenContainer.mReduceTransparencyAndBlurOn = isReduceTransparencyEnabled;
                                if (notificationChildrenContainer.mGroupHeader != null) {
                                    ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateHeader(notificationChildrenContainer.mGroupHeader, notificationChildrenContainer.mContainingNotification, true);
                                }
                                if (notificationChildrenContainer.mMinimizedGroupHeader != null) {
                                    ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateHeader(notificationChildrenContainer.mMinimizedGroupHeader, notificationChildrenContainer.mContainingNotification, true);
                                }
                                if (notificationChildrenContainer.mNotificationHeaderExpanded != null) {
                                    ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).updateHeader(notificationChildrenContainer.mNotificationHeaderExpanded, notificationChildrenContainer.mContainingNotification, false);
                                }
                            }
                        }
                    }
                }
            }
        };
        this.mIsStartFromContentsBound = false;
        this.mView = notificationStackScrollLayout;
        this.mLogger = notificationStackScrollLogger;
        this.mAllowLongPress = z;
        this.mNotificationGutsManager = notificationGutsManager;
        this.mNotificationsController = notificationsController;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mHeadsUpManager = headsUpManager;
        int i2 = SceneContainerFlag.$r8$clinit;
        this.mNotificationRoundnessManager = notificationRoundnessManager;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mConfigurationController = configurationController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mPowerInteractor = powerInteractor;
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
        this.mShadeController = shadeController;
        this.mLockscreenNotificationManager = lockscreenNotificationManager;
        this.mNotificationTargetsHelper = notificationTargetsHelper;
        this.mSecureSettings = secureSettings;
        this.mDismissibilityProvider = notificationDismissibilityProvider;
        this.mActivityStarter = activityStarter;
        this.mSensitiveNotificationProtectionController = sensitiveNotificationProtectionController;
        notificationStackScrollLayout.mSplitShadeStateController = splitShadeStateController;
        notificationStackScrollLayout.updateSplitNotificationShade();
        this.mMagneticNotificationRowManager = magneticNotificationRowManager;
        this.mSectionsManager = notificationSectionsManager;
        dumpManager.registerDumpable(this);
        this.mShelfManager = notificationShelfManager;
        ((KeyguardVisibilityMonitor) Dependency.sDependency.getDependencyInner(KeyguardVisibilityMonitor.class)).addVisibilityChangedListener(r8);
        KeyguardFoldControllerImpl keyguardFoldControllerImpl = (KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class));
        if (!((ArrayList) keyguardFoldControllerImpl.foldOpenModeListeners).contains(anonymousClass2)) {
            ((ArrayList) keyguardFoldControllerImpl.foldOpenModeListeners).add(anonymousClass2);
        }
        ((NotificationSectionsManager) Dependency.sDependency.getDependencyInner(NotificationSectionsManager.class)).sectionStateProvider = this;
        if (NotiRune.NOTI_STYLE_APP_LOCK) {
            this.mAppLockNotificationController = appLockNotificationController;
        }
        this.mZenModeController = zenModeController;
        this.mActiveNotificationsInteractor = activeNotificationsInteractor;
        notificationStackSizeCalculator.updateResources();
        notificationStackScrollLayout.mStateAnimator.mLogger = stackStateLogger;
        notificationStackScrollLayout.mController = this;
        notificationRoundnessManager.mAnimatedChildren = notificationStackScrollLayout.mChildrenToAddAnimated;
        notificationStackScrollLayout.mLogger = notificationStackScrollLogger;
        TouchHandler touchHandler = new TouchHandler();
        this.mTouchHandler = touchHandler;
        notificationStackScrollLayout.mTouchHandler = touchHandler;
        Objects.requireNonNull(notificationsController);
        notificationStackScrollLayout.mResetUserExpandedStatesRunnable = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(notificationsController, 4);
        notificationStackScrollLayout.mActivityStarter = activityStarter;
        notificationStackScrollLayout.mClearAllAnimationListener = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda4(this);
        notificationStackScrollLayout.mClearAllListener = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda4(this);
        notificationStackScrollLayout.mShelfManager = notificationShelfManager;
        notificationStackScrollLayout.mClearAllFinishedWhilePanelExpandedRunnable = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(this, 0);
        notificationStackScrollLayout.mCollapseShadeDelayedWhenNoViewsToAnimateAwayRunnable = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda6(this, 3);
        dumpManager.registerDumpable(notificationStackScrollLayout);
        final int i3 = 0;
        keyguardBypassController.registerOnBypassStateChangedListener(new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda8
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z2) {
                switch (i3) {
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
        builder.mNotificationCallback = anonymousClass11;
        builder.mOnMenuEventListener = r152;
        NotificationSwipeHelper notificationSwipeHelper = new NotificationSwipeHelper(builder.mResources, builder.mViewConfiguration, builder.mFalsingManager, builder.mFeatureFlags, builder.mNotificationCallback, builder.mOnMenuEventListener, builder.mNotificationRoundnessManager);
        builder.mDumpManager.registerDumpable(notificationSwipeHelper);
        this.mSwipeHelper = notificationSwipeHelper;
        notifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.17
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
        notificationStackScrollLayout.updateDndView(((ZenModeControllerImpl) zenModeController).mZenMode, notificationStackScrollLayout.getDndStatusText(zenModeController));
        notificationStackScrollLayout.mKeyguardBypassEnabled = keyguardBypassController.getBypassEnabled();
        keyguardBypassController.registerOnBypassStateChangedListener(new KeyguardBypassController.OnBypassStateChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda8
            @Override // com.android.systemui.statusbar.phone.KeyguardBypassController.OnBypassStateChangedListener
            public final void onBypassStateChanged(boolean z2) {
                switch (i) {
                    case 0:
                        ((NotificationStackScrollLayoutController) notificationStackScrollLayout).mNotificationRoundnessManager.mRoundForPulsingViews = !z2;
                        break;
                    default:
                        ((NotificationStackScrollLayout) notificationStackScrollLayout).mKeyguardBypassEnabled = z2;
                        break;
                }
            }
        });
        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) headsUpManager;
        headsUpManagerImpl.addListener(r153);
        headsUpManagerImpl.mAnimationStateHandler = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda4(notificationStackScrollLayout);
        lockscreenShadeTransitionController.nsslController = this;
        NotificationStackScrollLayout.AnonymousClass11 anonymousClass112 = notificationStackScrollLayout.mExpandHelperCallback;
        DragDownHelper dragDownHelper = lockscreenShadeTransitionController.touchHelper;
        dragDownHelper.expandCallback = anonymousClass112;
        dragDownHelper.notificationStackScrollLayoutController = this;
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager;
        notificationLockscreenUserManagerImpl.addUserChangedListener(r82);
        notificationLockscreenUserManagerImpl.mNotifStateChangedListeners.addIfAbsent(new NotificationLockscreenUserManager.NotificationStateChangedListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda11
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.NotificationStateChangedListener
            public final void onNotificationStateChanged() {
                NotificationStackScrollLayoutController.AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
                NotificationStackScrollLayoutController.this.updateSensitivenessWithAnimation(false);
            }
        });
        visibilityLocationProviderDelegator.delegate = new NotificationStackScrollLayoutController$$ExternalSyntheticLambda12(this);
        tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda2
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                NotificationStackScrollLayoutController.AnonymousClass4 anonymousClass4 = NotificationStackScrollLayoutController.HIDE_ALPHA_PROPERTY;
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                notificationStackScrollLayoutController.getClass();
                str.getClass();
                if (str.equals("high_priority")) {
                    notificationStackScrollLayoutController.mView.mHighPriorityBeforeSpeedBump = "1".equals(str2);
                }
            }
        }, "high_priority", SettingsHelper.INDEX_COVER_SCREEN_NOTIFICATION_HISTORY);
        keyguardMediaController.getClass();
        ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).mListeners.addIfAbsent(r15);
        if (notificationStackScrollLayout.isAttachedToWindow()) {
            onAttachStateChangeListener = onAttachStateChangeListener2;
            onAttachStateChangeListener.onViewAttachedToWindow(notificationStackScrollLayout);
        } else {
            onAttachStateChangeListener = onAttachStateChangeListener2;
        }
        notificationStackScrollLayout.addOnAttachStateChangeListener(onAttachStateChangeListener);
        ((HashSet) ((GroupExpansionManagerImpl) groupExpansionManager).mOnGroupChangeListeners).add(new NotificationStackScrollLayoutController$$ExternalSyntheticLambda3(this));
        notificationListViewBinder.bindWhileAttached(notificationStackScrollLayout, this);
        notificationStackScrollLayout.mMediaHost = secMediaHost;
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(onChangedCallback, Settings.Global.getUriFor(SettingsHelper.INDEX_NOTI_POLICY_APPLY_WALPAPER_THEME));
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(onChangedCallback2, Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL));
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mReduceTransparencyAndBlurCallback, Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY));
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
        CarrierTextController$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("mMaxAlphaFromView="), this.mMaxAlphaFromView, printWriter, "mMaxAlphaForUnhide="), this.mMaxAlphaForUnhide, printWriter, "mMaxAlphaForRebind="), this.mMaxAlphaForRebind, printWriter, "mMaxAlphaForGlanceableHub="), this.mMaxAlphaForGlanceableHub, printWriter, "mMaxAlphaForKeyguard="), this.mMaxAlphaForKeyguard, printWriter, "mMaxAlphaForKeyguardSource="), this.mMaxAlphaForKeyguardSource, printWriter);
    }

    public final void generateHeadsUpAnimation(NotificationEntry notificationEntry, boolean z) {
        int i = StatusBarNotifChips.$r8$clinit;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        notificationStackScrollLayout.getClass();
        int i2 = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        boolean z2 = notificationStackScrollLayout.mAnimationsEnabled && (z || notificationStackScrollLayout.mHeadsUpGoingAwayAnimationsAllowed);
        NotificationThrottleHun notificationThrottleHun = NotificationThrottleHun.INSTANCE;
        if (z2) {
            boolean z3 = ((HashMap) notificationStackScrollLayout.mHeadsUpChangeAnimations).containsKey(expandableNotificationRow) && ((HeadsUpAnimationEvent) ((HashMap) notificationStackScrollLayout.mHeadsUpChangeAnimations).get(expandableNotificationRow)).isHeadsUpAppearance;
            if (!z && z3) {
                ((HashMap) notificationStackScrollLayout.mHeadsUpChangeAnimations).remove(expandableNotificationRow);
                notificationStackScrollLayout.logHunAnimationSkipped(expandableNotificationRow, "previous hun appear animation cancelled");
                return;
            }
            ((HashMap) notificationStackScrollLayout.mHeadsUpChangeAnimations).put(expandableNotificationRow, new HeadsUpAnimationEvent(expandableNotificationRow, z, false));
            notificationStackScrollLayout.mNeedsAnimation = true;
            if (!notificationStackScrollLayout.mIsExpanded && !notificationStackScrollLayout.mWillExpand && !z) {
                expandableNotificationRow.setHeadsUpAnimatingAway(true);
                int i3 = SceneContainerFlag.$r8$clinit;
            }
            int i4 = StatusBarNotifChips.$r8$clinit;
            notificationStackScrollLayout.requestChildrenUpdate();
        }
    }

    public final int getNotGoneChildCount() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        notificationStackScrollLayout.getClass();
        int childCount = notificationStackScrollLayout.getChildCount();
        int i2 = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i3);
            if (expandableView.getVisibility() != 8 && !expandableView.mWillBeGone && expandableView != notificationStackScrollLayout.mShelf) {
                i2++;
            }
        }
        return i2;
    }

    public TouchHandler getTouchHandler() {
        return this.mTouchHandler;
    }

    public final float getWidth() {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return this.mView.getWidth();
    }

    public final boolean isInContentBounds$2(float f) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        float f2 = notificationStackScrollLayout.mSidePaddings;
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        float x = notificationStackScrollLayout.getX() + f2;
        return x < f && f < (getWidth() + x) - (f2 * 2.0f);
    }

    public final boolean isLeftOrRightOutOfNSSL(float f) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        return f < notificationStackScrollLayout.getX() + ((float) notificationStackScrollLayout.mSidePaddings) || f > (notificationStackScrollLayout.getX() + ((float) notificationStackScrollLayout.getWidth())) - ((float) notificationStackScrollLayout.mSidePaddings);
    }

    public final void setMaxDisplayedNotifications(int i) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
        int i2 = notificationStackScrollLayoutController.mBarState;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        if (i2 != 1 || notificationStackScrollLayout.mAmbientState.mDragDownOnKeyguard || notificationStackScrollLayoutController.mKeyguardBypassController.getBypassEnabled()) {
            notificationStackScrollLayout.setMaxDisplayedNotifications(-1);
        } else {
            notificationStackScrollLayout.setMaxDisplayedNotifications(i);
        }
    }

    public final void setOverExpansion(float f) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        notificationStackScrollLayout.mAmbientState.mOverExpansion = f;
        notificationStackScrollLayout.mStackScrollAlgorithm.mOverExpansionAmount = f;
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        notificationStackScrollLayout.updateStackPosition(false);
        notificationStackScrollLayout.requestChildrenUpdate();
    }

    public final void setOverScrollAmount(int i) {
        float f = i;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        notificationStackScrollLayout.mExtraTopInsetForFullShadeTransition = f;
        notificationStackScrollLayout.mAmbientState.mExtraTopInsetForFullShadeTransition = f;
        notificationStackScrollLayout.updateStackPosition(false);
        notificationStackScrollLayout.requestChildrenUpdate();
    }

    public final void setPanelFlinging(boolean z) {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        notificationStackScrollLayout.getClass();
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        ambientState.getClass();
        if (ambientState.isOnKeyguard$1() && !z && ambientState.mIsFlinging) {
            ambientState.mIsFlingRequiredAfterLockScreenSwipeUp = false;
        }
        ambientState.mIsFlinging = z;
        if (z) {
            return;
        }
        notificationStackScrollLayout.updateStackPosition(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0049, code lost:
    
        if (r0.getHeight() != 0) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setQsExpansionFraction(float r8) {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.setQsExpansionFraction(float):void");
    }

    public final void updateAlpha$1$1() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        if (notificationStackScrollLayout != null) {
            notificationStackScrollLayout.setAlpha(Math.min(this.mMaxAlphaForRebind, Math.min(Math.min(this.mMaxAlphaFromView, this.mMaxAlphaForKeyguard), Math.min(this.mMaxAlphaForUnhide, this.mMaxAlphaForGlanceableHub))));
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
        boolean z2;
        Trace.beginSection("NSSLC.updateSensitivenessWithAnimation");
        boolean z3 = NotiRune.NOTI_STYLE_APP_LOCK;
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        SensitiveNotificationProtectionController sensitiveNotificationProtectionController = this.mSensitiveNotificationProtectionController;
        NotificationLockscreenUserManager notificationLockscreenUserManager = this.mLockscreenUserManager;
        if (z3) {
            boolean isAnyProfilePublicMode = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isAnyProfilePublicMode();
            boolean isSensitiveStateActive = ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).isSensitiveStateActive();
            boolean isAppLockEnabled = ((AppLockNotificationControllerImpl) this.mAppLockNotificationController).isAppLockEnabled();
            boolean z4 = isAnyProfilePublicMode || isSensitiveStateActive || isAppLockEnabled;
            z2 = z && !isSensitiveStateActive;
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m(" updateSensitivenessWithAnimation isSensitive :", " isAppLockEnabled:", "ALNM-NSSLC", z4, isAppLockEnabled);
            notificationStackScrollLayout.updateSensitiveness(z2, z4);
        } else {
            boolean isAnyProfilePublicMode2 = ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).isAnyProfilePublicMode();
            boolean isSensitiveStateActive2 = ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).isSensitiveStateActive();
            boolean z5 = isAnyProfilePublicMode2 || isSensitiveStateActive2;
            z2 = z && !isSensitiveStateActive2;
            NotificationStackScrollLogger notificationStackScrollLogger = this.mLogger;
            notificationStackScrollLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotificationStackScrollLogger$$ExternalSyntheticLambda0 notificationStackScrollLogger$$ExternalSyntheticLambda0 = new NotificationStackScrollLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer = notificationStackScrollLogger.notificationRenderBuffer;
            LogMessage obtain = logBuffer.obtain("NotificationStackScroll", logLevel, notificationStackScrollLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.bool1 = z2;
            logMessageImpl.bool2 = z5;
            logMessageImpl.bool3 = isSensitiveStateActive2;
            logMessageImpl.bool4 = isAnyProfilePublicMode2;
            logBuffer.commit(obtain);
            notificationStackScrollLayout.updateSensitiveness(z2, z5);
        }
        Trace.endSection();
    }

    public final void updateShowDndStatusView() {
        this.mView.updateDndView(((ZenModeControllerImpl) this.mZenModeController).mZenMode, this.mView.getDndStatusText(this.mZenModeController));
    }
}
