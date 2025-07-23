package com.android.systemui.qs.pipeline.dagger;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddable;
import com.android.systemui.qs.pipeline.domain.autoaddable.A11yShortcutAutoAddableList;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import dagger.internal.Provider;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BaseAutoAddableModule_Companion_ProvidesA11yShortcutAutoAddableFactory implements Provider {
    public final Provider a11yShortcutAutoAddableFactoryProvider;

    public BaseAutoAddableModule_Companion_ProvidesA11yShortcutAutoAddableFactory(Provider provider) {
        this.a11yShortcutAutoAddableFactoryProvider = provider;
    }

    public static Set providesA11yShortcutAutoAddable(DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass157 anonymousClass157) {
        BaseAutoAddableModule.Companion.getClass();
        A11yShortcutAutoAddableList.INSTANCE.getClass();
        TileSpec.Companion.getClass();
        Set set = ArraysKt___ArraysKt.toSet(new A11yShortcutAutoAddable[]{anonymousClass157.create(TileSpec.Companion.create("ColorCorrection"), AccessibilityShortcutController.DALTONIZER_COMPONENT_NAME), anonymousClass157.create(TileSpec.Companion.create("ColorInversion"), AccessibilityShortcutController.COLOR_INVERSION_COMPONENT_NAME), anonymousClass157.create(TileSpec.Companion.create("onehanded"), AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME), anonymousClass157.create(TileSpec.Companion.create("ReduceBrightColors"), AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_COMPONENT_NAME), anonymousClass157.create(TileSpec.Companion.create("hearing_devices"), AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME), anonymousClass157.create(TileSpec.Companion.create("HighContrastFont"), AccessibilityShortcutController.HIGH_CONTRAST_FONTS_COMPONENT_NAME), anonymousClass157.create(TileSpec.Companion.create("ColorLens"), AccessibilityShortcutController.COLOR_LENS_COMPONENT_NAME)});
        set.getClass();
        return set;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesA11yShortcutAutoAddable((DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.SwitchingProvider.AnonymousClass157) this.a11yShortcutAutoAddableFactoryProvider.get());
    }
}
