package com.android.systemui.qs.tiles.impl.work.ui;

import android.app.admin.DevicePolicyManager;
import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.work.domain.model.WorkModeTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final WorkModeTileModel workModeTileModel = (WorkModeTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.work.ui.WorkModeTileMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                final QSTileState.Builder builder = (QSTileState.Builder) obj2;
                final WorkModeTileMapper workModeTileMapper = WorkModeTileMapper.this;
                String string = workModeTileMapper.devicePolicyManager.getResources().getString("SystemUi.QS_WORK_PROFILE_LABEL", new Supplier() { // from class: com.android.systemui.qs.tiles.impl.work.ui.WorkModeTileMapper$getTileLabel$1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return WorkModeTileMapper.this.resources.getString(R.string.quick_settings_work_mode_label);
                    }
                });
                Intrinsics.checkNotNull(string);
                builder.label = string;
                builder.contentDescription = string;
                builder.iconRes = 17304492;
                final WorkModeTileMapper workModeTileMapper2 = WorkModeTileMapper.this;
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.work.ui.WorkModeTileMapper$map$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Resources resources2 = WorkModeTileMapper.this.resources;
                        Integer num = builder.iconRes;
                        Intrinsics.checkNotNull(num);
                        return new Icon.Loaded(resources2.getDrawable(num.intValue(), WorkModeTileMapper.this.theme), null);
                    }
                };
                WorkModeTileModel workModeTileModel2 = workModeTileModel;
                if (workModeTileModel2 instanceof WorkModeTileModel.HasActiveProfile) {
                    if (((WorkModeTileModel.HasActiveProfile) workModeTileModel2).isEnabled) {
                        builder.activationState = QSTileState.ActivationState.ACTIVE;
                        builder.secondaryLabel = "";
                    } else {
                        builder.activationState = QSTileState.ActivationState.INACTIVE;
                        builder.secondaryLabel = WorkModeTileMapper.this.resources.getString(R.string.quick_settings_work_mode_paused_state);
                    }
                    builder.supportedActions = SetsKt__SetsKt.setOf(QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK);
                } else if (workModeTileModel2 instanceof WorkModeTileModel.NoActiveProfile) {
                    builder.activationState = QSTileState.ActivationState.UNAVAILABLE;
                    builder.secondaryLabel = WorkModeTileMapper.this.resources.getStringArray(R.array.tile_states_work)[0];
                    builder.supportedActions = EmptySet.INSTANCE;
                }
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
