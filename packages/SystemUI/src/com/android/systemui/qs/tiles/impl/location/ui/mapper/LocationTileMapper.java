package com.android.systemui.qs.tiles.impl.location.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.location.domain.model.LocationTileModel;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class LocationTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public LocationTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        final boolean z = ((LocationTileModel) obj).isEnabled;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.location.ui.mapper.LocationTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                boolean z2 = z;
                int i = z2 ? R.drawable.qs_location_icon_on : R.drawable.qs_location_icon_off;
                LocationTileMapper locationTileMapper = this;
                builder.icon = new Icon.Loaded(locationTileMapper.resources.getDrawable(i, locationTileMapper.theme), null, Integer.valueOf(i));
                builder.label = locationTileMapper.resources.getString(R.string.quick_settings_location_label);
                if (z2) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = locationTileMapper.resources.getStringArray(R.array.tile_states_location)[2];
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = locationTileMapper.resources.getStringArray(R.array.tile_states_location)[1];
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
