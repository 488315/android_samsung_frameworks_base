package com.android.systemui.shade;

import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;

public final class QuickSettingsControllerSceneImpl implements QuickSettingsController {
    public final QSSceneAdapter qsSceneAdapter;
    public final ShadeInteractor shadeInteractor;

    public QuickSettingsControllerSceneImpl(ShadeInteractor shadeInteractor, QSSceneAdapter qSSceneAdapter) {
        this.shadeInteractor = shadeInteractor;
        this.qsSceneAdapter = qSSceneAdapter;
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final void closeQsCustomizer() {
        QSImpl qSImpl = (QSImpl) ((QSSceneAdapterImpl) this.qsSceneAdapter).qsImpl.$$delegate_0.getValue();
        if (qSImpl != null) {
            qSImpl.closeCustomizer();
        }
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final boolean getExpanded$1() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isQsExpanded().getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final SecQuickSettingsControllerImpl getSecQuickSettingsControllerImpl$1() {
        return null;
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final boolean isCustomizing() {
        return ((Boolean) ((QSSceneAdapterImpl) this.qsSceneAdapter).isCustomizerShowing.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.QuickSettingsController
    public final void closeQs() {
    }
}
