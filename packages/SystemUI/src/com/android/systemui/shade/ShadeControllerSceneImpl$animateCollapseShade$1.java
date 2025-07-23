package com.android.systemui.shade;

import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeControllerSceneImpl$animateCollapseShade$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShadeControllerSceneImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.shade.ShadeControllerSceneImpl$animateCollapseShade$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ShadeControllerSceneImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ShadeControllerSceneImpl shadeControllerSceneImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = shadeControllerSceneImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ShadeControllerSceneImpl shadeControllerSceneImpl = this.this$0;
            int i = ShadeControllerSceneImpl.$r8$clinit;
            shadeControllerSceneImpl.getClass();
            TransitionKeys.INSTANCE.getClass();
            ((ShadeInteractorImpl) shadeControllerSceneImpl.shadeInteractor).collapseEitherShade("ShadeController.animateCollapseShade", TransitionKeys.SlightlyFasterShadeCollapse);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeControllerSceneImpl$animateCollapseShade$1(ShadeControllerSceneImpl shadeControllerSceneImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeControllerSceneImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeControllerSceneImpl$animateCollapseShade$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeControllerSceneImpl$animateCollapseShade$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r1, r3, r5) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0027, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(125, r5) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r6)
            goto L3d
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L2a
        L1c:
            kotlin.ResultKt.throwOnFailure(r6)
            r5.label = r3
            r3 = 125(0x7d, double:6.2E-322)
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r6 != r0) goto L2a
            goto L3c
        L2a:
            com.android.systemui.shade.ShadeControllerSceneImpl r6 = r5.this$0
            kotlinx.coroutines.CoroutineDispatcher r1 = r6.mainDispatcher
            com.android.systemui.shade.ShadeControllerSceneImpl$animateCollapseShade$1$1 r3 = new com.android.systemui.shade.ShadeControllerSceneImpl$animateCollapseShade$1$1
            r4 = 0
            r3.<init>(r6, r4)
            r5.label = r2
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r1, r3, r5)
            if (r5 != r0) goto L3d
        L3c:
            return r0
        L3d:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ShadeControllerSceneImpl$animateCollapseShade$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
