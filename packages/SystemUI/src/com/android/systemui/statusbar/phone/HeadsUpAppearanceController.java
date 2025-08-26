package com.android.systemui.statusbar.phone;

import android.util.MathUtils;
import android.view.View;
import android.widget.TextView;
import com.android.internal.widget.ViewClippingUtil;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.HeadsUpStatusBarView;
import com.android.systemui.statusbar.chips.notification.shared.StatusBarNotifChips;
import com.android.systemui.statusbar.core.StatusBarRootModernization;
import com.android.systemui.statusbar.headsup.shared.StatusBarNoHunBehavior;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationIconInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.headsup.PinnedStatus;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class HeadsUpAppearanceController extends ViewController implements OnHeadsUpChangedListener, DarkIconDispatcher.DarkReceiver, NotificationWakeUpCoordinator.WakeUpListener {
    public static final SourceType$Companion$from$1 HEADS_UP = SourceType.from("HeadsUp");
    public static final SourceType$Companion$from$1 PULSING = SourceType.from("Pulsing");
    public boolean mAnimationsEnabled;
    float mAppearFraction;
    public final KeyguardBypassController mBypassController;
    public final Optional mCarrierLogoFrameView;
    public final View mClockView;
    public final CommandQueue mCommandQueue;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass2 mConfigurationListener;
    public final DarkIconDispatcher mDarkIconDispatcher;
    float mExpandedHeight;
    public final HeadsUpManager mHeadsUpManager;
    public final HeadsUpNotificationIconInteractor mHeadsUpNotificationIconInteractor;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public final NotificationRoundnessManager mNotificationRoundnessManager;
    public final Optional mOperatorNameViewOptional;
    public final AnonymousClass1 mParentClippingParams;
    public final PhoneStatusBarTransitions mPhoneStatusBarTransitions;
    public PinnedStatus mPinnedStatus;
    public final HeadsUpAppearanceController$$ExternalSyntheticLambda5 mSetExpandedHeight;
    public final HeadsUpAppearanceController$$ExternalSyntheticLambda0 mSetTrackingHeadsUp;
    public final ShadeViewController mShadeViewController;
    public final NotificationStackScrollLayoutController mStackScrollerController;
    public final StatusBarStateController mStatusBarStateController;
    public ExpandableNotificationRow mTrackedChild;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda5] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.phone.HeadsUpAppearanceController$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.statusbar.phone.HeadsUpAppearanceController$2] */
    public HeadsUpAppearanceController(NotificationIconAreaController notificationIconAreaController, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, PhoneStatusBarTransitions phoneStatusBarTransitions, KeyguardBypassController keyguardBypassController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, DarkIconDispatcher darkIconDispatcher, KeyguardStateController keyguardStateController, CommandQueue commandQueue, NotificationStackScrollLayoutController notificationStackScrollLayoutController, ShadeViewController shadeViewController, NotificationRoundnessManager notificationRoundnessManager, HeadsUpStatusBarView headsUpStatusBarView, View view, Optional<View> optional, HeadsUpNotificationIconInteractor headsUpNotificationIconInteractor, Optional<View> optional2, IndicatorGardenPresenter indicatorGardenPresenter, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener) {
        super(headsUpStatusBarView);
        this.mSetTrackingHeadsUp = new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, 3);
        this.mSetExpandedHeight = new BiConsumer() { // from class: com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                HeadsUpAppearanceController headsUpAppearanceController = this.f$0;
                float fFloatValue = ((Float) obj).floatValue();
                float fFloatValue2 = ((Float) obj2).floatValue();
                boolean z = fFloatValue != headsUpAppearanceController.mExpandedHeight;
                boolean zIsExpanded$1 = headsUpAppearanceController.isExpanded$1();
                headsUpAppearanceController.mExpandedHeight = fFloatValue;
                headsUpAppearanceController.mAppearFraction = fFloatValue2;
                if (z) {
                    ((HeadsUpManagerImpl) headsUpAppearanceController.mHeadsUpManager).getAllEntries().forEach(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(headsUpAppearanceController, 4));
                }
                if (headsUpAppearanceController.isExpanded$1() != zIsExpanded$1) {
                    headsUpAppearanceController.updatePinnedStatus();
                }
            }
        };
        this.mPinnedStatus = PinnedStatus.NotPinned;
        this.mParentClippingParams = new ViewClippingUtil.ClippingParameters(this) { // from class: com.android.systemui.statusbar.phone.HeadsUpAppearanceController.1
            public final boolean shouldFinish(View view2) {
                return view2.getId() == R.id.status_bar;
            }
        };
        this.mAnimationsEnabled = true;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.HeadsUpAppearanceController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                HeadsUpAppearanceController headsUpAppearanceController = HeadsUpAppearanceController.this;
                ((TextView) ((HeadsUpStatusBarView) ((ViewController) headsUpAppearanceController).mView).findViewById(R.id.text)).setTextSize(0, headsUpAppearanceController.getResources().getDimensionPixelSize(R.dimen.heads_up_status_bar_text_size) * headsUpAppearanceController.mIndicatorScaleGardener.getLatestScaleModel(((HeadsUpStatusBarView) ((ViewController) headsUpAppearanceController).mView).getContext()).ratio);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDisplayDeviceTypeChanged() {
                onDensityOrFontScaleChanged();
            }
        };
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mNotificationRoundnessManager = notificationRoundnessManager;
        this.mHeadsUpManager = headsUpManager;
        this.mTrackedChild = shadeViewController.getShadeHeadsUpTracker$1().getTrackedHeadsUpNotification();
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        this.mAppearFraction = notificationStackScrollLayout.mLastSentAppear;
        this.mExpandedHeight = notificationStackScrollLayout.mLastSentExpandedHeight;
        this.mStackScrollerController = notificationStackScrollLayoutController;
        this.mShadeViewController = shadeViewController;
        this.mHeadsUpNotificationIconInteractor = headsUpNotificationIconInteractor;
        notificationStackScrollLayoutController.mHeadsUpAppearanceController = this;
        notificationStackScrollLayout.mHeadsUpAppearanceController = this;
        this.mClockView = view;
        this.mOperatorNameViewOptional = optional2;
        this.mCarrierLogoFrameView = optional;
        this.mDarkIconDispatcher = darkIconDispatcher;
        int i = StatusBarNoHunBehavior.$r8$clinit;
        ((HeadsUpStatusBarView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.HeadsUpAppearanceController.3
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                if (HeadsUpAppearanceController.this.shouldHeadsUpStatusBarBeVisible()) {
                    HeadsUpAppearanceController.this.updatePinnedStatus();
                    HeadsUpAppearanceController.this.mStackScrollerController.mView.requestLayout();
                }
                ((HeadsUpStatusBarView) ((ViewController) HeadsUpAppearanceController.this).mView).removeOnLayoutChangeListener(this);
            }
        });
        this.mBypassController = keyguardBypassController;
        this.mStatusBarStateController = statusBarStateController;
        this.mPhoneStatusBarTransitions = phoneStatusBarTransitions;
        this.mWakeUpCoordinator = notificationWakeUpCoordinator;
        this.mCommandQueue = commandQueue;
        this.mKeyguardStateController = keyguardStateController;
        this.mIndicatorGardenPresenter = indicatorGardenPresenter;
        this.mConfigurationController = configurationController;
        this.mIndicatorScaleGardener = indicatorScaleGardener;
    }

    public PinnedStatus getPinnedStatus() {
        int i = StatusBarNoHunBehavior.$r8$clinit;
        return this.mPinnedStatus;
    }

    public final void hide(final View view, final int i, final HeadsUpAppearanceController$$ExternalSyntheticLambda3 headsUpAppearanceController$$ExternalSyntheticLambda3) {
        int i2 = StatusBarNoHunBehavior.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (this.mAnimationsEnabled) {
            CrossFadeHelper.fadeOut(110L, view, new Runnable() { // from class: com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    View view2 = view;
                    int i3 = i;
                    HeadsUpAppearanceController$$ExternalSyntheticLambda3 headsUpAppearanceController$$ExternalSyntheticLambda32 = headsUpAppearanceController$$ExternalSyntheticLambda3;
                    SourceType$Companion$from$1 sourceType$Companion$from$1 = HeadsUpAppearanceController.HEADS_UP;
                    view2.setVisibility(i3);
                    if (headsUpAppearanceController$$ExternalSyntheticLambda32 != null) {
                        headsUpAppearanceController$$ExternalSyntheticLambda32.run();
                    }
                }
            });
            return;
        }
        view.setVisibility(i);
        if (headsUpAppearanceController$$ExternalSyntheticLambda3 != null) {
            headsUpAppearanceController$$ExternalSyntheticLambda3.run();
        }
    }

    public final boolean isExpanded$1() {
        return this.mExpandedHeight > 0.0f;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        int i2 = StatusBarNoHunBehavior.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        HeadsUpStatusBarView headsUpStatusBarView = (HeadsUpStatusBarView) this.mView;
        headsUpStatusBarView.mTextView.setTextColor(DarkIconDispatcher.getTint(arrayList, headsUpStatusBarView, i));
    }

    @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
    public final void onFullyHiddenChanged(boolean z) {
        int i = StatusBarNoHunBehavior.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        updatePinnedStatus();
    }

    @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
    public final void onHeadsUpPinned(NotificationEntry notificationEntry) {
        updatePinnedStatus();
        updateHeader(notificationEntry.row);
        updateHeadsUpAndPulsingRoundness(notificationEntry.row);
    }

    @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        updateHeadsUpAndPulsingRoundness(notificationEntry.row);
        PhoneStatusBarTransitions phoneStatusBarTransitions = this.mPhoneStatusBarTransitions;
        phoneStatusBarTransitions.mIsHeadsUp = z;
        phoneStatusBarTransitions.applyMode(phoneStatusBarTransitions.mMode, false);
    }

    @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
    public final void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
        updatePinnedStatus();
        updateHeader(notificationEntry.row);
        updateHeadsUpAndPulsingRoundness(notificationEntry.row);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((HeadsUpManagerImpl) this.mHeadsUpManager).addListener(this);
        int i = StatusBarNoHunBehavior.$r8$clinit;
        ((HeadsUpStatusBarView) this.mView).mOnDrawingRectChangedListener = new HeadsUpAppearanceController$$ExternalSyntheticLambda3(this, 1);
        int i2 = NotificationIconContainerRefactor.$r8$clinit;
        this.mDarkIconDispatcher.addDarkReceiver(this);
        this.mWakeUpCoordinator.wakeUpListeners.add(this);
        ShadeViewController shadeViewController = this.mShadeViewController;
        shadeViewController.getShadeHeadsUpTracker$1().addTrackingHeadsUpListener(this.mSetTrackingHeadsUp);
        shadeViewController.getShadeHeadsUpTracker$1().setHeadsUpAppearanceController(this);
        this.mStackScrollerController.mView.mExpandedHeightListeners.add(this.mSetExpandedHeight);
        this.mIndicatorGardenPresenter.headsUpAppearanceController = this;
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((HeadsUpManagerImpl) this.mHeadsUpManager).removeListener(this);
        int i = StatusBarNoHunBehavior.$r8$clinit;
        ((HeadsUpStatusBarView) this.mView).mOnDrawingRectChangedListener = null;
        HeadsUpNotificationIconInteractor headsUpNotificationIconInteractor = this.mHeadsUpNotificationIconInteractor;
        headsUpNotificationIconInteractor.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        headsUpNotificationIconInteractor.repository.isolatedIconLocation.setValue(null);
        this.mDarkIconDispatcher.removeDarkReceiver(this);
        this.mWakeUpCoordinator.wakeUpListeners.remove(this);
        ShadeViewController shadeViewController = this.mShadeViewController;
        shadeViewController.getShadeHeadsUpTracker$1().removeTrackingHeadsUpListener(this.mSetTrackingHeadsUp);
        shadeViewController.getShadeHeadsUpTracker$1().setHeadsUpAppearanceController(null);
        this.mStackScrollerController.mView.mExpandedHeightListeners.remove(this.mSetExpandedHeight);
        this.mIndicatorGardenPresenter.headsUpAppearanceController = null;
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    public void setAnimationsEnabled(boolean z) {
        this.mAnimationsEnabled = z;
    }

    public final void setPinnedStatus(PinnedStatus pinnedStatus) {
        int i = 2;
        int i2 = 0;
        int i3 = StatusBarNoHunBehavior.$r8$clinit;
        if (this.mPinnedStatus != pinnedStatus) {
            this.mPinnedStatus = pinnedStatus;
            int i4 = StatusBarNotifChips.$r8$clinit;
            if (pinnedStatus.isPinned()) {
                updateParentClipping(false);
                ((HeadsUpStatusBarView) this.mView).setVisibility(0);
                show(this.mView);
                int i5 = StatusBarRootModernization.$r8$clinit;
                hide(this.mClockView, 4, null);
                this.mCarrierLogoFrameView.ifPresent(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, i2));
                this.mOperatorNameViewOptional.ifPresent(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, 1));
            } else {
                int i6 = StatusBarRootModernization.$r8$clinit;
                show(this.mClockView);
                this.mCarrierLogoFrameView.ifPresent(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, i));
                this.mOperatorNameViewOptional.ifPresent(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, i));
                hide(this.mView, 8, new HeadsUpAppearanceController$$ExternalSyntheticLambda3(this, 0));
            }
            if (this.mStatusBarStateController.getState() != 0) {
                this.mCommandQueue.recomputeDisableFlags(((HeadsUpStatusBarView) this.mView).getContext().getDisplayId(), false);
            }
        }
    }

    public final boolean shouldHeadsUpStatusBarBeVisible() {
        int i = StatusBarNoHunBehavior.$r8$clinit;
        int i2 = StatusBarNotifChips.$r8$clinit;
        boolean z = this.mWakeUpCoordinator.notificationsFullyHidden;
        return ((this.mBypassController.getBypassEnabled() && ((this.mStatusBarStateController.getState() == 1 || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) && !z)) || !(isExpanded$1() || z)) && ((HeadsUpManagerImpl) this.mHeadsUpManager).mHasPinnedNotification;
    }

    public final void show(View view) {
        int i = StatusBarNoHunBehavior.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        if (this.mAnimationsEnabled) {
            CrossFadeHelper.fadeIn(view, 110L, 100);
        } else {
            view.setVisibility(0);
        }
    }

    public final void updateHeader(ExpandableNotificationRow expandableNotificationRow) {
        int i = AsyncGroupHeaderViewInflation.$r8$clinit;
        float f = (expandableNotificationRow.mPinnedStatus.isPinned() || expandableNotificationRow.mHeadsupDisappearRunning || expandableNotificationRow == this.mTrackedChild || expandableNotificationRow.showingPulsing()) ? this.mAppearFraction : 1.0f;
        if (expandableNotificationRow.mHeaderVisibleAmount != f) {
            expandableNotificationRow.mHeaderVisibleAmount = f;
            for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
                NotificationViewWrapper notificationViewWrapper = notificationContentView.mContractedWrapper;
                if (notificationViewWrapper != null) {
                    notificationViewWrapper.setHeaderVisibleAmount(f);
                }
                NotificationViewWrapper notificationViewWrapper2 = notificationContentView.mHeadsUpWrapper;
                if (notificationViewWrapper2 != null) {
                    notificationViewWrapper2.setHeaderVisibleAmount(f);
                }
                NotificationViewWrapper notificationViewWrapper3 = notificationContentView.mExpandedWrapper;
                if (notificationViewWrapper3 != null) {
                    notificationViewWrapper3.setHeaderVisibleAmount(f);
                }
            }
            expandableNotificationRow.notifyHeightChanged(false);
        }
    }

    public final void updateHeadsUpAndPulsingRoundness(ExpandableNotificationRow expandableNotificationRow) {
        boolean z = expandableNotificationRow == this.mTrackedChild;
        boolean zIsPinned = expandableNotificationRow.mPinnedStatus.isPinned();
        SourceType$Companion$from$1 sourceType$Companion$from$1 = HEADS_UP;
        if (zIsPinned || expandableNotificationRow.mHeadsupDisappearRunning || z) {
            float fSaturate = MathUtils.saturate(1.0f - this.mAppearFraction);
            expandableNotificationRow.requestRoundness(fSaturate, fSaturate, sourceType$Companion$from$1, expandableNotificationRow.getRoundableState().targetView.isShown());
        } else {
            expandableNotificationRow.requestRoundnessReset(sourceType$Companion$from$1);
        }
        if (this.mNotificationRoundnessManager.mRoundForPulsingViews) {
            boolean zShowingPulsing = expandableNotificationRow.showingPulsing();
            SourceType$Companion$from$1 sourceType$Companion$from$12 = PULSING;
            if (zShowingPulsing) {
                expandableNotificationRow.requestRoundness(1.0f, 1.0f, sourceType$Companion$from$12, expandableNotificationRow.getRoundableState().targetView.isShown());
            } else {
                expandableNotificationRow.requestRoundnessReset(sourceType$Companion$from$12);
            }
        }
    }

    public final void updateIsolatedIconLocation(boolean z) {
        int i = NotificationIconContainerRefactor.$r8$clinit;
        this.mNotificationIconAreaController.setIsolatedIconLocation(((HeadsUpStatusBarView) this.mView).mIconDrawingRect, z);
    }

    public final void updateParentClipping(boolean z) {
        int i = StatusBarNoHunBehavior.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ViewClippingUtil.setClippingDeactivated(this.mView, !z, this.mParentClippingParams);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePinnedStatus() {
        boolean z;
        boolean zIsExpanded$1;
        HeadsUpManagerImpl.HeadsUpEntry topHeadsUpEntry;
        int i = StatusBarNoHunBehavior.$r8$clinit;
        NotificationEntry notificationEntry = (!shouldHeadsUpStatusBarBeVisible() || (topHeadsUpEntry = ((HeadsUpManagerImpl) this.mHeadsUpManager).getTopHeadsUpEntry()) == null) ? null : topHeadsUpEntry.mEntry;
        HeadsUpStatusBarView headsUpStatusBarView = (HeadsUpStatusBarView) this.mView;
        NotificationEntry notificationEntry2 = headsUpStatusBarView.mShowingEntry;
        headsUpStatusBarView.setEntry(notificationEntry);
        if (notificationEntry != notificationEntry2) {
            if (notificationEntry == null) {
                setPinnedStatus(PinnedStatus.NotPinned);
                zIsExpanded$1 = isExpanded$1();
            } else {
                if (notificationEntry2 != null) {
                    z = false;
                    int i2 = NotificationIconContainerRefactor.$r8$clinit;
                    updateIsolatedIconLocation(false);
                    this.mNotificationIconAreaController.showIconIsolated(notificationEntry != null ? notificationEntry.mIcons.mStatusBarIcon : null, z);
                }
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                setPinnedStatus(expandableNotificationRow != null ? expandableNotificationRow.mPinnedStatus : PinnedStatus.NotPinned);
                zIsExpanded$1 = isExpanded$1();
            }
            z = !zIsExpanded$1;
            int i22 = NotificationIconContainerRefactor.$r8$clinit;
            updateIsolatedIconLocation(false);
            this.mNotificationIconAreaController.showIconIsolated(notificationEntry != null ? notificationEntry.mIcons.mStatusBarIcon : null, z);
        }
    }
}
