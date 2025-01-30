package com.android.keyguard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.telephony.PinResult;
import android.util.Log;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPukViewController;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardSimPukViewController.C07793 c07793 = (KeyguardSimPukViewController.C07793) this.f$0;
                PinResult pinResult = this.f$1;
                ProgressDialog progressDialog = c07793.this$0.mSimUnlockProgressDialog;
                if (progressDialog != null) {
                    progressDialog.hide();
                }
                ((KeyguardSimPukView) c07793.this$0.mView).resetPasswordText(true, pinResult.getResult() != 0);
                if (pinResult.getResult() == 0) {
                    c07793.this$0.mKeyguardUpdateMonitor.reportSimUnlocked(c07793.mSubId);
                    KeyguardSimPukViewController keyguardSimPukViewController = c07793.this$0;
                    keyguardSimPukViewController.mRemainingAttempts = -1;
                    keyguardSimPukViewController.mShowDefaultMessage = true;
                    keyguardSimPukViewController.getKeyguardSecurityCallback().dismiss(KeyguardUpdateMonitor.getCurrentUser(), KeyguardSecurityModel.SecurityMode.SimPuk, true);
                } else {
                    c07793.this$0.mShowDefaultMessage = false;
                    if (pinResult.getResult() == 1) {
                        KeyguardSimPukViewController keyguardSimPukViewController2 = c07793.this$0;
                        keyguardSimPukViewController2.mMessageAreaController.setMessage(((KeyguardSimPukView) keyguardSimPukViewController2.mView).getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), false, KeyguardEsimArea.isEsimLocked(c07793.mSubId, ((KeyguardSimPukView) c07793.this$0.mView).getContext())), false);
                        if (pinResult.getAttemptsRemaining() <= 2) {
                            KeyguardSimPukViewController keyguardSimPukViewController3 = c07793.this$0;
                            int attemptsRemaining = pinResult.getAttemptsRemaining();
                            KeyguardSimPukView keyguardSimPukView = (KeyguardSimPukView) keyguardSimPukViewController3.mView;
                            String pukPasswordErrorMessage = keyguardSimPukView.getPukPasswordErrorMessage(attemptsRemaining, false, KeyguardEsimArea.isEsimLocked(keyguardSimPukViewController3.mSubId, keyguardSimPukView.getContext()));
                            AlertDialog alertDialog = keyguardSimPukViewController3.mRemainingAttemptsDialog;
                            if (alertDialog == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(((KeyguardSimPukView) keyguardSimPukViewController3.mView).getContext());
                                builder.setMessage(pukPasswordErrorMessage);
                                builder.setCancelable(false);
                                builder.setNeutralButton(R.string.f789ok, (DialogInterface.OnClickListener) null);
                                AlertDialog create = builder.create();
                                keyguardSimPukViewController3.mRemainingAttemptsDialog = create;
                                create.getWindow().setType(2009);
                            } else {
                                alertDialog.setMessage(pukPasswordErrorMessage);
                            }
                            keyguardSimPukViewController3.mRemainingAttemptsDialog.show();
                        } else {
                            KeyguardSimPukViewController keyguardSimPukViewController4 = c07793.this$0;
                            keyguardSimPukViewController4.mMessageAreaController.setMessage(((KeyguardSimPukView) keyguardSimPukViewController4.mView).getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), false, KeyguardEsimArea.isEsimLocked(c07793.mSubId, ((KeyguardSimPukView) c07793.this$0.mView).getContext())), false);
                        }
                    } else {
                        KeyguardSimPukViewController keyguardSimPukViewController5 = c07793.this$0;
                        keyguardSimPukViewController5.mMessageAreaController.setMessage(((KeyguardSimPukView) keyguardSimPukViewController5.mView).getResources().getString(R.string.kg_password_puk_failed), false);
                    }
                    Log.d("KeyguardSimPukView", "verifyPasswordAndUnlock  UpdateSim.onSimCheckResponse:  attemptsRemaining=" + pinResult.getAttemptsRemaining());
                }
                c07793.this$0.mStateMachine.reset();
                c07793.this$0.mCheckSimPukThread = null;
                break;
            default:
                this.f$0.onSimLockChangedResponse(this.f$1);
                break;
        }
    }
}
