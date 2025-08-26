package com.android.systemui.qs.pipeline.dagger;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable;
import com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddableList;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import dagger.internal.Provider;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;

/* loaded from: classes2.dex */
public final class BaseAutoAddableModule_Companion_ProvidesA11yShortcutAutoAddableFactory implements Provider {
    public final Provider a11yShortcutAutoAddableFactoryProvider;

    public BaseAutoAddableModule_Companion_ProvidesA11yShortcutAutoAddableFactory(Provider provider) {
        this.a11yShortcutAutoAddableFactoryProvider = provider;
    }

    public static Set providesA11yShortcutAutoAddable(DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass158 anonymousClass158) {
        BaseAutoAddableModule.Companion.getClass();
        A11yShortcutAutoAddableList.INSTANCE.getClass();
        TileSpec.Companion.getClass();
        Set set = ArraysKt___ArraysKt.toSet(new A11yShortcutAutoAddable[]{anonymousClass158.create(TileSpec.Companion.create("ColorCorrection"), AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME), anonymousClass158.create(TileSpec.Companion.create("ColorInversion"), AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME), anonymousClass158.create(TileSpec.Companion.create("onehanded"), AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME), anonymousClass158.create(TileSpec.Companion.create("ReduceBrightColors"), AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME), anonymousClass158.create(TileSpec.Companion.create("hearing_devices"), AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME), anonymousClass158.create(TileSpec.Companion.create("HighContrastFont"), AccessibilityShortcutController.HIGH_CONTRAST_FONTS_COMPONENT_NAME), anonymousClass158.create(TileSpec.Companion.create("ColorLens"), AccessibilityShortcutController.COLOR_LENS_COMPONENT_NAME)});
        set.getClass();
        return set;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesA11yShortcutAutoAddable((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass158) this.a11yShortcutAutoAddableFactoryProvider.get());
    }
}
