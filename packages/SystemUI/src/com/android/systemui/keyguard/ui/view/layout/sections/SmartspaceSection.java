package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardSmartspaceInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import dagger.Lazy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SmartspaceSection extends KeyguardSection {
    public final LockscreenSmartspaceController smartspaceController;

    public SmartspaceSection(Context context, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardSmartspaceInteractor keyguardSmartspaceInteractor, LockscreenSmartspaceController lockscreenSmartspaceController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, Lazy lazy) {
        this.smartspaceController = lockscreenSmartspaceController;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void onRebuildBegin() {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.smartspaceController;
        lockscreenSmartspaceController.suppressDisconnects = true;
        lockscreenSmartspaceController.disconnect();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void onRebuildEnd() {
        LockscreenSmartspaceController lockscreenSmartspaceController = this.smartspaceController;
        lockscreenSmartspaceController.suppressDisconnects = false;
        lockscreenSmartspaceController.disconnect();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }
}
