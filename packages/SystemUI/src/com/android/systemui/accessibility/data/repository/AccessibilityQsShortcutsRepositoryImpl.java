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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AccessibilityQsShortcutsRepositoryImpl implements AccessibilityQsShortcutsRepository {
    public static final Map TILE_SPEC_TO_COMPONENT_MAPPING;
    public final CoroutineDispatcher backgroundDispatcher;
    public final AccessibilityManager manager;
    public final SparseArray userA11yQsShortcutsRepositories = new SparseArray();
    public final UserA11yQsShortcutsRepository.Factory userA11yQsShortcutsRepositoryFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TILE_SPEC_TO_COMPONENT_MAPPING = MapsKt__MapsKt.mapOf(new Pair("ColorCorrection", AccessibilityShortcutController.DALTONIZER_TILE_COMPONENT_NAME), new Pair("ColorInversion", AccessibilityShortcutController.COLOR_INVERSION_TILE_COMPONENT_NAME), new Pair("onehanded", AccessibilityShortcutController.ONE_HANDED_TILE_COMPONENT_NAME), new Pair("ReduceBrightColors", AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_TILE_SERVICE_COMPONENT_NAME), new Pair("font_scaling", AccessibilityShortcutController.FONT_SIZE_TILE_COMPONENT_NAME), new Pair("hearing_devices", AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_TILE_COMPONENT_NAME), new Pair("HighContrastFont", AccessibilityShortcutController.HIGH_CONTRAST_FONTS_TILE_COMPONENT_NAME), new Pair("ColorLens", AccessibilityShortcutController.COLOR_LENS_TILE_COMPONENT_NAME));
    }

    public AccessibilityQsShortcutsRepositoryImpl(AccessibilityManager accessibilityManager, UserA11yQsShortcutsRepository.Factory factory, CoroutineDispatcher coroutineDispatcher) {
        this.manager = accessibilityManager;
        this.userA11yQsShortcutsRepositoryFactory = factory;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x00dd, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r11, r2, r0) != r1) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00df, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0068, code lost:
    
        if (r2 == r1) goto L41;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /* JADX WARN: Type inference failed for: r9v6, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object notifyAccessibilityManagerTilesChanged(android.content.Context r10, java.util.List r11, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            Method dump skipped, instructions count: 227
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl.notifyAccessibilityManagerTilesChanged(android.content.Context, java.util.List, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
