package com.android.systemui.biometrics.data.repository;

import android.os.Handler;
import com.android.systemui.util.settings.SecureSettings;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FaceUserSettingsRepositoryImpl implements FaceUserSettingsRepository {
    public final StateFlowImpl _alwaysRequireConfirmationInApps;
    public final ReadonlyStateFlow alwaysRequireConfirmationInApps;
    public final Handler mainHandler;
    public final SecureSettings secureSettings;
    public final int userId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Empty implements FaceUserSettingsRepository {
        public static final Empty INSTANCE = new Empty();
        public static final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 alwaysRequireConfirmationInApps = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);

        private Empty() {
        }

        @Override // com.android.systemui.biometrics.data.repository.FaceUserSettingsRepository
        public final Flow getAlwaysRequireConfirmationInApps() {
            return alwaysRequireConfirmationInApps;
        }
    }

    public FaceUserSettingsRepositoryImpl(int i, Handler handler, SecureSettings secureSettings) {
        this.userId = i;
        this.mainHandler = handler;
        this.secureSettings = secureSettings;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._alwaysRequireConfirmationInApps = MutableStateFlow;
        this.alwaysRequireConfirmationInApps = FlowKt.asStateFlow(MutableStateFlow);
    }

    @Override // com.android.systemui.biometrics.data.repository.FaceUserSettingsRepository
    public final Flow getAlwaysRequireConfirmationInApps() {
        return this.alwaysRequireConfirmationInApps;
    }
}
