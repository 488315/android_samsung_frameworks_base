package com.android.systemui.qs.tiles.impl.colorcorrection.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.colorcorrection.domain.model.ColorCorrectionTileModel;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class ColorCorrectionTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public ColorCorrectionTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        final boolean z = ((ColorCorrectionTileModel) obj).isEnabled;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.colorcorrection.ui.mapper.ColorCorrectionTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) throws Resources.NotFoundException {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                ColorCorrectionTileMapper colorCorrectionTileMapper = this.f$0;
                String[] stringArray = colorCorrectionTileMapper.resources.getStringArray(R.array.tile_states_color_correction);
                builder.icon = new Icon.Loaded(colorCorrectionTileMapper.resources.getDrawable(R.drawable.ic_qs_color_correction, colorCorrectionTileMapper.theme), null, Integer.valueOf(R.drawable.ic_qs_color_correction));
                if (z) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = stringArray[2];
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = stringArray[1];
                }
                builder.contentDescription = builder.label;
                builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
