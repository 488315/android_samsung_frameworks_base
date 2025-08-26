package com.android.systemui.qs.tiles.impl.qr.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.qr.domain.model.QRCodeScannerTileModel;
import java.util.Collections;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class QRCodeScannerTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public QRCodeScannerTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final QRCodeScannerTileModel qRCodeScannerTileModel = (QRCodeScannerTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.qr.ui.mapper.QRCodeScannerTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) throws Resources.NotFoundException {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                QRCodeScannerTileMapper qRCodeScannerTileMapper = this.f$0;
                String string = qRCodeScannerTileMapper.resources.getString(R.string.qr_code_scanner_title);
                builder.label = string;
                builder.contentDescription = string;
                builder.icon = new Icon.Loaded(qRCodeScannerTileMapper.resources.getDrawable(R.drawable.ic_qr_code_scanner, qRCodeScannerTileMapper.theme), null, Integer.valueOf(R.drawable.ic_qr_code_scanner));
                builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                builder.supportedActions = Collections.singleton(QSTileState.UserAction.CLICK);
                QRCodeScannerTileModel qRCodeScannerTileModel2 = qRCodeScannerTileModel;
                if (qRCodeScannerTileModel2 instanceof QRCodeScannerTileModel.Available) {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = null;
                } else {
                    if (!(qRCodeScannerTileModel2 instanceof QRCodeScannerTileModel.TemporarilyUnavailable)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    builder.activationState = QSTileState.ActivationState.UNAVAILABLE;
                    builder.secondaryLabel = qRCodeScannerTileMapper.resources.getString(R.string.qr_code_scanner_updating_secondary_label);
                }
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
