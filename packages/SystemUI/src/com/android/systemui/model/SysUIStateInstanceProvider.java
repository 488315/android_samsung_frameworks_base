package com.android.systemui.model;

import com.android.app.displaylib.PerDisplayInstanceProviderWithTeardown;
import com.android.systemui.model.SysUIStateOverride;
import com.android.systemui.model.SysUiStateImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SysUIStateInstanceProvider implements PerDisplayInstanceProviderWithTeardown {
    public final SysUiStateImpl.Factory factory;
    public final SysUIStateOverride.Factory overrideFactory;

    public SysUIStateInstanceProvider(SysUiStateImpl.Factory factory, SysUIStateOverride.Factory factory2) {
        this.factory = factory;
        this.overrideFactory = factory2;
    }

    @Override // com.android.app.displaylib.PerDisplayInstanceProvider
    public final Object createInstance(int i) {
        SysUiStateImpl create = i == 0 ? this.factory.create(i) : this.overrideFactory.create(i);
        create.start();
        return create;
    }

    @Override // com.android.app.displaylib.PerDisplayInstanceProviderWithTeardown
    public final void destroyInstance(Object obj) {
        ((SysUiState) obj).destroy();
    }
}
