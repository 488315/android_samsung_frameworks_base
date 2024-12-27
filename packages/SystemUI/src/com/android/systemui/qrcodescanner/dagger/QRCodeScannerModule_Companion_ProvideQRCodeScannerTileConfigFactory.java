package com.android.systemui.qrcodescanner.dagger;

import com.android.systemui.R;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QRCodeScannerModule_Companion_ProvideQRCodeScannerTileConfigFactory implements Provider {
    public final javax.inject.Provider uiEventLoggerProvider;

    public QRCodeScannerModule_Companion_ProvideQRCodeScannerTileConfigFactory(javax.inject.Provider provider) {
        this.uiEventLoggerProvider = provider;
    }

    public static QSTileConfig provideQRCodeScannerTileConfig(QsEventLogger qsEventLogger) {
        QRCodeScannerModule.Companion.getClass();
        TileSpec.Companion.getClass();
        return new QSTileConfig(TileSpec.Companion.create("qr_code_scanner"), new QSTileUIConfig.Resource(R.drawable.ic_qr_code_scanner, R.string.qr_code_scanner_title), ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId(), null, null, 24, null);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideQRCodeScannerTileConfig((QsEventLogger) this.uiEventLoggerProvider.get());
    }
}
