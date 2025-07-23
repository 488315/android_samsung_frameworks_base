package com.android.systemui.qs.tiles.impl.hearingdevices.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.hearingdevices.domain.model.HearingDevicesTileModel;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HearingDevicesTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public HearingDevicesTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final HearingDevicesTileModel hearingDevicesTileModel = (HearingDevicesTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.hearingdevices.ui.mapper.HearingDevicesTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                HearingDevicesTileMapper hearingDevicesTileMapper = HearingDevicesTileMapper.this;
                builder.label = hearingDevicesTileMapper.resources.getString(R.string.quick_settings_hearing_devices_label);
                builder.icon = new Icon.Loaded(hearingDevicesTileMapper.resources.getDrawable(R.drawable.qs_hearing_devices_icon, hearingDevicesTileMapper.theme), null, Integer.valueOf(R.drawable.qs_hearing_devices_icon));
                builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                builder.contentDescription = builder.label;
                HearingDevicesTileModel hearingDevicesTileModel2 = hearingDevicesTileModel;
                if (hearingDevicesTileModel2.isAnyActiveHearingDevice) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = hearingDevicesTileMapper.resources.getString(R.string.quick_settings_hearing_devices_connected);
                } else if (hearingDevicesTileModel2.isAnyPairedHearingDevice) {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = hearingDevicesTileMapper.resources.getString(R.string.quick_settings_hearing_devices_disconnected);
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = "";
                }
                builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
