package com.android.systemui.keyboard.docking.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyboardDockingIndicationInteractor {
    public final Flow onKeyboardConnected;

    public KeyboardDockingIndicationInteractor(KeyboardRepository keyboardRepository) {
        this.onKeyboardConnected = ((KeyboardRepositoryImpl) keyboardRepository).isAnyKeyboardConnected;
    }
}
