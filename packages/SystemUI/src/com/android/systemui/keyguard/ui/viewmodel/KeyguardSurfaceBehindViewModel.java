package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardSurfaceBehindInteractor;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardSurfaceBehindViewModel {
    public final Flow surfaceBehindViewParams;

    public KeyguardSurfaceBehindViewModel(KeyguardSurfaceBehindInteractor keyguardSurfaceBehindInteractor) {
        Flow flow = keyguardSurfaceBehindInteractor.viewParams;
    }
}
