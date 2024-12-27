package com.android.systemui.qs.tiles.impl.reducebrightness.ui;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.reducebrightness.domain.model.ReduceBrightColorsTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class ReduceBrightColorsTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public ReduceBrightColorsTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final boolean z = ((ReduceBrightColorsTileModel) obj).isEnabled;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.reducebrightness.ui.ReduceBrightColorsTileMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                final QSTileState.Builder builder = (QSTileState.Builder) obj2;
                if (z) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.iconRes = Integer.valueOf(R.drawable.qs_extra_dim_icon_on);
                    builder.secondaryLabel = this.resources.getStringArray(R.array.tile_states_reduce_brightness)[2];
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.iconRes = Integer.valueOf(R.drawable.qs_extra_dim_icon_off);
                    builder.secondaryLabel = this.resources.getStringArray(R.array.tile_states_reduce_brightness)[1];
                }
                final ReduceBrightColorsTileMapper reduceBrightColorsTileMapper = this;
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.reducebrightness.ui.ReduceBrightColorsTileMapper$map$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Resources resources2 = ReduceBrightColorsTileMapper.this.resources;
                        Integer num = builder.iconRes;
                        Intrinsics.checkNotNull(num);
                        return new Icon.Loaded(resources2.getDrawable(num.intValue(), ReduceBrightColorsTileMapper.this.theme), null);
                    }
                };
                String string = this.resources.getString(17042536);
                builder.label = string;
                builder.contentDescription = string;
                builder.supportedActions = SetsKt__SetsKt.setOf(QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK);
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
