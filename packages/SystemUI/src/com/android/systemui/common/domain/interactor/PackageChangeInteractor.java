package com.android.systemui.common.domain.interactor;

import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

public final class PackageChangeInteractor {
    public final PackageChangeRepository packageChangeRepository;
    public final SelectedUserInteractor userInteractor;

    public PackageChangeInteractor(PackageChangeRepository packageChangeRepository, SelectedUserInteractor selectedUserInteractor) {
        this.packageChangeRepository = packageChangeRepository;
        this.userInteractor = selectedUserInteractor;
    }
}
