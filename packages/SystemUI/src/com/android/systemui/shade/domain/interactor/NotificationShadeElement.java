package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.domain.interactor.ShadeExpandedStateInteractor;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;

/* loaded from: classes3.dex */
public final class NotificationShadeElement extends ShadeExpandedStateInteractor.ShadeElement {
    public NotificationShadeElement(ShadeInteractor shadeInteractor, CoroutineContext coroutineContext) {
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
