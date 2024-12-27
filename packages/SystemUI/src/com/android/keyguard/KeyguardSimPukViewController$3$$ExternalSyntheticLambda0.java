package com.android.keyguard;

import android.app.ProgressDialog;
import android.telephony.PinResult;
import android.util.Log;
import android.view.View;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPukViewController;
import com.android.systemui.R;
import com.android.systemui.util.ViewController;

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
    public final void run() {
        View view;
        View view2;
        View view3;
        View view4;
        View view5;
        View view6;
        switch (this.$r8$classId) {
            case 0:
                KeyguardSimPukViewController.AnonymousClass3 anonymousClass3 = (KeyguardSimPukViewController.AnonymousClass3) this.f$0;
                PinResult pinResult = this.f$1;
                ProgressDialog progressDialog = KeyguardSimPukViewController.this.mSimUnlockProgressDialog;
                if (progressDialog != null) {
                    progressDialog.hide();
                }
                view = ((ViewController) KeyguardSimPukViewController.this).mView;
                ((KeyguardSimPukView) view).resetPasswordText(true, pinResult.getResult() != 0);
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
                        KeyguardSecMessageAreaController keyguardSecMessageAreaController = keyguardSimPukViewController2.mMessageAreaController;
                        view3 = ((ViewController) keyguardSimPukViewController2).mView;
                        int attemptsRemaining = pinResult.getAttemptsRemaining();
                        view4 = ((ViewController) KeyguardSimPukViewController.this).mView;
                        keyguardSecMessageAreaController.setMessage(((KeyguardSimPukView) view3).getPukPasswordErrorMessage(attemptsRemaining, false, KeyguardEsimArea.isEsimLocked(anonymousClass3.mSubId, ((KeyguardSimPukView) view4).getContext())));
                        if (pinResult.getAttemptsRemaining() <= 2) {
                            KeyguardSimPukViewController.this.getPukRemainingAttemptsDialog(pinResult.getAttemptsRemaining()).show();
                        } else {
                            KeyguardSimPukViewController keyguardSimPukViewController3 = KeyguardSimPukViewController.this;
                            KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = keyguardSimPukViewController3.mMessageAreaController;
                            view5 = ((ViewController) keyguardSimPukViewController3).mView;
                            int attemptsRemaining2 = pinResult.getAttemptsRemaining();
                            view6 = ((ViewController) KeyguardSimPukViewController.this).mView;
                            keyguardSecMessageAreaController2.setMessage(((KeyguardSimPukView) view5).getPukPasswordErrorMessage(attemptsRemaining2, false, KeyguardEsimArea.isEsimLocked(anonymousClass3.mSubId, ((KeyguardSimPukView) view6).getContext())));
                        }
                    } else {
                        KeyguardSimPukViewController keyguardSimPukViewController4 = KeyguardSimPukViewController.this;
                        KeyguardSecMessageAreaController keyguardSecMessageAreaController3 = keyguardSimPukViewController4.mMessageAreaController;
                        view2 = ((ViewController) keyguardSimPukViewController4).mView;
                        keyguardSecMessageAreaController3.setMessage(((KeyguardSimPukView) view2).getResources().getString(R.string.kg_password_puk_failed));
                    }
                    Log.d("KeyguardSimPukView", "verifyPasswordAndUnlock  UpdateSim.onSimCheckResponse:  attemptsRemaining=" + pinResult.getAttemptsRemaining());
                }
                KeyguardSimPukViewController.this.mStateMachine.reset();
                KeyguardSimPukViewController.this.mCheckSimPukThread = null;
                break;
            default:
                this.f$0.onSimLockChangedResponse(this.f$1);
                break;
        }
    }
}
