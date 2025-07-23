package com.android.systemui.qs.tiles.impl.sensorprivacy.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.sensorprivacy.domain.model.SensorPrivacyToggleTileModel;
import com.android.systemui.qs.tiles.impl.sensorprivacy.ui.model.SensorPrivacyTileResources;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SensorPrivacyToggleTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final SensorPrivacyTileResources sensorPrivacyTileResources;
    public final Resources.Theme theme;

    public SensorPrivacyToggleTileMapper(Resources resources, Resources.Theme theme, SensorPrivacyTileResources sensorPrivacyTileResources) {
        this.resources = resources;
        this.theme = theme;
        this.sensorPrivacyTileResources = sensorPrivacyTileResources;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        final boolean z = ((SensorPrivacyToggleTileModel) obj).isBlocked;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.sensorprivacy.ui.mapper.SensorPrivacyToggleTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                SensorPrivacyToggleTileMapper sensorPrivacyToggleTileMapper = SensorPrivacyToggleTileMapper.this;
                Resources resources2 = sensorPrivacyToggleTileMapper.resources;
                SensorPrivacyTileResources sensorPrivacyTileResources = sensorPrivacyToggleTileMapper.sensorPrivacyTileResources;
                String string = resources2.getString(sensorPrivacyTileResources.getTileLabelRes());
                builder.label = string;
                builder.contentDescription = string;
                builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                boolean z2 = z;
                int iconRes = sensorPrivacyTileResources.getIconRes(z2);
                builder.icon = new Icon.Loaded(sensorPrivacyToggleTileMapper.resources.getDrawable(iconRes, sensorPrivacyToggleTileMapper.theme), null, Integer.valueOf(iconRes));
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                if (z2) {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = sensorPrivacyToggleTileMapper.resources.getString(R.string.quick_settings_camera_mic_blocked);
                } else {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = sensorPrivacyToggleTileMapper.resources.getString(R.string.quick_settings_camera_mic_available);
                }
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
