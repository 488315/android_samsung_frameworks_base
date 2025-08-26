package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.util.kotlin.Utils;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
final class FromDozingTransitionInteractor$listenForDozingToAny$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromDozingTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToAny$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FromDozingTransitionInteractor this$0;

        public AnonymousClass2(FromDozingTransitionInteractor fromDozingTransitionInteractor) {
            this.this$0 = fromDozingTransitionInteractor;
        }

        /* JADX WARN: Code restructure failed: missing block: B:34:0x0146, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r13.this$0, r2, null, null, "lockscreen not enabled", r6, 6) == r0) goto L77;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0161, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r1, r2, null, null, "lockscreen not enabled", r6, 6) == r0) goto L77;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x018d, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r13.this$0, r2, null, null, null, r6, 14) == r0) goto L77;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x01aa, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r13.this$0, r2, null, null, null, r6, 14) == r0) goto L77;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x01e4, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r13.this$0, r2, null, null, null, r6, 14) == r0) goto L77;
         */
        /* JADX WARN: Code restructure failed: missing block: B:76:0x0207, code lost:
        
            if (com.android.systemui.keyguard.domain.interactor.TransitionInteractor.startTransitionTo$default(r1, r2, null, null, r5, r6, 6) == r0) goto L77;
         */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00f4  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0168  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Triple triple, Continuation continuation) {
            FromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1 fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1;
            boolean zBooleanValue;
            boolean zBooleanValue2;
            boolean z;
            boolean z2;
            if (continuation instanceof FromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1) {
                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1 = (FromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1) continuation;
                int i = fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1 = new FromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1(this, continuation);
                }
            }
            FromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1 fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12 = fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1;
            Object objIsLockscreenEnabled = fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            switch (fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.label) {
                case 0:
                    ResultKt.throwOnFailure(objIsLockscreenEnabled);
                    WakefulnessModel wakefulnessModel = (WakefulnessModel) triple.component1();
                    zBooleanValue = ((Boolean) triple.component2()).booleanValue();
                    boolean zBooleanValue3 = ((Boolean) triple.component3()).booleanValue();
                    FromDozingTransitionInteractor fromDozingTransitionInteractor = this.this$0;
                    boolean z3 = fromDozingTransitionInteractor.keyguardViewMediatorHelperImpl.curIsOccluded;
                    KeyguardInteractor keyguardInteractor = fromDozingTransitionInteractor.keyguardInteractor;
                    boolean zBooleanValue4 = ((Boolean) keyguardInteractor.primaryBouncerShowing.$$delegate_0.getValue()).booleanValue();
                    zBooleanValue2 = ((Boolean) keyguardInteractor.isKeyguardGoingAway.$$delegate_0.getValue()).booleanValue();
                    boolean z4 = fromDozingTransitionInteractor.keyguardViewMediatorHelperImpl.curIsOccluded;
                    Object value = keyguardInteractor.isKeyguardOccluded.getValue();
                    StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("listenForDozingToAny isKeyguardOccludedLegacy=", ", keyguardViewMediatorHelperImpl.isOccluded()=", " keyguardInteractor.isKeyguardOccluded.value=", z3, z4);
                    sbM.append(value);
                    sbM.append(" primaryBouncerShowing=");
                    sbM.append(zBooleanValue4);
                    sbM.append(" isKeyguardGoingAway=");
                    KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sbM, zBooleanValue2, "FromDozingTransitionInteractor");
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$0 = this;
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$1 = wakefulnessModel;
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.Z$0 = zBooleanValue;
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.Z$1 = zBooleanValue3;
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.Z$2 = z3;
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.Z$3 = zBooleanValue4;
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.Z$4 = zBooleanValue2;
                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.label = 1;
                    objIsLockscreenEnabled = ((DeviceEntryRepositoryImpl) fromDozingTransitionInteractor.deviceEntryInteractor.repository).isLockscreenEnabled(fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12);
                    if (objIsLockscreenEnabled != coroutineSingletons) {
                        z = z3;
                        z2 = zBooleanValue4;
                        if (!((Boolean) objIsLockscreenEnabled).booleanValue()) {
                            boolean zAccess$canDismissLockscreen = FromDozingTransitionInteractor.access$canDismissLockscreen(this.this$0);
                            boolean z5 = z;
                            FromDozingTransitionInteractor fromDozingTransitionInteractor2 = this.this$0;
                            if (zAccess$canDismissLockscreen || zBooleanValue2) {
                                KeyguardState keyguardState = KeyguardState.GONE;
                                String str = FromDozingTransitionInteractor.access$canDismissLockscreen(fromDozingTransitionInteractor2) ? "canDismissLockscreen()" : "isKeyguardGoingAway";
                                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$0 = null;
                                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$1 = null;
                                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.label = 4;
                                break;
                            } else if (z2) {
                                KeyguardState keyguardState2 = KeyguardState.PRIMARY_BOUNCER;
                                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$0 = null;
                                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$1 = null;
                                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.label = 5;
                                break;
                            } else if (z5) {
                                KeyguardState keyguardState3 = KeyguardState.OCCLUDED;
                                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$0 = null;
                                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$1 = null;
                                fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.label = 6;
                                break;
                            } else {
                                fromDozingTransitionInteractor2.communalSettingsInteractor.isV2FlagEnabled();
                                if (!zBooleanValue || !fromDozingTransitionInteractor2.dreamManager.canStartDreaming(false)) {
                                    KeyguardState keyguardState4 = KeyguardState.LOCKSCREEN;
                                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$0 = null;
                                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$1 = null;
                                    fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.label = 7;
                                    break;
                                } else {
                                    CommunalSceneInteractor.snapToScene$default(fromDozingTransitionInteractor2.communalSceneInteractor, CommunalScenes.Communal, "from dozing to hub", 0L, 12);
                                    return Unit.INSTANCE;
                                }
                            }
                        } else if (!z && (((Boolean) ((KeyguardBouncerRepositoryImpl) this.this$0.keyguardBouncerRepository).primaryBouncerShow.$$delegate_0.getValue()).booleanValue() || ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isSimPinSecure() || ((Boolean) ((KeyguardBouncerRepositoryImpl) this.this$0.keyguardBouncerRepository).primaryBouncerShowingSoon.$$delegate_0.getValue()).booleanValue())) {
                            KeyguardState keyguardState5 = KeyguardState.PRIMARY_BOUNCER;
                            fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$0 = null;
                            fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$1 = null;
                            fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.label = 2;
                            break;
                        } else {
                            FromDozingTransitionInteractor fromDozingTransitionInteractor3 = this.this$0;
                            KeyguardState keyguardState6 = KeyguardState.GONE;
                            fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$0 = null;
                            fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$1 = null;
                            fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.label = 3;
                            break;
                        }
                    }
                    return coroutineSingletons;
                case 1:
                    boolean z6 = fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.Z$4;
                    z2 = fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.Z$3;
                    z = fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.Z$2;
                    zBooleanValue = fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.Z$0;
                    AnonymousClass2 anonymousClass2 = (AnonymousClass2) fromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$12.L$0;
                    ResultKt.throwOnFailure(objIsLockscreenEnabled);
                    zBooleanValue2 = z6;
                    this = anonymousClass2;
                    if (!((Boolean) objIsLockscreenEnabled).booleanValue()) {
                    }
                    return coroutineSingletons;
                case 2:
                    ResultKt.throwOnFailure(objIsLockscreenEnabled);
                    return Unit.INSTANCE;
                case 3:
                    ResultKt.throwOnFailure(objIsLockscreenEnabled);
                    return Unit.INSTANCE;
                case 4:
                    ResultKt.throwOnFailure(objIsLockscreenEnabled);
                    return Unit.INSTANCE;
                case 5:
                    ResultKt.throwOnFailure(objIsLockscreenEnabled);
                    return Unit.INSTANCE;
                case 6:
                    ResultKt.throwOnFailure(objIsLockscreenEnabled);
                    return Unit.INSTANCE;
                case 7:
                    ResultKt.throwOnFailure(objIsLockscreenEnabled);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDozingTransitionInteractor$listenForDozingToAny$1(FromDozingTransitionInteractor fromDozingTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromDozingTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromDozingTransitionInteractor$listenForDozingToAny$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromDozingTransitionInteractor$listenForDozingToAny$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Utils.Companion companion = Utils.Companion;
            FromDozingTransitionInteractor fromDozingTransitionInteractor = this.this$0;
            Flow flowSample = companion.sample(new TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1(FlowKt.debounce(fromDozingTransitionInteractor.powerInteractor.detailedWakefulness, 50L), fromDozingTransitionInteractor, new FromDozingTransitionInteractor$listenForDozingToAny$1$$ExternalSyntheticLambda0()), this.this$0.communalInteractor.isCommunalAvailable(), this.this$0.communalSettingsInteractor.autoOpenEnabled);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0);
            this.label = 1;
            if (flowSample.collect(anonymousClass2, this) == coroutineSingletons) {
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
