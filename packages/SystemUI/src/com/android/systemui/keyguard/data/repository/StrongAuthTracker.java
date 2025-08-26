package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import android.content.pm.UserInfo;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
    public final StateFlowImpl _authFlags;
    public final StateFlowImpl _nonStrongBiometricAllowed;
    public final ChannelFlowTransformLatest currentUserAuthFlags;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isNonStrongBiometricAllowed;
    public final StrongAuthTracker$special$$inlined$map$2 isStrongBiometricAllowed;
    public final UserRepository userRepository;

    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.keyguard.data.repository.StrongAuthTracker$special$$inlined$map$2, kotlinx.coroutines.flow.Flow] */
    public StrongAuthTracker(UserRepository userRepository, Context context) {
        super(context);
        this.userRepository = userRepository;
        final UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) userRepository).selectedUserInfo;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.data.repository.StrongAuthTracker$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.repository.StrongAuthTracker$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.data.repository.StrongAuthTracker$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Integer num = new Integer(((UserInfo) obj).id);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = userRepositoryImpl$special$$inlined$map$2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this._authFlags = StateFlowKt.MutableStateFlow(new AuthenticationFlags(((UserRepositoryImpl) userRepository).getSelectedUserInfo().id, getStrongAuthForUser(((UserRepositoryImpl) userRepository).getSelectedUserInfo().id)));
        this._nonStrongBiometricAllowed = StateFlowKt.MutableStateFlow(new Pair(Integer.valueOf(((UserRepositoryImpl) userRepository).getSelectedUserInfo().id), Boolean.valueOf(isNonStrongBiometricAllowedAfterIdleTimeout(((UserRepositoryImpl) userRepository).getSelectedUserInfo().id))));
        final ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(flowDistinctUntilChanged, new StrongAuthTracker$special$$inlined$flatMapLatest$1(null, this));
        this.currentUserAuthFlags = channelFlowTransformLatestTransformLatest;
        ?? r1 = new Flow() { // from class: com.android.systemui.keyguard.data.repository.StrongAuthTracker$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.data.repository.StrongAuthTracker$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ StrongAuthTracker this$0;

                /* renamed from: com.android.systemui.keyguard.data.repository.StrongAuthTracker$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, StrongAuthTracker strongAuthTracker) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = strongAuthTracker;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(this.this$0.isBiometricAllowedForUser(true, ((AuthenticationFlags) obj).userId));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelFlowTransformLatestTransformLatest.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.isStrongBiometricAllowed = r1;
        this.isNonStrongBiometricAllowed = BiometricSettingsRepositoryKt.access$and(FlowKt.transformLatest(flowDistinctUntilChanged, new StrongAuthTracker$special$$inlined$flatMapLatest$2(null, this)), r1);
    }

    public final void onIsNonStrongBiometricAllowedChanged(int i) {
        boolean zIsNonStrongBiometricAllowedAfterIdleTimeout = isNonStrongBiometricAllowedAfterIdleTimeout(i);
        this._nonStrongBiometricAllowed.updateState(null, new Pair(Integer.valueOf(i), Boolean.valueOf(zIsNonStrongBiometricAllowedAfterIdleTimeout)));
        Log.d("BiometricsRepositoryImpl", "onIsNonStrongBiometricAllowedChanged for userId: " + i + ", " + zIsNonStrongBiometricAllowedAfterIdleTimeout);
    }

    public final void onStrongAuthRequiredChanged(int i) {
        int strongAuthForUser = getStrongAuthForUser(i);
        this._authFlags.updateState(null, new AuthenticationFlags(i, strongAuthForUser));
        KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("onStrongAuthRequiredChanged for userId: "), i, ", flag value: ", strongAuthForUser, "BiometricsRepositoryImpl");
    }
}
