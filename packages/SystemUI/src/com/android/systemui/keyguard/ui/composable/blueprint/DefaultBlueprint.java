package com.android.systemui.keyguard.ui.composable.blueprint;

import com.android.systemui.keyguard.ui.composable.section.BottomAreaSection;
import com.android.systemui.keyguard.ui.composable.section.LockSection;
import com.android.systemui.keyguard.ui.composable.section.NotificationSection;
import com.android.systemui.keyguard.ui.composable.section.SettingsMenuSection;
import com.android.systemui.keyguard.ui.composable.section.StatusBarSection;
import com.android.systemui.keyguard.ui.composable.section.TopAreaSection;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DefaultBlueprint implements ComposableLockscreenSceneBlueprint {
    public final Optional ambientIndicationSectionOptional;
    public final BottomAreaSection bottomAreaSection;
    public final String id = "default";
    public final LockSection lockSection;
    public final NotificationSection notificationSection;
    public final SettingsMenuSection settingsMenuSection;
    public final StatusBarSection statusBarSection;
    public final TopAreaSection topAreaSection;
    public final LockscreenContentViewModel viewModel;

    public DefaultBlueprint(LockscreenContentViewModel lockscreenContentViewModel, StatusBarSection statusBarSection, LockSection lockSection, Optional<Object> optional, BottomAreaSection bottomAreaSection, SettingsMenuSection settingsMenuSection, TopAreaSection topAreaSection, NotificationSection notificationSection) {
    }

    @Override // com.android.systemui.keyguard.ui.composable.blueprint.ComposableLockscreenSceneBlueprint
    public final String getId() {
        return this.id;
    }
}
