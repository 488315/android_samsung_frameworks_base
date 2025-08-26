package com.android.systemui.display.data.repository;

import com.android.app.displaylib.PerDisplayInstanceProvider;
import com.android.app.displaylib.PerDisplayRepository;
import com.android.systemui.display.dagger.SystemUIDisplaySubcomponent;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class DisplayScopeRepositoryInstanceProvider implements PerDisplayInstanceProvider {
    public final CoroutineScope backgroundApplicationScope;
    public final PerDisplayRepository displayComponentRepository;

    public DisplayScopeRepositoryInstanceProvider(CoroutineScope coroutineScope, PerDisplayRepository perDisplayRepository) {
        this.backgroundApplicationScope = coroutineScope;
        this.displayComponentRepository = perDisplayRepository;
    }

    @Override // com.android.app.displaylib.PerDisplayInstanceProvider
    public final Object createInstance(int i) {
        if (i == 0) {
            return this.backgroundApplicationScope;
        }
        SystemUIDisplaySubcomponent systemUIDisplaySubcomponent = (SystemUIDisplaySubcomponent) this.displayComponentRepository.get(i);
        if (systemUIDisplaySubcomponent != null) {
            return systemUIDisplaySubcomponent.getDisplayCoroutineScope();
        }
        return null;
    }
}
