package com.android.systemui.qs.tiles.impl.work.ui.mapper;

import android.app.admin.DevicePolicyManager;
import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.work.domain.model.WorkModeTileModel;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WorkModeTileMapper implements QSTileDataToStateMapper {
    public final DevicePolicyManager devicePolicyManager;
    public final Resources resources;
    public final Resources.Theme theme;

    public WorkModeTileMapper(Resources resources, Resources.Theme theme, DevicePolicyManager devicePolicyManager) {
        this.resources = resources;
        this.theme = theme;
        this.devicePolicyManager = devicePolicyManager;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final WorkModeTileModel workModeTileModel = (WorkModeTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.work.ui.mapper.WorkModeTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                final WorkModeTileMapper workModeTileMapper = WorkModeTileMapper.this;
                String string = workModeTileMapper.devicePolicyManager.getResources().getString("SystemUi.QS_WORK_PROFILE_LABEL", new Supplier() { // from class: com.android.systemui.qs.tiles.impl.work.ui.mapper.WorkModeTileMapper$getTileLabel$1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return WorkModeTileMapper.this.resources.getString(R.string.quick_settings_work_mode_label);
                    }
                });
                string.getClass();
                builder.label = string;
                builder.contentDescription = string;
                builder.icon = new Icon.Loaded(workModeTileMapper.resources.getDrawable(17304606, workModeTileMapper.theme), null, 17304606);
                WorkModeTileModel workModeTileModel2 = workModeTileModel;
                if (workModeTileModel2 instanceof WorkModeTileModel.HasActiveProfile) {
                    if (((WorkModeTileModel.HasActiveProfile) workModeTileModel2).isEnabled) {
                        builder.activationState = QSTileState.ActivationState.ACTIVE;
                        builder.secondaryLabel = "";
                    } else {
                        builder.activationState = QSTileState.ActivationState.INACTIVE;
                        builder.secondaryLabel = workModeTileMapper.resources.getString(R.string.quick_settings_work_mode_paused_state);
                    }
                    builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                } else {
                    if (!(workModeTileModel2 instanceof WorkModeTileModel.NoActiveProfile)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    builder.activationState = QSTileState.ActivationState.UNAVAILABLE;
                    builder.secondaryLabel = workModeTileMapper.resources.getStringArray(R.array.tile_states_work)[0];
                    builder.supportedActions = EmptySet.INSTANCE;
                }
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
