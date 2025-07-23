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
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.QSImpl$$ExternalSyntheticLambda2;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder;
import com.android.systemui.settings.multisim.ui.viewmodel.Bar;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MultiSIMPreferredSlotBar extends BarItemImpl implements TunerService.Tunable, Bar {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final MultiSIMController mController;
    public int mCurrentOrientation;
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
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mController = (MultiSIMController) Dependency.sDependency.getDependencyInner(MultiSIMController.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EnterpriseDeviceManager.ACTION_KNOX_RESTRICTIONS_CHANGED);
        broadcastDispatcher.registerReceiver(intentFilter, r2);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsListener, uriArr);
        ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).addTunable(this, "multi_sim_bar_show_on_qspanel");
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        this.mBroadcastDispatcher.unregisterReceiver(this.mIntentReceiver);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(this.mSettingsListener);
        this.mSettingsListener = null;
        ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).removeTunable(this);
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
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_panel_multi_sim_preffered_slot, viewGroup, false);
        this.mBarRootView = inflate;
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.slot_button_group);
        linearLayout.setBackground(this.mContext.getDrawable(R.drawable.sec_large_button_ripple_background));
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper != null) {
            coloredBGHelper.addBarBackground(linearLayout, false);
        }
        MultiSIMPreferredBarViewBinder.bind(this, this.mController.mMultiSIMViewModel);
        updateHeightMargins();
        updateBarVisibilities();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_panel_multi_sim_preffered_slot, (ViewGroup) null);
        this.mClonedBarView = inflate;
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.slot_button_group);
        linearLayout.setBackground(this.mContext.getDrawable(R.drawable.sec_large_button_ripple_background));
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper != null) {
            coloredBGHelper.addBarBackground(linearLayout, false);
        }
        MultiSIMPreferredBarViewBinder.bindClone(this, this.mController.mMultiSIMViewModel);
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
            QSImpl$$ExternalSyntheticLambda2 qSImpl$$ExternalSyntheticLambda2 = BarController.this.mQSLastExpansionInitializer;
            if (qSImpl$$ExternalSyntheticLambda2 != null) {
                qSImpl$$ExternalSyntheticLambda2.run();
            }
            anonymousClass3.val$animatorRunner.run();
        }
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        MediaSessions$H$$ExternalSyntheticOutline0.m("onTuningChanged() : key = ", str, ", newValue = ", str2, "MultiSIMPreferredSlotBar");
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
    public final void removeCloneTileBG() {
        View findViewById = this.mClonedBarView.findViewById(R.id.slot_button_group);
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper == null || findViewById == null) {
            return;
        }
        coloredBGHelper.removeFromBarBackground(findViewById);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setExpanded(boolean z) {
        this.mQsExpanded = z;
        ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("setExpanded : ", " mShowing : ", z), this.mShowing, "MultiSIMPreferredSlotBar");
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void showBar(boolean z) {
        super.showBar(z);
        View view = this.mClonedBarView;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
        ((MultiSIMViewModelImpl) this.mController.mMultiSIMViewModel).isBarShowing.updateState(null, Boolean.valueOf(z));
    }

    public final void updateBarVisibilities() {
        boolean z = ((MultiSIMViewModelImpl) this.mController.mMultiSIMViewModel).isAvailable() && !this.mIsMultiSIMBarHideByKnoxRequest && this.mIsMultiSIMBarShowOnQSPanel;
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
