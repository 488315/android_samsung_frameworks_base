package com.android.keyguard;

import android.R;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.settingslib.WirelessUtils;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class EmergencyButtonController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EmergencyButtonController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ EmergencyButtonController$$ExternalSyntheticLambda2(EmergencyButtonController emergencyButtonController, boolean z, boolean z2, int i) {
        this.$r8$classId = i;
        this.f$0 = emergencyButtonController;
        this.f$1 = z;
        this.f$2 = z2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0089, code lost:
    
        if (r6 != false) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x011a, code lost:
    
        if (r3.getVisibility() == 0) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x011d, code lost:
    
        if (r7 != false) goto L72;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0139  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        boolean z;
        boolean z2;
        boolean z3;
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                EmergencyButtonController emergencyButtonController = this.f$0;
                boolean z4 = this.f$1;
                boolean z5 = this.f$2;
                emergencyButtonController.getClass();
                emergencyButtonController.mMainExecutor.execute(new EmergencyButtonController$$ExternalSyntheticLambda2(emergencyButtonController, z4, z5, i));
                break;
            default:
                EmergencyButtonController emergencyButtonController2 = this.f$0;
                boolean z6 = this.f$1;
                boolean z7 = this.f$2;
                EmergencyButton emergencyButton = (EmergencyButton) emergencyButtonController2.mView;
                boolean hasSystemFeature = emergencyButtonController2.getContext().getPackageManager().hasSystemFeature("android.hardware.telephony");
                emergencyButtonController2.mKeyguardUpdateMonitor.isSimPinSecure();
                int i2 = emergencyButtonController2.mCurrentSimState;
                boolean z8 = emergencyButtonController2.mBouncerShowing;
                emergencyButton.getClass();
                KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
                boolean z9 = LsRune.SECURITY_NOT_REQUIRE_SIMPUK_CODE && i2 == 3;
                boolean z10 = LsRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE;
                if (z10) {
                    z = keyguardUpdateMonitor.isOutOfService();
                    z2 = WirelessUtils.isAirplaneModeOn(emergencyButton.getContext());
                    EmergencyButtonController$$ExternalSyntheticOutline0.m59m("updateEmergencyCallButton isOutOfService = ", z, " isAirplaneModeOn = ", z2, "EmergencyButton");
                } else {
                    z = false;
                    z2 = false;
                }
                if (!z10 || !z || z2) {
                    if (LsRune.SECURITY_HIDE_EMERGENCY_BUTTON_BY_SIMSTATE) {
                        if (i2 != 1 && i2 != 2 && i2 != 3 && i2 != 7) {
                            z3 = false;
                            break;
                        } else {
                            z3 = true;
                            break;
                        }
                    }
                    if (hasSystemFeature) {
                        if (!z6) {
                            if (!z7) {
                                if (!LsRune.LOCKUI_BOTTOM_USIM_TEXT) {
                                    if (z8) {
                                        break;
                                    }
                                } else {
                                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
                                    if (i2 == 1 && DeviceState.isNoSimState(emergencyButton.getContext())) {
                                        Log.d("EmergencyButton", "SIM_STATE_ABSENT");
                                    } else {
                                        TelephonyManager telephonyManager = emergencyButton.mTelephonyManager;
                                        if (telephonyManager != null && telephonyManager.isVoiceCapable() && keyguardUpdateMonitor2.isAllSlotEmergencyOnly()) {
                                            Log.d("EmergencyButton", "AllSlot EmergencyOnly");
                                        } else if (i2 == 5 && LsRune.SECURITY_SKT_USIM_TEXT && !SystemProperties.get("ril.simtype").equals("") && 19 == Integer.valueOf(SystemProperties.get("ril.simtype")).intValue()) {
                                            Log.d("EmergencyButton", "SKT Usim unregisterd");
                                        } else if (WirelessUtils.isAirplaneModeOn(emergencyButton.getContext())) {
                                            Log.d("EmergencyButton", "AirplaneMode On");
                                        } else {
                                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Can't match sim state, simState : ", i2, "EmergencyButton");
                                        }
                                    }
                                }
                            } else {
                                Log.d("EmergencyButton", "updateEmergencyCallButton : secure");
                            }
                        }
                        if (i != 0) {
                            emergencyButton.setVisibility(8);
                            break;
                        } else {
                            emergencyButton.setVisibility(0);
                            emergencyButton.setText(z6 ? R.string.permdesc_changeTetherState : z9 ? com.android.systemui.R.string.kg_sim_puk_tmo_enter_unlock_code : com.android.systemui.R.string.kg_lockscreen_emergency_call_button_text);
                            break;
                        }
                    }
                }
                i = 0;
                if (i != 0) {
                }
                break;
        }
    }
}
