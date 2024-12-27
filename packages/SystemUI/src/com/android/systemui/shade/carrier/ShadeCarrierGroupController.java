package com.android.systemui.shade.carrier;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextManager;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import com.android.systemui.util.CarrierConfigTracker;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import kotlin.jvm.functions.Function1;

public final class ShadeCarrierGroupController {
    public final ActivityStarter mActivityStarter;
    public final Handler mBgHandler;
    public final Callback mCallback;
    public final View[] mCarrierDividers;
    public final ShadeCarrier[] mCarrierGroups;
    public final CarrierTextManager mCarrierTextManager;
    public final CellSignalState[] mInfos;
    public boolean mIsSingleCarrier;
    public final int[] mLastSignalLevel;
    public final String[] mLastSignalLevelDescription;
    public final LatinNetworkNameProvider mLatinNetworkNameProvider;
    public boolean mListening;
    public final ShadeCarrierGroupControllerLogger mLogger;
    public final H mMainHandler;
    public final NetworkController mNetworkController;
    public final TextView mNoSimTextView;
    public final QuickStarHelper mQuickStarHelper;
    public final AnonymousClass1 mSignalCallback;
    public final SlotIndexResolver mSlotIndexResolver;
    public final StatusBarPipelineFlags mStatusBarPipelineFlags;

    public final class Builder {
        public final ActivityStarter mActivityStarter;
        public final CarrierConfigTracker mCarrierConfigTracker;
        public final CarrierTextManager.Builder mCarrierTextControllerBuilder;
        public final Context mContext;
        public final Handler mHandler;
        public final LatinNetworkNameProvider mLatinNetworkNameProvider;
        public final ShadeCarrierGroupControllerLogger mLogger;
        public final Looper mLooper;
        public final MobileContextProvider mMobileContextProvider;
        public final MobileUiAdapter mMobileUiAdapter;
        public final NetworkController mNetworkController;
        public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;
        public final SlotIndexResolver mSlotIndexResolver;
        public final StatusBarPipelineFlags mStatusBarPipelineFlags;

        public Builder(ActivityStarter activityStarter, Handler handler, Looper looper, ShadeCarrierGroupControllerLogger shadeCarrierGroupControllerLogger, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, SlotIndexResolver slotIndexResolver, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, StatusBarPipelineFlags statusBarPipelineFlags, LatinNetworkNameProvider latinNetworkNameProvider, SlimIndicatorViewMediator slimIndicatorViewMediator) {
            this.mActivityStarter = activityStarter;
            this.mHandler = handler;
            this.mLooper = looper;
            this.mLogger = shadeCarrierGroupControllerLogger;
            this.mNetworkController = networkController;
            this.mCarrierTextControllerBuilder = builder;
            this.mContext = context;
            this.mCarrierConfigTracker = carrierConfigTracker;
            this.mSlotIndexResolver = slotIndexResolver;
            this.mMobileUiAdapter = mobileUiAdapter;
            this.mMobileContextProvider = mobileContextProvider;
            this.mStatusBarPipelineFlags = statusBarPipelineFlags;
            this.mLatinNetworkNameProvider = latinNetworkNameProvider;
            this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        }
    }

    public final class Callback implements CarrierTextManager.CarrierTextCallback {
        public final H mHandler;

        public Callback(H h) {
            this.mHandler = h;
        }

        @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
        public final void updateCarrierInfo(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
            this.mHandler.obtainMessage(0, carrierTextCallbackInfo).sendToTarget();
        }
    }

    public final class H extends Handler {
        public final Consumer mUpdateCarrierInfo;
        public final Runnable mUpdateState;

