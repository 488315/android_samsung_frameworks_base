package com.android.systemui.wallet.dagger;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTilePolicy;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WalletModule_ProvideQuickAccessWalletTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public WalletModule_ProvideQuickAccessWalletTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideQuickAccessWalletTileConfig(QsEventLogger qsEventLogger) {
        TileSpec.Companion.getClass();
        TileSpec tileSpecCreate = TileSpec.Companion.create("wallet");
        return new QSTileConfig(tileSpecCreate, new QSTileUIConfig.Resource(R.drawable.ic_wallet_lockscreen, R.string.wallet_title), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.UTILITIES, tileSpecCreate.getSpec(), QSTilePolicy.NoRestrictions.INSTANCE);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideQuickAccessWalletTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
