package com.android.systemui.qs.tiles.impl.flashlight.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.flashlight.domain.model.FlashlightTileModel;
import java.util.Collections;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FlashlightMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public FlashlightMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final FlashlightTileModel flashlightTileModel = (FlashlightTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.flashlight.ui.mapper.FlashlightMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                FlashlightTileModel flashlightTileModel2 = FlashlightTileModel.this;
                boolean z = flashlightTileModel2 instanceof FlashlightTileModel.FlashlightAvailable;
                int i = (z && ((FlashlightTileModel.FlashlightAvailable) flashlightTileModel2).isEnabled) ? R.drawable.qs_flashlight_icon_on : R.drawable.qs_flashlight_icon_off;
                FlashlightMapper flashlightMapper = this;
                builder.icon = new Icon.Loaded(flashlightMapper.resources.getDrawable(i, flashlightMapper.theme), null, Integer.valueOf(i));
                builder.contentDescription = builder.label;
                if (flashlightTileModel2 instanceof FlashlightTileModel.FlashlightTemporarilyUnavailable) {
                    builder.activationState = QSTileState.ActivationState.UNAVAILABLE;
                    String string = flashlightMapper.resources.getString(R.string.quick_settings_flashlight_camera_in_use);
                    builder.secondaryLabel = string;
                    builder.stateDescription = string;
                    builder.supportedActions = EmptySet.INSTANCE;
                    return Unit.INSTANCE;
                }
                if (z && ((FlashlightTileModel.FlashlightAvailable) flashlightTileModel2).isEnabled) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = flashlightMapper.resources.getStringArray(R.array.tile_states_flashlight)[2];
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = flashlightMapper.resources.getStringArray(R.array.tile_states_flashlight)[1];
                }
                builder.supportedActions = Collections.singleton(QSTileState.UserAction.CLICK);
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
