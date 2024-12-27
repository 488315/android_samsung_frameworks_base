package com.android.systemui.qs.tiles.base.viewmodel;

import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.analytics.QSTileAnalytics;
import com.android.systemui.qs.tiles.base.interactor.DisabledByPolicyInteractor;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.logging.QSTileLogger;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfigProvider;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfigProviderImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public final UserRepository userRepository;

    public QSTileViewModelFactory$Static(DisabledByPolicyInteractor disabledByPolicyInteractor, UserRepository userRepository, FalsingManager falsingManager, QSTileAnalytics qSTileAnalytics, QSTileLogger qSTileLogger, QSTileConfigProvider qSTileConfigProvider, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, QSTileCoroutineScopeFactory qSTileCoroutineScopeFactory) {
        this.disabledByPolicyInteractor = disabledByPolicyInteractor;
        this.userRepository = userRepository;
        this.falsingManager = falsingManager;
        this.qsTileAnalytics = qSTileAnalytics;
        this.qsTileLogger = qSTileLogger;
        this.qsTileConfigProvider = qSTileConfigProvider;
        this.systemClock = systemClock;
        this.backgroundDispatcher = coroutineDispatcher;
        this.coroutineScopeFactory = qSTileCoroutineScopeFactory;
    }

    public final QSTileViewModelImpl create(TileSpec tileSpec, final QSTileUserActionInteractor qSTileUserActionInteractor, final QSTileDataInteractor qSTileDataInteractor, final QSTileDataToStateMapper qSTileDataToStateMapper) {
        QSTileConfig config = ((QSTileConfigProviderImpl) this.qsTileConfigProvider).getConfig(tileSpec.getSpec());
        Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelFactory$Static$create$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return QSTileUserActionInteractor.this;
            }
        };
        Function0 function02 = new Function0() { // from class: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelFactory$Static$create$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return QSTileDataInteractor.this;
            }
        };
        Function0 function03 = new Function0() { // from class: com.android.systemui.qs.tiles.base.viewmodel.QSTileViewModelFactory$Static$create$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return QSTileDataToStateMapper.this;
            }
        };
        ContextScope CoroutineScope = CoroutineScopeKt.CoroutineScope(this.coroutineScopeFactory.applicationScope.getCoroutineContext().plus(SupervisorKt.SupervisorJob$default()));
        return new QSTileViewModelImpl(config, function0, function02, function03, this.disabledByPolicyInteractor, this.userRepository, this.falsingManager, this.qsTileAnalytics, this.qsTileLogger, this.systemClock, this.backgroundDispatcher, CoroutineScope);
    }
}
