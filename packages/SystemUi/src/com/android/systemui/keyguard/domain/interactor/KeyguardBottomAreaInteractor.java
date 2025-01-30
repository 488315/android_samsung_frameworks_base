package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardBottomAreaInteractor {
    public final StateFlow alpha;
    public final StateFlow animateDozingTransitions;
    public final StateFlow clockPosition;
    public final KeyguardRepository repository;

    public KeyguardBottomAreaInteractor(KeyguardRepository keyguardRepository) {
        this.repository = keyguardRepository;
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        this.animateDozingTransitions = keyguardRepositoryImpl.animateBottomAreaDozingTransitions;
        this.alpha = keyguardRepositoryImpl.bottomAreaAlpha;
        this.clockPosition = keyguardRepositoryImpl.clockPosition;
    }
}
