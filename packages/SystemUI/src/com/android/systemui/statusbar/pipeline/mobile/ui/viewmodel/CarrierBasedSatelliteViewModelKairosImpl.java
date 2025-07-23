package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairos;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorKairosImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CarrierBasedSatelliteViewModelKairosImpl implements MobileIconViewModelKairosCommon {
    public final StateInit activityContainerVisible;
    public final StateInit activityInVisible;
    public final StateInit activityOutVisible;
    public final StateInit contentDescription = StateKt.stateOf(null);
    public final MobileIconInteractorKairos iconInteractor;
    public final StateInit isVisible;
    public final StateInit networkTypeBackground;
    public final StateInit networkTypeIcon;
    public final StateInit roaming;

    public CarrierBasedSatelliteViewModelKairosImpl(int i, MobileIconInteractorKairos mobileIconInteractorKairos, State state) {
        this.iconInteractor = mobileIconInteractorKairos;
        this.isVisible = StateKt.map(state, new CarrierBasedSatelliteViewModelKairosImpl$$ExternalSyntheticLambda0());
        Boolean bool = Boolean.FALSE;
        this.roaming = StateKt.stateOf(bool);
        this.networkTypeIcon = StateKt.stateOf(null);
        this.networkTypeBackground = StateKt.stateOf(null);
        this.activityInVisible = StateKt.stateOf(bool);
        this.activityOutVisible = StateKt.stateOf(bool);
        this.activityContainerVisible = StateKt.stateOf(bool);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getActivityContainerVisible() {
        return this.activityContainerVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getActivityInVisible() {
        return this.activityInVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getActivityOutVisible() {
        return this.activityOutVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getContentDescription() {
        return this.contentDescription;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getIcon() {
        return ((MobileIconInteractorKairosImpl) this.iconInteractor).signalLevelIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getNetworkTypeBackground() {
        return this.networkTypeBackground;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getNetworkTypeIcon() {
        return this.networkTypeIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State getRoaming() {
        return this.roaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelKairosCommon
    public final State isVisible() {
        return this.isVisible;
    }
}
