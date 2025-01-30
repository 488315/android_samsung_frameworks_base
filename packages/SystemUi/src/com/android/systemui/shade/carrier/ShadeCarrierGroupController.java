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
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextManager;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.util.CarrierConfigTracker;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final HandlerC2466H mMainHandler;
    public final NetworkController mNetworkController;
    public final TextView mNoSimTextView;
    public final QuickStarHelper mQuickStarHelper;
    public final C24641 mSignalCallback;
    public final SlotIndexResolver mSlotIndexResolver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final ActivityStarter mActivityStarter;
        public final CarrierConfigTracker mCarrierConfigTracker;
        public final CarrierTextManager.Builder mCarrierTextControllerBuilder;
        public final Context mContext;
        public final Handler mHandler;
        public final LatinNetworkNameProvider mLatinNetworkNameProvider;
        public final Looper mLooper;
        public final NetworkController mNetworkController;
        public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;
        public final SlotIndexResolver mSlotIndexResolver;

        public Builder(ActivityStarter activityStarter, Handler handler, Looper looper, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, SlotIndexResolver slotIndexResolver, LatinNetworkNameProvider latinNetworkNameProvider, SlimIndicatorViewMediator slimIndicatorViewMediator) {
            this.mActivityStarter = activityStarter;
            this.mHandler = handler;
            this.mLooper = looper;
            this.mNetworkController = networkController;
            this.mCarrierTextControllerBuilder = builder;
            this.mContext = context;
            this.mCarrierConfigTracker = carrierConfigTracker;
            this.mSlotIndexResolver = slotIndexResolver;
            this.mLatinNetworkNameProvider = latinNetworkNameProvider;
            this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Callback implements CarrierTextManager.CarrierTextCallback {
        public final HandlerC2466H mHandler;

        public Callback(HandlerC2466H handlerC2466H) {
            this.mHandler = handlerC2466H;
        }

        @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
        public final void updateCarrierInfo(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
            this.mHandler.obtainMessage(0, carrierTextCallbackInfo).sendToTarget();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.shade.carrier.ShadeCarrierGroupController$H */
    public final class HandlerC2466H extends Handler {
        public final Consumer mUpdateCarrierInfo;
        public final Runnable mUpdateState;

        public HandlerC2466H(Looper looper, Consumer<CarrierTextManager.CarrierTextCallbackInfo> consumer, Runnable runnable) {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class QuickStarHelper implements SlimIndicatorViewSubscriber {
        public boolean mIsRegistered;
        public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;

        public QuickStarHelper(SlimIndicatorViewMediator slimIndicatorViewMediator) {
            this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        }

        /* JADX WARN: Code restructure failed: missing block: B:6:0x0019, code lost:
        
            if ((r0.mCarrierCrew.mIsPanelCarrierDisabled == 1) != false) goto L11;
         */
        @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateQuickStarStyle() {
            boolean z;
            ShadeCarrierGroupController shadeCarrierGroupController = ShadeCarrierGroupController.this;
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) shadeCarrierGroupController.mQuickStarHelper.mSlimIndicatorViewMediator;
            if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected) {
                z = true;
            }
            z = false;
            ShadeCarrier[] shadeCarrierArr = shadeCarrierGroupController.mCarrierGroups;
            if (z) {
                shadeCarrierArr[0].setVisibility(8);
            } else {
                shadeCarrierArr[0].setVisibility(0);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SlotIndexResolver {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SubscriptionManagerSlotIndexResolver implements SlotIndexResolver {
    }

    public /* synthetic */ ShadeCarrierGroupController(ShadeCarrierGroup shadeCarrierGroup, ActivityStarter activityStarter, Handler handler, Looper looper, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, SlotIndexResolver slotIndexResolver, LatinNetworkNameProvider latinNetworkNameProvider, SlimIndicatorViewMediator slimIndicatorViewMediator, int i) {
        this(shadeCarrierGroup, activityStarter, handler, looper, networkController, builder, context, carrierConfigTracker, slotIndexResolver, latinNetworkNameProvider, slimIndicatorViewMediator);
    }

    public int getShadeCarrierVisibility(int i) {
        return this.mCarrierGroups[i].getVisibility();
    }

    public int getSlotIndex(int i) {
        ((SubscriptionManagerSlotIndexResolver) this.mSlotIndexResolver).getClass();
        return SubscriptionManager.getSlotIndex(i);
    }

    public final void handleUpdateState() {
        CellSignalState[] cellSignalStateArr;
        HandlerC2466H handlerC2466H = this.mMainHandler;
        if (!handlerC2466H.getLooper().isCurrentThread()) {
            handlerC2466H.obtainMessage(1).sendToTarget();
            return;
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            cellSignalStateArr = this.mInfos;
            if (i >= 3) {
                break;
            }
            if (cellSignalStateArr[i].visible) {
                i2++;
            }
            i++;
        }
        boolean z = i2 == 1;
        if (z) {
            for (int i3 = 0; i3 < 3; i3++) {
                CellSignalState cellSignalState = cellSignalStateArr[i3];
                if (cellSignalState.visible && cellSignalState.mobileSignalIconId == R.drawable.ic_shade_sim_card) {
                    cellSignalStateArr[i3] = new CellSignalState(true, R.drawable.ic_blank, "", "", false);
                }
            }
        }
        int i4 = 0;
        while (true) {
            if (i4 >= 3) {
                break;
            }
            ShadeCarrier shadeCarrier = this.mCarrierGroups[i4];
            CellSignalState cellSignalState2 = cellSignalStateArr[i4];
            if (!Objects.equals(cellSignalState2, shadeCarrier.mLastSignalState) || z != shadeCarrier.mIsSingleCarrier) {
                shadeCarrier.mLastSignalState = cellSignalState2;
                shadeCarrier.mIsSingleCarrier = z;
                boolean z2 = cellSignalState2.visible;
                shadeCarrier.mMobileGroup.setVisibility(8);
                shadeCarrier.mSpacer.setVisibility(z ? 0 : 8);
            }
            i4++;
        }
        View[] viewArr = this.mCarrierDividers;
        viewArr[0].setVisibility(8);
        viewArr[1].setVisibility(8);
        if (this.mIsSingleCarrier != z) {
            this.mIsSingleCarrier = z;
        }
    }

    public final void setListening(boolean z) {
        if (z == this.mListening) {
            return;
        }
        this.mListening = z;
        this.mBgHandler.post(new ShadeCarrierGroupController$$ExternalSyntheticLambda1(this, 1));
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.shade.carrier.ShadeCarrierGroupController$1] */
    private ShadeCarrierGroupController(ShadeCarrierGroup shadeCarrierGroup, ActivityStarter activityStarter, Handler handler, Looper looper, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, SlotIndexResolver slotIndexResolver, LatinNetworkNameProvider latinNetworkNameProvider, SlimIndicatorViewMediator slimIndicatorViewMediator) {
        this.mInfos = new CellSignalState[3];
        this.mCarrierDividers = new View[]{shadeCarrierGroup.findViewById(R.id.shade_carrier_divider1), shadeCarrierGroup.findViewById(R.id.shade_carrier_divider2)};
        this.mCarrierGroups = new ShadeCarrier[]{(ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier1), (ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier2), (ShadeCarrier) shadeCarrierGroup.findViewById(R.id.carrier3)};
        this.mLastSignalLevel = new int[3];
        this.mLastSignalLevelDescription = new String[3];
        this.mSignalCallback = new SignalCallback() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController.1
            @Override // com.android.systemui.statusbar.connectivity.SignalCallback
            public final void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
                int slotIndex = ShadeCarrierGroupController.this.getSlotIndex(mobileDataIndicators.subId);
                if (slotIndex >= 3) {
                    IconCompat$$ExternalSyntheticOutline0.m30m("setMobileDataIndicators - slot: ", slotIndex, "ShadeCarrierGroup");
                } else if (slotIndex == -1) {
                    Log.e("ShadeCarrierGroup", "Invalid SIM slot index for subscription: " + mobileDataIndicators.subId);
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
        this.mNetworkController = networkController;
        builder.mShowAirplaneMode = false;
        builder.mShowMissingSim = true;
        builder.mDebugLocation = "Shade";
        this.mCarrierTextManager = builder.build();
        this.mSlotIndexResolver = slotIndexResolver;
        this.mNoSimTextView = (TextView) shadeCarrierGroup.findViewById(R.id.no_carrier_text);
        HandlerC2466H handlerC2466H = new HandlerC2466H(looper, new Consumer() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController$$ExternalSyntheticLambda0
            /* JADX WARN: Removed duplicated region for block: B:17:0x0079  */
            /* JADX WARN: Removed duplicated region for block: B:20:0x007f  */
            @Override // java.util.function.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(Object obj) {
                boolean z;
                ShadeCarrierGroupController shadeCarrierGroupController = ShadeCarrierGroupController.this;
                CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo = (CarrierTextManager.CarrierTextCallbackInfo) obj;
                ShadeCarrierGroupController.HandlerC2466H handlerC2466H2 = shadeCarrierGroupController.mMainHandler;
                if (!handlerC2466H2.getLooper().isCurrentThread()) {
                    handlerC2466H2.obtainMessage(0, carrierTextCallbackInfo).sendToTarget();
                    return;
                }
                shadeCarrierGroupController.mNoSimTextView.setVisibility(8);
                String trim = carrierTextCallbackInfo.carrierText.toString().trim();
                LatinNetworkNameProviderImpl latinNetworkNameProviderImpl = (LatinNetworkNameProviderImpl) shadeCarrierGroupController.mLatinNetworkNameProvider;
                if (latinNetworkNameProviderImpl.isUseLatinNetworkName()) {
                    latinNetworkNameProviderImpl.isAirplaneMode = carrierTextCallbackInfo.airplaneMode;
                    trim = latinNetworkNameProviderImpl.getCombinedNetworkName();
                }
                StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("handleUpdateCarrierInfo [", trim, "] isLatin=");
                m4m.append(latinNetworkNameProviderImpl.isUseLatinNetworkName());
                Log.d("ShadeCarrierGroup", m4m.toString());
                ShadeCarrier[] shadeCarrierArr = shadeCarrierGroupController.mCarrierGroups;
                shadeCarrierArr[0].setCarrierText(trim);
                SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) shadeCarrierGroupController.mQuickStarHelper.mSlimIndicatorViewMediator;
                if (slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected) {
                    if (slimIndicatorViewMediatorImpl.mCarrierCrew.mIsPanelCarrierDisabled == 1) {
                        z = true;
                        if (z) {
                            shadeCarrierArr[0].setVisibility(0);
                        } else {
                            shadeCarrierArr[0].setVisibility(8);
                        }
                        shadeCarrierArr[1].setVisibility(8);
                        shadeCarrierGroupController.handleUpdateState();
                    }
                }
                z = false;
                if (z) {
                }
                shadeCarrierArr[1].setVisibility(8);
                shadeCarrierGroupController.handleUpdateState();
            }
        }, new ShadeCarrierGroupController$$ExternalSyntheticLambda1(this, 0));
        this.mMainHandler = handlerC2466H;
        this.mCallback = new Callback(handlerC2466H);
        for (int i = 0; i < 3; i++) {
            this.mInfos[i] = new CellSignalState(true, R.drawable.ic_shade_no_calling_sms, context.getText(R.string.accessibility_no_calling).toString(), "", false);
            this.mLastSignalLevel[i] = TelephonyIcons.MOBILE_CALL_STRENGTH_ICONS[0];
            this.mLastSignalLevelDescription[i] = context.getText(AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH[0]).toString();
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 3; i3++) {
            if (this.mInfos[i3].visible) {
                i2++;
            }
        }
        this.mIsSingleCarrier = i2 == 1;
        shadeCarrierGroup.setImportantForAccessibility(1);
        shadeCarrierGroup.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController.2
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                ShadeCarrierGroupController.this.setListening(true);
                LatinNetworkNameProviderImpl latinNetworkNameProviderImpl = (LatinNetworkNameProviderImpl) ShadeCarrierGroupController.this.mLatinNetworkNameProvider;
                latinNetworkNameProviderImpl.getClass();
                CarrierInfraMediator.Conditions conditions = CarrierInfraMediator.Conditions.DISPLAY_CBCH50;
                int i4 = 0;
                CarrierInfraMediator carrierInfraMediator = latinNetworkNameProviderImpl.carrierInfraMediator;
                boolean isEnabled = carrierInfraMediator.isEnabled(conditions, 0, new Object[0]);
                String str = null;
                Context context2 = latinNetworkNameProviderImpl.context;
                if (isEnabled) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.SERVICE_STATE");
                    intentFilter.addAction("android.telephony.action.SERVICE_PROVIDERS_UPDATED");
                    intentFilter.addAction("android.location.MODE_CHANGED");
                    BroadcastDispatcher.registerReceiver$default(latinNetworkNameProviderImpl.broadcastDispatcher, latinNetworkNameProviderImpl.broadcastReceiver, intentFilter, null, null, 0, null, 60);
                    Intent registerReceiver = context2.registerReceiver(null, new IntentFilter("android.intent.action.SERVICE_STATE"));
                    LatinNetworkNameProviderImpl$broadcastReceiver$1 latinNetworkNameProviderImpl$broadcastReceiver$1 = latinNetworkNameProviderImpl.broadcastReceiver;
                    if (registerReceiver != null) {
                        latinNetworkNameProviderImpl$broadcastReceiver$1.onReceive(context2, registerReceiver);
                    }
                    Intent registerReceiver2 = context2.registerReceiver(null, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED"));
                    if (registerReceiver2 != null) {
                        latinNetworkNameProviderImpl$broadcastReceiver$1.onReceive(context2, registerReceiver2);
                    }
                    i4 = 0;
                }
                if (carrierInfraMediator.isEnabled(conditions, i4, new Object[i4])) {
                    IntentFilter intentFilter2 = new IntentFilter();
                    intentFilter2.addAction("com.sec.android.app.UPDATE_NETWORK_EMERGENCY_ONLY");
                    intentFilter2.addAction("android.telephony.action.AREA_INFO_UPDATED");
                    intentFilter2.addAction("com.sec.android.app.mms.CB_CLEAR");
                    intentFilter2.addAction("android.intent.action.SIM_STATE_CHANGED");
                    BroadcastDispatcher.registerReceiver$default(latinNetworkNameProviderImpl.broadcastDispatcher, latinNetworkNameProviderImpl.broadcastReceiver, intentFilter2, null, null, 0, null, 60);
                    if (latinNetworkNameProviderImpl.cellBroadcastService == null) {
                        List<ResolveInfo> queryIntentServices = context2.getPackageManager().queryIntentServices(new Intent("android.telephony.CellBroadcastService"), PackageManager.ResolveInfoFlags.of(1048576L));
                        if (queryIntentServices.size() != 1) {
                            NestedScrollView$$ExternalSyntheticOutline0.m34m("getCellBroadcastServicePackageName: found ", queryIntentServices.size(), "LatinNetworkNameProvider");
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
                                } else if (context2.getPackageManager().checkPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", str2) == 0) {
                                    AbstractC0000x2c234b15.m3m("getCellBroadcastServicePackageName: ", str2, "LatinNetworkNameProvider");
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
                            if (!context2.bindService(intent, latinNetworkNameProviderImpl.cellBroadcastServiceConnection, 1)) {
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
        ((LatinNetworkNameProviderImpl) latinNetworkNameProvider).latinNetworkNameCallback = new ShadeCarrierGroupController$$ExternalSyntheticLambda2(this);
        this.mQuickStarHelper = new QuickStarHelper(slimIndicatorViewMediator);
    }
}
