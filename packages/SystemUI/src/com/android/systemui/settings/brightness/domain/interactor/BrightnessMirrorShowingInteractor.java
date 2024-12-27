package com.android.systemui.settings.brightness.domain.interactor;

import com.android.systemui.settings.brightness.data.repository.BrightnessMirrorShowingRepository;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BrightnessMirrorShowingInteractor {
    public final BrightnessMirrorShowingRepository brightnessMirrorShowingRepository;
    public final ReadonlyStateFlow isShowing;

    public BrightnessMirrorShowingInteractor(BrightnessMirrorShowingRepository brightnessMirrorShowingRepository) {
        this.brightnessMirrorShowingRepository = brightnessMirrorShowingRepository;
        this.isShowing = brightnessMirrorShowingRepository.isShowing;
    }
}
