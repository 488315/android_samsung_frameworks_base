package com.android.systemui.qs.tiles.impl.night.ui;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.base.logging.QSTileLogger;
import com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class NightDisplayTileMapper implements QSTileDataToStateMapper {
    public static final DateTimeFormatter formatter12Hour;
    public static final DateTimeFormatter formatter24Hour;
    public static final TileSpec spec;
    public final QSTileLogger logger;
    public final Resources resources;
    public final Resources.Theme theme;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final NightDisplayTileModel nightDisplayTileModel = (NightDisplayTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.night.ui.NightDisplayTileMapper$map$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LocalTime localTime;
                int i;
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                builder.label = NightDisplayTileMapper.this.resources.getString(R.string.quick_settings_night_display_label);
                builder.supportedActions = SetsKt__SetsKt.setOf(QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK);
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                if (nightDisplayTileModel.isActivated()) {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.iconRes = Integer.valueOf(R.drawable.qs_nightlight_icon_on);
                } else {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.iconRes = Integer.valueOf(R.drawable.qs_nightlight_icon_off);
                }
                Resources resources2 = NightDisplayTileMapper.this.resources;
                Integer num = builder.iconRes;
                Intrinsics.checkNotNull(num);
                Drawable drawable = resources2.getDrawable(num.intValue(), NightDisplayTileMapper.this.theme);
                String str = null;
                final Icon.Loaded loaded = new Icon.Loaded(drawable, null);
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.night.ui.NightDisplayTileMapper$map$1.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Icon.Loaded.this;
                    }
                };
                NightDisplayTileMapper nightDisplayTileMapper = NightDisplayTileMapper.this;
                NightDisplayTileModel nightDisplayTileModel2 = nightDisplayTileModel;
                Resources resources3 = nightDisplayTileMapper.resources;
                if (nightDisplayTileModel2 instanceof NightDisplayTileModel.AutoModeTwilight) {
                    if (((NightDisplayTileModel.AutoModeTwilight) nightDisplayTileModel2).isLocationEnabled) {
                        str = resources3.getString(((NightDisplayTileModel.AutoModeTwilight) nightDisplayTileModel2).isActivated ? R.string.quick_settings_night_secondary_label_until_sunrise : R.string.quick_settings_night_secondary_label_on_at_sunset);
                    }
                } else if (nightDisplayTileModel2 instanceof NightDisplayTileModel.AutoModeOff) {
                    str = resources3.getStringArray(R.array.tile_states_night)[((NightDisplayTileModel.AutoModeOff) nightDisplayTileModel2).isActivated ? (char) 2 : (char) 1];
                } else {
                    if (!(nightDisplayTileModel2 instanceof NightDisplayTileModel.AutoModeCustom)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    if (((NightDisplayTileModel.AutoModeCustom) nightDisplayTileModel2).isActivated) {
                        localTime = ((NightDisplayTileModel.AutoModeCustom) nightDisplayTileModel2).endTime;
                        if (localTime != null) {
                            i = R.string.quick_settings_secondary_label_until;
                        }
                    } else {
                        localTime = ((NightDisplayTileModel.AutoModeCustom) nightDisplayTileModel2).startTime;
                        if (localTime != null) {
                            i = R.string.quick_settings_night_secondary_label_on_at;
                        }
                    }
                    try {
                        str = resources3.getString(i, (((NightDisplayTileModel.AutoModeCustom) nightDisplayTileModel2).is24HourFormat ? NightDisplayTileMapper.formatter24Hour : NightDisplayTileMapper.formatter12Hour).format(localTime));
                    } catch (DateTimeException e) {
                        String valueOf = String.valueOf(e.getMessage());
                        DateTimeFormatter dateTimeFormatter = NightDisplayTileMapper.formatter12Hour;
                        nightDisplayTileMapper.logger.logWarning(valueOf);
                    }
                }
                builder.secondaryLabel = str;
                builder.contentDescription = TextUtils.isEmpty(str) ? builder.label : TextUtils.concat(builder.label, ", ", builder.secondaryLabel);
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
