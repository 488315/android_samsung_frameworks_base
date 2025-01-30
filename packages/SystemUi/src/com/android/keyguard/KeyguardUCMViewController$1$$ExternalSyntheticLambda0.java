package com.android.keyguard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import com.android.keyguard.KeyguardUCMViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUCMViewController$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Thread f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Bundle f$3;

    public /* synthetic */ KeyguardUCMViewController$1$$ExternalSyntheticLambda0(Thread thread, int i, int i2, Bundle bundle, int i3) {
        this.$r8$classId = i3;
        this.f$0 = thread;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardUCMViewController.C07911 c07911 = (KeyguardUCMViewController.C07911) this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                Bundle bundle = this.f$3;
                KeyguardUCMViewController keyguardUCMViewController = KeyguardUCMViewController.this;
                Object obj = KeyguardUCMViewController.syncObj;
                ((KeyguardUCMView) keyguardUCMViewController.mView).resetPasswordText(true, true);
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
                KeyguardUCMViewController keyguardUCMViewController2 = KeyguardUCMViewController.this;
                keyguardUCMViewController2.mCheckUcmPinThread = null;
                ((KeyguardUCMView) keyguardUCMViewController2.mView).setKeepScreenOn(false);
                KeyguardUCMViewController.this.mUnlockOngoing = false;
                break;
            default:
                KeyguardUCMViewController.C07922 c07922 = (KeyguardUCMViewController.C07922) this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                Bundle bundle2 = this.f$3;
                KeyguardUCMViewController keyguardUCMViewController3 = KeyguardUCMViewController.this;
                Object obj2 = KeyguardUCMViewController.syncObj;
                ((KeyguardUCMView) keyguardUCMViewController3.mView).resetPasswordText(true, true);
                ProgressDialog progressDialog = KeyguardUCMViewController.this.mUnlockProgressDialog;
                if (progressDialog != null) {
                    progressDialog.hide();
                    ((KeyguardUCMView) KeyguardUCMViewController.this.mView).setKeepScreenOn(false);
                    KeyguardUCMViewController.this.mUnlockProgressDialog = null;
                }
                KeyguardUCMViewController.this.mStateMachine.setStateAndRefreshUIIfNeeded(i3, i4, false, bundle2);
                if (KeyguardUCMViewController.this.getKeyguardSecurityCallback() != null) {
                    KeyguardUCMViewController.this.getKeyguardSecurityCallback().userActivity();
                }
                KeyguardUCMViewController keyguardUCMViewController4 = KeyguardUCMViewController.this;
                keyguardUCMViewController4.mCheckUcmPukThread = null;
                ((KeyguardUCMView) keyguardUCMViewController4.mView).setKeepScreenOn(false);
                KeyguardUCMViewController.this.mUnlockOngoing = false;
                break;
        }
    }
}
