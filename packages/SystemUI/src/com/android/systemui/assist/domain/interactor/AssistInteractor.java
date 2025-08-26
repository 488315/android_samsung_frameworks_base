package com.android.systemui.assist.domain.interactor;

import com.android.systemui.assist.data.repository.AssistRepository;

/* loaded from: classes.dex */
public final class AssistInteractor {
    public final AssistRepository repository;

    public AssistInteractor(AssistRepository assistRepository) {
        this.repository = assistRepository;
        assistRepository.getClass();
    }
}
