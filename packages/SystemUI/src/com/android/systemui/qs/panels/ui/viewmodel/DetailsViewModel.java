package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DetailsViewModel {
    public final MutableState _activeTileDetails = SnapshotStateKt.mutableStateOf$default(null);

    public DetailsViewModel(CurrentTilesInteractor currentTilesInteractor, ShadeModeInteractor shadeModeInteractor) {
    }
}
