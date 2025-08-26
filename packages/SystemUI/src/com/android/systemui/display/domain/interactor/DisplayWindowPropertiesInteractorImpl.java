package com.android.systemui.display.domain.interactor;

import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;

/* loaded from: classes2.dex */
public final class DisplayWindowPropertiesInteractorImpl implements DisplayWindowPropertiesInteractor {
    public final DisplayWindowPropertiesRepository repo;

    public DisplayWindowPropertiesInteractorImpl(DisplayWindowPropertiesRepository displayWindowPropertiesRepository) {
        this.repo = displayWindowPropertiesRepository;
    }
}
