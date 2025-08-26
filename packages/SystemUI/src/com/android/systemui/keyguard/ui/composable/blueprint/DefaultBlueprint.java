package com.android.systemui.keyguard.ui.composable.blueprint;

import com.android.systemui.keyguard.ui.composable.section.BottomAreaSection;
import com.android.systemui.keyguard.ui.composable.section.LockSection;
import com.android.systemui.keyguard.ui.composable.section.NotificationSection;
import com.android.systemui.keyguard.ui.composable.section.SettingsMenuSection;
import com.android.systemui.keyguard.ui.composable.section.StatusBarSection;
import com.android.systemui.keyguard.ui.composable.section.TopAreaSection;
import java.util.Optional;

/* loaded from: classes2.dex */
public final class DefaultBlueprint implements ComposableLockscreenSceneBlueprint {
    public final Optional ambientIndicationSectionOptional;
    public final BottomAreaSection bottomAreaSection;
    public final LockSection lockSection;
    public final NotificationSection notificationSection;
    public final SettingsMenuSection settingsMenuSection;
    public final StatusBarSection statusBarSection;
    public final TopAreaSection topAreaSection;

    public DefaultBlueprint(StatusBarSection statusBarSection, LockSection lockSection, Optional<Object> optional, BottomAreaSection bottomAreaSection, SettingsMenuSection settingsMenuSection, TopAreaSection topAreaSection, NotificationSection notificationSection) {
        this.statusBarSection = statusBarSection;
        this.lockSection = lockSection;
        this.ambientIndicationSectionOptional = optional;
        this.bottomAreaSection = bottomAreaSection;
        this.settingsMenuSection = settingsMenuSection;
        this.topAreaSection = topAreaSection;
        this.notificationSection = notificationSection;
    }

    @Override // com.android.systemui.keyguard.ui.composable.blueprint.ComposableLockscreenSceneBlueprint
    public final String getId() {
        return "default";
    }
}
