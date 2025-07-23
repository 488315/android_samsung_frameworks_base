package com.android.systemui.qs.panels.ui.compose;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.TileSpecKt;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class EditModeKt$EditMode$3$1$1 extends FunctionReferenceImpl implements Function2 {
    public EditModeKt$EditMode$3$1$1(Object obj) {
        super(2, obj, EditModeViewModel.class, "addTile", "addTile(Lcom/android/systemui/qs/pipeline/shared/TileSpec;I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        TileSpec tileSpec = (TileSpec) obj;
        int intValue = ((Number) obj2).intValue();
        EditModeViewModel editModeViewModel = (EditModeViewModel) this.receiver;
        CurrentTilesInteractor currentTilesInteractor = editModeViewModel.currentTilesInteractor;
        ArrayList arrayList = new ArrayList(currentTilesInteractor.getCurrentTilesSpecs());
        int indexOf = arrayList.indexOf(tileSpec);
        boolean z = indexOf != -1;
        if (indexOf != -1) {
            if (indexOf != intValue) {
                arrayList.remove(indexOf);
            }
            return Unit.INSTANCE;
        }
        if (intValue < 0 || intValue >= arrayList.size()) {
            arrayList.add(tileSpec);
        } else {
            arrayList.add(intValue, tileSpec);
        }
        UiEventLogger uiEventLogger = editModeViewModel.uiEventLogger;
        QSEditEvent qSEditEvent = z ? QSEditEvent.QS_EDIT_MOVE : QSEditEvent.QS_EDIT_ADD;
        String metricSpec = TileSpecKt.getMetricSpec(tileSpec);
        if (z) {
            CurrentTilesInteractor.Companion.getClass();
            if (intValue == CurrentTilesInteractor.Companion.POSITION_AT_END) {
                intValue = arrayList.size() - 1;
            }
        }
        uiEventLogger.logWithPosition(qSEditEvent, 0, metricSpec, intValue);
        currentTilesInteractor.setTiles(arrayList);
        return Unit.INSTANCE;
    }
}
