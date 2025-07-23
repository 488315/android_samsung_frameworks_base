package com.android.systemui.qs.tiles.impl.screenrecord.domain.ui.mapper;

import android.content.res.Resources;
import android.text.TextUtils;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import java.util.Arrays;
import java.util.Collections;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenRecordTileMapper implements QSTileDataToStateMapper {
    public final Resources resources;
    public final Resources.Theme theme;

    public ScreenRecordTileMapper(Resources resources, Resources.Theme theme) {
        this.resources = resources;
        this.theme = theme;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final ScreenRecordModel screenRecordModel = (ScreenRecordModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.screenrecord.domain.ui.mapper.ScreenRecordTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                ScreenRecordTileMapper screenRecordTileMapper = ScreenRecordTileMapper.this;
                builder.label = screenRecordTileMapper.resources.getString(R.string.quick_settings_screen_record_label);
                builder.supportedActions = Collections.singleton(QSTileState.UserAction.CLICK);
                ScreenRecordModel screenRecordModel2 = screenRecordModel;
                boolean z = screenRecordModel2 instanceof ScreenRecordModel.Recording;
                int i = R.drawable.qs_screen_record_icon_on;
                if (z) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                    builder.secondaryLabel = screenRecordTileMapper.resources.getString(R.string.quick_settings_screen_record_stop);
                } else if (screenRecordModel2 instanceof ScreenRecordModel.Starting) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                    int i2 = StringCompanionObject.$r8$clinit;
                    builder.secondaryLabel = String.format("%d...", Arrays.copyOf(new Object[]{Long.valueOf(((ScreenRecordModel.Starting) screenRecordModel2).countdownSeconds)}, 1));
                } else {
                    if (!(screenRecordModel2 instanceof ScreenRecordModel.DoingNothing)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                    builder.secondaryLabel = screenRecordTileMapper.resources.getString(R.string.quick_settings_screen_record_start);
                    i = R.drawable.qs_screen_record_icon_off;
                }
                builder.icon = new Icon.Loaded(screenRecordTileMapper.resources.getDrawable(i, screenRecordTileMapper.theme), null, Integer.valueOf(i));
                builder.contentDescription = TextUtils.isEmpty(builder.secondaryLabel) ? builder.label : TextUtils.concat(builder.label, ", ", builder.secondaryLabel);
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
