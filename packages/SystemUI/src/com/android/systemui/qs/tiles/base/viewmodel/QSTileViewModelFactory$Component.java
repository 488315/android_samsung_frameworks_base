package com.android.systemui.qs.tiles.base.viewmodel;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.analytics.QSTileAnalytics;
import com.android.systemui.qs.tiles.base.interactor.DisabledByPolicyInteractor;
import com.android.systemui.qs.tiles.base.logging.QSTileLogger;
import com.android.systemui.qs.tiles.impl.custom.di.CustomTileComponent;
import com.android.systemui.qs.tiles.impl.custom.di.QSTileConfigModule;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfigProvider;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfigProviderImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.time.SystemClock;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QSTileViewModelFactory$Component {
    public final CoroutineDispatcher backgroundDispatcher;
    public final CustomTileComponent.Builder customTileComponentBuilder;
    public final DisabledByPolicyInteractor disabledByPolicyInteractor;
    public final FalsingManager falsingManager;
    public final QSTileAnalytics qsTileAnalytics;
    public final QSTileConfigProvider qsTileConfigProvider;
    public final QSTileLogger qsTileLogger;
    public final SystemClock systemClock;
    public final UserRepository userRepository;

    public QSTileViewModelFactory$Component(DisabledByPolicyInteractor disabledByPolicyInteractor, UserRepository userRepository, FalsingManager falsingManager, QSTileAnalytics qSTileAnalytics, QSTileLogger qSTileLogger, QSTileConfigProvider qSTileConfigProvider, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, CustomTileComponent.Builder builder) {
        this.disabledByPolicyInteractor = disabledByPolicyInteractor;
        this.userRepository = userRepository;
        this.falsingManager = falsingManager;
        this.qsTileAnalytics = qSTileAnalytics;
        this.qsTileLogger = qSTileLogger;
        this.qsTileConfigProvider = qSTileConfigProvider;
        this.systemClock = systemClock;
        this.backgroundDispatcher = coroutineDispatcher;
        this.customTileComponentBuilder = builder;
    }

    public final QSTileViewModelImpl create(TileSpec.CustomTileSpec customTileSpec) {
        QSTileConfig config = ((QSTileConfigProviderImpl) this.qsTileConfigProvider).getConfig(customTileSpec.spec);
        QSTileConfigModule qSTileConfigModule = new QSTileConfigModule(config);
        DaggerReferenceGlobalRootComponent.CustomTileComponentBuilder customTileComponentBuilder = (DaggerReferenceGlobalRootComponent.CustomTileComponentBuilder) this.customTileComponentBuilder;
        customTileComponentBuilder.getClass();
        customTileComponentBuilder.qSTileConfigModule = qSTileConfigModule;
        CustomTileComponent build = customTileComponentBuilder.build();
        QSTileViewModelFactory$Component$create$1 qSTileViewModelFactory$Component$create$1 = new QSTileViewModelFactory$Component$create$1(build);
        QSTileViewModelFactory$Component$create$2 qSTileViewModelFactory$Component$create$2 = new QSTileViewModelFactory$Component$create$2(build);
        QSTileViewModelFactory$Component$create$3 qSTileViewModelFactory$Component$create$3 = new QSTileViewModelFactory$Component$create$3(build);
        CoroutineScope coroutineScope = build.coroutineScope();
        return new QSTileViewModelImpl(config, qSTileViewModelFactory$Component$create$1, qSTileViewModelFactory$Component$create$2, qSTileViewModelFactory$Component$create$3, this.disabledByPolicyInteractor, this.userRepository, this.falsingManager, this.qsTileAnalytics, this.qsTileLogger, this.systemClock, this.backgroundDispatcher, coroutineScope);
    }
}
