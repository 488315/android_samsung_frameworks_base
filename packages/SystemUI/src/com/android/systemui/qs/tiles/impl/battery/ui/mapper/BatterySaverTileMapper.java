package com.android.systemui.qs.tiles.impl.battery.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel;
import java.util.Collections;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public class BatterySaverTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public BatterySaverTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final BatterySaverTileModel batterySaverTileModel = (BatterySaverTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.battery.ui.mapper.BatterySaverTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) throws Resources.NotFoundException {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                BatterySaverTileMapper batterySaverTileMapper = this.f$0;
                String string = batterySaverTileMapper.resources.getString(R.string.battery_detail_switch_title);
                builder.label = string;
                builder.contentDescription = string;
                BatterySaverTileModel batterySaverTileModel2 = batterySaverTileModel;
                int i = batterySaverTileModel2.isPowerSaving() ? R.drawable.qs_battery_saver_icon_on : R.drawable.qs_battery_saver_icon_off;
                builder.icon = new Icon.Loaded(batterySaverTileMapper.resources.getDrawable(i, batterySaverTileMapper.theme), null, Integer.valueOf(i));
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                if (batterySaverTileModel2.isPluggedIn()) {
                    builder.activationState = QSTileState.ActivationState.UNAVAILABLE;
                    builder.supportedActions = Collections.singleton(QSTileState.UserAction.LONG_CLICK);
                    builder.secondaryLabel = "";
                } else if (batterySaverTileModel2.isPowerSaving()) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                    if (batterySaverTileModel2 instanceof BatterySaverTileModel.Extreme) {
                        String string2 = batterySaverTileMapper.resources.getString(((BatterySaverTileModel.Extreme) batterySaverTileModel2).isExtremeSaving ? R.string.extreme_battery_saver_text : R.string.standard_battery_saver_text);
                        builder.secondaryLabel = string2;
                        builder.stateDescription = string2;
                    } else {
                        builder.secondaryLabel = "";
                    }
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                    builder.secondaryLabel = "";
                }
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
