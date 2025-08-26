package com.android.systemui.qs.panels.domain.interactor;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepository;
import com.android.systemui.qs.panels.data.repository.DefaultLargeTilesRepositoryImpl;
import com.android.systemui.qs.panels.data.repository.LargeTileSpanRepository;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class IconTilesInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CurrentTilesInteractor currentTilesInteractor;
    public final ReadonlyStateFlow largeTilesSpan;
    public final ReadonlyStateFlow largeTilesSpecs;
    public final LogBuffer logBuffer;
    public final QSPreferencesInteractor preferencesInteractor;
    public final DefaultLargeTilesRepository repo;
    public final UiEventLogger uiEventLogger;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public IconTilesInteractor(DefaultLargeTilesRepository defaultLargeTilesRepository, CurrentTilesInteractor currentTilesInteractor, QSPreferencesInteractor qSPreferencesInteractor, UiEventLogger uiEventLogger, LargeTileSpanRepository largeTileSpanRepository, LogBuffer logBuffer, CoroutineScope coroutineScope) {
        this.repo = defaultLargeTilesRepository;
        this.currentTilesInteractor = currentTilesInteractor;
        this.preferencesInteractor = qSPreferencesInteractor;
        this.uiEventLogger = uiEventLogger;
        this.logBuffer = logBuffer;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(qSPreferencesInteractor.largeTilesSpecs, new IconTilesInteractor$largeTilesSpecs$1(this, null));
        SharingStarted.Companion.getClass();
        this.largeTilesSpecs = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.Eagerly, ((DefaultLargeTilesRepositoryImpl) defaultLargeTilesRepository).defaultLargeTiles);
        this.largeTilesSpan = largeTileSpanRepository.span;
    }
}
