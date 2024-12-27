package com.android.systemui.qs.bar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.settings.multisim.MultiSIMController$$ExternalSyntheticLambda6;
import com.android.systemui.settings.multisim.MultiSIMController.AnonymousClass14;
import com.android.systemui.settings.multisim.MultiSIMData;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.systemui.splugins.volume.VolumePanelState;

public final class MultiSIMPreferredSlotBar extends BarItemImpl implements TunerService.Tunable, MultiSIMController.MultiSIMDataChangedCallback {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final MultiSIMController mController;
    public int mCurrentOrientation;
    public final MultiSIMData mData;
    public boolean mExpanded;
    public final AnonymousClass1 mIntentReceiver;
    public boolean mIsMultiSIMBarHideByKnoxRequest;
    public boolean mIsMultiSIMBarShowOnQSPanel;
    private SettingsHelper.OnChangedCallback mSettingsListener;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.BroadcastReceiver, com.android.systemui.qs.bar.MultiSIMPreferredSlotBar$1] */
    public MultiSIMPreferredSlotBar(Context context, BroadcastDispatcher broadcastDispatcher) {
        super(context);
        this.mIsMultiSIMBarShowOnQSPanel = true;
        this.mIsMultiSIMBarHideByKnoxRequest = false;
        this.mExpanded = false;
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.qs.bar.MultiSIMPreferredSlotBar.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                Log.d("MultiSIMPreferredSlotBar", "onReceive() - action = " + action);
                if (EnterpriseDeviceManager.ACTION_KNOX_RESTRICTIONS_CHANGED.equals(action)) {
                    MultiSIMPreferredSlotBar multiSIMPreferredSlotBar = MultiSIMPreferredSlotBar.this;
                    multiSIMPreferredSlotBar.getClass();
                    boolean z = false;
                    Bundle applicationRestrictions = ApplicationRestrictionsManager.getInstance(context2).getApplicationRestrictions("com.samsung.android.app.telephonyui", 0);
                    if (applicationRestrictions == null || applicationRestrictions.isEmpty()) {
                        multiSIMPreferredSlotBar.mIsMultiSIMBarHideByKnoxRequest = false;
                    } else if (applicationRestrictions.containsKey("telephonyui_simcard_manager_data_preference") && applicationRestrictions.getBundle("telephonyui_simcard_manager_data_preference") != null && (applicationRestrictions.getBundle("telephonyui_simcard_manager_data_preference").getBoolean("grayout") || applicationRestrictions.getBundle("telephonyui_simcard_manager_data_preference").getBoolean("hide"))) {
                        multiSIMPreferredSlotBar.mIsMultiSIMBarHideByKnoxRequest = true;
                    } else if (applicationRestrictions.containsKey("telephonyui_simcard_manager_call_preference") && applicationRestrictions.getBundle("telephonyui_simcard_manager_call_preference") != null && (applicationRestrictions.getBundle("telephonyui_simcard_manager_call_preference").getBoolean("grayout") || applicationRestrictions.getBundle("telephonyui_simcard_manager_call_preference").getBoolean("hide"))) {
                        multiSIMPreferredSlotBar.mIsMultiSIMBarHideByKnoxRequest = true;
                    } else {
                        if (applicationRestrictions.containsKey("telephonyui_simcard_manager_text_preference") && applicationRestrictions.getBundle("telephonyui_simcard_manager_text_preference") != null && (applicationRestrictions.getBundle("telephonyui_simcard_manager_text_preference").getBoolean("grayout") || applicationRestrictions.getBundle("telephonyui_simcard_manager_text_preference").getBoolean("hide"))) {
                            z = true;
                        }
                        multiSIMPreferredSlotBar.mIsMultiSIMBarHideByKnoxRequest = z;
                    }
                    ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).setValue(multiSIMPreferredSlotBar.mIsMultiSIMBarHideByKnoxRequest ? 1 : 0, "multi_sim_bar_hide_by_knox_restrictions");
                    multiSIMPreferredSlotBar.updateBarVisibilities();
                }
            }
        };
        this.mIntentReceiver = r2;
        Uri[] uriArr = {Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE)};
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.MultiSIMPreferredSlotBar.2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE))) {
                    Log.d("MultiSIMPreferredSlotBar", "onChanged() - emergency_mode : ");
                    MultiSIMPreferredSlotBar.this.updateBarVisibilities();
                }
            }
        };
        if (QpRune.QUICK_BAR_MULTISIM) {
            this.mContext = context;
            this.mBroadcastDispatcher = broadcastDispatcher;
            MultiSIMController multiSIMController = (MultiSIMController) Dependency.sDependency.getDependencyInner(MultiSIMController.class);
            this.mController = multiSIMController;
            if (multiSIMController.mData == null) {
                multiSIMController.mData = new MultiSIMData();
            }
            MultiSIMData multiSIMData = new MultiSIMData();
            multiSIMData.copyFrom(multiSIMController.mData);
            multiSIMController.reverseSlotIfNeed(multiSIMData);
            this.mData = multiSIMData;
            multiSIMController.registerCallback(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(EnterpriseDeviceManager.ACTION_KNOX_RESTRICTIONS_CHANGED);
            broadcastDispatcher.registerReceiver(intentFilter, r2);
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsListener, uriArr);
            ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).addTunable(this, "multi_sim_bar_show_on_qspanel");
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        if (QpRune.QUICK_BAR_MULTISIM) {
            this.mBroadcastDispatcher.unregisterReceiver(this.mIntentReceiver);
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(this.mSettingsListener);
            this.mSettingsListener = null;
            ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).removeTunable(this);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.multi_sim_bar_height);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.qs_panel_multi_sim_preffered_slot;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        LinearLayout linearLayout;
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_panel_multi_sim_preffered_slot, viewGroup, false);
        this.mBarRootView = inflate;
        if (inflate != null && (linearLayout = (LinearLayout) inflate.findViewById(R.id.slot_button_group)) != null) {
            linearLayout.setBackground(this.mContext.getDrawable(R.drawable.sec_large_button_ripple_background));
            ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
            if (coloredBGHelper != null) {
                coloredBGHelper.addBarBackground(linearLayout, false);
            }
        }
        updateHeightMargins();
        updateBarVisibilities();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final boolean isAvailable() {
        return QpRune.QUICK_BAR_MULTISIM;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        LinearLayout linearLayout;
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_panel_multi_sim_preffered_slot, (ViewGroup) null);
        this.mClonedBarView = inflate;
        if (inflate != null && (linearLayout = (LinearLayout) inflate.findViewById(R.id.slot_button_group)) != null) {
            linearLayout.setBackground(this.mContext.getDrawable(R.drawable.sec_large_button_ripple_background));
            ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
            if (coloredBGHelper != null) {
                coloredBGHelper.addBarBackground(linearLayout, false);
            }
        }
        updateLayout$2(this.mClonedBarView);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        BarController.AnonymousClass3 anonymousClass3;
        int i = this.mCurrentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mCurrentOrientation = i2;
            if (this.mBarRootView == null) {
                return;
            }
            updateHeightMargins();
            BarController.AnonymousClass4 anonymousClass4 = this.mCallback;
            if (anonymousClass4 == null || (anonymousClass3 = BarController.this.mBarListener) == null) {
                return;
            }
            Runnable runnable = BarController.this.mQSLastExpansionInitializer;
            if (runnable != null) {
                runnable.run();
            }
            anonymousClass3.val$animatorRunner.run();
        }
    }

    @Override // com.android.systemui.settings.multisim.MultiSIMController.MultiSIMDataChangedCallback
    public final void onDataChanged(MultiSIMData multiSIMData) {
        MultiSIMData multiSIMData2 = this.mData;
        boolean z = multiSIMData2.isMultiSimReady;
        boolean z2 = multiSIMData.isMultiSimReady;
        if (z == z2 && multiSIMData2.isSecondaryUser == multiSIMData.isSecondaryUser) {
            return;
        }
        multiSIMData2.isMultiSimReady = z2;
        multiSIMData2.isSecondaryUser = multiSIMData.isSecondaryUser;
        updateBarVisibilities();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        MWBixbyController$$ExternalSyntheticOutline0.m("onTuningChanged() : key = ", str, ", newValue = ", str2, "MultiSIMPreferredSlotBar");
        if (str2 != null && str.equals("multi_sim_bar_show_on_qspanel")) {
            boolean z = true;
            try {
                if (Integer.parseInt(str2) == 0) {
                    z = false;
                }
            } catch (NumberFormatException unused) {
            }
            this.mIsMultiSIMBarShowOnQSPanel = z;
            updateBarVisibilities();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setExpanded(boolean z) {
        this.mQsExpanded = z;
        if (this.mExpanded != z) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("setExpanded : ", " mShowing : ", z), this.mShowing, "MultiSIMPreferredSlotBar");
            this.mExpanded = z;
        }
        if (z && this.mShowing) {
            MultiSIMController multiSIMController = this.mController;
            if (multiSIMController.mSimCardManagerService == null) {
                multiSIMController.mSimCardManagerService = SimCardManagerServiceProvider.getService(multiSIMController.mContext);
                Log.d("MultiSIMController", "registerSimCardManagerCallback SimCardManagerService " + multiSIMController.mSimCardManagerService);
            }
            MultiSIMController.AnonymousClass14 anonymousClass14 = SimCardManagerServiceProvider.sSimCardManagerServiceCallback;
            multiSIMController.mSimCardCallback = anonymousClass14;
            if (anonymousClass14 != null) {
                Log.d("MultiSIMController", "registerSimCardManagerCallback : mSimCardCallback is not null ");
                return;
            }
            MultiSIMController.AnonymousClass14 anonymousClass142 = multiSIMController.new AnonymousClass14();
            multiSIMController.mSimCardCallback = anonymousClass142;
            if (multiSIMController.mSimCardManagerService == null) {
                Log.d("MultiSIMController", "registerSimCardManagerCallback : mSimCardManagerService is null ");
                return;
            }
            try {
                SimCardManagerServiceProvider.sSimCardManagerServiceCallback = anonymousClass142;
                return;
            } catch (Exception e) {
                Log.d("MultiSIMController", "Caught exception from registerSimCardManagerCallback", e);
                return;
            }
        }
        MultiSIMController multiSIMController2 = this.mController;
        if (multiSIMController2.mSimCardManagerService != null) {
            try {
                if (multiSIMController2.mSimCardCallback != null && !SimCardManagerServiceProvider.mIsRemainCallbackCall) {
                    SimCardManagerServiceProvider.sSimCardManagerServiceCallback = null;
                }
                multiSIMController2.mSimCardCallback = null;
            } catch (Exception e2) {
                Log.d("MultiSIMController", "Caught exception from unRegisterSimCardManagerCallback", e2);
            }
        }
        if (SimCardManagerServiceProvider.sServiceBindHelper != null) {
            int i = (SimCardManagerServiceProvider.mIsRemainCallbackCall && SimCardManagerServiceProvider.isServiceRunningCheck(SimCardManagerServiceProvider.mContext)) ? VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS : 0;
            SimCardManagerServiceProvider.AnonymousClass1 anonymousClass1 = SimCardManagerServiceProvider.mHandler;
            if (anonymousClass1 != null) {
                Log.d("SimCardManagerServiceProvider", "CloseService : mIsRemainCallbackCall = " + SimCardManagerServiceProvider.mIsRemainCallbackCall + ", delayTime = " + i);
                if (SimCardManagerServiceProvider.sServiceBindHelper.mServiceStatus == 0) {
                    Log.d("SimCardManagerServiceProvider", "CloseService : already disconnected so initial value");
                    SimCardManagerServiceProvider.sSimCardManagerServiceCallback = null;
                    SimCardManagerServiceProvider.sServiceBindHelper = null;
                    SimCardManagerServiceProvider.sInstance = null;
                    SimCardManagerServiceProvider.mIsServiceClose = true;
                    SimCardManagerServiceProvider.mIsRemainCallbackCall = false;
                } else {
                    anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(0), i);
                }
            }
        }
        multiSIMController2.mSimCardManagerService = null;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void showBar(boolean z) {
        super.showBar(z);
        View view = this.mClonedBarView;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
        MultiSIMController multiSIMController = this.mController;
        MultiSIMController.AnonymousClass13 anonymousClass13 = multiSIMController.mUIHandler;
        if (z && !multiSIMController.mUIVisible) {
            multiSIMController.mUIVisible = true;
            anonymousClass13.post(new MultiSIMController$$ExternalSyntheticLambda6(multiSIMController, 0));
        }
        multiSIMController.mUIVisible = z;
        MultiSIMController$$ExternalSyntheticLambda6 multiSIMController$$ExternalSyntheticLambda6 = multiSIMController.mNotifyVisToCallbackRunnable;
        anonymousClass13.removeCallbacks(multiSIMController$$ExternalSyntheticLambda6);
        anonymousClass13.post(multiSIMController$$ExternalSyntheticLambda6);
    }

    public final void updateBarVisibilities() {
        boolean z = QpRune.QUICK_BAR_MULTISIM && this.mController.isMultiSimAvailable() && !this.mIsMultiSIMBarHideByKnoxRequest && this.mIsMultiSIMBarShowOnQSPanel;
        Log.d("MultiSIMPreferredSlotBar", "updateBarVisibilities " + z);
        showBar(z);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        updateLayout$2(this.mBarRootView);
    }

    public final void updateLayout$2(View view) {
        if (view == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LinearLayout.LayoutParams(-1, -1);
        }
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.multi_sim_bar_height);
        layoutParams.width = -1;
        view.setLayoutParams(layoutParams);
    }
}
