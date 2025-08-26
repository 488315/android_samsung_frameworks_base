package com.android.systemui.qs.tiles.impl.rotation.ui.mapper;

import android.content.res.Resources;
import android.hardware.devicestate.DeviceStateManager;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper;
import com.android.systemui.qs.tiles.impl.rotation.domain.model.RotationLockTileModel;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.util.Utils;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class RotationLockTileMapper implements QSTileDataToStateMapper {
    public final DevicePostureController devicePostureController;
    public final DeviceStateManager deviceStateManager;
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
    }

    public RotationLockTileMapper(Resources resources, Resources.Theme theme, DevicePostureController devicePostureController, DeviceStateManager deviceStateManager) {
        this.resources = resources;
        this.theme = theme;
        this.devicePostureController = devicePostureController;
        this.deviceStateManager = deviceStateManager;
    }

    @Override // com.android.systemui.qs.tiles.base.ui.model.QSTileDataToStateMapper
    public final QSTileState map(QSTileConfig qSTileConfig, Object obj) {
        final RotationLockTileModel rotationLockTileModel = (RotationLockTileModel) obj;
        QSTileState.Companion companion = QSTileState.Companion;
        Resources resources = this.resources;
        Resources.Theme theme = this.theme;
        QSTileUIConfig qSTileUIConfig = qSTileConfig.uiConfig;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.tiles.impl.rotation.ui.mapper.RotationLockTileMapper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                int i;
                QSTileState.Builder builder = (QSTileState.Builder) obj2;
                RotationLockTileMapper rotationLockTileMapper = this.f$0;
                builder.label = rotationLockTileMapper.resources.getString(R.string.quick_settings_rotation_unlocked_label);
                builder.contentDescription = rotationLockTileMapper.resources.getString(R.string.accessibility_quick_settings_rotation);
                RotationLockTileModel rotationLockTileModel2 = rotationLockTileModel;
                if (rotationLockTileModel2.isRotationLocked) {
                    builder.activationState = QSTileState.ActivationState.INACTIVE;
                    builder.secondaryLabel = "";
                    i = R.drawable.qs_auto_rotate_icon_off;
                } else {
                    builder.activationState = QSTileState.ActivationState.ACTIVE;
                    builder.secondaryLabel = rotationLockTileModel2.isCameraRotationEnabled ? rotationLockTileMapper.resources.getString(R.string.rotation_lock_camera_rotation_on) : "";
                    i = R.drawable.qs_auto_rotate_icon_on;
                }
                builder.icon = new Icon.Loaded(rotationLockTileMapper.resources.getDrawable(i, rotationLockTileMapper.theme), null, Integer.valueOf(i));
                if (Utils.isDeviceFoldable(rotationLockTileMapper.resources, rotationLockTileMapper.deviceStateManager)) {
                    String str = rotationLockTileMapper.resources.getStringArray(R.array.tile_states_rotation)[builder.activationState == QSTileState.ActivationState.ACTIVE ? (char) 2 : (char) 1];
                    String string = ((DevicePostureControllerImpl) rotationLockTileMapper.devicePostureController).getDevicePosture() == 1 ? rotationLockTileMapper.resources.getString(R.string.quick_settings_rotation_posture_folded) : rotationLockTileMapper.resources.getString(R.string.quick_settings_rotation_posture_unfolded);
                    string.getClass();
                    builder.secondaryLabel = rotationLockTileMapper.resources.getString(R.string.rotation_tile_with_posture_secondary_label_template, str, string);
                }
                builder.stateDescription = builder.secondaryLabel;
                builder.sideViewIcon = QSTileState.SideViewIcon.None.INSTANCE;
                builder.supportedActions = ArraysKt___ArraysKt.toSet(new QSTileState.UserAction[]{QSTileState.UserAction.CLICK, QSTileState.UserAction.LONG_CLICK});
                return Unit.INSTANCE;
            }
        };
        companion.getClass();
        return QSTileState.Companion.build(resources, theme, qSTileUIConfig, function1);
    }
}
