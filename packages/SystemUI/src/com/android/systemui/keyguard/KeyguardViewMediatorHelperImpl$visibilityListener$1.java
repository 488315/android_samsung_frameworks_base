package com.android.systemui.keyguard;

import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class KeyguardViewMediatorHelperImpl$visibilityListener$1 extends FunctionReferenceImpl implements Function1 {
    public KeyguardViewMediatorHelperImpl$visibilityListener$1(Object obj) {
        super(1, obj, KeyguardViewMediatorHelperImpl.class, "onKeyguardVisibilityChanged", "onKeyguardVisibilityChanged(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        int intValue = ((Number) obj).intValue();
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = (KeyguardViewMediatorHelperImpl) this.receiver;
        if (intValue != 0) {
            ((ArrayList) keyguardViewMediatorHelperImpl.keyguardVisibilityMonitor.visibilityChangedListeners).remove(new KeyguardViewMediatorHelperImplKt$sam$java_util_function_IntConsumer$0((Function1) keyguardViewMediatorHelperImpl.visibilityListener));
            keyguardViewMediatorHelperImpl.notifyDrawn();
        } else {
            keyguardViewMediatorHelperImpl.getClass();
        }
        return Unit.INSTANCE;
    }
}
