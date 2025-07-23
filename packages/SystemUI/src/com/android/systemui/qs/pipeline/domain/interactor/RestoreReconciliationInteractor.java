package com.android.systemui.qs.pipeline.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.qs.pipeline.data.model.RestoreProcessor;
import com.android.systemui.qs.pipeline.data.repository.AutoAddRepository;
import com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredRepository;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import java.util.Set;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RestoreReconciliationInteractor {
    public final CoroutineScope applicationScope;
    public final AutoAddRepository autoAddRepository;
    public final CoroutineDispatcher backgroundDispatcher;
    public final QSPipelineLogger qsPipelineLogger;
    public final QSSettingsRestoredRepository qsSettingsRestoredRepository;
    public final Set restoreProcessors;
    public final TileSpecRepository tileSpecRepository;

    public RestoreReconciliationInteractor(TileSpecRepository tileSpecRepository, AutoAddRepository autoAddRepository, QSSettingsRestoredRepository qSSettingsRestoredRepository, Set<RestoreProcessor> set, QSPipelineLogger qSPipelineLogger, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.tileSpecRepository = tileSpecRepository;
        this.autoAddRepository = autoAddRepository;
        this.qsSettingsRestoredRepository = qSSettingsRestoredRepository;
        this.restoreProcessors = set;
        this.qsPipelineLogger = qSPipelineLogger;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new RestoreReconciliationInteractor$start$1(this, null), 5);
    }
}
