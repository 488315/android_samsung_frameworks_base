package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.domain.interactor.ShadeExpandedStateInteractor;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class QSShadeElement extends ShadeExpandedStateInteractor.ShadeElement {
    public QSShadeElement(ShadeInteractor shadeInteractor, CoroutineContext coroutineContext) {
        super(null);
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeExpandedStateInteractor.ShadeElement
    public final Unit collapse() {
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeExpandedStateInteractor.ShadeElement
    public final Unit expand() {
        return Unit.INSTANCE;
    }
}
