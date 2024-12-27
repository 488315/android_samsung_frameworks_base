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
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class SubroomBluetoothSettingsView extends LinearLayout implements SubscreenQSControllerContract$View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public LinearLayout mBluetoothBackground;
    public ImageView mBluetoothButton;
    public final Context mContext;
    public final SubscreenBleController mSubscreenBleController;

    public SubroomBluetoothSettingsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        SubscreenBleController subscreenBleController = SubscreenBleController.getInstance(context);
        this.mSubscreenBleController = subscreenBleController;
        subscreenBleController.mBleView = this;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSubscreenBleController.registerReceiver(true);
        updateView(this.mSubscreenBleController.isEnabled());
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("SubroomBluetoothSettingsView", "SBSV onDetachedFromWindow");
        this.mSubscreenBleController.unRegisterReceiver(true);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBluetoothButton = (ImageView) findViewById(R.id.bluetooth_image_view);
        this.mBluetoothBackground = (LinearLayout) findViewById(R.id.bluetooth_background);
        this.mBluetoothButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubroomBluetoothSettingsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubroomBluetoothSettingsView subroomBluetoothSettingsView = SubroomBluetoothSettingsView.this;
                int i = SubroomBluetoothSettingsView.$r8$clinit;
                subroomBluetoothSettingsView.getClass();
                if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isBluetoothTileBlocked()) {
                    Log.d("SubroomBluetoothSettingsView", "Subscreen Bluetooth tile not available by KnoxStateMonitor.");
                } else {
                    SubscreenBleController subscreenBleController = subroomBluetoothSettingsView.mSubscreenBleController;
                    StringBuilder sb = new StringBuilder("isTransient ");
                    BluetoothController bluetoothController = subscreenBleController.mBluetoothController;
                    sb.append(bluetoothController.getBluetoothState());
                    Log.d("SubscreenBleController", sb.toString());
                    if (bluetoothController.getBluetoothState() != 11) {
                        subroomBluetoothSettingsView.mSubscreenBleController.getClass();
                        if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isSecure() && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isLockFunctionsEnabled()) {
                            KeyguardManager keyguardManager = (KeyguardManager) SubscreenBleController.mContext.getSystemService("keyguard");
                            Intent intent = new Intent();
                            intent.setAction("BLUETOOTH_STATE_CHANGE");
                            PendingIntent broadcast = PendingIntent.getBroadcast(SubscreenBleController.mContext, 0, intent, 201326592);
                            Intent intent2 = new Intent();
                            intent2.putExtra("showCoverToast", true);
                            intent2.putExtra("runOnCover", true);
                            intent2.putExtra("afterKeyguardGone", true);
                            intent2.putExtra("ignoreKeyguardState", true);
                            keyguardManager.semSetPendingIntentAfterUnlock(broadcast, intent2);
                        } else {
                            subroomBluetoothSettingsView.mSubscreenBleController.mBluetoothController.setBluetoothEnabled(!subroomBluetoothSettingsView.mSubscreenBleController.isEnabled());
                        }
                    }
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BLUETOOTH_COVER);
            }
        });
        this.mBluetoothButton.setOnLongClickListener(new SubroomBluetoothSettingsView$$ExternalSyntheticLambda1());
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$View
    public final void updateView(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("SBSV updateView state: ", "SubroomBluetoothSettingsView", z);
        this.mBluetoothBackground.setBackground(z ? this.mContext.getDrawable(R.drawable.subroom_active_background) : this.mContext.getDrawable(R.drawable.subroom_inactive_background));
        StringBuffer stringBuffer = new StringBuffer();
        String string = this.mContext.getString(z ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
        stringBuffer.append(this.mContext.getString(R.string.quick_settings_bluetooth_label));
        stringBuffer.append(",");
        stringBuffer.append(string);
        this.mBluetoothButton.setContentDescription(stringBuffer);
    }
}
