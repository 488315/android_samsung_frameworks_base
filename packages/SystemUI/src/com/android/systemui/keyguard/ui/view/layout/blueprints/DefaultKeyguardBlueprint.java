package com.android.systemui.keyguard.ui.view.layout.blueprints;

import com.android.systemui.keyguard.shared.model.KeyguardBlueprint;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.view.layout.sections.AccessibilityActionsSection;
import com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInSection;
import com.android.systemui.keyguard.ui.view.layout.sections.AodNotificationIconsSection;
import com.android.systemui.keyguard.ui.view.layout.sections.AodPromotedNotificationSection;
import com.android.systemui.keyguard.ui.view.layout.sections.ClockSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultDeviceEntrySection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultIndicationAreaSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultNotificationStackScrollLayoutSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultSettingsPopupMenuSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultStatusBarSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultUdfpsAccessibilityOverlaySection;
import com.android.systemui.keyguard.ui.view.layout.sections.KeyguardSliceViewSection;
import com.android.systemui.keyguard.ui.view.layout.sections.SmartspaceSection;
import java.util.List;
import java.util.Optional;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DefaultKeyguardBlueprint implements KeyguardBlueprint {
    public final List sections;

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
    }

    public DefaultKeyguardBlueprint(AccessibilityActionsSection accessibilityActionsSection, DefaultIndicationAreaSection defaultIndicationAreaSection, DefaultDeviceEntrySection defaultDeviceEntrySection, DefaultShortcutsSection defaultShortcutsSection, Optional<KeyguardSection> optional, DefaultSettingsPopupMenuSection defaultSettingsPopupMenuSection, DefaultStatusBarSection defaultStatusBarSection, DefaultNotificationStackScrollLayoutSection defaultNotificationStackScrollLayoutSection, AodPromotedNotificationSection aodPromotedNotificationSection, AodNotificationIconsSection aodNotificationIconsSection, AodBurnInSection aodBurnInSection, ClockSection clockSection, SmartspaceSection smartspaceSection, KeyguardSliceViewSection keyguardSliceViewSection, DefaultUdfpsAccessibilityOverlaySection defaultUdfpsAccessibilityOverlaySection) {
        this.sections = ArraysKt___ArraysKt.filterNotNull(new KeyguardSection[]{accessibilityActionsSection, defaultIndicationAreaSection, defaultShortcutsSection, optional.orElse(null), defaultSettingsPopupMenuSection, defaultStatusBarSection, defaultNotificationStackScrollLayoutSection, aodNotificationIconsSection, aodPromotedNotificationSection, smartspaceSection, aodBurnInSection, clockSection, keyguardSliceViewSection, defaultDeviceEntrySection, defaultUdfpsAccessibilityOverlaySection});
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardBlueprint
    public final String getId() {
        return "default";
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardBlueprint
    public final List getSections() {
        return this.sections;
    }
}
