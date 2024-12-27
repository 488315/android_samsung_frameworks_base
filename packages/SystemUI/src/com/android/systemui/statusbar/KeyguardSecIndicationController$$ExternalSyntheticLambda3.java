package com.android.systemui.statusbar;

import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.KeyguardSecIndicationController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardSecIndicationController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecIndicationController$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                KeyguardSecIndicationController keyguardSecIndicationController = (KeyguardSecIndicationController) obj;
                keyguardSecIndicationController.getClass();
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    int rotation = keyguardSecIndicationController.mContext.getResources().getConfiguration().windowConfiguration.getRotation();
                    i = rotation != 1 ? rotation != 2 ? rotation != 3 ? R.string.kg_in_display_fingerprint_sensor_0_help_instructions : R.string.kg_in_display_fingerprint_sensor_270_help_instructions : R.string.kg_in_display_fingerprint_sensor_180_help_instructions : R.string.kg_in_display_fingerprint_sensor_90_help_instructions;
                } else {
                    i = 0;
                }
                if (i != 0) {
                    keyguardSecIndicationController.mTopIndicationView.announceForAccessibility(keyguardSecIndicationController.mContext.getString(i));
                    break;
                }
                break;
            default:
                ((KeyguardSecIndicationController.SecKeyguardCallback) obj).this$0.getClass();
                break;
        }
    }
}
