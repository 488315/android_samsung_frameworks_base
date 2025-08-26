package com.android.systemui.statusbar.policy;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelFactory$Static;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl;
import com.android.systemui.qs.tiles.impl.sensorprivacy.ui.model.SensorPrivacyTileResources;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class PolicyModule_Companion_ProvideMicrophoneToggleTileViewModelFactory implements Provider {
    public final Provider factoryProvider;
    public final Provider mapperProvider;
    public final Provider stateInteractorProvider;
    public final Provider userActionInteractorProvider;

    public PolicyModule_Companion_ProvideMicrophoneToggleTileViewModelFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.factoryProvider = provider;
        this.mapperProvider = provider2;
        this.stateInteractorProvider = provider3;
        this.userActionInteractorProvider = provider4;
    }

    public static QSTileViewModelImpl provideMicrophoneToggleTileViewModel(QSTileViewModelFactory$Static qSTileViewModelFactory$Static, DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass22 anonymousClass22, DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass23 anonymousClass23, DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass24 anonymousClass24) {
        PolicyModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return qSTileViewModelFactory$Static.create(TileSpec.Companion.create("mictoggle"), anonymousClass24.create(1), anonymousClass23.create(1), anonymousClass22.create(SensorPrivacyTileResources.MicrophonePrivacyTileResources.INSTANCE), null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideMicrophoneToggleTileViewModel((QSTileViewModelFactory$Static) this.factoryProvider.get(), (DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass22) this.mapperProvider.get(), (DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass23) this.stateInteractorProvider.get(), (DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass24) this.userActionInteractorProvider.get());
    }
}
