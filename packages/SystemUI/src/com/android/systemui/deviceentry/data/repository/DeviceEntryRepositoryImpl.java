package com.android.systemui.deviceentry.data.repository;

import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceEntryRepositoryImpl implements DeviceEntryRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlyStateFlow isBypassEnabled;
    public final KeyguardBypassController keyguardBypassController;
    public final LockPatternUtils lockPatternUtils;
    public final UserRepository userRepository;

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

    public DeviceEntryRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository, LockPatternUtils lockPatternUtils, KeyguardBypassController keyguardBypassController) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.userRepository = userRepository;
        this.lockPatternUtils = lockPatternUtils;
        this.keyguardBypassController = keyguardBypassController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DeviceEntryRepositoryImpl$isBypassEnabled$1 deviceEntryRepositoryImpl$isBypassEnabled$1 = new DeviceEntryRepositoryImpl$isBypassEnabled$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(deviceEntryRepositoryImpl$isBypassEnabled$1);
        SharingStarted.Companion.getClass();
        this.isBypassEnabled = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.valueOf(keyguardBypassController.getBypassEnabled()));
    }

    public final Object isLockscreenEnabled(ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundDispatcher, new DeviceEntryRepositoryImpl$isLockscreenEnabled$2(this, null), continuationImpl);
    }
}
