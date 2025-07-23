package com.android.systemui.qs.bar.domain.interactor;

import com.android.systemui.qs.bar.repository.ColoredBackgroundRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ColoredBackgroundInteractor {
    public final ReadonlyStateFlow backgroundColor;
    public final ColoredBackgroundRepository coloredBackgroundRepository;

    public ColoredBackgroundInteractor(ColoredBackgroundRepository coloredBackgroundRepository) {
        this.coloredBackgroundRepository = coloredBackgroundRepository;
        this.backgroundColor = coloredBackgroundRepository.currentExtractedBackgroundColor;
    }
}
