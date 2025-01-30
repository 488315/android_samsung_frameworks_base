package com.android.keyguard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.telephony.PinResult;
import android.util.Log;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPinViewController;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        switch (this.$r8$classId) {
            case 0:
                KeyguardSimPinViewController.C07752 c07752 = (KeyguardSimPinViewController.C07752) this.f$0;
                PinResult pinResult = this.f$1;
                KeyguardSimPinViewController keyguardSimPinViewController = KeyguardSimPinViewController.this;
                pinResult.getAttemptsRemaining();
                keyguardSimPinViewController.getClass();
                ProgressDialog progressDialog = KeyguardSimPinViewController.this.mSimUnlockProgressDialog;
                if (progressDialog != null) {
                    progressDialog.hide();
                }
                ((KeyguardSimPinView) KeyguardSimPinViewController.this.mView).resetPasswordText(true, pinResult.getResult() != 0);
                if (pinResult.getResult() == 0) {
                    KeyguardSimPinViewController.this.mKeyguardUpdateMonitor.reportSimUnlocked(c07752.mSubId);
                    KeyguardSimPinViewController.this.getClass();
                    KeyguardSimPinViewController keyguardSimPinViewController2 = KeyguardSimPinViewController.this;
                    keyguardSimPinViewController2.mShowDefaultMessage = true;
                    keyguardSimPinViewController2.getKeyguardSecurityCallback().dismiss(KeyguardUpdateMonitor.getCurrentUser(), KeyguardSecurityModel.SecurityMode.SimPin, true);
                } else {
                    KeyguardSimPinViewController.this.mShowDefaultMessage = false;
                    if (pinResult.getResult() != 1) {
                        KeyguardSimPinViewController keyguardSimPinViewController3 = KeyguardSimPinViewController.this;
                        keyguardSimPinViewController3.mMessageAreaController.setMessage(((KeyguardSimPinView) keyguardSimPinViewController3.mView).getResources().getString(R.string.kg_password_pin_failed), false);
                    } else if (pinResult.getAttemptsRemaining() <= 2) {
                        KeyguardSimPinViewController keyguardSimPinViewController4 = KeyguardSimPinViewController.this;
                        String pinPasswordErrorMessage = keyguardSimPinViewController4.getPinPasswordErrorMessage(pinResult.getAttemptsRemaining());
                        AlertDialog alertDialog = keyguardSimPinViewController4.mRemainingAttemptsDialog;
                        if (alertDialog == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(((KeyguardSimPinView) keyguardSimPinViewController4.mView).getContext());
                            builder.setMessage(pinPasswordErrorMessage);
                            builder.setCancelable(false);
                            builder.setNeutralButton(R.string.f789ok, (DialogInterface.OnClickListener) null);
                            AlertDialog create = builder.create();
                            keyguardSimPinViewController4.mRemainingAttemptsDialog = create;
                            create.getWindow().setType(2009);
                        } else {
                            alertDialog.setMessage(pinPasswordErrorMessage);
                        }
                        keyguardSimPinViewController4.mRemainingAttemptsDialog.show();
                    } else {
                        KeyguardSimPinViewController keyguardSimPinViewController5 = KeyguardSimPinViewController.this;
                        keyguardSimPinViewController5.mMessageAreaController.setMessage(keyguardSimPinViewController5.getPinPasswordErrorMessage(pinResult.getAttemptsRemaining()), false);
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
