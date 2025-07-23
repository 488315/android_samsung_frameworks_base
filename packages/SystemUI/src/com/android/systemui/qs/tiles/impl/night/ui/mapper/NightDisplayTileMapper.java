package com.android.systemui.qs.tiles.impl.night.ui.mapper;

import android.content.res.Resources;
import android.text.TextUtils;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NightDisplayTileMapper implements QSTileDataToStateMapper {
    public static final DateTimeFormatter formatter12Hour;
    public static final DateTimeFormatter formatter24Hour;
    public static final TileSpec spec;
    public final QSTileLogger logger;
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
        TileSpec.Companion.getClass();
        spec = TileSpec.Companion.create("night");
    }

    public NightDisplayTileMapper(Resources resources, Resources.Theme theme, QSTileLogger qSTileLogger) {
        this.resources = resources;
        this.theme = theme;
        this.logger = qSTileLogger;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final NightDisplayTileModel nightDisplayTileModel = (NightDisplayTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.night.ui.mapper.NightDisplayTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                int i;
                LocalTime localTime;
                int i2;
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                NightDisplayTileMapper nightDisplayTileMapper = NightDisplayTileMapper.this;
                builder.label = nightDisplayTileMapper.resources.getString(R.string.quick_settings_night_display_label);
                builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                NightDisplayTileModel nightDisplayTileModel2 = nightDisplayTileModel;
                if (nightDisplayTileModel2.isActivated()) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    i = R.drawable.qs_nightlight_icon_on;
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    i = R.drawable.qs_nightlight_icon_off;
                }
                String str = null;
                builder.icon = new Icon.Loaded(nightDisplayTileMapper.resources.getDrawable(i, nightDisplayTileMapper.theme), null, Integer.valueOf(i));
                Resources resources2 = nightDisplayTileMapper.resources;
                if (nightDisplayTileModel2 instanceof NightDisplayTileModel.AutoModeTwilight) {
                    NightDisplayTileModel.AutoModeTwilight autoModeTwilight = (NightDisplayTileModel.AutoModeTwilight) nightDisplayTileModel2;
                    if (autoModeTwilight.isLocationEnabled) {
                        str = resources2.getString(autoModeTwilight.isActivated ? R.string.quick_settings_night_secondary_label_until_sunrise : R.string.quick_settings_night_secondary_label_on_at_sunset);
                    }
                } else if (nightDisplayTileModel2 instanceof NightDisplayTileModel.AutoModeOff) {
                    str = resources2.getStringArray(R.array.tile_states_night)[((NightDisplayTileModel.AutoModeOff) nightDisplayTileModel2).isActivated ? (char) 2 : (char) 1];
                } else {
                    if (!(nightDisplayTileModel2 instanceof NightDisplayTileModel.AutoModeCustom)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    NightDisplayTileModel.AutoModeCustom autoModeCustom = (NightDisplayTileModel.AutoModeCustom) nightDisplayTileModel2;
                    if (autoModeCustom.isActivated) {
                        localTime = autoModeCustom.endTime;
                        if (localTime != null) {
                            i2 = R.string.quick_settings_secondary_label_until;
                        }
                    } else {
                        localTime = autoModeCustom.startTime;
                        if (localTime != null) {
                            i2 = R.string.quick_settings_night_secondary_label_on_at;
                        }
                    }
                    try {
                        str = resources2.getString(i2, (((NightDisplayTileModel.AutoModeCustom) nightDisplayTileModel2).is24HourFormat ? NightDisplayTileMapper.formatter24Hour : NightDisplayTileMapper.formatter12Hour).format(localTime));
                    } catch (DateTimeException e) {
                        String valueOf = String.valueOf(e.getMessage());
                        TileSpec tileSpec = NightDisplayTileMapper.spec;
                        LogBuffer logBuffer = nightDisplayTileMapper.logger.getLogBuffer(tileSpec);
                        LogMessage obtain = logBuffer.obtain(QSTileLogger.getLogTag(tileSpec), LogLevel.WARNING, new QSTileLogger$$ExternalSyntheticLambda0(1), null);
                        ((LogMessageImpl) obtain).str1 = valueOf;
                        logBuffer.commit(obtain);
                    }
                }
                builder.secondaryLabel = str;
                builder.contentDescription = TextUtils.isEmpty(str) ? builder.label : TextUtils.concat(builder.label, ", ", builder.secondaryLabel);
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
