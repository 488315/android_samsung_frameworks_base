package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;

/* loaded from: classes2.dex */
public final class DetailsViewModel {
    public final MutableState _activeTileDetails = SnapshotStateKt.mutableStateOf$default(null);

    public DetailsViewModel(CurrentTilesInteractor currentTilesInteractor, ShadeModeInteractor shadeModeInteractor) {
    }
}
