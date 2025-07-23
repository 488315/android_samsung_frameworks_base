package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.shared.model.ShadeMode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ShadeModeInteractor {
    default boolean isDualShade() {
        return ((ShadeModeInteractorImpl) this).shadeMode.$$delegate_0.getValue() instanceof ShadeMode.Dual;
    }

    default boolean isSplitShade() {
        return ((ShadeModeInteractorImpl) this).shadeMode.$$delegate_0.getValue() instanceof ShadeMode.Split;
    }
}
