package com.android.keyguard;

import android.app.ProgressDialog;
import android.telephony.PinResult;
import android.util.Log;
import android.view.View;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPinViewController;
import com.android.systemui.R;
import com.android.systemui.util.ViewController;

public final /* synthetic */ class KeyguardSimPinViewController$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSimPinViewController.CheckSimPin f$0;
    public final /* synthetic */ PinResult f$1;

    public /* synthetic */ KeyguardSimPinViewController$2$$ExternalSyntheticLambda0(KeyguardSimPinViewController.CheckSimPin checkSimPin, PinResult pinResult, int i) {
        this.$r8$classId = i;
        this.f$0 = checkSimPin;
        this.f$1 = pinResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        View view;
        View view2;
        switch (this.$r8$classId) {
            case 0:
                KeyguardSimPinViewController.AnonymousClass2 anonymousClass2 = (KeyguardSimPinViewController.AnonymousClass2) this.f$0;
                PinResult pinResult = this.f$1;
                KeyguardSimPinViewController keyguardSimPinViewController = KeyguardSimPinViewController.this;
                pinResult.getAttemptsRemaining();
                keyguardSimPinViewController.getClass();
                ProgressDialog progressDialog = KeyguardSimPinViewController.this.mSimUnlockProgressDialog;
                if (progressDialog != null) {
                    progressDialog.hide();
                }
                view = ((ViewController) KeyguardSimPinViewController.this).mView;
                ((KeyguardSimPinView) view).resetPasswordText(true, pinResult.getResult() != 0);
                if (pinResult.getResult() == 0) {
                    KeyguardSimPinViewController.this.mKeyguardUpdateMonitor.reportSimUnlocked(anonymousClass2.mSubId);
                    KeyguardSimPinViewController.this.getClass();
                    KeyguardSimPinViewController keyguardSimPinViewController2 = KeyguardSimPinViewController.this;
                    keyguardSimPinViewController2.mShowDefaultMessage = true;
                    keyguardSimPinViewController2.getKeyguardSecurityCallback().dismiss(true, KeyguardSimPinViewController.this.mSelectedUserInteractor.getSelectedUserId(false), KeyguardSecurityModel.SecurityMode.SimPin);
                } else {
                    KeyguardSimPinViewController.this.mShowDefaultMessage = false;
                    if (pinResult.getResult() != 1) {
                        KeyguardSimPinViewController keyguardSimPinViewController3 = KeyguardSimPinViewController.this;
                        KeyguardSecMessageAreaController keyguardSecMessageAreaController = keyguardSimPinViewController3.mMessageAreaController;
                        view2 = ((ViewController) keyguardSimPinViewController3).mView;
                        keyguardSecMessageAreaController.setMessage$1(((KeyguardSimPinView) view2).getResources().getString(R.string.kg_password_pin_failed), false);
                    } else if (pinResult.getAttemptsRemaining() <= 2) {
                        KeyguardSimPinViewController.m841$$Nest$mgetSimRemainingAttemptsDialog(KeyguardSimPinViewController.this, pinResult.getAttemptsRemaining()).show();
                    } else {
                        KeyguardSimPinViewController keyguardSimPinViewController4 = KeyguardSimPinViewController.this;
                        keyguardSimPinViewController4.mMessageAreaController.setMessage$1(keyguardSimPinViewController4.getPinPasswordErrorMessage(pinResult.getAttemptsRemaining()), false);
                    }
                    Log.d("KeyguardSimPinView", "verifyPasswordAndUnlock  CheckSimPin.onSimCheckResponse: " + pinResult + " attemptsRemaining=" + pinResult.getAttemptsRemaining());
                }
                if (KeyguardSimPinViewController.this.getKeyguardSecurityCallback() != null) {
                    KeyguardSimPinViewController.this.getKeyguardSecurityCallback().userActivity();
                }
                KeyguardSimPinViewController.this.mCheckSimPinThread = null;
                break;
            default:
                this.f$0.onSimCheckResponse(this.f$1);
                break;
        }
    }
}
