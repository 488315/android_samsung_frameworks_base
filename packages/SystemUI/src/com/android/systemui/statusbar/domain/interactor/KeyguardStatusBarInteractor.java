package com.android.systemui.statusbar.domain.interactor;

import com.android.systemui.statusbar.data.repository.KeyguardStatusBarRepository;
import com.android.systemui.statusbar.data.repository.KeyguardStatusBarRepositoryImpl;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardStatusBarInteractor {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isKeyguardUserSwitcherEnabled;

    public KeyguardStatusBarInteractor(KeyguardStatusBarRepository keyguardStatusBarRepository) {
        this.isKeyguardUserSwitcherEnabled = ((KeyguardStatusBarRepositoryImpl) keyguardStatusBarRepository).isKeyguardUserSwitcherEnabled;
    }
}
