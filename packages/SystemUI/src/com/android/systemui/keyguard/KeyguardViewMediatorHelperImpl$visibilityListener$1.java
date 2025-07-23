package com.android.systemui.keyguard;

import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class KeyguardViewMediatorHelperImpl$visibilityListener$1 extends FunctionReferenceImpl implements Function1 {
    public KeyguardViewMediatorHelperImpl$visibilityListener$1(Object obj) {
        super(1, obj, KeyguardViewMediatorHelperImpl.class, "onKeyguardVisibilityChanged", "onKeyguardVisibilityChanged(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
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
