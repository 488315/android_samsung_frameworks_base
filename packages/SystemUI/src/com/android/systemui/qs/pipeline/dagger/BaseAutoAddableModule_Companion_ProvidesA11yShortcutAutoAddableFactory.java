package com.android.systemui.qs.pipeline.dagger;

import android.view.accessibility.Flags;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddableList;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Set;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt__SetsKt;

public final class BaseAutoAddableModule_Companion_ProvidesA11yShortcutAutoAddableFactory implements Provider {
    public final javax.inject.Provider a11yShortcutAutoAddableFactoryProvider;

    public BaseAutoAddableModule_Companion_ProvidesA11yShortcutAutoAddableFactory(javax.inject.Provider provider) {
        this.a11yShortcutAutoAddableFactoryProvider = provider;
    }

    public static Set providesA11yShortcutAutoAddable(DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass49 anonymousClass49) {
        Set set;
        BaseAutoAddableModule.Companion.getClass();
        A11yShortcutAutoAddableList.INSTANCE.getClass();
        if (Flags.a11yQsShortcut()) {
            TileSpec.Companion.getClass();
            set = SetsKt__SetsKt.setOf(anonymousClass49.create(TileSpec.Companion.create("ColorCorrection"), AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME), anonymousClass49.create(TileSpec.Companion.create("ColorInversion"), AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME), anonymousClass49.create(TileSpec.Companion.create("onehanded"), AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME), anonymousClass49.create(TileSpec.Companion.create("ReduceBrightColors"), AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME), anonymousClass49.create(TileSpec.Companion.create("hearing_devices"), AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME));
        } else {
            set = EmptySet.INSTANCE;
        }
        Preconditions.checkNotNullFromProvides(set);
        return set;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesA11yShortcutAutoAddable((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass49) this.a11yShortcutAutoAddableFactoryProvider.get());
    }
}
