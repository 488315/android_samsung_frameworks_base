package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$shadeCollapseFadeIn$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SharedNotificationContainerViewModel this$0;

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(!this.Z$0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.Z$0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerViewModel$shadeCollapseFadeIn$1(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sharedNotificationContainerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SharedNotificationContainerViewModel$shadeCollapseFadeIn$1 sharedNotificationContainerViewModel$shadeCollapseFadeIn$1 = new SharedNotificationContainerViewModel$shadeCollapseFadeIn$1(this.this$0, continuation);
        sharedNotificationContainerViewModel$shadeCollapseFadeIn$1.L$0 = obj;
        return sharedNotificationContainerViewModel$shadeCollapseFadeIn$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SharedNotificationContainerViewModel$shadeCollapseFadeIn$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x00c0, code lost:
    
        if (r12.collect(r7, r11) != r0) goto L9;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006f A[PHI: r1
      0x006f: PHI (r1v2 kotlinx.coroutines.flow.FlowCollector) = (r1v3 kotlinx.coroutines.flow.FlowCollector), (r1v14 kotlinx.coroutines.flow.FlowCollector) binds: [B:23:0x006c, B:13:0x002c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0083 A[PHI: r1
      0x0083: PHI (r1v1 kotlinx.coroutines.flow.FlowCollector) = (r1v2 kotlinx.coroutines.flow.FlowCollector), (r1v16 kotlinx.coroutines.flow.FlowCollector) binds: [B:26:0x0080, B:12:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c3  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00c0 -> B:9:0x001a). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        final FlowCollector flowCollector2;
        Flow flow;
        AnonymousClass2 anonymousClass2;
        Boolean bool;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            if (JobKt.isActive(getContext())) {
            }
        } else {
            if (i == 1) {
                flowCollector2 = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                bool = Boolean.FALSE;
                this.L$0 = flowCollector2;
                this.label = 2;
                if (flowCollector2.emit(bool, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i == 2) {
                flowCollector2 = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                flow = this.this$0.isShadeLocked;
                anonymousClass2 = new AnonymousClass2(null);
                this.L$0 = flowCollector2;
                this.label = 3;
                if (FlowKt.first(flow, anonymousClass2, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 3) {
                if (i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                flowCollector2 = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                flowCollector = flowCollector2;
                if (JobKt.isActive(getContext())) {
                    return Unit.INSTANCE;
                }
                Flow flow2 = this.this$0.isShadeLocked;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
                this.L$0 = flowCollector;
                this.label = 1;
                if (FlowKt.first(flow2, anonymousClass1, this) != coroutineSingletons) {
                    flowCollector2 = flowCollector;
                    bool = Boolean.FALSE;
                    this.L$0 = flowCollector2;
                    this.label = 2;
                    if (flowCollector2.emit(bool, this) != coroutineSingletons) {
                        flow = this.this$0.isShadeLocked;
                        anonymousClass2 = new AnonymousClass2(null);
                        this.L$0 = flowCollector2;
                        this.label = 3;
                        if (FlowKt.first(flow, anonymousClass2, this) != coroutineSingletons) {
                            SharedNotificationContainerViewModel sharedNotificationContainerViewModel = this.this$0;
                            sharedNotificationContainerViewModel.getClass();
                            Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                            ref$BooleanRef.element = true;
                            Edge.StateToState stateToStateM = KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.LOCKSCREEN, KeyguardState.AOD);
                            String str = KeyguardTransitionInteractor.TAG;
                            SafeFlow safeFlowTransformWhile = FlowKt.transformWhile(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(sharedNotificationContainerViewModel.isOnLockscreenWithoutShade, sharedNotificationContainerViewModel.keyguardTransitionInteractor.isInTransition(stateToStateM, null), SharedNotificationContainerViewModel$awaitCollapse$3.INSTANCE), new SharedNotificationContainerViewModel$awaitCollapse$4(ref$BooleanRef, null));
                            FlowCollector flowCollector3 = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1.3
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    if (!((Boolean) obj2).booleanValue()) {
                                        return Unit.INSTANCE;
                                    }
                                    Object objEmit = flowCollector2.emit(Boolean.TRUE, continuation);
                                    return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
                                }
                            };
                            this.L$0 = flowCollector2;
                            this.label = 4;
                        }
                    }
                }
                return coroutineSingletons;
            }
            flowCollector2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            SharedNotificationContainerViewModel sharedNotificationContainerViewModel2 = this.this$0;
            sharedNotificationContainerViewModel2.getClass();
            Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
            ref$BooleanRef2.element = true;
            Edge.StateToState stateToStateM2 = KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.LOCKSCREEN, KeyguardState.AOD);
            String str2 = KeyguardTransitionInteractor.TAG;
            SafeFlow safeFlowTransformWhile2 = FlowKt.transformWhile(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(sharedNotificationContainerViewModel2.isOnLockscreenWithoutShade, sharedNotificationContainerViewModel2.keyguardTransitionInteractor.isInTransition(stateToStateM2, null), SharedNotificationContainerViewModel$awaitCollapse$3.INSTANCE), new SharedNotificationContainerViewModel$awaitCollapse$4(ref$BooleanRef2, null));
            FlowCollector flowCollector32 = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    if (!((Boolean) obj2).booleanValue()) {
                        return Unit.INSTANCE;
                    }
                    Object objEmit = flowCollector2.emit(Boolean.TRUE, continuation);
                    return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
                }
            };
            this.L$0 = flowCollector2;
            this.label = 4;
        }
    }
}
