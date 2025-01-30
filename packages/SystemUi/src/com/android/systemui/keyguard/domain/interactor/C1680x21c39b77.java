package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.Utils;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1", m277f = "FromLockscreenTransitionInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1 */
/* loaded from: classes.dex */
final class C1680x21c39b77 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new Pair((KeyguardState) obj, Boolean.valueOf(((Boolean) obj2).booleanValue()));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public AnonymousClass3(Object obj) {
            super(3, obj, Utils.Companion.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            Utils.Companion companion = (Utils.Companion) this.receiver;
            Boolean valueOf = Boolean.valueOf(booleanValue);
            companion.getClass();
            return Utils.Companion.toTriple(valueOf, (Pair) obj2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1680x21c39b77(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Continuation<? super C1680x21c39b77> continuation) {
        super(2, continuation);
        this.this$0 = fromLockscreenTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C1680x21c39b77(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1680x21c39b77) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = this.this$0;
            KeyguardInteractor keyguardInteractor = fromLockscreenTransitionInteractor.keyguardInteractor;
            SafeFlow sample = FlowKt.sample(keyguardInteractor.isKeyguardOccluded, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fromLockscreenTransitionInteractor.keyguardTransitionInteractor.finishedKeyguardState, keyguardInteractor.isDreaming, AnonymousClass2.INSTANCE), new AnonymousClass3(Utils.Companion));
            final FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1.4
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Triple triple = (Triple) obj2;
                    boolean booleanValue = ((Boolean) triple.component1()).booleanValue();
                    KeyguardState keyguardState = (KeyguardState) triple.component2();
                    boolean booleanValue2 = ((Boolean) triple.component3()).booleanValue();
                    if (booleanValue && !booleanValue2 && keyguardState == KeyguardState.LOCKSCREEN) {
                        FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor3 = FromLockscreenTransitionInteractor.this;
                        KeyguardTransitionRepository keyguardTransitionRepository = fromLockscreenTransitionInteractor3.keyguardTransitionRepository;
                        KeyguardState keyguardState2 = KeyguardState.OCCLUDED;
                        FromLockscreenTransitionInteractor.Companion.getClass();
                        ((KeyguardTransitionRepositoryImpl) keyguardTransitionRepository).startTransition(new TransitionInfo(fromLockscreenTransitionInteractor3.name, keyguardState, keyguardState2, fromLockscreenTransitionInteractor3.m1578getAnimatorLRDsOJo(FromLockscreenTransitionInteractor.TO_OCCLUDED_DURATION)), false);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (sample.collect(flowCollector, this) == coroutineSingletons) {
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
