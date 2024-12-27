package com.android.systemui.biometrics.domain.interactor;

import android.app.ActivityTaskManager;
import com.android.systemui.biometrics.data.repository.BiometricStatusRepository;
import com.android.systemui.biometrics.data.repository.BiometricStatusRepositoryImpl;
import com.android.systemui.biometrics.data.repository.BiometricStatusRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepository;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepositoryImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BiometricStatusInteractorImpl implements BiometricStatusInteractor {
    public final ActivityTaskManager activityTaskManager;
    public final BiometricStatusRepositoryImpl$special$$inlined$map$2 fingerprintAcquiredStatus;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 sfpsAuthenticationReason;

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

    public BiometricStatusInteractorImpl(ActivityTaskManager activityTaskManager, BiometricStatusRepository biometricStatusRepository, FingerprintPropertyRepository fingerprintPropertyRepository) {
        this.activityTaskManager = activityTaskManager;
        BiometricStatusRepositoryImpl biometricStatusRepositoryImpl = (BiometricStatusRepositoryImpl) biometricStatusRepository;
        this.sfpsAuthenticationReason = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(biometricStatusRepositoryImpl.fingerprintAuthenticationReason, ((FingerprintPropertyRepositoryImpl) fingerprintPropertyRepository).sensorType, new BiometricStatusInteractorImpl$sfpsAuthenticationReason$1(this, null))), new BiometricStatusInteractorImpl$sfpsAuthenticationReason$2(null));
        this.fingerprintAcquiredStatus = biometricStatusRepositoryImpl.fingerprintAcquiredStatus;
    }
}
