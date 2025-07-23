package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyChipEvent;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.qs.HeaderPrivacyIconsController;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.carrier.ShadeCarrier;
import com.android.systemui.shade.carrier.ShadeCarrierGroup;
import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.data.repository.StatusBarContentInsetsProviderStore;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.StatusIconContainerController;
import com.android.systemui.statusbar.phone.StatusOverlayHoverListenerFactory;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.VariableDateViewController$Factory;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.ViewController;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptyIterator;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeHeaderController extends ViewController implements Dumpable {
    public final ActivityStarter activityStarter;
    public final BatteryMeterView batteryIcon;
    public final BatteryMeterViewController batteryMeterViewController;
    public EmptyList carrierIconSlots;
    public final ShadeHeaderController$chipVisibilityListener$1 chipVisibilityListener;
    public final CombinedShadeHeadersConstraintManager combinedShadeHeadersConstraintManager;
    public final ConfigurationController configurationController;
    public final ShadeHeaderController$configurationControllerListener$1 configurationControllerListener;
    public final Context context;
    public boolean customizing;
    public DisplayCutout cutout;
    public final DumpManager dumpManager;
    public final MotionLayout header;
    public final StatusIconContainer iconContainer;
    public TintedIconManager iconManager;
    public final ShadeHeaderController$insetListener$1 insetListener;
    public boolean largeScreenActive;
    public WindowInsets lastInsets;
    public final ShadeCarrierGroup mShadeCarrierGroup;
    public ShadeCarrierGroupController mShadeCarrierGroupController;
    public PendingIntent nextAlarmIntent;
    public boolean panelExpanded;
    public final HeaderPrivacyIconsController privacyIconsController;
    public final QsBatteryModeController qsBatteryModeController;
    public boolean qsDisabled;
    public float qsExpandedFraction;
    public int qsScrollY;
    public boolean qsVisible;
    public final Lazy samsungExt;
    public final ShadeCarrierGroupController.Builder shadeCarrierGroupControllerBuilder;
    public final Lazy shadeDisplaysRepositoryLazy;
    public float shadeExpandedFraction;
    public final StateFlowImpl showBatteryEstimate;
    public final StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore;
    public final StatusBarIconController statusBarIconController;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    shadeHeaderController.updateVisibility$7();
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
                shadeHeaderController.updateVisibility$7();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.shade.ShadeHeaderController$insetListener$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.shade.ShadeHeaderController$configurationControllerListener$1] */
    public ShadeHeaderController(MotionLayout motionLayout, Lazy lazy, StatusBarIconController statusBarIconController, TintedIconManager.Factory factory, HeaderPrivacyIconsController headerPrivacyIconsController, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore, ConfigurationController configurationController, Context context, Lazy lazy2, VariableDateViewController$Factory variableDateViewController$Factory, BatteryMeterViewController batteryMeterViewController, BatteryViewModel.Factory factory2, DumpManager dumpManager, ShadeCarrierGroupController.Builder builder, CombinedShadeHeadersConstraintManager combinedShadeHeadersConstraintManager, DemoModeController demoModeController, QsBatteryModeController qsBatteryModeController, NextAlarmController nextAlarmController, ActivityStarter activityStarter, StatusOverlayHoverListenerFactory statusOverlayHoverListenerFactory) {
        super(motionLayout);
        this.header = motionLayout;
        this.samsungExt = lazy;
        this.statusBarIconController = statusBarIconController;
        this.tintedIconManagerFactory = factory;
        this.privacyIconsController = headerPrivacyIconsController;
        this.statusBarContentInsetsProviderStore = statusBarContentInsetsProviderStore;
        this.configurationController = configurationController;
        this.context = context;
        this.shadeDisplaysRepositoryLazy = lazy2;
        this.batteryMeterViewController = batteryMeterViewController;
        this.dumpManager = dumpManager;
        this.shadeCarrierGroupControllerBuilder = builder;
        this.combinedShadeHeadersConstraintManager = combinedShadeHeadersConstraintManager;
        this.qsBatteryModeController = qsBatteryModeController;
        this.activityStarter = activityStarter;
        this.batteryIcon = (BatteryMeterView) motionLayout.requireViewById(R.id.batteryRemainingIcon);
        this.iconContainer = (StatusIconContainer) motionLayout.requireViewById(R.id.statusIcons);
        this.mShadeCarrierGroup = (ShadeCarrierGroup) motionLayout.requireViewById(R.id.carrier_group);
        this.systemIconsHoverContainer = motionLayout.requireViewById(R.id.hover_system_icons_container);
        this.showBatteryEstimate = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.largeScreenActive = true;
        this.shadeExpandedFraction = -1.0f;
        this.qsExpandedFraction = -1.0f;
        this.insetListener = new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.shade.ShadeHeaderController$insetListener$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                WindowInsets windowInsets2 = new WindowInsets(windowInsets);
                if (windowInsets2.equals(ShadeHeaderController.this.lastInsets)) {
                    return windowInsets;
                }
                ShadeHeaderController.this.updateConstraintsForInsets((MotionLayout) view, windowInsets);
                ShadeHeaderController.this.lastInsets = windowInsets2;
                return view.onApplyWindowInsets(windowInsets);
            }
        };
        this.chipVisibilityListener = new ShadeHeaderController$chipVisibilityListener$1(this);
        this.configurationControllerListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.shade.ShadeHeaderController$configurationControllerListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                ((SamsungShadeHeaderControllerExt) ShadeHeaderController.this.samsungExt.get()).updateHeaderPadding();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ShadeHeaderController.Companion companion = ShadeHeaderController.Companion;
                ShadeHeaderController shadeHeaderController = ShadeHeaderController.this;
                float dimensionPixelSize = shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size);
                Lazy lazy3 = shadeHeaderController.samsungExt;
                SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = (SamsungShadeHeaderControllerExt) lazy3.get();
                float f = dimensionPixelSize * samsungShadeHeaderControllerExt.indicatorScaleGardener.getLatestScaleModel(samsungShadeHeaderControllerExt.context).ratio;
                ShadeCarrierGroup shadeCarrierGroup = shadeHeaderController.mShadeCarrierGroup;
                TextView textView = (TextView) shadeCarrierGroup.findViewById(R.id.no_carrier_text);
                TypedArray obtainStyledAttributes = textView.getContext().obtainStyledAttributes(R.style.TextAppearance_QS_Status_SamsungCarriers, new int[]{android.R.attr.textSize});
                textView.setTextSize(0, obtainStyledAttributes.getDimensionPixelSize(0, (int) textView.getTextSize()));
                obtainStyledAttributes.recycle();
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier1)).mCarrierText.setTextAppearance(R.style.TextAppearance_QS_Status_SamsungCarriers);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier2)).mCarrierText.setTextAppearance(R.style.TextAppearance_QS_Status_SamsungCarriers);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier3)).mCarrierText.setTextAppearance(R.style.TextAppearance_QS_Status_SamsungCarriers);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier1)).mCarrierText.setTextSize(0, f);
                int i = ShadeHeaderController.QQS_HEADER_CONSTRAINT;
                MotionLayout motionLayout2 = shadeHeaderController.header;
                motionLayout2.getConstraintSet(i).load(shadeHeaderController.context, shadeHeaderController.getResources().getXml(R.xml.qqs_header));
                motionLayout2.getConstraintSet(ShadeHeaderController.QS_HEADER_CONSTRAINT).load(shadeHeaderController.context, shadeHeaderController.getResources().getXml(R.xml.qs_header));
                motionLayout2.getConstraintSet(ShadeHeaderController.LARGE_SCREEN_HEADER_CONSTRAINT).load(shadeHeaderController.context, shadeHeaderController.getResources().getXml(R.xml.large_screen_shade_header));
                motionLayout2.setMinHeight(shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.large_screen_shade_header_min_height));
                WindowInsets windowInsets = shadeHeaderController.lastInsets;
                if (windowInsets != null) {
                    shadeHeaderController.updateConstraintsForInsets(motionLayout2, windowInsets);
                }
                shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.rounded_corner_content_padding);
                ((SamsungShadeHeaderControllerExt) lazy3.get()).updateHeaderPadding();
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

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "visible: ", this.visible);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "shadeExpanded: ", this.qsVisible);
        printWriter.println("shadeExpandedFraction: " + this.shadeExpandedFraction);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "active: ", this.largeScreenActive);
        printWriter.println("qsExpandedFraction: " + this.qsExpandedFraction);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("qsScrollY: ", this.qsScrollY, printWriter);
        int currentState = this.header.getCurrentState();
        Companion.getClass();
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "currentState: ", currentState == QQS_HEADER_CONSTRAINT ? "QQS Header" : currentState == QS_HEADER_CONSTRAINT ? "QS Header" : currentState == LARGE_SCREEN_HEADER_CONSTRAINT ? "Large Screen Header" : MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(currentState, "Unknown state "));
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
        MotionLayout motionLayout = this.header;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(motionLayout.getContext(), android.R.attr.textColorPrimary, 0);
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(motionLayout.getContext(), android.R.attr.textColorPrimaryInverse, 0);
        TintedIconManager create = this.tintedIconManagerFactory.create(this.iconContainer, StatusBarLocation.QS);
        this.iconManager = create;
        create.setTint(this.context.getColor(R.color.status_bar_clock_color), Utils.getColorAttrDefaultColor(motionLayout.getContext(), android.R.attr.textColorPrimaryInverse, 0));
        if (BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION) {
            TintedIconManager tintedIconManager = this.iconManager;
            if (tintedIconManager == null) {
                tintedIconManager = null;
            }
            tintedIconManager.setBlockList(ArraysKt___ArraysKt.toList(getResources().getStringArray(R.array.config_panel_statusbar_icon_blocklist)));
        }
        BatteryMeterViewController batteryMeterViewController = this.batteryMeterViewController;
        batteryMeterViewController.init();
        batteryMeterViewController.mIgnoreTunerUpdates = true;
        if (batteryMeterViewController.mIsSubscribedForTunerUpdates) {
            batteryMeterViewController.mTunerService.removeTunable(batteryMeterViewController.mTunable);
            batteryMeterViewController.mIsSubscribedForTunerUpdates = false;
        }
        BatteryMeterView batteryMeterView = this.batteryIcon;
        batteryMeterView.setVisibility(0);
        batteryMeterView.updateColors(colorAttrDefaultColor, colorAttrDefaultColor2, colorAttrDefaultColor);
        this.carrierIconSlots = EmptyList.INSTANCE;
        ShadeCarrierGroupController.Builder builder = this.shadeCarrierGroupControllerBuilder;
        builder.getClass();
        this.mShadeCarrierGroupController = new ShadeCarrierGroupController(this.mShadeCarrierGroup, builder.mActivityStarter, builder.mHandler, builder.mLooper, builder.mLogger, builder.mNetworkController, builder.mCarrierTextControllerBuilder, builder.mContext, builder.mCarrierConfigTracker, builder.mSlotIndexResolver, builder.mMobileUiAdapter, builder.mMobileContextProvider, builder.mStatusBarPipelineFlags, builder.mMobileUiAdapterKairos, builder.mAppScope, builder.mKairosNetwork, builder.mLatinNetworkNameProvider, builder.mSlimIndicatorViewMediator, 0);
        final HeaderPrivacyIconsController headerPrivacyIconsController = this.privacyIconsController;
        headerPrivacyIconsController.getClass();
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$onParentVisible$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (((DeviceProvisionedControllerImpl) HeaderPrivacyIconsController.this.deviceProvisionedController).deviceProvisioned.get()) {
                    HeaderPrivacyIconsController.this.uiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_CLICK);
                    HeaderPrivacyIconsController headerPrivacyIconsController2 = HeaderPrivacyIconsController.this;
                    final PrivacyDialogController privacyDialogController = headerPrivacyIconsController2.privacyDialogController;
                    final Context context = ((ShadeDialogContextInteractorImpl) headerPrivacyIconsController2.shadeDialogContextInteractor).getContext();
                    PrivacyDialog privacyDialog = privacyDialogController.dialog;
                    if (privacyDialog != null) {
                        privacyDialog.dismiss();
                    }
                    privacyDialogController.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyDialogController$showDialog$1
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:116:0x02af  */
                        /* JADX WARN: Removed duplicated region for block: B:118:0x00da A[SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:26:0x00c6  */
                        /* JADX WARN: Removed duplicated region for block: B:35:0x02b8  */
                        /* JADX WARN: Removed duplicated region for block: B:38:0x02bb A[SYNTHETIC] */
                        /* JADX WARN: Removed duplicated region for block: B:40:0x00f1  */
                        /* JADX WARN: Removed duplicated region for block: B:60:0x0208  */
                        /* JADX WARN: Removed duplicated region for block: B:63:0x021d  */
                        /* JADX WARN: Type inference failed for: r12v4, types: [java.lang.CharSequence, java.lang.Object] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void run() {
                            /*
                                Method dump skipped, instructions count: 723
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.privacy.PrivacyDialogController$showDialog$1.run():void");
                        }
                    });
                }
            }
        };
        OngoingPrivacyChip ongoingPrivacyChip = headerPrivacyIconsController.privacyChip;
        ongoingPrivacyChip.setOnClickListener(onClickListener);
        headerPrivacyIconsController.setChipVisibility(ongoingPrivacyChip.getVisibility() == 0);
        headerPrivacyIconsController.notifyPrivacyItemsChanged(ongoingPrivacyChip.privacyList);
        PrivacyConfig privacyConfig = headerPrivacyIconsController.privacyItemController.privacyConfig;
        headerPrivacyIconsController.micCameraIndicatorsEnabled = privacyConfig.micCameraAvailable;
        headerPrivacyIconsController.locationIndicatorsEnabled = privacyConfig.locationAvailable;
        headerPrivacyIconsController.updatePrivacyIconSlots();
        final SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = (SamsungShadeHeaderControllerExt) this.samsungExt.get();
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.shade.ShadeHeaderController$onInit$2
            @Override // java.lang.Runnable
            public final void run() {
                ShadeHeaderController shadeHeaderController = ShadeHeaderController.this;
                ShadeHeaderController.Companion companion = ShadeHeaderController.Companion;
                shadeHeaderController.updateVisibility$7();
            }
        };
        samsungShadeHeaderControllerExt.printLog$1("onInit()");
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (netspeedViewController = samsungShadeHeaderControllerExt.netspeedViewController) != null) {
            netspeedViewController.init();
        }
        StatusIconContainerController statusIconContainerController = samsungShadeHeaderControllerExt.statusIconContainerController;
        statusIconContainerController.init();
        if (BasicRune.STATUS_POP_OVER_PANEL_BAR && BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            samsungShadeHeaderControllerExt.privacyItemController.addCallback(samsungShadeHeaderControllerExt.privacyItemControllerCallback);
            statusIconContainerController.view.mSidelingCutoutContainerInfo = new SidelingCutoutContainerInfo() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$onInit$3
                @Override // com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo
                public final int getRightSideAvailableWidth(Rect rect) {
                    if (!DeviceState.isShowingPopOverStatusBar()) {
                        return 0;
                    }
                    SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt2 = SamsungShadeHeaderControllerExt.this;
                    int width = samsungShadeHeaderControllerExt2.context.getResources().getConfiguration().windowConfiguration.getBounds().width();
                    int i = rect.right;
                    int paddingEnd = samsungShadeHeaderControllerExt2.headerView.getPaddingEnd();
                    return (width - (((samsungShadeHeaderControllerExt2.privacyContainer.getMeasuredWidth() + samsungShadeHeaderControllerExt2.batteryIcon.getMeasuredWidth()) + paddingEnd) + samsungShadeHeaderControllerExt2.iconContainer.getPaddingEnd())) - i;
                }
            };
        }
        samsungShadeHeaderControllerExt.lockscreenShadeTransitionController.addCallback(new LockscreenShadeTransitionController.Callback() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$onInit$4
            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public final void setTransitionToFullShadeAmount(float f) {
                boolean z = !(f == 0.0f);
                SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt2 = SamsungShadeHeaderControllerExt.this;
                if (samsungShadeHeaderControllerExt2.prvFragmentToShade != z) {
                    samsungShadeHeaderControllerExt2.prvFragmentToShade = z;
                    runnable.run();
                }
            }
        });
        this.mView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.shade.ShadeHeaderController$onInit$3
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SecPanelSplitHelper.Companion.getClass();
                if (SecPanelSplitHelper.isEnabled) {
                    return false;
                }
                return ShadeHeaderController.this.panelExpanded;
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.privacyIconsController.chipVisibilityListener = this.chipVisibilityListener;
        updateVisibility$7();
        updateTransition$1();
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

    public final void simulateViewDetached$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        onViewDetached();
    }

    public final void updateConstraintsForInsets(MotionLayout motionLayout, WindowInsets windowInsets) {
        ConstraintsChanges plus;
        Integer num;
        ShadeWindowGoesAround.INSTANCE.getClass();
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = (StatusBarContentInsetsProvider) this.statusBarContentInsetsProviderStore.forDisplay(ShadeWindowGoesAround.FLAG.isTrue() ? ((Number) ((ShadeDisplaysRepositoryImpl) ((ShadeDisplaysRepository) this.shadeDisplaysRepositoryLazy.get())).displayId.getValue()).intValue() : this.context.getDisplayId());
        if (statusBarContentInsetsProvider == null) {
            return;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        this.cutout = displayCutout;
        StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl = (StatusBarContentInsetsProviderImpl) statusBarContentInsetsProvider;
        Insets statusBarContentInsetsForCurrentRotation = statusBarContentInsetsProviderImpl.getStatusBarContentInsetsForCurrentRotation();
        final int i = statusBarContentInsetsForCurrentRotation.left;
        int i2 = statusBarContentInsetsForCurrentRotation.right;
        boolean currentRotationHasCornerCutout = statusBarContentInsetsProviderImpl.currentRotationHasCornerCutout();
        final int i3 = motionLayout.isLayoutRtl() ? i2 : i;
        MotionLayout motionLayout2 = this.header;
        final int paddingStart = motionLayout2.getPaddingStart();
        if (!motionLayout.isLayoutRtl()) {
            i = i2;
        }
        final int paddingEnd = motionLayout2.getPaddingEnd();
        CombinedShadeHeadersConstraintManagerImpl combinedShadeHeadersConstraintManagerImpl = (CombinedShadeHeadersConstraintManagerImpl) this.combinedShadeHeadersConstraintManager;
        combinedShadeHeadersConstraintManagerImpl.getClass();
        Function1 function1 = new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ConstraintSet constraintSet = (ConstraintSet) obj;
                CombinedShadeHeadersConstraintManagerImpl combinedShadeHeadersConstraintManagerImpl2 = CombinedShadeHeadersConstraintManagerImpl.INSTANCE;
                constraintSet.setGuidelineBegin(R.id.begin_guide, Math.max(i3 - paddingStart, 0));
                constraintSet.setGuidelineEnd(R.id.end_guide, Math.max(i - paddingEnd, 0));
                return Unit.INSTANCE;
            }
        };
        ConstraintsChanges constraintsChanges = new ConstraintsChanges(function1, function1, function1);
        if (displayCutout != null) {
            Rect boundingRectTop = displayCutout.getBoundingRectTop();
            if (boundingRectTop.isEmpty() || currentRotationHasCornerCutout) {
                combinedShadeHeadersConstraintManagerImpl.getClass();
                plus = constraintsChanges.plus(new ConstraintsChanges(new CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda0(), null, null, 6, null));
            } else {
                boolean isLayoutRtl = motionLayout.isLayoutRtl();
                final int width = (((motionLayout.getWidth() - motionLayout.getPaddingLeft()) - motionLayout.getPaddingRight()) - boundingRectTop.width()) / 2;
                combinedShadeHeadersConstraintManagerImpl.getClass();
                final int i4 = R.id.center_right;
                final int i5 = !isLayoutRtl ? R.id.center_left : R.id.center_right;
                if (isLayoutRtl) {
                    i4 = R.id.center_left;
                }
                final int i6 = 0;
                Function1 function12 = new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        int i7 = i4;
                        int i8 = width;
                        int i9 = i5;
                        ConstraintSet constraintSet = (ConstraintSet) obj;
                        switch (i6) {
                            case 0:
                                CombinedShadeHeadersConstraintManagerImpl combinedShadeHeadersConstraintManagerImpl2 = CombinedShadeHeadersConstraintManagerImpl.INSTANCE;
                                constraintSet.setGuidelineBegin(i9, i8);
                                constraintSet.setGuidelineEnd(i7, i8);
                                constraintSet.connect(R.id.date, 7, i9, 6);
                                constraintSet.connect(R.id.shade_header_system_icons, 6, i7, 7);
                                constraintSet.connect(R.id.privacy_container, 6, i7, 7);
                                constraintSet.constrainedWidth(R.id.date, true);
                                constraintSet.constrainedWidth(R.id.shade_header_system_icons, true);
                                break;
                            default:
                                CombinedShadeHeadersConstraintManagerImpl combinedShadeHeadersConstraintManagerImpl3 = CombinedShadeHeadersConstraintManagerImpl.INSTANCE;
                                constraintSet.setGuidelineBegin(i9, i8);
                                constraintSet.setGuidelineEnd(i7, i8);
                                constraintSet.connect(R.id.privacy_container, 6, i7, 7);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
                final int i7 = 1;
                plus = constraintsChanges.plus(new ConstraintsChanges(function12, new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        int i72 = i4;
                        int i8 = width;
                        int i9 = i5;
                        ConstraintSet constraintSet = (ConstraintSet) obj;
                        switch (i7) {
                            case 0:
                                CombinedShadeHeadersConstraintManagerImpl combinedShadeHeadersConstraintManagerImpl2 = CombinedShadeHeadersConstraintManagerImpl.INSTANCE;
                                constraintSet.setGuidelineBegin(i9, i8);
                                constraintSet.setGuidelineEnd(i72, i8);
                                constraintSet.connect(R.id.date, 7, i9, 6);
                                constraintSet.connect(R.id.shade_header_system_icons, 6, i72, 7);
                                constraintSet.connect(R.id.privacy_container, 6, i72, 7);
                                constraintSet.constrainedWidth(R.id.date, true);
                                constraintSet.constrainedWidth(R.id.shade_header_system_icons, true);
                                break;
                            default:
                                CombinedShadeHeadersConstraintManagerImpl combinedShadeHeadersConstraintManagerImpl3 = CombinedShadeHeadersConstraintManagerImpl.INSTANCE;
                                constraintSet.setGuidelineBegin(i9, i8);
                                constraintSet.setGuidelineEnd(i72, i8);
                                constraintSet.connect(R.id.privacy_container, 6, i72, 7);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                }, null, 4, null));
            }
        } else {
            combinedShadeHeadersConstraintManagerImpl.getClass();
            plus = constraintsChanges.plus(new ConstraintsChanges(new CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda0(), null, null, 6, null));
        }
        Function1 function13 = plus.qqsConstraintsChanges;
        if (function13 != null) {
            int i8 = QQS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet = motionLayout.getConstraintSet(i8);
            constraintSet.getClass();
            function13.mo779invoke(constraintSet);
            motionLayout.updateState(i8, constraintSet);
        }
        Function1 function14 = plus.qsConstraintsChanges;
        if (function14 != null) {
            int i9 = QS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet2 = motionLayout.getConstraintSet(i9);
            constraintSet2.getClass();
            function14.mo779invoke(constraintSet2);
            motionLayout.updateState(i9, constraintSet2);
        }
        DisplayCutout displayCutout2 = this.cutout;
        float f = this.qsExpandedFraction;
        QsBatteryModeController qsBatteryModeController = this.qsBatteryModeController;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider2 = (StatusBarContentInsetsProvider) qsBatteryModeController.insetsProviderStore.forDisplay(qsBatteryModeController.context.getDisplayId());
        int i10 = 3;
        if (f > qsBatteryModeController.fadeInStartFraction) {
            num = 3;
        } else if (statusBarContentInsetsProvider2 == null || f >= qsBatteryModeController.fadeOutCompleteFraction) {
            num = null;
        } else {
            if (displayCutout2 != null && !((StatusBarContentInsetsProviderImpl) statusBarContentInsetsProvider2).currentRotationHasCornerCutout() && !displayCutout2.getBoundingRectTop().isEmpty()) {
                i10 = 1;
            }
            num = Integer.valueOf(i10);
        }
        if (num != null) {
            this.batteryIcon.setPercentShowMode(num.intValue());
        }
    }

    public final void updateIgnoredSlots() {
        boolean z = this.largeScreenActive;
        StatusIconContainer statusIconContainer = this.iconContainer;
        if (z || this.qsExpandedFraction >= 0.5d) {
            EmptyList emptyList = this.carrierIconSlots;
            if (emptyList == null) {
                emptyList = null;
            }
            statusIconContainer.getClass();
            EmptyIterator emptyIterator = (EmptyIterator) emptyList.iterator();
            if (emptyIterator.hasNext()) {
                emptyIterator.next();
                throw null;
            }
            return;
        }
        EmptyList emptyList2 = this.carrierIconSlots;
        EmptyList emptyList3 = emptyList2 != null ? emptyList2 : null;
        statusIconContainer.getClass();
        Iterator it = emptyList3.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            z2 |= statusIconContainer.mIgnoredSlots.remove((String) it.next());
        }
        if (z2) {
            statusIconContainer.requestLayout();
        }
    }

    public final void updateTransition$1() {
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
        WindowInsets windowInsets = this.lastInsets;
        if (windowInsets != null) {
            updateConstraintsForInsets(motionLayout, windowInsets);
        }
        motionLayout.jumpToState(motionLayout.getStartState());
        if (this.largeScreenActive) {
            return;
        }
        motionLayout.setScrollY(this.qsScrollY);
    }

    public final void updateVisibility$7() {
        int i;
        if (this.qsDisabled) {
            i = 8;
        } else if ((!this.qsVisible || this.customizing) && !((SamsungShadeHeaderControllerExt) this.samsungExt.get()).prvFragmentToShade) {
            QsAnimatorState qsAnimatorState = QsAnimatorState.INSTANCE;
            i = 4;
        } else {
            i = 0;
        }
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
