package com.android.systemui.deviceentry.data.repository;

import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntryRepositoryImpl implements DeviceEntryRepository {
    public final StateFlowImpl _isLockscreenEnabled;
    public final CoroutineDispatcher backgroundDispatcher;
    public final StateFlowImpl deviceUnlockStatus;
    public final ReadonlyStateFlow isBypassEnabled;
    public final ReadonlyStateFlow isLockscreenEnabled;
    public final KeyguardBypassController keyguardBypassController;
    public final LockPatternUtils lockPatternUtils;
    public final UserRepository userRepository;

    public DeviceEntryRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository, LockPatternUtils lockPatternUtils, KeyguardBypassController keyguardBypassController) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.userRepository = userRepository;
        this.lockPatternUtils = lockPatternUtils;
        this.keyguardBypassController = keyguardBypassController;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._isLockscreenEnabled = MutableStateFlow;
        this.isLockscreenEnabled = FlowKt.asStateFlow(MutableStateFlow);
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new DeviceEntryRepositoryImpl$isBypassEnabled$1(this, null));
        SharingStarted.Companion.getClass();
        this.isBypassEnabled = FlowKt.stateIn(conflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.valueOf(keyguardBypassController.getBypassEnabled()));
        this.deviceUnlockStatus = StateFlowKt.MutableStateFlow(new DeviceUnlockStatus(false, null));
    }

    public final Object isLockscreenEnabled(ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundDispatcher, new DeviceEntryRepositoryImpl$isLockscreenEnabled$2(this, null), continuationImpl);
    }
}
