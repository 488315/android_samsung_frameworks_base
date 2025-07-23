package com.android.systemui.keyguard;

import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class KeyguardViewMediatorHelperImpl$setupLocked$1 extends FunctionReferenceImpl implements Function2 {
    public KeyguardViewMediatorHelperImpl$setupLocked$1(Object obj) {
        super(2, obj, KeyguardViewMediatorHelperImpl.class, "onPanelStateChanged", "onPanelStateChanged(II)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int intValue = ((Number) obj).intValue();
        int intValue2 = ((Number) obj2).intValue();
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = (KeyguardViewMediatorHelperImpl) this.receiver;
        keyguardViewMediatorHelperImpl.getClass();
        if (intValue == 0 && intValue2 == 1) {
            ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) keyguardViewMediatorHelperImpl.shadeWindowControllerHelper$delegate.getValue())).resetForceInvisible(false);
        }
        return Unit.INSTANCE;
    }
}
