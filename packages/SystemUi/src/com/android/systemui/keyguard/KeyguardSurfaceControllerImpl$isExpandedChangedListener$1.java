package com.android.systemui.keyguard;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class KeyguardSurfaceControllerImpl$isExpandedChangedListener$1 extends FunctionReferenceImpl implements Function1 {
    public KeyguardSurfaceControllerImpl$isExpandedChangedListener$1(Object obj) {
        super(1, obj, KeyguardSurfaceControllerImpl.class, "internalRestoreKeyguardSurfaceIfVisible", "internalRestoreKeyguardSurfaceIfVisible(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((KeyguardSurfaceControllerImpl) this.receiver).internalRestoreKeyguardSurfaceIfVisible(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }
}
