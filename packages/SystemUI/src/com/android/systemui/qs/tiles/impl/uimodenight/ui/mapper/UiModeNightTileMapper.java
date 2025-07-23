package com.android.systemui.qs.tiles.impl.uimodenight.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.model.UiModeNightTileModel;
import java.time.format.DateTimeFormatter;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class UiModeNightTileMapper implements QSTileDataToStateMapper {
    public static final DateTimeFormatter formatter12Hour;
    public static final DateTimeFormatter formatter24Hour;
    public final Resources resources;
    public final Resources.Theme theme;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        formatter12Hour = DateTimeFormatter.ofPattern("hh:mm a");
        formatter24Hour = DateTimeFormatter.ofPattern("HH:mm");
    }

    public UiModeNightTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final UiModeNightTileModel uiModeNightTileModel = (UiModeNightTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.uimodenight.ui.mapper.UiModeNightTileMapper$$ExternalSyntheticLambda0
            /* JADX WARN: Removed duplicated region for block: B:10:0x00ab  */
            /* JADX WARN: Removed duplicated region for block: B:15:0x00e4  */
            /* JADX WARN: Removed duplicated region for block: B:18:0x0104  */
            /* JADX WARN: Removed duplicated region for block: B:22:0x010b  */
            /* JADX WARN: Removed duplicated region for block: B:23:0x00e8  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x00bc  */
            /* JADX WARN: Removed duplicated region for block: B:34:0x0090  */
            /* JADX WARN: Removed duplicated region for block: B:7:0x008d  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object mo779invoke(java.lang.Object r11) {
                /*
                    Method dump skipped, instructions count: 284
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.uimodenight.ui.mapper.UiModeNightTileMapper$$ExternalSyntheticLambda0.mo779invoke(java.lang.Object):java.lang.Object");
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
