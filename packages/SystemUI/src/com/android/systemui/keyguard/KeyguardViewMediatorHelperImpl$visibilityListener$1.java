package com.android.systemui.keyguard;

import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
