package com.android.systemui.qs.panels.ui.compose;

import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class EditModeKt$EditMode$3$4$1 extends FunctionReferenceImpl implements Function0 {
    public EditModeKt$EditMode$3$4$1(Object obj) {
        super(0, obj, EditModeViewModel.class, "stopEditing", "stopEditing()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((EditModeViewModel) this.receiver).stopEditing();
        return Unit.INSTANCE;
    }
}
