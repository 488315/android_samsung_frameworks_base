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
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultSettingsPopupMenuSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultShortcutsSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultStatusBarSection;
import com.android.systemui.keyguard.ui.view.layout.sections.DefaultStatusViewSection;
import com.android.systemui.keyguard.ui.view.layout.sections.SmartspaceSection;
import com.android.systemui.keyguard.ui.view.layout.sections.SplitShadeGuidelines;
import com.android.systemui.keyguard.ui.view.layout.sections.SplitShadeMediaSection;
import com.android.systemui.keyguard.ui.view.layout.sections.SplitShadeNotificationStackScrollLayoutSection;
import java.util.List;
import java.util.Optional;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SplitShadeKeyguardBlueprint implements KeyguardBlueprint {
    public final String id = "split-shade";
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

    public SplitShadeKeyguardBlueprint(AccessibilityActionsSection accessibilityActionsSection, DefaultIndicationAreaSection defaultIndicationAreaSection, DefaultDeviceEntrySection defaultDeviceEntrySection, DefaultShortcutsSection defaultShortcutsSection, Optional<KeyguardSection> optional, DefaultSettingsPopupMenuSection defaultSettingsPopupMenuSection, DefaultStatusViewSection defaultStatusViewSection, DefaultStatusBarSection defaultStatusBarSection, SplitShadeNotificationStackScrollLayoutSection splitShadeNotificationStackScrollLayoutSection, SplitShadeGuidelines splitShadeGuidelines, AodNotificationIconsSection aodNotificationIconsSection, AodBurnInSection aodBurnInSection, CommunalTutorialIndicatorSection communalTutorialIndicatorSection, ClockSection clockSection, SmartspaceSection smartspaceSection, SplitShadeMediaSection splitShadeMediaSection) {
        this.sections = ArraysKt___ArraysKt.filterNotNull(new KeyguardSection[]{accessibilityActionsSection, defaultIndicationAreaSection, defaultShortcutsSection, optional.orElse(null), defaultSettingsPopupMenuSection, defaultStatusViewSection, defaultStatusBarSection, splitShadeNotificationStackScrollLayoutSection, splitShadeGuidelines, aodNotificationIconsSection, smartspaceSection, aodBurnInSection, communalTutorialIndicatorSection, clockSection, splitShadeMediaSection, defaultDeviceEntrySection});
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
