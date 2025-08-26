package com.android.systemui.qs.bar.domain.interactor;

import com.android.systemui.qs.bar.repository.ColoredBackgroundRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class ColoredBackgroundInteractor {
    public final ReadonlyStateFlow backgroundColor;
    public final ColoredBackgroundRepository coloredBackgroundRepository;

    public ColoredBackgroundInteractor(ColoredBackgroundRepository coloredBackgroundRepository) {
        this.coloredBackgroundRepository = coloredBackgroundRepository;
        this.backgroundColor = coloredBackgroundRepository.currentExtractedBackgroundColor;
    }
}
