package com.android.systemui.statusbar.domain.interactor;

import com.android.systemui.statusbar.data.repository.KeyguardStatusBarRepository;
import com.android.systemui.statusbar.data.repository.KeyguardStatusBarRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

public final class KeyguardStatusBarInteractor {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isKeyguardUserSwitcherEnabled;

    public KeyguardStatusBarInteractor(KeyguardStatusBarRepository keyguardStatusBarRepository) {
        this.isKeyguardUserSwitcherEnabled = ((KeyguardStatusBarRepositoryImpl) keyguardStatusBarRepository).isKeyguardUserSwitcherEnabled;
    }
}
