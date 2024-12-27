package com.android.systemui.statusbar.phone.logo;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

public final class CarrierHomeLogoViewController extends ViewController implements Dumpable, ConfigurationController.ConfigurationListener {
    public final CarrierConfigTracker carrierConfigTracker;
    public final CarrierInfraMediator carrierInfraMediator;
    public final CarrierLogoVisibilityManager carrierLogoVisibilityManager;
    public final ConfigurationController configurationController;
    public final DarkIconDispatcher darkIconDispatcher;
    public final CarrierHomeLogoViewController$defaultDataListener$1 defaultDataListener;
    public final DeviceProvisionedController deviceProvisionedController;
    public final CarrierHomeLogoViewController$deviceProvisionedListener$1 deviceProvisionedListener;
    public final DumpManager dumpManager;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final CarrierLogoView logoView;
    public final CarrierHomeLogoViewController$quickStarListener$1 quickStarListener;
    public final CarrierHomeLogoViewController$special$$inlined$map$1 serviceStateChanged;
    private final SettingsHelper settingsHelper;
    private final SettingsHelper.OnChangedCallback settingsListener;
    public final SimCardInfoUtil simCardInfoUtil;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 simStateChanged;
    public final SlimIndicatorViewMediator slimIndicatorViewMediator;
    public final int slotId;
    public final Flow spnUpdated;
    public final SubscriptionManager subscriptionManager;
    public boolean userSetup;
    public final LinkedList visibilityHistory;

    public final class Factory {
        public final BroadcastDispatcher broadcastDispatcher;
        public final CarrierConfigTracker carrierConfigTracker;
        public final CarrierInfraMediator carrierInfraMediator;
        public final ConfigurationController configurationController;
        public final DarkIconDispatcher darkIconDispatcher;
        public final DeviceProvisionedController deviceProvisionedController;
        public final DumpManager dumpManager;
        public final IndicatorScaleGardener indicatorScaleGardener;
        private final SettingsHelper settingsHelper;
        public final SimCardInfoUtil simCardInfoUtil;
        public final SlimIndicatorViewMediator slimIndicatorViewMediator;
        public final SubscriptionManager subscriptionManager;
        public final TelephonyManager telephonyManager;

        public Factory(CarrierInfraMediator carrierInfraMediator, DarkIconDispatcher darkIconDispatcher, DumpManager dumpManager, SettingsHelper settingsHelper, SlimIndicatorViewMediator slimIndicatorViewMediator, SimCardInfoUtil simCardInfoUtil, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener, BroadcastDispatcher broadcastDispatcher, SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, CarrierConfigTracker carrierConfigTracker, DeviceProvisionedController deviceProvisionedController) {
            this.carrierInfraMediator = carrierInfraMediator;
            this.darkIconDispatcher = darkIconDispatcher;
            this.dumpManager = dumpManager;
            this.settingsHelper = settingsHelper;
            this.slimIndicatorViewMediator = slimIndicatorViewMediator;
            this.simCardInfoUtil = simCardInfoUtil;
            this.configurationController = configurationController;
            this.indicatorScaleGardener = indicatorScaleGardener;
            this.broadcastDispatcher = broadcastDispatcher;
            this.subscriptionManager = subscriptionManager;
            this.telephonyManager = telephonyManager;
            this.carrierConfigTracker = carrierConfigTracker;
            this.deviceProvisionedController = deviceProvisionedController;
        }

