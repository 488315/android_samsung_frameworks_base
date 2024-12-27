package com.android.systemui.common.domain.interactor;

import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PackageChangeInteractor {
    public final PackageChangeRepository packageChangeRepository;
    public final SelectedUserInteractor userInteractor;

    public PackageChangeInteractor(PackageChangeRepository packageChangeRepository, SelectedUserInteractor selectedUserInteractor) {
        this.packageChangeRepository = packageChangeRepository;
        this.userInteractor = selectedUserInteractor;
    }
}
