package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Trace;
import android.os.UserHandle;
import android.permission.PermissionGroupUsage;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyChipEvent;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.qs.HeaderPrivacyIconsController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePhonePicker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.carrier.ShadeCarrier;
import com.android.systemui.shade.carrier.ShadeCarrierGroup;
import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.StatusIconContainerController;
import com.android.systemui.statusbar.phone.StatusOverlayHoverListenerFactory;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.VariableDateViewController$Factory;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeHeaderController extends ViewController implements Dumpable, LockscreenShadeTransitionController.Callback {
    public final ActivityStarter activityStarter;
    public final BatteryMeterView batteryIcon;
    public final BatteryMeterViewController batteryMeterViewController;
    public EmptyList carrierIconSlots;
    public final ShadeHeaderController$chipVisibilityListener$1 chipVisibilityListener;
    public final CombinedShadeHeadersConstraintManager combinedShadeHeadersConstraintManager;
    public final ConfigurationController configurationController;
    public final ShadeHeaderController$configurationControllerListener$1 configurationControllerListener;
    public boolean customizing;
    public DisplayCutout cutout;
    public final DumpManager dumpManager;
    public final MotionLayout header;
    public final StatusIconContainer iconContainer;
    public TintedIconManager iconManager;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final ShadeHeaderController$insetListener$1 insetListener;
    public final StatusBarContentInsetsProvider insetsProvider;
    public boolean largeScreenActive;
    public WindowInsets lastInsets;
    public final LockscreenShadeTransitionController lockscreenShadeTransitionController;
    public boolean mPanelExpandedInLockScreen;
    public final ShadeCarrierGroup mShadeCarrierGroup;
    public ShadeCarrierGroupController mShadeCarrierGroupController;
    public final NetspeedViewController netspeedViewController;
    public PendingIntent nextAlarmIntent;
    public boolean panelExpanded;
    public final HeaderPrivacyIconsController privacyIconsController;
    public final QsBatteryModeController qsBatteryModeController;
    public boolean qsDisabled;
    public float qsExpandedFraction;
    public final SecQSPanelResourcePicker qsPanelResourcePicker;
    public int qsScrollY;
    public boolean qsVisible;
    public final ShadeCarrierGroupController.Builder shadeCarrierGroupControllerBuilder;
    public float shadeExpandedFraction;
    public final StatusBarIconController statusBarIconController;
    public final StatusIconContainerController statusIconContainerController;
    public final View systemIconsHoverContainer;
    public final TintedIconManager.Factory tintedIconManagerFactory;
    public boolean visible;
    public static final Companion Companion = new Companion(null);
    public static final int HEADER_TRANSITION_ID = R.id.header_transition;
    public static final int LARGE_SCREEN_HEADER_TRANSITION_ID = R.id.large_screen_header_transition;
    public static final int QQS_HEADER_CONSTRAINT = R.id.qqs_header_constraint;
    public static final int QS_HEADER_CONSTRAINT = R.id.qs_header_constraint;
    public static final int LARGE_SCREEN_HEADER_CONSTRAINT = R.id.large_screen_header_constraint;
    public static final Intent DEFAULT_CLOCK_INTENT = new Intent("android.intent.action.SHOW_ALARMS");

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getDEFAULT_CLOCK_INTENT$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }

        public static /* synthetic */ void getHEADER_TRANSITION_ID$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }

        public static /* synthetic */ void getLARGE_SCREEN_HEADER_CONSTRAINT$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }

        public static /* synthetic */ void getLARGE_SCREEN_HEADER_TRANSITION_ID$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }

        public static /* synthetic */ void getQQS_HEADER_CONSTRAINT$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }

        public static /* synthetic */ void getQS_HEADER_CONSTRAINT$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CustomizerAnimationListener extends AnimatorListenerAdapter {
        public final boolean enteringCustomizing;

        public CustomizerAnimationListener(boolean z) {
            this.enteringCustomizing = z;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            ShadeHeaderController.this.header.animate().setListener(null);
            if (this.enteringCustomizing) {
                ShadeHeaderController shadeHeaderController = ShadeHeaderController.this;
                if (!shadeHeaderController.customizing) {
                    shadeHeaderController.customizing = true;
                    shadeHeaderController.updateVisibility$5();
                }
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            if (this.enteringCustomizing) {
                return;
            }
            ShadeHeaderController shadeHeaderController = ShadeHeaderController.this;
            if (shadeHeaderController.customizing) {
                shadeHeaderController.customizing = false;
                shadeHeaderController.updateVisibility$5();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.shade.ShadeHeaderController$insetListener$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.shade.ShadeHeaderController$configurationControllerListener$1] */
    public ShadeHeaderController(MotionLayout motionLayout, StatusBarIconController statusBarIconController, TintedIconManager.Factory factory, HeaderPrivacyIconsController headerPrivacyIconsController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, ConfigurationController configurationController, VariableDateViewController$Factory variableDateViewController$Factory, BatteryMeterViewController batteryMeterViewController, DumpManager dumpManager, ShadeCarrierGroupController.Builder builder, CombinedShadeHeadersConstraintManager combinedShadeHeadersConstraintManager, DemoModeController demoModeController, QsBatteryModeController qsBatteryModeController, NextAlarmController nextAlarmController, ActivityStarter activityStarter, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory, SecQSPanelResourcePicker secQSPanelResourcePicker, StatusIconContainerController statusIconContainerController, IndicatorScaleGardener indicatorScaleGardener, ShadeHeaderColorPicker shadeHeaderColorPicker, NetspeedViewController netspeedViewController, LockscreenShadeTransitionController lockscreenShadeTransitionController) {
        super(motionLayout);
        this.header = motionLayout;
        this.statusBarIconController = statusBarIconController;
        this.tintedIconManagerFactory = factory;
        this.privacyIconsController = headerPrivacyIconsController;
        this.insetsProvider = statusBarContentInsetsProvider;
        this.configurationController = configurationController;
        this.batteryMeterViewController = batteryMeterViewController;
        this.dumpManager = dumpManager;
        this.shadeCarrierGroupControllerBuilder = builder;
        this.combinedShadeHeadersConstraintManager = combinedShadeHeadersConstraintManager;
        this.qsBatteryModeController = qsBatteryModeController;
        this.activityStarter = activityStarter;
        this.qsPanelResourcePicker = secQSPanelResourcePicker;
        this.statusIconContainerController = statusIconContainerController;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.netspeedViewController = netspeedViewController;
        this.lockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.batteryIcon = (BatteryMeterView) motionLayout.requireViewById(R.id.batteryRemainingIcon);
        this.iconContainer = (StatusIconContainer) motionLayout.requireViewById(R.id.statusIcons);
        this.mShadeCarrierGroup = (ShadeCarrierGroup) motionLayout.requireViewById(R.id.carrier_group);
        this.systemIconsHoverContainer = motionLayout.requireViewById(R.id.hover_system_icons_container);
        this.largeScreenActive = true;
        this.shadeExpandedFraction = -1.0f;
        this.qsExpandedFraction = -1.0f;
        this.insetListener = new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.shade.ShadeHeaderController$insetListener$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                ShadeHeaderController.access$updateConstraintsForInsets(ShadeHeaderController.this, (MotionLayout) view, windowInsets);
                ShadeHeaderController.this.lastInsets = new WindowInsets(windowInsets);
                return view.onApplyWindowInsets(windowInsets);
            }
        };
        this.chipVisibilityListener = new ShadeHeaderController$chipVisibilityListener$1(this);
        this.configurationControllerListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.shade.ShadeHeaderController$configurationControllerListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                ShadeHeaderController.Companion companion = ShadeHeaderController.Companion;
                ShadeHeaderController.this.updateHeaderPadding();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ShadeHeaderController.Companion companion = ShadeHeaderController.Companion;
                ShadeHeaderController shadeHeaderController = ShadeHeaderController.this;
                float dimensionPixelSize = shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size) * shadeHeaderController.indicatorScaleGardener.getLatestScaleModel(shadeHeaderController.getContext()).ratio;
                ShadeCarrierGroup shadeCarrierGroup = shadeHeaderController.mShadeCarrierGroup;
                TextView textView = (TextView) shadeCarrierGroup.findViewById(R.id.no_carrier_text);
                TypedArray obtainStyledAttributes = textView.getContext().obtainStyledAttributes(R.style.TextAppearance_QS_Status_SamsungCarriers, new int[]{android.R.attr.textSize});
                textView.setTextSize(0, obtainStyledAttributes.getDimensionPixelSize(0, (int) textView.getTextSize()));
                obtainStyledAttributes.recycle();
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier1)).mCarrierText.setTextAppearance(R.style.TextAppearance_QS_Status_SamsungCarriers);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier2)).mCarrierText.setTextAppearance(R.style.TextAppearance_QS_Status_SamsungCarriers);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier3)).mCarrierText.setTextAppearance(R.style.TextAppearance_QS_Status_SamsungCarriers);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier1)).mCarrierText.setTextSize(0, dimensionPixelSize);
                int i = ShadeHeaderController.QQS_HEADER_CONSTRAINT;
                MotionLayout motionLayout2 = shadeHeaderController.header;
                motionLayout2.getConstraintSet(i).load(shadeHeaderController.getContext(), shadeHeaderController.getResources().getXml(R.xml.qqs_header));
                motionLayout2.getConstraintSet(ShadeHeaderController.QS_HEADER_CONSTRAINT).load(shadeHeaderController.getContext(), shadeHeaderController.getResources().getXml(R.xml.qs_header));
                motionLayout2.getConstraintSet(ShadeHeaderController.LARGE_SCREEN_HEADER_CONSTRAINT).load(shadeHeaderController.getContext(), shadeHeaderController.getResources().getXml(R.xml.large_screen_shade_header));
                motionLayout2.setMinHeight(shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.large_screen_shade_header_min_height));
                WindowInsets windowInsets = shadeHeaderController.lastInsets;
                if (windowInsets != null) {
                    ShadeHeaderController.access$updateConstraintsForInsets(shadeHeaderController, motionLayout2, windowInsets);
                }
                shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.rounded_corner_content_padding);
                shadeHeaderController.updateHeaderPadding();
                shadeHeaderController.qsBatteryModeController.updateResources();
                OngoingPrivacyChip ongoingPrivacyChip = shadeHeaderController.privacyIconsController.privacyChip;
                ongoingPrivacyChip.updateResources$6();
                ongoingPrivacyChip.iconsContainer.setBackground(ongoingPrivacyChip.getContext().getDrawable(R.drawable.sec_privacy_chip_bg));
                ongoingPrivacyChip.iconsContainer.getLayoutParams().height = ongoingPrivacyChip.getContext().getResources().getDimensionPixelSize(R.dimen.sec_ongoing_appops_chip_height);
                int dimensionPixelSize2 = ongoingPrivacyChip.getContext().getResources().getDimensionPixelSize(R.dimen.sec_ongoing_appops_chip_start_end_padding);
                ongoingPrivacyChip.iconsContainer.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, 0);
                ongoingPrivacyChip.setPrivacyList(ongoingPrivacyChip.privacyList);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDisplayDeviceTypeChanged() {
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    onDensityOrFontScaleChanged();
                }
            }
        };
        new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.shade.ShadeHeaderController$nextAlarmCallback$1
            @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                ShadeHeaderController.this.nextAlarmIntent = alarmClockInfo != null ? alarmClockInfo.getShowIntent() : null;
            }
        };
    }

    public static final void access$updateConstraintsForInsets(ShadeHeaderController shadeHeaderController, MotionLayout motionLayout, WindowInsets windowInsets) {
        ConstraintsChanges plus;
        Integer num;
        shadeHeaderController.getClass();
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        shadeHeaderController.cutout = displayCutout;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = shadeHeaderController.insetsProvider;
        Insets statusBarContentInsetsForCurrentRotation = statusBarContentInsetsProvider.getStatusBarContentInsetsForCurrentRotation();
        final int i = statusBarContentInsetsForCurrentRotation.left;
        int i2 = statusBarContentInsetsForCurrentRotation.right;
        boolean currentRotationHasCornerCutout = statusBarContentInsetsProvider.currentRotationHasCornerCutout();
        final int i3 = motionLayout.isLayoutRtl() ? i2 : i;
        MotionLayout motionLayout2 = shadeHeaderController.header;
        final int paddingStart = motionLayout2.getPaddingStart();
        if (!motionLayout.isLayoutRtl()) {
            i = i2;
        }
        final int paddingEnd = motionLayout2.getPaddingEnd();
        ((CombinedShadeHeadersConstraintManagerImpl) shadeHeaderController.combinedShadeHeadersConstraintManager).getClass();
        Function1 function1 = new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$edgesGuidelinesConstraints$change$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ConstraintSet constraintSet = (ConstraintSet) obj;
                constraintSet.setGuidelineBegin(R.id.begin_guide, Math.max(i3 - paddingStart, 0));
                constraintSet.setGuidelineEnd(R.id.end_guide, Math.max(i - paddingEnd, 0));
                return Unit.INSTANCE;
            }
        };
        ConstraintsChanges constraintsChanges = new ConstraintsChanges(function1, function1, function1);
        if (displayCutout != null) {
            Rect boundingRectTop = displayCutout.getBoundingRectTop();
            if (boundingRectTop.isEmpty() || currentRotationHasCornerCutout) {
                plus = constraintsChanges.plus(new ConstraintsChanges(new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$emptyCutoutConstraints$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ConstraintSet constraintSet = (ConstraintSet) obj;
                        constraintSet.connect(R.id.date, 7, R.id.barrier, 6);
                        constraintSet.createBarrier(R.id.barrier, 6, 0, R.id.shade_header_system_icons, R.id.privacy_container);
                        constraintSet.connect(R.id.shade_header_system_icons, 6, R.id.date, 7);
                        constraintSet.connect(R.id.privacy_container, 6, R.id.date, 7);
                        constraintSet.constrainWidth(R.id.shade_header_system_icons, -2);
                        constraintSet.constrainedWidth(R.id.date, true);
                        constraintSet.constrainedWidth(R.id.shade_header_system_icons, true);
                        return Unit.INSTANCE;
                    }
                }, null, null, 6, null));
            } else {
                boolean isLayoutRtl = motionLayout.isLayoutRtl();
                final int width = (((motionLayout.getWidth() - motionLayout.getPaddingLeft()) - motionLayout.getPaddingRight()) - boundingRectTop.width()) / 2;
                final int i4 = R.id.center_right;
                final int i5 = !isLayoutRtl ? R.id.center_left : R.id.center_right;
                if (isLayoutRtl) {
                    i4 = R.id.center_left;
                }
                plus = constraintsChanges.plus(new ConstraintsChanges(new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$centerCutoutConstraints$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ConstraintSet constraintSet = (ConstraintSet) obj;
                        constraintSet.setGuidelineBegin(i5, width);
                        constraintSet.setGuidelineEnd(i4, width);
                        constraintSet.connect(R.id.date, 7, i5, 6);
                        constraintSet.connect(R.id.shade_header_system_icons, 6, i4, 7);
                        constraintSet.connect(R.id.privacy_container, 6, i4, 7);
                        constraintSet.constrainedWidth(R.id.date, true);
                        constraintSet.constrainedWidth(R.id.shade_header_system_icons, true);
                        return Unit.INSTANCE;
                    }
                }, new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$centerCutoutConstraints$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ConstraintSet constraintSet = (ConstraintSet) obj;
                        constraintSet.setGuidelineBegin(i5, width);
                        constraintSet.setGuidelineEnd(i4, width);
                        constraintSet.connect(R.id.privacy_container, 6, i4, 7);
                        return Unit.INSTANCE;
                    }
                }, null, 4, null));
            }
        } else {
            plus = constraintsChanges.plus(new ConstraintsChanges(new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$emptyCutoutConstraints$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    ConstraintSet constraintSet = (ConstraintSet) obj;
                    constraintSet.connect(R.id.date, 7, R.id.barrier, 6);
                    constraintSet.createBarrier(R.id.barrier, 6, 0, R.id.shade_header_system_icons, R.id.privacy_container);
                    constraintSet.connect(R.id.shade_header_system_icons, 6, R.id.date, 7);
                    constraintSet.connect(R.id.privacy_container, 6, R.id.date, 7);
                    constraintSet.constrainWidth(R.id.shade_header_system_icons, -2);
                    constraintSet.constrainedWidth(R.id.date, true);
                    constraintSet.constrainedWidth(R.id.shade_header_system_icons, true);
                    return Unit.INSTANCE;
                }
            }, null, null, 6, null));
        }
        Function1 function12 = plus.qqsConstraintsChanges;
        if (function12 != null) {
            int i6 = QQS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet = motionLayout.getConstraintSet(i6);
            Intrinsics.checkNotNull(constraintSet);
            function12.invoke(constraintSet);
            motionLayout.updateState(i6, constraintSet);
        }
        Function1 function13 = plus.qsConstraintsChanges;
        if (function13 != null) {
            int i7 = QS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet2 = motionLayout.getConstraintSet(i7);
            Intrinsics.checkNotNull(constraintSet2);
            function13.invoke(constraintSet2);
            motionLayout.updateState(i7, constraintSet2);
        }
        DisplayCutout displayCutout2 = shadeHeaderController.cutout;
        float f = shadeHeaderController.qsExpandedFraction;
        QsBatteryModeController qsBatteryModeController = shadeHeaderController.qsBatteryModeController;
        int i8 = 3;
        if (f > qsBatteryModeController.fadeInStartFraction) {
            num = 3;
        } else if (f < qsBatteryModeController.fadeOutCompleteFraction) {
            if (displayCutout2 != null && !qsBatteryModeController.insetsProvider.currentRotationHasCornerCutout() && !displayCutout2.getBoundingRectTop().isEmpty()) {
                i8 = 1;
            }
            num = Integer.valueOf(i8);
        } else {
            num = null;
        }
        if (num != null) {
            shadeHeaderController.batteryIcon.setPercentShowMode(num.intValue());
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("visible: ", this.visible, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("shadeExpanded: ", this.qsVisible, printWriter);
        printWriter.println("shadeExpandedFraction: " + this.shadeExpandedFraction);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("active: ", this.largeScreenActive, printWriter);
        printWriter.println("qsExpandedFraction: " + this.qsExpandedFraction);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("qsScrollY: ", this.qsScrollY, printWriter);
        int currentState = this.header.getCurrentState();
        Companion.getClass();
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "currentState: ", currentState == QQS_HEADER_CONSTRAINT ? "QQS Header" : currentState == QS_HEADER_CONSTRAINT ? "QS Header" : currentState == LARGE_SCREEN_HEADER_CONSTRAINT ? "Large Screen Header" : MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(currentState, "Unknown state "));
    }

    public final void launchClockActivity$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        PendingIntent pendingIntent = this.nextAlarmIntent;
        ActivityStarter activityStarter = this.activityStarter;
        if (pendingIntent != null) {
            activityStarter.postStartActivityDismissingKeyguard(pendingIntent);
        } else {
            activityStarter.postStartActivityDismissingKeyguard(DEFAULT_CLOCK_INTENT, 0);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        NetspeedViewController netspeedViewController;
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (netspeedViewController = this.netspeedViewController) != null) {
            netspeedViewController.init();
        }
        BatteryMeterViewController batteryMeterViewController = this.batteryMeterViewController;
        batteryMeterViewController.init();
        batteryMeterViewController.mIgnoreTunerUpdates = true;
        if (batteryMeterViewController.mIsSubscribedForTunerUpdates) {
            batteryMeterViewController.mTunerService.removeTunable(batteryMeterViewController.mTunable);
            batteryMeterViewController.mIsSubscribedForTunerUpdates = false;
        }
        MotionLayout motionLayout = this.header;
        Utils.getColorAttrDefaultColor(motionLayout.getContext(), android.R.attr.textColorPrimary, 0);
        Utils.getColorAttrDefaultColor(motionLayout.getContext(), android.R.attr.textColorPrimaryInverse, 0);
        TintedIconManager create = this.tintedIconManagerFactory.create(this.iconContainer, StatusBarLocation.QS);
        this.iconManager = create;
        create.setTint(getContext().getColor(R.color.status_bar_clock_color), Utils.getColorAttrDefaultColor(motionLayout.getContext(), android.R.attr.textColorPrimaryInverse, 0));
        this.carrierIconSlots = EmptyList.INSTANCE;
        ShadeCarrierGroupController.Builder builder = this.shadeCarrierGroupControllerBuilder;
        builder.getClass();
        this.mShadeCarrierGroupController = new ShadeCarrierGroupController(this.mShadeCarrierGroup, builder.mActivityStarter, builder.mHandler, builder.mLooper, builder.mLogger, builder.mNetworkController, builder.mCarrierTextControllerBuilder, builder.mContext, builder.mCarrierConfigTracker, builder.mSlotIndexResolver, builder.mMobileUiAdapter, builder.mMobileContextProvider, builder.mStatusBarPipelineFlags, builder.mLatinNetworkNameProvider, builder.mSlimIndicatorViewMediator, 0);
        final HeaderPrivacyIconsController headerPrivacyIconsController = this.privacyIconsController;
        headerPrivacyIconsController.getClass();
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$onParentVisible$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (((DeviceProvisionedControllerImpl) HeaderPrivacyIconsController.this.deviceProvisionedController).deviceProvisioned.get()) {
                    HeaderPrivacyIconsController.this.uiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_CLICK);
                    HeaderPrivacyIconsController headerPrivacyIconsController2 = HeaderPrivacyIconsController.this;
                    final PrivacyDialogController privacyDialogController = headerPrivacyIconsController2.privacyDialogController;
                    final Context context = headerPrivacyIconsController2.privacyChip.getContext();
                    Dialog dialog = privacyDialogController.dialog;
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    privacyDialogController.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyDialogController$showDialog$1
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Type inference failed for: r10v5, types: [java.lang.CharSequence, java.lang.Object] */
                        @Override // java.lang.Runnable
                        public final void run() {
                            PrivacyType privacyType;
                            PrivacyType privacyType2;
                            Object obj;
                            String packageName;
                            Intent intent;
                            ActivityInfo activityInfo;
                            PrivacyDialogController privacyDialogController2 = PrivacyDialogController.this;
                            List<PermissionGroupUsage> indicatorAppOpUsageData = privacyDialogController2.permissionManager.getIndicatorAppOpUsageData(((AppOpsControllerImpl) privacyDialogController2.appOpsController).mMicMuted);
                            List userProfiles = ((UserTrackerImpl) PrivacyDialogController.this.userTracker).getUserProfiles();
                            PrivacyDialogController.this.privacyLogger.logUnfilteredPermGroupUsage(indicatorAppOpUsageData);
                            PrivacyDialogController privacyDialogController3 = PrivacyDialogController.this;
                            final ArrayList arrayList = new ArrayList();
                            for (PermissionGroupUsage permissionGroupUsage : indicatorAppOpUsageData) {
                                String permissionGroupName = permissionGroupUsage.getPermissionGroupName();
                                privacyDialogController3.getClass();
                                int hashCode = permissionGroupName.hashCode();
                                PrivacyDialog.PrivacyElement privacyElement = null;
                                if (hashCode == -1140935117) {
                                    if (permissionGroupName.equals("android.permission-group.CAMERA")) {
                                        privacyType = PrivacyType.TYPE_CAMERA;
                                    }
                                    privacyType = null;
                                } else if (hashCode != 828638019) {
                                    if (hashCode == 1581272376 && permissionGroupName.equals("android.permission-group.MICROPHONE")) {
                                        privacyType = PrivacyType.TYPE_MICROPHONE;
                                    }
                                    privacyType = null;
                                } else {
                                    if (permissionGroupName.equals("android.permission-group.LOCATION")) {
                                        privacyType = PrivacyType.TYPE_LOCATION;
                                    }
                                    privacyType = null;
                                }
                                if (privacyType != null) {
                                    PrivacyType privacyType3 = PrivacyType.TYPE_CAMERA;
                                    PrivacyItemController privacyItemController = privacyDialogController3.privacyItemController;
                                    if (((privacyType != privacyType3 && privacyType != PrivacyType.TYPE_MICROPHONE) || !privacyItemController.privacyConfig.micCameraAvailable) && (privacyType != PrivacyType.TYPE_LOCATION || !privacyItemController.privacyConfig.locationAvailable)) {
                                        privacyType = null;
                                    }
                                    privacyType2 = privacyType;
                                } else {
                                    privacyType2 = null;
                                }
                                Iterator it = userProfiles.iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        obj = it.next();
                                        if (((UserInfo) obj).id == UserHandle.getUserId(permissionGroupUsage.getUid())) {
                                            break;
                                        }
                                    } else {
                                        obj = null;
                                        break;
                                    }
                                }
                                UserInfo userInfo = (UserInfo) obj;
                                if ((userInfo != null || permissionGroupUsage.isPhoneCall()) && privacyType2 != null) {
                                    if (permissionGroupUsage.isPhoneCall()) {
                                        packageName = "";
                                    } else {
                                        packageName = permissionGroupUsage.getPackageName();
                                        try {
                                            ?? loadLabel = privacyDialogController3.packageManager.getApplicationInfoAsUser(packageName, 0, UserHandle.getUserId(permissionGroupUsage.getUid())).loadLabel(privacyDialogController3.packageManager);
                                            Intrinsics.checkNotNull(loadLabel);
                                            packageName = loadLabel;
                                        } catch (PackageManager.NameNotFoundException unused) {
                                            Log.w("PrivacyDialogController", "Label not found for: ".concat(packageName));
                                        }
                                    }
                                    String str = packageName;
                                    int userId = UserHandle.getUserId(permissionGroupUsage.getUid());
                                    String packageName2 = permissionGroupUsage.getPackageName();
                                    CharSequence attributionTag = permissionGroupUsage.getAttributionTag();
                                    CharSequence attributionLabel = permissionGroupUsage.getAttributionLabel();
                                    CharSequence proxyLabel = permissionGroupUsage.getProxyLabel();
                                    long lastAccessTimeMillis = permissionGroupUsage.getLastAccessTimeMillis();
                                    boolean isActive = permissionGroupUsage.isActive();
                                    boolean isManagedProfile = userInfo != null ? userInfo.isManagedProfile() : false;
                                    boolean isPhoneCall = permissionGroupUsage.isPhoneCall();
                                    String permissionGroupName2 = permissionGroupUsage.getPermissionGroupName();
                                    String packageName3 = permissionGroupUsage.getPackageName();
                                    String permissionGroupName3 = permissionGroupUsage.getPermissionGroupName();
                                    CharSequence attributionTag2 = permissionGroupUsage.getAttributionTag();
                                    boolean z = permissionGroupUsage.getAttributionLabel() != null;
                                    if (attributionTag2 != null && z && privacyDialogController3.locationManager.isProviderPackage(null, packageName3, attributionTag2.toString())) {
                                        intent = new Intent("android.intent.action.MANAGE_PERMISSION_USAGE");
                                        intent.setPackage(packageName3);
                                        intent.putExtra("android.intent.extra.PERMISSION_GROUP_NAME", permissionGroupName3.toString());
                                        intent.putExtra("android.intent.extra.ATTRIBUTION_TAGS", new String[]{attributionTag2.toString()});
                                        intent.putExtra("android.intent.extra.SHOWING_ATTRIBUTION", true);
                                        ResolveInfo resolveActivity = privacyDialogController3.packageManager.resolveActivity(intent, PackageManager.ResolveInfoFlags.of(0L));
                                        if (resolveActivity != null && (activityInfo = resolveActivity.activityInfo) != null && Intrinsics.areEqual(activityInfo.permission, "android.permission.START_VIEW_PERMISSION_USAGE")) {
                                            intent.setComponent(new ComponentName(packageName3, resolveActivity.activityInfo.name));
                                            privacyElement = new PrivacyDialog.PrivacyElement(privacyType2, packageName2, userId, str, attributionTag, attributionLabel, proxyLabel, lastAccessTimeMillis, isActive, isManagedProfile, isPhoneCall, permissionGroupName2, intent);
                                        }
                                    }
                                    intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
                                    intent.putExtra("android.intent.extra.PACKAGE_NAME", packageName3);
                                    intent.putExtra("android.intent.extra.USER", UserHandle.of(userId));
                                    privacyElement = new PrivacyDialog.PrivacyElement(privacyType2, packageName2, userId, str, attributionTag, attributionLabel, proxyLabel, lastAccessTimeMillis, isActive, isManagedProfile, isPhoneCall, permissionGroupName2, intent);
                                }
                                if (privacyElement != null) {
                                    arrayList.add(privacyElement);
                                }
                            }
                            final PrivacyDialogController privacyDialogController4 = PrivacyDialogController.this;
                            Executor executor = privacyDialogController4.uiExecutor;
                            final Context context2 = context;
                            executor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyDialogController$showDialog$1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Iterable singletonList;
                                    PrivacyDialogController privacyDialogController5 = PrivacyDialogController.this;
                                    List list = arrayList;
                                    int i = PrivacyDialogController.$r8$clinit;
                                    privacyDialogController5.getClass();
                                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                                    for (Object obj2 : list) {
                                        PrivacyType privacyType4 = ((PrivacyDialog.PrivacyElement) obj2).type;
                                        Object obj3 = linkedHashMap.get(privacyType4);
                                        if (obj3 == null) {
                                            obj3 = new ArrayList();
                                            linkedHashMap.put(privacyType4, obj3);
                                        }
                                        ((List) obj3).add(obj2);
                                    }
                                    TreeMap treeMap = new TreeMap(linkedHashMap);
                                    ArrayList arrayList2 = new ArrayList();
                                    Iterator it2 = treeMap.entrySet().iterator();
                                    while (true) {
                                        Object obj4 = null;
                                        if (!it2.hasNext()) {
                                            break;
                                        }
                                        List list2 = (List) ((Map.Entry) it2.next()).getValue();
                                        Intrinsics.checkNotNull(list2);
                                        List list3 = list2;
                                        ArrayList arrayList3 = new ArrayList();
                                        for (Object obj5 : list3) {
                                            if (((PrivacyDialog.PrivacyElement) obj5).active) {
                                                arrayList3.add(obj5);
                                            }
                                        }
                                        if (!arrayList3.isEmpty()) {
                                            singletonList = CollectionsKt___CollectionsKt.sortedWith(arrayList3, new Comparator() { // from class: com.android.systemui.privacy.PrivacyDialogController$filterAndSelect$lambda$6$$inlined$sortedByDescending$1
                                                @Override // java.util.Comparator
                                                public final int compare(Object obj6, Object obj7) {
                                                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((PrivacyDialog.PrivacyElement) obj7).lastActiveTimestamp), Long.valueOf(((PrivacyDialog.PrivacyElement) obj6).lastActiveTimestamp));
                                                }
                                            });
                                        } else {
                                            Iterator it3 = list3.iterator();
                                            if (it3.hasNext()) {
                                                obj4 = it3.next();
                                                if (it3.hasNext()) {
                                                    long j = ((PrivacyDialog.PrivacyElement) obj4).lastActiveTimestamp;
                                                    do {
                                                        Object next = it3.next();
                                                        long j2 = ((PrivacyDialog.PrivacyElement) next).lastActiveTimestamp;
                                                        if (j < j2) {
                                                            obj4 = next;
                                                            j = j2;
                                                        }
                                                    } while (it3.hasNext());
                                                }
                                            }
                                            PrivacyDialog.PrivacyElement privacyElement2 = (PrivacyDialog.PrivacyElement) obj4;
                                            singletonList = privacyElement2 != null ? Collections.singletonList(privacyElement2) : EmptyList.INSTANCE;
                                        }
                                        CollectionsKt__MutableCollectionsKt.addAll(singletonList, arrayList2);
                                    }
                                    if (!(!arrayList2.isEmpty())) {
                                        Log.w("PrivacyDialogController", "Trying to show empty dialog");
                                        return;
                                    }
                                    PrivacyDialogController.DialogProvider dialogProvider = PrivacyDialogController.this.dialogProvider;
                                    Context context3 = context2;
                                    PrivacyDialogController$showDialog$1$1$d$1 privacyDialogController$showDialog$1$1$d$1 = new PrivacyDialogController$showDialog$1$1$d$1(PrivacyDialogController.this);
                                    ((PrivacyDialogControllerKt$defaultDialogProvider$1) dialogProvider).getClass();
                                    PrivacyDialog privacyDialog = new PrivacyDialog(context3, arrayList2, privacyDialogController$showDialog$1$1$d$1);
                                    SystemUIDialog.setShowForAllUsers(privacyDialog);
                                    PrivacyDialogController$onDialogDismissed$1 privacyDialogController$onDialogDismissed$1 = PrivacyDialogController.this.onDialogDismissed;
                                    if (privacyDialog.dismissed.get()) {
                                        PrivacyDialogController privacyDialogController6 = privacyDialogController$onDialogDismissed$1.this$0;
                                        privacyDialogController6.privacyLogger.logPrivacyDialogDismissed();
                                        privacyDialogController6.uiEventLogger.log(PrivacyDialogEvent.PRIVACY_DIALOG_DISMISSED);
                                        privacyDialogController6.dialog = null;
                                    } else {
                                        ((ArrayList) privacyDialog.dismissListeners).add(new WeakReference(privacyDialogController$onDialogDismissed$1));
                                    }
                                    PrivacyDialogController.this.getClass();
                                    privacyDialog.mDialogTranslateX = 0;
                                    SecPanelSplitHelper.Companion.getClass();
                                    privacyDialog.qsExpanded = SecPanelSplitHelper.isEnabled ? ((SecPanelSplitHelper) PrivacyDialogController.this.panelSplitHepler$delegate.getValue()).isQSState() : ((Boolean) ((ShadeInteractorImpl) PrivacyDialogController.this.shadeInteractor).baseShadeInteractor.isQsExpanded().getValue()).booleanValue();
                                    privacyDialog.show();
                                    PrivacyDialogController.this.privacyLogger.logShowDialogContents(arrayList2);
                                    PrivacyDialogController.this.dialog = privacyDialog;
                                }
                            });
                        }
                    });
                }
            }
        };
        OngoingPrivacyChip ongoingPrivacyChip = headerPrivacyIconsController.privacyChip;
        ongoingPrivacyChip.setOnClickListener(onClickListener);
        headerPrivacyIconsController.setChipVisibility(ongoingPrivacyChip.getVisibility() == 0);
        PrivacyConfig privacyConfig = headerPrivacyIconsController.privacyItemController.privacyConfig;
        headerPrivacyIconsController.micCameraIndicatorsEnabled = privacyConfig.micCameraAvailable;
        headerPrivacyIconsController.locationIndicatorsEnabled = privacyConfig.locationAvailable;
        headerPrivacyIconsController.updatePrivacyIconSlots();
        this.statusIconContainerController.init();
        this.mView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.shade.ShadeHeaderController$onInit$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SecPanelSplitHelper.Companion.getClass();
                if (SecPanelSplitHelper.isEnabled) {
                    return false;
                }
                return ShadeHeaderController.this.panelExpanded;
            }
        });
        this.lockscreenShadeTransitionController.addCallback(this);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.privacyIconsController.chipVisibilityListener = this.chipVisibilityListener;
        updateVisibility$5();
        updateTransition();
        this.header.setOnApplyWindowInsetsListener(this.insetListener);
        this.dumpManager.registerDumpable(this);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationControllerListener);
        TintedIconManager tintedIconManager = this.iconManager;
        if (tintedIconManager == null) {
            tintedIconManager = null;
        }
        ((StatusBarIconControllerImpl) this.statusBarIconController).addIconGroup(tintedIconManager);
        this.batteryIcon.setTag("ShadeHeaderController");
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.privacyIconsController.chipVisibilityListener = null;
        this.dumpManager.unregisterDumpable("ShadeHeaderController");
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationControllerListener);
        TintedIconManager tintedIconManager = this.iconManager;
        if (tintedIconManager == null) {
            tintedIconManager = null;
        }
        ((StatusBarIconControllerImpl) this.statusBarIconController).removeIconGroup(tintedIconManager);
        this.systemIconsHoverContainer.setOnHoverListener(null);
    }

    @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
    public final void setTransitionToFullShadeAmount(float f) {
        boolean z = !(f == 0.0f);
        if (this.mPanelExpandedInLockScreen != z) {
            this.mPanelExpandedInLockScreen = z;
            updateVisibility$5();
        }
    }

    public final void simulateViewDetached$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        onViewDetached();
    }

    public final void updateHeaderPadding() {
        Context context = getContext();
        SecQSPanelResourcePhonePicker targetPicker = this.qsPanelResourcePicker.resourcePickHelper.getTargetPicker();
        targetPicker.getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        int dateButtonContainerPadding = SecQSPanelResourceCommon.Companion.isPortrait(context) ? targetPicker.getDateButtonContainerPadding(context) : targetPicker.getDateButtonContainerPadding(context);
        MotionLayout motionLayout = this.header;
        motionLayout.setPadding(dateButtonContainerPadding, motionLayout.getPaddingTop(), dateButtonContainerPadding, motionLayout.getPaddingBottom());
    }

    public final void updateTransition() {
        boolean z = this.largeScreenActive;
        MotionLayout motionLayout = this.header;
        if (z) {
            Trace.instantForTrack(4096L, "LargeScreenHeaderController", "Large screen constraints set");
            motionLayout.setTransition(LARGE_SCREEN_HEADER_TRANSITION_ID);
        } else {
            Trace.instantForTrack(4096L, "LargeScreenHeaderController", "Small screen constraints set");
            motionLayout.setTransition(HEADER_TRANSITION_ID);
            this.systemIconsHoverContainer.setOnClickListener(null);
            this.systemIconsHoverContainer.setClickable(false);
        }
        motionLayout.jumpToState(motionLayout.getStartState());
        if (this.largeScreenActive) {
            return;
        }
        motionLayout.setScrollY(this.qsScrollY);
    }

    public final void updateVisibility$5() {
        int i = this.qsDisabled ? 8 : ((!this.qsVisible || this.customizing) && !this.mPanelExpandedInLockScreen) ? 4 : 0;
        MotionLayout motionLayout = this.header;
        if (motionLayout.getVisibility() != i) {
            motionLayout.setVisibility(i);
        }
        boolean z = this.visible;
        if (z || i != 0 || z) {
            return;
        }
        this.visible = true;
        ShadeCarrierGroupController shadeCarrierGroupController = this.mShadeCarrierGroupController;
        if (shadeCarrierGroupController == null) {
            shadeCarrierGroupController = null;
        }
        shadeCarrierGroupController.setListening(true);
    }
}
