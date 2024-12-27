package com.android.systemui.qs.tiles.impl.qr.ui;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.qr.domain.model.QRCodeScannerTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import java.util.Collections;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class QRCodeScannerTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public QRCodeScannerTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final QRCodeScannerTileModel qRCodeScannerTileModel = (QRCodeScannerTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.qr.ui.QRCodeScannerTileMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                final QSTileState.Builder builder = (QSTileState.Builder) obj2;
                String string = QRCodeScannerTileMapper.this.resources.getString(R.string.qr_code_scanner_title);
                builder.label = string;
                builder.contentDescription = string;
                builder.iconRes = Integer.valueOf(R.drawable.ic_qr_code_scanner);
                final QRCodeScannerTileMapper qRCodeScannerTileMapper = QRCodeScannerTileMapper.this;
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.qr.ui.QRCodeScannerTileMapper$map$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Resources resources2 = QRCodeScannerTileMapper.this.resources;
                        Integer num = builder.iconRes;
                        Intrinsics.checkNotNull(num);
                        return new Icon.Loaded(resources2.getDrawable(num.intValue(), QRCodeScannerTileMapper.this.theme), null);
                    }
                };
                builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                builder.supportedActions = Collections.singleton(QSTileState.UserAction.CLICK);
                QRCodeScannerTileModel qRCodeScannerTileModel2 = qRCodeScannerTileModel;
                if (qRCodeScannerTileModel2 instanceof QRCodeScannerTileModel.Available) {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = null;
                } else if (qRCodeScannerTileModel2 instanceof QRCodeScannerTileModel.TemporarilyUnavailable) {
                    builder.activationState = QSTileState.ActivationState.UNAVAILABLE;
                    builder.secondaryLabel = QRCodeScannerTileMapper.this.resources.getString(R.string.qr_code_scanner_updating_secondary_label);
                }
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
