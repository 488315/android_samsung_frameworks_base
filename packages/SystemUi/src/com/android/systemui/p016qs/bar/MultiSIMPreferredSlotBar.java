package com.android.systemui.p016qs.bar;

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
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.android.systemui.p016qs.bar.BarController;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.settings.multisim.MultiSIMController$$ExternalSyntheticLambda1;
import com.android.systemui.settings.multisim.MultiSIMController.C240513;
import com.android.systemui.settings.multisim.MultiSIMData;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.app.telephonyui.netsettings.p044ui.simcardmanager.service.SimCardManagerServiceProvider;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiSIMPreferredSlotBar extends BarItemImpl implements TunerService.Tunable, MultiSIMController.MultiSIMDataChangedCallback {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final MultiSIMController mController;
    public int mCurrentOrientation;
    public final MultiSIMData mData;
    public boolean mExpanded;
    public final C20921 mIntentReceiver;
    public boolean mIsMultiSIMBarHideByKnoxRequest;
    public boolean mIsMultiSIMBarShowOnQSPanel;
    public C20932 mSettingsListener;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.content.BroadcastReceiver, com.android.systemui.qs.bar.MultiSIMPreferredSlotBar$1] */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.systemui.qs.bar.MultiSIMPreferredSlotBar$2] */
    public MultiSIMPreferredSlotBar(Context context, BroadcastDispatcher broadcastDispatcher) {
        super(context);
        this.mIsMultiSIMBarShowOnQSPanel = true;
        this.mIsMultiSIMBarHideByKnoxRequest = false;
        this.mExpanded = false;
        ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.qs.bar.MultiSIMPreferredSlotBar.1
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
                    ((TunerService) Dependency.get(TunerService.class)).setValue(multiSIMPreferredSlotBar.mIsMultiSIMBarHideByKnoxRequest ? 1 : 0, "multi_sim_bar_hide_by_knox_restrictions");
                    multiSIMPreferredSlotBar.updateBarVisibilities();
                }
            }
        };
        this.mIntentReceiver = r0;
        Uri[] uriArr = {Settings.System.getUriFor("emergency_mode")};
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.MultiSIMPreferredSlotBar.2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && uri.equals(Settings.System.getUriFor("emergency_mode"))) {
                    Log.d("MultiSIMPreferredSlotBar", "onChanged() - emergency_mode : ");
                    MultiSIMPreferredSlotBar.this.updateBarVisibilities();
                }
            }
        };
        if (QpRune.QUICK_BAR_MULTISIM) {
            this.mContext = context;
            this.mBroadcastDispatcher = broadcastDispatcher;
            MultiSIMController multiSIMController = (MultiSIMController) Dependency.get(MultiSIMController.class);
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
            broadcastDispatcher.registerReceiver(intentFilter, r0);
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(this.mSettingsListener, uriArr);
            ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "multi_sim_bar_show_on_qspanel");
        }
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        if (QpRune.QUICK_BAR_MULTISIM) {
            this.mBroadcastDispatcher.unregisterReceiver(this.mIntentReceiver);
            ((SettingsHelper) Dependency.get(SettingsHelper.class)).unregisterCallback(this.mSettingsListener);
            this.mSettingsListener = null;
            ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        }
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final int getBarHeight() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.multi_sim_bar_height);
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.qs_panel_multi_sim_preffered_slot;
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        LinearLayout linearLayout;
        Context context = this.mContext;
        View inflate = LayoutInflater.from(context).inflate(R.layout.qs_panel_multi_sim_preffered_slot, viewGroup, false);
        this.mBarRootView = inflate;
        if (inflate != null && (linearLayout = (LinearLayout) inflate.findViewById(R.id.slot_button_group)) != null) {
            linearLayout.setBackground(context.getDrawable(R.drawable.sec_large_button_ripple_background));
        }
        updateHeightMargins();
        updateBarVisibilities();
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final boolean isAvailable() {
        return QpRune.QUICK_BAR_MULTISIM;
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        int i = this.mCurrentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mCurrentOrientation = i2;
            if (this.mBarRootView == null) {
                return;
            }
            updateHeightMargins();
            BarController.C20874 c20874 = this.mCallback;
            if (c20874 != null) {
                c20874.onBarHeightChanged();
            }
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
        CustomizationProvider$$ExternalSyntheticOutline0.m135m("onTuningChanged() : key = ", str, ", newValue = ", str2, "MultiSIMPreferredSlotBar");
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

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void setExpanded(boolean z) {
        if (this.mExpanded != z) {
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("setExpanded : ", z, " mShowing : "), this.mShowing, "MultiSIMPreferredSlotBar");
            this.mExpanded = z;
        }
        MultiSIMController multiSIMController = this.mController;
        if (!z || !this.mShowing) {
            if (multiSIMController.mSimCardManagerService != null) {
                try {
                    if (multiSIMController.mSimCardCallback != null && !SimCardManagerServiceProvider.mIsRemainCallbackCall) {
                        SimCardManagerServiceProvider.sSimCardManagerServiceCallback = null;
                    }
                    if (SimCardManagerServiceProvider.isServiceRunningCheck(multiSIMController.mContext)) {
                        SimCardManagerServiceProvider.CloseService();
                    }
                } catch (Exception e) {
                    Log.d("MultiSIMController", "Caught exception from unRegisterSimCardManagerCallback", e);
                }
            }
            multiSIMController.mSimCardManagerService = null;
            multiSIMController.mSimCardCallback = null;
            return;
        }
        if (multiSIMController.mSimCardManagerService == null) {
            multiSIMController.mSimCardManagerService = SimCardManagerServiceProvider.getService(multiSIMController.mContext);
            Log.d("MultiSIMController", "registerSimCardManagerCallback SimCardManagerService " + multiSIMController.mSimCardManagerService);
        }
        MultiSIMController.C240513 c240513 = SimCardManagerServiceProvider.sSimCardManagerServiceCallback;
        multiSIMController.mSimCardCallback = c240513;
        if (c240513 != null) {
            Log.d("MultiSIMController", "registerSimCardManagerCallback : mSimCardCallback is not null ");
            return;
        }
        MultiSIMController.C240513 c2405132 = multiSIMController.new C240513();
        multiSIMController.mSimCardCallback = c2405132;
        if (multiSIMController.mSimCardManagerService == null) {
            Log.d("MultiSIMController", "registerSimCardManagerCallback : mSimCardManagerService is null ");
            return;
        }
        try {
            SimCardManagerServiceProvider.sSimCardManagerServiceCallback = c2405132;
        } catch (Exception e2) {
            Log.d("MultiSIMController", "Caught exception from registerSimCardManagerCallback", e2);
        }
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void showBar(boolean z) {
        super.showBar(z);
        MultiSIMController multiSIMController = this.mController;
        MultiSIMController.HandlerC240412 handlerC240412 = multiSIMController.mUIHandler;
        if (z && !multiSIMController.mUIVisible) {
            multiSIMController.mUIVisible = true;
            handlerC240412.post(new MultiSIMController$$ExternalSyntheticLambda1(multiSIMController, 2));
        }
        multiSIMController.mUIVisible = z;
        MultiSIMController$$ExternalSyntheticLambda1 multiSIMController$$ExternalSyntheticLambda1 = multiSIMController.mNotifyVisToCallbackRunnable;
        handlerC240412.removeCallbacks(multiSIMController$$ExternalSyntheticLambda1);
        handlerC240412.post(multiSIMController$$ExternalSyntheticLambda1);
    }

    public final void updateBarVisibilities() {
        boolean z = QpRune.QUICK_BAR_MULTISIM && this.mController.isMultiSimAvailable() && !this.mIsMultiSIMBarHideByKnoxRequest && this.mIsMultiSIMBarShowOnQSPanel;
        Log.d("MultiSIMPreferredSlotBar", "updateBarVisibilities " + z);
        showBar(z);
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        if (this.mBarRootView == null) {
            return;
        }
        Context context = this.mContext;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.multi_sim_bar_height);
        context.getResources().getDimensionPixelSize(R.dimen.bar_side_margin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, dimensionPixelSize);
        layoutParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.bar_top_margin);
        this.mBarRootView.setLayoutParams(layoutParams);
    }
}
