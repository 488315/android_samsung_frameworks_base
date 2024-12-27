package com.android.systemui.qs.tiles.impl.uimodenight.domain;

import android.content.res.Resources;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.uimodenight.domain.model.UiModeNightTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import java.time.format.DateTimeFormatter;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class UiModeNightTileMapper implements QSTileDataToStateMapper {
    public static final Companion Companion = new Companion(null);
    public static final DateTimeFormatter formatter12Hour = DateTimeFormatter.ofPattern("hh:mm a");
    public static final DateTimeFormatter formatter24Hour = DateTimeFormatter.ofPattern("HH:mm");
    public final Resources resources;
    public final Resources.Theme theme;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public UiModeNightTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final UiModeNightTileModel uiModeNightTileModel = (UiModeNightTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.uimodenight.domain.UiModeNightTileMapper$map$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r9) {
                /*
                    Method dump skipped, instructions count: 332
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.uimodenight.domain.UiModeNightTileMapper$map$1$1.invoke(java.lang.Object):java.lang.Object");
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
