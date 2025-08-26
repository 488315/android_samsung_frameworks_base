package com.android.systemui.qs.composefragment;

import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutState;
import com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl;
import com.android.compose.animation.scene.SceneKey;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
final class QSFragmentComposeKt$synchronizeQsState$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $editMode;
    final /* synthetic */ Flow $expansion;
    final /* synthetic */ MutableSceneTransitionLayoutState $state;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.qs.composefragment.QSFragmentComposeKt$synchronizeQsState$2$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return new Pair(bool, new Float(((Number) obj2).floatValue()));
        }
    }

    /* renamed from: com.android.systemui.qs.composefragment.QSFragmentComposeKt$synchronizeQsState$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ CoroutineScope $animationScope;
        final /* synthetic */ Ref$ObjectRef<ExpansionTransition> $currentTransition;
        final /* synthetic */ MutableSceneTransitionLayoutState $state;
        float F$0;
        /* synthetic */ Object L$0;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState, CoroutineScope coroutineScope, Ref$ObjectRef<ExpansionTransition> ref$ObjectRef, Continuation continuation) {
            super(2, continuation);
            this.$state = mutableSceneTransitionLayoutState;
            this.$animationScope = coroutineScope;
            this.$currentTransition = ref$ObjectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$state, this.$animationScope, this.$currentTransition, continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x006b, code lost:
        
            if (r4.join(r7) == r0) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x006e, code lost:
        
            r0 = r8;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x00a6, code lost:
        
            if (r4.join(r7) == r0) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x00a8, code lost:
        
            return r0;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00ab  */
        /* JADX WARN: Type inference failed for: r0v9, types: [T, com.android.compose.animation.scene.content.state.TransitionState$Transition, com.android.systemui.qs.composefragment.ExpansionTransition] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            boolean zBooleanValue;
            float fFloatValue;
            Pair targetScene$default;
            float f;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Pair pair = (Pair) this.L$0;
                zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                fFloatValue = ((Number) pair.component2()).floatValue();
                if (zBooleanValue) {
                    SceneKey currentScene = ((MutableSceneTransitionLayoutStateImpl) this.$state).getCurrentScene();
                    SceneKeys.INSTANCE.getClass();
                    SceneKey sceneKey = SceneKeys.EditMode;
                    if (!Intrinsics.areEqual(currentScene, sceneKey)) {
                        Pair targetScene$default2 = MutableSceneTransitionLayoutState.setTargetScene$default(this.$state, sceneKey, this.$animationScope);
                        if (targetScene$default2 != null && (r4 = (Job) targetScene$default2.getSecond()) != null) {
                            this.Z$0 = zBooleanValue;
                            this.F$0 = fFloatValue;
                            this.label = 1;
                        }
                    } else if (!zBooleanValue) {
                        SceneKey currentScene2 = ((MutableSceneTransitionLayoutStateImpl) this.$state).getCurrentScene();
                        SceneKeys.INSTANCE.getClass();
                        if (Intrinsics.areEqual(currentScene2, SceneKeys.EditMode) && (targetScene$default = MutableSceneTransitionLayoutState.setTargetScene$default(this.$state, SceneKeys.QuickSettings, this.$animationScope)) != null && (r4 = (Job) targetScene$default.getSecond()) != null) {
                            this.Z$0 = zBooleanValue;
                            this.F$0 = fFloatValue;
                            this.label = 2;
                        }
                    }
                }
                if (!zBooleanValue) {
                    if (fFloatValue == 0.0f) {
                        MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState = this.$state;
                        Ref$ObjectRef<ExpansionTransition> ref$ObjectRef = this.$currentTransition;
                        SceneKeys.INSTANCE.getClass();
                        MutableSceneTransitionLayoutState.snapTo$default(mutableSceneTransitionLayoutState, SceneKeys.QuickQuickSettings, null, 2);
                        ref$ObjectRef.element = null;
                    } else if (fFloatValue == 1.0f) {
                        MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState2 = this.$state;
                        Ref$ObjectRef<ExpansionTransition> ref$ObjectRef2 = this.$currentTransition;
                        SceneKeys.INSTANCE.getClass();
                        MutableSceneTransitionLayoutState.snapTo$default(mutableSceneTransitionLayoutState2, SceneKeys.QuickSettings, null, 2);
                        ref$ObjectRef2.element = null;
                    } else {
                        ExpansionTransition expansionTransition = this.$currentTransition.element;
                        if (expansionTransition != null) {
                            ((SnapshotMutableFloatStateImpl) expansionTransition.progress$delegate).setFloatValue(fFloatValue);
                            return Unit.INSTANCE;
                        }
                        ?? expansionTransition2 = new ExpansionTransition(fFloatValue);
                        this.$currentTransition.element = expansionTransition2;
                        ((MutableSceneTransitionLayoutStateImpl) this.$state).startTransitionImmediately(this.$animationScope, expansionTransition2, true);
                    }
                }
                return Unit.INSTANCE;
            }
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            f = this.F$0;
            zBooleanValue = this.Z$0;
            ResultKt.throwOnFailure(obj);
            fFloatValue = f;
            if (!zBooleanValue) {
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSFragmentComposeKt$synchronizeQsState$2(Flow flow, Flow flow2, MutableSceneTransitionLayoutState mutableSceneTransitionLayoutState, Continuation continuation) {
        super(2, continuation);
        this.$editMode = flow;
        this.$expansion = flow2;
        this.$state = mutableSceneTransitionLayoutState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QSFragmentComposeKt$synchronizeQsState$2 qSFragmentComposeKt$synchronizeQsState$2 = new QSFragmentComposeKt$synchronizeQsState$2(this.$editMode, this.$expansion, this.$state, continuation);
        qSFragmentComposeKt$synchronizeQsState$2.L$0 = obj;
        return qSFragmentComposeKt$synchronizeQsState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSFragmentComposeKt$synchronizeQsState$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.$editMode, this.$expansion, AnonymousClass3.INSTANCE);
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$state, coroutineScope, ref$ObjectRef, null);
            this.label = 1;
            if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, anonymousClass4, this) == coroutineSingletons) {
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
