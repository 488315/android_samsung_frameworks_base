package com.android.systemui.qs.tiles.impl.inversion.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.inversion.domain.model.ColorInversionTileModel;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class ColorInversionTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public ColorInversionTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        final boolean z = ((ColorInversionTileModel) obj).isEnabled;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.inversion.ui.mapper.ColorInversionTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) throws Resources.NotFoundException {
                int i;
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                ColorInversionTileMapper colorInversionTileMapper = this.f$0;
                String[] stringArray = colorInversionTileMapper.resources.getStringArray(R.array.tile_states_inversion);
                if (z) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = stringArray[2];
                    i = R.drawable.qs_invert_colors_icon_on;
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = stringArray[1];
                    i = R.drawable.qs_invert_colors_icon_off;
                }
                builder.icon = new Icon.Loaded(colorInversionTileMapper.resources.getDrawable(i, colorInversionTileMapper.theme), null, Integer.valueOf(i));
                builder.contentDescription = builder.label;
                builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
