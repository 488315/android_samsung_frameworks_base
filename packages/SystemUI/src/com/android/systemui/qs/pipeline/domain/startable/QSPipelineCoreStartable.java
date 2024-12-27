package com.android.systemui.qs.pipeline.domain.startable;

import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.qs.panels.domain.interactor.GridConsistencyInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.AccessibilityTilesInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSPipelineCoreStartable implements CoreStartable {
    public final QSPipelineFlagsRepository featureFlags;

    public QSPipelineCoreStartable(CurrentTilesInteractor currentTilesInteractor, AccessibilityTilesInteractor accessibilityTilesInteractor, AutoAddInteractor autoAddInteractor, QSPipelineFlagsRepository qSPipelineFlagsRepository, RestoreReconciliationInteractor restoreReconciliationInteractor, GridConsistencyInteractor gridConsistencyInteractor) {
        this.featureFlags = qSPipelineFlagsRepository;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.featureFlags.getClass();
        Flags.qsNewPipeline();
    }
}
