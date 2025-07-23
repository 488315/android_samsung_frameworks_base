package com.android.systemui.navigationbar.gestural.domain;

import com.android.systemui.navigationbar.gestural.domain.GestureInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class GestureInteractor$removeGestureBlockedMatcher$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ GestureInteractor.Scope $gestureScope;
    final /* synthetic */ TaskMatcher $matcher;
    int label;
    final /* synthetic */ GestureInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GestureInteractor.Scope.values().length];
            try {
                iArr[GestureInteractor.Scope.Local.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[GestureInteractor.Scope.Global.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GestureInteractor$removeGestureBlockedMatcher$1(GestureInteractor.Scope scope, GestureInteractor gestureInteractor, TaskMatcher taskMatcher, Continuation continuation) {
        super(2, continuation);
        this.$gestureScope = scope;
        this.this$0 = gestureInteractor;
        this.$matcher = taskMatcher;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GestureInteractor$removeGestureBlockedMatcher$1(this.$gestureScope, this.this$0, this.$matcher, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GestureInteractor$removeGestureBlockedMatcher$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0037, code lost:
    
        if (((com.android.systemui.navigationbar.gestural.data.respository.GestureRepositoryImpl) r5).removeGestureBlockedMatcher(r1, r4) == r0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x005d, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x005b, code lost:
    
        if (kotlin.Unit.INSTANCE == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L18
            if (r1 == r3) goto Lc
            if (r1 != r2) goto L10
        Lc:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L5e
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.navigationbar.gestural.domain.GestureInteractor$Scope r5 = r4.$gestureScope
            int[] r1 = com.android.systemui.navigationbar.gestural.domain.GestureInteractor$removeGestureBlockedMatcher$1.WhenMappings.$EnumSwitchMapping$0
            int r5 = r5.ordinal()
            r5 = r1[r5]
            if (r5 == r3) goto L40
            if (r5 != r2) goto L3a
            com.android.systemui.navigationbar.gestural.domain.GestureInteractor r5 = r4.this$0
            com.android.systemui.navigationbar.gestural.data.respository.GestureRepository r5 = r5.gestureRepository
            com.android.systemui.navigationbar.gestural.domain.TaskMatcher r1 = r4.$matcher
            r4.label = r2
            com.android.systemui.navigationbar.gestural.data.respository.GestureRepositoryImpl r5 = (com.android.systemui.navigationbar.gestural.data.respository.GestureRepositoryImpl) r5
            java.lang.Object r4 = r5.removeGestureBlockedMatcher(r1, r4)
            if (r4 != r0) goto L5e
            goto L5d
        L3a:
            kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
            r4.<init>()
            throw r4
        L40:
            com.android.systemui.navigationbar.gestural.domain.GestureInteractor r5 = r4.this$0
            kotlinx.coroutines.flow.StateFlowImpl r5 = r5._localGestureBlockedMatchers
            java.lang.Object r1 = r5.getValue()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.Set r1 = kotlin.collections.CollectionsKt___CollectionsKt.toMutableSet(r1)
            com.android.systemui.navigationbar.gestural.domain.TaskMatcher r2 = r4.$matcher
            r1.remove(r2)
            r4.label = r3
            r4 = 0
            r5.updateState(r4, r1)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            if (r4 != r0) goto L5e
        L5d:
            return r0
        L5e:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.domain.GestureInteractor$removeGestureBlockedMatcher$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
