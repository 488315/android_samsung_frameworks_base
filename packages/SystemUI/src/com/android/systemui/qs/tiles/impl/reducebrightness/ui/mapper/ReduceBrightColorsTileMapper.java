package com.android.systemui.qs.tiles.impl.reducebrightness.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.reducebrightness.domain.model.ReduceBrightColorsTileModel;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ReduceBrightColorsTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public ReduceBrightColorsTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        final boolean z = ((ReduceBrightColorsTileModel) obj).isEnabled;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.reducebrightness.ui.mapper.ReduceBrightColorsTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                int i;
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                boolean z2 = z;
                ReduceBrightColorsTileMapper reduceBrightColorsTileMapper = this;
                if (z2) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = reduceBrightColorsTileMapper.resources.getStringArray(R.array.tile_states_reduce_brightness)[2];
                    i = R.drawable.qs_extra_dim_icon_on;
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = reduceBrightColorsTileMapper.resources.getStringArray(R.array.tile_states_reduce_brightness)[1];
                    i = R.drawable.qs_extra_dim_icon_off;
                }
                builder.icon = new Icon.Loaded(reduceBrightColorsTileMapper.resources.getDrawable(i, reduceBrightColorsTileMapper.theme), null, Integer.valueOf(i));
                String string = reduceBrightColorsTileMapper.resources.getString(17042657);
                builder.label = string;
                builder.contentDescription = string;
                builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
