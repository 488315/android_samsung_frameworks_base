package com.android.systemui.volume.domain.interactor;

import com.android.systemui.volume.data.repository.VolumeDialogRepository;

public final class VolumeDialogInteractor {
    public final VolumeDialogRepository repository;

    public VolumeDialogInteractor(VolumeDialogRepository volumeDialogRepository) {
        this.repository = volumeDialogRepository;
    }
}
