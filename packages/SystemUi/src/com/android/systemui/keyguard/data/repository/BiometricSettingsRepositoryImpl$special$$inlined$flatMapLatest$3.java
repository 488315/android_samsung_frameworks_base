package com.android.systemui.keyguard.data.repository;

import android.content.pm.UserInfo;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3", m277f = "BiometricSettingsRepository.kt", m278l = {190}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BiometricSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3(Continuation continuation, BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl) {
        super(3, continuation);
        this.this$0 = biometricSettingsRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3 biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3 = new BiometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3((Continuation) obj3, this.this$0);
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3.L$0 = (FlowCollector) obj;
        biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3.L$1 = obj2;
        return biometricSettingsRepositoryImpl$special$$inlined$flatMapLatest$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final UserInfo userInfo = (UserInfo) this.L$1;
            final BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl = this.this$0;
            final ReadonlyStateFlow readonlyStateFlow = biometricSettingsRepositoryImpl.isFaceEnabledByBiometricsManager;
            Flow flow = new Flow() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManagerForCurrentUser$lambda$6$$inlined$map$1

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManagerForCurrentUser$lambda$6$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ UserInfo $userInfo$inlined;
                    public final /* synthetic */ BiometricSettingsRepositoryImpl this$0;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManagerForCurrentUser$lambda$6$$inlined$map$1$2", m277f = "BiometricSettingsRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManagerForCurrentUser$lambda$6$$inlined$map$1$2$1, reason: invalid class name */
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
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector, BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl, UserInfo userInfo) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = biometricSettingsRepositoryImpl;
                        this.$userInfo$inlined = userInfo;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        int i;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i2 = anonymousClass1.label;
                            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    Boolean bool = (Boolean) ((LinkedHashMap) this.this$0.biometricsEnabledForUser).get(new Integer(this.$userInfo$inlined.id));
                                    Boolean valueOf = Boolean.valueOf(bool != null ? bool.booleanValue() : false);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        anonymousClass1 = new AnonymousClass1(continuation);
                        Object obj22 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = anonymousClass1.label;
                        if (i != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, biometricSettingsRepositoryImpl, userInfo), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