        public final CarrierHomeLogoViewController create(View view, int i) {
            return new CarrierHomeLogoViewController(view, this.broadcastDispatcher, this.dumpManager, this.settingsHelper, this.configurationController, new CarrierLogoVisibilityManager(this.carrierInfraMediator, CarrierInfraMediator.Conditions.CARRIER_LOGO_ON_HOME_SCREEN, this.simCardInfoUtil, this.telephonyManager), i, this.carrierInfraMediator, this.darkIconDispatcher, this.slimIndicatorViewMediator, (CarrierLogoView) view.findViewById(R.id.carrier_logo), this.indicatorScaleGardener, this.subscriptionManager, this.simCardInfoUtil, this.carrierConfigTracker, this.deviceProvisionedController);
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$deviceProvisionedListener$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$quickStarListener$1] */
    /* JADX WARN: Type inference failed for: r2v15, types: [com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$defaultDataListener$1] */
    public CarrierHomeLogoViewController(View view, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, SettingsHelper settingsHelper, ConfigurationController configurationController, CarrierLogoVisibilityManager carrierLogoVisibilityManager, int i, CarrierInfraMediator carrierInfraMediator, DarkIconDispatcher darkIconDispatcher, SlimIndicatorViewMediator slimIndicatorViewMediator, CarrierLogoView carrierLogoView, IndicatorScaleGardener indicatorScaleGardener, SubscriptionManager subscriptionManager, SimCardInfoUtil simCardInfoUtil, CarrierConfigTracker carrierConfigTracker, DeviceProvisionedController deviceProvisionedController) {
        super(view);
        this.dumpManager = dumpManager;
        this.settingsHelper = settingsHelper;
        this.configurationController = configurationController;
        this.carrierLogoVisibilityManager = carrierLogoVisibilityManager;
        this.slotId = i;
        this.carrierInfraMediator = carrierInfraMediator;
        this.darkIconDispatcher = darkIconDispatcher;
        this.slimIndicatorViewMediator = slimIndicatorViewMediator;
        this.logoView = carrierLogoView;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.subscriptionManager = subscriptionManager;
        this.simCardInfoUtil = simCardInfoUtil;
        this.carrierConfigTracker = carrierConfigTracker;
        this.deviceProvisionedController = deviceProvisionedController;
        this.visibilityHistory = new LinkedList();
        this.settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$settingsListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                SettingsHelper settingsHelper2;
                SettingsHelper settingsHelper3;
                CarrierHomeLogoViewController carrierHomeLogoViewController = CarrierHomeLogoViewController.this;
                CarrierLogoVisibilityManager carrierLogoVisibilityManager2 = carrierHomeLogoViewController.carrierLogoVisibilityManager;
                settingsHelper2 = carrierHomeLogoViewController.settingsHelper;
                carrierLogoVisibilityManager2.settingEnabled = settingsHelper2.isCarrierLogoEnabled();
                carrierHomeLogoViewController.updateCarrierLogoVisibility();
                settingsHelper3 = carrierHomeLogoViewController.settingsHelper;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("Carrier logo setting changed=", "CarrierHomeLogoViewController", settingsHelper3.isCarrierLogoEnabled());
            }
        };
        this.defaultDataListener = new CarrierConfigTracker.DefaultDataSubscriptionChangedListener() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$defaultDataListener$1
            @Override // com.android.systemui.util.CarrierConfigTracker.DefaultDataSubscriptionChangedListener
            public final void onDefaultSubscriptionChanged(int i2) {
                CarrierHomeLogoViewController carrierHomeLogoViewController = CarrierHomeLogoViewController.this;
                CarrierLogoVisibilityManager carrierLogoVisibilityManager2 = carrierHomeLogoViewController.carrierLogoVisibilityManager;
                int slotIndex = SubscriptionManager.getSlotIndex(i2);
                carrierLogoVisibilityManager2.defaultSubscriptionSlotId = slotIndex;
                CarrierInfraMediator carrierInfraMediator2 = carrierLogoVisibilityManager2.carrierInfraMediator;
                CarrierInfraMediator.Conditions conditions = carrierLogoVisibilityManager2.featureName;
                boolean isEnabled = carrierInfraMediator2.isEnabled(conditions, slotIndex, new Object[0]);
                carrierLogoVisibilityManager2.featureEnabled = isEnabled;
                Log.d("CarrierLogoVisibilityManager", "Default data subscription is changed to slot" + carrierLogoVisibilityManager2.defaultSubscriptionSlotId + " " + conditions + "=" + isEnabled);
                carrierHomeLogoViewController.updateCarrierLogoVisibility();
            }
        };
        this.userSetup = ((DeviceProvisionedControllerImpl) deviceProvisionedController).isCurrentUserSetup();
        this.quickStarListener = new SlimIndicatorViewSubscriber() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$quickStarListener$1
            @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
            public final void updateQuickStarStyle() {
                CarrierHomeLogoViewController carrierHomeLogoViewController = CarrierHomeLogoViewController.this;
                CarrierLogoVisibilityManager carrierLogoVisibilityManager2 = carrierHomeLogoViewController.carrierLogoVisibilityManager;
                SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) carrierHomeLogoViewController.slimIndicatorViewMediator;
                carrierLogoVisibilityManager2.quickStarEnabled = !(slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && slimIndicatorViewMediatorImpl.mCarrierCrew.mIsHomeCarrierDisabled == 1);
                carrierHomeLogoViewController.updateCarrierLogoVisibility();
            }
        };
        this.simStateChanged = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CarrierHomeLogoViewController$simStateChanged$1(this, null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SIM_STATE_CHANGED"), null, 0, null, 14));
        this.serviceStateChanged = new CarrierHomeLogoViewController$special$$inlined$map$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.SERVICE_STATE"), null, new Function2() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$serviceStateChanged$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return (Intent) obj;
            }
        }, 14), this);
        this.spnUpdated = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED"), null, 0, null, 14);
        this.deviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController$deviceProvisionedListener$1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSetupChanged() {
                CarrierHomeLogoViewController carrierHomeLogoViewController = CarrierHomeLogoViewController.this;
                carrierHomeLogoViewController.userSetup = ((DeviceProvisionedControllerImpl) carrierHomeLogoViewController.deviceProvisionedController).isCurrentUserSetup();
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0065, code lost:
    
        if (r1 == com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.SKT) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0067, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0077, code lost:
    
        if (r1 == com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.KT) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0084, code lost:
    
        if (r1 == com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.LGT) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0102, code lost:
    
        if (r1 == false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$updateSimTypes(com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController r8) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController.access$updateSimTypes(com.android.systemui.statusbar.phone.logo.CarrierHomeLogoViewController):void");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(" userSetup=" + this.userSetup);
        printWriter.println();
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = this.carrierLogoVisibilityManager;
        carrierLogoVisibilityManager.getClass();
        printWriter.println("Last visibility state:");
        printWriter.println(carrierLogoVisibilityManager.toString());
        Iterator it = this.visibilityHistory.iterator();
        while (it.hasNext()) {
            printWriter.println((String) it.next());
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        float f = this.indicatorScaleGardener.getLatestScaleModel(getContext()).ratio;
        CarrierLogoView carrierLogoView = this.logoView;
        if (carrierLogoView != null) {
            ViewGroup.LayoutParams layoutParams = carrierLogoView.getLayoutParams();
            layoutParams.width = MathKt__MathJVMKt.roundToInt(carrierLogoView.getDrawable().getIntrinsicWidth() * f);
            layoutParams.height = MathKt__MathJVMKt.roundToInt(carrierLogoView.getDrawable().getIntrinsicHeight() * f);
            carrierLogoView.setLayoutParams(layoutParams);
        }
        this.mView.setPaddingRelative(0, 0, MathKt__MathJVMKt.roundToInt(getResources().getDimensionPixelSize(R.dimen.status_carrier_logo_margin_end) * f), 0);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDisplayDeviceTypeChanged() {
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            onDensityOrFontScaleChanged();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        String str;
        this.carrierConfigTracker.addDefaultDataSubscriptionChangedListener(this.defaultDataListener);
        boolean isCarrierLogoEnabled = this.settingsHelper.isCarrierLogoEnabled();
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = this.carrierLogoVisibilityManager;
        carrierLogoVisibilityManager.settingEnabled = isCarrierLogoEnabled;
        boolean z = false;
        this.settingsHelper.registerCallback(this.settingsListener, Settings.System.getUriFor(SettingsHelper.INDEX_INDICATOR_SHOW_NETWORK_INFORMATION));
        CarrierLogoView carrierLogoView = this.logoView;
        if (carrierLogoView != null) {
            CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
            CarrierInfraMediator carrierInfraMediator = this.carrierInfraMediator;
            int i = this.slotId;
            Object obj = carrierInfraMediator.get(values, i, new Object[0]);
            carrierLogoView.setImageResource(Intrinsics.areEqual(obj, "SKT") ? R.drawable.stat_notify_operator_logo_skt : Intrinsics.areEqual(obj, "KTT") ? R.drawable.stat_notify_operator_logo_kt : Intrinsics.areEqual(obj, "LGT") ? R.drawable.stat_notify_operator_logo_lgu : Intrinsics.areEqual(obj, "ORANGE") ? R.drawable.stat_notify_operator_logo_org : 0);
            String str2 = (String) carrierInfraMediator.get(values, i, new Object[0]);
            switch (str2.hashCode()) {
                case -1955522002:
                    if (str2.equals("ORANGE")) {
                        str = "Orange F";
                        break;
                    }
                    str = "";
                    break;
                case 74763:
                    if (str2.equals("KTT")) {
                        str = getContext().getString(R.string.status_bar_carrier_logo_kt_tts);
                        break;
                    }
                    str = "";
                    break;
                case 75321:
                    if (str2.equals("LGT")) {
                        str = getContext().getString(R.string.status_bar_carrier_logo_lgu_tts);
                        break;
                    }
                    str = "";
                    break;
                case 82172:
                    if (str2.equals("SKT")) {
                        str = getContext().getString(R.string.status_bar_carrier_logo_skt_tts);
                        break;
                    }
                    str = "";
                    break;
                default:
                    str = "";
                    break;
            }
            carrierLogoView.setContentDescription(str);
            this.darkIconDispatcher.addDarkReceiver(carrierLogoView);
        }
        ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).addCallback(this.deviceProvisionedListener);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this);
        SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator;
        if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && slimIndicatorViewMediatorImpl.mCarrierCrew.mIsHomeCarrierDisabled == 1) {
            z = true;
        }
        carrierLogoVisibilityManager.quickStarEnabled = !z;
        slimIndicatorViewMediatorImpl.registerSubscriber("CarrierHomeLogoViewController", this.quickStarListener);
        this.dumpManager.registerNormalDumpable("CarrierHomeLogoViewController", this);
        RepeatWhenAttachedKt.repeatWhenAttached(this.mView, EmptyCoroutineContext.INSTANCE, new CarrierHomeLogoViewController$onViewAttached$1(this, null));
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.carrierConfigTracker.removeDataSubscriptionChangedListener(this.defaultDataListener);
        this.settingsHelper.unregisterCallback(this.settingsListener);
        CarrierLogoView carrierLogoView = this.logoView;
        Intrinsics.checkNotNull(carrierLogoView);
        this.darkIconDispatcher.removeDarkReceiver(carrierLogoView);
        ((DeviceProvisionedControllerImpl) this.deviceProvisionedController).removeCallback(this.deviceProvisionedListener);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
        ((SlimIndicatorViewMediatorImpl) this.slimIndicatorViewMediator).unregisterSubscriber("CarrierHomeLogoViewController");
        this.dumpManager.unregisterDumpable("CarrierHomeLogoViewController");
    }

    public final void updateCarrierLogoVisibility() {
        CarrierLogoVisibilityManager carrierLogoVisibilityManager = this.carrierLogoVisibilityManager;
        if (carrierLogoVisibilityManager.getVisible() != this.mView.getVisibility()) {
            this.mView.setVisibility(carrierLogoVisibilityManager.getVisible());
            if (this.visibilityHistory.size() > 10) {
                this.visibilityHistory.poll();
            }
            this.visibilityHistory.offer(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(Long.valueOf(System.currentTimeMillis())) + " " + carrierLogoVisibilityManager);
            ListPopupWindow$$ExternalSyntheticOutline0.m(carrierLogoVisibilityManager.getVisible(), "Visibility changed=", "CarrierHomeLogoViewController");
        }
    }
}
