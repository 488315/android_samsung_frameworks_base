package com.android.compose.animation.scene;

import androidx.activity.BackEventCompat;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SnapSpec;
import androidx.compose.foundation.gestures.Orientation;
import com.android.compose.animation.scene.UserActionResult;
import com.android.mechanics.ProvidedGestureContext;
import com.android.mechanics.spec.InputDirection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes.dex */
final class PredictiveBackHandlerKt$PredictiveBackHandler$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SceneTransitionLayoutImpl $layoutImpl;
    final /* synthetic */ UserActionResult $result;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PredictiveBackHandlerKt$PredictiveBackHandler$1$1(UserActionResult userActionResult, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Continuation continuation) {
        super(2, continuation);
        this.$result = userActionResult;
        this.$layoutImpl = sceneTransitionLayoutImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PredictiveBackHandlerKt$PredictiveBackHandler$1$1 predictiveBackHandlerKt$PredictiveBackHandler$1$1 = new PredictiveBackHandlerKt$PredictiveBackHandler$1$1(this.$result, this.$layoutImpl, continuation);
        predictiveBackHandlerKt$PredictiveBackHandler$1$1.L$0 = obj;
        return predictiveBackHandlerKt$PredictiveBackHandler$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PredictiveBackHandlerKt$PredictiveBackHandler$1$1) create((Flow) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0030, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.first(r2, r20) == r1) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00d8, code lost:
    
        if (r0 == r1) goto L42;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        UserActionResult replaceByOverlay;
        UserActionResult userActionResult;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        final Flow flow = (Flow) this.L$0;
        UserActionResult userActionResult2 = this.$result;
        if (userActionResult2 == null) {
            this.label = 1;
        } else {
            if (userActionResult2 instanceof UserActionResult.ShowOverlay) {
                this.$layoutImpl.hideOverlays$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(((UserActionResult.ShowOverlay) userActionResult2).hideCurrentOverlays);
            }
            if (this.$result.getTransitionKey() != null) {
                userActionResult = this.$result;
            } else {
                UserActionResult userActionResult3 = this.$result;
                TransitionKey.Companion.getClass();
                TransitionKey transitionKey = TransitionKey.PredictiveBack;
                if (userActionResult3 instanceof UserActionResult.ChangeScene) {
                    UserActionResult.ChangeScene changeScene = (UserActionResult.ChangeScene) userActionResult3;
                    replaceByOverlay = new UserActionResult.ChangeScene(changeScene.toScene, transitionKey, changeScene.requiresFullDistanceSwipe);
                } else if (userActionResult3 instanceof UserActionResult.ShowOverlay) {
                    UserActionResult.ShowOverlay showOverlay = (UserActionResult.ShowOverlay) userActionResult3;
                    replaceByOverlay = new UserActionResult.ShowOverlay(showOverlay.overlay, transitionKey, showOverlay.requiresFullDistanceSwipe, showOverlay.hideCurrentOverlays);
                } else if (userActionResult3 instanceof UserActionResult.HideOverlay) {
                    UserActionResult.HideOverlay hideOverlay = (UserActionResult.HideOverlay) userActionResult3;
                    replaceByOverlay = new UserActionResult.HideOverlay(hideOverlay.overlay, transitionKey, hideOverlay.requiresFullDistanceSwipe);
                } else {
                    if (!(userActionResult3 instanceof UserActionResult.ReplaceByOverlay)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    UserActionResult.ReplaceByOverlay replaceByOverlay2 = (UserActionResult.ReplaceByOverlay) userActionResult3;
                    replaceByOverlay = new UserActionResult.ReplaceByOverlay(replaceByOverlay2.overlay, transitionKey, replaceByOverlay2.requiresFullDistanceSwipe);
                }
                userActionResult = replaceByOverlay;
            }
            UserActionResult userActionResult4 = userActionResult;
            Orientation orientation = Orientation.Horizontal;
            ProvidedGestureContext providedGestureContext = new ProvidedGestureContext(0.0f, InputDirection.Max);
            SceneTransitionLayoutImpl sceneTransitionLayoutImpl = this.$layoutImpl;
            SwipeAnimation swipeAnimationCreateSwipeAnimation = SwipeAnimationKt.createSwipeAnimation(sceneTransitionLayoutImpl, userActionResult4, false, orientation, providedGestureContext, sceneTransitionLayoutImpl.decayAnimationSpec, 1.0f);
            MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = this.$layoutImpl.state;
            Flow flow2 = new Flow() { // from class: com.android.compose.animation.scene.PredictiveBackHandlerKt$PredictiveBackHandler$1$1$invokeSuspend$$inlined$map$1

                /* renamed from: com.android.compose.animation.scene.PredictiveBackHandlerKt$PredictiveBackHandler$1$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.compose.animation.scene.PredictiveBackHandlerKt$PredictiveBackHandler$1$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                            Float f = new Float(((BackEventCompat) obj).progress);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            SnapSpec snapSpecSnap$default = AnimationSpecKt.snap$default();
            CoroutineScope coroutineScope = this.$layoutImpl.animationScope;
            this.label = 2;
            Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new PredictiveBackHandlerKt$animateProgress$2(coroutineScope, mutableSceneTransitionLayoutStateImpl, swipeAnimationCreateSwipeAnimation, flow2, null, snapSpecSnap$default, null), this);
            if (objCoroutineScope != obj2) {
                objCoroutineScope = Unit.INSTANCE;
            }
        }
        return obj2;
    }
}
