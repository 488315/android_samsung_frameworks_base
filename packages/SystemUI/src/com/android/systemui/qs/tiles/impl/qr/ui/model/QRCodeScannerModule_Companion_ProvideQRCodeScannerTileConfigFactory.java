package com.android.systemui.qs.tiles.impl.qr.ui.model;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class QRCodeScannerModule_Companion_ProvideQRCodeScannerTileConfigFactory implements Provider {
    public final Provider uiEventLoggerProvider;

    public QRCodeScannerModule_Companion_ProvideQRCodeScannerTileConfigFactory(Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideQRCodeScannerTileConfig(QsEventLogger qsEventLogger) {
        QRCodeScannerModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("qr_code_scanner"), new QSTileUIConfig.Resource(R.drawable.ic_qr_code_scanner, R.string.qr_code_scanner_title), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), TileCategory.UTILITIES, null, null, false, 112, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideQRCodeScannerTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
