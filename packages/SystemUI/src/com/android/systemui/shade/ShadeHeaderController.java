package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.PermissionChecker;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Trace;
import android.os.UserHandle;
import android.permission.PermissionGroupUsage;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.BasicRune;
import com.android.systemui.DualToneHandler;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.ScRune;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyChipEvent;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
import com.android.systemui.qs.HeaderPrivacyIconsController;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.carrier.ShadeCarrier;
import com.android.systemui.shade.carrier.ShadeCarrierGroup;
import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.data.repository.StatusBarContentInsetsProviderStore;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.KeyguardStatusBarWallpaperHelper;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.StatusIconContainerController;
import com.android.systemui.statusbar.phone.StatusOverlayHoverListenerFactory;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.ui.SamsungPopOverIconManager;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.pipeline.battery.ui.viewmodel.BatteryViewModel;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.NetspeedViewController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.VariableDateViewController$Factory;
import com.android.systemui.util.ViewController;
import dagger.Lazy;
import java.io.IOException;
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
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyIterator;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import org.xmlpull.v1.XmlPullParserException;

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
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) throws Resources.NotFoundException, NumberFormatException {
                WindowInsets windowInsets2 = new WindowInsets(windowInsets);
                if (windowInsets2.equals(this.this$0.lastInsets)) {
                    return windowInsets;
                }
                this.this$0.updateConstraintsForInsets((MotionLayout) view, windowInsets);
                this.this$0.lastInsets = windowInsets2;
                return view.onApplyWindowInsets(windowInsets);
            }
        };
        this.chipVisibilityListener = new ShadeHeaderController$chipVisibilityListener$1(this);
        this.configurationControllerListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.shade.ShadeHeaderController$configurationControllerListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                ((SamsungShadeHeaderControllerExt) this.this$0.samsungExt.get()).updateHeaderPadding();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() throws XmlPullParserException, Resources.NotFoundException, IOException, NumberFormatException {
                ShadeHeaderController.Companion companion = ShadeHeaderController.Companion;
                ShadeHeaderController shadeHeaderController = this.this$0;
                float dimensionPixelSize = shadeHeaderController.getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size);
                Lazy lazy3 = shadeHeaderController.samsungExt;
                SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = (SamsungShadeHeaderControllerExt) lazy3.get();
                float f = dimensionPixelSize * samsungShadeHeaderControllerExt.indicatorScaleGardener.getLatestScaleModel(samsungShadeHeaderControllerExt.context).ratio;
                ShadeCarrierGroup shadeCarrierGroup = shadeHeaderController.mShadeCarrierGroup;
                TextView textView = (TextView) shadeCarrierGroup.findViewById(R.id.no_carrier_text);
                TypedArray typedArrayObtainStyledAttributes = textView.getContext().obtainStyledAttributes(R.style.TextAppearance_QS_Status_SamsungCarriers, new int[]{android.R.attr.textSize});
                textView.setTextSize(0, typedArrayObtainStyledAttributes.getDimensionPixelSize(0, (int) textView.getTextSize()));
                typedArrayObtainStyledAttributes.recycle();
                shadeCarrierGroup.getCarrier1View().mCarrierText.setTextAppearance(R.style.TextAppearance_QS_Status_SamsungCarriers);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier2)).mCarrierText.setTextAppearance(R.style.TextAppearance_QS_Status_SamsungCarriers);
                ((ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier3)).mCarrierText.setTextAppearance(R.style.TextAppearance_QS_Status_SamsungCarriers);
                shadeCarrierGroup.getCarrier1View().mCarrierText.setTextSize(0, f);
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
            public final void onDisplayDeviceTypeChanged() throws XmlPullParserException, Resources.NotFoundException, IOException, NumberFormatException {
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    onDensityOrFontScaleChanged();
                }
            }
        };
        new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.shade.ShadeHeaderController$nextAlarmCallback$1
            @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                this.this$0.nextAlarmIntent = alarmClockInfo != null ? alarmClockInfo.getShowIntent() : null;
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
        int i;
        NetspeedViewController netspeedViewController;
        MotionLayout motionLayout = this.header;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(motionLayout.getContext(), android.R.attr.textColorPrimary, 0);
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(motionLayout.getContext(), android.R.attr.textColorPrimaryInverse, 0);
        boolean z = BasicRune.STATUS_POP_OVER_PANEL_BAR;
        Lazy lazy = this.samsungExt;
        if (z) {
            SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt = (SamsungShadeHeaderControllerExt) lazy.get();
            StatusBarLocation statusBarLocation = StatusBarLocation.QS;
            SamsungPopOverIconManager.Factory factory = samsungShadeHeaderControllerExt.popOverIconManagerFactory;
            factory.getClass();
            this.iconManager = new SamsungPopOverIconManager(this.iconContainer, statusBarLocation, factory.mWifiUiAdapter, factory.mMobileUiAdapter, factory.mMobileUiAdapterKairos, factory.mMobileContextProvider, factory.mKairosNetwork, factory.mAppScope, factory.mBTTetherUiAdapter);
        } else {
            this.iconManager = this.tintedIconManagerFactory.create(this.iconContainer, StatusBarLocation.QS);
        }
        TintedIconManager tintedIconManager = this.iconManager;
        if (tintedIconManager == null) {
            tintedIconManager = null;
        }
        tintedIconManager.setTint(this.context.getColor(R.color.status_bar_clock_color), Utils.getColorAttrDefaultColor(motionLayout.getContext(), android.R.attr.textColorPrimaryInverse, 0));
        if (BasicRune.STATUS_LAYOUT_SYSTEM_ICONS_LOCATION) {
            TintedIconManager tintedIconManager2 = this.iconManager;
            if (tintedIconManager2 == null) {
                tintedIconManager2 = null;
            }
            tintedIconManager2.setBlockList(ArraysKt___ArraysKt.toList(getResources().getStringArray(R.array.config_panel_statusbar_icon_blocklist)));
        }
        BatteryMeterViewController batteryMeterViewController = this.batteryMeterViewController;
        batteryMeterViewController.init();
        batteryMeterViewController.mIgnoreTunerUpdates = true;
        if (batteryMeterViewController.mIsSubscribedForTunerUpdates) {
            batteryMeterViewController.mTunerService.removeTunable(batteryMeterViewController.mTunable);
            i = 0;
            batteryMeterViewController.mIsSubscribedForTunerUpdates = false;
        } else {
            i = 0;
        }
        BatteryMeterView batteryMeterView = this.batteryIcon;
        batteryMeterView.setVisibility(i);
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
                if (((DeviceProvisionedControllerImpl) headerPrivacyIconsController.deviceProvisionedController).deviceProvisioned.get()) {
                    headerPrivacyIconsController.uiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_CLICK);
                    HeaderPrivacyIconsController headerPrivacyIconsController2 = headerPrivacyIconsController;
                    final PrivacyDialogController privacyDialogController = headerPrivacyIconsController2.privacyDialogController;
                    final Context context = ((ShadeDialogContextInteractorImpl) headerPrivacyIconsController2.shadeDialogContextInteractor).getContext();
                    PrivacyDialog privacyDialog = privacyDialogController.dialog;
                    if (privacyDialog != null) {
                        privacyDialog.dismiss();
                    }
                    privacyDialogController.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyDialogController$showDialog$1
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:101:0x0208  */
                        /* JADX WARN: Removed duplicated region for block: B:103:0x021d  */
                        /* JADX WARN: Removed duplicated region for block: B:23:0x0098  */
                        /* JADX WARN: Removed duplicated region for block: B:37:0x00b8  */
                        /* JADX WARN: Removed duplicated region for block: B:79:0x01a5  */
                        /* JADX WARN: Removed duplicated region for block: B:98:0x01fc  */
                        /* JADX WARN: Type inference failed for: r12v4, types: [java.lang.CharSequence, java.lang.Object] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void run() {
                            PrivacyType privacyType;
                            PrivacyType privacyType2;
                            Object next;
                            Iterator it;
                            List list;
                            Context context2;
                            Object privacyElement;
                            String packageName;
                            PermissionGroupUsage permissionGroupUsage;
                            boolean z2;
                            boolean z3;
                            Intent intent;
                            ActivityInfo activityInfo;
                            boolean z4;
                            PrivacyDialogController privacyDialogController2 = privacyDialogController;
                            List indicatorAppOpUsageData = privacyDialogController2.permissionManager.getIndicatorAppOpUsageData(((AppOpsControllerImpl) privacyDialogController2.appOpsController).mMicMuted);
                            List userProfiles = ((UserTrackerImpl) privacyDialogController.userTracker).getUserProfiles();
                            PrivacyLogger privacyLogger = privacyDialogController.privacyLogger;
                            privacyLogger.getClass();
                            LogLevel logLevel = LogLevel.DEBUG;
                            PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(10);
                            LogBuffer logBuffer = privacyLogger.buffer;
                            PrivacyType privacyType3 = null;
                            LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
                            ((LogMessageImpl) logMessageObtain).str1 = indicatorAppOpUsageData.toString();
                            logBuffer.commit(logMessageObtain);
                            PrivacyDialogController privacyDialogController3 = privacyDialogController;
                            Context context3 = context;
                            final ArrayList arrayList = new ArrayList();
                            Iterator it2 = indicatorAppOpUsageData.iterator();
                            while (it2.hasNext()) {
                                PermissionGroupUsage permissionGroupUsage2 = (PermissionGroupUsage) it2.next();
                                String permissionGroupName = permissionGroupUsage2.getPermissionGroupName();
                                privacyDialogController3.getClass();
                                int iHashCode = permissionGroupName.hashCode();
                                if (iHashCode != -1140935117) {
                                    if (iHashCode != 828638019) {
                                        privacyType = (iHashCode == 1581272376 && permissionGroupName.equals("android.permission-group.MICROPHONE")) ? PrivacyType.TYPE_MICROPHONE : privacyType3;
                                    } else if (permissionGroupName.equals("android.permission-group.LOCATION")) {
                                        privacyType = PrivacyType.TYPE_LOCATION;
                                    }
                                } else if (permissionGroupName.equals("android.permission-group.CAMERA")) {
                                    privacyType = PrivacyType.TYPE_CAMERA;
                                }
                                if (privacyType != null) {
                                    PrivacyType privacyType4 = PrivacyType.TYPE_CAMERA;
                                    PrivacyItemController privacyItemController = privacyDialogController3.privacyItemController;
                                    privacyType2 = (((privacyType == privacyType4 || privacyType == PrivacyType.TYPE_MICROPHONE) && privacyItemController.privacyConfig.micCameraAvailable) || (privacyType == PrivacyType.TYPE_LOCATION && privacyItemController.privacyConfig.locationAvailable)) ? privacyType : privacyType3;
                                }
                                Iterator it3 = userProfiles.iterator();
                                while (true) {
                                    if (!it3.hasNext()) {
                                        next = privacyType3;
                                        break;
                                    } else {
                                        next = it3.next();
                                        if (((UserInfo) next).id == UserHandle.getUserId(permissionGroupUsage2.getUid())) {
                                            break;
                                        }
                                    }
                                }
                                UserInfo userInfo = (UserInfo) next;
                                if (userInfo == null && !permissionGroupUsage2.isPhoneCall()) {
                                    it = it2;
                                    list = userProfiles;
                                    context2 = context3;
                                    privacyElement = privacyType3;
                                } else if (privacyType2 != null) {
                                    if (permissionGroupUsage2.isPhoneCall()) {
                                        packageName = "";
                                    } else {
                                        packageName = permissionGroupUsage2.getPackageName();
                                        try {
                                            ?? LoadLabel = privacyDialogController3.packageManager.getApplicationInfoAsUser(packageName, 0, UserHandle.getUserId(permissionGroupUsage2.getUid())).loadLabel(privacyDialogController3.packageManager);
                                            LoadLabel.getClass();
                                            packageName = LoadLabel;
                                        } catch (PackageManager.NameNotFoundException unused) {
                                            MotionLayout$$ExternalSyntheticOutline0.m("Label not found for: ", packageName, "PrivacyDialogController");
                                        }
                                    }
                                    String str = packageName;
                                    int userId = UserHandle.getUserId(permissionGroupUsage2.getUid());
                                    if (ScRune.QUICK_SUPPORT_LOCATION_PRIVACY_CHIP) {
                                        List userProfiles2 = ((UserTrackerImpl) privacyDialogController3.userTracker).getUserProfiles();
                                        if (privacyType2 == PrivacyType.TYPE_LOCATION) {
                                            int[] iArr = privacyDialogController3.LOCATION_OPS;
                                            int length = iArr.length;
                                            int i2 = 0;
                                            z4 = false;
                                            while (true) {
                                                if (i2 >= length) {
                                                    it = it2;
                                                    list = userProfiles;
                                                    permissionGroupUsage = permissionGroupUsage2;
                                                    break;
                                                }
                                                String strOpToPermission = AppOpsManager.opToPermission(iArr[i2]);
                                                it = it2;
                                                UserHandle userHandleForUid = UserHandle.getUserHandleForUid(permissionGroupUsage2.getUid());
                                                list = userProfiles;
                                                int size = userProfiles2.size();
                                                permissionGroupUsage = permissionGroupUsage2;
                                                int i3 = 0;
                                                boolean z5 = false;
                                                while (i3 < size) {
                                                    int i4 = size;
                                                    if (((UserInfo) userProfiles2.get(i3)).getUserHandle().equals(userHandleForUid)) {
                                                        z5 = true;
                                                    }
                                                    i3++;
                                                    size = i4;
                                                }
                                                if (!z5) {
                                                    z4 = true;
                                                    break;
                                                }
                                                int permissionFlags = privacyDialogController3.packageManager.getPermissionFlags(strOpToPermission, permissionGroupUsage.getPackageName(), userHandleForUid);
                                                int i5 = i2;
                                                if (PermissionChecker.checkPermissionForPreflight(context3, strOpToPermission, -1, permissionGroupUsage.getUid(), permissionGroupUsage.getPackageName()) == 0) {
                                                    if ((permissionFlags & 256) == 0) {
                                                        z4 = true;
                                                    }
                                                } else if ((permissionFlags & 512) == 0) {
                                                }
                                                i2 = i5 + 1;
                                                permissionGroupUsage2 = permissionGroupUsage;
                                                it2 = it;
                                                userProfiles = list;
                                            }
                                        } else {
                                            it = it2;
                                            list = userProfiles;
                                            permissionGroupUsage = permissionGroupUsage2;
                                            z4 = false;
                                        }
                                        z2 = z4;
                                    } else {
                                        it = it2;
                                        list = userProfiles;
                                        permissionGroupUsage = permissionGroupUsage2;
                                        z2 = false;
                                    }
                                    String packageName2 = permissionGroupUsage.getPackageName();
                                    CharSequence attributionTag = permissionGroupUsage.getAttributionTag();
                                    CharSequence attributionLabel = permissionGroupUsage.getAttributionLabel();
                                    CharSequence proxyLabel = permissionGroupUsage.getProxyLabel();
                                    long lastAccessTimeMillis = permissionGroupUsage.getLastAccessTimeMillis();
                                    PermissionGroupUsage permissionGroupUsage3 = permissionGroupUsage;
                                    boolean zIsActive = permissionGroupUsage3.isActive();
                                    if (userInfo != null ? userInfo.isManagedProfile() : false) {
                                        z3 = true;
                                        boolean zIsPhoneCall = permissionGroupUsage3.isPhoneCall();
                                        String permissionGroupName2 = permissionGroupUsage3.getPermissionGroupName();
                                        if (z2) {
                                            String packageName3 = permissionGroupUsage3.getPackageName();
                                            String permissionGroupName3 = permissionGroupUsage3.getPermissionGroupName();
                                            CharSequence attributionTag2 = permissionGroupUsage3.getAttributionTag();
                                            boolean z6 = permissionGroupUsage3.getAttributionLabel() != null;
                                            if (attributionTag2 == null || !z6) {
                                                context2 = context3;
                                            } else {
                                                context2 = context3;
                                                if (privacyDialogController3.locationManager.isProviderPackage(null, packageName3, attributionTag2.toString())) {
                                                    intent = new Intent("android.intent.action.MANAGE_PERMISSION_USAGE");
                                                    intent.setPackage(packageName3);
                                                    intent.putExtra("android.intent.extra.PERMISSION_GROUP_NAME", permissionGroupName3.toString());
                                                    intent.putExtra("android.intent.extra.ATTRIBUTION_TAGS", new String[]{attributionTag2.toString()});
                                                    intent.putExtra("android.intent.extra.SHOWING_ATTRIBUTION", true);
                                                    ResolveInfo resolveInfoResolveActivity = privacyDialogController3.packageManager.resolveActivity(intent, PackageManager.ResolveInfoFlags.of(0L));
                                                    if (resolveInfoResolveActivity != null && (activityInfo = resolveInfoResolveActivity.activityInfo) != null && Intrinsics.areEqual(activityInfo.permission, "android.permission.START_VIEW_PERMISSION_USAGE")) {
                                                        intent.setComponent(new ComponentName(packageName3, resolveInfoResolveActivity.activityInfo.name));
                                                    }
                                                }
                                            }
                                            intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
                                            intent.putExtra("android.intent.extra.PACKAGE_NAME", packageName3);
                                            intent.putExtra("android.intent.extra.USER", UserHandle.of(userId));
                                        } else {
                                            intent = new Intent("com.samsung.android.intent.action.LOCATION_RECENT_ACCESS");
                                            intent.putExtra("isOnlyShowSystem", true);
                                            context2 = context3;
                                        }
                                        privacyElement = new PrivacyDialog.PrivacyElement(privacyType2, packageName2, userId, str, attributionTag, attributionLabel, proxyLabel, lastAccessTimeMillis, zIsActive, z3, zIsPhoneCall, permissionGroupName2, intent, z2);
                                    } else {
                                        if (!(userInfo != null ? userInfo.isPrivateProfile() : false)) {
                                            z3 = false;
                                        }
                                        boolean zIsPhoneCall2 = permissionGroupUsage3.isPhoneCall();
                                        String permissionGroupName22 = permissionGroupUsage3.getPermissionGroupName();
                                        if (z2) {
                                        }
                                        privacyElement = new PrivacyDialog.PrivacyElement(privacyType2, packageName2, userId, str, attributionTag, attributionLabel, proxyLabel, lastAccessTimeMillis, zIsActive, z3, zIsPhoneCall2, permissionGroupName22, intent, z2);
                                    }
                                } else {
                                    it = it2;
                                    list = userProfiles;
                                    context2 = context3;
                                    privacyElement = null;
                                }
                                if (privacyElement != null) {
                                    arrayList.add(privacyElement);
                                }
                                it2 = it;
                                userProfiles = list;
                                context3 = context2;
                                privacyType3 = null;
                            }
                            final PrivacyDialogController privacyDialogController4 = privacyDialogController;
                            Executor executor = privacyDialogController4.uiExecutor;
                            final Context context4 = context;
                            executor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyDialogController$showDialog$1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PrivacyDialogController privacyDialogController5 = privacyDialogController4;
                                    List list2 = arrayList;
                                    int i6 = PrivacyDialogController.$r8$clinit;
                                    privacyDialogController5.getClass();
                                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                                    for (Object obj : list2) {
                                        PrivacyType privacyType5 = ((PrivacyDialog.PrivacyElement) obj).type;
                                        Object arrayList2 = linkedHashMap.get(privacyType5);
                                        if (arrayList2 == null) {
                                            arrayList2 = new ArrayList();
                                            linkedHashMap.put(privacyType5, arrayList2);
                                        }
                                        ((List) arrayList2).add(obj);
                                    }
                                    TreeMap treeMap = new TreeMap(linkedHashMap);
                                    ArrayList arrayList3 = new ArrayList();
                                    Iterator it4 = treeMap.entrySet().iterator();
                                    while (true) {
                                        Object next2 = null;
                                        if (!it4.hasNext()) {
                                            break;
                                        }
                                        Map.Entry entry = (Map.Entry) it4.next();
                                        PrivacyType privacyType6 = (PrivacyType) entry.getKey();
                                        Iterable iterableSingletonList = (List) entry.getValue();
                                        iterableSingletonList.getClass();
                                        Iterable iterable = iterableSingletonList;
                                        ArrayList arrayList4 = new ArrayList();
                                        for (Object obj2 : iterable) {
                                            if (((PrivacyDialog.PrivacyElement) obj2).active) {
                                                arrayList4.add(obj2);
                                            }
                                        }
                                        if (!arrayList4.isEmpty()) {
                                            iterableSingletonList = CollectionsKt___CollectionsKt.sortedWith(arrayList4, new Comparator() { // from class: com.android.systemui.privacy.PrivacyDialogController$filterAndSelect$lambda$7$$inlined$sortedByDescending$1
                                                @Override // java.util.Comparator
                                                public final int compare(Object obj3, Object obj4) {
                                                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((PrivacyDialog.PrivacyElement) obj4).lastActiveTimestamp), Long.valueOf(((PrivacyDialog.PrivacyElement) obj3).lastActiveTimestamp));
                                                }
                                            });
                                        } else if (!ScRune.QUICK_SUPPORT_LOCATION_PRIVACY_CHIP || privacyType6 != PrivacyType.TYPE_LOCATION) {
                                            Iterator it5 = iterable.iterator();
                                            if (it5.hasNext()) {
                                                next2 = it5.next();
                                                if (it5.hasNext()) {
                                                    long j = ((PrivacyDialog.PrivacyElement) next2).lastActiveTimestamp;
                                                    do {
                                                        Object next3 = it5.next();
                                                        long j2 = ((PrivacyDialog.PrivacyElement) next3).lastActiveTimestamp;
                                                        if (j < j2) {
                                                            next2 = next3;
                                                            j = j2;
                                                        }
                                                    } while (it5.hasNext());
                                                }
                                            }
                                            PrivacyDialog.PrivacyElement privacyElement2 = (PrivacyDialog.PrivacyElement) next2;
                                            if (privacyElement2 == null || (iterableSingletonList = Collections.singletonList(privacyElement2)) == null) {
                                                iterableSingletonList = EmptyList.INSTANCE;
                                            }
                                        }
                                        iterableSingletonList.getClass();
                                        CollectionsKt__MutableCollectionsKt.addAll(iterableSingletonList, arrayList3);
                                    }
                                    Log.i("PrivacyDialogController", "showDialog  elements " + arrayList3);
                                    if (arrayList3.isEmpty()) {
                                        Log.w("PrivacyDialogController", "Trying to show empty dialog");
                                        return;
                                    }
                                    PrivacyDialogController.DialogProvider dialogProvider = privacyDialogController4.dialogProvider;
                                    Context context5 = context4;
                                    PrivacyDialogController$showDialog$1$1$d$1 privacyDialogController$showDialog$1$1$d$1 = new PrivacyDialogController$showDialog$1$1$d$1(privacyDialogController4);
                                    ((PrivacyDialogControllerKt$defaultDialogProvider$1) dialogProvider).getClass();
                                    PrivacyDialog privacyDialog2 = new PrivacyDialog(context5, arrayList3, privacyDialogController$showDialog$1$1$d$1);
                                    SystemUIDialog.setShowForAllUsers(privacyDialog2);
                                    PrivacyDialogController$onDialogDismissed$1 privacyDialogController$onDialogDismissed$1 = privacyDialogController4.onDialogDismissed;
                                    if (privacyDialog2.dismissed.get()) {
                                        PrivacyDialogController privacyDialogController6 = privacyDialogController$onDialogDismissed$1.this$0;
                                        PrivacyLogger privacyLogger2 = privacyDialogController6.privacyLogger;
                                        privacyLogger2.getClass();
                                        LogLevel logLevel2 = LogLevel.INFO;
                                        PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda02 = new PrivacyLogger$$ExternalSyntheticLambda0(14);
                                        LogBuffer logBuffer2 = privacyLogger2.buffer;
                                        logBuffer2.commit(logBuffer2.obtain("PrivacyLog", logLevel2, privacyLogger$$ExternalSyntheticLambda02, null));
                                        privacyDialogController6.uiEventLogger.log(PrivacyDialogEvent.PRIVACY_DIALOG_DISMISSED);
                                        privacyDialogController6.dialog = null;
                                    } else {
                                        ((ArrayList) privacyDialog2.dismissListeners).add(new WeakReference(privacyDialogController$onDialogDismissed$1));
                                    }
                                    privacyDialogController4.getClass();
                                    SecPanelSplitHelper.Companion.getClass();
                                    privacyDialog2.qsExpanded = SecPanelSplitHelper.isEnabled ? ((SecPanelSplitHelper) privacyDialogController4.panelSplitHepler$delegate.getValue()).isQSState() : ((Boolean) ((ShadeInteractorImpl) privacyDialogController4.shadeInteractor).baseShadeInteractor.isQsExpanded().getValue()).booleanValue();
                                    privacyDialog2.show();
                                    PrivacyLogger privacyLogger3 = privacyDialogController4.privacyLogger;
                                    privacyLogger3.getClass();
                                    LogLevel logLevel3 = LogLevel.INFO;
                                    PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda03 = new PrivacyLogger$$ExternalSyntheticLambda0(1);
                                    LogBuffer logBuffer3 = privacyLogger3.buffer;
                                    LogMessage logMessageObtain2 = logBuffer3.obtain("PrivacyLog", logLevel3, privacyLogger$$ExternalSyntheticLambda03, null);
                                    ((LogMessageImpl) logMessageObtain2).str1 = arrayList3.toString();
                                    logBuffer3.commit(logMessageObtain2);
                                    privacyDialogController4.dialog = privacyDialog2;
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
        headerPrivacyIconsController.notifyPrivacyItemsChanged(ongoingPrivacyChip.privacyList);
        PrivacyConfig privacyConfig = headerPrivacyIconsController.privacyItemController.privacyConfig;
        headerPrivacyIconsController.micCameraIndicatorsEnabled = privacyConfig.micCameraAvailable;
        headerPrivacyIconsController.locationIndicatorsEnabled = privacyConfig.locationAvailable;
        headerPrivacyIconsController.updatePrivacyIconSlots();
        final SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt2 = (SamsungShadeHeaderControllerExt) lazy.get();
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.shade.ShadeHeaderController.onInit.2
            @Override // java.lang.Runnable
            public final void run() {
                ShadeHeaderController shadeHeaderController = ShadeHeaderController.this;
                Companion companion = ShadeHeaderController.Companion;
                shadeHeaderController.updateVisibility$7();
            }
        };
        TintedIconManager tintedIconManager3 = this.iconManager;
        TintedIconManager tintedIconManager4 = tintedIconManager3 == null ? null : tintedIconManager3;
        samsungShadeHeaderControllerExt2.printLog$1("onInit()");
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED && (netspeedViewController = samsungShadeHeaderControllerExt2.netspeedViewController) != null) {
            netspeedViewController.init();
        }
        StatusIconContainerController statusIconContainerController = samsungShadeHeaderControllerExt2.statusIconContainerController;
        statusIconContainerController.init();
        samsungShadeHeaderControllerExt2.updateColorModel(samsungShadeHeaderControllerExt2.model.colorModelNormalPanel, samsungShadeHeaderControllerExt2.emptyTintRect, 0.0f, new DualToneHandler(new ContextThemeWrapper(samsungShadeHeaderControllerExt2.context, R.style.Theme_SystemUI_QuickSettings_Header)).getSingleColor());
        if (z) {
            samsungShadeHeaderControllerExt2.tintedIconManager = tintedIconManager4;
            samsungShadeHeaderControllerExt2.darkIconDispatcher.addDarkReceiver(samsungShadeHeaderControllerExt2);
            ((KeyguardStateControllerImpl) samsungShadeHeaderControllerExt2.keyguardStateController).addCallback(samsungShadeHeaderControllerExt2.keyguardStateControllerCallback);
            KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = samsungShadeHeaderControllerExt2.keyguardStatusBarWallpaperHelper;
            keyguardStatusBarWallpaperHelper.wakefulnessLifecycle.addObserver(keyguardStatusBarWallpaperHelper);
            keyguardStatusBarWallpaperHelper.wallpaperEventNotifier.registerCallback(false, keyguardStatusBarWallpaperHelper, 17L);
            keyguardStatusBarWallpaperHelper.listener = samsungShadeHeaderControllerExt2.keyguardStatusBarWallpaperListener;
            ((ConfigurationControllerImpl) samsungShadeHeaderControllerExt2.configurationController).addCallback(samsungShadeHeaderControllerExt2.configurationControllerListener);
            samsungShadeHeaderControllerExt2.indicatorGardenPresenter.addCallback((IndicatorGardenPresenter.GardenListener) samsungShadeHeaderControllerExt2.indicatorGardenPresenterListener);
            if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
                samsungShadeHeaderControllerExt2.privacyItemController.addCallback(samsungShadeHeaderControllerExt2.privacyItemControllerCallback);
                ((BatteryControllerImpl) samsungShadeHeaderControllerExt2.batteryController).addCallback(samsungShadeHeaderControllerExt2.batteryStateChangeCallback);
                statusIconContainerController.view.mSidelingCutoutContainerInfo = samsungShadeHeaderControllerExt2.sidelingCutoutContainerInfo;
            }
        }
        samsungShadeHeaderControllerExt2.lockscreenShadeTransitionController.addCallback(new LockscreenShadeTransitionController.Callback() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$onInit$1
            @Override // com.android.systemui.statusbar.LockscreenShadeTransitionController.Callback
            public final void setTransitionToFullShadeAmount(float f) {
                boolean z2 = !(f == 0.0f);
                SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt3 = samsungShadeHeaderControllerExt2;
                if (samsungShadeHeaderControllerExt3.prvFragmentToShade != z2) {
                    samsungShadeHeaderControllerExt3.prvFragmentToShade = z2;
                    runnable.run();
                }
            }
        });
        samsungShadeHeaderControllerExt2.mainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SamsungShadeHeaderControllerExt$onInit$3
            @Override // java.lang.Runnable
            public final void run() {
                SamsungShadeHeaderControllerExt samsungShadeHeaderControllerExt3 = samsungShadeHeaderControllerExt2;
                samsungShadeHeaderControllerExt3.setChildFocusableFalse(samsungShadeHeaderControllerExt3.headerView);
            }
        });
        this.mView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.shade.ShadeHeaderController.onInit.3
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

    public final void updateConstraintsForInsets(MotionLayout motionLayout, WindowInsets windowInsets) throws Resources.NotFoundException, NumberFormatException {
        ConstraintsChanges constraintsChangesPlus;
        Integer numValueOf;
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
        boolean zCurrentRotationHasCornerCutout = statusBarContentInsetsProviderImpl.currentRotationHasCornerCutout();
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
            public final Object mo781invoke(Object obj) {
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
            if (boundingRectTop.isEmpty() || zCurrentRotationHasCornerCutout) {
                combinedShadeHeadersConstraintManagerImpl.getClass();
                constraintsChangesPlus = constraintsChanges.plus(new ConstraintsChanges(new CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda0(), null, null, 6, null));
            } else {
                boolean zIsLayoutRtl = motionLayout.isLayoutRtl();
                final int width = (((motionLayout.getWidth() - motionLayout.getPaddingLeft()) - motionLayout.getPaddingRight()) - boundingRectTop.width()) / 2;
                combinedShadeHeadersConstraintManagerImpl.getClass();
                final int i4 = R.id.center_right;
                final int i5 = !zIsLayoutRtl ? R.id.center_left : R.id.center_right;
                if (zIsLayoutRtl) {
                    i4 = R.id.center_left;
                }
                final int i6 = 0;
                Function1 function12 = new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
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
                constraintsChangesPlus = constraintsChanges.plus(new ConstraintsChanges(function12, new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
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
            constraintsChangesPlus = constraintsChanges.plus(new ConstraintsChanges(new CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda0(), null, null, 6, null));
        }
        Function1 function13 = constraintsChangesPlus.qqsConstraintsChanges;
        if (function13 != null) {
            int i8 = QQS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet = motionLayout.getConstraintSet(i8);
            constraintSet.getClass();
            function13.mo781invoke(constraintSet);
            motionLayout.updateState(i8, constraintSet);
        }
        Function1 function14 = constraintsChangesPlus.qsConstraintsChanges;
        if (function14 != null) {
            int i9 = QS_HEADER_CONSTRAINT;
            ConstraintSet constraintSet2 = motionLayout.getConstraintSet(i9);
            constraintSet2.getClass();
            function14.mo781invoke(constraintSet2);
            motionLayout.updateState(i9, constraintSet2);
        }
        DisplayCutout displayCutout2 = this.cutout;
        float f = this.qsExpandedFraction;
        QsBatteryModeController qsBatteryModeController = this.qsBatteryModeController;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider2 = (StatusBarContentInsetsProvider) qsBatteryModeController.insetsProviderStore.forDisplay(qsBatteryModeController.context.getDisplayId());
        int i10 = 3;
        if (f > qsBatteryModeController.fadeInStartFraction) {
            numValueOf = 3;
        } else if (statusBarContentInsetsProvider2 == null || f >= qsBatteryModeController.fadeOutCompleteFraction) {
            numValueOf = null;
        } else {
            if (displayCutout2 != null && !((StatusBarContentInsetsProviderImpl) statusBarContentInsetsProvider2).currentRotationHasCornerCutout() && !displayCutout2.getBoundingRectTop().isEmpty()) {
                i10 = 1;
            }
            numValueOf = Integer.valueOf(i10);
        }
        if (numValueOf != null) {
            this.batteryIcon.setPercentShowMode(numValueOf.intValue());
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
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= statusIconContainer.mIgnoredSlots.remove((String) it.next());
        }
        if (zRemove) {
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
