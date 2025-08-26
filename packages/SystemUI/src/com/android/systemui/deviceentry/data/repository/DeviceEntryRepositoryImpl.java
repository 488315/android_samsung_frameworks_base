package com.android.systemui.deviceentry.data.repository;

import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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

    /* renamed from: com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl$isLockscreenEnabled$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DeviceEntryRepositoryImpl.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = !DeviceEntryRepositoryImpl.this.lockPatternUtils.isLockScreenDisabled(((UserRepositoryImpl) DeviceEntryRepositoryImpl.this.userRepository).getSelectedUserInfo().id);
            DeviceEntryRepositoryImpl.this._isLockscreenEnabled.updateState(null, Boolean.valueOf(z));
            return Boolean.valueOf(z);
        }
    }

    public DeviceEntryRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository, LockPatternUtils lockPatternUtils, KeyguardBypassController keyguardBypassController) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.userRepository = userRepository;
        this.lockPatternUtils = lockPatternUtils;
        this.keyguardBypassController = keyguardBypassController;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._isLockscreenEnabled = stateFlowImplMutableStateFlow;
        this.isLockscreenEnabled = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new DeviceEntryRepositoryImpl$isBypassEnabled$1(this, null));
        SharingStarted.Companion.getClass();
        this.isBypassEnabled = FlowKt.stateIn(flowConflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.valueOf(keyguardBypassController.getBypassEnabled()));
        this.deviceUnlockStatus = StateFlowKt.MutableStateFlow(new DeviceUnlockStatus(false, null));
    }

    public final Object isLockscreenEnabled(ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(null), continuationImpl);
    }
}
