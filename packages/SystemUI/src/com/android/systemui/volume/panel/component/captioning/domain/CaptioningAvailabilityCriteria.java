package com.android.systemui.volume.panel.component.captioning.domain;

import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.view.accessibility.data.repository.CaptioningRepositoryImpl;
import com.android.settingslib.view.accessibility.domain.interactor.CaptioningInteractor;
import com.android.systemui.volume.panel.domain.ComponentAvailabilityCriteria;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class CaptioningAvailabilityCriteria implements ComponentAvailabilityCriteria {
    public final ReadonlySharedFlow availability;
    public final UiEventLogger uiEventLogger;

    public CaptioningAvailabilityCriteria(CaptioningInteractor captioningInteractor, CoroutineScope coroutineScope, UiEventLogger uiEventLogger) {
        this.uiEventLogger = uiEventLogger;
        this.availability = FlowKt.shareIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(((CaptioningRepositoryImpl) captioningInteractor.repository).isSystemAudioCaptioningUiEnabled, new CaptioningAvailabilityCriteria$availability$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 1);
    }

    @Override // com.android.systemui.volume.panel.domain.ComponentAvailabilityCriteria
    public final Flow isAvailable() {
        return this.availability;
    }
}
