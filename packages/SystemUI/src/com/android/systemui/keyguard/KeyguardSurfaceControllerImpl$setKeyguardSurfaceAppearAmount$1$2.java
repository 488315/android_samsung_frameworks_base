package com.android.systemui.keyguard;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class KeyguardSurfaceControllerImpl$setKeyguardSurfaceAppearAmount$1$2 extends FunctionReferenceImpl implements Function2 {
    public KeyguardSurfaceControllerImpl$setKeyguardSurfaceAppearAmount$1$2(Object obj) {
        super(2, obj, KeyguardSurfaceControllerImpl.class, "onPanelStateChanged", "onPanelStateChanged(II)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        KeyguardSurfaceControllerImpl.access$onPanelStateChanged((KeyguardSurfaceControllerImpl) this.receiver, ((Number) obj).intValue(), ((Number) obj2).intValue());
        return Unit.INSTANCE;
    }
}
