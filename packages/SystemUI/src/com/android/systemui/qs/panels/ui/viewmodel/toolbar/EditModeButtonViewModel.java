package com.android.systemui.qs.panels.ui.viewmodel.toolbar;

import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EditModeButtonViewModel {
    public final ActivityStarter activityStarter;
    public final EditModeViewModel editModeViewModel;
    public final FalsingInteractor falsingInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        EditModeButtonViewModel create();
    }

    public EditModeButtonViewModel(EditModeViewModel editModeViewModel, FalsingInteractor falsingInteractor, ActivityStarter activityStarter) {
        this.editModeViewModel = editModeViewModel;
        this.falsingInteractor = falsingInteractor;
        this.activityStarter = activityStarter;
    }
}
