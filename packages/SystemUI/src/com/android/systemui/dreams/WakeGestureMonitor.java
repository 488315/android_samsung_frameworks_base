package com.android.systemui.dreams;

import android.hardware.display.AmbientDisplayConfiguration;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class WakeGestureMonitor {
    public final AmbientDisplayConfiguration ambientDisplayConfiguration;
    public final AsyncSensorManager asyncSensorManager;
    public final Lazy pickupSensor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.dreams.WakeGestureMonitor$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return this.f$0.asyncSensorManager.getDefaultSensor(25);
        }
    });
    public final SecureSettings secureSettings;

    public WakeGestureMonitor(AmbientDisplayConfiguration ambientDisplayConfiguration, AsyncSensorManager asyncSensorManager, CoroutineContext coroutineContext, SecureSettings secureSettings, SelectedUserInteractor selectedUserInteractor) {
        this.ambientDisplayConfiguration = ambientDisplayConfiguration;
        this.asyncSensorManager = asyncSensorManager;
        this.secureSettings = secureSettings;
        FlowKt.flowOn(LatestConflatedKt.flatMapLatestConflated(LatestConflatedKt.flatMapLatestConflated(selectedUserInteractor.selectedUser, new WakeGestureMonitor$pickupGestureEnabled$1(this, null)), new WakeGestureMonitor$wakeUpDetected$1(this, null)), coroutineContext);
    }
}
