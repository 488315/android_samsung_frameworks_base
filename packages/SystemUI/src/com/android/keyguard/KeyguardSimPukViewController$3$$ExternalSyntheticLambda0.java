package com.android.keyguard;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.telephony.PinResult;
import android.util.Log;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPukViewController;
import com.android.systemui.R;
import com.android.systemui.util.ViewController;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSimPukViewController$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSimPukViewController.CheckSimPuk f$0;
    public final /* synthetic */ PinResult f$1;

    public /* synthetic */ KeyguardSimPukViewController$3$$ExternalSyntheticLambda0(KeyguardSimPukViewController.CheckSimPuk checkSimPuk, PinResult pinResult, int i) {
        this.$r8$classId = i;
        this.f$0 = checkSimPuk;
        this.f$1 = pinResult;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        switch (this.$r8$classId) {
            case 0:
                KeyguardSimPukViewController.AnonymousClass3 anonymousClass3 = (KeyguardSimPukViewController.AnonymousClass3) this.f$0;
                PinResult pinResult = this.f$1;
                ProgressDialog progressDialog = KeyguardSimPukViewController.this.mSimUnlockProgressDialog;
                if (progressDialog != null) {
                    progressDialog.hide();
                }
                ((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).resetPasswordText(true, pinResult.getResult() != 0);
                if (pinResult.getResult() == 0) {
                    KeyguardSimPukViewController.this.mKeyguardUpdateMonitor.reportSimUnlocked(anonymousClass3.mSubId);
                    KeyguardSimPukViewController keyguardSimPukViewController = KeyguardSimPukViewController.this;
                    keyguardSimPukViewController.mRemainingAttempts = -1;
                    keyguardSimPukViewController.mShowDefaultMessage = true;
                    keyguardSimPukViewController.getKeyguardSecurityCallback().dismiss(true, KeyguardSimPukViewController.this.mSelectedUserInteractor.getSelectedUserId(), KeyguardSecurityModel.SecurityMode.SimPuk);
                } else {
                    KeyguardSimPukViewController.this.mShowDefaultMessage = false;
                    if (pinResult.getResult() == 1) {
                        KeyguardSimPukViewController keyguardSimPukViewController2 = KeyguardSimPukViewController.this;
                        keyguardSimPukViewController2.mMessageAreaController.setMessage(((KeyguardSimPukView) ((ViewController) keyguardSimPukViewController2).mView).getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), false, KeyguardEsimArea.isEsimLocked(anonymousClass3.mSubId, ((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).getContext())), false);
                        if (pinResult.getAttemptsRemaining() <= 2) {
                            KeyguardSimPukViewController.this.getPukRemainingAttemptsDialog(pinResult.getAttemptsRemaining()).show();
                        } else {
                            KeyguardSimPukViewController keyguardSimPukViewController3 = KeyguardSimPukViewController.this;
                            keyguardSimPukViewController3.mMessageAreaController.setMessage(((KeyguardSimPukView) ((ViewController) keyguardSimPukViewController3).mView).getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), false, KeyguardEsimArea.isEsimLocked(anonymousClass3.mSubId, ((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).getContext())), false);
                        }
                    } else {
                        KeyguardSimPukViewController keyguardSimPukViewController4 = KeyguardSimPukViewController.this;
                        keyguardSimPukViewController4.mMessageAreaController.setMessage(((KeyguardSimPukView) ((ViewController) keyguardSimPukViewController4).mView).getResources().getString(R.string.kg_password_puk_failed), false);
                    }
                    Log.d("KeyguardSimPukView", "verifyPasswordAndUnlock  UpdateSim.onSimCheckResponse:  attemptsRemaining=" + pinResult.getAttemptsRemaining());
                }
                KeyguardSimPukViewController.this.mStateMachine.reset();
                KeyguardSimPukViewController.this.mCheckSimPukThread = null;
                break;
            default:
                KeyguardSimPukViewController.CheckSimPuk checkSimPuk = this.f$0;
                PinResult pinResult2 = this.f$1;
                int i = KeyguardSimPukViewController.CheckSimPuk.$r8$clinit;
                checkSimPuk.onSimLockChangedResponse(pinResult2);
                break;
        }
    }
}
