package com.android.systemui.qs.tiles.impl.rotation.domain.interactor;

import android.R;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.UserHandle;
import com.android.systemui.camera.data.repository.CameraAutoRotateRepository;
import com.android.systemui.camera.data.repository.CameraAutoRotateRepositoryImpl;
import com.android.systemui.camera.data.repository.CameraSensorPrivacyRepository;
import com.android.systemui.camera.data.repository.CameraSensorPrivacyRepositoryImpl;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.rotation.domain.model.RotationLockTileModel;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.kotlin.BatteryControllerExtKt;
import com.android.systemui.util.kotlin.RotationLockControllerExtKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class RotationLockTileDataInteractor implements QSTileDataInteractor {
    public final BatteryController batteryController;
    public final CameraAutoRotateRepository cameraAutoRotateRepository;
    public final CameraSensorPrivacyRepository cameraSensorPrivacyRepository;
    public final PackageManager packageManager;
    public final Resources resources;
    public final RotationLockController rotationLockController;

    /* renamed from: com.android.systemui.qs.tiles.impl.rotation.domain.interactor.RotationLockTileDataInteractor$tileData$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function5 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        /* synthetic */ boolean Z$2;
        /* synthetic */ boolean Z$3;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(5, continuation);
        }

        @Override // kotlin.jvm.functions.Function5
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
            boolean zBooleanValue4 = ((Boolean) obj4).booleanValue();
            AnonymousClass1 anonymousClass1 = RotationLockTileDataInteractor.this.new AnonymousClass1((Continuation) obj5);
            anonymousClass1.Z$0 = zBooleanValue;
            anonymousClass1.Z$1 = zBooleanValue2;
            anonymousClass1.Z$2 = zBooleanValue3;
            anonymousClass1.Z$3 = zBooleanValue4;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            String rotationResolverPackageName;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            boolean z3 = this.Z$2;
            boolean z4 = this.Z$3;
            RotationLockTileDataInteractor rotationLockTileDataInteractor = RotationLockTileDataInteractor.this;
            return new RotationLockTileModel(z, rotationLockTileDataInteractor.resources.getBoolean(R.bool.config_allowTheaterModeWakeFromKey) && !z3 && !z2 && (rotationResolverPackageName = rotationLockTileDataInteractor.packageManager.getRotationResolverPackageName()) != null && rotationLockTileDataInteractor.packageManager.checkPermission("android.permission.CAMERA", rotationResolverPackageName) == 0 && z4);
        }
    }

    public RotationLockTileDataInteractor(RotationLockController rotationLockController, BatteryController batteryController, CameraAutoRotateRepository cameraAutoRotateRepository, CameraSensorPrivacyRepository cameraSensorPrivacyRepository, PackageManager packageManager, Resources resources) {
        this.rotationLockController = rotationLockController;
        this.batteryController = batteryController;
        this.cameraAutoRotateRepository = cameraAutoRotateRepository;
        this.cameraSensorPrivacyRepository = cameraSensorPrivacyRepository;
        this.packageManager = packageManager;
        this.resources = resources;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.combine(RotationLockControllerExtKt.isRotationLockEnabled(this.rotationLockController), ((CameraSensorPrivacyRepositoryImpl) this.cameraSensorPrivacyRepository).isEnabled(userHandle), BatteryControllerExtKt.isBatteryPowerSaveEnabled(this.batteryController), ((CameraAutoRotateRepositoryImpl) this.cameraAutoRotateRepository).isCameraAutoRotateSettingEnabled(userHandle), new AnonymousClass1(null));
    }
}
