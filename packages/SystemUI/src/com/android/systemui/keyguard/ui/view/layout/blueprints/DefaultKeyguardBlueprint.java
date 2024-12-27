package com.android.systemui.keyguard.ui.view.layout.blueprints;

import com.android.systemui.communal.ui.view.layout.sections.CommunalTutorialIndicatorSection;
import com.android.systemui.keyguard.shared.model.KeyguardBlueprint;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.view.layout.sections.AccessibilityActionsSection;
import com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInSection;
import com.android.systemui.keyguard.ui.view.layout.sections.AodNotificationIconsSection;
import com.android.systemui.keyguard.ui.view.layout.sections.ClockSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultDeviceEntrySection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultIndicationAreaSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultNotificationStackScrollLayoutSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultSettingsPopupMenuSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultStatusBarSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultStatusViewSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultUdfpsAccessibilityOverlaySection;
import com.android.systemui.keyguard.ui.view.layout.sections.KeyguardSliceViewSection;
import com.android.systemui.keyguard.ui.view.layout.sections.SmartspaceSection;
import java.util.List;
import java.util.Optional;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DefaultKeyguardBlueprint implements KeyguardBlueprint {
    public final String id = "default";
    public final List sections;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DefaultKeyguardBlueprint(AccessibilityActionsSection accessibilityActionsSection, DefaultIndicationAreaSection defaultIndicationAreaSection, DefaultDeviceEntrySection defaultDeviceEntrySection, DefaultShortcutsSection defaultShortcutsSection, Optional<KeyguardSection> optional, DefaultSettingsPopupMenuSection defaultSettingsPopupMenuSection, DefaultStatusViewSection defaultStatusViewSection, DefaultStatusBarSection defaultStatusBarSection, DefaultNotificationStackScrollLayoutSection defaultNotificationStackScrollLayoutSection, AodNotificationIconsSection aodNotificationIconsSection, AodBurnInSection aodBurnInSection, CommunalTutorialIndicatorSection communalTutorialIndicatorSection, ClockSection clockSection, SmartspaceSection smartspaceSection, KeyguardSliceViewSection keyguardSliceViewSection, DefaultUdfpsAccessibilityOverlaySection defaultUdfpsAccessibilityOverlaySection) {
        this.sections = ArraysKt___ArraysKt.filterNotNull(new KeyguardSection[]{accessibilityActionsSection, defaultIndicationAreaSection, defaultShortcutsSection, optional.orElse(null), defaultSettingsPopupMenuSection, defaultStatusViewSection, defaultStatusBarSection, defaultNotificationStackScrollLayoutSection, aodNotificationIconsSection, smartspaceSection, aodBurnInSection, communalTutorialIndicatorSection, clockSection, keyguardSliceViewSection, defaultDeviceEntrySection, defaultUdfpsAccessibilityOverlaySection});
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardBlueprint
    public final String getId() {
        return this.id;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardBlueprint
    public final List getSections() {
        return this.sections;
    }
}
