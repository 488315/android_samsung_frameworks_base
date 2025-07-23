package com.android.systemui.controls.ui;

import android.util.Log;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