        public H(Looper looper, Consumer<CarrierTextManager.CarrierTextCallbackInfo> consumer, Runnable runnable) {
            super(looper);
            this.mUpdateCarrierInfo = consumer;
            this.mUpdateState = runnable;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                this.mUpdateCarrierInfo.accept((CarrierTextManager.CarrierTextCallbackInfo) message.obj);
            } else if (i != 1) {
                super.handleMessage(message);
            } else {
                this.mUpdateState.run();
            }
        }
    }

    class IconData {
        public final int slotIndex;

        public IconData(int i, int i2) {
            this.slotIndex = i2;
        }
    }

    public final class QuickStarHelper implements SlimIndicatorViewSubscriber {
        public boolean mIsRegistered;
        public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;

        public QuickStarHelper(SlimIndicatorViewMediator slimIndicatorViewMediator) {
            this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        }

        @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
        public final void updateQuickStarStyle() {
            ShadeCarrierGroupController shadeCarrierGroupController = ShadeCarrierGroupController.this;
            int i = 0;
            ShadeCarrier shadeCarrier = shadeCarrierGroupController.mCarrierGroups[0];
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) shadeCarrierGroupController.mQuickStarHelper.mSlimIndicatorViewMediator;
            if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && slimIndicatorViewMediatorImpl.mCarrierCrew.mIsPanelCarrierDisabled == 1) {
                i = 8;
            }
            shadeCarrier.setVisibility(i);
        }
    }

    public interface SlotIndexResolver {
    }

    public final class SubscriptionManagerSlotIndexResolver implements SlotIndexResolver {
    }

    public /* synthetic */ ShadeCarrierGroupController(ShadeCarrierGroup shadeCarrierGroup, ActivityStarter activityStarter, Handler handler, Looper looper, ShadeCarrierGroupControllerLogger shadeCarrierGroupControllerLogger, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, SlotIndexResolver slotIndexResolver, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, StatusBarPipelineFlags statusBarPipelineFlags, LatinNetworkNameProvider latinNetworkNameProvider, SlimIndicatorViewMediator slimIndicatorViewMediator, int i) {
        this(shadeCarrierGroup, activityStarter, handler, looper, shadeCarrierGroupControllerLogger, networkController, builder, context, carrierConfigTracker, slotIndexResolver, mobileUiAdapter, mobileContextProvider, statusBarPipelineFlags, latinNetworkNameProvider, slimIndicatorViewMediator);
    }

    public final boolean computeIsSingleCarrier() {
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            if (this.mInfos[i2].visible) {
                i++;
            }
        }
        return i == 1;
    }

    public int getShadeCarrierVisibility(int i) {
        return this.mCarrierGroups[i].getVisibility();
    }

    public int getSlotIndex(int i) {
        ((SubscriptionManagerSlotIndexResolver) this.mSlotIndexResolver).getClass();
        return SubscriptionManager.getSlotIndex(i);
    }

    public final void handleUpdateState() {
        H h = this.mMainHandler;
        if (!h.getLooper().isCurrentThread()) {
            h.obtainMessage(1).sendToTarget();
            return;
        }
        boolean computeIsSingleCarrier = computeIsSingleCarrier();
        CellSignalState[] cellSignalStateArr = this.mInfos;
        if (computeIsSingleCarrier) {
            for (int i = 0; i < 3; i++) {
                CellSignalState cellSignalState = cellSignalStateArr[i];
                if (cellSignalState.visible && cellSignalState.mobileSignalIconId == R.drawable.ic_shade_sim_card) {
                    cellSignalStateArr[i] = new CellSignalState(true, R.drawable.ic_blank, "", "", false);
                }
            }
        }
        StatusBarPipelineFlags statusBarPipelineFlags = this.mStatusBarPipelineFlags;
        statusBarPipelineFlags.getClass();
        Flags.INSTANCE.getClass();
        statusBarPipelineFlags.featureFlags.getClass();
        int i2 = 0;
        while (true) {
            if (i2 >= 3) {
                break;
            }
            ShadeCarrier shadeCarrier = this.mCarrierGroups[i2];
            CellSignalState cellSignalState2 = cellSignalStateArr[i2];
            if (!Objects.equals(cellSignalState2, shadeCarrier.mLastSignalState) || computeIsSingleCarrier != shadeCarrier.mIsSingleCarrier) {
                shadeCarrier.mLastSignalState = cellSignalState2;
                shadeCarrier.mIsSingleCarrier = computeIsSingleCarrier;
                boolean z = cellSignalState2.visible;
                shadeCarrier.mMobileGroup.setVisibility(8);
                shadeCarrier.mSpacer.setVisibility(computeIsSingleCarrier ? 0 : 8);
            }
            i2++;
        }
        this.mCarrierDividers[0].setVisibility(8);
        this.mCarrierDividers[1].setVisibility(8);
        if (this.mIsSingleCarrier != computeIsSingleCarrier) {
            this.mIsSingleCarrier = computeIsSingleCarrier;
        }
    }

    public List<IconData> processSubIdList(List<Integer> list) {
        return list.stream().limit(3L).map(new Function() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ShadeCarrierGroupController shadeCarrierGroupController = ShadeCarrierGroupController.this;
                Integer num = (Integer) obj;
                shadeCarrierGroupController.getClass();
                return new ShadeCarrierGroupController.IconData(num.intValue(), shadeCarrierGroupController.getSlotIndex(num.intValue()));
            }
        }).filter(new ShadeCarrierGroupController$$ExternalSyntheticLambda2()).toList();
    }

    public final void setListening(boolean z) {
        if (z == this.mListening) {
            return;
        }
        this.mListening = z;
        this.mBgHandler.post(new ShadeCarrierGroupController$$ExternalSyntheticLambda0(this, 0));
    }

    /* JADX WARN: Type inference failed for: r10v2, types: [com.android.systemui.shade.carrier.ShadeCarrierGroupController$1] */
    private ShadeCarrierGroupController(ShadeCarrierGroup shadeCarrierGroup, ActivityStarter activityStarter, Handler handler, Looper looper, ShadeCarrierGroupControllerLogger shadeCarrierGroupControllerLogger, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, SlotIndexResolver slotIndexResolver, MobileUiAdapter mobileUiAdapter, MobileContextProvider mobileContextProvider, StatusBarPipelineFlags statusBarPipelineFlags, LatinNetworkNameProvider latinNetworkNameProvider, SlimIndicatorViewMediator slimIndicatorViewMediator) {
        this.mInfos = new CellSignalState[3];
        this.mCarrierDividers = new View[]{shadeCarrierGroup.findViewById(R.id.shade_carrier_divider1), shadeCarrierGroup.findViewById(R.id.shade_carrier_divider2)};
        this.mCarrierGroups = new ShadeCarrier[]{(ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier1), (ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier2), (ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier3)};
        this.mLastSignalLevel = new int[3];
        this.mLastSignalLevelDescription = new String[3];
        this.mSignalCallback = new SignalCallback() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController.1
            @Override // com.android.systemui.statusbar.connectivity.SignalCallback
            public final void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
                ShadeCarrierGroupController shadeCarrierGroupController = ShadeCarrierGroupController.this;
                int i = mobileDataIndicators.subId;
                int slotIndex = shadeCarrierGroupController.getSlotIndex(i);
                if (slotIndex >= 3) {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(slotIndex, "setMobileDataIndicators - slot: ", "ShadeCarrierGroup");
                } else if (slotIndex == -1) {
                    ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i, "Invalid SIM slot index for subscription: ", "ShadeCarrierGroup");
                }
            }

            @Override // com.android.systemui.statusbar.connectivity.SignalCallback
            public final void setNoSims(boolean z, boolean z2) {
                ShadeCarrierGroupController shadeCarrierGroupController = ShadeCarrierGroupController.this;
                if (z) {
                    for (int i = 0; i < 3; i++) {
                        CellSignalState[] cellSignalStateArr = shadeCarrierGroupController.mInfos;
                        CellSignalState cellSignalState = cellSignalStateArr[i];
                        if (cellSignalState.visible) {
                            cellSignalState = new CellSignalState(false, cellSignalState.mobileSignalIconId, cellSignalState.contentDescription, cellSignalState.typeContentDescription, cellSignalState.roaming);
                        }
                        cellSignalStateArr[i] = cellSignalState;
                    }
                }
                shadeCarrierGroupController.mMainHandler.obtainMessage(1).sendToTarget();
            }
        };
        this.mActivityStarter = activityStarter;
        this.mBgHandler = handler;
        this.mLogger = shadeCarrierGroupControllerLogger;
        this.mNetworkController = networkController;
        this.mStatusBarPipelineFlags = statusBarPipelineFlags;
        builder.mShowAirplaneMode = false;
        builder.mShowMissingSim = true;
        builder.mDebugLocation = "Shade";
        this.mCarrierTextManager = builder.build();
        this.mSlotIndexResolver = slotIndexResolver;
        this.mNoSimTextView = (TextView) shadeCarrierGroup.findViewById(R.id.no_carrier_text);
        H h = new H(looper, new Consumer() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ShadeCarrierGroupController shadeCarrierGroupController = ShadeCarrierGroupController.this;
                CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo = (CarrierTextManager.CarrierTextCallbackInfo) obj;
                ShadeCarrierGroupController.H h2 = shadeCarrierGroupController.mMainHandler;
                int i = 0;
                if (!h2.getLooper().isCurrentThread()) {
                    h2.obtainMessage(0, carrierTextCallbackInfo).sendToTarget();
                    return;
                }
                ShadeCarrierGroupControllerLogger shadeCarrierGroupControllerLogger2 = shadeCarrierGroupController.mLogger;
                shadeCarrierGroupControllerLogger2.getClass();
                LogLevel logLevel = LogLevel.VERBOSE;
                ShadeCarrierGroupControllerLogger$logHandleUpdateCarrierInfo$2 shadeCarrierGroupControllerLogger$logHandleUpdateCarrierInfo$2 = new Function1() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupControllerLogger$logHandleUpdateCarrierInfo$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        LogMessage logMessage = (LogMessage) obj2;
                        String str1 = logMessage.getStr1();
                        boolean bool1 = logMessage.getBool1();
                        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("handleUpdateCarrierInfo: result=(carrierText=", str1, ", anySimReady=", ", airplaneMode=", bool1), logMessage.getBool2(), ")");
                    }
                };
                LogBuffer logBuffer = shadeCarrierGroupControllerLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("SCGC", logLevel, shadeCarrierGroupControllerLogger$logHandleUpdateCarrierInfo$2, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = String.valueOf(carrierTextCallbackInfo.carrierText);
                logMessageImpl.bool1 = carrierTextCallbackInfo.anySimReady;
                boolean z = carrierTextCallbackInfo.airplaneMode;
                logMessageImpl.bool2 = z;
                logBuffer.commit(obtain);
                shadeCarrierGroupController.mNoSimTextView.setVisibility(8);
                String trim = carrierTextCallbackInfo.carrierText.toString().trim();
                LatinNetworkNameProviderImpl latinNetworkNameProviderImpl = (LatinNetworkNameProviderImpl) shadeCarrierGroupController.mLatinNetworkNameProvider;
                if (latinNetworkNameProviderImpl.isUseLatinNetworkName()) {
                    latinNetworkNameProviderImpl.isAirplaneMode = z;
                    trim = latinNetworkNameProviderImpl.getCombinedNetworkName();
                }
                StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("handleUpdateCarrierInfo [", trim, "] isLatin=");
                m.append(latinNetworkNameProviderImpl.isUseLatinNetworkName());
                Log.d("ShadeCarrierGroup", m.toString());
                ShadeCarrier[] shadeCarrierArr = shadeCarrierGroupController.mCarrierGroups;
                shadeCarrierArr[0].setCarrierText(trim);
                ShadeCarrier shadeCarrier = shadeCarrierGroupController.mCarrierGroups[0];
                SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) shadeCarrierGroupController.mQuickStarHelper.mSlimIndicatorViewMediator;
                if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && slimIndicatorViewMediatorImpl.mCarrierCrew.mIsPanelCarrierDisabled == 1) {
                    i = 8;
                }
                shadeCarrier.setVisibility(i);
                shadeCarrierArr[1].setVisibility(8);
                shadeCarrierGroupController.handleUpdateState();
            }
        }, new ShadeCarrierGroupController$$ExternalSyntheticLambda0(this, 1));
        this.mMainHandler = h;
        this.mCallback = new Callback(h);
        MobileIconsViewModel mobileIconsViewModel = mobileUiAdapter.mobileIconsViewModel;
        statusBarPipelineFlags.getClass();
        Flags.INSTANCE.getClass();
        statusBarPipelineFlags.featureFlags.getClass();
        for (int i = 0; i < 3; i++) {
            this.mInfos[i] = new CellSignalState(false, R.drawable.ic_shade_no_calling_sms, context.getText(R.string.accessibility_no_calling).toString(), "", false);
            this.mLastSignalLevel[i] = TelephonyIcons.MOBILE_CALL_STRENGTH_ICONS[0];
            this.mLastSignalLevelDescription[i] = context.getText(AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH[0]).toString();
        }
        this.mIsSingleCarrier = computeIsSingleCarrier();
        shadeCarrierGroup.setImportantForAccessibility(1);
        shadeCarrierGroup.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController.2
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                ShadeCarrierGroupController.this.setListening(true);
                LatinNetworkNameProviderImpl latinNetworkNameProviderImpl = (LatinNetworkNameProviderImpl) ShadeCarrierGroupController.this.mLatinNetworkNameProvider;
                latinNetworkNameProviderImpl.getClass();
                CarrierInfraMediator.Conditions conditions = CarrierInfraMediator.Conditions.DISPLAY_CBCH50;
                CarrierInfraMediator carrierInfraMediator = latinNetworkNameProviderImpl.carrierInfraMediator;
                String str = null;
                if (carrierInfraMediator.isEnabled(conditions, 0, new Object[0])) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.SERVICE_STATE");
                    intentFilter.addAction("android.telephony.action.SERVICE_PROVIDERS_UPDATED");
                    intentFilter.addAction("android.location.MODE_CHANGED");
                    BroadcastDispatcher.registerReceiver$default(latinNetworkNameProviderImpl.broadcastDispatcher, latinNetworkNameProviderImpl.broadcastReceiver, intentFilter, null, null, 0, null, 60);
                    Intent registerReceiver = latinNetworkNameProviderImpl.context.registerReceiver(null, new IntentFilter("android.intent.action.SERVICE_STATE"));
                    LatinNetworkNameProviderImpl$broadcastReceiver$1 latinNetworkNameProviderImpl$broadcastReceiver$1 = latinNetworkNameProviderImpl.broadcastReceiver;
                    if (registerReceiver != null) {
                        latinNetworkNameProviderImpl$broadcastReceiver$1.onReceive(latinNetworkNameProviderImpl.context, registerReceiver);
                    }
                    Intent registerReceiver2 = latinNetworkNameProviderImpl.context.registerReceiver(null, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED"));
                    if (registerReceiver2 != null) {
                        latinNetworkNameProviderImpl$broadcastReceiver$1.onReceive(latinNetworkNameProviderImpl.context, registerReceiver2);
                    }
                }
                if (carrierInfraMediator.isEnabled(conditions, 0, new Object[0])) {
                    IntentFilter intentFilter2 = new IntentFilter();
                    intentFilter2.addAction("com.sec.android.app.UPDATE_NETWORK_EMERGENCY_ONLY");
                    intentFilter2.addAction("android.telephony.action.AREA_INFO_UPDATED");
                    intentFilter2.addAction("com.sec.android.app.mms.CB_CLEAR");
                    intentFilter2.addAction("android.intent.action.SIM_STATE_CHANGED");
                    BroadcastDispatcher.registerReceiver$default(latinNetworkNameProviderImpl.broadcastDispatcher, latinNetworkNameProviderImpl.broadcastReceiver, intentFilter2, null, null, 0, null, 60);
                    if (latinNetworkNameProviderImpl.cellBroadcastService == null) {
                        List<ResolveInfo> queryIntentServices = latinNetworkNameProviderImpl.context.getPackageManager().queryIntentServices(new Intent("android.telephony.CellBroadcastService"), PackageManager.ResolveInfoFlags.of(1048576L));
                        if (queryIntentServices.size() != 1) {
                            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(queryIntentServices.size(), "getCellBroadcastServicePackageName: found ", "LatinNetworkNameProvider");
                        }
                        Iterator<ResolveInfo> it = queryIntentServices.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                Log.e("LatinNetworkNameProvider", "getCellBroadcastServicePackageName: package name not found");
                                break;
                            }
                            ServiceInfo serviceInfo = it.next().serviceInfo;
                            if (serviceInfo != null) {
                                String str2 = serviceInfo.packageName;
                                if (TextUtils.isEmpty(str2)) {
                                    continue;
                                } else if (latinNetworkNameProviderImpl.context.getPackageManager().checkPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", str2) == 0) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getCellBroadcastServicePackageName: ", str2, "LatinNetworkNameProvider");
                                    str = str2;
                                    break;
                                } else {
                                    Log.e("LatinNetworkNameProvider", "getCellBroadcastServicePackageName: " + str2 + " does not have READ_PRIVILEGED_PHONE_STATE permission");
                                }
                            }
                        }
                        if (str != null) {
                            Intent intent = new Intent("android.telephony.CellBroadcastService");
                            intent.setPackage(str);
                            if (!latinNetworkNameProviderImpl.context.bindService(intent, latinNetworkNameProviderImpl.cellBroadcastServiceConnection, 1)) {
                                Log.d("LatinNetworkNameProvider", "Unable to bind to service");
                            }
                        }
                    }
                }
                latinNetworkNameProviderImpl.dumpManager.registerDumpable(latinNetworkNameProviderImpl);
                QuickStarHelper quickStarHelper = ShadeCarrierGroupController.this.mQuickStarHelper;
                if (quickStarHelper.mIsRegistered) {
                    return;
                }
                ((SlimIndicatorViewMediatorImpl) quickStarHelper.mSlimIndicatorViewMediator).registerSubscriber("ShadeCarrierGroup", quickStarHelper);
                quickStarHelper.mIsRegistered = true;
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ShadeCarrierGroupController.this.setListening(false);
                LatinNetworkNameProviderImpl latinNetworkNameProviderImpl = (LatinNetworkNameProviderImpl) ShadeCarrierGroupController.this.mLatinNetworkNameProvider;
                latinNetworkNameProviderImpl.getClass();
                CarrierInfraMediator.Conditions conditions = CarrierInfraMediator.Conditions.DISPLAY_CBCH50;
                CarrierInfraMediator carrierInfraMediator = latinNetworkNameProviderImpl.carrierInfraMediator;
                if (carrierInfraMediator.isEnabled(conditions, 0, new Object[0])) {
                    latinNetworkNameProviderImpl.unregisterLocationListener();
                }
                if (carrierInfraMediator.isEnabled(conditions, 0, new Object[0])) {
                    latinNetworkNameProviderImpl.context.unbindService(latinNetworkNameProviderImpl.cellBroadcastServiceConnection);
                }
                QuickStarHelper quickStarHelper = ShadeCarrierGroupController.this.mQuickStarHelper;
                if (quickStarHelper.mIsRegistered) {
                    ((SlimIndicatorViewMediatorImpl) quickStarHelper.mSlimIndicatorViewMediator).unregisterSubscriber("ShadeCarrierGroup");
                    quickStarHelper.mIsRegistered = false;
                }
            }
        });
        this.mLatinNetworkNameProvider = latinNetworkNameProvider;
        ((LatinNetworkNameProviderImpl) latinNetworkNameProvider).latinNetworkNameCallback = new ShadeCarrierGroupController$$ExternalSyntheticLambda5(this);
        this.mQuickStarHelper = new QuickStarHelper(slimIndicatorViewMediator);
    }
}
