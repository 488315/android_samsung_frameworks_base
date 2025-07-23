package com.android.systemui.qs.panels.domain.interactor;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SizedTilesResetInteractor implements EditTilesResetInteractor {
    public final CurrentTilesInteractor currentTilesInteractor;
    public final IconTilesInteractor iconTilesInteractor;
    public final UiEventLogger uiEventLogger;

    public SizedTilesResetInteractor(CurrentTilesInteractor currentTilesInteractor, IconTilesInteractor iconTilesInteractor, UiEventLogger uiEventLogger) {
        this.currentTilesInteractor = currentTilesInteractor;
        this.iconTilesInteractor = iconTilesInteractor;
        this.uiEventLogger = uiEventLogger;
    }
}
