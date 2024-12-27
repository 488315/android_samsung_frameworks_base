package com.android.systemui.qs.tiles.impl.airplane.domain;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.airplane.domain.model.AirplaneModeTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public final class AirplaneModeMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public AirplaneModeMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final boolean z = ((AirplaneModeTileModel) obj).isEnabled;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.airplane.domain.AirplaneModeMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                AirplaneModeMapper airplaneModeMapper = AirplaneModeMapper.this;
                final Icon.Loaded loaded = new Icon.Loaded(airplaneModeMapper.resources.getDrawable(z ? R.drawable.qs_airplane_icon_on : R.drawable.qs_airplane_icon_off, airplaneModeMapper.theme), null);
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.airplane.domain.AirplaneModeMapper$map$1.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Icon.Loaded.this;
                    }
                };
                if (z) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = AirplaneModeMapper.this.resources.getStringArray(R.array.tile_states_airplane)[2];
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = AirplaneModeMapper.this.resources.getStringArray(R.array.tile_states_airplane)[1];
                }
                builder.contentDescription = builder.label;
                builder.supportedActions = SetsKt__SetsKt.setOf(QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK);
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
