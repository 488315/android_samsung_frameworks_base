package com.android.systemui.qs.panels.ui.viewmodel.toolbar;

import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;

/* loaded from: classes2.dex */
public final class EditModeButtonViewModel {
    public final ActivityStarter activityStarter;
    public final EditModeViewModel editModeViewModel;
    public final FalsingInteractor falsingInteractor;

    public interface Factory {
        EditModeButtonViewModel create();
    }

    public EditModeButtonViewModel(EditModeViewModel editModeViewModel, FalsingInteractor falsingInteractor, ActivityStarter activityStarter) {
        this.editModeViewModel = editModeViewModel;
        this.falsingInteractor = falsingInteractor;
        this.activityStarter = activityStarter;
    }
}
