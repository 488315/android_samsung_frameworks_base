package com.android.systemui.qs.tiles.impl.rotation.domain.interactor;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.UserHandle;
import com.android.systemui.camera.data.repository.CameraAutoRotateRepository;
import com.android.systemui.camera.data.repository.CameraAutoRotateRepositoryImpl;
import com.android.systemui.camera.data.repository.CameraSensorPrivacyRepository;
import com.android.systemui.camera.data.repository.CameraSensorPrivacyRepositoryImpl;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.kotlin.BatteryControllerExtKt;
import com.android.systemui.util.kotlin.RotationLockControllerExtKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RotationLockTileDataInteractor implements QSTileDataInteractor {
    public final BatteryController batteryController;
    public final CameraAutoRotateRepository cameraAutoRotateRepository;
    public final CameraSensorPrivacyRepository cameraSensorPrivacyRepository;
    public final PackageManager packageManager;
    public final Resources resources;
    public final RotationLockController rotationLockController;

    public RotationLockTileDataInteractor(RotationLockController rotationLockController, BatteryController batteryController, CameraAutoRotateRepository cameraAutoRotateRepository, CameraSensorPrivacyRepository cameraSensorPrivacyRepository, PackageManager packageManager, Resources resources) {
        this.rotationLockController = rotationLockController;
        this.batteryController = batteryController;
        this.cameraAutoRotateRepository = cameraAutoRotateRepository;
        this.cameraSensorPrivacyRepository = cameraSensorPrivacyRepository;
        this.packageManager = packageManager;
        this.resources = resources;
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlySharedFlow readonlySharedFlow) {
        return FlowKt.combine(RotationLockControllerExtKt.isRotationLockEnabled(this.rotationLockController), ((CameraSensorPrivacyRepositoryImpl) this.cameraSensorPrivacyRepository).isEnabled(userHandle), BatteryControllerExtKt.isBatteryPowerSaveEnabled(this.batteryController), ((CameraAutoRotateRepositoryImpl) this.cameraAutoRotateRepository).isCameraAutoRotateSettingEnabled(userHandle), new RotationLockTileDataInteractor$tileData$1(this, null));
    }
}
