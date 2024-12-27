package com.android.systemui.shade;

import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class ShadeControllerSceneImpl$setVisibilityListener$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ShadeController.ShadeVisibilityListener $listener;
    int label;
    final /* synthetic */ ShadeControllerSceneImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeControllerSceneImpl$setVisibilityListener$1(ShadeControllerSceneImpl shadeControllerSceneImpl, ShadeController.ShadeVisibilityListener shadeVisibilityListener, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeControllerSceneImpl;
        this.$listener = shadeVisibilityListener;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeControllerSceneImpl$setVisibilityListener$1(this.this$0, this.$listener, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeControllerSceneImpl$setVisibilityListener$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ShadeControllerSceneImpl shadeControllerSceneImpl = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = shadeControllerSceneImpl.sceneInteractor.isVisible;
            final ShadeController.ShadeVisibilityListener shadeVisibilityListener = this.$listener;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.shade.ShadeControllerSceneImpl$setVisibilityListener$1.1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$setVisibilityListener$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C01841 extends SuspendLambda implements Function2 {
                    final /* synthetic */ boolean $isVisible;
                    final /* synthetic */ ShadeController.ShadeVisibilityListener $listener;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C01841(ShadeController.ShadeVisibilityListener shadeVisibilityListener, boolean z, Continuation continuation) {
                        super(2, continuation);
                        this.$listener = shadeVisibilityListener;
                        this.$isVisible = z;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C01841(this.$listener, this.$isVisible, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C01841) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (this.label != 0) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        ((CentralSurfacesImpl.AnonymousClass4) this.$listener).expandedVisibleChanged(this.$isVisible);
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object withContext = BuildersKt.withContext(ShadeControllerSceneImpl.this.mainDispatcher, new C01841(shadeVisibilityListener, ((Boolean) obj2).booleanValue(), null), continuation);
                    return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
