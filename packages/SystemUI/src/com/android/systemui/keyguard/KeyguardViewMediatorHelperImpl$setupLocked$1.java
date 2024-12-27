package com.android.systemui.keyguard;

import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            ((SecNotificationShadeWindowControllerHelperImpl) keyguardViewMediatorHelperImpl.getShadeWindowControllerHelper()).resetForceInvisible(false);
        }
        return Unit.INSTANCE;
    }
}
