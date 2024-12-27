package com.android.systemui.shade;

import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
