package com.android.systemui.controls.ui;

import android.util.Log;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class CustomControlsUiControllerImpl$show$2 extends FunctionReferenceImpl implements Function2 {
    public CustomControlsUiControllerImpl$show$2(Object obj) {
        super(2, obj, CustomControlsUiControllerImpl.class, "showSeedingView", "showSeedingView(Ljava/util/List;Ljava/util/List;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        CustomControlsUiControllerImpl customControlsUiControllerImpl = (CustomControlsUiControllerImpl) this.receiver;
        int i = CustomControlsUiControllerImpl.$r8$clinit;
        customControlsUiControllerImpl.getClass();
        Log.d("CustomControlsUiControllerImpl", "showSeedingView()");
        return Unit.INSTANCE;
    }
}
