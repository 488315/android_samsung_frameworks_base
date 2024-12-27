package com.android.systemui.qs.tiles.impl.alarm.domain;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.alarm.domain.model.AlarmTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import com.android.systemui.util.time.SystemClock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.TimeZone;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AlarmTileMapper implements QSTileDataToStateMapper {
    public static final Companion Companion = new Companion(null);
    public static final DateTimeFormatter formatter12Hour = DateTimeFormatter.ofPattern("E hh:mm a");
    public static final DateTimeFormatter formatter24Hour = DateTimeFormatter.ofPattern("E HH:mm");
    public static final DateTimeFormatter formatterDateOnly = DateTimeFormatter.ofPattern("E MMM d");
    public final SystemClock clock;
    public final Resources resources;
    public final Resources.Theme theme;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AlarmTileMapper(Resources resources, Resources.Theme theme, SystemClock systemClock) {
        this.resources = resources;
        this.theme = theme;
        this.clock = systemClock;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final AlarmTileModel alarmTileModel = (AlarmTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.alarm.domain.AlarmTileMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                String format;
                final QSTileState.Builder builder = (QSTileState.Builder) obj2;
                AlarmTileModel alarmTileModel2 = AlarmTileModel.this;
                if (alarmTileModel2 instanceof AlarmTileModel.NextAlarmSet) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    LocalDateTime ofInstant = LocalDateTime.ofInstant(Instant.ofEpochMilli(((AlarmTileModel.NextAlarmSet) alarmTileModel2).alarmClockInfo.getTriggerTime()), TimeZone.getDefault().toZoneId());
                    if (ofInstant.compareTo((ChronoLocalDateTime<?>) LocalDateTime.ofInstant(Instant.ofEpochMilli(this.clock.currentTimeMillis()), TimeZone.getDefault().toZoneId()).plusWeeks(1L).withSecond(0).withNano(0)) >= 0) {
                        AlarmTileMapper.Companion.getClass();
                        builder.secondaryLabel = AlarmTileMapper.formatterDateOnly.format(ofInstant);
                    } else {
                        if (((AlarmTileModel.NextAlarmSet) AlarmTileModel.this).is24HourFormat) {
                            AlarmTileMapper.Companion.getClass();
                            format = AlarmTileMapper.formatter24Hour.format(ofInstant);
                        } else {
                            AlarmTileMapper.Companion.getClass();
                            format = AlarmTileMapper.formatter12Hour.format(ofInstant);
                        }
                        builder.secondaryLabel = format;
                    }
                } else if (alarmTileModel2 instanceof AlarmTileModel.NoAlarmSet) {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = this.resources.getString(R.string.qs_alarm_tile_no_alarm);
                }
                builder.iconRes = Integer.valueOf(R.drawable.ic_alarm);
                final AlarmTileMapper alarmTileMapper = this;
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.alarm.domain.AlarmTileMapper$map$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Resources resources2 = AlarmTileMapper.this.resources;
                        Integer num = builder.iconRes;
                        Intrinsics.checkNotNull(num);
                        return new Icon.Loaded(resources2.getDrawable(num.intValue(), AlarmTileMapper.this.theme), null);
                    }
                };
                builder.sideViewIcon = QSTileState.SideViewIcon.Chevron.INSTANCE;
                builder.contentDescription = builder.label;
                builder.supportedActions = Collections.singleton(QSTileState.UserAction.CLICK);
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
