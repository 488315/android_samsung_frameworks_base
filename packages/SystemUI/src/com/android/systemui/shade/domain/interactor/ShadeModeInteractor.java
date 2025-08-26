package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.shared.model.ShadeMode;

/* loaded from: classes3.dex */
public interface ShadeModeInteractor {
    default boolean isDualShade() {
        return ((ShadeModeInteractorImpl) this).shadeMode.$$delegate_0.getValue() instanceof ShadeMode.Dual;
    }

    default boolean isSplitShade() {
        return ((ShadeModeInteractorImpl) this).shadeMode.$$delegate_0.getValue() instanceof ShadeMode.Split;
    }
}
