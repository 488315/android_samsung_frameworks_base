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
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Trace;
import android.os.UserHandle;
import android.permission.PermissionGroupUsage;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.DualToneHandler;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.HeaderPrivacyIconsController;
import com.android.systemui.qs.SecDarkModeEasel;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyChipEvent;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.carrier.ShadeCarrier;
import com.android.systemui.shade.carrier.ShadeCarrierGroup;
import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.StatusIconContainerController;
import com.android.systemui.statusbar.phone.SwitchableDoubleShadowTextView;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.NetspeedView;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.VariableDateView;
import com.android.systemui.statusbar.policy.VariableDateViewController;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeHeaderController extends ViewController implements Dumpable {
    public final ActivityStarter activityStarter;
    public final BatteryMeterView batteryIcon;
    public final BatteryMeterViewController batteryMeterViewController;
    public final ShadeHeaderController$chipVisibilityListener$1 chipVisibilityListener;
    public final Clock clock;
    public final CombinedShadeHeadersConstraintManager combinedShadeHeadersConstraintManager;
    public final ConfigurationController configurationController;
    public final ShadeHeaderController$configurationControllerListener$1 configurationControllerListener;
    public boolean customizing;
    public DisplayCutout cutout;
    public final SecDarkModeEasel darkModelEasel;
    public final TextView date;
    public final DemoModeController demoModeController;
    public final ShadeHeaderController$demoModeReceiver$1 demoModeReceiver;
    public final DumpManager dumpManager;
    public final MotionLayout header;
    public final StatusIconContainer iconContainer;
    public StatusBarIconController.TintedIconManager iconManager;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final ShadeHeaderController$insetListener$1 insetListener;
    public final StatusBarContentInsetsProvider insetsProvider;
    public boolean largeScreenActive;
    public WindowInsets lastInsets;
    public final ShadeCarrierGroup mShadeCarrierGroup;
    public ShadeCarrierGroupController mShadeCarrierGroupController;
    public final NetspeedViewController netspeedViewController;
    public final ShadeHeaderController$nextAlarmCallback$1 nextAlarmCallback;
    public final NextAlarmController nextAlarmController;
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
    public final ShadeHeaderColorPicker shadeHeaderColorPicker;
    public final StatusBarIconController statusBarIconController;
    public final StatusIconContainerController statusIconContainerController;
    public final StatusBarIconController.TintedIconManager.Factory tintedIconManagerFactory;
    public final VariableDateViewController.Factory variableDateViewControllerFactory;
    public boolean visible;
    public static final Companion Companion = new Companion(null);
    public static final int HEADER_TRANSITION_ID = R.id.header_transition;
    public static final int LARGE_SCREEN_HEADER_TRANSITION_ID = R.id.large_screen_header_transition;
    public static final int QQS_HEADER_CONSTRAINT = R.id.qqs_header_constraint;
    public static final int QS_HEADER_CONSTRAINT = R.id.qs_header_constraint;
    public static final int LARGE_SCREEN_HEADER_CONSTRAINT = R.id.large_screen_header_constraint;
    public static final Intent DEFAULT_CLOCK_INTENT = new Intent("android.intent.action.SHOW_ALARMS");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                    shadeHeaderController.updateVisibility();
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
                shadeHeaderController.updateVisibility();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.shade.ShadeHeaderController$insetListener$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.shade.ShadeHeaderController$demoModeReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.shade.ShadeHeaderController$configurationControllerListener$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.shade.ShadeHeaderController$nextAlarmCallback$1] */
    public ShadeHeaderController(MotionLayout motionLayout, StatusBarIconController statusBarIconController, StatusBarIconController.TintedIconManager.Factory factory, HeaderPrivacyIconsController headerPrivacyIconsController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, ConfigurationController configurationController, VariableDateViewController.Factory factory2, BatteryMeterViewController batteryMeterViewController, DumpManager dumpManager, ShadeCarrierGroupController.Builder builder, CombinedShadeHeadersConstraintManager combinedShadeHeadersConstraintManager, DemoModeController demoModeController, QsBatteryModeController qsBatteryModeController, NextAlarmController nextAlarmController, ActivityStarter activityStarter, SecQSPanelResourcePicker secQSPanelResourcePicker, StatusIconContainerController statusIconContainerController, IndicatorScaleGardener indicatorScaleGardener, NetspeedViewController netspeedViewController, ShadeHeaderColorPicker shadeHeaderColorPicker) {
        super(motionLayout);
        this.header = motionLayout;
        this.statusBarIconController = statusBarIconController;
        this.tintedIconManagerFactory = factory;
        this.privacyIconsController = headerPrivacyIconsController;
        this.insetsProvider = statusBarContentInsetsProvider;
        this.configurationController = configurationController;
        this.variableDateViewControllerFactory = factory2;
        this.batteryMeterViewController = batteryMeterViewController;
        this.dumpManager = dumpManager;
        this.shadeCarrierGroupControllerBuilder = builder;
        this.combinedShadeHeadersConstraintManager = combinedShadeHeadersConstraintManager;
        this.demoModeController = demoModeController;
        this.qsBatteryModeController = qsBatteryModeController;
        this.nextAlarmController = nextAlarmController;
        this.activityStarter = activityStarter;
        this.qsPanelResourcePicker = secQSPanelResourcePicker;
        this.statusIconContainerController = statusIconContainerController;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.netspeedViewController = netspeedViewController;
        this.shadeHeaderColorPicker = shadeHeaderColorPicker;
        this.batteryIcon = (BatteryMeterView) motionLayout.findViewById(R.id.batteryRemainingIcon);
        this.clock = (Clock) motionLayout.findViewById(R.id.clock);
        this.date = (TextView) motionLayout.findViewById(R.id.date);
        this.iconContainer = (StatusIconContainer) motionLayout.findViewById(R.id.statusIcons);
        this.mShadeCarrierGroup = (ShadeCarrierGroup) motionLayout.findViewById(R.id.carrier_group);
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
        this.demoModeReceiver = new DemoMode() { // from class: com.android.systemui.shade.ShadeHeaderController$demoModeReceiver$1
            @Override // com.android.systemui.demomode.DemoMode
            public final List demoCommands() {
                return Collections.singletonList(SubRoom.EXTRA_VALUE_CLOCK);
            }

            @Override // com.android.systemui.demomode.DemoModeCommandReceiver
            public final void dispatchDemoCommand(Bundle bundle, String str) {
                ShadeHeaderController.this.clock.dispatchDemoCommand(bundle, str);
            }

            @Override // com.android.systemui.demomode.DemoModeCommandReceiver
            public final void onDemoModeFinished() {
                ShadeHeaderController.this.clock.onDemoModeFinished();
            }
        };
        this.chipVisibilityListener = new ShadeHeaderController$chipVisibilityListener$1(this);
        this.configurationControllerListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.shade.ShadeHeaderController$configurationControllerListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                ShadeHeaderController.Companion companion = ShadeHeaderController.Companion;
                ShadeHeaderController shadeHeaderController = ShadeHeaderController.this;
                shadeHeaderController.updateHeaderPadding();
                shadeHeaderController.darkModelEasel.updateColors(configuration);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ShadeHeaderController.Companion companion = ShadeHeaderController.Companion;
                ShadeHeaderController shadeHeaderController = ShadeHeaderController.this;
                float dimensionPixelSize = shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size) * shadeHeaderController.indicatorScaleGardener.getLatestScaleModel(shadeHeaderController.getContext()).ratio;
                Clock clock = shadeHeaderController.clock;
                clock.setTextAppearance(2132018309);
                clock.setTextSize(0, dimensionPixelSize);
                shadeHeaderController.date.setTextAppearance(2132018304);
                ShadeCarrierGroup shadeCarrierGroup = shadeHeaderController.mShadeCarrierGroup;
                TextView textView = (TextView) shadeCarrierGroup.findViewById(R.id.no_carrier_text);
                TypedArray obtainStyledAttributes = textView.getContext().obtainStyledAttributes(2132018308, new int[]{android.R.attr.textSize});
                textView.setTextSize(0, obtainStyledAttributes.getDimensionPixelSize(0, (int) textView.getTextSize()));
                obtainStyledAttributes.recycle();
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier1)).mCarrierText.setTextAppearance(2132018308);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier2)).mCarrierText.setTextAppearance(2132018308);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier3)).mCarrierText.setTextAppearance(2132018308);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier1)).mCarrierText.setTextSize(0, dimensionPixelSize);
                int i = ShadeHeaderController.QQS_HEADER_CONSTRAINT;
                MotionLayout motionLayout2 = shadeHeaderController.header;
                motionLayout2.getConstraintSet(i).load(shadeHeaderController.getContext(), shadeHeaderController.getResources().getXml(R.xml.qqs_header));
                motionLayout2.getConstraintSet(ShadeHeaderController.QS_HEADER_CONSTRAINT).load(shadeHeaderController.getContext(), shadeHeaderController.getResources().getXml(R.xml.qs_header));
                motionLayout2.getConstraintSet(ShadeHeaderController.LARGE_SCREEN_HEADER_CONSTRAINT).load(shadeHeaderController.getContext(), shadeHeaderController.getResources().getXml(R.xml.large_screen_shade_header));
                int dimensionPixelSize2 = shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.large_screen_shade_header_min_height);
                if (dimensionPixelSize2 != motionLayout2.mMinHeight) {
                    motionLayout2.mMinHeight = dimensionPixelSize2;
                    motionLayout2.requestLayout();
                }
                WindowInsets windowInsets = shadeHeaderController.lastInsets;
                if (windowInsets != null) {
                    ShadeHeaderController.access$updateConstraintsForInsets(shadeHeaderController, motionLayout2, windowInsets);
                }
                shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.rounded_corner_content_padding);
                shadeHeaderController.updateHeaderPadding();
                shadeHeaderController.updateQQSPaddings();
                shadeHeaderController.qsBatteryModeController.updateResources();
                OngoingPrivacyChip ongoingPrivacyChip = shadeHeaderController.privacyIconsController.privacyChip;
                ongoingPrivacyChip.updateResources();
                ongoingPrivacyChip.iconsContainer.setBackground(ongoingPrivacyChip.getContext().getDrawable(R.drawable.sec_privacy_chip_bg));
                ongoingPrivacyChip.iconsContainer.getLayoutParams().height = ongoingPrivacyChip.getContext().getResources().getDimensionPixelSize(R.dimen.sec_ongoing_appops_chip_height);
                int dimensionPixelSize3 = ongoingPrivacyChip.getContext().getResources().getDimensionPixelSize(R.dimen.sec_ongoing_appops_chip_start_end_padding);
                ongoingPrivacyChip.iconsContainer.setPadding(dimensionPixelSize3, 0, dimensionPixelSize3, 0);
                ongoingPrivacyChip.setPrivacyList(ongoingPrivacyChip.privacyList);
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDisplayDeviceTypeChanged() {
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    onDensityOrFontScaleChanged();
                }
            }
        };
        this.nextAlarmCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.shade.ShadeHeaderController$nextAlarmCallback$1
            @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                ShadeHeaderController.this.nextAlarmIntent = alarmClockInfo != null ? alarmClockInfo.getShowIntent() : null;
            }
        };
        this.darkModelEasel = new SecDarkModeEasel(new SecDarkModeEasel.PictureSubject() { // from class: com.android.systemui.shade.ShadeHeaderController$darkModelEasel$1
            @Override // com.android.systemui.qs.SecDarkModeEasel.PictureSubject
            public final void drawDarkModelPicture() {
                ShadeHeaderController.Companion companion = ShadeHeaderController.Companion;
                ShadeHeaderController.this.updateColors();
            }
        });
    }

    public static final void access$updateConstraintsForInsets(ShadeHeaderController shadeHeaderController, MotionLayout motionLayout, WindowInsets windowInsets) {
        ConstraintsChanges plus;
        Integer num;
        shadeHeaderController.getClass();
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        shadeHeaderController.cutout = displayCutout;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = shadeHeaderController.insetsProvider;
        Pair statusBarContentInsetsForCurrentRotation = statusBarContentInsetsProvider.getStatusBarContentInsetsForCurrentRotation();
        Integer num2 = (Integer) statusBarContentInsetsForCurrentRotation.first;
        Integer num3 = (Integer) statusBarContentInsetsForCurrentRotation.second;
        boolean currentRotationHasCornerCutout = statusBarContentInsetsProvider.currentRotationHasCornerCutout();
        shadeHeaderController.updateQQSPaddings();
        final int intValue = (motionLayout.isLayoutRtl() ? num3 : num2).intValue();
        MotionLayout motionLayout2 = shadeHeaderController.header;
        final int paddingStart = motionLayout2.getPaddingStart();
        if (!motionLayout.isLayoutRtl()) {
            num2 = num3;
        }
        final int intValue2 = num2.intValue();
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
                constraintSet.setGuidelineBegin(R.id.begin_guide, Math.max(intValue - paddingStart, 0));
                constraintSet.setGuidelineEnd(R.id.end_guide, Math.max(intValue2 - paddingEnd, 0));
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
                        int[] iArr = {R.id.statusIcons, R.id.privacy_container};
                        ConstraintSet.Layout layout = constraintSet.get(R.id.barrier).layout;
                        layout.mHelperType = 1;
                        layout.mBarrierDirection = 6;
                        layout.mBarrierMargin = 0;
                        layout.mIsGuideline = false;
                        layout.mReferenceIds = iArr;
                        constraintSet.connect(R.id.statusIcons, 6, R.id.date, 7);
                        constraintSet.connect(R.id.privacy_container, 6, R.id.date, 7);
                        constraintSet.constrainWidth(R.id.statusIcons, -2);
                        constraintSet.get(R.id.date).layout.constrainedWidth = true;
                        constraintSet.get(R.id.statusIcons).layout.constrainedWidth = true;
                        return Unit.INSTANCE;
                    }
                }, null, null, 6, null));
            } else {
                boolean isLayoutRtl = motionLayout.isLayoutRtl();
                final int width = (((motionLayout.getWidth() - motionLayout.getPaddingLeft()) - motionLayout.getPaddingRight()) - boundingRectTop.width()) / 2;
                final int i = R.id.center_left;
                final int i2 = !isLayoutRtl ? R.id.center_left : R.id.center_right;
                if (!isLayoutRtl) {
                    i = R.id.center_right;
                }
                plus = constraintsChanges.plus(new ConstraintsChanges(new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$centerCutoutConstraints$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ConstraintSet constraintSet = (ConstraintSet) obj;
                        constraintSet.setGuidelineBegin(i2, width);
                        constraintSet.setGuidelineEnd(i, width);
                        constraintSet.connect(R.id.date, 7, i2, 6);
                        constraintSet.connect(R.id.statusIcons, 6, i, 7);
                        constraintSet.connect(R.id.privacy_container, 6, i, 7);
                        constraintSet.get(R.id.date).layout.constrainedWidth = true;
                        constraintSet.get(R.id.statusIcons).layout.constrainedWidth = true;
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
                        constraintSet.setGuidelineBegin(i2, width);
                        constraintSet.setGuidelineEnd(i, width);
                        constraintSet.connect(R.id.privacy_container, 6, i, 7);
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
                    int[] iArr = {R.id.statusIcons, R.id.privacy_container};
                    ConstraintSet.Layout layout = constraintSet.get(R.id.barrier).layout;
                    layout.mHelperType = 1;
                    layout.mBarrierDirection = 6;
                    layout.mBarrierMargin = 0;
                    layout.mIsGuideline = false;
                    layout.mReferenceIds = iArr;
                    constraintSet.connect(R.id.statusIcons, 6, R.id.date, 7);
                    constraintSet.connect(R.id.privacy_container, 6, R.id.date, 7);
                    constraintSet.constrainWidth(R.id.statusIcons, -2);
                    constraintSet.get(R.id.date).layout.constrainedWidth = true;
                    constraintSet.get(R.id.statusIcons).layout.constrainedWidth = true;
                    return Unit.INSTANCE;
                }
            }, null, null, 6, null));
        }
        Function1 function12 = plus.qqsConstraintsChanges;
        if (function12 != null) {
            int i3 = QQS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet = motionLayout.getConstraintSet(i3);
            function12.invoke(constraintSet);
            motionLayout.updateState(i3, constraintSet);
        }
        Function1 function13 = plus.qsConstraintsChanges;
        if (function13 != null) {
            int i4 = QS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet2 = motionLayout.getConstraintSet(i4);
            function13.invoke(constraintSet2);
            motionLayout.updateState(i4, constraintSet2);
        }
        DisplayCutout displayCutout2 = shadeHeaderController.cutout;
        float f = shadeHeaderController.qsExpandedFraction;
        QsBatteryModeController qsBatteryModeController = shadeHeaderController.qsBatteryModeController;
        if (f > qsBatteryModeController.fadeInStartFraction) {
            num = 3;
        } else if (f < qsBatteryModeController.fadeOutCompleteFraction) {
            num = Integer.valueOf(displayCutout2 != null && !qsBatteryModeController.insetsProvider.currentRotationHasCornerCutout() && !displayCutout2.getBoundingRectTop().isEmpty() ? 1 : 3);
        } else {
            num = null;
        }
        if (num != null) {
            shadeHeaderController.batteryIcon.setPercentShowMode(num.intValue());
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("visible: ", this.visible, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("shadeExpanded: ", this.qsVisible, printWriter);
        printWriter.println("shadeExpandedFraction: " + this.shadeExpandedFraction);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("active: ", this.largeScreenActive, printWriter);
        printWriter.println("qsExpandedFraction: " + this.qsExpandedFraction);
        SideFpsController$$ExternalSyntheticOutline0.m105m("qsScrollY: ", this.qsScrollY, printWriter);
        int i = this.header.mCurrentState;
        Companion.getClass();
        FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("currentState: ", i == QQS_HEADER_CONSTRAINT ? "QQS Header" : i == QS_HEADER_CONSTRAINT ? "QS Header" : i == LARGE_SCREEN_HEADER_CONSTRAINT ? "Large Screen Header" : AbstractC0000x2c234b15.m0m("Unknown state ", i), printWriter);
    }

    public final int getViewHeight() {
        return this.header.getHeight();
    }

    /* renamed from: launchClockActivity$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void m181xc1924890() {
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
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView;
        VariableDateView variableDateView = (VariableDateView) this.date;
        VariableDateViewController.Factory factory = this.variableDateViewControllerFactory;
        new VariableDateViewController(factory.systemClock, factory.broadcastDispatcher, factory.handler, variableDateView).init();
        boolean z = BasicRune.STATUS_REAL_TIME_NETWORK_SPEED;
        NetspeedViewController netspeedViewController = this.netspeedViewController;
        if (z && netspeedViewController != null) {
            netspeedViewController.init();
        }
        BatteryMeterViewController batteryMeterViewController = this.batteryMeterViewController;
        batteryMeterViewController.init();
        batteryMeterViewController.mIgnoreTunerUpdates = true;
        if (batteryMeterViewController.mIsSubscribedForTunerUpdates) {
            batteryMeterViewController.mTunerService.removeTunable(batteryMeterViewController.mTunable);
            batteryMeterViewController.mIsSubscribedForTunerUpdates = false;
        }
        ((BatteryMeterView) batteryMeterViewController.mView).mApplyShadowToPercentView = true;
        if (z && netspeedViewController != null && (switchableDoubleShadowTextView = ((NetspeedView) netspeedViewController.mView).mContentView) != null) {
            switchableDoubleShadowTextView.shadowEnabled = true;
        }
        StatusIconContainer statusIconContainer = this.iconContainer;
        StatusBarLocation statusBarLocation = StatusBarLocation.QS;
        StatusBarIconController.TintedIconManager.Factory factory2 = this.tintedIconManagerFactory;
        StatusBarIconController.TintedIconManager tintedIconManager = new StatusBarIconController.TintedIconManager(statusIconContainer, statusBarLocation, factory2.mStatusBarPipelineFlags, factory2.mWifiUiAdapter, factory2.mMobileUiAdapter, factory2.mMobileContextProvider, factory2.mBTTetherUiAdapter);
        this.iconManager = tintedIconManager;
        tintedIconManager.setTint(getContext().getColor(R.color.status_bar_clock_color));
        Collections.singletonList(this.header.getContext().getString(17042930));
        ShadeCarrierGroup shadeCarrierGroup = this.mShadeCarrierGroup;
        ShadeCarrierGroupController.Builder builder = this.shadeCarrierGroupControllerBuilder;
        builder.getClass();
        this.mShadeCarrierGroupController = new ShadeCarrierGroupController(shadeCarrierGroup, builder.mActivityStarter, builder.mHandler, builder.mLooper, builder.mNetworkController, builder.mCarrierTextControllerBuilder, builder.mContext, builder.mCarrierConfigTracker, builder.mSlotIndexResolver, builder.mLatinNetworkNameProvider, builder.mSlimIndicatorViewMediator, 0);
        final HeaderPrivacyIconsController headerPrivacyIconsController = this.privacyIconsController;
        headerPrivacyIconsController.getClass();
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$onParentVisible$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (((DeviceProvisionedControllerImpl) HeaderPrivacyIconsController.this.deviceProvisionedController).isDeviceProvisioned()) {
                    HeaderPrivacyIconsController.this.uiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_CLICK);
                    HeaderPrivacyIconsController headerPrivacyIconsController2 = HeaderPrivacyIconsController.this;
                    final PrivacyDialogController privacyDialogController = headerPrivacyIconsController2.privacyDialogController;
                    final Context context = headerPrivacyIconsController2.privacyChip.getContext();
                    Dialog dialog = privacyDialogController.dialog;
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    privacyDialogController.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyDialogController$showDialog$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            PrivacyType privacyType;
                            PrivacyType privacyType2;
                            Object obj;
                            CharSequence charSequence;
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
                                    boolean isPhoneCall = permissionGroupUsage.isPhoneCall();
                                    PackageManager packageManager = privacyDialogController3.packageManager;
                                    if (isPhoneCall) {
                                        charSequence = "";
                                    } else {
                                        String packageName = permissionGroupUsage.getPackageName();
                                        try {
                                            charSequence = packageManager.getApplicationInfoAsUser(packageName, 0, UserHandle.getUserId(permissionGroupUsage.getUid())).loadLabel(packageManager);
                                        } catch (PackageManager.NameNotFoundException unused) {
                                            Log.w("PrivacyDialogController", "Label not found for: ".concat(packageName));
                                            charSequence = packageName;
                                        }
                                    }
                                    CharSequence charSequence2 = charSequence;
                                    int userId = UserHandle.getUserId(permissionGroupUsage.getUid());
                                    String packageName2 = permissionGroupUsage.getPackageName();
                                    CharSequence attributionTag = permissionGroupUsage.getAttributionTag();
                                    CharSequence attributionLabel = permissionGroupUsage.getAttributionLabel();
                                    CharSequence proxyLabel = permissionGroupUsage.getProxyLabel();
                                    long lastAccessTimeMillis = permissionGroupUsage.getLastAccessTimeMillis();
                                    boolean isActive = permissionGroupUsage.isActive();
                                    boolean isManagedProfile = userInfo != null ? userInfo.isManagedProfile() : false;
                                    boolean isPhoneCall2 = permissionGroupUsage.isPhoneCall();
                                    String permissionGroupName2 = permissionGroupUsage.getPermissionGroupName();
                                    String packageName3 = permissionGroupUsage.getPackageName();
                                    String permissionGroupName3 = permissionGroupUsage.getPermissionGroupName();
                                    CharSequence attributionTag2 = permissionGroupUsage.getAttributionTag();
                                    boolean z2 = permissionGroupUsage.getAttributionLabel() != null;
                                    if (attributionTag2 != null && z2) {
                                        intent = new Intent("android.intent.action.MANAGE_PERMISSION_USAGE");
                                        intent.setPackage(packageName3);
                                        intent.putExtra("android.intent.extra.PERMISSION_GROUP_NAME", permissionGroupName3.toString());
                                        intent.putExtra("android.intent.extra.ATTRIBUTION_TAGS", new String[]{attributionTag2.toString()});
                                        intent.putExtra("android.intent.extra.SHOWING_ATTRIBUTION", true);
                                        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, PackageManager.ResolveInfoFlags.of(0L));
                                        if (resolveActivity != null && (activityInfo = resolveActivity.activityInfo) != null && Intrinsics.areEqual(activityInfo.permission, "android.permission.START_VIEW_PERMISSION_USAGE")) {
                                            intent.setComponent(new ComponentName(packageName3, resolveActivity.activityInfo.name));
                                            privacyElement = new PrivacyDialog.PrivacyElement(privacyType2, packageName2, userId, charSequence2, attributionTag, attributionLabel, proxyLabel, lastAccessTimeMillis, isActive, isManagedProfile, isPhoneCall2, permissionGroupName2, intent);
                                        }
                                    }
                                    intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
                                    intent.putExtra("android.intent.extra.PACKAGE_NAME", packageName3);
                                    intent.putExtra("android.intent.extra.USER", UserHandle.of(userId));
                                    privacyElement = new PrivacyDialog.PrivacyElement(privacyType2, packageName2, userId, charSequence2, attributionTag, attributionLabel, proxyLabel, lastAccessTimeMillis, isActive, isManagedProfile, isPhoneCall2, permissionGroupName2, intent);
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
                                        ArrayList arrayList3 = new ArrayList();
                                        for (Object obj5 : list2) {
                                            if (((PrivacyDialog.PrivacyElement) obj5).active) {
                                                arrayList3.add(obj5);
                                            }
                                        }
                                        if (true ^ arrayList3.isEmpty()) {
                                            singletonList = CollectionsKt___CollectionsKt.sortedWith(arrayList3, new Comparator() { // from class: com.android.systemui.privacy.PrivacyDialogController$filterAndSelect$lambda$6$$inlined$sortedByDescending$1
                                                @Override // java.util.Comparator
                                                public final int compare(Object obj6, Object obj7) {
                                                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((PrivacyDialog.PrivacyElement) obj7).lastActiveTimestamp), Long.valueOf(((PrivacyDialog.PrivacyElement) obj6).lastActiveTimestamp));
                                                }
                                            });
                                        } else {
                                            Iterator it3 = list2.iterator();
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
                                    PrivacyDialogController privacyDialogController6 = PrivacyDialogController.this;
                                    privacyDialogController6.shadeExpansionStateManager.addQsExpansionListener(privacyDialogController6.shadeQsExpansionListener);
                                    PrivacyDialogController.DialogProvider dialogProvider = PrivacyDialogController.this.dialogProvider;
                                    Context context3 = context2;
                                    PrivacyDialogController$showDialog$1$1$d$1 privacyDialogController$showDialog$1$1$d$1 = new PrivacyDialogController$showDialog$1$1$d$1(PrivacyDialogController.this);
                                    ((PrivacyDialogControllerKt$defaultDialogProvider$1) dialogProvider).getClass();
                                    PrivacyDialog privacyDialog = new PrivacyDialog(context3, arrayList2, privacyDialogController$showDialog$1$1$d$1);
                                    SystemUIDialog.setShowForAllUsers(privacyDialog);
                                    PrivacyDialogController$onDialogDismissed$1 privacyDialogController$onDialogDismissed$1 = PrivacyDialogController.this.onDialogDismissed;
                                    if (privacyDialog.dismissed.get()) {
                                        PrivacyDialogController privacyDialogController7 = privacyDialogController$onDialogDismissed$1.this$0;
                                        privacyDialogController7.shadeExpansionStateManager.qsExpansionListeners.remove(privacyDialogController7.shadeQsExpansionListener);
                                        privacyDialogController7.privacyLogger.logPrivacyDialogDismissed();
                                        privacyDialogController7.uiEventLogger.log(PrivacyDialogEvent.PRIVACY_DIALOG_DISMISSED);
                                        privacyDialogController7.dialog = null;
                                    } else {
                                        ((ArrayList) privacyDialog.dismissListeners).add(new WeakReference(privacyDialogController$onDialogDismissed$1));
                                    }
                                    PrivacyDialogController privacyDialogController8 = PrivacyDialogController.this;
                                    privacyDialog.mDialogTranslateX = privacyDialogController8.mDialogTranslateX;
                                    privacyDialog.qsExpanded = privacyDialogController8.qsExpanded;
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
                return ShadeHeaderController.this.panelExpanded;
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.privacyIconsController.chipVisibilityListener = this.chipVisibilityListener;
        updateVisibility();
        updateTransition();
        updateColors();
        this.header.setOnApplyWindowInsetsListener(this.insetListener);
        this.clock.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.shade.ShadeHeaderController$onViewAttached$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                view.setPivotX(view.isLayoutRtl() ? view.getWidth() : 0.0f);
                view.setPivotY(view.getHeight() / 2);
            }
        });
        this.dumpManager.registerDumpable(this);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationControllerListener);
        this.demoModeController.addCallback((DemoMode) this.demoModeReceiver);
        StatusBarIconController.TintedIconManager tintedIconManager = this.iconManager;
        if (tintedIconManager == null) {
            tintedIconManager = null;
        }
        ((StatusBarIconControllerImpl) this.statusBarIconController).addIconGroup(tintedIconManager);
        ((NextAlarmControllerImpl) this.nextAlarmController).addCallback(this.nextAlarmCallback);
        if (QpRune.QUICK_TABLET_BG) {
            this.mView.setElevation(500.0f);
        }
        this.batteryIcon.setTag("ShadeHeaderController");
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.clock.setOnClickListener(null);
        this.privacyIconsController.chipVisibilityListener = null;
        this.dumpManager.unregisterDumpable("ShadeHeaderController");
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configurationControllerListener);
        this.demoModeController.removeCallback((DemoMode) this.demoModeReceiver);
        StatusBarIconController.TintedIconManager tintedIconManager = this.iconManager;
        ((StatusBarIconControllerImpl) this.statusBarIconController).removeIconGroup(tintedIconManager != null ? tintedIconManager : null);
        ((NextAlarmControllerImpl) this.nextAlarmController).removeCallback(this.nextAlarmCallback);
    }

    /* renamed from: simulateViewDetached$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void m182x4d750941() {
        onViewDetached();
    }

    public final void updateColors() {
        Context context = this.header.getContext();
        ShadeHeaderColorPicker shadeHeaderColorPicker = this.shadeHeaderColorPicker;
        shadeHeaderColorPicker.update(context);
        this.clock.setTextColor(shadeHeaderColorPicker.getClockColor());
        ((ShadeCarrier) this.mShadeCarrierGroup.findViewById(R.id.carrier1)).mCarrierText.setTextColor(shadeHeaderColorPicker.getClockColor());
        StatusBarIconController.TintedIconManager tintedIconManager = this.iconManager;
        if (tintedIconManager == null) {
            tintedIconManager = null;
        }
        float f = shadeHeaderColorPicker.colorIntensity;
        DualToneHandler dualToneHandler = shadeHeaderColorPicker.dualToneHandler;
        tintedIconManager.setTint(dualToneHandler.getSingleColor(f));
        ArrayList arrayList = new ArrayList(Collections.singleton(new Rect(0, 0, 0, 0)));
        float f2 = shadeHeaderColorPicker.colorIntensity;
        this.batteryIcon.onDarkChanged(arrayList, f2, dualToneHandler.getSingleColor(f2));
    }

    public final void updateHeaderPadding() {
        Context context = getContext();
        this.qsPanelResourcePicker.getClass();
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_header_clock_buttons_start_padding) + SecQSPanelResourcePicker.getPanelStartEndPadding(context) + SecQSPanelResourcePicker.getQQSPanelSidePadding(context);
        MotionLayout motionLayout = this.header;
        motionLayout.setPadding(dimensionPixelSize, motionLayout.getPaddingTop(), dimensionPixelSize, motionLayout.getPaddingBottom());
    }

    public final void updateQQSPaddings() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.status_bar_left_clock_starting_padding);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.status_bar_left_clock_end_padding);
        Clock clock = this.clock;
        clock.setPaddingRelative(dimensionPixelSize, clock.getPaddingTop(), dimensionPixelSize2, clock.getPaddingBottom());
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
        }
        int i = motionLayout.mBeginState;
        if (!motionLayout.isAttachedToWindow()) {
            motionLayout.mCurrentState = i;
        }
        if (motionLayout.mBeginState == i) {
            motionLayout.setProgress(0.0f);
        } else if (motionLayout.mEndState == i) {
            motionLayout.setProgress(1.0f);
        } else {
            motionLayout.setTransition(i, i);
        }
        if (this.largeScreenActive) {
            return;
        }
        motionLayout.setScrollY(this.qsScrollY);
    }

    public final void updateVisibility() {
        int i = this.qsDisabled ? 8 : (!this.qsVisible || this.customizing) ? 4 : 0;
        MotionLayout motionLayout = this.header;
        if (motionLayout.getVisibility() != i) {
            motionLayout.setVisibility(i);
        }
        boolean z = this.visible;
        if (z || i != 0) {
            return;
        }
        if (!z) {
            this.visible = true;
            ShadeCarrierGroupController shadeCarrierGroupController = this.mShadeCarrierGroupController;
            if (shadeCarrierGroupController == null) {
                shadeCarrierGroupController = null;
            }
            shadeCarrierGroupController.setListening(true);
        }
        Log.d("ShadeHeaderController", "Shade header is visible but we don't have connection yet, set it again here");
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getDEFAULT_CLOCK_INTENT$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m183x21fde487() {
        }

        /* renamed from: getHEADER_TRANSITION_ID$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m184x4c6392f() {
        }

        /* renamed from: getLARGE_SCREEN_HEADER_CONSTRAINT$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m185xf7bb03fc() {
        }

        /* renamed from: getLARGE_SCREEN_HEADER_TRANSITION_ID$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m186xf64e112() {
        }

        /* renamed from: getQQS_HEADER_CONSTRAINT$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m187xa30a38f3() {
        }

        /* renamed from: getQS_HEADER_CONSTRAINT$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m188xf03ca30e() {
        }
    }
}
