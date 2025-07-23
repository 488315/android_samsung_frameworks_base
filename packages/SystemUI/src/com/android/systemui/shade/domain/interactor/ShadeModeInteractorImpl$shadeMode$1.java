package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class ShadeModeInteractorImpl$shadeMode$1 extends AdaptedFunctionReference implements Function3 {
    public ShadeModeInteractorImpl$shadeMode$1(Object obj) {
        super(3, obj, ShadeModeInteractorImpl.class, "determineShadeMode", "determineShadeMode(ZZ)Lcom/android/systemui/shade/shared/model/ShadeMode;", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) this.receiver;
        int i = ShadeModeInteractorImpl.$r8$clinit;
        shadeModeInteractorImpl.getClass();
        return booleanValue ? ShadeMode.Dual.INSTANCE : booleanValue2 ? ShadeMode.Split.INSTANCE : ShadeMode.Single.INSTANCE;
    }
}
