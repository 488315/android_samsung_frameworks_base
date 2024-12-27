package com.android.systemui.controls.ui;

import android.util.Log;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class SecControlsUiControllerImpl$show$2 extends FunctionReferenceImpl implements Function2 {
    public SecControlsUiControllerImpl$show$2(Object obj) {
        super(2, obj, SecControlsUiControllerImpl.class, "showSeedingView", "showSeedingView(Ljava/util/List;Ljava/util/List;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) this.receiver;
        int i = SecControlsUiControllerImpl.$r8$clinit;
        secControlsUiControllerImpl.getClass();
        Log.d("SecControlsUiControllerImpl", "showSeedingView");
        return Unit.INSTANCE;
    }
}
