package com.android.keyguard;

import android.view.MotionEvent;
import android.view.View;

public final /* synthetic */ class KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1 implements View.OnTouchListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardPinBasedInputViewController f$0;

    public /* synthetic */ KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardPinBasedInputViewController;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i = this.$r8$classId;
        KeyguardPinBasedInputViewController keyguardPinBasedInputViewController = this.f$0;
        switch (i) {
            case 0:
                KeyguardPinBasedInputViewController.$r8$lambda$1BZ0HKp0hpgY_5GG7usgwHBArEI(keyguardPinBasedInputViewController, motionEvent);
                break;
            default:
                keyguardPinBasedInputViewController.getClass();
                if (motionEvent.getActionMasked() == 0) {
                    keyguardPinBasedInputViewController.mFalsingCollector.avoidGesture();
                    break;
                }
                break;
        }
        return false;
    }
}
