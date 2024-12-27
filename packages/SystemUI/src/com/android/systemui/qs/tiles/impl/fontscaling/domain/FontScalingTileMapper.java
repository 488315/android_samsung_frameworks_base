package com.android.systemui.qs.tiles.impl.fontscaling.domain;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FontScalingTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public FontScalingTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.fontscaling.domain.FontScalingTileMapper$map$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                builder.iconRes = Integer.valueOf(R.drawable.ic_qs_font_scaling);
                FontScalingTileMapper fontScalingTileMapper = FontScalingTileMapper.this;
                final Icon.Loaded loaded = new Icon.Loaded(fontScalingTileMapper.resources.getDrawable(R.drawable.ic_qs_font_scaling, fontScalingTileMapper.theme), null);
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.fontscaling.domain.FontScalingTileMapper$map$1.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Icon.Loaded.this;
                    }
                };
                builder.contentDescription = builder.label;
                builder.activationState = QSTileState.ActivationState.ACTIVE;
                builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                builder.supportedActions = SetsKt__SetsKt.setOf(QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK);
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
