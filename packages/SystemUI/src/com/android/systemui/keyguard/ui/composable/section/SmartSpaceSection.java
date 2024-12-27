package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;

public final class SmartSpaceSection {
    public final AodBurnInViewModel aodBurnInViewModel;
    public final KeyguardSmartspaceViewModel keyguardSmartspaceViewModel;
    public final KeyguardUnlockAnimationController keyguardUnlockAnimationController;
    public final LockscreenContentViewModel lockscreenContentViewModel;
    public final LockscreenSmartspaceController lockscreenSmartspaceController;

    public SmartSpaceSection(LockscreenSmartspaceController lockscreenSmartspaceController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, AodBurnInViewModel aodBurnInViewModel, LockscreenContentViewModel lockscreenContentViewModel) {
    }
}
