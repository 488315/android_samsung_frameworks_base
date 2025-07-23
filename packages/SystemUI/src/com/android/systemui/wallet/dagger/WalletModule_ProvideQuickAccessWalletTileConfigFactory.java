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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WalletModule_ProvideQuickAccessWalletTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public WalletModule_ProvideQuickAccessWalletTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideQuickAccessWalletTileConfig(QsEventLogger qsEventLogger) {
        TileSpec.Companion.getClass();
        TileSpec create = TileSpec.Companion.create("wallet");
        return new QSTileConfig(create, new QSTileUIConfig.Resource(R.drawable.ic_wallet_lockscreen, R.string.wallet_title), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.UTILITIES, create.getSpec(), QSTilePolicy.NoRestrictions.INSTANCE);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideQuickAccessWalletTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
