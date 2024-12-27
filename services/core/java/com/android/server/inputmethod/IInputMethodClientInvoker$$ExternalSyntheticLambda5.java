package com.android.server.inputmethod;

import com.android.internal.inputmethod.InputBindResult;

public final /* synthetic */ class IInputMethodClientInvoker$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IInputMethodClientInvoker f$0;
    public final /* synthetic */ InputBindResult f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ IInputMethodClientInvoker$$ExternalSyntheticLambda5(
            IInputMethodClientInvoker iInputMethodClientInvoker,
            InputBindResult inputBindResult,
            int i,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = iInputMethodClientInvoker;
        this.f$1 = inputBindResult;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onStartInputResultInternal(this.f$1, this.f$2);
                break;
            default:
                this.f$0.onBindAccessibilityServiceInternal(this.f$1, this.f$2);
                break;
        }
    }
}
