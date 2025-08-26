package com.android.keyguard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import com.android.keyguard.KeyguardUCMViewController;
import com.android.systemui.util.ViewController;

/* loaded from: classes.dex */
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
        switch (this.$r8$classId) {
            case 0:
                KeyguardUCMViewController.AnonymousClass1 anonymousClass1 = (KeyguardUCMViewController.AnonymousClass1) this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                Bundle bundle = this.f$3;
                ((KeyguardUCMView) ((ViewController) KeyguardUCMViewController.this).mView).resetPasswordText(true, true);
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
                ((KeyguardUCMView) ((ViewController) keyguardUCMViewController).mView).setKeepScreenOn(false);
                KeyguardUCMViewController.this.mUnlockOngoing = false;
                break;
            default:
                KeyguardUCMViewController.AnonymousClass2 anonymousClass2 = (KeyguardUCMViewController.AnonymousClass2) this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                Bundle bundle2 = this.f$3;
                ((KeyguardUCMView) ((ViewController) KeyguardUCMViewController.this).mView).resetPasswordText(true, true);
                ProgressDialog progressDialog = KeyguardUCMViewController.this.mUnlockProgressDialog;
                if (progressDialog != null) {
                    progressDialog.hide();
                    ((KeyguardUCMView) ((ViewController) KeyguardUCMViewController.this).mView).setKeepScreenOn(false);
                    KeyguardUCMViewController.this.mUnlockProgressDialog = null;
                }
                KeyguardUCMViewController.this.mStateMachine.setStateAndRefreshUIIfNeeded(i3, i4, false, bundle2);
                if (KeyguardUCMViewController.this.getKeyguardSecurityCallback() != null) {
                    KeyguardUCMViewController.this.getKeyguardSecurityCallback().userActivity();
                }
                KeyguardUCMViewController keyguardUCMViewController2 = KeyguardUCMViewController.this;
                keyguardUCMViewController2.mCheckUcmPukThread = null;
                ((KeyguardUCMView) ((ViewController) keyguardUCMViewController2).mView).setKeepScreenOn(false);
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
