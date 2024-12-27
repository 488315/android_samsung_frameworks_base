package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.samsung.android.biometrics.app.setting.Utils;

import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class KeyboardVisibilityObserver implements ViewTreeObserver.OnGlobalLayoutListener {
    public final Supplier getScreenHeight;
    public boolean mIsStarted;
    public AuthCredentialPasswordView$$ExternalSyntheticLambda1 mListener;
    public final View mView;
    public int mVisibleState = 0;

    public KeyboardVisibilityObserver(
            View view,
            AuthCredentialPasswordView$$ExternalSyntheticLambda0
                    authCredentialPasswordView$$ExternalSyntheticLambda0) {
        this.mView = view;
        this.getScreenHeight = authCredentialPasswordView$$ExternalSyntheticLambda0;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        AuthCredentialPasswordView$$ExternalSyntheticLambda1
                authCredentialPasswordView$$ExternalSyntheticLambda1;
        AuthCredentialPasswordView$$ExternalSyntheticLambda1
                authCredentialPasswordView$$ExternalSyntheticLambda12;
        if (this.mIsStarted) {
            this.mIsStarted = false;
            this.mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        Rect rect = new Rect();
        this.mView.getWindowVisibleDisplayFrame(rect);
        int intValue = ((Integer) this.getScreenHeight.get()).intValue();
        int i = intValue - rect.bottom;
        if (Utils.DEBUG) {
            Log.d(
                    "BSS_KeyboardVisibilityObserver",
                    "Screen Height = " + intValue + ", Keyboard Height = " + i);
        }
        if (i > intValue * 0.15f) {
            if (this.mVisibleState != 1
                    && (authCredentialPasswordView$$ExternalSyntheticLambda12 = this.mListener)
                            != null) {
                AuthCredentialPasswordView authCredentialPasswordView =
                        authCredentialPasswordView$$ExternalSyntheticLambda12.f$0;
                authCredentialPasswordView.mIsKeyboardVisible = true;
                authCredentialPasswordView.onKeyboardVisibilityChanged(true);
            }
            this.mVisibleState = 1;
        } else {
            if (this.mVisibleState != 2
                    && (authCredentialPasswordView$$ExternalSyntheticLambda1 = this.mListener)
                            != null) {
                AuthCredentialPasswordView authCredentialPasswordView2 =
                        authCredentialPasswordView$$ExternalSyntheticLambda1.f$0;
                authCredentialPasswordView2.mIsKeyboardVisible = false;
                authCredentialPasswordView2.onKeyboardVisibilityChanged(false);
            }
            this.mVisibleState = 2;
        }
        if (this.mIsStarted) {
            return;
        }
        this.mView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        this.mIsStarted = true;
    }
}
