package com.android.systemui.statusbar.domain.interactor;

import com.android.systemui.statusbar.data.repository.RemoteInputRepository;
import com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

public final class RemoteInputInteractor {
    public RemoteInputInteractor(RemoteInputRepository remoteInputRepository) {
        Flow flow = ((RemoteInputRepositoryImpl) remoteInputRepository).isRemoteInputActive;
    }
}
