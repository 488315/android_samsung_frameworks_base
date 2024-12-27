package com.android.keyguard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.android.keyguard.KeyguardUCMViewController;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardUCMViewController$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Bundle f$3;

    public /* synthetic */ KeyguardUCMViewController$1$$ExternalSyntheticLambda0(KeyguardUCMViewController.AnonymousClass1 anonymousClass1, int i, int i2, Bundle bundle) {
        this.f$0 = anonymousClass1;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        View view;
        View view2;
        View view3;
        View view4;
        View view5;
        switch (this.$r8$classId) {
            case 0:
                KeyguardUCMViewController.AnonymousClass1 anonymousClass1 = (KeyguardUCMViewController.AnonymousClass1) this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                Bundle bundle = this.f$3;
                view = ((ViewController) KeyguardUCMViewController.this).mView;
                ((KeyguardUCMView) view).resetPasswordText(true, true);
                Log.d("KeyguardUCMPinView", "verifyPINAndUnlock : " + i);
                if (KeyguardUCMViewController.this.mUnlockProgressDialog != null) {
                    Log.d("KeyguardUCMPinView", "mUnlockProgressDialog != null");
                    KeyguardUCMViewController.this.mUnlockProgressDialog.hide();
                    KeyguardUCMViewController.this.mUnlockProgressDialog = null;
                }
                KeyguardUCMViewController.this.mStateMachine.setStateAndRefreshUIIfNeeded(i, i2, false, bundle);
                if (KeyguardUCMViewController.this.getKeyguardSecurityCallback() != null) {
                    KeyguardUCMViewController.this.getKeyguardSecurityCallback().userActivity();
                }
                KeyguardUCMViewController keyguardUCMViewController = KeyguardUCMViewController.this;
                keyguardUCMViewController.mCheckUcmPinThread = null;
                view2 = ((ViewController) keyguardUCMViewController).mView;
                ((KeyguardUCMView) view2).setKeepScreenOn(false);
                KeyguardUCMViewController.this.mUnlockOngoing = false;
                break;
            default:
                KeyguardUCMViewController.AnonymousClass2 anonymousClass2 = (KeyguardUCMViewController.AnonymousClass2) this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                Bundle bundle2 = this.f$3;
                view3 = ((ViewController) KeyguardUCMViewController.this).mView;
                ((KeyguardUCMView) view3).resetPasswordText(true, true);
                ProgressDialog progressDialog = KeyguardUCMViewController.this.mUnlockProgressDialog;
                if (progressDialog != null) {
                    progressDialog.hide();
                    view5 = ((ViewController) KeyguardUCMViewController.this).mView;
                    ((KeyguardUCMView) view5).setKeepScreenOn(false);
                    KeyguardUCMViewController.this.mUnlockProgressDialog = null;
                }
                KeyguardUCMViewController.this.mStateMachine.setStateAndRefreshUIIfNeeded(i3, i4, false, bundle2);
                if (KeyguardUCMViewController.this.getKeyguardSecurityCallback() != null) {
                    KeyguardUCMViewController.this.getKeyguardSecurityCallback().userActivity();
                }
                KeyguardUCMViewController keyguardUCMViewController2 = KeyguardUCMViewController.this;
                keyguardUCMViewController2.mCheckUcmPukThread = null;
                view4 = ((ViewController) keyguardUCMViewController2).mView;
                ((KeyguardUCMView) view4).setKeepScreenOn(false);
                KeyguardUCMViewController.this.mUnlockOngoing = false;
                break;
        }
    }

    public /* synthetic */ KeyguardUCMViewController$1$$ExternalSyntheticLambda0(KeyguardUCMViewController.AnonymousClass2 anonymousClass2, int i, int i2, Bundle bundle) {
        this.f$0 = anonymousClass2;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = bundle;
    }
}
