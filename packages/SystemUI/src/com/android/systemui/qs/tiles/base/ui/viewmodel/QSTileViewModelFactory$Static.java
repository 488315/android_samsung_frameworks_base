package com.android.systemui.qs.tiles.base.ui.viewmodel;

import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.domain.interactor.DisabledByPolicyInteractor;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProvider;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProviderImpl;
import com.android.systemui.qs.tiles.base.shared.model.QSTileCoroutineScopeFactory;
import com.android.systemui.qs.tiles.base.ui.analytics.QSTileAnalytics;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.dialog.InternetDetailsViewModel;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.time.SystemClock;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.SupervisorJobImpl;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes2.dex */
public final class QSTileViewModelFactory$Static {
    public final CoroutineDispatcher backgroundDispatcher;
    public final QSTileCoroutineScopeFactory coroutineScopeFactory;
    public final DisabledByPolicyInteractor disabledByPolicyInteractor;
    public final FalsingManager falsingManager;
    public final QSTileAnalytics qsTileAnalytics;
    public final QSTileConfigProvider qsTileConfigProvider;
    public final QSTileLogger qsTileLogger;
    public final SystemClock systemClock;
    public final CoroutineDispatcher uiBackgroundDispatcher;
    public final UserRepository userRepository;

    public QSTileViewModelFactory$Static(DisabledByPolicyInteractor disabledByPolicyInteractor, UserRepository userRepository, FalsingManager falsingManager, QSTileAnalytics qSTileAnalytics, QSTileLogger qSTileLogger, QSTileConfigProvider qSTileConfigProvider, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, QSTileCoroutineScopeFactory qSTileCoroutineScopeFactory) {
        this.disabledByPolicyInteractor = disabledByPolicyInteractor;
        this.userRepository = userRepository;
        this.falsingManager = falsingManager;
        this.qsTileAnalytics = qSTileAnalytics;
        this.qsTileLogger = qSTileLogger;
        this.qsTileConfigProvider = qSTileConfigProvider;
        this.systemClock = systemClock;
        this.backgroundDispatcher = coroutineDispatcher;
        this.uiBackgroundDispatcher = coroutineDispatcher2;
        this.coroutineScopeFactory = qSTileCoroutineScopeFactory;
    }

    public final QSTileViewModelImpl create(TileSpec tileSpec, final QSTileUserActionInteractor qSTileUserActionInteractor, final QSTileDataInteractor qSTileDataInteractor, final QSTileDataToStateMapper qSTileDataToStateMapper, InternetDetailsViewModel internetDetailsViewModel) {
        QSTileConfig config = ((QSTileConfigProviderImpl) this.qsTileConfigProvider).getConfig(tileSpec.getSpec());
        final int i = 0;
        Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelFactory$Static$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2 = i;
                Object obj = qSTileUserActionInteractor;
                switch (i2) {
                    case 0:
                        return (QSTileUserActionInteractor) obj;
                    case 1:
                        return (QSTileDataInteractor) obj;
                    default:
                        return (QSTileDataToStateMapper) obj;
                }
            }
        };
        final int i2 = 1;
        Function0 function02 = new Function0() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelFactory$Static$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i2;
                Object obj = qSTileDataInteractor;
                switch (i22) {
                    case 0:
                        return (QSTileUserActionInteractor) obj;
                    case 1:
                        return (QSTileDataInteractor) obj;
                    default:
                        return (QSTileDataToStateMapper) obj;
                }
            }
        };
        final int i3 = 2;
        Function0 function03 = new Function0() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelFactory$Static$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i22 = i3;
                Object obj = qSTileDataToStateMapper;
                switch (i22) {
                    case 0:
                        return (QSTileUserActionInteractor) obj;
                    case 1:
                        return (QSTileDataInteractor) obj;
                    default:
                        return (QSTileDataToStateMapper) obj;
                }
            }
        };
        QSTileCoroutineScopeFactory qSTileCoroutineScopeFactory = this.coroutineScopeFactory;
        qSTileCoroutineScopeFactory.getClass();
        SupervisorJobImpl supervisorJobImplSupervisorJob$default = SupervisorKt.SupervisorJob$default();
        CoroutineDispatcher coroutineDispatcher = qSTileCoroutineScopeFactory.bgDispatcher;
        coroutineDispatcher.getClass();
        CoroutineContext coroutineContextPlus = CoroutineContext.DefaultImpls.plus(coroutineDispatcher, supervisorJobImplSupervisorJob$default);
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        ContextScope contextScopeCoroutineScope = CoroutineScopeKt.CoroutineScope(coroutineContextPlus.plus(EmptyCoroutineContext.INSTANCE));
        return new QSTileViewModelImpl(config, function0, function02, function03, this.disabledByPolicyInteractor, this.userRepository, this.falsingManager, this.qsTileAnalytics, this.qsTileLogger, this.systemClock, this.backgroundDispatcher, this.uiBackgroundDispatcher, contextScopeCoroutineScope, internetDetailsViewModel);
    }
}
