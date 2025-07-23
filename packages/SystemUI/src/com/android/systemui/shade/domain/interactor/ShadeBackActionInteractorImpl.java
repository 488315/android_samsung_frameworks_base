package com.android.systemui.shade.domain.interactor;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeBackActionInteractorImpl implements ShadeBackActionInteractor {
    public final SceneInteractor sceneInteractor;
    public final ShadeInteractor shadeInteractor;
    public final ShadeModeInteractor shadeModeInteractor;

    public ShadeBackActionInteractorImpl(ShadeInteractor shadeInteractor, ShadeModeInteractor shadeModeInteractor, SceneInteractor sceneInteractor, DeviceEntryInteractor deviceEntryInteractor) {
        this.shadeInteractor = shadeInteractor;
        this.shadeModeInteractor = shadeModeInteractor;
        this.sceneInteractor = sceneInteractor;
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final void animateCollapseQs(boolean z) {
        if (((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isQsExpanded().getValue()).booleanValue()) {
            ShadeModeInteractor shadeModeInteractor = this.shadeModeInteractor;
            SceneInteractor.changeScene$default(this.sceneInteractor, (shadeModeInteractor.isDualShade() || shadeModeInteractor.isSplitShade()) ? SceneFamilies.Home : Scenes.Shade, "animateCollapseQs", null, null, false, 28);
        }
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final boolean canBeCollapsed() {
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) this.shadeInteractor;
        return ((Boolean) shadeInteractorImpl.baseShadeInteractor.isAnyExpanded().getValue()).booleanValue() && !((Boolean) shadeInteractorImpl.isUserInteracting.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.shade.domain.interactor.ShadeBackActionInteractor
    public final void onBackPressed() {
        animateCollapseQs(false);
    }
}
