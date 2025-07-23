package com.android.systemui.common.domain.interactor;

import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PackageChangeInteractor {
    public final PackageChangeRepository packageChangeRepository;
    public final SelectedUserInteractor userInteractor;

    public PackageChangeInteractor(PackageChangeRepository packageChangeRepository, SelectedUserInteractor selectedUserInteractor) {
        this.packageChangeRepository = packageChangeRepository;
        this.userInteractor = selectedUserInteractor;
    }
}
