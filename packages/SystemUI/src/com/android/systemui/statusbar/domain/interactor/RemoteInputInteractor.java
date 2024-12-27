package com.android.systemui.statusbar.domain.interactor;

import com.android.systemui.statusbar.data.repository.RemoteInputRepository;
import com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RemoteInputInteractor {
    public RemoteInputInteractor(RemoteInputRepository remoteInputRepository) {
        Flow flow = ((RemoteInputRepositoryImpl) remoteInputRepository).isRemoteInputActive;
    }
}
