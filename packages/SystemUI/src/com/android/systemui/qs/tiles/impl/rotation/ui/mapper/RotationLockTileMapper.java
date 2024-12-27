package com.android.systemui.qs.tiles.impl.rotation.ui.mapper;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.rotation.domain.model.RotationLockTileModel;
import com.android.systemui.qs.tiles.viewmodel.QSTileConfig;
import com.android.systemui.qs.tiles.viewmodel.QSTileState;
import com.android.systemui.qs.tiles.viewmodel.QSTileUIConfig;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RotationLockTileMapper implements QSTileDataToStateMapper {
    public final DevicePostureController devicePostureController;
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

    static {
        new Companion(null);
    }

    public RotationLockTileMapper(Resources resources, Resources.Theme theme, DevicePostureController devicePostureController) {
        this.resources = resources;
        this.theme = theme;
        this.devicePostureController = devicePostureController;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final RotationLockTileModel rotationLockTileModel = (RotationLockTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.rotation.ui.mapper.RotationLockTileMapper$map$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                final QSTileState.Builder builder = (QSTileState.Builder) obj2;
                builder.label = RotationLockTileMapper.this.resources.getString(R.string.quick_settings_rotation_unlocked_label);
                builder.contentDescription = RotationLockTileMapper.this.resources.getString(R.string.accessibility_quick_settings_rotation);
                RotationLockTileModel rotationLockTileModel2 = rotationLockTileModel;
                if (rotationLockTileModel2.isRotationLocked) {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = "";
                    builder.iconRes = Integer.valueOf(R.drawable.qs_auto_rotate_icon_off);
                } else {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = rotationLockTileModel2.isCameraRotationEnabled ? RotationLockTileMapper.this.resources.getString(R.string.rotation_lock_camera_rotation_on) : "";
                    builder.iconRes = Integer.valueOf(R.drawable.qs_auto_rotate_icon_on);
                }
                final RotationLockTileMapper rotationLockTileMapper = RotationLockTileMapper.this;
                builder.icon = new Function0() { // from class: com.android.systemui.qs.tiles.impl.rotation.ui.mapper.RotationLockTileMapper$map$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Resources resources2 = RotationLockTileMapper.this.resources;
                        Integer num = builder.iconRes;
                        Intrinsics.checkNotNull(num);
                        return new Icon.Loaded(resources2.getDrawable(num.intValue(), RotationLockTileMapper.this.theme), null);
                    }
                };
                if (!(RotationLockTileMapper.this.resources.getIntArray(android.R.array.preloaded_freeform_multi_window_drawables).length == 0)) {
                    RotationLockTileMapper rotationLockTileMapper2 = RotationLockTileMapper.this;
                    String str = rotationLockTileMapper2.resources.getStringArray(R.array.tile_states_rotation)[builder.activationState == QSTileState.ActivationState.ACTIVE ? (char) 2 : (char) 1];
                    String string = ((DevicePostureControllerImpl) rotationLockTileMapper2.devicePostureController).getDevicePosture() == 1 ? rotationLockTileMapper2.resources.getString(R.string.quick_settings_rotation_posture_folded) : rotationLockTileMapper2.resources.getString(R.string.quick_settings_rotation_posture_unfolded);
                    Intrinsics.checkNotNull(string);
                    builder.secondaryLabel = rotationLockTileMapper2.resources.getString(R.string.rotation_tile_with_posture_secondary_label_template, str, string);
                }
                builder.stateDescription = builder.secondaryLabel;
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                builder.supportedActions = SetsKt__SetsKt.setOf(QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK);
                return Unit.INSTANCE;
            }
        };
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
