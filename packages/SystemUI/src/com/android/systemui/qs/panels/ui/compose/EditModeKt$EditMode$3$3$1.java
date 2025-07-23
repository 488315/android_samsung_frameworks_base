package com.android.systemui.qs.panels.ui.compose;

import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class EditModeKt$EditMode$3$3$1 extends FunctionReferenceImpl implements Function1 {
    public EditModeKt$EditMode$3$3$1(Object obj) {
        super(1, obj, EditModeViewModel.class, "setTiles", "setTiles(Ljava/util/List;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((EditModeViewModel) this.receiver).setTiles((List) obj);
        return Unit.INSTANCE;
    }
}
