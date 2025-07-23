package com.android.systemui.qs.panels.ui.compose;

import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.TileSpecKt;
import java.util.Collections;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class EditModeKt$EditMode$3$2$1 extends FunctionReferenceImpl implements Function1 {
    public EditModeKt$EditMode$3$2$1(Object obj) {
        super(1, obj, EditModeViewModel.class, "removeTile", "removeTile(Lcom/android/systemui/qs/pipeline/shared/TileSpec;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        TileSpec tileSpec = (TileSpec) obj;
        EditModeViewModel editModeViewModel = (EditModeViewModel) this.receiver;
        editModeViewModel.uiEventLogger.log(QSEditEvent.QS_EDIT_REMOVE, 0, TileSpecKt.getMetricSpec(tileSpec));
        editModeViewModel.currentTilesInteractor.removeTiles(Collections.singletonList(tileSpec));
        return Unit.INSTANCE;
    }
}
