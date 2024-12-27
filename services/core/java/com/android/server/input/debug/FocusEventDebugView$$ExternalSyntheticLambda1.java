package com.android.server.input.debug;

import android.view.KeyEvent;
import android.view.MotionEvent;

public final /* synthetic */ class FocusEventDebugView$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ FocusEventDebugView f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ FocusEventDebugView$$ExternalSyntheticLambda1(
            FocusEventDebugView focusEventDebugView, KeyEvent keyEvent) {
        this.f$0 = focusEventDebugView;
        this.f$1 = keyEvent;
    }

    public /* synthetic */ FocusEventDebugView$$ExternalSyntheticLambda1(
            FocusEventDebugView focusEventDebugView, MotionEvent motionEvent) {
        this.f$0 = focusEventDebugView;
        this.f$1 = motionEvent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.handleRotaryInput((MotionEvent) this.f$1);
                break;
            default:
                this.f$0.handleKeyEvent((KeyEvent) this.f$1);
                break;
        }
    }
}
