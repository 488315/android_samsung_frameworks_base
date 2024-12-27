package com.android.systemui.qs.tiles.impl.saver.domain;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.saver.domain.model.DataSaverTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DataSaverTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public DataSaverTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final boolean z = ((DataSaverTileModel) obj).isEnabled;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.saver.domain.DataSaverTileMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                boolean z2 = z;
                DataSaverTileMapper dataSaverTileMapper = this;
                if (z2) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.iconRes = Integer.valueOf(R.drawable.qs_data_saver_icon_on);
                    builder.secondaryLabel = dataSaverTileMapper.resources.getStringArray(R.array.tile_states_saver)[2];
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.iconRes = Integer.valueOf(R.drawable.qs_data_saver_icon_off);
                    builder.secondaryLabel = dataSaverTileMapper.resources.getStringArray(R.array.tile_states_saver)[1];
                }
                Resources resources2 = dataSaverTileMapper.resources;
                Integer num = builder.iconRes;
                Intrinsics.checkNotNull(num);
                final Icon.Loaded loaded = new Icon.Loaded(resources2.getDrawable(num.intValue(), dataSaverTileMapper.theme), null);
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.saver.domain.DataSaverTileMapper$map$1$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Icon.Loaded.this;
                    }
                };
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
