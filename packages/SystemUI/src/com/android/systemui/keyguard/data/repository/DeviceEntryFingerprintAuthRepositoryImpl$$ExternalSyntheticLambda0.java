package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final /* synthetic */ class DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ DeviceEntryFingerprintAuthRepositoryImpl f$1;

    public /* synthetic */ DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0(DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$1 = deviceEntryFingerprintAuthRepositoryImpl;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$0;
        DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl = this.f$1;
        switch (this.$r8$classId) {
            case 0:
                int i = DeviceEntryFingerprintAuthRepositoryImpl.$r8$clinit;
                Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1(deviceEntryFingerprintAuthRepositoryImpl, null));
                SharingStarted.Companion.getClass();
                break;
            case 1:
                deviceEntryFingerprintAuthRepositoryImpl.keyguardUpdateMonitor.removeCallback((DeviceEntryFingerprintAuthRepositoryImpl$authenticationStatus$1$callback$1) obj);
                break;
            case 2:
                deviceEntryFingerprintAuthRepositoryImpl.authController.removeCallback((DeviceEntryFingerprintAuthRepositoryImpl$availableFpSensorType$1$callback$1) obj);
                break;
            case 3:
                ChannelExt channelExt = ChannelExt.INSTANCE;
                Boolean boolValueOf = Boolean.valueOf(deviceEntryFingerprintAuthRepositoryImpl.keyguardUpdateMonitor.isFingerprintLockedOut());
                channelExt.getClass();
                ChannelExt.trySendWithFailureLogging((ProducerScope) obj, boolValueOf, "DeviceEntryFingerprintAuthRepositoryImpl", "onLockedOutStateChanged");
                break;
            case 4:
                deviceEntryFingerprintAuthRepositoryImpl.keyguardUpdateMonitor.removeCallback((DeviceEntryFingerprintAuthRepositoryImpl$isLockedOut$2$1$callback$1) obj);
                break;
            case 5:
                deviceEntryFingerprintAuthRepositoryImpl.keyguardUpdateMonitor.removeCallback((DeviceEntryFingerprintAuthRepositoryImpl$isRunning$1$callback$1) obj);
                break;
            default:
                deviceEntryFingerprintAuthRepositoryImpl.keyguardUpdateMonitor.removeCallback((DeviceEntryFingerprintAuthRepositoryImpl$shouldUpdateIndicatorVisibility$1$callback$1) obj);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ DeviceEntryFingerprintAuthRepositoryImpl$$ExternalSyntheticLambda0(CoroutineScope coroutineScope, DeviceEntryFingerprintAuthRepositoryImpl deviceEntryFingerprintAuthRepositoryImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = coroutineScope;
        this.f$1 = deviceEntryFingerprintAuthRepositoryImpl;
    }
}
