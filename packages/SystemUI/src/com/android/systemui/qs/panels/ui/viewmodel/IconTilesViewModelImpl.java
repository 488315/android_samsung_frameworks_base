package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.panels.domain.interactor.IconTilesInteractor;
import com.android.systemui.qs.panels.domain.interactor.QSPreferencesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.TileSpecKt;
import java.util.ArrayList;
import java.util.Set;
import kotlin.collections.SetsKt___SetsKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class IconTilesViewModelImpl implements IconTilesViewModel {
    public final IconTilesInteractor interactor;
    public final ReadonlyStateFlow largeTiles;
    public final ReadonlyStateFlow largeTilesSpan;

    public IconTilesViewModelImpl(IconTilesInteractor iconTilesInteractor) {
        this.interactor = iconTilesInteractor;
        this.largeTiles = iconTilesInteractor.largeTilesSpecs;
        this.largeTilesSpan = iconTilesInteractor.largeTilesSpan;
    }

    @Override // com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel
    public final StateFlow getLargeTiles() {
        return this.largeTiles;
    }

    @Override // com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel
    public final StateFlow getLargeTilesSpan() {
        return this.largeTilesSpan;
    }

    @Override // com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel
    public final boolean isIconTile(TileSpec tileSpec) {
        return !((Set) this.interactor.largeTilesSpecs.$$delegate_0.getValue()).contains(tileSpec);
    }

    @Override // com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel
    public final void resize(TileSpec tileSpec, boolean z) {
        IconTilesInteractor iconTilesInteractor = this.interactor;
        if (((ArrayList) iconTilesInteractor.currentTilesInteractor.getCurrentTilesSpecs()).contains(tileSpec)) {
            ReadonlyStateFlow readonlyStateFlow = iconTilesInteractor.largeTilesSpecs;
            boolean zContains = ((Set) readonlyStateFlow.$$delegate_0.getValue()).contains(tileSpec);
            StateFlow stateFlow = readonlyStateFlow.$$delegate_0;
            QSPreferencesInteractor qSPreferencesInteractor = iconTilesInteractor.preferencesInteractor;
            if (z && zContains) {
                qSPreferencesInteractor.setLargeTilesSpecs(SetsKt___SetsKt.minus((Set) stateFlow.getValue(), tileSpec));
                iconTilesInteractor.uiEventLogger.log(QSEditEvent.QS_EDIT_RESIZE_SMALL, 0, TileSpecKt.getMetricSpec(tileSpec));
            } else {
                if (z || zContains) {
                    return;
                }
                qSPreferencesInteractor.setLargeTilesSpecs(SetsKt___SetsKt.plus((Set) stateFlow.getValue(), tileSpec));
                iconTilesInteractor.uiEventLogger.log(QSEditEvent.QS_EDIT_RESIZE_LARGE, 0, TileSpecKt.getMetricSpec(tileSpec));
            }
        }
    }
}
