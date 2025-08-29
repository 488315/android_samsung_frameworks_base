package com.android.systemui.statusbar;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.statusbar.KeyguardSecIndicationController;
import io.reactivex.functions.Action;

/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardSecIndicationController$SecKeyguardCallback$$ExternalSyntheticLambda0 implements Action {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecIndicationController.SecKeyguardCallback f$0;

    public /* synthetic */ KeyguardSecIndicationController$SecKeyguardCallback$$ExternalSyntheticLambda0(KeyguardSecIndicationController.SecKeyguardCallback secKeyguardCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = secKeyguardCallback;
    }

    @Override // io.reactivex.functions.Action
    public final void run() throws Resources.NotFoundException {
        switch (this.$r8$classId) {
            case 0:
                KeyguardSecIndicationController.m2959$$Nest$mupdateDefaultIndications(KeyguardSecIndicationController.this);
                break;
            case 1:
                KeyguardSecIndicationController keyguardSecIndicationController = KeyguardSecIndicationController.this;
                if (!keyguardSecIndicationController.mKeyguardUpdateMonitor.isKeyguardUnlocking()) {
                    IndicationEventType indicationEventType = IndicationEventType.TRUST_AGENT_HELP;
                    keyguardSecIndicationController.removeIndication(indicationEventType);
                    if (!keyguardSecIndicationController.hasTrust()) {
                        indicationEventType = IndicationEventType.UNLOCK_GUIDE;
                    }
                    keyguardSecIndicationController.addIndication(indicationEventType, keyguardSecIndicationController.getUnlockGuideText());
                    break;
                }
                break;
            default:
                KeyguardSecIndicationController keyguardSecIndicationController2 = KeyguardSecIndicationController.this;
                String string = keyguardSecIndicationController2.mContext.getString(R.string.kg_fingerprint_retry_notification);
                if (!keyguardSecIndicationController2.mKeyguardUpdateMonitor.isFingerprintLeave()) {
                    keyguardSecIndicationController2.addIndicationTimeout(IndicationEventType.BIOMETRICS_HELP, string, keyguardSecIndicationController2.mErrorColor, false);
                    break;
                } else {
                    KeyguardSecIndicationController.m2959$$Nest$mupdateDefaultIndications(keyguardSecIndicationController2);
                    break;
                }
        }
    }
}
