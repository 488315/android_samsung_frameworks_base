package com.android.systemui.development.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.development.data.repository.DevelopmentSettingRepository;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildNumberInteractor$buildNumber$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DevelopmentSettingRepository $repository;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildNumberInteractor$buildNumber$1(DevelopmentSettingRepository developmentSettingRepository, Continuation continuation) {
        super(2, continuation);
        this.$repository = developmentSettingRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BuildNumberInteractor$buildNumber$1 buildNumberInteractor$buildNumber$1 = new BuildNumberInteractor$buildNumber$1(this.$repository, continuation);
        buildNumberInteractor$buildNumber$1.L$0 = obj;
        return buildNumberInteractor$buildNumber$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuildNumberInteractor$buildNumber$1) create((UserInfo) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final UserInfo userInfo = (UserInfo) this.L$0;
        final DevelopmentSettingRepository developmentSettingRepository = this.$repository;
        developmentSettingRepository.getClass();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), developmentSettingRepository.settingFlow);
        return FlowKt.flowOn(new Flow() { // from class: com.android.systemui.development.data.repository.DevelopmentSettingRepository$isDevelopmentSettingEnabled$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.development.data.repository.DevelopmentSettingRepository$isDevelopmentSettingEnabled$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserInfo $userInfo$inlined;
                public final /* synthetic */ DevelopmentSettingRepository this$0;

                /* renamed from: com.android.systemui.development.data.repository.DevelopmentSettingRepository$isDevelopmentSettingEnabled$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DevelopmentSettingRepository developmentSettingRepository, UserInfo userInfo) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = developmentSettingRepository;
                    this.$userInfo$inlined = userInfo;
                }

                /* JADX WARN: Code restructure failed: missing block: B:18:0x005c, code lost:
                
                    if (r6.emit(r8, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.development.data.repository.DevelopmentSettingRepository$isDevelopmentSettingEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.development.data.repository.DevelopmentSettingRepository$isDevelopmentSettingEnabled$$inlined$map$1$2$1 r0 = (com.android.systemui.development.data.repository.DevelopmentSettingRepository$isDevelopmentSettingEnabled$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.development.data.repository.DevelopmentSettingRepository$isDevelopmentSettingEnabled$$inlined$map$1$2$1 r0 = new com.android.systemui.development.data.repository.DevelopmentSettingRepository$isDevelopmentSettingEnabled$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3a
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5f
                    L2a:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L32:
                        java.lang.Object r6 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L53
                    L3a:
                        kotlin.ResultKt.throwOnFailure(r8)
                        kotlin.Unit r7 = (kotlin.Unit) r7
                        android.content.pm.UserInfo r7 = r6.$userInfo$inlined
                        kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                        r0.L$0 = r8
                        r0.label = r4
                        com.android.systemui.development.data.repository.DevelopmentSettingRepository r6 = r6.this$0
                        java.lang.Object r6 = com.android.systemui.development.data.repository.DevelopmentSettingRepository.access$checkDevelopmentSettingEnabled(r6, r7, r0)
                        if (r6 != r1) goto L50
                        goto L5e
                    L50:
                        r5 = r8
                        r8 = r6
                        r6 = r5
                    L53:
                        r7 = 0
                        r0.L$0 = r7
                        r0.label = r3
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L5f
                    L5e:
                        return r1
                    L5f:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.development.data.repository.DevelopmentSettingRepository$isDevelopmentSettingEnabled$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, developmentSettingRepository, userInfo), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, developmentSettingRepository.backgroundDispatcher);
    }
}
