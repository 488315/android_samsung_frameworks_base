package com.android.systemui.p014qp;

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
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        subscreenWifiController.setListener(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSubscreenWifiController.registerReceiver(true);
        SubscreenWifiController subscreenWifiController = this.mSubscreenWifiController;
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("SWC isEnabled enabled: "), subscreenWifiController.mWifiState, "SubscreenWifiController");
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
                if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isWifiTileBlocked()) {
                    Log.d("SubroomWifiSettingsView", "Subscreen Wifi tile not available by KnoxStateMonitor.");
                } else {
                    SubscreenWifiController subscreenWifiController = subroomWifiSettingsView.mSubscreenWifiController;
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("SWC isEnabled enabled: "), subscreenWifiController.mWifiState, "SubscreenWifiController");
                    boolean z = subscreenWifiController.mWifiState;
                    if (z) {
                        SubscreenWifiController subscreenWifiController2 = subroomWifiSettingsView.mSubscreenWifiController;
                        subscreenWifiController2.getClass();
                        boolean z2 = false;
                        if (((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isSecure() && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isLockFunctionsEnabled()) {
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
                            z2 = true;
                        }
                        if (z2) {
                            return;
                        }
                    }
                    subroomWifiSettingsView.mSubscreenWifiController.mWifiAdapter.setWifiEnabled(!z);
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE2001");
            }
        });
        this.mWifiButton.setOnLongClickListener(new SubroomWifiSettingsView$$ExternalSyntheticLambda1());
    }

    @Override // com.android.systemui.p014qp.SubscreenQSControllerContract$View
    public final void updateView(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("SWSV updateView state: ", z, "SubroomWifiSettingsView");
        this.mWifiBackground.setBackground(z ? this.mContext.getResources().getDrawable(R.drawable.subroom_active_background) : this.mContext.getResources().getDrawable(R.drawable.subroom_inactive_background));
        StringBuffer stringBuffer = new StringBuffer();
        String string = this.mContext.getString(z ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
        stringBuffer.append(this.mContext.getString(R.string.quick_settings_sec_wifi_label));
        stringBuffer.append(",");
        stringBuffer.append(string);
        this.mWifiButton.setContentDescription(stringBuffer);
    }
}
