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
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

public class SubroomAirplaneModeSettingsView extends LinearLayout implements SubscreenQSControllerContract$View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public LinearLayout mAirplaneBackground;
    public ImageView mAirplaneButton;
    public final Context mContext;
    public final SubscreenAirplaneController mSubscreenAirplaneController;

    public SubroomAirplaneModeSettingsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        SubscreenAirplaneController subscreenAirplaneController = SubscreenAirplaneController.getInstance(context);
        this.mSubscreenAirplaneController = subscreenAirplaneController;
        subscreenAirplaneController.mAirplaneView = this;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSubscreenAirplaneController.registerReceiver(true);
        this.mSubscreenAirplaneController.getClass();
        updateView(((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAirplaneModeOn());
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mSubscreenAirplaneController.unRegisterReceiver(true);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mAirplaneButton = (ImageView) findViewById(R.id.airplane_image_view);
        this.mAirplaneBackground = (LinearLayout) findViewById(R.id.airplane_background);
        this.mAirplaneButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubroomAirplaneModeSettingsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubroomAirplaneModeSettingsView subroomAirplaneModeSettingsView = SubroomAirplaneModeSettingsView.this;
                int i = SubroomAirplaneModeSettingsView.$r8$clinit;
                subroomAirplaneModeSettingsView.getClass();
                if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isAirplaneModeTileBlocked()) {
                    Log.d("SubroomAirplaneModeSettingsView", "Subscreen Airplane Mode tile not available by KnoxStateMonitor.");
                } else {
                    subroomAirplaneModeSettingsView.mSubscreenAirplaneController.getClass();
                    if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isSecure() && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isLockFunctionsEnabled()) {
                        KeyguardManager keyguardManager = (KeyguardManager) SubscreenAirplaneController.mContext.getSystemService("keyguard");
                        Intent intent = new Intent();
                        intent.setAction("AIRPLANE_MODE_CHANGE");
                        PendingIntent broadcast = PendingIntent.getBroadcast(SubscreenAirplaneController.mContext, 0, intent, 201326592);
                        Intent intent2 = new Intent();
                        intent2.putExtra("showCoverToast", true);
                        intent2.putExtra("runOnCover", true);
                        intent2.putExtra("afterKeyguardGone", true);
                        intent2.putExtra("ignoreKeyguardState", true);
                        keyguardManager.semSetPendingIntentAfterUnlock(broadcast, intent2);
                    } else {
                        subroomAirplaneModeSettingsView.mSubscreenAirplaneController.getClass();
                        boolean z = !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAirplaneModeOn();
                        subroomAirplaneModeSettingsView.mSubscreenAirplaneController.mConnectivityManager.setAirplaneMode(z);
                        subroomAirplaneModeSettingsView.updateView(z);
                    }
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_AIRPLANEMODE_COVER);
            }
        });
        this.mAirplaneButton.setOnLongClickListener(new SubroomAirplaneModeSettingsView$$ExternalSyntheticLambda1());
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$View
    public final void updateView(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("SASV updateView state: ", "SubroomAirplaneModeSettingsView", z);
        this.mAirplaneBackground.setBackground(z ? this.mContext.getResources().getDrawable(R.drawable.subroom_active_background) : this.mContext.getResources().getDrawable(R.drawable.subroom_inactive_background));
        StringBuffer stringBuffer = new StringBuffer();
        String string = this.mContext.getString(z ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
        stringBuffer.append(this.mContext.getString(R.string.airplane_mode));
        stringBuffer.append(",");
        stringBuffer.append(string);
        this.mAirplaneButton.setContentDescription(stringBuffer);
    }
}
