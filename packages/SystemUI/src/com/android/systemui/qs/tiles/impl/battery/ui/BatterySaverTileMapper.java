package com.android.systemui.qs.tiles.impl.battery.ui;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import java.util.Collections;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BatterySaverTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public BatterySaverTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final BatterySaverTileModel batterySaverTileModel = (BatterySaverTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.battery.ui.BatterySaverTileMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                final QSTileState.Builder builder = (QSTileState.Builder) obj2;
                String string = BatterySaverTileMapper.this.resources.getString(R.string.battery_detail_switch_title);
                builder.label = string;
                builder.contentDescription = string;
                builder.iconRes = batterySaverTileModel.isPowerSaving() ? Integer.valueOf(R.drawable.qs_battery_saver_icon_on) : Integer.valueOf(R.drawable.qs_battery_saver_icon_off);
                final BatterySaverTileMapper batterySaverTileMapper = BatterySaverTileMapper.this;
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.battery.ui.BatterySaverTileMapper$map$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Resources resources2 = BatterySaverTileMapper.this.resources;
                        Integer num = builder.iconRes;
                        Intrinsics.checkNotNull(num);
                        return new Icon.Loaded(resources2.getDrawable(num.intValue(), BatterySaverTileMapper.this.theme), null);
                    }
                };
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                if (batterySaverTileModel.isPluggedIn()) {
                    builder.activationState = QSTileState.ActivationState.UNAVAILABLE;
                    builder.supportedActions = Collections.singleton(QSTileState.UserAction.LONG_CLICK);
                    builder.secondaryLabel = "";
                } else if (batterySaverTileModel.isPowerSaving()) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.supportedActions = SetsKt__SetsKt.setOf(QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK);
                    BatterySaverTileModel batterySaverTileModel2 = batterySaverTileModel;
                    if (batterySaverTileModel2 instanceof BatterySaverTileModel.Extreme) {
                        String string2 = BatterySaverTileMapper.this.resources.getString(((BatterySaverTileModel.Extreme) batterySaverTileModel2).isExtremeSaving ? R.string.extreme_battery_saver_text : R.string.standard_battery_saver_text);
                        builder.secondaryLabel = string2;
                        builder.stateDescription = string2;
                    } else {
                        builder.secondaryLabel = "";
                    }
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.supportedActions = SetsKt__SetsKt.setOf(QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK);
                    builder.secondaryLabel = "";
                }
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
