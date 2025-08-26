package com.android.systemui.qs.tiles.impl.uimodenight.ui.mapper;

import android.content.res.Resources;
import android.text.TextUtils;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor.model.UiModeNightTileModel;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class UiModeNightTileMapper implements QSTileDataToStateMapper {
    public static final DateTimeFormatter formatter12Hour;
    public static final DateTimeFormatter formatter24Hour;
    public final Resources resources;
    public final Resources.Theme theme;

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
            /* JADX WARN: Removed duplicated region for block: B:41:0x008d  */
            /* JADX WARN: Removed duplicated region for block: B:42:0x0090  */
            /* JADX WARN: Removed duplicated region for block: B:45:0x00ab  */
            /* JADX WARN: Removed duplicated region for block: B:48:0x00bc  */
            /* JADX WARN: Removed duplicated region for block: B:60:0x00e4  */
            /* JADX WARN: Removed duplicated region for block: B:61:0x00e8  */
            /* JADX WARN: Removed duplicated region for block: B:64:0x0104  */
            /* JADX WARN: Removed duplicated region for block: B:65:0x010b  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object mo781invoke(Object obj2) {
                boolean z;
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                DateTimeFormatter dateTimeFormatter = UiModeNightTileMapper.formatter12Hour;
                UiModeNightTileModel uiModeNightTileModel2 = uiModeNightTileModel;
                boolean z2 = uiModeNightTileModel2.isPowerSave;
                UiModeNightTileMapper uiModeNightTileMapper = this;
                boolean z3 = uiModeNightTileModel2.isNightMode;
                if (z2) {
                    builder.secondaryLabel = uiModeNightTileMapper.resources.getString(R.string.quick_settings_dark_mode_secondary_label_battery_saver);
                } else {
                    int i = uiModeNightTileModel2.uiMode;
                    if (i != 0 || !uiModeNightTileModel2.isLocationEnabled) {
                        if (i == 3) {
                            int i2 = uiModeNightTileModel2.nightModeCustomType;
                            if (i2 == 0) {
                                builder.secondaryLabel = uiModeNightTileMapper.resources.getString(z3 ? R.string.quick_settings_dark_mode_secondary_label_until : R.string.quick_settings_dark_mode_secondary_label_on_at, (uiModeNightTileModel2.is24HourFormat ? UiModeNightTileMapper.formatter24Hour : UiModeNightTileMapper.formatter12Hour).format(z3 ? uiModeNightTileModel2.customNightModeEnd : uiModeNightTileModel2.customNightModeStart));
                            } else if (i2 == 1) {
                                builder.secondaryLabel = uiModeNightTileMapper.resources.getString(z3 ? R.string.quick_settings_dark_mode_secondary_label_until_bedtime_ends : R.string.quick_settings_dark_mode_secondary_label_on_at_bedtime);
                            } else {
                                builder.secondaryLabel = null;
                            }
                        } else {
                            builder.secondaryLabel = null;
                        }
                        z = true;
                        builder.contentDescription = !TextUtils.isEmpty(builder.secondaryLabel) ? builder.label : TextUtils.concat(builder.label, ", ", builder.secondaryLabel);
                        if (uiModeNightTileModel2.isPowerSave) {
                            QSTileState.ActivationState activationState = z3 ? QSTileState.ActivationState.ACTIVE : QSTileState.ActivationState.INACTIVE;
                            builder.activationState = activationState;
                            if (z) {
                                builder.secondaryLabel = activationState == QSTileState.ActivationState.INACTIVE ? uiModeNightTileMapper.resources.getStringArray(R.array.tile_states_dark)[1] : uiModeNightTileMapper.resources.getStringArray(R.array.tile_states_dark)[2];
                            }
                        } else {
                            builder.activationState = QSTileState.ActivationState.UNAVAILABLE;
                            if (z) {
                                builder.secondaryLabel = uiModeNightTileMapper.resources.getStringArray(R.array.tile_states_dark)[0];
                            }
                        }
                        int i3 = builder.activationState != QSTileState.ActivationState.ACTIVE ? R.drawable.qs_light_dark_theme_icon_on : R.drawable.qs_light_dark_theme_icon_off;
                        builder.icon = new Icon.Loaded(uiModeNightTileMapper.resources.getDrawable(i3, uiModeNightTileMapper.theme), null, Integer.valueOf(i3));
                        builder.supportedActions = builder.activationState != QSTileState.ActivationState.UNAVAILABLE ? Collections.singleton(QSTileState.UserAction.LONG_CLICK) : ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                        return Unit.INSTANCE;
                    }
                    builder.secondaryLabel = uiModeNightTileMapper.resources.getString(z3 ? R.string.quick_settings_dark_mode_secondary_label_until_sunrise : R.string.quick_settings_dark_mode_secondary_label_on_at_sunset);
                }
                z = false;
                builder.contentDescription = !TextUtils.isEmpty(builder.secondaryLabel) ? builder.label : TextUtils.concat(builder.label, ", ", builder.secondaryLabel);
                if (uiModeNightTileModel2.isPowerSave) {
                }
                if (builder.activationState != QSTileState.ActivationState.ACTIVE) {
                }
                builder.icon = new Icon.Loaded(uiModeNightTileMapper.resources.getDrawable(i3, uiModeNightTileMapper.theme), null, Integer.valueOf(i3));
                builder.supportedActions = builder.activationState != QSTileState.ActivationState.UNAVAILABLE ? Collections.singleton(QSTileState.UserAction.LONG_CLICK) : ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
