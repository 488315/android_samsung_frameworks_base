package com.android.systemui.qs.tiles.impl.onehanded.ui;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.onehanded.domain.model.OneHandedModeTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class OneHandedModeTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public OneHandedModeTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final boolean z = ((OneHandedModeTileModel) obj).isEnabled;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.onehanded.ui.OneHandedModeTileMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                final QSTileState.Builder builder = (QSTileState.Builder) obj2;
                String[] stringArray = OneHandedModeTileMapper.this.resources.getStringArray(R.array.tile_states_onehanded);
                builder.label = OneHandedModeTileMapper.this.resources.getString(R.string.quick_settings_onehanded_label);
                builder.iconRes = Integer.valueOf(android.R.drawable.ic_storage_48dp);
                final OneHandedModeTileMapper oneHandedModeTileMapper = OneHandedModeTileMapper.this;
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.onehanded.ui.OneHandedModeTileMapper$map$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Resources resources2 = OneHandedModeTileMapper.this.resources;
                        Integer num = builder.iconRes;
                        Intrinsics.checkNotNull(num);
                        return new Icon.Loaded(resources2.getDrawable(num.intValue(), OneHandedModeTileMapper.this.theme), null);
                    }
                };
                if (z) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = stringArray[2];
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = stringArray[1];
                }
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
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
