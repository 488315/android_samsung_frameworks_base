package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SmartSpaceSection {
    public final AodBurnInViewModel aodBurnInViewModel;
    public final KeyguardSmartspaceViewModel keyguardSmartspaceViewModel;
    public final KeyguardUnlockAnimationController keyguardUnlockAnimationController;
    public final LockscreenContentViewModel lockscreenContentViewModel;
    public final LockscreenSmartspaceController lockscreenSmartspaceController;

    public SmartSpaceSection(LockscreenSmartspaceController lockscreenSmartspaceController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, AodBurnInViewModel aodBurnInViewModel, LockscreenContentViewModel lockscreenContentViewModel) {
    }
}
