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

/* loaded from: classes2.dex */
final /* synthetic */ class EditModeKt$EditMode$3$1$1 extends FunctionReferenceImpl implements Function2 {
    public EditModeKt$EditMode$3$1$1(Object obj) {
        super(2, obj, EditModeViewModel.class, "addTile", "addTile(Lcom/android/systemui/qs/pipeline/shared/TileSpec;I)V", 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0049  */
    @Override // kotlin.jvm.functions.Function2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke(Object obj, Object obj2) {
        TileSpec tileSpec = (TileSpec) obj;
        int iIntValue = ((Number) obj2).intValue();
        EditModeViewModel editModeViewModel = (EditModeViewModel) this.receiver;
        CurrentTilesInteractor currentTilesInteractor = editModeViewModel.currentTilesInteractor;
        ArrayList arrayList = new ArrayList(currentTilesInteractor.getCurrentTilesSpecs());
        int iIndexOf = arrayList.indexOf(tileSpec);
        boolean z = iIndexOf != -1;
        if (iIndexOf == -1) {
            if (iIntValue >= 0 || iIntValue >= arrayList.size()) {
                arrayList.add(tileSpec);
            } else {
                arrayList.add(iIntValue, tileSpec);
            }
            UiEventLogger uiEventLogger = editModeViewModel.uiEventLogger;
            QSEditEvent qSEditEvent = !z ? QSEditEvent.QS_EDIT_MOVE : QSEditEvent.QS_EDIT_ADD;
            String metricSpec = TileSpecKt.getMetricSpec(tileSpec);
            if (z) {
                CurrentTilesInteractor.Companion.getClass();
                if (iIntValue == CurrentTilesInteractor.Companion.POSITION_AT_END) {
                    iIntValue = arrayList.size() - 1;
                }
            }
            uiEventLogger.logWithPosition(qSEditEvent, 0, metricSpec, iIntValue);
            currentTilesInteractor.setTiles(arrayList);
        } else if (iIndexOf != iIntValue) {
            arrayList.remove(iIndexOf);
            if (iIntValue >= 0) {
                arrayList.add(tileSpec);
                UiEventLogger uiEventLogger2 = editModeViewModel.uiEventLogger;
                if (!z) {
                }
                String metricSpec2 = TileSpecKt.getMetricSpec(tileSpec);
                if (z) {
                }
                uiEventLogger2.logWithPosition(qSEditEvent, 0, metricSpec2, iIntValue);
                currentTilesInteractor.setTiles(arrayList);
            }
        }
        return Unit.INSTANCE;
    }
}
