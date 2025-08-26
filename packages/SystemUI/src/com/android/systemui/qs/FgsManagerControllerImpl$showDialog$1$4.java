package com.android.systemui.qs;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes2.dex */
final /* synthetic */ class FgsManagerControllerImpl$showDialog$1$4 extends AdaptedFunctionReference implements Function0 {
    public FgsManagerControllerImpl$showDialog$1$4(Object obj) {
        super(0, obj, FgsManagerControllerImpl.class, "updateAppItemsLocked", "updateAppItemsLocked(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        FgsManagerControllerImpl fgsManagerControllerImpl = (FgsManagerControllerImpl) this.receiver;
        int i = FgsManagerControllerImpl.$r8$clinit;
        fgsManagerControllerImpl.updateAppItemsLocked(false);
        return Unit.INSTANCE;
    }
}
