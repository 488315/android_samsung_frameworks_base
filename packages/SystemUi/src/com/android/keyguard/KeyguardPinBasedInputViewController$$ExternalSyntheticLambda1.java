package com.android.keyguard;

import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.classifier.FalsingCollectorImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1 implements View.OnTouchListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

    public /* synthetic */ KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardPinBasedInputViewController;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        switch (this.$r8$classId) {
            case 0:
                KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = this.f$0;
                keyguardPinBasedInputViewController.getClass();
                if (motionEvent.getActionMasked() == 0) {
                    ((KeyguardPinBasedInputView) keyguardPinBasedInputViewController.mView).doHapticKeyClick();
                    break;
                }
                break;
            default:
                KeyguardPinBasedInputViewController keyguardPinBasedInputViewController2 = this.f$0;
                keyguardPinBasedInputViewController2.getClass();
                if (motionEvent.getActionMasked() == 0) {
                    ((FalsingCollectorImpl) keyguardPinBasedInputViewController2.mFalsingCollector).avoidGesture();
                    break;
                }
                break;
        }
        return false;
    }
}
