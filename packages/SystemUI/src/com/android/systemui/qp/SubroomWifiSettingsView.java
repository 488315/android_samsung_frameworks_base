package com.android.systemui.qp;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qp.SubscreenWifiController.TileReceiver;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

public class SubroomWifiSettingsView extends LinearLayout implements SubscreenQSControllerContract$View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final SubscreenWifiController mSubscreenWifiController;
    public LinearLayout mWifiBackground;
    public ImageView mWifiButton;

    public SubroomWifiSettingsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        SubscreenWifiController subscreenWifiController = SubscreenWifiController.getInstance(context);
        this.mSubscreenWifiController = subscreenWifiController;
        subscreenWifiController.mWifiView = this;
        subscreenWifiController.mWifiViewReceiver = subscreenWifiController.new TileReceiver();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSubscreenWifiController.registerReceiver(true);
        SubscreenWifiController subscreenWifiController = this.mSubscreenWifiController;
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("SWC isEnabled enabled: "), subscreenWifiController.mWifiState, "SubscreenWifiController");
        updateView(subscreenWifiController.mWifiState);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("SubroomWifiSettingsView", "SWSV onDetachedFromWindow");
        this.mSubscreenWifiController.unRegisterReceiver(true);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mWifiButton = (ImageView) findViewById(R.id.wifi_image_view);
        this.mWifiBackground = (LinearLayout) findViewById(R.id.wifi_background);
        this.mWifiButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubroomWifiSettingsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubroomWifiSettingsView subroomWifiSettingsView = SubroomWifiSettingsView.this;
                int i = SubroomWifiSettingsView.$r8$clinit;
                subroomWifiSettingsView.getClass();
                if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isWifiTileBlocked()) {
                    Log.d("SubroomWifiSettingsView", "Subscreen Wifi tile not available by KnoxStateMonitor.");
                } else {
                    SubscreenWifiController subscreenWifiController = subroomWifiSettingsView.mSubscreenWifiController;
                    ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("SWC isEnabled enabled: "), subscreenWifiController.mWifiState, "SubscreenWifiController");
                    boolean z = subscreenWifiController.mWifiState;
                    if (z) {
                        SubscreenWifiController subscreenWifiController2 = subroomWifiSettingsView.mSubscreenWifiController;
                        subscreenWifiController2.getClass();
                        if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isSecure() && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isLockFunctionsEnabled()) {
                            KeyguardManager keyguardManager = (KeyguardManager) SubscreenWifiController.mContext.getSystemService("keyguard");
                            Intent intent = new Intent();
                            intent.setAction("WIFI_STATE_CHANGE");
                            intent.putExtra("wifi_state", subscreenWifiController2.mWifiState);
                            PendingIntent broadcast = PendingIntent.getBroadcast(SubscreenWifiController.mContext, 0, intent, 201326592);
                            Intent intent2 = new Intent();
                            intent2.putExtra("showCoverToast", true);
                            intent2.putExtra("runOnCover", true);
                            intent2.putExtra("afterKeyguardGone", true);
                            intent2.putExtra("ignoreKeyguardState", true);
                            keyguardManager.semSetPendingIntentAfterUnlock(broadcast, intent2);
                            return;
                        }
                    }
                    subroomWifiSettingsView.mSubscreenWifiController.mWifiAdapter.setWifiEnabled(!z);
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_WIFI_COVER);
            }
        });
        this.mWifiButton.setOnLongClickListener(new SubroomWifiSettingsView$$ExternalSyntheticLambda1());
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$View
    public final void updateView(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("SWSV updateView state: ", "SubroomWifiSettingsView", z);
        this.mWifiBackground.setBackground(z ? this.mContext.getResources().getDrawable(R.drawable.subroom_active_background) : this.mContext.getResources().getDrawable(R.drawable.subroom_inactive_background));
        StringBuffer stringBuffer = new StringBuffer();
        String string = this.mContext.getString(z ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
        stringBuffer.append(this.mContext.getString(R.string.quick_settings_sec_wifi_label));
        stringBuffer.append(",");
        stringBuffer.append(string);
        this.mWifiButton.setContentDescription(stringBuffer);
    }
}
