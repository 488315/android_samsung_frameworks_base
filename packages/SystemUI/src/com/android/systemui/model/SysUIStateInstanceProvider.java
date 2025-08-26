package com.android.systemui.model;

import com.android.app.displaylib.PerDisplayInstanceProviderWithTeardown;
import com.android.systemui.model.SysUIStateOverride;
import com.android.systemui.model.SysUiStateImpl;

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
        SysUiStateImpl sysUiStateImplCreate = i == 0 ? this.factory.create(i) : this.overrideFactory.create(i);
        sysUiStateImplCreate.start();
        return sysUiStateImplCreate;
    }

    @Override // com.android.app.displaylib.PerDisplayInstanceProviderWithTeardown
    public final void destroyInstance(Object obj) {
        ((SysUiState) obj).destroy();
    }
}
