package com.android.systemui.keyguard;

import com.android.systemui.shade.SecNotificationShadeWindowControllerHelper;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class KeyguardViewMediatorHelperImpl$setupLocked$1 extends FunctionReferenceImpl implements Function2 {
    public KeyguardViewMediatorHelperImpl$setupLocked$1(Object obj) {
        super(2, obj, KeyguardViewMediatorHelperImpl.class, "onPanelStateChanged", "onPanelStateChanged(II)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int iIntValue = ((Number) obj).intValue();
        int iIntValue2 = ((Number) obj2).intValue();
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = (KeyguardViewMediatorHelperImpl) this.receiver;
        keyguardViewMediatorHelperImpl.getClass();
        if (iIntValue == 0 && iIntValue2 == 1) {
            ((SecNotificationShadeWindowControllerHelperImpl) ((SecNotificationShadeWindowControllerHelper) keyguardViewMediatorHelperImpl.shadeWindowControllerHelper$delegate.getValue())).resetForceInvisible(false);
        }
        return Unit.INSTANCE;
    }
}
