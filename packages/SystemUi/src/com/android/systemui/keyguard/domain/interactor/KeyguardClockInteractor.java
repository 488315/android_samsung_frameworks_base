package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardClockRepository;
import com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$map$1;
import com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$mapNotNull$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardClockInteractor {
    public final KeyguardClockRepository$special$$inlined$mapNotNull$1 currentClockId;
    public final KeyguardClockRepository$special$$inlined$map$1 selectedClockSize;

    public KeyguardClockInteractor(KeyguardClockRepository keyguardClockRepository) {
        this.selectedClockSize = keyguardClockRepository.selectedClockSize;
        this.currentClockId = keyguardClockRepository.currentClockId;
    }
}
