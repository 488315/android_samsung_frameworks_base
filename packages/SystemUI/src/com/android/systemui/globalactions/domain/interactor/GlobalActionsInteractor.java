package com.android.systemui.globalactions.domain.interactor;

import com.android.systemui.globalactions.data.repository.GlobalActionsRepository;

/* loaded from: classes2.dex */
public final class GlobalActionsInteractor {
    public final GlobalActionsRepository repository;

    public GlobalActionsInteractor(GlobalActionsRepository globalActionsRepository) {
        this.repository = globalActionsRepository;
        globalActionsRepository.getClass();
    }
}
