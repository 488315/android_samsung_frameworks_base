package com.android.systemui.accessibility.data.repository;

import android.util.SparseArray;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

public final class AccessibilityQsShortcutsRepositoryImpl implements AccessibilityQsShortcutsRepository {
    public static final Map TILE_SPEC_TO_COMPONENT_MAPPING = null;
    public final CoroutineDispatcher backgroundDispatcher;
    public final AccessibilityManager manager;
    public final SparseArray userA11yQsShortcutsRepositories;
    public final UserA11yQsShortcutsRepository.Factory userA11yQsShortcutsRepositoryFactory;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        MapsKt__MapsKt.mapOf(new Pair("ColorCorrection", AccessibilityShortcutController.DALTONIZER_TILE_COMPONENT_NAME), new Pair("ColorInversion", AccessibilityShortcutController.COLOR_INVERSION_TILE_COMPONENT_NAME), new Pair("onehanded", AccessibilityShortcutController.ONE_HANDED_TILE_COMPONENT_NAME), new Pair("ReduceBrightColors", AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_TILE_SERVICE_COMPONENT_NAME), new Pair("font_scaling", AccessibilityShortcutController.FONT_SIZE_TILE_COMPONENT_NAME), new Pair("hearing_devices", AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_TILE_COMPONENT_NAME));
    }

    public AccessibilityQsShortcutsRepositoryImpl(AccessibilityManager accessibilityManager, UserA11yQsShortcutsRepository.Factory factory, CoroutineDispatcher coroutineDispatcher) {
        new SparseArray();
    }
}
