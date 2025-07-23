package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.TileSquishinessRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileSquishinessInteractor {
    public final TileSquishinessRepository repository;
    public final ReadonlyStateFlow squishiness;

    public TileSquishinessInteractor(TileSquishinessRepository tileSquishinessRepository) {
        this.repository = tileSquishinessRepository;
        this.squishiness = tileSquishinessRepository.squishiness;
    }
}
