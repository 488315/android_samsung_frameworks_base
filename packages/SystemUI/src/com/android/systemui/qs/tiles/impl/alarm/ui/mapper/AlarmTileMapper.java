package com.android.systemui.qs.tiles.impl.alarm.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.alarm.domain.model.AlarmTileModel;
import com.android.systemui.util.time.SystemClock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.TimeZone;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AlarmTileMapper implements QSTileDataToStateMapper {
    public static final DateTimeFormatter formatter12Hour;
    public static final DateTimeFormatter formatter24Hour;
    public static final DateTimeFormatter formatterDateOnly;
    public final SystemClock clock;
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
        formatter12Hour = DateTimeFormatter.ofPattern("E hh:mm a");
        formatter24Hour = DateTimeFormatter.ofPattern("E HH:mm");
        formatterDateOnly = DateTimeFormatter.ofPattern("E MMM d");
    }

    public AlarmTileMapper(Resources resources, Resources.Theme theme, SystemClock systemClock) {
        this.resources = resources;
        this.theme = theme;
        this.clock = systemClock;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final AlarmTileModel alarmTileModel = (AlarmTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.alarm.ui.mapper.AlarmTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                DateTimeFormatter dateTimeFormatter = AlarmTileMapper.formatter12Hour;
                AlarmTileModel alarmTileModel2 = AlarmTileModel.this;
                boolean z = alarmTileModel2 instanceof AlarmTileModel.NextAlarmSet;
                AlarmTileMapper alarmTileMapper = this;
                if (z) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    AlarmTileModel.NextAlarmSet nextAlarmSet = (AlarmTileModel.NextAlarmSet) alarmTileModel2;
                    LocalDateTime ofInstant = LocalDateTime.ofInstant(Instant.ofEpochMilli(nextAlarmSet.alarmClockInfo.getTriggerTime()), TimeZone.getDefault().toZoneId());
                    if (ofInstant.compareTo((ChronoLocalDateTime<?>) LocalDateTime.ofInstant(Instant.ofEpochMilli(alarmTileMapper.clock.currentTimeMillis()), TimeZone.getDefault().toZoneId()).plusWeeks(1L).withSecond(0).withNano(0)) >= 0) {
                        builder.secondaryLabel = AlarmTileMapper.formatterDateOnly.format(ofInstant);
                    } else {
                        builder.secondaryLabel = nextAlarmSet.is24HourFormat ? AlarmTileMapper.formatter24Hour.format(ofInstant) : AlarmTileMapper.formatter12Hour.format(ofInstant);
                    }
                } else {
                    if (!(alarmTileModel2 instanceof AlarmTileModel.NoAlarmSet)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = alarmTileMapper.resources.getString(R.string.qs_alarm_tile_no_alarm);
                }
                builder.icon = new Icon.Loaded(alarmTileMapper.resources.getDrawable(R.drawable.ic_alarm, alarmTileMapper.theme), null, Integer.valueOf(R.drawable.ic_alarm));
                builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                builder.contentDescription = builder.label;
                builder.supportedActions = Collections.singleton(QSTileState.UserAction.CLICK);
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
