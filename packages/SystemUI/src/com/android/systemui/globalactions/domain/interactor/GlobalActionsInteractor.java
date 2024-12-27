package com.android.systemui.globalactions.domain.interactor;

import com.android.systemui.globalactions.data.repository.GlobalActionsRepository;

public final class GlobalActionsInteractor {
    public final GlobalActionsRepository repository;

    public GlobalActionsInteractor(GlobalActionsRepository globalActionsRepository) {
        this.repository = globalActionsRepository;
    }
}
