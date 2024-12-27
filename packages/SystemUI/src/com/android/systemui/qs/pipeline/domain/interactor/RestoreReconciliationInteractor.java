package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.systemui.qs.pipeline.data.model.RestoreProcessor;
import com.android.systemui.qs.pipeline.data.repository.AutoAddRepository;
import com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredRepository;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import java.util.Set;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RestoreReconciliationInteractor {
    public final AutoAddRepository autoAddRepository;
    public final QSPipelineLogger qsPipelineLogger;
    public final QSSettingsRestoredRepository qsSettingsRestoredRepository;
    public final Set restoreProcessors;
    public final TileSpecRepository tileSpecRepository;

    public RestoreReconciliationInteractor(TileSpecRepository tileSpecRepository, AutoAddRepository autoAddRepository, QSSettingsRestoredRepository qSSettingsRestoredRepository, Set<RestoreProcessor> set, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.restoreProcessors = set;
    }
}
