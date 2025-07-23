package com.android.systemui.volume.panel.component.anc.ui.composable;

import com.android.systemui.volume.panel.component.anc.ui.viewmodel.AncViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class AncButtonComponent$Content$2$1$1$1 extends FunctionReferenceImpl implements Function1 {
    public AncButtonComponent$Content$2$1$1$1(Object obj) {
        super(1, obj, AncViewModel.class, "onButtonSliceWidthChanged", "onButtonSliceWidthChanged(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((AncViewModel) this.receiver).interactor.buttonSliceWidth.updateState(null, Integer.valueOf(((Number) obj).intValue()));
        return Unit.INSTANCE;
    }
}
