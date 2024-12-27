package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.data.repository.KeyguardClockRepositoryImpl$special$$inlined$map$3;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardPreviewClockViewModel {
    public final KeyguardPreviewClockViewModel$special$$inlined$map$1 isLargeClockVisible;
    public final KeyguardPreviewClockViewModel$special$$inlined$map$2 isSmallClockVisible;
    public final KeyguardClockRepositoryImpl$special$$inlined$map$3 previewClock;
    public final ReadonlyStateFlow selectedClockSize;

    public KeyguardPreviewClockViewModel(KeyguardClockInteractor keyguardClockInteractor) {
        ReadonlyStateFlow readonlyStateFlow = keyguardClockInteractor.selectedClockSize;
        this.isLargeClockVisible = new KeyguardPreviewClockViewModel$special$$inlined$map$1(readonlyStateFlow);
        this.isSmallClockVisible = new KeyguardPreviewClockViewModel$special$$inlined$map$2(readonlyStateFlow);
        KeyguardClockRepositoryImpl$special$$inlined$map$3 keyguardClockRepositoryImpl$special$$inlined$map$3 = keyguardClockInteractor.previewClock;
    }
}
