package com.android.systemui.keyevent.domain.interactor;

import com.android.systemui.keyevent.data.repository.KeyEventRepository;
import com.android.systemui.keyevent.data.repository.KeyEventRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyEventInteractor {
    public final Flow isPowerButtonDown;

    public KeyEventInteractor(KeyEventRepository keyEventRepository) {
        this.isPowerButtonDown = ((KeyEventRepositoryImpl) keyEventRepository).isPowerButtonDown;
    }
}
