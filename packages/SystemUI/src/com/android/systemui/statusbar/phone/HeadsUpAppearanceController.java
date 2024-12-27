package com.android.systemui.statusbar.phone;

import android.util.MathUtils;
import android.view.View;
import android.widget.TextView;
import com.android.internal.widget.ViewClippingUtil;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.HeadsUpStatusBarView;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationIconInteractor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HeadsUpAppearanceController extends ViewController implements OnHeadsUpChangedListener, DarkIconDispatcher.DarkReceiver, NotificationWakeUpCoordinator.WakeUpListener {
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
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public final NotificationRoundnessManager mNotificationRoundnessManager;
    public final Optional mOperatorNameViewOptional;
    public final AnonymousClass1 mParentClippingParams;
    public final PhoneStatusBarTransitions mPhoneStatusBarTransitions;
    public final HeadsUpAppearanceController$$ExternalSyntheticLambda1 mSetExpandedHeight;
    public final HeadsUpAppearanceController$$ExternalSyntheticLambda0 mSetTrackingHeadsUp;
    public final ShadeViewController mShadeViewController;
    public boolean mShown;
    public final NotificationStackScrollLayoutController mStackScrollerController;
    public final StatusBarStateController mStatusBarStateController;
    public ExpandableNotificationRow mTrackedChild;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.phone.HeadsUpAppearanceController$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.statusbar.phone.HeadsUpAppearanceController$2] */
    public HeadsUpAppearanceController(NotificationIconAreaController notificationIconAreaController, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, PhoneStatusBarTransitions phoneStatusBarTransitions, KeyguardBypassController keyguardBypassController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, DarkIconDispatcher darkIconDispatcher, KeyguardStateController keyguardStateController, CommandQueue commandQueue, NotificationStackScrollLayoutController notificationStackScrollLayoutController, ShadeViewController shadeViewController, NotificationRoundnessManager notificationRoundnessManager, HeadsUpStatusBarView headsUpStatusBarView, View view, Optional<View> optional, FeatureFlagsClassic featureFlagsClassic, HeadsUpNotificationIconInteractor headsUpNotificationIconInteractor, Optional<View> optional2, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener) {
        super(headsUpStatusBarView);
        this.mSetTrackingHeadsUp = new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, 0);
        this.mSetExpandedHeight = new BiConsumer() { // from class: com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                HeadsUpAppearanceController headsUpAppearanceController = HeadsUpAppearanceController.this;
                float floatValue = ((Float) obj).floatValue();
                float floatValue2 = ((Float) obj2).floatValue();
                boolean z = floatValue != headsUpAppearanceController.mExpandedHeight;
                boolean isExpanded$1 = headsUpAppearanceController.isExpanded$1();
                headsUpAppearanceController.mExpandedHeight = floatValue;
                headsUpAppearanceController.mAppearFraction = floatValue2;
                if (z) {
                    ((BaseHeadsUpManager) headsUpAppearanceController.mHeadsUpManager).getHeadsUpEntryList().stream().map(new BaseHeadsUpManager$$ExternalSyntheticLambda0()).forEach(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(headsUpAppearanceController, 4));
                }
                if (headsUpAppearanceController.isExpanded$1() != isExpanded$1) {
                    headsUpAppearanceController.updateTopEntry();
                }
            }
        };
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
        notificationStackScrollLayoutController.mHeadsUpAppearanceController = this;
        notificationStackScrollLayout.mHeadsUpAppearanceController = this;
        this.mClockView = view;
        this.mOperatorNameViewOptional = optional2;
        this.mCarrierLogoFrameView = optional;
        this.mDarkIconDispatcher = darkIconDispatcher;
        ((HeadsUpStatusBarView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.HeadsUpAppearanceController.3
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (HeadsUpAppearanceController.this.shouldBeVisible$1()) {
                    HeadsUpAppearanceController.this.updateTopEntry();
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
        this.mConfigurationController = configurationController;
        this.mIndicatorScaleGardener = indicatorScaleGardener;
    }

    public final void hide(final View view, final int i, final HeadsUpAppearanceController$$ExternalSyntheticLambda5 headsUpAppearanceController$$ExternalSyntheticLambda5) {
        if (this.mAnimationsEnabled) {
            CrossFadeHelper.fadeOut(110L, view, new Runnable() { // from class: com.android.systemui.statusbar.phone.HeadsUpAppearanceController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    View view2 = view;
                    int i2 = i;
                    Runnable runnable = headsUpAppearanceController$$ExternalSyntheticLambda5;
                    view2.setVisibility(i2);
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
            return;
        }
        view.setVisibility(i);
        if (headsUpAppearanceController$$ExternalSyntheticLambda5 != null) {
            headsUpAppearanceController$$ExternalSyntheticLambda5.run();
        }
    }

    public final boolean isExpanded$1() {
        return this.mExpandedHeight > 0.0f;
    }

    public boolean isShown() {
        return this.mShown;
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        HeadsUpStatusBarView headsUpStatusBarView = (HeadsUpStatusBarView) this.mView;
        headsUpStatusBarView.mTextView.setTextColor(DarkIconDispatcher.getTint(arrayList, headsUpStatusBarView, i));
    }

    @Override // com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator.WakeUpListener
    public final void onFullyHiddenChanged(boolean z) {
        updateTopEntry();
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public final void onHeadsUpPinned(NotificationEntry notificationEntry) {
        updateTopEntry();
        updateHeader(notificationEntry);
        updateHeadsUpAndPulsingRoundness(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        updateHeadsUpAndPulsingRoundness(notificationEntry);
        PhoneStatusBarTransitions phoneStatusBarTransitions = this.mPhoneStatusBarTransitions;
        phoneStatusBarTransitions.mIsHeadsUp = z;
        phoneStatusBarTransitions.applyMode(phoneStatusBarTransitions.mMode, false);
    }

    @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
    public final void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
        updateTopEntry();
        updateHeader(notificationEntry);
        updateHeadsUpAndPulsingRoundness(notificationEntry);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        ((BaseHeadsUpManager) this.mHeadsUpManager).addListener(this);
        ((HeadsUpStatusBarView) this.mView).mOnDrawingRectChangedListener = new HeadsUpAppearanceController$$ExternalSyntheticLambda5(this, 1);
        int i = NotificationIconContainerRefactor.$r8$clinit;
        this.mWakeUpCoordinator.wakeUpListeners.add(this);
        ShadeViewController shadeViewController = this.mShadeViewController;
        shadeViewController.getShadeHeadsUpTracker$1().addTrackingHeadsUpListener(this.mSetTrackingHeadsUp);
        shadeViewController.getShadeHeadsUpTracker$1().setHeadsUpAppearanceController(this);
        this.mStackScrollerController.mView.mExpandedHeightListeners.add(this.mSetExpandedHeight);
        this.mDarkIconDispatcher.addDarkReceiver(this);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((BaseHeadsUpManager) this.mHeadsUpManager).mListeners.remove(this);
        ((HeadsUpStatusBarView) this.mView).mOnDrawingRectChangedListener = null;
        int i = NotificationIconContainerRefactor.$r8$clinit;
        this.mWakeUpCoordinator.wakeUpListeners.remove(this);
        ShadeViewController shadeViewController = this.mShadeViewController;
        shadeViewController.getShadeHeadsUpTracker$1().removeTrackingHeadsUpListener(this.mSetTrackingHeadsUp);
        shadeViewController.getShadeHeadsUpTracker$1().setHeadsUpAppearanceController(null);
        this.mStackScrollerController.mView.mExpandedHeightListeners.remove(this.mSetExpandedHeight);
        this.mDarkIconDispatcher.removeDarkReceiver(this);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    public void setAnimationsEnabled(boolean z) {
        this.mAnimationsEnabled = z;
    }

    public final void setShown(boolean z) {
        if (this.mShown != z) {
            this.mShown = z;
            if (z) {
                updateParentClipping(false);
                ((HeadsUpStatusBarView) this.mView).setVisibility(0);
                show(this.mView);
                hide(this.mClockView, 4, null);
                this.mCarrierLogoFrameView.ifPresent(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, 1));
                this.mOperatorNameViewOptional.ifPresent(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, 2));
            } else {
                show(this.mClockView);
                int i = 3;
                this.mCarrierLogoFrameView.ifPresent(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, i));
                this.mOperatorNameViewOptional.ifPresent(new HeadsUpAppearanceController$$ExternalSyntheticLambda0(this, i));
                hide(this.mView, 8, new HeadsUpAppearanceController$$ExternalSyntheticLambda5(this, 0));
            }
            if (this.mStatusBarStateController.getState() != 0) {
                this.mCommandQueue.recomputeDisableFlags(((HeadsUpStatusBarView) this.mView).getContext().getDisplayId(), false);
            }
        }
    }

    public final boolean shouldBeVisible$1() {
        boolean z = !this.mWakeUpCoordinator.notificationsFullyHidden;
        boolean z2 = !isExpanded$1() && z;
        if (this.mBypassController.getBypassEnabled() && ((this.mStatusBarStateController.getState() == 1 || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mKeyguardGoingAway) && z)) {
            z2 = true;
        }
        return z2 && ((BaseHeadsUpManager) this.mHeadsUpManager).mHasPinnedNotification;
    }

    public final void show(View view) {
        if (this.mAnimationsEnabled) {
            CrossFadeHelper.fadeIn(view, 110L, 100);
        } else {
            view.setVisibility(0);
        }
    }

    public final void updateHeader(NotificationEntry notificationEntry) {
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        int i = AsyncGroupHeaderViewInflation.$r8$clinit;
        Flags.notificationAsyncGroupHeaderInflation();
        float f = (expandableNotificationRow.mIsPinned || expandableNotificationRow.mHeadsupDisappearRunning || expandableNotificationRow == this.mTrackedChild || expandableNotificationRow.showingPulsing()) ? this.mAppearFraction : 1.0f;
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

    public final void updateHeadsUpAndPulsingRoundness(NotificationEntry notificationEntry) {
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        boolean z = expandableNotificationRow == this.mTrackedChild;
        boolean z2 = expandableNotificationRow.mIsPinned;
        SourceType$Companion$from$1 sourceType$Companion$from$1 = HEADS_UP;
        if (z2 || expandableNotificationRow.mHeadsupDisappearRunning || z) {
            float saturate = MathUtils.saturate(1.0f - this.mAppearFraction);
            expandableNotificationRow.requestRoundness(saturate, saturate, sourceType$Companion$from$1);
        } else {
            expandableNotificationRow.requestRoundnessReset(sourceType$Companion$from$1);
        }
        if (this.mNotificationRoundnessManager.mRoundForPulsingViews) {
            boolean showingPulsing = expandableNotificationRow.showingPulsing();
            SourceType$Companion$from$1 sourceType$Companion$from$12 = PULSING;
            if (showingPulsing) {
                expandableNotificationRow.requestRoundness(1.0f, 1.0f, sourceType$Companion$from$12);
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
        ViewClippingUtil.setClippingDeactivated(this.mView, !z, this.mParentClippingParams);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateTopEntry() {
        /*
            r5 = this;
            boolean r0 = r5.shouldBeVisible$1()
            r1 = 0
            if (r0 == 0) goto L14
            com.android.systemui.statusbar.policy.HeadsUpManager r0 = r5.mHeadsUpManager
            com.android.systemui.statusbar.policy.BaseHeadsUpManager r0 = (com.android.systemui.statusbar.policy.BaseHeadsUpManager) r0
            com.android.systemui.statusbar.policy.BaseHeadsUpManager$HeadsUpEntry r0 = r0.getTopHeadsUpEntry()
            if (r0 == 0) goto L14
            com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r0.mEntry
            goto L15
        L14:
            r0 = r1
        L15:
            T extends android.view.View r2 = r5.mView
            com.android.systemui.statusbar.HeadsUpStatusBarView r2 = (com.android.systemui.statusbar.HeadsUpStatusBarView) r2
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r2.mShowingEntry
            r2.setEntry(r0)
            if (r0 == r3) goto L49
            r2 = 1
            r4 = 0
            if (r0 != 0) goto L2d
            r5.setShown(r4)
            boolean r3 = r5.isExpanded$1()
        L2b:
            r2 = r2 ^ r3
            goto L38
        L2d:
            if (r3 != 0) goto L37
            r5.setShown(r2)
            boolean r3 = r5.isExpanded$1()
            goto L2b
        L37:
            r2 = r4
        L38:
            int r3 = com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor.$r8$clinit
            r5.updateIsolatedIconLocation(r4)
            if (r0 != 0) goto L40
            goto L44
        L40:
            com.android.systemui.statusbar.notification.icon.IconPack r0 = r0.mIcons
            com.android.systemui.statusbar.StatusBarIconView r1 = r0.mStatusBarIcon
        L44:
            com.android.systemui.statusbar.phone.NotificationIconAreaController r5 = r5.mNotificationIconAreaController
            r5.showIconIsolated(r1, r2)
        L49:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.HeadsUpAppearanceController.updateTopEntry():void");
    }
}
