package com.android.keyguard;

import android.widget.EditText;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardPasswordViewController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardPasswordViewController f$0;

    public /* synthetic */ KeyguardPasswordViewController$$ExternalSyntheticLambda0(KeyguardPasswordViewController keyguardPasswordViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardPasswordViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardPasswordViewController keyguardPasswordViewController = this.f$0;
                EditText editText = keyguardPasswordViewController.mPasswordEntry;
                editText.requestFocus();
                keyguardPasswordViewController.mInputMethodManager.showSoftInput(editText, 1);
                break;
            case 1:
                this.f$0.updateSwitchImeButton();
                break;
            default:
                KeyguardPasswordViewController.$r8$lambda$LhbF6YAKV9pHtOhdlBcgE_uPvik(this.f$0);
                break;
        }
    }
}
