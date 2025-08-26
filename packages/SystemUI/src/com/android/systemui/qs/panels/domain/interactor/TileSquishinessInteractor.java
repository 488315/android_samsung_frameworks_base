package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.TileSquishinessRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class TileSquishinessInteractor {
    public final TileSquishinessRepository repository;
    public final ReadonlyStateFlow squishiness;

    public TileSquishinessInteractor(TileSquishinessRepository tileSquishinessRepository) {
        this.repository = tileSquishinessRepository;
        this.squishiness = tileSquishinessRepository.squishiness;
    }
}
