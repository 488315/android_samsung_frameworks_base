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
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            /* JADX WARN: Removed duplicated region for block: B:19:0x00a0  */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onClick(View view) {
                SubroomBluetoothSettingsView subroomBluetoothSettingsView = SubroomBluetoothSettingsView.this;
                int i = SubroomBluetoothSettingsView.$r8$clinit;
                subroomBluetoothSettingsView.getClass();
                if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isBluetoothTileBlocked()) {
                    Log.d("SubroomBluetoothSettingsView", "Subscreen Bluetooth tile not available by KnoxStateMonitor.");
                } else {
                    boolean isTransient = subroomBluetoothSettingsView.mSubscreenBleController.isTransient();
                    boolean isEnabled = subroomBluetoothSettingsView.mSubscreenBleController.isEnabled();
                    EmergencyButtonController$$ExternalSyntheticOutline0.m59m("mBluetoothButton onClick,enabled = ", isEnabled, ",isTransient = ", isTransient, "SubroomBluetoothSettingsView");
                    if (!isTransient) {
                        SubscreenBleController subscreenBleController = subroomBluetoothSettingsView.mSubscreenBleController;
                        boolean z = true;
                        if (((KeyguardStateControllerImpl) subscreenBleController.mKeyguardStateController).mShowing) {
                            KeyguardUpdateMonitor keyguardUpdateMonitor = subscreenBleController.mKeyguardUpdateMonitor;
                            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isLockFunctionsEnabled()) {
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
                                if (!z) {
                                    boolean z2 = !isEnabled;
                                    subroomBluetoothSettingsView.mSubscreenBleController.mBluetoothController.setBluetoothEnabled(z2);
                                    subroomBluetoothSettingsView.updateView(z2);
                                }
                            }
                        }
                        z = false;
                        if (!z) {
                        }
                    }
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE2003");
            }
        });
        this.mBluetoothButton.setOnLongClickListener(new SubroomBluetoothSettingsView$$ExternalSyntheticLambda1());
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$View
    public final void updateView(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("SBSV updateView state: ", z, "SubroomBluetoothSettingsView");
        this.mBluetoothBackground.setBackground(z ? this.mContext.getDrawable(R.drawable.subroom_active_background) : this.mContext.getDrawable(R.drawable.subroom_inactive_background));
        StringBuffer stringBuffer = new StringBuffer();
        String string = this.mContext.getString(z ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
        stringBuffer.append(this.mContext.getString(R.string.quick_settings_bluetooth_label));
        stringBuffer.append(",");
        stringBuffer.append(string);
        this.mBluetoothButton.setContentDescription(stringBuffer);
    }
}
